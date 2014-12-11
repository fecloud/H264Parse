package cn.yuncore.flv;


/**
 * FLV tag body
 * 
 * @author Administrator
 * 
 */
public abstract class FLVTagBody {

	/**
	 * 每一个tag的数据
	 */
	protected byte[] data;

	public abstract void decoder(byte[] bytes) throws CodingException;

	public abstract byte[] encoder() throws CodingException;

}