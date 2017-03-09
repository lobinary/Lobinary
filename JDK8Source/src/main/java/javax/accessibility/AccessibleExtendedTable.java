/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
  * Class AccessibleExtendedTable provides extended information about
  * a user-interface component that presents data in a two-dimensional
  * table format.
  * Applications can determine if an object supports the
  * AccessibleExtendedTable interface by first obtaining its
  * AccessibleContext and then calling the
  * {@link AccessibleContext#getAccessibleTable} method.
  * If the return value is not null and the type of the return value is
  * AccessibleExtendedTable, the object supports this interface.
  *
  * <p>
  *  AccessibleExtendedTable类提供关于以二维表格式呈现数据的用户界面组件的扩展信息。
  * 应用程序可以通过首先获取其AccessibleContext然后调用{@link AccessibleContext#getAccessibleTable}方法来确定对象是否支持AccessibleEx
  * tendedTable接口。
  *  AccessibleExtendedTable类提供关于以二维表格式呈现数据的用户界面组件的扩展信息。
  * 如果返回值不为null并且返回值的类型为AccessibleExtendedTable,则对象支持此接口。
  * 
  * 
  * @author      Lynn Monsanto
  * @since 1.4
  */
public interface AccessibleExtendedTable extends AccessibleTable {

     /**
      * Returns the row number of an index in the table.
      *
      * <p>
      *  返回表中索引的行号。
      * 
      * 
      * @param index the zero-based index in the table.  The index is
      * the table cell offset from row == 0 and column == 0.
      * @return the zero-based row of the table if one exists;
      * otherwise -1.
      */
     public int getAccessibleRow(int index);

     /**
      * Returns the column number of an index in the table.
      *
      * <p>
      *  返回表中索引的列号。
      * 
      * 
      * @param index the zero-based index in the table.  The index is
      * the table cell offset from row == 0 and column == 0.
      * @return the zero-based column of the table if one exists;
      * otherwise -1.
      */
     public int getAccessibleColumn(int index);

    /**
      * Returns the index at a row and column in the table.
      *
      * <p>
      *  返回表中行和列的索引。
      * 
      * @param r zero-based row of the table
      * @param c zero-based column of the table
      * @return the zero-based index in the table if one exists;
      * otherwise -1.  The index is  the table cell offset from
      * row == 0 and column == 0.
      */
     public int getAccessibleIndex(int r, int c);
}
