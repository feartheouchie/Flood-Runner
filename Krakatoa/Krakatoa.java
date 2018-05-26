//Shashank Anand & Michael Raczkowski

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Krakatoa extends JFrame {
  // private JLabel lbl[][] = new JLabel[10][10];       // number of buttons - can be changed

   private Container c;
   private MyPanel mainPanel = new MyPanel();
   private JPanel southPanel = new JPanel();
   private JButton actionBtn1= new JButton("Jump");     //can change Jump   label on action buttons
   private JButton actionBtn2 = new JButton("Pause/Continue");      //can change Pause/Continue   label on action buttons
   private JButton exit = new JButton("Exit");          //label on action buttons


   public Krakatoa()   {
      super( "Button Template" );

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
 class MyPanel extends JPanel implements ActionListener, KeyListener {
	//variables - they are all global
	int x;
	int jump = 0;
	int y = 0;
	boolean isjump = false;
	boolean isPaused = false;
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
		new ImageIcon("Jogging13.png"),
	 };
	 
	ImageIcon [] jumping = {
		new ImageIcon("Jumping01.png"),
		new ImageIcon("Jumping02.png"),
		new ImageIcon("Jumping03.png"),
		new ImageIcon("Jumping04.png"),
		new ImageIcon("Jumping05.png"),
	};
	 
	 
	private Timer myTimer= new Timer( 60, this );
	 
	public  MyPanel() { 			//initial all the variables

           // Constructor: set background color to white set up listeners to respond to mouse actions
         setBackground(new Color(250,100,10));				 
		 addKeyListener(this);	
         x=0;
		myTimer.start();
	}				 
    public void keyPressed( KeyEvent ev ) {
		//System.out.println(ev.getKeyCode() ); Do we still need this?
		if (ev.getKeyCode()==38 && y==0){
			isjump = true;
			jump = x;
		}	
	}	
    public void keyReleased( KeyEvent e ){		
	}
    public void keyTyped( KeyEvent e ){}
	
    public void actionPerformed( ActionEvent e ){ 
        if (e.getSource()==myTimer){
		   x++;	
		   if (isjump){
			y = 20*(x-jump) - (x-jump)*(x-jump);
			if (y < 0){
				isjump = false;
				y = 0;
			}
		}
		// timer events
        repaint();		
		}
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
		

		gr.drawImage(pic.getImage(),600-(x+1656)*5%3312, 0, null );
		gr.drawImage(pic.getImage(),600-x*5%3312, 0, null );
		gr.setColor(Color.blue);
		gr.fillRect(600-x*10%720,300,120,60);
		gr.setColor(Color.yellow);
		if (y == 0)
			gr.drawImage(jogger[x%8+1].getImage(),120,212-y,null);
		else
			gr.drawImage(jumping[0].getImage(),120,212-y,null);

	}
}