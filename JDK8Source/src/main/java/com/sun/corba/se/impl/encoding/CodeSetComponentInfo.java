/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
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
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性Copyright IBM Corp. 1998 1999保留所有权利
 * 
 */

package com.sun.corba.se.impl.encoding;

import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.CompletionStatus;

import com.sun.corba.se.spi.logging.CORBALogDomains;
import com.sun.corba.se.impl.logging.ORBUtilSystemException;

public final class CodeSetComponentInfo {

    /**
     * CodeSetComponent is part of an IOR multi-component profile.  Two
     * instances constitute a CodeSetComponentInfo (one for char and one
     * for wchar data)
     * <p>
     *  CodeSetComponent是IOR多组件配置文件的一部分。两个实例构成一个CodeSetComponentInfo(一个用于char,一个用于wchar数据)
     * 
     */
    public static final class CodeSetComponent {
        int nativeCodeSet;
        int[] conversionCodeSets;

        public boolean equals( Object obj )
        {
            if (this == obj)
                return true ;

            if (!(obj instanceof CodeSetComponent))
                return false ;

            CodeSetComponent other = (CodeSetComponent)obj ;

            return (nativeCodeSet == other.nativeCodeSet) &&
                Arrays.equals( conversionCodeSets, other.conversionCodeSets ) ;
        }

        public int hashCode()
        {
            int result = nativeCodeSet ;
            for (int ctr=0; ctr<conversionCodeSets.length; ctr++)
                result = 37*result + conversionCodeSets[ctr] ;
            return result ;
        }

        public CodeSetComponent() {}

        public CodeSetComponent(int nativeCodeSet, int[] conversionCodeSets) {
            this.nativeCodeSet = nativeCodeSet;
            if (conversionCodeSets == null)
                this.conversionCodeSets = new int[0];
            else
                this.conversionCodeSets = conversionCodeSets;
        }

        public void read(MarshalInputStream in) {
            nativeCodeSet = in.read_ulong();
            int len = in.read_long();
            conversionCodeSets = new int[len];
            in.read_ulong_array(conversionCodeSets, 0, len);

        }

        public void write(MarshalOutputStream out) {
            out.write_ulong(nativeCodeSet);
            out.write_long(conversionCodeSets.length);
            out.write_ulong_array(conversionCodeSets, 0, conversionCodeSets.length);
        }

        public String toString() {
            StringBuffer sbuf = new StringBuffer("CodeSetComponent(");

            sbuf.append("native:");
            sbuf.append(Integer.toHexString(nativeCodeSet));
            sbuf.append(" conversion:");
            if (conversionCodeSets == null)
                sbuf.append("null");
            else {
                for (int i = 0; i < conversionCodeSets.length; i++) {
                    sbuf.append(Integer.toHexString(conversionCodeSets[i]));
                    sbuf.append(' ');
                }
            }
            sbuf.append( ")" ) ;

            return sbuf.toString();
        }
    }

    private CodeSetComponent forCharData;
    private CodeSetComponent forWCharData;

    public boolean equals( Object obj )
    {
        if (this == obj)
            return true ;

        if (!(obj instanceof CodeSetComponentInfo))
            return false ;

        CodeSetComponentInfo other = (CodeSetComponentInfo)obj ;
        return forCharData.equals( other.forCharData ) &&
            forWCharData.equals( other.forWCharData ) ;
    }

    public int hashCode()
    {
        return forCharData.hashCode() ^ forWCharData.hashCode() ;
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer("CodeSetComponentInfo(");

        sbuf.append("char_data:");
        sbuf.append(forCharData.toString());
        sbuf.append(" wchar_data:");
        sbuf.append(forWCharData.toString());
        sbuf.append(")");

        return sbuf.toString();
    }

    public CodeSetComponentInfo() {
        forCharData = CodeSetComponentInfo.JAVASOFT_DEFAULT_CODESETS.forCharData;
        forWCharData = CodeSetComponentInfo.JAVASOFT_DEFAULT_CODESETS.forWCharData;
    }

    public CodeSetComponentInfo(CodeSetComponent charData,
                                CodeSetComponent wcharData) {
        forCharData = charData;
        forWCharData = wcharData;
    }

    public void read(MarshalInputStream in) {
        forCharData = new CodeSetComponent();
        forCharData.read(in);
        forWCharData = new CodeSetComponent();
        forWCharData.read(in);
    }

    public void write(MarshalOutputStream out) {
        forCharData.write(out);
        forWCharData.write(out);
    }

    public CodeSetComponent getCharComponent() {
        return forCharData;
    }

    public CodeSetComponent getWCharComponent() {
        return forWCharData;
    }

    /**
     * CodeSetContext goes in a GIOP service context
     * <p>
     *  CodeSetContext进入GIOP服务上下文
     * 
     */
    public static final class CodeSetContext {
        private int char_data;
        private int wchar_data;

        public CodeSetContext() {}

        public CodeSetContext(int charEncoding, int wcharEncoding) {
            char_data = charEncoding;
            wchar_data = wcharEncoding;
        }

        public void read(MarshalInputStream in) {
            char_data = in.read_ulong();
            wchar_data = in.read_ulong();
        }

        public void write(MarshalOutputStream out) {
            out.write_ulong(char_data);
            out.write_ulong(wchar_data);
        }

        public int getCharCodeSet() {
            return char_data;
        }

        public int getWCharCodeSet() {
            return wchar_data;
        }

        public String toString() {
            StringBuffer sbuf = new StringBuffer();
            sbuf.append("CodeSetContext char set: ");
            sbuf.append(Integer.toHexString(char_data));
            sbuf.append(" wchar set: ");
            sbuf.append(Integer.toHexString(wchar_data));
            return sbuf.toString();
        }
    }

    /**
     * Our default code set scheme is as follows:
     *
     * char data:
     *
     * Native code set:  ISO 8859-1 (8-bit)
     * Conversion sets:  UTF-8, ISO 646 (7-bit)
     *
     * wchar data:
     *
     * Native code set:  UTF-16
     * Conversion sets:  UCS-2
     *
     * Pre-Merlin/J2EE 1.3 JavaSoft ORBs listed ISO646 for char and
     * UCS-2 for wchar, and provided no conversion sets.  They also
     * didn't do correct negotiation or provide the fallback sets.
     * UCS-2 is still in the conversion list for backwards compatibility.
     *
     * The fallbacks are UTF-8 for char and UTF-16 for wchar.
     *
     * In GIOP 1.1, interoperability with wchar is limited to 2 byte fixed
     * width encodings since its wchars aren't preceded by a length.
     * Thus, I've chosen not to include UTF-8 in the conversion set
     * for wchar data.
     *
     * <p>
     *  我们的默认代码集方案如下：
     * 
     *  字符数据：
     * 
     *  本地代码集：ISO 8859-1(8位)转换集：UTF-8,ISO 646(7位)
     * 
     *  wchar数据：
     * 
     *  本地代码集：UTF-16转换集：UCS-2
     * 
     *  Pre-Merlin / J2EE 1.3 JavaSoft ORB列出了针对char的ISO646和针对wchar的UCS-2,并且未提供转换集。他们也没有做正确的谈判或提供回退集。
     *  UCS-2仍然在转换列表中,用于向后兼容。
     * 
     *  备用字符为UTF-8用于char,UTF-16用于wchar。
     */
    public static final CodeSetComponentInfo JAVASOFT_DEFAULT_CODESETS;
    static {
        CodeSetComponent charData
            = new CodeSetComponent(OSFCodeSetRegistry.ISO_8859_1.getNumber(),
                                   new int[] {
                                       OSFCodeSetRegistry.UTF_8.getNumber(),
                                       OSFCodeSetRegistry.ISO_646.getNumber()
                                   });

        CodeSetComponent wcharData
            = new CodeSetComponent(OSFCodeSetRegistry.UTF_16.getNumber(),
                                   new int[]
                                   {
                                       OSFCodeSetRegistry.UCS_2.getNumber()
                                   });

        JAVASOFT_DEFAULT_CODESETS = new CodeSetComponentInfo(charData, wcharData);
    }

    /**
     * Creates a CodeSetComponent from a String which contains a comma
     * delimited list of OSF Code Set Registry numbers.  An INITIALIZE
     * exception is thrown if any of the numbers are not known by our
     * registry.  Used by corba.ORB init.
     *
     * The first number in the list is taken as the native code set,
     * and the rest is the conversion code set list.
     *
     * The numbers can either be decimal or hex.
     * <p>
     * 
     *  在GIOP 1.1中,与wchar的互操作性限于2字节固定宽度编码,因为它的wchar之前没有长度。因此,我选择不在UTF-8的转换集中为wchar数据。
     * 
     */
    public static CodeSetComponent createFromString(String str) {
        ORBUtilSystemException wrapper = ORBUtilSystemException.get(
            CORBALogDomains.RPC_ENCODING ) ;

        if (str == null || str.length() == 0)
            throw wrapper.badCodeSetString() ;

        StringTokenizer stok = new StringTokenizer(str, ", ", false);
        int nativeSet = 0;
        int conversionInts[] = null;

        try {

            // The first value is the native code set
            nativeSet = Integer.decode(stok.nextToken()).intValue();

            if (OSFCodeSetRegistry.lookupEntry(nativeSet) == null)
                throw wrapper.unknownNativeCodeset( new Integer(nativeSet) ) ;

            List conversionList = new ArrayList(10);

            // Now process the other values as part of the
            // conversion code set list.
            while (stok.hasMoreTokens()) {

                // decode allows us to specify hex, decimal, etc
                Integer value = Integer.decode(stok.nextToken());

                if (OSFCodeSetRegistry.lookupEntry(value.intValue()) == null)
                    throw wrapper.unknownConversionCodeSet( value ) ;

                conversionList.add(value);
            }

            conversionInts = new int[conversionList.size()];

            for (int i = 0; i < conversionInts.length; i++)
                conversionInts[i] = ((Integer)conversionList.get(i)).intValue();

        } catch (NumberFormatException nfe) {
            throw wrapper.invalidCodeSetNumber( nfe ) ;
        } catch (NoSuchElementException nsee) {
            throw wrapper.invalidCodeSetString( nsee, str ) ;
        }

        // Otherwise return the CodeSetComponent representing
        // the given values
        return new CodeSetComponent(nativeSet, conversionInts);
    }

    /**
     * Code sets for local cases without a connection.
     * <p>
     *  从包含逗号分隔的OSF代码集注册表号列表的字符串中创建一个CodeSetComponent。如果任何数字不为我们的注册表所知,则抛出INITIALIZE异常。由corba.ORB init使用。
     * 
     *  列表中的第一个数字被视为本机代码集,其余的是转换代码集列表。
     * 
     * 数字可以是十进制或十六进制。
     */
    public static final CodeSetContext LOCAL_CODE_SETS
        = new CodeSetContext(OSFCodeSetRegistry.ISO_8859_1.getNumber(),
                             OSFCodeSetRegistry.UTF_16.getNumber());
}
