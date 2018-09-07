package com.mym.practice.tridentwordcount;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

public class WCTridentFilter extends BaseFilter{

	/**根据true或false决定是否要保留这个truple*/
	public boolean isKeep(TridentTuple tuple) {
		return !"i".equals(tuple.getString(0));
	}

}
