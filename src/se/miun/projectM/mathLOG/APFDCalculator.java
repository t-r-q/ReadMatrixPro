package se.miun.projectM.mathLOG;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class APFDCalculator {
    public APFDCalculator() {

    }


    public void APFD(Map<String, Double> testCasesOrder, ArrayList<String> testNames, int[][] mutant){
     int[] totalListOfMutant = new int[mutant.length];
        ArrayList<Integer> posi = new ArrayList<Integer>();
        ArrayList<Integer> TFi = new ArrayList<Integer>();
      int size = mutant.length;

        int indexTN =0;
        int count = 0;
        float percent =0;

        for (var tc : testCasesOrder.keySet()) {
            // check name in order list and return index of test name
            if (testNames.contains(tc)){
                count =0;
               // return index
                indexTN = testNames.indexOf(tc);
                System.out.print("  *** Test name is:" + testNames.get(indexTN) +".     '' ");
            }else
                System.err.println(" Error in  APMK finding test name in the list");

            for (int x =0; x < mutant.length; x++) {
                if (mutant[x][indexTN] == 1){
                   if (totalListOfMutant[x]!=1){
                       totalListOfMutant[x] = 1;
                       count++;
                   }
                }
            }
            TFi.add(count);
            percent += ((double)( count * 100) / size);
            if (count !=0){
                System.out.println("Has count of the test: (" + count  + "). The Percent: " +  (int)percent + "% ''");
                posi.add((int) percent);
            }
            else{
                System.out.println("Has not any new test. The Percent: " +  (int)percent + "%");
                posi.add((int) percent);
            }

        }
        System.out.println(" \n Simulation of the mutants discovery sequencing process according to test priority.");

        for (var ie : posi) {
            System.out.print(ie + " ");
        }

        APFDCalculate(TFi, mutant.length, mutant[0].length);
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
