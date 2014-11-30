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
				if (in.read(bs) == 3) {
					if (bs[0] == bs[1] && bs[2] == 0x1) {
						return in.getFilePointer();
					}
				}
			}
		}
		return -1;
	}

	public H264NALU reader() throws IOException {

		H264NALU h264nalu = null;
		long start = startNALU();
		long end = startNALU();
		final RandomAccessFile in2 = new RandomAccessFile(file, "r");
		in2.seek(start);
		final byte[] bs = new byte[(int) (end - 4)];
		if (in2.read(bs) == bs.length) {
			h264nalu = new H264NALU();
			h264nalu.setType(0x1F & bs[0]);
			h264nalu.setData(bs);
		}

		return h264nalu;

	}
}
