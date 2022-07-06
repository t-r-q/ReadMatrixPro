package se.miun.projectM.mathLOG;
import java.util.*;

public class APFDCalculator {
    public void APFD(Map<String, Double> testCasesOrder, ArrayList<String> testNames, int[][] mutant){
        Set<String> nameSet;
        nameSet = testCasesOrder.keySet();
        int divideInPercent = testCasesOrder.size()/10;
        int getTheFivePercent = testCasesOrder.size()/20;
        ArrayList<String> nameTest = new ArrayList<String>(nameSet);
        int [][] mySetsOfMutantsForAPFD = new int[mutant.length][mutant[0].length];

        ArrayList<Integer> TFi = new ArrayList<Integer>();

        int count, count1 = 0, count2 = 0;
        float APFD =0;

        for (int x =0; x < testCasesOrder.size(); x++) {
            for (int i =0; i < testCasesOrder.size(); i++) {
                if (Objects.equals(nameTest.get(x), testNames.get(i))) {
                    for (int j =0; j < mutant[0].length; j++) {
                        mySetsOfMutantsForAPFD[x][j] = mutant[i][j];
                    }
                }
            }
        }

        System.out.println(" If the number of test cases is less than 15, then the result show the " +
                "number of mutant killed per test.");
        System.out.print( "Test: ");
        int[] tal = new int[mySetsOfMutantsForAPFD[0].length];
        int ones = 0;
        int zeroes = mySetsOfMutantsForAPFD[0].length;
        for (int x =0; x < mySetsOfMutantsForAPFD.length; x++) {
            for (int i =0; i < mySetsOfMutantsForAPFD[0].length; i++) {
                if (mySetsOfMutantsForAPFD[x][i] == 1){
                    if (tal[i] != 1){
                        ones++;
                        tal[i] = 1;
                    }
                }
            }
            if (count2 == 11)
                break;
            count1++;
            zeroes = zeroes - ones;
            if (testCasesOrder.size() < 15){
                System.out.print(ones + " ");
                zeroes = mySetsOfMutantsForAPFD[0].length;
            } else if (count1 >= divideInPercent || count1 == 1 || count1 == getTheFivePercent){
                float mutantPercent = ((float) 100/mySetsOfMutantsForAPFD[0].length);
                count2++;
                System.out.print(ones * mutantPercent + " ");
                if (count1 != 1){
                    divideInPercent = divideInPercent + testCasesOrder.size()/10;
                }

            }
        }
        System.out.println(" ");

        for (int x =0; x < mySetsOfMutantsForAPFD[0].length; x++) {

            count =0;
            for (int[] mySetsOfMutant : mySetsOfMutantsForAPFD) {
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

        //float random = randomPrioritization(testNames, mutant);
        System.out.println(" ******************* APFD = " + APFD);
    }

    public void randomPrioritization(ArrayList<String> testNames, int[][] mutant) {
        ArrayList<String> nameTest = new ArrayList<String>(testNames);
        Collections.shuffle(nameTest);
        int sizeOfTestSuite = testNames.size();
        int [][] mySetsOfMutants = new int[mutant.length][mutant[0].length];
        ArrayList<Integer> TFi = new ArrayList<Integer>();
        int divideInPercent = sizeOfTestSuite/10;
        int getTheFivePercent = sizeOfTestSuite/20;


        int count, count1 = 0, count2 = 0;
        float APFD = 0;
        for (int x =0; x < sizeOfTestSuite; x++) {
            for (int i =0; i < sizeOfTestSuite; i++) {
                if (Objects.equals(nameTest.get(x), testNames.get(i))) {
                    for (int j =0; j < mutant[0].length; j++) {
                        mySetsOfMutants[x][j] = mutant[i][j];
                    }
                }
            }
        }

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
            count1++;
            if (count2 == 11)
                break;
            if (sizeOfTestSuite < 15){
                System.out.print(ones + " ");
                zeroes = mySetsOfMutants[0].length;
            } else if (count1 >= divideInPercent || count1 == 1 || count1 == getTheFivePercent){
                float mutantPercent = ((float) 100/mySetsOfMutants[0].length);
                count2++;
                System.out.print(ones * mutantPercent + " ");
                if (count1 != 1){
                    divideInPercent = divideInPercent + sizeOfTestSuite/10;
                }
            }
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

        APFD = (float) (1 - (sum / (numbFaults * testSize)) + ((float) 1 / (2 * testSize)));
        System.out.println("************************** Random = " + APFD);
        //return APFD;
    }
}
