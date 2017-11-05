import java.awt.Graphics;


public class WorldElement {
	
	// Dimensions of the element
	int x;
	int y;
	
	int width;
	int height;
	
	// Constants for world element type
	public final static int PLATFORM = 0;
	public static final int PLATFORMDESTROY = 1;
	public static final int LASERBOLT = 2;
	public static final int LASERGROUND = 3;
	public static final int NONINTERACTIVE = 4;
	public static final int BALLOFDEATH = 5;
	
	private static final int DIFICULTYMULTIPLY = 2;
	
	
	
	// set the type as platform.
	int type = WorldElement.PLATFORM;
	
	public void setInitialXposition(int multiply,int x){
		
		this.x = x+(int)((Players.MAX_VELOCIDAD*(Players.JUMP_POWER/(Players.GRAVITY*multiply)))*Math.random());
		
	}
	
	// The basic world element will be a
	// basic platform.
	public WorldElement(int x,int y){
		
		this.height = 10+(int)(30*Math.random());
		this.width = 20+(int)(50*Math.random());
		
		setInitialXposition(WorldElement.DIFICULTYMULTIPLY,x);
		
		if(Math.random() > 0.5){
		
			this.y = 120;
			
			while(this.y>=120){
			
				this.y = y+(int)(Players.JUMP_POWER*Math.random());
				
			}
					
		} else {
			
			this.y = 20;
			
			while(this.y <= 20){
				
				this.y = y-(int)(Players.JUMP_POWER*Math.random());
				
			}
		
		}
		
	}

public WorldElement(int y){
		
		this.height = 10+(int)(30*Math.random());
		this.width = 160;
		
		this.x = 0;
		
		this.y = y;
		
	}
	
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
	public void paint(Graphics layer,int x,int y){
		
		boolean todraw;
		
		todraw = false;
			
		if(Math.abs(x-this.x) < 180){
				
			todraw = true;
			
		}
		
		if(Math.abs(x-(this.x+this.width)) < 180){
			
			todraw = true;
			
		}

		if(todraw == true){
			
			layer.setColor(PlayerSmasher.DARK_COLOR);
			layer.fillRoundRect((80-x)+this.x, this.y, this.width, this.height, 10, 10);
			
			layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
			layer.drawRoundRect((80-x)+this.x, this.y, this.width, this.height, 10, 10);
			
		}
	}

}
