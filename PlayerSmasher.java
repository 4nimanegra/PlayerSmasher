import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayerSmasher extends JPanel implements KeyListener{

	// Constants refereed to the game screen.
	public static final int TITLE_SCREEN=0;
	public static final int MENU_SCREEN=1;
	public static final int GAME_SCREEN=2;
	public static final int PLAYER_SELECT_SCREEN=3;
	public static final int HOF_SCREEN=4;
	public static final int NO_STATUS=-1;
	
	// Colors for screen.
	public static final Color VERY_DARK_COLOR = new Color(0x0f,0x38,0x0f);
	public static final Color DARK_COLOR = new Color(0x30,0x62,0x30);
	public static final Color LIGTH_COLOR = new Color(0x8b,0xac,0x0f);
	public static final Color VERY_LIGTH_COLOR = new Color(0x9b,0xbc,0x0f);
	
	// Fonts for the game
	public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
	public static final Font HEAD_FONT = new Font("Arial", Font.BOLD, 14);
	public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 10);
	public static final Font BOLD_NORMAL_FONT = new Font("Arial", Font.PLAIN, 10);
	
	// Keys for player
	public static final int KEY_FIRE = KeyEvent.VK_SPACE;
	public static final int KEY_DOWN = KeyEvent.VK_DOWN;
	public static final int KEY_LEFT = KeyEvent.VK_LEFT;
	public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	public static final int KEY_UP = KeyEvent.VK_UP;
	
	// Objetos para las diferentes pantallas.
	TitleScreen titleScreen;
	MenuScreen menuScreen;
	GameScreen gameScreen;
	PlayerSelect playerSelect;
	HofScreen hofScreen;
	
	// Variable used to redraw operations
	Timer timer;
	
	// Status of the game is the part where
	// the game is. In order to have a separate
	// paint process for the title, menu, game 
	// and hallOfFame screen.
	int gameStatus;
	
	// Main window related variables
	JFrame window;
	
	int window_orig_width;
	int window_orig_height;
	
	// Constant for the game dimensions
	public static final int SCREEN_WIDTH=160;
	public static final int SCREEN_HEIGHT=144;
	
	// Music player object for background music.
	// TODO:
	Music backgroundmusic;
	
	// Game class generator.
	// This object will draw and manage 
	// all the game events. And will create the
	// different objects.
	public PlayerSmasher(JFrame window){
		
		this.window = window;
		
		window.setSize(PlayerSmasher.SCREEN_WIDTH,PlayerSmasher.SCREEN_HEIGHT);
		
		this.setPreferredSize(new Dimension(PlayerSmasher.SCREEN_WIDTH,PlayerSmasher.SCREEN_HEIGHT));
		
		this.setSize(PlayerSmasher.SCREEN_WIDTH,PlayerSmasher.SCREEN_HEIGHT);
		
		this.setFocusable(true);
		
		this.addKeyListener(this);
		
		// TODO:
		this.backgroundmusic = new Music();
		
		gameStatus=PlayerSmasher.TITLE_SCREEN;
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new RepeatMe(), 0, 40);
		
	}
	
	// This method have the task to change the
	// state from one screen to other and also
	// create the different screens of the game
	private void screenChanger(){
		
		int previousStatus;
		
		previousStatus = PlayerSmasher.NO_STATUS;
		
		// First the screenChanger have to know
		// in which screen we are.
		while(previousStatus != this.gameStatus){
			
			previousStatus = this.gameStatus;
		
			switch(this.gameStatus){
		
				case  PlayerSmasher.TITLE_SCREEN:
					
					if(this.titleScreen == null){
						
						this.titleScreen = new TitleScreen();
						
						
					}
					
					if(this.titleScreen.finished == true){
						
						this.gameStatus = this.titleScreen.nextStatus;
						
						this.titleScreen.resetFinished();
						
					} else {
						
						this.titleScreen.refresh();
						
					}
					
					break;
				case  PlayerSmasher.GAME_SCREEN:
	
					if(this.gameScreen == null){
						
						
						this.gameScreen = new GameScreen(this.playerSelect.playersList[this.playerSelect.character]);
						
					}				
					
					if(this.gameScreen.finished == true){
						
						this.gameStatus = this.gameScreen.nextStatus;
						
						if(this.hofScreen == null){
							
							this.hofScreen = new HofScreen();
							
						}
						
						this.hofScreen.putPoints(this.gameScreen.points.points,this.playerSelect.playersList[this.playerSelect.character].name);
						
						this.gameScreen.resetFinished();
						
					} else {
						
						this.gameScreen.player=this.playerSelect.playersList[this.playerSelect.character];
						
						this.gameScreen.refresh();
						
					}
					
					break;
					
				case  PlayerSmasher.PLAYER_SELECT_SCREEN:
	
					if(this.playerSelect == null){
						
						this.playerSelect = new PlayerSelect();
						
					}
					
					if(this.playerSelect.finished == true){
						
						this.gameStatus = this.playerSelect.nextStatus;
						
						this.playerSelect.resetFinished();
						
					}else {
						
						this.playerSelect.refresh();
						
					}
					
					break;
					
				case  PlayerSmasher.MENU_SCREEN:
	
					if(this.menuScreen == null){
						
						this.menuScreen = new MenuScreen();
						
					}
					
					if(this.menuScreen.finished == true){
					
						this.gameStatus = this.menuScreen.nextStatus;
						
						this.menuScreen.resetFinished();
						
					}else {
						
						this.menuScreen.refresh();
						
					}
					
					break;
				case  PlayerSmasher.HOF_SCREEN:
	
					if(this.hofScreen == null){
						
						this.hofScreen = new HofScreen();
						
					}
					
					if(this.hofScreen.finished == true){
						
						this.gameStatus = this.hofScreen.nextStatus;
						
						this.hofScreen.resetFinished();
						
					}else {
						
						this.hofScreen.refresh();
						
					}
					
					break;
					
				default:
					
						break;
			
			}
			
		}
		
		this.backgroundmusic.song(this.gameStatus);
		
	}
	
	// Method to get the drawable object to show
	// on screen
	public void paint(Graphics layerWindow){
		
		BufferedImage layerImage;
		Graphics layer;
		
		layerImage = new BufferedImage(PlayerSmasher.SCREEN_WIDTH,PlayerSmasher.SCREEN_HEIGHT,BufferedImage.TYPE_3BYTE_BGR);
		layer = layerImage.getGraphics();
		
		switch(this.gameStatus){
		
			case  PlayerSmasher.TITLE_SCREEN:
				
				this.titleScreen.paint(layer);
				
				break;
				
			case  PlayerSmasher.GAME_SCREEN:

				this.gameScreen.paint(layer);
				
				break;
				
			case  PlayerSmasher.PLAYER_SELECT_SCREEN:

				this.playerSelect.paint(layer);
				
				break;
				
			case  PlayerSmasher.MENU_SCREEN:

				this.menuScreen.paint(layer);
				
				break;
				
			case  PlayerSmasher.HOF_SCREEN:

				this.hofScreen.paint(layer);
				
				break;
				
				
			default:
				
					break;
			
		}
		
		if(this.window_orig_width != -1){
		
			((Graphics2D)layerWindow).scale((double)this.getWidth()/(double)PlayerSmasher.SCREEN_WIDTH, (double)this.getHeight()/(double)PlayerSmasher.SCREEN_HEIGHT);
					
		}
		
		layerWindow.drawImage(layerImage,0,0,null);

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
		switch(this.gameStatus){
		
			case  PlayerSmasher.TITLE_SCREEN:
				
				if(this.titleScreen != null){
					
					this.titleScreen.keyPressed(arg0.getKeyCode());
					
				}
				
				break;
				
			case  PlayerSmasher.GAME_SCREEN:
	
				if(this.gameScreen != null){
					
					this.gameScreen.keyPressed(arg0.getKeyCode());
					
				}
				
				break;
				
			case  PlayerSmasher.PLAYER_SELECT_SCREEN:
	
				if(this.playerSelect != null){
					
					this.playerSelect.keyPressed(arg0.getKeyCode());
					
				}
				
				break;
				
			case  PlayerSmasher.MENU_SCREEN:
	
				if(this.menuScreen != null){
					
					this.menuScreen.keyPressed(arg0.getKeyCode());
					
				}
				
				break;
				
			case  PlayerSmasher.HOF_SCREEN:
	
				if(this.hofScreen != null){
					
					this.hofScreen.keyPressed(arg0.getKeyCode());
					
				}
				
				break;
				
			default:
				
					break;
	
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

		switch(this.gameStatus){
		
		case  PlayerSmasher.TITLE_SCREEN:
			
			if(this.titleScreen != null){
				
				this.titleScreen.keyReleased(arg0.getKeyCode());
				
			}
			
			break;
			
		case  PlayerSmasher.GAME_SCREEN:

			if(this.gameScreen != null){
				
				this.gameScreen.keyReleased(arg0.getKeyCode());
				
			}
			
			break;
			
		case  PlayerSmasher.PLAYER_SELECT_SCREEN:

			if(this.playerSelect != null){
				
				this.playerSelect.keyReleased(arg0.getKeyCode());
				
			}
			
			break;
			
		case  PlayerSmasher.MENU_SCREEN:

			if(this.menuScreen != null){
				
				this.menuScreen.keyReleased(arg0.getKeyCode());
				
			}
			
			break;
			
		case  PlayerSmasher.HOF_SCREEN:

			if(this.hofScreen != null){
				
				this.hofScreen.keyReleased(arg0.getKeyCode());
				
			}
			
			break;
			
		default:
			
				break;

	}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	// Private class used to 
	// redraw and repetitive operations.
	private class RepeatMe extends TimerTask{

		@Override
		public void run() {
			PlayerSmasher.this.screenChanger();
			PlayerSmasher.this.repaint();
			
			
			if(PlayerSmasher.this.window_orig_height == -1){
				PlayerSmasher.this.window_orig_height = PlayerSmasher.SCREEN_HEIGHT;
				PlayerSmasher.this.window_orig_width = PlayerSmasher.SCREEN_WIDTH;
			}
			
		}
		
	}
	
	// Main method to execute the program
	// here is set the main window to be shown.
	public static void main(String[] args) {
		
		JFrame window;
		PlayerSmasher game;
		
		// Window creation in a gameboy size style.
		window = new JFrame();
		
		// Add the game on the window.
		game = new PlayerSmasher(window);
		window.add(game);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		
		window.setVisible(true);
		
		game.window_orig_height = -1;
		game.window_orig_width = -1;
		
	}

}
