package mochi.tool.xml.reader;

import mochi.tool.xml.reader.exception.NoNodeCollectionException;
import mochi.tool.xml.reader.exception.NoSingleNodeException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SingleNode {
	
	private Element node;

	public SingleNode(Node node) {
		this.node = (Element) node;
	}
	
	public NodeCollection getNodeCollectionByTagName(String tagName) throws NoNodeCollectionException {
		NodeList nodeList = this.node.getElementsByTagName(tagName);
		if(nodeList.getLength() != 0) {
			return new NodeCollection(nodeList, tagName);
		} else {
			throw new NoNodeCollectionException();
		}
	}
	
	public String getTag() throws NoSingleNodeException {
		if(this.node != null) {
			return this.node.getTagName();
		} else {
			throw new NoSingleNodeException();
		}
	}
	
	public String getValue() throws NoSingleNodeException {
		if(this.node != null) {
			return this.node.getChildNodes().item(0).getNodeValue();
		} else {
			throw new NoSingleNodeException();
		}
	}

}
