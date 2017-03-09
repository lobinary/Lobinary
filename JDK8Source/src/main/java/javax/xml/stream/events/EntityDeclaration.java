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
 * An interface for handling Entity Declarations
 *
 * This interface is used to record and report unparsed entity declarations.
 *
 * <p>
 *  用于处理实体声明的接口
 * 
 *  此接口用于记录和报告未解析的实体声明。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface EntityDeclaration extends XMLEvent {

  /**
   * The entity's public identifier, or null if none was given
   * <p>
   *  实体的公共标识符,如果没有给出,则为null
   * 
   * 
   * @return the public ID for this declaration or null
   */
  String getPublicId();

  /**
   * The entity's system identifier.
   * <p>
   *  实体的系统标识符。
   * 
   * 
   * @return the system ID for this declaration or null
   */
  String getSystemId();

  /**
   * The entity's name
   * <p>
   *  实体的名称
   * 
   * 
   * @return the name, may not be null
   */
  String getName();

  /**
   * The name of the associated notation.
   * <p>
   *  相关联的符号的名称。
   * 
   * 
   * @return the notation name
   */
  String getNotationName();

  /**
   * The replacement text of the entity.
   * This method will only return non-null
   * if this is an internal entity.
   * <p>
   *  实体的替换文本。此方法将只返回非null,如果这是一个内部实体。
   * 
   * 
   * @return null or the replacment text
   */
  String getReplacementText();

  /**
   * Get the base URI for this reference
   * or null if this information is not available
   * <p>
   *  获取此引用的基本URI,如果此信息不可用,则为null
   * 
   * @return the base URI or null
   */
  String getBaseURI();

}
