package cn.yuncore.flv;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import cn.yuncore.flv.lang.ECMA_Array;
import cn.yuncore.flv.lang.FLVData;
import cn.yuncore.flv.lang.Number;

public class FLVScriptTagBody extends FLVTagBody {

	final ByteBuffer buffer = ByteBuffer.allocate(1024);

	private List<FLVData> objects = new ArrayList<>();

	/**
	 * 添加一个string
	 * 
	 * @param name
	 * @throws IOException
	 */
	public void put(String name) throws CodingException {
		buffer.put(new cn.yuncore.flv.lang.String(name).encoder());
	}

	/**
	 * 添加一个number
	 * 
	 * @param number
	 * @throws IOException
	 */
	public void put(long number) throws CodingException {
		buffer.put(new Number(number).encoder());
	}

	/**
	 * 添加一个数组
	 * 
	 * @param array
	 * @throws CodingException
	 */
	public void putArray(ECMA_Array array) throws CodingException {
		if (null != array) {
			buffer.put(array.encoder());
		}
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
		array.decoder(buffer, buffer.getInt());
		return array;
	}

	/**
	 * 解析String类型
	 * 
	 * @param buffer
	 * @return
	 * @throws CodingException
	 */
	protected FLVData parseString(ByteBuffer buffer) throws CodingException {
		final cn.yuncore.flv.lang.String string = new cn.yuncore.flv.lang.String();
		ByteBuffer tempBuffer = null;
		byte[] bytes = null;
		final char stringLen = buffer.getChar();
		tempBuffer = ByteBuffer.allocate(stringLen + 2);
		tempBuffer.putChar(stringLen);
		bytes = new byte[stringLen];
		buffer.get(bytes);
		tempBuffer.put(bytes);
		string.decoder(tempBuffer.array());
		return string;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVData#decoder(byte[])
	 */
	@Override
	public void decoder(byte[] bytes) throws CodingException {
		final ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		while (buffer.hasRemaining()) {
			switch (buffer.get()) {
			case FLVData.STRING:
				objects.add(parseString(buffer));
				break;
			case FLVData.ECMA_ARRAY:
				objects.add(parseECMAArray(buffer));
			default:
				break;
			}
		}
	}

	@Override
	public byte[] encoder() throws CodingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<FLVData> getObjects() {
		return objects;
	}

	public void setObjects(List<FLVData> objects) {
		this.objects = objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVData#getType()
	 */
	@Override
	public byte getType() {
		return 0;
	}

}
