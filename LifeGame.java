package org.simulator.lifegame;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.example.polygonsample.PolygonDrawer;


public class LifeGame extends Activity implements GLSurfaceView.Renderer{
	//GLSurfaceView
	private GLSurfaceView gLSurfaceView;
	
	//surface size
	// �T�[�t�F�C�X�̕��E�������X�V
    private int surfaceWidth;
    private int surfaceHeight;
	
	//�Z�����ƃZ���T�C�Y
	private int cellWidth;
	private int cellHeight;
	private float cellSize;
	
	//Schale
	private Schale schale;
	
	//updateGene
	//����X�V�ہ@true,false = ON,OFF
	private boolean update;
	
	//�^�b�`���W
	private ArrayList<Point> points = new ArrayList<Point>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //�^�C�g���o�[������
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //GLSurfaceView�𐶐�
        gLSurfaceView = new GLSurfaceView(this);
        
        this.cellWidth = 70;
        this.cellHeight = 95;
        this.cellSize = 4;
        
        //�V���[���𐶐�
        this.schale = new Schale(this.cellWidth, this.cellHeight);
        
        //�e�X�g�p�|�n������
        Random r = new Random();
        for(int x=0; x < this.schale.getMedium().getWidth(); x++){
        	for(int y=0; y < this.schale.getMedium().getHeight(); y++){
        		this.schale.getMedium().setState(x, y, r.nextBoolean());
        	}
		}
        
        //update�t���O
        this.update = false;
        
        //�����_���[�𐶐����ăZ�b�g
        gLSurfaceView.setRenderer(this);
        
        //���C�A�E�g�̃��\�[�X�Q�Ƃ͓n�����A����View�I�u�W�F�N�g��n��
        //setContentView(R.layout.main);
        setContentView(gLSurfaceView);
    }
    
    //�`��̂��߂ɖ��t���[���Ăяo�����
    public void onDrawFrame(GL10 gl){
    	
    	//�`��p�o�b�t�@���N���A
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	
    	//�J�����ʒu���Z�b�g
    	GLU.gluLookAt(gl, 0.0f, 0.0f, 200.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    	
    	//�V���[���̐���X�V
    	if(this.update)
    		this.schale.shiftTheGeneration();
    	
    	//�S�Z����`��
    	for(float x=0; x < this.schale.getMedium().getWidth(); x++){
			for(float y=0; y < this.schale.getMedium().getHeight(); y++){
				//�|���S���̕`�惁�\�b�h���Ă�
				int red = 0, green = 0, blue = 0;
				if(this.schale.getMedium().getState((int)x, (int)y)){
					red = this.schale.getMedium().getCell((int)x, (int)y).red();
					green = this.schale.getMedium().getCell((int)x, (int)y).green();
					blue = this.schale.getMedium().getCell((int)x, (int)y).blue();
					PolygonDrawer.drawBoard(gl, x*this.cellSize-140.0f, y*this.cellSize-180.0f, 4, 4, red, green, blue, 255);
				}
			}
		}
    	
    	//�^�b�`���W�ɒa��
    	for(int i=0; i<this.points.size(); i++){
    		Point po = this.points.remove(this.points.size() - 1);
    		int x = (int)((po.x + 140) / this.cellSize);
    		int y = (int)((po.y + 180) / this.cellSize);
    		this.schale.getMedium().setCell(new LongevityCell(this.schale.getMedium(), x, y), x, y);
    	}
    	
    }
    
    //�T�[�t�F�C�X�̃T�C�Y�ύX���ɌĂяo�����
    public void onSurfaceChanged(GL10 gl, int width, int height){
    	// �T�[�t�F�C�X�̕��E�������X�V
        this.surfaceWidth = width;
        this.surfaceHeight = height;
        
    	//�r���[�|�[�g���T�C�Y�ɍ��킹�ăZ�b�g���Ȃ���
    	gl.glViewport(0, 0, width, height);
    	
    	//�A�X�y�N�g���[�g
    	float ratio = (float)width / height;
    	
    	//�ˉe�s���I��
    	gl.glMatrixMode(GL10.GL_PROJECTION);
    	
    	//���ݑI������Ă���s��i�ˉe�s��j�ɁA�P�ʍs����Z�b�g
    	gl.glLoadIdentity();
    	
    	//�������e�p�̐���̃p�����[�^���Z�b�g
    	gl.glFrustumf(-ratio, ratio, -1.0f, 1.0f, 1.0f, 1000.0f);
    	
    }
    
    //�T�[�t�F�C�X�����������ۂ܂��͍Đ��������ۂɌĂяo�����
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
    	
    	//�f�B�U�𖳌���
    	gl.glDisable(GL10.GL_DITHER);
    	//�J���[�ƃe�N�X�`�����W�̕�Ԑ��x���A�ł������I�Ȃ��̂Ɏw��B
    	gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
    	//�o�b�t�@���������̃J���[�����Z�b�g
    	gl.glClearColor(0, 0, 0, 1);
    	//�Жʕ\����L����
    	gl.glEnable(GL10.GL_CULL_FACE);
    	//�J�����O�ݒ��CCW��
    	gl.glFrontFace(GL10.GL_CCW);
    	//�[�x�e�X�g��L����
    	gl.glEnable(GL10.GL_DEPTH_TEST);
    	//�X���[�X�V�F�[�f�B���O�ɃZ�b�g
    	gl.glShadeModel(GL10.GL_SMOOTH);
    }
    
    //���j���[����
    public boolean onCreateOptionsMenu(Menu menu){  
    	super.onCreateOptionsMenu(menu);  
    	getMenuInflater().inflate(R.menu.menu,menu);
    	return true;  
    }  
    
    //���j���[���ڑI����
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case R.id.item1:
    		this.update = false;
    		break;
    	case R.id.item2:
    		this.update = true;
    		break;
    	case R.id.item3:
    		this.update = false;
    		this.schale.refresh();
    		break;
    	default:
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    //�^�b�`�C�x���g
    public boolean onTouchEvent(MotionEvent event){
    	//�^�b�`���W���T�[�t�F�C�X�̍��W�ɍ��킹��
    	float x = event.getX() - this.surfaceWidth / 2;
    	float y = (event.getY()  * -1) + this.surfaceHeight / 2 + 25;
    	
    	this.points.add(new Point((int)x, (int)y));
    	
    	return super.onTouchEvent(event);
    }
    
    public boolean onOptionMenuClosed(){
    	this.update = true;
    	return true;
    }
    
    //�|�[�Y����̕��A����уA�N�e�B�r�e�B�������ɌĂяo�����
    protected void onResume(){
    	super.onResume();
    	gLSurfaceView.onResume();
    }
    
    //�A�N�e�B�r�e�B�ꎞ��~����I�����ɌĂяo�����
    protected void onPause(){
    	super.onPause();
    	gLSurfaceView.onPause();
    }
}