/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package javax.swing.text.rtf;

import java.io.*;
import java.lang.*;

/**
 * A generic superclass for streams which read and parse text
 * consisting of runs of characters interspersed with occasional
 * ``specials'' (formatting characters).
 *
 * <p> Most of the functionality
 * of this class would be redundant except that the
 * <code>ByteToChar</code> converters
 * are suddenly private API. Presumably this class will disappear
 * when the API is made public again. (sigh) That will also let us handle
 * multibyte character sets...
 *
 * <P> A subclass should override at least <code>write(char)</code>
 * and <code>writeSpecial(int)</code>. For efficiency's sake it's a
 * good idea to override <code>write(String)</code> as well. The subclass'
 * initializer may also install appropriate translation and specials tables.
 *
 * <p>
 *  流的通用超类,它读取和解析包含偶发"特殊"(格式化字符)的字符串的文本。
 * 
 *  <p>这个类的大部分功能将是冗余的,除了<code> ByteToChar </code>转换器突然私有API。据推测,当API再次公开时,此类将消失。 (叹息)这也将让我们处理多字节字符集...
 * 
 *  <P>子类应至少覆盖<code> write(char)</code>和<code> writeSpecial(int)</code>。
 * 为了效率的缘故,这是一个好主意,重写<code> write(String)</code>以及。子类的初始化器也可以安装适当的翻译和特殊表。
 * 
 * 
 * @see OutputStream
 */
abstract class AbstractFilter extends OutputStream
{
    /** A table mapping bytes to characters */
    protected char translationTable[];
    /** A table indicating which byte values should be interpreted as
    /* <p>
    /* 
     *  characters and which should be treated as formatting codes */
    protected boolean specialsTable[];

    /** A translation table which does ISO Latin-1 (trivial) */
    static final char latin1TranslationTable[];
    /** A specials table which indicates that no characters are special */
    static final boolean noSpecialsTable[];
    /** A specials table which indicates that all characters are special */
    static final boolean allSpecialsTable[];

    static {
      int i;

      noSpecialsTable = new boolean[256];
      for (i = 0; i < 256; i++)
        noSpecialsTable[i] = false;

      allSpecialsTable = new boolean[256];
      for (i = 0; i < 256; i++)
        allSpecialsTable[i] = true;

      latin1TranslationTable = new char[256];
      for (i = 0; i < 256; i++)
        latin1TranslationTable[i] = (char)i;
    }

    /**
     * A convenience method that reads text from a FileInputStream
     * and writes it to the receiver.
     * The format in which the file
     * is read is determined by the concrete subclass of
     * AbstractFilter to which this method is sent.
     * <p>This method does not close the receiver after reaching EOF on
     * the input stream.
     * The user must call <code>close()</code> to ensure that all
     * data are processed.
     *
     * <p>
     *  一种方便的方法,从FileInputStream读取文本并将其写入接收器。读取文件的格式由发送此方法的AbstractFilter的具体子类确定。 <p>此方法在输入流上达到EOF后不关闭接收器。
     * 用户必须调用<code> close()</code>以确保处理所有数据。
     * 
     * 
     * @param in      An InputStream providing text.
     */
    public void readFromStream(InputStream in)
      throws IOException
    {
        byte buf[];
        int count;

        buf = new byte[16384];

        while(true) {
            count = in.read(buf);
            if (count < 0)
                break;

            this.write(buf, 0, count);
        }
    }

    public void readFromReader(Reader in)
      throws IOException
    {
        char buf[];
        int count;

        buf = new char[2048];

        while(true) {
            count = in.read(buf);
            if (count < 0)
                break;
            for (int i = 0; i < count; i++) {
              this.write(buf[i]);
            }
        }
    }

    public AbstractFilter()
    {
        translationTable = latin1TranslationTable;
        specialsTable = noSpecialsTable;
    }

    /**
     * Implements the abstract method of OutputStream, of which this class
     * is a subclass.
     * <p>
     *  实现OutputStream的抽象方法,其中这个类是一个子类。
     * 
     */
    public void write(int b)
      throws IOException
    {
      if (b < 0)
        b += 256;
      if (specialsTable[b])
        writeSpecial(b);
      else {
        char ch = translationTable[b];
        if (ch != (char)0)
          write(ch);
      }
    }

    /**
     * Implements the buffer-at-a-time write method for greater
     * efficiency.
     *
     * <p> <strong>PENDING:</strong> Does <code>write(byte[])</code>
     * call <code>write(byte[], int, int)</code> or is it the other way
     * around?
     * <p>
     *  实现缓冲区一次写入方法,以提高效率。
     * 
     * <p> <strong> PENDING：</strong> <code> write(byte [])</code>调用<code> write(byte [],int,int)</code>周围?。
     * 
     */
    public void write(byte[] buf, int off, int len)
      throws IOException
    {
      StringBuilder accumulator = null;
      while (len > 0) {
        short b = (short)buf[off];

        // stupid signed bytes
        if (b < 0)
            b += 256;

        if (specialsTable[b]) {
          if (accumulator != null) {
            write(accumulator.toString());
            accumulator = null;
          }
          writeSpecial(b);
        } else {
          char ch = translationTable[b];
          if (ch != (char)0) {
            if (accumulator == null)
              accumulator = new StringBuilder();
            accumulator.append(ch);
          }
        }

        len --;
        off ++;
      }

      if (accumulator != null)
        write(accumulator.toString());
    }

    /**
     * Hopefully, all subclasses will override this method to accept strings
     * of text, but if they don't, AbstractFilter's implementation
     * will spoon-feed them via <code>write(char)</code>.
     *
     * <p>
     *  希望所有的子类都会覆盖这个方法来接受文本字符串,但是如果没有,AbstractFilter的实现将通过<code> write(char)</code>来实现。
     * 
     * 
     * @param s The string of non-special characters written to the
     *          OutputStream.
     */
    public void write(String s)
      throws IOException
    {
      int index, length;

      length = s.length();
      for(index = 0; index < length; index ++) {
        write(s.charAt(index));
      }
    }

    /**
     * Subclasses must provide an implementation of this method which
     * accepts a single (non-special) character.
     *
     * <p>
     *  子类必须提供接受单个(非特殊)字符的此方法的实现。
     * 
     * 
     * @param ch The character written to the OutputStream.
     */
    protected abstract void write(char ch) throws IOException;

    /**
     * Subclasses must provide an implementation of this method which
     * accepts a single special byte. No translation is performed
     * on specials.
     *
     * <p>
     *  子类必须提供一个接受一个特殊字节的方法的实现。不对特价进行翻译。
     * 
     * @param b The byte written to the OutputStream.
     */
    protected abstract void writeSpecial(int b) throws IOException;
}
