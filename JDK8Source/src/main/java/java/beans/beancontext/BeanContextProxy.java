/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2002, Oracle and/or its affiliates. All rights reserved.
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

package java.beans.beancontext;

/**
 * <p>
 * This interface is implemented by a JavaBean that does
 * not directly have a BeanContext(Child) associated with
 * it (via implementing that interface or a subinterface thereof),
 * but has a public BeanContext(Child) delegated from it.
 * For example, a subclass of java.awt.Container may have a BeanContext
 * associated with it that all Component children of that Container shall
 * be contained within.
 * </p>
 * <p>
 * An Object may not implement this interface and the
 * BeanContextChild interface
 * (or any subinterfaces thereof) they are mutually exclusive.
 * </p>
 * <p>
 * Callers of this interface shall examine the return type in order to
 * obtain a particular subinterface of BeanContextChild as follows:
 * <code>
 * BeanContextChild bcc = o.getBeanContextProxy();
 *
 * if (bcc instanceof BeanContext) {
 *      // ...
 * }
 * </code>
 * or
 * <code>
 * BeanContextChild bcc = o.getBeanContextProxy();
 * BeanContext      bc  = null;
 *
 * try {
 *     bc = (BeanContext)bcc;
 * } catch (ClassCastException cce) {
 *     // cast failed, bcc is not an instanceof BeanContext
 * }
 * </code>
 * </p>
 * <p>
 * The return value is a constant for the lifetime of the implementing
 * instance
 * </p>
 * <p>
 * <p>
 *  这个接口由一个JavaBean实现,它不直接有一个BeanContext(Child)与它相关联(通过实现该接口或其子接口),但有一个从它委派的公共BeanContext(Child)。
 * 例如,java.awt.Container的子类可能有一个与它相关联的BeanContext,该Container的所有Component子节点都应包含在其中。
 * </p>
 * <p>
 *  对象可能不实现这个接口和BeanContextChild接口(或其任何子接口),它们是互斥的。
 * </p>
 * <p>
 *  此接口的调用者应检查返回类型,以获取BeanContextChild的特定子接口,如下所示：
 * <code>
 *  BeanContextChild bcc = o.getBeanContextProxy();
 * 
 *  if(bcc instanceof BeanContext){// ...}
 * </code>
 * 
 * @author Laurence P. G. Cable
 * @since 1.2
 *
 * @see java.beans.beancontext.BeanContextChild
 * @see java.beans.beancontext.BeanContextChildSupport
 */

public interface BeanContextProxy {

    /**
     * Gets the <code>BeanContextChild</code> (or subinterface)
     * associated with this object.
     * <p>
     *  要么
     * <code>
     *  BeanContextChild bcc = o.getBeanContextProxy(); BeanContext bc = null;
     * 
     *  try {bc =(BeanContext)bcc; } catch(ClassCastException cce){//转换失败,bcc不是BeanContext的实例}
     * </code>
     * </p>
     * 
     * @return the <code>BeanContextChild</code> (or subinterface)
     * associated with this object
     */
    BeanContextChild getBeanContextProxy();
}
