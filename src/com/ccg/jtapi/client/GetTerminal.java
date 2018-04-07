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
import javax.telephony.phone.Component;

import com.cisco.cti.util.Condition;
import com.cisco.jtapi.CiscoJtapiPeerImpl;
import com.cisco.jtapi.extensions.CiscoJtapiPeer;
import com.cisco.jtapi.extensions.CiscoJtapiProperties;
import com.cisco.jtapi.extensions.CiscoJtapiVersion;
import com.cisco.jtapi.extensions.CiscoProvider;
import com.cisco.jtapi.extensions.CiscoTermInServiceEv;
import com.cisco.jtapi.extensions.CiscoTermOutOfServiceEv;
import com.cisco.jtapi.extensions.CiscoTerminal;


public class GetTerminal implements ProviderObserver,TerminalObserver{

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
	private String SERVER;
	private String USERNAME;
	private String PASSWORD;
	private String SRC;
	private String keypress;

	public GetTerminal(){

	}
	public GetTerminal (String SERVER,String USERNAME,String PASSWORD,String SRC,String DEST,int DELAY)  {
		try {
			System.out.println ( "Initializing Jtapi" );
			String providerName = SERVER;
			String login = USERNAME;
			String passwd = PASSWORD;
			JtapiPeer peer = JtapiPeerFactory.getJtapiPeer ( null );
			String providerString = providerName + ";login=" + login + ";passwd=" + passwd;
			System.out.println ( "Opening " + providerString + "...\n" );
			provider = peer.getProvider ( providerString );
			provider.addObserver ( this );
			conditionInService.waitTrue ();
			this.destAddress = provider.getAddress(SRC);
			this.observedTerminal = (CiscoTerminal)destAddress.getTerminals()[0];
			try {
				if ( destAddress != null ) {
					System.out.println ("Adding Terminal Observer to Terminal" + observedTerminal.getName ());
					observedTerminal.addObserver ( this );
				}
			}
			catch ( Exception e ) {
			} 
		}
		catch ( Exception e ) {
			System.out.println ( "Caught exception " + e );
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
					
//					String Cisco = "<CiscoIPPhoneText>" + "<Title>Alert</Title>" + "<Prompt>Select an option</Prompt>" + "<Text>Cisco Systems Again.</Text>" + "</CiscoIPPhoneText>";
//					String Res2 = observedTerminal.sendData(Cisco);
//					System.out.println ( "Received " + Res2 + "for Pushing Data " );
//					observedTerminal.sendData("<CiscoIPPhoneExecute><ExecuteItem URL='Key:Speaker'/><ExecuteItem URL='Key:KeyPad0'/></CiscoIPPhoneExecute>"); 
					Thread.sleep(500);
//					observedTerminal.sendData("<CiscoIPPhoneExecute><ExecuteItem URL='Key:"+keypress+"'/><ExecuteItem URL='Key:KeyPad0'/></CiscoIPPhoneExecute>"); 
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
	  public Provider setProvider() throws WordContainsException{
		  try {
//			  Component localComponent, downloadedComponent;
//			  Component updater = new Component();
//			  String localPath = updater.getLocalComponentPath(args[1]);
//			  localComponent = updater.queryLocalComponentVersion("jtapi.jar",localPath);
//			  localComponent.copyTo("component.temp");
//			  String provString = args[0] + ";login=" + args[2] + ";passwd=" + args[3];
//			  CiscoJtapiPeer cpeer = (CiscoJtapiPeer) (JtapiPeerFactory.getJtapiPeer(null));
//			  CiscoJtapiProperties tempProp = ( (CiscoJtapiPeerImpl) (cpeer)). getJtapiProperties();
//			  tempProp.setLightWeightProvider(true);
//			  Provider provider = cpeer.getProvider(provString);
//			  String url = ( (CiscoProvider) (provider)).getJTAPIURL(); provider.shutdown();
//			  Component serverComponent = updater.queryServerComponentVersion("jtapi.jar", url);
//			  downloadedComponent = serverComponent.fetchFromServer();	
//			  int retVal = downloadedComponent.replaces(localComponent);
//

			  System.out.println ( "Initializing Provider" );
			  String providerName = SERVER;
			  String login = USERNAME;
			  String passwd = PASSWORD;
			  JtapiPeer peer = JtapiPeerFactory.getJtapiPeer ( null );
			  String providerString = providerName + ";login=" + login + ";passwd=" + passwd;
			  System.out.println ( "Opening " + providerString + "...\n" );
			  provider = peer.getProvider ( providerString );
			  provider.addObserver ( this );
			  conditionInService.waitTrue ();
			  this.destAddress = provider.getAddress(SRC);
			  this.observedTerminal = (CiscoTerminal)destAddress.getTerminals()[0];
		  }
		  catch ( Exception e ) {
			  throw new WordContainsException(e.toString());
		  }
		  return provider;
	  }

	  public CiscoTerminal setTerminal (Provider provider)throws WordContainsException{
		  try {
			  System.out.println ( "Initializing Terminal" );
			  this.observedTerminal = (CiscoTerminal)destAddress.getTerminals()[0];
		  }
		  catch ( Exception e ) {
			  System.out.println ( "Caught exception " + e );
		  }
		  return observedTerminal;
	  }
	  
	  public void setServer(String v){
			this.SERVER=v;
	  }
	  public void setUsername(String v){
			this.USERNAME=v;

	  }
	  public void setPassword(String v){
			this.PASSWORD=v;
	  }
	  public void setSource(String v){
			this.SRC=v;
	  }	  

}
class WordContainsException extends Exception
{
      //Parameterless Constructor
      public WordContainsException() {}

      //Constructor that accepts a message
      public WordContainsException(String message)
      {
         super(message);
      }
 }
