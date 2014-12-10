package cn.yuncore.io;

import java.io.IOException;

public interface MediaInputStream {

	/**
	 * 读取一帧数据
	 * 
	 * @return
	 * @throws IOException
	 */
	byte[] reader() throws IOException;

	/**
	 * 关闭输入流
	 * 
	 * @throws IOException
	 */
	void close() throws IOException;

}
