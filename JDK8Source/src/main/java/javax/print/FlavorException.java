/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

import javax.print.DocFlavor;

/**
 * Interface FlavorException is a mixin interface which a subclass of {@link
 * PrintException PrintException} can implement to report an error condition
 * involving a doc flavor or flavors (class {@link javax.print.DocFlavor
 * DocFlavor}). The Print Service API does not define any print exception
 * classes that implement interface FlavorException, that being left to the
 * Print Service implementor's discretion.
 *
 * <p>
 *  Interface FlavorException是一个mixin接口,{@link PrintException PrintException}的子类可以实现来报告涉及doc flavor或flav
 * ors(class {@link javax.print.DocFlavor DocFlavor})的错误条件。
 * 打印服务API不定义任何实现接口FlavorException的打印异常类,由打印服务实施者自行决定。
 * 
 */
public interface FlavorException {

    /**
     * Returns the unsupported flavors.
     * <p>
     * 
     * @return the unsupported doc flavors.
     */
    public DocFlavor[] getUnsupportedFlavors();

}
