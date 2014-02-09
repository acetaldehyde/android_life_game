package org.simulator.lifegame;
/*
 * ã≠Ç¢ÉZÉã
 * ÉnÉCÉâÉCÉtÉQÅ[ÉÄÇÃÉAÉãÉSÉäÉYÉÄÇ≈ÅA6ÉZÉãê∂ë∂ÇµÇƒÇ¢ÇÍÇŒíaê∂Ç∑ÇÈ
 */
public class StrongCell extends AbstractCell {

	public StrongCell(Medium medium, int x, int y, boolean state) {
		super(medium, x, y, state);

		this.red = 0;
		this.green = 255;
		this.blue = 255;

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
		
		//âﬂñßî≠ê∂
		if(livingCellsCount >= 6){
			this.child = true;
			return true;
		}
		
		//âﬂñßéÄÇ∆âﬂëaéÄ
		if(livingCellsCount >= 4 || livingCellsCount < 2){
			this.child = false;
			return false;
		}
		
		//íaê∂
		if(livingCellsCount >=3){
			this.child = true;
			return true;
		}
		
		//ê∂ë∂
		return this.state;
	}

	@Override
	public void update() {
		int rand = SingletonRandom.getInstance().nextInt(1000);
		if(rand < 1){
			this.medium.setCell(new LongevityCell(this.medium, this.x, this.y), this.x, this.y);
		}else{
			this.state = this.child;
		}
	}

}
