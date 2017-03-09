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

import java.util.List;

/**
 * This is the top level interface for events dealing with DTDs
 *
 * <p>
 *  这是处理DTD的事件的顶层接口
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface DTD extends XMLEvent {

  /**
   * Returns the entire Document Type Declaration as a string, including
   * the internal DTD subset.
   * This may be null if there is not an internal subset.
   * If it is not null it must return the entire
   * Document Type Declaration which matches the doctypedecl
   * production in the XML 1.0 specification
   * <p>
   *  以字符串形式返回整个文档类型声明,包括内部DTD子集。如果没有内部子集,则可以为null。如果它不为null,它必须返回与XML 1.0规范中的doctypedecl生产相匹配的整个文档类型声明
   * 
   */
  String getDocumentTypeDeclaration();

  /**
   * Returns an implementation defined representation of the DTD.
   * This method may return null if no representation is available.
   * <p>
   *  返回DTD的实现定义表示。如果没有可用的表示,此方法可能返回null。
   * 
   */
  Object getProcessedDTD();

  /**
   * Return a List containing the notations declared in the DTD.
   * This list must contain NotationDeclaration events.
   * <p>
   *  返回包含在DTD中声明的符号的列表。此列表必须包含NotationDeclaration事件。
   * 
   * 
   * @see NotationDeclaration
   * @return an unordered list of NotationDeclaration events
   */
  List getNotations();

  /**
   * Return a List containing the general entities,
   * both external and internal, declared in the DTD.
   * This list must contain EntityDeclaration events.
   * <p>
   *  返回包含在DTD中声明的外部和内部的一般实体的列表。此列表必须包含EntityDeclaration事件。
   * 
   * @see EntityDeclaration
   * @return an unordered list of EntityDeclaration events
   */
  List getEntities();
}
