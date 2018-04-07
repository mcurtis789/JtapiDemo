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
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.telephony.Address;
import javax.telephony.Provider;
import javax.telephony.ProviderObserver;
import javax.telephony.Terminal;
import javax.telephony.TerminalObserver;
import javax.telephony.events.ProvEv;
import javax.telephony.events.ProvInServiceEv;
import javax.telephony.events.TermEv;

import com.cisco.cti.util.Condition;
import com.ccg.jtapi.client.KeyPress;
import com.ccg.jtapi.client.StopSignal;
import com.ccg.jtapi.client.BuildXML;
import com.ccg.jtapi.client.BuildXMLByModel;
import com.cisco.jtapi.extensions.CiscoJtapiVersion;
import com.cisco.jtapi.extensions.CiscoTermInServiceEv;
import com.cisco.jtapi.extensions.CiscoTermOutOfServiceEv;
import com.cisco.jtapi.extensions.CiscoTerminal;

public class KeyPadPanel extends JPanel
{

	private KeyPadListener kpl =new KeyPadListener();
	private JPanel panelPhonePadPanel = new JPanel(new GridLayout(1,1));//new GridLayout(2,2)
	private JPanel panelkeypad = new JPanel(new GridLayout(4,3));
	private JPanel panelkeypadaux = new JPanel(new GridLayout(7,1));
	
	private String keyCmd[] = new String[2];

	private JButton keypadb1 = new JButton("1");
	private JButton keypadb2 = new JButton("2");
	private JButton keypadb3 = new JButton("3");
	private JButton keypadb4 = new JButton("4");
	private JButton keypadb5 = new JButton("5");
	private JButton keypadb6 = new JButton("6");
	private JButton keypadb7 = new JButton("7");
	private JButton keypadb8 = new JButton("8");
	private JButton keypadb9 = new JButton("9");
	private JButton keypadb0 = new JButton("0");
	private JButton keypadbs = new JButton("*");
	private JButton keypadbp = new JButton("#");
	
	private JButton keypadInfo = new JButton("Info");
	private JButton keypadAppmenu = new JButton("AppMenu");
	private JButton keypadNavDwn = new JButton("NavDown");
	private JButton keypadNavUp = new JButton("NavUp");
	private JButton keypadNavRight = new JButton("NavRight");
	private JButton keypadNavLeft = new JButton("NavLeft");
	private JButton keypadHold = new JButton("Hold");
	private JButton keypadSettings = new JButton("Settings");
	private JButton keypadServices = new JButton("Services");
	private JButton keypadDirectories = new JButton("Directories");
	private JButton keypadMessages = new JButton("Messages");
	private JButton keypadMute = new JButton("Mute");
	
	private JButton keypadbSpeaker = new JButton("Speaker");
	private JButton keypadbDial = new JButton("Dial");
	
	public JTextField Ext = new JTextField("2485551212");    
	
	private String REFRESH="REFRESH";
	private String USERNAME="";
	private String PASSWORD="";
	private String DEVICEMODEL;
	private String DEVICEIPADDRESS;
	
	private BuildXMLByModel bxm = new BuildXMLByModel();
	
	public static final int OUT_OF_SERVICE=0;
	public static final int IN_SERVICE=1;
	private Address destAddress;
	private CiscoTerminal observedTerminal;
	private boolean addressInService;
	private boolean terminalInService;
	protected int state = OUT_OF_SERVICE;
	Condition conditionInService = new Condition ();
	Provider provider;
	StopSignal	stopSignal;

	
	public KeyPadPanel () {
		
		setLayout(new GridLayout(2, 2));
		setBorder(BorderFactory.createTitledBorder("Keypad"));

		keypadb1.setActionCommand("1");
		keypadb1.addActionListener(kpl);

		keypadb2.setActionCommand("2");
		keypadb2.addActionListener(kpl);
  	  
		keypadb3.setActionCommand("3");
		keypadb3.addActionListener(kpl);

		keypadb4.setActionCommand("4");
		keypadb4.addActionListener(kpl);

		keypadb5.setActionCommand("5");
		keypadb5.addActionListener(kpl);

		keypadb6.setActionCommand("6");
		keypadb6.addActionListener(kpl);

		keypadb7.setActionCommand("7");
		keypadb7.addActionListener(kpl);

		keypadb8.setActionCommand("8");
		keypadb8.addActionListener(kpl);

		keypadb9.setActionCommand("9");
		keypadb9.addActionListener(kpl);
  	  
		keypadb0.setActionCommand("0");
		keypadb0.addActionListener(kpl);

		keypadbs.setActionCommand("Star");
		keypadbs.addActionListener(kpl);

		keypadbp.setActionCommand("Pound");
		keypadbp.addActionListener(kpl);

		keypadbDial.setActionCommand("Dial");
		keypadbDial.addActionListener(kpl);
		
		keypadbSpeaker.setActionCommand("Speaker");
		keypadbSpeaker.addActionListener(kpl);
  	  
		keypadInfo.setActionCommand("Info");
		keypadInfo.addActionListener(kpl);
		
		keypadSettings.setActionCommand("Settings");
		keypadSettings.addActionListener(kpl);
		
		keypadNavDwn.setActionCommand("NavDwn");
		keypadNavDwn.addActionListener(kpl);
		
		keypadNavUp.setActionCommand("NavUp");
		keypadNavUp.addActionListener(kpl);
		
		keypadNavRight.setActionCommand("NavRight");
		keypadNavRight.addActionListener(kpl);
		
		keypadNavLeft.setActionCommand("NavLeft");
		keypadNavLeft.addActionListener(kpl);
		
		keypadHold.setActionCommand("Hold");
		keypadHold.addActionListener(kpl);
	
		keypadServices.setActionCommand("Services");
		keypadServices.addActionListener(kpl);
		
		keypadDirectories.setActionCommand("Directories");
		keypadDirectories.addActionListener(kpl);
		
		keypadMessages.setActionCommand("Messages");
		keypadMessages.addActionListener(kpl);
		
		keypadMute.setActionCommand("Mute");
		keypadMute.addActionListener(kpl);
		
//		keypadsoft1       
//		keypadsoft2       
//		keypadsoft3       
//		keypadsoft4       
//		keypadsoft5       
//		keypadInfo        
//		keypadSettings     
//		keypadNavDwn      
//		keypadNavUp       
//		keypadHold        
//		keypadSettings    
//		keypadServices    
//		keypadDirectories 
//		keypadMessages    
//		keypadMute        
		
		panelkeypad.add(keypadb1);
	    panelkeypad.add(keypadb2);
	  	panelkeypad.add(keypadb3);
	  	panelkeypad.add(keypadb4);
	  	panelkeypad.add(keypadb5);
	  	panelkeypad.add(keypadb6);
	  	panelkeypad.add(keypadb7);
	  	panelkeypad.add(keypadb8);
	  	panelkeypad.add(keypadb9);
	  	panelkeypad.add(keypadbs);
	  	panelkeypad.add(keypadb0);
	  	panelkeypad.add(keypadbp);

	  	panelkeypadaux.add(new JLabel("Number to dial"));
	  	panelkeypadaux.add(Ext); 
	  	panelkeypadaux.add(keypadbDial);
	  	panelkeypadaux.add(keypadbSpeaker);
	  	panelkeypadaux.add(keypadMute);
	  	panelkeypadaux.add(keypadHold);
	  	panelkeypadaux.add(keypadMessages);
	  	panelkeypadaux.add(keypadSettings);
	  	panelkeypadaux.add(keypadNavUp);
	  	panelkeypadaux.add(keypadNavDwn);
	  	panelkeypadaux.add(keypadNavLeft);
	  	panelkeypadaux.add(keypadNavRight);
	  	
	  	GridBagConstraints c = new GridBagConstraints();	

	  	// Create the radio buttons.
	  	

//	  	c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.FIRST_LINE_START;
	  	
//	  	c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.LINE_START;
		
//	  	c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.LINE_END;
		
//	  	add(panelPhonePadPanel);
	  	add(panelkeypad);
	  	add(panelkeypadaux);
	  	
	  	setVisible(false);
	}
	
	private class KeyPadListener implements ActionListener  {
		public void actionPerformed(ActionEvent e) {
			ArrayList keyCmdList = new ArrayList();
			if (e.getActionCommand()=="0"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
			}
			if (e.getActionCommand()=="1"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
			}
			if (e.getActionCommand()=="2"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
			}
			if (e.getActionCommand()=="3"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
	    	}
			if (e.getActionCommand()=="4"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
	    	}
			if (e.getActionCommand()=="5"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
	    	}
			if (e.getActionCommand()=="6"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
	    	}
			if (e.getActionCommand()=="7"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
	    	}
			if (e.getActionCommand()=="8"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
	    	}
			if (e.getActionCommand()=="9"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
	   		}
	   		if (e.getActionCommand()=="0"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
	   		}
    		if (e.getActionCommand()=="Star"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
	   		}
    		if (e.getActionCommand()=="Pound"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:KeyPad", e.getActionCommand()));
    		}
    		if (e.getActionCommand()=="Speaker"){
			    keyCmdList.add( BuildXML.createKeyCmd("Key:",e.getActionCommand()));
	   		}
    		if (e.getActionCommand()=="Dial"){
    			keyCmdList.add(BuildXML.createKeyCmd("Dial:", Ext.getText()));
    		}
    		if (e.getActionCommand()=="Info"){
    			keyCmdList.add( BuildXML.createKeyCmd("Key:", e.getActionCommand()));
    		}
    		if (e.getActionCommand()=="Settings"){
    			keyCmdList = bxm.settings(observedTerminal,provider, e.getActionCommand());
    		}
    		if (e.getActionCommand()=="NavDwn"){
    			keyCmdList.add( BuildXML.createKeyCmd("Key:", e.getActionCommand()));
    		}
    		if (e.getActionCommand()=="NavUp"){
    			keyCmdList.add( BuildXML.createKeyCmd("Key:", e.getActionCommand()));
    		}
    		if (e.getActionCommand()=="NavRight"){
    			keyCmdList.add( BuildXML.createKeyCmd("Key:", e.getActionCommand()));
    		}
    		if (e.getActionCommand()=="NavLeft"){
    			keyCmdList.add( BuildXML.createKeyCmd("Key:", e.getActionCommand()));
    		}
    		if (e.getActionCommand()=="Hold"){
    			keyCmdList =bxm.hold(observedTerminal,provider, e.getActionCommand());
    		}
    		if (e.getActionCommand()=="AppMenu"){
    			keyCmdList.add( BuildXML.createKeyCmd("Key:", e.getActionCommand()));
    		}
    		if (e.getActionCommand()=="Services"){
    			keyCmdList =bxm.services(observedTerminal,provider, e.getActionCommand());
    		}
    		if (e.getActionCommand()=="Directories"){
    			keyCmdList.add( BuildXML.createKeyCmd("Key:", e.getActionCommand()));
    		}
    		if (e.getActionCommand()=="Messages"){
    			keyCmdList.add( BuildXML.createKeyCmd("Key:", e.getActionCommand()));
    		}
    		if (e.getActionCommand()=="Mute"){
    			keyCmdList.add( BuildXML.createKeyCmd("Key:", e.getActionCommand()));
    		}
    		try {
	    		for (int i=0;keyCmdList.size()>i;i++){
	    			new KeyPress(provider,observedTerminal,keyCmdList.get(i).toString());
	    			Thread.sleep(200);					
	    		}
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
    		
		}
	}
	
    public void setUserName(String v){
    	this.USERNAME= v;
    }
    public void setPassword(String v){
    	this.PASSWORD=v;
    }
    public void setModel(String v){
    	this.DEVICEMODEL=v;
    }
    public void setDeviceIP(String v){
    	this.DEVICEIPADDRESS=v;
    }
    public void setTerminal(CiscoTerminal observedTerminal){
		this.observedTerminal =observedTerminal;
    }
    public void setProvider(Provider provider){
		this.provider =provider;
    }
    
}
