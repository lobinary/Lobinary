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

package com.sun.corba.se.impl.naming.namingutil;

import java.io.StringWriter;

import org.omg.CORBA.DATA_CONVERSION;
import org.omg.CORBA.CompletionStatus;

import com.sun.corba.se.impl.logging.NamingSystemException;
import com.sun.corba.se.spi.logging.CORBALogDomains;

/**
 *  Utility methods for Naming.
 *
 *  @Author Hemanth
 * <p>
 *  命名的实用方法。
 * 
 *  @作者Hemanth
 * 
 */
class Utility {
    private static NamingSystemException wrapper =
        NamingSystemException.get( CORBALogDomains.NAMING ) ;

    /**
     * cleanEscapes removes URL escapes as per IETF 2386 RFP.
     * <p>
     *  cleanEscapes根据IETF 2386 RFP删除URL转义。
     * 
     */
    static String cleanEscapes( String stringToDecode ) {
        StringWriter theStringWithoutEscape = new StringWriter();
        for( int i = 0; i < stringToDecode.length(); i++ ) {
            char c = stringToDecode.charAt( i ) ;
            if( c != '%' ) {
                theStringWithoutEscape.write( c );
            } else {
                // Get the two hexadecimal digits and convert that into int
                i++;
                int Hex1 = hexOf( stringToDecode.charAt(i) );
                i++;
                int Hex2 = hexOf( stringToDecode.charAt(i) );
                int value = (Hex1 * 16) + Hex2;
                // Convert the integer to ASCII
                theStringWithoutEscape.write( (char) value );
            }
        }
        return theStringWithoutEscape.toString();
    }

    /**
     *  Converts an Ascii Character into Hexadecimal digit
     *  NOTE: THIS METHOD IS DUPLICATED TO DELIVER NAMING AS A SEPARATE
     *  COMPONENT TO RI.
     * <p>
     *  将ASCII字符转换为十六进制数字注意：此方法被重复提供作为一个独立的组件到RI的命名。
     * 
     * 
     **/
    static int hexOf( char x )
    {
        int val;

        val = x - '0';
        if (val >=0 && val <= 9)
            return val;

        val = (x - 'a') + 10;
        if (val >= 10 && val <= 15)
            return val;

        val = (x - 'A') + 10;
        if (val >= 10 && val <= 15)
            return val;

        throw new DATA_CONVERSION( );
    }

    /**
     * If GIOP Version is not correct, This method throws a BAD_PARAM
     * Exception.
     * <p>
     *  如果GIOP版本不正确,此方法会抛出BAD_PARAM异常。
     * 
     **/
    static void validateGIOPVersion( IIOPEndpointInfo endpointInfo ) {
        if ((endpointInfo.getMajor() > NamingConstants.MAJORNUMBER_SUPPORTED) ||
            (endpointInfo.getMinor() > NamingConstants.MINORNUMBERMAX ) )
        {
            throw wrapper.insBadAddress() ;
        }
    }
}
