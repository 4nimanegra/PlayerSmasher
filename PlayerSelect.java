import java.awt.Graphics;


public class PlayerSelect extends Screen{

	// A list for the players to select
	Players[] playersList;
	
	// And the constants to fix the numbers
	final static int TOTAL_SELECTED_PLAYERS = 2;
	
	final static int PLAYER_INITIAL_X=80;
	final static int PLAYER_INITIAL_Y=100;
	
	// For known which character have chosen the player
	int character;
	int changeto;
	
	public PlayerSelect() {
		
		super(PlayerSmasher.GAME_SCREEN);
		
		this.finished = false;
		
		this.playersList = new Players[PlayerSelect.TOTAL_SELECTED_PLAYERS];
		
		this.playersList[0] = new Players(PlayerSelect.PLAYER_INITIAL_X,PlayerSelect.PLAYER_INITIAL_Y);
		this.playersList[1] = new Celinda(PlayerSelect.PLAYER_INITIAL_X,PlayerSelect.PLAYER_INITIAL_Y);
		
		this.character = 0;
		this.changeto = 0;
	
	}
	
	public void paint(Graphics layer){
		
		// Lets put the same formating as the previous screen.
		
		layer.setColor(PlayerSmasher.DARK_COLOR);
		
		layer.fillRect(0, 0, 160, 144);
		
		layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
		
		layer.fillRect(4, 4, 152, 136);
		
		layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
		
		layer.fillRect(5, 5, 150, 134);
		
		// Title of the screen
		layer.setColor(PlayerSmasher.DARK_COLOR);
		layer.setFont(PlayerSmasher.HEAD_FONT);
		layer.drawString("Select your",35,30);
		layer.drawString("character!!!",30,45);
		
		// paint the selected player.
		this.playersList[this.character].paint(layer);
		
		if(this.changeto != 0){
			
			if(this.playersList[this.character].isFinished()){
				
				this.playersList[this.character].reset();
				this.character = (this.character + this.changeto)%PlayerSelect.TOTAL_SELECTED_PLAYERS;
				
				if(this.character<0){
					
					this.character=PlayerSelect.TOTAL_SELECTED_PLAYERS-1;
					
				}
				
				this.changeto = 0;
				
			}
			
		}
		
	}
	
	public void refresh(){
	
		this.playersList[this.character].refresh(null);
		
	}
	
	public void keyPressed(int keyCode){
		
		this.playersList[this.character].keyPressed(keyCode);
		
		switch(keyCode){
			
			case PlayerSmasher.KEY_LEFT:
			
				this.changeto = -1;
				
				break;
			
			case PlayerSmasher.KEY_RIGHT:
			
				this.changeto = 1;
				
				break;
				
			case PlayerSmasher.KEY_FIRE:
				
				this.finished = true;
				
				break;
			
			default:
				
				break;
			
		}
		
	}
	
	public void keyReleased(int keyCode){
		
		this.playersList[this.character].keyReleased(keyCode);
		
	}
	
	// This can be put on the top class, but for developing
	// purposes it is here.
	// TODO: put it on the top class when finished.
	public void resetFinished(){
		
		this.finished = false;
		
		this.playersList[this.character].reset();
		
		this.playersList[this.character].x = PlayerSelect.PLAYER_INITIAL_X;
		this.playersList[this.character].y = PlayerSelect.PLAYER_INITIAL_Y;
		
	}

}
