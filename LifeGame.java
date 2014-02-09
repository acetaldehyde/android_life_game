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
	// サーフェイスの幅・高さを更新
    private int surfaceWidth;
    private int surfaceHeight;
	
	//セル数とセルサイズ
	private int cellWidth;
	private int cellHeight;
	private float cellSize;
	
	//Schale
	private Schale schale;
	
	//updateGene
	//世代更新可否　true,false = ON,OFF
	private boolean update;
	
	//タッチ座標
	private ArrayList<Point> points = new ArrayList<Point>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //タイトルバーを消す
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //GLSurfaceViewを生成
        gLSurfaceView = new GLSurfaceView(this);
        
        this.cellWidth = 70;
        this.cellHeight = 95;
        this.cellSize = 4;
        
        //シャーレを生成
        this.schale = new Schale(this.cellWidth, this.cellHeight);
        
        //テスト用培地初期化
        Random r = new Random();
        for(int x=0; x < this.schale.getMedium().getWidth(); x++){
        	for(int y=0; y < this.schale.getMedium().getHeight(); y++){
        		this.schale.getMedium().setState(x, y, r.nextBoolean());
        	}
		}
        
        //updateフラグ
        this.update = false;
        
        //レンダラーを生成してセット
        gLSurfaceView.setRenderer(this);
        
        //レイアウトのリソース参照は渡さず、直接Viewオブジェクトを渡す
        //setContentView(R.layout.main);
        setContentView(gLSurfaceView);
    }
    
    //描画のために毎フレーム呼び出される
    public void onDrawFrame(GL10 gl){
    	
    	//描画用バッファをクリア
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	
    	//カメラ位置をセット
    	GLU.gluLookAt(gl, 0.0f, 0.0f, 200.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    	
    	//シャーレの世代更新
    	if(this.update)
    		this.schale.shiftTheGeneration();
    	
    	//全セルを描画
    	for(float x=0; x < this.schale.getMedium().getWidth(); x++){
			for(float y=0; y < this.schale.getMedium().getHeight(); y++){
				//ポリゴンの描画メソッドを呼ぶ
				int red = 0, green = 0, blue = 0;
				if(this.schale.getMedium().getState((int)x, (int)y)){
					red = this.schale.getMedium().getCell((int)x, (int)y).red();
					green = this.schale.getMedium().getCell((int)x, (int)y).green();
					blue = this.schale.getMedium().getCell((int)x, (int)y).blue();
					PolygonDrawer.drawBoard(gl, x*this.cellSize-140.0f, y*this.cellSize-180.0f, 4, 4, red, green, blue, 255);
				}
			}
		}
    	
    	//タッチ座標に誕生
    	for(int i=0; i<this.points.size(); i++){
    		Point po = this.points.remove(this.points.size() - 1);
    		int x = (int)((po.x + 140) / this.cellSize);
    		int y = (int)((po.y + 180) / this.cellSize);
    		this.schale.getMedium().setCell(new LongevityCell(this.schale.getMedium(), x, y), x, y);
    	}
    	
    }
    
    //サーフェイスのサイズ変更時に呼び出される
    public void onSurfaceChanged(GL10 gl, int width, int height){
    	// サーフェイスの幅・高さを更新
        this.surfaceWidth = width;
        this.surfaceHeight = height;
        
    	//ビューポートをサイズに合わせてセットしなおす
    	gl.glViewport(0, 0, width, height);
    	
    	//アスペクトレート
    	float ratio = (float)width / height;
    	
    	//射影行列を選択
    	gl.glMatrixMode(GL10.GL_PROJECTION);
    	
    	//現在選択されている行列（射影行列）に、単位行列をセット
    	gl.glLoadIdentity();
    	
    	//透視投影用の雛台のパラメータをセット
    	gl.glFrustumf(-ratio, ratio, -1.0f, 1.0f, 1.0f, 1000.0f);
    	
    }
    
    //サーフェイスが生成される際または再生成される際に呼び出される
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
    	
    	//ディザを無効化
    	gl.glDisable(GL10.GL_DITHER);
    	//カラーとテクスチャ座標の補間精度を、最も効率的なものに指定。
    	gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
    	//バッファ初期化時のカラー情報をセット
    	gl.glClearColor(0, 0, 0, 1);
    	//片面表示を有効に
    	gl.glEnable(GL10.GL_CULL_FACE);
    	//カリング設定をCCWに
    	gl.glFrontFace(GL10.GL_CCW);
    	//深度テストを有効に
    	gl.glEnable(GL10.GL_DEPTH_TEST);
    	//スムースシェーディングにセット
    	gl.glShadeModel(GL10.GL_SMOOTH);
    }
    
    //メニュー生成
    public boolean onCreateOptionsMenu(Menu menu){  
    	super.onCreateOptionsMenu(menu);  
    	getMenuInflater().inflate(R.menu.menu,menu);
    	return true;  
    }  
    
    //メニュー項目選択時
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
    
    //タッチイベント
    public boolean onTouchEvent(MotionEvent event){
    	//タッチ座標をサーフェイスの座標に合わせる
    	float x = event.getX() - this.surfaceWidth / 2;
    	float y = (event.getY()  * -1) + this.surfaceHeight / 2 + 25;
    	
    	this.points.add(new Point((int)x, (int)y));
    	
    	return super.onTouchEvent(event);
    }
    
    public boolean onOptionMenuClosed(){
    	this.update = true;
    	return true;
    }
    
    //ポーズからの復帰およびアクティビティ生成時に呼び出される
    protected void onResume(){
    	super.onResume();
    	gLSurfaceView.onResume();
    }
    
    //アクティビティ一時停止時や終了時に呼び出される
    protected void onPause(){
    	super.onPause();
    	gLSurfaceView.onPause();
    }
}