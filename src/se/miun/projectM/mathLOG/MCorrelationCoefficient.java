package se.miun.projectM.mathLOG;

public class MCorrelationCoefficient {

    public static double calculMCC(int x[], int y[], int n){
        float sumX = 0;
        float sumY = 0;
        float sumXY = 0;
        float squareSumX2 = 0;
        float squareSumY2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            squareSumX2 += x[i] * x[i];
            squareSumY2 += y[i] * y[i];
        }

        final double bottom = Math.sqrt((n * squareSumX2 - sumX * sumX) * (n * squareSumY2 - sumY * sumY));
        if (bottom == 0)
            return 0;
        final double top = n * sumXY - sumX * sumY;
        return  (top / bottom);

    }


}
