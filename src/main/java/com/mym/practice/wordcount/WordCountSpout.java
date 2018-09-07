package com.mym.practice.wordcount;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class WordCountSpout extends BaseRichSpout{

	private SpoutOutputCollector collector;
	
	/*key=messageId,value=emit的数据包*/
	private Map<String, Values> cacheMap;

	/**初始化*/
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		this.cacheMap = new HashMap<String, Values>();
	}

	/**处理：生命期while(true)循环执行*/
	public void nextTuple() {
		//向bolt发送数据
		Values values = new Values("i am stuff of bonree place in shenzhen and my name is mym who locate in shenzhen");
		String messageId = UUID.randomUUID().toString();
		collector.emit(values, messageId);
		
		//缓存一份
		cacheMap.put(messageId, values);
	}

	/**输出：声明输出字段名*/
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("mym"));
	}
	
	/**收到ack消息时*/
	@Override
	public void ack(Object msgId) {
		// TODO Auto-generated method stub
		super.ack(msgId);
		cacheMap.remove(msgId);
	}
	
	/**收到fail消息时，这里可做重发*/
	@Override
	public void fail(Object msgId) {
		// TODO Auto-generated method stub
		super.fail(msgId);
	}

}
