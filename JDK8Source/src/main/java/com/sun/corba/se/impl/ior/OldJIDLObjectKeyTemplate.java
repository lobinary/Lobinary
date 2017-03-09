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

package com.sun.corba.se.impl.ior;

import org.omg.CORBA.OctetSeqHolder ;

import org.omg.CORBA_2_3.portable.InputStream ;
import org.omg.CORBA_2_3.portable.OutputStream ;

import com.sun.corba.se.spi.ior.ObjectId ;
import com.sun.corba.se.spi.ior.ObjectKeyFactory ;

import com.sun.corba.se.spi.orb.ORB ;
import com.sun.corba.se.spi.orb.ORBVersion ;
import com.sun.corba.se.spi.orb.ORBVersionFactory ;

import com.sun.corba.se.impl.ior.ObjectKeyFactoryImpl ;

import com.sun.corba.se.impl.encoding.CDRInputStream ;

/**
 * Handles object keys created by JDK ORBs from before JDK 1.4.0.
 * <p>
 *  处理JDK 1.4.0之前的JDK ORB创建的对象键。
 * 
 */
public final class OldJIDLObjectKeyTemplate extends OldObjectKeyTemplateBase
{
    /**
     * JDK 1.3.1 FCS did not include a version byte at the end of
     * its object keys.  JDK 1.3.1_01 included the byte with the
     * value 1.  Anything below 1 is considered an invalid value.
     * <p>
     *  JDK 1.3.1 FCS在其对象键的末尾不包含版本字节。 JDK 1.3.1_01包括具有值1的字节。低于1的任何内容被认为是无效值。
     * 
     */
    public static final byte NULL_PATCH_VERSION = 0;

    byte patchVersion = OldJIDLObjectKeyTemplate.NULL_PATCH_VERSION;

    public OldJIDLObjectKeyTemplate( ORB orb, int magic, int scid,
        InputStream is, OctetSeqHolder osh )
    {
        this( orb, magic, scid, is );

        osh.value = readObjectKey( is ) ;

        /**
         * Beginning with JDK 1.3.1_01, a byte was placed at the end of
         * the object key with a value indicating the patch version.
         * JDK 1.3.1_01 had the value 1.  If other patches are necessary
         * which involve ORB versioning changes, they should increment
         * the patch version.
         *
         * Note that if we see a value greater than 1 in this code, we
         * will treat it as if we're talking to the most recent ORB version.
         *
         * WARNING: This code is sensitive to changes in CDRInputStream
         * getPosition.  It assumes that the CDRInputStream is an
         * encapsulation whose position can be compared to the object
         * key array length.
         * <p>
         *  从JDK 1.3.1_01开始,一个字节被放置在对象键的末尾,并带有指示补丁版本的值。 JDK 1.3.1_01的值为1.如果需要涉及ORB版本控制更改的其他修补程序,则它们应该增加修补程序版本。
         * 
         *  注意,如果我们在这段代码中看到一个大于1的值,我们将把它看作是和最近的ORB版本交谈。
         */
        if (magic == ObjectKeyFactoryImpl.JAVAMAGIC_NEW &&
            osh.value.length > ((CDRInputStream)is).getPosition()) {

            patchVersion = is.read_octet();

            if (patchVersion == ObjectKeyFactoryImpl.JDK1_3_1_01_PATCH_LEVEL)
                setORBVersion(ORBVersionFactory.getJDK1_3_1_01());
            else if (patchVersion > ObjectKeyFactoryImpl.JDK1_3_1_01_PATCH_LEVEL)
                setORBVersion(ORBVersionFactory.getORBVersion());
            else
                throw wrapper.invalidJdk131PatchLevel( new Integer( patchVersion ) ) ;
        }
    }


    public OldJIDLObjectKeyTemplate( ORB orb, int magic, int scid, int serverid)
    {
        super( orb, magic, scid, serverid, JIDL_ORB_ID, JIDL_OAID ) ;
    }

    public OldJIDLObjectKeyTemplate(ORB orb, int magic, int scid, InputStream is)
    {
        this( orb, magic, scid, is.read_long() ) ;
    }

    protected void writeTemplate( OutputStream os )
    {
        os.write_long( getMagic() ) ;
        os.write_long( getSubcontractId() ) ;
        os.write_long( getServerId() ) ;
    }

    public void write(ObjectId objectId, OutputStream os)
    {
        super.write(objectId, os);

        if (patchVersion != OldJIDLObjectKeyTemplate.NULL_PATCH_VERSION)
           os.write_octet( patchVersion ) ;
    }
}
