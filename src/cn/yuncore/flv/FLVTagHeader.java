package cn.yuncore.flv;

import java.nio.ByteBuffer;

import cn.yuncore.util.Utils;

/**
 * tag header
 * 
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

	public void decoder(byte[] bytes) throws CodingException {
		final ByteBuffer buffer = ByteBuffer.allocate(15);
		buffer.put(bytes);
		buffer.flip();
		previousTagSize = buffer.getInt();
		type = buffer.get();
		dataLength = buffer.getInt();
		dataLength >>= 8;
		buffer.position(buffer.position() - 1);

		timestamp = buffer.getInt();
		if ((timestamp & 0xFF) == 0) {
			timestamp >>= 8;
		}
		buffer.position(buffer.position() - 1);
		streamid = buffer.getInt();
		streamid &= 0xFFFFFF;

	}

	public byte[] encoder() throws CodingException {
		final ByteBuffer buffer = ByteBuffer.allocate(15);
		// 写入上一个tag的大小
		buffer.putInt(previousTagSize);

		// 写入类型
		buffer.put(type);

		// 写入body长度
		final byte[] bodyLengthBytes = Utils.int2Byte(dataLength);
		buffer.put(bodyLengthBytes[1]).put(bodyLengthBytes[2])
				.put(bodyLengthBytes[3]);

		if (timestamp >= 0xFFFFFFFF) {
			buffer.putInt(timestamp);
		} else {
			buffer.putInt(timestamp << 8);
		}
		final byte[] streamidBytes = Utils.int2Byte(streamid);
		buffer.put(streamidBytes[1]).put(streamidBytes[2])
				.put(streamidBytes[3]);
		buffer.flip();
		return buffer.array();
	}

	@Override
	public String toString() {
		return "FLVHeader [previousTagSize=" + previousTagSize + ", type="
				+ Utils.getTagType(type) + ", dataLength=" + dataLength
				+ ", timestamp=" + timestamp + ", streamid=" + streamid + "]";
	}

}
