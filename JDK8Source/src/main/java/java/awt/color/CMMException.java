/***** Lobxxx Translate Finished ******/
/*
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
    Created by gbp, October 25, 1997

 *
 * <p>
 *  创建于gbp,1997年10月25日
 * 
 */
/*
 **********************************************************************
 **********************************************************************
 **********************************************************************
 *** COPYRIGHT (c) Eastman Kodak Company, 1997                      ***
 *** As  an unpublished  work pursuant to Title 17 of the United    ***
 *** States Code.  All rights reserved.                             ***
 **********************************************************************
 **********************************************************************
 * <p>
 *  **************************************************** ****************** ****************************
 * **** ************************************ ************** ********************************************
 * ******** **** * COPYRIGHT(c)Eastman Kodak Company,1997 *** *根据United *** *国家法典第17章的未发表的作品。
 * 版权所有。
 *  *** *********************************************** ********************* **************************
 * *** ***************************************。
 * 版权所有。
 * 
 **********************************************************************/


package java.awt.color;


/**
 * This exception is thrown if the native CMM returns an error.
 * <p>
 * 
 */

public class CMMException extends java.lang.RuntimeException {

    /**
     *  Constructs a CMMException with the specified detail message.
     * <p>
     *  如果本机CMM返回错误,则抛出此异常。
     * 
     * 
     *  @param s the specified detail message
     */
    public CMMException (String s) {
        super (s);
    }
}
