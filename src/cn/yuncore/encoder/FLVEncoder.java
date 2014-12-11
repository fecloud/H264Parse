package cn.yuncore.encoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import cn.yuncore.flv.FLVScriptTagBody;
import cn.yuncore.flv.lang.ECMA_Array;
import cn.yuncore.flv.lang.Number;
import cn.yuncore.flv.lang.Struct;
import cn.yuncore.io.MediaInputStream;
import cn.yuncore.io.MediaOutputStream;
import cn.yuncore.util.Log;
import cn.yuncore.util.Utils;

public class FLVEncoder {

	static final String TAG = "FLVEncoder";

	private MediaInputStream in;

	private MediaOutputStream out;

	private ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 2);

	public FLVEncoder(MediaInputStream in, MediaOutputStream out) {
		super();
		this.in = in;
		this.out = out;
	}

	/**
	 * 解析视频宽高帧率
	 * 
	 * @param sps
	 */
	protected void encoderOnMetaData(byte[] sps) throws EncoderException {
		try {
			final Struct struct = new Struct();
			struct.put("@setDataFrame");
			struct.put("onMetaData");
			ECMA_Array array = new ECMA_Array();
			array.put("width", new Number(320));
			array.put("height", new Number(240));
			array.put("videodatarate", new Number(0));
			array.put("framerate", new Number(15));
			array.put("videocodecid", new Number(7));
			array.put("encoder", new cn.yuncore.flv.lang.String("Lavf56.4.101"));
			array.put("encoder", new Number(0));
			write(struct.encoder());
		} catch (Exception e) {
			throw new EncoderException("encode onMetaData error", e);
		}
	}

	/**
	 * 编码SPS 与 PPS
	 * 
	 * @param sps
	 * @param pps
	 * @throws IOException
	 */
	protected void encoderSPSPPS(byte[] sps, byte[] pps) throws IOException {
		encoderOnMetaData(sps);

		Log.d(TAG, "encoderSPSPPS");
		buffer.clear();
		// 写入avc头
		buffer.put((byte) 0x17);
		buffer.put((byte) 0x0);
		buffer.put((byte) 0x0);
		buffer.put((byte) 0x0);
		buffer.put((byte) 0x0);

		// 写入AVC file forma
		buffer.put((byte) 0x1);
		buffer.put(sps[1]);
		buffer.put(sps[2]);
		buffer.put(sps[3]);

		// lengthSizeMinusOne
		buffer.put((byte) 0xFF);

		buffer.put((byte) 0xE1);
		// sps nums and data
		buffer.putChar((char) sps.length);
		buffer.put(sps);

		// pps num and data
		buffer.putChar((char) pps.length);
		buffer.put(pps);

		buffer.flip();

		byte[] bytes = new byte[buffer.limit()];
		buffer.get(bytes);
		Log.d(TAG, Utils.formatBytes(bytes));

		write(bytes);
	}

	/**
	 * 成功写入一帧
	 * 
	 * @param bytes
	 * @throws IOException
	 */
	protected void write(byte[] bytes) throws IOException {
		out.write(bytes);
		out.flush();
	}

	/**
	 * 编码普通帧
	 * 
	 * @param bytes
	 */
	protected void encoderData(byte[] bytes) {

	}

	/**
	 * 启动编码
	 * 
	 * @throws IOException
	 */
	public void encoder() throws IOException {
		Log.d(TAG, "encoder");
		byte[] sps = null;
		byte[] pps = null;

		byte[] tbyte = null;
		while (null != (tbyte = in.reader())) {
			switch (tbyte[0] & 0x1F) {
			case 7:
				sps = tbyte;
				break;
			case 8:
				pps = tbyte;
				if (sps != null && pps != null) {
					encoderSPSPPS(sps, pps);
					sps = null;
					pps = null;
				}
				break;
			default:
				break;
			}
		}

	}
}
