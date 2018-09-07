package com.mym.practice.wordcount;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class WordCountCalcBolt extends BaseRichBolt{
	
    private OutputCollector collector;
    
    private Map<String, Integer> map;

	/**初始化*/
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		this.map = new HashMap<String, Integer>();
	}

	/**循环执行*/
	public void execute(Tuple input) {
		try {
			String word = input.getString(0);
			Integer num = input.getInteger(1);//与上一个节点的declare声明的对应 
	        if (map.containsKey(word)){
	            map.put(word,map.get(word) + num);
	        }else {
	            map.put(word,num);
	        }
	        System.out.println("当前线程name："+Thread.currentThread().getName()+"\t当前线程ID："+Thread.currentThread().getId() + "\t 当前处理的 word："+word+"\t 当前统计数量："+ map.get(word));
	        collector.ack(input);
		} catch(Exception e){
			collector.fail(input);
		}
		
	}

	/**声明输出*/
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

}
