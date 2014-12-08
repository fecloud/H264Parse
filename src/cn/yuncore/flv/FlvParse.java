package cn.yuncore.flv;

import java.io.File;
import java.io.IOException;

import cn.yuncore.Utils;

/**
 * 锟斤拷取FLV锟侥硷拷tag
 * 
 * @author Administrator
 * 
 */
public class FlvParse {

	public static void main(String[] args) throws IOException {
		int line = -1;
		File file = new File("test.flv");

		if (args != null && args.length > 0) {
			file = new File(args[0]);
		}
		if (args != null && args.length > 1) {
			line = Integer.valueOf(args[1]);
		}
		FLVReader reader = new FLVReader(file);
		System.out.println(String.format("[name:%s len:%s %sKB]",
				file.getName(), file.length(), file.length() / 1024));
		final FLVFileHead flvHead = reader.readFileHead();
		if (flvHead != null) {

			System.out.println(flvHead);
			System.out.println("");
			int i = 0;
			FLVTag temp = null;
			while (null != (temp = reader.readerTag())
					&& (line == -1 || line > i)) {
				i++;
				System.out.println("[index:" + i + "] " + temp + "\r\n");

				if (temp.getBody() != null && temp.getBody().getData() != null) {
					System.out.println(Utils.formatBytes(temp.getBody()
							.getData()));
				}

			}

			reader.close();
		}
	}
}