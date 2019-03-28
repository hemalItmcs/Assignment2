package com.example.telegram.utility;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XmlParserUtility {
    
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;
    Node root;
        
    public XmlParserUtility(String xmlContent) throws Exception{
        factory = DocumentBuilderFactory.newInstance();
        ByteArrayInputStream input = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));
        builder = factory.newDocumentBuilder();
        document = builder.parse(input);
        NodeList nodelist = document.getChildNodes();
        root=nodelist.item(0);
    }
    
    public Node getRootNode(){
        return root;
    }
    
    public Node getNodeByName(Node root, String nodeName){
        Node output = null;
        if(!root.getNodeName().equals(nodeName)){
            if (root.hasChildNodes()) {
                NodeList nodes = root.getChildNodes();
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    if (node.getNodeName().equals(nodeName)) {
                        output=node;
                        break;
                    } else {
                        if (node.hasChildNodes()) {
                            if (!node.getFirstChild().getNodeName().equals("#text") && !node.getFirstChild().getNodeName().equals("#cdata-section")) {
                                i++;
                                output=getNodeByName(nodes.item(i), nodeName);
                                if(output!=null){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }else{
            output=root;
        }
        return output;
    }
    
    public String getNodeText(Node root, String nodeName) {
        
        if (root.hasChildNodes()) {
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeName().equals(nodeName)) {
                    return node.getTextContent();
                } else {
                    if (node.hasChildNodes()) {
                        if (!node.getFirstChild().getNodeName().equals("#text") && !node.getFirstChild().getNodeName().equals("#cdata-section")) {
                            return getNodeText(node, nodeName);
                        }
                    }
                }
            }

        }
        return null;
    }
    
    public void getWeatherInformation(Node node,Map<String, Object> currentWeather,XmlParserUtility utility){
        currentWeather.put("celsius",utility.getNodeText(node, "temp_C")!=null ? utility.getNodeText(node, "temp_C") : utility.getNodeText(node, "tempC"));
        currentWeather.put("fahrenheit",utility.getNodeText(node, "temp_F")!=null ? utility.getNodeText(node, "temp_F") : utility.getNodeText(node, "tempF"));
        currentWeather.put("weatherDescription",utility.getNodeText(node, "weatherDesc"));
    }
}
