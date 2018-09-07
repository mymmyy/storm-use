package com.mym.practice.wordcount;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordCountSplitBolt extends BaseRichBolt{
	
	private OutputCollector collector;

	/**初始化*/
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	/**循环执行处理tuple*/
	public void execute(Tuple input) {
		try {
			String line = input.getString(0);
			String[] words = line.split(" ");
			for (String word:words){
	            collector.emit(new Values(word,1));
	        }
			
	        collector.ack(input);
		} catch(Exception e){
			collector.fail(input);
		}
	}

	/**声明输出字段名*/
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word","num"));//与new Values(word,1) 关系对应
	}

}
