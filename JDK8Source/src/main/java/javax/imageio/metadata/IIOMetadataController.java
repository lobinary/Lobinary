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

package javax.imageio.metadata;

/**
 * An interface to be implemented by objects that can determine the
 * settings of an <code>IIOMetadata</code> object, either by putting
 * up a GUI to obtain values from a user, or by other means.  This
 * interface merely specifies a generic <code>activate</code> method
 * that invokes the controller, without regard for how the controller
 * obtains values (<i>i.e.</i>, whether the controller puts up a GUI
 * or merely computes a set of values is irrelevant to this
 * interface).
 *
 * <p> Within the <code>activate</code> method, a controller obtains
 * initial values by querying the <code>IIOMetadata</code> object's
 * settings, either using the XML DOM tree or a plug-in specific
 * interface, modifies values by whatever means, then modifies the
 * <code>IIOMetadata</code> object's settings, using either the
 * <code>setFromTree</code> or <code>mergeTree</code> methods, or a
 * plug-in specific interface.  In general, applications may expect
 * that when the <code>activate</code> method returns
 * <code>true</code>, the <code>IIOMetadata</code> object is ready for
 * use in a write operation.
 *
 * <p> Vendors may choose to provide GUIs for the
 * <code>IIOMetadata</code> subclasses they define for a particular
 * plug-in.  These can be set up as default controllers in the
 * corresponding <code>IIOMetadata</code> subclasses.
 *
 * <p> Alternatively, an algorithmic process such as a database lookup
 * or the parsing of a command line could be used as a controller, in
 * which case the <code>activate</code> method would simply look up or
 * compute the settings, call methods on <code>IIOMetadata</code> to
 * set its state, and return <code>true</code>.
 *
 * <p>
 *  要由可以通过放置GUI以从用户获得值或通过其他方式确定<code> IIOMetadata </code>对象的设置的对象实现的接口。
 * 该接口仅仅指定调用控制器的通用<code> activate </code>方法,而不考虑控制器如何获得值(即,<i> </i>,控制器是放置GUI还是仅计算集合的值与此接口无关)。
 * 
 *  <p>在<code> activate </code>方法中,控制器通过查询<code> IIOMetadata </code>对象的设置来获取初始值,使用XML DOM树或插件特定接口修改值通过任何
 * 方式,然后使用<code> setFromTree </code>或<code> mergeTree </code>方法或插件特定接口修改<code> IIOMetadata </code>对象的设置。
 * 通常,应用程序可能期望当<code> activate </code>方法返回<code> true </code>时,<code> IIOMetadata </code>对象准备好在写操作中使用。
 * 
 *  <p>供应商可以选择为他们为特定插件定义的<code> IIOMetadata </code>子类提供GUI。
 * 这些可以在相应的<code> IIOMetadata </code>子类中设置为默认控制器。
 * 
 * @see IIOMetadata#setController
 * @see IIOMetadata#getController
 * @see IIOMetadata#getDefaultController
 * @see IIOMetadata#hasController
 * @see IIOMetadata#activateController
 *
 */
public interface IIOMetadataController {

    /**
     * Activates the controller.  If <code>true</code> is returned,
     * all settings in the <code>IIOMetadata</code> object should be
     * ready for use in a write operation.  If <code>false</code> is
     * returned, no settings in the <code>IIOMetadata</code> object
     * will be disturbed (<i>i.e.</i>, the user canceled the
     * operation).
     *
     * <p>
     * 
     * <p>或者,诸如数据库查找或命令行解析的算法过程可以用作控制器,在这种情况下,<code> activate </code>方法将简单地查找或计算设置,调用方法在<code> IIOMetadata </code>
     * 上设置其状态,并返回<code> true </code>。
     * 
     * 
     * @param metadata the <code>IIOMetadata</code> object to be modified.
     *
     * @return <code>true</code> if the <code>IIOMetadata</code> has been
     * modified, <code>false</code> otherwise.
     *
     * @exception IllegalArgumentException if <code>metadata</code> is
     * <code>null</code> or is not an instance of the correct class.
     */
    boolean activate(IIOMetadata metadata);
}
