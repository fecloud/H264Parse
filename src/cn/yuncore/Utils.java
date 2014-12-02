package cn.yuncore;

import java.io.UnsupportedEncodingException;

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
}
