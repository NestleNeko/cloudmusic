package com.cloudmusic.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;
import com.cloudmusic.entity.Music;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;



import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.AudioInfo;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;

@Component
public class MusicAnalysis implements InitializingBean {

	private Logger log = Logger.getLogger(getClass());

	public Music getMusicById(String id) {
		FileInputStream stream = null;
		InputStreamReader reader = null;
		BufferedReader in = null;
		try {
			File musicTable = getMusicTable();
			stream = new FileInputStream(musicTable);
			reader = new InputStreamReader(stream);
			in = new BufferedReader(reader);
			String line = null;
			Music music = null;
			while ((line = in.readLine()) != null) {
				music = JSON.parseObject(line, Music.class);
				if (music.getMid().equals(id))
					break;
			}
			return music;
		} catch (FileNotFoundException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		} finally {
			try {
				if (in != null)
					in.close();
				if (reader != null)
					reader.close();
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				log.error("", e);
			}
		}
		return null;
	}

	/**
	 * 添加音乐
	 * 
	 * @param file
	 * @throws EncoderException
	 * @throws InputFormatException
	 * @throws IOException 
	 */
	public void addMusicToLib(File file) throws InputFormatException, EncoderException, IOException {
		Music music = getMusicInfo(file);
		addMusicInfoToDB(music);
	}

	public void batchAddMusic(String path) {
		
	}
	/**
	 * 从音乐文件中获取一些基础信息
	 * @param musicFile
	 * @return
	 * @throws IOException
	 * @throws InputFormatException
	 * @throws EncoderException
	 */
	public Music getMusicInfo(File musicFile) throws IOException, InputFormatException, EncoderException{
		Music music = new Music();
//		music.setMid(getSongNextId());
		String filename = musicFile.getName().substring(0, musicFile.getName().indexOf('.'));
		music.setFilename(filename);
		System.out.println("文件名："+filename);
		Encoder encoder = new Encoder();
		MultimediaInfo m = encoder.getInfo(musicFile);
		long ls = m.getDuration();
		long duration = ls / 1000;
		System.out.println("时长："+duration / 60 + ":" + duration % 60);
		music.setDuration((int) duration);
		AudioInfo audio = m.getAudio();
		int rate = audio.getBitRate();
		music.setBitRate(rate);
		System.out.println("比特率："+rate);
		RandomAccessFile in = null;
		try {
			in = new RandomAccessFile(musicFile, "r"); //
			byte[] b = new byte[128]; // 定义要获取多少字节数
			in.seek(musicFile.length() - 128); // 因为信息在mp3最后那我们就跳到最后来读，f.length()是文件总字节数-128，那么就跳到从倒数128个字节数开始。
			in.read(b);// 直接读取最后的128字节
			if (new String(b, 0, 3, "GBK").startsWith("TAG")) { // 读完后，先判断前3个字符是不是TAG
				String title = new String(b, 3, 30, "GBK").trim();
				music.setName(title);
				System.out.println("标题:" + title); // 前面有“TAG”这个东西，所以标题从第3位开始，往后30位结束截取为GBK编码的字符串。.trim()是把字符串前端与末端的空格去掉。
				String artist = new String(b, 33, 30, "GBK").trim();
				music.setAuthor(artist);
				System.out.println("艺术家:" + artist);// 前的标题占了30位还加上TAG这三个占3位=33,下面以此类推
				String album = new String(b, 63, 30, "GBK").trim();
				music.setAlbum(album);
				System.out.println("专辑名:" + album);
				String year = new String(b, 93, 4, "GBK").trim();
				music.setYear(year);
				System.out.println("发行年份:" + year);
			}else {System.out.println("读取错误");}
		} catch (IOException ex) {
			log.error("", ex);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
		return music;
	}
	
	/**
	 * 将音乐对象存储到数据文件
	 * @param music
	 */
	private void addMusicInfoToDB(Music music) {
		Object json = JSON.toJSON(music);
		String s = json.toString();
		writeToMusicInfoFile(s, false);
	}

	/**
	 * 写入文件
	 * @param content 写入内容
	 * @param cover 是否覆盖
	 */
	private void writeToMusicInfoFile(String content, boolean cover) {
		FileWriter writer = null;
		try {
			File file = getMusicTable();
			if (!file.exists())
				file.createNewFile();
			writer = new FileWriter(file, cover);
			writer.write(content + "\r\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 获取一个自增的序列号
	 * 
	 * @return
	 * @throws IOException
	 */
	private synchronized String getSongNextId() throws IOException {
		File file = new File(getStoreRoot() + File.separator + "song_seq.dat");
		if (!file.exists())
			file.createNewFile();
		FileWriter writer = null;
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			char[] buf = new char[50];
			reader.read(buf);
			String s = new String(buf);
			s = s.trim();
			Integer index = 1;
			if (s.length() > 0) {
				index = Integer.valueOf(s);
			}
			System.out.println(index);
			writer = new FileWriter(file);
			writer.write((index + 1) + "");
			return index + "";
		} finally {
			if (writer != null)
				writer.close();
			if (reader != null)
				reader.close();
		}
	}

//	@Override
	public void afterPropertiesSet() throws Exception {
		initStoreRoot();
	}

	/**
	 * 初始化数据存储根路径
	 */
	private void initStoreRoot() {
		String storeRoot = getStoreRoot();
		File file = new File(storeRoot);
		if (!file.exists()) {
			file.mkdirs();
			log.info("初始化本地数据存储根目录");
		}
	}

	/**
	 * 获取数据存储根路径
	 * 
	 * @return
	 */
	private String getStoreRoot() {
		/*
		 * WebApplicationContext context =
		 * ContextLoader.getCurrentWebApplicationContext(); ServletContext
		 * servletContext = context.getServletContext(); String path =
		 * servletContext.getRealPath("/"); System.out.println(path);
		 */
		String property = System.getProperty("catalina.home");
		property += File.separator + "userData";
		System.out.println(property);
		return property;
	}

	private File getMusicTable() throws IOException {
		String path = getStoreRoot() + File.separator + "t_music.json";
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}
	
}
