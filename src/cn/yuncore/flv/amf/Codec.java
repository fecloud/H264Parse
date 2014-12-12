/**
 * Codec.java
 * 2014-12-12
 */
package cn.yuncore.flv.amf;

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