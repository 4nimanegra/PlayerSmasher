import java.awt.Color;
import java.awt.Graphics;


public class LaserBolt extends WorldElement{

	// Used for identify when the bolt is active or not
	boolean active;
	int step;
	
	// Class constants.
	final static int BOLTWIDHT=5;
	final static int BOLTHEIGHT=PlayerSmasher.SCREEN_HEIGHT;
	final static int BOLTTIME=30;
	final static int BOLTSTEPS=10;
	final static int BOLTOFFRATIO=2;
	
	public LaserBolt(int x, int y) {
		
		super(x, y);
		
		this.active = false;
		
		this.width = 10;
		this.height = PlayerSmasher.SCREEN_HEIGHT;
		
		this.y=0;
		this.type = WorldElement.LASERBOLT;
		this.step = ((int)(Math.random()*(LaserBolt.BOLTOFFRATIO*LaserBolt.BOLTSTEPS+LaserBolt.BOLTSTEPS)))-LaserBolt.BOLTSTEPS;
		
	}
	
	public void paint(Graphics layer,int x,int y){
		
		boolean todraw;
		int i,segment,prev,actual;
		
		todraw = false;
			
		if(Math.abs(x-this.x) < 180){
				
			todraw = true;
			
		}
		
		if(Math.abs(x-(this.x+this.width)) < 180){
			
			todraw = true;
			
		}

		if(todraw == true){
			
			// Lets change the status in order to set if its time of
			// lasering or not.
			this.step=this.step-1;
			
			if(this.step < -LaserBolt.BOLTSTEPS){
			
				this.step = LaserBolt.BOLTOFFRATIO*LaserBolt.BOLTSTEPS;
			
			}
			
			this.active = false;
			
			// if step is more than 0 the laser is on.
			if(this.step < 0){
			
				this.active = true;
				
				drawBolt(PlayerSmasher.LIGTH_COLOR,layer, x, y);
				drawBolt(PlayerSmasher.DARK_COLOR,layer, x, y);
				drawBolt(PlayerSmasher.LIGTH_COLOR,layer, x, y);
				
			}
			
			// First paint the two lasers pistols
			layer.setColor(PlayerSmasher.DARK_COLOR);
			layer.fillRect((80-x)+this.x+LaserBolt.BOLTWIDHT-2, this.y, 5, 10);
			layer.fillRect((80-x)+this.x+LaserBolt.BOLTWIDHT-2, this.height-10, 5, 10);
			
			layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
			layer.drawRect((80-x)+this.x+LaserBolt.BOLTWIDHT-2, this.y, 5, 10);
			layer.drawRect((80-x)+this.x+LaserBolt.BOLTWIDHT-2, this.height-10, 5, 10);
			
		}
		
	}

	void drawBolt(Color color, Graphics layer, int x, int y){
	
		int segment,actual,prev;
		int i;
		
		// Bolt Ligth
		layer.setColor(color);
		
		segment=(this.height-20)/LaserBolt.BOLTSTEPS;
		
		prev=(80-x)+this.x+1+LaserBolt.BOLTWIDHT;
		
		for(i=0;i<LaserBolt.BOLTSTEPS-1;i++){
			
			actual=(80-x)+this.x+((int)(Math.random()*2*LaserBolt.BOLTWIDHT));
			
			layer.drawLine(prev, 10+i*segment, actual, 10+(i+1)*segment);
			
			prev=actual;
			
		}
		
		actual=(80-x)+this.x+1+LaserBolt.BOLTWIDHT;
		
		layer.drawLine(prev, 10+i*segment, actual, 3+10+(i+1)*segment);
	
	}

}
