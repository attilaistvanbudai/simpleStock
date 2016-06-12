package com.stocks.model;

import java.math.BigDecimal;

public class Stock {

	private String symbol;
	private BigDecimal lastDivident;
	private BigDecimal perValue;

	public Stock(String symbol, BigDecimal lastDivident, BigDecimal perValue) {
		this.symbol = symbol;
		this.lastDivident = lastDivident;
		this.perValue = perValue;
	}

	public String getSymbol() {
		return symbol;
	}

	public BigDecimal getLastDivident() {
		return lastDivident;
	}

	public BigDecimal getPerValue() {
		return perValue;
	}

	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", lastDivident=" + lastDivident + ", perValue=" + perValue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

}
