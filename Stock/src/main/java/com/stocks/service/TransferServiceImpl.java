package com.stocks.service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.stocks.dao.TransferDao;
import com.stocks.mathutils.RootCalculus;
import com.stocks.model.PreferredStock;
import com.stocks.model.Stock;
import com.stocks.model.Transfer;

public class TransferServiceImpl implements TransferService {

	TransferDao dao;

	public TransferServiceImpl(TransferDao dao) {
		this.dao = dao;
	}

	public BigDecimal calculateDivident(Stock stock, BigDecimal tickerPrice) throws IllegalArgumentException {
		if (tickerPrice.compareTo(BigDecimal.ZERO) != 1) {
			throw new IllegalArgumentException("Ticker price should be greate than zero!");
		}
		return stock.getLastDivident().divide(tickerPrice);
	}

	public BigDecimal calculateDivident(PreferredStock stock, BigDecimal tickerPrice) {
		return stock.getPerValue().multiply(stock.getFixedDivident()).divide(tickerPrice);
	}

	public BigDecimal getPERatio(Stock stock, BigDecimal tickerPrice) {
		BigDecimal divident = calculateDivident(stock, tickerPrice);
		return tickerPrice.divide(divident);
	}

	public BigDecimal getPERatio(PreferredStock stock, BigDecimal tickerPrice) {
		BigDecimal divident = calculateDivident(stock, tickerPrice);
		return tickerPrice.divide(divident);
	}

	public BigDecimal calculateWeightedAverageStcokPriceForTheLast15Minutes(Stock stock) {
		List<Transfer> transferRows = dao.getLastXMinutes(15, stock);
		return calculateWeightedAverageStcokPrice(transferRows);

	}

	private BigDecimal calculateWeightedAverageStcokPrice(List<Transfer> transferRows) {
		if (transferRows.isEmpty() == false) {
			BigDecimal numberOfAllShares = BigDecimal.ZERO;
			BigDecimal sumOfPrices = BigDecimal.ZERO;
			for (Transfer row : transferRows) {
				sumOfPrices = sumOfPrices.add(row.getPrice().multiply(row.getQuantity()));
				numberOfAllShares = numberOfAllShares.add(row.getQuantity());
			}

			return sumOfPrices.divide(numberOfAllShares);
		}

		return BigDecimal.ZERO;

	}

	@Override
	public BigDecimal calculateGBCEShareIndex() {
		List<Transfer> transferRows = dao.getAllTransfer();
		Map<Stock, List<Transfer>> map = transferRows.stream().collect(Collectors.groupingBy(obj -> obj.getStock()));
		List<BigDecimal> prices = new LinkedList<BigDecimal>();
		for (Entry<Stock, List<Transfer>> entry : map.entrySet()) {
			List<Transfer> value = entry.getValue();
			prices.add(calculateWeightedAverageStcokPrice(value));
		}
		BigDecimal index = RootCalculus.nthRoot(prices.size(), prices.stream().reduce(BigDecimal.ONE, BigDecimal::multiply));
		return index;
	}
}
