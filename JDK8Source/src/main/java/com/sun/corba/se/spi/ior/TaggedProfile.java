/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.ior;

import com.sun.corba.se.spi.orb.ORB ;

/** TaggedProfile represents a tagged profile in an IOR.
 * A profile contains all of the information necessary for an invocation.
 * It contains one or more endpoints that may be used for an invocation.
 * A TaggedProfile conceptually has three parts: A TaggedProfileTemplate,
 * an ObjectKeyTemplate, and an ObjectId.
 * <p>
 *  配置文件包含调用所需的所有信息。它包含可用于调用的一个或多个端点。
 *  TaggedProfile在概念上有三个部分：TaggedProfileTemplate,ObjectKeyTemplate和ObjectId。
 * 
 */
public interface TaggedProfile extends Identifiable, MakeImmutable
{
    TaggedProfileTemplate getTaggedProfileTemplate() ;

    ObjectId getObjectId() ;

    ObjectKeyTemplate getObjectKeyTemplate() ;

    ObjectKey getObjectKey() ;

    /** Return true is prof is equivalent to this TaggedProfile.
     * This means that this and prof are indistinguishable for
     * the purposes of remote invocation.  Typically this means that
     * the profile data is identical and both profiles contain exactly
     * the same components (if components are applicable).
     * isEquivalent( prof ) should imply that getObjectId().equals(
     * prof.getObjectId() ) is true, and so is
     * getObjectKeyTemplate().equals( prof.getObjectKeyTemplate() ).
     * <p>
     *  这意味着这个和prof是不可分辨的为了远程调用的目的。通常,这意味着配置文件数据是相同的,并且两个配置文件包含完全相同的组件(如果组件适用)。
     *  isEquivalent(prof)应该暗示getObjectId()。equals(prof.getObjectId())是true,因此是getObjectKeyTemplate()。
     * equals(prof.getObjectKeyTemplate())。
     * 
     */
    boolean isEquivalent( TaggedProfile prof ) ;

    /** Return the TaggedProfile as a CDR encapsulation in the standard
     * format.  This is required for Portable interceptors.
     * <p>
     *  格式。这是便携式拦截器所必需的。
     * 
     */
    org.omg.IOP.TaggedProfile getIOPProfile();

    /** Return true if this TaggedProfile was created in orb.
     *  Caches the result.
     * <p>
     *  缓存结果。
     */
    boolean isLocal() ;
}
