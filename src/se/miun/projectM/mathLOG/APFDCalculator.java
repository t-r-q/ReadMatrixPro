package se.miun.projectM.mathLOG;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class APFDCalculator {
    public APFDCalculator() {

    }


    public void APFD(Map<String, Double> testCasesOrder, ArrayList<String> testNames, int[][] mutant){
     int[] totalListOfMutant = new int[mutant.length];
        ArrayList<Integer> p = new ArrayList<Integer>();
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
            percent += ((double)( count * 100) / size);
            if (count !=0){
                System.out.println("Has count of the test: (" + count  + "). The Percent: " +  (int)percent + "% ''");
                p.add((int) percent);
            }
            else{
                System.out.println("The Percent: " +  (int)percent + "%");
                p.add((int) percent);
            }

        }

        for (var ie : p) {
            System.out.print(ie + " ");
        }
    }

    public void APFD(ArrayList<String> allTests, int[][] mut) {
        int[] totalLOfMutant = new int[mut.length];
        ArrayList<Integer> p = new ArrayList<Integer>();
        int size = mut[0].length;
        int count = 0;
        float perc =0;
        for (int r =0; r < allTests.size(); r++){
            count =0;
            System.out.print("  *** Test name is:" + allTests.get(r) +".     ** ");
                for (int x =0; x < mut.length; x++) {
                    if (mut[x][r] == 1){
                        if (totalLOfMutant[x]!=1){
                            totalLOfMutant[x] = 1;
                            count++;
                        }
                    }
                }
                perc += ((float) ( count * 100) / size);
                if (count !=0){
                    System.out.println("Has count of the test: (" + count  + "). The Percent: " +  (int)perc + "% ** ");
                    p.add((int) perc);
                }
                else{
                    System.out.println("The Percent: " +  (int)perc + "%");
                    p.add((int) perc);
                }

        }
        for (var ie : p) {
            System.out.print(ie + " ");
        }
    }

}
