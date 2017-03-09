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

package com.sun.corba.se.spi.ior.iiop;

import com.sun.corba.se.spi.ior.TaggedProfile ;

import com.sun.corba.se.spi.orb.ORB ;
import com.sun.corba.se.spi.orb.ORBVersion ;

import com.sun.corba.se.spi.ior.iiop.GIOPVersion ;

/** IIOPProfile represents an IIOP tagged profile.
* It is essentially composed of an object identifier and
* a template.  The template contains all of the
* IIOP specific information in the profile.
* Templates are frequently shared between many different profiles,
* while the object identifiy is unique to each profile.
* <p>
*  它基本上由对象标识符和模板组成。模板包含配置文件中的所有IIOP特定信息。模板经常在许多不同的简档之间共享,而对象标识对于每个简档是唯一的。
* 
*/
public interface IIOPProfile extends TaggedProfile
{
    ORBVersion getORBVersion() ;

    /** Return the servant for this profile, if it is local
     * AND if the OA that implements this objref supports direct access to servants
     * outside of an invocation.
     * XXX move this to the ObjectKeyTemplate
     * <p>
     *  并且如果实现此objref的OA支持对调用之外的服务器的直接访问。 XXX将其移动到ObjectKeyTemplate
     * 
     */
    java.lang.Object getServant() ;

    /** Return the GIOPVersion of this profile.  Caches the result.
    /* <p>
     */
    GIOPVersion getGIOPVersion() ;

    /** Return the Codebase of this profile.  Caches the result.
    /* <p>
     */
    String getCodebase() ;
}
