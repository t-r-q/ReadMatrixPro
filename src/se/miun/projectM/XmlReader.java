package se.miun.projectM;


import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlReader {
    private static String FILENAME = "";
     ArrayList<Mutation> mutations;

    public XmlReader() {
    }
    public XmlReader(ArrayList<Mutation> mut, String path) {
        FILENAME = path;
      mutations = new ArrayList<>(mut);
    }

    public void readFile(){
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));

            // optional, but recommended
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // get <mutation>
            NodeList list = doc.getElementsByTagName("mutation");

            for (int numNode = 0; numNode < list.getLength(); numNode++) {
                Mutation mut = new Mutation();
                Node node = list.item(numNode);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element elementMutate = (Element) node;
                    mut.id = String.valueOf(numNode +1);
                    // get staff's attribute
                    mut.status = elementMutate.getAttribute("status");

                    // get text
                    mut.sourceFile = elementMutate.getElementsByTagName("sourceFile").item(0).getTextContent();
                    mut.mutatedMethod = elementMutate.getElementsByTagName("mutatedMethod").item(0).getTextContent();
                    mut.lineNumber = elementMutate.getElementsByTagName("lineNumber").item(0).getTextContent();
                    mut.mutator = elementMutate.getElementsByTagName("mutator").item(0).getTextContent();
                    mut.index = elementMutate.getElementsByTagName("index").item(0).getTextContent();
                    mut.block = elementMutate.getElementsByTagName("block").item(0).getTextContent();
                    mut.killingTests = elementMutate.getElementsByTagName("killingTests").item(0).getTextContent();
                    mut.succeedingTests = elementMutate.getElementsByTagName("succeedingTests").item(0).getTextContent();
                    mut.description = elementMutate.getElementsByTagName("description").item(0).getTextContent();


                    //     System.out.println("Current Element :" + node.getNodeName());
                    //  System.out.println("Status : " + status);
                    //   System.out.println("Source File : " + sourceFile);
                    //   System.out.println("Mutated Method : " + mutatedMethod);
                    //   System.out.println("Line Number : " + lineNumber);
                    mutations.add(mut);

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
