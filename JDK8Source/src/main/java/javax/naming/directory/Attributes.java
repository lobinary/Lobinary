/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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


package javax.naming.directory;

import java.util.Hashtable;
import java.util.Enumeration;

import javax.naming.NamingException;
import javax.naming.NamingEnumeration;

/**
  * This interface represents a collection of attributes.
  *<p>
  * In a directory, named objects can have associated with them
  * attributes.  The Attributes interface represents a collection of attributes.
  * For example, you can request from the directory the attributes
  * associated with an object.  Those attributes are returned in
  * an object that implements the Attributes interface.
  *<p>
  * Attributes in an object that implements the  Attributes interface are
  * unordered. The object can have zero or more attributes.
  * Attributes is either case-sensitive or case-insensitive (case-ignore).
  * This property is determined at the time the Attributes object is
  * created. (see BasicAttributes constructor for example).
  * In a case-insensitive Attributes, the case of its attribute identifiers
  * is ignored when searching for an attribute, or adding attributes.
  * In a case-sensitive Attributes, the case is significant.
  *<p>
  * Note that updates to Attributes (such as adding or removing an attribute)
  * do not affect the corresponding representation in the directory.
  * Updates to the directory can only be effected
  * using operations in the DirContext interface.
  *
  * <p>
  *  此接口表示属性的集合。
  * p>
  *  在目录中,命名对象可以与其关联的属性。属性接口表示属性的集合。例如,您可以从目录请求与对象关联的属性。这些属性在实现Attributes接口的对象中返回。
  * p>
  *  实现Attributes接口的对象中的属性是无序的。对象可以具有零个或多个属性。属性区分大小写或不区分大小写(大小写忽略)。此属性在创建Attributes对象时确定。
  *  (例如参见BasicAttributes构造函数)。在不区分大小写的属性中,在搜索属性或添加属性时忽略其属性标识符的大小写。在区分大小写的属性中,大小写很大。
  * p>
  *  请注意,属性的更新(例如添加或删除属性)不会影响目录中的相应表示。只有使用DirContext接口中的操作才能更新目录。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see DirContext#getAttributes
  * @see DirContext#modifyAttributes
  * @see DirContext#bind
  * @see DirContext#rebind
  * @see DirContext#createSubcontext
  * @see DirContext#search
  * @see BasicAttributes
  * @since 1.3
  */

public interface Attributes extends Cloneable, java.io.Serializable {
    /**
      * Determines whether the attribute set ignores the case of
      * attribute identifiers when retrieving or adding attributes.
      * <p>
      *  确定属性集在检索或添加属性时是否忽略属性标识符的大小写。
      * 
      * 
      * @return true if case is ignored; false otherwise.
      */
    boolean isCaseIgnored();

    /**
      * Retrieves the number of attributes in the attribute set.
      *
      * <p>
      *  检索属性集中的属性数。
      * 
      * 
      * @return The nonnegative number of attributes in this attribute set.
      */
    int size();

    /**
      * Retrieves the attribute with the given attribute id from the
      * attribute set.
      *
      * <p>
      * 从属性集中检索具有给定属性id的属性。
      * 
      * 
      * @param attrID The non-null id of the attribute to retrieve.
      *           If this attribute set ignores the character
      *           case of its attribute ids, the case of attrID
      *           is ignored.
      * @return The attribute identified by attrID; null if not found.
      * @see #put
      * @see #remove
      */
    Attribute get(String attrID);

    /**
      * Retrieves an enumeration of the attributes in the attribute set.
      * The effects of updates to this attribute set on this enumeration
      * are undefined.
      *
      * <p>
      *  检索属性集中属性的枚举。对此枚举的此属性集的更新的影响未定义。
      * 
      * 
      * @return A non-null enumeration of the attributes in this attribute set.
      *         Each element of the enumeration is of class <tt>Attribute</tt>.
      *         If attribute set has zero attributes, an empty enumeration
      *         is returned.
      */
    NamingEnumeration<? extends Attribute> getAll();

    /**
      * Retrieves an enumeration of the ids of the attributes in the
      * attribute set.
      * The effects of updates to this attribute set on this enumeration
      * are undefined.
      *
      * <p>
      *  检索属性集中属性的ID的枚举。对此枚举的此属性集的更新的影响未定义。
      * 
      * 
      * @return A non-null enumeration of the attributes' ids in
      *         this attribute set. Each element of the enumeration is
      *         of class String.
      *         If attribute set has zero attributes, an empty enumeration
      *         is returned.
      */
    NamingEnumeration<String> getIDs();

    /**
      * Adds a new attribute to the attribute set.
      *
      * <p>
      *  向属性集添加新属性。
      * 
      * 
      * @param attrID   non-null The id of the attribute to add.
      *           If the attribute set ignores the character
      *           case of its attribute ids, the case of attrID
      *           is ignored.
      * @param val      The possibly null value of the attribute to add.
      *                 If null, the attribute does not have any values.
      * @return The Attribute with attrID that was previous in this attribute set;
      *         null if no such attribute existed.
      * @see #remove
      */
    Attribute put(String attrID, Object val);

    /**
      * Adds a new attribute to the attribute set.
      *
      * <p>
      *  向属性集添加新属性。
      * 
      * 
      * @param attr     The non-null attribute to add.
      *                 If the attribute set ignores the character
      *                 case of its attribute ids, the case of
      *                 attr's identifier is ignored.
      * @return The Attribute with the same ID as attr that was previous
      *         in this attribute set;
      *         null if no such attribute existed.
      * @see #remove
      */
    Attribute put(Attribute attr);

    /**
      * Removes the attribute with the attribute id 'attrID' from
      * the attribute set. If the attribute does not exist, ignore.
      *
      * <p>
      *  从属性集中删除属性id为"attrID"的属性。如果属性不存在,请忽略。
      * 
      * 
      * @param attrID   The non-null id of the attribute to remove.
      *                 If the attribute set ignores the character
      *                 case of its attribute ids, the case of
      *                 attrID is ignored.
      * @return The Attribute with the same ID as attrID that was previous
      *         in the attribute set;
      *         null if no such attribute existed.
      */
    Attribute remove(String attrID);

    /**
      * Makes a copy of the attribute set.
      * The new set contains the same attributes as the original set:
      * the attributes are not themselves cloned.
      * Changes to the copy will not affect the original and vice versa.
      *
      * <p>
      *  创建属性集的副本。新集包含与原始集相同的属性：属性本身不是克隆的。对副本的更改不会影响原始副本,反之亦然。
      * 
      * 
      * @return A non-null copy of this attribute set.
      */
    Object clone();

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    // static final long serialVersionUID = -7247874645443605347L;
}
