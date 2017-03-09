/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown when code that is dependent on a keyboard, display, or mouse
 * is called in an environment that does not support a keyboard, display,
 * or mouse.
 *
 * <p>
 *  在不支持键盘,显示屏或鼠标的环境中调用依赖于键盘,显示屏或鼠标的代码时抛出。
 * 
 * 
 * @since 1.4
 * @author  Michael Martak
 */
public class HeadlessException extends UnsupportedOperationException {
    /*
     * JDK 1.4 serialVersionUID
     * <p>
     *  JDK 1.4 serialVersionUID
     */
    private static final long serialVersionUID = 167183644944358563L;
    public HeadlessException() {}
    public HeadlessException(String msg) {
        super(msg);
    }
    public String getMessage() {
        String superMessage = super.getMessage();
        String headlessMessage = GraphicsEnvironment.getHeadlessMessage();

        if (superMessage == null) {
            return headlessMessage;
        } else if (headlessMessage == null) {
            return superMessage;
        } else {
            return superMessage + headlessMessage;
        }
    }
}
