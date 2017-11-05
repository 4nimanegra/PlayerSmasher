import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Celinda extends Players{
	
	final static int MAX_VELOCIDAD = 4;
	final static int ADERENCIA = 2;
	final static int ACELERACION = Celinda.ADERENCIA+1;
	
	final static int GRAVITY = 1;
	final static int POW_JUMP = Celinda.GRAVITY + 7;
	
	final static int HEIGHT = 12;
	final static int WIDHT = 6;

	// Definition for status of player
	final static int STATUS_FIX = 0;
	final static int STATUS_ONGAME = 1;
	
	// Definition of different on game status
	final static int ONGAME_STOP = 0;
	final static int ONGAME_RUNING = 1;
	final static int ONGAME_JUMPING = 2;
	final static int ONGAME_APARITION = 3;
	final static int ONGAME_DIED = 4;
	
	final static int ANIMATION_APARITION = 10;
	final static int ANIMATION_DEAD = 10;
	
	// Some player defines for player movement.
	public static final double JUMP_POWER = 20;
	private static final int MAX_VELOCIDADY = 10;

	Celinda(int x,int y){
		
		super(x,y);
		
		// For collision detection
		this.height = Celinda.HEIGHT;
		this.width = Celinda.WIDHT;
		
		this.name = "Celinda";
		
		this.aderencia = Celinda.ADERENCIA;
		this.max_velocidad = Celinda.MAX_VELOCIDAD;
		this.aceleracion = Celinda.ACELERACION;
		this.pow_jump = Celinda.POW_JUMP;
	}
	
	public void paint(Graphics layer){
		
		int i;
		int aux;
		
		switch(this.status){
			
			case Celinda.ONGAME_APARITION:
				
				// A simple apparition from 10 points go to center
				// where character will appear
				for(i=0;i<10;i++){
					
					aux=this.x-80+i*(160/10);
					
					aux=(Math.abs((aux-this.x))/Celinda.ANIMATION_APARITION);
					
					aux=this.frame*aux;
					
					if(i*(160/10) > this.x){
						
						aux = - aux;
						
					}
					
					layer.setColor(PlayerSmasher.DARK_COLOR);
					
					
					layer.fillRect((80-this.camerax)+(i*(160/10)+aux), this.frame*(this.y/Celinda.ANIMATION_APARITION), 2, 2);
					
				}
				
				if(this.frame == Celinda.ANIMATION_APARITION){
					
					this.status = Celinda.ONGAME_STOP;
					
					this.frame = 0;
					
				}
			
				break;
			
			case Celinda.ONGAME_DIED:
				
				// A simple disapparition from 10 points go to center
				// where character will appear
				for(i=0;i<10;i++){
					
					aux=this.x-80+i*(160/10);
					
					aux=(Math.abs((aux-this.x))/Celinda.ANIMATION_DEAD);
					
					aux=(Celinda.ANIMATION_DEAD-this.frame)*aux;
					
					if(i*(160/10) > this.x){
						
						aux = - aux;
						
					}
					
					layer.setColor(PlayerSmasher.DARK_COLOR);
					
					layer.fillRect((80-this.camerax)+i*(160/10)+aux, (Celinda.ANIMATION_DEAD-this.frame)*(this.y/Celinda.ANIMATION_APARITION), 2, 2);
					
				}
				
				break;
				
			case Celinda.ONGAME_JUMPING:
				
				break;
				
			case Celinda.ONGAME_RUNING:
				
				// the player is one big rectangle with two eyes
				// and two little feet.
				
				// lets begin with the corpse
				if(((int)(this.frame/4))%2 == 0){
					
					// The ribbon
					layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-8, 1, 1);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-7, 2, 1);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-6, 3, 1);
					layer.fillRect((80-this.camerax)+this.x+2, this.y-8, 1, 1);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-7, 2, 1);
					layer.fillRect((80-this.camerax)+this.x, this.y-6, 3, 1);
					
					// lets begin with the corpse
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-5, 6, 5);
					layer.fillRect((80-this.camerax)+this.x-2, this.y, 4, 5);
					
					// the the eyes.
					layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
					layer.fillRect((80-this.camerax)+this.x-2, this.y-2, 1, 1);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-2, 1, 1);
					
					// the mouth
					layer.fillRect((80-this.camerax)+this.x-1, this.y, 2, 1);
					
					// the two feet
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-4, this.y+5, 3, 2);
					layer.fillRect((80-this.camerax)+this.x+1, this.y+5, 3, 2);
					
				} else {
					
					layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-8, 1, 1);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-7, 2, 1);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-6, 3, 1);
					layer.fillRect((80-this.camerax)+this.x+2, this.y-8, 1, 1);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-7, 2, 1);
					layer.fillRect((80-this.camerax)+this.x, this.y-6, 3, 1);
					
					// lets begin with the corpse
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-5, 6, 5);
					layer.fillRect((80-this.camerax)+this.x-2, this.y, 4, 5);
					
					// the the eyes.
					layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
					layer.fillRect((80-this.camerax)+this.x-2, this.y-2, 1, 1);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-2, 1, 1);
					
					// the mouth
					layer.fillRect((80-this.camerax)+this.x-1, this.y, 2, 1);
					
					// the two feet
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y+5, 2, 2);
					layer.fillRect((80-this.camerax)+this.x+1, this.y+5, 2, 2);
					
				}
				
				
				
				break;
				
			case Celinda.ONGAME_STOP:
				
				// the player is one big rectangle with two eyes
				// and two little feet.
				
				// lets begin with the corpse
				if(((int)(this.frame/4))%2 == 0){
					
					layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-8, 1, 1);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-7, 2, 1);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-6, 3, 1);
					layer.fillRect((80-this.camerax)+this.x+2, this.y-8, 1, 1);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-7, 2, 1);
					layer.fillRect((80-this.camerax)+this.x, this.y-6, 3, 1);
					
					// lets begin with the corpse
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-5, 6, 5);
					layer.fillRect((80-this.camerax)+this.x-2, this.y, 4, 5);
					
					// the the eyes.
					layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
					layer.fillRect((80-this.camerax)+this.x-2, this.y-2, 1, 1);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-2, 1, 1);
					
					// the mouth
					layer.fillRect((80-this.camerax)+this.x-1, this.y, 2, 1);
					
				} else {
					
					layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-9, 1, 1);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-8, 2, 1);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-7, 3, 1);
					layer.fillRect((80-this.camerax)+this.x+2, this.y-9, 1, 1);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-8, 2, 1);
					layer.fillRect((80-this.camerax)+this.x, this.y-7, 3, 1);
					
					// lets begin with the corpse
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-6, 6, 5);
					layer.fillRect((80-this.camerax)+this.x-2, this.y-1, 4, 6);
					
					// the the eyes.
					layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
					layer.fillRect((80-this.camerax)+this.x-2, this.y-3, 1, 1);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-3, 1, 1);
					
					// the mouth
					layer.fillRect((80-this.camerax)+this.x-1, this.y-1, 2, 1);
					
				}
				
				// the two feet
				layer.setColor(PlayerSmasher.DARK_COLOR);
				layer.fillRect((80-this.camerax)+this.x-4, this.y+5, 3, 2);
				layer.fillRect((80-this.camerax)+this.x+1, this.y+5, 3, 2);
				
				break;
				
			default:
				
				System.err.println("Error, a unhandled player status detected.");
				
				break;
			
		}
		
		// Status fix is for player selection screen
		if(this.ongamestatus == Celinda.STATUS_FIX){
			
			layer.setColor(PlayerSmasher.DARK_COLOR);
			layer.setFont(PlayerSmasher.NORMAL_FONT);
			layer.drawString(this.name, this.x - 20, this.y - 20);
			
		}
		
		// Advance one frame
		this.frame = this.frame + 1;
		
	}
	
}


