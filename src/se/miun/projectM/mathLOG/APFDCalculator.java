package se.miun.projectM.mathLOG;


import java.util.ArrayList;
import java.util.Map;

public class APFDCalculator {
    public APFDCalculator() {

    }


    public void APFD(Map<String, Double> testCasesOrder, ArrayList<String> testNames, int[][] mutant){
     int[] totalListOfMutant = new int[mutant.length];
     
      int size = mutant.length;

        int indexTN =0;
        int count = 0;
        double percent =0;
        for (var tc : testCasesOrder.keySet()) {
            // check name in order list and return index of test name
            if (testNames.contains(tc)){
                count =0;
               // return index
                indexTN = testNames.indexOf(tc);
                System.out.println(" test name " + testNames.get(indexTN) );
            }else
                System.err.println(" Error in  APMK finding test name in the list");

            for (int x =0; x < mutant.length; x++) {

               // tp = ctP * testPercent;
                if (mutant[x][indexTN] == 1){
                   if (totalListOfMutant[x]!=1){
                       totalListOfMutant[x] = 1;
                       System.out.println(totalListOfMutant[x] + " index " + x);
                       count++;
                   }
                }
            }

            percent += ((double)( count * 100) / size);
            System.out.println(" The count of the test this has " + count  + " In Percent " +  (int)percent + "%  ");
        }
    }

}
