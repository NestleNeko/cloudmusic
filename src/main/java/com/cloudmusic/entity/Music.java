package com.cloudmusic.entity;

public class Music {
	private String mid;
	private String filename;//文件名
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	private String name;//标题
	private String author;//艺术家
	private String album;//专辑
	private int duration;//音乐时长
	private int bitRate;//比特率
	private String year;//发行年份
	private String localLoc;//本地地址
//	private String remoteLoc;//远程地址
	private long lastplaytime;//歌词
	private int playTimes;//播放次数
	private String coverLoc;//专辑封面
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getLocalLoc() {
		return localLoc;
	}
	public void setLocalLoc(String localLoc) {
		this.localLoc = localLoc;
	}
	
	
	public long getLastplaytime() {
		return lastplaytime;
	}
	public void setLastplaytime(long lastplaytime) {
		this.lastplaytime = lastplaytime;
	}
	public int getPlayTimes() {
		return playTimes;
	}
	public void setPlayTimes(int playTimes) {
		this.playTimes = playTimes;
	}
	public String getCoverLoc() {
		return coverLoc;
	}
	public void setCoverLoc(String coverLoc) {
		this.coverLoc = coverLoc;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getBitRate() {
		return bitRate;
	}
	public void setBitRate(int bitRate) {
		this.bitRate = bitRate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
}
