/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text.html;

import javax.swing.text.*;


/**
 * TextAreaDocument extends the capabilities of the PlainDocument
 * to store the data that is initially set in the Document.
 * This is stored in order to enable an accurate reset of the
 * state when a reset is requested.
 *
 * <p>
 *  TextAreaDocument扩展了PlainDocument的功能,以存储最初在文档中设置的数据。这被存储以便在请求复位时实现状态的精确复位。
 * 
 * 
 * @author Sunita Mani
 */

class TextAreaDocument extends PlainDocument {

    String initialText;


    /**
     * Resets the model by removing all the data,
     * and restoring it to its initial state.
     * <p>
     *  通过删除所有数据并将其恢复到其初始状态来重置模型。
     * 
     */
    void reset() {
        try {
            remove(0, getLength());
            if (initialText != null) {
                insertString(0, initialText, null);
            }
        } catch (BadLocationException e) {
        }
    }

    /**
     * Stores the data that the model is initially
     * loaded with.
     * <p>
     *  存储模型最初加载的数据。
     */
    void storeInitialText() {
        try {
            initialText = getText(0, getLength());
        } catch (BadLocationException e) {
        }
    }
}
