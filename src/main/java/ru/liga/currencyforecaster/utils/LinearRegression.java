package ru.liga.currencyforecaster.utils;

import lombok.Getter;

import java.util.List;

/**
 * The {@code LinearRegression} class performs a simple linear regression
 * on an set of <em>n</em> data points (<em>y<sub>i</sub></em>, <em>x<sub>i</sub></em>).
 * That is, it fits a straight line <em>y</em> = &alpha; + &beta; <em>x</em>,
 * (where <em>y</em> is the response variable, <em>x</em> is the predictor variable,
 * &alpha; is the <em>y-intercept</em>, and &beta; is the <em>slope</em>)
 * that minimizes the sum of squared residuals of the linear regression model.
 * It also computes associated statistics, including the coefficient of
 * determination <em>R</em><sup>2</sup> and the standard deviation of the
 * estimates for the slope and <em>y</em>-intercept.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class LinearRegression {
    @Getter
    private final double intercept;
    @Getter
    private final double slope;
    @Getter
    private final double r2;
    private final double svar0;
    private final double svar1;

    /**
     * Performs a linear regression on the data points {@code (y.get(i), x[i])}.
     *
     * @param x the values of the predictor variable
     * @param y the corresponding values of the response variable
     * @throws IllegalArgumentException if the lengths of the two arrays are not equal
     */
    public LinearRegression(List<Double> x, List<Double> y) {
        if (x.size() != y.size()) {
            throw new IllegalArgumentException("array lengths are not equal");
        }
        int n = x.size();

        // first pass
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            sumx += x.get(i);
            sumx2 += x.get(i) * x.get(i);
            sumy += y.get(i);
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x.get(i) - xbar) * (x.get(i) - xbar);
            yybar += (y.get(i) - ybar) * (y.get(i) - ybar);
            xybar += (x.get(i) - xbar) * (y.get(i) - ybar);
        }
        slope = xybar / xxbar;
        intercept = ybar - slope * xbar;

        // more statistical analysis
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = slope * x.get(i) + intercept;
            rss += (fit - y.get(i)) * (fit - y.get(i));
            ssr += (fit - ybar) * (fit - ybar);
        }

        int degreesOfFreedom = n - 2;
        r2 = ssr / yybar;
        double svar = rss / degreesOfFreedom;
        svar1 = svar / xxbar;
        svar0 = svar / n + xbar * xbar * svar1;
    }

    /**
     * Returns the expected response {@code y} given the value of the predictor
     * variable {@code x}.
     *
     * @param x the value of the predictor variable
     * @return the expected response {@code y} given the value of the predictor
     * variable {@code x}
     */
    public double predict(double x) {
        return slope * x + intercept;
    }

    /**
     * Returns a string representation of the simple linear regression model.
     *
     * @return a string representation of the simple linear regression model,
     * including the best-fit line and the coefficient of determination
     * <em>R</em><sup>2</sup>
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%.2f n + %.2f", getSlope(), getIntercept()));
        s.append("  (R^2 = ").append(String.format("%.3f", getR2())).append(")");
        return s.toString();
    }
}
