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

import com.sun.beans.finder.ClassFinder;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;

import java.awt.Image;

import java.beans.beancontext.BeanContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;

import java.lang.reflect.Modifier;

import java.net.URL;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * This class provides some general purpose beans control methods.
 * <p>
 *  这个类提供了一些通用的bean控制方法。
 * 
 */

public class Beans {

    /**
     * <p>
     * Instantiate a JavaBean.
     * </p>
     * <p>
     * <p>
     *  实例化JavaBean。
     * </p>
     * 
     * @return a JavaBean
     * @param     cls         the class-loader from which we should create
     *                        the bean.  If this is null, then the system
     *                        class-loader is used.
     * @param     beanName    the name of the bean within the class-loader.
     *                        For example "sun.beanbox.foobah"
     *
     * @exception ClassNotFoundException if the class of a serialized
     *              object could not be found.
     * @exception IOException if an I/O error occurs.
     */

    public static Object instantiate(ClassLoader cls, String beanName) throws IOException, ClassNotFoundException {
        return Beans.instantiate(cls, beanName, null, null);
    }

    /**
     * <p>
     * Instantiate a JavaBean.
     * </p>
     * <p>
     * <p>
     *  实例化JavaBean。
     * </p>
     * 
     * @return a JavaBean
     *
     * @param     cls         the class-loader from which we should create
     *                        the bean.  If this is null, then the system
     *                        class-loader is used.
     * @param     beanName    the name of the bean within the class-loader.
     *                        For example "sun.beanbox.foobah"
     * @param     beanContext The BeanContext in which to nest the new bean
     *
     * @exception ClassNotFoundException if the class of a serialized
     *              object could not be found.
     * @exception IOException if an I/O error occurs.
     */

    public static Object instantiate(ClassLoader cls, String beanName, BeanContext beanContext) throws IOException, ClassNotFoundException {
        return Beans.instantiate(cls, beanName, beanContext, null);
    }

    /**
     * Instantiate a bean.
     * <p>
     * The bean is created based on a name relative to a class-loader.
     * This name should be a dot-separated name such as "a.b.c".
     * <p>
     * In Beans 1.0 the given name can indicate either a serialized object
     * or a class.  Other mechanisms may be added in the future.  In
     * beans 1.0 we first try to treat the beanName as a serialized object
     * name then as a class name.
     * <p>
     * When using the beanName as a serialized object name we convert the
     * given beanName to a resource pathname and add a trailing ".ser" suffix.
     * We then try to load a serialized object from that resource.
     * <p>
     * For example, given a beanName of "x.y", Beans.instantiate would first
     * try to read a serialized object from the resource "x/y.ser" and if
     * that failed it would try to load the class "x.y" and create an
     * instance of that class.
     * <p>
     * If the bean is a subtype of java.applet.Applet, then it is given
     * some special initialization.  First, it is supplied with a default
     * AppletStub and AppletContext.  Second, if it was instantiated from
     * a classname the applet's "init" method is called.  (If the bean was
     * deserialized this step is skipped.)
     * <p>
     * Note that for beans which are applets, it is the caller's responsiblity
     * to call "start" on the applet.  For correct behaviour, this should be done
     * after the applet has been added into a visible AWT container.
     * <p>
     * Note that applets created via beans.instantiate run in a slightly
     * different environment than applets running inside browsers.  In
     * particular, bean applets have no access to "parameters", so they may
     * wish to provide property get/set methods to set parameter values.  We
     * advise bean-applet developers to test their bean-applets against both
     * the JDK appletviewer (for a reference browser environment) and the
     * BDK BeanBox (for a reference bean container).
     *
     * <p>
     *  实例化bean。
     * <p>
     *  该bean是基于相对于类加载器的名称创建的。此名称应为点分隔名称,例如"a.b.c"。
     * <p>
     *  在Bean 1.0中,给定的名称可以指示序列化对象或类。将来可能增加其他机制。在bean 1.0中,我们首先尝试将beanName视为序列化对象名称,然后作为类名称。
     * <p>
     *  当使用beanName作为序列化对象名称时,我们将给定的beanName转换为资源路径名,并添加一个尾随的".ser"后缀。然后,我们尝试从该资源加载序列化对象。
     * <p>
     *  例如,给定beanName"xy",Beans.instantiate将首先尝试从资源"x / y.ser"中读取序列化对象,如果失败,它将尝试加载类"xy"并创建一个实例的类。
     * <p>
     *  如果bean是java.applet.Applet的子类型,那么它会给出一些特殊的初始化。首先,它提供了默认的AppletStub和AppletContext。
     * 第二,如果它是从类名实例化的,applet的"init"方法被调用。 (如果bean被反序列化,则跳过此步骤。)。
     * <p>
     * 注意,对于是applet的bean,调用者在applet上调用"start"的责任。为了正确的行为,这应该在小程序被添加到可见的AWT容器中之后完成。
     * <p>
     *  注意,通过beans.instantiate创建的applet在与浏览器中运行的applet稍微不同的环境中运行。
     * 特别是,bean applet没有访问"参数",因此他们可能希望提供属性get / set方法来设置参数值。
     * 我们建议bean-applet开发人员针对JDK appletviewer(用于引用浏览器环境)和BDK BeanBox(用于引用bean容器)测试他们的bean-applets。
     * 
     * 
     * @return a JavaBean
     * @param     cls         the class-loader from which we should create
     *                        the bean.  If this is null, then the system
     *                        class-loader is used.
     * @param     beanName    the name of the bean within the class-loader.
     *                        For example "sun.beanbox.foobah"
     * @param     beanContext The BeanContext in which to nest the new bean
     * @param     initializer The AppletInitializer for the new bean
     *
     * @exception ClassNotFoundException if the class of a serialized
     *              object could not be found.
     * @exception IOException if an I/O error occurs.
     */

    public static Object instantiate(ClassLoader cls, String beanName, BeanContext beanContext, AppletInitializer initializer)
                        throws IOException, ClassNotFoundException {

        InputStream ins;
        ObjectInputStream oins = null;
        Object result = null;
        boolean serialized = false;
        IOException serex = null;

        // If the given classloader is null, we check if an
        // system classloader is available and (if so)
        // use that instead.
        // Note that calls on the system class loader will
        // look in the bootstrap class loader first.
        if (cls == null) {
            try {
                cls = ClassLoader.getSystemClassLoader();
            } catch (SecurityException ex) {
                // We're not allowed to access the system class loader.
                // Drop through.
            }
        }

        // Try to find a serialized object with this name
        final String serName = beanName.replace('.','/').concat(".ser");
        if (cls == null)
            ins =  ClassLoader.getSystemResourceAsStream(serName);
        else
            ins =  cls.getResourceAsStream(serName);
        if (ins != null) {
            try {
                if (cls == null) {
                    oins = new ObjectInputStream(ins);
                } else {
                    oins = new ObjectInputStreamWithLoader(ins, cls);
                }
                result = oins.readObject();
                serialized = true;
                oins.close();
            } catch (IOException ex) {
                ins.close();
                // Drop through and try opening the class.  But remember
                // the exception in case we can't find the class either.
                serex = ex;
            } catch (ClassNotFoundException ex) {
                ins.close();
                throw ex;
            }
        }

        if (result == null) {
            // No serialized object, try just instantiating the class
            Class<?> cl;

            try {
                cl = ClassFinder.findClass(beanName, cls);
            } catch (ClassNotFoundException ex) {
                // There is no appropriate class.  If we earlier tried to
                // deserialize an object and got an IO exception, throw that,
                // otherwise rethrow the ClassNotFoundException.
                if (serex != null) {
                    throw serex;
                }
                throw ex;
            }

            if (!Modifier.isPublic(cl.getModifiers())) {
                throw new ClassNotFoundException("" + cl + " : no public access");
            }

            /*
             * Try to instantiate the class.
             * <p>
             *  尝试实例化类。
             * 
             */

            try {
                result = cl.newInstance();
            } catch (Exception ex) {
                // We have to remap the exception to one in our signature.
                // But we pass extra information in the detail message.
                throw new ClassNotFoundException("" + cl + " : " + ex, ex);
            }
        }

        if (result != null) {

            // Ok, if the result is an applet initialize it.

            AppletStub stub = null;

            if (result instanceof Applet) {
                Applet  applet      = (Applet) result;
                boolean needDummies = initializer == null;

                if (needDummies) {

                    // Figure our the codebase and docbase URLs.  We do this
                    // by locating the URL for a known resource, and then
                    // massaging the URL.

                    // First find the "resource name" corresponding to the bean
                    // itself.  So a serialzied bean "a.b.c" would imply a
                    // resource name of "a/b/c.ser" and a classname of "x.y"
                    // would imply a resource name of "x/y.class".

                    final String resourceName;

                    if (serialized) {
                        // Serialized bean
                        resourceName = beanName.replace('.','/').concat(".ser");
                    } else {
                        // Regular class
                        resourceName = beanName.replace('.','/').concat(".class");
                    }

                    URL objectUrl = null;
                    URL codeBase  = null;
                    URL docBase   = null;

                    // Now get the URL correponding to the resource name.
                    if (cls == null) {
                        objectUrl = ClassLoader.getSystemResource(resourceName);
                    } else
                        objectUrl = cls.getResource(resourceName);

                    // If we found a URL, we try to locate the docbase by taking
                    // of the final path name component, and the code base by taking
                    // of the complete resourceName.
                    // So if we had a resourceName of "a/b/c.class" and we got an
                    // objectURL of "file://bert/classes/a/b/c.class" then we would
                    // want to set the codebase to "file://bert/classes/" and the
                    // docbase to "file://bert/classes/a/b/"

                    if (objectUrl != null) {
                        String s = objectUrl.toExternalForm();

                        if (s.endsWith(resourceName)) {
                            int ix   = s.length() - resourceName.length();
                            codeBase = new URL(s.substring(0,ix));
                            docBase  = codeBase;

                            ix = s.lastIndexOf('/');

                            if (ix >= 0) {
                                docBase = new URL(s.substring(0,ix+1));
                            }
                        }
                    }

                    // Setup a default context and stub.
                    BeansAppletContext context = new BeansAppletContext(applet);

                    stub = (AppletStub)new BeansAppletStub(applet, context, codeBase, docBase);
                    applet.setStub(stub);
                } else {
                    initializer.initialize(applet, beanContext);
                }

                // now, if there is a BeanContext, add the bean, if applicable.

                if (beanContext != null) {
                    unsafeBeanContextAdd(beanContext, result);
                }

                // If it was deserialized then it was already init-ed.
                // Otherwise we need to initialize it.

                if (!serialized) {
                    // We need to set a reasonable initial size, as many
                    // applets are unhappy if they are started without
                    // having been explicitly sized.
                    applet.setSize(100,100);
                    applet.init();
                }

                if (needDummies) {
                  ((BeansAppletStub)stub).active = true;
                } else initializer.activate(applet);

            } else if (beanContext != null) unsafeBeanContextAdd(beanContext, result);
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private static void unsafeBeanContextAdd(BeanContext beanContext, Object res) {
        beanContext.add(res);
    }

    /**
     * From a given bean, obtain an object representing a specified
     * type view of that source object.
     * <p>
     * The result may be the same object or a different object.  If
     * the requested target view isn't available then the given
     * bean is returned.
     * <p>
     * This method is provided in Beans 1.0 as a hook to allow the
     * addition of more flexible bean behaviour in the future.
     *
     * <p>
     *  从给定的bean,获取表示该源对象的指定类型视图的对象。
     * <p>
     *  结果可以是相同的对象或不同的对象。如果请求的目标视图不可用,则返回给定的bean。
     * <p>
     *  此方法在Beans 1.0中作为一个钩子提供,以允许在将来添加更灵活的bean行为。
     * 
     * 
     * @return an object representing a specified type view of the
     * source object
     * @param bean        Object from which we want to obtain a view.
     * @param targetType  The type of view we'd like to get.
     *
     */
    public static Object getInstanceOf(Object bean, Class<?> targetType) {
        return bean;
    }

    /**
     * Check if a bean can be viewed as a given target type.
     * The result will be true if the Beans.getInstanceof method
     * can be used on the given bean to obtain an object that
     * represents the specified targetType type view.
     *
     * <p>
     *  检查bean是否可以被视为给定的目标类型。如果可以在给定的bean上使用Beans.getInstanceof方法来获取表示指定的targetType类型视图的对象,则结果为true。
     * 
     * 
     * @param bean  Bean from which we want to obtain a view.
     * @param targetType  The type of view we'd like to get.
     * @return "true" if the given bean supports the given targetType.
     *
     */
    public static boolean isInstanceOf(Object bean, Class<?> targetType) {
        return Introspector.isSubclass(bean.getClass(), targetType);
    }

    /**
     * Test if we are in design-mode.
     *
     * <p>
     *  测试我们是否处于设计模式。
     * 
     * 
     * @return  True if we are running in an application construction
     *          environment.
     *
     * @see DesignMode
     */
    public static boolean isDesignTime() {
        return ThreadGroupContext.getContext().isDesignTime();
    }

    /**
     * Determines whether beans can assume a GUI is available.
     *
     * <p>
     *  确定bean是否可以假定GUI可用。
     * 
     * 
     * @return  True if we are running in an environment where beans
     *     can assume that an interactive GUI is available, so they
     *     can pop up dialog boxes, etc.  This will normally return
     *     true in a windowing environment, and will normally return
     *     false in a server environment or if an application is
     *     running as part of a batch job.
     *
     * @see Visibility
     *
     */
    public static boolean isGuiAvailable() {
        return ThreadGroupContext.getContext().isGuiAvailable();
    }

    /**
     * Used to indicate whether of not we are running in an application
     * builder environment.
     *
     * <p>Note that this method is security checked
     * and is not available to (for example) untrusted applets.
     * More specifically, if there is a security manager,
     * its <code>checkPropertiesAccess</code>
     * method is called. This could result in a SecurityException.
     *
     * <p>
     *  用于指示我们是否正在应用程序构建器环境中运行。
     * 
     * <p>请注意,此方法已进行安全检查,并且不可用于(例如)不受信任的小程序。更具体地说,如果有一个安全管理器,它的<code> checkPropertiesAccess </code>方法被调用。
     * 这可能导致SecurityException。
     * 
     * 
     * @param isDesignTime  True if we're in an application builder tool.
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertiesAccess</code> method doesn't allow setting
     *              of system properties.
     * @see SecurityManager#checkPropertiesAccess
     */

    public static void setDesignTime(boolean isDesignTime)
                        throws SecurityException {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPropertiesAccess();
        }
        ThreadGroupContext.getContext().setDesignTime(isDesignTime);
    }

    /**
     * Used to indicate whether of not we are running in an environment
     * where GUI interaction is available.
     *
     * <p>Note that this method is security checked
     * and is not available to (for example) untrusted applets.
     * More specifically, if there is a security manager,
     * its <code>checkPropertiesAccess</code>
     * method is called. This could result in a SecurityException.
     *
     * <p>
     *  用于指示我们是否在GUI交互可用的环境中运行。
     * 
     *  <p>请注意,此方法已进行安全检查,并且不可用于(例如)不受信任的小程序。更具体地说,如果有一个安全管理器,它的<code> checkPropertiesAccess </code>方法被调用。
     * 这可能导致SecurityException。
     * 
     * 
     * @param isGuiAvailable  True if GUI interaction is available.
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertiesAccess</code> method doesn't allow setting
     *              of system properties.
     * @see SecurityManager#checkPropertiesAccess
     */

    public static void setGuiAvailable(boolean isGuiAvailable)
                        throws SecurityException {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPropertiesAccess();
        }
        ThreadGroupContext.getContext().setGuiAvailable(isGuiAvailable);
    }
}

/**
 * This subclass of ObjectInputStream delegates loading of classes to
 * an existing ClassLoader.
 * <p>
 *  ObjectInputStream的这个子类将类的加载委托给现有的ClassLoader。
 * 
 */

class ObjectInputStreamWithLoader extends ObjectInputStream
{
    private ClassLoader loader;

    /**
     * Loader must be non-null;
     * <p>
     *  装载器必须是非null;
     * 
     */

    public ObjectInputStreamWithLoader(InputStream in, ClassLoader loader)
            throws IOException, StreamCorruptedException {

        super(in);
        if (loader == null) {
            throw new IllegalArgumentException("Illegal null argument to ObjectInputStreamWithLoader");
        }
        this.loader = loader;
    }

    /**
     * Use the given ClassLoader rather than using the system class
     * <p>
     *  使用给定的ClassLoader而不是使用系统类
     * 
     */
    @SuppressWarnings("rawtypes")
    protected Class resolveClass(ObjectStreamClass classDesc)
        throws IOException, ClassNotFoundException {

        String cname = classDesc.getName();
        return ClassFinder.resolveClass(cname, this.loader);
    }
}

/**
 * Package private support class.  This provides a default AppletContext
 * for beans which are applets.
 * <p>
 *  包私有支持类。这为applet提供了一个默认的AppletContext。
 * 
 */

class BeansAppletContext implements AppletContext {
    Applet target;
    Hashtable<URL,Object> imageCache = new Hashtable<>();

    BeansAppletContext(Applet target) {
        this.target = target;
    }

    public AudioClip getAudioClip(URL url) {
        // We don't currently support audio clips in the Beans.instantiate
        // applet context, unless by some luck there exists a URL content
        // class that can generate an AudioClip from the audio URL.
        try {
            return (AudioClip) url.getContent();
        } catch (Exception ex) {
            return null;
        }
    }

    public synchronized Image getImage(URL url) {
        Object o = imageCache.get(url);
        if (o != null) {
            return (Image)o;
        }
        try {
            o = url.getContent();
            if (o == null) {
                return null;
            }
            if (o instanceof Image) {
                imageCache.put(url, o);
                return (Image) o;
            }
            // Otherwise it must be an ImageProducer.
            Image img = target.createImage((java.awt.image.ImageProducer)o);
            imageCache.put(url, img);
            return img;

        } catch (Exception ex) {
            return null;
        }
    }

    public Applet getApplet(String name) {
        return null;
    }

    public Enumeration<Applet> getApplets() {
        Vector<Applet> applets = new Vector<>();
        applets.addElement(target);
        return applets.elements();
    }

    public void showDocument(URL url) {
        // We do nothing.
    }

    public void showDocument(URL url, String target) {
        // We do nothing.
    }

    public void showStatus(String status) {
        // We do nothing.
    }

    public void setStream(String key, InputStream stream)throws IOException{
        // We do nothing.
    }

    public InputStream getStream(String key){
        // We do nothing.
        return null;
    }

    public Iterator<String> getStreamKeys(){
        // We do nothing.
        return null;
    }
}

/**
 * Package private support class.  This provides an AppletStub
 * for beans which are applets.
 * <p>
 *  包私有支持类。这为applet提供了一个AppletStub。
 */
class BeansAppletStub implements AppletStub {
    transient boolean active;
    transient Applet target;
    transient AppletContext context;
    transient URL codeBase;
    transient URL docBase;

    BeansAppletStub(Applet target,
                AppletContext context, URL codeBase,
                                URL docBase) {
        this.target = target;
        this.context = context;
        this.codeBase = codeBase;
        this.docBase = docBase;
    }

    public boolean isActive() {
        return active;
    }

    public URL getDocumentBase() {
        // use the root directory of the applet's class-loader
        return docBase;
    }

    public URL getCodeBase() {
        // use the directory where we found the class or serialized object.
        return codeBase;
    }

    public String getParameter(String name) {
        return null;
    }

    public AppletContext getAppletContext() {
        return context;
    }

    public void appletResize(int width, int height) {
        // we do nothing.
    }
}
