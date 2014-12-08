package cn.yuncore.h264;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class H264Reader {

	private File file;

	private RandomAccessFile in;

	private long index;

	public H264Reader(File file) throws IOException {
		super();
		this.file = file;
		if (file != null && file.exists()) {
			in = new RandomAccessFile(file, "r");
		}
		System.out.println(String.format("[name:%s len:%s %sKB]",
				file.getName(), file.length(), file.length() / 1024));
		if (findNALU() == file.length() - 1) {
			throw new RuntimeException("data is not found nalu");
		}
	}

	private long findNALU() throws IOException {
		while (in.getFilePointer() < file.length()) {
			if (in.readByte() == 0) {
				final byte[] bs = new byte[3];
				int len = in.read(bs);

				if (len == 3) {
					if (bs[2] == 0x1 && bs[0] == bs[1]) {
						index = in.getFilePointer();
						return in.getFilePointer() - 4;
					} else if (bs[0] == 0 && bs[1] == 0x1) {
						index = in.getFilePointer() - 1;
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
		long start = in.getFilePointer();
		long end = findNALU();
		System.out.println(String.format("start:%s end:%s", start, end));
		final byte[] bs = new byte[(int) (end - start)];

		in.seek(start);
		int len = in.read(bs);
		in.seek(end);
		h264nalu = new H264NALU();
		h264nalu.setData(bs);
		if (h264nalu.getData() != null && h264nalu.getData().length == len)
			h264nalu.setType(0xFF & (0x1F & h264nalu.getData()[0]));
		in.seek(index);
		return h264nalu;

	}
}
