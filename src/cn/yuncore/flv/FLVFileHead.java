package cn.yuncore.flv;

/**
 * 文件头
 * @author Administrator
 *
 */
public class FLVFileHead {

	/**
	 * 是否有音频
	 */
	public boolean haveAudio;
	
	/**
	 * 是否有视频
	 */
	public boolean haveVideo;

	public boolean isHaveAudio() {
		return haveAudio;
	}

	public void setHaveAudio(boolean haveAudio) {
		this.haveAudio = haveAudio;
	}

	public boolean isHaveVideo() {
		return haveVideo;
	}

	public void setHaveVideo(boolean haveVideo) {
		this.haveVideo = haveVideo;
	}

	@Override
	public String toString() {
		return "FLVHead [haveAudio=" + haveAudio + ", haveVideo=" + haveVideo
				+ "]";
	}
}