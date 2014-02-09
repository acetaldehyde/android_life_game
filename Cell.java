package org.simulator.lifegame;

/*
 * Cell�C���^�[�t�F�[�X
 */
public interface Cell {
	/*
	 * �q�̔��������݂�
	 */
	public boolean bleed();
	
	/*
	 * �������シ��
	 */
	public void update();
	
	/*
	 * ��ԁi�����j��ݒ肷��
	 */
	public void setState(Boolean state);
	
	/*
	 * �����m�F
	 */
	public boolean isLive();
	
	/*
	 * �F
	 */
	public int red();
	public int green();
	public int blue();
}
