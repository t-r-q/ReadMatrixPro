package se.miun.projectM;


import se.miun.projectM.mathLOG.APFDCalculator;
import se.miun.projectM.mathLOG.ConfusionMatrix;
import se.miun.projectM.read.MutationKilledParser;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    // This FILENAME is a Path file for testing only
    //  private static final String FILENAME = "db/mutations0.xml";
    // private static final String FILENAME = "db/commons-codec.xml";
     private static final String FILENAME = "db/gameoflife-java.xml";
    //private static final String FILENAME = "db/algorithms-miscellaneous-6.xml";


    static ArrayList<Mutation> mutations = new ArrayList<>();
    static ArrayList<MatrixObject> objectOfMatrix = new ArrayList<>();
    static ArrayList<MatrixObject> objectOfKilledMutant = new ArrayList<>();
    static ArrayList<String> allTests = new ArrayList<>();
    static Map<String, Double> testCasesSMAverage = new HashMap<>();
    static double[][] MATRIX;

    static int[][] twentyPercentOfMatrix;
    static int[][] eightyPercentOfMatrix;
    static double[][] DISTANCE_80_MATRIX_MCC;
    static double[][] DISTANCE_80_MATRIX_ACC;
    static double[][] DISTANCE_80_MATRIX_FowlkesMallows;
    static double[][] DISTANCE_MATRIX_MCC;

    static double[][] DISTANCE_MATRIX_ACC;

    static double[][] DISTANCE_MATRIX_FowlkesMallows;
    static ArrayList<String> PROIRITISETEST = new ArrayList<>();

   // static LinearRegressionCLASS lr;


    public static void main(String[] args) throws Exception {

        // Read XML file and fill ObjectOfMatrix
        MutationKilledParser pars = new MutationKilledParser(FILENAME, mutations);

        mutations = pars.readFile();

        // Greate matrix for killed mutants ****
        for (var m : mutations) {
          if (m.status.equals("KILLED"))
            makeObjectOfKilledMutants(m);
        }

        // for create object of matrix
        for (var m : mutations) {
            makeMatrix(m);
        }

        // for fill allTests
        if (!objectOfMatrix.isEmpty()) {
            // Fill unique names
            fillAlltestsNames();


            // fill matrix List
            fillOutcomeMatrix();


             // fill inverse matrix List
            createInverseMatrix();

            //Calculation of distances between tests
            CalculateDistances();

            // Linear regression
            // prioritiseTests();

        }

     if (!objectOfKilledMutant.isEmpty()){

         // division mutations to 20/80
         split20And80Percent();

         CalculateDistancesForPart();

         // Fill unique names with number of mutation killed
         fillAlltestsNamesWithCount();


      }
        // Print out to The Console
        for (int r = 0; r < allTests.size(); r++) {
            int l = allTests.get(r).length();
            l = 35 - l; // Space after the name of the class
            System.out.print('[');
            System.out.print(allTests.get(r) + " ");
            for (; l >= 0; l--) {
                System.out.print(" ");
            }

            for (var z : objectOfMatrix) {
                System.out.print("  " + z.getMatrix().get(r));
            }
            System.out.println("]");

        }
        System.out.println(" *** 20% of MATRIX Which killed mutant *** ");
        for (int r = 0; r < allTests.size(); r++) {
            int le = allTests.get(r).length();
            le = 35 - le; // Space after the name of the class
            System.out.print('[');
            System.out.print(allTests.get(r) + " ");
            for (; le >= 0; le--) {
                System.out.print(" ");
            }

            for (int[] percentOfMatrix : twentyPercentOfMatrix) {
                System.out.print("  " + percentOfMatrix[r]);
            }
            System.out.println("]");
        }

        System.out.println(" 80% of MATRIX which killed mutant ");
        for (int r = 0; r < allTests.size(); r++) {
            int l = allTests.get(r).length();
            l = 35 - l; // Space after the name of the class
            System.out.print('[');
            System.out.print(allTests.get(r) + " ");
            for (; l >= 0; l--) {
                System.out.print(" ");
            }

            for (int p = 0; p < eightyPercentOfMatrix.length; p++) {
                System.out.print("  " + eightyPercentOfMatrix[p][r]);
            }
            System.out.println("]");
        }


        System.out.println("  --------------- ******  DISTANCE 80% of MATRIX with MCC  ****** ---------------");
        for (int ro = 0; ro < DISTANCE_80_MATRIX_MCC.length; ro++) {//var z : DISTANCE_MATRIX) {
            int l = allTests.get(ro).length();
            l = 40 - l; // Space after the name of the class
            System.out.print(allTests.get(ro));
            for (; l >= 0; l--) {
                System.out.print(" ");
            }
            for (int c = 0; c < DISTANCE_80_MATRIX_MCC[ro].length; c++) {
                String vl = String.valueOf(DISTANCE_80_MATRIX_MCC[ro][c]);
                System.out.print("   " + DISTANCE_80_MATRIX_MCC[ro][c]);
                int li = vl.length();
                if (li < 5) {
                    li = 5 - li;
                    for (; li > 0; li--) {
                        System.out.print(" ");
                    }
                }

            }
            System.out.println("");
        }

        System.out.println("  --------------- ******  DISTANCE 80% of MATRIX with ACC  ****** ---------------");
        for (int ro = 0; ro < DISTANCE_80_MATRIX_ACC.length; ro++) {
            int l = allTests.get(ro).length();
            l = 40 - l; // Space after the name of the class
            System.out.print(allTests.get(ro));
            for (; l >= 0; l--) {
                System.out.print(" ");
            }
            for (int c = 0; c < DISTANCE_80_MATRIX_ACC[ro].length; c++) {
                String vl = String.valueOf(DISTANCE_80_MATRIX_ACC[ro][c]);
                System.out.print("   " + DISTANCE_80_MATRIX_ACC[ro][c]);
                int li = vl.length();
                if (li < 5) {
                    li = 5 - li;
                    for (; li > 0; li--) {
                        System.out.print(" ");
                    }
                }

            }
            System.out.println("");
        }

        System.out.println("  --------------- ******  DISTANCE 80% of MATRIX with Fowlkes Mallows  ****** ---------------");
        for (int ro = 0; ro < DISTANCE_80_MATRIX_FowlkesMallows.length; ro++) {
            int l = allTests.get(ro).length();

            l = 40 - l; // Space after the name of the class
            System.out.print(allTests.get(ro));
            for (; l >= 0; l--) {
                System.out.print(" ");
            }
            for (int c = 0; c < DISTANCE_80_MATRIX_FowlkesMallows[ro].length; c++) {
                String vl = String.valueOf(DISTANCE_80_MATRIX_FowlkesMallows[ro][c]);
                System.out.print("   " + DISTANCE_80_MATRIX_FowlkesMallows[ro][c]);
                int li = vl.length();
                if (li < 5) {
                    li = 5 - li;
                    for (; li > 0; li--) {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println("");
        }
        System.out.println(" ''' TEST PRIORITIZATION based on MCC ''' ");
        for (String i : testCasesSMAverage.keySet()) {
            System.out.println("key: " + i + " value: " + testCasesSMAverage.get(i));
        }

        for (var el : PROIRITISETEST) {
            System.out.println(" " + el);
        }


        APFDCalculator APMK = new APFDCalculator();
        // Calculate APMK for 20% of matrix
        APMK.APFD(testCasesSMAverage, allTests, twentyPercentOfMatrix);
    }
static int randomNumber(int max){
        int min =0;
    Random rand = new Random(); //instance of random class
    return rand.nextInt(min, max);
}
    private static void split20And80Percent() {
        int col = objectOfKilledMutant.size();
        int ro = allTests.size();
        int num20 = (int) (col * 20 / 100);
        int num80 = col - num20;
        twentyPercentOfMatrix = new int[num20][ro];
        eightyPercentOfMatrix = new int[num80][ro];
        int count= 0;
        int x =0;
for ( int limiNum=0; limiNum < num20; limiNum++){
    int sz = objectOfKilledMutant.size();
    int ran = randomNumber(sz);
    int y1 =0;
    for (var mut : allTests) {
            if (objectOfKilledMutant.get(ran).testKillingTests.contains(mut)) {
              //  objectOfKilledMutant.get(ran).matrix.add("1");
                twentyPercentOfMatrix[limiNum][y1] = 1;
            } else if (objectOfKilledMutant.get(ran).testSucceedingTests.contains(mut)) {
               // objectOfKilledMutant.get(ran).matrix.add("0");
                twentyPercentOfMatrix[limiNum][y1] = 0;
            } else {
              //  objectOfKilledMutant.get(ran).matrix.add("-");
                twentyPercentOfMatrix[limiNum][y1] = 1;
            }
        y1++;

    }
    objectOfKilledMutant.remove(ran);
}
for(int nm =0; nm < objectOfKilledMutant.size(); nm++){
            int y2 =0;
            for (var mut : allTests) {
             /*   if (count < num20){
                    if (objectOfKilledMutant.get(nm).testKillingTests.contains(mut)) {
                        objectOfKilledMutant.get(nm).matrix.add("1");
                        twentyPercentOfMatrix[nm][y] = 1;
                    } else if (objectOfKilledMutant.get(nm).testSucceedingTests.contains(mut)) {
                        objectOfKilledMutant.get(nm).matrix.add("0");
                        twentyPercentOfMatrix[nm][y] = 0;
                    } else {
                        objectOfKilledMutant.get(nm).matrix.add("-");
                        twentyPercentOfMatrix[nm][y] = 0;
                    }

              // } else { */
                    if (objectOfKilledMutant.get(nm).testKillingTests.contains(mut)) {
                        objectOfKilledMutant.get(nm).matrix.add("1");
                        eightyPercentOfMatrix[x][y2] = 1;
                    } else if (objectOfKilledMutant.get(nm).testSucceedingTests.contains(mut)) {
                        objectOfKilledMutant.get(nm).matrix.add("0");
                        eightyPercentOfMatrix[x][y2] = 0;
                    } else {
                        objectOfKilledMutant.get(nm).matrix.add("-");
                        eightyPercentOfMatrix[x][y2] = 1;
                    }
             //   }
               y2++;
            }
            count++;
           // if (nm >= num20)
                x++;
        }

    }


    private static void prioritiseTests() throws Exception {

        // lr = new LinearRegressionCLASS(MATRIX, DISTANCE_MATRIX);
        double biggerNumber = 0;
        int indexRow = 0, indexCol = 0;
        for (int rowP = 0; rowP < DISTANCE_MATRIX_MCC.length; rowP++) {
            for (int colP = 0; colP < DISTANCE_MATRIX_MCC.length; colP++) {
                if (biggerNumber <= DISTANCE_MATRIX_MCC[rowP][colP]) {
                    biggerNumber = DISTANCE_MATRIX_MCC[rowP][colP];
                    if (!PROIRITISETEST.contains(allTests.get(rowP))) {
                        PROIRITISETEST.add(allTests.get(rowP));
                    }
                    indexRow = rowP;
                    indexCol = colP;
                }
            }
        }
        System.out.println(" " + biggerNumber + " row is " + indexRow + " colum is " + indexCol);
    }

    /**
     * Calculate Distances Test cases by ACC, MCC and Fowlkes Mallows
     */
    private static void CalculateDistances() {


        int col = objectOfMatrix.size();
        int ro = allTests.size();
        MATRIX = new double[ro][col];
        // Init all distance matrix.
        DISTANCE_MATRIX_MCC = new double[ro][ro];
        DISTANCE_MATRIX_ACC = new double[ro][ro];
        DISTANCE_MATRIX_FowlkesMallows = new double[ro][ro];

        double Xd[] = new double[col];
        int X[] = new int[col];
        for (int row = 0; row < allTests.size(); row++) {
            // fill X array
            int numX = 0;
            for (var tst1 : objectOfMatrix) {
                if (tst1.inverseMatrix.get(row).equals("0")) {
                    X[numX] = 0;
                    Xd[numX] = 0;
                } else {
                    X[numX] = 1;
                    Xd[numX] = 1;
                }

                numX++;
            }


            // fill Y array
            for (int column = 0; column < allTests.size(); column++) {
                int Y[] = new int[col];
                int numY = 0;
                for (var ror : objectOfMatrix) {
                    if (ror.matrix.get(column).equals("0")) Y[numY] = 0;
                    else Y[numY] = 1;
                    numY++;
                }
                MATRIX[column] = Xd;
                // Calc mcc & Normalize values between 0 and 1
                //   double valueMCC = MCorrelationCoefficient.calculMCC(X, Y, col);
                double valueMCC = ConfusionMatrix.calMCC(X, Y, col);
                DISTANCE_MATRIX_MCC[row][column] = Double.parseDouble(new DecimalFormat("##.###").format(valueMCC));
                double valueACC = ConfusionMatrix.calACC(X, Y, col);
                //  double valueACC = org.nd4j.evaluation.classification.ConfusionMatrix
                DISTANCE_MATRIX_ACC[row][column] = Double.parseDouble(new DecimalFormat("##.###").format(valueACC));
                double valueFM = ConfusionMatrix.calFowlkesMallows(X, Y, col);
                DISTANCE_MATRIX_FowlkesMallows[row][column] = Double.parseDouble(new DecimalFormat("##.###").format(valueFM));
            }
        }
        //  System.out.println(Arrays.deepToString(DISTANCE_MATRIX));
    }


    private static void CalculateDistancesForPart() {


        int col = eightyPercentOfMatrix.length;
        int ro = eightyPercentOfMatrix[0].length;
        // Init all distance matrix.
        DISTANCE_80_MATRIX_ACC = new double[ro][ro];
        DISTANCE_80_MATRIX_MCC = new double[ro][ro];
        DISTANCE_80_MATRIX_FowlkesMallows = new double[ro][ro];

        double Xd[] = new double[col];
        int X[] = new int[col];
        for (int row = 0; row < ro; row++) {
            // fill X array
            int numX = 0;

            for (var tst1 : eightyPercentOfMatrix[row]) {
                if (tst1 == 0) {
                    X[numX] = 0;
                    Xd[numX] = 0;
                } else {
                    X[numX] = 1;
                    Xd[numX] = 1;
                }

                numX++;
            }


            // fill Y array
            for (int column = 0; column < ro; column++) {
                int Y[] = new int[col];
                int numY = 0;
                for (var ror : eightyPercentOfMatrix[column]) {
                    if (ror ==0)
                        Y[numY] = 0;
                    else
                        Y[numY] = 1;

                    numY++;
                }
                // Calc mcc & Normalize values between 0 and 1
                //   double valueMCC = MCorrelationCoefficient.calculMCC(X, Y, col);
                double valueMCC = ConfusionMatrix.calMCC(X, Y, col);
                DISTANCE_80_MATRIX_MCC[row][column] = Double.parseDouble(new DecimalFormat("##.#######").format(valueMCC));
               double valueACC = ConfusionMatrix.calACC(X, Y, col);
             //    double valueACC = org.nd4j.evaluation.classification.ConfusionMatrix
               DISTANCE_80_MATRIX_ACC[row][column] = Double.parseDouble(new DecimalFormat("##.#######").format(valueACC));

                double valueFM = ConfusionMatrix.calFowlkesMallows(X, Y, col);
               DISTANCE_80_MATRIX_FowlkesMallows[row][column] = Double.parseDouble(new DecimalFormat("##.#######").format(valueFM));
            }
        }
        //  System.out.println(Arrays.deepToString(DISTANCE_MATRIX));
    }

    /**
     * Create Test Outcome Matrix list from classes tests fill it with (0,1)
     * Where is 0 represents a test passed, whereas 1 represents that a test fails.
     */
    private static void createInverseMatrix() {
        for (var mut : allTests) {
            for (var obj : objectOfMatrix) {
                if (obj.testKillingTests.contains(mut)) {
                    obj.inverseMatrix.add("1");
                } else if (obj.testSucceedingTests.contains(mut)) {
                    obj.inverseMatrix.add("0");
                } else obj.inverseMatrix.add("1");
            }
        }
    }

    /**
     * Create Test Outcome Matrix list from classes tests fill it with (0,1)
     * Where is 0 represents a test passed, whereas 1 represents that a test fails.
     */
    private static void fillOutcomeMatrix() {

        for (var obInM : objectOfMatrix) {
            for (var mut : allTests) {
                if (obInM.testKillingTests.contains(mut)) {
                    obInM.matrix.add("1");
                } else if (obInM.testSucceedingTests.contains(mut)) {
                    obInM.matrix.add("0");
                } else {
                    obInM.matrix.add("-");
                }
            }
        }
    }

    /**
     * Fill allTests list with classes names
     */
    private static void fillAlltestsNames() {
        for (var obInM : objectOfMatrix) {
            for (var mut : obInM.testKillingTests) {
                if (!allTests.contains(mut) && !Objects.equals(mut, "")) {
                    allTests.add(mut);
                    //    System.out.println(mut);
                }
            }
        }
    }

    private static void fillAlltestsNamesWithCount() {
        HashMap<String, Double> temp = new HashMap<>();
       int y =0;
       // Arithmetic average
        double sma=0;
       for (int x =0; x < allTests.size(); x++){
           double average =0;
           for (int z =0; z < DISTANCE_80_MATRIX_MCC.length; z++){
               average += DISTANCE_80_MATRIX_MCC[x][z];
           }

           sma = Double.parseDouble(new DecimalFormat("##.####").format(average / (DISTANCE_80_MATRIX_MCC[x].length -1)));

           temp.put(allTests.get(x), sma);
       }
        temp.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> testCasesSMAverage.put(x.getKey(), x.getValue()));
        testCasesSMAverage = sortByValue(temp);
        }
   /*     //
    }*/


    /**
     * makeObjectOfMatrix method to create object of matrix
     *
     * @param muta
     */
    public static void makeMatrix(Mutation muta) {
        MatrixObject nMat = new MatrixObject();
        nMat.sourceName = muta.sourceFile.replace(".java", "");

        // Read killingTests String
        String[] arrOfKilling = muta.killingTests.split("\\|", 20);
        ArrayList<String> nam = new ArrayList<>();
        for (String value : arrOfKilling) {
            nam.add(GetNameMethod(value));
        }
        nMat.setTestKillingTests(nam);

        // Read succeedingTests String
        ArrayList<String> nams = new ArrayList<>();
        String[] arrOfSucceeding = muta.succeedingTests.split("\\|", 20);
        for (String s : arrOfSucceeding) {
            nams.add(GetNameMethod(s));
        }
        nMat.setTestSucceedingTests(nams);

        objectOfMatrix.add(nMat);
    }

    public static void makeObjectOfKilledMutants(Mutation muta) {
        MatrixObject nMat = new MatrixObject();
        nMat.sourceName = muta.sourceFile.replace(".java", "");

        // Read killingTests String tag
        String[] arrOfKilling = muta.killingTests.split("\\|", 20);
        ArrayList<String> nam = new ArrayList<>();
        for (String value : arrOfKilling) {
            nam.add(GetNameMethod(value));
        }
        nMat.setTestKillingTests(nam);

        // Read succeedingTests String
        ArrayList<String> nams = new ArrayList<>();
        String[] arrOfSucceeding = muta.succeedingTests.split("\\|", 20);
        for (String s : arrOfSucceeding) {
            nams.add(GetNameMethod(s));
        }
        nMat.setTestSucceedingTests(nams);

        objectOfKilledMutant.add(nMat);
    }


    /**
     * TO get a name of method which has mutated
     *
     * @param str
     * @return method name
     */
    public static String GetNameMethod(String str) {
        String nm = "";
        int endIndex = str.indexOf("(");
        int dot=0;
        for (int i = endIndex - 1; i > 0; i--) {
            if (str.charAt(i) == '.') {
                if (dot >0){
                    nm = (str.substring(i + 1, endIndex));
                    break;
                }
                dot++;
            }
        }
        return nm;
    }

    /**
     * Sorting HashMap
     *
     * @param HM
     * @return HashMap
     */
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> HM) {

        return HM.entrySet().stream().sorted((i1, i2) -> i2.getValue().compareTo(i1.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
