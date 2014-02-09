package org.simulator.lifegame;

public class Schale {
	private Medium medium;
	
	/*
	 * シャーレのコンストラクタ
	 */
	public Schale(){
		this.medium = new Medium();
	}
	
	/*
	 * シャーレのコンストラクタ
	 * 培地の要素数を指定できる
	 * @param maxX, maxY 培地の要素数
	 */
	public Schale(int width, int height){
		this.medium = new Medium(width, height);
	}
	
	/*
	 * シャーレの培地を初期化します
	 * @param medium 培地
	 */
	public void initialize(Medium medium){
		this.medium = medium;
	}
	
	/*
	 * 世代を更新し、培地を更新します
	 */
	public void shiftTheGeneration(){
		
		for(int x=0; x < this.medium.getWidth(); x++){
			for(int y=0; y < this.medium.getHeight(); y++){
				this.medium.getCell(x, y).bleed();
			}
		}
		for(int x=0; x < this.medium.getWidth(); x++){
			for(int y=0; y < this.medium.getHeight(); y++){
				this.medium.getCell(x, y).update();
			}
		}
		
	}
	
	/*
	 * 培地のシャローコピーを返却します
	 */
	public Medium getMedium(){
		return this.medium;
	}
	
	/*
	 * 培地にあるすべてのセルを殺す
	 */
	public void refresh(){
		this.medium.killAll();
	}
}
