package cn.yuncore.io;

import java.io.IOException;

public interface MediaOutputStream {

	/**
	 * 写入编码好的数据
	 * @param bytes
	 * @throws IOException
	 */
	void write(byte [] bytes) throws IOException;
	
	/**
	 * 强制写入
	 * @throws IOException
	 */
	void flush() throws IOException;
	
	/**
	 * 关闭输出流
	 * @throws IOException
	 */
	void close() throws IOException;
	
}
