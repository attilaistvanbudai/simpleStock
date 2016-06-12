package com.stocks.service;

import java.math.BigDecimal;

import com.stocks.model.PreferredStock;
import com.stocks.model.Stock;

// TODO: Auto-generated Javadoc
/**
 * The Interface TransferService.
 */
public interface TransferService {

	/**
	 * Calculate divident for common stocks based on the stock and the current
	 * price
	 *
	 * @param stock
	 *            the stock
	 * @param tickerPrice
	 *            the ticker price
	 * @return the big decimal
	 */
	public BigDecimal calculateDivident(Stock stock, BigDecimal tickerPrice);

	/**
	 * Calculate divident for preferred stocks based on the stock and the
	 * current price
	 *
	 * @param stock
	 *            the stock
	 * @param tickerPrice
	 *            the ticker price
	 * @return the big decimal
	 */
	public BigDecimal calculateDivident(PreferredStock stock, BigDecimal tickerPrice);

	/**
	 * Calculate the PE ratio for common stocks based on the stock and the
	 * current price
	 *
	 * @param stock
	 *            the stock
	 * @param tickerPrice
	 *            the ticker price
	 * @return the PE ratio
	 */
	public BigDecimal getPERatio(Stock stock, BigDecimal tickerPrice);

	/**
	 * Calculate the PE ratio for preferred stocks based on the stock and the
	 * current price
	 *
	 * @param stock
	 *            the stock
	 * @param tickerPrice
	 *            the ticker price
	 * @return the PE ratio
	 */
	public BigDecimal getPERatio(PreferredStock stock, BigDecimal tickerPrice);

	/**
	 * Calculate weighted average based on the last 15 minutes trades.
	 *
	 * @param stock
	 *            the stock
	 * @return the big decimal
	 */
	public BigDecimal calculateWeightedAverageStcokPriceForTheLast15Minutes(Stock stock);

	public BigDecimal calculateGBCEShareIndex();
}
