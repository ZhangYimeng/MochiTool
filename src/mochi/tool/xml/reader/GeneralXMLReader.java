package mochi.tool.xml.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import mochi.tool.xml.reader.exception.NoNodeCollectionException;

/**
 * 创建一个Xml的读取器，适用于已知XML文件内容的情况。
 * @author zhangyimeng
 *
 */
public class GeneralXMLReader {

	private Document xmlDoc;
	private NodeList rootNodeList;
	private NodeList currentNodeList;
	private Node currentNode;
	private String rootTag;
	
	/**
	 * 构造函数。
	 * @param xmlfilePath XML文件路径。
	 */
	public GeneralXMLReader(String xmlfilePath) {
		try {
			File xmlFile = new File(xmlfilePath);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	        docBuilderFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder db = docBuilderFactory.newDocumentBuilder();
			InputStream xmlFileStream = new FileInputStream(xmlFile);
			this.xmlDoc = db.parse(xmlFileStream);
			this.xmlDoc.getDocumentElement().normalize();
			this.rootNodeList = this.xmlDoc.getChildNodes();
			this.currentNodeList = this.rootNodeList;
			this.currentNode = this.currentNodeList.item(0);
			this.rootTag = this.currentNode.getNodeName();
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造函数。
	 * @param in XML文件的输入流。
	 */
	public GeneralXMLReader(InputStream in) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder db = docBuilderFactory.newDocumentBuilder();
			this.xmlDoc = db.parse(in);
			this.xmlDoc.getDocumentElement().normalize();
			this.rootNodeList = this.xmlDoc.getChildNodes();
			this.currentNodeList = this.rootNodeList;
			this.currentNode = this.currentNodeList.item(0);
			this.rootTag = this.currentNode.getNodeName();
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用标签名称获取一个Node集合。
	 * @param tagName 标签名。
	 * @return Node集合。
	 * @throws NoNodeCollectionException 抛出集合中没有Node异常。
	 */
	public NodeCollection getNodeCollectionByTagName(String tagName) throws NoNodeCollectionException {
		NodeList nl = ((Element) this.currentNode).getElementsByTagName(tagName);
		if(nl.getLength() != 0) {
			return new NodeCollection(nl, tagName);
		} else {
			throw new NoNodeCollectionException();
		}
	}
	
	public String getRootTag() {
		return this.rootTag;
	}
	
}
