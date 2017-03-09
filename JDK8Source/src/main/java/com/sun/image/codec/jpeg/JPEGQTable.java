/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**********************************************************************
 **********************************************************************
 **********************************************************************
 *** COPYRIGHT (c) 1997-1998 Eastman Kodak Company.                 ***
 *** As  an unpublished  work pursuant to Title 17 of the United    ***
 *** States Code.  All rights reserved.                             ***
 **********************************************************************
 **********************************************************************
 * <p>
 *  **************************************************** ****************** ****************************
 * **** ************************************ * COPYRIGHT(c)1997-1998 Eastman Kodak公司。
 *  *** *根据United *** *国家法典第17章的未发表的作品。版权所有。
 *  *** *********************************************** ********************* **************************
 * *** ***************************************。
 *  *** *根据United *** *国家法典第17章的未发表的作品。版权所有。
 * 
 * 
 **********************************************************************/

package com.sun.image.codec.jpeg;



/** Class to encapsulate the JPEG quantization tables.
 * <p>
 * Note that the classes in the com.sun.image.codec.jpeg package are not
 * part of the core Java APIs.  They are a part of Sun's JDK and JRE
 * distributions.  Although other licensees may choose to distribute these
 * classes, developers cannot depend on their availability in non-Sun
 * implementations.  We expect that equivalent functionality will eventually
 * be available in a core API or standard extension.
 * <p>
 * <p>
 * <p>
 *  请注意,com.sun.image.codec.jpeg包中的类不是核心Java API的一部分。它们是Sun的JDK和JRE发行版的一部分。
 * 虽然其他许可证持有者可能选择分发这些类,但开发人员不能依赖其在非Sun实施中的可用性。我们期望等效功能最终将在核心API或标准扩展中可用。
 * <p>
 */
public class JPEGQTable {

        /** Quantization step for each coefficient in zig-zag order */
        private int quantval[];

        /** The number of coefficients in a DCT block */
        private static final byte QTABLESIZE = 64;

        /**
         * This is the sample luminance quantization table given in the
         * JPEG spec section K.1, expressed in zigzag order. The spec says
         * that the values given produce "good" quality, and when divided
         * by 2, "very good" quality.
         * <p>
         *  这是在JPEG规格部分K.1中给出的样本亮度量化表,以之字形顺序表示。规格说,给定的值产生"好"质量,并除以2,"非常好"的质量。
         * 
         */
        public static final JPEGQTable StdLuminance = new JPEGQTable();
        static {
                int [] lumVals = {
                        16,   11,  12,  14,  12,  10,  16,  14,
                        13,   14,  18,  17,  16,  19,  24,  40,
                        26,   24,  22,  22,  24,  49,  35,  37,
                        29,   40,  58,  51,  61,  60,  57,  51,
                        56,   55,  64,  72,  92,  78,  64,  68,
                        87,   69,  55,  56,  80, 109,  81,  87,
                        95,   98, 103, 104, 103,  62,  77, 113,
                        121, 112, 100, 120,  92, 101, 103,  99
                };

                StdLuminance.quantval = lumVals;
        }

        /**
         * This is the sample luminance quantization table given in the
         * JPEG spec section K.1, expressed in zigzag order. The spec says
         * that the values given produce "good" quality, and when divided
         * by 2, "very good" quality.
         * <p>
         *  这是在JPEG规格部分K.1中给出的样本亮度量化表,以之字形顺序表示。规格说,给定的值产生"好"质量,并除以2,"非常好"的质量。
         * 
         */
        public static final JPEGQTable StdChrominance = new JPEGQTable();
        static {
                int [] chromVals = {
                        17,  18,  18,  24,  21,  24,  47,  26,
                        26,  47,  99,  66,  56,  66,  99,  99,
                        99,  99,  99,  99,  99,  99,  99,  99,
                        99,  99,  99,  99,  99,  99,  99,  99,
                        99,  99,  99,  99,  99,  99,  99,  99,
                        99,  99,  99,  99,  99,  99,  99,  99,
                        99,  99,  99,  99,  99,  99,  99,  99,
                        99,  99,  99,  99,  99,  99,  99,  99
                };
                StdChrominance.quantval = chromVals;
        }


        /**
         * Constructs an empty quantization table. This is used to create
         * the Std Q-Tables.
         * <p>
         * 构造一个空的量化表。这用于创建Std Q表。
         * 
         */
        private JPEGQTable() {
                quantval = new int[QTABLESIZE];
        }

        /**
         * Constructs an quantization table from the array that was
         * passed.  The coefficents must be in zig-zag order. The array
         * must be of length 64.
         * <p>
         *  从传递的数组构造一个量化表。系数必须是之字形顺序。该数组的长度必须为64。
         * 
         * 
         *  @param table the quantization table (this is copied).
         */
        public JPEGQTable( int table[] ) {
                if ( table.length != QTABLESIZE ) {
                        throw new IllegalArgumentException
                                ("Quantization table is the wrong size.");
                } else {
                        quantval = new int[QTABLESIZE];
                        System.arraycopy( table, 0, quantval, 0, QTABLESIZE );
                }
        }


        /**
         * Returns the current quantization table as an array of ints in
         * zig zag order.
         * <p>
         *  以zig zag顺序将当前量化表返回为int数组。
         * 
         * 
         *  @return A copy of the contained quantization table.
         */
        public int[] getTable() {
                int[] table = new int[QTABLESIZE];
                System.arraycopy( quantval, 0, table, 0, QTABLESIZE );
                return table;
        }

        /**
         * Returns a new Quantization table where the values are
         * multiplied by scaleFactor and then clamped to the range
         * 1..32767 (or to 1..255 if forceBaseline is 'true'). <P>

         * Values less than one tend to improve the quality level of the
         * table, and values greater than one degrade the quality level of
         * the table.

         * <p>
         *  返回一个新的量化表,其中值乘以scaleFactor,然后钳位到范围1..32767(或如果forceBaseline为'true',则为1..255)。 <P>
         * 
         * 
         * @param scaleFactor the multiplication factor for the table
         * @param forceBaseline if true the values will be clamped
         * to the range  [1 .. 255]
         * @return A new Q-Table that is a linear multiple of this Q-Table
         */
        public JPEGQTable getScaledInstance(float scaleFactor,
                                                                                boolean forceBaseline ) {
                long  max    = (forceBaseline)?255L:32767L;
                int []ret    = new int[QTABLESIZE];

                for (int i=0; i<QTABLESIZE; i++ ) {
                        long holder = (long)((quantval[i] * scaleFactor) + 0.5);

                        // limit to valid range
                        if (holder <= 0L) holder = 1L;

                        // Max quantizer for 12 bits
                        if (holder > max ) holder = max;

                        ret[i] = (int)holder;
                }
                return new JPEGQTable(ret);
        }
}
