package apxmlmanager;

public interface IAPXMLReadable<T>{
	java.util.ArrayList<T> parse(org.w3c.dom.NodeList nodes);
}
