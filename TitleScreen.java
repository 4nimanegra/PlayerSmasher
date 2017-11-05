import java.awt.Font;
import java.awt.Graphics;


public class TitleScreen extends Screen {
	
	public TitleScreen() {
		
		super(PlayerSmasher.MENU_SCREEN);
		
		this.finished = false;
		
	}
	
	public void paint(Graphics layer){
		
		int i;
		double myRand;
		
		i=0;
		
		while(i<20){
		
			myRand = Math.random();
			
			if(myRand < 0.25){
			
				layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
				
			} else if(myRand > 0.25 && myRand < 0.5){
				
				layer.setColor(PlayerSmasher.DARK_COLOR);
				
			} else if(myRand > 0.5 && myRand < 0.75){
				
				layer.setColor(PlayerSmasher.LIGTH_COLOR);
				
			} else {
				
				layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
				
			}
		
			layer.fillRect(0+i, 0+i, 160-2*i, 144-2*i);
			
			i=i+1;
			
		}
		
		layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
		layer.fillRect(0+i, 0+i, 160-2*i, 144-2*i);
		
		layer.setColor(PlayerSmasher.DARK_COLOR);
		
		layer.setFont(PlayerSmasher.TITLE_FONT);
		
		layer.drawString("Player", 50, 50);
		
		layer.drawString("Smasher!!!", 30, 70);
		
		layer.setFont(PlayerSmasher.NORMAL_FONT);
		
		layer.drawString("Press start button", 40, 105);
		
	}

	public void keyPressed(int key){
		
		this.finished = true;
		
	}

}
