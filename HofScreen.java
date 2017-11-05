import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class HofScreen extends Screen {
	
	// Statics variables for number of winners and scoreboard
	// data file.
	final static String SCORE_FILE = "scores/scores.dat";
	final static int SCORE_NUM = 10;
	
	// Class field to store all the best scores.
	String[] list;
	long[] points;
	String[] names;
	
	public HofScreen() {
		
		super(PlayerSmasher.MENU_SCREEN);
		
		int i;
		String[] aux;
		
		// lets says that we have an usable object from now 
		this.finished = false;
		
		// Lets fill the scoreboard with the file
		list = new String[HofScreen.SCORE_NUM];
		names = new String[HofScreen.SCORE_NUM];
		points = new long[HofScreen.SCORE_NUM];
		
		// Some error could occur, so cross your fingers 
		try {
			
			// The objects need to read and this kind of issues
			FileInputStream inputFile = new FileInputStream(HofScreen.SCORE_FILE);
			
			InputStreamReader inputStream = new InputStreamReader(inputFile);
			
			BufferedReader inputReader = new BufferedReader(inputStream);
			
			// lets read line by line filling the score list
			i = 0;
			
			while(i < HofScreen.SCORE_NUM){
				
				// Read a new line an put it on list
				list[i] = inputReader.readLine();
				
				// Some error on file, but lets continue with
				// the game, it is better play than stop the
				// game for issues like this
				if(list[i] == null){
					
					// Put some rubbish score
					this.list[i] = new String("BBC 000999");
					this.names[i] = "BBC";
					this.points[i] = 999;
					
				} else {
				
					aux = list[i].split(" ");
					list[i] = String.format("%s %06d", aux[0], Long.parseLong(aux[1]));
					this.names[i] = aux[0];
					this.points[i] = Long.parseLong(aux[1]);
			
				}
				
				i = i + 1;
				
			}
			
			// Be clean and close your file descriptors
			inputReader.close();
			
		} catch (FileNotFoundException e) {
			
			// Some error, the score file is not found. 
			// A weird thing but probably a bad installation
			// issue related, better go out.
			System.err.println("File not found "+HofScreen.SCORE_FILE);
			
			System.exit(-1);
			
		} catch (IOException e) {
			
			// Ok an IOException, is a bad ass error, better go out.
			System.err.println("Input Output error on file "+HofScreen.SCORE_FILE);
			
			System.exit(-1);
			
		}
		
	}
	
	public void paint(Graphics layer){
		
		int i;
		
		// Lets put the same formating as the previous screen.
		
		layer.setColor(PlayerSmasher.DARK_COLOR);
		
		layer.fillRect(0, 0, 160, 144);
		
		layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
		
		layer.fillRect(4, 4, 152, 136);
		
		layer.setColor(PlayerSmasher.VERY_LIGTH_COLOR);
		
		layer.fillRect(5, 5, 150, 134);
		
		layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
		
		layer.setFont(PlayerSmasher.HEAD_FONT);
		
		// And now the title of the Hall Of Fame
		
		layer.drawString("Hall of fame!!!", 35, 25);
		
		// And the best players go here
		i = 0;
		
		while(i < HofScreen.SCORE_NUM){
		
			layer.setFont(PlayerSmasher.NORMAL_FONT);
			
			layer.drawString(this.list[i], 55, 40+(i*10));
			
			i = i+1;
			
		}
		
	}
	
	// When any key is pressed back to the menu window
	public void keyPressed(int key){
		
		this.finished = true;
				
	}
	
	// This can be put on the top class, but for developing
	// purposes it is here.
	// TODO: put it on the top class when finished.
	public void resetFinished(){
	
		this.finished = false;
		
	}
	
	public void putPoints(long points,String name){
	
		int i,ii;
		
		// Lets see if my points are more than another one.
		i=0;
		
		while((i<HofScreen.SCORE_NUM) && (points < this.points[i])){
			
			i=i+1;
			
		}
		
		// Lets move the possible lower punctuations.
		//for(ii=i;ii<(HofScreen.SCORE_NUM-1);ii++){
		for(ii=(HofScreen.SCORE_NUM-1);ii>i;ii--){
			
			this.points[ii]=this.points[ii-1];
			this.names[ii]=this.names[ii-1];
		
		}
		
		// If there are changes on the punctuation list lets change the file.
		if(i < HofScreen.SCORE_NUM){
			this.points[i]=points;
			this.names[i]=name;
		
		
			//Now save the points in file.
			try {
				
				// The objects need to read and this kind of issues
				FileOutputStream outputFile = new FileOutputStream(HofScreen.SCORE_FILE);
				
				OutputStreamWriter outputStream = new OutputStreamWriter(outputFile);
				
				BufferedWriter outputWriter = new BufferedWriter(outputStream);
				
				// lets read line by line filling the score list
				for(i=0;i < HofScreen.SCORE_NUM;i++){
				
					outputWriter.write(this.names[i]+" "+String.format("%06d\n", this.points[i]));
					list[i] = String.format("%s %06d", this.names[i], this.points[i]);
					
				}
				
				outputWriter.close();
				
			} catch(IOException e){
				
				System.out.println("Error: Can not write into the file.");
				
			}
	
		}
	
	}

}
