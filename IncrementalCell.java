package org.simulator.lifegame;

public class IncrementalCell extends AbstractCell {
	private boolean incremental;
	private boolean done;
	public IncrementalCell(Medium medium, int x, int y, boolean state, boolean done) {
		super(medium, x, y, state);
		this.incremental = false;
		this.red = 255;
		this.green = 255;
		this.blue = 0;
		this.done = done;
		// TODO 自動生成されたコンストラクター・スタブ
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
			this.incremental = true;
			return true;
		}
		
		//生存
		return this.state;
	}

	@Override
	public void update() {
		if(this.incremental && this.child && !this.done){
			
			this.medium.setCell(new IncrementalCell(this.medium, this.x-1, this.y-1, true, false), this.x-1, this.y-1);
			this.medium.setCell(new IncrementalCell(this.medium, this.x, this.y-1, true, false), this.x, this.y-1);
			this.medium.setCell(new IncrementalCell(this.medium, this.x+1, this.y-1, true, false), this.x+1, this.y-1);
			
			this.medium.setCell(new IncrementalCell(this.medium, this.x-1, this.y, true, false), this.x-1, this.y);
			this.medium.setCell(new IncrementalCell(this.medium, this.x-1, this.y+1, true, false), this.x-1, this.y+1);
			
			this.medium.setCell(new IncrementalCell(this.medium, this.x, this.y+1, true, false), this.x, this.y+1);
			
			this.medium.setCell(new IncrementalCell(this.medium, this.x+1, this.y, true, true), this.x+1, this.y);
			this.medium.setCell(new IncrementalCell(this.medium, this.x+1, this.y+1, true, false), this.x+1, this.y+1);
			
			
			this.medium.setCell(new BasicCell(this.medium, this.x, this.y, false), this.x, this.y);
		}else if(!this.done){
			this.state = this.child;
		}
		this.done = false;
	}

}
