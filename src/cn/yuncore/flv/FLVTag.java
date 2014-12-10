package cn.yuncore.flv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * FLV tag
 * 
 * @author Administrator 一个tag分为两个部分 header 与body
 */
public class FLVTag {

	private FLVTagHeader header;

	private FLVTagBody body;

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

	public int getLength() {
		return 0;
	}

	/**
	 * tag二进制数据
	 * 
	 * @return
	 * @throws IOException 
	 */
	public byte[] toBytes() throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(header.toBytes());
		out.write(body.encoder());
		return out.toByteArray();
	}

	@Override
	public String toString() {
		return "FLVTag [header=" + header + ", body=" + body + "]";
	}

}