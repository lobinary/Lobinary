/***** Lobxxx Translate Finished ******/
/*
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

/*
 * Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * <p>
 *  Oracle Corporation的版权所有(c)2009。版权所有。
 * 
 */

package javax.xml.stream.events;

/**
 * This describes the interface to Characters events.
 * All text events get reported as Characters events.
 * Content, CData and whitespace are all reported as
 * Characters events.  IgnorableWhitespace, in most cases,
 * will be set to false unless an element declaration of element
 * content is present for the current element.
 *
 * <p>
 *  这描述了字符事件的接口。所有文本事件都将报告为"字符"事件。内容,CData和空格都被报告为Characters事件。
 *  IgnorableWhitespace在大多数情况下将被设置为false,除非当前元素存在元素内容的元素声明。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface Characters extends XMLEvent {
  /**
   * Get the character data of this event
   * <p>
   *  获取此事件的字符数据
   * 
   */
  public String getData();

  /**
   * Returns true if this set of Characters
   * is all whitespace.  Whitespace inside a document
   * is reported as CHARACTERS.  This method allows
   * checking of CHARACTERS events to see if they
   * are composed of only whitespace characters
   * <p>
   *  如果此组字符全部为空格,则返回true。文档中的空格被报告为CHARACTERS。此方法允许检查CHARACTERS事件,以查看它们是否只由空格字符组成
   * 
   */
  public boolean isWhiteSpace();

  /**
   * Returns true if this is a CData section.  If this
   * event is CData its event type will be CDATA
   *
   * If javax.xml.stream.isCoalescing is set to true CDATA Sections
   * that are surrounded by non CDATA characters will be reported
   * as a single Characters event. This method will return false
   * in this case.
   * <p>
   *  如果这是一个CData节,返回true。如果此事件是CData,其事件类型将是CDATA
   * 
   *  如果javax.xml.stream.isCoalescing设置为true CDATA由非CDATA字符包围的节将报告为单个字符事件。在这种情况下,此方法将返回false。
   * 
   */
  public boolean isCData();

  /**
   * Return true if this is ignorableWhiteSpace.  If
   * this event is ignorableWhiteSpace its event type will
   * be SPACE.
   * <p>
   */
  public boolean isIgnorableWhiteSpace();

}
