package cn.yuncore.flv;

import java.nio.ByteBuffer;
import java.util.Arrays;

import cn.yuncore.util.Utils;

public class FLVAudioTagBody extends FLVTagBody {

	/**
	 * ·0 -- 未压缩
	 * 
	 * ·1 -- ADPCM
	 * 
	 * ·2 -- MP3
	 * 
	 * ·4 -- Nellymoser 16-kHz mono
	 * 
	 * ·5 -- Nellymoser 8-kHz mono
	 * 
	 * ·10 -- AAC
	 */
	private byte format;

	/**
	 * ·0 -- 5.5KHz
	 * 
	 * ·1 -- 11kHz
	 * 
	 * ·2 -- 22kHz
	 * 
	 * ·3 -- 44kHz
	 */
	private byte samplerate;

	/**
	 * ·0 -- snd8Bit
	 * 
	 * ·1 -- snd16Bit
	 */
	private byte snd;

	/**
	 * 0 -- sndMomo
	 * 
	 * ·1 -- sndStereo
	 */
	private byte sndType;

	public byte getFormat() {
		return format;
	}

	public void setFormat(byte format) {
		this.format = format;
	}

	public byte getSamplerate() {
		return samplerate;
	}

	public void setSamplerate(byte samplerate) {
		this.samplerate = samplerate;
	}

	public byte getSnd() {
		return snd;
	}

	public void setSnd(byte snd) {
		this.snd = snd;
	}

	public byte getSndType() {
		return sndType;
	}

	public void setSndType(byte sndType) {
		this.sndType = sndType;
	}

	@Override
	public String toString() {
		return "FLVAudioTagBody [format=" + Utils.getAudioFormat(format)
				+ ", samplerate=" + Utils.getAudioSamplerate(samplerate)
				+ ", snd=" + Utils.getAudioSnd(snd) + ", sndType="
				+ Utils.getAudioSndType(sndType) + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.FLVTagBody#decoder(byte[])
	 */
	@Override
	public void decoder(byte[] bytes) throws CodingException {
		if (null == data) {
			throw new CodingException("decoder data is empty!");
		}
		final byte audio = data[0];
		setFormat((byte) ((0xF0 & audio) >> 4));
		setSamplerate((byte) ((0x0C & audio) >> 2));
		setSnd((byte) ((0x2 & audio) >> 1));
		setSndType((byte) (0x1 & audio));
		this.data = Arrays.copyOfRange(data, 1, data.length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.yuncore.flv.FLVTagBody#encoder()
	 */
	@Override
	public byte[] encoder() throws CodingException {
		final ByteBuffer buffer = ByteBuffer.allocate(1 + data.length);
		byte first = format;
		first <<= 4;
		first |= (samplerate << 2);
		first |= (snd << 1);
		first |= sndType;
		buffer.put(first);
		buffer.put(data);
		return buffer.array();
	}

}
