/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.activation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.rmi.MarshalledObject;
import java.util.Arrays;
import java.util.Properties;

/**
 * An activation group descriptor contains the information necessary to
 * create/recreate an activation group in which to activate objects.
 * Such a descriptor contains: <ul>
 * <li> the group's class name,
 * <li> the group's code location (the location of the group's class), and
 * <li> a "marshalled" object that can contain group specific
 * initialization data. </ul> <p>
 *
 * The group's class must be a concrete subclass of
 * <code>ActivationGroup</code>. A subclass of
 * <code>ActivationGroup</code> is created/recreated via the
 * <code>ActivationGroup.createGroup</code> static method that invokes
 * a special constructor that takes two arguments: <ul>
 *
 * <li> the group's <code>ActivationGroupID</code>, and
 * <li> the group's initialization data (in a
 * <code>java.rmi.MarshalledObject</code>)</ul><p>
 *
 * <p>
 *  激活组描述符包含创建/重新创建激活对象所需的激活组所需的信息。这样的描述符包含：<ul> <li>组的类名,<li>组的代码位置(组的类的位置)和<li>可以包含组特定初始化数据的"已编组"对象。
 *  </ul> <p>。
 * 
 *  组的类必须是<code> ActivationGroup </code>的具体子类。
 * 通过<code> ActivationGroup.createGroup </code>静态方法创建/重新创建<code> ActivationGroup </code>的子类,该方法调用一个特殊的构造
 * 函数,。
 *  组的类必须是<code> ActivationGroup </code>的具体子类。
 * 
 *  <li>群组的<code> ActivationGroupID </code>和<li>群组的初始化资料(在<code> java.rmi.MarshalledObject </code>)</ul>
 * 。
 * 
 * 
 * @author      Ann Wollrath
 * @since       1.2
 * @see         ActivationGroup
 * @see         ActivationGroupID
 */
public final class ActivationGroupDesc implements Serializable {

    /**
    /* <p>
    /* 
     * @serial The group's fully package qualified class name.
     */
    private String className;

    /**
    /* <p>
    /* 
     * @serial The location from where to load the group's class.
     */
    private String location;

    /**
    /* <p>
    /* 
     * @serial The group's initialization data.
     */
    private MarshalledObject<?> data;

    /**
    /* <p>
    /* 
     * @serial The controlling options for executing the VM in
     * another process.
     */
    private CommandEnvironment env;

    /**
    /* <p>
    /* 
     * @serial A properties map which will override those set
     * by default in the subprocess environment.
     */
    private Properties props;

    /** indicate compatibility with the Java 2 SDK v1.2 version of class */
    private static final long serialVersionUID = -4936225423168276595L;

    /**
     * Constructs a group descriptor that uses the system defaults for group
     * implementation and code location.  Properties specify Java
     * environment overrides (which will override system properties in
     * the group implementation's VM).  The command
     * environment can control the exact command/options used in
     * starting the child VM, or can be <code>null</code> to accept
     * rmid's default.
     *
     * <p>This constructor will create an <code>ActivationGroupDesc</code>
     * with a <code>null</code> group class name, which indicates the system's
     * default <code>ActivationGroup</code> implementation.
     *
     * <p>
     *  构造使用系统默认值实现组实现和代码位置的组描述符。属性指定Java环境覆盖(这将覆盖组实现的VM中的系统属性)。
     * 命令环境可以控制用于启动子VM的确切命令/选项,或者可以<code> null </code>接受rmid的默认值。
     * 
     *  <p>此构造函数将创建一个具有<code> null </code>组类名称的<code> ActivationGroupDesc </code>,表示系统的默认<code> ActivationGr
     * oup </code>实现。
     * 
     * 
     * @param overrides the set of properties to set when the group is
     * recreated.
     * @param cmd the controlling options for executing the VM in
     * another process (or <code>null</code>).
     * @since 1.2
     */
    public ActivationGroupDesc(Properties overrides,
                               CommandEnvironment cmd)
    {
        this(null, null, null, overrides, cmd);
    }

    /**
     * Specifies an alternate group implementation and execution
     * environment to be used for the group.
     *
     * <p>
     * 指定要用于组的备用组实施和执行环境。
     * 
     * 
     * @param className the group's package qualified class name or
     * <code>null</code>. A <code>null</code> group class name indicates
     * the system's default <code>ActivationGroup</code> implementation.
     * @param location the location from where to load the group's
     * class
     * @param data the group's initialization data contained in
     * marshalled form (could contain properties, for example)
     * @param overrides a properties map which will override those set
     * by default in the subprocess environment (will be translated
     * into <code>-D</code> options), or <code>null</code>.
     * @param cmd the controlling options for executing the VM in
     * another process (or <code>null</code>).
     * @since 1.2
     */
    public ActivationGroupDesc(String className,
                               String location,
                               MarshalledObject<?> data,
                               Properties overrides,
                               CommandEnvironment cmd)
    {
        this.props = overrides;
        this.env = cmd;
        this.data = data;
        this.location = location;
        this.className = className;
    }

    /**
     * Returns the group's class name (possibly <code>null</code>).  A
     * <code>null</code> group class name indicates the system's default
     * <code>ActivationGroup</code> implementation.
     * <p>
     *  返回组的类名(可能为<code> null </code>)。 <code> null </code>组类名称指示系统的默认<code> ActivationGroup </code>实现。
     * 
     * 
     * @return the group's class name
     * @since 1.2
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns the group's code location.
     * <p>
     *  返回组的代码位置。
     * 
     * 
     * @return the group's code location
     * @since 1.2
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the group's initialization data.
     * <p>
     *  返回组的初始化数据。
     * 
     * 
     * @return the group's initialization data
     * @since 1.2
     */
    public MarshalledObject<?> getData() {
        return data;
    }

    /**
     * Returns the group's property-override list.
     * <p>
     *  返回组的属性覆盖列表。
     * 
     * 
     * @return the property-override list, or <code>null</code>
     * @since 1.2
     */
    public Properties getPropertyOverrides() {
        return (props != null) ? (Properties) props.clone() : null;
    }

    /**
     * Returns the group's command-environment control object.
     * <p>
     *  返回组的命令环境控制对象。
     * 
     * 
     * @return the command-environment object, or <code>null</code>
     * @since 1.2
     */
    public CommandEnvironment getCommandEnvironment() {
        return this.env;
    }


    /**
     * Startup options for ActivationGroup implementations.
     *
     * This class allows overriding default system properties and
     * specifying implementation-defined options for ActivationGroups.
     * <p>
     *  ActivationGroup实现的启动选项。
     * 
     *  此类允许覆盖默认系统属性,并为ActivationGroups指定实现定义的选项。
     * 
     * 
     * @since 1.2
     */
    public static class CommandEnvironment implements Serializable {
        private static final long serialVersionUID = 6165754737887770191L;

        /**
        /* <p>
        /* 
         * @serial
         */
        private String command;

        /**
        /* <p>
        /* 
         * @serial
         */
        private String[] options;

        /**
         * Create a CommandEnvironment with all the necessary
         * information.
         *
         * <p>
         *  创建一个具有所有必要信息的CommandEnvironment。
         * 
         * 
         * @param cmdpath the name of the java executable, including
         * the full path, or <code>null</code>, meaning "use rmid's default".
         * The named program <em>must</em> be able to accept multiple
         * <code>-Dpropname=value</code> options (as documented for the
         * "java" tool)
         *
         * @param argv extra options which will be used in creating the
         * ActivationGroup.  Null has the same effect as an empty
         * list.
         * @since 1.2
         */
        public CommandEnvironment(String cmdpath,
                                  String[] argv)
        {
            this.command = cmdpath;     // might be null

            // Hold a safe copy of argv in this.options
            if (argv == null) {
                this.options = new String[0];
            } else {
                this.options = new String[argv.length];
                System.arraycopy(argv, 0, this.options, 0, argv.length);
            }
        }

        /**
         * Fetch the configured path-qualified java command name.
         *
         * <p>
         *  获取配置的路径限定的java命令名称。
         * 
         * 
         * @return the configured name, or <code>null</code> if configured to
         * accept the default
         * @since 1.2
         */
        public String getCommandPath() {
            return (this.command);
        }

        /**
         * Fetch the configured java command options.
         *
         * <p>
         *  获取配置的java命令选项。
         * 
         * 
         * @return An array of the command options which will be passed
         * to the new child command by rmid.
         * Note that rmid may add other options before or after these
         * options, or both.
         * Never returns <code>null</code>.
         * @since 1.2
         */
        public String[] getCommandOptions() {
            return options.clone();
        }

        /**
         * Compares two command environments for content equality.
         *
         * <p>
         *  比较两个命令环境的内容相等性。
         * 
         * 
         * @param       obj     the Object to compare with
         * @return      true if these Objects are equal; false otherwise.
         * @see         java.util.Hashtable
         * @since 1.2
         */
        public boolean equals(Object obj) {

            if (obj instanceof CommandEnvironment) {
                CommandEnvironment env = (CommandEnvironment) obj;
                return
                    ((command == null ? env.command == null :
                      command.equals(env.command)) &&
                     Arrays.equals(options, env.options));
            } else {
                return false;
            }
        }

        /**
         * Return identical values for similar
         * <code>CommandEnvironment</code>s.
         * <p>
         *  为类似的<code> CommandEnvironment </code>返回相同的值。
         * 
         * 
         * @return an integer
         * @see java.util.Hashtable
         */
        public int hashCode()
        {
            // hash command and ignore possibly expensive options
            return (command == null ? 0 : command.hashCode());
        }

        /**
         * <code>readObject</code> for custom serialization.
         *
         * <p>This method reads this object's serialized form for this
         * class as follows:
         *
         * <p>This method first invokes <code>defaultReadObject</code> on
         * the specified object input stream, and if <code>options</code>
         * is <code>null</code>, then <code>options</code> is set to a
         * zero-length array of <code>String</code>.
         * <p>
         *  <code> readObject </code>用于自定义序列化。
         * 
         *  <p>此方法读取此对象的此类的序列化形式,如下所示：
         * 
         *  <p>此方法首先在指定的对象输入流上调用<code> defaultReadObject </code>,如果<code> options </code>是<code> null </code>,那么
         * <code> >设置为<code> String </code>的零长度数组。
         * 
         */
        private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException
        {
            in.defaultReadObject();
            if (options == null) {
                options = new String[0];
            }
        }
    }

    /**
     * Compares two activation group descriptors for content equality.
     *
     * <p>
     * 
     * @param   obj     the Object to compare with
     * @return  true if these Objects are equal; false otherwise.
     * @see             java.util.Hashtable
     * @since 1.2
     */
    public boolean equals(Object obj) {

        if (obj instanceof ActivationGroupDesc) {
            ActivationGroupDesc desc = (ActivationGroupDesc) obj;
            return
                ((className == null ? desc.className == null :
                  className.equals(desc.className)) &&
                 (location == null ? desc.location == null :
                  location.equals(desc.location)) &&
                 (data == null ? desc.data == null : data.equals(desc.data)) &&
                 (env == null ? desc.env == null : env.equals(desc.env)) &&
                 (props == null ? desc.props == null :
                  props.equals(desc.props)));
        } else {
            return false;
        }
    }

    /**
     * Produce identical numbers for similar <code>ActivationGroupDesc</code>s.
     * <p>
     *  比较两个激活组描述符的内容相等性。
     * 
     * 
     * @return an integer
     * @see java.util.Hashtable
     */
    public int hashCode() {
        // hash location, className, data, and env
        // but omit props (may be expensive)
        return ((location == null
                    ? 0
                    : location.hashCode() << 24) ^
                (env == null
                    ? 0
                    : env.hashCode() << 16) ^
                (className == null
                    ? 0
                    : className.hashCode() << 8) ^
                (data == null
                    ? 0
                    : data.hashCode()));
    }
}
