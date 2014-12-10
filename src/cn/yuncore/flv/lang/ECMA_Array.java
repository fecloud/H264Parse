/**
 * ECMA_Array.java
 * 2014-12-10
 * 欧阳丰
 */
package cn.yuncore.flv.lang;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import cn.yuncore.flv.CodingException;

/**
 * @author 欧阳丰
 * 
 */
public class ECMA_Array implements FLVData {

	private List<java.lang.String> names = new ArrayList<java.lang.String>();

	private List<FLVData> values = new ArrayList<>();

	public void put(java.lang.String name, FLVData data) {
		names.add(name);
		values.add(data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVDataType#decoder(byte[])
	 */
	@Override
	public void decoder(byte[] bytes) throws CodingException {
		// TODO Auto-generated method stub

	}

	public void decoder(ByteBuffer buffer, int size) throws CodingException {
		char nameLength = 0;
		byte[] bytes = null;
		try {
			for (int i = 0; i < size; i++) {
				nameLength = buffer.getChar();
				bytes = new byte[nameLength];
				buffer.get(bytes);
				names.add(new java.lang.String(bytes, "UTF-8"));
				
			}
		} catch (Exception e) {
			throw new CodingException(e);
		}

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
	 * @see cn.yuncore.flv.lang.FLVDataType#encoder()
	 */
	@Override
	public byte[] encoder() throws CodingException {
		try {
			final ByteBuffer buffer = ByteBuffer.allocate(1024);
			buffer.put(ECMA_ARRAY);
			buffer.putInt(names.size());
			FLVData value = null;
			java.lang.String name = null;
			for (int i = 0; i < names.size();) {
				name = names.get(i);
				value = values.get(i);
				if (value != null) {
					buffer.put(encoderName(name));
					buffer.put(value.encoder());
				}
			}
			buffer.put((byte) 0x0);
			buffer.put((byte) 0x0);
			buffer.put(OBJECT_END);
			buffer.flip();
			final byte[] bytes = new byte[buffer.limit()];
			buffer.get(bytes);
			return bytes;
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
		return ECMA_ARRAY;
	}

}
