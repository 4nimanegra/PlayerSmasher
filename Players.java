import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Players {
	
	// Some player defines for player movement.
	public static final double JUMP_POWER = 20;
	private static final int MAX_VELOCIDADY = 10;
	
	final static int MAX_VELOCIDAD = 5;
	final static int ADERENCIA = 1;
	final static int ACELERACION = Players.ADERENCIA + 1;
	
	final static int GRAVITY = 1;
	final static int POW_JUMP = Players.GRAVITY + 5;
	
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
	
	
	
	// camera view x and y
	int camerax;
	int cameray;
	
	// For collision detection
	int height;
	int width;
	
	// Used for player movement and key pressing.
	ArrayList<Integer> keysPressed;
	
	// Used for the status of the player
	int status;
	int ongamestatus;
	
	protected boolean onground;
	protected boolean onceil;
	protected boolean onwall;
	
	// Used for the animation of the player.
	int frame;
	
	// Used for the name of the character.
	String name;
	public int yground;
	
	// Used for screen positioning
	int x;
	int y;
	
	int initial_x;
	int initial_y;
	
	int xcorner;
	int ycorner;
	
	// movement and velocity related variables and constants
	int velocidad;
	int velocidady;
	
	// Variables for different character characteristics
	int max_velocidad;
	int aderencia;
	int aceleracion;
	
	int gravity;
	int pow_jump;

	Players(int x,int y){
	
		this.x = x;
		this.y = y;
		
		this.initial_y = y;
		this.initial_x = x;
		
		this.camerax = this.x;
		this.cameray = this.y;
		
		this.ongamestatus = Players.STATUS_FIX;
		this.status = Players.ONGAME_APARITION;
		
		this.frame = 0;
		
		this.keysPressed = new ArrayList<Integer>();
		
		this.velocidad = 0;
		this.velocidady = 0;
		
		this.onground = false;
		this.onwall = false;
		this.onceil = false;
		
		// For collision detection
		this.height = Players.HEIGHT;
		this.width = Players.WIDHT;
		
		this.name = "Natham";

		this.calculateCorners();
	
		this.max_velocidad = Players.MAX_VELOCIDAD;
		this.aderencia = Players.ADERENCIA;
		this.aceleracion = Players.ACELERACION;
		
		this.gravity = Players.GRAVITY;
		this.pow_jump = Players.POW_JUMP;
		
	}
	
	protected void calculateCorners(){
		
		this.xcorner=this.x-3;
		this.ycorner=this.y-5;
		
	}
	
	public boolean rectangularCollision(Rectangle playerrect, Rectangle werect){
		
		// Detecting the collision on the floor.
		playerrect = new Rectangle(this.xcorner,this.ycorner,this.width,this.height);
				
		return playerrect.intersects(werect);
		
	}
	
	public void normalPlatformCollision(Rectangle playerrect, Rectangle werect){
		
		// Detecting the collision on the floor.
		playerrect = new Rectangle(this.xcorner,this.ycorner+this.height-1,this.width,1);
		
		if(playerrect.intersects(werect)){
		
			this.onground = true;
			
		}
		
		// Detecting the collision on the ceil.
		playerrect = new Rectangle(this.xcorner,this.ycorner-5,this.width,1);
		
		if(playerrect.intersects(werect)){
			
			this.onceil = true;
			
		}
		
		// Detecting the collision on the left wall.
		playerrect = new Rectangle(this.xcorner,this.ycorner-5+1,1,this.height-2);
		
		if(playerrect.intersects(werect)){
			
			this.onwall = true;
			
		}
		
		// Detecting collision on the right wall.
		playerrect = new Rectangle(this.xcorner+this.width-1,this.ycorner+1,1,this.height-2);
		
		if(playerrect.intersects(werect)){
			
			this.onwall = true;
			
		}
		
	}
	
	public void collisionDetector(WorldElement we){
	
		Rectangle playerrect;
		Rectangle werect;
		
		playerrect = new Rectangle(this.xcorner,this.ycorner,this.width,this.height);
		werect = new Rectangle(we.x,we.y,we.width,we.height);
		
		if(playerrect.intersects(werect)){
		
			switch(we.type){
			
				case WorldElement.PLATFORM:
	
					normalPlatformCollision(playerrect, werect);
					
					break;
					
				case WorldElement.PLATFORMDESTROY:
					
					if(((UnestablePlatform)we).destroy[0] > 0){
						
						normalPlatformCollision(playerrect, werect);
						
					}
					
					break;
					
				case WorldElement.LASERBOLT:
				case WorldElement.LASERGROUND:
					
					if(((LaserBolt)we).active){
							
						this.ongamestatus = Players.STATUS_FIX;
						this.setDestroyed();
						
					}
					
					break;
					
				case WorldElement.BALLOFDEATH:
										
					if(((BallOfDeath)we).detectCollision(playerrect) == true){
						
						this.ongamestatus = Players.STATUS_FIX;
						this.setDestroyed();
						
					}
					
					break;
					
				default:
					break;
			
			}
			
		}		
			
	}
	
	public void paint(Graphics layer){
		
		int i;
		int aux;
		
		switch(this.status){
			
			case Players.ONGAME_APARITION:
				
				// A simple apparition from 10 points go to center
				// where character will appear
				for(i=0;i<10;i++){
					
					aux=this.x-80+i*(160/10);
					
					aux=(Math.abs((aux-this.x))/Players.ANIMATION_APARITION);
					
					aux=this.frame*aux;
					
					if(i*(160/10) > this.x){
						
						aux = - aux;
						
					}
					
					layer.setColor(PlayerSmasher.DARK_COLOR);
					
					
					layer.fillRect((80-this.camerax)+(i*(160/10)+aux), this.frame*(this.y/Players.ANIMATION_APARITION), 2, 2);
					
				}
				
				if(this.frame == Players.ANIMATION_APARITION){
					
					this.status = Players.ONGAME_STOP;
					
					this.frame = 0;
					
				}
			
				break;
			
			case Players.ONGAME_DIED:
				
				// A simple disapparition from 10 points go to center
				// where character will appear
				for(i=0;i<10;i++){
					
					aux=this.x-80+i*(160/10);
					
					aux=(Math.abs((aux-this.x))/Players.ANIMATION_DEAD);
					
					aux=(Players.ANIMATION_DEAD-this.frame)*aux;
					
					if(i*(160/10) > this.x){
						
						aux = - aux;
						
					}
					
					layer.setColor(PlayerSmasher.DARK_COLOR);
					
					layer.fillRect((80-this.camerax)+i*(160/10)+aux, (Players.ANIMATION_DEAD-this.frame)*(this.y/Players.ANIMATION_APARITION), 2, 2);
					
				}
				
				break;
				
			case Players.ONGAME_JUMPING:
				
				break;
				
			case Players.ONGAME_RUNING:
				
				// the player is one big rectangle with two eyes
				// and two little feet.
				
				// lets begin with the corpse
				if(((int)(this.frame/4))%2 == 0){
					
					// lets begin with the corpse
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-5, 6, 10);
					
					// the the eyes.
					layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
					layer.fillRect((80-this.camerax)+this.x-2, this.y-2, 1, 3);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-2, 1, 3);
					
					// the two feet
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y+5, 2, 2);
					layer.fillRect((80-this.camerax)+this.x+1, this.y+5, 2, 2);
					
				} else {
					
					// lets begin with the corpse
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-5, 6, 10);
					
					// the the eyes.
					layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
					layer.fillRect((80-this.camerax)+this.x-2, this.y-2, 1, 3);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-2, 1, 3);
					
					// the two feet
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-1, this.y+5, 2, 2);
					layer.fillRect((80-this.camerax)+this.x, this.y+5, 2, 2);
					
				}
				
				
				
				break;
				
			case Players.ONGAME_STOP:
				
				// the player is one big rectangle with two eyes
				// and two little feet.
				
				// lets begin with the corpse
				if(((int)(this.frame/4))%2 == 0){
					
					// lets begin with the corpse
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-5, 6, 10);
					
					// the the eyes.
					layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
					layer.fillRect((80-this.camerax)+this.x-2, this.y-2, 1, 3);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-2, 1, 3);
					
				} else {
					
					// lets begin with the corpse
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillRect((80-this.camerax)+this.x-3, this.y-6, 6, 11);
					
					// the the eyes.
					layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
					layer.fillRect((80-this.camerax)+this.x-2, this.y-3, 1, 3);
					layer.fillRect((80-this.camerax)+this.x+1, this.y-3, 1, 3);
					
				}
				
				// the two feet
				layer.setColor(PlayerSmasher.DARK_COLOR);
				layer.fillRect((80-this.camerax)+this.x-3, this.y+5, 2, 2);
				layer.fillRect((80-this.camerax)+this.x+1, this.y+5, 2, 2);
				
				break;
				
			default:
				
				System.err.println("Error, a unhandled player status detected.");
				
				break;
			
		}
		
		// Status fix is for player selection screen
		if(this.ongamestatus == Players.STATUS_FIX){
			
			layer.setColor(PlayerSmasher.DARK_COLOR);
			layer.setFont(PlayerSmasher.NORMAL_FONT);
			layer.drawString(this.name, this.x - 20, this.y - 20);
			
		}
		
		// Advance one frame
		this.frame = this.frame + 1;
		
	}
	
	// For player movement.
	
	// introduces new keys in the player keys.
	public void keyPressed(int keyCode){
		
		int i;
		
		for(i=0;i<this.keysPressed.size();i++){
			
			if(this.keysPressed.get(i).equals(keyCode)){
			
				break;
				
			}
			
		}
		
		if(i == this.keysPressed.size()){
			
			this.keysPressed.add(keyCode);
			
		}
		
	}
	
	// Delete a key released from the player keys.
	public void keyReleased(int keyCode){
		
		int i;
		
		for(i=0;i<this.keysPressed.size();i++){
			
			if(this.keysPressed.get(i).equals(keyCode)){
							
				this.keysPressed.remove(i);
				
				break;
				
			}
			
		}
		
	}
	
	// Set the player as destroyed.
	// Mainly for the character selection screen.
	public void setDestroyed(){
		
		this.status = Players.ONGAME_DIED;
		this.frame = 0;
		
	}
	
	public void reset(){
		
		this.ongamestatus = Players.STATUS_FIX;
		this.status = Players.ONGAME_APARITION;
		
		this.frame = 0;
		
		this.keysPressed.clear();
		
		this.y = this.initial_y;
		this.x = this.initial_x;
		
	}

	public void refresh(ArrayList <WorldElement>myworld) {
		
		int endx;
		int endy;
		
		this.camerax = this.x;
		this.cameray = this.y;
		
		this.yground = this.y+7;
		
		if(this.y > GameScreen.MAX_Y_TO_DEATH){
			
			if(this.status != Players.ONGAME_DIED){
			
				this.ongamestatus = Players.STATUS_FIX;
				this.setDestroyed();
				
			}
			
		}
		
		if(this.ongamestatus == Players.STATUS_FIX){
			
			if(!this.keysPressed.isEmpty()){
				
				if(this.status != Players.ONGAME_DIED){
				
					setDestroyed();
					
				}
				
			}
			
		} else {
			
			int i;
			
			for(i=0;i<this.keysPressed.size();i++){
				
				if(this.keysPressed.get(i).equals(PlayerSmasher.KEY_RIGHT)){
				
					if(this.velocidad <= this.max_velocidad){
					
						this.velocidad = this.velocidad + this.aceleracion;
						
						if(this.status != Players.ONGAME_APARITION && 
								this.status != Players.ONGAME_DIED &&
								this.status != Players.ONGAME_JUMPING){
						
							this.status = Players.ONGAME_RUNING;
							
						}
						
					}
					
				}
				
				if(this.keysPressed.get(i).equals(PlayerSmasher.KEY_LEFT)){
					
					if(this.velocidad >= -this.max_velocidad){
						
						this.velocidad = this.velocidad - this.aceleracion;
						
						if(this.status != Players.ONGAME_APARITION && 
								this.status != Players.ONGAME_DIED &&
								this.status != Players.ONGAME_JUMPING){
						
							this.status = Players.ONGAME_RUNING;
							
						}
						
					}
					
				}
				
				if(this.keysPressed.get(i).equals(PlayerSmasher.KEY_FIRE)){
					
					if(this.onground == true){
					
						this.velocidady = -this.pow_jump;
						
					}
					
				}
				
			}
			
			// X player movement
			endx = this.x + this.velocidad;
			
			while(this.x != endx && this.velocidad != 0){
			
				// Detect the collision on the collision list.
				detectAllCollisions(myworld);
				
				if(this.onwall == true){
					
					if(this.velocidad > 0){
						
						this.x = this.x-1;
						
					} else {
						
						this.x = this.x+1;
						
					}
					
					this.velocidad = 0;
					
				} else {
					
					if(this.velocidad > 0){
					
						this.x = this.x + 1;
								
					} else {
						
						this.x = this.x - 1;
						
					}
					
				}
				
			}
			
			if(this.velocidad > this.aderencia){
				
				this.velocidad = this.velocidad - this.aderencia;
				
			} else if (this.velocidad < -this.aderencia){
				
				this.velocidad = this.velocidad + this.aderencia;
			
			} else {
			
				this.velocidad = 0;
				
				if(this.status != Players.ONGAME_APARITION && 
						this.status != Players.ONGAME_DIED){
				
					this.status = Players.ONGAME_STOP;
					
				}
				
			}
			
			// Y player movement
			endy = this.y + this.velocidady;
			
			while(this.y != endy && this.velocidady != 0){
				
				// Detect the collision on the collision list.
				detectAllCollisions(myworld);
				
				if(this.onground == true){
					
					if(this.velocidady > 0){
						
						this.y = this.y-1;
						
					}
					
					this.velocidady = 0;
					
				} else if(this.onceil == true){
					
					if(this.velocidady < 0){
						
						this.y = this.y+1;
						
					}
					
					this.velocidady = 0;
					
				} else {
					
					if(this.velocidady > 0){
					
						this.y = this.y + 1;
								
					} else {
						
						this.y = this.y - 1;
						
					}
					
				}
				
			}
			
			if(this.velocidady < Players.MAX_VELOCIDADY){
				
				this.velocidady = this.velocidady + this.gravity;
				
			}
			
		}
		
	}

	private void detectAllCollisions(ArrayList <WorldElement>myworld) {
		
		int i;
		
		this.onwall = false;
		this.onceil = false;
		this.onground = false;
		
		this.calculateCorners();
		
		for(i=0; i < myworld.size();i++){
			
			collisionDetector(myworld.get(i));
			
		}
		
	}

	public boolean isFinished() {
		
		if(this.frame != Players.ANIMATION_DEAD){
		
			return false;
			
		} else {
			return true;
		}
	}
	
}


