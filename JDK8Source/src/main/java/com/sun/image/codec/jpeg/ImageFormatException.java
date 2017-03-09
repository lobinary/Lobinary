/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/* ********************************************************************
 **********************************************************************
 **********************************************************************
 *** COPYRIGHT (c) Eastman Kodak Company, 1997                      ***
 *** As  an unpublished  work pursuant to Title 17 of the United    ***
 *** States Code.  All rights reserved.                             ***
 **********************************************************************
 **********************************************************************
 * <p>
 *  **************************************************** ****************** ****************************
 * **** ************************************ * COPYRIGHT(c)Eastman Kodak Company,1997 *** *根据United *** 
 * *国家法典第17章的未公布的工作。
 * 版权所有。
 *  *** *********************************************** ********************* **************************
 * *** ***************************************。
 * 版权所有。
 * 
 * 
 **********************************************************************/

package com.sun.image.codec.jpeg;

/**
 * Signals that an Image Format exception of some sort has occurred.
 * <p>
 * Note that the classes in the com.sun.image.codec.jpeg package are not
 * part of the core Java APIs.  They are a part of Sun's JDK and JRE
 * distributions.  Although other licensees may choose to distribute these
 * classes, developers cannot depend on their availability in non-Sun
 * implementations.  We expect that equivalent functionality will eventually
 * be available in a core API or standard extension.
 * <p>
 *
 * <p>
 *  表示发生了某种类型的图像格式异常。
 * <p>
 *  请注意,com.sun.image.codec.jpeg包中的类不是核心Java API的一部分。它们是Sun的JDK和JRE发行版的一部分。
 * 虽然其他许可证持有者可能选择分发这些类,但开发人员不能依赖其在非Sun实施中的可用性。我们期望等效功能最终将在核心API或标准扩展中可用。
 * <p>
 * 
 * 
 * @author  Tom Sausner
 * @see     JPEGImageEncoder
 * @see     JPEGImageDecoder
 * @since   1.2
 */
public
class ImageFormatException extends RuntimeException {
    /**
     * Constructs an <code>ImageFormatException</code> with no detail message.
     * <p>
     */
    public ImageFormatException() {
        super();
    }

    /**
     * Constructs an <code>ImageFormatException</code> with the specified
     * detailed message.
     *
     * <p>
     *  构造一个没有详细消息的<code> ImageFormatException </code>。
     * 
     * 
     * @param   s   the message.
     */
    public ImageFormatException(String s) {
        super(s);
    }
}
