package apxmlmanager;

public interface IAPXMLWriteable<T> {
	org.w3c.dom.Document parse(org.w3c.dom.Document doc,org.w3c.dom.Element root, java.util.ArrayList<T> list);
}
