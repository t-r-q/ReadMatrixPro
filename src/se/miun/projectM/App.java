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
import java.util.Objects;

public class App {
    // This FILENAME is a Path file for testing only
    private static final String FILENAME = "db/mutations.xml";
  // private static final String FILENAME = "E:/workshope_projects/PITProjects/mutations.xml";
    static ArrayList<Mutation> mutations = new ArrayList<Mutation>();
    static ArrayList<MatrixObject> objectOfMatrix = new ArrayList<>();

    static ArrayList<String> allTests = new ArrayList<>();



    public static void main(String[] args) {

        // REad XML file
        readFile();


// for create object of matrix
        for (var m:mutations) {
           makeObjectOfMatrix(m);
        }

        // for fill allTests
       if (!objectOfMatrix.isEmpty()){
           // Fill unique names
          fillAlltestsNames();
           // fill matrix
          fillOutcomeMatrix();
       }

        for (int r=0; r< allTests.size(); r++) {
           int l = allTests.get(r).length();
           l = 20 -l;
               System.out.print('[');
            System.out.print(allTests.get(r) +" ");
            for (; l>=0; l--){
                System.out.print(" ");
            }


            for (var z : objectOfMatrix) {
                System.out.print("  " +z.getMatrix().get(r));

            }
                System.out.println(']');

        }


    }

    private static void fillOutcomeMatrix() {
        for (var obInM: objectOfMatrix) {
            for (var mut: allTests) {
                if (obInM.testKillingTests.contains(mut)){
                    obInM.matrix.add("1");
                } else if (obInM.testSucceedingTests.contains(mut)) {
                    obInM.matrix.add("0");
                }else
                    obInM.matrix.add("-");
            }
        }
    }

    /**
     *
     */
    private static void fillAlltestsNames() {
        for (var obInM: objectOfMatrix) {
            for (var mut: obInM.testKillingTests) {
                if (!allTests.contains(mut) && !Objects.equals(mut, "")){
                    allTests.add(mut);
                //    System.out.println(mut);
                }
            }
        }
    }

    /**
     * readFile Method to read XML file and fill ObjectOfMatrix
     */
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
                    // add mutant which has number up 1
                    if (!elementMutate.getAttribute("numberOfTestsRun").equals("1") && !elementMutate.getAttribute("numberOfTestsRun").equals("0"))
                    { // get mutant's attribute
                        mut.setId(String.valueOf(numNode +1));
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
                    }


                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * makeObjectOfMatrix method to create object of matrix
     * @param muta
     */
    public static void makeObjectOfMatrix(Mutation muta){
        MatrixObject nMat = new MatrixObject();
            nMat.sourceName = muta.sourceFile.replace(".java", "");

            // Read killingTests String
        String[] arrOfKilling = muta.killingTests.split("\\|", 20);
        ArrayList<String> nam = new ArrayList<>();
        for (int l=0; l < arrOfKilling.length; l++){
            nam.add(GetNameMethod(arrOfKilling[l]));
        }
        nMat.setTestKillingTests(nam);

        // Read succeedingTests String
        ArrayList<String> nams = new ArrayList<>();
        String[] arrOfSucceeding = muta.succeedingTests.split("\\|", 20);
        for (int l=0; l < arrOfSucceeding.length; l++){
            nams.add(GetNameMethod(arrOfSucceeding[l]));
        }
        nMat.setTestSucceedingTests(nams);
       // System.out.println(nam + " " + nams);


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
