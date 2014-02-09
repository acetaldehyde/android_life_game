package org.simulator.lifegame;

/*
 * ’ŠÛCell
 * Å’áŒÀ‚ÌÀ‘•
 */
public abstract class AbstractCell implements Cell {
	protected Medium medium;
	protected Boolean state = false;
	protected Boolean child;
	protected int x, y;
	protected int red, green, blue;

	public AbstractCell(Medium medium, int x, int y, boolean state){
		this.medium = medium;
		this.child = this.state = state;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public abstract boolean bleed();
	
	@Override
	public abstract void update();

	@Override
	public void setState(Boolean state){
		this.state = state;
	}
	
	@Override
	public boolean isLive(){
		return this.state;
	}
	
	public int red(){
		return this.red;
	}
	public int green(){
		return this.green;
	}
	public int blue(){
		return this.blue;
	}
}
