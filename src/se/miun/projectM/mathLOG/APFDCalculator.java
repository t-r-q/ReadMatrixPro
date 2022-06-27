package se.miun.projectM.mathLOG;


import java.text.DecimalFormat;
import java.util.*;

public class APFDCalculator {
    public APFDCalculator() {

    }


    public void APFD(Map<String, Double> testCasesOrder, ArrayList<String> testNames, int[][] mutant){
     int[] totalListOfMutant = new int[mutant.length];
        ArrayList<Integer> posi = new ArrayList<Integer>();
        Set<String> nameSet;
        nameSet = testCasesOrder.keySet();
        ArrayList<String> nameTest = new ArrayList<String>(nameSet);


        int [][] mySetsOfMutants = new int[mutant.length][mutant[0].length];
        int [][] prioritizingBasedOnProcent = new int[mutant.length][mutant[0].length];





        ArrayList<Integer> TFi = new ArrayList<Integer>();
      int size = mutant.length;

        int count = 0;
        float APFD =0;

        for (int x =0; x < testCasesOrder.size(); x++) {
            for (int i =0; i < testCasesOrder.size(); i++) {
                if (Objects.equals(nameTest.get(x), testNames.get(i))) {
                    for (int j =0; j < mutant[0].length; j++) {
                        mySetsOfMutants[x][j] = mutant[i][j];
                    }
                }
            }
        }

        int[][] test = new int [9][45];
        for (int x =0; x < testNames.size(); x++) {
            for (int i =0; i < nameTest.size(); i++){
                if (nameTest.get(x) == testNames.get(i)){
                    System.out.println(nameTest.get(i));
                    for (int j =0; j < mySetsOfMutants[0].length; j++) {
                        prioritizingBasedOnProcent[x][j] = mutant[i][j];
                    }
                }
            }
        }

        int counti = 0;
        System.out.println("MCC prioritized list!");
        System.out.print( "Test: ");
        int[] tal = new int[prioritizingBasedOnProcent[0].length];
        int ones = 0;
        int zeroes = prioritizingBasedOnProcent[0].length;
        for (int x =0; x < prioritizingBasedOnProcent.length; x++) {
            for (int i =0; i < prioritizingBasedOnProcent[0].length; i++) {
                if (prioritizingBasedOnProcent[x][i] == 1){
                    if (tal[i] != 1){
                        ones++;
                        tal[i] = 1;
                    }

                }
            }
            zeroes = zeroes - ones;
            counti++;
            System.out.print(ones + " ");
            zeroes = prioritizingBasedOnProcent[0].length;
        }
        System.out.println(" ");



        for (int x =0; x < mySetsOfMutants[0].length; x++) {

            count =0;
            for (int[] mySetsOfMutant : mySetsOfMutants) {
                if (mySetsOfMutant[x] == 1) {
                    count++;
                    TFi.add(count);
                    break;
                } else {
                    count++;
                }

            }
        }

        double sum = 0;
        for(Integer d : TFi)
            sum += d;

        int testSize = testNames.size();
        int numbFaults = mutant[0].length;


        APFD = (float) (1 - (sum / (numbFaults * testSize)) + ((float) 1 / (2 * testSize)));

        float normal = Calcoly( testCasesOrder,  testNames, mutant);

        System.out.println(" ******************* APFD = " + APFD + " ******* Normal = " + normal);

        for (var ie : posi) {
            System.out.print(ie + " ");
        }

        APFDCalculate(TFi, mutant.length, mutant[0].length);
    }









    private float Calcoly(Map<String, Double> testCasesOrder, ArrayList<String> testNames, int[][] mutant) {
        ArrayList<Integer> posi = new ArrayList<Integer>();
        Set<String> nameSet;
        nameSet = testCasesOrder.keySet();
        ArrayList<String> nameTest = new ArrayList<String>(nameSet);
        Collections.shuffle(nameTest);

        int [][] mySetsOfMutants = new int[mutant.length][mutant[0].length];




        ArrayList<Integer> TFi = new ArrayList<Integer>();

        int count = 0;
        float APFD = 0;


        for (int x =0; x < testCasesOrder.size(); x++) {
            for (int i =0; i < testCasesOrder.size(); i++) {
                if (Objects.equals(nameTest.get(x), testNames.get(i))) {
                    for (int j =0; j < mutant[0].length; j++) {
                        mySetsOfMutants[x][j] = mutant[i][j];
                    }
                }
            }
        }


        int counti = 0;
        System.out.println("Random prioritized list.");
        System.out.print( "Test: ");

        int[] tal = new int[mySetsOfMutants[0].length];
        int ones = 0;

        int zeroes = mySetsOfMutants[0].length;
        for (int x =0; x < mySetsOfMutants.length; x++) {
            for (int i =0; i < mySetsOfMutants[0].length; i++) {
                if (mySetsOfMutants[x][i] == 1){
                    if (tal[i] != 1){
                        ones++;
                        tal[i] = 1;
                    }
                }
            }
            zeroes = zeroes - ones;
            counti++;
            System.out.print(ones + " ");
            zeroes = mySetsOfMutants[0].length;
        }

        System.out.println(" ");

        for (int x =0; x < mySetsOfMutants[0].length; x++) {

            count =0;
            for (int i =0; i < mySetsOfMutants.length; i++) {
                if (mySetsOfMutants[i][x] == 1){
                    count++;
                    TFi.add(count);
                    break;
                } else {
                    count++;
                }

            }
        }


        double sum = 0;
        for (Integer d : TFi)
            sum += d;

        int testSize = testNames.size();
        int numbFaults = mutant[0].length;


        //APFD = (float) (1 - (sum / (numbFaults * testSize)) + (1 / (2 * testSize)));
        APFD = (float) (1 - (sum / (numbFaults * testSize)) + ((float) 1 / (2 * testSize)));
        return APFD;
    }














    public void APFD(ArrayList<String> allTests, int[][] mut) {
        int[] totalLOfMutant = new int[mut[0].length];
        ArrayList<Integer> p = new ArrayList<Integer>();
        ArrayList<Integer> TFi = new ArrayList<Integer>();
        int size = mut[0].length;
        int count = 0;
        float perc =0;
        for (int x =0; x < mut[0].length; x++) {
            count =0;
            System.out.print("  *** Test name is:" + allTests.get(x) +".     ** ");
            for (int r =0; r < mut.length; r++){
                    if (mut[r][x] == 1){
                        if (totalLOfMutant[x]!=1){
                            totalLOfMutant[x] = 1;
                            count++;
                        }
                    }
                }
            TFi.add(count);
                perc += ((float) ( count * 100) / size);
                if (count !=0){
                    System.out.println("Has count of the test: (" + count  + "). The Percent: " +  (int)perc + "% ** ");
                    p.add((int) perc);
                }
                else{
                    System.out.println("Has not any new test. The Percent: " +  (int)perc + "%");
                    p.add((int) perc);
                }

        }

        System.out.println(" \n Simulation of the mutants discovery sequencing process according to test priority.");
        for (var ie : p) {
            System.out.print(ie + " ");
        }

        System.out.println(" ");
        APFDCalculate(TFi, mut.length, mut[0].length);
    }

    public void APFDCalculate(ArrayList<Integer> TFi, int n, int m){
double apfd ;
int cal =0;
        double firstPart =0, secondPart=0;
        for (var itm : TFi) {
            cal += itm;
        }
        firstPart = (float) cal / (n*m);
        secondPart = (float) (1 / (2*n));
        apfd = Double.parseDouble(new DecimalFormat("##.###").format(1- firstPart + secondPart));

        System.out.println(" APFD:  " + apfd);
    }
}
