/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.script;
import java.util.*;
import java.security.*;
import java.util.ServiceLoader;
import java.util.ServiceConfigurationError;

/**
 * The <code>ScriptEngineManager</code> implements a discovery and instantiation
 * mechanism for <code>ScriptEngine</code> classes and also maintains a
 * collection of key/value pairs storing state shared by all engines created
 * by the Manager. This class uses the <a href="../../../technotes/guides/jar/jar.html#Service%20Provider">service provider</a> mechanism to enumerate all the
 * implementations of <code>ScriptEngineFactory</code>. <br><br>
 * The <code>ScriptEngineManager</code> provides a method to return a list of all these factories
 * as well as utility methods which look up factories on the basis of language name, file extension
 * and mime type.
 * <p>
 * The <code>Bindings</code> of key/value pairs, referred to as the "Global Scope"  maintained
 * by the manager is available to all instances of <code>ScriptEngine</code> created
 * by the <code>ScriptEngineManager</code>.  The values in the <code>Bindings</code> are
 * generally exposed in all scripts.
 *
 * <p>
 *  <code> ScriptEngineManager </code>实现<code> ScriptEngine </code>类的发现和实例化机制,还维护一组键/值对,存储由Manager创建的所有引
 * 擎共享的状态。
 * 此类使用<a href="../../../technotes/guides/jar/jar.html#Service%20Provider">服务提供商</a>机制枚举<code> ScriptEng
 * ineFactory的所有实现</code>。
 *  <br> <br> <code> ScriptEngineManager </code>提供了一种返回所有这些工厂的列表的方法,以及基于语言名称,文件扩展名和MIME类型查找工厂的实用程序方法。
 * <p>
 *  由管理器维护的称为"全局范围"的键/值对的<code> Bindings </code>可用于由<code> ScriptEngineManager </code>创建的<code> ScriptEn
 * gine </code>代码>。
 *  <code> Bindings </code>中的值通常在所有脚本中公开。
 * 
 * 
 * @author Mike Grogan
 * @author A. Sundararajan
 * @since 1.6
 */
public class ScriptEngineManager  {
    private static final boolean DEBUG = false;
    /**
     * The effect of calling this constructor is the same as calling
     * <code>ScriptEngineManager(Thread.currentThread().getContextClassLoader())</code>.
     *
     * <p>
     *  调用此构造函数的效果与调用<code> ScriptEngineManager(Thread.currentThread()。getContextClassLoader())</code>相同。
     * 
     * 
     * @see java.lang.Thread#getContextClassLoader
     */
    public ScriptEngineManager() {
        ClassLoader ctxtLoader = Thread.currentThread().getContextClassLoader();
        init(ctxtLoader);
    }

    /**
     * This constructor loads the implementations of
     * <code>ScriptEngineFactory</code> visible to the given
     * <code>ClassLoader</code> using the <a href="../../../technotes/guides/jar/jar.html#Service%20Provider">service provider</a> mechanism.<br><br>
     * If loader is <code>null</code>, the script engine factories that are
     * bundled with the platform and that are in the usual extension
     * directories (installed extensions) are loaded. <br><br>
     *
     * <p>
     * 此构造函数使用<a href ="../../../ technotes / guides / jar / jar,将<code> ScriptEngineFactory </code>的实现加载到给定
     * 的<code> ClassLoader </code> html#Service％20Provider">服务提供商</a>机制。
     * <br> <br>如果loader是<code> null </code>,则与平台捆绑在一起并且是通常扩展的脚本引擎工厂目录(已安装的扩展)。 <br> <br>。
     * 
     * 
     * @param loader ClassLoader used to discover script engine factories.
     */
    public ScriptEngineManager(ClassLoader loader) {
        init(loader);
    }

    private void init(final ClassLoader loader) {
        globalScope = new SimpleBindings();
        engineSpis = new HashSet<ScriptEngineFactory>();
        nameAssociations = new HashMap<String, ScriptEngineFactory>();
        extensionAssociations = new HashMap<String, ScriptEngineFactory>();
        mimeTypeAssociations = new HashMap<String, ScriptEngineFactory>();
        initEngines(loader);
    }

    private ServiceLoader<ScriptEngineFactory> getServiceLoader(final ClassLoader loader) {
        if (loader != null) {
            return ServiceLoader.load(ScriptEngineFactory.class, loader);
        } else {
            return ServiceLoader.loadInstalled(ScriptEngineFactory.class);
        }
    }

    private void initEngines(final ClassLoader loader) {
        Iterator<ScriptEngineFactory> itr = null;
        try {
            ServiceLoader<ScriptEngineFactory> sl = AccessController.doPrivileged(
                new PrivilegedAction<ServiceLoader<ScriptEngineFactory>>() {
                    @Override
                    public ServiceLoader<ScriptEngineFactory> run() {
                        return getServiceLoader(loader);
                    }
                });

            itr = sl.iterator();
        } catch (ServiceConfigurationError err) {
            System.err.println("Can't find ScriptEngineFactory providers: " +
                          err.getMessage());
            if (DEBUG) {
                err.printStackTrace();
            }
            // do not throw any exception here. user may want to
            // manage his/her own factories using this manager
            // by explicit registratation (by registerXXX) methods.
            return;
        }

        try {
            while (itr.hasNext()) {
                try {
                    ScriptEngineFactory fact = itr.next();
                    engineSpis.add(fact);
                } catch (ServiceConfigurationError err) {
                    System.err.println("ScriptEngineManager providers.next(): "
                                 + err.getMessage());
                    if (DEBUG) {
                        err.printStackTrace();
                    }
                    // one factory failed, but check other factories...
                    continue;
                }
            }
        } catch (ServiceConfigurationError err) {
            System.err.println("ScriptEngineManager providers.hasNext(): "
                            + err.getMessage());
            if (DEBUG) {
                err.printStackTrace();
            }
            // do not throw any exception here. user may want to
            // manage his/her own factories using this manager
            // by explicit registratation (by registerXXX) methods.
            return;
        }
    }

    /**
     * <code>setBindings</code> stores the specified <code>Bindings</code>
     * in the <code>globalScope</code> field. ScriptEngineManager sets this
     * <code>Bindings</code> as global bindings for <code>ScriptEngine</code>
     * objects created by it.
     *
     * <p>
     *  <code> setBindings </code>在<code> globalScope </code>字段中存储指定的<code> Bindings </code>。
     *  ScriptEngineManager将此<code> Bindings </code>设置为由其创建的<code> ScriptEngine </code>对象的全局绑定。
     * 
     * 
     * @param bindings The specified <code>Bindings</code>
     * @throws IllegalArgumentException if bindings is null.
     */
    public void setBindings(Bindings bindings) {
        if (bindings == null) {
            throw new IllegalArgumentException("Global scope cannot be null.");
        }

        globalScope = bindings;
    }

    /**
     * <code>getBindings</code> returns the value of the <code>globalScope</code> field.
     * ScriptEngineManager sets this <code>Bindings</code> as global bindings for
     * <code>ScriptEngine</code> objects created by it.
     *
     * <p>
     *  <code> getBindings </code>返回<code> globalScope </code>字段的值。
     *  ScriptEngineManager将此<code> Bindings </code>设置为由其创建的<code> ScriptEngine </code>对象的全局绑定。
     * 
     * 
     * @return The globalScope field.
     */
    public Bindings getBindings() {
        return globalScope;
    }

    /**
     * Sets the specified key/value pair in the Global Scope.
     * <p>
     *  在全局范围中设置指定的键/值对。
     * 
     * 
     * @param key Key to set
     * @param value Value to set.
     * @throws NullPointerException if key is null.
     * @throws IllegalArgumentException if key is empty string.
     */
    public void put(String key, Object value) {
        globalScope.put(key, value);
    }

    /**
     * Gets the value for the specified key in the Global Scope
     * <p>
     *  获取全局范围中指定键的值
     * 
     * 
     * @param key The key whose value is to be returned.
     * @return The value for the specified key.
     */
    public Object get(String key) {
        return globalScope.get(key);
    }

    /**
     * Looks up and creates a <code>ScriptEngine</code> for a given  name.
     * The algorithm first searches for a <code>ScriptEngineFactory</code> that has been
     * registered as a handler for the specified name using the <code>registerEngineName</code>
     * method.
     * <br><br> If one is not found, it searches the set of <code>ScriptEngineFactory</code> instances
     * stored by the constructor for one with the specified name.  If a <code>ScriptEngineFactory</code>
     * is found by either method, it is used to create instance of <code>ScriptEngine</code>.
     * <p>
     * 查找并为给定名称创建<code> ScriptEngine </code>。
     * 该算法首先使用<code> registerEngineName </code>方法搜索已注册为指定名称的处理程序的<code> ScriptEngineFactory </code>。
     *  <br> <br>如果找不到,则会搜索由构造函数存储的<code> ScriptEngineFactory </code>实例集中具有指定名称的实例。
     * 如果通过任一方法找到<code> ScriptEngineFactory </code>,它将用于创建<code> ScriptEngine </code>的实例。
     * 
     * 
     * @param shortName The short name of the <code>ScriptEngine</code> implementation.
     * returned by the <code>getNames</code> method of its <code>ScriptEngineFactory</code>.
     * @return A <code>ScriptEngine</code> created by the factory located in the search.  Returns null
     * if no such factory was found.  The <code>ScriptEngineManager</code> sets its own <code>globalScope</code>
     * <code>Bindings</code> as the <code>GLOBAL_SCOPE</code> <code>Bindings</code> of the newly
     * created <code>ScriptEngine</code>.
     * @throws NullPointerException if shortName is null.
     */
    public ScriptEngine getEngineByName(String shortName) {
        if (shortName == null) throw new NullPointerException();
        //look for registered name first
        Object obj;
        if (null != (obj = nameAssociations.get(shortName))) {
            ScriptEngineFactory spi = (ScriptEngineFactory)obj;
            try {
                ScriptEngine engine = spi.getScriptEngine();
                engine.setBindings(getBindings(), ScriptContext.GLOBAL_SCOPE);
                return engine;
            } catch (Exception exp) {
                if (DEBUG) exp.printStackTrace();
            }
        }

        for (ScriptEngineFactory spi : engineSpis) {
            List<String> names = null;
            try {
                names = spi.getNames();
            } catch (Exception exp) {
                if (DEBUG) exp.printStackTrace();
            }

            if (names != null) {
                for (String name : names) {
                    if (shortName.equals(name)) {
                        try {
                            ScriptEngine engine = spi.getScriptEngine();
                            engine.setBindings(getBindings(), ScriptContext.GLOBAL_SCOPE);
                            return engine;
                        } catch (Exception exp) {
                            if (DEBUG) exp.printStackTrace();
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Look up and create a <code>ScriptEngine</code> for a given extension.  The algorithm
     * used by <code>getEngineByName</code> is used except that the search starts
     * by looking for a <code>ScriptEngineFactory</code> registered to handle the
     * given extension using <code>registerEngineExtension</code>.
     * <p>
     *  查找并为给定的扩展名创建一个<code> ScriptEngine </code>。
     * 使用<code> getEngineByName </code>使用的算法,除了搜索开始于寻找<code> ScriptEngineFactory </code>注册以使用<code> register
     * EngineExtension </code>处理给定的扩展。
     *  查找并为给定的扩展名创建一个<code> ScriptEngine </code>。
     * 
     * 
     * @param extension The given extension
     * @return The engine to handle scripts with this extension.  Returns <code>null</code>
     * if not found.
     * @throws NullPointerException if extension is null.
     */
    public ScriptEngine getEngineByExtension(String extension) {
        if (extension == null) throw new NullPointerException();
        //look for registered extension first
        Object obj;
        if (null != (obj = extensionAssociations.get(extension))) {
            ScriptEngineFactory spi = (ScriptEngineFactory)obj;
            try {
                ScriptEngine engine = spi.getScriptEngine();
                engine.setBindings(getBindings(), ScriptContext.GLOBAL_SCOPE);
                return engine;
            } catch (Exception exp) {
                if (DEBUG) exp.printStackTrace();
            }
        }

        for (ScriptEngineFactory spi : engineSpis) {
            List<String> exts = null;
            try {
                exts = spi.getExtensions();
            } catch (Exception exp) {
                if (DEBUG) exp.printStackTrace();
            }
            if (exts == null) continue;
            for (String ext : exts) {
                if (extension.equals(ext)) {
                    try {
                        ScriptEngine engine = spi.getScriptEngine();
                        engine.setBindings(getBindings(), ScriptContext.GLOBAL_SCOPE);
                        return engine;
                    } catch (Exception exp) {
                        if (DEBUG) exp.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Look up and create a <code>ScriptEngine</code> for a given mime type.  The algorithm
     * used by <code>getEngineByName</code> is used except that the search starts
     * by looking for a <code>ScriptEngineFactory</code> registered to handle the
     * given mime type using <code>registerEngineMimeType</code>.
     * <p>
     *  查找并为给定的mime类型创建一个<code> ScriptEngine </code>。
     * 使用<code> getEngineByName </code>使用的算法,除了搜索开始于寻找<code> ScriptEngineFactory </code>注册以使用<code> register
     * EngineMimeType </code>处理给定的mime类型。
     *  查找并为给定的mime类型创建一个<code> ScriptEngine </code>。
     * 
     * 
     * @param mimeType The given mime type
     * @return The engine to handle scripts with this mime type.  Returns <code>null</code>
     * if not found.
     * @throws NullPointerException if mimeType is null.
     */
    public ScriptEngine getEngineByMimeType(String mimeType) {
        if (mimeType == null) throw new NullPointerException();
        //look for registered types first
        Object obj;
        if (null != (obj = mimeTypeAssociations.get(mimeType))) {
            ScriptEngineFactory spi = (ScriptEngineFactory)obj;
            try {
                ScriptEngine engine = spi.getScriptEngine();
                engine.setBindings(getBindings(), ScriptContext.GLOBAL_SCOPE);
                return engine;
            } catch (Exception exp) {
                if (DEBUG) exp.printStackTrace();
            }
        }

        for (ScriptEngineFactory spi : engineSpis) {
            List<String> types = null;
            try {
                types = spi.getMimeTypes();
            } catch (Exception exp) {
                if (DEBUG) exp.printStackTrace();
            }
            if (types == null) continue;
            for (String type : types) {
                if (mimeType.equals(type)) {
                    try {
                        ScriptEngine engine = spi.getScriptEngine();
                        engine.setBindings(getBindings(), ScriptContext.GLOBAL_SCOPE);
                        return engine;
                    } catch (Exception exp) {
                        if (DEBUG) exp.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns a list whose elements are instances of all the <code>ScriptEngineFactory</code> classes
     * found by the discovery mechanism.
     * <p>
     *  返回一个列表,其元素是由发现机制找到的所有<code> ScriptEngineFactory </code>类的实例。
     * 
     * 
     * @return List of all discovered <code>ScriptEngineFactory</code>s.
     */
    public List<ScriptEngineFactory> getEngineFactories() {
        List<ScriptEngineFactory> res = new ArrayList<ScriptEngineFactory>(engineSpis.size());
        for (ScriptEngineFactory spi : engineSpis) {
            res.add(spi);
        }
        return Collections.unmodifiableList(res);
    }

    /**
     * Registers a <code>ScriptEngineFactory</code> to handle a language
     * name.  Overrides any such association found using the Discovery mechanism.
     * <p>
     *  注册<code> ScriptEngineFactory </code>以处理语言名称。覆盖使用发现机制找到的任何此类关联。
     * 
     * 
     * @param name The name to be associated with the <code>ScriptEngineFactory</code>.
     * @param factory The class to associate with the given name.
     * @throws NullPointerException if any of the parameters is null.
     */
    public void registerEngineName(String name, ScriptEngineFactory factory) {
        if (name == null || factory == null) throw new NullPointerException();
        nameAssociations.put(name, factory);
    }

    /**
     * Registers a <code>ScriptEngineFactory</code> to handle a mime type.
     * Overrides any such association found using the Discovery mechanism.
     *
     * <p>
     * 注册<code> ScriptEngineFactory </code>以处理mime类型。覆盖使用发现机制找到的任何此类关联。
     * 
     * 
     * @param type The mime type  to be associated with the
     * <code>ScriptEngineFactory</code>.
     *
     * @param factory The class to associate with the given mime type.
     * @throws NullPointerException if any of the parameters is null.
     */
    public void registerEngineMimeType(String type, ScriptEngineFactory factory) {
        if (type == null || factory == null) throw new NullPointerException();
        mimeTypeAssociations.put(type, factory);
    }

    /**
     * Registers a <code>ScriptEngineFactory</code> to handle an extension.
     * Overrides any such association found using the Discovery mechanism.
     *
     * <p>
     *  注册<code> ScriptEngineFactory </code>以处理扩展。覆盖使用发现机制找到的任何此类关联。
     * 
     * @param extension The extension type  to be associated with the
     * <code>ScriptEngineFactory</code>.
     * @param factory The class to associate with the given extension.
     * @throws NullPointerException if any of the parameters is null.
     */
    public void registerEngineExtension(String extension, ScriptEngineFactory factory) {
        if (extension == null || factory == null) throw new NullPointerException();
        extensionAssociations.put(extension, factory);
    }

    /** Set of script engine factories discovered. */
    private HashSet<ScriptEngineFactory> engineSpis;

    /** Map of engine name to script engine factory. */
    private HashMap<String, ScriptEngineFactory> nameAssociations;

    /** Map of script file extension to script engine factory. */
    private HashMap<String, ScriptEngineFactory> extensionAssociations;

    /** Map of script script MIME type to script engine factory. */
    private HashMap<String, ScriptEngineFactory> mimeTypeAssociations;

    /** Global bindings associated with script engines created by this manager. */
    private Bindings globalScope;
}
