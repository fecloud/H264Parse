package cn.yuncore.h264;

import java.io.File;
import java.io.IOException;

import cn.yuncore.util.Utils;

public class H264Parse {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final long start = System.currentTimeMillis();
		int line = -1;
		File file = new File("my_funny_video.h264");

		if (args != null && args.length > 0) {
			file = new File(args[0]);
		}
		if (args != null && args.length > 1) {
			line = Integer.valueOf(args[1]);
		}

		H264Reader h264Reader = new H264Reader(file);

		H264NALU h264nalu = null;
		int i = 0;
		while (null != (h264nalu = h264Reader.readerH264())
				&& (line == -1 || line > i)) {
			//i++;
			//System.out.println("====================================================");
//			if (h264nalu.getType() == 5) {
				// System.out.println("[index:" + i + "] " + h264nalu + "\r\n");
//			}
			//System.out.println(String.format("index:%s %s", i ,h264nalu));
//			if (h264nalu.getType() == 7 || h264nalu.getType() == 8) {
				//System.out.println(Utils.formatBytes(h264nalu.getData()));
//			}

		}
		h264Reader.close();
		System.out.println(String.format("finish time:%sms",
				System.currentTimeMillis() - start));
	}

}
