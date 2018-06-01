import java.util.ArrayList;

class Goofin{
	public static void main(String[]args){
	
	//MyPanel
	ImageIcon[] platformType = {};
	
	int[] waitTimes = new int[]{5,6,7,8};
	int lheight;
	
	int pType = 4; //Long platform B O I
	int wait = 0; 
	int height = 400; //Elevetion of the special platform
	
	ArrayList <int[]> platforms = new ArrayList<int[]>();
	int [] arr = {1,2,3,5};
	platforms.add(arr);
	
	
	
	//Timer loop
	if(wait == 0){
		int[] tempList = new int[]{600, height, pType};
		platforms.add(tempList);
		pType = (int)(Math.random()*4);
		wait += waitTimes[pType];
		lheight = platforms.get(platforms.size()-2)[1];
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	for(int i = 0; i<arr.length; i++){
		System.out.println(platforms.get(0)[i]);
	}
	
	}
}