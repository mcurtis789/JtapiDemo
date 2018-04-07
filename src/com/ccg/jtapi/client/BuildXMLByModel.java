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
import java.util.ArrayList;
import javax.telephony.Provider;

public class BuildXMLByModel {

	public ArrayList keyPad(CiscoTerminal observedTerminal, Provider provider, String v){
		String model = observedTerminal.getTypeName();
		ArrayList list = new ArrayList();
		list.add(BuildXML.createKeyCmd("Key:", v));
		return list;
	}
	
	public ArrayList settings(CiscoTerminal observedTerminal, Provider provider, String v){
		String model = observedTerminal.getTypeName();
		ArrayList list = new ArrayList();
		if(model.contains("7911")){
			list.add(BuildXML.createKeyCmd("Key:", "Applications"));			
		}else if (model.contains("69")||model.contains("79")){
			list.add(BuildXML.createKeyCmd("Init:", "Services"));
			list.add(BuildXML.createKeyCmd("Key:", v));
		}else{
			list.add(BuildXML.createKeyCmd("Key:", "Applications"));
		}
		return list;
	}
	
	public ArrayList services(CiscoTerminal observedTerminal, Provider provider,String v){
		String model = observedTerminal.getTypeName();
		ArrayList list = new ArrayList();
		if (model.contains("69")||model.contains("79")||model.contains("89")||model.contains("99")){
			list.add(BuildXML.createKeyCmd("Key:", v));
		}else{
			list.add(BuildXML.createKeyCmd("Key:", v));
		}
		return list;
	}

	public ArrayList hold(CiscoTerminal observedTerminal, Provider provider, String v){
		String model = observedTerminal.getTypeName();
		ArrayList list = new ArrayList();
		if (model.contains("69")||model.contains("89")||model.contains("99")){			
			list.add(BuildXML.createKeyCmd("Key:FixedFeature", "3"));
		}else{
			list.add(BuildXML.createKeyCmd("Key:", v));
		}
		return list;
	}
	
	public ArrayList confrence(CiscoTerminal observedTerminal, Provider provider,String v){
		String model = observedTerminal.getTypeName();
		ArrayList list = new ArrayList();
		if (model.contains("69")||model.contains("89")||model.contains("99")){
			list.add(BuildXML.createKeyCmd("Key:FixedFeature", "2"));
		}else{
			list.add(BuildXML.createKeyCmd("Key:", v));
		}
		return list;
	}
	
	public ArrayList transfer(CiscoTerminal observedTerminal, Provider provider,String v){
		String model = observedTerminal.getTypeName();
		ArrayList list = new ArrayList();
		if (model.contains("69")||model.contains("89")||model.contains("99")){
			list.add(BuildXML.createKeyCmd("Key:FixedFeature", "1"));
		}else{
			list.add(BuildXML.createKeyCmd("Key:", v));
		}
		return list;
	}
	
}
