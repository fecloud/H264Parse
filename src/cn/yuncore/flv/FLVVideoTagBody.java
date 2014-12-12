package cn.yuncore.flv;

import java.nio.ByteBuffer;
import java.util.Arrays;

import cn.yuncore.flv.FLVTagBody;
import cn.yuncore.util.Utils;

/**
 * 视频tagbody
 * 
 * @author Administrator
 * 
 */
public class FLVVideoTagBody extends FLVTagBody {

	/**
	 * ·1-- keyframe
	 * 
	 * ·2 -- inner frame
	 * 
	 * ·3 -- disposable inner frame （h.263 only）
	 * 
	 * ·4 -- generated keyframe
	 */
	private byte frameType;

	/**
	 * 2 -- seronson h.263
	 * 
	 * ·3 -- screen video
	 * 
	 * ·4 -- On2 VP6
	 * 
	 * ·5 -- On2 VP6 with alpha channel
	 * 
	 * ·6 -- Screen video version 2
	 * 
	 * ·7 -- AVC (h.264)
	 */
	private byte codec;

	public int getFrameType() {
		return frameType;
	}

	public void setFrameType(byte frameType) {
		this.frameType = frameType;
	}

	public byte getCodec() {
		return codec;
	}

	public void setCodec(byte codec) {
		this.codec = codec;
	}

	@Override
	public String toString() {
		return "FLVVideoTagBody [frameType=" + Utils.getVideoFrame(frameType)
				+ ", codec=" + Utils.getVideoCodecName(codec) + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.FLVTagBody#decoder(byte[])
	 */
	@Override
	public void decoder(byte[] bytes) throws CodingException {
		if (null == bytes) {
			throw new CodingException("decoder data is empty!");
		}

		final byte video = bytes[0];
		setFrameType((byte) ((0xF0 & video) >> 4));
		setCodec((byte) (0x0F & video));
		this.data = Arrays.copyOfRange(bytes, 1, bytes.length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.FLVTagBody#encoder()
	 */
	@Override
	public byte[] encoder() throws CodingException {
		final ByteBuffer buffer = ByteBuffer.allocate(1 + data.length);
		byte first = frameType;
		first <<= 4;
		first |= codec;
		buffer.put(first);
		buffer.put(data);
		return buffer.array();
	}

}
