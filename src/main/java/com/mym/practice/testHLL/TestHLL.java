package com.mym.practice.testHLL;



import java.util.ArrayList;
import java.util.List;

import com.github.prasanthj.hll.HyperLogLog;
import com.github.prasanthj.hll.HyperLogLog.EncodingType;

public class TestHLL {

    public static void main(String[] args) {
        int reTime = 20;
        int j = 0;
        long startTime = 0l;
        long endTime = 0l;
        long totalStartTime = System.currentTimeMillis();
        List<String>  raw = null;
        
        double totalwucha = 0l;
        while(j++ < reTime){
            startTime = System.currentTimeMillis();

            long num = 10000000;
            String key = "hello";

            raw = new ArrayList<>();

            String value = "";
            for(int i = 0; i < num;i++){
                value = "sadgegg"+i+Math.random()*100;
                raw.add(value);

//            store.Hyadd(key,HashUtil.murmur32AsInt(value)+"");
//            store.Hyadd(key,value);
            }
            totalwucha += testHLLStatistic(raw, key, num);
            endTime = System.currentTimeMillis();
            System.out.println("本次计算耗时："+(endTime - startTime)+", 截至目前计算了"+j+"次，平均误差为："+(totalwucha / j));
        }
        long totalEndTime = System.currentTimeMillis();
        System.out.println("总计算耗时："+(totalEndTime - totalStartTime));

    }

    /**返回误差*/
    public static double testHLLStatistic(List<String> raw, String key, long num){
//        HyperlogStore store = new HyperlogStore();
    	HyperLogLog hll1 = HyperLogLog.builder().setEncoding(EncodingType.SPARSE).setNumRegisterIndexBits(4).build();

        for(String str:raw){
//            store.Hyadd(key,str);
        	hll1.addString(str);
        }

        double hycount = hll1.count();
        double abs = Math.abs((num-hycount) / num);
        
        
        System.out.println(num+"个基数统计结果："+(long)hycount+"，误差："+abs);
        hll1 = null;
        
        return abs;
    }

}
