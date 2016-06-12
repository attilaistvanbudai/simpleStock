package com.stock.dao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.stocks.dao.TransferDao;
import com.stocks.dao.TransferDaoImpl;
import com.stocks.model.Stock;
import com.stocks.model.Transfer;
import com.stocks.model.Type;
import com.stocks.timeutils.TimeUtils;

public class TransferDaoTest {

	Calendar cal;
	TimeUtils time;
	TransferDao dao;

	private long getMinusMinutes(int minutes) {
		cal.add(Calendar.MINUTE, -1 * minutes);
		return cal.getTimeInMillis();
	}

	@Before
	public void setup() {
		cal = Calendar.getInstance();

		long now = getMinusMinutes(0);
		time = mock(TimeUtils.class);
		when(time.getTimeNow()).thenReturn(new Timestamp(now));
		dao = new TransferDaoImpl(time);
	}

	@Test
	public void getTransferById() {
		// given
		Transfer transfer = mock(Transfer.class);
		String mockId = "mockId";
		dao.addTransfer(transfer);
		when(transfer.getId()).thenReturn(mockId);

		// when
		Transfer result = dao.getTransfer(mockId);

		// then
		Assert.assertEquals(transfer, result);

	}

	@Test
	public void getTransferByIdWhenNoResultFoundShouldReturnNull() {
		// given
		Transfer transfer = mock(Transfer.class);
		String mockId = "mockId";
		dao.addTransfer(transfer);
		when(transfer.getId()).thenReturn(mockId);

		// when
		Transfer result = dao.getTransfer("differentMockId");

		// then
		Assert.assertNull(result);

	}

	@Test
	public void getAllTransferWhenNoTransfersAdded() {
		// when
		List<Transfer> result = dao.getAllTransfer();

		// then
		Assert.assertEquals(0, result.size());

	}

	@Test
	public void getAllTransferWhenOneTransfersAdded() {
		// given
		Transfer transfer = mock(Transfer.class);
		dao.addTransfer(transfer);

		// when
		List<Transfer> result = dao.getAllTransfer();

		// then
		Assert.assertEquals(1, result.size());

	}

	@Test
	public void getTransferByStockWithoutTransfers() {

		// given
		Stock stock = mock(Stock.class);

		// when
		List<Transfer> result = dao.getTransfersByStock(stock);

		// then
		Assert.assertEquals(0, result.size());
	}

	@Test
	public void getTransferByStockWithOneMatchingElement() {

		// given
		Transfer transfer = mock(Transfer.class);
		dao.addTransfer(transfer);

		Stock stock = mock(Stock.class);
		when(transfer.getStock()).thenReturn(stock);

		// when
		List<Transfer> result = dao.getTransfersByStock(stock);

		// then
		Assert.assertEquals(1, result.size());
	}

	@Test
	public void getTransferByStockWithoutMatchingElement() {

		// given
		Transfer transfer = mock(Transfer.class);
		dao.addTransfer(transfer);

		Stock stock = mock(Stock.class);
		Stock stock2 = mock(Stock.class);
		when(transfer.getStock()).thenReturn(stock);

		// when
		List<Transfer> result = dao.getTransfersByStock(stock2);

		// then
		Assert.assertEquals(0, result.size());
	}

	@Test
	public void getTransferByStockWithMultipleMatchingElements() {

		// given
		Transfer transfer = mock(Transfer.class);
		dao.addTransfer(transfer);

		Transfer transfer2 = mock(Transfer.class);
		dao.addTransfer(transfer2);

		Stock stock = mock(Stock.class);
		when(transfer.getStock()).thenReturn(stock);
		when(transfer2.getStock()).thenReturn(stock);

		// when
		List<Transfer> result = dao.getTransfersByStock(stock);

		// then
		Assert.assertEquals(2, result.size());
	}

	@Test
	public void lastXMinutesTestWithTwoElements() {
		// given
		Stock stock = mock(Stock.class);

		long nowMinusFiveMinutes = getMinusMinutes(5);
		long nowMinusTenMinutes = getMinusMinutes(5);

		dao.addTransfer(new Transfer(stock, new Timestamp(nowMinusTenMinutes), BigDecimal.TEN, Type.BUY, BigDecimal.TEN));
		dao.addTransfer(new Transfer(stock, new Timestamp(nowMinusFiveMinutes), BigDecimal.TEN, Type.BUY, BigDecimal.TEN));

		// when
		List<Transfer> list = dao.getLastXMinutes(15, stock);

		// then
		Assert.assertEquals(2, list.size());

	}

	@Test
	public void lastXMinutesTest() {
		// given
		Stock stock = mock(Stock.class);

		long nowMinusTenMinutes = getMinusMinutes(10);
		long nowMinusTwentyMinutes = getMinusMinutes(10);

		dao.addTransfer(new Transfer(stock, new Timestamp(nowMinusTwentyMinutes), BigDecimal.TEN, Type.BUY, BigDecimal.TEN));
		dao.addTransfer(new Transfer(stock, new Timestamp(nowMinusTenMinutes), BigDecimal.TEN, Type.BUY, BigDecimal.TEN));

		// when
		List<Transfer> list = dao.getLastXMinutes(15, stock);

		// then
		Assert.assertEquals(1, list.size());

	}

	@Test
	public void lastXMinutesTestWithDifferentStocks() {
		// given
		Stock stock = new Stock("a", BigDecimal.ONE, BigDecimal.ONE);
		Stock stock2 = new Stock("b", BigDecimal.ONE, BigDecimal.ONE);

		long nowMinusFiveMinutes = getMinusMinutes(5);
		long nowMinusTenMinutes = getMinusMinutes(5);

		dao.addTransfer(new Transfer(stock, new Timestamp(nowMinusTenMinutes), BigDecimal.TEN, Type.BUY, BigDecimal.TEN));
		dao.addTransfer(new Transfer(stock2, new Timestamp(nowMinusFiveMinutes), BigDecimal.TEN, Type.BUY, BigDecimal.TEN));

		// when
		List<Transfer> list = dao.getLastXMinutes(15, stock);

		// then
		Assert.assertEquals(1, list.size());

	}
}
