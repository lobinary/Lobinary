/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

import java.lang.reflect.AnnotatedElement;
import java.io.InputStream;
import java.util.Enumeration;

import java.util.StringTokenizer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarException;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import sun.net.www.ParseUtil;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.lang.annotation.Annotation;

/**
 * {@code Package} objects contain version information
 * about the implementation and specification of a Java package.
 * This versioning information is retrieved and made available
 * by the {@link ClassLoader} instance that
 * loaded the class(es).  Typically, it is stored in the manifest that is
 * distributed with the classes.
 *
 * <p>The set of classes that make up the package may implement a
 * particular specification and if so the specification title, version number,
 * and vendor strings identify that specification.
 * An application can ask if the package is
 * compatible with a particular version, see the {@link
 * #isCompatibleWith isCompatibleWith}
 * method for details.
 *
 * <p>Specification version numbers use a syntax that consists of nonnegative
 * decimal integers separated by periods ".", for example "2.0" or
 * "1.2.3.4.5.6.7".  This allows an extensible number to be used to represent
 * major, minor, micro, etc. versions.  The version specification is described
 * by the following formal grammar:
 * <blockquote>
 * <dl>
 * <dt><i>SpecificationVersion:</i>
 * <dd><i>Digits RefinedVersion<sub>opt</sub></i>

 * <dt><i>RefinedVersion:</i>
 * <dd>{@code .} <i>Digits</i>
 * <dd>{@code .} <i>Digits RefinedVersion</i>
 *
 * <dt><i>Digits:</i>
 * <dd><i>Digit</i>
 * <dd><i>Digits</i>
 *
 * <dt><i>Digit:</i>
 * <dd>any character for which {@link Character#isDigit} returns {@code true},
 * e.g. 0, 1, 2, ...
 * </dl>
 * </blockquote>
 *
 * <p>The implementation title, version, and vendor strings identify an
 * implementation and are made available conveniently to enable accurate
 * reporting of the packages involved when a problem occurs. The contents
 * all three implementation strings are vendor specific. The
 * implementation version strings have no specified syntax and should
 * only be compared for equality with desired version identifiers.
 *
 * <p>Within each {@code ClassLoader} instance all classes from the same
 * java package have the same Package object.  The static methods allow a package
 * to be found by name or the set of all packages known to the current class
 * loader to be found.
 *
 * <p>
 *  {@code Package}对象包含有关Java包的实现和规范的版本信息。此版本控制信息由加载类的{@link ClassLoader}实例检索并提供。通常,它存储在与类一起分发的清单中。
 * 
 *  <p>组成包的类的集合可以实现特定规范,如果是,则规范标题,版本号和厂商串标识该规范。
 * 应用程序可以询问软件包是否与特定版本兼容,有关详细信息,请参阅{@link #isCompatibleWith isCompatibleWith}方法。
 * 
 *  <p>规范版本号使用由以句点"。"分隔的非负十进制整数组成的语法,例如"2.0"或"1.2.3.4.5.6.7"。这允许可扩展数字用于表示主要,次要,微型等版本。版本规范由以下形式语法描述：
 * <blockquote>
 * <dl>
 *  <dt> <i> SpecificationVersion：</i> <dd> <i> Digits RefinedVersion <sub> opt </sub> </i>
 * 
 *  <dt> <i> RefinedVersion：</i> <dd> {@ code。} <i>数字</i> <dd> {@ code}
 * 
 *  <dt> <i>数字：</i> <dd> <i>数字</i> <dd> <i>数字</i>
 * 
 *  <dt> <i>数字：</i> <dd> {@link Character#isDigit}返回{@code true}的任何字符,例如0,1,2,...
 * </dl>
 * </blockquote>
 * 
 * <p>实施标题,版本和供应商字符串标识了一个实施,并可方便地提供,以便在发生问题时能够准确报告相关包。内容所有三个实现字符串是供应商特定的。
 * 实现版本字符串没有指定的语法,并且应该仅仅与期望的版本标识符的相等性进行比较。
 * 
 *  <p>在每个{@code ClassLoader}实例中,来自同一个Java包的所有类都具有相同的Package对象。静态方法允许通过名称或者找到当前类加载器已知的所有包的集合来找到包。
 * 
 * 
 * @see ClassLoader#definePackage
 */
public class Package implements java.lang.reflect.AnnotatedElement {
    /**
     * Return the name of this package.
     *
     * <p>
     *  返回此包的名称。
     * 
     * 
     * @return  The fully-qualified name of this package as defined in section 6.5.3 of
     *          <cite>The Java&trade; Language Specification</cite>,
     *          for example, {@code java.lang}
     */
    public String getName() {
        return pkgName;
    }


    /**
     * Return the title of the specification that this package implements.
     * <p>
     *  返回此包实现的规范的标题。
     * 
     * 
     * @return the specification title, null is returned if it is not known.
     */
    public String getSpecificationTitle() {
        return specTitle;
    }

    /**
     * Returns the version number of the specification
     * that this package implements.
     * This version string must be a sequence of nonnegative decimal
     * integers separated by "."'s and may have leading zeros.
     * When version strings are compared the most significant
     * numbers are compared.
     * <p>
     *  返回此程序包实现的规范的版本号。此版本字符串必须是由"。"分隔的非负十进制整数序列,并且可以具有前导零。当比较版本字符串时,比较最有效的数字。
     * 
     * 
     * @return the specification version, null is returned if it is not known.
     */
    public String getSpecificationVersion() {
        return specVersion;
    }

    /**
     * Return the name of the organization, vendor,
     * or company that owns and maintains the specification
     * of the classes that implement this package.
     * <p>
     *  返回拥有并维护实现此包的类的规范的组织,供应商或公司的名称。
     * 
     * 
     * @return the specification vendor, null is returned if it is not known.
     */
    public String getSpecificationVendor() {
        return specVendor;
    }

    /**
     * Return the title of this package.
     * <p>
     *  返回此包的标题。
     * 
     * 
     * @return the title of the implementation, null is returned if it is not known.
     */
    public String getImplementationTitle() {
        return implTitle;
    }

    /**
     * Return the version of this implementation. It consists of any string
     * assigned by the vendor of this implementation and does
     * not have any particular syntax specified or expected by the Java
     * runtime. It may be compared for equality with other
     * package version strings used for this implementation
     * by this vendor for this package.
     * <p>
     * 返回此实现的版本。它由此实现的供应商分配的任何字符串组成,并且没有Java运行时指定或预期的任何特定语法。它可以与此供应商针对此包的此实现所使用的其他包版本字符串相等。
     * 
     * 
     * @return the version of the implementation, null is returned if it is not known.
     */
    public String getImplementationVersion() {
        return implVersion;
    }

    /**
     * Returns the name of the organization,
     * vendor or company that provided this implementation.
     * <p>
     *  返回提供此实现的组织,供应商或公司的名称。
     * 
     * 
     * @return the vendor that implemented this package..
     */
    public String getImplementationVendor() {
        return implVendor;
    }

    /**
     * Returns true if this package is sealed.
     *
     * <p>
     *  如果此程序包是密封的,则返回true。
     * 
     * 
     * @return true if the package is sealed, false otherwise
     */
    public boolean isSealed() {
        return sealBase != null;
    }

    /**
     * Returns true if this package is sealed with respect to the specified
     * code source url.
     *
     * <p>
     *  如果此程序包相对于指定的代码源网址是密封的,则返回true。
     * 
     * 
     * @param url the code source url
     * @return true if this package is sealed with respect to url
     */
    public boolean isSealed(URL url) {
        return url.equals(sealBase);
    }

    /**
     * Compare this package's specification version with a
     * desired version. It returns true if
     * this packages specification version number is greater than or equal
     * to the desired version number. <p>
     *
     * Version numbers are compared by sequentially comparing corresponding
     * components of the desired and specification strings.
     * Each component is converted as a decimal integer and the values
     * compared.
     * If the specification value is greater than the desired
     * value true is returned. If the value is less false is returned.
     * If the values are equal the period is skipped and the next pair of
     * components is compared.
     *
     * <p>
     *  将此软件包的规范版本与所需的版本进行比较。如果此包规范版本号大于或等于所需的版本号,则返回true。 <p>
     * 
     *  通过顺序地比较期望字符串和规范字符串的相应组件来比较版本号。每个组件转换为十进制整数,并比较值。如果规格值大于所需值,则返回true。如果值小于false,则返回。
     * 如果值相等,则跳过周期,并比较下一对分量。
     * 
     * 
     * @param desired the version string of the desired version.
     * @return true if this package's version number is greater
     *          than or equal to the desired version number
     *
     * @exception NumberFormatException if the desired or current version
     *          is not of the correct dotted form.
     */
    public boolean isCompatibleWith(String desired)
        throws NumberFormatException
    {
        if (specVersion == null || specVersion.length() < 1) {
            throw new NumberFormatException("Empty version string");
        }

        String [] sa = specVersion.split("\\.", -1);
        int [] si = new int[sa.length];
        for (int i = 0; i < sa.length; i++) {
            si[i] = Integer.parseInt(sa[i]);
            if (si[i] < 0)
                throw NumberFormatException.forInputString("" + si[i]);
        }

        String [] da = desired.split("\\.", -1);
        int [] di = new int[da.length];
        for (int i = 0; i < da.length; i++) {
            di[i] = Integer.parseInt(da[i]);
            if (di[i] < 0)
                throw NumberFormatException.forInputString("" + di[i]);
        }

        int len = Math.max(di.length, si.length);
        for (int i = 0; i < len; i++) {
            int d = (i < di.length ? di[i] : 0);
            int s = (i < si.length ? si[i] : 0);
            if (s < d)
                return false;
            if (s > d)
                return true;
        }
        return true;
    }

    /**
     * Find a package by name in the callers {@code ClassLoader} instance.
     * The callers {@code ClassLoader} instance is used to find the package
     * instance corresponding to the named class. If the callers
     * {@code ClassLoader} instance is null then the set of packages loaded
     * by the system {@code ClassLoader} instance is searched to find the
     * named package. <p>
     *
     * Packages have attributes for versions and specifications only if the class
     * loader created the package instance with the appropriate attributes. Typically,
     * those attributes are defined in the manifests that accompany the classes.
     *
     * <p>
     *  在调用者{@code ClassLoader}实例中按名称查找包。调用者{@code ClassLoader}实例用于查找与命名类对应的包实例。
     * 如果调用者{@code ClassLoader}实例为null,则会搜索系统{@code ClassLoader}实例加载的一组包,以查找指定的包。 <p>。
     * 
     * 只有在类加载器使用适当的属性创建了包实例时,才有包含版本和规范的属性。通常,这些属性在类附带的清单中定义。
     * 
     * 
     * @param name a package name, for example, java.lang.
     * @return the package of the requested name. It may be null if no package
     *          information is available from the archive or codebase.
     */
    @CallerSensitive
    public static Package getPackage(String name) {
        ClassLoader l = ClassLoader.getClassLoader(Reflection.getCallerClass());
        if (l != null) {
            return l.getPackage(name);
        } else {
            return getSystemPackage(name);
        }
    }

    /**
     * Get all the packages currently known for the caller's {@code ClassLoader}
     * instance.  Those packages correspond to classes loaded via or accessible by
     * name to that {@code ClassLoader} instance.  If the caller's
     * {@code ClassLoader} instance is the bootstrap {@code ClassLoader}
     * instance, which may be represented by {@code null} in some implementations,
     * only packages corresponding to classes loaded by the bootstrap
     * {@code ClassLoader} instance will be returned.
     *
     * <p>
     *  获取当前为调用者的{@code ClassLoader}实例所知的所有包。这些包对应于通过{@code ClassLoader}实例加载或通过名称访问的类。
     * 如果调用者的{@code ClassLoader}实例是引导{@code ClassLoader}实例(在某些实现中可能由{@code null}表示),则只有对应于由引导{@code ClassLoader}
     * 实例加载的类的包才会回。
     *  获取当前为调用者的{@code ClassLoader}实例所知的所有包。这些包对应于通过{@code ClassLoader}实例加载或通过名称访问的类。
     * 
     * 
     * @return a new array of packages known to the callers {@code ClassLoader}
     * instance.  An zero length array is returned if none are known.
     */
    @CallerSensitive
    public static Package[] getPackages() {
        ClassLoader l = ClassLoader.getClassLoader(Reflection.getCallerClass());
        if (l != null) {
            return l.getPackages();
        } else {
            return getSystemPackages();
        }
    }

    /**
     * Get the package for the specified class.
     * The class's class loader is used to find the package instance
     * corresponding to the specified class. If the class loader
     * is the bootstrap class loader, which may be represented by
     * {@code null} in some implementations, then the set of packages
     * loaded by the bootstrap class loader is searched to find the package.
     * <p>
     * Packages have attributes for versions and specifications only
     * if the class loader created the package
     * instance with the appropriate attributes. Typically those
     * attributes are defined in the manifests that accompany
     * the classes.
     *
     * <p>
     *  获取指定类的包。类的类加载器用于查找与指定类对应的包实例。如果类加载器是引导类加载器,在一些实现中可以由{@code null}表示,则搜索由引导类加载器加载的该组包以找到该包。
     * <p>
     *  只有在类加载器使用适当的属性创建了包实例时,才有包含版本和规范的属性。通常,这些属性在伴随类的清单中定义。
     * 
     * 
     * @param c the class to get the package of.
     * @return the package of the class. It may be null if no package
     *          information is available from the archive or codebase.  */
    static Package getPackage(Class<?> c) {
        String name = c.getName();
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(0, i);
            ClassLoader cl = c.getClassLoader();
            if (cl != null) {
                return cl.getPackage(name);
            } else {
                return getSystemPackage(name);
            }
        } else {
            return null;
        }
    }

    /**
     * Return the hash code computed from the package name.
     * <p>
     *  返回从包名计算的哈希码。
     * 
     * 
     * @return the hash code computed from the package name.
     */
    public int hashCode(){
        return pkgName.hashCode();
    }

    /**
     * Returns the string representation of this Package.
     * Its value is the string "package " and the package name.
     * If the package title is defined it is appended.
     * If the package version is defined it is appended.
     * <p>
     * 返回此软件包的字符串表示形式。它的值是字符串"package"和包名称。如果定义了包标题,则会附加它。如果定义了包版本,则会附加它。
     * 
     * 
     * @return the string representation of the package.
     */
    public String toString() {
        String spec = specTitle;
        String ver =  specVersion;
        if (spec != null && spec.length() > 0)
            spec = ", " + spec;
        else
            spec = "";
        if (ver != null && ver.length() > 0)
            ver = ", version " + ver;
        else
            ver = "";
        return "package " + pkgName + spec + ver;
    }

    private Class<?> getPackageInfo() {
        if (packageInfo == null) {
            try {
                packageInfo = Class.forName(pkgName + ".package-info", false, loader);
            } catch (ClassNotFoundException ex) {
                // store a proxy for the package info that has no annotations
                class PackageInfoProxy {}
                packageInfo = PackageInfoProxy.class;
            }
        }
        return packageInfo;
    }

    /**
    /* <p>
    /* 
     * @throws NullPointerException {@inheritDoc}
     * @since 1.5
     */
    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        return getPackageInfo().getAnnotation(annotationClass);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws NullPointerException {@inheritDoc}
     * @since 1.5
     */
    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return AnnotatedElement.super.isAnnotationPresent(annotationClass);
    }

    /**
    /* <p>
    /* 
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    public  <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationClass) {
        return getPackageInfo().getAnnotationsByType(annotationClass);
    }

    /**
    /* <p>
    /* 
     * @since 1.5
     */
    public Annotation[] getAnnotations() {
        return getPackageInfo().getAnnotations();
    }

    /**
    /* <p>
    /* 
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    public <A extends Annotation> A getDeclaredAnnotation(Class<A> annotationClass) {
        return getPackageInfo().getDeclaredAnnotation(annotationClass);
    }

    /**
    /* <p>
    /* 
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> annotationClass) {
        return getPackageInfo().getDeclaredAnnotationsByType(annotationClass);
    }

    /**
    /* <p>
    /* 
     * @since 1.5
     */
    public Annotation[] getDeclaredAnnotations()  {
        return getPackageInfo().getDeclaredAnnotations();
    }

    /**
     * Construct a package instance with the specified version
     * information.
     * <p>
     *  构造具有指定版本信息的包实例。
     * 
     * 
     * @param name the name of the package
     * @param spectitle the title of the specification
     * @param specversion the version of the specification
     * @param specvendor the organization that maintains the specification
     * @param impltitle the title of the implementation
     * @param implversion the version of the implementation
     * @param implvendor the organization that maintains the implementation
     */
    Package(String name,
            String spectitle, String specversion, String specvendor,
            String impltitle, String implversion, String implvendor,
            URL sealbase, ClassLoader loader)
    {
        pkgName = name;
        implTitle = impltitle;
        implVersion = implversion;
        implVendor = implvendor;
        specTitle = spectitle;
        specVersion = specversion;
        specVendor = specvendor;
        sealBase = sealbase;
        this.loader = loader;
    }

    /*
     * Construct a package using the attributes from the specified manifest.
     *
     * <p>
     *  使用指定清单中的属性构造包。
     * 
     * 
     * @param name the package name
     * @param man the optional manifest for the package
     * @param url the optional code source url for the package
     */
    private Package(String name, Manifest man, URL url, ClassLoader loader) {
        String path = name.replace('.', '/').concat("/");
        String sealed = null;
        String specTitle= null;
        String specVersion= null;
        String specVendor= null;
        String implTitle= null;
        String implVersion= null;
        String implVendor= null;
        URL sealBase= null;
        Attributes attr = man.getAttributes(path);
        if (attr != null) {
            specTitle   = attr.getValue(Name.SPECIFICATION_TITLE);
            specVersion = attr.getValue(Name.SPECIFICATION_VERSION);
            specVendor  = attr.getValue(Name.SPECIFICATION_VENDOR);
            implTitle   = attr.getValue(Name.IMPLEMENTATION_TITLE);
            implVersion = attr.getValue(Name.IMPLEMENTATION_VERSION);
            implVendor  = attr.getValue(Name.IMPLEMENTATION_VENDOR);
            sealed      = attr.getValue(Name.SEALED);
        }
        attr = man.getMainAttributes();
        if (attr != null) {
            if (specTitle == null) {
                specTitle = attr.getValue(Name.SPECIFICATION_TITLE);
            }
            if (specVersion == null) {
                specVersion = attr.getValue(Name.SPECIFICATION_VERSION);
            }
            if (specVendor == null) {
                specVendor = attr.getValue(Name.SPECIFICATION_VENDOR);
            }
            if (implTitle == null) {
                implTitle = attr.getValue(Name.IMPLEMENTATION_TITLE);
            }
            if (implVersion == null) {
                implVersion = attr.getValue(Name.IMPLEMENTATION_VERSION);
            }
            if (implVendor == null) {
                implVendor = attr.getValue(Name.IMPLEMENTATION_VENDOR);
            }
            if (sealed == null) {
                sealed = attr.getValue(Name.SEALED);
            }
        }
        if ("true".equalsIgnoreCase(sealed)) {
            sealBase = url;
        }
        pkgName = name;
        this.specTitle = specTitle;
        this.specVersion = specVersion;
        this.specVendor = specVendor;
        this.implTitle = implTitle;
        this.implVersion = implVersion;
        this.implVendor = implVendor;
        this.sealBase = sealBase;
        this.loader = loader;
    }

    /*
     * Returns the loaded system package for the specified name.
     * <p>
     *  返回指定名称的加载系统包。
     * 
     */
    static Package getSystemPackage(String name) {
        synchronized (pkgs) {
            Package pkg = pkgs.get(name);
            if (pkg == null) {
                name = name.replace('.', '/').concat("/");
                String fn = getSystemPackage0(name);
                if (fn != null) {
                    pkg = defineSystemPackage(name, fn);
                }
            }
            return pkg;
        }
    }

    /*
     * Return an array of loaded system packages.
     * <p>
     *  返回加载的系统包的数组。
     * 
     */
    static Package[] getSystemPackages() {
        // First, update the system package map with new package names
        String[] names = getSystemPackages0();
        synchronized (pkgs) {
            for (int i = 0; i < names.length; i++) {
                defineSystemPackage(names[i], getSystemPackage0(names[i]));
            }
            return pkgs.values().toArray(new Package[pkgs.size()]);
        }
    }

    private static Package defineSystemPackage(final String iname,
                                               final String fn)
    {
        return AccessController.doPrivileged(new PrivilegedAction<Package>() {
            public Package run() {
                String name = iname;
                // Get the cached code source url for the file name
                URL url = urls.get(fn);
                if (url == null) {
                    // URL not found, so create one
                    File file = new File(fn);
                    try {
                        url = ParseUtil.fileToEncodedURL(file);
                    } catch (MalformedURLException e) {
                    }
                    if (url != null) {
                        urls.put(fn, url);
                        // If loading a JAR file, then also cache the manifest
                        if (file.isFile()) {
                            mans.put(fn, loadManifest(fn));
                        }
                    }
                }
                // Convert to "."-separated package name
                name = name.substring(0, name.length() - 1).replace('/', '.');
                Package pkg;
                Manifest man = mans.get(fn);
                if (man != null) {
                    pkg = new Package(name, man, url, null);
                } else {
                    pkg = new Package(name, null, null, null,
                                      null, null, null, null, null);
                }
                pkgs.put(name, pkg);
                return pkg;
            }
        });
    }

    /*
     * Returns the Manifest for the specified JAR file name.
     * <p>
     *  返回指定的JAR文件名的清单。
     * 
     */
    private static Manifest loadManifest(String fn) {
        try (FileInputStream fis = new FileInputStream(fn);
             JarInputStream jis = new JarInputStream(fis, false))
        {
            return jis.getManifest();
        } catch (IOException e) {
            return null;
        }
    }

    // The map of loaded system packages
    private static Map<String, Package> pkgs = new HashMap<>(31);

    // Maps each directory or zip file name to its corresponding url
    private static Map<String, URL> urls = new HashMap<>(10);

    // Maps each code source url for a jar file to its manifest
    private static Map<String, Manifest> mans = new HashMap<>(10);

    private static native String getSystemPackage0(String name);
    private static native String[] getSystemPackages0();

    /*
     * Private storage for the package name and attributes.
     * <p>
     *  包名称和属性的专用存储。
     */
    private final String pkgName;
    private final String specTitle;
    private final String specVersion;
    private final String specVendor;
    private final String implTitle;
    private final String implVersion;
    private final String implVendor;
    private final URL sealBase;
    private transient final ClassLoader loader;
    private transient Class<?> packageInfo;
}
