package cn.yuncore.flv;

import java.io.IOException;
import java.nio.ByteBuffer;

import cn.yuncore.flv.lang.ECMA_Array;
import cn.yuncore.flv.lang.Number;

public class FLVScriptTagBody extends FLVTagBody {

	final ByteBuffer buffer = ByteBuffer.allocate(1024);

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
}
