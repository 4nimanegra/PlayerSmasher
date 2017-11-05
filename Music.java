import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Music {
	
	String[] backgroundMusic;
	OggClip myClip;
	
	int previousScreen;
	
	public static final String INTRO_MUSIC = "music/oberture.ogg";
	public static final String PLAYER_MUSIC = "music/playerselect.ogg";
	public static final String LEVEL1_MUSIC = "music/level.ogg";
	public static final String LEVEL2_MUSIC = "music/level2.ogg";
	
	public Music(){
		
		this.previousScreen = -1;
		
		this.backgroundMusic = new String[4];
			
		this.backgroundMusic[0] = Music.INTRO_MUSIC;
		
		this.backgroundMusic[1] = Music.LEVEL1_MUSIC;
		
		this.backgroundMusic[2] = Music.LEVEL2_MUSIC;
		
		this.backgroundMusic[3] = Music.PLAYER_MUSIC;
		
	}
	
	public void song(int screen){
		
		if(this.previousScreen != screen){
			
			if(this.previousScreen != -1){
				
				this.myClip.stop();
				
				
				this.previousScreen = -1;
				
				
			}
			
		}
		
		try{
		
			switch(screen){
					
				case PlayerSmasher.MENU_SCREEN:
					
					if(this.previousScreen != screen){
						
						this.myClip = new OggClip(new FileInputStream(Music.INTRO_MUSIC));
						
						this.myClip.loop();
						
					}
					
					break;
					
				case PlayerSmasher.GAME_SCREEN:
					
					if(this.previousScreen != screen){
						
						this.myClip = new OggClip(new FileInputStream(Music.LEVEL1_MUSIC));
						
						this.myClip.loop();
						
					}
					
					break;
					
				case PlayerSmasher.HOF_SCREEN:
					
					if(this.previousScreen != screen){
						
						this.myClip = new OggClip(new FileInputStream(Music.INTRO_MUSIC));
						
						this.myClip.loop();
						
					}
					
					break;
					
				case PlayerSmasher.PLAYER_SELECT_SCREEN:
					
					if(this.previousScreen != screen){
						
						this.myClip = new OggClip(new FileInputStream(Music.PLAYER_MUSIC));
						
						this.myClip.loop();
						
					}
					
					break;
					
				case PlayerSmasher.TITLE_SCREEN:
									
					if(this.previousScreen != screen){
						
						this.myClip = new OggClip(new FileInputStream(Music.INTRO_MUSIC));
						
						this.myClip.loop();
						
					}
					
					break;
					
				default:
					
					break;
			
			}
				
		}catch(IOException e){
			
			System.out.println("Excepcion.");
		
		}
		
		this.previousScreen = screen;
		
	}

}
