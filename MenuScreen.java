import java.awt.Graphics;

public class MenuScreen extends Screen{

	// Possible options to use in the menu screeen
	private final static int NEWGAME_OPTION = 0;
	private final static int OPTIONS_OPTION = 1;
	private final static int HALLOFFAME_OPTION = 2;
	private final static int EXIT_OPTION = 3;
	
	private final static int TOTAL_OPTION = 4;
	
	int option_selected;
	
	public MenuScreen() {
		
		super(PlayerSmasher.PLAYER_SELECT_SCREEN);
		
		this.finished = false;
		
		this.option_selected = 0;
		
	}
	
	public void paint(Graphics layer){

		layer.setColor(PlayerSmasher.DARK_COLOR);
		
		layer.fillRect(0, 0, 160, 144);
		
		layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
		
		layer.fillRect(4, 4, 152, 136);
		
		layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
		
		layer.fillRect(5, 5, 150, 134);
		
		layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
		
		layer.setFont(PlayerSmasher.HEAD_FONT);
		
		layer.drawString("Player Smasher!!!", 25, 40);
		
		if(this.option_selected == MenuScreen.NEWGAME_OPTION){
			
			layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
			
			layer.setFont(PlayerSmasher.BOLD_NORMAL_FONT);
			
			layer.drawString("New game", 50, 70);
			
		} else {
			
			layer.setColor(PlayerSmasher.DARK_COLOR);
			
			layer.setFont(PlayerSmasher.NORMAL_FONT);
			
			layer.drawString("New game", 50, 70);
			
		}
		
		if(this.option_selected == MenuScreen.OPTIONS_OPTION){
			
			layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
			
			layer.setFont(PlayerSmasher.BOLD_NORMAL_FONT);
			
			layer.drawString("Options", 50, 90);
			
		} else {
			
			layer.setColor(PlayerSmasher.DARK_COLOR);
			
			layer.setFont(PlayerSmasher.NORMAL_FONT);
			
			layer.drawString("Options", 50, 90);
			
		}
		
		
		
		if(this.option_selected == MenuScreen.HALLOFFAME_OPTION){
			
			layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
			
			layer.setFont(PlayerSmasher.BOLD_NORMAL_FONT);
			
			layer.drawString("Hall of fame", 50, 110);
			
		} else {
			
			layer.setColor(PlayerSmasher.DARK_COLOR);
		
			layer.setFont(PlayerSmasher.NORMAL_FONT);
			
			layer.drawString("Hall of fame", 50, 110);
			
		}
		
		
		
		if(this.option_selected == MenuScreen.EXIT_OPTION){
			
			layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
			
			layer.setFont(PlayerSmasher.BOLD_NORMAL_FONT);
			
			layer.drawString("Exit game", 50, 130);
			
		} else {
			
			layer.setColor(PlayerSmasher.DARK_COLOR);
			
			layer.setFont(PlayerSmasher.NORMAL_FONT);
			
			layer.drawString("Exit game", 50, 130);
			
		}
		
	}
	
	public void keyPressed(int key){
		
		switch(key){
		
			case PlayerSmasher.KEY_DOWN:
				
				this.option_selected = (this.option_selected + 1)%MenuScreen.TOTAL_OPTION;
				
				break;
				
			case PlayerSmasher.KEY_UP:
				
				this.option_selected = (this.option_selected - 1 + MenuScreen.TOTAL_OPTION)%MenuScreen.TOTAL_OPTION;
				
				break;
				
			case PlayerSmasher.KEY_FIRE:
				
				this.finished = true;
				
				switch(this.option_selected){
				
					case MenuScreen.HALLOFFAME_OPTION:
						
						this.nextStatus = PlayerSmasher.HOF_SCREEN;
						break;
						
					case MenuScreen.NEWGAME_OPTION:
						
						this.nextStatus = PlayerSmasher.PLAYER_SELECT_SCREEN;
						break;
						
					case MenuScreen.OPTIONS_OPTION:
						
						this.nextStatus = PlayerSmasher.MENU_SCREEN;
						break;
						
					case MenuScreen.EXIT_OPTION:
						
						System.exit(0);
						break;
				
				}
				
				break;
				
		}		
		
	}
	
	// This can be put on the top class, but for developing
	// purposes it is here.
	// TODO: put it on the top class when finished.
	public void resetFinished(){
	
		this.finished = false;
		
	}

}
