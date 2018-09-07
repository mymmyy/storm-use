package com.mym.practice.tridentwordcount;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import storm.trident.TridentTopology;

public class StartServer {
	
	private static boolean isLocal = true;

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		
		String topologyName = "test_tridentTopology";
		
		System.out.println("start the topology ... ");
		StormTopology buildTridentTopology = buildTridentTopology(topologyName);
		
		/*创建topology的配置。创建一个configuration，用来指定当前topology 需要的worker的数量*/
		Config config =  new Config();
        config.setNumWorkers(2);
//        config.setNumAckers(4);	
        config.setMaxSpoutPending(2);
        
        /*提交任务：分本地模式和集群模式*/
        if(isLocal) {
        	LocalCluster localCluster = new LocalCluster();
        	localCluster.submitTopology(topologyName, config, buildTridentTopology);
        }else {
        	StormSubmitter.submitTopology(topologyName, config, buildTridentTopology);
        }
	}
	
	public static StormTopology buildTridentTopology(String topologyName) {
		TridentTopology topology = new TridentTopology();
		topology.newStream("WCTSpout", new WCTridentSpout())
			.shuffle()			//随机分配到各个partition。每个partition随机拿到部分
			//.broadcast() 		//广播到partition。每个partition都有一份完整数
			.name(topologyName)
			
			/*Field中的字段个数要与collect.emit()个数一致，否则会缺失数据 */
			.each(new Fields("mym"), new WCTridentSplitFunction(), new Fields("wordcountSplit-word","wordcountSplit-num"))//输入流属性、function对象、输出流属性定义
			/*增加Filter*/
			.each(new Fields("wordcountSplit-word","wordcountSplit-num"), new WCTridentFilter())
			.each(new Fields("wordcountSplit-word","wordcountSplit-num"), new WCTridentCalcFunction(),new Fields("wordcountCalc-word","wordcountCalc-num"))
//			.each(new Fields("wordcountSplit-word","wordcountSplit-num"), new WCTridentCalcFunction(),new Fields("wordcountCalc-word","wordcountCalc-num","wordcountCalc-word-re","wordcountCalc-num-re"))
			.parallelismHint(2)
			
			/*partitionAggregate对每个partition执行一个function操作（实际上是聚合操作），但它又不同于上面的functions操作，partitionAggregate的输出tuple将会取代收到的输入tuple*/
			.partitionAggregate(new Fields("wordcountCalc-word","wordcountCalc-num"), new WCTridentPartitionAggregate(), new Fields("partitionAggregate-word","partitionAggregate-num"))
			.parallelismHint(2)
			
			/*groupBy、aggregate*/
			.groupBy(new Fields("partitionAggregate-word")).name("wordGroupBy")
			.aggregate(new Fields("partitionAggregate-word", "partitionAggregate-num"), new WCTridentAggregate(), new Fields("wordcountAggregate-word","wordcountAggregate-num"))
			.parallelismHint(2)
			
			
			/*groupBy、aggregate*/
//			.groupBy(new Fields("wordcountCalc-word")).name("wordGroupBy")
//			.aggregate(new Fields("wordcountCalc-num", "wordcountCalc-num"), new WCTridentAggregate(), new Fields("wordcountAggregate-word","wordcountAggregate-num"))
//			.parallelismHint(2)

			
			
			;
		
		return topology.build();
	}

	
}


