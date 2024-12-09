
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class GameWin extends JFrame{
	public static int state=0;//记录状态
	private int WIDTH=600;//款度
	private int HEIGHT=720;//高度
	private int count=1;
	public static int score=0;
	Image screen=null;
	BgObj bgobj=new BgObj(GameImage.bgplay,0,-720,2);
	Plane plane=new Plane(GameImage.Plane,280,500,0,120,75,this);
//	public boss boss0=new boss(GameImage.boss,250,10,5,113,125,this);
	public boss boss0=null;
	//Shell shell=new Shell(GameImage.shell2,plane.getX()+20,plane.getY(),14,29,5,this);
	public void launch() {
			repaint();
			this.setSize(WIDTH,HEIGHT);
			this.setTitle("飞机大战");
			this.setLocationRelativeTo(null);
//			this.setResizable(false);
			
			GameImage.gameobj.add(bgobj);
			GameImage.gameobj.add(plane);
//			repaint();
//			GameImage.gameobj.add(boss0);
			this.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if(e.getButton()==1&&state==0) {
						state=1;
						repaint();
					}
				}
				
			});
			
			this.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
//					super.keyPressed(e);
					if(e.getKeyCode()==32) {
						if(state==1) {
							state=2;
							
						}else if(state==2) {
							state=1;
						}
					}
				}
				
			});
			this.setVisible(true);
			while(true) {
				if(state==1) {
					createObj();
					repaint();
				}
				
				try {
					Thread.sleep(25);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}//图形窗口
	public void paint(Graphics g) {
		if(screen==null) {
			screen=createImage(WIDTH,HEIGHT);
		} 
		Graphics gImage=screen.getGraphics();
		gImage.fillRect(0, 0, WIDTH, HEIGHT);
		if(state==0) {
//			gImage.drawImage(GameImage.bgImg, 0,0,null);
//			gImage.drawImage(GameImage.bgImg1,20,200,null);
//			gImage.drawImage(GameImage.boss, 380,200,null);
//			gImage.drawImage(GameImage.Plane, 390,500,null);
//			gImage.drawImage(GameImage.title, 80,90,null);
//			gImage.drawImage(GameImage.qd, 260,600,null);
			gImage.drawImage(GameImage.MainImg1,0,0,null);
		}
		if(state==1) {
			GameImage.gameobj.addAll(GameImage.exploads);
//			System.out.println("游戏开始");
//			g.drawImage(GameImage.bgplay,0,0,null);
			for(int i=0;i<GameImage.gameobj.size();i++) {
				GameImage.gameobj.get(i).paintSelf(gImage);
			}
			//shell.paintSelf(gImage);
			GameImage.gameobj.removeAll(GameImage.removes);
//			GameImage.drawWord(gImage, "得分:"+score, Color.RED, 40, 0, 10);
//			g.setColor(Color.BLUE);
//			g.setFont(new Font("楷体",Font.BOLD,30));
//			g.drawString("点击确定开始游戏",150,300);
			gImage.setColor(Color.GREEN);
			gImage.setFont(new Font("仿宋",Font.BOLD,15));
			String ss="得分:"+score;
			gImage.drawString(ss,10,100);
		}
		if(state==3) {
//			gImage.drawImage(GameImage.bgplay, 0,0,null);
			gImage.drawImage(GameImage.fail,0,0,null);
//			gImage.drawImage(GameImage.Plane,270,600,null);
//			gImage.drawImage(GameImage.bz3,300,250,null);
//			gImage.drawImage(GameImage.bz1,300,250,null);
//			GameImage.drawWord(gImage, "得分:"+score, Color.RED, 40, 0, 10);
			this.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if(e.getButton()==1&&state==3) {
						
					}
				}
				
			});
		}
//		GameImage.drawWord(g, "得分:"+score,Color.RED,40, 0, 10);
		if(state==4) {
			gImage.drawImage(GameImage.win, 0,0,null);
//			gImage.drawImage(GameImage.fail,100,60,null);
//			gImage.drawImage(GameImage.Plane,270,600,null);
//			gImage.drawImage(GameImage.bz3,300,250,null);
			this.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if(e.getButton()==1&&state==4) {
						
					}
				}
				
			});
		}
		g.drawImage(screen, 0, 0,null);
		System.out.println(GameImage.gameobj.size());
		count++;
	}
	void createObj() {
		if(count%20==0){
			GameImage.shells.add(new Shell(GameImage.shell1,plane.getX()+0,plane.getY(),5,85,85,this));
			GameImage.gameobj.add(GameImage.shells.get(GameImage.shells.size()-1));
			
		}
		if(count%50==0) {
			GameImage.enemys.add((new EnemyObj(GameImage.enemy,(int)(Math.random()*500-76),0,5,76,68,this)));
			GameImage.gameobj.add(GameImage.enemys.get(GameImage.enemys.size()-1));
		}
		if(score>=20 && boss0==null) {
			boss0=new boss(GameImage.boss,250,10,5,113,125,this);
			GameImage.gameobj.add(boss0);
		}
		if(count%50==0 && boss0!=null) {
			GameImage.bullets.add((new BulletObj(GameImage.bullet,boss0.getX()+45,boss0.getY()+80,3,28,55,this)));
			GameImage.gameobj.add(GameImage.bullets.get(GameImage.bullets.size()-1));
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameWin game=new GameWin();
		game.launch();
//		System.out.println(GameImage.gameobj.size());
	}

}
class GameImage {
//	public static Image bgImg=Toolkit.getDefaultToolkit().getImage("./img/bg.jpg");
//	public static Image bgImg1=Toolkit.getDefaultToolkit().getImage("./img/girl1.png");
	public static Image MainImg1=Toolkit.getDefaultToolkit().getImage("./img/main1.png");
	public static Image MainImg2=Toolkit.getDefaultToolkit().getImage("./img/main2.png");
	public static Image bgplay=Toolkit.getDefaultToolkit().getImage("./img/bg2.png");
	public static Image title=Toolkit.getDefaultToolkit().getImage("./img/tit.png");
	public static Image boss=Toolkit.getDefaultToolkit().getImage("./img/plane41.png");
	public static Image enemy=Toolkit.getDefaultToolkit().getImage("./img/plane31.png");
	public static Image bullet=Toolkit.getDefaultToolkit().getImage("./img/bullet1.png");
	public static Image Plane=Toolkit.getDefaultToolkit().getImage("./img/plane11.png");
	public static Image shell1=Toolkit.getDefaultToolkit().getImage("./img/bu11.png");
	public static Image shell2=Toolkit.getDefaultToolkit().getImage("./img/bu21.png");
	public static Image shell3=Toolkit.getDefaultToolkit().getImage("./img/bu4.png");
	public static Image bz0=Toolkit.getDefaultToolkit().getImage("./img/0.png");
	public static Image bz1=Toolkit.getDefaultToolkit().getImage("./img/1.png");
	public static Image bz2=Toolkit.getDefaultToolkit().getImage("./img/2.png");
	public static Image bz3=Toolkit.getDefaultToolkit().getImage("./img/3.png");
	public static Image fail=Toolkit.getDefaultToolkit().getImage("./img/fail.png");
	public static Image win=Toolkit.getDefaultToolkit().getImage("./img/win.png");
	public static Image qd=Toolkit.getDefaultToolkit().getImage("./img/qd.png");
	public static List<GameObj> gameobj=new ArrayList<GameObj>();
	public static List<Shell> shells=new ArrayList<Shell>();
	public static List<EnemyObj> enemys=new ArrayList<EnemyObj>();
	public static List<GameObj> removes=new ArrayList<GameObj>();
	public static List<BulletObj> bullets=new ArrayList<BulletObj>();
	public static List<Expload> exploads=new ArrayList<Expload>();
	public static void drawWord(Graphics gImage,String str,Color color,int size,int x,int y) {
		gImage.setColor(color);
		gImage.setFont(new Font("仿宋",Font.BOLD,size));
		gImage.drawString(str,x,y);
	}
}//加载图片
class GameObj{
	Image img;
	int x;
	int y;
	double speed;
	int WIDTH;
	int HEIGHT;
	GameWin frame;
	
	public GameObj() {
		super();
	}//无参构造
	
	public GameObj(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public GameObj(Image img,int x,int y,double speed) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	public GameObj(Image img, int x, int y, double speed, int wIDTH, int hEIGHT, GameWin frame) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
		WIDTH = wIDTH;
		HEIGHT = hEIGHT;
		this.frame = frame;
	}//有参构造
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public int getWIDTH() {
		return WIDTH;
	}
	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}
	public int getHEIGHT() {
		return HEIGHT;
	}
	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}
	public GameWin getFrame() {
		return frame;
	}
	public void setFrame(GameWin frame) {
		this.frame = frame;
	}
	public void paintSelf(Graphics gImage) {
		gImage.drawImage(img,x,y,null);
	}
	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
}
class BgObj extends GameObj{

	public BgObj() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BgObj(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
		// TODO Auto-generated constructor stub
	}
	public BgObj(Image img, int x, int y, double speed, int wIDTH, int hEIGHT, GameWin frame) {
		super(img, x, y, speed, wIDTH, hEIGHT, frame);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintSelf(Graphics gImage) {
		// TODO Auto-generated method stub
		super.paintSelf(gImage);
		y+=speed;
		if(y>=0) {
			y=-720;
		}
	}
	
}
class Plane extends GameObj{

	public Plane() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Plane(Image img, int x, int y, double speed, int wIDTH, int hEIGHT, GameWin frame) {
		super(img, x, y, speed, wIDTH, hEIGHT, frame);
		// TODO Auto-generated constructor stub
		this.frame.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Plane.super.x=e.getX()-40;
				Plane.super.y=e.getY()-50;
			}
			
		});
	}

	public Plane(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintSelf(Graphics gImage) {
		// TODO Auto-generated method stub
		super.paintSelf(gImage);
		if(this.frame.boss0!=null&&this.getRect().intersects(this.frame.boss0.getRect())) {
			GameWin.state=3;
		}
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return super.getRect();
	}
	
}
class Shell extends GameObj{

	public Shell() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Shell(Image img, int x, int y, double speed, int wIDTH, int hEIGHT, GameWin frame) {
		super(img, x, y, speed, wIDTH, hEIGHT, frame);
		// TODO Auto-generated constructor stub
	}

	public Shell(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Image getImg() {
		// TODO Auto-generated method stub
		return super.getImg();
	}

	@Override
	public void paintSelf(Graphics gImage) {
		// TODO Auto-generated method stub
		super.paintSelf(gImage);
		y-=speed;
		if(y<0) {
			setY(-200);
			setX(200);
			GameImage.removes.add(this);
		}
//		for(EnemyObj enemy:GameImage.enemys) {
//			if(this.getRect().intersects(enemy.getRect())) {
////				System.out.println("碰撞");
//				enemy.setX(-200);
//				enemy.setY(200);
//				this.x=-200;
//				this.y=200;
//				GameImage.removes.add(enemy);
//				GameImage.removes.add(this);
////				System.out.println("碰撞"+i);
////				i++;
//			}
//		}
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return super.getRect();
	}
	
}
class EnemyObj extends GameObj{
	private static int i=0;
	public EnemyObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnemyObj(Image img, int x, int y, double speed, int wIDTH, int hEIGHT, GameWin frame) {
		super(img, x, y, speed, wIDTH, hEIGHT, frame);
		// TODO Auto-generated constructor stub
	}

	public EnemyObj(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Image getImg() {
		// TODO Auto-generated method stub
		return super.getImg();
	}

	@Override
	public void paintSelf(Graphics gImage) {
		// TODO Auto-generated method stub
		super.paintSelf(gImage);
		y+=speed;
		if(this.getRect().intersects(this.frame.plane.getRect()) ) {
			GameWin.state=3;
		}
		if(this.y>720) {
			setX(-200);
			setY(-200);
			GameImage.removes.add(this);
		}
		for(Shell shell:GameImage.shells) {
			if(this.getRect().intersects(shell.getRect())) {
//				System.out.println("碰撞");
				Expload expload=new Expload(x,y);
				GameImage.exploads.add(expload);
				GameImage.removes.add(expload);
				shell.setX(-200);
				shell.setY(200);
				this.x=-400;
				this.y=-400;
				GameImage.removes.add(shell);
				GameImage.removes.add(this);
				GameWin.score++;
			}
		}
		
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return super.getRect();
	}
	
}
class boss extends GameObj{
	private int val=100;
	public boss() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boss(Image img, int x, int y, double speed, int wIDTH, int hEIGHT, GameWin frame) {
		super(img, x, y, speed, wIDTH, hEIGHT, frame);
		// TODO Auto-generated constructor stub
	}

	public boss(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Image getImg() {
		// TODO Auto-generated method stub
		return super.getImg();
	}

	@Override
	public void paintSelf(Graphics gImage) {
		// TODO Auto-generated method stub
		super.paintSelf(gImage);
		if(x>500||x<10) {
			speed=-speed;
		}
		x+=speed;
		for(Shell shell:GameImage.shells) {
			if(this.getRect().intersects(shell.getRect())) {
				shell.setX(-200);
				shell.setY(200);
				GameImage.removes.add(shell);
				val-=5;
			}
			if(val<=0) {
				GameWin.state=4;
			}
			
		}
		gImage.setColor(Color.white);
		gImage.fillRect(20,40,100,10);
		gImage.setColor(Color.RED);
		gImage.fillRect(20, 40, val*100/10, 8);
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return super.getRect();
	}
	
}
class BulletObj extends GameObj{

	public BulletObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BulletObj(Image img, int x, int y, double speed, int wIDTH, int hEIGHT, GameWin frame) {
		super(img, x, y, speed, wIDTH, hEIGHT, frame);
		// TODO Auto-generated constructor stub
	}

	public BulletObj(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintSelf(Graphics gImage) {
		// TODO Auto-generated method stub
		super.paintSelf(gImage);
		y+=speed;
		if(this.getRect().intersects(this.frame.plane.getRect())) {
			GameWin.state=3;
		}
		if(this.y>720) {
			GameImage.removes.add(this);
		}
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return super.getRect();
	}
	
}
class Expload extends GameObj{
	static Image[] pic=new Image[4];
	static {
		pic[0]=GameImage.bz0;
		pic[1]=GameImage.bz1;
		pic[2]=GameImage.bz2;
		pic[3]=GameImage.bz3;
	}
	int expload=0;
	public Expload() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Expload(int x, int y) {
		super(x, y);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintSelf(Graphics gImage) {
		// TODO Auto-generated method stub
		
		if(expload<4) {
			img=pic[expload];
			super.paintSelf(gImage);
			expload++;
		}
	}
	
}

