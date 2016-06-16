package com.stocks.dao;

import java.util.List;

import com.stocks.model.Stock;
import com.stocks.model.Transfer;

/* 
 * The TransferDao handles the data operation independently from the database
 */
public interface TransferDao {

	/**
	 * Gets all the transfers
	 *
	 * @return a list of transfers
	 */
	public List<Transfer> getAllTransfer();

	/**
	 * Gets transfer by id.
	 *
	 * @param id
	 *            Id of transfer
	 * @return the transfer
	 */
	public Transfer getTransfer(String id);

	/**
	 * Adds transfer to database
	 *
	 * @param transfer
	 *            transfer to be added
	 */
	public void addTransfer(Transfer transfer);

	/**
	 * Gets the transfers by stock.
	 *
	 * @param stock
	 *            the stock
	 * @return the transfers by stock
	 */
	public List<Transfer> getTransfersByStock(Stock stock);

	/**
	 * Gets the last x minutes.
	 *
	 * @param minutes
	 *            the minutes
	 * @param stock
	 *            the stock
	 * @return transfers of the last x minutes
	 */
	public List<Transfer> getLastXMinutes(int minutes, Stock stock);

}
