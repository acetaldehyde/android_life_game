package org.simulator.lifegame;

import java.util.Random;

public class SingletonRandom {
	private static SingletonRandom singletonRandom = new SingletonRandom();
	private Random random;
	private SingletonRandom(){
		this.random = new Random();
	}
	
	public static SingletonRandom getInstance(){
		return singletonRandom;
	}
	
	public int nextInt(int n){
		return this.random.nextInt(n);
	}
	
	public boolean nextBoolean(){
		return this.random.nextBoolean();
	}
}
