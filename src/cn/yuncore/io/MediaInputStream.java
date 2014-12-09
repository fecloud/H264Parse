package cn.yuncore.io;

import java.io.IOException;

public interface MediaInputStream {

	byte [] reader() throws IOException;
	
	void close() throws IOException;
	
}
