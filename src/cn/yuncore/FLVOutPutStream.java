package cn.yuncore;

import java.io.FileOutputStream;
import java.io.IOException;

public class FLVOutPutStream {

	private FileOutputStream file;

	public FLVOutPutStream(FileOutputStream file) throws IOException {
		this.file = file;
		writeFileHeader();
	}

	private void writeFileHeader() throws IOException {

		final byte flvFileHeader[] = { 0x46, 0x4c, 0x56, 0x01, 0x01, 0x00,
				0x00, 0x00, 0x09 };
		write(flvFileHeader);
		flush();

	}

	public void write(byte[] bytes) throws IOException {
		if (null != file) {
			file.write(bytes);
		}
	}

	public void flush() throws IOException {
		file.flush();
	}

	public void close() throws IOException {
		file.close();
	}

}
