package com.stocks.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.stocks.model.Stock;
import com.stocks.model.Transfer;
import com.stocks.timeutils.TimeUtils;

public class TransferDaoImpl implements TransferDao {

	private List<Transfer> transfers;
	private TimeUtils time;

	public TransferDaoImpl(TimeUtils timeutil) {
		transfers = new ArrayList<Transfer>();
		time = timeutil;
	}

	public void addTransfer(Transfer row) {
		transfers.add(row);
	}

	public List<Transfer> getAllTransfer() {
		return new ArrayList<Transfer>(transfers);
	}

	@Override
	public Transfer getTransfer(String id) {
		Optional<Transfer> value = transfers.stream().filter(elem -> elem.getId().equals(id)).findFirst();
		if (!value.isPresent()) {
			return null;
		}
		return value.get();
	}

	@Override
	public List<Transfer> getTransfersByStock(Stock stock) {
		List<Transfer> value = transfers.stream().filter(elem -> elem.getStock().equals(stock)).collect(Collectors.toList());
		return value;

	}

	public List<Transfer> getLastXMinutes(int minutes, Stock stock) {
		long nowMinusMinutes = time.getTimeNow().getTime() - (minutes * 60 * 1000);

		// @formatter:off
		List<Transfer> value = transfers.stream() //
				.filter(elem -> elem.getStock().equals(stock))//
				.filter(elem -> (elem.getTime().getTime() - nowMinusMinutes > 0))//
				.collect(Collectors.toList());
		// @formatter:on
		return value;

	}

}
