/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.font;

/**
 * The <code>OpenType</code> interface represents OpenType and
 * TrueType fonts.  This interface makes it possible to obtain
 * <i>sfnt</i> tables from the font.  A particular
 * <code>Font</code> object can implement this interface.
 * <p>
 * For more information on TrueType and OpenType fonts, see the
 * OpenType specification.
 * ( <a href="http://www.microsoft.com/typography/otspec/">http://www.microsoft.com/typography/otspec/</a> ).
 * <p>
 *  <code> OpenType </code>界面表示OpenType和TrueType字体。该接口使得可以从字体获得<i> sfnt </i>表。
 * 一个特定的<code> Font </code>对象可以实现这个接口。
 * <p>
 *  有关TrueType和OpenType字体的详细信息,请参阅OpenType规范。
 *  (<a href="http://www.microsoft.com/typography/otspec/"> http://www.microsoft.com/typography/otspec/ 
 * </a>)。
 *  有关TrueType和OpenType字体的详细信息,请参阅OpenType规范。
 * 
 */
public interface OpenType {

  /* 51 tag types so far */

  /**
   * Character to glyph mapping.  Table tag "cmap" in the Open
   * Type Specification.
   * <p>
   *  字符映射的字符。表格标签"cmap"在开放类型规范。
   * 
   */
  public final static int       TAG_CMAP        = 0x636d6170;

  /**
   * Font header.  Table tag "head" in the Open
   * Type Specification.
   * <p>
   *  字体标题。打开类型规范中的表标记"head"。
   * 
   */
  public final static int       TAG_HEAD        = 0x68656164;

  /**
   * Naming table.  Table tag "name" in the Open
   * Type Specification.
   * <p>
   *  命名表。表格标签"名称"在开放类型规范。
   * 
   */
  public final static int       TAG_NAME        = 0x6e616d65;

  /**
   * Glyph data.  Table tag "glyf" in the Open
   * Type Specification.
   * <p>
   *  字形数据。表格标签"glyf"在开放类型规范。
   * 
   */
  public final static int       TAG_GLYF        = 0x676c7966;

  /**
   * Maximum profile.  Table tag "maxp" in the Open
   * Type Specification.
   * <p>
   *  最大配置文件。打开类型规范中的表标记"maxp"。
   * 
   */
  public final static int       TAG_MAXP        = 0x6d617870;

  /**
   * CVT preprogram.  Table tag "prep" in the Open
   * Type Specification.
   * <p>
   *  CVT预编程。表格标签"prep"在开放类型规范。
   * 
   */
  public final static int       TAG_PREP        = 0x70726570;

  /**
   * Horizontal metrics.  Table tag "hmtx" in the Open
   * Type Specification.
   * <p>
   *  水平度量。表格标签"hmtx"在开放类型规范。
   * 
   */
  public final static int       TAG_HMTX        = 0x686d7478;

  /**
   * Kerning.  Table tag "kern" in the Open
   * Type Specification.
   * <p>
   *  Kerning。在开放类型规范中的表标记"kern"。
   * 
   */
  public final static int       TAG_KERN        = 0x6b65726e;

  /**
   * Horizontal device metrics.  Table tag "hdmx" in the Open
   * Type Specification.
   * <p>
   *  水平设备度量。表格标签"hdmx"在开放类型规范。
   * 
   */
  public final static int       TAG_HDMX        = 0x68646d78;

  /**
   * Index to location.  Table tag "loca" in the Open
   * Type Specification.
   * <p>
   *  位置索引。表格标签"loca"在开放类型规范。
   * 
   */
  public final static int       TAG_LOCA        = 0x6c6f6361;

  /**
   * PostScript Information.  Table tag "post" in the Open
   * Type Specification.
   * <p>
   *  PostScript信息。在开放类型规范中的表标记"post"。
   * 
   */
  public final static int       TAG_POST        = 0x706f7374;

  /**
   * OS/2 and Windows specific metrics.  Table tag "OS/2"
   * in the Open Type Specification.
   * <p>
   *  OS / 2和Windows特定指标。打开类型规范中的表标记"OS / 2"。
   * 
   */
  public final static int       TAG_OS2 = 0x4f532f32;

  /**
   * Control value table.  Table tag "cvt "
   * in the Open Type Specification.
   * <p>
   *  控制值表。表格标签"cvt"在开放类型规范。
   * 
   */
  public final static int       TAG_CVT = 0x63767420;

  /**
   * Grid-fitting and scan conversion procedure.  Table tag
   * "gasp" in the Open Type Specification.
   * <p>
   * 网格拟合和扫描转换过程。表格标签"gasp"在开放型规格。
   * 
   */
  public final static int       TAG_GASP        = 0x67617370;

  /**
   * Vertical device metrics.  Table tag "VDMX" in the Open
   * Type Specification.
   * <p>
   *  垂直设备指标。打开类型规范中的表标签"VDMX"。
   * 
   */
  public final static int       TAG_VDMX        = 0x56444d58;

  /**
   * Vertical metrics.  Table tag "vmtx" in the Open
   * Type Specification.
   * <p>
   *  垂直指标。表格标签"vmtx"在开放类型规范。
   * 
   */
  public final static int       TAG_VMTX        = 0x766d7478;

  /**
   * Vertical metrics header.  Table tag "vhea" in the Open
   * Type Specification.
   * <p>
   *  垂直指标标题。表格标签"vhea"在开放类型规范。
   * 
   */
  public final static int       TAG_VHEA        = 0x76686561;

  /**
   * Horizontal metrics header.  Table tag "hhea" in the Open
   * Type Specification.
   * <p>
   *  水平度量标题。表格标签"hhea"在开放类型规范。
   * 
   */
  public final static int       TAG_HHEA        = 0x68686561;

  /**
   * Adobe Type 1 font data.  Table tag "typ1" in the Open
   * Type Specification.
   * <p>
   *  Adobe Type 1字体数据。表格标签"typ1"在开放类型规范。
   * 
   */
  public final static int       TAG_TYP1        = 0x74797031;

  /**
   * Baseline table.  Table tag "bsln" in the Open
   * Type Specification.
   * <p>
   *  基线表。在开放类型规范中的表标记"bsln"。
   * 
   */
  public final static int       TAG_BSLN        = 0x62736c6e;

  /**
   * Glyph substitution.  Table tag "GSUB" in the Open
   * Type Specification.
   * <p>
   *  字形替换。打开类型规范中的表标记"GSUB"。
   * 
   */
  public final static int       TAG_GSUB        = 0x47535542;

  /**
   * Digital signature.  Table tag "DSIG" in the Open
   * Type Specification.
   * <p>
   *  电子签名。表格标签"DSIG"在开放类型规范。
   * 
   */
  public final static int       TAG_DSIG        = 0x44534947;

  /**
   * Font program.   Table tag "fpgm" in the Open
   * Type Specification.
   * <p>
   *  字体程序。打开类型规范中的表标记"fpgm"。
   * 
   */
  public final static int       TAG_FPGM        = 0x6670676d;

  /**
   * Font variation.   Table tag "fvar" in the Open
   * Type Specification.
   * <p>
   *  字体变体。在开放类型规范中的表标记"fvar"。
   * 
   */
  public final static int       TAG_FVAR        = 0x66766172;

  /**
   * Glyph variation.  Table tag "gvar" in the Open
   * Type Specification.
   * <p>
   *  字形变化。打开类型规范中的表标记"gvar"。
   * 
   */
  public final static int       TAG_GVAR        = 0x67766172;

  /**
   * Compact font format (Type1 font).  Table tag
   * "CFF " in the Open Type Specification.
   * <p>
   *  紧凑字体格式(Type1字体)。表格标签"CFF"在开放类型规范。
   * 
   */
  public final static int       TAG_CFF = 0x43464620;

  /**
   * Multiple master supplementary data.  Table tag
   * "MMSD" in the Open Type Specification.
   * <p>
   *  多主控补充数据。表格标签"MMSD"在开放类型规范。
   * 
   */
  public final static int       TAG_MMSD        = 0x4d4d5344;

  /**
   * Multiple master font metrics.  Table tag
   * "MMFX" in the Open Type Specification.
   * <p>
   *  多个主字体度量。表格标签"MMFX"在开放类型规范。
   * 
   */
  public final static int       TAG_MMFX        = 0x4d4d4658;

  /**
   * Baseline data.  Table tag "BASE" in the Open
   * Type Specification.
   * <p>
   *  基线数据。打开类型规范中的表标记"BASE"。
   * 
   */
  public final static int       TAG_BASE        = 0x42415345;

  /**
   * Glyph definition.  Table tag "GDEF" in the Open
   * Type Specification.
   * <p>
   *  字形定义。在开放类型规范中的表标记"GDEF"。
   * 
   */
  public final static int       TAG_GDEF        = 0x47444546;

  /**
   * Glyph positioning.  Table tag "GPOS" in the Open
   * Type Specification.
   * <p>
   *  字形定位。打开类型规范中的表标记"GPOS"。
   * 
   */
  public final static int       TAG_GPOS        = 0x47504f53;

  /**
   * Justification.  Table tag "JSTF" in the Open
   * Type Specification.
   * <p>
   *  理由。打开类型规范中的表标记"JSTF"。
   * 
   */
  public final static int       TAG_JSTF        = 0x4a535446;

  /**
   * Embedded bitmap data.  Table tag "EBDT" in the Open
   * Type Specification.
   * <p>
   *  嵌入式位图数据。表格标签"EBDT"在开放类型规范中。
   * 
   */
  public final static int       TAG_EBDT        = 0x45424454;

  /**
   * Embedded bitmap location.  Table tag "EBLC" in the Open
   * Type Specification.
   * <p>
   * 嵌入式位图位置。表格标签"EBLC"在开放类型规范。
   * 
   */
  public final static int       TAG_EBLC        = 0x45424c43;

  /**
   * Embedded bitmap scaling.  Table tag "EBSC" in the Open
   * Type Specification.
   * <p>
   *  嵌入式位图缩放。表格标签"EBSC"在开放类型规范。
   * 
   */
  public final static int       TAG_EBSC        = 0x45425343;

  /**
   * Linear threshold.  Table tag "LTSH" in the Open
   * Type Specification.
   * <p>
   *  线性阈值。表格标签"LTSH"在开放类型规范。
   * 
   */
  public final static int       TAG_LTSH        = 0x4c545348;

  /**
   * PCL 5 data.  Table tag "PCLT" in the Open
   * Type Specification.
   * <p>
   *  PCL 5数据。表格标签"PCLT"在开放类型规范。
   * 
   */
  public final static int       TAG_PCLT        = 0x50434c54;

  /**
   * Accent attachment.  Table tag "acnt" in the Open
   * Type Specification.
   * <p>
   *  重音附件。表格标签"acnt"在开放类型规范。
   * 
   */
  public final static int       TAG_ACNT        = 0x61636e74;

  /**
   * Axis variation.  Table tag "avar" in the Open
   * Type Specification.
   * <p>
   *  轴变化。表格标签"avar"在开放类型规范。
   * 
   */
  public final static int       TAG_AVAR        = 0x61766172;

  /**
   * Bitmap data.  Table tag "bdat" in the Open
   * Type Specification.
   * <p>
   *  位图数据。在开放类型规范中的表标记"bdat"。
   * 
   */
  public final static int       TAG_BDAT        = 0x62646174;

  /**
   * Bitmap location.  Table tag "bloc" in the Open
   * Type Specification.
   * <p>
   *  位图位置。打开类型规范中的表标记"bloc"。
   * 
   */
  public final static int       TAG_BLOC        = 0x626c6f63;

   /**
    * CVT variation.  Table tag "cvar" in the Open
    * Type Specification.
    * <p>
    *  CVT变化。打开类型规范中的表标记"cvar"。
    * 
    */
  public final static int       TAG_CVAR        = 0x63766172;

  /**
   * Feature name.  Table tag "feat" in the Open
    * Type Specification.
    * <p>
    *  功能名称。表格标签"feat"在开放类型规范。
    * 
   */
  public final static int       TAG_FEAT        = 0x66656174;

  /**
   * Font descriptors.  Table tag "fdsc" in the Open
   * Type Specification.
   * <p>
   *  字体描述符。打开类型规范中的表标记"fdsc"。
   * 
   */
  public final static int       TAG_FDSC        = 0x66647363;

  /**
   * Font metrics.  Table tag "fmtx" in the Open
   * Type Specification.
   * <p>
   *  字体指标。打开类型规范中的表标记"fmtx"。
   * 
   */
  public final static int       TAG_FMTX        = 0x666d7478;

  /**
   * Justification.  Table tag "just" in the Open
   * Type Specification.
   * <p>
   *  理由。在开放类型规范中的表标签"just"。
   * 
   */
  public final static int       TAG_JUST        = 0x6a757374;

  /**
   * Ligature caret.   Table tag "lcar" in the Open
   * Type Specification.
   * <p>
   *  连接点。表格标签"lcar"在开放类型规范。
   * 
   */
  public final static int       TAG_LCAR        = 0x6c636172;

  /**
   * Glyph metamorphosis.  Table tag "mort" in the Open
   * Type Specification.
   * <p>
   *  字形变态。在开放类型规范中的表标记"mort"。
   * 
   */
  public final static int       TAG_MORT        = 0x6d6f7274;

  /**
   * Optical bounds.  Table tag "opbd" in the Open
   * Type Specification.
   * <p>
   *  光学界限。表格标签"opbd"在开放类型规范。
   * 
   */
  public final static int       TAG_OPBD        = 0x6d6f7274;

  /**
   * Glyph properties.  Table tag "prop" in the Open
   * Type Specification.
   * <p>
   *  字形属性。在开放类型规范中的表标记"prop"。
   * 
   */
  public final static int       TAG_PROP        = 0x70726f70;

  /**
   * Tracking.  Table tag "trak" in the Open
   * Type Specification.
   * <p>
   *  跟踪。在开放类型规范中的表标记"trak"。
   * 
   */
  public final static int       TAG_TRAK        = 0x7472616b;

  /**
   * Returns the version of the <code>OpenType</code> font.
   * 1.0 is represented as 0x00010000.
   * <p>
   *  返回<code> OpenType </code>字体的版本。 1.0表示为0x00010000。
   * 
   * 
   * @return the version of the <code>OpenType</code> font.
   */
  public int getVersion();

  /**
   * Returns the table as an array of bytes for a specified tag.
   * Tags for sfnt tables include items like <i>cmap</i>,
   * <i>name</i> and <i>head</i>.  The <code>byte</code> array
   * returned is a copy of the font data in memory.
   * <p>
   * 以指定标记的字节数组形式返回表。 sfnt表格的标签包括<i> cmap </i>,<i>名称</i>和<i>头</i>等项目。
   * 返回的<code> byte </code>数组是内存中的字体数据的副本。
   * 
   * 
   * @param     sfntTag a four-character code as a 32-bit integer
   * @return a <code>byte</code> array that is the table that
   * contains the font data corresponding to the specified
   * tag.
   */
  public byte[] getFontTable(int sfntTag);

  /**
   * Returns the table as an array of bytes for a specified tag.
   * Tags for sfnt tables include items like <i>cmap</i>,
   * <i>name</i> and <i>head</i>.  The byte array returned is a
   * copy of the font data in memory.
   * <p>
   *  以指定标记的字节数组形式返回表。 sfnt表格的标签包括<i> cmap </i>,<i>名称</i>和<i>头</i>等项目。返回的字节数组是内存中字体数据的副本。
   * 
   * 
   * @param     strSfntTag a four-character code as a
   *            <code>String</code>
   * @return a <code>byte</code> array that is the table that
   * contains the font data corresponding to the specified
   * tag.
   */
  public byte[] getFontTable(String strSfntTag);

  /**
   * Returns a subset of the table as an array of bytes
   * for a specified tag.  Tags for sfnt tables include
   * items like <i>cmap</i>, <i>name</i> and <i>head</i>.
   * The byte array returned is a copy of the font data in
   * memory.
   * <p>
   *  以指定标记的字节数组形式返回表的子集。 sfnt表格的标签包括<i> cmap </i>,<i>名称</i>和<i>头</i>等项目。返回的字节数组是内存中字体数据的副本。
   * 
   * 
   * @param     sfntTag a four-character code as a 32-bit integer
   * @param     offset index of first byte to return from table
   * @param     count number of bytes to return from table
   * @return a subset of the table corresponding to
   *            <code>sfntTag</code> and containing the bytes
   *            starting at <code>offset</code> byte and including
   *            <code>count</code> bytes.
   */
  public byte[] getFontTable(int sfntTag, int offset, int count);

  /**
   * Returns a subset of the table as an array of bytes
   * for a specified tag.  Tags for sfnt tables include items
   * like <i>cmap</i>, <i>name</i> and <i>head</i>. The
   * <code>byte</code> array returned is a copy of the font
   * data in memory.
   * <p>
   *  以指定标记的字节数组形式返回表的子集。 sfnt表格的标签包括<i> cmap </i>,<i>名称</i>和<i>头</i>等项目。
   * 返回的<code> byte </code>数组是内存中的字体数据的副本。
   * 
   * 
   * @param     strSfntTag a four-character code as a
   * <code>String</code>
   * @param     offset index of first byte to return from table
   * @param     count  number of bytes to return from table
   * @return a subset of the table corresponding to
   *            <code>strSfntTag</code> and containing the bytes
   *            starting at <code>offset</code> byte and including
   *            <code>count</code> bytes.
   */
  public byte[] getFontTable(String strSfntTag, int offset, int count);

  /**
   * Returns the size of the table for a specified tag. Tags for sfnt
   * tables include items like <i>cmap</i>, <i>name</i> and <i>head</i>.
   * <p>
   *  返回指定标记的表的大小。 sfnt表格的标签包括<i> cmap </i>,<i>名称</i>和<i>头</i>等项目。
   * 
   * 
   * @param     sfntTag a four-character code as a 32-bit integer
   * @return the size of the table corresponding to the specified
   * tag.
   */
  public int getFontTableSize(int sfntTag);

  /**
   * Returns the size of the table for a specified tag. Tags for sfnt
   * tables include items like <i>cmap</i>, <i>name</i> and <i>head</i>.
   * <p>
   *  返回指定标记的表的大小。 sfnt表格的标签包括<i> cmap </i>,<i>名称</i>和<i>头</i>等项目。
   * 
   * @param     strSfntTag a four-character code as a
   * <code>String</code>
   * @return the size of the table corresponding to the specified tag.
   */
  public int getFontTableSize(String strSfntTag);


}
