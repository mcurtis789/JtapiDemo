package com.ccg.jtapi.client;
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
import java.util.ArrayList;

import javax.telephony.Provider;

import com.cisco.jtapi.extensions.CiscoTerminal;

public class Macro {
	
	private CiscoTerminal observedTerminal;
	private Provider provider;
	
	private BuildXMLByModel bxm = new BuildXMLByModel();

	public void setBackground88xx(int backgroundOptionX,int backgroundOptionY){
		ArrayList keyCmdList = new ArrayList();
		try {			
			keyCmdList = bxm.settings(observedTerminal,provider, "Settings");		
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "2"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "1"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Soft2"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Soft1"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Soft1"));
			try {
	    		for (int i=0;keyCmdList.size()>i;i++){
	    			Thread.sleep(6000);
	    			//TODO move through grid
	    			for (int x=0;backgroundOptionX>x;x++){
	    				for (int y=0;backgroundOptionY>y;y++){
	    					keyCmdList.add(BuildXML.createKeyCmd("Key:", "NavDwn"));
	    					new KeyPress(provider,observedTerminal,keyCmdList.get(y).toString());
	    	    			
	    				}
	    				keyCmdList.add(BuildXML.createKeyCmd("Key:", "NavRight"));
	    				new KeyPress(provider,observedTerminal,keyCmdList.get(x).toString());
		    			
	    			}
	    			new KeyPress(provider,observedTerminal,keyCmdList.get(i).toString());
	    			Thread.sleep(200);					
	    		}
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setBackground99xx(int backgroundOptionX,int backgroundOptionY){
		ArrayList keyCmdList = new ArrayList();
		try{
			keyCmdList = bxm.settings(observedTerminal, provider, "Settings");		
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "2"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "2"));
			keyCmdList.add(BuildXML.createKeyCmd("Soft:", "2"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Soft1"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Soft1"));
			try {
	    		for (int i=0;keyCmdList.size()>i;i++){
	    			Thread.sleep(6000);
	    			//TODO move through grid
	    			for (int x=0;backgroundOptionX>x;x++){
	    				for (int y=0;backgroundOptionY>y;y++){
	    					keyCmdList.add(BuildXML.createKeyCmd("Key:", "NavDwn"));
	    					new KeyPress(provider,observedTerminal,keyCmdList.get(y).toString());
	    				}
	    				keyCmdList.add(BuildXML.createKeyCmd("Key:", "NavRight"));
	    				new KeyPress(provider,observedTerminal,keyCmdList.get(x).toString());
	    			}
	    			new KeyPress(provider,observedTerminal,keyCmdList.get(i).toString());
	    			Thread.sleep(200);					
	    		}
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void eraseITL7911(){
		ArrayList keyCmdList = new ArrayList();
		try {			
			keyCmdList = bxm.settings(observedTerminal,provider, "Settings");		
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "3"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "4"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "5"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Star"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Star"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Pound"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Soft4"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Soft2"));
			try {
	    		for (int i=0;keyCmdList.size()>i;i++){
	    			new KeyPress(provider,observedTerminal,keyCmdList.get(i).toString());
	    			Thread.sleep(200);					
	    		}
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void eraseITL79xx(){
		ArrayList keyCmdList = new ArrayList();
		try {			
			keyCmdList = bxm.settings(observedTerminal,provider, "Settings");		
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "4"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "4"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "5"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "2"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Star"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Star"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Pound"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Soft4"));
			keyCmdList.add(BuildXML.createKeyCmd("Key:", "Soft2"));
			try {
	    		for (int i=0;keyCmdList.size()>i;i++){
	    			new KeyPress(provider,observedTerminal,keyCmdList.get(i).toString());
	    			Thread.sleep(200);					
	    		}
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void setTerminal(CiscoTerminal observedTerminal){
		this.observedTerminal =observedTerminal;
    }
    public void setProvider(Provider provider){
		this.provider =provider;
    }

}
