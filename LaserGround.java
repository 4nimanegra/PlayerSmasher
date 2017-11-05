import java.awt.Color;
import java.awt.Graphics;

public class LaserGround extends LaserBolt{
	
	public static final int MINIMUNSIZE = 40;
	private static final int GROUNDBOLTOFFRATIO = 2*LaserBolt.BOLTOFFRATIO;

	public LaserGround(int x, int y, int width) {
		super(x, y);
		
		this.width = width;
		this.x = x;
		this.y = y-2*LaserBolt.BOLTWIDHT-Players.HEIGHT;
		this.height = 2*LaserBolt.BOLTWIDHT;
		
		this.type = WorldElement.LASERGROUND;
		
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
			
				this.step = LaserGround.GROUNDBOLTOFFRATIO*LaserBolt.BOLTSTEPS;
			
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
			layer.fillRect((80-x)+this.x, this.y+LaserBolt.BOLTWIDHT, 4, 4);
			layer.fillRect((80-x)+this.x+ this.width-4, this.y+LaserBolt.BOLTWIDHT , 4, 4);
						
			layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
			layer.drawRect((80-x)+this.x, this.y+LaserBolt.BOLTWIDHT, 4, 4);
			layer.drawRect((80-x)+this.x+ this.width-4, this.y+LaserBolt.BOLTWIDHT , 4, 4);
			
		}
		
	}

	void drawBolt(Color color, Graphics layer, int x, int y){
	
		int segment,actual,prev;
		int i;
		
		// Bolt Ligth
		layer.setColor(color);
		
		segment=(this.width-4)/LaserBolt.BOLTSTEPS;
		
		prev=this.y+LaserBolt.BOLTWIDHT;
		
		for(i=0;i<LaserBolt.BOLTSTEPS-1;i++){
			
			actual=this.y+((int)(Math.random()*2*LaserBolt.BOLTWIDHT));
			
			layer.drawLine((80-x)+this.x+4+i*segment,prev, (80-x)+this.x+4+(i+1)*segment, actual);
			
			prev=actual;
			
		}
		
		actual=this.y+LaserBolt.BOLTWIDHT;
		
		layer.drawLine((80-x)+this.x+4+i*segment,prev, (80-x)+this.x+this.width-4, actual);
	
	}
	
}
