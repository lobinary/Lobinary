/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import sun.misc.SharedSecrets;
import sun.misc.JavaIOFileDescriptorAccess;

/**
 * This class defines the plain DatagramSocketImpl that is used on
 * Windows platforms greater than or equal to Windows Vista. These
 * platforms have a dual layer TCP/IP stack and can handle both IPv4
 * and IPV6 through a single file descriptor.
 * <p>
 * Note: Multicasting on a dual layer TCP/IP stack is always done with
 * TwoStacksPlainDatagramSocketImpl. This is to overcome the lack
 * of behavior defined for multicasting over a dual layer socket by the RFC.
 *
 * <p>
 *  此类定义了在大于或等于Windows Vista的Windows平台上使用的普通DatagramSocketImpl。
 * 这些平台具有双层TCP / IP堆栈,并且可以通过单个文件描述符处理IPv4和IPV6。
 * <p>
 *  注意：在双层TCP / IP堆栈上的组播始终由TwoStacksPlainDatagramSocketImpl完成。这是为了克服由RFC定义的通过双层套接字的多播的行为的缺乏。
 * 
 * 
 * @author Chris Hegarty
 */

class DualStackPlainDatagramSocketImpl extends AbstractPlainDatagramSocketImpl
{
    static JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();

    // true if this socket is exclusively bound
    private final boolean exclusiveBind;

    /*
     * Set to true if SO_REUSEADDR is set after the socket is bound to
     * indicate SO_REUSEADDR is being emulated
     * <p>
     *  如果在绑定套接字以指示正在模拟SO_REUSEADDR之后设置SO_REUSEADDR,那么设置为true
     * 
     */
    private boolean reuseAddressEmulated;

    // emulates SO_REUSEADDR when exclusiveBind is true and socket is bound
    private boolean isReuseAddress;

    DualStackPlainDatagramSocketImpl(boolean exclBind) {
        exclusiveBind = exclBind;
    }

    protected void datagramSocketCreate() throws SocketException {
        if (fd == null)
            throw new SocketException("Socket closed");

        int newfd = socketCreate(false /* v6Only */);

        fdAccess.set(fd, newfd);
    }

    protected synchronized void bind0(int lport, InetAddress laddr)
        throws SocketException {
        int nativefd = checkAndReturnNativeFD();

        if (laddr == null)
            throw new NullPointerException("argument address");

        socketBind(nativefd, laddr, lport, exclusiveBind);
        if (lport == 0) {
            localPort = socketLocalPort(nativefd);
        } else {
            localPort = lport;
        }
    }

    protected synchronized int peek(InetAddress address) throws IOException {
        int nativefd = checkAndReturnNativeFD();

        if (address == null)
            throw new NullPointerException("Null address in peek()");

        // Use peekData()
        DatagramPacket peekPacket = new DatagramPacket(new byte[1], 1);
        int peekPort = peekData(peekPacket);
        address = peekPacket.getAddress();
        return peekPort;
    }

    protected synchronized int peekData(DatagramPacket p) throws IOException {
        int nativefd = checkAndReturnNativeFD();

        if (p == null)
            throw new NullPointerException("packet");
        if (p.getData() == null)
            throw new NullPointerException("packet buffer");

        int newfd = socketCreate(false /* <p>
        int newfd = socketCreate(false /*  fdAccess.set(fd,newfd); }}
        int newfd = socketCreate(false /* 
        int newfd = socketCreate(false /*  protected void bind0(int lport,InetAddress laddr)throws SocketException {int nativefd = checkAndReturnNativeFD();。
        int newfd = socketCreate(false /* 
        int newfd = socketCreate(false /*  if(laddr == null)throw new NullPointerException("argument address");
        int newfd = socketCreate(false /* 
        int newfd = socketCreate(false /*  socketBind(nativefd,laddr,lport,exclusiveBind); if(lport == 0){localPort = socketLocalPort(nativefd); }
        int newfd = socketCreate(false /*  else {localPort = lport; }}。
        int newfd = socketCreate(false /* 
        int newfd = socketCreate(false /*  protected InetAddress address(InetAddress address)throws IOException {int nativefd = checkAndReturnNativeFD();。
        int newfd = socketCreate(false /* 
        int newfd = socketCreate(false /*  if(address == null)throw new NullPointerException("Null address in peek()");
        int newfd = socketCreate(false /* 
        int newfd = socketCreate(false /*  // Use peekData()DatagramPacket peekPacket = new DatagramPacket(new byte [1],1); int peekPort = peek
        int newfd = socketCreate(false /* Data(peekPacket); address = peekPacket.getAddress(); return peekPort; }}。
        int newfd = socketCreate(false /* 
        int newfd = socketCreate(false /* protected synchronized int peekData(DatagramPacket p)throws IOException {int nativefd = checkAndReturnNativeFD();。
        int newfd = socketCreate(false /* 
        int newfd = socketCreate(false /*  if(p == null)throw new NullPointerException("packet"); if(p.getData()== null)throw new NullPointerEx
        int newfd = socketCreate(false /* ception("packet buffer");。
        int newfd = socketCreate(false /* 
        int newfd = socketCreate(false /* 
        return socketReceiveOrPeekData(nativefd, p, timeout, connected, true /*peek*/);
    }

    protected synchronized void receive0(DatagramPacket p) throws IOException {
        int nativefd = checkAndReturnNativeFD();

        if (p == null)
            throw new NullPointerException("packet");
        if (p.getData() == null)
            throw new NullPointerException("packet buffer");

        return socketReceiveOrPeekData(nativefd, p, timeout, connected, true /* <p>
        return socketReceiveOrPeekData(nativefd, p, timeout, connected, true /*  }}
        return socketReceiveOrPeekData(nativefd, p, timeout, connected, true /* 
        return socketReceiveOrPeekData(nativefd, p, timeout, connected, true /*  protected synchronized void receive0(DatagramPacket p)throws IOException {int nativefd = checkAndReturnNativeFD();。
        return socketReceiveOrPeekData(nativefd, p, timeout, connected, true /* 
        return socketReceiveOrPeekData(nativefd, p, timeout, connected, true /*  if(p == null)throw new NullPointerException("packet"); if(p.getData()== null)throw new NullPointerEx
        return socketReceiveOrPeekData(nativefd, p, timeout, connected, true /* ception("packet buffer");。
        return socketReceiveOrPeekData(nativefd, p, timeout, connected, true /* 
        return socketReceiveOrPeekData(nativefd, p, timeout, connected, true /* 
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /*receive*/);
    }

    protected void send(DatagramPacket p) throws IOException {
        int nativefd = checkAndReturnNativeFD();

        if (p == null)
            throw new NullPointerException("null packet");

        if (p.getAddress() == null ||p.getData() ==null)
            throw new NullPointerException("null address || null buffer");

        socketSend(nativefd, p.getData(), p.getOffset(), p.getLength(),
                   p.getAddress(), p.getPort(), connected);
    }

    protected void connect0(InetAddress address, int port) throws SocketException {
        int nativefd = checkAndReturnNativeFD();

        if (address == null)
            throw new NullPointerException("address");

        socketConnect(nativefd, address, port);
    }

        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* <p>
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /*  }}
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* 
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /*  protected void send(DatagramPacket p)throws IOException {int nativefd = checkAndReturnNativeFD();
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* 
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /*  if(p == null)throw new NullPointerException("null packet");
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* 
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /*  if(p.getAddress()== null || p.getData()== null)throw new NullPointerException("null address || null 
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* buffer");。
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* 
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /*  socketSend(nativefd,p.getData(),p.getOffset(),p.getLength(),p.getAddress(),p.getPort(),connected); }
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* }。
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* 
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /*  protected void connect0(InetAddress address,int port)throws SocketException {int nativefd = checkAndReturnNativeFD();。
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* 
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /*  if(address == null)throw new NullPointerException("address");
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* 
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /*  socketConnect(nativefd,address,port); }}
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* 
        socketReceiveOrPeekData(nativefd, p, timeout, connected, false /* 
    protected void disconnect0(int family /*unused*/) {
        if (fd == null || !fd.valid())
            return;   // disconnect doesn't throw any exceptions

        socketDisconnect(fdAccess.get(fd));
    }

    protected void datagramSocketClose() {
        if (fd == null || !fd.valid())
            return;   // close doesn't throw any exceptions

        socketClose(fdAccess.get(fd));
        fdAccess.set(fd, -1);
    }

    @SuppressWarnings("fallthrough")
    protected void socketSetOption(int opt, Object val) throws SocketException {
        int nativefd = checkAndReturnNativeFD();

        int optionValue = 0;

        switch(opt) {
            case IP_TOS :
            case SO_RCVBUF :
            case SO_SNDBUF :
                optionValue = ((Integer)val).intValue();
                break;
            case SO_REUSEADDR :
                if (exclusiveBind && localPort != 0)  {
                    // socket already bound, emulate SO_REUSEADDR
                    reuseAddressEmulated = true;
                    isReuseAddress = (Boolean)val;
                    return;
                }
                //Intentional fallthrough
            case SO_BROADCAST :
                optionValue = ((Boolean)val).booleanValue() ? 1 : 0;
                break;
    protected void disconnect0(int family /* <p>
    protected void disconnect0(int family /*  if(fd == null ||！fd.valid())return; // disconnect不会抛出任何异常
    protected void disconnect0(int family /* 
    protected void disconnect0(int family /*  socketDisconnect(fdAccess.get(fd)); }}
    protected void disconnect0(int family /* 
    protected void disconnect0(int family /*  protected void datagramSocketClose(){if(fd == null ||！fd.valid())return; // close不会抛出任何异常
    protected void disconnect0(int family /* 
    protected void disconnect0(int family /*  socketClose(fdAccess.get(fd)); fdAccess.set(fd,-1); }}
    protected void disconnect0(int family /* 
    protected void disconnect0(int family /* @SuppressWarnings("fallthrough")protected void socketSetOption(int opt,Object val)throws SocketExcept
    protected void disconnect0(int family /* ion {int nativefd = checkAndReturnNativeFD();。
    protected void disconnect0(int family /* 
            default: /* shouldn't get here */
                throw new SocketException("Option not supported");
        }

        socketSetIntOption(nativefd, opt, optionValue);
    }

    protected Object socketGetOption(int opt) throws SocketException {
        int nativefd = checkAndReturnNativeFD();

         // SO_BINDADDR is not a socket option.
        if (opt == SO_BINDADDR) {
            return socketLocalAddress(nativefd);
        }
        if (opt == SO_REUSEADDR && reuseAddressEmulated)
            return isReuseAddress;

        int value = socketGetIntOption(nativefd, opt);
        Object returnValue = null;

        switch (opt) {
            case SO_REUSEADDR :
            case SO_BROADCAST :
                returnValue =  (value == 0) ? Boolean.FALSE : Boolean.TRUE;
                break;
            case IP_TOS :
            case SO_RCVBUF :
            case SO_SNDBUF :
                returnValue = new Integer(value);
                break;
            default: /* shouldn't get here */
                throw new SocketException("Option not supported");
        }

        return returnValue;
    }

    /* Multicast specific methods.
     * Multicasting on a dual layer TCP/IP stack is always done with
     * TwoStacksPlainDatagramSocketImpl. This is to overcome the lack
     * of behavior defined for multicasting over a dual layer socket by the RFC.
     * <p>
     * 
     *  int optionValue = 0;
     * 
     *  switch(opt){case IP_TOS：case SO_RCVBUF：case SO_SNDBUF：optionValue =((Integer)val).intValue();打破; case SO_REUSEADDR：if(exclusiveBind && localPort！= 0){//套接字已绑定,模拟SO_REUSEADDR reuseAddressEmulated = true; isReuseAddress =(Boolean)val;返回; }
     *  //意图fallthrough case SO_BROADCAST：optionValue =((Boolean)val).booleanValue()? 1：0;打破;。
     */
    protected void join(InetAddress inetaddr, NetworkInterface netIf)
        throws IOException {
        throw new IOException("Method not implemented!");
    }

    protected void leave(InetAddress inetaddr, NetworkInterface netIf)
        throws IOException {
        throw new IOException("Method not implemented!");
    }

    protected void setTimeToLive(int ttl) throws IOException {
        throw new IOException("Method not implemented!");
    }

    protected int getTimeToLive() throws IOException {
        throw new IOException("Method not implemented!");
    }

    @Deprecated
    protected void setTTL(byte ttl) throws IOException {
        throw new IOException("Method not implemented!");
    }

    @Deprecated
    protected byte getTTL() throws IOException {
        throw new IOException("Method not implemented!");
    }
    /* END Multicast specific methods */

    private int checkAndReturnNativeFD() throws SocketException {
        if (fd == null || !fd.valid())
            throw new SocketException("Socket closed");

        return fdAccess.get(fd);
    }

    /* Native methods */

    private static native void initIDs();

    private static native int socketCreate(boolean v6Only);

    private static native void socketBind(int fd, InetAddress localAddress,
            int localport, boolean exclBind) throws SocketException;

    private static native void socketConnect(int fd, InetAddress address, int port)
        throws SocketException;

    private static native void socketDisconnect(int fd);

    private static native void socketClose(int fd);

    private static native int socketLocalPort(int fd) throws SocketException;

    private static native Object socketLocalAddress(int fd) throws SocketException;

    private static native int socketReceiveOrPeekData(int fd, DatagramPacket packet,
        int timeout, boolean connected, boolean peek) throws IOException;

    private static native void socketSend(int fd, byte[] data, int offset, int length,
        InetAddress address, int port, boolean connected) throws IOException;

    private static native void socketSetIntOption(int fd, int cmd,
        int optionValue) throws SocketException;

    private static native int socketGetIntOption(int fd, int cmd) throws SocketException;
}
