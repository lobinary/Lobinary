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

package java.net;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import sun.security.action.*;
import java.security.AccessController;

/**
 * This class represents a Network Interface made up of a name,
 * and a list of IP addresses assigned to this interface.
 * It is used to identify the local interface on which a multicast group
 * is joined.
 *
 * Interfaces are normally known by names such as "le0".
 *
 * <p>
 *  此类表示由名称和分配给此接口的IP地址列表组成的网络接口。用于标识组播组加入的本地接口。
 * 
 *  接口通常以诸如"le0"的名称已知。
 * 
 * 
 * @since 1.4
 */
public final class NetworkInterface {
    private String name;
    private String displayName;
    private int index;
    private InetAddress addrs[];
    private InterfaceAddress bindings[];
    private NetworkInterface childs[];
    private NetworkInterface parent = null;
    private boolean virtual = false;
    private static final NetworkInterface defaultInterface;
    private static final int defaultIndex; /* index of defaultInterface */

    static {
        AccessController.doPrivileged(
            new java.security.PrivilegedAction<Void>() {
                public Void run() {
                    System.loadLibrary("net");
                    return null;
                }
            });

        init();
        defaultInterface = DefaultInterface.getDefault();
        if (defaultInterface != null) {
            defaultIndex = defaultInterface.getIndex();
        } else {
            defaultIndex = 0;
        }
    }

    /**
     * Returns an NetworkInterface object with index set to 0 and name to null.
     * Setting such an interface on a MulticastSocket will cause the
     * kernel to choose one interface for sending multicast packets.
     *
     * <p>
     *  返回一个NetworkInterface对象,索引设置为0,名称为null。在多播套接字上设置这样的接口将导致内核选择一个接口来发送多播包。
     * 
     */
    NetworkInterface() {
    }

    NetworkInterface(String name, int index, InetAddress[] addrs) {
        this.name = name;
        this.index = index;
        this.addrs = addrs;
    }

    /**
     * Get the name of this network interface.
     *
     * <p>
     *  获取此网络接口的名称。
     * 
     * 
     * @return the name of this network interface
     */
    public String getName() {
            return name;
    }

    /**
     * Convenience method to return an Enumeration with all or a
     * subset of the InetAddresses bound to this network interface.
     * <p>
     * If there is a security manager, its {@code checkConnect}
     * method is called for each InetAddress. Only InetAddresses where
     * the {@code checkConnect} doesn't throw a SecurityException
     * will be returned in the Enumeration. However, if the caller has the
     * {@link NetPermission}("getNetworkInformation") permission, then all
     * InetAddresses are returned.
     * <p>
     *  方便方法返回一个Enumeration与绑定到此网络接口的InetAddress的全部或子集。
     * <p>
     *  如果有安全管理器,则会为每个InetAddress调用其{@code checkConnect}方法。
     * 只有在{@code checkConnect}不抛出SecurityException的InetAddresses将在枚举中返回。
     * 但是,如果调用者具有{@link NetPermission}("getNetworkInformation")权限,则返回所有InetAddresses。
     * 
     * 
     * @return an Enumeration object with all or a subset of the InetAddresses
     * bound to this network interface
     */
    public Enumeration<InetAddress> getInetAddresses() {

        class checkedAddresses implements Enumeration<InetAddress> {

            private int i=0, count=0;
            private InetAddress local_addrs[];

            checkedAddresses() {
                local_addrs = new InetAddress[addrs.length];
                boolean trusted = true;

                SecurityManager sec = System.getSecurityManager();
                if (sec != null) {
                    try {
                        sec.checkPermission(new NetPermission("getNetworkInformation"));
                    } catch (SecurityException e) {
                        trusted = false;
                    }
                }
                for (int j=0; j<addrs.length; j++) {
                    try {
                        if (sec != null && !trusted) {
                            sec.checkConnect(addrs[j].getHostAddress(), -1);
                        }
                        local_addrs[count++] = addrs[j];
                    } catch (SecurityException e) { }
                }

            }

            public InetAddress nextElement() {
                if (i < count) {
                    return local_addrs[i++];
                } else {
                    throw new NoSuchElementException();
                }
            }

            public boolean hasMoreElements() {
                return (i < count);
            }
        }
        return new checkedAddresses();

    }

    /**
     * Get a List of all or a subset of the {@code InterfaceAddresses}
     * of this network interface.
     * <p>
     * If there is a security manager, its {@code checkConnect}
     * method is called with the InetAddress for each InterfaceAddress.
     * Only InterfaceAddresses where the {@code checkConnect} doesn't throw
     * a SecurityException will be returned in the List.
     *
     * <p>
     *  获取此网络接口的所有或部分{@code InterfaceAddresses}的列表。
     * <p>
     *  如果有安全管理器,则使用每个InterfaceAddress的InetAddress调用其{@code checkConnect}方法。
     * 只有在{@code checkConnect}不抛出SecurityException的InterfaceAddress将在列表中返回。
     * 
     * 
     * @return a {@code List} object with all or a subset of the
     *         InterfaceAddresss of this network interface
     * @since 1.6
     */
    public java.util.List<InterfaceAddress> getInterfaceAddresses() {
        java.util.List<InterfaceAddress> lst = new java.util.ArrayList<InterfaceAddress>(1);
        SecurityManager sec = System.getSecurityManager();
        for (int j=0; j<bindings.length; j++) {
            try {
                if (sec != null) {
                    sec.checkConnect(bindings[j].getAddress().getHostAddress(), -1);
                }
                lst.add(bindings[j]);
            } catch (SecurityException e) { }
        }
        return lst;
    }

    /**
     * Get an Enumeration with all the subinterfaces (also known as virtual
     * interfaces) attached to this network interface.
     * <p>
     * For instance eth0:1 will be a subinterface to eth0.
     *
     * <p>
     * 获取附加到此网络接口的所有子接口(也称为虚拟接口)的枚举。
     * <p>
     *  例如eth0：1将是eth0的子接口。
     * 
     * 
     * @return an Enumeration object with all of the subinterfaces
     * of this network interface
     * @since 1.6
     */
    public Enumeration<NetworkInterface> getSubInterfaces() {
        class subIFs implements Enumeration<NetworkInterface> {

            private int i=0;

            subIFs() {
            }

            public NetworkInterface nextElement() {
                if (i < childs.length) {
                    return childs[i++];
                } else {
                    throw new NoSuchElementException();
                }
            }

            public boolean hasMoreElements() {
                return (i < childs.length);
            }
        }
        return new subIFs();

    }

    /**
     * Returns the parent NetworkInterface of this interface if this is
     * a subinterface, or {@code null} if it is a physical
     * (non virtual) interface or has no parent.
     *
     * <p>
     *  返回此接口的父NetworkInterface(如果是子接口)或{@code null}(如果它是物理(非虚拟)接口或没有父接口)。
     * 
     * 
     * @return The {@code NetworkInterface} this interface is attached to.
     * @since 1.6
     */
    public NetworkInterface getParent() {
        return parent;
    }

    /**
     * Returns the index of this network interface. The index is an integer greater
     * or equal to zero, or {@code -1} for unknown. This is a system specific value
     * and interfaces with the same name can have different indexes on different
     * machines.
     *
     * <p>
     *  返回此网络接口的索引。索引是大于或等于零的整数,或对于未知的是{@code -1}。这是一个系统特定的值,具有相同名称的接口可以在不同的机器上具有不同的索引。
     * 
     * 
     * @return the index of this network interface or {@code -1} if the index is
     *         unknown
     * @see #getByIndex(int)
     * @since 1.7
     */
    public int getIndex() {
        return index;
    }

    /**
     * Get the display name of this network interface.
     * A display name is a human readable String describing the network
     * device.
     *
     * <p>
     *  获取此网络接口的显示名称。显示名称是描述网络设备的可读的字符串。
     * 
     * 
     * @return a non-empty string representing the display name of this network
     *         interface, or null if no display name is available.
     */
    public String getDisplayName() {
        /* strict TCK conformance */
        return "".equals(displayName) ? null : displayName;
    }

    /**
     * Searches for the network interface with the specified name.
     *
     * <p>
     *  搜索具有指定名称的网络接口。
     * 
     * 
     * @param   name
     *          The name of the network interface.
     *
     * @return  A {@code NetworkInterface} with the specified name,
     *          or {@code null} if there is no network interface
     *          with the specified name.
     *
     * @throws  SocketException
     *          If an I/O error occurs.
     *
     * @throws  NullPointerException
     *          If the specified name is {@code null}.
     */
    public static NetworkInterface getByName(String name) throws SocketException {
        if (name == null)
            throw new NullPointerException();
        return getByName0(name);
    }

    /**
     * Get a network interface given its index.
     *
     * <p>
     *  给定其索引获取网络接口。
     * 
     * 
     * @param index an integer, the index of the interface
     * @return the NetworkInterface obtained from its index, or {@code null} if
     *         there is no interface with such an index on the system
     * @throws  SocketException  if an I/O error occurs.
     * @throws  IllegalArgumentException if index has a negative value
     * @see #getIndex()
     * @since 1.7
     */
    public static NetworkInterface getByIndex(int index) throws SocketException {
        if (index < 0)
            throw new IllegalArgumentException("Interface index can't be negative");
        return getByIndex0(index);
    }

    /**
     * Convenience method to search for a network interface that
     * has the specified Internet Protocol (IP) address bound to
     * it.
     * <p>
     * If the specified IP address is bound to multiple network
     * interfaces it is not defined which network interface is
     * returned.
     *
     * <p>
     *  方便的方法来搜索具有绑定到它的指定的Internet协议(IP)地址的网络接口。
     * <p>
     *  如果指定的IP地址绑定到多个网络接口,则不定义返回哪个网络接口。
     * 
     * 
     * @param   addr
     *          The {@code InetAddress} to search with.
     *
     * @return  A {@code NetworkInterface}
     *          or {@code null} if there is no network interface
     *          with the specified IP address.
     *
     * @throws  SocketException
     *          If an I/O error occurs.
     *
     * @throws  NullPointerException
     *          If the specified address is {@code null}.
     */
    public static NetworkInterface getByInetAddress(InetAddress addr) throws SocketException {
        if (addr == null) {
            throw new NullPointerException();
        }
        if (!(addr instanceof Inet4Address || addr instanceof Inet6Address)) {
            throw new IllegalArgumentException ("invalid address type");
        }
        return getByInetAddress0(addr);
    }

    /**
     * Returns all the interfaces on this machine. The {@code Enumeration}
     * contains at least one element, possibly representing a loopback
     * interface that only supports communication between entities on
     * this machine.
     *
     * NOTE: can use getNetworkInterfaces()+getInetAddresses()
     *       to obtain all IP addresses for this node
     *
     * <p>
     *  返回此机器上的所有接口。 {@code枚举}包含至少一个元素,可能表示一个只支持本机上实体之间通信的回送接口。
     * 
     *  注意：可以使用getNetworkInterfaces()+ getInetAddresses()获取此节点的所有IP地址
     * 
     * 
     * @return an Enumeration of NetworkInterfaces found on this machine
     * @exception  SocketException  if an I/O error occurs.
     */

    public static Enumeration<NetworkInterface> getNetworkInterfaces()
        throws SocketException {
        final NetworkInterface[] netifs = getAll();

        // specified to return null if no network interfaces
        if (netifs == null)
            return null;

        return new Enumeration<NetworkInterface>() {
            private int i = 0;
            public NetworkInterface nextElement() {
                if (netifs != null && i < netifs.length) {
                    NetworkInterface netif = netifs[i++];
                    return netif;
                } else {
                    throw new NoSuchElementException();
                }
            }

            public boolean hasMoreElements() {
                return (netifs != null && i < netifs.length);
            }
        };
    }

    private native static NetworkInterface[] getAll()
        throws SocketException;

    private native static NetworkInterface getByName0(String name)
        throws SocketException;

    private native static NetworkInterface getByIndex0(int index)
        throws SocketException;

    private native static NetworkInterface getByInetAddress0(InetAddress addr)
        throws SocketException;

    /**
     * Returns whether a network interface is up and running.
     *
     * <p>
     *  返回网络接口是否已启动并正在运行。
     * 
     * 
     * @return  {@code true} if the interface is up and running.
     * @exception       SocketException if an I/O error occurs.
     * @since 1.6
     */

    public boolean isUp() throws SocketException {
        return isUp0(name, index);
    }

    /**
     * Returns whether a network interface is a loopback interface.
     *
     * <p>
     *  返回网络接口是否为回送接口。
     * 
     * 
     * @return  {@code true} if the interface is a loopback interface.
     * @exception       SocketException if an I/O error occurs.
     * @since 1.6
     */

    public boolean isLoopback() throws SocketException {
        return isLoopback0(name, index);
    }

    /**
     * Returns whether a network interface is a point to point interface.
     * A typical point to point interface would be a PPP connection through
     * a modem.
     *
     * <p>
     * 返回网络接口是否为点对点接口。典型的点对点接口将是通过调制解调器的PPP连接。
     * 
     * 
     * @return  {@code true} if the interface is a point to point
     *          interface.
     * @exception       SocketException if an I/O error occurs.
     * @since 1.6
     */

    public boolean isPointToPoint() throws SocketException {
        return isP2P0(name, index);
    }

    /**
     * Returns whether a network interface supports multicasting or not.
     *
     * <p>
     *  返回网络接口是否支持多播。
     * 
     * 
     * @return  {@code true} if the interface supports Multicasting.
     * @exception       SocketException if an I/O error occurs.
     * @since 1.6
     */

    public boolean supportsMulticast() throws SocketException {
        return supportsMulticast0(name, index);
    }

    /**
     * Returns the hardware address (usually MAC) of the interface if it
     * has one and if it can be accessed given the current privileges.
     * If a security manager is set, then the caller must have
     * the permission {@link NetPermission}("getNetworkInformation").
     *
     * <p>
     *  返回接口的硬件地址(通常为MAC),如果它有一个,如果它可以访问给定的当前权限。
     * 如果设置了安全管理器,则调用者必须具有{@link NetPermission}("getNetworkInformation")权限。
     * 
     * 
     * @return  a byte array containing the address, or {@code null} if
     *          the address doesn't exist, is not accessible or a security
     *          manager is set and the caller does not have the permission
     *          NetPermission("getNetworkInformation")
     *
     * @exception       SocketException if an I/O error occurs.
     * @since 1.6
     */
    public byte[] getHardwareAddress() throws SocketException {
        SecurityManager sec = System.getSecurityManager();
        if (sec != null) {
            try {
                sec.checkPermission(new NetPermission("getNetworkInformation"));
            } catch (SecurityException e) {
                if (!getInetAddresses().hasMoreElements()) {
                    // don't have connect permission to any local address
                    return null;
                }
            }
        }
        for (InetAddress addr : addrs) {
            if (addr instanceof Inet4Address) {
                return getMacAddr0(((Inet4Address)addr).getAddress(), name, index);
            }
        }
        return getMacAddr0(null, name, index);
    }

    /**
     * Returns the Maximum Transmission Unit (MTU) of this interface.
     *
     * <p>
     *  返回此接口的最大传输单元(MTU)。
     * 
     * 
     * @return the value of the MTU for that interface.
     * @exception       SocketException if an I/O error occurs.
     * @since 1.6
     */
    public int getMTU() throws SocketException {
        return getMTU0(name, index);
    }

    /**
     * Returns whether this interface is a virtual interface (also called
     * subinterface).
     * Virtual interfaces are, on some systems, interfaces created as a child
     * of a physical interface and given different settings (like address or
     * MTU). Usually the name of the interface will the name of the parent
     * followed by a colon (:) and a number identifying the child since there
     * can be several virtual interfaces attached to a single physical
     * interface.
     *
     * <p>
     *  返回此接口是否为虚拟接口(也称为子接口)。在一些系统上,虚拟接口是作为物理接口的子节点创建的并且给定不同设置(如地址或MTU)的接口。
     * 通常接口的名称将是父的名称,后跟冒号(:)和一个标识子的数字,因为可以有几个虚拟接口连接到单个物理接口。
     * 
     * 
     * @return {@code true} if this interface is a virtual interface.
     * @since 1.6
     */
    public boolean isVirtual() {
        return virtual;
    }

    private native static boolean isUp0(String name, int ind) throws SocketException;
    private native static boolean isLoopback0(String name, int ind) throws SocketException;
    private native static boolean supportsMulticast0(String name, int ind) throws SocketException;
    private native static boolean isP2P0(String name, int ind) throws SocketException;
    private native static byte[] getMacAddr0(byte[] inAddr, String name, int ind) throws SocketException;
    private native static int getMTU0(String name, int ind) throws SocketException;

    /**
     * Compares this object against the specified object.
     * The result is {@code true} if and only if the argument is
     * not {@code null} and it represents the same NetworkInterface
     * as this object.
     * <p>
     * Two instances of {@code NetworkInterface} represent the same
     * NetworkInterface if both name and addrs are the same for both.
     *
     * <p>
     *  将此对象与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},它表示与此对象相同的NetworkInterface。
     * <p>
     *  {@code NetworkInterface}的两个实例表示相同的NetworkInterface,如果name和addrs都相同。
     * 
     * 
     * @param   obj   the object to compare against.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     * @see     java.net.InetAddress#getAddress()
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof NetworkInterface)) {
            return false;
        }
        NetworkInterface that = (NetworkInterface)obj;
        if (this.name != null ) {
            if (!this.name.equals(that.name)) {
                return false;
            }
        } else {
            if (that.name != null) {
                return false;
            }
        }

        if (this.addrs == null) {
            return that.addrs == null;
        } else if (that.addrs == null) {
            return false;
        }

        /* Both addrs not null. Compare number of addresses */

        if (this.addrs.length != that.addrs.length) {
            return false;
        }

        InetAddress[] thatAddrs = that.addrs;
        int count = thatAddrs.length;

        for (int i=0; i<count; i++) {
            boolean found = false;
            for (int j=0; j<count; j++) {
                if (addrs[i].equals(thatAddrs[j])) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return name == null? 0: name.hashCode();
    }

    public String toString() {
        String result = "name:";
        result += name == null? "null": name;
        if (displayName != null) {
            result += " (" + displayName + ")";
        }
        return result;
    }

    private static native void init();

    /**
     * Returns the default network interface of this system
     *
     * <p>
     * 
     * @return the default interface
     */
    static NetworkInterface getDefault() {
        return defaultInterface;
    }
}
