package se.miun.projectM;

import java.util.Objects;

public class Mutation {
    String id;
    String status;
    String numberOfTestsRun;
    String sourceFile;
    String mutatedClassName;
    String mutatedMethod;
    String lineNumber;
    String mutator;
    String index;
    String block;
    String killingTests;
    String succeedingTests;
    String description;

    String mutatedMethodName;

    String mutationType;
    public Mutation() {
    }

    public Mutation(String id, String status, String numberOfTestsRun, String sourceFile, String mutatedMethod, String lineNumber, String mutator, String index, String block, String killingTests, String succeedingTests, String description) {
        this.id = id;
        this.status = status;
        this.numberOfTestsRun = numberOfTestsRun;
        this.sourceFile = sourceFile;
        this.mutatedMethod = mutatedMethod;
        this.lineNumber = lineNumber;
        this.mutator = mutator;
        this.index = index;
        this.block = block;
        this.killingTests = killingTests;
        this.succeedingTests = succeedingTests;
        this.description = description;
    }

    public Mutation(String mutatedClassName, String mutatedMethodName, String mutatedMethodDescriptionName, String mutatedLineNumber, String mutationType) {
        this.mutatedClassName = mutatedClassName;
        this.mutatedMethodName = mutatedMethodName;
        this.description = mutatedMethodDescriptionName;
        this.lineNumber = mutatedLineNumber;
        this.mutationType = mutationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void setNumberOfTestsRun(String numberOfTestsRun) {
        this.numberOfTestsRun = numberOfTestsRun;
    }
    public void setMutatedMethod(String mutatedMethod) {
        this.mutatedMethod = mutatedMethod;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setMutator(String mutator) {
        this.mutator = mutator;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setKillingTests(String killingTests) {
        this.killingTests = killingTests;
    }

    public void setSucceedingTests(String succeedingTests) {
        this.succeedingTests = succeedingTests;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public String getSourceFile() {
        return sourceFile;
    }
    public String getNumberOfTestsRun() {
        return numberOfTestsRun;
    }
    public String getMutatedMethod() {
        return mutatedMethod;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getMutator() {
        return mutator;
    }

    public String getIndex() {
        return index;
    }

    public String getBlock() {
        return block;
    }

    public String getKillingTests() {
        return killingTests;
    }

    public String getSucceedingTests() {
        return succeedingTests;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mutation mutation = (Mutation) o;
        return Objects.equals(id, mutation.id) && Objects.equals(status, mutation.status) && Objects.equals(numberOfTestsRun, mutation.numberOfTestsRun) && Objects.equals(sourceFile, mutation.sourceFile) && Objects.equals(mutatedMethod, mutation.mutatedMethod) && Objects.equals(lineNumber, mutation.lineNumber) && Objects.equals(mutator, mutation.mutator) && Objects.equals(index, mutation.index) && Objects.equals(block, mutation.block) && Objects.equals(killingTests, mutation.killingTests) && Objects.equals(succeedingTests, mutation.succeedingTests) && Objects.equals(description, mutation.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, numberOfTestsRun, sourceFile, mutatedMethod, lineNumber, mutator, index, block, killingTests, succeedingTests, description);
    }

    @Override
    public String toString() {
        return "mutation{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", sourceFile='" + sourceFile + '\'' +
                ", mutatedMethod='" + mutatedMethod + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                ", mutator='" + mutator + '\'' +
                ", index='" + index + '\'' +
                ", block='" + block + '\'' +
                ", killingTests='" + killingTests + '\'' +
                ", succeedingTests='" + succeedingTests + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
