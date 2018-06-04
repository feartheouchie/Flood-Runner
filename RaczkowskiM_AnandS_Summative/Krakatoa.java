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
	//int jumpSprite = 0;
	int y = 0;
	int JUMPLENGTH = 20; //Jump is 20 frames long
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
	
	int MAXHEIGHT;
	int MINHEIGHT = 525;
	double G = 2;
	double V = 20;
	double JUMPHEIGHT = (V*V)/(2*G);
	int VX = speed*10
	
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
		new ImageIcon("Platform1.png")
	};
	
	private Timer myTimer= new Timer( 60, this );
	 
	public  MyPanel() { 			//initial all the variables

           // Constructor: set background color to white set up listeners to respond to mouse actions

		 
         setBackground(new Color(248,236,194));				 
		 addKeyListener(this);	
         x=0;
		myTimer.start();
	}				 
    public void keyPressed( KeyEvent ev ) {
		if (ev.getKeyCode()==38 && y==0){
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
				y = (int)(V*(x-jump) - 0.5*G*Math.pow((x-jump), 2));
				jframe = (int)Math.floor((double)(x-jump)/JUMPLENGTH * 8 + 0.03);
				
			if (y < 0){
				isjump = false;
				y = 0;
			}
		}
		// timer events
        repaint();		
		}
		
		//Buttons
		else{
			JButton b= (JButton)e.getSource();	   
			if (b.getText()=="Jump" && y == 0){
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
		else{
			gr.drawImage(pic.getImage(),(int)Math.floor(600-(x*speed/2+1656)*5%3312), 0, null );
			gr.drawImage(pic.getImage(),(int)Math.floor(600-x*speed/2*5%3312), 0, null );
			gr.setColor(Color.blue);
			//gr.fillRect(600-x*10%720,300,120,60);		 Not needed right now
			gr.drawImage(platformSprites[0].getImage(),(int)Math.floor(600-x*speed*10%841), 300, null);
			gr.setFont(f1);
			if (y == 0)
				gr.drawImage(jogger[x%8+1].getImage(),120,212-y,null);
			else
				gr.drawImage(jumping[jframe].getImage(),120,212-y,null);
		}

	}
}