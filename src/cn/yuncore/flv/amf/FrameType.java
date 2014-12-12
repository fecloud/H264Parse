/**
 * FrameType.java
 * 2014-12-12
 */
package cn.yuncore.flv.amf;

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
