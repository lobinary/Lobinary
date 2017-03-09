/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2008, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.mbeanserver;

import com.sun.jmx.defaults.ServiceName;
import static com.sun.jmx.defaults.JmxProperties.MBEANSERVER_LOGGER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.Map;
import java.util.Set;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.RuntimeOperationsException;

/**
 * This repository does not support persistency.
 *
 * <p>
 *  此存储库不支持持久性。
 * 
 * 
 * @since 1.5
 */
public class Repository {

    /**
     * An interface that allows the caller to get some control
     * over the registration.
     * <p>
     *  允许呼叫者获得对注册的一些控制的接口。
     * 
     * 
     * @see #addMBean
     * @see #remove
     */
    public interface RegistrationContext {
        /**
         * Called by {@link #addMBean}.
         * Can throw a RuntimeOperationsException to cancel the
         * registration.
         * <p>
         *  由{@link #addMBean}调用。可以抛出一个RuntimeOperationsException来取消注册。
         * 
         */
        public void registering();

        /**
         * Called by {@link #remove}.
         * Any exception thrown by this method will be ignored.
         * <p>
         *  由{@link #remove}呼叫。此方法抛出的任何异常都将被忽略。
         * 
         */
        public void unregistered();
    }

    // Private fields -------------------------------------------->

    /**
     * The structure for storing the objects is very basic.
     * A Hashtable is used for storing the different domains
     * For each domain, a hashtable contains the instances with
     * canonical key property list string as key and named object
     * aggregated from given object name and mbean instance as value.
     * <p>
     *  用于存储对象的结构是非常基本的。 Hashtable用于存储不同的域对于每个域,哈希表包含实例,其中规范的关键属性列表字符串作为键,命名对象从给定对象名称和mbean实例聚合为值。
     * 
     */
    private final Map<String,Map<String,NamedObject>> domainTb;

    /**
     * Number of elements contained in the Repository
     * <p>
     *  存储库中包含的元素数
     * 
     */
    private volatile int nbElements = 0;

    /**
     * Domain name of the server the repository is attached to.
     * It is quicker to store the information in the repository rather
     * than querying the framework each time the info is required.
     * <p>
     *  连接到存储库的服务器的域名。它更快地将信息存储在存储库中,而不是在每次需要信息时查询框架。
     * 
     */
    private final String domain;

    /**
     * We use a global reentrant read write lock to protect the repository.
     * This seems safer and more efficient: we are using Maps of Maps,
     * Guaranteing consistency while using Concurent objects at each level
     * may be more difficult.
     * <p>
     *  我们使用全局可重入读写锁来保护存储库。这似乎更安全和更有效：我们使用地图的地图,保证一致性,而在每个级别使用Concurent对象可能更困难。
     * 
     * 
     **/
    private final ReentrantReadWriteLock lock;

    // Private fields <=============================================

    // Private methods --------------------------------------------->

    /* This class is used to match an ObjectName against a pattern. */
    private final static class ObjectNamePattern {
        private final String[] keys;
        private final String[] values;
        private final String   properties;
        private final boolean  isPropertyListPattern;
        private final boolean  isPropertyValuePattern;

        /**
         * The ObjectName pattern against which ObjectNames are matched.
         * <p>
         *  ObjectName与之匹配的ObjectName模式。
         * 
         * 
         **/
        public final ObjectName pattern;

        /**
         * Builds a new ObjectNamePattern object from an ObjectName pattern.
         * <p>
         *  从ObjectName模式构建一个新的ObjectNamePattern对象。
         * 
         * 
         * @param pattern The ObjectName pattern under examination.
         **/
        public ObjectNamePattern(ObjectName pattern) {
            this(pattern.isPropertyListPattern(),
                 pattern.isPropertyValuePattern(),
                 pattern.getCanonicalKeyPropertyListString(),
                 pattern.getKeyPropertyList(),
                 pattern);
        }

        /**
         * Builds a new ObjectNamePattern object from an ObjectName pattern
         * constituents.
         * <p>
         *  从ObjectName模式构成一个新的ObjectNamePattern对象。
         * 
         * 
         * @param propertyListPattern pattern.isPropertyListPattern().
         * @param propertyValuePattern pattern.isPropertyValuePattern().
         * @param canonicalProps pattern.getCanonicalKeyPropertyListString().
         * @param keyPropertyList pattern.getKeyPropertyList().
         * @param pattern The ObjectName pattern under examination.
         **/
        ObjectNamePattern(boolean propertyListPattern,
                          boolean propertyValuePattern,
                          String canonicalProps,
                          Map<String,String> keyPropertyList,
                          ObjectName pattern) {
            this.isPropertyListPattern = propertyListPattern;
            this.isPropertyValuePattern = propertyValuePattern;
            this.properties = canonicalProps;
            final int len = keyPropertyList.size();
            this.keys   = new String[len];
            this.values = new String[len];
            int i = 0;
            for (Map.Entry<String,String> entry : keyPropertyList.entrySet()) {
                keys[i]   = entry.getKey();
                values[i] = entry.getValue();
                i++;
            }
            this.pattern = pattern;
        }

        /**
         * Return true if the given ObjectName matches the ObjectName pattern
         * for which this object has been built.
         * WARNING: domain name is not considered here because it is supposed
         *          not to be wildcard when called. PropertyList is also
         *          supposed not to be zero-length.
         * <p>
         * 如果给定的ObjectName与已构建此对象的ObjectName模式匹配,则返回true。警告：域名在这里不被考虑,因为它应该在调用时不是通配符。 PropertyList也不应该是零长度。
         * 
         * 
         * @param name The ObjectName we want to match against the pattern.
         * @return true if <code>name</code> matches the pattern.
         **/
        public boolean matchKeys(ObjectName name) {
            // If key property value pattern but not key property list
            // pattern, then the number of key properties must be equal
            //
            if (isPropertyValuePattern &&
                !isPropertyListPattern &&
                (name.getKeyPropertyList().size() != keys.length))
                return false;

            // If key property value pattern or key property list pattern,
            // then every property inside pattern should exist in name
            //
            if (isPropertyValuePattern || isPropertyListPattern) {
                for (int i = keys.length - 1; i >= 0 ; i--) {
                    // Find value in given object name for key at current
                    // index in receiver
                    //
                    String v = name.getKeyProperty(keys[i]);
                    // Did we find a value for this key ?
                    //
                    if (v == null) return false;
                    // If this property is ok (same key, same value), go to next
                    //
                    if (isPropertyValuePattern &&
                        pattern.isPropertyValuePattern(keys[i])) {
                        // wildmatch key property values
                        // values[i] is the pattern;
                        // v is the string
                        if (Util.wildmatch(v,values[i]))
                            continue;
                        else
                            return false;
                    }
                    if (v.equals(values[i])) continue;
                    return false;
                }
                return true;
            }

            // If no pattern, then canonical names must be equal
            //
            final String p1 = name.getCanonicalKeyPropertyListString();
            final String p2 = properties;
            return (p1.equals(p2));
        }
    }

    /**
     * Add all the matching objects from the given hashtable in the
     * result set for the given ObjectNamePattern
     * Do not check whether the domains match (only check for matching
     * key property lists - see <i>matchKeys()</i>)
     * <p>
     *  在给定ObjectNamePattern的结果集中添加来自给定散列表的所有匹配对象不检查域是否匹配(仅检查匹配的关键属性列表,请参阅<i> matchKeys()</i>)
     * 
     * 
     **/
    private void addAllMatching(final Map<String,NamedObject> moiTb,
                                final Set<NamedObject> result,
                                final ObjectNamePattern pattern) {
        synchronized (moiTb) {
            for (NamedObject no : moiTb.values()) {
                final ObjectName on = no.getName();
                // if all couples (property, value) are contained
                if (pattern.matchKeys(on)) result.add(no);
            }
        }
    }

    private void addNewDomMoi(final DynamicMBean object,
                              final String dom,
                              final ObjectName name,
                              final RegistrationContext context) {
        final Map<String,NamedObject> moiTb =
            new HashMap<String,NamedObject>();
        final String key = name.getCanonicalKeyPropertyListString();
        addMoiToTb(object,name,key,moiTb,context);
        domainTb.put(dom, moiTb);
        nbElements++;
    }

    private void registering(RegistrationContext context) {
        if (context == null) return;
        try {
            context.registering();
        } catch (RuntimeOperationsException x) {
            throw x;
        } catch (RuntimeException x) {
            throw new RuntimeOperationsException(x);
        }
    }

    private void unregistering(RegistrationContext context, ObjectName name) {
        if (context == null) return;
        try {
            context.unregistered();
        } catch (Exception x) {
            // shouldn't come here...
            MBEANSERVER_LOGGER.log(Level.FINE,
                    "Unexpected exception while unregistering "+name,
                    x);
        }
    }

    private void addMoiToTb(final DynamicMBean object,
            final ObjectName name,
            final String key,
            final Map<String,NamedObject> moiTb,
            final RegistrationContext context) {
        registering(context);
        moiTb.put(key,new NamedObject(name, object));
    }

    /**
     * Retrieves the named object contained in repository
     * from the given objectname.
     * <p>
     *  从给定的对象名称检索存储库中包含的命名对象。
     * 
     */
    private NamedObject retrieveNamedObject(ObjectName name) {

        // No patterns inside reposit
        if (name.isPattern()) return null;

        // Extract the domain name.
        String dom = name.getDomain().intern();

        // Default domain case
        if (dom.length() == 0) {
            dom = domain;
        }

        Map<String,NamedObject> moiTb = domainTb.get(dom);
        if (moiTb == null) {
            return null; // No domain containing registered object names
        }

        return moiTb.get(name.getCanonicalKeyPropertyListString());
    }

    // Private methods <=============================================

    // Protected methods --------------------------------------------->

    // Protected methods <=============================================

    // Public methods --------------------------------------------->

    /**
     * Construct a new repository with the given default domain.
     * <p>
     *  使用给定的默认域构造新的存储库。
     * 
     */
    public Repository(String domain) {
        this(domain,true);
    }

    /**
     * Construct a new repository with the given default domain.
     * <p>
     *  使用给定的默认域构造新的存储库。
     * 
     */
    public Repository(String domain, boolean fairLock) {
        lock = new ReentrantReadWriteLock(fairLock);

        domainTb = new HashMap<String,Map<String,NamedObject>>(5);

        if (domain != null && domain.length() != 0)
            this.domain = domain.intern(); // we use == domain later on...
        else
            this.domain = ServiceName.DOMAIN;

        // Creates a new hashtable for the default domain
        domainTb.put(this.domain, new HashMap<String,NamedObject>());
    }

    /**
     * Returns the list of domains in which any MBean is currently
     * registered.
     *
     * <p>
     *  返回当前注册了任何MBean的域的列表。
     * 
     */
    public String[] getDomains() {

        lock.readLock().lock();
        final List<String> result;
        try {
            // Temporary list
            result = new ArrayList<String>(domainTb.size());
            for (Map.Entry<String,Map<String,NamedObject>> entry :
                     domainTb.entrySet()) {
                // Skip domains that are in the table but have no
                // MBean registered in them
                // in particular the default domain may be like this
                Map<String,NamedObject> t = entry.getValue();
                if (t != null && t.size() != 0)
                    result.add(entry.getKey());
            }
        } finally {
            lock.readLock().unlock();
        }

        // Make an array from result.
        return result.toArray(new String[result.size()]);
    }

    /**
     * Stores an MBean associated with its object name in the repository.
     *
     * <p>
     *  将与其对象名称相关联的MBean存储在存储库中。
     * 
     * 
     * @param object  MBean to be stored in the repository.
     * @param name    MBean object name.
     * @param context A registration context. If non null, the repository
     *                will call {@link RegistrationContext#registering()
     *                context.registering()} from within the repository
     *                lock, when it has determined that the {@code object}
     *                can be stored in the repository with that {@code name}.
     *                If {@link RegistrationContext#registering()
     *                context.registering()} throws an exception, the
     *                operation is abandonned, the MBean is not added to the
     *                repository, and a {@link RuntimeOperationsException}
     *                is thrown.
     */
    public void addMBean(final DynamicMBean object, ObjectName name,
            final RegistrationContext context)
        throws InstanceAlreadyExistsException {

        if (MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
            MBEANSERVER_LOGGER.logp(Level.FINER, Repository.class.getName(),
                    "addMBean", "name = " + name);
        }

        // Extract the domain name.
        String dom = name.getDomain().intern();
        boolean to_default_domain = false;

        // Set domain to default if domain is empty and not already set
        if (dom.length() == 0)
            name = Util.newObjectName(domain + name.toString());

        // Do we have default domain ?
        if (dom == domain) {  // ES: OK (dom & domain are interned)
            to_default_domain = true;
            dom = domain;
        } else {
            to_default_domain = false;
        }

        // Validate name for an object
        if (name.isPattern()) {
            throw new RuntimeOperationsException(
             new IllegalArgumentException("Repository: cannot add mbean for " +
                                          "pattern name " + name.toString()));
        }

        lock.writeLock().lock();
        try {
            // Domain cannot be JMImplementation if entry does not exist
            if ( !to_default_domain &&
                    dom.equals("JMImplementation") &&
                    domainTb.containsKey("JMImplementation")) {
                throw new RuntimeOperationsException(
                        new IllegalArgumentException(
                        "Repository: domain name cannot be JMImplementation"));
            }

            // If domain does not already exist, add it to the hash table
            final Map<String,NamedObject> moiTb = domainTb.get(dom);
            if (moiTb == null) {
                addNewDomMoi(object, dom, name, context);
                return;
            } else {
                // Add instance if not already present
                String cstr = name.getCanonicalKeyPropertyListString();
                NamedObject elmt= moiTb.get(cstr);
                if (elmt != null) {
                    throw new InstanceAlreadyExistsException(name.toString());
                } else {
                    nbElements++;
                    addMoiToTb(object,name,cstr,moiTb,context);
                }
            }

        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Checks whether an MBean of the name specified is already stored in
     * the repository.
     *
     * <p>
     *  检查指定名称的MBean是否已存储在存储库中。
     * 
     * 
     * @param name name of the MBean to find.
     *
     * @return  true if the MBean is stored in the repository,
     *          false otherwise.
     */
    public boolean contains(ObjectName name) {
        if (MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
            MBEANSERVER_LOGGER.logp(Level.FINER, Repository.class.getName(),
                    "contains", " name = " + name);
        }
        lock.readLock().lock();
        try {
            return (retrieveNamedObject(name) != null);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Retrieves the MBean of the name specified from the repository. The
     * object name must match exactly.
     *
     * <p>
     *  检索从存储库指定的名称的MBean。对象名称必须完全匹配。
     * 
     * 
     * @param name name of the MBean to retrieve.
     *
     * @return  The retrieved MBean if it is contained in the repository,
     *          null otherwise.
     */
    public DynamicMBean retrieve(ObjectName name) {
        if (MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
            MBEANSERVER_LOGGER.logp(Level.FINER, Repository.class.getName(),
                    "retrieve", "name = " + name);
        }

        // Calls internal retrieve method to get the named object
        lock.readLock().lock();
        try {
            NamedObject no = retrieveNamedObject(name);
            if (no == null) return null;
            else return no.getObject();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Selects and retrieves the list of MBeans whose names match the specified
     * object name pattern and which match the specified query expression
     * (optionally).
     *
     * <p>
     *  选择并检索名称与指定对象名称模式匹配且与指定查询表达式(可选)匹配的MBeans列表。
     * 
     * 
     * @param pattern The name of the MBean(s) to retrieve - may be a specific
     * object or a name pattern allowing multiple MBeans to be selected.
     * @param query query expression to apply when selecting objects - this
     * parameter will be ignored when the Repository Service does not
     * support filtering.
     *
     * @return  The list of MBeans selected. There may be zero, one or many
     *          MBeans returned in the set.
     */
    public Set<NamedObject> query(ObjectName pattern, QueryExp query) {

        final Set<NamedObject> result = new HashSet<NamedObject>();

        // The following filter cases are considered:
        // null, "", "*:*" : names in all domains
        // ":*", ":[key=value],*" : names in defaultDomain
        // "domain:*", "domain:[key=value],*" : names in the specified domain

        // Surely one of the most frequent cases ... query on the whole world
        ObjectName name;
        if (pattern == null ||
            pattern.getCanonicalName().length() == 0 ||
            pattern.equals(ObjectName.WILDCARD))
           name = ObjectName.WILDCARD;
        else name = pattern;

        lock.readLock().lock();
        try {

            // If pattern is not a pattern, retrieve this mbean !
            if (!name.isPattern()) {
                final NamedObject no = retrieveNamedObject(name);
                if (no != null) result.add(no);
                return result;
            }

            // All names in all domains
            if (name == ObjectName.WILDCARD) {
                for (Map<String,NamedObject> moiTb : domainTb.values()) {
                    result.addAll(moiTb.values());
                }
                return result;
            }

            final String canonical_key_property_list_string =
                    name.getCanonicalKeyPropertyListString();
            final boolean allNames =
                    (canonical_key_property_list_string.length()==0);
            final ObjectNamePattern namePattern =
                (allNames?null:new ObjectNamePattern(name));

            // All names in default domain
            if (name.getDomain().length() == 0) {
                final Map<String,NamedObject> moiTb = domainTb.get(domain);
                if (allNames)
                    result.addAll(moiTb.values());
                else
                    addAllMatching(moiTb, result, namePattern);
                return result;
            }

            if (!name.isDomainPattern()) {
                final Map<String,NamedObject> moiTb = domainTb.get(name.getDomain());
                if (moiTb == null) return Collections.emptySet();
                if (allNames)
                    result.addAll(moiTb.values());
                else
                    addAllMatching(moiTb, result, namePattern);
                return result;
            }

            // Pattern matching in the domain name (*, ?)
            final String dom2Match = name.getDomain();
            for (String dom : domainTb.keySet()) {
                if (Util.wildmatch(dom, dom2Match)) {
                    final Map<String,NamedObject> moiTb = domainTb.get(dom);
                    if (allNames)
                        result.addAll(moiTb.values());
                    else
                        addAllMatching(moiTb, result, namePattern);
                }
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Removes an MBean from the repository.
     *
     * <p>
     *  从存储库中删除MBean。
     * 
     * 
     * @param name name of the MBean to remove.
     * @param context A registration context. If non null, the repository
     *                will call {@link RegistrationContext#unregistered()
     *                context.unregistered()} from within the repository
     *                lock, just after the mbean associated with
     *                {@code name} is removed from the repository.
     *                If {@link RegistrationContext#unregistered()
     *                context.unregistered()} is not expected to throw any
     *                exception. If it does, the exception is logged
     *                and swallowed.
     *
     * @exception InstanceNotFoundException The MBean does not exist in
     *            the repository.
     */
    public void remove(final ObjectName name,
            final RegistrationContext context)
        throws InstanceNotFoundException {

        // Debugging stuff
        if (MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
            MBEANSERVER_LOGGER.logp(Level.FINER, Repository.class.getName(),
                    "remove", "name = " + name);
        }

        // Extract domain name.
        String dom= name.getDomain().intern();

        // Default domain case
        if (dom.length() == 0) dom = domain;

        lock.writeLock().lock();
        try {
            // Find the domain subtable
            final Map<String,NamedObject> moiTb = domainTb.get(dom);
            if (moiTb == null) {
                throw new InstanceNotFoundException(name.toString());
            }

            // Remove the corresponding element
            if (moiTb.remove(name.getCanonicalKeyPropertyListString())==null) {
                throw new InstanceNotFoundException(name.toString());
            }

            // We removed it !
            nbElements--;

            // No more object for this domain, we remove this domain hashtable
            if (moiTb.isEmpty()) {
                domainTb.remove(dom);

                // set a new default domain table (always present)
                // need to reinstantiate a hashtable because of possible
                // big buckets array size inside table, never cleared,
                // thus the new !
                if (dom == domain) // ES: OK dom and domain are interned.
                    domainTb.put(domain, new HashMap<String,NamedObject>());
            }

            unregistering(context,name);

        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Gets the number of MBeans stored in the repository.
     *
     * <p>
     *  获取存储在存储库中的MBean数。
     * 
     * 
     * @return  Number of MBeans.
     */
    public Integer getCount() {
        return nbElements;
    }

    /**
     * Gets the name of the domain currently used by default in the
     * repository.
     *
     * <p>
     *  获取存储库中默认情况下当前使用的域的名称。
     * 
     * @return  A string giving the name of the default domain name.
     */
    public String getDefaultDomain() {
        return domain;
    }

    // Public methods <=============================================
}
