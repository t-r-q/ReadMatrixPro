package se.miun.projectM.mathLOG;


import java.util.*;


public class ConfusionMatrix {

    /**
     *
     * @param x 2D array is Matrix of mutation testing
     * @param y  2D array is reverse Matrix of mutation testing
     * @param n size of array
     * @return
     */
    public static double calMCC(int x[], int y[], int n){
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
         double top = n * sumXY - sumX * sumY;
        return  ((1 - (top / bottom)) / 2);

    }

    /**
     *
     * @param x 2D array is Matrix of mutation testing
     * @param y 2D array is reverse Matrix of mutation testing
     * @param n  size of array
     * @return
     */
    public static double calACC(int x[], int y[], int n){
         int TPe=0, FNe=0, FPe=0, TNe=0;

        for (int i = 0; i < n; i++) {
            if(x[i] == 1 && y[i] == 1){
                TPe++;
            } else if (x[i] == 0 && y[i] == 1) {
                FPe++;
            }else if (x[i] == 1 && y[i] == 0) {
              FNe++;
            }else
              TNe++;
        }

        final double bottom = (TPe + TNe + FPe + FNe);
        if (bottom == 0){
            return 0;
        }else {
            final double top = TPe + TNe;
            double v = 1-(top / bottom);
            return v;
        }
    }

    /**
     * Fowlkes–Mallows index
     * @param x 2D array is Matrix of mutation testing
     * @param y 2D array is reverse Matrix of mutation testing
     * @param n  size of array
     * @return
     */
    public static double calFowlkesMallows(int x[], int y[], int n){
        int TPf=0, FNf=0, FPf=0, TNf=0;

        for (int i = 0; i < n; i++) {
            if(x[i] == 1 && y[i] == 1){
                TPf++;
            } else if (x[i] == 0 && y[i] == 1) {
                FPf++;
            }else if (x[i] == 1 && y[i] == 0) {
                FNf++;
            }else
                TNf++;
        }
        //  precision or PPV is the positive predictive rate
        double precision = Precision(TPf, FPf);

        // Recall or TPR is the true positive rate
        double recall =Recall(TPf, FNf);
            double Fm = Math.sqrt(precision * recall);
            return  (1- Fm);
    }

    /**
     * Recall is the true positive rate
     * @param tp
     * @param fn
     * @return double value
     */
    private static double Recall(int tp, int fn) {
        final double bottom = (tp + fn);
        if (bottom == 0){
            return 0;
        }else
            return  (tp / bottom);
    }

    /**
     * Precision is the positive predictive rate
     * @param tp
     * @param fp
     * @return double value
     */
    private static double Precision(int tp, int fp) {

        final double bottom = (tp + fp);
        if (bottom == 0){
            return 0;
        }else
            return  (tp / bottom);
    }

}
