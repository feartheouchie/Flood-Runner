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
  // private JLabel lbl[][] = new JLabel[10][10];       // number of buttons - can be changed

   private Container c;
   private MyPanel mainPanel = new MyPanel();
   private JPanel southPanel = new JPanel();
   private JButton actionBtn1= new JButton("Jump");     //can change Jump   label on action buttons
   private JButton actionBtn2 = new JButton("Pause/Continue");      //can change Pause/Continue   label on action buttons
   private JButton exit = new JButton("Exit");          //label on action buttons


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
	int x;
	int jump = 0;
	int JUMPLENGTH = 20;
	boolean isjump = false;
	boolean isPaused = false;
	int screen = 0;
	double speed = 1;
	String storyline = "The year is 1883. You are a Dutch colonist in   #search of precious jewels. The natives of the   #island of Java had told you not to enter        #KRAKATOA, but in your folly and greed, you had  #decided to ignore them. Now, the volcano is     #erupting, and you must escape before it is too  #late. Your chances do not look good...          #Press the up key to jump.                       #Press enter to begin.                           ";
	String story[] = storyline.split("#");
	Font  f1  = new Font(Font.MONOSPACED, Font.BOLD,  100);
	Font f2 = new Font(Font.MONOSPACED, Font.BOLD, 40);
	Font f3 = new Font(Font.MONOSPACED, Font.PLAIN, 20);
	int jframe = 0;
	int MAXHEIGHT = 100;
	int MINHEIGHT = 525;
	double G = 2;
	double V = 20;
	double JUMPHEIGHT = (V*V)/(2*G);
	int VX = (int)(speed*10);
	int[] waitTimes = new int[]{241,241,241,241,0};
	int lHeight;
	int pType = 4; //Long platform
	int wait = 0; 
	int height = 212; //Elevation of the special platform
	int MINH = 450;
	int MAXH = 100;
	int minT;
	int maxT;
	int maxD;
	
	
	int x2 = 0;
	int MANX = 163;
	int ymin = 212;
	int y = ymin;
	int yminT = ymin;
	int ylast = y;
	boolean isPlatform = false;
	int t = 0;
	boolean isfalling = false;
	int jT = 0;
	
	int[] tempList = new int[3];
	ImageIcon pic = new ImageIcon("background2.gif");
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
		new ImageIcon("Platform1.png"),
		new ImageIcon("Platform1.png"),
		new ImageIcon("Platform1.png"),
		new ImageIcon("PlatformL.png")
	};
	ArrayList <Integer> platformsT = new ArrayList<Integer>();
	ArrayList <Integer> platformsX = new ArrayList<Integer>();
	ArrayList <Integer> platformsY = new ArrayList<Integer>();

	
	private Timer myTimer= new Timer( 60, this );
	 
	public  MyPanel() { 			//initial all the variables

           // Constructor: set background color to white set up listeners to respond to mouse actions

		 
         setBackground(new Color(248,236,194));				 
		 addKeyListener(this);	
         x=0;
		myTimer.start();
	}				 
    public void keyPressed( KeyEvent ev ) {
		if (ev.getKeyCode()==38 && !isjump){
			isjump = true;
			jump = x;
		}

		if (ev.getKeyCode()==10 && screen < 2){
			screen += 1;
			x = 0;
		}
	}	
    public void keyReleased( KeyEvent e ){		
	}
    public void keyTyped( KeyEvent e ){}
	
    public void actionPerformed( ActionEvent e ){ 
        if (e.getSource()==myTimer){
			x++;
		    if (isjump){
				jT = x-jump;
				if (isfalling)
					jT += JUMPLENGTH;
				y = (int)(0.5*G*Math.pow((jT), 2) - V*(jT) + ymin);
				if (jT < JUMPLENGTH)
					jframe = (int)Math.floor((double)(jT)/JUMPLENGTH * 8 + 0.03);
				else
					jframe = 7;
				}
		// timer events
			repaint();

			if (screen > 1){
				if(wait <= 0){
					wait = 0;
					platformsT.add(pType);
					
					if (pType == 4)
						platformsX.add(0);
					else
						platformsX.add((int)(600 + speed*10));
					platformsY.add(height);
					wait += waitTimes[pType];
					pType = (int)(Math.random()*4);
					lHeight = platformsY.get(platformsY.size()-1);
					maxT = Math.max((int)(lHeight - JUMPHEIGHT),MAXH);
					minT = Math.min((int)(lHeight + JUMPHEIGHT),MINH);
					height = (int)(Math.random()*(minT-maxT))+maxT;
					maxD = (int)(VX * (  ( V + (int)Math.sqrt(V*V - 2*G*height) ) / G  ) );
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
					if (MANX >= x2 && MANX <= x2 + t){
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
					if (ylast <= yminT){
						isjump = false;
						ymin = yminT;
						y = yminT;
					}
					else
						isjump = true;
				}
				
				
				ylast = y;
			}
			if (!isjump && y != yminT && screen > 1 && !isfalling){
				isfalling = true;
				isjump = true;
				ymin = y;
				jump = x;
			}
			
			if (!isjump && isfalling)
				isfalling = false;
			
			
			if (y > 700){
				screen = -1;
				isjump = false;
			}
			
			
			
		}



		
		
		//Buttons
		else{
			JButton b= (JButton)e.getSource();	   
			if (b.getText()=="Jump" && !isjump){
			    isjump = true;
				jump = x;
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
	
	
	// end actionPerformed
	
	
	public void paintComponent(Graphics gr){  // painting
		super.paintComponent(gr);
		
		gr.setColor(Color.pink);
		gr.setFont(f1);
		
		if (screen == 0){
			gr.drawImage(pic.getImage(),600-(x+1656)*5%3312, 0, null );
			gr.drawImage(pic.getImage(),600-x*5%3312, 0, null );
			gr.setColor(Color.white);
			gr.setFont(f1);
			gr.drawString("KRAKATOA",0,100);
			gr.setFont(f2);
			gr.drawString("West of Java",0,150);
			if (x%10 < 5){
				gr.setColor(Color.blue);
				gr.drawString("Press enter to start",0,500);
			}
		}
		
		else if (screen == 1) {
			gr.setColor(Color.black);
			gr.setFont(f3);
			gr.drawString("Press enter to skip.", 10, 500);
			for (int i = 0; i < story.length; i++){
				if (x >= i*48){
					if (x >= (i+1)*48)
						gr.drawString(story[i], 0, 20*(i+1));
					else
						gr.drawString(story[i].substring(0,x%48), 0, 20*(i+1));
				}
			}
		}
		
		else if (screen == -1){
			gr.setColor(Color.black);
			gr.drawString("You died",0,300);
		}
		else{
			gr.drawImage(pic.getImage(),(int)Math.floor(600-(x*speed/2+1656)*5%3312), 0, null );
			gr.drawImage(pic.getImage(),(int)Math.floor(600-x*speed/2*5%3312), 0, null );
			gr.setColor(Color.blue);
			for(int i = 0; i < platformsX.size(); i++)
				gr.drawImage(platformSprites[platformsT.get(i)].getImage(), platformsX.get(i), platformsY.get(i), null);
			gr.setFont(f1);
			if (y == ymin)
				gr.drawImage(jogger[x%8+1].getImage(),120,y-88,null);
			else
				gr.drawImage(jumping[jframe].getImage(),120,y-88,null);
		}

	}
}