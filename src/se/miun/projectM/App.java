package se.miun.projectM;


import se.miun.projectM.mathLOG.APFDCalculator;
import se.miun.projectM.mathLOG.ConfusionMatrix;
import se.miun.projectM.read.MutationKilledParser;
import java.lang.Object;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class App {
    // This FILENAME is a Path file for testing only
    private static final String FILENAME = "db/mutations0.xml";
    //private static final String FILENAME = "db/game-of-life-mutation-test.xml";
    //private static final String FILENAME = "db/commons-codec.xml";
    //private static final String FILENAME = "db/algorithms-miscellaneous-6.xml";
    //private static final String FILENAME = "db/jackson-core.xml";
    //private static final String FILENAME = "db/mp4parser.xml";
    //private static final String FILENAME = "db/jsoup.xml";
    static ArrayList<Mutation> mutations = new ArrayList<>();
   // static ArrayList<MatrixObject> objectOfMatrix = new ArrayList<>();
    static ArrayList<MatrixObject> objectOfKilledMutant = new ArrayList<>();
    static ArrayList<MatrixObject> objectOfKilledMutantCopy = new ArrayList<>();
    static ArrayList<String> allTests = new ArrayList<>();
    static Map<String, Double> testCasesSMAverageMCC = new HashMap<>();
    static Map<String, Double> testCasesSMAverageACC = new HashMap<>();
    static Map<String, Double> testCasesSMAverageFM = new HashMap<>();
    static Map<String, Double> testCasesSMAverageRANDOM = new HashMap<>();
    static int[][] twentyPercentOfMatrix;
    static int[][] eightyPercentOfMatrix;

    static double[][] DISTANCE_80_MATRIX_MCC;
    static double[][] DISTANCE_80_MATRIX_ACC;
    static double[][] DISTANCE_80_MATRIX_FowlkesMallows;


    public static void main(String[] args) throws Exception {

        // Read XML file and fill ObjectOfMatrix
        MutationKilledParser pars = new MutationKilledParser(FILENAME, mutations);

        mutations = pars.readFile();

        // Greate matrix for killed mutants ****
        for (var m : mutations) {
          if (m.status.equals("KILLED") && Integer.parseInt(m.numberOfTestsRun) > 1)
            makeObjectOfKilledMutants(m);
        }


        // for fill allTests
        if (!objectOfKilledMutant.isEmpty()){
            // Fill unique names
            fillAlltestsNames();

            // fill matrix List
            fillOutcomeMatrix();

            // division mutations to 20/80
            split20And80Percent();

            CalculateDistancesForPart();

            //CalculateDistancesForPart11111();

            // Fill unique names with number of mutation killed
            fillAlltestsNamesWithCount();


        }

// Print out to The Console a Matrix
        for (int r = 0; r < allTests.size(); r++) {
            int l = allTests.get(r).length();
            l = 35 - l; // Space after the name of the class
            System.out.print('[');
            System.out.print(allTests.get(r) + " ");
            for (; l >= 0; l--) {
                System.out.print(" ");
            }

            for (var z : objectOfKilledMutantCopy) {
                System.out.print("  " + z.getMatrix().get(r));
            }
            System.out.println("]");

        }

// Print 20% of the MATRIX
        System.out.println("  \n *** 20% of MATRIX Which killed mutant *** ");

        for (int r1 = 0; r1 < allTests.size(); r1++) {
            int le = allTests.get(r1).length();
            le = 35 - le; // Space after the name of the class
            System.out.print('[');
            System.out.print(allTests.get(r1) + " ");
            for (; le >= 0; le--) {
                System.out.print(" ");
            }
            int bb = twentyPercentOfMatrix[0].length;

            for(int i = 0; i < 1 ; i++)
            {
                for(int j = 0; j < bb; j++)
                {
                    System.out.print("  " + twentyPercentOfMatrix[r1][j]);
                }
            }
            System.out.println("]");
        }
// Print 80% of the MATRIX
        System.out.println(" \n 80% of MATRIX which killed mutant ");
        for (int r2 = 0; r2 < allTests.size(); r2++) {
            int l = allTests.get(r2).length();
            l = 35 - l; // Space after the name of the class
            System.out.print('[');
            System.out.print(allTests.get(r2) + " ");
            for (; l >= 0; l--) {
                System.out.print(" ");
            }

            int bb = eightyPercentOfMatrix[0].length;

            for(int i = 0; i < 1 ; i++)
            {
                for(int j = 0; j < bb; j++)
                {
                    System.out.print("  " + eightyPercentOfMatrix[r2][j]);
                }
            }

            System.out.println("]");
        }

// Print The MATRIX with MCC
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
                if (li < 7) {
                    li = 7 - li;
                    for (; li > 0; li--) {
                        System.out.print(" ");
                    }
                }

            }
            System.out.println();
        }
// Print The MATRIX with ACC
        System.out.println("  --------------- ******  DISTANCE 80% of MATRIX with ACC  ****** ---------------");
        /*for (int ro = 0; ro < DISTANCE_80_MATRIX_ACC.length; ro++) {
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
                if (li < 7) {
                    li = 7 - li;
                    for (; li > 0; li--) {
                        System.out.print(" ");
                    }
                }

            }
            System.out.println("");
        }*/
// Print The MATRIX with FM
        System.out.println("  --------------- ******  DISTANCE 80% of MATRIX with Fowlkes Mallows  ****** ------");
        /*for (int ro = 0; ro < DISTANCE_80_MATRIX_FowlkesMallows.length; ro++) {
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
                if (li < 7) {
                    li = 7 - li;
                    for (; li > 0; li--) {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println("");
        }*/

        System.out.println(" ''' TEST PRIORITIZATION based on MCC ''' ");
        for (String i : testCasesSMAverageMCC.keySet()) {
            System.out.println("key: " + i + " VALUE is: " + testCasesSMAverageMCC.get(i));
        }

        System.out.println(" ''' TEST PRIORITIZATION based on ACC ''' ");
        /*for (String i : testCasesSMAverageACC.keySet()) {
            System.out.println("key: " + i + " VALUE is: " + testCasesSMAverageACC.get(i));
        }*/

        System.out.println(" ''' TEST PRIORITIZATION based on FM ''' ");
        /*for (String i : testCasesSMAverageFM.keySet()) {
            System.out.println(i + " VALUE is: " + testCasesSMAverageFM.get(i));
        }*/


        APFDCalculator APMK = new APFDCalculator();
        // Calculate APMK for 20% of matrix
        System.out.println(" \n  Drawing the APFD for MCC process on the console ");
        APMK.APFD(testCasesSMAverageMCC, allTests, twentyPercentOfMatrix);
        System.out.println(" \n  Drawing the APFD for ACC process on the console ");
        //APMK.APFD(testCasesSMAverageACC, allTests, twentyPercentOfMatrix);
        System.out.println(" \n  Drawing the APFD for FM process on the console ");
        //APMK.APFD(testCasesSMAverageFM, allTests, twentyPercentOfMatrix);
        System.out.println(" \n  Drawing the APFD for Random process on the console ");
        //APMK.APFD(allTests, twentyPercentOfMatrix);
    }


    private static void CalculateDistancesForPart() {
        int col = eightyPercentOfMatrix.length;
        int ro = eightyPercentOfMatrix[0].length;
        // Init all distance matrix.
        DISTANCE_80_MATRIX_ACC = new double[col][col];
        DISTANCE_80_MATRIX_MCC = new double[col][col];
        DISTANCE_80_MATRIX_FowlkesMallows = new double[col][col];

        double Xd[] = new double[ro];
        int X[] = new int[ro];


        for (int row = 0; row < col; row++) {
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
            for (int column = 0; column < col; column++) {
                int Y[] = new int[ro];
                int numY = 0;
                for (var ror : eightyPercentOfMatrix[column]) {
                    if (ror ==0)
                        Y[numY] = 0;
                    else
                        Y[numY] = 1;

                    numY++;
                }

                double valueMCC = ConfusionMatrix.calMCC(X, Y, ro);
                DISTANCE_80_MATRIX_MCC[row][column] = Double.parseDouble(new DecimalFormat("##.####").format(valueMCC));
                double valueACC = ConfusionMatrix.calACC(X, Y, ro);
                //    double valueACC = org.nd4j.evaluation.classification.ConfusionMatrix
                DISTANCE_80_MATRIX_ACC[row][column] = Double.parseDouble(new DecimalFormat("##.####").format(valueACC));

                double valueFM = ConfusionMatrix.calFowlkesMallows(X, Y, col);
                DISTANCE_80_MATRIX_FowlkesMallows[row][column] = Double.parseDouble(new DecimalFormat("##.####").format(valueFM));
            }
        }
    }

    static int randomNumber(int max){
        int min =0;
    Random rand = new Random(); //instance of random class
    return rand.nextInt(min, max);
}


    private static void split20And80Percent() {

        objectOfKilledMutantCopy = new ArrayList<>(objectOfKilledMutant);


        int col = objectOfKilledMutant.size();
        int ro = allTests.size();
        int num20 = (int) (col * 20 / 100);
        int num80 = col - num20;
        twentyPercentOfMatrix = new int[ro][num20];
        eightyPercentOfMatrix = new int[ro][num80];
        int count= 0;
        int x =0;
      // fill array twentyPercentOfMatrix with 20% random data of test cases
    for ( int limiNum=0; limiNum < num20; limiNum++){
    int sz = objectOfKilledMutant.size();
    int ran = randomNumber(sz);
    int y1 =0;
    for (var mut : allTests) {
            if (objectOfKilledMutant.get(ran).testKillingTests.contains(mut)) {
              //  objectOfKilledMutant.get(ran).matrix.add("1");
                twentyPercentOfMatrix[y1][limiNum] = 1;
            } else if (objectOfKilledMutant.get(ran).testSucceedingTests.contains(mut)) {
               // objectOfKilledMutant.get(ran).matrix.add("0");
                twentyPercentOfMatrix[y1][limiNum] = 0;
            } else {
              //  objectOfKilledMutant.get(ran).matrix.add("-");
                twentyPercentOfMatrix[y1][limiNum] = 0;
            }
        y1++;

    }
    objectOfKilledMutant.remove(ran);
    }
        // fill array twentyPercentOfMatrix with 80% from data of test cases
    for(int nm =0; nm < objectOfKilledMutant.size(); nm++){
            int y2 =0;
            for (var mut : allTests) {
                    if (objectOfKilledMutant.get(nm).testKillingTests.contains(mut)) {
                        //objectOfKilledMutant.get(nm).matrix.add("1");
                        eightyPercentOfMatrix[y2][x] = 1;
                    } else if (objectOfKilledMutant.get(nm).testSucceedingTests.contains(mut)) {
                      //  objectOfKilledMutant.get(nm).matrix.add("0");
                        eightyPercentOfMatrix[y2][x] = 0;
                    } else {
                      //  objectOfKilledMutant.get(nm).matrix.add("-");
                        eightyPercentOfMatrix[y2][x] = 0;
                    }
             //   }
               y2++;
            }
            count++;
           // if (nm >= num20)
                x++;
        }

    }

    /**
     * Create Test Outcome Matrix list from classes tests fill it with (0,1)
     * Where is 0 represents a test passed, whereas 1 represents that a test fails.
     */
    private static void fillOutcomeMatrix() {

        for (var obInM : objectOfKilledMutant) {
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


        for (var obInM : objectOfKilledMutant) {
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
        temp = testCasesSMAverag(DISTANCE_80_MATRIX_MCC);
        temp.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> testCasesSMAverageMCC.put(x.getKey(), x.getValue()));
        testCasesSMAverageMCC = sortByValue(temp);

        temp.clear();
        /*temp = testCasesSMAverag(DISTANCE_80_MATRIX_ACC);
        temp.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> testCasesSMAverageACC.put(x.getKey(), x.getValue()));
        testCasesSMAverageACC = sortByValue(temp);

        temp.clear();
        temp = testCasesSMAverag(DISTANCE_80_MATRIX_FowlkesMallows);
        temp.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> testCasesSMAverageFM.put(x.getKey(), x.getValue()));
        testCasesSMAverageFM = sortByValue(temp);*/

        }

        static HashMap<String, Double> testCasesSMAverag(double[][] arr){
            HashMap<String, Double> tmp = new HashMap<>();
            double sma=0;
            for (int x =0; x < allTests.size(); x++){
                double average =0;
                for (int z =0; z < arr.length; z++){
                    average += arr[x][z];
                }

                sma = Double.parseDouble(new DecimalFormat("##.####").format(average / (arr[x].length -1)));

                tmp.put(allTests.get(x), sma);
            }
            return tmp;
        }


    public static void makeObjectOfKilledMutants(Mutation muta) {
        MatrixObject nMat = new MatrixObject();
        nMat.mutStatus = muta.status;
        nMat.numberOTR = Integer.parseInt(muta.numberOfTestsRun);
        nMat.sourceName = muta.sourceFile.replace(".java", "");
        // Read killingTests String tag
        String[] arrOfKilling = muta.killingTests.split("\\|", 20);
        ArrayList<String> nam = new ArrayList<>();
        for (String value : arrOfKilling) {  nam.add(GetNameMethod(value)); }
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
            if (str.charAt(i) == '.' || str.charAt(i) == '/') {
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
