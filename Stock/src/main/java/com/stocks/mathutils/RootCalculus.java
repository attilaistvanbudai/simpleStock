package com.stocks.mathutils;

import java.math.BigDecimal;

/**
 * The Class RootCalculus is a helper class to calculate nth root of BigDecimal
 * object.
 */
public final class RootCalculus {

	private static final int SCALE = 10;
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_DOWN;

	/**
	 * Nth root.
	 *
	 * @param n
	 *            the n
	 * @param base
	 *            the base
	 * @return the nth root of the base
	 */
	public static BigDecimal nthRoot(final int n, final BigDecimal base) {
		return nthRoot(n, base, BigDecimal.valueOf(.1).movePointLeft(SCALE));
	}

	/**
	 * Nth root.
	 *
	 * @param n
	 *            the n
	 * @param base
	 *            the base
	 * @param p
	 *            the p
	 * @return the nth root of the base
	 */
	private static BigDecimal nthRoot(final int n, final BigDecimal a, final BigDecimal p) {
		if (a.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("nth root can only be calculated for positive numbers");
		}
		if (a.equals(BigDecimal.ZERO)) {
			return BigDecimal.ZERO;
		}
		BigDecimal xPrev = a;
		BigDecimal x = a.divide(new BigDecimal(n), SCALE, ROUNDING_MODE); // starting
																			// "guessed"
																			// value...
		while (x.subtract(xPrev).abs().compareTo(p) > 0) {
			xPrev = x;
			x = BigDecimal.valueOf(n - 1.0).multiply(x).add(a.divide(x.pow(n - 1), SCALE, ROUNDING_MODE)).divide(new BigDecimal(n), SCALE, ROUNDING_MODE);
		}
		return x;
	}

	private RootCalculus() {
	}
}