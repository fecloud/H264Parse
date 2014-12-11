/**
 * String.java
 * 2014-12-10
 */
package cn.yuncore.flv.lang;

import java.nio.ByteBuffer;

import cn.yuncore.flv.CodingException;

/**
 * 
 */
public class String implements FLVData {

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
	 * @see cn.yuncore.flv.lang.FLVDataType#getType()
	 */
	@Override
	public byte getType() {
		return STRING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVData#decoder(java.nio.ByteBuffer)
	 */
	@Override
	public void decoder(ByteBuffer buffer) throws CodingException {
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
	 * @see cn.yuncore.flv.lang.FLVData#encoder(java.nio.ByteBuffer)
	 */
	@Override
	public void encoder(ByteBuffer buffer) throws CodingException {
		try {
			final byte[] stringBytes = string.getBytes("UTF-8");
			buffer.put(STRING);
			buffer.putChar((char) stringBytes.length);
			buffer.put(stringBytes);
		} catch (Exception e) {
			throw new CodingException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public java.lang.String toString() {
		return "String [string=" + string + "]";
	}

}
