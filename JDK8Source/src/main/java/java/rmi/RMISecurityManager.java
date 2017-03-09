/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi;

import java.security.*;

/**
 * {@code RMISecurityManager} implements a policy identical to the policy
 * implemented by {@link SecurityManager}. RMI applications
 * should use the {@code SecurityManager} class or another appropriate
 * {@code SecurityManager} implementation instead of this class. RMI's class
 * loader will download classes from remote locations only if a security
 * manager has been set.
 *
 * @implNote
 * <p>Applets typically run in a container that already has a security
 * manager, so there is generally no need for applets to set a security
 * manager. If you have a standalone application, you might need to set a
 * {@code SecurityManager} in order to enable class downloading. This can be
 * done by adding the following to your code. (It needs to be executed before
 * RMI can download code from remote hosts, so it most likely needs to appear
 * in the {@code main} method of your application.)
 *
 * <pre>{@code
 *    if (System.getSecurityManager() == null) {
 *        System.setSecurityManager(new SecurityManager());
 *    }
 * }</pre>
 *
 * <p>
 *  {@code RMISecurityManager}实施与{@link SecurityManager}实施的策略相同的策略。
 *  RMI应用程序应该使用{@code SecurityManager}类或另一个适当的{@code SecurityManager}实现,而不是此类。
 *  RMI的类装载器只有在设置了安全管理器时才从远程位置下载类。
 * 
 *  @implNote <p> Applet通常在已经具有安全管理器的容器中运行,因此通常不需要applet来设置安全管理器。
 * 如果您有独立应用程序,则可能需要设置{@code SecurityManager}才能启用类下载。这可以通过在代码中添加以下代码来实现。
 *  (它需要在RMI可以从远程主机下载代码之前执行,因此很可能需要显示在应用程序的{@code main}方法中。)。
 * 
 * @author  Roger Riggs
 * @author  Peter Jones
 * @since JDK1.1
 * @deprecated Use {@link SecurityManager} instead.
 */
@Deprecated
public class RMISecurityManager extends SecurityManager {

    /**
     * Constructs a new {@code RMISecurityManager}.
     * <p>
     * 
     *  <pre> {@ code if(System.getSecurityManager()== null){System.setSecurityManager(new SecurityManager()); }
     * } </pre>。
     * 
     * 
     * @since JDK1.1
     */
    public RMISecurityManager() {
    }
}
