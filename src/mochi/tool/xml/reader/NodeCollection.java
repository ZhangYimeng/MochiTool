package mochi.tool.xml.reader;

import mochi.tool.xml.reader.exception.NoNodeCollectionException;
import mochi.tool.xml.reader.exception.NoSingleNodeException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeCollection {
	
	private NodeList nodeList;
	private String tagName;
	
	/**
	 * 构造函数，不需用户直接调用。
	 * @param nodeList Node集合。
	 * @param tagName 标签名。
	 * @throws NoNodeCollectionException 如果Node集合中没有元素则抛出该异常。
	 */
	public NodeCollection(NodeList nodeList, String tagName) throws NoNodeCollectionException {
		if(nodeList.getLength() != 0) {
			this.nodeList = nodeList;
			this.tagName = tagName;
		} else {
			throw new NoNodeCollectionException();
		}
	}
	
	/**
	 * 得到Node集合中Node的个数。
	 * @return Node的个数。
	 */
	public int getLength() {
		return this.nodeList.getLength();
	}
	
	/**
	 * 获得Node的标签名。
	 * @return Node的标签名。
	 */
	public String getTag() {
		return this.tagName;
	}
	
	/**
	 * 使用index获得Node集合中的第index的Node。
	 * @param index Node的指示标。
	 * @return 一个单一Node。
	 * @throws NoSingleNodeException 当index超出NodeCollection的Node个数时，抛出此异常。
	 */
	public SingleNode item(int index) throws NoSingleNodeException {
		Node node = this.nodeList.item(index);
		if(node != null) {
			return new SingleNode(node);
		} else {
			throw new NoSingleNodeException();
		}
	}
	
}
