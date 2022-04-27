package se.miun.projectM;

import java.util.ArrayList;

public class MatrixObject {
    String id;
    String sourceName;

    ArrayList<String> testKillingTests = new ArrayList<>();
    ArrayList<String> testSucceedingTests = new ArrayList<>();
    ArrayList<String> inverseMatrix = new ArrayList<>();

    // ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
     ArrayList<String> matrix = new ArrayList<>();
    public MatrixObject() {
    }

    public MatrixObject(String id, String sourceName, ArrayList<String> testKillingTests, ArrayList<String> testSucceedingTests, ArrayList<String> matrix) {
        this.id = id;
        this.sourceName = sourceName;
        this.testKillingTests = testKillingTests;
        this.testSucceedingTests = testSucceedingTests;
        this.matrix = matrix;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public ArrayList<String> getTestKillingTests() {
        return testKillingTests;
    }

    public void setTestKillingTests(ArrayList<String> testKillingTests) {
        this.testKillingTests = testKillingTests;
    }

    public ArrayList<String> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<String> matrix) {
        this.matrix = matrix;
    }

    public ArrayList<String> getTestSucceedingTests() {
        return testSucceedingTests;
    }

    public void setTestSucceedingTests(ArrayList<String> testSucceedingTests) {
        this.testSucceedingTests = testSucceedingTests;
    }

    // ......Find the correlation coefficient between two arrays........
    public double calculMCC(int X[], int Y[], int n){

        int  sum_X = 0, sum_Y = 0, sum_XY = 0, squareSum_X = 0, squareSum_Y = 0;

        for (int i = 0; i < n; i++)
        {
            // sum of elements of array X.
            sum_X = sum_X + X[i];

            // sum of elements of array Y.
            sum_Y = sum_Y + Y[i];

            // sum of X[i] * Y[i].
            sum_XY = sum_XY + X[i] * Y[i];

            // sum of square of array elements.
            squareSum_X = squareSum_X + X[i] * X[i];
            squareSum_Y = squareSum_Y + Y[i] * Y[i];
        }
        // use formula for calculating correlation coefficient.
        float mcc = (float)(n * sum_XY - sum_X * sum_Y)/
                (float)(Math.sqrt((n * squareSum_X -
                        sum_X * sum_X) * (n * squareSum_Y -
                        sum_Y * sum_Y)));

        return mcc;

    }


}
