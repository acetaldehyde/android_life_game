package org.simulator.lifegame;

/*
 * 培地クラス
 * セルが繁殖する培地
 */
public class Medium{
	private Cell cells[][];
	private int width = 50;
	private int height = 50;
	
	/*
	 * 培地のコンストラクタ
	 * セル数を指定しない場合は、50*50が基本となります
	 */
	public Medium(){
		this.cells = new Cell[this.width][this.height];
		this.initialize();
		this.killAll();
	}
	
	/*
	 * 培地のコンストラクタ
	 * @param width, height 培地の要素数
	 */
	public Medium(int width, int height){
		this.width = width;
		this.height = height;
		this.cells = new Cell[this.width][this.height];
		this.initialize();
		this.killAll();
	}
	
	/*
	 * 培地の全セルを殺します。
	 */
	public void killAll(){
		for(int x=0; x < this.width; x++){
			for(int y=0; y < this.height; y++){
				this.cells[x][y].setState(false);
			}
		}
	}
	
	/*
	 * 全てのセルをBasicCellで埋め尽くし、各自の座標を認識させます。
	 */
	public void initialize(){
		for(int x=0; x < this.width; x++){
			for(int y=0; y < this.height; y++){
				this.cells[x][y] = new BasicCell(this, x, y, false);
			}
		}
	}
	
	/*
	 * セルの状態を返却します
	 * セルが存在しない場合はfalseを返却
	 * @param x,y 状態を取得するセルの座標
	 */
	public boolean getState(int x, int y){
		if(x >= this.width || y >= this.height || x < 0 || y < 0)
			return false;
		
		return this.cells[x][y].isLive();
		
	}
	
	/*
	 * セルを取得します
	 * @param x, y 取得するセルの座標
	 */
	public Cell getCell(int x, int y){
		if(x >= this.width || y >= this.height || x < 0 || y < 0)
			return null;
		
		return this.cells[x][y];
		
	}
	
	/*
	 * セルを挿入します
	 * @param cell セル
	 * @param x, y セットする培地の座標
	 */
	public void setCell(Cell cell, int x, int y){
		if(x < this.width || y < this.height || x >= 0 || y >= 0){
			this.cells[x][y] = cell;
		}
	}
	
	/*
	 * セルの状態を設定します
	 * @param x,y 設定対象のセルの座標
	 * @param state 設定する状態
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
