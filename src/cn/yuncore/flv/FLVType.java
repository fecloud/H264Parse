/**
 * FLVType.java
 * 2014-12-12
 */
package cn.yuncore.flv;

/**
 * 内容类型
 * 
 */
public interface FLVType {
	/**
	 * 音频（0x8）
	 */
	byte AUDIO = 0x8;

	/**
	 * 视频（0x9）
	 */
	byte VIDEO = 0x9;

	/**
	 * 脚本（0x12）
	 */
	byte SCRIPT = 0x12;
}
