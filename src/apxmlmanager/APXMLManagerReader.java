package apxmlmanager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class APXMLManagerReader<T> extends APXMLManagerBase<T> {
	private Document doc;
	private Element root;
	private IAPXMLReadable<T> context;
	
	public APXMLManagerReader(IAPXMLReadable<T> context,String filePath, String rootName) {
		super(rootName);
		this.context = context;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream f = new FileInputStream(filePath);
			doc = builder.parse(f);
			root = doc.getDocumentElement();
			f.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			doc = null;
		}
	}
	
	
	public ArrayList<T> read() {
		if(doc!=null) {
			if(root.getTagName().equals(super.getRootName())) {
				return this.context.parse(root.getChildNodes());
			}
			else return null;
		}
		else return null;
	}
}
