package cn.yuncore;

import java.io.File;
import java.io.IOException;

public class H264Parse {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		H264Reader h264Reader = new H264Reader(new File("test.h264"));
		H264NALU h264nalu = null;

		while (null != (h264nalu = h264Reader.reader())) {

			System.out.println(h264nalu);
			if (h264nalu.getType() == 7 || h264nalu.getType() == 8) {
				for (byte b : h264nalu.getData()) {
					System.out.print(Integer.toHexString((0xFF & b)) + " ");
				}
			}
			System.out.println();

		}
	}

}
