package cn.yuncore.flv;

import java.nio.ByteBuffer;

/**
 * FLV tag
 * 
 * @author Administrator 一个tag分为两个部分 header 与body
 */
public class FLVTag {

	private FLVTagHeader header;

	private FLVTagBody body;

	private int length;

	public FLVTagHeader getHeader() {
		return header;
	}

	public void setHeader(FLVTagHeader header) {
		this.header = header;
	}

	public FLVTagBody getBody() {
		return body;
	}

	public void setBody(FLVTagBody body) {
		this.body = body;
	}

	public byte[] encoder() throws CodingException {
		final ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 2);
		buffer.put(header.encoder());
		buffer.put(body.encoder());
		buffer.flip();
		final byte[] bytes = new byte[buffer.limit()];
		buffer.get(bytes);
		// 除去上一个tag的size
		this.length = bytes.length - 4;
		return bytes;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	@Override
	public String toString() {
		return "FLVTag [header=" + header + ", body=" + body + "]";
	}

}