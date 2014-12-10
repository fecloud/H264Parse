package cn.yuncore.flv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FLVReader {

	/**
	 * 读取taghead
	 */
	private RandomAccessFile in;

	private long fileLength;

	public FLVReader(File file) {
		super();
		if (file != null && file.exists()) {
			try {
				fileLength = file.length();
				in = new RandomAccessFile(file, "r");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取taghead
	 * 
	 * @return
	 * @throws IOException
	 */
	public FLVFileHead readFileHead() throws IOException {

		/**
		 * FLV文件头9个字节
		 */
		final byte[] bs = new byte[9];
		if (in.read(bs) == bs.length) {
			/**
			 * 是否有音频视频在第5个字节
			 */
			byte av = bs[4];
			final FLVFileHead head = new FLVFileHead();
			/**
			 * 第5个字节的第1位为1 说明有视频
			 */
			if ((av & 0x1) == 0x1) {
				head.setHaveVideo(true);
			}
			/**
			 * 第5个字节的第4位为1 说明有音频
			 */
			if ((av & 0x4) == 0x4) {
				head.setHaveAudio(true);
			}
			return head;
		}
		return null;

	}

	/**
	 * 读取FLV文件tag
	 * 
	 * @return
	 * @throws IOException
	 */
	public FLVTag readerTag() throws IOException {
		final FLVTag flvTag = new FLVTag();
		flvTag.setHeader(readFLVTagHeader());
		if (flvTag.getHeader() == null) {
			return null;
		}
		readFLVTagBody(flvTag);
		return flvTag;
	}

	private FLVTagHeader readFLVTagHeader() throws IOException {
		if (in.getFilePointer() != fileLength) {
			int previousTagSize = in.readInt();
			if (previousTagSize != -1) {
				final FLVTagHeader flvTagHeader = new FLVTagHeader();
				flvTagHeader.setPreviousTagSize(previousTagSize);
				// 每一个tag head 为11个字节(与RTMP类似)
				final byte[] bs = new byte[11];
				if (in.read(bs) == bs.length) {
					// tag 在第一个字节
					flvTagHeader.setType(bs[0]);
					// tag body 长度为234个字节
					flvTagHeader.setDataLength(bytesToInt(bs[1], bs[2], bs[3]));
					// 如果时间戳3位不够会占用4个位
					if (bs[7] != 0) {
						// 4个位数的时间戳
						flvTagHeader.setTimestamp(bytesToInt(bs[4], bs[5],
								bs[6], bs[7]));
					} else {
						// 3个位的时间戳
						flvTagHeader.setTimestamp(bytesToInt(bs[4], bs[5],
								bs[6]));
					}

					flvTagHeader.setStreamid(bytesToInt(bs[8], bs[9], bs[10]));
					flvTagHeader.setDataPostion(in.getFilePointer());
					return flvTagHeader;
				}
			}
		}
		return null;
	}

	/**
	 * 读取FLVtagbody
	 * 
	 * @param tag
	 * @return
	 * @throws IOException
	 */
	private void readFLVTagBody(FLVTag tag) throws IOException {
		if (tag != null && tag.getHeader() != null
				&& tag.getHeader().getDataLength() > 0
				&& tag.getHeader().getDataPostion() > -1) {
			final byte[] bs = new byte[tag.getHeader().getDataLength()];
			if ((in.read(bs)) == bs.length) {
				FLVTagBody body = null;

				if (tag.getHeader().getType() == 0x8) {
					final FLVAudioTagBody audioTagBody = new FLVAudioTagBody();
					audioTagBody.decoder(bs);
					body = audioTagBody;
				} else if (tag.getHeader().getType() == 0x9) {
					final FLVVideoTagBody flvVideoTagBody = new FLVVideoTagBody();
					flvVideoTagBody.decoder(bs);
					body = flvVideoTagBody;
				} else if (tag.getHeader().getType() == 0x12) {
					final FLVScriptTagBody flvScriptTagBody = new FLVScriptTagBody();
					flvScriptTagBody.decoder(bs);
					body = flvScriptTagBody;
				}

				tag.setBody(body);
			}

		}

	}

	/**
	 * byte 转成int
	 * 
	 * @param bytes
	 * @return
	 */
	public static int bytesToInt(byte... bytes) {
		int value = 0;
		for (int i = 0; i < bytes.length; i++) {
			int shift = (bytes.length - 1 - i) * 8;
			value += (bytes[i + 0] & 0x000000FF) << shift;
		}
		return value;
	}

	/**
	 * 关闭reader
	 */
	public void close() {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
