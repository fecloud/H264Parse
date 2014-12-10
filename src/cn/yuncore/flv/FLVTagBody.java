package cn.yuncore.flv;

/**
 * FLV tag body
 * 
 * @author Administrator
 * 
 */
public class FLVTagBody {

	
	/**
	 * 每一个tag的数据
	 */
	private byte[] data;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}