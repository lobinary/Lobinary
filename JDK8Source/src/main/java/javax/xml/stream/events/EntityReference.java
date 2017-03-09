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
 * An interface for handling Entity events.
 *
 * This event reports entities that have not been resolved
 * and reports their replacement text unprocessed (if
 * available).  This event will be reported if javax.xml.stream.isReplacingEntityReferences
 * is set to false.  If javax.xml.stream.isReplacingEntityReferences is set to true
 * entity references will be resolved transparently.
 *
 * Entities are handled in two possible ways:
 *
 * (1) If javax.xml.stream.isReplacingEntityReferences is set to true
 * all entity references are resolved and reported as markup transparently.
 * (2) If javax.xml.stream.isReplacingEntityReferences is set to false
 * Entity references are reported as an EntityReference Event.
 *
 * <p>
 *  用于处理实体事件的接口。
 * 
 *  此事件报告尚未解析的实体,并报告未处理的替换文本(如果可用)。如果javax.xml.stream.isReplacingEntityReferences设置为false,则将报告此事件。
 * 如果javax.xml.stream.isReplacingEntityReferences设置为true,实体引用将被透明地解析。
 * 
 *  实体以两种可能的方式处理：
 * 
 *  (1)如果javax.xml.stream.isReplacingEntityReferences设置为true,则所有实体引用都将被解析并透明地报告为标记。
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface EntityReference extends XMLEvent {

  /**
   * Return the declaration of this entity.
   * <p>
   *  (2)如果javax.xml.stream.isReplacingEntityReferences设置为false,则将实体引用报告为EntityReference事件。
   * 
   */
  EntityDeclaration getDeclaration();

  /**
   * The name of the entity
   * <p>
   *  返回此实体的声明。
   * 
   * 
   * @return the entity's name, may not be null
   */
  String getName();
}
