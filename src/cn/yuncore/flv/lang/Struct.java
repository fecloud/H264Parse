/**
 * FLVStruct.java
 * 2014-12-11
 */
package cn.yuncore.flv.lang;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import cn.yuncore.flv.CodingException;
import cn.yuncore.flv.FLVTagBody;

/**
 *
 */
public class Struct extends FLVTagBody {

	protected List<FLVData> objects = new ArrayList<>();

	/**
	 * 解析String类型
	 * 
	 * @param buffer
	 * @return
	 * @throws CodingException
	 */
	protected FLVData parseString(ByteBuffer buffer) throws CodingException {
		final cn.yuncore.flv.lang.String string = new cn.yuncore.flv.lang.String();
		string.decoder(buffer);
		return string;
	}

	/**
	 * 解析Number 64bit
	 * 
	 * @param buffer
	 * @return
	 * @throws CodingException
	 */
	private FLVData parseNubmer(ByteBuffer buffer) throws CodingException {
		final Number number = new Number();
		number.decoder(buffer);
		return number;
	}

	/**
	 * 解析数组类型
	 * 
	 * @param buffer
	 * @return
	 * @throws CodingException
	 */
	protected FLVData parseECMAArray(ByteBuffer buffer) throws CodingException {
		final ECMA_Array array = new ECMA_Array();
		array.decoder(buffer);
		return array;
	}

	/**
	 * 解析所有类型
	 * 
	 * @param buffer
	 * @return
	 * @throws CodingException
	 */
	protected FLVData parseFLVData(ByteBuffer buffer) throws CodingException {

		switch (buffer.get()) {
		case FLVData.STRING:
			return parseString(buffer);
		case FLVData.ECMA_ARRAY:
			return parseECMAArray(buffer);
		case FLVData.NUMBER:
			return parseNubmer(buffer);
		default:
			return null;
		}

	}

	/**
	 * 添加一个string
	 * 
	 * @param name
	 * @throws IOException
	 */
	public void put(java.lang.String name) throws CodingException {
		this.objects.add(new String(name));
	}

	/**
	 * 添加一个number
	 * 
	 * @param number
	 * @throws IOException
	 */
	public void put(long number) throws CodingException {
		this.objects.add(new Number(number));
	}

	/**
	 * 添加一个数组
	 * 
	 * @param array
	 * @throws CodingException
	 */
	public void putArray(ECMA_Array array) throws CodingException {
		if (null != array) {
			this.objects.add(array);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.FLVTagBody#decoder(byte[])
	 */
	@Override
	public void decoder(byte[] bytes) throws CodingException {
		final ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		buffer.put(bytes).flip();
		while (buffer.hasRemaining()) {
			this.objects.add(parseFLVData(buffer));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.FLVTagBody#encoder()
	 */
	@Override
	public byte[] encoder() throws CodingException {
		final ByteBuffer buffer = ByteBuffer.allocate(1024 * 100);
		for (FLVData data : this.objects) {
			data.encoder(buffer);
		}
		buffer.flip();
		final byte[] bytes = new byte[buffer.limit()];
		buffer.get(bytes);
		return bytes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public java.lang.String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[ ");
		for (FLVData data : this.objects) {
			builder.append(data.toString());
		}
		builder.append(" ]");
		return builder.toString();
	}

}
