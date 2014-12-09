package cn.yuncore.io;

import java.io.IOException;

public interface MediaOutputStream {

	void write(byte [] bytes) throws IOException;
	
	void flush() throws IOException;
	
	void close() throws IOException;
	
}
