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
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.Provider;

import com.ccg.http.Http;
import com.ccg.jtapi.client.DeviceType;
import com.ccg.jtapi.client.GetTerminal;
import com.cisco.jtapi.extensions.CiscoTerminal;

public class ScreenShot extends JPanel{
	
	private SoftKeyPadPanel skpp= new SoftKeyPadPanel();

	private EventActionListener ael =new EventActionListener();

	private CiscoTerminal observedTerminal;
	private Provider provider;

	private Image image;
	private JLabel lblimage;

	private String REFRESH="REFRESH";
	private String USERNAME="";
	private String PASSWORD="";

	
	public JCheckBox cb = new JCheckBox();
	private ScheduledExecutorService scheduledPool = null;

	public ScreenShot(){
		setBorder(BorderFactory.createTitledBorder("Phone Screen"));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();	
		setPreferredSize(new Dimension(400, 350));
		setVisible(false);

		cb.setText("Refresh");
		cb.addActionListener(ael);
		cb.setActionCommand(REFRESH);
		  
		image = null;
		lblimage=new JLabel(new ImageIcon(getScaledImage(image,400,240)));
		  
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		add(lblimage, c);
  	  
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.gridx = 0;
		c.gridy = 100;
		add(cb,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.gridx = 0;
		c.gridy = 150;
		
		add(skpp,c);

	}
	
	   private class EventActionListener implements ActionListener 
	   {
	      public void actionPerformed(ActionEvent e) 
	      {
	    	  if (REFRESH==e.getActionCommand()){
	    		  if (cb.isSelected()){
	    			  scheduledPool = Executors.newScheduledThreadPool(1);
	    			  scheduledPool.scheduleWithFixedDelay(refreshImage, 6, 6, TimeUnit.SECONDS);
	    		  }else{
	    			  scheduledPool.shutdown();
	    		  }
	    	  }
	      }
	   }
		Runnable refreshImage = new Runnable()
		{
			@Override
			public void run()
			{
				
				try {
					Http h = new Http();
					DeviceType dt = new DeviceType();
					h.setModel(dt.getPhoneType(observedTerminal));
					h.setPassword(PASSWORD);
					h.setUserName(USERNAME);
					image = h.GetImage(observedTerminal.getIPV4Address().getHostAddress().toString());
					lblimage.setIcon(new ImageIcon(getScaledImage(image,400,240)));  
				} catch (InvalidStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MethodNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};

	   private Image getScaledImage(Image srcImg, int w, int h){
		    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2 = resizedImg.createGraphics();

		    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g2.drawImage(srcImg, 0, 0, w, h, null);
		    g2.dispose();

		    return resizedImg;
		}
	    public void setUserName(String v){
	    	this.USERNAME= v;
	    }
	    public void setPassword(String v){
	    	this.PASSWORD=v;
	    }
	    public void setTerminal(CiscoTerminal observedTerminal){
			this.observedTerminal =observedTerminal;
	    }
	    public void setProvider(Provider provider){
			this.provider =provider;
	    }
	    public void stopSS(){
	    	scheduledPool.shutdown();
	    }
	    public void updateProvider(){
	    	try {
	    		skpp.setProvider(provider);
	    		skpp.setTerminal(observedTerminal);
	    		Http h = new Http();
	    		DeviceType dt = new DeviceType();
	    		h.setModel(dt.getPhoneType(observedTerminal));
	    		h.setPassword(PASSWORD);
	    		h.setUserName(USERNAME);
	    		image = h.GetImage(observedTerminal.getIPV4Address().getHostAddress().toString());
	    		lblimage.setIcon(new ImageIcon(getScaledImage(image,400,240)));  
			} catch (InvalidStateException | MethodNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
	    }
}
