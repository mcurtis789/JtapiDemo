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

import com.cisco.jtapi.extensions.CiscoTerminal;
import java.io.PrintStream;

public class BuildXML
{
//  public static final String SOFTKEY = "Key:Soft";
//  public static final String LINEKEY = "Key:Line";
//  public static final String KEYPADKEY = "Key:KeyPad";
//  public static final String DIALKEY = "Dial:";
//  public static final String VOLUP = "Key:VolUp";
//  public static final String VOLDOWN = "Key:VolDwn";
//  public static final String HEADSET = "Key:Headset";
//  public static final String MUTE = "Key:Mute";
//  public static final String SPEAKER = "Key:Speaker";
//  public static final String INFO = "Key:Info";
//  public static final String MESSAGES = "Key:Messages ";
//  public static final String SERVICES = "Key:Services";
//  public static final String DIRECTORIES = "Key:Directories";
//  public static final String SETTINGS = "Key:Settings";
//  public static final String NAVUP = "Key:NavUp";
//  public static final String NAVDOWN = "Key:NavDwn";
//  public static final String APPMENU = "Key:AppMenu";
//  public static final String HOLD = "Key:Hold";
  

//  public static String createTextMsgCmd(String argTitle, String argPrompt, String argText)
//  {
//    String IPTEXTTAG = "CiscoIPPhoneText";
//    String TITLETAG = "Title";
//    String PROMPTTAG = "Prompt";
//    String TEXTTAG = "Text";
//    
//    String TitleMsg = createXMLMsg("Title", argTitle);
//    String PromptMsg = createXMLMsg("Prompt", argPrompt);
//    String TextMsg = createXMLMsg("Text", argText);
//    
//    return createXMLMsg("CiscoIPPhoneText", TitleMsg + PromptMsg + TextMsg);
//  }
//  
//  public static String createExecuteCmd(String cmd)
//  {
//    String EXECUTETAG = "CiscoIPPhoneExecute";
//    return createXMLMsg("CiscoIPPhoneExecute", cmd);
//  }
//  
  public static String createKeyCmd(String key)
  {
    String tag = "CiscoIPPhoneExecute";
    String content ="<ExecuteItem Priority=\"0\" URL=\"" + key + "\"/>";
    return "<" + tag + ">" + content + "</" + tag + ">";
  }
  
  public static String createKeyCmd(String keyType, String number)
  {
    return createKeyCmd(keyType + number);
  }
  
  public static String createXMLMsg(String tag, String content)
  {
    return "<" + tag + ">" + content + "</" + tag + ">";
  }
  
}
