package com.stocks.model;

import java.math.BigDecimal;

public class PreferredStock extends Stock {

	private BigDecimal fixedDivident;

	public PreferredStock(String symbol, BigDecimal lastDivident, BigDecimal perValue, BigDecimal fixedDividentPercentage) {
		super(symbol, lastDivident, perValue);
		this.fixedDivident = fixedDividentPercentage;
	}

	public BigDecimal getFixedDivident() {
		return fixedDivident;
	}

	public void setFixedDivident(BigDecimal fixedDivident) {
		this.fixedDivident = fixedDivident;
	}

}
