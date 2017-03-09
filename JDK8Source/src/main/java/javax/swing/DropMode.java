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
package javax.swing;

/**
 * Drop modes, used to determine the method by which a component
 * tracks and indicates a drop location during drag and drop.
 *
 * <p>
 *  拖放模式,用于确定组件在拖放期间跟踪和指示拖放位置的方法。
 * 
 * 
 * @author Shannon Hickey
 * @see JTable#setDropMode
 * @see JList#setDropMode
 * @see JTree#setDropMode
 * @see javax.swing.text.JTextComponent#setDropMode
 * @since 1.6
 */
public enum DropMode {

    /**
     * A component's own internal selection mechanism (or caret for text
     * components) should be used to track the drop location.
     * <p>
     *  组件自身的内部选择机制(或文本组件的插入符号)应用于跟踪丢弃位置。
     * 
     */
    USE_SELECTION,

    /**
     * The drop location should be tracked in terms of the index of
     * existing items. Useful for dropping on items in tables, lists,
     * and trees.
     * <p>
     *  丢弃位置应该根据现有项目的索引进行跟踪。对于删除表,列表和树中的项目很有用。
     * 
     */
    ON,

    /**
     * The drop location should be tracked in terms of the position
     * where new data should be inserted. For components that manage
     * a list of items (list and tree for example), the drop location
     * should indicate the index where new data should be inserted.
     * For text components the location should represent a position
     * between characters. For components that manage tabular data
     * (table for example), the drop location should indicate
     * where to insert new rows, columns, or both, to accommodate
     * the dropped data.
     * <p>
     *  丢弃位置应根据应插入新数据的位置进行跟踪。对于管理项目列表(例如,列表和树)的组件,删除位置应指示应插入新数据的索引。对于文本组件,位置应表示字符之间的位置。
     * 对于管理表格数据的组件(例如表),删除位置应指示插入新行或列的位置,以适应已删除的数据。
     * 
     */
    INSERT,

    /**
     * The drop location should be tracked in terms of the row index
     * where new rows should be inserted to accommodate the dropped
     * data. This is useful for components that manage tabular data.
     * <p>
     *  删除位置应该根据行索引来跟踪,其中应插入新行以适应丢弃的数据。这对管理表格数据的组件很有用。
     * 
     */
    INSERT_ROWS,

    /**
     * The drop location should be tracked in terms of the column index
     * where new columns should be inserted to accommodate the dropped
     * data. This is useful for components that manage tabular data.
     * <p>
     *  丢弃位置应该根据列索引来跟踪,其中应插入新列以适应丢弃的数据。这对管理表格数据的组件很有用。
     * 
     */
    INSERT_COLS,

    /**
     * This mode is a combination of <code>ON</code>
     * and <code>INSERT</code>, specifying that data can be
     * dropped on existing items, or in insert locations
     * as specified by <code>INSERT</code>.
     * <p>
     * 此模式是<code> ON </code>和<code> INSERT </code>的组合,指定可以在现有项目上或在由<code> INSERT </code>指定的插入位置中删除数据。
     * 
     */
    ON_OR_INSERT,

    /**
     * This mode is a combination of <code>ON</code>
     * and <code>INSERT_ROWS</code>, specifying that data can be
     * dropped on existing items, or as insert rows
     * as specified by <code>INSERT_ROWS</code>.
     * <p>
     *  此模式是<code> ON </code>和<code> INSERT_ROWS </code>的组合,指定可以将数据放在现有项目上,或作为由<code> INSERT_ROWS </code>指定的
     * 插入行。
     * 
     */
    ON_OR_INSERT_ROWS,

    /**
     * This mode is a combination of <code>ON</code>
     * and <code>INSERT_COLS</code>, specifying that data can be
     * dropped on existing items, or as insert columns
     * as specified by <code>INSERT_COLS</code>.
     * <p>
     *  此模式是<code> ON </code>和<code> INSERT_COLS </code>的组合,指定可以将数据放在现有项目上,或作为<code> INSERT_COLS </code>指定的插
     * 入列。
     */
    ON_OR_INSERT_COLS
}
