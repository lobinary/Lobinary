/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

/**
 * Thrown by method createFont in the <code>Font</code> class to indicate
 * that the specified font is bad.
 *
 * <p>
 *  在<code> Font </code>类中通过方法createFont抛出,以指示指定的字体是错误的。
 * 
 * 
 * @author  Parry Kejriwal
 * @see     java.awt.Font
 * @since   1.3
 */
public
class FontFormatException extends Exception {
    /*
     * serialVersionUID
     * <p>
     *  serialVersionUID
     * 
     */
    private static final long serialVersionUID = -4481290147811361272L;

    /**
     * Report a FontFormatException for the reason specified.
     * <p>
     *  报告指定的原因的FontFormatException。
     * 
     * @param reason a <code>String</code> message indicating why
     *        the font is not accepted.
     */
    public FontFormatException(String reason) {
      super (reason);
    }
}
