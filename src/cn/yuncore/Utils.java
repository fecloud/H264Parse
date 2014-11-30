package cn.yuncore;

public final class Utils {

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

}
