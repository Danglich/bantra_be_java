package com.danglich.bantra.exception;

public class OutOfStockExcepion extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int quantity;

	public OutOfStockExcepion(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public OutOfStockExcepion(String message,int quantity) {
		super(message);
		this.quantity = quantity;
		// TODO Auto-generated constructor stub
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	

}
