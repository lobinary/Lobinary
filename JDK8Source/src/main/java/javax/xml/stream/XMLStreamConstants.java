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

package javax.xml.stream;

/**
 * This interface declares the constants used in this API.
 * Numbers in the range 0 to 256 are reserved for the specification,
 * user defined events must use event codes outside that range.
 *
 * <p>
 *  此接口声明此API中使用的常量。范围0到256中的数字保留用于规范,用户定义的事件必须使用该范围之外的事件代码。
 * 
 * 
 * @since 1.6
 */

public interface XMLStreamConstants {
  /**
   * Indicates an event is a start element
   * <p>
   *  表示事件是开始元素
   * 
   * 
   * @see javax.xml.stream.events.StartElement
   */
  public static final int START_ELEMENT=1;
  /**
   * Indicates an event is an end element
   * <p>
   *  表示事件是结束元素
   * 
   * 
   * @see javax.xml.stream.events.EndElement
   */
  public static final int END_ELEMENT=2;
  /**
   * Indicates an event is a processing instruction
   * <p>
   *  表示事件是处理指令
   * 
   * 
   * @see javax.xml.stream.events.ProcessingInstruction
   */
  public static final int PROCESSING_INSTRUCTION=3;

  /**
   * Indicates an event is characters
   * <p>
   *  表示事件是字符
   * 
   * 
   * @see javax.xml.stream.events.Characters
   */
  public static final int CHARACTERS=4;

  /**
   * Indicates an event is a comment
   * <p>
   *  表示事件是注释
   * 
   * 
   * @see javax.xml.stream.events.Comment
   */
  public static final int COMMENT=5;

  /**
   * The characters are white space
   * (see [XML], 2.10 "White Space Handling").
   * Events are only reported as SPACE if they are ignorable white
   * space.  Otherwise they are reported as CHARACTERS.
   * <p>
   *  字符是空格(参见[XML],2.10"空白处理")。只有当它们是可忽略的空格时,事件才会报告为空格。否则它们将报告为CHARACTERS。
   * 
   * 
   * @see javax.xml.stream.events.Characters
   */
  public static final int SPACE=6;

  /**
   * Indicates an event is a start document
   * <p>
   *  表示事件是开始文档
   * 
   * 
   * @see javax.xml.stream.events.StartDocument
   */
  public static final int START_DOCUMENT=7;

  /**
   * Indicates an event is an end document
   * <p>
   *  表示事件是结束文档
   * 
   * 
   * @see javax.xml.stream.events.EndDocument
   */
  public static final int END_DOCUMENT=8;

  /**
   * Indicates an event is an entity reference
   * <p>
   *  表示事件是实体引用
   * 
   * 
   * @see javax.xml.stream.events.EntityReference
   */
  public static final int ENTITY_REFERENCE=9;

  /**
   * Indicates an event is an attribute
   * <p>
   *  表示事件是属性
   * 
   * 
   * @see javax.xml.stream.events.Attribute
   */
  public static final int ATTRIBUTE=10;

  /**
   * Indicates an event is a DTD
   * <p>
   *  表示事件是DTD
   * 
   * 
   * @see javax.xml.stream.events.DTD
   */
  public static final int DTD=11;

  /**
   * Indicates an event is a CDATA section
   * <p>
   *  表示事件是CDATA部分
   * 
   * 
   * @see javax.xml.stream.events.Characters
   */
  public static final int CDATA=12;

  /**
   * Indicates the event is a namespace declaration
   *
   * <p>
   *  表示事件是一个命名空间声明
   * 
   * 
   * @see javax.xml.stream.events.Namespace
   */
  public static final int NAMESPACE=13;

  /**
   * Indicates a Notation
   * <p>
   *  表示符号
   * 
   * 
   * @see javax.xml.stream.events.NotationDeclaration
   */
  public static final int NOTATION_DECLARATION=14;

  /**
   * Indicates a Entity Declaration
   * <p>
   *  表示实体声明
   * 
   * @see javax.xml.stream.events.NotationDeclaration
   */
  public static final int ENTITY_DECLARATION=15;
}
