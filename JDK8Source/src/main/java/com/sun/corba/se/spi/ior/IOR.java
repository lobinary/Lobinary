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

import java.util.List ;
import java.util.Iterator ;

import com.sun.corba.se.spi.orb.ORBVersion ;

import com.sun.corba.se.spi.ior.iiop.GIOPVersion ;
import com.sun.corba.se.spi.ior.iiop.IIOPProfile ;

import com.sun.corba.se.spi.orb.ORB ;

/** An IOR is represented as a list of profiles.
* Only instances of TaggedProfile are contained in the list.
* <p>
*  只有TaggedProfile的实例包含在列表中。
* 
*/
public interface IOR extends List, Writeable, MakeImmutable
{
    ORB getORB() ;

    /** Return the type id string from the IOR.
    /* <p>
    */
    String getTypeId() ;

    /** Return an iterator that iterates over tagged profiles with
    * identifier id.  It is not possible to modify the list through this
    * iterator.
    * <p>
    *  标识符ID。不可能通过此迭代器修改列表。
    * 
    */
    Iterator iteratorById( int id ) ;

    /** Return a representation of this IOR in the standard GIOP stringified
     * format that begins with "IOR:".
     * <p>
     *  格式以"IOR："开头。
     * 
     */
    String stringify() ;

    /** Return a representation of this IOR in the standard GIOP marshalled
     * form.
     * <p>
     *  形成。
     * 
     */
    org.omg.IOP.IOR getIOPIOR() ;

    /** Return true if this IOR has no profiles.
    /* <p>
     */
    boolean isNil() ;

    /** Return true if this IOR is equivalent to ior.  Here equivalent means
     * that the typeids are the same, they have the same number of profiles,
     * and each profile is equivalent to the corresponding profile.
     * <p>
     *  typeids是相同的,它们具有相同数目的简档,并且每个简档相当于相应的简档。
     * 
     */
    boolean isEquivalent(IOR ior) ;

    /** Return the IORTemplate for this IOR.  This is simply a list
     * of all TaggedProfileTemplates derived from the TaggedProfiles
     * of the IOR.
     * <p>
     *  的从IOR的TaggedProfiles派生的所有TaggedProfileTemplates。
     * 
     */
    IORTemplateList getIORTemplates() ;

    /** Return the first IIOPProfile in this IOR.
     * XXX THIS IS TEMPORARY FOR BACKWARDS COMPATIBILITY AND WILL BE REMOVED
     * SOON!
     * <p>
     *  XXX这是临时的反向兼容性,并将被删除！
     */
    IIOPProfile getProfile() ;
}
