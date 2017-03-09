/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1998, Oracle and/or its affiliates. All rights reserved.
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

package java.beans;

/**
 * Under some circumstances a bean may be run on servers where a GUI
 * is not available.  This interface can be used to query a bean to
 * determine whether it absolutely needs a gui, and to advise the
 * bean whether a GUI is available.
 * <p>
 * This interface is for expert developers, and is not needed
 * for normal simple beans.  To avoid confusing end-users we
 * avoid using getXXX setXXX design patterns for these methods.
 * <p>
 *  在某些情况下,bean可能在GUI不可用的服务器上运行。此接口可用于查询bean以确定它是否绝对需要gui,并建议bean是否可用GUI。
 * <p>
 *  此接口适用于专业开发人员,并且对于正常的简单Bean不需要。为了避免混淆最终用户,我们避免对这些方法使用getXXX setXXX设计模式。
 * 
 */

public interface Visibility {

    /**
     * Determines whether this bean needs a GUI.
     *
     * <p>
     *  确定此bean是否需要GUI。
     * 
     * 
     * @return True if the bean absolutely needs a GUI available in
     *          order to get its work done.
     */
    boolean needsGui();

    /**
     * This method instructs the bean that it should not use the Gui.
     * <p>
     *  这个方法指示bean不应该使用Gui。
     * 
     */
    void dontUseGui();

    /**
     * This method instructs the bean that it is OK to use the Gui.
     * <p>
     *  这个方法指示bean可以使用Gui。
     * 
     */
    void okToUseGui();

    /**
     * Determines whether this bean is avoiding using a GUI.
     *
     * <p>
     *  确定此bean是否正在使用GUI。
     * 
     * @return true if the bean is currently avoiding use of the Gui.
     *   e.g. due to a call on dontUseGui().
     */
    boolean avoidingGui();

}
