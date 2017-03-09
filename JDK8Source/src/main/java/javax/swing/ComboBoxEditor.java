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
package javax.swing;

import java.awt.*;
import java.awt.event.*;

/**
 * The editor component used for JComboBox components.
 *
 * <p>
 *  用于JComboBox组件的编辑器组件。
 * 
 * 
 * @author Arnaud Weber
 */
public interface ComboBoxEditor {

  /** Return the component that should be added to the tree hierarchy for
    * this editor
    * <p>
    *  这个编辑
    */
  public Component getEditorComponent();

  /** Set the item that should be edited. Cancel any editing if necessary **/
  public void setItem(Object anObject);

  /** Return the edited item **/
  public Object getItem();

  /** Ask the editor to start editing and to select everything **/
  public void selectAll();

  /** Add an ActionListener. An action event is generated when the edited item changes **/
  public void addActionListener(ActionListener l);

  /** Remove an ActionListener **/
  public void removeActionListener(ActionListener l);
}
