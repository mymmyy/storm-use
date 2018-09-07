package com.mym.practice.tridentwordcount;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseAggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**通用聚合器
 * Map<String,Integer> 是定义了在聚合过程中使用的数据结构，并非输出结构，并非输入结构
 * */
public class WCTridentAggregate extends BaseAggregator<Map<String, Integer>>{

	/**在处理数据之前被调用，它的返回值会作为一个状态值传递给aggregate和complete方法*/
	public Map<String, Integer> init(Object batchId, TridentCollector collector) {
		// TODO Auto-generated method stub
		return new HashMap<String, Integer>();
	}

	/**用来处理每一个输入的tuple，它可以更新状态值也可以发射tuple*/
	public void aggregate(Map<String, Integer> val, TridentTuple tuple, TridentCollector collector) {
		//1.获取数据
//		String word = tuple.getStringByField("wordcountCalc-word-re");
//		Integer num = tuple.getIntegerByField("wordcountCalc-num-re");
		String word = tuple.getString(0);
		Integer num = tuple.getInteger(1);
		
		//2.做聚合操作
		if(val.containsKey(word)) {
			val.put(word, num + val.get(word));
		}else {
			val.put(word, num);
		}
		
	}

	/**当所有tuple都被处理完成后被调用*/
	public void complete(Map<String, Integer> val, TridentCollector collector) {
		for(Entry<String, Integer> entry:val.entrySet()) {
			collector.emit(new Values(entry.getKey(), entry.getValue()));
			System.out.println("WCTridentAggregate - ["+entry.getKey()+","+entry.getValue()+"]");
		}
		
	}

}
