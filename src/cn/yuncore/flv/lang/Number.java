/**
 * Number.java
 * 2014-12-10
 */
package cn.yuncore.flv.lang;

import java.nio.ByteBuffer;

import cn.yuncore.flv.CodingException;

/**
 * 
 */
public class Number implements FLVData {

	private double number;

	public Number() {
	}

	/**
	 * @param number
	 */
	public Number(double number) {
		super();
		this.number = number;
	}

	/**
	 * @return the number
	 */
	public double getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(long number) {
		this.number = number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVDataType#getType()
	 */
	@Override
	public byte getType() {
		return NUMBER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVData#decoder(java.nio.ByteBuffer)
	 */
	@Override
	public void decoder(ByteBuffer buffer) throws CodingException {
		this.number = buffer.getDouble();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVData#encoder(java.nio.ByteBuffer)
	 */
	@Override
	public void encoder(ByteBuffer buffer) throws CodingException {
		buffer.put(NUMBER);
		buffer.putDouble(number);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public java.lang.String toString() {
		return "Number [number=" + number + "]";
	}

}
