package cn.yuncore.flv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.yuncore.io.MediaOutputStream;

public class FLVOutPutStream implements MediaOutputStream {

	private FileOutputStream file;

	private FLVTag tag;

	public FLVOutPutStream(File file) throws IOException {
		this.file = new FileOutputStream(file);
		writeFileHeader();
	}

	private void writeFileHeader() throws IOException {

		final byte flvFileHeader[] = { 0x46, 0x4c, 0x56, 0x01, 0x01, 0x00,
				0x00, 0x00, 0x09 };
		write(flvFileHeader);
		flush();

	}

	protected void writeTag(byte[] bytes) throws IOException {
		final FLVTag flvTag = new FLVTag();
		final FLVTagHeader header = new FLVTagHeader();
		header.setDataLength(bytes.length);
		if(null == tag){
			header.setPreviousTagSize(0);
		}else {
			header.setPreviousTagSize(tag.getLength());
		}
		header.setType((byte) 0x9);
		file.write(flvTag.toBytes());
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
