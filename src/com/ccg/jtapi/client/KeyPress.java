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
import com.ccg.jtapi.client.*;
import com.ccg.jtapi.sample.Originator;
import com.ccg.jtapi.sample.TraceWindow;

import java.text.SimpleDateFormat;

/**
 * makecall.java
 * 
 * Copyright Cisco Systems, Inc.
 * 
 * Performance-testing application (first pass) for Cisco JTAPI
 * implementation.
 * 
 * Known problems:
 * 
 * Due to synchronization problems between Actors, calls may
 * not be cleared when this application shuts down.
 * 
 */

//import com.ms.wfc.app.*;

import java.util.*;
import javax.telephony.*;
import javax.telephony.events.*;
import com.cisco.cti.util.Condition;
import com.cisco.jtapi.extensions.CiscoJtapiVersion;
import com.cisco.jtapi.extensions.CiscoTermInServiceEv;
import com.cisco.jtapi.extensions.CiscoTermOutOfServiceEv;
import com.cisco.jtapi.extensions.CiscoTerminal;


public class KeyPress implements ProviderObserver,TerminalObserver{

	private SimpleDateFormat SDF = new SimpleDateFormat("hh:mm:ss:SSS a");
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
	  
	private String PHONEIP;
	private String PHONEMODEL;
	private String SRC;
	private String keypress;

	public KeyPress (Provider provider,CiscoTerminal observedTerminal,String v)  {
		keypress=v;
		this.provider=provider;
		this.observedTerminal=observedTerminal;
		System.out.println("Starting KeyPress");
					
		try {
			provider.addObserver ( this );
			System.out.println("waiting for provider");
			conditionInService.waitTrue ();				
			try {
				System.out.println ("Adding Terminal Observer to Terminal" + observedTerminal.getName ());
				observedTerminal.addObserver ( this );
				
			}
			catch ( Exception e ) {
				e.printStackTrace();
			} 
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	  }
					
	  public void terminalChangedEvent ( TermEv [] events ) {
		for ( int i=0; i<events.length; i++ ) {
			Terminal terminal = events[i].getTerminal ();
			switch ( events[i].getID () ) {
				case CiscoTermInServiceEv.ID:
				System.out.println ( "Received " + events[i] + "for " + terminal.getName ());
				terminalInService = true;
				if ( addressInService ) {
					if ( state != IN_SERVICE ) {
						state = IN_SERVICE;
					}
				}
				//Added code to show how senddata works
				System.out.println();
				
				try {
					System.out.println("--- Sent at: " + this.SDF.format(new Date()) + " ---\n" );
					System.out.println("Sending "+keypress);
//					String Res2 = observedTerminal.sendData("<CiscoIPPhoneExecute><ExecuteItem URL='Key:"+keypress+"'/><ExecuteItem URL='Key:KeyPad"+keypress+"'/></CiscoIPPhoneExecute>"); 
					String Res2 = observedTerminal.sendData(keypress);
					System.out.println("--- Recived at: " + this.SDF.format(new Date()) + " ---\n" +Res2);

				}
				catch ( Exception e ) {
					System.out.println ( "Caught exception " + e );
				}
				break;
				case CiscoTermOutOfServiceEv.ID:
					System.out.println ( "Received " + events[i] + "for " + terminal.getName () );
					terminalInService = false;
					if ( state != OUT_OF_SERVICE ) { // you only want to notify when you had notified earlier that you are IN_SERVICE
						state = OUT_OF_SERVICE;
					}
				break;
			}
		}
	  }

	public void providerChangedEvent ( ProvEv [] eventList ) {
		if ( eventList != null ) {
			for ( int i = 0; i < eventList.length; i++ )
			{
				if ( eventList[i] instanceof ProvInServiceEv ) {
					conditionInService.set ();
				}
			}
		}
	  }
		
	  protected final void onStop () {
	 	stopSignal = new StopSignal();
		stopSignal.stop ();
	  }
	  public void setSource(String v){
			this.SRC=v;
	  }	  
	  public String getPhoneIP(){
		  return PHONEIP;
	  }
	  public String getModel(){
		  return PHONEMODEL;
	  }
}