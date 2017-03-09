/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util.prefs;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.ServiceConfigurationError;

// These imports needed only as a workaround for a JavaDoc bug
import java.lang.RuntimePermission;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Float;
import java.lang.Double;

/**
 * A node in a hierarchical collection of preference data.  This class
 * allows applications to store and retrieve user and system
 * preference and configuration data.  This data is stored
 * persistently in an implementation-dependent backing store.  Typical
 * implementations include flat files, OS-specific registries,
 * directory servers and SQL databases.  The user of this class needn't
 * be concerned with details of the backing store.
 *
 * <p>There are two separate trees of preference nodes, one for user
 * preferences and one for system preferences.  Each user has a separate user
 * preference tree, and all users in a given system share the same system
 * preference tree.  The precise description of "user" and "system" will vary
 * from implementation to implementation.  Typical information stored in the
 * user preference tree might include font choice, color choice, or preferred
 * window location and size for a particular application.  Typical information
 * stored in the system preference tree might include installation
 * configuration data for an application.
 *
 * <p>Nodes in a preference tree are named in a similar fashion to
 * directories in a hierarchical file system.   Every node in a preference
 * tree has a <i>node name</i> (which is not necessarily unique),
 * a unique <i>absolute path name</i>, and a path name <i>relative</i> to each
 * ancestor including itself.
 *
 * <p>The root node has a node name of the empty string ("").  Every other
 * node has an arbitrary node name, specified at the time it is created.  The
 * only restrictions on this name are that it cannot be the empty string, and
 * it cannot contain the slash character ('/').
 *
 * <p>The root node has an absolute path name of <tt>"/"</tt>.  Children of
 * the root node have absolute path names of <tt>"/" + </tt><i>&lt;node
 * name&gt;</i>.  All other nodes have absolute path names of <i>&lt;parent's
 * absolute path name&gt;</i><tt> + "/" + </tt><i>&lt;node name&gt;</i>.
 * Note that all absolute path names begin with the slash character.
 *
 * <p>A node <i>n</i>'s path name relative to its ancestor <i>a</i>
 * is simply the string that must be appended to <i>a</i>'s absolute path name
 * in order to form <i>n</i>'s absolute path name, with the initial slash
 * character (if present) removed.  Note that:
 * <ul>
 * <li>No relative path names begin with the slash character.
 * <li>Every node's path name relative to itself is the empty string.
 * <li>Every node's path name relative to its parent is its node name (except
 * for the root node, which does not have a parent).
 * <li>Every node's path name relative to the root is its absolute path name
 * with the initial slash character removed.
 * </ul>
 *
 * <p>Note finally that:
 * <ul>
 * <li>No path name contains multiple consecutive slash characters.
 * <li>No path name with the exception of the root's absolute path name
 * ends in the slash character.
 * <li>Any string that conforms to these two rules is a valid path name.
 * </ul>
 *
 * <p>All of the methods that modify preferences data are permitted to operate
 * asynchronously; they may return immediately, and changes will eventually
 * propagate to the persistent backing store with an implementation-dependent
 * delay.  The <tt>flush</tt> method may be used to synchronously force
 * updates to the backing store.  Normal termination of the Java Virtual
 * Machine will <i>not</i> result in the loss of pending updates -- an explicit
 * <tt>flush</tt> invocation is <i>not</i> required upon termination to ensure
 * that pending updates are made persistent.
 *
 * <p>All of the methods that read preferences from a <tt>Preferences</tt>
 * object require the invoker to provide a default value.  The default value is
 * returned if no value has been previously set <i>or if the backing store is
 * unavailable</i>.  The intent is to allow applications to operate, albeit
 * with slightly degraded functionality, even if the backing store becomes
 * unavailable.  Several methods, like <tt>flush</tt>, have semantics that
 * prevent them from operating if the backing store is unavailable.  Ordinary
 * applications should have no need to invoke any of these methods, which can
 * be identified by the fact that they are declared to throw {@link
 * BackingStoreException}.
 *
 * <p>The methods in this class may be invoked concurrently by multiple threads
 * in a single JVM without the need for external synchronization, and the
 * results will be equivalent to some serial execution.  If this class is used
 * concurrently <i>by multiple JVMs</i> that store their preference data in
 * the same backing store, the data store will not be corrupted, but no
 * other guarantees are made concerning the consistency of the preference
 * data.
 *
 * <p>This class contains an export/import facility, allowing preferences
 * to be "exported" to an XML document, and XML documents representing
 * preferences to be "imported" back into the system.  This facility
 * may be used to back up all or part of a preference tree, and
 * subsequently restore from the backup.
 *
 * <p>The XML document has the following DOCTYPE declaration:
 * <pre>{@code
 * <!DOCTYPE preferences SYSTEM "http://java.sun.com/dtd/preferences.dtd">
 * }</pre>
 * Note that the system URI (http://java.sun.com/dtd/preferences.dtd) is
 * <i>not</i> accessed when exporting or importing preferences; it merely
 * serves as a string to uniquely identify the DTD, which is:
 * <pre>{@code
 *    <?xml version="1.0" encoding="UTF-8"?>
 *
 *    <!-- DTD for a Preferences tree. -->
 *
 *    <!-- The preferences element is at the root of an XML document
 *         representing a Preferences tree. -->
 *    <!ELEMENT preferences (root)>
 *
 *    <!-- The preferences element contains an optional version attribute,
 *          which specifies version of DTD. -->
 *    <!ATTLIST preferences EXTERNAL_XML_VERSION CDATA "0.0" >
 *
 *    <!-- The root element has a map representing the root's preferences
 *         (if any), and one node for each child of the root (if any). -->
 *    <!ELEMENT root (map, node*) >
 *
 *    <!-- Additionally, the root contains a type attribute, which
 *         specifies whether it's the system or user root. -->
 *    <!ATTLIST root
 *              type (system|user) #REQUIRED >
 *
 *    <!-- Each node has a map representing its preferences (if any),
 *         and one node for each child (if any). -->
 *    <!ELEMENT node (map, node*) >
 *
 *    <!-- Additionally, each node has a name attribute -->
 *    <!ATTLIST node
 *              name CDATA #REQUIRED >
 *
 *    <!-- A map represents the preferences stored at a node (if any). -->
 *    <!ELEMENT map (entry*) >
 *
 *    <!-- An entry represents a single preference, which is simply
 *          a key-value pair. -->
 *    <!ELEMENT entry EMPTY >
 *    <!ATTLIST entry
 *              key   CDATA #REQUIRED
 *              value CDATA #REQUIRED >
 * }</pre>
 *
 * Every <tt>Preferences</tt> implementation must have an associated {@link
 * PreferencesFactory} implementation.  Every Java(TM) SE implementation must provide
 * some means of specifying which <tt>PreferencesFactory</tt> implementation
 * is used to generate the root preferences nodes.  This allows the
 * administrator to replace the default preferences implementation with an
 * alternative implementation.
 *
 * <p>Implementation note: In Sun's JRE, the <tt>PreferencesFactory</tt>
 * implementation is located as follows:
 *
 * <ol>
 *
 * <li><p>If the system property
 * <tt>java.util.prefs.PreferencesFactory</tt> is defined, then it is
 * taken to be the fully-qualified name of a class implementing the
 * <tt>PreferencesFactory</tt> interface.  The class is loaded and
 * instantiated; if this process fails then an unspecified error is
 * thrown.</p></li>
 *
 * <li><p> If a <tt>PreferencesFactory</tt> implementation class file
 * has been installed in a jar file that is visible to the
 * {@link java.lang.ClassLoader#getSystemClassLoader system class loader},
 * and that jar file contains a provider-configuration file named
 * <tt>java.util.prefs.PreferencesFactory</tt> in the resource
 * directory <tt>META-INF/services</tt>, then the first class name
 * specified in that file is taken.  If more than one such jar file is
 * provided, the first one found will be used.  The class is loaded
 * and instantiated; if this process fails then an unspecified error
 * is thrown.  </p></li>
 *
 * <li><p>Finally, if neither the above-mentioned system property nor
 * an extension jar file is provided, then the system-wide default
 * <tt>PreferencesFactory</tt> implementation for the underlying
 * platform is loaded and instantiated.</p></li>
 *
 * </ol>
 *
 * <p>
 *  偏好数据的分层集合中的节点。此类允许应用程序存储和检索用户和系统首选项和配置数据。此数据持久存储在实现相关的后备存储中。典型的实现包括平面文件,操作系统特定的注册表,目录服务器和SQL数据库。
 * 这个类的用户不需要关心后台存储的细节。
 * 
 *  <p>偏好节点有两个单独的树,一个用于用户偏好,一个用于系统偏好。每个用户具有单独的用户首选项树,并且给定系统中的所有用户共享相同的系统首选项树。 "用户"和"系统"的精确描述将随着实现而变​​化。
 * 存储在用户偏好树中的典型信息可以包括用于特定应用的字体选择,颜色选择或优选的窗口位置和大小。存储在系统偏好树中的典型信息可能包括应用程序的安装配置数据。
 * 
 *  <p>偏好树中的节点以与分层文件系统中的目录相似的方式命名。偏好树中的每个节点具有<i>节点名</i>(其不一定是唯一的),唯一的<i>绝对路径名</i>和路径名<i>到每个祖先包括它自己。
 * 
 * <p>根节点具有空字符串("")的节点名称。每个其他节点都有一个任意节点名称,在创建时指定。对此名称的唯一限制是它不能是空字符串,并且不能包含斜杠字符('/')。
 * 
 *  <p>根节点的绝对路径名为<tt>"/"</tt>。根节点的子节点具有<tt>"/"+ </tt> <i>&lt; node name&gt; </i>的绝对路径名。
 * 所有其他节点具有<i>&lt;父的绝对路径名&gt; </i> <tt> +"/"+ </tt> <i>&lt; node name&gt; </i>的绝对路径名。
 * 请注意,所有绝对路径名以斜杠字符开头。
 * 
 *  <p>节点<n>相对于其祖先</i>的路径名称只是必须附加到</i>绝对路径的字符串名称,以便形成<i>的绝对路径名,其中删除了初始斜杠字符(如果存在)。注意：
 * <ul>
 *  <li>没有相对路径名以斜杠字符开头。 <li>每个节点相对于自身的路径名称都是空字符串。 <li>每个节点相对于其父节点的路径名称是其节点名称(根节点除外,它没有父节点)。
 *  <li>每个节点相对于根的路径名称都是其绝对路径名称,并删除了初始斜杠字符。
 * </ul>
 * 
 *  <p>最后请注意：
 * <ul>
 * <li>没有路径名称包含多个连续的斜杠字符。 <li>除了根的绝对路径名称之外,没有路径名以斜杠字符结尾。 <li>符合这两条规则的任何字符串都是有效的路径名。
 * </ul>
 * 
 *  <p>允许修改首选项数据的所有方法异步操作;它们可以立即返回,并且改变将最终传播到具有实现相关延迟的持久后备存储。 <tt> flush </tt>方法可用于同步强制对后备存储的更新。
 *  Java虚拟机的正常终止将不会导致等待更新的丢失 - 在终止时不需要显式的<tt> flush </t>>调用,以确保等待更新被持久化。
 * 
 *  <p>从<tt>首选项</tt>对象读取首选项的所有方法都需要调用者提供默认值。如果之前未设置任何值<i>或后备存储器不可用,则会返回默认值。
 * </i>其目的是允许应用程序操作,即使有稍微降低的功能,即使后备存储不可用。有几种方法,如<tt> flush </tt>,如果后备存储不可用,就有语义阻止它们操作。
 * 普通应用程序应该不需要调用任何这些方法,这可以通过它们被声明抛出{@link BackingStoreException}的事实来识别。
 * 
 * <p>此类中的方法可以由单个JVM中的多个线程并发调用,而不需要外部同步,并且结果将等同于一些串行执行。
 * 如果这个类通过将它们的偏好数据存储在同一后备存储器中的多个JVMs同时使用,则数据存储器将不会被破坏,但是关于偏好数据的一致性没有做出其他保证。
 * 
 *  <p>此类包含导出/导入功能,允许将"首选项"导出到XML文档,以及将表示首选项的XML文档"导入"回系统。该设施可以用于备份偏好树的全部或部分,并且随后从备份中恢复。
 * 
 *  <p> XML文档具有以下DOCTYPE声明：<pre> {@ code
 * <!DOCTYPE preferences SYSTEM "http://java.sun.com/dtd/preferences.dtd">
 *  } </pre>请注意,在导出或导入首选项时,系统URI(http://java.sun.com/dtd/preferences.dtd)未被</i>访问;它只是作为一个字符串来唯一标识DTD,它是：
 * <pre> {@ code。
 * <?xml version="1.0" encoding="UTF-8"?>
 * 
 * <!-- DTD for a Preferences tree. -->
 * 
 *  <！ - 首选项元素位于XML文档的根位置
 * representing a Preferences tree. -->
 * <!ELEMENT preferences (root)>
 * 
 *  <！ -  preferences元素包含可选的版本属性,
 * which specifies version of DTD. -->
 * <!ATTLIST preferences EXTERNAL_XML_VERSION CDATA "0.0" >
 * 
 *  <！ - 根元素有一个映射表示根的首选项
 * (if any), and one node for each child of the root (if any). -->
 * <!ELEMENT root (map, node*) >
 * 
 *  <！ - 此外,根包含一个type属性,它
 * specifies whether it's the system or user root. -->
 *  <！ATTLIST root
 * type (system|user) #REQUIRED >
 * 
 *  <！ - 每个节点都有一个映射表示其首选项(如果有)
 * and one node for each child (if any). -->
 * <!ELEMENT node (map, node*) >
 * 
 * <!-- Additionally, each node has a name attribute -->
 *  <！ATTLIST节点
 * name CDATA #REQUIRED >
 * 
 * <!-- A map represents the preferences stored at a node (if any). -->
 * <!ELEMENT map (entry*) >
 * 
 *  <！ - 一个条目表示单个首选项,这是简单的
 * a key-value pair. -->
 * <!ELEMENT entry EMPTY >
 * <！ATTLIST entry key CDATA #REQUIRED
 * value CDATA #REQUIRED >
 *  } </pre>
 * 
 *  每个<tt>偏好设置</tt>实施必须具有关联的{@link PreferencesFactory}实现。
 * 每个Java(TM)SE实现必须提供一些方法来指定哪个<tt> PreferencesFactory </tt>实现用于生成根首选项节点。这允许管理员用替代实现替换默认首选项实现。
 * 
 *  <p>实施注意事项：在Sun的JRE中,<tt> PreferencesFactory </tt>实施如下：
 * 
 * <ol>
 * 
 *  <li> <p>如果定义了系统属性<tt> java.util.prefs.PreferencesFactory </tt>,则它将被视为实现<tt> PreferencesFactory </tt>
 * 的类的完全限定名称>接口。
 * 类被加载和实例化;如果此过程失败,则会抛出未指定的错误。</p> </li>。
 * 
 *  <li> <p>如果在{@link java.lang.ClassLoader#getSystemClassLoader系统类加载器}可见的jar文件中安装了<tt> PreferencesFacto
 * ry </tt>实现类文件,并且该jar文件在资源目录<tt> META-INF / services </tt>中包含名为<tt> java.util.prefs.PreferencesFactory
 *  </tt>的提供程序配置文件,则会采用该文件中指定的第一个类名。
 * 如果提供了多个这样的jar文件,则将使用找到的第一个jar文件。类被加载和实例化;如果此过程失败,则抛出未指定的错误。 </p> </li>。
 * 
 * <li> <p>最后,如果既不提供上述系统属性也不提供扩展jar文件,则会加载并实例化基础平台的系统级默认<tt> PreferencesFactory </tt>实现。 p> </li>
 * 
 * </ol>
 * 
 * 
 * @author  Josh Bloch
 * @since   1.4
 */
public abstract class Preferences {

    private static final PreferencesFactory factory = factory();

    private static PreferencesFactory factory() {
        // 1. Try user-specified system property
        String factoryName = AccessController.doPrivileged(
            new PrivilegedAction<String>() {
                public String run() {
                    return System.getProperty(
                        "java.util.prefs.PreferencesFactory");}});
        if (factoryName != null) {
            // FIXME: This code should be run in a doPrivileged and
            // not use the context classloader, to avoid being
            // dependent on the invoking thread.
            // Checking AllPermission also seems wrong.
            try {
                return (PreferencesFactory)
                    Class.forName(factoryName, false,
                                  ClassLoader.getSystemClassLoader())
                    .newInstance();
            } catch (Exception ex) {
                try {
                    // workaround for javaws, plugin,
                    // load factory class using non-system classloader
                    SecurityManager sm = System.getSecurityManager();
                    if (sm != null) {
                        sm.checkPermission(new java.security.AllPermission());
                    }
                    return (PreferencesFactory)
                        Class.forName(factoryName, false,
                                      Thread.currentThread()
                                      .getContextClassLoader())
                        .newInstance();
                } catch (Exception e) {
                    throw new InternalError(
                        "Can't instantiate Preferences factory "
                        + factoryName, e);
                }
            }
        }

        return AccessController.doPrivileged(
            new PrivilegedAction<PreferencesFactory>() {
                public PreferencesFactory run() {
                    return factory1();}});
    }

    private static PreferencesFactory factory1() {
        // 2. Try service provider interface
        Iterator<PreferencesFactory> itr = ServiceLoader
            .load(PreferencesFactory.class, ClassLoader.getSystemClassLoader())
            .iterator();

        // choose first provider instance
        while (itr.hasNext()) {
            try {
                return itr.next();
            } catch (ServiceConfigurationError sce) {
                if (sce.getCause() instanceof SecurityException) {
                    // Ignore the security exception, try the next provider
                    continue;
                }
                throw sce;
            }
        }

        // 3. Use platform-specific system-wide default
        String osName = System.getProperty("os.name");
        String platformFactory;
        if (osName.startsWith("Windows")) {
            platformFactory = "java.util.prefs.WindowsPreferencesFactory";
        } else if (osName.contains("OS X")) {
            platformFactory = "java.util.prefs.MacOSXPreferencesFactory";
        } else {
            platformFactory = "java.util.prefs.FileSystemPreferencesFactory";
        }
        try {
            return (PreferencesFactory)
                Class.forName(platformFactory, false,
                              Preferences.class.getClassLoader()).newInstance();
        } catch (Exception e) {
            throw new InternalError(
                "Can't instantiate platform default Preferences factory "
                + platformFactory, e);
        }
    }

    /**
     * Maximum length of string allowed as a key (80 characters).
     * <p>
     *  允许作为键的字符串的最大长度(80个字符)。
     * 
     */
    public static final int MAX_KEY_LENGTH = 80;

    /**
     * Maximum length of string allowed as a value (8192 characters).
     * <p>
     *  允许作为值的字符串的最大长度(8192个字符)。
     * 
     */
    public static final int MAX_VALUE_LENGTH = 8*1024;

    /**
     * Maximum length of a node name (80 characters).
     * <p>
     *  节点名称的最大长度(80个字符)。
     * 
     */
    public static final int MAX_NAME_LENGTH = 80;

    /**
     * Returns the preference node from the calling user's preference tree
     * that is associated (by convention) with the specified class's package.
     * The convention is as follows: the absolute path name of the node is the
     * fully qualified package name, preceded by a slash (<tt>'/'</tt>), and
     * with each period (<tt>'.'</tt>) replaced by a slash.  For example the
     * absolute path name of the node associated with the class
     * <tt>com.acme.widget.Foo</tt> is <tt>/com/acme/widget</tt>.
     *
     * <p>This convention does not apply to the unnamed package, whose
     * associated preference node is <tt>&lt;unnamed&gt;</tt>.  This node
     * is not intended for long term use, but for convenience in the early
     * development of programs that do not yet belong to a package, and
     * for "throwaway" programs.  <i>Valuable data should not be stored
     * at this node as it is shared by all programs that use it.</i>
     *
     * <p>A class <tt>Foo</tt> wishing to access preferences pertaining to its
     * package can obtain a preference node as follows: <pre>
     *    static Preferences prefs = Preferences.userNodeForPackage(Foo.class);
     * </pre>
     * This idiom obviates the need for using a string to describe the
     * preferences node and decreases the likelihood of a run-time failure.
     * (If the class name is misspelled, it will typically result in a
     * compile-time error.)
     *
     * <p>Invoking this method will result in the creation of the returned
     * node and its ancestors if they do not already exist.  If the returned
     * node did not exist prior to this call, this node and any ancestors that
     * were created by this call are not guaranteed to become permanent until
     * the <tt>flush</tt> method is called on the returned node (or one of its
     * ancestors or descendants).
     *
     * <p>
     *  从调用用户的首选项树返回偏好节点,该偏好树与指定类的包关联(按照约定)。约定如下：节点的绝对路径名是完全限定包名称,前面带有斜杠(<tt>'/'</tt>),并且每个句点(<tt>'。
     * '< tt>)替换为斜杠。例如,与类<tt> com.acme.widget.Foo </tt>关联的节点的绝对路径名为<tt> / com / acme / widget </tt>。
     * 
     *  <p>此约定不适用于其相关联的首选项节点为<tt>&lt;无名的&gt; </tt>的未命名包。此节点不是为了长期使用,而是为了方便早期开发尚不属于包的程序和"一次性"程序。
     *  <i>有价值的数据不应存储在此节点上,因为它使用它的所有程序共享。</i>。
     * 
     *  <p>希望访问与其包相关的首选项的类<tt> Foo </tt>可以获得首选节点,如下所示：<pre> static Preferences prefs = Preferences.userNodeF
     * orPackage(Foo.class);。
     * </pre>
     * 这个习语消除了使用字符串来描述偏好节点并降低运行时失败的可能性的需要。 (如果类名拼写错误,通常会导致编译时错误。)
     * 
     *  <p>调用此方法将导致创建返回的节点及其祖先(如果它们尚不存在)。
     * 如果在此调用之前返回的节点不存在,则此调用创建的此节点和任何祖先不能保证永久性,直到在返回的节点上调用<tt> flush </tt>方法(或其祖先或后代)。
     * 
     * 
     * @param c the class for whose package a user preference node is desired.
     * @return the user preference node associated with the package of which
     *         <tt>c</tt> is a member.
     * @throws NullPointerException if <tt>c</tt> is <tt>null</tt>.
     * @throws SecurityException if a security manager is present and
     *         it denies <tt>RuntimePermission("preferences")</tt>.
     * @see    RuntimePermission
     */
    public static Preferences userNodeForPackage(Class<?> c) {
        return userRoot().node(nodeName(c));
    }

    /**
     * Returns the preference node from the system preference tree that is
     * associated (by convention) with the specified class's package.  The
     * convention is as follows: the absolute path name of the node is the
     * fully qualified package name, preceded by a slash (<tt>'/'</tt>), and
     * with each period (<tt>'.'</tt>) replaced by a slash.  For example the
     * absolute path name of the node associated with the class
     * <tt>com.acme.widget.Foo</tt> is <tt>/com/acme/widget</tt>.
     *
     * <p>This convention does not apply to the unnamed package, whose
     * associated preference node is <tt>&lt;unnamed&gt;</tt>.  This node
     * is not intended for long term use, but for convenience in the early
     * development of programs that do not yet belong to a package, and
     * for "throwaway" programs.  <i>Valuable data should not be stored
     * at this node as it is shared by all programs that use it.</i>
     *
     * <p>A class <tt>Foo</tt> wishing to access preferences pertaining to its
     * package can obtain a preference node as follows: <pre>
     *  static Preferences prefs = Preferences.systemNodeForPackage(Foo.class);
     * </pre>
     * This idiom obviates the need for using a string to describe the
     * preferences node and decreases the likelihood of a run-time failure.
     * (If the class name is misspelled, it will typically result in a
     * compile-time error.)
     *
     * <p>Invoking this method will result in the creation of the returned
     * node and its ancestors if they do not already exist.  If the returned
     * node did not exist prior to this call, this node and any ancestors that
     * were created by this call are not guaranteed to become permanent until
     * the <tt>flush</tt> method is called on the returned node (or one of its
     * ancestors or descendants).
     *
     * <p>
     *  从与指定类的包关联(按照约定)的系统首选项树返回首选项节点。约定如下：节点的绝对路径名是完全限定包名称,前面带有斜杠(<tt>'/'</tt>),并且每个句点(<tt>'。'< tt>)替换为斜杠。
     * 例如,与类<tt> com.acme.widget.Foo </tt>关联的节点的绝对路径名为<tt> / com / acme / widget </tt>。
     * 
     *  <p>此约定不适用于其相关联的首选项节点为<tt>&lt;无名的&gt; </tt>的未命名包。此节点不是为了长期使用,而是为了方便早期开发尚不属于包的程序和"一次性"程序。
     *  <i>有价值的数据不应存储在此节点上,因为它使用它的所有程序共享。</i>。
     * 
     * <p>希望访问与其包相关的首选项的类<tt> Foo </tt>可以获得如下的首选节点：<pre> static Preferences prefs = Preferences.systemNodeFo
     * rPackage(Foo.class);。
     * </pre>
     *  这个习语消除了使用字符串来描述偏好节点并降低运行时失败的可能性的需要。 (如果类名拼写错误,通常会导致编译时错误。)
     * 
     *  <p>调用此方法将导致创建返回的节点及其祖先(如果它们尚不存在)。
     * 如果在此调用之前返回的节点不存在,则此调用创建的此节点和任何祖先不能保证永久性,直到在返回的节点上调用<tt> flush </tt>方法(或其祖先或后代)。
     * 
     * 
     * @param c the class for whose package a system preference node is desired.
     * @return the system preference node associated with the package of which
     *         <tt>c</tt> is a member.
     * @throws NullPointerException if <tt>c</tt> is <tt>null</tt>.
     * @throws SecurityException if a security manager is present and
     *         it denies <tt>RuntimePermission("preferences")</tt>.
     * @see    RuntimePermission
     */
    public static Preferences systemNodeForPackage(Class<?> c) {
        return systemRoot().node(nodeName(c));
    }

    /**
     * Returns the absolute path name of the node corresponding to the package
     * of the specified object.
     *
     * <p>
     *  返回与指定对象的包对应的节点的绝对路径名。
     * 
     * 
     * @throws IllegalArgumentException if the package has node preferences
     *         node associated with it.
     */
    private static String nodeName(Class<?> c) {
        if (c.isArray())
            throw new IllegalArgumentException(
                "Arrays have no associated preferences node.");
        String className = c.getName();
        int pkgEndIndex = className.lastIndexOf('.');
        if (pkgEndIndex < 0)
            return "/<unnamed>";
        String packageName = className.substring(0, pkgEndIndex);
        return "/" + packageName.replace('.', '/');
    }

    /**
     * This permission object represents the permission required to get
     * access to the user or system root (which in turn allows for all
     * other operations).
     * <p>
     *  此权限对象表示访问用户或系统根目录所需的权限(依次允许所有其他操作)。
     * 
     */
    private static Permission prefsPerm = new RuntimePermission("preferences");

    /**
     * Returns the root preference node for the calling user.
     *
     * <p>
     *  返回主叫用户的根首选节点。
     * 
     * 
     * @return the root preference node for the calling user.
     * @throws SecurityException If a security manager is present and
     *         it denies <tt>RuntimePermission("preferences")</tt>.
     * @see    RuntimePermission
     */
    public static Preferences userRoot() {
        SecurityManager security = System.getSecurityManager();
        if (security != null)
            security.checkPermission(prefsPerm);

        return factory.userRoot();
    }

    /**
     * Returns the root preference node for the system.
     *
     * <p>
     *  返回系统的根首选节点。
     * 
     * 
     * @return the root preference node for the system.
     * @throws SecurityException If a security manager is present and
     *         it denies <tt>RuntimePermission("preferences")</tt>.
     * @see    RuntimePermission
     */
    public static Preferences systemRoot() {
        SecurityManager security = System.getSecurityManager();
        if (security != null)
            security.checkPermission(prefsPerm);

        return factory.systemRoot();
    }

    /**
     * Sole constructor. (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    protected Preferences() {
    }

    /**
     * Associates the specified value with the specified key in this
     * preference node.
     *
     * <p>
     *  将指定的值与此首选项节点中指定的键相关联。
     * 
     * 
     * @param key key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     * @throws NullPointerException if key or value is <tt>null</tt>.
     * @throws IllegalArgumentException if <tt>key.length()</tt> exceeds
     *       <tt>MAX_KEY_LENGTH</tt> or if <tt>value.length</tt> exceeds
     *       <tt>MAX_VALUE_LENGTH</tt>.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     */
    public abstract void put(String key, String value);

    /**
     * Returns the value associated with the specified key in this preference
     * node.  Returns the specified default if there is no value associated
     * with the key, or the backing store is inaccessible.
     *
     * <p>Some implementations may store default values in their backing
     * stores.  If there is no value associated with the specified key
     * but there is such a <i>stored default</i>, it is returned in
     * preference to the specified default.
     *
     * <p>
     * 返回与此首选项节点中指定键关联的值。如果没有与密钥关联的值,或返回存储不可访问,则返回指定的缺省值。
     * 
     *  <p>一些实现可以在其后备存储中存储默认值。如果没有与指定键相关联的值,但存在此类<i>存储的默认值</i>,则优先返回指定的默认值。
     * 
     * 
     * @param key key whose associated value is to be returned.
     * @param def the value to be returned in the event that this
     *        preference node has no value associated with <tt>key</tt>.
     * @return the value associated with <tt>key</tt>, or <tt>def</tt>
     *         if no value is associated with <tt>key</tt>, or the backing
     *         store is inaccessible.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.  (A
     *         <tt>null</tt> value for <tt>def</tt> <i>is</i> permitted.)
     */
    public abstract String get(String key, String def);

    /**
     * Removes the value associated with the specified key in this preference
     * node, if any.
     *
     * <p>If this implementation supports <i>stored defaults</i>, and there is
     * such a default for the specified preference, the stored default will be
     * "exposed" by this call, in the sense that it will be returned
     * by a succeeding call to <tt>get</tt>.
     *
     * <p>
     *  删除与此首选项节点中指定键相关联的值(如果有)。
     * 
     *  <p>如果此实现支持<i>存储的默认值</i>,并且对于指定的首选项有这样的默认值,则存储的默认值将通过此调用"暴露",意味着它将由后续调用<tt> get </tt>。
     * 
     * 
     * @param key key whose mapping is to be removed from the preference node.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     */
    public abstract void remove(String key);

    /**
     * Removes all of the preferences (key-value associations) in this
     * preference node.  This call has no effect on any descendants
     * of this node.
     *
     * <p>If this implementation supports <i>stored defaults</i>, and this
     * node in the preferences hierarchy contains any such defaults,
     * the stored defaults will be "exposed" by this call, in the sense that
     * they will be returned by succeeding calls to <tt>get</tt>.
     *
     * <p>
     *  删除此首选项节点中的所有首选项(键值关联)。此调用对此节点的任何后代没有影响。
     * 
     *  <p>如果此实现支持<i>存储的默认值</i>,并且首选项层次结构中的此节点包含任何此类默认值,则存储的默认值将由此调用"暴露",意味着它们将由后续对<tt> get </tt>的调用。
     * 
     * 
     * @throws BackingStoreException if this operation cannot be completed
     *         due to a failure in the backing store, or inability to
     *         communicate with it.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #removeNode()
     */
    public abstract void clear() throws BackingStoreException;

    /**
     * Associates a string representing the specified int value with the
     * specified key in this preference node.  The associated string is the
     * one that would be returned if the int value were passed to
     * {@link Integer#toString(int)}.  This method is intended for use in
     * conjunction with {@link #getInt}.
     *
     * <p>
     *  将表示指定int值的字符串与此首选项节点中的指定键相关联。如果int值被传递给{@link Integer#toString(int)},则相关联的字符串将被返回。
     * 此方法旨在与{@link #getInt}结合使用。
     * 
     * 
     * @param key key with which the string form of value is to be associated.
     * @param value value whose string form is to be associated with key.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @throws IllegalArgumentException if <tt>key.length()</tt> exceeds
     *         <tt>MAX_KEY_LENGTH</tt>.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #getInt(String,int)
     */
    public abstract void putInt(String key, int value);

    /**
     * Returns the int value represented by the string associated with the
     * specified key in this preference node.  The string is converted to
     * an integer as by {@link Integer#parseInt(String)}.  Returns the
     * specified default if there is no value associated with the key,
     * the backing store is inaccessible, or if
     * <tt>Integer.parseInt(String)</tt> would throw a {@link
     * NumberFormatException} if the associated value were passed.  This
     * method is intended for use in conjunction with {@link #putInt}.
     *
     * <p>If the implementation supports <i>stored defaults</i> and such a
     * default exists, is accessible, and could be converted to an int
     * with <tt>Integer.parseInt</tt>, this int is returned in preference to
     * the specified default.
     *
     * <p>
     * 返回由此首选项节点中与指定键关联的字符串表示的int值。该字符串通过{@link Integer#parseInt(String)}转换为整数。
     * 如果没有与键相关联的值,后备存储器不可访问,或者如果传递了相关值,则<tt> Integer.parseInt(String)</tt>将抛出{@link NumberFormatException},
     * 则返回指定的默认值。
     * 返回由此首选项节点中与指定键关联的字符串表示的int值。该字符串通过{@link Integer#parseInt(String)}转换为整数。此方法旨在与{@link #putInt}结合使用。
     * 
     *  <p>如果实现支持<i>存储的默认值</i>,并且此类默认值存在,可访问,并且可以转换为<tt> Integer.parseInt </tt>到指定的默认值。
     * 
     * 
     * @param key key whose associated value is to be returned as an int.
     * @param def the value to be returned in the event that this
     *        preference node has no value associated with <tt>key</tt>
     *        or the associated value cannot be interpreted as an int,
     *        or the backing store is inaccessible.
     * @return the int value represented by the string associated with
     *         <tt>key</tt> in this preference node, or <tt>def</tt> if the
     *         associated value does not exist or cannot be interpreted as
     *         an int.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @see #putInt(String,int)
     * @see #get(String,String)
     */
    public abstract int getInt(String key, int def);

    /**
     * Associates a string representing the specified long value with the
     * specified key in this preference node.  The associated string is the
     * one that would be returned if the long value were passed to
     * {@link Long#toString(long)}.  This method is intended for use in
     * conjunction with {@link #getLong}.
     *
     * <p>
     *  将表示指定长整型值的字符串与此首选项节点中的指定键相关联。如果长值传递给{@link Long#toString(long)},则相关联的字符串将被返回。
     * 此方法旨在与{@link #getLong}结合使用。
     * 
     * 
     * @param key key with which the string form of value is to be associated.
     * @param value value whose string form is to be associated with key.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @throws IllegalArgumentException if <tt>key.length()</tt> exceeds
     *         <tt>MAX_KEY_LENGTH</tt>.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #getLong(String,long)
     */
    public abstract void putLong(String key, long value);

    /**
     * Returns the long value represented by the string associated with the
     * specified key in this preference node.  The string is converted to
     * a long as by {@link Long#parseLong(String)}.  Returns the
     * specified default if there is no value associated with the key,
     * the backing store is inaccessible, or if
     * <tt>Long.parseLong(String)</tt> would throw a {@link
     * NumberFormatException} if the associated value were passed.  This
     * method is intended for use in conjunction with {@link #putLong}.
     *
     * <p>If the implementation supports <i>stored defaults</i> and such a
     * default exists, is accessible, and could be converted to a long
     * with <tt>Long.parseLong</tt>, this long is returned in preference to
     * the specified default.
     *
     * <p>
     *  返回由此首选项节点中与指定键关联的字符串表示的长整型值。该字符串由{@link Long#parseLong(String)}转换为long。
     * 如果没有与键相关联的值,后备存储器不可访问,或者如果传递了相关值,则<tt> Long.parseLong(String)</tt>将抛出{@link NumberFormatException},则返
     * 回指定的默认值。
     *  返回由此首选项节点中与指定键关联的字符串表示的长整型值。该字符串由{@link Long#parseLong(String)}转换为long。此方法旨在与{@link #putLong}结合使用。
     * 
     * <p>如果实施支持<i>存储的默认值</i>,并且此类默认值存在,可访问,并且可以通过<tt> Long.parseLong </tt>转换为long,到指定的默认值。
     * 
     * 
     * @param key key whose associated value is to be returned as a long.
     * @param def the value to be returned in the event that this
     *        preference node has no value associated with <tt>key</tt>
     *        or the associated value cannot be interpreted as a long,
     *        or the backing store is inaccessible.
     * @return the long value represented by the string associated with
     *         <tt>key</tt> in this preference node, or <tt>def</tt> if the
     *         associated value does not exist or cannot be interpreted as
     *         a long.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @see #putLong(String,long)
     * @see #get(String,String)
     */
    public abstract long getLong(String key, long def);

    /**
     * Associates a string representing the specified boolean value with the
     * specified key in this preference node.  The associated string is
     * <tt>"true"</tt> if the value is true, and <tt>"false"</tt> if it is
     * false.  This method is intended for use in conjunction with
     * {@link #getBoolean}.
     *
     * <p>
     *  将表示指定布尔值的字符串与此首选项节点中指定的键相关联。如果值为true,则关联的字符串为<tt>"true"</tt>,如果值为false,则为<tt>"false"</tt>。
     * 此方法旨在与{@link #getBoolean}结合使用。
     * 
     * 
     * @param key key with which the string form of value is to be associated.
     * @param value value whose string form is to be associated with key.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @throws IllegalArgumentException if <tt>key.length()</tt> exceeds
     *         <tt>MAX_KEY_LENGTH</tt>.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #getBoolean(String,boolean)
     * @see #get(String,String)
     */
    public abstract void putBoolean(String key, boolean value);

    /**
     * Returns the boolean value represented by the string associated with the
     * specified key in this preference node.  Valid strings
     * are <tt>"true"</tt>, which represents true, and <tt>"false"</tt>, which
     * represents false.  Case is ignored, so, for example, <tt>"TRUE"</tt>
     * and <tt>"False"</tt> are also valid.  This method is intended for use in
     * conjunction with {@link #putBoolean}.
     *
     * <p>Returns the specified default if there is no value
     * associated with the key, the backing store is inaccessible, or if the
     * associated value is something other than <tt>"true"</tt> or
     * <tt>"false"</tt>, ignoring case.
     *
     * <p>If the implementation supports <i>stored defaults</i> and such a
     * default exists and is accessible, it is used in preference to the
     * specified default, unless the stored default is something other than
     * <tt>"true"</tt> or <tt>"false"</tt>, ignoring case, in which case the
     * specified default is used.
     *
     * <p>
     *  返回由与此首选项节点中的指定键关联的字符串表示的布尔值。有效字符串为<tt>"true"</tt>(表示true)和<tt>"false"</tt>,表示false。
     * 大小写被忽略,因此,例如<tt>"TRUE"</tt>和<tt>"False"</tt>也有效。此方法旨在与{@link #putBoolean}结合使用。
     * 
     *  <p>如果没有与键相关联的值,后备存储器无法访问,或者相关值不是<tt>"true"</tt>或<tt>"false"的其他值,则返回指定的默认值< / tt>,忽略大小写。
     * 
     *  <p>如果实现支持<i>存储的默认值</i>,并且此类默认值存在且可访问,则优先于指定的默认值使用,除非存储的默认值为<tt>"true" / tt>或<tt>"false"</tt>,忽略大小写,在
     * 这种情况下使用指定的默认值。
     * 
     * 
     * @param key key whose associated value is to be returned as a boolean.
     * @param def the value to be returned in the event that this
     *        preference node has no value associated with <tt>key</tt>
     *        or the associated value cannot be interpreted as a boolean,
     *        or the backing store is inaccessible.
     * @return the boolean value represented by the string associated with
     *         <tt>key</tt> in this preference node, or <tt>def</tt> if the
     *         associated value does not exist or cannot be interpreted as
     *         a boolean.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @see #get(String,String)
     * @see #putBoolean(String,boolean)
     */
    public abstract boolean getBoolean(String key, boolean def);

    /**
     * Associates a string representing the specified float value with the
     * specified key in this preference node.  The associated string is the
     * one that would be returned if the float value were passed to
     * {@link Float#toString(float)}.  This method is intended for use in
     * conjunction with {@link #getFloat}.
     *
     * <p>
     * 将表示指定浮点值的字符串与此首选项节点中的指定键相关联。如果float值传递给{@link Float#toString(float)},那么相关的字符串就是返回的字符串。
     * 此方法旨在与{@link #getFloat}结合使用。
     * 
     * 
     * @param key key with which the string form of value is to be associated.
     * @param value value whose string form is to be associated with key.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @throws IllegalArgumentException if <tt>key.length()</tt> exceeds
     *         <tt>MAX_KEY_LENGTH</tt>.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #getFloat(String,float)
     */
    public abstract void putFloat(String key, float value);

    /**
     * Returns the float value represented by the string associated with the
     * specified key in this preference node.  The string is converted to an
     * integer as by {@link Float#parseFloat(String)}.  Returns the specified
     * default if there is no value associated with the key, the backing store
     * is inaccessible, or if <tt>Float.parseFloat(String)</tt> would throw a
     * {@link NumberFormatException} if the associated value were passed.
     * This method is intended for use in conjunction with {@link #putFloat}.
     *
     * <p>If the implementation supports <i>stored defaults</i> and such a
     * default exists, is accessible, and could be converted to a float
     * with <tt>Float.parseFloat</tt>, this float is returned in preference to
     * the specified default.
     *
     * <p>
     *  返回由此首选项节点中与指定键关联的字符串表示的浮点值。该字符串通过{@link Float#parseFloat(String)}转换为整数。
     * 如果没有与键相关联的值,后备存储器不可访问,或者如果传递了相关值,则<tt> Float.parseFloat(String)</tt>会抛出{@link NumberFormatException},
     * 则返回指定的默认值。
     *  返回由此首选项节点中与指定键关联的字符串表示的浮点值。该字符串通过{@link Float#parseFloat(String)}转换为整数。此方法旨在与{@link #putFloat}结合使用。
     * 
     *  <p>如果实现支持<i>存储的默认值</i>,并且这样的默认值存在,可访问,并且可以转换为<tt> Float.parseFloat </tt>到指定的默认值。
     * 
     * 
     * @param key key whose associated value is to be returned as a float.
     * @param def the value to be returned in the event that this
     *        preference node has no value associated with <tt>key</tt>
     *        or the associated value cannot be interpreted as a float,
     *        or the backing store is inaccessible.
     * @return the float value represented by the string associated with
     *         <tt>key</tt> in this preference node, or <tt>def</tt> if the
     *         associated value does not exist or cannot be interpreted as
     *         a float.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @see #putFloat(String,float)
     * @see #get(String,String)
     */
    public abstract float getFloat(String key, float def);

    /**
     * Associates a string representing the specified double value with the
     * specified key in this preference node.  The associated string is the
     * one that would be returned if the double value were passed to
     * {@link Double#toString(double)}.  This method is intended for use in
     * conjunction with {@link #getDouble}.
     *
     * <p>
     *  将表示指定的double值的字符串与此首选项节点中的指定键相关联。如果double值传递给{@link Double#toString(double)},则相关联的字符串将被返回。
     * 此方法旨在与{@link #getDouble}结合使用。
     * 
     * 
     * @param key key with which the string form of value is to be associated.
     * @param value value whose string form is to be associated with key.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @throws IllegalArgumentException if <tt>key.length()</tt> exceeds
     *         <tt>MAX_KEY_LENGTH</tt>.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #getDouble(String,double)
     */
    public abstract void putDouble(String key, double value);

    /**
     * Returns the double value represented by the string associated with the
     * specified key in this preference node.  The string is converted to an
     * integer as by {@link Double#parseDouble(String)}.  Returns the specified
     * default if there is no value associated with the key, the backing store
     * is inaccessible, or if <tt>Double.parseDouble(String)</tt> would throw a
     * {@link NumberFormatException} if the associated value were passed.
     * This method is intended for use in conjunction with {@link #putDouble}.
     *
     * <p>If the implementation supports <i>stored defaults</i> and such a
     * default exists, is accessible, and could be converted to a double
     * with <tt>Double.parseDouble</tt>, this double is returned in preference
     * to the specified default.
     *
     * <p>
     * 返回由此首选项节点中与指定键关联的字符串表示的双精度值。该字符串通过{@link Double#parseDouble(String)}转换为整数。
     * 如果没有与键相关联的值,后备存储器不可访问,或者如果传递了相关值,则<tt> Double.parseDouble(String)</tt>将抛出{@link NumberFormatException}
     * ,则返回指定的默认值。
     * 返回由此首选项节点中与指定键关联的字符串表示的双精度值。该字符串通过{@link Double#parseDouble(String)}转换为整数。
     * 此方法旨在与{@link #putDouble}结合使用。
     * 
     *  <p>如果实现支持<i>存储的默认值</i>,并且此类默认值存在,可访问,并且可以使用<tt> Double.parseDouble </tt>转换为double,到指定的默认值。
     * 
     * 
     * @param key key whose associated value is to be returned as a double.
     * @param def the value to be returned in the event that this
     *        preference node has no value associated with <tt>key</tt>
     *        or the associated value cannot be interpreted as a double,
     *        or the backing store is inaccessible.
     * @return the double value represented by the string associated with
     *         <tt>key</tt> in this preference node, or <tt>def</tt> if the
     *         associated value does not exist or cannot be interpreted as
     *         a double.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
     * @see #putDouble(String,double)
     * @see #get(String,String)
     */
    public abstract double getDouble(String key, double def);

    /**
     * Associates a string representing the specified byte array with the
     * specified key in this preference node.  The associated string is
     * the <i>Base64</i> encoding of the byte array, as defined in <a
     * href=http://www.ietf.org/rfc/rfc2045.txt>RFC 2045</a>, Section 6.8,
     * with one minor change: the string will consist solely of characters
     * from the <i>Base64 Alphabet</i>; it will not contain any newline
     * characters.  Note that the maximum length of the byte array is limited
     * to three quarters of <tt>MAX_VALUE_LENGTH</tt> so that the length
     * of the Base64 encoded String does not exceed <tt>MAX_VALUE_LENGTH</tt>.
     * This method is intended for use in conjunction with
     * {@link #getByteArray}.
     *
     * <p>
     *  将表示指定字节数组的字符串与此首选项节点中指定的键相关联。
     * 相关联的字符串是字节数组的<i> Base64 </i>编码,如<a href=http://www.ietf.org/rfc/rfc2045.txt> RFC 2045 </a>中所定义, 6.8,有
     * 一个小的更改：字符串将只包含来自<i> Base64 Alphabet </i>的字符;它不会包含任何换行符。
     *  将表示指定字节数组的字符串与此首选项节点中指定的键相关联。
     * 请注意,字节数组的最大长度限制为<tt> MAX_VALUE_LENGTH </tt>的四分之三,以便Base64编码字符串的长度不超过<tt> MAX_VALUE_LENGTH </tt>。
     * 此方法旨在与{@link #getByteArray}结合使用。
     * 
     * 
     * @param key key with which the string form of value is to be associated.
     * @param value value whose string form is to be associated with key.
     * @throws NullPointerException if key or value is <tt>null</tt>.
     * @throws IllegalArgumentException if key.length() exceeds MAX_KEY_LENGTH
     *         or if value.length exceeds MAX_VALUE_LENGTH*3/4.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #getByteArray(String,byte[])
     * @see #get(String,String)
     */
    public abstract void putByteArray(String key, byte[] value);

    /**
     * Returns the byte array value represented by the string associated with
     * the specified key in this preference node.  Valid strings are
     * <i>Base64</i> encoded binary data, as defined in <a
     * href=http://www.ietf.org/rfc/rfc2045.txt>RFC 2045</a>, Section 6.8,
     * with one minor change: the string must consist solely of characters
     * from the <i>Base64 Alphabet</i>; no newline characters or
     * extraneous characters are permitted.  This method is intended for use
     * in conjunction with {@link #putByteArray}.
     *
     * <p>Returns the specified default if there is no value
     * associated with the key, the backing store is inaccessible, or if the
     * associated value is not a valid Base64 encoded byte array
     * (as defined above).
     *
     * <p>If the implementation supports <i>stored defaults</i> and such a
     * default exists and is accessible, it is used in preference to the
     * specified default, unless the stored default is not a valid Base64
     * encoded byte array (as defined above), in which case the
     * specified default is used.
     *
     * <p>
     * 返回由此首选项节点中与指定键关联的字符串表示的字节数组值。
     * 有效的字符串是<i> Base64 </i>编码的二进制数据,如<a href=http://www.ietf.org/rfc/rfc2045.txt> RFC 2045 </a>第6.8节中所定义,其
     * 中一个次要更改：字符串必须只包含来自<i> Base64 Alphabet </i>的字符;不允许换行符或无关字符。
     * 返回由此首选项节点中与指定键关联的字符串表示的字节数组值。此方法旨在与{@link #putByteArray}结合使用。
     * 
     *  <p>如果没有与键相关联的值,后备存储器不可访问或者关联值不是有效的Base64编码字节数组(如上定义),则返回指定的默认值。
     * 
     *  <p>如果实施支持<i>存储的默认值</i>,并且此类默认值存在且可访问,则优先于指定的默认值使用,除非存储的默认值不是有效的Base64编码字节数组上面),在这种情况下使用指定的默认值。
     * 
     * 
     * @param key key whose associated value is to be returned as a byte array.
     * @param def the value to be returned in the event that this
     *        preference node has no value associated with <tt>key</tt>
     *        or the associated value cannot be interpreted as a byte array,
     *        or the backing store is inaccessible.
     * @return the byte array value represented by the string associated with
     *         <tt>key</tt> in this preference node, or <tt>def</tt> if the
     *         associated value does not exist or cannot be interpreted as
     *         a byte array.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.  (A
     *         <tt>null</tt> value for <tt>def</tt> <i>is</i> permitted.)
     * @see #get(String,String)
     * @see #putByteArray(String,byte[])
     */
    public abstract byte[] getByteArray(String key, byte[] def);

    /**
     * Returns all of the keys that have an associated value in this
     * preference node.  (The returned array will be of size zero if
     * this node has no preferences.)
     *
     * <p>If the implementation supports <i>stored defaults</i> and there
     * are any such defaults at this node that have not been overridden,
     * by explicit preferences, the defaults are returned in the array in
     * addition to any explicit preferences.
     *
     * <p>
     *  返回在此首选项节点中具有关联值的所有键。 (如果此节点没有首选项,返回的数组的大小为零。)
     * 
     *  <p>如果实施支持<i>存储的默认值</i>,并且在此节点上有任何未被重写的默认值,通过显式首选项,除了任何显式首选项之外,还会在数组中返回默认值。
     * 
     * 
     * @return an array of the keys that have an associated value in this
     *         preference node.
     * @throws BackingStoreException if this operation cannot be completed
     *         due to a failure in the backing store, or inability to
     *         communicate with it.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     */
    public abstract String[] keys() throws BackingStoreException;

    /**
     * Returns the names of the children of this preference node, relative to
     * this node.  (The returned array will be of size zero if this node has
     * no children.)
     *
     * <p>
     * 返回此首选节点相对于此节点的子节点的名称。 (如果此节点没有子节点,返回的数组的大小为零。)
     * 
     * 
     * @return the names of the children of this preference node.
     * @throws BackingStoreException if this operation cannot be completed
     *         due to a failure in the backing store, or inability to
     *         communicate with it.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     */
    public abstract String[] childrenNames() throws BackingStoreException;

    /**
     * Returns the parent of this preference node, or <tt>null</tt> if this is
     * the root.
     *
     * <p>
     *  返回此首选项节点的父项,如果这是根,则返回<tt> null </tt>。
     * 
     * 
     * @return the parent of this preference node.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     */
    public abstract Preferences parent();

    /**
     * Returns the named preference node in the same tree as this node,
     * creating it and any of its ancestors if they do not already exist.
     * Accepts a relative or absolute path name.  Relative path names
     * (which do not begin with the slash character <tt>('/')</tt>) are
     * interpreted relative to this preference node.
     *
     * <p>If the returned node did not exist prior to this call, this node and
     * any ancestors that were created by this call are not guaranteed
     * to become permanent until the <tt>flush</tt> method is called on
     * the returned node (or one of its ancestors or descendants).
     *
     * <p>
     *  返回与此节点相同的树中的命名首选项节点,如果它和它的任何祖先不存在,则创建它。接受相对路径名或绝对路径名。相对路径名(不以斜杠字符<tt>('/')</tt>开头)将相对于此首选项节点进行解释。
     * 
     *  <p>如果在此调用之前返回的节点不存在,则此调用创建的此节点和任何祖先不能保证永久性,直到在返回的节点上调用<tt> flush </tt>方法或其祖先或后代之一)。
     * 
     * 
     * @param pathName the path name of the preference node to return.
     * @return the specified preference node.
     * @throws IllegalArgumentException if the path name is invalid (i.e.,
     *         it contains multiple consecutive slash characters, or ends
     *         with a slash character and is more than one character long).
     * @throws NullPointerException if path name is <tt>null</tt>.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #flush()
     */
    public abstract Preferences node(String pathName);

    /**
     * Returns true if the named preference node exists in the same tree
     * as this node.  Relative path names (which do not begin with the slash
     * character <tt>('/')</tt>) are interpreted relative to this preference
     * node.
     *
     * <p>If this node (or an ancestor) has already been removed with the
     * {@link #removeNode()} method, it <i>is</i> legal to invoke this method,
     * but only with the path name <tt>""</tt>; the invocation will return
     * <tt>false</tt>.  Thus, the idiom <tt>p.nodeExists("")</tt> may be
     * used to test whether <tt>p</tt> has been removed.
     *
     * <p>
     *  如果命名的首选项节点与该节点存在于同一个树中,则返回true。相对路径名(不以斜杠字符<tt>('/')</tt>开头)将相对于此首选项节点进行解释。
     * 
     *  <p>如果已通过{@link #removeNode()}方法删除了此节点(或祖代),则</i>是调用此方法的合法操作,但只有路径名称<tt >""</tt>;该调用将返回<tt> false </tt>
     * 。
     * 因此,习语<tt> p.nodeExists("")</tt>可用于测试<tt> p </tt>是否已被删除。
     * 
     * 
     * @param pathName the path name of the node whose existence
     *        is to be checked.
     * @return true if the specified node exists.
     * @throws BackingStoreException if this operation cannot be completed
     *         due to a failure in the backing store, or inability to
     *         communicate with it.
     * @throws IllegalArgumentException if the path name is invalid (i.e.,
     *         it contains multiple consecutive slash characters, or ends
     *         with a slash character and is more than one character long).
     * @throws NullPointerException if path name is <tt>null</tt>.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method and
     *         <tt>pathName</tt> is not the empty string (<tt>""</tt>).
     */
    public abstract boolean nodeExists(String pathName)
        throws BackingStoreException;

    /**
     * Removes this preference node and all of its descendants, invalidating
     * any preferences contained in the removed nodes.  Once a node has been
     * removed, attempting any method other than {@link #name()},
     * {@link #absolutePath()}, {@link #isUserNode()}, {@link #flush()} or
     * {@link #node(String) nodeExists("")} on the corresponding
     * <tt>Preferences</tt> instance will fail with an
     * <tt>IllegalStateException</tt>.  (The methods defined on {@link Object}
     * can still be invoked on a node after it has been removed; they will not
     * throw <tt>IllegalStateException</tt>.)
     *
     * <p>The removal is not guaranteed to be persistent until the
     * <tt>flush</tt> method is called on this node (or an ancestor).
     *
     * <p>If this implementation supports <i>stored defaults</i>, removing a
     * node exposes any stored defaults at or below this node.  Thus, a
     * subsequent call to <tt>nodeExists</tt> on this node's path name may
     * return <tt>true</tt>, and a subsequent call to <tt>node</tt> on this
     * path name may return a (different) <tt>Preferences</tt> instance
     * representing a non-empty collection of preferences and/or children.
     *
     * <p>
     * 删除此首选项节点及其所有后代,使包含在已删除节点中的任何首选项无效。
     * 删除节点后,尝试使用{@link #name()},{@link #absolutePath()},{@link #isUserNode()},{@link #flush()}或{@在相应的<tt>首选项</tt>实例上的链接#node(String)nodeExists("")}
     * 将失败并显示<tt> IllegalStateException </tt>。
     * 删除此首选项节点及其所有后代,使包含在已删除节点中的任何首选项无效。
     *  (在{@link Object}上定义的方法在删除后仍然可以在节点上调用;它们不会引发<tt> IllegalStateException </tt>。)。
     * 
     *  <p>在此节点(或祖先)上调用<tt> flush </tt>方法之前,不会保证删除操作是持久的。
     * 
     *  <p>如果此实现支持<i>存储的默认值</i>,则删除节点会显示该节点或其下的任何已存储的默认值。
     * 因此,对此节点路径名上的<tt> nodeExists </tt>的后续调用可能返回<tt> true </tt>,并且对此路径名上的<tt>节点</tt>的后续调用可能返回(不同)<tt>首选项</tt>
     * 表示非首选项集合和/或子项的实例。
     *  <p>如果此实现支持<i>存储的默认值</i>,则删除节点会显示该节点或其下的任何已存储的默认值。
     * 
     * 
     * @throws BackingStoreException if this operation cannot be completed
     *         due to a failure in the backing store, or inability to
     *         communicate with it.
     * @throws IllegalStateException if this node (or an ancestor) has already
     *         been removed with the {@link #removeNode()} method.
     * @throws UnsupportedOperationException if this method is invoked on
     *         the root node.
     * @see #flush()
     */
    public abstract void removeNode() throws BackingStoreException;

    /**
     * Returns this preference node's name, relative to its parent.
     *
     * <p>
     *  返回此首选项节点的名称,相对于其父节点。
     * 
     * 
     * @return this preference node's name, relative to its parent.
     */
    public abstract String name();

    /**
     * Returns this preference node's absolute path name.
     *
     * <p>
     *  返回此首选项节点的绝对路径名。
     * 
     * 
     * @return this preference node's absolute path name.
     */
    public abstract String absolutePath();

    /**
     * Returns <tt>true</tt> if this preference node is in the user
     * preference tree, <tt>false</tt> if it's in the system preference tree.
     *
     * <p>
     *  如果此首选项节点在用户首选项树中,则返回<tt> true </tt>,如果它在系统首选项树中,则返回<tt> false </tt>。
     * 
     * 
     * @return <tt>true</tt> if this preference node is in the user
     *         preference tree, <tt>false</tt> if it's in the system
     *         preference tree.
     */
    public abstract boolean isUserNode();

    /**
     * Returns a string representation of this preferences node,
     * as if computed by the expression:<tt>(this.isUserNode() ? "User" :
     * "System") + " Preference Node: " + this.absolutePath()</tt>.
     * <p>
     * 返回此首选项节点的字符串表示形式,如同通过表达式计算：<tt>(this.isUserNode()?"User"："System")+"Preference Node："+ this.absoluteP
     * ath()</tt> 。
     * 
     */
    public abstract String toString();

    /**
     * Forces any changes in the contents of this preference node and its
     * descendants to the persistent store.  Once this method returns
     * successfully, it is safe to assume that all changes made in the
     * subtree rooted at this node prior to the method invocation have become
     * permanent.
     *
     * <p>Implementations are free to flush changes into the persistent store
     * at any time.  They do not need to wait for this method to be called.
     *
     * <p>When a flush occurs on a newly created node, it is made persistent,
     * as are any ancestors (and descendants) that have yet to be made
     * persistent.  Note however that any preference value changes in
     * ancestors are <i>not</i> guaranteed to be made persistent.
     *
     * <p> If this method is invoked on a node that has been removed with
     * the {@link #removeNode()} method, flushSpi() is invoked on this node,
     * but not on others.
     *
     * <p>
     *  强制将此首选项节点及其后代的内容中的任何更改强制更改为持久存储。一旦该方法成功返回,可以安全地假定在方法调用之前在根节点处的子树中进行的所有更改已成为永久性。
     * 
     *  <p>实现可随时将更改刷新到持久存储中。他们不需要等待这个方法被调用。
     * 
     *  <p>当一个新创建的节点发生刷新时,它被设置为持久化,任何祖先(和后代)也必须持久化。然而,请注意,祖先中的任何偏好值变化不能保证被持久化。
     * 
     *  <p>如果在使用{@link #removeNode()}方法删除的节点上调用此方法,则会在此节点上调用flushSpi(),而在其他节点上则不会调用。
     * 
     * 
     * @throws BackingStoreException if this operation cannot be completed
     *         due to a failure in the backing store, or inability to
     *         communicate with it.
     * @see    #sync()
     */
    public abstract void flush() throws BackingStoreException;

    /**
     * Ensures that future reads from this preference node and its
     * descendants reflect any changes that were committed to the persistent
     * store (from any VM) prior to the <tt>sync</tt> invocation.  As a
     * side-effect, forces any changes in the contents of this preference node
     * and its descendants to the persistent store, as if the <tt>flush</tt>
     * method had been invoked on this node.
     *
     * <p>
     *  确保未来从此首选节点及其后代的读取反映在<tt> sync </tt>调用之前提交到持久存储(从任何VM)的任何更改。
     * 作为副作用,将此首选项节点及其后代的内容的任何更改强制到持久存储,就好像已在此节点上调用<tt> flush </tt>方法。
     * 
     * 
     * @throws BackingStoreException if this operation cannot be completed
     *         due to a failure in the backing store, or inability to
     *         communicate with it.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see    #flush()
     */
    public abstract void sync() throws BackingStoreException;

    /**
     * Registers the specified listener to receive <i>preference change
     * events</i> for this preference node.  A preference change event is
     * generated when a preference is added to this node, removed from this
     * node, or when the value associated with a preference is changed.
     * (Preference change events are <i>not</i> generated by the {@link
     * #removeNode()} method, which generates a <i>node change event</i>.
     * Preference change events <i>are</i> generated by the <tt>clear</tt>
     * method.)
     *
     * <p>Events are only guaranteed for changes made within the same JVM
     * as the registered listener, though some implementations may generate
     * events for changes made outside this JVM.  Events may be generated
     * before the changes have been made persistent.  Events are not generated
     * when preferences are modified in descendants of this node; a caller
     * desiring such events must register with each descendant.
     *
     * <p>
     * 注册指定的侦听器以接收此首选项节点的<i>首选项更改事件</i>。当首选项添加到此节点,从此节点删除或与首选项关联的值更改时,会生成首选项更改事件。
     *  (偏好改变事件不是由{@link #removeNode()}方法产生的,它产生<i>节点改变事件</i>。偏好改变事件<i> </i> >由<tt>清除</tt>方法生成)。
     * 
     *  <p>只有在与注册侦听器相同的JVM中进行的更改才能保证事件,但某些实现可能会为在此JVM外部进行的更改生成事件。可以在更改持续之前生成事件。
     * 在此节点的后代中修改首选项时不会生成事件;希望这样的事件的呼叫者必须向每个后代登记。
     * 
     * 
     * @param pcl The preference change listener to add.
     * @throws NullPointerException if <tt>pcl</tt> is null.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #removePreferenceChangeListener(PreferenceChangeListener)
     * @see #addNodeChangeListener(NodeChangeListener)
     */
    public abstract void addPreferenceChangeListener(
        PreferenceChangeListener pcl);

    /**
     * Removes the specified preference change listener, so it no longer
     * receives preference change events.
     *
     * <p>
     *  删除指定的首选项更改侦听器,因此它不再接收首选项更改事件。
     * 
     * 
     * @param pcl The preference change listener to remove.
     * @throws IllegalArgumentException if <tt>pcl</tt> was not a registered
     *         preference change listener on this node.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #addPreferenceChangeListener(PreferenceChangeListener)
     */
    public abstract void removePreferenceChangeListener(
        PreferenceChangeListener pcl);

    /**
     * Registers the specified listener to receive <i>node change events</i>
     * for this node.  A node change event is generated when a child node is
     * added to or removed from this node.  (A single {@link #removeNode()}
     * invocation results in multiple <i>node change events</i>, one for every
     * node in the subtree rooted at the removed node.)
     *
     * <p>Events are only guaranteed for changes made within the same JVM
     * as the registered listener, though some implementations may generate
     * events for changes made outside this JVM.  Events may be generated
     * before the changes have become permanent.  Events are not generated
     * when indirect descendants of this node are added or removed; a
     * caller desiring such events must register with each descendant.
     *
     * <p>Few guarantees can be made regarding node creation.  Because nodes
     * are created implicitly upon access, it may not be feasible for an
     * implementation to determine whether a child node existed in the backing
     * store prior to access (for example, because the backing store is
     * unreachable or cached information is out of date).  Under these
     * circumstances, implementations are neither required to generate node
     * change events nor prohibited from doing so.
     *
     * <p>
     *  注册指定的侦听器以接收此节点的<i>节点更改事件</i>。当子节点添加到该节点或从该节点删除时,会生成节点更改事件。
     *  (单个{@link #removeNode()}调用导致多个节点改变事件,其中一个用于处在移除的节点处的子树中的每个节点)。
     * 
     * <p>只有在与注册侦听器相同的JVM中进行的更改才能保证事件,但某些实现可能会为在此JVM外部进行的更改生成事件。可以在更改变为永久之前生成事件。
     * 添加或删除此节点的间接后代时不生成事件;希望这样的事件的呼叫者必须向每个后代登记。
     * 
     *  <p>对节点创建几乎没有保证。因为节点是在访问时隐式创建的,所以对于实现来说确定在访问之前是否存在后备存储中的子节点(例如,因为后备存储不可达或缓存的信息是过时的)可能是不可行的。
     * 在这些情况下,实现既不需要生成节点改变事件也不禁止这样做。
     * 
     * 
     * @param ncl The <tt>NodeChangeListener</tt> to add.
     * @throws NullPointerException if <tt>ncl</tt> is null.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #removeNodeChangeListener(NodeChangeListener)
     * @see #addPreferenceChangeListener(PreferenceChangeListener)
     */
    public abstract void addNodeChangeListener(NodeChangeListener ncl);

    /**
     * Removes the specified <tt>NodeChangeListener</tt>, so it no longer
     * receives change events.
     *
     * <p>
     *  删除指定的<tt> NodeChangeListener </tt>,因此它不再接收更改事件。
     * 
     * 
     * @param ncl The <tt>NodeChangeListener</tt> to remove.
     * @throws IllegalArgumentException if <tt>ncl</tt> was not a registered
     *         <tt>NodeChangeListener</tt> on this node.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see #addNodeChangeListener(NodeChangeListener)
     */
    public abstract void removeNodeChangeListener(NodeChangeListener ncl);

    /**
     * Emits on the specified output stream an XML document representing all
     * of the preferences contained in this node (but not its descendants).
     * This XML document is, in effect, an offline backup of the node.
     *
     * <p>The XML document will have the following DOCTYPE declaration:
     * <pre>{@code
     * <!DOCTYPE preferences SYSTEM "http://java.sun.com/dtd/preferences.dtd">
     * }</pre>
     * The UTF-8 character encoding will be used.
     *
     * <p>This method is an exception to the general rule that the results of
     * concurrently executing multiple methods in this class yields
     * results equivalent to some serial execution.  If the preferences
     * at this node are modified concurrently with an invocation of this
     * method, the exported preferences comprise a "fuzzy snapshot" of the
     * preferences contained in the node; some of the concurrent modifications
     * may be reflected in the exported data while others may not.
     *
     * <p>
     *  在指定的输出流上发出一个XML文档,该文档表示此节点中包含的所有首选项(但不包含其后代)。这个XML文档实际上是节点的离线备份。
     * 
     *  <p> XML文档将具有以下DOCTYPE声明：<pre> {@ code
     * <!DOCTYPE preferences SYSTEM "http://java.sun.com/dtd/preferences.dtd">
     *  } </pre>将使用UTF-8字符编码。
     * 
     * <p>此方法是一般规则的例外,即在此类中同时执行多个方法的结果产生等同于某些串行执行的结果。
     * 如果在该节点的偏好与该方法的调用同时被修改,则所导出的偏好包括该节点中包含的偏好的"模糊快照";一些并发修改可以反映在导出的数据中,而其他修改可能不反映。
     * 
     * 
     * @param os the output stream on which to emit the XML document.
     * @throws IOException if writing to the specified output stream
     *         results in an <tt>IOException</tt>.
     * @throws BackingStoreException if preference data cannot be read from
     *         backing store.
     * @see    #importPreferences(InputStream)
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     */
    public abstract void exportNode(OutputStream os)
        throws IOException, BackingStoreException;

    /**
     * Emits an XML document representing all of the preferences contained
     * in this node and all of its descendants.  This XML document is, in
     * effect, an offline backup of the subtree rooted at the node.
     *
     * <p>The XML document will have the following DOCTYPE declaration:
     * <pre>{@code
     * <!DOCTYPE preferences SYSTEM "http://java.sun.com/dtd/preferences.dtd">
     * }</pre>
     * The UTF-8 character encoding will be used.
     *
     * <p>This method is an exception to the general rule that the results of
     * concurrently executing multiple methods in this class yields
     * results equivalent to some serial execution.  If the preferences
     * or nodes in the subtree rooted at this node are modified concurrently
     * with an invocation of this method, the exported preferences comprise a
     * "fuzzy snapshot" of the subtree; some of the concurrent modifications
     * may be reflected in the exported data while others may not.
     *
     * <p>
     *  发出一个XML文档,表示此节点及其所有后代中包含的所有首选项。这个XML文档实际上是以节点为根的子树的离线备份。
     * 
     *  <p> XML文档将具有以下DOCTYPE声明：<pre> {@ code
     * <!DOCTYPE preferences SYSTEM "http://java.sun.com/dtd/preferences.dtd">
     *  } </pre>将使用UTF-8字符编码。
     * 
     *  <p>此方法是一般规则的例外,即在此类中同时执行多个方法的结果产生等同于某些串行执行的结果。
     * 如果根据此节点的子树中的偏好或节点与该方法的调用同时被修改,则所导出的偏好包括子树的"模糊快照";一些并发修改可以反映在导出的数据中,而其他修改可能不反映。
     * 
     * 
     * @param os the output stream on which to emit the XML document.
     * @throws IOException if writing to the specified output stream
     *         results in an <tt>IOException</tt>.
     * @throws BackingStoreException if preference data cannot be read from
     *         backing store.
     * @throws IllegalStateException if this node (or an ancestor) has been
     *         removed with the {@link #removeNode()} method.
     * @see    #importPreferences(InputStream)
     * @see    #exportNode(OutputStream)
     */
    public abstract void exportSubtree(OutputStream os)
        throws IOException, BackingStoreException;

    /**
     * Imports all of the preferences represented by the XML document on the
     * specified input stream.  The document may represent user preferences or
     * system preferences.  If it represents user preferences, the preferences
     * will be imported into the calling user's preference tree (even if they
     * originally came from a different user's preference tree).  If any of
     * the preferences described by the document inhabit preference nodes that
     * do not exist, the nodes will be created.
     *
     * <p>The XML document must have the following DOCTYPE declaration:
     * <pre>{@code
     * <!DOCTYPE preferences SYSTEM "http://java.sun.com/dtd/preferences.dtd">
     * }</pre>
     * (This method is designed for use in conjunction with
     * {@link #exportNode(OutputStream)} and
     * {@link #exportSubtree(OutputStream)}.
     *
     * <p>This method is an exception to the general rule that the results of
     * concurrently executing multiple methods in this class yields
     * results equivalent to some serial execution.  The method behaves
     * as if implemented on top of the other public methods in this class,
     * notably {@link #node(String)} and {@link #put(String, String)}.
     *
     * <p>
     * 导入由指定输入流上的XML文档表示的所有首选项。文档可以表示用户偏好或系统偏好。如果它代表用户首选项,则首选项将被导入到主叫用户的首选项树中(即使它们最初来自不同用户的首选项树)。
     * 如果文档描述的任何偏好驻留在不存在的偏好节点中,则将创建节点。
     * 
     *  <p> XML文档必须具有以下DOCTYPE声明：<pre> {@ code
     * <!DOCTYPE preferences SYSTEM "http://java.sun.com/dtd/preferences.dtd">
     * 
     * @param is the input stream from which to read the XML document.
     * @throws IOException if reading from the specified input stream
     *         results in an <tt>IOException</tt>.
     * @throws InvalidPreferencesFormatException Data on input stream does not
     *         constitute a valid XML document with the mandated document type.
     * @throws SecurityException If a security manager is present and
     *         it denies <tt>RuntimePermission("preferences")</tt>.
     * @see    RuntimePermission
     */
    public static void importPreferences(InputStream is)
        throws IOException, InvalidPreferencesFormatException
    {
        XmlSupport.importPreferences(is);
    }
}
