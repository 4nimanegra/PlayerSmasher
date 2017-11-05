import java.awt.Graphics;
import java.util.ArrayList;


public class GameScreen extends Screen{

	// Probabilities for the automatic world generation
	private static final double UNESTABLEPLATFORMPROB = 0.5;
	private static final double LASERBOLTPROB = 0.75;
	private static final double BALLPROB = 0.75;
	
	private static final int STEP_SHOW_LEVEL = 30;

	// Some constraints for the game
	static int MAX_Y_TO_DEATH=2*PlayerSmasher.SCREEN_HEIGHT;
	
	// Some level and displaying data elements.
	Points points;
	Clock clock;
	
	int level;
	int xlength;
	
	boolean prepared;
	
	ArrayList<WorldElement> world;
	
	// Characters of the game
	Players player;
	
	int showLevel;
	
	public GameScreen(Players player) {
		
		super(PlayerSmasher.MENU_SCREEN);
		
		this.clock = new Clock();
		this.points = new Points();
		
		this.world = new ArrayList<WorldElement>();
		
		this.finished = false;
	
		this.player = player;
		this.level = 0;
		
		this.prepared = false;
		
		this.showLevel = 0;
		
	}
	
	private void destroyLevel(){
		
		world.clear();
		
	}
	
	public void refresh(){
	
		int i;
		
		if(this.prepared == false){
			
			this.player.ongamestatus = Players.STATUS_ONGAME;
			
			this.level = this.level + 1;
			
			this.xlength = 2000+500*(int)(this.level/5);
			
			this.clock.reset(this.level);
			
			prepare_level();
			
			this.prepared = true;
			
		} else {
			
			if(this.player.status == Players.ONGAME_DIED){
								
				if(this.player.isFinished()){
				
					this.finished = true;
					
				}
				
			}
			
			for(i=0; i<this.world.size();i++){
				
				this.world.get(i).refresh();
				
			}
			
			this.player.refresh(this.world);
			
			if(this.player.x >= this.xlength){
				
				this.points.points = this.points.points + 10+(int)(10*this.level/10)+this.clock.seconds*10+(int)(this.clock.miliseconds/10); 
				
				this.prepared = false;
				
				this.player.reset();
				
				this.player.y = this.player.yground;
				
				this.destroyLevel();
				
			}
			
			if(this.clock.timeEnds()){
				
				this.level = this.level - 1;
				
				this.prepared = false;
				
				this.player.reset();
				
				this.player.y = this.player.yground;
				
				this.destroyLevel();
				
			}
			
		}
	
	}
	
	private void prepare_level() {

		int i;
		WorldElement aux,aux2;
		
		i=0;
		
		aux = new WorldElement(this.player.yground);
		
		this.world.add(aux);
		
		i=i+aux.width;
		
		while(i < this.xlength){
			
			aux2=null;
			
			if(this.level > 4){
				
				if(this.world.size() > 2){
					
					if((this.world.get(this.world.size()-1)).type == WorldElement.PLATFORM){
						
						if((Math.random()*Math.random()*((double)(this.level-4))) >= GameScreen.BALLPROB){
							
							aux2=aux;
							
							aux = new BallOfDeath(this.world.get(this.world.size()-1).x+(this.world.get(this.world.size()-1).width/2),this.world.get(this.world.size()-1).y);
							
							this.world.add(aux);
							
							aux=aux2;
							
							continue;
							
						}
						
					}
					
				}
				
			}
			
			if(this.level > 3){
				
				if(Math.random()*Math.random()*((double)(this.level-3)) > GameScreen.LASERBOLTPROB){
					
					if(this.world.size() > 2){
						
						if((this.world.get(this.world.size()-1).type == WorldElement.PLATFORM)
								&& (this.world.get(this.world.size()-1).width > LaserGround.MINIMUNSIZE)
								&& (this.world.get(this.world.size()-2).type != WorldElement.LASERGROUND)){
						
							aux2=aux;
							
							aux = new LaserGround(aux2.x,aux2.y,aux2.width);
							
							this.world.add(aux);
							
							aux=aux2;
							
							continue;
							
						}
						
					}
					
				}
				
			}
			
			if(this.level > 2){
				
				if(Math.random()*Math.random()*((double)(this.level-2)) > GameScreen.LASERBOLTPROB){
					
					if(this.world.size() > 2){
						
						if((this.world.get(this.world.size()-1).type != WorldElement.LASERBOLT)
								&& (this.world.get(this.world.size()-1).type != WorldElement.LASERGROUND)){
						
							aux2=aux;
							
							aux = new LaserBolt(aux.x+(int)(Math.random()*(i-aux.x-LaserBolt.BOLTWIDHT)),aux.y);
							
							this.world.add(aux);
							
							aux=aux2;
							
							continue;
							
						}
						
					}
					
				}
				
			}
			
			if(aux2 == null){
			
				if(this.level > 1){
				
					if(Math.random()*Math.random()*((double)(this.level-1)) > GameScreen.UNESTABLEPLATFORMPROB){
					
						if(this.world.size() > 0){
						
							if(this.world.get(this.world.size()-1).type == WorldElement.PLATFORM){
								
								aux = new UnestablePlatform(i,aux.y);
								
								aux2=aux;
								
							}
							
						}
						
					}
				
				}
				
			}
			
			if(aux2 == null) {
				
				aux = new WorldElement(i,aux.y);
				
			}
			
			this.world.add(aux);
			
			i = aux.x;
			
			i = i+aux.width;
			
		}
		
		i=this.world.size()-1;
		
		while(this.world.get(i).x > this.xlength){
		
			i=i-1;
			
		}
		
		if(this.world.get(i).width+this.world.get(i).x < this.xlength){
			
			this.world.get(i).width=2*(this.xlength-this.world.get(i).x);
			
			if(i < this.world.size()-1){
			
				if(this.world.get(i).x+this.world.get(i).width > this.world.get(i+1).x){
				
					this.world.remove(i+1);
					
				}	
				
			}
			
		}
		
		this.world.add(new ArriveMark(this.xlength,this.world.get(i).y));
		
		this.sortMyWorld();
		
		this.showLevel = 0;
		
	}
	
	private void sortMyWorld(){
	
		int i;
		WorldElement aux;
		
		i=1;
		
		while(i < this.world.size()){
			
			if((this.world.get(i).type==WorldElement.PLATFORM) ||
					(this.world.get(i).type==WorldElement.PLATFORMDESTROY)){
				
				if((this.world.get(i-1).type!=WorldElement.PLATFORM) &&
						(this.world.get(i-1).type!=WorldElement.PLATFORMDESTROY)){
					
					aux=this.world.get(i-1);
					this.world.set(i-1,this.world.get(i));
					this.world.set(i,aux);
					
				}
				
					
			}
			
			i=i+1;
			
		}
	
	}

	public void paint(Graphics layer){
		
		int i;
		
		if(this.showLevel > GameScreen.STEP_SHOW_LEVEL){
		
			layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
			layer.fillRect(0,0,160,144);
			
			// lets draw all the whole level
			for(i=0; i<this.world.size();i++){
				
				this.world.get(i).paint(layer,this.player.camerax,this.player.cameray);
				
			}
			
			// now the player.
			this.player.paint(layer);
					
			// Now paint the clock and points
			this.clock.paint(layer);
			this.points.paint(layer);
			
		} else {
			
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
			
			switch(this.showLevel%3){
			
				case 0:
					layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
					break;
				case 1:
					layer.setColor(PlayerSmasher.LIGTH_COLOR);
					break;
				case 2:
					layer.setColor(PlayerSmasher.DARK_COLOR);
					break;
				default:
					break;
				
			}
			
			layer.setFont(PlayerSmasher.TITLE_FONT);
			layer.drawString("Be ready for", PlayerSmasher.SCREEN_WIDTH/2-70,
					PlayerSmasher.SCREEN_HEIGHT/2);
			layer.drawString("Level "+this.level, PlayerSmasher.SCREEN_WIDTH/2-50,
					PlayerSmasher.SCREEN_HEIGHT/2+20);
			
			this.showLevel = this.showLevel + 1;
			
		}
	}
	
	public void keyPressed(int key){
	
		this.player.keyPressed(key);
		
	}
	
	public void keyReleased(int key){
		
		this.player.keyReleased(key);
		
	}
	
	// This can be put on the top class, but for developing
	// purposes it is here.
	// TODO: put it on the top class when finished.
	public void resetFinished(){
		
		this.finished = false;
		
		this.points.reset();
		
		world = new ArrayList<WorldElement>();
		
		this.finished = false;
	
		this.level = 0;
		
		this.clock.reset(this.level);
		
		this.prepared = false;
		
		this.player.reset();
			
	}

}
