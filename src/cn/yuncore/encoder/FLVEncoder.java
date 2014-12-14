package cn.yuncore.encoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import cn.yuncore.flv.FLVOutPutStream;
import cn.yuncore.flv.FLVType;
import cn.yuncore.flv.amf.Codec;
import cn.yuncore.flv.amf.FrameType;
import cn.yuncore.flv.lang.ECMA_Array;
import cn.yuncore.flv.lang.Number;
import cn.yuncore.flv.lang.Struct;
import cn.yuncore.h264.H264NALUType;
import cn.yuncore.io.MediaInputStream;
import cn.yuncore.util.Log;
import cn.yuncore.util.Utils;

public class FLVEncoder {

	static final String TAG = "FLVEncoder";

	static final byte AVC_PACKAGE = 0x0;
	static final byte AVC = 0x1;
	static final byte AVC_END = 0x2;

	private MediaInputStream in;

	private FLVOutPutStream out;

	private byte[] sps;
	private byte[] pps;

	private byte[] sei;
	private byte[] idr;

	private boolean findSPSPPS = false;
	private boolean findSEIIDR = false;

	private ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 2);

	public FLVEncoder(MediaInputStream in, FLVOutPutStream out) {
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
			// struct.put("@setDataFrame");
			struct.put("onMetaData");
			ECMA_Array array = new ECMA_Array();
			array.put("duration", new Number(18.347));
			array.put("width", new Number(320));
			array.put("height", new Number(240));
			array.put("videodatarate", new Number(0));
			array.put("framerate", new Number(15));
			array.put("videocodecid", new Number(7));
			array.put("encoder", new cn.yuncore.flv.lang.String("Lavf56.4.101"));
			array.put("filesize", new Number(330329));
			struct.putArray(array);
			writeTag(FLVType.SCRIPT, struct.encoder());
			Log.d(TAG, Utils.formatBytes(struct.encoder()));
		} catch (Exception e) {
			throw new EncoderException("encode onMetaData error", e);
		}
	}

	/**
	 * 编码SPS 与 PPS 一个文件中 可能会出现多个,只解析一个
	 * 
	 * @param sps
	 * @param pps
	 * @throws IOException
	 */
	protected void encoderSPSPPS() throws IOException {
		encoderOnMetaData(sps);

		Log.d(TAG, "encoderSPSPPS");
		buffer.clear();
		// 写入avc头
		buffer.put((byte) ((FrameType.KEYF_RAME << 4) | Codec.AVC_H264));
		buffer.put((byte) AVC_PACKAGE).put((byte) 0x0).put((byte) 0x0)
				.put((byte) 0x0);

		// 写入AVC file forma
		buffer.put((byte) 0x1).put(sps[1]).put(sps[2]).put(sps[3]);

		// lengthSizeMinusOne
		buffer.put((byte) 0xFF);

		buffer.put((byte) 0xE1);
		// sps nums and data
		buffer.putChar((char) sps.length).put(sps);

		// pps num and data
		buffer.put((byte) 0x1).putChar((char) pps.length).put(pps);

		buffer.flip();

		final byte[] bytes = new byte[buffer.limit()];
		buffer.get(bytes);

		writeTag(FLVType.VIDEO, bytes);
	}

	/**
	 * 一个文件中有多个IDR 只有一个SEI
	 * 
	 * @throws IOException
	 */
	protected void encodeSEI() throws IOException {
		buffer.clear();
		buffer.put((byte) ((FrameType.KEYF_RAME << 4) | Codec.AVC_H264));
		buffer.put((byte) AVC).put((byte) 0x0).put((byte) 0x0).put((byte) 0x0);
		// 写入SPS信息
		buffer.putInt(sps.length).put(sps);

		// 写入PPS信息
		buffer.putInt(pps.length).put(pps);

		if (!findSEIIDR)
			buffer.putInt(sei.length).put(sei);
		findSEIIDR = true;

		buffer.putInt(idr.length).put(idr);

		buffer.flip();

		final byte[] bs = new byte[buffer.limit()];
		buffer.get(bs);
		writeTag(FLVType.VIDEO, bs);
	}

	/**
	 * 编码普通帧
	 * 
	 * @param frameType
	 * @param cocdec
	 * @param data
	 * @return
	 * @throws IOException
	 */
	protected void encodeFrame(byte frameType, byte cocdec, byte[] data)
			throws IOException {
		buffer.clear();
		buffer.put((byte) ((frameType << 4) | cocdec));
		buffer.put((byte) AVC).put((byte) 0x0).put((byte) 0x0).put((byte) 0x0);
		// 写入h264长度加数据
		buffer.putInt(data.length).put(data);
		buffer.flip();
		byte[] bytes = new byte[buffer.limit()];
		buffer.get(bytes);
		writeTag(FLVType.VIDEO, bytes);
	}

	/**
	 * 结束帧
	 * 
	 * @throws IOException
	 */
	protected void encodeEndFrame() throws IOException {
		buffer.clear();
		buffer.put((byte) ((FrameType.KEYF_RAME << 4) | Codec.AVC_H264));
		buffer.put((byte) AVC_END).put((byte) 0x0).put((byte) 0x0)
				.put((byte) 0x0);
		buffer.flip();
		byte[] bytes = new byte[buffer.limit()];
		buffer.get(bytes);
		writeTag(FLVType.VIDEO, bytes);
	}

	/**
	 * 成功写入一帧
	 * 
	 * @param bytes
	 * @throws IOException
	 */
	protected void writeTag(byte type, byte[] bytes) throws IOException {
		Log.d(TAG, Utils.formatBytes(bytes));
		out.writeTag(type, bytes);
		out.flush();
	}

	/**
	 * 启动编码
	 * 
	 * @throws IOException
	 */
	public void encoder() throws IOException {
		Log.d(TAG, "encoder");

		byte[] tbyte = null;
		while (null != (tbyte = in.reader())) {
			switch (tbyte[0] & 0x1F) {
			case H264NALUType.SEI:
				sei = tbyte;
				break;
			case H264NALUType.IDR:
				idr = tbyte;
				if (sei != null && idr != null) {
					encodeSEI();
				}
				break;
			case H264NALUType.SPS:
				sps = tbyte;
				break;
			case H264NALUType.PPS:
				pps = tbyte;
				if (sps != null && pps != null && !findSPSPPS) {
					encoderSPSPPS();
					findSPSPPS = true;
				}
				break;
			default:
				encodeFrame(FrameType.INNER_FRAME, Codec.AVC_H264, tbyte);
				break;
			}
		}

		encodeEndFrame();

	}
}
