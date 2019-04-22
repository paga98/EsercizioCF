package apxmlmanager;

public class APXMLManagerBase<T>
{
	private String rootName;
	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public APXMLManagerBase(String rootName) {
		setRootName(rootName);
	}
}
