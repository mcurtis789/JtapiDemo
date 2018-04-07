package com.ccg.http;
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
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import javax.imageio.ImageIO;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTMLDocument;

public class Http{

	private String USERNAME;
	private String PASSWORD;
	//private String URL="http://10.10.255.20/CGI/Screenshot";
	private String MODEL;
	private String URL="";
	
	public Image GetImage(String host){
		Image image=null;

		if (MODEL.equals("ThirdGen")){
			image=ThirdGen(host);
		}else if (MODEL.equals("ForthGen")){
			image=ForthGen(host);
		}else {
			System.out.println("Unsupported Phone Screen Shot");
		}
	    return image; 
	}
	public Image ThirdGen(String host){
			//URL="http://"+host+"/CGI/lcd.bmp";
		
		MyAuthenticator.setPasswordAuthentication(USERNAME,PASSWORD);
		Authenticator.setDefault (new MyAuthenticator ());
		Image image=null;
		try {
			URLConnection uc = null;
			InputStream content = null;
			ByteArrayOutputStream out = null;
					
			URL="http://"+host+"/CGI/Screenshot";      
			URL url = new URL(URL);
	        uc = url.openConnection();
	          
	        content = (InputStream)uc.getInputStream();
	        out = new ByteArrayOutputStream();
	          
	        byte[] buf = new byte[1024];
	        int n = 0;
	        while (-1!=(n=content.read(buf))) {
	        	out.write(buf, 0, n);
	          
	        }
	        System.out.println("\ndownload: \n");
	        System.out.println(">> URL: " + url);
	        System.out.println(">> size: " + out.size() + " bytes");
  
	        URL="http://"+host+"/CGI/lcd.bmp";
	        URL url2 = new URL(URL);
	        uc = url2.openConnection();
	        	        
	        content = (InputStream)uc.getInputStream();
	        out = new ByteArrayOutputStream();
	          
	        buf = new byte[1024];
	        n = 0;
	        while (-1!=(n=content.read(buf))) {
	        	out.write(buf, 0, n);
	          
	        }
	        System.out.println("\ndownload: \n");
	        System.out.println(">> URL: " + url2);
	          
	        System.out.println(">> size: " + out.size() + " bytes");

	        out.close();
	        content.close();
	        byte[] response = out.toByteArray();
	        	
	        image = ImageIO.read(new ByteArrayInputStream(response));
	    	  

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return image; 
	}

	public Image ForthGen(String host){
		
		URL="http://"+host+"/CGI/Screenshot";
		MyAuthenticator.setPasswordAuthentication(USERNAME,PASSWORD);
		Authenticator.setDefault (new MyAuthenticator ());
		Image image=null;
		try {
	          URL url = new URL(URL);
	          URLConnection uc = url.openConnection();
	          
	          System.out.println("\ndownload: \n");
	    	  System.out.println(">> URL: " + url);
	    	  
	          InputStream content = (InputStream)uc.getInputStream();
	          ByteArrayOutputStream out = new ByteArrayOutputStream();
	          
	          byte[] buf = new byte[1024];
	          int n = 0;
	          while (-1!=(n=content.read(buf))) {
	             out.write(buf, 0, n);
	          
	          }
	          
	          System.out.println(">> size: " + out.size() + " bytes");

	          out.close();
	          content.close();
	          byte[] response = out.toByteArray();
	        
	          image = ImageIO.read(new ByteArrayInputStream(response));
	    	  

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return image; 
	}
    public void setUserName(String v){
    	this.USERNAME= v;
    }
    public void setPassword(String v){
    	this.PASSWORD=v;
    }
    public void setModel(String v){
    	this.MODEL=v;
    }

}
