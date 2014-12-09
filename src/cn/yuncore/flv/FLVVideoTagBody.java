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
	 * ·1-- keyframe
	 * 
	 * ·2 -- inner frame
	 * 
	 * ·3 -- disposable inner frame （h.263 only）
	 * 
	 * ·4 -- generated keyframe
	 */
	private int frameType;

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
	private int codec;

	public int getFrameType() {
		return frameType;
	}

	public void setFrameType(int frameType) {
		this.frameType = frameType;
	}

	public int getCodec() {
		return codec;
	}

	public void setCodec(int codec) {
		this.codec = codec;
	}

	@Override
	public String toString() {
		return "FLVVideoTagBody [frameType=" + Utils.getVideoFrame(frameType)
				+ ", codec=" + Utils.getVideoCodecName(codec) + "]";
	}

}
