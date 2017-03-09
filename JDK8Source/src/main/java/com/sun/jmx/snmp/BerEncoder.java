/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
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


package com.sun.jmx.snmp;


/**
 * The <CODE>BerEncoder</CODE> class is used for encoding data using BER.
 *
 * A <CODE>BerEncoder</CODE> needs to be set up with a byte buffer. The encoded
 * data are stored in this byte buffer.
 * <P>
 * NOTE : the buffer is filled from end to start. This means the caller
 * needs to encode its data in the reverse order.
 *
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 *
 * <p>
 *  <CODE> BerEncoder </CODE>类用于使用BER对数据进行编码。
 * 
 *  需要使用字节缓冲区来设置<CODE> BerEncoder </CODE>。编码数据存储在该字节缓冲器中。
 * <P>
 *  注意：缓冲区从头到尾填充。这意味着调用者需要以相反的顺序对其数据进行编码。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */

public class BerEncoder {

  /**
  * Constructs a new encoder and attaches it to the specified byte string.
  *
  * <p>
  *  构造一个新的编码器并将其附加到指定的字节字符串。
  * 
  * 
  * @param b The byte string containing the encoded data.
  */

  public BerEncoder(byte b[]) {
    bytes = b ;
    start = b.length ;
    stackTop = 0 ;
  }


  /**
  * Trim the encoding data and returns the length of the encoding.
  *
  * The encoder does backward encoding : so the bytes buffer is
  * filled from end to start. The encoded data must be shift before
  * the buffer can be used. This is the purpose of the <CODE>trim</CODE> method.
  *
  * After a call to the <CODE>trim</CODE> method, the encoder is reinitialized and <CODE>putXXX</CODE>
  * overwrite any existing encoded data.
  *
  * <p>
  *  修剪编码数据并返回编码的长度。
  * 
  *  编码器执行反向编码：所以字节缓冲器从头到尾填充。编码数据必须在可以使用缓冲器之前移位。这是<CODE> trim </CODE>方法的目的。
  * 
  *  调用<CODE> trim </CODE>方法后,编码器重新初始化,<CODE> putXXX </CODE>将覆盖任何现有的编码数据。
  * 
  * 
  * @return The length of the encoded data.
  */

  public int trim() {
    final int result = bytes.length - start ;

    // for (int i = start ; i < bytes.length ; i++) {
    //  bytes[i-start] = bytes[i] ;
    // }
    if (result > 0)
        java.lang.System.arraycopy(bytes,start,bytes,0,result);

    start = bytes.length ;
    stackTop = 0 ;

    return result ;
  }

  /**
  * Put an integer.
  *
  * <p>
  *  放一个整数。
  * 
  * 
  * @param v The integer to encode.
  */

  public void putInteger(int v) {
    putInteger(v, IntegerTag) ;
  }


  /**
  * Put an integer with the specified tag.
  *
  * <p>
  *  放置带有指定标记的整数。
  * 
  * 
  * @param v The integer to encode.
  * @param tag The tag to encode.
  */

  public void putInteger(int v, int tag) {
    putIntegerValue(v) ;
    putTag(tag) ;
  }



  /**
  * Put an integer expressed as a long.
  *
  * <p>
  *  放一个表示为long的整数。
  * 
  * 
  * @param v The long to encode.
  */

  public void putInteger(long v) {
    putInteger(v, IntegerTag) ;
  }


  /**
  * Put an integer expressed as a long with the specified tag.
  *
  * <p>
  *  将带有指定标记的整数表示为long。
  * 
  * 
  * @param v The long to encode
  * @param tag The tag to encode.
  */

  public void putInteger(long v, int tag) {
    putIntegerValue(v) ;
    putTag(tag) ;
  }



  /**
  * Put an octet string.
  *
  * <p>
  *  放一个八位字节字符串。
  * 
  * 
  * @param s The bytes to encode
  */

  public void putOctetString(byte[] s) {
    putOctetString(s, OctetStringTag) ;
  }


  /**
  * Put an octet string with a specified tag.
  *
  * <p>
  *  放置具有指定标签的八位字节字符串。
  * 
  * 
  * @param s The bytes to encode
  * @param tag The tag to encode.
  */

  public void putOctetString(byte[] s, int tag) {
    putStringValue(s) ;
    putTag(tag) ;
  }


  /**
  * Put an object identifier.
  *
  * <p>
  *  放置对象标识符。
  * 
  * 
  * @param s The oid to encode.
  */

  public void putOid(long[] s) {
    putOid(s, OidTag) ;
  }


  /**
  * Put an object identifier with a specified tag.
  *
  * <p>
  *  放置具有指定标记的对象标识符。
  * 
  * 
  * @param s The integer to encode.
  * @param tag The tag to encode.
  */

  public void putOid(long[] s, int tag) {
    putOidValue(s) ;
    putTag(tag) ;
  }


  /**
  * Put a <CODE>NULL</CODE> value.
  * <p>
  *  放置一个<CODE> NULL </CODE>值。
  * 
  */

  public void putNull() {
    putNull(NullTag) ;
  }


  /**
  * Put a <CODE>NULL</CODE> value with a specified tag.
  *
  * <p>
  *  使用指定的标记放置一个<CODE> NULL </CODE>值。
  * 
  * 
  * @param tag The tag to encode.
  */

  public void putNull(int tag) {
    putLength(0) ;
    putTag(tag) ;
  }



  /**
  * Put an <CODE>ANY</CODE> value. In fact, this method does not encode anything.
  * It simply copies the specified bytes into the encoding.
  *
  * <p>
  * 放置一个<CODE> ANY </CODE>值。事实上,这种方法不会对任何东西进行编码。它只是将指定的字节复制到编码中。
  * 
  * 
  * @param s The encoding of the <CODE>ANY</CODE> value.
  */

  public void putAny(byte[] s) {
        putAny(s, s.length) ;
  }


  /**
  * Put an <CODE>ANY</CODE> value. Only the first <CODE>byteCount</CODE> are considered.
  *
  * <p>
  *  放置一个<CODE> ANY </CODE>值。只考虑第一个<CODE> byteCount </CODE>。
  * 
  * 
  * @param s The encoding of the <CODE>ANY</CODE> value.
  * @param byteCount The number of bytes of the encoding.
  */

  public void putAny(byte[] s, int byteCount) {
      java.lang.System.arraycopy(s,0,bytes,start-byteCount,byteCount);
      start -= byteCount;
      //    for (int i = byteCount - 1 ; i >= 0 ; i--) {
      //      bytes[--start] = s[i] ;
      //    }
  }


  /**
  * Open a sequence.
  * The encoder push the current position on its stack.
  * <p>
  *  打开序列。编码器推动其堆栈上的当前位置。
  * 
  */

  public void openSequence() {
    stackBuf[stackTop++] = start ;
  }


  /**
  * Close a sequence.
  * The decode pull the stack to know the end of the current sequence.
  * <p>
  *  关闭序列。解码拉动堆栈以知道当前序列的结束。
  * 
  */

  public void closeSequence() {
    closeSequence(SequenceTag) ;
  }


  /**
  * Close a sequence with the specified tag.
  * <p>
  *  关闭具有指定标记的序列。
  * 
  */

  public void closeSequence(int tag) {
    final int end = stackBuf[--stackTop] ;
    putLength(end - start) ;
    putTag(tag) ;
  }


  //
  // Some standard tags
  //
  public final static int BooleanTag      = 1 ;
  public final static int IntegerTag      = 2 ;
  public final static int OctetStringTag  = 4 ;
  public final static int NullTag          = 5 ;
  public final static int OidTag          = 6 ;
  public final static int SequenceTag      = 0x30 ;




  ////////////////////////// PROTECTED ///////////////////////////////



  /**
  * Put a tag and move the current position backward.
  *
  * <p>
  *  放置标签并向后移动当前位置。
  * 
  * 
  * @param tag The tag to encode.
  */

  protected final void putTag(int tag) {
    if (tag < 256) {
      bytes[--start] = (byte)tag ;
    }
    else {
      while (tag != 0) {
        bytes[--start] = (byte)(tag & 127) ;
        tag = tag << 7 ;
      }
    }
  }


  /**
  * Put a length and move the current position backward.
  *
  * <p>
  *  放一个长度并向后移动当前位置。
  * 
  * 
  * @param length The length to encode.
  */

  protected final void putLength(final int length) {
    if (length < 0) {
      throw new IllegalArgumentException() ;
    }
    else if (length < 128) {
      bytes[--start] = (byte)length ;
    }
    else if (length < 256) {
      bytes[--start] = (byte)length ;
      bytes[--start] = (byte)0x81 ;
    }
    else if (length < 65536) {
      bytes[--start] = (byte)(length) ;
      bytes[--start] = (byte)(length >> 8) ;
      bytes[--start] = (byte)0x82 ;
    }
    else if (length < 16777126) {
      bytes[--start] = (byte)(length) ;
      bytes[--start] = (byte)(length >> 8) ;
      bytes[--start] = (byte)(length >> 16) ;
      bytes[--start] = (byte)0x83 ;
    }
    else {
      bytes[--start] = (byte)(length) ;
      bytes[--start] = (byte)(length >> 8) ;
      bytes[--start] = (byte)(length >> 16) ;
      bytes[--start] = (byte)(length >> 24) ;
      bytes[--start] = (byte)0x84 ;
    }
  }


  /**
  * Put an integer value and move the current position backward.
  *
  * <p>
  *  设置一个整数值并将当前位置向后移动。
  * 
  * 
  * @param v The integer to encode.
  */

  protected final void putIntegerValue(int v) {
    final int end = start ;
    int mask = 0x7f800000 ;
    int byteNeeded = 4 ;
    if (v < 0) {
      while (((mask & v) == mask) && (byteNeeded > 1)) {
        mask = mask >> 8 ;
        byteNeeded-- ;
      }
    }
    else {
      while (((mask & v) == 0) && (byteNeeded > 1)) {
        mask = mask >> 8 ;
        byteNeeded-- ;
      }
    }
    for (int i = 0 ; i < byteNeeded ; i++) {
      bytes[--start] = (byte)v ;
      v =  v >> 8 ;
    }
    putLength(end - start) ;
  }


  /**
  * Put an integer value expressed as a long.
  *
  * <p>
  *  设置一个表示为long的整数值。
  * 
  * 
  * @param v The integer to encode.
  */

  protected final void putIntegerValue(long v) {
    final int end = start ;
    long mask = 0x7f80000000000000L ;
    int byteNeeded = 8 ;
    if (v < 0) {
      while (((mask & v) == mask) && (byteNeeded > 1)) {
        mask = mask >> 8 ;
        byteNeeded-- ;
      }
    }
    else {
      while (((mask & v) == 0) && (byteNeeded > 1)) {
        mask = mask >> 8 ;
        byteNeeded-- ;
      }
    }
    for (int i = 0 ; i < byteNeeded ; i++) {
      bytes[--start] = (byte)v ;
      v =  v >> 8 ;
    }
    putLength(end - start) ;
  }


  /**
  * Put a byte string and move the current position backward.
  *
  * <p>
  *  放置一个字节字符串并向后移动当前位置。
  * 
  * 
  * @param s The byte string to encode.
  */

  protected final void putStringValue(byte[] s) {
      final int datalen = s.length;
      java.lang.System.arraycopy(s,0,bytes,start-datalen,datalen);
      start -= datalen;
      // for (int i = s.length - 1 ; i >= 0 ; i--) {
      //   bytes[--start] = s[i] ;
      // }
      putLength(datalen) ;
  }



  /**
  * Put an oid and move the current position backward.
  *
  * <p>
  *  放一个oid并向后移动当前位置。
  * 
  * @param s The oid to encode.
  */

  protected final void putOidValue(final long[] s) {
      final int end = start ;
      final int slength = s.length;

      // bugId 4641746: 0, 1, and 2 are legal values.
      if ((slength < 2) || (s[0] > 2) || (s[1] >= 40)) {
          throw new IllegalArgumentException() ;
      }
      for (int i = slength - 1 ; i >= 2 ; i--) {
          long c = s[i] ;
          if (c < 0) {
              throw new IllegalArgumentException() ;
          }
          else if (c < 128) {
              bytes[--start] = (byte)c ;
          }
          else {
              bytes[--start] = (byte)(c & 127) ;
              c = c >> 7 ;
              while (c != 0) {
                  bytes[--start] = (byte)(c | 128) ;
                  c = c >> 7 ;
              }
          }
      }
      bytes[--start] = (byte)(s[0] * 40 + s[1]) ;
      putLength(end - start) ;
  }


  //
  // This is the byte array containing the encoding.
  //
  protected final byte bytes[];

  //
  // This is the index of the first byte of the encoding.
  // It is initialized to <CODE>bytes.length</CODE> and decrease each time
  // an value is put in the encoder.
  //
  protected int start = -1 ;

  //
  // This is the stack where end of sequences are kept.
  // A value is computed and pushed in it each time the <CODE>openSequence</CODE> method
  // is invoked.
  // A value is pulled and checked each time the <CODE>closeSequence</CODE> method is called.
  //
  protected final int stackBuf[] = new int[200] ;
  protected int stackTop = 0 ;

}
