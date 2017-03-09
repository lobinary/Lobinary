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

package java.rmi.activation;

import java.rmi.server.UID;

/**
 * The identifier for a registered activation group serves several
 * purposes: <ul>
 * <li>identifies the group uniquely within the activation system, and
 * <li>contains a reference to the group's activation system so that the
 * group can contact its activation system when necessary.</ul><p>
 *
 * The <code>ActivationGroupID</code> is returned from the call to
 * <code>ActivationSystem.registerGroup</code> and is used to identify
 * the group within the activation system. This group id is passed
 * as one of the arguments to the activation group's special constructor
 * when an activation group is created/recreated.
 *
 * <p>
 *  注册激活组的标识符具有多个用途：<ul> <li>在激活系统中唯一标识组,<li>包含对组的激活系统的引用,以便组可以在必要时联系其激活系统。 </ul> <p>
 * 
 *  <code> ActivationGroupID </code>从调用<code> ActivationSystem.registerGroup </code>返回,用于标识激活系统中的组。
 * 当创建/重新创建激活组时,此组ID作为激活组的特殊构造函数的参数之一传递。
 * 
 * 
 * @author      Ann Wollrath
 * @see         ActivationGroup
 * @see         ActivationGroupDesc
 * @since       1.2
 */
public class ActivationGroupID implements java.io.Serializable {
    /**
    /* <p>
    /* 
     * @serial The group's activation system.
     */
    private ActivationSystem system;

    /**
    /* <p>
    /* 
     * @serial The group's unique id.
     */
    private UID uid = new UID();

    /** indicate compatibility with the Java 2 SDK v1.2 version of class */
    private  static final long serialVersionUID = -1648432278909740833L;

    /**
     * Constructs a unique group id.
     *
     * <p>
     *  构造唯一的组ID。
     * 
     * 
     * @param system the group's activation system
     * @throws UnsupportedOperationException if and only if activation is
     *         not supported by this implementation
     * @since 1.2
     */
    public ActivationGroupID(ActivationSystem system) {
        this.system = system;
    }

    /**
     * Returns the group's activation system.
     * <p>
     *  返回组的激活系统。
     * 
     * 
     * @return the group's activation system
     * @since 1.2
     */
    public ActivationSystem getSystem() {
        return system;
    }

    /**
     * Returns a hashcode for the group's identifier.  Two group
     * identifiers that refer to the same remote group will have the
     * same hash code.
     *
     * <p>
     *  返回组标识符的哈希码。引用同一远程组的两个组标识符将具有相同的哈希码。
     * 
     * 
     * @see java.util.Hashtable
     * @since 1.2
     */
    public int hashCode() {
        return uid.hashCode();
    }

    /**
     * Compares two group identifiers for content equality.
     * Returns true if both of the following conditions are true:
     * 1) the unique identifiers are equivalent (by content), and
     * 2) the activation system specified in each
     *    refers to the same remote object.
     *
     * <p>
     *  比较两个组标识符的内容相等性。如果以下两个条件都为真,则返回true：1)唯一标识符是等效的(通过内容),2)每个中指定的激活系统引用同一个远程对象。
     * 
     * @param   obj     the Object to compare with
     * @return  true if these Objects are equal; false otherwise.
     * @see             java.util.Hashtable
     * @since 1.2
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof ActivationGroupID) {
            ActivationGroupID id = (ActivationGroupID)obj;
            return (uid.equals(id.uid) && system.equals(id.system));
        } else {
            return false;
        }
    }
}
