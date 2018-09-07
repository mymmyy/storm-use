package com.mym.practice.tridentwordcount;

import java.util.Map;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

public class WCTridentSplitFunction extends BaseFunction{
	
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String line = tuple.getString(0);
		String[] words = line.split(" ");
		for (String word:words){
            collector.emit(new Values(word,1));		//下传
        }
	}

	/**task初始化时执行*/
	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		// TODO Auto-generated method stub
		super.prepare(conf, context);
		System.out.println("WCTridentSplitFunction prepare ... ");
	}

	/**task结束时的清理操作*/
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		super.cleanup();
		System.out.println("WCTridentSplitFunction cleanup ... ");
	}

}
