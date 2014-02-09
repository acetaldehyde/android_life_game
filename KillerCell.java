package org.simulator.lifegame;

/*
 * �L���[�Z��
 * �ߖ��������A4�̐����Z�����אڂ����ꍇ�͂�����E���B
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
		
		//�ߖ��̓L���t���O
		if(livingCellsCount >= 4){
			this.kill = this.child = true;
			return true;
		}
		
		//�a��
		if(livingCellsCount >=3){
			this.child = true;
			return true;
		}
		
		//�ߑa�͎��Ńt���O
		if(livingCellsCount < 2){
			this.child = false;
			this.death = true;
			return false;
		}
		
		//����
		return this.state;
	}

	@Override
	public void update() {
		if(this.death){
			//���Ńt���O�������Ă���΁A���ʂ̃Z���ɖ߂��Ď���
			this.medium.setCell(new BasicCell(this.medium, this.x, this.y, false), this.x, this.y);
		}else if(this.kill){
			//�L���t���O�������Ă���΁A���͂̃Z�����E��
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
