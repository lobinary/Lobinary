/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Copyright (c) 1995-96 by Cisco Systems, Inc.

package com.sun.jmx.snmp;

/**
 * Contains SNMP data type constants.
 * All members are static and can be used by any application.
 *
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  包含SNMP数据类型常量。所有成员都是静态的,可以由任何应用程序使用。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public interface SnmpDataTypeEnums  {

  // ASN1 Type definitions.
  //-----------------------

  /**
   * ASN.1 tag for representing the boolean type.
   * <p>
   *  ASN.1标签用于表示布尔类型。
   * 
   */
  static public final int BooleanTag= 1;

  /**
   * ASN.1 tag for representing the integer type.
   * <p>
   *  ASN.1标签,用于表示整数类型。
   * 
   */
  static public final int IntegerTag= 2;

   /**
   * ASN.1 tag for representing the bit string type.
   * <p>
   *  ASN.1标签用于表示位字符串类型。
   * 
   */
  static public final int BitStringTag= 2;

   /**
   * ASN.1 tag for representing the octet string type.
   * <p>
   *  ASN.1标签,用于表示八位字节字符串类型。
   * 
   */
  static public final int OctetStringTag= 4;

   /**
   * ASN.1 tag for representing the null type.
   * <p>
   *  用于表示空类型的ASN.1标记。
   * 
   */
  static public final int NullTag= 5;

   /**
   * ASN.1 tag for representing the Object Identifier type.
   * <p>
   *  ASN.1标签用于表示对象标识符类型。
   * 
   */
  static public final int ObjectIdentiferTag= 6;


 /**
  * Represents a unknown syntax type. No meaning in an ASN.1 context.
  * <p>
  *  表示未知的语法类型。在ASN.1上下文中没有意义。
  * 
  */
  final public static int  UnknownSyntaxTag     =  0xFF ;

 /**
  * ASN.1 tag for a <CODE>SEQUENCE</CODE> or <CODE>SEQUENCE OF</CODE>.
  * <p>
  *  用于<CODE> SEQUENCE </CODE>或<CODE> SEQUENCE OF </CODE>的ASN.1标记。
  * 
  */
  final public static int  SequenceTag     =  0x30 ;

 /**
  * Represents an SNMP table. No meaning in an ASN.1 context.
  * <p>
  *  表示SNMP表。在ASN.1上下文中没有意义。
  * 
  */
  final public static int  TableTag     =  0xFE ;

  // SNMP definitions.
  //------------------

  /**
   * ASN.1 Tag for application context.
   * <p>
   *  ASN.1应用程序上下文的标签。
   * 
   */
  static public final int ApplFlag = 64 ;

 /**
  * ASN.1 tag for implicit context.
  * <p>
  *  隐式上下文的ASN.1标记。
  * 
  */
  static public final int CtxtFlag = 128 ;

  /**
   * ASN.1 tag for representing an IP address as defined in RFC 1155.
   * <p>
   *  ASN.1标签,用于表示RFC 1155中定义的IP地址。
   * 
   */
  static public final int IpAddressTag  = ApplFlag | 0 ;

  /**
   * ASN.1 tag for representing a <CODE>Counter32</CODE> as defined in RFC 1155.
   * <p>
   *  用于表示如RFC 1155中定义的<CODE> Counter32 </CODE>的ASN.1标签。
   * 
   */
  static public final int CounterTag    = ApplFlag | 1 ;

  /**
   * ASN.1 tag for representing a <CODE>Gauge32</CODE> as defined in RFC 1155.
   * <p>
   *  ASN.1标签,用于表示如RFC 1155中定义的<CODE> Gauge32 </CODE>。
   * 
   */
  static public final int GaugeTag      = ApplFlag | 2 ;

  /**
   * ASN.1 tag for representing a <CODE>Timeticks</CODE> as defined in RFC 1155.
   * <p>
   *  ASN.1标签,用于表示如RFC 1155中定义的<CODE> Timeticks </CODE>。
   * 
   */
  static public final int TimeticksTag  = ApplFlag | 3 ;

  /**
   * ASN.1 tag for representing an <CODE>Opaque</CODE> type as defined in RFC 1155.
   * <p>
   *  ASN.1标签,用于表示如RFC 1155中定义的<CODE>不透明</CODE>类型。
   * 
   */
  static public final int OpaqueTag     = ApplFlag | 4 ;

  /**
   * ASN.1 tag for representing a <CODE>Counter64</CODE> as defined in RFC 1155.
   * <p>
   *  ASN.1标签,用于表示如RFC 1155中定义的<CODE> Counter64 </CODE>。
   * 
   */
  static public final int Counter64Tag  = ApplFlag | 6 ;

  /**
   * ASN.1 tag for representing an <CODE>Nsap</CODE> as defined in RFC 1902.
   * <p>
   *  ASN.1标签,用于表示如RFC 1902中定义的<CODE> Nsap </CODE>。
   * 
   */
  static final public int NsapTag       = ApplFlag | 5 ;

  /**
   * ASN.1 tag for representing an <CODE>Unsigned32</CODE> integer as defined in RFC 1902.
   * <p>
   *  ASN.1标签,用于表示RFC 1902中定义的<CODE> Unsigned32 </CODE>整数。
   * 
   */
  static final public int UintegerTag      = ApplFlag | 7 ;

  /**
   * ASN.1 tag for representing a <CODE>NoSuchObject</CODE> as defined in RFC 1902.
   * <p>
   * ASN.1标签,用于表示如RFC 1902中定义的<CODE> NoSuchObject </CODE>。
   * 
   */
  static final public int errNoSuchObjectTag    = CtxtFlag | 0 ;

   /**
   * ASN.1 tag for representing a <CODE>NoSuchInstance</CODE> as defined in RFC 1902.
   * <p>
   *  ASN.1标签,用于表示如RFC 1902中定义的<CODE> NoSuchInstance </CODE>。
   * 
   */
  static final public int errNoSuchInstanceTag  = CtxtFlag | 1 ;

  /**
   * ASN.1 tag for representing an <CODE>EndOfMibView</CODE> as defined in RFC 1902.
   * <p>
   *  ASN.1标签,用于表示如RFC 1902中定义的<CODE> EndOfMibView </CODE>。
   */
  static final public int errEndOfMibViewTag    = CtxtFlag | 2 ;


}
