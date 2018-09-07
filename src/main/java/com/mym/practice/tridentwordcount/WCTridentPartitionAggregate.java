package com.mym.practice.tridentwordcount;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseAggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**分区聚合器 partitionAggregate对每个partition执行一个function操作（实际上是聚合操作），
 * 但它又不同于上面的functions操作，partitionAggregate的输出tuple将会取代收到的输入tuple
 * Map<String,Integer> 是定义了在聚合过程中使用的数据结构，并非输出结构，并非输入结构
 * */
public class WCTridentPartitionAggregate extends BaseAggregator<Map<String,Integer>>{

	/**开始聚合的初始化操作*/
	public Map<String, Integer> init(Object batchId, TridentCollector collector) {
		
		return new HashMap<String, Integer>();
	}

	public void aggregate(Map<String, Integer> val, TridentTuple tuple, TridentCollector collector) {
		//1.获取数据
		String word = tuple.getStringByField("wordcountCalc-word");
		Integer num = tuple.getIntegerByField("wordcountCalc-num");
		
		//2.做聚合操作
		if(val.containsKey(word)) {
			val.put(word, num + val.get(word));
		}else {
			val.put(word, num);
		}
	}

	/**聚合完成之后做的操作*/
	public void complete(Map<String, Integer> val, TridentCollector collector) {
		for(Entry<String, Integer> entry:val.entrySet()) {
			collector.emit(new Values(entry.getKey(), entry.getValue()));
			System.out.println("WCTridentPartitionAggregate - ["+entry.getKey()+","+entry.getValue()+"]");
		}
	}

}
