//package com.mym.practice.testHLL;
//
//import com.google.common.hash.HashFunction;
//import net.agkn.hll.HLL;
//
//import java.nio.charset.Charset;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 利用hyperLog java接口代替jedis的的数据统计
// * @author	kehao
// * @date   	2018-6-20 上午11:18:20
// */
//public class HyperlogStore {
//
//    private Map<String, HLL> hyperLogMap = new ConcurrentHashMap<> ();
//
//    private final int seed = 0X1234ABCD;
//    private HashFunction hash = com.google.common.hash.Hashing.murmur3_128(seed);
//    private final Charset CHARSET = Charset.forName ("utf-8");
//    public HyperlogStore () {
//
//    }
//
//    public void hyAdd( String key, String val) {
//        long hashcode = hash.newHasher().putString (val, CHARSET).hash().asLong();
//        HLL hll = getOrCreateByKey (key);
//        synchronized (hll){
//            hll.addRaw (hashcode);
//        }
//    }
//
//    private HLL getOrCreateByKey(String key){
//        HLL hll;
//        if((hll = hyperLogMap.get(key)) == null) {
//            synchronized (this){
//                if((hll = hyperLogMap.get (key)) == null){
//                    hll = new HLL(13, 5); //number of bucket and bits per bucket
//                    hyperLogMap.put(key, hll);
//                }
//            }
//        }
//        return hll;
//    }
//
//    public synchronized long hyCount(String key) {
//        HLL hll;
//        if((hll = hyperLogMap.get(key)) == null) {
//            return 0;
//        } else {
//            return hll.cardinality();
//        }
//    }
//
//}
