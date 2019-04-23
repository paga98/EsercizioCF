

import apxmlmanager.APXMLManagerReader;
import apxmlmanager.APXMLManagerWriter;
import apxmlmanager.IAPXMLReadable;
import apxmlmanager.IAPXMLWriteable;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

public class FiscalCode implements IAPXMLWriteable<String>,IAPXMLReadable<String>
{
	private String code;
	//final properties for xml
	public static final String filePath = "C:\\Users\\Massimiliano\\Desktop\\Progetto Arnaldo\\CodiceFiscale\\EsercizioCF\\src\\codiciFiscali.xml";
	public static final String rootName = "codici";
	public static final String code_field = "codice";

	public String get_code() {
		return code;
	}
	public void set_code(String code) {
		this.code = code;
	}
	
	public FiscalCode() {}
	
	
	public static ArrayList<String> getList()
	{
		IAPXMLReadable<String> ir = new FiscalCode();
		return new APXMLManagerReader<String>(ir,filePath,rootName).read();
	}
	
	public static boolean setList(ArrayList<String> listFCode) {
		IAPXMLWriteable<String> ir = new FiscalCode();
		return new APXMLManagerWriter<String>(ir,filePath,rootName).write(listFCode);
	}
	

	@Override
	public Document parse(Document doc, Element root, ArrayList<String> list) {
		Element email;
		for(String s : list) {
			email = doc.createElement(code_field);
			email.appendChild(doc.createTextNode(s));
			root.appendChild(email);
		}
        doc.appendChild(root);
        return doc;
	}

	@Override
	public ArrayList<String> parse(NodeList nodes) {
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<nodes.getLength();i++) {
			org.w3c.dom.Node child = nodes.item(i);
			if(child.getNodeName().equals(code_field)) {
				String text = child.getTextContent();
				//System.out.println(text);
				list.add(text);
			}
		}
		return list;
	}
}
