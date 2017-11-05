import java.awt.Graphics;


public class Points {
	
	long points;

	public void paint(Graphics layer) {
		
		layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
		layer.setFont(PlayerSmasher.NORMAL_FONT);
		 
		layer.drawString(String.format("Points: %06d", this.points),(PlayerSmasher.SCREEN_WIDTH/20), PlayerSmasher.SCREEN_HEIGHT/10);
		
	}

	public void reset() {
		
		this.points = 0;
		
	}

}
