import java.util.ArrayList;
import javax.swing.*;

class Goofin{
	public static void main(String[]args){
	
	//MyPanel
	ImageIcon[] platformType = {};
	
	int[] waitTimes = new int[]{5,6,7,8};
	int lHeight;
	
	int pType = 4; //Long platform ;)
	int wait = 0; 
	int height = 400; //Elevation of the special platform
	int MINH = 450;
	int MAXH = 100;
	int minT;
	int maxT;
	int maxD;
	
	int G = 2;
	int V = 20;
	int JUMPHEIGHT = (V*V)/(2*G);
	int speed = 1;
	int VX = speed;
	
	int[] tempList = new int[3];
	
	ImageIcon [] platformSprites = {
		new ImageIcon("Platform1.png"),
		new ImageIcon("Platform1.png"),
		new ImageIcon("Platform1.png"),
		new ImageIcon("Platform1.png"),
		new ImageIcon("Platform1.png")
	};
	
	// ArrayList <int[]> platforms = new ArrayList<int[]>();
	// int [] arr = {1,2,3,5};
	// platforms.add(arr);
	

	
	//Timer loop
	if(wait == 0){
		tempList[0] = 600 + speed*10;
		tempList[1] = height;
		tempList[2] = pType;
		platforms.add(tempList);
		
		pType = (int)(Math.random()*4);
		wait += waitTimes[pType];
		lHeight = platforms.get(platforms.size()-1)[1];
		maxT = Math.max(lHeight - JUMPHEIGHT-10,MAXH);
		minT = Math.min(lHeight + JUMPHEIGHT-10,MINH);
		height = (int)(Math.random()*(minT-maxT))+maxT;
		maxD = VX * (  ( V + (int)Math.sqrt(V*V + 2*G*height) ) / G  )  -  10;
		wait += (int)(Math.random()*maxD);
		
	}
	wait -= speed*10;
	
	for(int i = 0; i < platforms.size(); i++)
		platforms.get(i)[0] -= speed*10;
	
	for(int i = 0; i < platforms.size(); i++)
		gr.drawImage(platformSprites[platforms.get(i)[2]].getImage(), platforms.get(i)[0], platforms.get(i)[1], null);
	
	
	
	
	
	
	
	
	
	
	
	
	
	// for(int i = 0; i<arr.length; i++){
		// System.out.println(platforms.get(0)[i]);
	// }
	
	// }
}