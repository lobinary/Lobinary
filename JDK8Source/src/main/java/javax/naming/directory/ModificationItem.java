/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2001, Oracle and/or its affiliates. All rights reserved.
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

/**
  * This class represents a modification item.
  * It consists of a modification code and an attribute on which to operate.
  *<p>
  * A ModificationItem instance is not synchronized against concurrent
  * multithreaded access. Multiple threads trying to access and modify
  * a single ModificationItem instance should lock the object.
  *
  * <p>
  *  此类表示修改项。它由修改代码和要操作的属性组成。
  * p>
  *  修改项实例不与并发多线程访问同步。尝试访问和修改单个ModificationItem实例的多个线程应锁定该对象。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

/*
  *<p>
  * The serialized form of a ModificationItem object consists of the
  * modification op (and int) and the corresponding Attribute.
  * <p>
  * p>
  *  ModificationItem对象的序列化形式由修改op(和int)和相应的属性组成。
  * 
*/

public class ModificationItem implements java.io.Serializable {
    /**
     * Contains an integer identify the modification
     * to be performed.
     * <p>
     *  包含整数标识要执行的修改。
     * 
     * 
     * @serial
     */
    private int mod_op;
    /**
     * Contains the attribute identifying
     * the attribute and/or its value to be applied for the modification.
     * <p>
     *  包含标识要应用于修改的属性和/或其值的属性。
     * 
     * 
     * @serial
     */
    private Attribute attr;

    /**
      * Creates a new instance of ModificationItem.
      * <p>
      *  创建一个ModificationItem的新实例。
      * 
      * 
      * @param mod_op Modification to apply.  It must be one of:
      *         DirContext.ADD_ATTRIBUTE
      *         DirContext.REPLACE_ATTRIBUTE
      *         DirContext.REMOVE_ATTRIBUTE
      * @param attr     The non-null attribute to use for modification.
      * @exception IllegalArgumentException If attr is null, or if mod_op is
      *         not one of the ones specified above.
      */
    public ModificationItem(int mod_op, Attribute attr) {
        switch (mod_op) {
        case DirContext.ADD_ATTRIBUTE:
        case DirContext.REPLACE_ATTRIBUTE:
        case DirContext.REMOVE_ATTRIBUTE:
            if (attr == null)
                throw new IllegalArgumentException("Must specify non-null attribute for modification");

            this.mod_op = mod_op;
            this.attr = attr;
            break;

        default:
            throw new IllegalArgumentException("Invalid modification code " + mod_op);
        }
    }

    /**
      * Retrieves the modification code of this modification item.
      * <p>
      *  检索此修改项的修改代码。
      * 
      * 
      * @return The modification code.  It is one of:
      *         DirContext.ADD_ATTRIBUTE
      *         DirContext.REPLACE_ATTRIBUTE
      *         DirContext.REMOVE_ATTRIBUTE
      */
    public int getModificationOp() {
        return mod_op;
    }

    /**
      * Retrieves the attribute associated with this modification item.
      * <p>
      *  检索与此修改项关联的属性。
      * 
      * 
      * @return The non-null attribute to use for the modification.
      */
    public Attribute getAttribute() {
        return attr;
    }

    /**
      * Generates the string representation of this modification item,
      * which consists of the modification operation and its related attribute.
      * The string representation is meant for debugging and not to be
      * interpreted programmatically.
      *
      * <p>
      *  生成此修改项的字符串表示,其由修改操作及其相关属性组成。字符串表示用于调试,不能以编程方式解释。
      * 
      * 
      * @return The non-null string representation of this modification item.
      */
    public String toString() {
        switch (mod_op) {
        case DirContext.ADD_ATTRIBUTE:
            return ("Add attribute: " + attr.toString());

        case DirContext.REPLACE_ATTRIBUTE:
            return ("Replace attribute: " + attr.toString());

        case DirContext.REMOVE_ATTRIBUTE:
            return ("Remove attribute: " + attr.toString());
        }
        return "";      // should never happen
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 7573258562534746850L;
}
