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

package javax.security.auth.kerberos;

import java.util.*;
import java.security.Permission;
import java.security.BasicPermission;
import java.security.PermissionCollection;
import java.io.ObjectStreamField;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * This class is used to restrict the usage of the Kerberos
 * delegation model, ie: forwardable and proxiable tickets.
 * <p>
 * The target name of this {@code Permission} specifies a pair of
 * kerberos service principals. The first is the subordinate service principal
 * being entrusted to use the TGT. The second service principal designates
 * the target service the subordinate service principal is to
 * interact with on behalf of the initiating KerberosPrincipal. This
 * latter service principal is specified to restrict the use of a
 * proxiable ticket.
 * <p>
 * For example, to specify the "host" service use of a forwardable TGT the
 * target permission is specified as follows:
 *
 * <pre>
 *  DelegationPermission("\"host/foo.example.com@EXAMPLE.COM\" \"krbtgt/EXAMPLE.COM@EXAMPLE.COM\"");
 * </pre>
 * <p>
 * To give the "backup" service a proxiable nfs service ticket the target permission
 * might be specified:
 *
 * <pre>
 *  DelegationPermission("\"backup/bar.example.com@EXAMPLE.COM\" \"nfs/home.EXAMPLE.COM@EXAMPLE.COM\"");
 * </pre>
 *
 * <p>
 *  此类用于限制Kerberos委派模型的使用,即：可转发和可行的票证。
 * <p>
 *  此{@code Permission}的目标名称指定一对kerberos服务主体。第一个是委托使用TGT的从属服务主体。
 * 第二个服务主体指定下级服务主体代表初始KerberosPrincipal与之交互的目标服务。指定后一个服务主体以限制使用可优惠票。
 * <p>
 *  例如,要指定可转发TGT的"主机"服务使用,请指定目标权限如下：
 * 
 * <pre>
 *  委派权限("\"host/foo.example.com@EXAMPLE.COM \"\"krbtgt/EXAMPLE.COM@EXAMPLE.COM \"");
 * </pre>
 * <p>
 *  为了给"备份"服务一个可行的nfs服务票证,可以指定目标权限：
 * 
 * <pre>
 *  委派权限("\"backup/bar.example.com@EXAMPLE.COM \"\"nfs/home.EXAMPLE.COM@EXAMPLE.COM \"");
 * </pre>
 * 
 * 
 * @since 1.4
 */

public final class DelegationPermission extends BasicPermission
    implements java.io.Serializable {

    private static final long serialVersionUID = 883133252142523922L;

    private transient String subordinate, service;

    /**
     * Create a new {@code DelegationPermission}
     * with the specified subordinate and target principals.
     *
     * <p>
     *
     * <p>
     *  使用指定的下级和目标主体创建新的{@code DelegationPermission}。
     * 
     * <p>
     * 
     * 
     * @param principals the name of the subordinate and target principals
     *
     * @throws NullPointerException if {@code principals} is {@code null}.
     * @throws IllegalArgumentException if {@code principals} is empty.
     */
    public DelegationPermission(String principals) {
        super(principals);
        init(principals);
    }

    /**
     * Create a new {@code DelegationPermission}
     * with the specified subordinate and target principals.
     * <p>
     *
     * <p>
     *  使用指定的下级和目标主体创建新的{@code DelegationPermission}。
     * <p>
     * 
     * 
     * @param principals the name of the subordinate and target principals
     * <p>
     * @param actions should be null.
     *
     * @throws NullPointerException if {@code principals} is {@code null}.
     * @throws IllegalArgumentException if {@code principals} is empty.
     */
    public DelegationPermission(String principals, String actions) {
        super(principals, actions);
        init(principals);
    }


    /**
     * Initialize the DelegationPermission object.
     * <p>
     *  初始化DelegationPermission对象。
     * 
     */
    private void init(String target) {

        StringTokenizer t = null;
        if (!target.startsWith("\"")) {
            throw new IllegalArgumentException
                ("service principal [" + target +
                 "] syntax invalid: " +
                 "improperly quoted");
        } else {
            t = new StringTokenizer(target, "\"", false);
            subordinate = t.nextToken();
            if (t.countTokens() == 2) {
                t.nextToken();  // bypass whitespace
                service = t.nextToken();
            } else if (t.countTokens() > 0) {
                throw new IllegalArgumentException
                    ("service principal [" + t.nextToken() +
                     "] syntax invalid: " +
                     "improperly quoted");
            }
        }
    }

    /**
     * Checks if this Kerberos delegation permission object "implies" the
     * specified permission.
     * <P>
     * If none of the above are true, {@code implies} returns false.
     * <p>
     *  检查此Kerberos委派权限对象是否"暗示"指定的权限。
     * <P>
     *  如果上面没有一个是真的,{@code implies}返回false。
     * 
     * 
     * @param p the permission to check against.
     *
     * @return true if the specified permission is implied by this object,
     * false if not.
     */
    public boolean implies(Permission p) {
        if (!(p instanceof DelegationPermission))
            return false;

        DelegationPermission that = (DelegationPermission) p;
        if (this.subordinate.equals(that.subordinate) &&
            this.service.equals(that.service))
            return true;

        return false;
    }


    /**
     * Checks two DelegationPermission objects for equality.
     * <P>
     * <p>
     * 检查两个DelegPermission对象是否相等。
     * <P>
     * 
     * @param obj the object to test for equality with this object.
     *
     * @return true if <i>obj</i> is a DelegationPermission, and
     *  has the same subordinate and service principal as this.
     *  DelegationPermission object.
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (! (obj instanceof DelegationPermission))
            return false;

        DelegationPermission that = (DelegationPermission) obj;
        return implies(that);
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
        return getName().hashCode();
    }


    /**
     * Returns a PermissionCollection object for storing
     * DelegationPermission objects.
     * <br>
     * DelegationPermission objects must be stored in a manner that
     * allows them to be inserted into the collection in any order, but
     * that also enables the PermissionCollection implies method to
     * be implemented in an efficient (and consistent) manner.
     *
     * <p>
     *  返回用于存储DelegPermission对象的PermissionCollection对象。
     * <br>
     *  DelegPermission对象必须以允许以任何顺序插入到集合中的方式存储,但这也使得PermissionCollection隐含方法能够以高效(且一致)的方式实现。
     * 
     * 
     * @return a new PermissionCollection object suitable for storing
     * DelegationPermissions.
     */

    public PermissionCollection newPermissionCollection() {
        return new KrbDelegationPermissionCollection();
    }

    /**
     * WriteObject is called to save the state of the DelegationPermission
     * to a stream. The actions are serialized, and the superclass
     * takes care of the name.
     * <p>
     *  将调用WriteObject以将DelegationPermission的状态保存到流。操作是序列化的,超类负责处理名称。
     * 
     */
    private synchronized void writeObject(java.io.ObjectOutputStream s)
        throws IOException
    {
        s.defaultWriteObject();
    }

    /**
     * readObject is called to restore the state of the
     * DelegationPermission from a stream.
     * <p>
     *  readObject被调用以从流恢复DelegationPermission的状态。
     * 
     */
    private synchronized void readObject(java.io.ObjectInputStream s)
         throws IOException, ClassNotFoundException
    {
        // Read in the action, then initialize the rest
        s.defaultReadObject();
        init(getName());
    }

    /*
      public static void main(String args[]) throws Exception {
      DelegationPermission this_ =
      new DelegationPermission(args[0]);
      DelegationPermission that_ =
      new DelegationPermission(args[1]);
      System.out.println("-----\n");
      System.out.println("this.implies(that) = " + this_.implies(that_));
      System.out.println("-----\n");
      System.out.println("this = "+this_);
      System.out.println("-----\n");
      System.out.println("that = "+that_);
      System.out.println("-----\n");

      KrbDelegationPermissionCollection nps =
      new KrbDelegationPermissionCollection();
      nps.add(this_);
      nps.add(new DelegationPermission("\"host/foo.example.com@EXAMPLE.COM\" \"CN=Gary Ellison/OU=JSN/O=SUNW/L=Palo Alto/ST=CA/C=US\""));
      try {
      nps.add(new DelegationPermission("host/foo.example.com@EXAMPLE.COM \"CN=Gary Ellison/OU=JSN/O=SUNW/L=Palo Alto/ST=CA/C=US\""));
      } catch (Exception e) {
      System.err.println(e);
      }

      System.out.println("nps.implies(that) = " + nps.implies(that_));
      System.out.println("-----\n");

      Enumeration e = nps.elements();

      while (e.hasMoreElements()) {
      DelegationPermission x =
      (DelegationPermission) e.nextElement();
      System.out.println("nps.e = " + x);
      }
      }
    /* <p>
    /*  public static void main(String args [])throws Exception {DelegPermission this_ = new DelegatePermission(args [0]); assignPermission that_ = new DelegPermission(args [1]); System.out.println("----- \ n"); System.out.println("this.implies(that)="+ this_.implies(that_)); System.out.println("----- \ n"); System.out.println("this ="+ this_); System.out.println("----- \ n"); System.out.println("that ="+ that_); System.out.println("----- \ n");。
    /* 
    /* KrbDelegationPermissionCollection nps = new KrbDelegationPermissionCollection(); nps.add(this_); nps.
    /* add(new DelegatePermission("\"host/foo.example.com@EXAMPLE.COM \"\ CN = Gary Ellison / OU = JSN / O =
    /*  SUNW / L = Palo Alto / ST = CA / C = US \"")); try {nps.add(new DelegationPermission("host/foo.example.com@EXAMPLE.COM \ CN = Gary Ellison / OU = JSN / O = SUNW / L = Palo Alto / ST = CA / C = US \ ")); }
    /*  catch(Exception e){System.err.println(e); }}。
    /* 
    /*  System.out.println("nps.implies(that)="+ nps.implies(that_)); System.out.println("----- \ n");
    /* 
    /*  枚举e = nps.elements();
    /* 
    /*  while(e.hasMoreElements()){DelegatePermission x =(DelegatePermission)e.nextElement(); System.out.println("nps.e ="+ x); }
    /* }。
    /* 
    */
}


final class KrbDelegationPermissionCollection extends PermissionCollection
    implements java.io.Serializable {

    // Not serialized; see serialization section at end of class.
    private transient List<Permission> perms;

    public KrbDelegationPermissionCollection() {
        perms = new ArrayList<Permission>();
    }


    /**
     * Check and see if this collection of permissions implies the permissions
     * expressed in "permission".
     *
     * <p>
     *  检查并查看此权限集合是否意味着在"权限"中表达的权限。
     * 
     * 
     * @param permission the Permission object to compare
     *
     * @return true if "permission" is a proper subset of a permission in
     * the collection, false if not.
     */
    public boolean implies(Permission permission) {
        if (! (permission instanceof DelegationPermission))
                return false;

        synchronized (this) {
            for (Permission x : perms) {
                if (x.implies(permission))
                    return true;
            }
        }
        return false;

    }

    /**
     * Adds a permission to the DelegationPermissions. The key for
     * the hash is the name.
     *
     * <p>
     *  向DelegationPermissions添加权限。哈希的键是名称。
     * 
     * 
     * @param permission the Permission object to add.
     *
     * @exception IllegalArgumentException - if the permission is not a
     *                                       DelegationPermission
     *
     * @exception SecurityException - if this PermissionCollection object
     *                                has been marked readonly
     */
    public void add(Permission permission) {
        if (! (permission instanceof DelegationPermission))
            throw new IllegalArgumentException("invalid permission: "+
                                               permission);
        if (isReadOnly())
            throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");

        synchronized (this) {
            perms.add(0, permission);
        }
    }

    /**
     * Returns an enumeration of all the DelegationPermission objects
     * in the container.
     *
     * <p>
     *  返回容器中所有DelegPermission对象的枚举。
     * 
     * 
     * @return an enumeration of all the DelegationPermission objects.
     */
    public Enumeration<Permission> elements() {
        // Convert Iterator into Enumeration
        synchronized (this) {
            return Collections.enumeration(perms);
        }
    }

    private static final long serialVersionUID = -3383936936589966948L;

    // Need to maintain serialization interoperability with earlier releases,
    // which had the serializable field:
    //    private Vector permissions;
    /**
    /* <p>
    /* 
     * @serialField permissions java.util.Vector
     *     A list of DelegationPermission objects.
     */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("permissions", Vector.class),
    };

    /**
    /* <p>
    /* 
     * @serialData "permissions" field (a Vector containing the DelegationPermissions).
     */
    /*
     * Writes the contents of the perms field out as a Vector for
     * serialization compatibility with earlier releases.
     * <p>
     *  将perms字段的内容作为Vector与先前版本的序列化兼容性写入。
     * 
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // Don't call out.defaultWriteObject()

        // Write out Vector
        Vector<Permission> permissions = new Vector<>(perms.size());

        synchronized (this) {
            permissions.addAll(perms);
        }

        ObjectOutputStream.PutField pfields = out.putFields();
        pfields.put("permissions", permissions);
        out.writeFields();
    }

    /*
     * Reads in a Vector of DelegationPermissions and saves them in the perms field.
     * <p>
     *  读取DelegationPermissions的向量并将其保存在perms字段中。
     */
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        // Don't call defaultReadObject()

        // Read in serialized fields
        ObjectInputStream.GetField gfields = in.readFields();

        // Get the one we want
        Vector<Permission> permissions =
                (Vector<Permission>)gfields.get("permissions", null);
        perms = new ArrayList<Permission>(permissions.size());
        perms.addAll(permissions);
    }
}
