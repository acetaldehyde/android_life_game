package org.simulator.lifegame;

/*
 * 無敵セル
 *　最低でも20世代は生き残り、その後通常のセルになる。
 */
public class LongevityCell extends AbstractCell {
	private int limit;
	public LongevityCell(Medium medium, int x, int y) {
		super(medium, x, y, true);
		
		this.limit = SingletonRandom.getInstance().nextInt(80) + 20;
		this.child = this.state = true;
		
		this.red = 0;
		this.green = 0;
		this.blue = 255;
	}

	@Override
	public boolean bleed() {
		this.child = true;
		return true;
	}
	
	@Override
	public void update(){
		this.limit--;
		if(this.limit == 0){
			this.medium.setCell(new BasicCell(this.medium, this.x, this.y, false), this.x, this.y);
		}
	}

}
