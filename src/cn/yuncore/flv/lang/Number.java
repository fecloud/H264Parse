/**
 * Number.java
 * 2014-12-10
 * 深圳市五月高球信息咨询有限公司
 * 欧阳丰
 */
package cn.yuncore.flv.lang;

import java.nio.ByteBuffer;

import cn.yuncore.flv.CodingException;

/**
 * @author 欧阳丰
 * 
 */
public class Number implements FLVDataType {

	private long number;

	public Number() {
	}

	/**
	 * @param number
	 */
	public Number(long number) {
		super();
		this.number = number;
	}

	/**
	 * @return the number
	 */
	public long getNumber() {
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
	 * @see cn.yuncore.flv.lang.FLVDataType#decoder(byte[])
	 */
	@Override
	public void decoder(byte[] bytes) throws CodingException {
		final ByteBuffer buffer = ByteBuffer.wrap(bytes);
		this.number = buffer.getLong();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVDataType#encoder()
	 */
	@Override
	public byte[] encoder() throws CodingException {
		final ByteBuffer buffer = ByteBuffer.allocate(9);
		buffer.put(NUMBER);
		buffer.putLong(number);
		return buffer.array();
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

}
