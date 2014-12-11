/**
 * ECMA_Array.java
 * 2014-12-10
 */
package cn.yuncore.flv.lang;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import cn.yuncore.flv.CodingException;

/**
 * 
 */
public class ECMA_Array extends Struct implements FLVData {

	private List<java.lang.String> names = new ArrayList<java.lang.String>();

	private List<FLVData> values = new ArrayList<>();

	public void put(java.lang.String name, FLVData data) {
		names.add(name);
		values.add(data);
	}

	/**
	 * 编码key
	 * 
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected byte[] encoderName(java.lang.String name)
			throws UnsupportedEncodingException {
		final byte[] nameBytes = name.getBytes("UTF-8");
		final ByteBuffer buffer = ByteBuffer.allocate(nameBytes.length + 2);
		buffer.putChar((char) nameBytes.length);
		buffer.put(nameBytes);
		return buffer.array();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVDataType#getType()
	 */
	@Override
	public byte getType() {
		return ECMA_ARRAY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVData#decoder(java.nio.ByteBuffer)
	 */
	@Override
	public void decoder(ByteBuffer buffer) throws CodingException {
		byte[] tmp = null;
		try {
			char nameLength = 0;
			int size = buffer.getInt();
			for (int i = 0; i < size && buffer.hasRemaining(); i++) {
				nameLength = buffer.getChar();
				tmp = new byte[nameLength];
				if (buffer.hasRemaining()) {
					buffer.get(tmp);
					this.names.add(new java.lang.String(tmp, "UTF-8"));
				}
				if (buffer.hasRemaining()) {
					this.values.add(parseFLVData(buffer));
				}
			}
			if (buffer.hasRemaining()) {
				buffer.position(buffer.position() + 3);
			}
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
			buffer.put(ECMA_ARRAY);
			buffer.putInt(names.size());
			FLVData value = null;
			java.lang.String name = null;
			for (int i = 0; i < names.size(); i++) {
				name = names.get(i);
				value = values.get(i);
				if (value != null) {
					buffer.put(encoderName(name));
					value.encoder(buffer);
				}
			}
			buffer.put((byte) 0x0);
			buffer.put((byte) 0x0);
			buffer.put(OBJECT_END);
		} catch (Exception e) {
			throw new CodingException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.Struct#toString()
	 */
	@Override
	public java.lang.String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("ECMA_Array [ ");
		java.lang.String name = null;
		FLVData value = null;
		for (int i = 0; i < names.size(); i++) {
			name = names.get(i);
			value = values.get(i);
			if (value != null) {
				buffer.append(name).append(":").append(value);
			}
		}
		buffer.append(" ]");
		return buffer.toString();
	}

}
