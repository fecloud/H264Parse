package cn.yuncore.flv;

import java.nio.ByteBuffer;

import cn.yuncore.flv.lang.Struct;

public class FLVScriptTagBody extends FLVTagBody {

	final ByteBuffer buffer = ByteBuffer.allocate(1024);

	private Struct struct = new Struct();

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.lang.FLVData#decoder(byte[])
	 */
	@Override
	public void decoder(byte[] bytes) throws CodingException {
		final ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		struct.decoder(bytes);
	}

	@Override
	public byte[] encoder() throws CodingException {
		if (struct != null)
			return struct.encoder();
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return struct.toString();
	}

}
