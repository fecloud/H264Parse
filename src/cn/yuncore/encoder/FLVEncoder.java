package cn.yuncore.encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.yuncore.io.MediaInputStream;
import cn.yuncore.io.MediaOutputStream;
import cn.yuncore.util.Log;

public class FLVEncoder {

	static final String TAG = "FLVEncoder";
	
	private MediaInputStream in;

	private MediaOutputStream out;

	private ByteArrayOutputStream temp = new ByteArrayOutputStream();

	public FLVEncoder(MediaInputStream in, MediaOutputStream out) {
		super();
		this.in = in;
		this.out = out;
	}

	/**
	 * 编码SPS 与 PPS
	 * 
	 * @param sps
	 * @param pps
	 * @throws IOException
	 */
	private void encoderSPSPPS(byte[] sps, byte[] pps) throws IOException {
		
		Log.d(TAG, "encoderSPSPPS");
		temp.reset();
		final byte[] avc = new byte[5];
		avc[0] = 0x17;
		// 写入avc头
		temp.write(avc);

		final byte[] avc_config = new byte[7];
		avc_config[0] = 0x1;
		avc_config[1] = sps[1];
		avc_config[2] = sps[2];
		avc_config[3] = sps[3];
		// 写入AVC file forma
		temp.write(avc_config);

		// 写入PPS的长度
		final int pps_length = pps.length;
		temp.write(new byte[] { (byte) ((0xFF00 & pps_length) >> 8),
				(byte) (0xFF & pps_length) });
		// 写入PPS
		temp.write(pps);

		// 成功写入一帧
		out.write(temp.toByteArray());
		out.flush();
	}

	/**
	 * 启动编码
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
