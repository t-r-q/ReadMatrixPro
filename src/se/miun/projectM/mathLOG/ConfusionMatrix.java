package se.miun.projectM.mathLOG;

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

        int tp = 0, tn = 0, fp = 0, fn = 0;
        double tPfP, tPfN, tNfP, tNfN;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            if (x[i] == 0 && y[i] == 0){
                tn++;
            } else if (x[i] == 1 && y[i] == 0){
                fn++;
            } else if (x[i] == 0 && y[i] == 1){
                fp++;
            } else if (x[i] == 1 && y[i] == 1){
                tp++;
            }
        }
        int upper = (tp * tn) - (fp * fn);

        tPfP = tp + fp;
        tPfN = tp + fn;
        tNfP = tn + fp;
        tNfN = tn + fn;

        double sqe = tPfP * tPfN * tNfP * tNfN;
        float sqr = (float) Math.sqrt(sqe);
        float total = ((1 - (upper / sqr)) / 2);
        if (upper == 0 || sqe == 0){
            return 0;
        }

        return total;
    }

    /**
     *
     * @param x 2D array is Matrix of mutation testing
     * @param y 2D array is reverse Matrix of mutation testing
     * @param n  size of array
     * @return
     */
    public static double calACC(int x[], int y[], int n){
        float sumX = 0;
        float sumY = 0;

        int tp = 0, tn = 0, fp = 0, fn = 0;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            if (x[i] == 0 && y[i] == 0){
                tn++;
            } else if (x[i] == 1 && y[i] == 0){
                fn++;
            } else if (x[i] == 0 && y[i] == 1){
                fp++;
            } else if (x[i] == 1 && y[i] == 1){
                tp++;
            }
        }

        final double bottom = (tp + tn + fp + fn);
        if (bottom == 0){
            return 0;
        }else {
            final double top = tp + tn;
            double v = 1-( (double) top / bottom);
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
        float sumX = 0;
        float sumY = 0;

        int tp = 0, tn = 0, fp = 0, fn = 0;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            if (x[i] == 0 && y[i] == 0){
                tn++;
            } else if (x[i] == 1 && y[i] == 0){
                fn++;
            } else if (x[i] == 0 && y[i] == 1){
                fp++;
            } else if (x[i] == 1 && y[i] == 1){
                tp++;
            }
        }

        //  precision or PPV is the positive predictive rate
        double precision = Precision(tp, fp);

        // Recall or TPR is the true positive rate
        double recall =Recall(tp, fn);
        double Fm = Math.sqrt(precision * recall);
        if (Fm == 2){
            int ss = 2;
        }
        return  ((double) (1- Fm) / 2);
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
