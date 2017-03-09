/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.sound.sampled;

import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.IOException;


/**
 * An audio input stream is an input stream with a specified audio format and
 * length.  The length is expressed in sample frames, not bytes.
 * Several methods are provided for reading a certain number of bytes from
 * the stream, or an unspecified number of bytes.
 * The audio input stream keeps track  of the last byte that was read.
 * You can skip over an arbitrary number of bytes to get to a later position
 * for reading. An audio input stream may support marks.  When you set a mark,
 * the current position is remembered so that you can return to it later.
 * <p>
 * The <code>AudioSystem</code> class includes many methods that manipulate
 * <code>AudioInputStream</code> objects.
 * For example, the methods let you:
 * <ul>
 * <li> obtain an
 * audio input stream from an external audio file, stream, or URL
 * <li> write an external file from an audio input stream
 * <li> convert an audio input stream to a different audio format
 * </ul>
 *
 * <p>
 *  音频输入流是具有指定的音频格式和长度的输入流。长度用样本帧表示,而不是字节。提供了几种用于从流中读取特定数量的字节或未指定数量的字节的方法。音频输入流跟踪读取的最后一个字节。
 * 您可以跳过任意数量的字节,以到达以后的位置进行读取。音频输入流可以支持标记。设置标记时,将记住当前位置,以便以后可以返回。
 * <p>
 *  <code> AudioSystem </code>类包含许多操作<code> AudioInputStream </code>对象的方法。例如,方法让您：
 * <ul>
 *  <li>从外部音频文件,流或URL获取音频输入流<li>从音频输入流中写入外部文件<li>将音频输入流转换为不同的音频格式
 * </ul>
 * 
 * 
 * @author David Rivas
 * @author Kara Kytle
 * @author Florian Bomers
 *
 * @see AudioSystem
 * @see Clip#open(AudioInputStream) Clip.open(AudioInputStream)
 * @since 1.3
 */
public class AudioInputStream extends InputStream {

    /**
     * The <code>InputStream</code> from which this <code>AudioInputStream</code>
     * object was constructed.
     * <p>
     *  构造此<code> AudioInputStream </code>对象的<code> InputStream </code>。
     * 
     */
    private InputStream stream;

    /**
     * The format of the audio data contained in the stream.
     * <p>
     *  流中包含的音频数据的格式。
     * 
     */
    protected AudioFormat format;

    /**
     * This stream's length, in sample frames.
     * <p>
     *  此流的长度,在采样帧。
     * 
     */
    protected long frameLength;

    /**
     * The size of each frame, in bytes.
     * <p>
     *  每帧的大小,以字节为单位。
     * 
     */
    protected int frameSize;

    /**
     * The current position in this stream, in sample frames (zero-based).
     * <p>
     *  此流中的当前位置,采样帧(从零开始)。
     * 
     */
    protected long framePos;

    /**
     * The position where a mark was set.
     * <p>
     *  设置标记的位置。
     * 
     */
    private long markpos;

    /**
     * When the underlying stream could only return
     * a non-integral number of frames, store
     * the remainder in a temporary buffer
     * <p>
     *  当底层流只能返回非整数帧时,将剩余部分存储在临时缓冲区中
     * 
     */
    private byte[] pushBackBuffer = null;

    /**
     * number of valid bytes in the pushBackBuffer
     * <p>
     * pushBackBuffer中的有效字节数
     * 
     */
    private int pushBackLen = 0;

    /**
     * MarkBuffer at mark position
     * <p>
     *  MarkBuffer在标记位置
     * 
     */
    private byte[] markPushBackBuffer = null;

    /**
     * number of valid bytes in the markPushBackBuffer
     * <p>
     *  markPushBackBuffer中的有效字节数
     * 
     */
    private int markPushBackLen = 0;


    /**
     * Constructs an audio input stream that has the requested format and length in sample frames,
     * using audio data from the specified input stream.
     * <p>
     *  使用来自指定输入流的音频数据构造在采样帧中具有请求的格式和长度的音频输入流。
     * 
     * 
     * @param stream the stream on which this <code>AudioInputStream</code>
     * object is based
     * @param format the format of this stream's audio data
     * @param length the length in sample frames of the data in this stream
     */
    public AudioInputStream(InputStream stream, AudioFormat format, long length) {

        super();

        this.format = format;
        this.frameLength = length;
        this.frameSize = format.getFrameSize();

        // any frameSize that is not well-defined will
        // cause that this stream will be read in bytes
        if( this.frameSize == AudioSystem.NOT_SPECIFIED || frameSize <= 0) {
            this.frameSize = 1;
        }

        this.stream = stream;
        framePos = 0;
        markpos = 0;
    }


    /**
     * Constructs an audio input stream that reads its data from the target
     * data line indicated.  The format of the stream is the same as that of
     * the target data line, and the length is AudioSystem#NOT_SPECIFIED.
     * <p>
     *  构造音频输入流,从指示的目标数据行读取其数据。流的格式与目标数据行的格式相同,长度为AudioSystem#NOT_SPECIFIED。
     * 
     * 
     * @param line the target data line from which this stream obtains its data.
     * @see AudioSystem#NOT_SPECIFIED
     */
    public AudioInputStream(TargetDataLine line) {

        TargetDataLineInputStream tstream = new TargetDataLineInputStream(line);
        format = line.getFormat();
        frameLength = AudioSystem.NOT_SPECIFIED;
        frameSize = format.getFrameSize();

        if( frameSize == AudioSystem.NOT_SPECIFIED || frameSize <= 0) {
            frameSize = 1;
        }
        this.stream = tstream;
        framePos = 0;
        markpos = 0;
    }


    /**
     * Obtains the audio format of the sound data in this audio input stream.
     * <p>
     *  获取此音频输入流中的声音数据的音频格式。
     * 
     * 
     * @return an audio format object describing this stream's format
     */
    public AudioFormat getFormat() {
        return format;
    }


    /**
     * Obtains the length of the stream, expressed in sample frames rather than bytes.
     * <p>
     *  获取流的长度,以采样帧而不是字节表示。
     * 
     * 
     * @return the length in sample frames
     */
    public long getFrameLength() {
        return frameLength;
    }


    /**
     * Reads the next byte of data from the audio input stream.  The audio input
     * stream's frame size must be one byte, or an <code>IOException</code>
     * will be thrown.
     *
     * <p>
     *  从音频输入流读取数据的下一个字节。音频输入流的帧大小必须为一个字节,否则将抛出<code> IOException </code>。
     * 
     * 
     * @return the next byte of data, or -1 if the end of the stream is reached
     * @throws IOException if an input or output error occurs
     * @see #read(byte[], int, int)
     * @see #read(byte[])
     * @see #available
     * <p>
     */
    public int read() throws IOException {
        if( frameSize != 1 ) {
            throw new IOException("cannot read a single byte if frame size > 1");
        }

        byte[] data = new byte[1];
        int temp = read(data);
        if (temp <= 0) {
            // we have a weird situation if read(byte[]) returns 0!
            return -1;
        }
        return data[0] & 0xFF;
    }


    /**
     * Reads some number of bytes from the audio input stream and stores them into
     * the buffer array <code>b</code>. The number of bytes actually read is
     * returned as an integer. This method blocks until input data is
     * available, the end of the stream is detected, or an exception is thrown.
     * <p>This method will always read an integral number of frames.
     * If the length of the array is not an integral number
     * of frames, a maximum of <code>b.length - (b.length % frameSize)
     * </code> bytes will be read.
     *
     * <p>
     *  从音频输入流读取一些字节数,并将它们存储到缓冲器数组<code> b </code>中。实际读取的字节数作为整数返回。此方法阻塞,直到输入数据可用,检测到流的结尾,或抛出异常。
     *  <p>此方法将始终读取整数帧。如果数组的长度不是整数帧数,将读取<code> b.length  - (b.length％frameSize)</code>字节的最大值。
     * 
     * 
     * @param b the buffer into which the data is read
     * @return the total number of bytes read into the buffer, or -1 if there
     * is no more data because the end of the stream has been reached
     * @throws IOException if an input or output error occurs
     * @see #read(byte[], int, int)
     * @see #read()
     * @see #available
     */
    public int read(byte[] b) throws IOException {
        return read(b,0,b.length);
    }


    /**
     * Reads up to a specified maximum number of bytes of data from the audio
     * stream, putting them into the given byte array.
     * <p>This method will always read an integral number of frames.
     * If <code>len</code> does not specify an integral number
     * of frames, a maximum of <code>len - (len % frameSize)
     * </code> bytes will be read.
     *
     * <p>
     * 从音频流中读取数据的最大字节数,将它们放入给定的字节数组。 <p>此方法将始终读取整数帧。
     * 如果<code> len </code>未指定整数帧数,则将读取最大<code> len  - (len％frameSize)</code>字节。
     * 
     * 
     * @param b the buffer into which the data is read
     * @param off the offset, from the beginning of array <code>b</code>, at which
     * the data will be written
     * @param len the maximum number of bytes to read
     * @return the total number of bytes read into the buffer, or -1 if there
     * is no more data because the end of the stream has been reached
     * @throws IOException if an input or output error occurs
     * @see #read(byte[])
     * @see #read()
     * @see #skip
     * @see #available
     */
    public int read(byte[] b, int off, int len) throws IOException {

        // make sure we don't read fractions of a frame.
        if( (len%frameSize) != 0 ) {
            len -= (len%frameSize);
            if (len == 0) {
                return 0;
            }
        }

        if( frameLength != AudioSystem.NOT_SPECIFIED ) {
            if( framePos >= frameLength ) {
                return -1;
            } else {

                // don't try to read beyond our own set length in frames
                if( (len/frameSize) > (frameLength-framePos) ) {
                    len = (int) (frameLength-framePos) * frameSize;
                }
            }
        }

        int bytesRead = 0;
        int thisOff = off;

        // if we've bytes left from last call to read(),
        // use them first
        if (pushBackLen > 0 && len >= pushBackLen) {
            System.arraycopy(pushBackBuffer, 0,
                             b, off, pushBackLen);
            thisOff += pushBackLen;
            len -= pushBackLen;
            bytesRead += pushBackLen;
            pushBackLen = 0;
        }

        int thisBytesRead = stream.read(b, thisOff, len);
        if (thisBytesRead == -1) {
            return -1;
        }
        if (thisBytesRead > 0) {
            bytesRead += thisBytesRead;
        }
        if (bytesRead > 0) {
            pushBackLen = bytesRead % frameSize;
            if (pushBackLen > 0) {
                // copy everything we got from the beginning of the frame
                // to our pushback buffer
                if (pushBackBuffer == null) {
                    pushBackBuffer = new byte[frameSize];
                }
                System.arraycopy(b, off + bytesRead - pushBackLen,
                                 pushBackBuffer, 0, pushBackLen);
                bytesRead -= pushBackLen;
            }
            // make sure to update our framePos
            framePos += bytesRead/frameSize;
        }
        return bytesRead;
    }


    /**
     * Skips over and discards a specified number of bytes from this
     * audio input stream.
     * <p>
     *  跳过并丢弃该音频输入流中指定数量的字节。
     * 
     * 
     * @param n the requested number of bytes to be skipped
     * @return the actual number of bytes skipped
     * @throws IOException if an input or output error occurs
     * @see #read
     * @see #available
     */
    public long skip(long n) throws IOException {

        // make sure not to skip fractional frames
        if( (n%frameSize) != 0 ) {
            n -= (n%frameSize);
        }

        if( frameLength != AudioSystem.NOT_SPECIFIED ) {
            // don't skip more than our set length in frames.
            if( (n/frameSize) > (frameLength-framePos) ) {
                n = (frameLength-framePos) * frameSize;
            }
        }
        long temp = stream.skip(n);

        // if no error, update our position.
        if( temp%frameSize != 0 ) {

            // Throw an IOException if we've skipped a fractional number of frames
            throw new IOException("Could not skip an integer number of frames.");
        }
        if( temp >= 0 ) {
            framePos += temp/frameSize;
        }
        return temp;

    }


    /**
     * Returns the maximum number of bytes that can be read (or skipped over) from this
     * audio input stream without blocking.  This limit applies only to the next invocation of
     * a <code>read</code> or <code>skip</code> method for this audio input stream; the limit
     * can vary each time these methods are invoked.
     * Depending on the underlying stream,an IOException may be thrown if this
     * stream is closed.
     * <p>
     *  返回可以从此音频输入流读取(或跳过)而不阻塞的最大字节数。
     * 此限制仅适用于对此音频输入流的<code> read </code>或<code> skip </code>方法的下一次调用;每次调用这些方法时,限制都会有所不同。
     * 根据基础流,如果此流关闭,可能会抛出IOException。
     * 
     * 
     * @return the number of bytes that can be read from this audio input stream without blocking
     * @throws IOException if an input or output error occurs
     * @see #read(byte[], int, int)
     * @see #read(byte[])
     * @see #read()
     * @see #skip
     */
    public int available() throws IOException {

        int temp = stream.available();

        // don't return greater than our set length in frames
        if( (frameLength != AudioSystem.NOT_SPECIFIED) && ( (temp/frameSize) > (frameLength-framePos)) ) {
            return (int) (frameLength-framePos) * frameSize;
        } else {
            return temp;
        }
    }


    /**
     * Closes this audio input stream and releases any system resources associated
     * with the stream.
     * <p>
     *  关闭此音频输入流,并释放与流相关联的任何系统资源。
     * 
     * 
     * @throws IOException if an input or output error occurs
     */
    public void close() throws IOException {
        stream.close();
    }


    /**
     * Marks the current position in this audio input stream.
     * <p>
     *  标记此音频输入流中的当前位置。
     * 
     * 
     * @param readlimit the maximum number of bytes that can be read before
     * the mark position becomes invalid.
     * @see #reset
     * @see #markSupported
     */

    public void mark(int readlimit) {

        stream.mark(readlimit);
        if (markSupported()) {
            markpos = framePos;
            // remember the pushback buffer
            markPushBackLen = pushBackLen;
            if (markPushBackLen > 0) {
                if (markPushBackBuffer == null) {
                    markPushBackBuffer = new byte[frameSize];
                }
                System.arraycopy(pushBackBuffer, 0, markPushBackBuffer, 0, markPushBackLen);
            }
        }
    }


    /**
     * Repositions this audio input stream to the position it had at the time its
     * <code>mark</code> method was last invoked.
     * <p>
     *  将此音频输入流重新定位到其上次调用<code>标记</code>方法时的位置。
     * 
     * 
     * @throws IOException if an input or output error occurs.
     * @see #mark
     * @see #markSupported
     */
    public void reset() throws IOException {

        stream.reset();
        framePos = markpos;
        // re-create the pushback buffer
        pushBackLen = markPushBackLen;
        if (pushBackLen > 0) {
            if (pushBackBuffer == null) {
                pushBackBuffer = new byte[frameSize - 1];
            }
            System.arraycopy(markPushBackBuffer, 0, pushBackBuffer, 0, pushBackLen);
        }
    }


    /**
     * Tests whether this audio input stream supports the <code>mark</code> and
     * <code>reset</code> methods.
     * <p>
     *  测试此音频输入流是否支持<code>标记</code>和<code>重置</code>方法。
     * 
     * 
     * @return <code>true</code> if this stream supports the <code>mark</code>
     * and <code>reset</code> methods; <code>false</code> otherwise
     * @see #mark
     * @see #reset
     */
    public boolean markSupported() {

        return stream.markSupported();
    }


    /**
     * Private inner class that makes a TargetDataLine look like an InputStream.
     * <p>
     *  使TargetDataLine看起来像InputStream的私有内部类。
     * 
     */
    private class TargetDataLineInputStream extends InputStream {

        /**
         * The TargetDataLine on which this TargetDataLineInputStream is based.
         * <p>
         *  TargetDataLineInputStream所基于的TargetDataLine。
         */
        TargetDataLine line;


        TargetDataLineInputStream(TargetDataLine line) {
            super();
            this.line = line;
        }


        public int available() throws IOException {
            return line.available();
        }

        //$$fb 2001-07-16: added this method to correctly close the underlying TargetDataLine.
        // fixes bug 4479984
        public void close() throws IOException {
            // the line needs to be flushed and stopped to avoid a dead lock...
            // Probably related to bugs 4417527, 4334868, 4383457
            if (line.isActive()) {
                line.flush();
                line.stop();
            }
            line.close();
        }

        public int read() throws IOException {

            byte[] b = new byte[1];

            int value = read(b, 0, 1);

            if (value == -1) {
                return -1;
            }

            value = (int)b[0];

            if (line.getFormat().getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
                value += 128;
            }

            return value;
        }


        public int read(byte[] b, int off, int len) throws IOException {
            try {
                return line.read(b, off, len);
            } catch (IllegalArgumentException e) {
                throw new IOException(e.getMessage());
            }
        }
    }
}
