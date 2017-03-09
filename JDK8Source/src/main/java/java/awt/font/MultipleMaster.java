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

import java.awt.Font;

/**
 * The <code>MultipleMaster</code> interface represents Type 1
 * Multiple Master fonts.
 * A particular {@link Font} object can implement this interface.
 * <p>
 *  <code> MultipleMaster </code>接口表示类型1多主字体。一个特定的{@link Font}对象可以实现这个接口。
 * 
 */
public interface MultipleMaster {

  /**
   * Returns the number of multiple master design controls.
   * Design axes include things like width, weight and optical scaling.
   * <p>
   *  返回多个主设计控件的数量。设计轴包括宽度,重量和光学缩放等。
   * 
   * 
   * @return the number of multiple master design controls
   */
  public  int getNumDesignAxes();

  /**
   * Returns an array of design limits interleaved in the form [from&rarr;to]
   * for each axis.  For example,
   * design limits for weight could be from 0.1 to 1.0. The values are
   * returned in the same order returned by
   * <code>getDesignAxisNames</code>.
   * <p>
   *  返回每个轴以[from&rarr; to]形式交错的设计限制数组。例如,重量的设计限制可以为0.1至1.0。
   * 这些值按照<code> getDesignAxisNames </code>返回的相同顺序返回。
   * 
   * 
   * @return an array of design limits for each axis.
   */
  public  float[]  getDesignAxisRanges();

  /**
   * Returns an array of default design values for each axis.  For example,
   * the default value for weight could be 1.6. The values are returned
   * in the same order returned by <code>getDesignAxisNames</code>.
   * <p>
   *  返回每个轴的默认设计值数组。例如,weight的默认值可以是1.6。这些值按照<code> getDesignAxisNames </code>返回的相同顺序返回。
   * 
   * 
   * @return an array of default design values for each axis.
   */
  public  float[]  getDesignAxisDefaults();

  /**
   * Returns the name for each design axis. This also determines the order in
   * which the values for each axis are returned.
   * <p>
   *  返回每个设计轴的名称。这也确定返回每个轴的值的顺序。
   * 
   * 
   * @return an array containing the names of each design axis.
   */
  public  String[] getDesignAxisNames();

  /**
   * Creates a new instance of a multiple master font based on the design
   * axis values contained in the specified array. The size of the array
   * must correspond to the value returned from
   * <code>getNumDesignAxes</code> and the values of the array elements
   * must fall within limits specified by
   * <code>getDesignAxesLimits</code>. In case of an error,
   * <code>null</code> is returned.
   * <p>
   *  基于指定数组中包含的设计轴值创建多主字体的新实例。
   * 数组的大小必须与从<code> getNumDesignAxes </code>返回的值相对应,并且数组元素的值必须在<code> getDesignAxesLimits </code>指定的限制之内。
   *  基于指定数组中包含的设计轴值创建多主字体的新实例。如果出现错误,将返回<code> null </code>。
   * 
   * 
   * @param axes an array containing axis values
   * @return a {@link Font} object that is an instance of
   * <code>MultipleMaster</code> and is based on the design axis values
   * provided by <code>axes</code>.
   */
  public Font deriveMMFont(float[] axes);

  /**
   * Creates a new instance of a multiple master font based on detailed metric
   * information. In case of an error, <code>null</code> is returned.
   * <p>
   * 
   * @param glyphWidths an array of floats representing the desired width
   * of each glyph in font space
   * @param avgStemWidth the average stem width for the overall font in
   * font space
   * @param typicalCapHeight the height of a typical upper case char
   * @param typicalXHeight the height of a typical lower case char
   * @param italicAngle the angle at which the italics lean, in degrees
   * counterclockwise from vertical
   * @return a <code>Font</code> object that is an instance of
   * <code>MultipleMaster</code> and is based on the specified metric
   * information.
   */
  public Font deriveMMFont(
                                   float[] glyphWidths,
                                   float avgStemWidth,
                                   float typicalCapHeight,
                                   float typicalXHeight,
                                   float italicAngle);


}
