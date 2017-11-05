import java.awt.Graphics;

public class ArriveMark extends WorldElement{

	public ArriveMark(int x, int y) {
		super(x, y);
		
		this.x=x;
		this.y=y;
		
		this.type = WorldElement.NONINTERACTIVE;
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
			layer.fillRect((80-x)+this.x, this.y-10, 1, 10);
			
			layer.setColor(PlayerSmasher.DARK_COLOR);
			layer.fillRect((80-x)+this.x, this.y-10, 5, 5);
			
			layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
			layer.drawRect((80-x)+this.x, this.y-10, 5, 5);
			
		}
	}
	
	
}
