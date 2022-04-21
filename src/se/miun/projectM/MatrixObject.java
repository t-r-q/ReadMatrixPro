package se.miun.projectM;

import java.util.ArrayList;

public class MatrixObject {
    String id;
    String sourceName;

    ArrayList<String> testKillingTests = new ArrayList<>();
    ArrayList<String> testSucceedingTests = new ArrayList<>();
     //ArrayList<Integer> matrix = new ArrayList<>(); // ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
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
}
