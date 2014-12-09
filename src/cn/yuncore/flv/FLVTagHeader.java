package cn.yuncore.flv;

import cn.yuncore.util.Utils;

/**
 * tag header
 * @author Administrator
 *
 */
public class FLVTagHeader {

	/**
	 * 1到4个字节为上一个tag的总长度
	 */
	private int previousTagSize;

	/**
	 * 音频（0x8），视频（0x9），脚本（0x12）
	 */
	private byte type;

	/**
	 * flvbody的长度
	 */
	private int dataLength;

	/**
	 * 这一帧或者视频的时间戳
	 */
	private int timestamp;

	/**
	 * 流id(好像总是为0)
	 */
	private int streamid;

	/**
	 * tag body 在整个文件的起始位置
	 */
	private long dataPostion;

	public int getPreviousTagSize() {
		return previousTagSize;
	}

	public void setPreviousTagSize(int previousTagSize) {
		this.previousTagSize = previousTagSize;
	}

	public int getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public int getStreamid() {
		return streamid;
	}

	public void setStreamid(int streamid) {
		this.streamid = streamid;
	}

	public long getDataPostion() {
		return dataPostion;
	}

	public void setDataPostion(long dataPostion) {
		this.dataPostion = dataPostion;
	}
	
	/**
	 * tag头二进制数据
	 * @return
	 */
	public byte[] toBytes(){
		final byte [] bytes = new byte[15];
		
		//写入上一个tag的大小
		final byte [] previousTagSizeBytes = Utils.int2Byte(previousTagSize);
		bytes[0] = previousTagSizeBytes[0];
		bytes[1] = previousTagSizeBytes[1];
		bytes[2] = previousTagSizeBytes[2];
		bytes[3] = previousTagSizeBytes[3];
		
		//写入类型
		bytes[4] = type;
		
		//写入body长度
		final byte[] bodyLengthBytes = Utils.int2Byte(dataLength);
		bytes[5] = bodyLengthBytes[1];
		bytes[6] = bodyLengthBytes[2];
		bytes[7] = bodyLengthBytes[3];
		
		
		
		return bytes;
	}

	@Override
	public String toString() {
		return "FLVHeader [previousTagSize=" + previousTagSize + ", type=" + Utils.getTagType(type)
				+ ", dataLength=" + dataLength + ", timestamp=" + timestamp
				+ ", streamid=" + streamid + ", dataPostion=" + dataPostion
				+ "]";
	}

}
