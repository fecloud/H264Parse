package cn.yuncore.flv;

import cn.yuncore.flv.lang.FLVData;

/**
 * FLV tag body
 * 
 * @author Administrator
 * 
 */
public abstract class FLVTagBody implements FLVData {

	
	/**
	 * 每一个tag的数据
	 */
	protected byte[] data;
//	
//	/**
//	 * 解码数据
//	 * @param data
//	 * @throws CodingException
//	 */
//	public abstract void decoder(byte[] data) throws CodingException;
//	
//	/**
//	 * 编码数据
//	 * @return
//	 * @throws CodingException
//	 */
//	public abstract byte[] encoder() throws CodingException;
//	

}