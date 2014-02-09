package org.simulator.lifegame;

/*
 * �|�n�N���X
 * �Z�����ɐB����|�n
 */
public class Medium{
	private Cell cells[][];
	private int width = 50;
	private int height = 50;
	
	/*
	 * �|�n�̃R���X�g���N�^
	 * �Z�������w�肵�Ȃ��ꍇ�́A50*50����{�ƂȂ�܂�
	 */
	public Medium(){
		this.cells = new Cell[this.width][this.height];
		this.initialize();
		this.killAll();
	}
	
	/*
	 * �|�n�̃R���X�g���N�^
	 * @param width, height �|�n�̗v�f��
	 */
	public Medium(int width, int height){
		this.width = width;
		this.height = height;
		this.cells = new Cell[this.width][this.height];
		this.initialize();
		this.killAll();
	}
	
	/*
	 * �|�n�̑S�Z�����E���܂��B
	 */
	public void killAll(){
		for(int x=0; x < this.width; x++){
			for(int y=0; y < this.height; y++){
				this.cells[x][y].setState(false);
			}
		}
	}
	
	/*
	 * �S�ẴZ����BasicCell�Ŗ��ߐs�����A�e���̍��W��F�������܂��B
	 */
	public void initialize(){
		for(int x=0; x < this.width; x++){
			for(int y=0; y < this.height; y++){
				this.cells[x][y] = new BasicCell(this, x, y, false);
			}
		}
	}
	
	/*
	 * �Z���̏�Ԃ�ԋp���܂�
	 * �Z�������݂��Ȃ��ꍇ��false��ԋp
	 * @param x,y ��Ԃ��擾����Z���̍��W
	 */
	public boolean getState(int x, int y){
		if(x >= this.width || y >= this.height || x < 0 || y < 0)
			return false;
		
		return this.cells[x][y].isLive();
		
	}
	
	/*
	 * �Z�����擾���܂�
	 * @param x, y �擾����Z���̍��W
	 */
	public Cell getCell(int x, int y){
		if(x >= this.width || y >= this.height || x < 0 || y < 0)
			return null;
		
		return this.cells[x][y];
		
	}
	
	/*
	 * �Z����}�����܂�
	 * @param cell �Z��
	 * @param x, y �Z�b�g����|�n�̍��W
	 */
	public void setCell(Cell cell, int x, int y){
		if(x < this.width || y < this.height || x >= 0 || y >= 0){
			this.cells[x][y] = cell;
		}
	}
	
	/*
	 * �Z���̏�Ԃ�ݒ肵�܂�
	 * @param x,y �ݒ�Ώۂ̃Z���̍��W
	 * @param state �ݒ肷����
	 */
	public void setState(int x, int y, boolean state){
		if(x < this.width && y < this.height && x >= 0 && y >= 0)
			this.cells[x][y].setState(state);
	}
	public Medium clone(){
		return this.clone();
	}
	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
}
