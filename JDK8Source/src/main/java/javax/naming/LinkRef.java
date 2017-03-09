/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming;

/**
  * This class represents a Reference whose contents is a name, called the link name,
  * that is bound to an atomic name in a context.
  *<p>
  * The name is a URL, or a name to be resolved relative to the initial
  * context, or if the first character of the name is ".", the name
  * is relative to the context in which the link is bound.
  *<p>
  * Normal resolution of names in context operations always follow links.
  * Resolution of the link name itself may cause resolution to pass through
  * other  links. This gives rise to the possibility of a cycle of links whose
  * resolution could not terminate normally. As a simple means to avoid such
  * non-terminating resolutions, service providers may define limits on the
  * number of links that may be involved in any single operation invoked
  * by the caller.
  *<p>
  * A LinkRef contains a single StringRefAddr, whose type is "LinkAddress",
  * and whose contents is the link name. The class name field of the
  * Reference is that of this (LinkRef) class.
  *<p>
  * LinkRef is bound to a name using the normal Context.bind()/rebind(), and
  * DirContext.bind()/rebind(). Context.lookupLink() is used to retrieve the link
  * itself if the terminal atomic name is bound to a link.
  *<p>
  * Many naming systems support a native notion of link that may be used
  * within the naming system itself. JNDI does not specify whether
  * there is any relationship between such native links and JNDI links.
  *<p>
  * A LinkRef instance is not synchronized against concurrent access by multiple
  * threads. Threads that need to access a LinkRef instance concurrently should
  * synchronize amongst themselves and provide the necessary locking.
  *
  * <p>
  *  此类表示一个引用,其内容是一个名称,称为链接名称,绑定到上下文中的原子名称。
  * p>
  *  名称是URL或要相对于初始上下文解析的名称,或者如果名称的第一个字符是"。",则名称是相对于链接所绑定的上下文。
  * p>
  *  在上下文操作中名称的正常解析总是跟随链接。解析链接名称本身可能导致解析通过其他链接。这导致链路的循环的可能性,其分辨率不能正常终止。
  * 作为避免这种非终止分辨率的简单手段,服务提供商可以定义对由呼叫者调用的任何单个操作中可能涉及的链路数量的限制。
  * p>
  *  LinkRef包含一个StringRefAddr,其类型为"LinkAddress",其内容为链接名称。引用的类名字段是这个(LinkRef)类的类。
  * p>
  *  LinkRef使用正常的Context.bind()/ rebind()和DirContext.bind()/ rebind()绑定到一个名称。
  * 如果终端原子名称绑定到链接,Context.lookupLink()用于检索链接本身。
  * p>
  *  许多命名系统支持可以在命名系统本身内使用的链接的本地概念。 JNDI不指定这样的本地链接和JNDI链接之间是否存在任何关系。
  * p>
  * LinkRef实例不会与多个线程的并发访问同步。需要并发访问LinkRef实例的线程应在它们之间同步并提供必要的锁定。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see LinkException
  * @see LinkLoopException
  * @see MalformedLinkException
  * @see Context#lookupLink
  * @since 1.3
  */

  /*<p>
  * The serialized form of a LinkRef object consists of the serialized
  * fields of its Reference superclass.
  * <p>
  */

public class LinkRef extends Reference {
    /* code for link handling */
    static final String linkClassName = LinkRef.class.getName();
    static final String linkAddrType = "LinkAddress";

    /**
      * Constructs a LinkRef for a name.
      * <p>
      *  LinkRef对象的序列化形式由其参考超类的序列化字段组成。
      * 
      * 
      * @param linkName The non-null name for which to create this link.
      */
    public LinkRef(Name linkName) {
        super(linkClassName, new StringRefAddr(linkAddrType, linkName.toString()));
    }

    /**
      * Constructs a LinkRef for a string name.
      * <p>
      *  为名称构造LinkRef。
      * 
      * 
      * @param linkName The non-null name for which to create this link.
      */
    public LinkRef(String linkName) {
        super(linkClassName, new StringRefAddr(linkAddrType, linkName));
    }

    /**
      * Retrieves the name of this link.
      *
      * <p>
      *  构造一个字符串名称的LinkRef。
      * 
      * 
      * @return The non-null name of this link.
      * @exception MalformedLinkException If a link name could not be extracted
      * @exception NamingException If a naming exception was encountered.
      */
    public String getLinkName() throws NamingException {
        if (className != null && className.equals(linkClassName)) {
            RefAddr addr = get(linkAddrType);
            if (addr != null && addr instanceof StringRefAddr) {
                return (String)((StringRefAddr)addr).getContent();
            }
        }
        throw new MalformedLinkException();
    }
    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  检索此链接的名称。
     * 
     */
    private static final long serialVersionUID = -5386290613498931298L;
}
