package se.miun.projectM.mathLOG;


import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *  The LinearRegression class performs a simple linear regression
 *  on an set of n data points (y[i], x[i]).
 *  That is, it fits a straight line (y = beta * x + alpha) Where:
 *  y is the response variable,
 *  x is the predictor variable,
 *  alpha is the y-intercept,
 *   beta is the slope.
 *  that minimizes the sum of squared residuals of the linear regression model.
 *  It also computes associated statistics, including the coefficient of determination R2
 *  and the standard deviation of the estimates for the slope and y-intercept.
 */
public class LinearRegressionCLASS {
    private RealMatrix w, estimate;

    /**
     *
     * @param xArray is the predictor variable
     * @param yArray is the response variable
     */
    public LinearRegressionCLASS(double [][]xArray, double[][] yArray) throws Exception{
        applyNormalEquation(MatrixUtils.createRealMatrix(xArray), MatrixUtils.createRealMatrix(yArray));

    }
    private void applyNormalEquation(RealMatrix x, RealMatrix y) throws Exception {
        LUDecomposition luDecomposition = new LUDecomposition(x.transpose().multiply(x));
       if (luDecomposition.getDeterminant()!=0.0){
           if(luDecomposition.getDeterminant()==0.0) throw new Exception("singular matrix w/ no inverse");
           else {w = luDecomposition.getSolver().getInverse().multiply(x.transpose().multiply(y));}
           estimate = x.multiply(w);
       }


    }
    public double estimateRent(String entry) {
        return MatrixUtils.createColumnRealMatrix(new double[] {1, Double.valueOf(entry)}).transpose().multiply(w).getData()[0][0];
    }
    public RealMatrix getW() {return w;}
    public RealMatrix getEstimate() { return estimate;}


    /**
     * Returns the y-intercept alpha of the best of the best-fit line (y = beta * x + alpha).
     */
    //public double intercept() {        return intercept;    }

    /**
     * Returns the slope &beta; of the best of the best-fit line (y = beta * x + alpha).
     */
   // public double slope() {        return slope;    }

    /**
     * Returns the coefficient of determination R<sup>2.
     *         which is a real number between 0 and 1
     */
   // public double R2() {      return r2;    }

    /**
     * @return the standard error of the estimate for the intercept
     */
   // public double interceptStdErr() {      return Math.sqrt(svar0);    }

    /**
    * @return the standard error of the estimate for the slope
     */
  //  public double slopeStdErr() {  return Math.sqrt(svar1); }

    /**
     * Returns the expected response {@code y} given the value of the predictor
     * variable {@code x}.
     *
     * @param  x the value of the predictor variable
     * @return the expected response {@code y} given the value of the predictor
     *         variable {@code x}
     */
  //  public double predict(double x) {  return slope*x + intercept; }

    /**
     * Returns a string representation of the simple linear regression model.
     *
     * @return a string representation of the simple linear regression model,
     *         including the best-fit line and the coefficient of determination
     *         <em>R</em><sup>2</sup>
     */
  /*  public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%.2f n + %.2f", slope(), intercept()));
        s.append("  (R^2 = " + String.format("%.3f", R2()) + ")");
        return s.toString();
    }*/

}
