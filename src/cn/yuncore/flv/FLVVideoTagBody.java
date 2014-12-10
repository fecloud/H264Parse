package cn.yuncore.flv;

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
	 * 视频帧类型
	 * 
	 */
	public interface FrameType {

		/**
		 * ·1-- keyframe
		 */
		byte KEYF_RAME = 0x1;

		/**
		 * 2 -- inner frame
		 */
		byte INNER_FRAME = 0x2;

		/**
		 * 3 -- disposable inner frame （h.263 only）
		 */
		byte DISPOSABLE_INNER_FRAME = 0x3;

		/**
		 * 4 -- generated keyframe
		 */
		byte GENERATED_KEYFRAME = 0x4;

	}

	/**
	 * 视频帧编码类型
	 * 
	 */
	public interface Codec {
		/**
		 * seronson h.263
		 */
		byte SERONSON_H263 = 0x2;

		/**
		 * screen video
		 */
		byte SCREEN_VIDEO = 0x3;

		/**
		 * On2 VP6
		 */
		byte ON2_VP6 = 0x4;

		/**
		 * On2 VP6 with alpha channel
		 */
		byte ON2_VP6_WITH_ALPHA_CHANNEL = 0x5;

		/**
		 * Screen video version 2
		 */
		byte SCREEN_VIDEO_VERSION_2 = 0x6;
		
		/**
		 * AVC (h.264)
		 */
		byte AVC_H264 = 0x7;

	}

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

}
