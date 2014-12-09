package cn.yuncore.flv;

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
	private int format;

	/**
	 * ·0 -- 5.5KHz
	 * 
	 * ·1 -- 11kHz
	 * 
	 * ·2 -- 22kHz
	 * 
	 * ·3 -- 44kHz
	 */
	private int samplerate;

	/**
	 * ·0 -- snd8Bit
	 * 
	 * ·1 -- snd16Bit
	 */
	private int snd;

	/**
	 * 0 -- sndMomo
	 * 
	 * ·1 -- sndStereo
	 */
	private int sndType;

	public int getFormat() {
		return format;
	}

	public void setFormat(int format) {
		this.format = format;
	}

	public int getSamplerate() {
		return samplerate;
	}

	public void setSamplerate(int samplerate) {
		this.samplerate = samplerate;
	}

	public int getSnd() {
		return snd;
	}

	public void setSnd(int snd) {
		this.snd = snd;
	}

	public int getSndType() {
		return sndType;
	}

	public void setSndType(int sndType) {
		this.sndType = sndType;
	}

	@Override
	public String toString() {
		return "FLVAudioTagBody [format=" + Utils.getAudioFormat(format) + ", samplerate="
 + Utils.getAudioSamplerate(samplerate)
				+ ", snd=" + Utils.getAudioSnd(snd) + ", sndType="
				+ Utils.getAudioSndType(sndType) + "]";
	}

}
