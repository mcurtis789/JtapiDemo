package com.ccg.gui;

/*   
*  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
*  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
*  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JCRAFT,
*  INC. OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,
*  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
*  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
*  OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
*  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
*  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
*  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import javax.swing.*;
import javax.telephony.Provider;
import com.ccg.jtapi.client.GetTerminal;
import com.cisco.jtapi.extensions.CiscoTerminal;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;


public class Main extends JFrame {

	private CiscoTerminal observedTerminal;
	private Provider provider =null;
	private KeyPadPanel kpp= new KeyPadPanel();
	private ScreenShot ss = new ScreenShot();
		
	private EventActionListener ael =new EventActionListener();

	public JLabel label;
	public JLabel statusLabel=new JLabel("Status: Not Connected");
	
	public JTextField usernametxtf;
	public JPasswordField passwordtxtf;
	public JTextField servertxtf;
	public JTextField srctxtf;
	public JTextField desttxtf;
	
	private JButton startSession;
	private JButton endSession;
		
	private String USERNAME="";
	private String PASSWORD="";
	private String SERVER=null;
	private String SRC="";
	private String STARTSESSION="STARTSESSION";
	private String ENDSESSION="ENDSESSION";
	
	private boolean terminalInService;
	
	  /**
    The buildButtonPanel method builds the button panel.
 */   
	public static void main(String[] args) throws IOException{
		new Main();
	}

	/**
	 *Constructor
	 */

   public Main() throws IOException {      
	   JPanel panelcredentialsPanel = new JPanel(new GridBagLayout());//new GridLayout(6,2)
	   panelcredentialsPanel.setBorder(BorderFactory.createTitledBorder("Credentials"));
		try {
	    	  usernametxtf = new JTextField("jtapi"); 
	    	  passwordtxtf = new JPasswordField("");
	    	  servertxtf = new JTextField("");
	    	  srctxtf = new JTextField("5001");
	    	  desttxtf = new JTextField("5900");
	    	  
//	    	  FIRST_LINE_START 	PAGE_START 	FIRST_LINE_END
//	    	  LINE_START 		CENTER 		LINE_END
//	    	  LAST_LINE_START 	PAGE_END 	LAST_LINE_END
	    	  GridBagConstraints c;	
	    	  c = new GridBagConstraints();
	    	  c.anchor = GridBagConstraints.FIRST_LINE_START;
	    	  c.gridx = 0;
	    	  c.gridy = 0;
	    	  panelcredentialsPanel .add(new JLabel("Username"),c);    
	
	    	  c = new GridBagConstraints();
	    	  c.anchor = GridBagConstraints.FIRST_LINE_START;
	    	  c.gridx = 50;
	    	  c.gridy = 0;
	    	  c.gridwidth = 75;
	    	  c.fill = GridBagConstraints.HORIZONTAL;
	    	  panelcredentialsPanel.add(usernametxtf,c);
	    	  
	    	  c = new GridBagConstraints();
	    	  c.anchor = GridBagConstraints.FIRST_LINE_START;
	    	  c.gridx = 0;
	    	  c.gridy = 50;
	    	  panelcredentialsPanel.add(new JLabel("Password"),c);
	    	  
	    	  c = new GridBagConstraints();
	    	  c.anchor = GridBagConstraints.FIRST_LINE_START;
	    	  c.gridx = 50;
	    	  c.gridy = 50;
	    	  c.gridwidth = 75;
	    	  c.fill = GridBagConstraints.HORIZONTAL;
	    	  panelcredentialsPanel.add(passwordtxtf,c);
	    	  
	    	  c = new GridBagConstraints();
	    	  c.anchor = GridBagConstraints.LAST_LINE_START;
	    	  c.gridx = 0;
	    	  c.gridy = 100;
	    	  panelcredentialsPanel.add(new JLabel("Server"),c);
	    	  
	    	  c = new GridBagConstraints();
	    	  c.anchor = GridBagConstraints.FIRST_LINE_START;
	    	  c.gridx = 50;
	    	  c.gridy = 100;
	    	  c.gridwidth = 75;
	    	  c.fill = GridBagConstraints.HORIZONTAL;
	    	  panelcredentialsPanel.add(servertxtf,c);
	    	  
	    	  c = new GridBagConstraints();
	    	  c.anchor = GridBagConstraints.LAST_LINE_START;
	    	  c.gridx = 0;
	    	  c.gridy = 150;
	    	  panelcredentialsPanel.add(new JLabel("Source Phone Ext"),c);
	    	  
	    	  c = new GridBagConstraints();
	    	  c.anchor = GridBagConstraints.FIRST_LINE_START;
	    	  c.gridx = 50;
	    	  c.gridy = 150;
	    	  c.gridwidth = 75;
	    	  c.fill = GridBagConstraints.HORIZONTAL;
	    	  panelcredentialsPanel.add(srctxtf,c);
	    	  
	    	  c = new GridBagConstraints();
	    	  startSession = new JButton("Start Session");   
	    	  startSession.addActionListener(ael);
	    	  startSession.setActionCommand(STARTSESSION);
	    	  c.anchor = GridBagConstraints.LINE_START;
	    	  c.gridx = 0;
	    	  c.gridy = 200;
	    	  panelcredentialsPanel.add(startSession,c);
	    	  
	    	  c = new GridBagConstraints();
	    	  endSession = new JButton("End Session");   
	    	  endSession.addActionListener(ael);
	    	  endSession.setActionCommand(ENDSESSION);
	    	  c.anchor = GridBagConstraints.LINE_START;
	    	  c.gridx = 50;
	    	  c.gridy = 200;
	    	  panelcredentialsPanel.add(endSession,c);
	    	  
	    	  c = new GridBagConstraints();
	    	  c.anchor = GridBagConstraints.LAST_LINE_START;
	    	  c.gridx = 0;
	    	  c.gridy = 250;
	    	  c.fill = GridBagConstraints.HORIZONTAL;
	    	  c.gridwidth = 100;
	    	  panelcredentialsPanel.add(statusLabel,c);
	    	  
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }

	  // Display a title.
      setTitle("JTAPI Demo");
      
      // Specify an action for the close button.
      //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      addWindowListener(new java.awt.event.WindowAdapter() {
   	   @SuppressWarnings("static-access")
   	   @Override
   	   public void windowClosing(java.awt.event.WindowEvent windowEvent) {
   	    	if (provider!=null){
   	    		System.out.println("Stopping Provider");
   	    		provider.shutdown();
   	    	}  	
   	        System.exit(0);
   	    }
   		});

      // Create a BorderLayout manager.
      //setLayout(new BorderLayout());
	  setLayout(new GridBagLayout());
	  
	  GridBagConstraints m = new GridBagConstraints();	
		
      // Add the components to the content pane.
	  m.fill = GridBagConstraints.HORIZONTAL;
	  m.anchor = GridBagConstraints.FIRST_LINE_START;
	  m.gridx = 0;
	  m.gridy = 0;
	  add(panelcredentialsPanel, m);
	  
	  m.fill = GridBagConstraints.HORIZONTAL;
	  m.anchor = GridBagConstraints.PAGE_START;
	  m.gridx = 150;
	  m.gridy = 0;
	  add(ss,m);
	  
	  m.fill = GridBagConstraints.HORIZONTAL;
	  m.anchor = GridBagConstraints.FIRST_LINE_END;
	  m.gridx = 550;
	  m.gridy = 0;
	  add(kpp, m);
	  // Pack the contents of the window and display it.
      pack();
      setVisible(true);
   }

   private class EventActionListener implements ActionListener {

	   public void actionPerformed(ActionEvent e) {
    	  if (STARTSESSION==e.getActionCommand()){
    		  PASSWORD=String.valueOf(passwordtxtf.getPassword());
    		  //PASSWORD=passwordtxtf.getText();
    		  SERVER= servertxtf.getText();
    		  USERNAME=usernametxtf.getText();
    		  
    		  GetTerminal m = new GetTerminal();  
   	    	  
    		  m.setUsername(USERNAME);
    		  m.setPassword(PASSWORD);
    		  m.setServer(SERVER);
    		  m.setSource(SRC=srctxtf.getText());

    		  try{
    			  provider = m.setProvider();
    			  if (provider!=null){
    				  observedTerminal =m.setTerminal(provider);
         	    	  ss.setPassword(PASSWORD);
        	    	  ss.setUserName(USERNAME);
        	    	  ss.setProvider(provider);
        	    	  ss.setTerminal(observedTerminal);
        	    	  ss.updateProvider();

        	    	  kpp.setProvider(provider);
        	    	  kpp.setTerminal(observedTerminal);	    	  

        	   		  if (provider!=null){
            			  statusLabel.setText("Connected");
            	    	  kpp.setVisible(true);
            	    	  ss.setVisible(true);
            	    	  revalidate();
        				  repaint();  
        				  pack();
            	      }

    			  }
    		  }catch (Exception ex) {
    			  String message[]=ex.toString().split("--");
    			  try{
	    			  if (Arrays.asList(message).size()>1){
	    				  if(Arrays.asList(message).get(1).contains("bad login")){
		    				  statusLabel.setText("Status: "+message[1]);
		    				  revalidate();
		    				  repaint();
		    				  pack();
		    			  }else{
		    				  statusLabel.setText("Status: "+Arrays.asList(message).get(1));
		    				  revalidate();
		    				  repaint();
		    				  pack();
		    			  }
	    			  }else{
	    				  if(Arrays.asList(message).get(0).contains("not in provider's domain")){
		    				  statusLabel.setText("Status: Not Controlled By User");
		    				  revalidate();
		    				  repaint();
		    				  pack();
	    				  }else{
	    					  statusLabel.setText("Status: "+message[0]);
		    				  revalidate();
		    				  repaint();
		    				  pack();
	    				  }
	    			  }
    			  }catch(Exception exx){
    				  statusLabel.setText("Status: "+exx.toString());
    				  revalidate();
    				  repaint();
    				  pack();
    			  }
    		  }
    		  

    	  }if(ENDSESSION==e.getActionCommand()){
    		  if (provider!=null){
    			  if(ss.cb.isSelected()){
    				  ss.stopSS();
    			  }
    			  provider.shutdown();
        		  statusLabel.setText("Status: Not Connected");
        		  kpp.setVisible(false);
    	    	  ss.setVisible(false);
    			  revalidate();
    			  repaint();
    			  pack();
    		  }
    	  }	  
      }
   }
}

