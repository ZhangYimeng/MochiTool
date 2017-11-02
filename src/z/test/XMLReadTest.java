package z.test;

import java.util.LinkedList;
import java.util.List;

import mochi.tool.xml.reader.GeneralXMLReader;
import mochi.tool.xml.reader.NodeCollection;
import mochi.tool.xml.reader.SingleNode;
import mochi.tool.xml.reader.exception.NoNodeCollectionException;
import mochi.tool.xml.reader.exception.NoSingleNodeException;

public class XMLReadTest {

	public static void main(String[] args) throws NoNodeCollectionException, NoSingleNodeException {
		/*GeneralXMLReader reader = new GeneralXMLReader("xmlfile/XGDXML.xml");
		NodeCollection nc = null;
		try {
			System.out.println(reader.getRootTag());
			nc = reader.getNodeCollectionByTagName("Node");
		} catch (NoNodeCollectionException e) {
			e.printStackTrace();
		}
		System.out.println(nc);
		System.out.println("asdasdasdas");
		System.out.println("asdasdasdas");
		System.out.println("asdasdasdas");
		System.out.println("asdasdasdas");
		System.out.println("asdasdasdas");
		System.out.println("asdasdasdas");*/
		GeneralXMLReader reader = new GeneralXMLReader("xmlfile/XGDXML.xml");
		NodeCollection nodeCollection = reader.getNodeCollectionByTagName("Node");
		int nodeNumber = nodeCollection.getLength();
		String[] nodeNames = new String[nodeNumber];
		LinkedList<List<String>> sensors = new LinkedList<List<String>>();
		for(int i = 0; i < nodeNumber; i++) {
			SingleNode singleNode = nodeCollection.item(i);
			nodeNames[i] = "Node " + (i + 1);
			System.out.println(nodeNames[i]);
			List<String> sensorsOfSingleNode = new LinkedList<String>();
			NodeCollection sensorCollection = singleNode.getNodeCollectionByTagName("Sensor");
			int sensorsNumber = sensorCollection.getLength();
			for(int j = 0; j < sensorsNumber; j++) {
				sensorsOfSingleNode.add(sensorCollection.item(j).getValue());
				System.out.println(sensorCollection.item(j).getValue());
			}
			sensors.add(sensorsOfSingleNode);
			System.out.println(nodeNames[i] + " done");
		}
	}

}
