import java.util.ArrayList;

class Goofin{
	public static void main(String[]args){
	
	//MyPanel
	ImageIcon[] platformType = {};
	
	int[] waitTimes = new int{5,6,7,8};
	
	int pType = 4;
	int wait = 0;
	int height = 400;
	
	ArrayList <int[]> platforms = new ArrayList<int[]>();
	int [] arr = {1,2,3,5};
	platforms.add(arr);
	
	
	
	//Timer loop
	if(wait == 0){
		platform.add({600, height, pType});
		pType = (int)(Math.random()*4);
		wait += waitTimes[pType];
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	for(int i = 0; i<arr.length; i++){
		System.out.println(platforms.get(0)[i]);
	}
	
	}
}