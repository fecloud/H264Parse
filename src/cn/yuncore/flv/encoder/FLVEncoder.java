package cn.yuncore.flv.encoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.yuncore.FLVOutPutStream;
import cn.yuncore.h264.H264NALU;
import cn.yuncore.h264.H264Reader;

public class FLVEncoder {

	private  H264Reader h264Reader;
	
	private FLVOutPutStream out;

	public FLVEncoder(String out, String in) throws IOException {
		super();
		if(checkInAndOut(in,out)){
			this.h264Reader = new H264Reader(new File(in));
			this.out = new FLVOutPutStream(new FileOutputStream(out));
		}else {
			throw new IOException("check in or out file");
		}
	}

	public boolean checkInAndOut(String in ,String out){
		
		if(new File(in).exists()){
			if(!new File(out).exists() &&new File(out).canWrite()){
				return true;
			}
		}
		
		return  false;
		
	}
	
	private void encoderSPSPPS(H264Reader reader) throws IOException{
		H264NALU sps = h264Reader.reader();
		H264NALU pps = h264Reader.reader();
		if(sps !=null && pps !=null){
			
		}
	}

	public void encoder() throws IOException {
			long start = System.currentTimeMillis();
			H264NALU h264nalu = null;
			while (null != (h264nalu = h264Reader.reader())) {
				
				

			}
			h264Reader.close();
			System.out.println(String.format("finish time:%sms",
					System.currentTimeMillis() - start));
			
	}
}
