/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2015, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.net.URL;

/**
 * This is a support class to make it easier for people to provide
 * BeanInfo classes.
 * <p>
 * It defaults to providing "noop" information, and can be selectively
 * overriden to provide more explicit information on chosen topics.
 * When the introspector sees the "noop" values, it will apply low
 * level introspection and design patterns to automatically analyze
 * the target bean.
 * <p>
 *  这是一个支持类,使人们更容易提供BeanInfo类。
 * <p>
 *  它默认提供"noop"信息,并且可以选择性地覆盖以提供关于选择的主题的更明确的信息。当内部查看器看到"noop"值时,它将应用低级内省和设计模式来自动分析目标bean。
 * 
 */

public class SimpleBeanInfo implements BeanInfo {

    /**
     * Deny knowledge about the class and customizer of the bean.
     * You can override this if you wish to provide explicit info.
     * <p>
     *  拒绝关于bean的类和定制器的知识。如果您想提供显式信息,您可以覆盖此。
     * 
     */
    public BeanDescriptor getBeanDescriptor() {
        return null;
    }

    /**
     * Deny knowledge of properties. You can override this
     * if you wish to provide explicit property info.
     * <p>
     *  否认属性知识。如果您想提供显式属性信息,您可以覆盖此。
     * 
     */
    public PropertyDescriptor[] getPropertyDescriptors() {
        return null;
    }

    /**
     * Deny knowledge of a default property. You can override this
     * if you wish to define a default property for the bean.
     * <p>
     *  拒绝知道默认属性。如果要为bean定义默认属性,可以覆盖此值。
     * 
     */
    public int getDefaultPropertyIndex() {
        return -1;
    }

    /**
     * Deny knowledge of event sets. You can override this
     * if you wish to provide explicit event set info.
     * <p>
     *  拒绝事件集的知识。如果您希望提供显式事件集信息,您可以覆盖此。
     * 
     */
    public EventSetDescriptor[] getEventSetDescriptors() {
        return null;
    }

    /**
     * Deny knowledge of a default event. You can override this
     * if you wish to define a default event for the bean.
     * <p>
     *  拒绝默认事件的知识。如果您希望为bean定义一个默认事件,可以覆盖此。
     * 
     */
    public int getDefaultEventIndex() {
        return -1;
    }

    /**
     * Deny knowledge of methods. You can override this
     * if you wish to provide explicit method info.
     * <p>
     *  否认方法的知识。如果您希望提供显式方法信息,您可以覆盖此。
     * 
     */
    public MethodDescriptor[] getMethodDescriptors() {
        return null;
    }

    /**
     * Claim there are no other relevant BeanInfo objects.  You
     * may override this if you want to (for example) return a
     * BeanInfo for a base class.
     * <p>
     *  声明没有其他相关的BeanInfo对象。如果你想(例如)返回一个BeanInfo为一个基类,你可以覆盖这个。
     * 
     */
    public BeanInfo[] getAdditionalBeanInfo() {
        return null;
    }

    /**
     * Claim there are no icons available.  You can override
     * this if you want to provide icons for your bean.
     * <p>
     *  声明没有可用的图标。如果要为bean提供图标,您可以覆盖此选项。
     * 
     */
    public Image getIcon(int iconKind) {
        return null;
    }

    /**
     * This is a utility method to help in loading icon images.
     * It takes the name of a resource file associated with the
     * current object's class file and loads an image object
     * from that file.  Typically images will be GIFs.
     * <p>
     * <p>
     * 这是一个帮助加载图标图像的实用程序方法。它使用与当前对象的类文件相关联的资源文件的名称,并从该文件加载图像对象。通常图像将是GIF。
     * 
     * @param resourceName  A pathname relative to the directory
     *          holding the class file of the current class.  For example,
     *          "wombat.gif".
     * @return  an image object.  May be null if the load failed.
     */
    public Image loadImage(final String resourceName) {
        try {
            final URL url = getClass().getResource(resourceName);
            if (url != null) {
                final ImageProducer ip = (ImageProducer) url.getContent();
                if (ip != null) {
                    return Toolkit.getDefaultToolkit().createImage(ip);
                }
            }
        } catch (final Exception ignored) {
        }
        return null;
    }
}
