/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.ior ;

import java.util.Iterator ;

import org.omg.CORBA.portable.InputStream ;
import org.omg.CORBA.portable.OutputStream ;
import org.omg.CORBA.portable.StreamableValue ;

import org.omg.CORBA.TypeCode ;

import org.omg.PortableInterceptor.ObjectReferenceTemplate ;
import org.omg.PortableInterceptor.ObjectReferenceTemplateHelper ;

import com.sun.corba.se.spi.oa.ObjectAdapter ;

import com.sun.corba.se.spi.ior.ObjectId ;
import com.sun.corba.se.spi.ior.ObjectKeyTemplate ;
import com.sun.corba.se.spi.ior.ObjectAdapterId ;
import com.sun.corba.se.spi.ior.IOR;
import com.sun.corba.se.spi.ior.IORFactory;
import com.sun.corba.se.spi.ior.IORTemplate;
import com.sun.corba.se.spi.ior.IORTemplateList;
import com.sun.corba.se.spi.ior.IORFactories;

import com.sun.corba.se.impl.orbutil.ORBUtility ;

import com.sun.corba.se.spi.orb.ORB ;

/** This is an implementation of the ObjectReferenceTemplate abstract value
* type defined by the portable interceptors IDL.
* Note that this is a direct Java implementation
* of the abstract value type: there is no stateful value type defined in IDL,
* since defining the state in IDL is awkward and inefficient.  The best way
* to define the state is to use internal data structures that can be written
* to and read from CORBA streams.
* <p>
*  类型由便携式拦截器IDL定义。注意,这是抽象值类型的直接Java实现：在IDL中没有定义有状态值类型,因为在IDL中定义状态是尴尬和低效的。
* 定义状态的最好方法是使用可以写入和读取CORBA流的内部数据结构。
* 
*/
public class ObjectReferenceTemplateImpl extends ObjectReferenceProducerBase
    implements ObjectReferenceTemplate, StreamableValue
{
    transient private IORTemplate iorTemplate ;

    public ObjectReferenceTemplateImpl( InputStream is )
    {
        super( (ORB)(is.orb()) ) ;
        _read( is ) ;
    }

    public ObjectReferenceTemplateImpl( ORB orb, IORTemplate iortemp )
    {
        super( orb ) ;
        iorTemplate = iortemp ;
    }

    public boolean equals( Object obj )
    {
        if (!(obj instanceof ObjectReferenceTemplateImpl))
            return false ;

        ObjectReferenceTemplateImpl other = (ObjectReferenceTemplateImpl)obj ;

        return (iorTemplate != null) &&
            iorTemplate.equals( other.iorTemplate ) ;
    }

    public int hashCode()
    {
        return iorTemplate.hashCode() ;
    }

    // Note that this repository ID must reflect the implementation
    // of the abstract valuetype (that is, this class), not the
    // repository ID of the org.omg.PortableInterceptor.ObjectReferenceTemplate
    // class.  This allows for multiple independent implementations
    // of the abstract valuetype, should that become necessary.
    public static final String repositoryId =
        "IDL:com/sun/corba/se/impl/ior/ObjectReferenceTemplateImpl:1.0" ;

    public String[] _truncatable_ids()
    {
        return new String[] { repositoryId } ;
    }

    public TypeCode _type()
    {
        return ObjectReferenceTemplateHelper.type() ;
    }

    /** Read the data into a (presumably) empty ORTImpl.  This sets the
    * orb to the ORB of the InputStream.
    * <p>
    *  orb到InputStream的ORB。
    * 
    */
    public void _read( InputStream is )
    {
        org.omg.CORBA_2_3.portable.InputStream istr =
            (org.omg.CORBA_2_3.portable.InputStream)is ;
        iorTemplate = IORFactories.makeIORTemplate( istr ) ;
        orb = (ORB)(istr.orb()) ;
    }

    /** Write the state to the OutputStream.
    /* <p>
     */
    public void _write( OutputStream os )
    {
        org.omg.CORBA_2_3.portable.OutputStream ostr =
            (org.omg.CORBA_2_3.portable.OutputStream)os ;

        iorTemplate.write( ostr ) ;
    }

    public String server_id ()
    {
        int val = iorTemplate.getObjectKeyTemplate().getServerId() ;
        return Integer.toString( val ) ;
    }

    public String orb_id ()
    {
        return iorTemplate.getObjectKeyTemplate().getORBId() ;
    }

    public String[] adapter_name()
    {
        ObjectAdapterId poaid =
            iorTemplate.getObjectKeyTemplate().getObjectAdapterId() ;

        return poaid.getAdapterName() ;
    }

    public IORFactory getIORFactory()
    {
        return iorTemplate ;
    }

    public IORTemplateList getIORTemplateList()
    {
        IORTemplateList tl = IORFactories.makeIORTemplateList() ;
        tl.add( iorTemplate ) ;
        tl.makeImmutable() ;
        return tl ;
    }
}
