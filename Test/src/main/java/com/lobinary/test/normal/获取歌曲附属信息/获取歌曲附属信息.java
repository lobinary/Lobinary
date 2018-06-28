package com.lobinary.test.normal.获取歌曲附属信息;

import java.io.IOException;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.TextEncodedStringSizeTerminated;

public class 获取歌曲附属信息 {

	public static void main(String[] args) throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		MP3File f = new MP3File("C:/temp/LY.mp3");
		System.out.println(f);
		System.out.println(f.getTag().getFieldCount());
		System.out.println("FieldKey.YEAR:"+f.getTag().getFirst(FieldKey.YEAR));
		String first = f.getTag().getFirst(FieldKey.TITLE);
		System.out.println("FieldKey.TITLE:"+new String(first.getBytes(), "utf-8"));
		
//		Iterator<TagField> fields = f.getTag().getFields();
//		while (fields.hasNext()) {
//			TagField t = (TagField) fields.next();
//			System.out.println(t.getRawContent().toString());
//		}
	}

}
