/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;

import java.io.InputStream;
import java.awt.datatransfer.DataFlavor;

/**
 *
 * The <code>AccessibleStreamable</code> interface should be implemented
 * by the <code>AccessibleContext</code> of any component that presents the
 * raw stream behind a component on the display screen.  Examples of such
 * components are HTML, bitmap images and MathML.  An object that implements
 * <code>AccessibleStreamable</code> provides two things: a list of MIME
 * types supported by the object and a streaming interface for each MIME type to
 * get the data.
 *
 * <p>
 *  <code> AccessibleStreamable </code>接口应该由在显示屏幕上呈现组件后面的原始流的任何组件的<code> AccessibleContext </code>实现。
 * 这样的组件的示例是HTML,位图图像和MathML。
 * 实现<code> AccessibleStreamable </code>的对象提供两件事：对象支持的MIME类型列表和每个MIME类型的流接口以获取数据。
 * 
 * 
 * @author Lynn Monsanto
 * @author Peter Korn
 *
 * @see javax.accessibility.AccessibleContext
 * @since 1.5
 */
public interface AccessibleStreamable {
    /**
      * Returns an array of DataFlavor objects for the MIME types
      * this object supports.
      *
      * <p>
      *  返回此对象支持的MIME类型的DataFlavor对象的数组。
      * 
      * 
      * @return an array of DataFlavor objects for the MIME types
      * this object supports.
      */
     DataFlavor[] getMimeTypes();

    /**
      * Returns an InputStream for a DataFlavor
      *
      * <p>
      *  返回DataFlavor的InputStream
      * 
      * @param flavor the DataFlavor
      * @return an ImputStream if an ImputStream for this DataFlavor exists.
      * Otherwise, null is returned.
      */
     InputStream getStream(DataFlavor flavor);
}
