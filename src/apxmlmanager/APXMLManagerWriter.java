package apxmlmanager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class APXMLManagerWriter<T> extends APXMLManagerBase<T> {
	private boolean is_ok = true;
	private Document document;
	private IAPXMLWriteable<T> context;
	private String filePath;
	
	public APXMLManagerWriter(IAPXMLWriteable<T> context, String filePath, String rootName) {
		super(rootName);
		this.filePath = filePath;
		this.context = context;
		//create document
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			document = documentBuilder.newDocument();
			document.setXmlStandalone(true);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			is_ok = false;
		}
	}
	
	public boolean write(ArrayList<T> list_to_write) {
		//check if is all OK
		if(is_ok) {
			 //create root
	        // root element
	        Element root = document.createElement(super.getRootName());
	        Attr attr = document.createAttribute("numero");
            attr.setValue(Integer.toString(list_to_write.size()));
            root.setAttributeNode(attr);
	        document = this.context.parse(document,root,list_to_write);
	        //save file
	        try {
	        	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        	Transformer transformer = transformerFactory.newTransformer();
	        	DOMSource domSource = new DOMSource(document);
	        	StreamResult streamResult = new StreamResult(new File(this.filePath));
	            transformer.transform(domSource, streamResult);
	            System.out.println("Scrittura terminata");
	        }
	        catch(Exception ex) {
	        	return false;
	        }
	        return true;
		}else return false;
	}
}
