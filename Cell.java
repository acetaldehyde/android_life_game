package org.simulator.lifegame;

/*
 * Cellインターフェース
 */
public interface Cell {
	/*
	 * 子の発生を試みる
	 */
	public boolean bleed();
	
	/*
	 * 世代を交代する
	 */
	public void update();
	
	/*
	 * 状態（生死）を設定する
	 */
	public void setState(Boolean state);
	
	/*
	 * 生存確認
	 */
	public boolean isLive();
	
	/*
	 * 色
	 */
	public int red();
	public int green();
	public int blue();
}
