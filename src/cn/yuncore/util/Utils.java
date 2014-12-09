package cn.yuncore.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utils {

	public static final String[] HEX = { "0", "1", "2", "3", "4", "5", "6",
		"7", "8", "9", "A", "B", "C", "D", "E", "F" };
	
	public static String getNALUTypeName(int type) {
		switch (type) {
		case 1:
			return "NALU_TYPE_SLICE";
		case 2:
			return "NALU_TYPE_DPA";
		case 3:
			return "NALU_TYPE_DPB";
		case 4:
			return "NALU_TYPE_DPC";
		case 5:
			return "NALU_TYPE_IDR";
		case 6:
			return "NALU_TYPE_SEI";
		case 7:
			return "NALU_TYPE_SPS";
		case 8:
			return "NALU_TYPE_PPS";
		case 9:
			return "NALU_TYPE_AUD";
		case 10:
			return "NALU_TYPE_EOSEQ";
		case 11:
			return "NALU_TYPE_EOSTREAM";
		case 12:
			return "NALU_TYPE_FILL";

		default:
			break;
		}
		return null;
	}

	public static String formatBytes(byte[] bytes)
			throws UnsupportedEncodingException {
		final StringBuilder builder = new StringBuilder("{ ");
		for (String s : HEX) {
			builder.append("0").append(s).append(" ");
		}
		builder.append("}\r\n\r\n");

		if (null != bytes && bytes.length > 0) {
			StringBuilder hex = new StringBuilder();
			StringBuilder asill = new StringBuilder();
			int line = bytes.length / 16;

			if (bytes.length % 16 > 0) {
				line += 1;
			}

			for (int j = 0; j < line; j++) {

				for (int i = j * 16; i < (j + 1) * 16; i++) {
					if (i < bytes.length) {
						final int b = 0xFF & bytes[i];
						String bToHex = Integer.toHexString(b);
						if (bToHex.length() == 1) {
							bToHex = "0" + bToHex;
						}
						hex.append(bToHex.toUpperCase() + " ");
						if (b < 127 && b > 31) {
							asill.append((char) (bytes[i])).append(" ");
						} else {
							asill.append(".").append(" ");
						}
					} else {
						break;
					}
				}
				hex.insert(0, "[ ");
				hex.append("]");

				asill.insert(0, "[ ");
				asill.append("]");

				builder.append(hex).append(asill).append("\r\n");

				hex = new StringBuilder();
				asill = new StringBuilder();

			}
		}

		return builder.toString();
	}
	
	/**
	 * tag类型
	 * 
	 * @param type
	 * @return
	 */
	public static String getTagType(int type) {
		switch (type) {
		case 0x8:
			return "audio";
		case 0x9:
			return "video";
		case 0x12:
			return "script";
		default:
			break;
		}
		return null;
	}

	/**
	 * tag body 解码器类型
	 * 
	 * @param codec
	 * @return
	 */
	public static String getVideoCodecName(int codec) {
		switch (codec) {
		case 2:
			return "seronson h.263";
		case 3:
			return "screen video";
		case 4:
			return "On2 VP6";
		case 5:
			return "On2 VP6 with alpha channel";
		case 6:
			return "Screen video version 2";
		case 7:
			return "AVC (h.264)";
		default:
			break;
		}
		return null;
	}

	/**
	 * 取音频格式
	 * 
	 * @param format
	 * @return
	 */
	public static String getAudioFormat(int format) {
		switch (format) {
		case 0:
			return "UNCOMPREES";
		case 1:
			return "ADPCM";
		case 2:
			return "MP3";
		case 4:
			return "Nellymoser 16-kHz mono";
		case 5:
			return "Nellymoser 8-kHz mono";
		case 10:
			return "AAC";
		default:
			break;
		}
		return null;
	}

	public static String getAudioSamplerate(int samplerate) {

		switch (samplerate) {
		case 0:
			return "5.5kHz";
		case 1:
			return "11kHz";
		case 2:
			return "22kHz";
		case 3:
			return "44kHz";
		default:
			break;
		}
		return null;

	}

	/**
	 * 音频采样率
	 * 
	 * @param snd
	 * @return
	 */
	public static String getAudioSnd(int snd) {
		switch (snd) {
		case 0:
			return "snd8Bit";
		case 1:
			return "snd16Bit";
		default:
			break;
		}
		return null;
	}

	public static String getAudioSndType(int sndType) {
		switch (sndType) {
		case 0:
			return "sndMomo";
		case 1:
			return "sndStereo";
		default:
			break;
		}
		return null;
	}

	/**
	 * 取视频帧类型
	 * 
	 * @param frame
	 * @return
	 */
	public static String getVideoFrame(int frame) {
		switch (frame) {
		case 1:
			return "keyframe";
		case 2:
			return "inner frame";
		case 3:
			return "disposable inner frame （h.263 only）";
		case 4:
			return "generated keyframe";
		default:
			break;
		}
		return null;

	}
	
	public static String formatNow(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SS");
		return format.format(new Date());
	}
	
	public static byte[] int2Byte(int i){
		final byte[] bystes = new byte[4];
		bystes[0] = (byte) ((0xFF000000 & i) >>24);
		bystes[0] = (byte) ((0xFF0000 & i) >>16);
		bystes[0] = (byte) ((0xFF00 & i) >>8);
		bystes[0] = (byte) ((0xFF & i));
		return bystes;
	}
}
