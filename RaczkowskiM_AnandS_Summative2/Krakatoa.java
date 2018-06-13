/*
Shashank Anand & Michael Raczkowski
June 13 2018
Culminating Activity
ICS 3U1
Ms. Strelkovska
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Krakatoa extends JFrame {

   private Container c;
   private MyPanel mainPanel = new MyPanel();
   private JPanel southPanel = new JPanel();
   private JButton actionBtn1= new JButton("Jump");     //Jump button
   private JButton actionBtn2 = new JButton("Pause/Continue");      //Pause/Continue button
   private JButton exit = new JButton("Exit");          //Exit button 


	public Krakatoa()   {
		super( "Krakatoa: West of Java" );

		southPanel.setLayout(new GridLayout(1,3));

		c = getContentPane();
		c.setLayout( new BorderLayout() );

		// create and add buttons


		actionBtn1.addActionListener( mainPanel );
		actionBtn1.addKeyListener(mainPanel);
		southPanel.add(actionBtn1);
		actionBtn2.addActionListener( mainPanel );
		actionBtn2.addKeyListener(mainPanel);
		southPanel.add(actionBtn2);
		exit.addActionListener( mainPanel );
		exit.addKeyListener(mainPanel);
		southPanel.add(exit);
		addKeyListener(mainPanel);

		c.add( southPanel, BorderLayout.SOUTH );
		c.add( mainPanel, BorderLayout.CENTER  );
		setSize( 600, 600 );                          //size of the window, can be changed
		setVisible(true);
   }

  
         
  
    public static void main( String args[] ) {
        Krakatoa app = new Krakatoa();
        app.addWindowListener(
            new WindowAdapter() {
                public void windowClosing( WindowEvent e )
                {
                   System.exit( 0 );
				}
         }
      );
   } 
}
 class MyPanel extends JPanel implements ActionListener, KeyListener{
	//variables - they are all global
	int count; //timer (count variable)
	int jump = 0; //Marks the frame when you start jumping
	int JUMPLENGTH = 20; //Length of the jump animation in frames
	boolean isjump = false; 
	boolean isPaused = false;
	int screen = 0; 	//Controls whether the main menu, story, or game are up; 0 = title screen, 1 = instructions, 2 and up is the game
	double speed = 1.4;	 	//coefficient that controls how fast the platforms come at you
	String storyline = "The year is 1883. You are a Dutch colonist in   #search of precious jewels. The natives of the   #island of Java had told you not to enter        #KRAKATOA, but in your folly and greed, you had  #decided to ignore them. Now, the volcano is     #erupting, and you must escape before it is too  #late. Your chances do not look good...                    #Press enter to begin.                           ";
	String story[] = storyline.split("#"); 		
	String deathMsg = "You died";
	String enterMsg = "Press enter to replay";
	String scoreMsg = "";
	Font  f1  = new Font(Font.MONOSPACED, Font.BOLD,  100);
	Font f2 = new Font(Font.MONOSPACED, Font.BOLD, 40);
	Font f3 = new Font(Font.MONOSPACED, Font.PLAIN, 20);
	Font f4 = new Font(Font.MONOSPACED, Font.BOLD, 20);
	int jframe = 0; 	//Shows which frame of the jump you're in
	int MAXHEIGHT = 100; 	//Maximum height of the screen
	int MINHEIGHT = 525;	//Minimum height of the screen
	double G = 2;	//Acceleration caused by gravity
	double V = 20; 	//Initial y velocity of the jump
	double JUMPHEIGHT = (V*V)/(2*G); 
	int VX = (int)(speed*10);
	int lHeight; 	//height of current platform (last height) or platform you just jumped off of
	int pType = 3;	//type of platform 
	int wait = 0; 	//time between deployment of platforms 
	int height = 212;	//platform height
	int MINH = 450;		//Absolute lowest character can go 
	int MAXH = 100;	 	//Absolute highest character can go 
	int minT; 			//Lowest character can go this jump
	int maxT;			//Highest character can go this jump 
	int maxD;			//Maximum distance between any two platforms
	int x2 = 0;			//The count coordinate of the platform above or under the character
	int MANX = 152;		//count coordinate of the character
	int ymin = 212;		//Height of the platform character jumped off of 
	int y = ymin;		//Y coordinate of character
	int yminT = ymin;	//Height of the platform character is over/under
	int ylast = y;		//Previous y coordinate of the character
	boolean isPlatform = false;		//Determines whether or not the character is above a platform
	int t = 0; 			//Length of platform 
	boolean isfalling = false;
	int jT = 0; 		//Jump Time — how many frames the character has been in the jump for
	boolean firstLast = true;  		//Checks if it's the first frame of the last screen 
	int score = 0;		
	int hScore = 0;		//High score
	int[] waitTimes = new int[]{241,304,179,0}; 	//Length of platforms. Alternatively, how long to wait before next platform

	//<Sprites>
	ImageIcon background = new ImageIcon("background2.gif");
	ImageIcon [] jogger = {
		new ImageIcon("Jogging01.png"),
		new ImageIcon("Jogging06.png"),
		new ImageIcon("Jogging07.png"),
		new ImageIcon("Jogging08.png"),
		new ImageIcon("Jogging09.png"),
		new ImageIcon("Jogging10.png"),
		new ImageIcon("Jogging11.png"),
		new ImageIcon("Jogging12.png"),
		new ImageIcon("Jogging13.png")
	 }; 
	ImageIcon falling = new ImageIcon("Jumping05.png");
	ImageIcon [] jumping = {
		new ImageIcon("Jumping01.png"),
		new ImageIcon("Jumping02.png"),
		new ImageIcon("Jumping03.png"),
		new ImageIcon("Jumping04.png"),
		falling,
		falling,
		falling,
		falling
	};
	ImageIcon [] platformSprites = {
		new ImageIcon("Platform1.png"),
		new ImageIcon("Platform2.png"),
		new ImageIcon("Platform4.png"),
		new ImageIcon("PlatformL.png")
	};
	// </Sprites>
	
	//Platform lists
	ArrayList <Integer> platformsT = new ArrayList<Integer>();
	ArrayList <Integer> platformsX = new ArrayList<Integer>();
	ArrayList <Integer> platformsY = new ArrayList<Integer>();

	
	private Timer myTimer= new Timer( 60, this );
	 
	 
	//Sets panel size
	public  MyPanel() { 			//initialize all the variables

           // Constructor: set background color to white set up listeners to respond to mouse actions

		 
		setBackground(new Color(248,236,194));				 
		addKeyListener(this);	
        count=0;
  		myTimer.start();
	}

	
	
	
    public void keyPressed( KeyEvent ev ) {
		
		// jump when up key pressed
		if (ev.getKeyCode()==38 && !isjump && screen > 1){
			isjump = true;
			jump = count;
		}

		//changes screen
		if (ev.getKeyCode()==10 && screen < 2){
			if (screen == -1)
				screen = 2;
			else
				screen += 1;
			count = 0;
			firstLast = true;
		}
	}	
    public void keyReleased( KeyEvent e ){		
	}
    public void keyTyped( KeyEvent e ){}
	
    public void actionPerformed( ActionEvent e ){ 
	
		//timer events
        if (e.getSource()==myTimer){
			
			//Increment variables
			count++;
			if (screen > 1)		
				speed += 0.000275;
			score = count;
			
			//calculates next frame of jump
		    if (isjump){
				jT = count-jump;
				

				if (isfalling)
					y = (int) (0.5*G*Math.pow(jT, 2) + ymin);
				else
					y = (int)(0.5*G*Math.pow((jT), 2) - V*(jT) + ymin);
				
				if (jT < JUMPLENGTH && !isfalling)
					jframe = (int)Math.floor((double)(jT)/JUMPLENGTH * 8 + 0.03);
				else
					jframe = 7;
				}
			repaint();
			
			if (screen > 1){
				if(wait <= 0){
					wait = 0;
					platformsT.add(pType);
					
					if (pType == 3)
						platformsX.add(0);
					else
						platformsX.add((int)(600 + speed*10));
					platformsY.add(height);
					wait += waitTimes[pType];
					pType = (int)(Math.random()*3);
					lHeight = platformsY.get(platformsY.size()-1);
					maxT = Math.max((int)(lHeight - JUMPHEIGHT),MAXH);
					minT = Math.min((int)(lHeight + JUMPHEIGHT),MINH);
					height = (int)(Math.random()*(minT-maxT))+maxT;
					maxD = (int)(speed * VX * (  ( V + (int)Math.sqrt(V*V - 2*G*height) ) / G  ) );
					wait += (int)(Math.random()*maxD);
					
				}
				wait -= speed*10;
				
				for(int i = 0; i < platformsX.size(); i++){
					if (platformsX.get(i) < -2000){
						platformsT.remove(i);
						platformsX.remove(i);
						platformsY.remove(i);
						i--;
					}
					else{
						platformsX.set(i,(int)(platformsX.get(i) - speed*10));
					}
				}
			}
			
				isPlatform = false;
				for (int i = 0; i < platformsX.size(); i++){
					x2 = platformsX.get(i);
					t = waitTimes[platformsT.get(i)];
					if (t == 0)
						t = 600;
					if (MANX + 53 >= x2 && MANX <= x2 + t){ //The + 52 is there to offest the fact that MANX is the left side of the character.
						yminT = platformsY.get(i);
						isPlatform = true;
						break;
					}	
				}	
				
				if(!isPlatform){
					yminT = 800;
				}

			if (isjump) {

				if (y < yminT)
					isjump = true;
				else{
					if (ylast - 15 <= yminT){ //15 is just an arbitrary number to make the hitbox feel more like the character itself
						isjump = false;
						ymin = yminT;
						y = yminT;
					}
					else
						isjump = true;
				}
				
				
				ylast = y;
			}
			if (!isjump && y != yminT  && !isfalling && screen > 1){
				isfalling = true;
				isjump = true;
				ymin = y;
				jump = count;
			}
			
			if (!isjump && isfalling)
				isfalling = false;
			
			//Ends the game if you fall below the screen
			if (y > 700){
				endGame();
			}
			
		}

		
		
		//Buttons
		else{
			JButton b= (JButton)e.getSource();	   
			if (b.getText()=="Jump" && !isjump && screen > 1){
			    isjump = true;
				jump = count;
			}
			else if (b.getText()=="Pause/Continue"){
				if (isPaused){
					myTimer.start();
					isPaused = false;
				}
				else{
					myTimer.stop();
					isPaused = true;
				}
			}
			else if (b.getText()=="Exit"){
				System.exit(0);
			}
		}	   
    }
	
	//Resets variables after you die
	public void endGame(){
		screen = -1;
		pType = 3;
		wait = 0;
		height = 212;
		ymin = 212;
		y = ymin;
		yminT = ymin;
		ylast = y;
		speed = 1.4;
		platformsT.clear();
		platformsX.clear();
		platformsY.clear();
		isjump = false;
		if (firstLast){
			count = 0;
			if (score > hScore)
				hScore = score;
			scoreMsg = "Score: " + score + " High Score: " + hScore;
			firstLast = false;
		}
	}
	// end actionPerformed
	
	
	public void paintComponent(Graphics gr){  // painting
		super.paintComponent(gr);
		
		gr.setColor(Color.pink);
		gr.setFont(f1);
		
		//Title screen
		if (screen == 0){
			gr.drawImage(background.getImage(),600-(count+1656)*5%3312, 0, null );
			gr.drawImage(background.getImage(),600-count*5%3312, 0, null );
			gr.setColor(Color.white);
			gr.setFont(f1);
			gr.drawString("KRAKATOA",0,100);
			gr.setFont(f2);
			gr.drawString("West of Java",0,150);
			if (count%10 < 5){
				gr.setColor(Color.blue);
				gr.drawString("Press enter to start",0,500);
			}
		}
		
		//Instructions/story screen
		else if (screen == 1) {
			gr.setColor(Color.black);
			gr.setFont(f4);
			gr.drawString("Instructions:", 10, 450);
			gr.setFont(f3);
			gr.drawString("Press enter to skip.", 10, 420);
			gr.drawString("Press the up key to jump.", 10, 470);
			gr.drawString("Try to survive for as long as you can!", 10, 490);
			for (int i = 0; i < story.length; i++){
				if (count >= i*48){
					if (count >= (i+1)*48)
						gr.drawString(story[i], 0, 20*(i+1));
					else
						gr.drawString(story[i].substring(0,count%48), 0, 20*(i+1));
				}
			}
		}
		
		//Death screen
		else if (screen == -1){
			gr.setColor(Color.black);
			gr.setFont(f1);
			
			if (count < deathMsg.length())
				gr.drawString(deathMsg.substring(0,count),0,250);
			else
				gr.drawString(deathMsg,0,250);
			gr.setFont(f2);
			if (count >= deathMsg.length()){
				if (count-deathMsg.length() < enterMsg.length())
					gr.drawString(enterMsg.substring(0,count-deathMsg.length()),0,300);
				else
					gr.drawString(enterMsg,0,300);
			}
			gr.setFont(f3);
			if (count >= deathMsg.length() + enterMsg.length()){
				if (count - deathMsg.length() - enterMsg.length() + 4 < scoreMsg.length())
					gr.drawString(scoreMsg.substring(0,count-scoreMsg.length()),0,330);
				else
					gr.drawString(scoreMsg,0,330);
			}
			
			
		}
		else{
			gr.drawImage(background.getImage(),(int)Math.floor(600-(count*speed/2+1656)*5%3312), 0, null );
			gr.drawImage(background.getImage(),(int)Math.floor(600-count*speed/2*5%3312), 0, null );
			gr.setColor(Color.blue);
			for(int i = 0; i < platformsX.size(); i++)
				gr.drawImage(platformSprites[platformsT.get(i)].getImage(), platformsX.get(i), platformsY.get(i), null);
			gr.setFont(f1);
			if (!isjump)
				gr.drawImage(jogger[count%8+1].getImage(),120,y-88,null);
			else
				gr.drawImage(jumping[jframe].getImage(),120,y-88,null);
			gr.setColor(Color.white);
			gr.setFont(f4);
			gr.drawString("Score: " + score,0,520);
			if (score < hScore)
				gr.drawString("High Score: " + hScore, 378, 515);
			else
				gr.drawString("High Score: " + score, 378, 515);
		}
	}
}