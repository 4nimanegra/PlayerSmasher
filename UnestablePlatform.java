import java.awt.Graphics;

public class UnestablePlatform extends WorldElement{

	public static final int BLOCK_LENGTH = 10;

	private static final int STEEPSTODESTROY = 40;
	
	int[] xbridge;
	int[] ybridge;
	int[] destroy;
	
	public UnestablePlatform(int x, int y) {
		
		super(x, y);
		
		int i,ii,initialcounter;
		// Lets make the bridge with a muliply of each element size.	
		this.width = this.width+UnestablePlatform.BLOCK_LENGTH-this.width%UnestablePlatform.BLOCK_LENGTH;
		
		// This kind of element must be the same eight as each element.
		this.height = UnestablePlatform.BLOCK_LENGTH;
			
		// Now calculate the number of elements to show.
		i = this.width/UnestablePlatform.BLOCK_LENGTH;
		
		this.destroy = new int[i];
		this.xbridge = new int[i];
		this.ybridge = new int[i];
		
		initialcounter = ((int)(Math.random()*2*UnestablePlatform.STEEPSTODESTROY)-UnestablePlatform.STEEPSTODESTROY);
		
		for(ii=0;ii<i;ii++){
			
			this.destroy[ii] = initialcounter;
			this.xbridge[ii] = this.x+ii*UnestablePlatform.BLOCK_LENGTH;
			this.ybridge[ii] = this.y;
			
		}
		
		this.type = WorldElement.PLATFORMDESTROY;
		
	}

	public void paint(Graphics layer,int x,int y){
		
		boolean todraw;
		int i;
		
		todraw = false;
			
		if(Math.abs(x-this.x) < 180){
				
			todraw = true;
			
		}
		
		if(Math.abs(x-(this.x+this.width)) < 180){
			
			todraw = true;
			
		}

		if(todraw == true){
			
			for(i=0;i<this.xbridge.length;i++){
			
				if(this.destroy[i] > 0){
				
					layer.setColor(PlayerSmasher.DARK_COLOR);
					layer.fillOval((80-x)+this.xbridge[i], this.ybridge[i], UnestablePlatform.BLOCK_LENGTH, UnestablePlatform.BLOCK_LENGTH);
			
					layer.setColor(PlayerSmasher.VERY_DARK_COLOR);
					layer.drawOval((80-x)+this.xbridge[i], this.ybridge[i], UnestablePlatform.BLOCK_LENGTH, UnestablePlatform.BLOCK_LENGTH);
					
					layer.drawOval((80-x)+this.xbridge[i]+2, this.ybridge[i]+2, UnestablePlatform.BLOCK_LENGTH-4, UnestablePlatform.BLOCK_LENGTH-4);
					
					layer.drawOval((80-x)+this.xbridge[i]+4, this.ybridge[i]+4, UnestablePlatform.BLOCK_LENGTH-8, UnestablePlatform.BLOCK_LENGTH-8);
					
				}
				
				if(this.destroy[i] > -UnestablePlatform.STEEPSTODESTROY){
					
					this.destroy[i] = this.destroy[i] - 1;
					
				}else{
				
					this.destroy[i] = UnestablePlatform.STEEPSTODESTROY;
				
				}
				
			}
			
		}
	}
	
}
