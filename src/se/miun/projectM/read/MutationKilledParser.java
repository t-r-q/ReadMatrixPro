package se.miun.projectM.read;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import se.miun.projectM.Mutation;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MutationKilledParser {
    static ArrayList<Mutation> mutations;
    public static HashMap<Mutation, Set<String>> mutantMap;
    private static String mutationReportFile;



    public MutationKilledParser(String mutationReportFile, ArrayList<Mutation> mutations) {
        MutationKilledParser.mutationReportFile = mutationReportFile;
        MutationKilledParser.mutations = mutations;


    }

    /**
     * readFile Method to read XML file and fill ObjectOfMatrix
     */
    public ArrayList<Mutation> readFile() {
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(mutationReportFile));

            // optional, but recommended
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------** Description **--------");
            System.out.println("Where is 0 represents that a test passed, whereas 1 represents that a test fails.");
            System.out.println("Every column has version (v) is a mutant.");
            // get <mutation>
            NodeList list = doc.getElementsByTagName("mutation");

            for (int numNode = 0; numNode < list.getLength(); numNode++) {
                Mutation mut = new Mutation();
                Node node = list.item(numNode);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element elementMutate = (Element) node;
                    Element eElement = (Element) node;
                    // add mutant which has number up 1
                    // if (!elementMutate.getAttribute("numberOfTestsRun").equals("1") && !elementMutate.getAttribute("numberOfTestsRun").equals("0")){
                    // get mutant's attribute
                    mut.setId(String.valueOf(numNode + 1));
                    mut.setStatus(elementMutate.getAttribute("status"));
                    mut.setNumberOfTestsRun(elementMutate.getAttribute("numberOfTestsRun"));
                    // get text
                    mut.setSourceFile(elementMutate.getElementsByTagName("sourceFile").item(0).getTextContent());
                    mut.setMutatedMethod(elementMutate.getElementsByTagName("mutatedMethod").item(0).getTextContent());
                    mut.setLineNumber(elementMutate.getElementsByTagName("lineNumber").item(0).getTextContent());
                    mut.setMutator(elementMutate.getElementsByTagName("mutator").item(0).getTextContent());
                    mut.setBlock(elementMutate.getElementsByTagName("block").item(0).getTextContent());
                    mut.setKillingTests(elementMutate.getElementsByTagName("killingTests").item(0).getTextContent());
                    mut.setSucceedingTests(elementMutate.getElementsByTagName("succeedingTests").item(0).getTextContent());
                    mut.setDescription(elementMutate.getElementsByTagName("description").item(0).getTextContent());

                    mutations.add(mut);
                    //   }

                    String mutatedClassName = eElement.getElementsByTagName("mutatedClass").item(0).getTextContent();
                    String mutatedMethodName = eElement.getElementsByTagName("mutatedMethod").item(0).getTextContent();
                    String mutatedMethodDescriptionName = eElement.getElementsByTagName("methodDescription").item(0).getTextContent();
                    String mutatedLineNumber = eElement.getElementsByTagName("lineNumber").item(0).getTextContent();
                    String mutationType = eElement.getElementsByTagName("mutator").item(0).getTextContent();
                    if (elementMutate.getAttribute("status").equals("KILLED")) {
                        Mutation mutant = new Mutation(mutatedClassName, mutatedMethodName, mutatedMethodDescriptionName, mutatedLineNumber, mutationType);

                    }


                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return mutations;
    }
}
