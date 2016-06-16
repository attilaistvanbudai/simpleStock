package com.stocks.functional;

import java.math.BigDecimal;
import java.util.Random;

import org.junit.Test;

import com.stocks.dao.TransferDao;
import com.stocks.dao.TransferDaoImpl;
import com.stocks.model.PreferredStock;
import com.stocks.model.Stock;
import com.stocks.model.Transfer;
import com.stocks.model.Type;
import com.stocks.service.TransferService;
import com.stocks.service.TransferServiceImpl;
import com.stocks.timeutils.TimeUtils;

public class SinlgeThreadedTests {

	String[] stockSymbols = new String[] { "TEA", "POP", "ALE", "GIN", "JOE" };
	TimeUtils timeUtils = new TimeUtils();

	public void addNTransfers(int n, TransferDao dao) {
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			int random = r.nextInt(5) + 1;
			int q = r.nextInt(100);
			boolean t = r.nextBoolean();
			int price = r.nextInt(100);
			Type type;
			if (t) {
				type = Type.BUY;
			} else {
				type = Type.SELL;
			}
			dao.addTransfer(new Transfer(getStock(random), timeUtils.getTimeNow(), new BigDecimal(q), type, new BigDecimal(price)));
		}
	}

	public Stock getStock(int num) {
		Stock stock = null;

		if (num == 1) {
			stock = new Stock(stockSymbols[0], new BigDecimal(0), new BigDecimal(100));
		} else if (num == 2) {
			stock = new Stock(stockSymbols[1], new BigDecimal(8), new BigDecimal(100));
		} else if (num == 3) {
			stock = new Stock(stockSymbols[2], new BigDecimal(23), new BigDecimal(60));
		} else if (num == 4) {
			stock = new PreferredStock(stockSymbols[3], new BigDecimal(8), new BigDecimal(100), new BigDecimal(2));
		} else if (num == 5) {
			stock = new Stock(stockSymbols[4], new BigDecimal(13), new BigDecimal(250));
		}

		return stock;
	}

	@Test
	public void simpleTest() {
		TransferDao dao = new TransferDaoImpl(timeUtils);
		addNTransfers(1000, dao);

		TransferService service = new TransferServiceImpl(dao);
		System.out.println(service.calculateGBCEShareIndex());
	}

}
