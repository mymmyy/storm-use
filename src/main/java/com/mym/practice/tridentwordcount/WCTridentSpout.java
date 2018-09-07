package com.mym.practice.tridentwordcount;

import java.util.Map;
import java.util.UUID;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class WCTridentSpout implements IRichSpout{
	
	private SpoutOutputCollector collector;
	
	int i = 0;

	/**task初始化调用*/
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	/**task被shutdown后执行的动作*/
	public void close() {
		// TODO Auto-generated method stub
		System.out.println("WCTridentSpout close ... ");
	}

	/**task被激活时*/
	public void activate() {
		// TODO Auto-generated method stub
		System.out.println("WCTridentSpout activate ... ");
	}

	/**task被deactivate时*/
	public void deactivate() {
		// TODO Auto-generated method stub
		System.out.println("WCTridentSpout deactivate ... ");
	}

	/**核心处理方法*/
	public void nextTuple() {
		
		while(i++ < 100) {
			Values values = new Values("i am stuff of bonree place in shenzhen and my name is mym who locate in shenzhen");
			String messageId = UUID.randomUUID().toString();
			collector.emit(values,messageId);
		}
		System.out.println("spout 暂时没数据了...");

	}

	/**收到ack消息时*/
	public void ack(Object msgId) {
		// TODO Auto-generated method stub
		System.out.println("WCTridentSpout ack ... ");
	}

	/**收到fail消息时，可做重发*/
	public void fail(Object msgId) {
		// TODO Auto-generated method stub
		System.out.println("WCTridentSpout fail ... ");
	}

	/**声明输出*/
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("mym"));
	}

	/**获取本task的component 配置*/
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
