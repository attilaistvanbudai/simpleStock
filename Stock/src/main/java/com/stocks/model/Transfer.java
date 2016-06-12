package com.stocks.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class Transfer {

	private String id;
	private Stock stock;
	private Timestamp time;
	private BigDecimal quantity;
	private Type type;
	private BigDecimal price;

	public Transfer(Stock stock, Timestamp time, BigDecimal quantity, Type type, BigDecimal price) {
		this.id = UUID.randomUUID().toString();
		this.stock = stock;
		this.time = time;
		this.quantity = quantity;
		this.type = type;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public Stock getStock() {
		return stock;
	}

	public Timestamp getTime() {
		return time;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public Type getType() {
		return type;
	}

	public BigDecimal getPrice() {
		return price;
	}

}
