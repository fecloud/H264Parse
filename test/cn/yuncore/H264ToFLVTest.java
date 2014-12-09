package cn.yuncore;

import java.io.File;
import java.io.IOException;

import cn.yuncore.encoder.FLVEncoder;
import cn.yuncore.flv.FLVOutPutStream;
import cn.yuncore.h264.H264Reader;

public class H264ToFLVTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		H264Reader reader = new H264Reader(new File("test1.h264"));
		FLVOutPutStream out = new FLVOutPutStream(new File("test1.flv"));
		FLVEncoder encoder = new FLVEncoder(reader, out);
		encoder.encoder();
	}

}
