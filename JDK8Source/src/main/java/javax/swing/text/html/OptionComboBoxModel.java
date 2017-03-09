/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved.
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

import javax.swing.*;
import java.io.Serializable;


/**
 * OptionComboBoxModel extends the capabilities of the DefaultComboBoxModel,
 * to store the Option that is initially marked as selected.
 * This is stored, in order to enable an accurate reset of the
 * ComboBox that represents the SELECT form element when the
 * user requests a clear/reset.  Given that a combobox only allow
 * for one item to be selected, the last OPTION that has the
 * attribute set wins.
 *
 * <p>
 *  OptionComboBoxModel扩展了DefaultComboBoxModel的功能,以存储最初标记为选定的选项。
 * 这被存储,以便在用户请求清除/重置时能够精确地重置代表SELECT表单元素的ComboBox。假定组合框只允许选择一个项目,则最后一个具有属性集的选项wins。
 * 
 * 
  @author Sunita Mani
 */

class OptionComboBoxModel<E> extends DefaultComboBoxModel<E> implements Serializable {

    private Option selectedOption = null;

    /**
     * Stores the Option that has been marked its
     * selected attribute set.
     * <p>
     *  存储已标记其所选属性集的选项。
     * 
     */
    public void setInitialSelection(Option option) {
        selectedOption = option;
    }

    /**
     * Fetches the Option item that represents that was
     * initially set to a selected state.
     * <p>
     *  获取表示最初设置为选定状态的选项项目。
     */
    public Option getInitialSelection() {
        return selectedOption;
    }
}
