package com.stocks.dao;

import java.util.List;

import com.stocks.model.Stock;
import com.stocks.model.Transfer;

public interface TransferDao {

	public List<Transfer> getAllTransfer();

	public Transfer getTransfer(String id);

	public void addTransfer(Transfer transfer);

	public List<Transfer> getTransfersByStock(Stock stock);

	public List<Transfer> getLastXMinutes(int minutes, Stock stock);

}
