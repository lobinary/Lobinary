/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.auth;

import java.util.*;
import java.text.MessageFormat;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Principal;
import sun.security.util.ResourcesMgr;

/**
 * This class is used to protect access to private Credentials
 * belonging to a particular {@code Subject}.  The {@code Subject}
 * is represented by a Set of Principals.
 *
 * <p> The target name of this {@code Permission} specifies
 * a Credential class name, and a Set of Principals.
 * The only valid value for this Permission's actions is, "read".
 * The target name must abide by the following syntax:
 *
 * <pre>
 *      CredentialClass {PrincipalClass "PrincipalName"}*
 * </pre>
 *
 * For example, the following permission grants access to the
 * com.sun.PrivateCredential owned by Subjects which have
 * a com.sun.Principal with the name, "duke".  Note that although
 * this example, as well as all the examples below, do not contain
 * Codebase, SignedBy, or Principal information in the grant statement
 * (for simplicity reasons), actual policy configurations should
 * specify that information when appropriate.
 *
 * <pre>
 *
 *    grant {
 *      permission javax.security.auth.PrivateCredentialPermission
 *              "com.sun.PrivateCredential com.sun.Principal \"duke\"",
 *              "read";
 *    };
 * </pre>
 *
 * If CredentialClass is "*", then access is granted to
 * all private Credentials belonging to the specified
 * {@code Subject}.
 * If "PrincipalName" is "*", then access is granted to the
 * specified Credential owned by any {@code Subject} that has the
 * specified {@code Principal} (the actual PrincipalName doesn't matter).
 * For example, the following grants access to the
 * a.b.Credential owned by any {@code Subject} that has
 * an a.b.Principal.
 *
 * <pre>
 *    grant {
 *      permission javax.security.auth.PrivateCredentialPermission
 *              "a.b.Credential a.b.Principal "*"",
 *              "read";
 *    };
 * </pre>
 *
 * If both the PrincipalClass and "PrincipalName" are "*",
 * then access is granted to the specified Credential owned by
 * any {@code Subject}.
 *
 * <p> In addition, the PrincipalClass/PrincipalName pairing may be repeated:
 *
 * <pre>
 *    grant {
 *      permission javax.security.auth.PrivateCredentialPermission
 *              "a.b.Credential a.b.Principal "duke" c.d.Principal "dukette"",
 *              "read";
 *    };
 * </pre>
 *
 * The above grants access to the private Credential, "a.b.Credential",
 * belonging to a {@code Subject} with at least two associated Principals:
 * "a.b.Principal" with the name, "duke", and "c.d.Principal", with the name,
 * "dukette".
 *
 * <p>
 *  此类用于保护对属于特定{@code Subject}的私有凭据的访问。 {@code主题}由一组主体表示。
 * 
 *  <p>此{@code Permission}的目标名称指定了凭据类名称和一组原则。此权限操作的唯一有效值是"读取"。目标名称必须遵守以下语法：
 * 
 * <pre>
 *  CredentialClass {PrincipalClass"PrincipalName"} *
 * </pre>
 * 
 *  例如,以下权限授予对拥有com.sun.Principal名称为"duke"的主题拥有的com.sun.PrivateCredential的访问权限。
 * 请注意,虽然此示例以及以下所有示例在grant语句中不包含Codebase,SignedBy或Principal信息(为了简单起见),但实际策略配置应在适当时指定该信息。
 * 
 * <pre>
 * 
 *  grant {permission javax.security.auth.PrivateCredentialPermission"com.sun.PrivateCredential com.sun.Principal \"duke \"","read"; }
 * ;。
 * </pre>
 * 
 * 如果CredentialClass为"*",则授予属于指定{@code Subject}的所有私有凭据的访问权限。
 * 如果"PrincipalName"是"*",则授予对具有指定的{@code Principal}(实际PrincipalName无关紧要)的任何{@code Subject}拥有的指定凭证的访问权。
 * 例如,以下授权访问由具有a.b.Principal的任何{@code Subject}拥有的a.b.Credential。
 * 
 * <pre>
 *  grant {permission javax.security.auth.PrivateCredentialPermission"a.b.Credential a.b.Principal"*"","read"; }
 * ;。
 * </pre>
 * 
 *  如果PrincipalClass和"PrincipalName"都是"*",则访问被授予任何{@code Subject}所拥有的指定凭证。
 * 
 *  <p>此外,PrincipalClass / PrincipalName配对可以重复：
 * 
 * <pre>
 *  grant {permission javax.security.auth.PrivateCredentialPermission"a.b.Credential a.b.Principal"duke"c.d.Principal"dukette"","read"; }
 * ;。
 * </pre>
 * 
 *  以上授权访问属于具有至少两个关联主体的{@code Subject}的私有Credential"abCredential"：具有名称"duke"和"cdPrincipal"的"abPrincipal"
 * ,其名称,"dukette"。
 * 
 */
public final class PrivateCredentialPermission extends Permission {

    private static final long serialVersionUID = 5284372143517237068L;

    private static final CredOwner[] EMPTY_PRINCIPALS = new CredOwner[0];

    /**
    /* <p>
    /* 
     * @serial
     */
    private String credentialClass;

    /**
    /* <p>
    /* 
     * @serial The Principals associated with this permission.
     *          The set contains elements of type,
     *          {@code PrivateCredentialPermission.CredOwner}.
     */
    private Set<Principal> principals;  // ignored - kept around for compatibility
    private transient CredOwner[] credOwners;

    /**
    /* <p>
    /* 
     * @serial
     */
    private boolean testing = false;

    /**
     * Create a new {@code PrivateCredentialPermission}
     * with the specified {@code credentialClass} and Principals.
     * <p>
     *  使用指定的{@code credentialClass}和主体创建新的{@code PrivateCredentialPermission}。
     * 
     */
    PrivateCredentialPermission(String credentialClass,
                        Set<Principal> principals) {

        super(credentialClass);
        this.credentialClass = credentialClass;

        synchronized(principals) {
            if (principals.size() == 0) {
                this.credOwners = EMPTY_PRINCIPALS;
            } else {
                this.credOwners = new CredOwner[principals.size()];
                int index = 0;
                Iterator<Principal> i = principals.iterator();
                while (i.hasNext()) {
                    Principal p = i.next();
                    this.credOwners[index++] = new CredOwner
                                                (p.getClass().getName(),
                                                p.getName());
                }
            }
        }
    }

    /**
     * Creates a new {@code PrivateCredentialPermission}
     * with the specified {@code name}.  The {@code name}
     * specifies both a Credential class and a {@code Principal} Set.
     *
     * <p>
     *
     * <p>
     *  使用指定的{@code name}创建新的{@code PrivateCredentialPermission}。
     *  {@code name}指定Credential类和{@code Principal} Set。
     * 
     * <p>
     * 
     * 
     * @param name the name specifying the Credential class and
     *          {@code Principal} Set. <p>
     *
     * @param actions the actions specifying that the Credential can be read.
     *
     * @throws IllegalArgumentException if {@code name} does not conform
     *          to the correct syntax or if {@code actions} is not "read".
     */
    public PrivateCredentialPermission(String name, String actions) {
        super(name);

        if (!"read".equalsIgnoreCase(actions))
            throw new IllegalArgumentException
                (ResourcesMgr.getString("actions.can.only.be.read."));
        init(name);
    }

    /**
     * Returns the Class name of the Credential associated with this
     * {@code PrivateCredentialPermission}.
     *
     * <p>
     *
     * <p>
     * 返回与此{@code PrivateCredentialPermission}关联的Credential的类名。
     * 
     * <p>
     * 
     * 
     * @return the Class name of the Credential associated with this
     *          {@code PrivateCredentialPermission}.
     */
    public String getCredentialClass() {
        return credentialClass;
    }

    /**
     * Returns the {@code Principal} classes and names
     * associated with this {@code PrivateCredentialPermission}.
     * The information is returned as a two-dimensional array (array[x][y]).
     * The 'x' value corresponds to the number of {@code Principal}
     * class and name pairs.  When (y==0), it corresponds to
     * the {@code Principal} class value, and when (y==1),
     * it corresponds to the {@code Principal} name value.
     * For example, array[0][0] corresponds to the class name of
     * the first {@code Principal} in the array.  array[0][1]
     * corresponds to the {@code Principal} name of the
     * first {@code Principal} in the array.
     *
     * <p>
     *
     * <p>
     *  返回与此{@code PrivateCredentialPermission}相关联的{@code Principal}类和名称。信息作为二维数组(array [x] [y])返回。
     *  'x'值对应于{@code Principal}类和名称对的数量。
     * 当(y == 0)时,它对应于{@code Principal}类值,当(y == 1)时,它对应于{@code Principal}名称值。
     * 例如,array [0] [0]对应于数组中第一个{@code Principal}的类名。
     *  array [0] [1]对应于数组中第一个{@code Principal}的{@code Principal}名称。
     * 
     * <p>
     * 
     * 
     * @return the {@code Principal} class and names associated
     *          with this {@code PrivateCredentialPermission}.
     */
    public String[][] getPrincipals() {

        if (credOwners == null || credOwners.length == 0) {
            return new String[0][0];
        }

        String[][] pArray = new String[credOwners.length][2];
        for (int i = 0; i < credOwners.length; i++) {
            pArray[i][0] = credOwners[i].principalClass;
            pArray[i][1] = credOwners[i].principalName;
        }
        return pArray;
    }

    /**
     * Checks if this {@code PrivateCredentialPermission} implies
     * the specified {@code Permission}.
     *
     * <p>
     *
     * This method returns true if:
     * <ul>
     * <li> <i>p</i> is an instanceof PrivateCredentialPermission and
     * <li> the target name for <i>p</i> is implied by this object's
     *          target name.  For example:
     * <pre>
     *  [* P1 "duke"] implies [a.b.Credential P1 "duke"].
     *  [C1 P1 "duke"] implies [C1 P1 "duke" P2 "dukette"].
     *  [C1 P2 "dukette"] implies [C1 P1 "duke" P2 "dukette"].
     * </pre>
     * </ul>
     *
     * <p>
     *
     * <p>
     *  检查此{@code PrivateCredentialPermission}是否暗示指定的{@code权限}。
     * 
     * <p>
     * 
     *  此方法返回true如果：
     * <ul>
     *  <li> <i> p </i>是PrivateCredentialPermission的实例,<li>该对象的目标名称隐含了<i> p </i>的目标名称。例如：
     * <pre>
     *  [* P1"duke"]暗示[a.b.Credential P1"duke"]。 [C1 P1"duke"]表示[C1 P1"duke"P2"dukette"]。
     *  [C1 P2"dukette"]表示[C1 P1"duke"P2"dukette"]。
     * </pre>
     * </ul>
     * 
     * <p>
     * 
     * 
     * @param p the {@code Permission} to check against.
     *
     * @return true if this {@code PrivateCredentialPermission} implies
     * the specified {@code Permission}, false if not.
     */
    public boolean implies(Permission p) {

        if (p == null || !(p instanceof PrivateCredentialPermission))
            return false;

        PrivateCredentialPermission that = (PrivateCredentialPermission)p;

        if (!impliesCredentialClass(credentialClass, that.credentialClass))
            return false;

        return impliesPrincipalSet(credOwners, that.credOwners);
    }

    /**
     * Checks two {@code PrivateCredentialPermission} objects for
     * equality.  Checks that <i>obj</i> is a
     * {@code PrivateCredentialPermission},
     * and has the same credential class as this object,
     * as well as the same Principals as this object.
     * The order of the Principals in the respective Permission's
     * target names is not relevant.
     *
     * <p>
     *
     * <p>
     *  检查两个{@code PrivateCredentialPermission}对象是否相等。
     * 检查<i> obj </i>是否为{@code PrivateCredentialPermission},并且具有与此对象相同的凭据类,以及与此对象相同的原则。各个权限的目标名称中的主体的顺序不相关。
     * 
     * <p>
     * 
     * 
     * @param obj the object we are testing for equality with this object.
     *
     * @return true if obj is a {@code PrivateCredentialPermission},
     *          has the same credential class as this object,
     *          and has the same Principals as this object.
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (! (obj instanceof PrivateCredentialPermission))
            return false;

        PrivateCredentialPermission that = (PrivateCredentialPermission)obj;

        return (this.implies(that) && that.implies(this));
    }

    /**
     * Returns the hash code value for this object.
     *
     * <p>
     *  返回此对象的哈希码值。
     * 
     * 
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return this.credentialClass.hashCode();
    }

    /**
     * Returns the "canonical string representation" of the actions.
     * This method always returns the String, "read".
     *
     * <p>
     *
     * <p>
     * 返回操作的"规范字符串表示"。这个方法总是返回String,"read"。
     * 
     * <p>
     * 
     * 
     * @return the actions (always returns "read").
     */
    public String getActions() {
        return "read";
    }

    /**
     * Return a homogeneous collection of PrivateCredentialPermissions
     * in a {@code PermissionCollection}.
     * No such {@code PermissionCollection} is defined,
     * so this method always returns {@code null}.
     *
     * <p>
     *
     * <p>
     *  在{@code PermissionCollection}中返回PrivateCredentialPermissions的同质集合。
     * 没有定义这样的{@code PermissionCollection},所以这个方法总是返回{@code null}。
     * 
     * <p>
     * 
     * 
     * @return null in all cases.
     */
    public PermissionCollection newPermissionCollection() {
        return null;
    }

    private void init(String name) {

        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("invalid empty name");
        }

        ArrayList<CredOwner> pList = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(name, " ", true);
        String principalClass = null;
        String principalName = null;

        if (testing)
            System.out.println("whole name = " + name);

        // get the Credential Class
        credentialClass = tokenizer.nextToken();
        if (testing)
            System.out.println("Credential Class = " + credentialClass);

        if (tokenizer.hasMoreTokens() == false) {
            MessageFormat form = new MessageFormat(ResourcesMgr.getString
                ("permission.name.name.syntax.invalid."));
            Object[] source = {name};
            throw new IllegalArgumentException
                (form.format(source) + ResourcesMgr.getString
                        ("Credential.Class.not.followed.by.a.Principal.Class.and.Name"));
        }

        while (tokenizer.hasMoreTokens()) {

            // skip delimiter
            tokenizer.nextToken();

            // get the Principal Class
            principalClass = tokenizer.nextToken();
            if (testing)
                System.out.println("    Principal Class = " + principalClass);

            if (tokenizer.hasMoreTokens() == false) {
                MessageFormat form = new MessageFormat(ResourcesMgr.getString
                        ("permission.name.name.syntax.invalid."));
                Object[] source = {name};
                throw new IllegalArgumentException
                        (form.format(source) + ResourcesMgr.getString
                        ("Principal.Class.not.followed.by.a.Principal.Name"));
            }

            // skip delimiter
            tokenizer.nextToken();

            // get the Principal Name
            principalName = tokenizer.nextToken();

            if (!principalName.startsWith("\"")) {
                MessageFormat form = new MessageFormat(ResourcesMgr.getString
                        ("permission.name.name.syntax.invalid."));
                Object[] source = {name};
                throw new IllegalArgumentException
                        (form.format(source) + ResourcesMgr.getString
                        ("Principal.Name.must.be.surrounded.by.quotes"));
            }

            if (!principalName.endsWith("\"")) {

                // we have a name with spaces in it --
                // keep parsing until we find the end quote,
                // and keep the spaces in the name

                while (tokenizer.hasMoreTokens()) {
                    principalName = principalName + tokenizer.nextToken();
                    if (principalName.endsWith("\""))
                        break;
                }

                if (!principalName.endsWith("\"")) {
                    MessageFormat form = new MessageFormat
                        (ResourcesMgr.getString
                        ("permission.name.name.syntax.invalid."));
                    Object[] source = {name};
                    throw new IllegalArgumentException
                        (form.format(source) + ResourcesMgr.getString
                                ("Principal.Name.missing.end.quote"));
                }
            }

            if (testing)
                System.out.println("\tprincipalName = '" + principalName + "'");

            principalName = principalName.substring
                                        (1, principalName.length() - 1);

            if (principalClass.equals("*") &&
                !principalName.equals("*")) {
                    throw new IllegalArgumentException(ResourcesMgr.getString
                        ("PrivateCredentialPermission.Principal.Class.can.not.be.a.wildcard.value.if.Principal.Name.is.not.a.wildcard.value"));
            }

            if (testing)
                System.out.println("\tprincipalName = '" + principalName + "'");

            pList.add(new CredOwner(principalClass, principalName));
        }

        this.credOwners = new CredOwner[pList.size()];
        pList.toArray(this.credOwners);
    }

    private boolean impliesCredentialClass(String thisC, String thatC) {

        // this should never happen
        if (thisC == null || thatC == null)
            return false;

        if (testing)
            System.out.println("credential class comparison: " +
                                thisC + "/" + thatC);

        if (thisC.equals("*"))
            return true;

        /**
         * XXX let's not enable this for now --
         *      if people want it, we'll enable it later
         * <p>
         *  XXX现在不能启用此功能 - 如果有人想要,我们稍后将启用它
         * 
         */
        /*
        if (thisC.endsWith("*")) {
            String cClass = thisC.substring(0, thisC.length() - 2);
            return thatC.startsWith(cClass);
        }
        if (thisC.endsWith("* <p>
        if (thisC.endsWith("*  if(thisC.endsWith("*")){String cClass = thisC.substring(0,thisC.length() -  2); return c.CstartWith(cClass); }
        if (thisC.endsWith("* }。
        if (thisC.endsWith("* 
        */

        return thisC.equals(thatC);
    }

    private boolean impliesPrincipalSet(CredOwner[] thisP, CredOwner[] thatP) {

        // this should never happen
        if (thisP == null || thatP == null)
            return false;

        if (thatP.length == 0)
            return true;

        if (thisP.length == 0)
            return false;

        for (int i = 0; i < thisP.length; i++) {
            boolean foundMatch = false;
            for (int j = 0; j < thatP.length; j++) {
                if (thisP[i].implies(thatP[j])) {
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reads this object from a stream (i.e., deserializes it)
     * <p>
     *  从流中读取此对象(即,对其进行反序列化)
     * 
     */
    private void readObject(java.io.ObjectInputStream s) throws
                                        java.io.IOException,
                                        ClassNotFoundException {

        s.defaultReadObject();

        // perform new initialization from the permission name

        if (getName().indexOf(" ") == -1 && getName().indexOf("\"") == -1) {

            // name only has a credential class specified
            credentialClass = getName();
            credOwners = EMPTY_PRINCIPALS;

        } else {

            // perform regular initialization
            init(getName());
        }
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class CredOwner implements java.io.Serializable {

        private static final long serialVersionUID = -5607449830436408266L;

        /**
        /* <p>
        /* 
         * @serial
         */
        String principalClass;
        /**
        /* <p>
        /* 
         * @serial
         */
        String principalName;

        CredOwner(String principalClass, String principalName) {
            this.principalClass = principalClass;
            this.principalName = principalName;
        }

        public boolean implies(Object obj) {
            if (obj == null || !(obj instanceof CredOwner))
                return false;

            CredOwner that = (CredOwner)obj;

            if (principalClass.equals("*") ||
                principalClass.equals(that.principalClass)) {

                if (principalName.equals("*") ||
                    principalName.equals(that.principalName)) {
                    return true;
                }
            }

            /**
             * XXX no code yet to support a.b.*
             * <p>
             *  XXX没有代码尚未支持a.b. *
             */

            return false;
        }

        public String toString() {
            MessageFormat form = new MessageFormat(ResourcesMgr.getString
                ("CredOwner.Principal.Class.class.Principal.Name.name"));
            Object[] source = {principalClass, principalName};
            return (form.format(source));
        }
    }
}
