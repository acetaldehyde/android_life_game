package org.simulator.lifegame;

public class Schale {
	private Medium medium;
	
	/*
	 * �V���[���̃R���X�g���N�^
	 */
	public Schale(){
		this.medium = new Medium();
	}
	
	/*
	 * �V���[���̃R���X�g���N�^
	 * �|�n�̗v�f�����w��ł���
	 * @param maxX, maxY �|�n�̗v�f��
	 */
	public Schale(int width, int height){
		this.medium = new Medium(width, height);
	}
	
	/*
	 * �V���[���̔|�n�����������܂�
	 * @param medium �|�n
	 */
	public void initialize(Medium medium){
		this.medium = medium;
	}
	
	/*
	 * ������X�V���A�|�n���X�V���܂�
	 */
	public void shiftTheGeneration(){
		
		for(int x=0; x < this.medium.getWidth(); x++){
			for(int y=0; y < this.medium.getHeight(); y++){
				this.medium.getCell(x, y).bleed();
			}
		}
		for(int x=0; x < this.medium.getWidth(); x++){
			for(int y=0; y < this.medium.getHeight(); y++){
				this.medium.getCell(x, y).update();
			}
		}
		
	}
	
	/*
	 * �|�n�̃V�����[�R�s�[��ԋp���܂�
	 */
	public Medium getMedium(){
		return this.medium;
	}
	
	/*
	 * �|�n�ɂ��邷�ׂẴZ�����E��
	 */
	public void refresh(){
		this.medium.killAll();
	}
}
