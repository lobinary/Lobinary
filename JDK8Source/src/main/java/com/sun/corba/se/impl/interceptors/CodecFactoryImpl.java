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

package com.sun.corba.se.impl.interceptors;

import org.omg.IOP.Codec;
import org.omg.IOP.CodecFactory;
import org.omg.IOP.CodecFactoryPackage.UnknownEncoding;
import org.omg.IOP.Encoding;
import org.omg.IOP.ENCODING_CDR_ENCAPS;

import com.sun.corba.se.spi.logging.CORBALogDomains;

import com.sun.corba.se.impl.logging.ORBUtilSystemException;

import org.omg.CORBA.ORB;
import org.omg.CORBA.LocalObject;

/**
 * CodecFactoryImpl is the implementation of the Codec Factory, as described
 * in orbos/99-12-02.
 * <p>
 *  CodecFactoryImpl是Codec Factory的实现,如orbos / 99-12-02中所述。
 * 
 */
public final class CodecFactoryImpl
    extends org.omg.CORBA.LocalObject
    implements CodecFactory
{
    // The ORB that created this Codec Factory
    private ORB orb;
    private ORBUtilSystemException wrapper ;

    // The maximum minor version of GIOP supported by this codec factory.
    // Currently, this is 1.2.
    private static final int MAX_MINOR_VERSION_SUPPORTED = 2;

    // The pre-created minor versions of Codec version 1.0, 1.1, ...,
    // 1.(MAX_MINOR_VERSION_SUPPORTED)
    private Codec codecs[] = new Codec[MAX_MINOR_VERSION_SUPPORTED + 1];

    /**
     * Creates a new CodecFactory implementation.  Stores the ORB that
     * created this factory, for later use by the Codec.
     * <p>
     *  创建新的CodecFactory实现。存储创建此工厂的ORB,以供以后由Codec使用。
     * 
     */
    public CodecFactoryImpl( ORB orb ) {
        this.orb = orb;
        wrapper = ORBUtilSystemException.get(
            (com.sun.corba.se.spi.orb.ORB)orb,
            CORBALogDomains.RPC_PROTOCOL ) ;

        // Precreate a codec for version 1.0 through
        // 1.(MAX_MINOR_VERSION_SUPPORTED).  This can be
        // done since Codecs are immutable in their current implementation.
        // This is an optimization that eliminates the overhead of creating
        // a new Codec each time create_codec is called.
        for( int minor = 0; minor <= MAX_MINOR_VERSION_SUPPORTED; minor++ ) {
            codecs[minor] = new CDREncapsCodec( orb, 1, minor );
        }
    }

    /**
     * Creates a codec of the given encoding.  The only format recognized
     * by this factory is ENCODING_CDR_ENCAPS, versions 1.0 through
     * 1.(MAX_MINOR_VERSION_SUPPORTED).
     *
     * <p>
     *  创建给定编码的编解码器。该工厂识别的唯一格式是ENCODING_CDR_ENCAPS,版本1.0到1.(MAX_MINOR_VERSION_SUPPORTED)。
     * 
     * 
     * @exception UnknownEncoding Thrown if this factory cannot create a
     *   Codec of the given encoding.
     */
    public Codec create_codec ( Encoding enc )
        throws UnknownEncoding
    {
        if( enc == null ) nullParam();

        Codec result = null;

        // This is the only format we can currently create codecs for:
        if( (enc.format == ENCODING_CDR_ENCAPS.value) &&
            (enc.major_version == 1) )
        {
            if( (enc.minor_version >= 0) &&
                (enc.minor_version <= MAX_MINOR_VERSION_SUPPORTED) )
            {
                result = codecs[enc.minor_version];
            }
        }

        if( result == null ) {
            throw new UnknownEncoding();
        }

        return result;
    }

    /**
     * Called when an invalid null parameter was passed.  Throws a
     * BAD_PARAM with a minor code of 1
     * <p>
     *  当传递无效的空参数时调用。投掷BAD_PARAM,次要代码为1
     */
    private void nullParam()
    {
        throw wrapper.nullParam() ;
    }
}
