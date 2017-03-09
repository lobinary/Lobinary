/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.print.attribute;

import java.io.Serializable;

/**
 * Interface Attribute is the base interface implemented by any and every
 * printing attribute class to indicate that the class represents a
 * printing attribute. All printing attributes are serializable.
 * <P>
 *
 * <p>
 *  接口属性是由任何和每个打印属性类实现的基本接口,以指示该类表示打印属性。所有打印属性都是可序列化的。
 * <P>
 * 
 * 
 * @author  David Mendenhall
 * @author  Alan Kaminsky
 */
public interface Attribute extends Serializable {

  /**
   * Get the printing attribute class which is to be used as the "category"
   * for this printing attribute value when it is added to an attribute set.
   *
   * <p>
   *  获取打印属性类,将其用作此打印属性值的"类别",将其添加到属性集中。
   * 
   * 
   * @return  Printing attribute class (category), an instance of class
   *          {@link java.lang.Class java.lang.Class}.
   */
  public Class<? extends Attribute> getCategory();

  /**
   * Get the name of the category of which this attribute value is an
   * instance.
   * <P>
   * <I>Note:</I> This method is intended to provide a default, nonlocalized
   * string for the attribute's category. If two attribute objects return the
   * same category from the <CODE>getCategory()</CODE> method, they should
   * return the same name from the <CODE>getName()</CODE> method.
   *
   * <p>
   *  获取此属性值为实例的类别的名称。
   * <P>
   *  <I>注意：</I>此方法旨在为属性的类别提供默认的非本地化字符串。
   * 
   * @return  Attribute category name.
   */
  public String getName();

}
