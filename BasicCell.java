package org.simulator.lifegame;



/*
 * 一般的なのライフゲームでの繁殖アルゴリズムを持ったセル
 */
public class BasicCell extends AbstractCell{
	public BasicCell(Medium medium, int x, int y, boolean state){
		super(medium, x, y, state);
		this.red = 0;
		this.green = 255;
		this.blue = 0;
	}

	@Override
	public boolean bleed() {
		int livingCellsCount = 0;
		
		if(this.medium.getState(x-1, y-1)) livingCellsCount++;
		if(this.medium.getState(x, y-1)) livingCellsCount++;
		if(this.medium.getState(x+1, y-1)) livingCellsCount++;
		
		if(this.medium.getState(x-1, y)) livingCellsCount++;
		if(this.medium.getState(x-1, y+1)) livingCellsCount++;

		if(this.medium.getState(x, y+1)) livingCellsCount++;
		
		if(this.medium.getState(x+1, y)) livingCellsCount++;
		if(this.medium.getState(x+1, y+1)) livingCellsCount++;
		
		//過密死と過疎死
		if(livingCellsCount >= 4 || livingCellsCount < 2){
			this.child = false;
			return false;
		}
		
		//誕生
		if(livingCellsCount >=3){
			this.child = true;
			return true;
		}
		
		//生存
		this.child = this.state;
		return this.state;
	}

	@Override
	public void update() {
		int rand = SingletonRandom.getInstance().nextInt(1000);
		if(rand < 1 && this.child){
			this.medium.setCell(new StrongCell(this.medium, this.x, this.y, this.child), this.x, this.y);
		}else if(rand < 4 && this.child){
			this.medium.setCell(new KillerCell(this.medium, this.x, this.y, this.child), this.x, this.y);
		}else{
			this.state = this.child;
		}
	}
	
}
