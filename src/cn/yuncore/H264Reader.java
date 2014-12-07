package cn.yuncore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class H264Reader {

	private File file;

	private RandomAccessFile in;

	public H264Reader(File file) throws FileNotFoundException {
		super();
		this.file = file;
		if (file != null && file.exists()) {
			in = new RandomAccessFile(file, "r");
		}
	}

	public long startNALU() throws IOException {
		while (in.getFilePointer() < file.length()) {
			if (in.readByte() == 0) {
				final byte[] bs = new byte[3];
				int len = in.read(bs);
				if ( len == 3 || len  == 2) {
					if (bs[2] == 0x1 && bs[0] == bs[1]) {
						return in.getFilePointer() - 4;
					}
					else if(bs[0] == 0 && bs[1] == 0x1) {
						return in.getFilePointer() - 4;
					}
				}
			}
		}
		return file.length() - 1;
	}

	public void close() throws IOException {
		in.close();
	}

	public H264NALU reader() throws IOException {

		H264NALU h264nalu = null;
		long start = startNALU();
		if (start == file.length() - 1) {
			return null;
		} else {
			start += 4;
		}

		long end = startNALU();
		System.out.println(String.format("start:%s end:%s", start, end));
		final byte[] bs = new byte[(int) (end - start)];

		in.seek(start);
		int len = in.read(bs);
		in.seek(end);
		h264nalu = new H264NALU();
		h264nalu.setData(bs);
		if (h264nalu.getData() != null && h264nalu.getData().length == len)
			h264nalu.setType(0xFF & (0x1F & h264nalu.getData()[0]));
		return h264nalu;

	}
}
