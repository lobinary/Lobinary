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

package com.sun.corba.se.spi.orb ;

import com.sun.corba.se.spi.orb.ORBVersion ;
import com.sun.corba.se.impl.orb.ORBVersionImpl ;
import org.omg.CORBA.portable.InputStream ;
import org.omg.CORBA.INTERNAL ;

public class ORBVersionFactory {
    private ORBVersionFactory() {} ;

    public static ORBVersion getFOREIGN()
    {
        return ORBVersionImpl.FOREIGN ;
    }

    public static ORBVersion getOLD()
    {
        return ORBVersionImpl.OLD ;
    }

    public static ORBVersion getNEW()
    {
        return ORBVersionImpl.NEW ;
    }

    public static ORBVersion getJDK1_3_1_01()
    {
        return ORBVersionImpl.JDK1_3_1_01 ;
    }

    public static ORBVersion getNEWER()
    {
        return ORBVersionImpl.NEWER ;
    }

    public static ORBVersion getPEORB()
    {
        return ORBVersionImpl.PEORB ;
    }

    /** Return the current version of this ORB
    /* <p>
     */
    public static ORBVersion getORBVersion()
    {
        return ORBVersionImpl.PEORB ;
    }

    public static ORBVersion create( InputStream is )
    {
        byte value = is.read_octet() ;
        return byteToVersion( value ) ;
    }

    private static ORBVersion byteToVersion( byte value )
    {
        /* Throwing an exception here would cause this version to be
        * incompatible with future versions of the ORB, to the point
        * that this version could
        * not even unmarshal objrefs from a newer version that uses
        * extended versioning.  Therefore, we will simply treat all
        * unknown versions as the latest version.
        if (value < 0)
            throw new INTERNAL() ;
        * <p>
        *  与ORB的未来版本不兼容,以至于该版本甚至不能从使用扩展版本控制的较新版本解组objrefs。因此,我们将只处理所有未知版本作为最新版本。
        *  if(value <0)throw new INTERNAL();。
        * 
        */

        /**
         * Update: If we treat all unknown versions as the latest version
         * then when we send an IOR with a PEORB version to an ORB that
         * doesn't know the PEORB version it will treat it as whatever
         * its idea of the latest version is.  Then, if that IOR is
         * sent back to the server and compared with the original
         * the equality check will fail because the versions will be
         * different.
         *
         * Instead, just capture the version bytes.
         * <p>
         *  更新：如果我们将所有未知版本作为最新版本,那么当我们发送带有PEORB版本的IOR到不知道PEORB版本的ORB时,它会将其视为最新版本的想法。
         * 然后,如果该IOR被发送回服务器并与原始比较,则等式检查将失败,因为版本将不同。
         * 
         */

        switch (value) {
            case ORBVersion.FOREIGN : return ORBVersionImpl.FOREIGN ;
            case ORBVersion.OLD : return ORBVersionImpl.OLD ;
            case ORBVersion.NEW : return ORBVersionImpl.NEW ;
            case ORBVersion.JDK1_3_1_01: return ORBVersionImpl.JDK1_3_1_01 ;
            case ORBVersion.NEWER : return ORBVersionImpl.NEWER ;
            case ORBVersion.PEORB : return ORBVersionImpl.PEORB ;
            default : return new ORBVersionImpl(value);
        }
    }
}
