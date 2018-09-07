package com.mym.practice.tridentwordcount;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

public class WCTridentCalcFunction extends BaseFunction{
	
	private Map<String, Integer> map;
	
	public void execute(TridentTuple tuple, TridentCollector collector) {
	
		String word = tuple.getString(0);
		Integer num = tuple.getInteger(1);//与上一个节点的declare声明的对应
        if (map.containsKey(word)){
            map.put(word,map.get(word) + num);
        }else {
            map.put(word,num);
        }
        collector.emit(new Values(word, map.get(word)));
        System.out.println("当前线程name："+Thread.currentThread().getName()+"\t当前线程ID："+Thread.currentThread().getId() + "\t 当前处理的 word："+word+"\t 当前统计数量："+ map.get(word));
	}

	/**task初始化时执行*/
	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		// TODO Auto-generated method stub
		super.prepare(conf, context);
		System.out.println("WCTridentCalcFunction prepare ... ");
		this.map = new HashMap<String, Integer>();
		
	}

	/**task结束时的清理操作*/
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		super.cleanup();
		map.clear();
		System.out.println("WCTridentCalcFunction cleanup ... ");
	}

}
