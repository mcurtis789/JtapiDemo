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
import com.cisco.jtapi.extensions.CiscoJtapiVersion;
import com.cisco.jtapi.extensions.CiscoTermInServiceEv;
import com.cisco.jtapi.extensions.CiscoTermOutOfServiceEv;
import com.cisco.jtapi.extensions.CiscoTerminal;

public class SoftKeyPadPanel extends JPanel
{

	private KeyPadListener kpl =new KeyPadListener();
	
	private String keyCmd ="";
	
	private JButton keypadsoft1 = new JButton("Soft 1");
	private JButton keypadsoft2 = new JButton("Soft 2");
	private JButton keypadsoft3 = new JButton("Soft 3");
	private JButton keypadsoft4 = new JButton("Soft 4");
	private JButton keypadsoft5 = new JButton("Soft 5");
	
	private String REFRESH="REFRESH";
	private String USERNAME="";
	private String PASSWORD="";
	private String DEVICEMODEL;
	private String DEVICEIPADDRESS;
	
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

	
	public SoftKeyPadPanel () {
		
//		setLayout(new GridLayout(2, 2));
		//setBorder(BorderFactory.createTitledBorder("Keypad"));
  	  
		keypadsoft1.setActionCommand("Soft1");
		keypadsoft1.addActionListener(kpl);
		
		keypadsoft2.setActionCommand("Soft2");
		keypadsoft2.addActionListener(kpl);
		
		keypadsoft3.setActionCommand("Soft3");
		keypadsoft3.addActionListener(kpl);
		
		keypadsoft4.setActionCommand("Soft4");
		keypadsoft4.addActionListener(kpl);
		
		keypadsoft5.setActionCommand("Soft5");
		keypadsoft5.addActionListener(kpl);
		
		
//		keypadsoft1       
//		keypadsoft2       
//		keypadsoft3       
//		keypadsoft4       
//		keypadsoft5       
//		keypadInfo        
//		keypadAppMenu     
//		keypadNavDwn      
//		keypadNavUp       
//		keypadHold        
//		keypadSettings    
//		keypadServices    
//		keypadDirectories 
//		keypadMessages    
//		keypadMute        
		
		add(keypadsoft1);
	    add(keypadsoft2);
	  	add(keypadsoft3);
	  	add(keypadsoft4);
	  	add(keypadsoft5);
	  	
	  	GridBagConstraints c = new GridBagConstraints();	

	  	// Create the radio buttons.
	  	

//	  	c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.FIRST_LINE_START;
	  	
//	  	c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.LINE_START;
		
//	  	c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.LINE_END;
		
//	  	add(panelPhonePadPanel);
	  	
	  	setVisible(true);
	}
	
	private class KeyPadListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
    		if (e.getActionCommand()=="Soft1"){
    			keyCmd = BuildXML.createKeyCmd("Key:", e.getActionCommand());
    		}
    		if (e.getActionCommand()=="Soft2"){
    			keyCmd = BuildXML.createKeyCmd("Key:", e.getActionCommand());
    		}
    		if (e.getActionCommand()=="Soft3"){
    			keyCmd = BuildXML.createKeyCmd("Key:", e.getActionCommand());
    		}
    		if (e.getActionCommand()=="Soft4"){
    			keyCmd = BuildXML.createKeyCmd("Key:", e.getActionCommand());
    		}
    		if (e.getActionCommand()=="Soft5"){
    			keyCmd = BuildXML.createKeyCmd("Key:", e.getActionCommand());
    		}
    		new KeyPress(provider,observedTerminal,keyCmd);
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
