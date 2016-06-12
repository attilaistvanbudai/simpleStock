package com.stocks.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.stocks.dao.TransferDao;
import com.stocks.model.PreferredStock;
import com.stocks.model.Stock;
import com.stocks.model.Transfer;
import com.stocks.model.Type;

public class TransferServiceTest {

	TransferService service;
	TransferDao dao;

	@Before
	public void setup() {
		dao = mock(TransferDao.class);
		service = new TransferServiceImpl(dao);
	}

	@Test
	public void calculateDivident() {
		Stock commonStock = new Stock("SimpleCommonStock", BigDecimal.TEN, BigDecimal.TEN);
		BigDecimal result = service.calculateDivident(commonStock, BigDecimal.ONE);
		Assert.assertEquals(10, result.intValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void calculateDividentWithZeroPriceShouldThrowException() {
		Stock commonStock = new Stock("SimpleCommonStock", BigDecimal.TEN, BigDecimal.TEN);
		service.calculateDivident(commonStock, BigDecimal.ZERO);
	}

	@Test
	public void calculateDividentPreferred() {
		PreferredStock prefStock = new PreferredStock("PreferredStock", BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);
		BigDecimal result = service.calculateDivident(prefStock, BigDecimal.ONE);
		Assert.assertEquals(100, result.intValue());
	}

	@Test
	public void calculatePERatio() {
		Stock commonStock = new Stock("SimpleCommonStock", BigDecimal.TEN, BigDecimal.TEN);
		BigDecimal result = service.getPERatio(commonStock, BigDecimal.ONE);
		Assert.assertEquals(0.1, result.doubleValue());
	}

	@Test
	public void calculatePERatioPreferred() {
		PreferredStock prefStock = new PreferredStock("PreferredStock", BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);
		BigDecimal result = service.getPERatio(prefStock, BigDecimal.ONE);
		Assert.assertEquals(0.01, result.doubleValue());
	}

	@Test
	public void calculateWeightedAverageStcokPriceWithoutStocks() {
		List<Transfer> transfers = new ArrayList<Transfer>();
		when(dao.getLastXMinutes(any(Integer.class), any(Stock.class))).thenReturn(transfers);
		Stock randomStock = mock(Stock.class);
		BigDecimal result = service.calculateWeightedAverageStcokPriceForTheLast15Minutes(randomStock);
		Assert.assertEquals(0, result.intValue());

	}

	@Test
	public void calculateWeightedAverageStcokPrice() {
		List<Transfer> transfers = new ArrayList<Transfer>();
		Stock stock = new Stock("test", BigDecimal.ONE, BigDecimal.ONE);
		Timestamp time = mock(Timestamp.class);
		Transfer transfer = new Transfer(stock, time, BigDecimal.ONE, Type.BUY, BigDecimal.TEN);
		transfers.add(transfer);
		when(dao.getLastXMinutes(any(Integer.class), any(Stock.class))).thenReturn(transfers);
		BigDecimal result = service.calculateWeightedAverageStcokPriceForTheLast15Minutes(stock);
		Assert.assertEquals(10, result.intValue());

	}

	@Test
	public void calculateWeightedAverageStcokPriceWithTwoSTockOfTheSameType() {
		List<Transfer> transfers = new ArrayList<Transfer>();
		Stock stock = new Stock("test", BigDecimal.ONE, BigDecimal.ONE);
		Timestamp time = mock(Timestamp.class);
		Transfer transfer = new Transfer(stock, time, BigDecimal.ONE, Type.BUY, BigDecimal.TEN);
		Transfer transfer2 = new Transfer(stock, time, BigDecimal.TEN, Type.BUY, new BigDecimal(21));
		transfers.add(transfer);
		transfers.add(transfer2);

		when(dao.getLastXMinutes(any(Integer.class), any(Stock.class))).thenReturn(transfers);
		BigDecimal result = service.calculateWeightedAverageStcokPriceForTheLast15Minutes(stock);
		Assert.assertEquals(20, result.intValue());

	}

	@Test
	public void calculateGBCEShareIndex() {
		List<Transfer> transfers = new ArrayList<Transfer>();
		Stock stock = new Stock("test", BigDecimal.ONE, BigDecimal.ONE);
		Timestamp time = mock(Timestamp.class);
		Transfer transfer = new Transfer(stock, time, BigDecimal.ONE, Type.BUY, BigDecimal.TEN);
		Transfer transfer2 = new Transfer(stock, time, BigDecimal.TEN, Type.BUY, new BigDecimal(21));
		transfers.add(transfer);
		transfers.add(transfer2);
		when(dao.getAllTransfer()).thenReturn(transfers);
		System.out.println(service.calculateGBCEShareIndex());
	}

	@Test
	public void calculateGBCEShareIndexWithDifferentStocks() {
		List<Transfer> transfers = new ArrayList<Transfer>();
		Stock stock = new Stock("test", BigDecimal.ONE, BigDecimal.ONE);
		Stock stock2 = new Stock("test2", BigDecimal.ONE, BigDecimal.ONE);
		Timestamp time = mock(Timestamp.class);
		Transfer transfer = new Transfer(stock, time, BigDecimal.ONE, Type.BUY, BigDecimal.TEN);
		Transfer transfer2 = new Transfer(stock2, time, BigDecimal.TEN, Type.BUY, new BigDecimal(21));
		transfers.add(transfer);
		transfers.add(transfer2);
		when(dao.getAllTransfer()).thenReturn(transfers);
		System.out.println(service.calculateGBCEShareIndex());
	}
}
