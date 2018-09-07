package com.mym.practice.wordcount;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class WordCountTopology {
	
	public void createTopology(String topologyName, boolean isLocal) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		
		/*准备一个topology*/
		TopologyBuilder topologyBuilder = new TopologyBuilder();
		topologyBuilder.setSpout("wordCountSpout", new WordCountSpout(), 2);
		topologyBuilder.setBolt("wordCountSplitBolt", new WordCountSplitBolt(), 2).shuffleGrouping("wordCountSpout");
		topologyBuilder.setBolt("wordCountCalcBolt", new WordCountCalcBolt(), 4).fieldsGrouping("wordCountSplitBolt", new Fields("word"));
		
		/*创建topology的配置。创建一个configuration，用来指定当前topology 需要的worker的数量*/
		Config config =  new Config();
        config.setNumWorkers(2);
        
        /*提交任务：分本地模式和集群模式*/
        if(isLocal) {
        	LocalCluster localCluster = new LocalCluster();
        	localCluster.submitTopology(topologyName, config, topologyBuilder.createTopology());
        }else {
        	StormSubmitter.submitTopology(topologyName, config, topologyBuilder.createTopology());
        }
	}

}
