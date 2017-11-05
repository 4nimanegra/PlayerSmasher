import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class BallOfDeath extends WorldElement{

	double step;
	int chainradio;
	double angle;
	
	int ballx;
	int bally;
	
	// Class constants.
	final static int BALLLWIDHT=20;
	final static int BALLHEIGHT=20;
	
	final static int CENTERWIDHT=5;
	final static int CENTERHEIGHT=5;
	
	final static int MINIMUNRADIO=BallOfDeath.BALLLWIDHT+20;
	
	public BallOfDeath(int x, int y) {
		
		super(x, y);
		
		this.chainradio = BallOfDeath.MINIMUNRADIO+((int)(Math.random()*(((PlayerSmasher.SCREEN_HEIGHT/2)-BallOfDeath.MINIMUNRADIO))));
		
		this.y = y-(((int)(Math.random()*y))+(this.chainradio));
		this.x = x-this.chainradio;
		
		this.width = this.chainradio*2;
		this.height = this.chainradio*2;
		
		this.type = WorldElement.BALLOFDEATH;
		
		this.angle = 0;
		this.step = (Math.random()*0.05)+0.10;

	}
	
	public void paint(Graphics layer,int x,int y){
		
		boolean todraw;
		
		todraw = false;
		
		int localx,localy;
			
		if(Math.abs(x-this.x) < 180){
				
			todraw = true;
			
		}
		
		if(Math.abs(x-(this.x+this.width)) < 180){
			
			todraw = true;
			
		}

		if(todraw == true){
			
			localx=this.x+(this.width/2);
			localy=this.y+(this.height/2);
			
			layer.setColor(PlayerSmasher.DARK_COLOR);
			
			layer.fillRect((80-x)+localx, localy, BallOfDeath.CENTERWIDHT, BallOfDeath.CENTERHEIGHT);
			
			layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
			
			layer.drawRect((80-x)+localx, localy, BallOfDeath.CENTERWIDHT, BallOfDeath.CENTERHEIGHT);
			
			this.angle=this.angle+this.step;
			
			this.drawBall(layer, ((int)(Math.sin(this.angle)*(this.chainradio-(BallOfDeath.BALLHEIGHT/2))))+localx, 
					((int)(Math.cos(this.angle)*(this.chainradio-(BallOfDeath.BALLHEIGHT/2))))+localy,(80-x));
			
		}
		
	}

	void drawBall(Graphics layer, int x, int y,int offset){
		
		this.ballx = (x-BallOfDeath.BALLHEIGHT/2);
		this.bally = (y-BallOfDeath.BALLHEIGHT/2);
		
		layer.setColor(PlayerSmasher.DARK_COLOR);
		
		layer.fillOval(offset+this.ballx,this.bally, BallOfDeath.BALLLWIDHT, BallOfDeath.BALLHEIGHT);
		
		layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
		
		layer.drawOval(offset+this.ballx,this.bally, BallOfDeath.BALLLWIDHT, BallOfDeath.BALLHEIGHT);
	
	}
	
	public boolean detectCollision(Rectangle hero){
		
		Rectangle ball;
				
		ball = new Rectangle(this.ballx,this.bally, BallOfDeath.BALLLWIDHT, BallOfDeath.BALLHEIGHT);
		
		if(ball.intersects(hero)){
		
			return true;
			
		}else{
		
			return false;
			
		}
		
	}

}
