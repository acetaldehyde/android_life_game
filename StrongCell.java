package org.simulator.lifegame;
/*
 * 強いセル
 * ハイライフゲームのアルゴリズムで、6セル生存していれば誕生する
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
		
		//過密発生
		if(livingCellsCount >= 6){
			this.child = true;
			return true;
		}
		
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
