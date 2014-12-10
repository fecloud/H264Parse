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

	@Override
	public void decoder(byte[] data) throws CodingException {
		if(null == data){
			throw new CodingException("decoder data is empty!");
		}
		final byte audio = data[0];
		setFormat((0xF0 & audio) >> 4);
		setSamplerate((0x0C & audio) >> 2);
		setSnd((0x2 & audio) >> 1);
		setSndType(0x1 & audio);
		
	}

	@Override
	public byte[] encoder() throws CodingException {
		// TODO Auto-generated method stub
		return null;
	}

}
