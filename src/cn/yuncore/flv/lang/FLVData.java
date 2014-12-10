/**
 * FLVDataType.java
 * 2014-12-10
 * 深圳市五月高球信息咨询有限公司
 * 欧阳丰
 */
package cn.yuncore.flv.lang;

import cn.yuncore.flv.CodingException;

/**
 * @author 欧阳丰
 * 
 */
public interface FLVData {

	
	/**
	 * (Encoded as IEEE 64-bit double-precision floating point number)
	 */
	byte NUMBER = 0x00;
	/**
	 * (Encoded as a single byte of value 0x00 or 0x01)
	 */
	byte BOOLEAN = 0x01;//

	/**
	 * (16-bit integer string length with UTF-8 string)
	 */
	byte STRING = 0x02;//

	/**
	 * (32-bit entry count)
	 */
	byte OBJECT = 0x03;

	byte NULL = 0x05;

	/**
	 * (32-bit entry count)
	 */
	byte ECMA_ARRAY = 0x08;

	/**
	 * (preceded by an empty 16-bit string length)
	 */
	byte OBJECT_END = 0x09;

	/**
	 * (32-bit entry count)
	 */
	byte STRICT_ARRAY = 0x0a;

	/**
	 * (Encoded as IEEE 64-bit double-precision floating point number with
	 * 16-bit integer timezone offset)
	 */
	byte DATE = 0x0b;

	/**
	 * (32-bit integer string length with UTF-8 string)
	 */
	byte LONG_STRING = 0x0c;

	/**
	 * (32-bit integer string length with UTF-8 string)
	 */
	byte XML_DOCUMENT = (byte) 0xFF;
	/**
	 * (16-bit integer name length with UTF-8 name, followed by entries)
	 */
	byte TYPED_OBJECT = 0x10;

	byte SWITCH_TO_AMF3 = 0x11;
	
	/**
	 * 数据类型
	 * @return
	 */
	byte getType();
	
	/**
	 * 解码
	 * 
	 * @param bytes
	 */
	void decoder(byte[] bytes) throws CodingException;

	/**
	 * 编码
	 * 
	 * @return
	 */
	byte[] encoder()throws CodingException;

}
