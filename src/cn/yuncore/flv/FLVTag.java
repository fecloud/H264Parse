package cn.yuncore.flv;

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

	@Override
	public String toString() {
		return "FLVTag [header=" + header + ", body=" + body + "]";
	}

}