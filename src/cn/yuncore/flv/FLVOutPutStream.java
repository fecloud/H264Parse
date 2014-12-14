package cn.yuncore.flv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import cn.yuncore.flv.amf.FrameType;
import cn.yuncore.io.MediaOutputStream;

public class FLVOutPutStream implements MediaOutputStream {

	private FileOutputStream file;

	private FLVTag tag;

	private int time;

	public FLVOutPutStream(File file) throws IOException {
		this.file = new FileOutputStream(file);
		writeFileHeader();
	}

	/**
	 * 写入FLV文件头
	 * 
	 * @throws IOException
	 */
	private void writeFileHeader() throws IOException {

		final byte flvFileHeader[] = { 0x46, 0x4c, 0x56, 0x01, 0x01, 0x00,
				0x00, 0x00, 0x09 };
		file.write(flvFileHeader);
		file.flush();

	}

	/**
	 * 写入一个TAG
	 * 
	 * @param bytes
	 * @throws IOException
	 */
	public void writeTag(byte bodyType, byte[] bytes) throws IOException {
		final FLVTag flvTag = new FLVTag();
		final FLVTagHeader header = new FLVTagHeader();
		final FLVTagBody tagBody = getFlvTagBody(bodyType);
		tagBody.decoder(bytes);
		header.setDataLength(bytes.length);
		if (null == tag) {
			header.setPreviousTagSize(0);
		} else {
			header.setPreviousTagSize(tag.getLength());
		}
		header.setType(bodyType);
		header.setTimestamp(time);
		if (tagBody instanceof FLVVideoTagBody) {
			final FLVVideoTagBody flvVideoTagBody = (FLVVideoTagBody) tagBody;
			if (flvVideoTagBody.getFrameType() == FrameType.INNER_FRAME) {
				time += 40;
			}
		}

		flvTag.setHeader(header);
		flvTag.setBody(tagBody);
		this.tag = flvTag;
		file.write(flvTag.encoder());
	}

	protected FLVTagBody getFlvTagBody(byte type) {
		switch (type) {
		case FLVType.AUDIO:
			return new FLVAudioTagBody();
		case FLVType.VIDEO:
			return new FLVVideoTagBody();
		case FLVType.SCRIPT:
			return new FLVScriptTagBody();
		default:
			break;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.io.MediaOutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] bytes) throws IOException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.io.MediaOutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		file.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.io.MediaOutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		file.close();
	}

	/**
	 * 解析SPS 写入onMetaData
	 */
	protected void decoderSPS(byte[] bytes) {
		final ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		buffer.getInt();
	}

}
