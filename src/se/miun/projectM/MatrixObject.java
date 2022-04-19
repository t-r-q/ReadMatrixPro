package se.miun.projectM;

import java.util.ArrayList;

public class MatrixObject {
    String id;
    String sourceName;

    ArrayList<String> testCaseMember = new ArrayList<>();
     ArrayList<Integer> matrix = new ArrayList<>(); // ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();

    public MatrixObject() {
    }

    public MatrixObject(String id, String sourceName, ArrayList<String> testCaseMember, ArrayList<Integer> matrix) {
        this.id = id;
        this.sourceName = sourceName;
        this.testCaseMember = testCaseMember;
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

    public ArrayList<String> getTestCaseMember() {
        return testCaseMember;
    }

    public void setTestCaseMember(ArrayList<String> testCaseMember) {
        this.testCaseMember = testCaseMember;
    }

    public ArrayList<Integer> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<Integer> matrix) {
        this.matrix = matrix;
    }
}
