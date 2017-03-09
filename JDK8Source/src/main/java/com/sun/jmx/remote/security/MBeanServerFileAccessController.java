/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.remote.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.security.auth.Subject;

/**
 * <p>An object of this class implements the MBeanServerAccessController
 * interface and, for each of its methods, calls an appropriate checking
 * method and then forwards the request to a wrapped MBeanServer object.
 * The checking method may throw a SecurityException if the operation is
 * not allowed; in this case the request is not forwarded to the
 * wrapped object.</p>
 *
 * <p>This class implements the {@link #checkRead()}, {@link #checkWrite()},
 * {@link #checkCreate(String)}, and {@link #checkUnregister(ObjectName)}
 * methods based on an access level properties file containing username/access
 * level pairs. The set of username/access level pairs is passed either as a
 * filename which denotes a properties file on disk, or directly as an instance
 * of the {@link Properties} class.  In both cases, the name of each property
 * represents a username, and the value of the property is the associated access
 * level.  Thus, any given username either does not exist in the properties or
 * has exactly one access level. The same access level can be shared by several
 * usernames.</p>
 *
 * <p>The supported access level values are {@code readonly} and
 * {@code readwrite}.  The {@code readwrite} access level can be
 * qualified by one or more <i>clauses</i>, where each clause looks
 * like <code>create <i>classNamePattern</i></code> or {@code
 * unregister}.  For example:</p>
 *
 * <pre>
 * monitorRole  readonly
 * controlRole  readwrite \
 *              create javax.management.timer.*,javax.management.monitor.* \
 *              unregister
 * </pre>
 *
 * <p>(The continuation lines with {@code \} come from the parser for
 * Properties files.)</p>
 * <p>
 *  <p>此类的对象实现MBeanServerAccessController接口,并为每个方法调用相应的检查方法,然后将请求转发到包装的MBeanServer对象。
 * 如果不允许操作,检查方法可能抛出SecurityException;在这种情况下,请求不会转发到包装的对象。</p>。
 * 
 *  <p>此类根据访问权限实现{@link #checkRead()},{@link #checkWrite()},{@link #checkCreate(String)}和{@link #checkUnregister(ObjectName)}
 * 方法级别属性文件包含用户名/访问级别对。
 * 用户名/访问级别对的集合作为表示磁盘上的属性文件的文件名传递,或者直接作为{@link属性}类的实例传递。在这两种情况下,每个属性的名称表示用户名,并且属性的值是关联的访问级别。
 * 因此,任何给定的用户名或者不存在于属性中或者具有恰好一个访问级别。相同的访问级别可以由多个用户名共享。</p>。
 * 
 *  <p>支持的访问级别值为{@code readonly}和{@code readwrite}。
 *  {@code readwrite}访问级别可以由一个或多个<i>子句</i>限定,其中每个子句类似于<code> create <i> classNamePattern </i> </code>或{@code unregister }
 * 。
 *  <p>支持的访问级别值为{@code readonly}和{@code readwrite}。例如：</p>。
 * 
 * <pre>
 * monitorRole readonly controlRole readwrite \ create javax.management.timer。
 * *,javax.management.monitor。* \ unregister。
 * </pre>
 * 
 *  <p>(与{@code \}的延续线来自属性文件的解析器。)</p>
 * 
 */
public class MBeanServerFileAccessController
    extends MBeanServerAccessController {

    static final String READONLY = "readonly";
    static final String READWRITE = "readwrite";

    static final String CREATE = "create";
    static final String UNREGISTER = "unregister";

    private enum AccessType {READ, WRITE, CREATE, UNREGISTER};

    private static class Access {
        final boolean write;
        final String[] createPatterns;
        private boolean unregister;

        Access(boolean write, boolean unregister, List<String> createPatternList) {
            this.write = write;
            int npats = (createPatternList == null) ? 0 : createPatternList.size();
            if (npats == 0)
                this.createPatterns = NO_STRINGS;
            else
                this.createPatterns = createPatternList.toArray(new String[npats]);
            this.unregister = unregister;
        }

        private final String[] NO_STRINGS = new String[0];
    }

    /**
     * <p>Create a new MBeanServerAccessController that forwards all the
     * MBeanServer requests to the MBeanServer set by invoking the {@link
     * #setMBeanServer} method after doing access checks based on read and
     * write permissions.</p>
     *
     * <p>This instance is initialized from the specified properties file.</p>
     *
     * <p>
     *  <p>创建一个新的MBeanServerAccessController,通过在根据读取和写入权限进行访问检查后调用{@link #setMBeanServer}方法将所有MBeanServer请求转
     * 发到MBeanServer集。
     * </p>。
     * 
     *  <p>此实例从指定的属性文件初始化。</p>
     * 
     * 
     * @param accessFileName name of the file which denotes a properties
     * file on disk containing the username/access level entries.
     *
     * @exception IOException if the file does not exist, is a
     * directory rather than a regular file, or for some other
     * reason cannot be opened for reading.
     *
     * @exception IllegalArgumentException if any of the supplied access
     * level values differs from "readonly" or "readwrite".
     */
    public MBeanServerFileAccessController(String accessFileName)
        throws IOException {
        super();
        this.accessFileName = accessFileName;
        Properties props = propertiesFromFile(accessFileName);
        parseProperties(props);
    }

    /**
     * <p>Create a new MBeanServerAccessController that forwards all the
     * MBeanServer requests to <code>mbs</code> after doing access checks
     * based on read and write permissions.</p>
     *
     * <p>This instance is initialized from the specified properties file.</p>
     *
     * <p>
     *  <p>创建一个新的MBeanServerAccessController,在根据读取和写入权限进行访问检查后,将所有MBeanServer请求转发到<code> mbs </code>。</p>
     * 
     *  <p>此实例从指定的属性文件初始化。</p>
     * 
     * 
     * @param accessFileName name of the file which denotes a properties
     * file on disk containing the username/access level entries.
     *
     * @param mbs the MBeanServer object to which requests will be forwarded.
     *
     * @exception IOException if the file does not exist, is a
     * directory rather than a regular file, or for some other
     * reason cannot be opened for reading.
     *
     * @exception IllegalArgumentException if any of the supplied access
     * level values differs from "readonly" or "readwrite".
     */
    public MBeanServerFileAccessController(String accessFileName,
                                           MBeanServer mbs)
        throws IOException {
        this(accessFileName);
        setMBeanServer(mbs);
    }

    /**
     * <p>Create a new MBeanServerAccessController that forwards all the
     * MBeanServer requests to the MBeanServer set by invoking the {@link
     * #setMBeanServer} method after doing access checks based on read and
     * write permissions.</p>
     *
     * <p>This instance is initialized from the specified properties
     * instance.  This constructor makes a copy of the properties
     * instance and it is the copy that is consulted to check the
     * username and access level of an incoming connection. The
     * original properties object can be modified without affecting
     * the copy. If the {@link #refresh} method is then called, the
     * <code>MBeanServerFileAccessController</code> will make a new
     * copy of the properties object at that time.</p>
     *
     * <p>
     *  <p>创建一个新的MBeanServerAccessController,通过在根据读取和写入权限进行访问检查后调用{@link #setMBeanServer}方法将所有MBeanServer请求转
     * 发到MBeanServer集。
     * </p>。
     * 
     *  <p>此实例从指定的属性实例初始化。此构造函数创建属性实例的副本,它是查阅的副本以检查传入连接的用户名和访问级别。可以修改原始属性对象,而不会影响副本。
     * 如果随后调用{@link #refresh}方法,那么<code> MBeanServerFileAccessController </code>将会创建此时的属性对象的新副本。</p>。
     * 
     * 
     * @param accessFileProps properties list containing the username/access
     * level entries.
     *
     * @exception IllegalArgumentException if <code>accessFileProps</code> is
     * <code>null</code> or if any of the supplied access level values differs
     * from "readonly" or "readwrite".
     */
    public MBeanServerFileAccessController(Properties accessFileProps)
        throws IOException {
        super();
        if (accessFileProps == null)
            throw new IllegalArgumentException("Null properties");
        originalProps = accessFileProps;
        parseProperties(accessFileProps);
    }

    /**
     * <p>Create a new MBeanServerAccessController that forwards all the
     * MBeanServer requests to the MBeanServer set by invoking the {@link
     * #setMBeanServer} method after doing access checks based on read and
     * write permissions.</p>
     *
     * <p>This instance is initialized from the specified properties
     * instance.  This constructor makes a copy of the properties
     * instance and it is the copy that is consulted to check the
     * username and access level of an incoming connection. The
     * original properties object can be modified without affecting
     * the copy. If the {@link #refresh} method is then called, the
     * <code>MBeanServerFileAccessController</code> will make a new
     * copy of the properties object at that time.</p>
     *
     * <p>
     * <p>创建一个新的MBeanServerAccessController,通过在根据读取和写入权限进行访问检查后调用{@link #setMBeanServer}方法将所有MBeanServer请求转发
     * 到MBeanServer集。
     * </p>。
     * 
     *  <p>此实例从指定的属性实例初始化。此构造函数创建属性实例的副本,它是查阅的副本以检查传入连接的用户名和访问级别。可以修改原始属性对象,而不会影响副本。
     * 如果随后调用{@link #refresh}方法,那么<code> MBeanServerFileAccessController </code>将会创建此时的属性对象的新副本。</p>。
     * 
     * 
     * @param accessFileProps properties list containing the username/access
     * level entries.
     *
     * @param mbs the MBeanServer object to which requests will be forwarded.
     *
     * @exception IllegalArgumentException if <code>accessFileProps</code> is
     * <code>null</code> or if any of the supplied access level values differs
     * from "readonly" or "readwrite".
     */
    public MBeanServerFileAccessController(Properties accessFileProps,
                                           MBeanServer mbs)
        throws IOException {
        this(accessFileProps);
        setMBeanServer(mbs);
    }

    /**
     * Check if the caller can do read operations. This method does
     * nothing if so, otherwise throws SecurityException.
     * <p>
     *  检查调用者是否可以执行读取操作。此方法不执行任何操作,如果是这样,否则抛出SecurityException。
     * 
     */
    @Override
    public void checkRead() {
        checkAccess(AccessType.READ, null);
    }

    /**
     * Check if the caller can do write operations.  This method does
     * nothing if so, otherwise throws SecurityException.
     * <p>
     *  检查调用者是否可以进行写操作。此方法不执行任何操作,如果是这样,否则抛出SecurityException。
     * 
     */
    @Override
    public void checkWrite() {
        checkAccess(AccessType.WRITE, null);
    }

    /**
     * Check if the caller can create MBeans or instances of the given class.
     * This method does nothing if so, otherwise throws SecurityException.
     * <p>
     *  检查调用者是否可以创建MBean或给定类的实例。此方法不执行任何操作,如果是这样,否则抛出SecurityException。
     * 
     */
    @Override
    public void checkCreate(String className) {
        checkAccess(AccessType.CREATE, className);
    }

    /**
     * Check if the caller can do unregister operations.  This method does
     * nothing if so, otherwise throws SecurityException.
     * <p>
     *  检查调用程序是否可以取消注册操作。此方法不执行任何操作,如果是这样,否则抛出SecurityException。
     * 
     */
    @Override
    public void checkUnregister(ObjectName name) {
        checkAccess(AccessType.UNREGISTER, null);
    }

    /**
     * <p>Refresh the set of username/access level entries.</p>
     *
     * <p>If this instance was created using the
     * {@link #MBeanServerFileAccessController(String)} or
     * {@link #MBeanServerFileAccessController(String,MBeanServer)}
     * constructors to specify a file from which the entries are read,
     * the file is re-read.</p>
     *
     * <p>If this instance was created using the
     * {@link #MBeanServerFileAccessController(Properties)} or
     * {@link #MBeanServerFileAccessController(Properties,MBeanServer)}
     * constructors then a new copy of the <code>Properties</code> object
     * is made.</p>
     *
     * <p>
     *  <p>刷新用户名/访问级别条目集。</p>
     * 
     *  <p>如果此实例是使用{@link #MBeanServerFileAccessController(String)}或{@link #MBeanServerFileAccessController(String,MBeanServer)}
     * 构造函数创建的,以指定从中读取条目的文件,则会重新读取该文件。
     *  </p>。
     * 
     * @exception IOException if the file does not exist, is a
     * directory rather than a regular file, or for some other
     * reason cannot be opened for reading.
     *
     * @exception IllegalArgumentException if any of the supplied access
     * level values differs from "readonly" or "readwrite".
     */
    public synchronized void refresh() throws IOException {
        Properties props;
        if (accessFileName == null)
            props = (Properties) originalProps;
        else
            props = propertiesFromFile(accessFileName);
        parseProperties(props);
    }

    private static Properties propertiesFromFile(String fname)
        throws IOException {
        FileInputStream fin = new FileInputStream(fname);
        try {
            Properties p = new Properties();
            p.load(fin);
            return p;
        } finally {
            fin.close();
        }
    }

    private synchronized void checkAccess(AccessType requiredAccess, String arg) {
        final AccessControlContext acc = AccessController.getContext();
        final Subject s =
            AccessController.doPrivileged(new PrivilegedAction<Subject>() {
                    public Subject run() {
                        return Subject.getSubject(acc);
                    }
                });
        if (s == null) return; /* security has not been enabled */
        final Set principals = s.getPrincipals();
        String newPropertyValue = null;
        for (Iterator i = principals.iterator(); i.hasNext(); ) {
            final Principal p = (Principal) i.next();
            Access access = accessMap.get(p.getName());
            if (access != null) {
                boolean ok;
                switch (requiredAccess) {
                    case READ:
                        ok = true;  // all access entries imply read
                        break;
                    case WRITE:
                        ok = access.write;
                        break;
                    case UNREGISTER:
                        ok = access.unregister;
                        if (!ok && access.write)
                            newPropertyValue = "unregister";
                        break;
                    case CREATE:
                        ok = checkCreateAccess(access, arg);
                        if (!ok && access.write)
                            newPropertyValue = "create " + arg;
                        break;
                    default:
                        throw new AssertionError();
                }
                if (ok)
                    return;
            }
        }
        SecurityException se = new SecurityException("Access denied! Invalid " +
                "access level for requested MBeanServer operation.");
        // Add some more information to help people with deployments that
        // worked before we required explicit create clauses. We're not giving
        // any information to the bad guys, other than that the access control
        // is based on a file, which they could have worked out from the stack
        // trace anyway.
        if (newPropertyValue != null) {
            SecurityException se2 = new SecurityException("Access property " +
                    "for this identity should be similar to: " + READWRITE +
                    " " + newPropertyValue);
            se.initCause(se2);
        }
        throw se;
    }

    private static boolean checkCreateAccess(Access access, String className) {
        for (String classNamePattern : access.createPatterns) {
            if (classNameMatch(classNamePattern, className))
                return true;
        }
        return false;
    }

    private static boolean classNameMatch(String pattern, String className) {
        // We studiously avoided regexes when parsing the properties file,
        // because that is done whenever the VM is started with the
        // appropriate -Dcom.sun.management options, even if nobody ever
        // creates an MBean.  We don't want to incur the overhead of loading
        // all the regex code whenever those options are specified, but if we
        // get as far as here then the VM is already running and somebody is
        // doing the very unusual operation of remotely creating an MBean.
        // Because that operation is so unusual, we don't try to optimize
        // by hand-matching or by caching compiled Pattern objects.
        StringBuilder sb = new StringBuilder();
        StringTokenizer stok = new StringTokenizer(pattern, "*", true);
        while (stok.hasMoreTokens()) {
            String tok = stok.nextToken();
            if (tok.equals("*"))
                sb.append("[^.]*");
            else
                sb.append(Pattern.quote(tok));
        }
        return className.matches(sb.toString());
    }

    private void parseProperties(Properties props) {
        this.accessMap = new HashMap<String, Access>();
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            String identity = (String) entry.getKey();
            String accessString = (String) entry.getValue();
            Access access = Parser.parseAccess(identity, accessString);
            accessMap.put(identity, access);
        }
    }

    private static class Parser {
        private final static int EOS = -1;  // pseudo-codepoint "end of string"
        static {
            assert !Character.isWhitespace(EOS);
        }

        private final String identity;  // just for better error messages
        private final String s;  // the string we're parsing
        private final int len;   // s.length()
        private int i;
        private int c;
        // At any point, either c is s.codePointAt(i), or i == len and
        // c is EOS.  We use int rather than char because it is conceivable
        // (if unlikely) that a classname in a create clause might contain
        // "supplementary characters", the ones that don't fit in the original
        // 16 bits for Unicode.

        private Parser(String identity, String s) {
            this.identity = identity;
            this.s = s;
            this.len = s.length();
            this.i = 0;
            if (i < len)
                this.c = s.codePointAt(i);
            else
                this.c = EOS;
        }

        static Access parseAccess(String identity, String s) {
            return new Parser(identity, s).parseAccess();
        }

        private Access parseAccess() {
            skipSpace();
            String type = parseWord();
            Access access;
            if (type.equals(READONLY))
                access = new Access(false, false, null);
            else if (type.equals(READWRITE))
                access = parseReadWrite();
            else {
                throw syntax("Expected " + READONLY + " or " + READWRITE +
                        ": " + type);
            }
            if (c != EOS)
                throw syntax("Extra text at end of line");
            return access;
        }

        private Access parseReadWrite() {
            List<String> createClasses = new ArrayList<String>();
            boolean unregister = false;
            while (true) {
                skipSpace();
                if (c == EOS)
                    break;
                String type = parseWord();
                if (type.equals(UNREGISTER))
                    unregister = true;
                else if (type.equals(CREATE))
                    parseCreate(createClasses);
                else
                    throw syntax("Unrecognized keyword " + type);
            }
            return new Access(true, unregister, createClasses);
        }

        private void parseCreate(List<String> createClasses) {
            while (true) {
                skipSpace();
                createClasses.add(parseClassName());
                skipSpace();
                if (c == ',')
                    next();
                else
                    break;
            }
        }

        private String parseClassName() {
            // We don't check that classname components begin with suitable
            // characters (so we accept 1.2.3 for example).  This means that
            // there are only two states, which we can call dotOK and !dotOK
            // according as a dot (.) is legal or not.  Initially we're in
            // !dotOK since a classname can't start with a dot; after a dot
            // we're in !dotOK again; and after any other characters we're in
            // dotOK.  The classname is only accepted if we end in dotOK,
            // so we reject an empty name or a name that ends with a dot.
            final int start = i;
            boolean dotOK = false;
            while (true) {
                if (c == '.') {
                    if (!dotOK)
                        throw syntax("Bad . in class name");
                    dotOK = false;
                } else if (c == '*' || Character.isJavaIdentifierPart(c))
                    dotOK = true;
                else
                    break;
                next();
            }
            String className = s.substring(start, i);
            if (!dotOK)
                throw syntax("Bad class name " + className);
            return className;
        }

        // Advance c and i to the next character, unless already at EOS.
        private void next() {
            if (c != EOS) {
                i += Character.charCount(c);
                if (i < len)
                    c = s.codePointAt(i);
                else
                    c = EOS;
            }
        }

        private void skipSpace() {
            while (Character.isWhitespace(c))
                next();
        }

        private String parseWord() {
            skipSpace();
            if (c == EOS)
                throw syntax("Expected word at end of line");
            final int start = i;
            while (c != EOS && !Character.isWhitespace(c))
                next();
            String word = s.substring(start, i);
            skipSpace();
            return word;
        }

        private IllegalArgumentException syntax(String msg) {
            return new IllegalArgumentException(
                    msg + " [" + identity + " " + s + "]");
        }
    }

    private Map<String, Access> accessMap;
    private Properties originalProps;
    private String accessFileName;
}
