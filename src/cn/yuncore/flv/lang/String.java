/**
 * String.java
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
public class String implements FLVDataType {

	private java.lang.String string;

	public String() {
	}

	/**
	 * @param string
	 */
	public String(java.lang.String string) {
		super();
		this.string = string;
	}

	/**
	 * @return the string
	 */
	public java.lang.String getString() {
		return string;
	}

	/**
	 * @param string
	 *            the string to set
	 */
	public void setString(java.lang.String string) {
		this.string = string;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVDataType#decoder(byte[])
	 */
	@Override
	public void decoder(byte[] bytes) throws CodingException {
		final ByteBuffer buffer = ByteBuffer.wrap(bytes);
		final char length = buffer.getChar();
		final byte[] stringBytes = new byte[length];
		buffer.get(stringBytes);
		try {
			this.string = new java.lang.String(stringBytes, "UTF-8");
		} catch (Exception e) {
			throw new CodingException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVDataType#encoder()
	 */
	@Override
	public byte[] encoder() throws CodingException {
		try {
			final byte[] stringBytes = string.getBytes("UTF-8");
			ByteBuffer buffer = ByteBuffer.allocate(stringBytes.length + 3);
			buffer.put(STRING);
			buffer.putChar((char) stringBytes.length);
			buffer.put(stringBytes);
			return buffer.array();
		} catch (Exception e) {
			throw new CodingException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVDataType#getType()
	 */
	@Override
	public byte getType() {
		return STRING;
	}

}
