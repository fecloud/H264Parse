/**
 * AudioFormat.java
 * 2014-12-12
 */
package cn.yuncore.flv.amf;

/**
 * 音频格式
 */
public interface AudioFormat {

	/**
	 * 未压缩
	 */
	byte UNCOMPRESSED = 0x0;
	
	/**
	 * ADPCM
	 */
	byte ADPCM = 0x1;
	
	/**
	 * MP3
	 */
	byte MP3 = 0x2;
	
	/**
	 * Nellymoser 16-kHz mono
	 */
	byte NELLYMOSER_16K = 0x4;
	
	/**
	 * Nellymoser 8-kHz mono
	 */
	byte NELLYMOSER_8K = 0x5;
	
	/**
	 * AAC
	 */
	byte AAC = 0x10;

}
