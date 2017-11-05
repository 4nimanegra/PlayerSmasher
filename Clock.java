import java.awt.Graphics;

public class Clock {
	
	int seconds,miliseconds;
	
	public Clock(){
		
		seconds = 0;
		miliseconds = 0;
		
	}

	public void paint(Graphics layer) {
		int prev;
		prev = this.miliseconds;
		this.miliseconds = this.miliseconds-1;
		
		if(this.miliseconds < 0){
			 this.miliseconds = 999;
		}
		
		if(this.miliseconds > prev){
			 this.seconds = this.seconds -1;
		}
		 
		layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
		layer.setFont(PlayerSmasher.NORMAL_FONT);
		 
		layer.drawString(String.format("Time: %02d:%3d", this.seconds, this.miliseconds),11*(PlayerSmasher.SCREEN_WIDTH/20), PlayerSmasher.SCREEN_HEIGHT/10);
		 
	}

	public void reset(int level) {
		
		seconds = (int)(800*level/1000);
		miliseconds = 800*level-this.seconds*1000;
	}
	
	public boolean timeEnds(){
	
		return (this.seconds<=0)&&(this.miliseconds<=0);
		
	}

}
