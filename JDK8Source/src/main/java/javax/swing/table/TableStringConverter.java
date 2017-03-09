/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.table;

/**
 * TableStringConverter is used to convert objects from the model into
 * strings.  This is useful in filtering and searching when the model returns
 * objects that do not have meaningful <code>toString</code> implementations.
 *
 * <p>
 *  TableStringConverter用于将对象从模型转换为字符串。当模型返回没有有意义的<code> toString </code>实现的对象时,这在过滤和搜索中非常有用。
 * 
 * 
 * @since 1.6
 */
public abstract class TableStringConverter {
    /**
     * Returns the string representation of the value at the specified
     * location.
     *
     * <p>
     *  返回指定位置处的值的字符串表示形式。
     * 
     * @param model the <code>TableModel</code> to fetch the value from
     * @param row the row the string is being requested for
     * @param column the column the string is being requested for
     * @return the string representation.  This should never return null.
     * @throws NullPointerException if <code>model</code> is null
     * @throws IndexOutOfBoundsException if the arguments are outside the
     *         bounds of the model
     */
    public abstract String toString(TableModel model, int row, int column);
}
