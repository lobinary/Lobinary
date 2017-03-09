/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import java.awt.Container;

/**
 * A factory to create a view of some portion of document subject.
 * This is intended to enable customization of how views get
 * mapped over a document model.
 *
 * <p>
 *  一个工厂来创建文档主题的一些部分的视图。这旨在实现视图在文档模型上的映射方式的自定义。
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface ViewFactory {

    /**
     * Creates a view from the given structural element of a
     * document.
     *
     * <p>
     *  从文档的给定结构元素创建视图。
     * 
     * @param elem  the piece of the document to build a view of
     * @return the view
     * @see View
     */
    public View create(Element elem);

}
