package dev_vlcj;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
//import java.awt.Point;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;


public class newMultiVideo extends JFrame implements ActionListener {
   
	static int Num_Video = 16;
	int Video_Width = 200;
    int Video_Height = 150;
    
    private JPanel topPanel = new JPanel();
    private JPanel statusPanel = new JPanel();
    private JButton playButton = new JButton("Play");
    private JButton btnStop = new JButton("Stop");
    private JButton btnPause = new JButton("Pause");
    private JButton btnresize=new JButton("Resize");
  //  private MediaPlayerFactory factory[]=new MediaPlayerFactory[Num_Video];
  //  private EmbeddedMediaPlayer mediaPlayer[]=new EmbeddedMediaPlayer[Num_Video];
  //  Canvas[] videoCanvas = new Canvas[Num_Video];
    JPanel[] vidPanel = new JPanel[Num_Video];
    private EmbeddedMediaPlayerComponent mediaPlayerComponent[]=new EmbeddedMediaPlayerComponent[Num_Video];
    static String links[]=new String[Num_Video];		
    
   static void setlinks(){
    	 for(int i=0;i<Num_Video;i++){
    	    	links[i]="http://localhost:8080/test";
    	    }

    }
     
    

   
    public newMultiVideo() {
   
    	NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),"C:/Program Files/VideoLAN/VLC/" );
    	Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
    	
    /*	btnresize.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			mouseClicked(null);
    		}
    	});
    */
           
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        btnStop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               stop();
            }
        });

        btnPause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               pause();
            }
        });


        topPanel.add(playButton);
        topPanel.add(btnStop);
        topPanel.add(btnPause);
        topPanel.add(btnresize);

        
        
        this.setSize(1000, 800);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
                
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,1,1));
        

        

       for(int i=0;i<Num_Video;i++){
         mediaPlayerComponent[i] = new EmbeddedMediaPlayerComponent();
        
        // Add regular Java listeners, no native hook or global event listener functions...
        MouseHandler mouseHandler = new MouseHandler(links[i]);
        

        // You *must* do this...
        mediaPlayerComponent[i].getMediaPlayer().setEnableKeyInputHandling(false);
        mediaPlayerComponent[i].getMediaPlayer().setEnableMouseInputHandling(false);

        
        
        mediaPlayerComponent[i].getVideoSurface().addMouseListener(mouseHandler);
        mediaPlayerComponent[i].getVideoSurface().addMouseMotionListener(mouseHandler);
        mediaPlayerComponent[i].getVideoSurface().addMouseWheelListener(mouseHandler);

         // You must explicitly request focus to the video surface, this is one way to do it...
        // Clicking in the Canvas will *not* by default set focus (you could request focus in
        // response to a mouse-clicked event)
        mediaPlayerComponent[i].getVideoSurface().requestFocusInWindow();
        
        }

       for (int i = 0; i < Num_Video; i++) {
           vidPanel[i] = new JPanel();
           
           vidPanel[i].setPreferredSize(new Dimension(Video_Width, Video_Height));
           vidPanel[i].setBackground(Color.black);
           vidPanel[i].setLayout(new BorderLayout());

           
           vidPanel[i].add(mediaPlayerComponent[i],BorderLayout.CENTER);
           
           
           mainPanel.add(vidPanel[i]);
           

       }
  

        this.add(topPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(statusPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) throws IllegalStateException {

       if (e.getActionCommand().equals("OK")) {
          this.dispose();
         }
       
    }

    public void start() {
    	
        this.setVisible(true);
  
        /*   for(int i=0;i<Num_Video;i++){
        	System.out.println(vidPanel[i].getWidth()+" x "+vidPanel[i].getHeight());        	
        }
*/
        }

    private void play() {
       for(int i=0;i<Num_Video;i++){
    	   mediaPlayerComponent[i].getMediaPlayer().playMedia(links[i]);
    	try{ Thread.sleep(5);
    	}catch(Exception e){}}
    }
    private void stop() {
        for(int i=0;i<Num_Video;i++){
        	mediaPlayerComponent[i].getMediaPlayer().stop();
        }
    }

    private void pause() {
    	for(int i=0;i<Num_Video;i++){
    		mediaPlayerComponent[i].getMediaPlayer().pause();
        }
    }
        
  /*This is an unused Mouse Click Function
   *Still present,but doesn't help much. 
   *  private void mouseClicked(MouseEvent event)
	   {
	     if (event.getClickCount() == 2) {
	     System.out.println("double mouse click");
	      	     }
	   }
   */
    
    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	setlinks();
            	newMultiVideo mv=new newMultiVideo();
            	mv.start();
            }
        });
    }
}