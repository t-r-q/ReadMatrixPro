package se.miun.projectM;

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
import java.util.ArrayList;

public class App {
    private static final String FILENAME = "E:/workshope_projects/PITProjects/New-test-YearAndMonth/target/pit-reports/202204131215/mutations.xml";

    static ArrayList<Mutation> mutations = new ArrayList<Mutation>();
    static ArrayList<MatrixObject> objectOfMatrix = new ArrayList<>();

    static ArrayList<String> allTests = new ArrayList<>();



    public static void main(String[] args) {

        readFile();


// for create object of matrix
        for (var m:mutations) {
           // nMat.sourceName = m.sourceFile;
       //   if (numberOfTestsRun > 1)
            makeMatrix(m);
         //   System.out.println(m);
        }

        // for fill allTests
       if (!objectOfMatrix.isEmpty()){
           for (var obInM: objectOfMatrix) {
               for (var mut: obInM.testCaseMember) {
                  if (!allTests.contains(mut)){
                      allTests.add(mut);
                      System.out.println(mut);
                  }
               }
           }
       }
       // fill matrix
        if (!objectOfMatrix.isEmpty()){
            for (var obInM: objectOfMatrix) {
                for (var mut: obInM.testCaseMember) {
                    if (allTests.contains(mut)){
                       int numb = allTests.indexOf(mut);
                       // obInM.matrix.get(numb).add(0);
                    }
                }
            }
        }

    }

    public static void readFile(){
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

                    mutations.add(mut);

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * makeMatrix method to create object of matrix
     * @param muta
     */
    public static void makeMatrix(Mutation muta){
        MatrixObject nMat = new MatrixObject();
            nMat.sourceName = muta.sourceFile.replace(".java", "");

        String[] arrOfMethodName = muta.killingTests.split("\\|", 20);
        ArrayList<String> nam = new ArrayList<>();
        for (int l=0; l < arrOfMethodName.length; l++){
            nam.add(GetNameMethod(arrOfMethodName[l]));
        }
        nMat.setTestCaseMember(nam);
        System.out.println(nam);
        objectOfMatrix.add(nMat);
       //   int startIndex = muta.killingTests.indexOf(")");
         //  nMat.matrix.
    }

    /**
     * TO get a name of method which has mutated
     * @param str
     * @return method name
     */
    public static String GetNameMethod(String str){
        String nm = "";
        int  endIndex = str.indexOf("(");
        for (int i = endIndex -1; i > 0; i--){
            if (str.charAt(i) == '.'){
                nm = (str.substring(i + 1 , endIndex));
                break;
            }
        }
        return nm;
    }
}
