/**
 * H264NALUType.java
 * 2014-12-12
 */
package cn.yuncore.h264;

/**
 * H264 NALU Type
 */
public interface H264NALUType {

	byte SLICE = 0x1;

	byte DPA = 0x2;

	byte DPB = 0x3;

	byte DPC = 0x4;

	byte IDR = 0x5;

	byte SEI = 0x6;

	byte SPS = 0x7;

	byte PPS = 0x8;

	byte AUD = 0x9;

	byte EOSEQ = 0x10;

	byte EOSTREAM = 0x11;

	byte FILL = 0x12;
}
