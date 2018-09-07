package com.mym.practice.wordcount;

public class StartServer {
	
	public static void main(String[] args) {
		//一些准备
		String topologyName = "test_"+(int)(Math.random() * 10);
		boolean isLocal = true;
		
		System.out.println("start the topology ... ");
		try {
			new WordCountTopology().createTopology(topologyName, isLocal);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
