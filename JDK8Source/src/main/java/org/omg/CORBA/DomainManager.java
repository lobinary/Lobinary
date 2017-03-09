/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/** Provides mechanisms for establishing and navigating relationships to
 *  superior and subordinate domains, as well as for creating and accessing
 *  policies. The <tt>DomainManager</tt> has associated with it the policy
 *  objects for a
 *  particular domain. The domain manager also records the membership of
 *  the domain and provides the means to add and remove members. The domain
 *  manager is itself a member of a domain, possibly the domain it manages.
 *  The domain manager provides mechanisms for establishing and navigating
 *  relationships to superior and subordinate domains and
 *  creating and accessing policies.
 * <p>
 *  上级和下级域名,以及创建和访问策略。 <tt> DomainManager </tt>已将其与特定域的策略对象相关联。域管理器还记录域的成员资格,并提供添加和删除成员的方法。
 * 域管理器本身是域的成员,可能是其管理的域。域管理器提供用于建立和导航到上级和下级域的关系以及创建和访问策略的机制。
 */

public interface DomainManager extends DomainManagerOperations,
    org.omg.CORBA.Object, org.omg.CORBA.portable.IDLEntity
{
}
