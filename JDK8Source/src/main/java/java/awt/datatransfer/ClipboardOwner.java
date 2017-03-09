/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2002, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.datatransfer;

/**
 * Defines the interface for classes that will provide data to
 * a clipboard. An instance of this interface becomes the owner
 * of the contents of a clipboard (clipboard owner) if it is
 * passed as an argument to
 * {@link java.awt.datatransfer.Clipboard#setContents} method of
 * the clipboard and this method returns successfully.
 * The instance remains the clipboard owner until another application
 * or another object within this application asserts ownership
 * of this clipboard.
 *
 * <p>
 *  定义将为剪贴板提供数据的类的接口。
 * 如果将此接口的实例作为参数传递到剪贴板的{@link java.awt.datatransfer.Clipboard#setContents}方法,并且此方法成功返回,此接口的实例将成为剪贴板(剪贴板所
 * 有者)内容的所有者。
 *  定义将为剪贴板提供数据的类的接口。实例保留剪贴板所有者,直到此应用程序中的另一个应用程序或另一个对象声明该剪贴板的所有权。
 * 
 * @see java.awt.datatransfer.Clipboard
 *
 * @author      Amy Fowler
 */

public interface ClipboardOwner {

    /**
     * Notifies this object that it is no longer the clipboard owner.
     * This method will be called when another application or another
     * object within this application asserts ownership of the clipboard.
     *
     * <p>
     * 
     * 
     * @param clipboard the clipboard that is no longer owned
     * @param contents the contents which this owner had placed on the clipboard
     */
    public void lostOwnership(Clipboard clipboard, Transferable contents);

}
