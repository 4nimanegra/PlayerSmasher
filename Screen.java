import java.awt.Graphics;


public class Screen {
	
	public boolean finished;
	public int nextStatus;
	
	public Screen(int status){
		
		this.finished = true;
		this.nextStatus = status;
		
	}
	
	public void paint(Graphics layer){
		
		System.err.println("Error: executing paint class from generic paint class.\n Next status will be "+nextStatus);
		
	}
	
	public void refresh(){
		
		
	}
	
	public void resetFinished(){
		
		
	}
	
	public void keyPressed(int key){
		
	}
	
	public void keyReleased(int key){
		
	}
	

}
