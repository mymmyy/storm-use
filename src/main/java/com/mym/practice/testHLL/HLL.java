package com.mym.practice.testHLL;

import com.github.prasanthj.hll.HyperLogLog;
import com.github.prasanthj.hll.HyperLogLog.EncodingType;

public class HLL {

	public HLL() {
		
	}
	
	public static void main(String[] args) {
		HyperLogLog hll1 = HyperLogLog.builder().setEncoding(EncodingType.DENSE).build();
		
	}
	
}
