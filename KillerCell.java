package org.simulator.lifegame;

/*
 * キラーセル
 * 過密死せず、4つの生存セルが隣接した場合はそれを殺す。
 */
public class KillerCell extends AbstractCell {
	private boolean kill;
	private boolean death;
	
	public KillerCell(Medium medium, int x, int y, boolean state) {
		super(medium, x, y, state);
		this.kill = this.death = false;
		
		this.red = 255;
		this.green = 0;
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
		
		//過密はキルフラグ
		if(livingCellsCount >= 4){
			this.kill = this.child = true;
			return true;
		}
		
		//誕生
		if(livingCellsCount >=3){
			this.child = true;
			return true;
		}
		
		//過疎は死滅フラグ
		if(livingCellsCount < 2){
			this.child = false;
			this.death = true;
			return false;
		}
		
		//生存
		return this.state;
	}

	@Override
	public void update() {
		if(this.death){
			//死滅フラグが立っていれば、普通のセルに戻って死ぬ
			this.medium.setCell(new BasicCell(this.medium, this.x, this.y, false), this.x, this.y);
		}else if(this.kill){
			//キルフラグが立っていれば、周囲のセルを殺す
			this.medium.setState(x-1, y-1, false);
			this.medium.setState(x, y-1, false);
			this.medium.setState(x+1, y-1, false);
			
			this.medium.setState(x-1, y, false);
			this.medium.setState(x-1, y+1, false);
			
			this.medium.setState(x, y+1, false);
			
			this.medium.setState(x+1, y, false);
			this.medium.setState(x+1, y+1, false);
			
			this.state = this.child;
		}else{
			this.state = this.child;
		}

	}

}
