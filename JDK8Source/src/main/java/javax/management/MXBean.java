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

package javax.management;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// remaining imports are for Javadoc
import java.io.InvalidObjectException;
import java.lang.management.MemoryUsage;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.List;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataInvocationHandler;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeDataView;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenMBeanInfo;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

/**
    <p>Annotation to mark an interface explicitly as being an MXBean
    interface, or as not being an MXBean interface.  By default, an
    interface is an MXBean interface if it is public and its name ends
    with {@code MXBean}, as in {@code SomethingMXBean}.  The following
    interfaces are MXBean interfaces:</p>

    <pre>
    public interface WhatsitMXBean {}

    &#64;MXBean
    public interface Whatsit1Interface {}

    &#64;MXBean(true)
    public interface Whatsit2Interface {}
    </pre>

    <p>The following interfaces are not MXBean interfaces:</p>

    <pre>
    interface NonPublicInterfaceNotMXBean{}

    public interface Whatsit3Interface{}

    &#64;MXBean(false)
    public interface MisleadingMXBean {}
    </pre>

    <h3 id="MXBean-spec">MXBean specification</h3>

    <p>The MXBean concept provides a simple way to code an MBean
      that only references a predefined set of types, the ones defined
      by {@link javax.management.openmbean}.  In this way, you can be
      sure that your MBean will be usable by any client, including
      remote clients, without any requirement that the client have
      access to <em>model-specific classes</em> representing the types
      of your MBeans.</p>

    <p>The concepts are easier to understand by comparison with the
      Standard MBean concept.  Here is how a managed object might be
      represented as a Standard MBean, and as an MXBean:</p>

    <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
      <tr>
        <th>Standard MBean</th><th>MXBean</th>
      </tr>
      <tr>
        <td><pre>
public interface MemoryPool<b>MBean</b> {
    String getName();
    MemoryUsage getUsage();
    // ...
}
          </pre></td>
        <td><pre>
public interface MemoryPool<b>MXBean</b> {
    String getName();
    MemoryUsage getUsage();
    // ...
}
          </pre></td>
      </tr>
    </table>

    <p>As you can see, the definitions are very similar.  The only
      difference is that the convention for naming the interface is to use
      <code><em>Something</em>MXBean</code> for MXBeans, rather than
      <code><em>Something</em>MBean</code> for Standard MBeans.</p>

    <p>In this managed object, there is an attribute called
      <code>Usage</code> of type {@link MemoryUsage}.  The point of an
      attribute like this is that it gives a coherent snapshot of a set
      of data items.  For example, it might include the current amount
      of used memory in the memory pool, and the current maximum of the
      memory pool.  If these were separate items, obtained with separate
      {@link MBeanServer#getAttribute getAttribute} calls, then we could
      get values seen at different times that were not consistent.  We
      might get a <code>used</code> value that was greater than the
      <code>max</code> value.</p>

    <p>So, we might define <code>MemoryUsage</code> like this:</p>

    <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
      <tr>
        <th>Standard MBean</th><th>MXBean</th>
      </tr>
      <tr>
        <td><pre>
public class MemoryUsage <b>implements Serializable</b> {
    // standard JavaBean conventions with getters

    public MemoryUsage(long init, long used,
                       long committed, long max) {...}
    long getInit() {...}
    long getUsed() {...}
    long getCommitted() {...}
    long getMax() {...}
}
          </pre></td>
        <td><pre>
public class MemoryUsage {
    // standard JavaBean conventions with getters
    <b>&#64;ConstructorProperties({"init", "used", "committed", "max"})</b>
    public MemoryUsage(long init, long used,
                       long committed, long max) {...}
    long getInit() {...}
    long getUsed() {...}
    long getCommitted() {...}
    long getMax() {...}
}
          </pre></td>
      </tr>
    </table>

    <p>The definitions are the same in the two cases, except
      that with the MXBean, <code>MemoryUsage</code> no longer needs to
      be marked <code>Serializable</code> (though it can be).  On
      the other hand, we have added a {@code @ConstructorProperties} annotation
      to link the constructor parameters to the corresponding getters.
      We will see more about this below.</p>

    <p><code>MemoryUsage</code> is a <em>model-specific class</em>.
      With Standard MBeans, a client of the MBean Server cannot access the
      <code>Usage</code> attribute if it does not know the class
      <code>MemoryUsage</code>.  Suppose the client is a generic console
      based on JMX technology.  Then the console would have to be
      configured with the model-specific classes of every application it
      might connect to.  The problem is even worse for clients that are
      not written in the Java language.  Then there may not be any way
      to tell the client what a <code>MemoryUsage</code> looks like.</p>

    <p>This is where MXBeans differ from Standard MBeans.  Although we
      define the management interface in almost exactly the same way,
      the MXBean framework <em>converts</em> model-specific classes into
      standard classes from the Java platform.  Using arrays and the
      {@link javax.management.openmbean.CompositeData CompositeData} and
      {@link javax.management.openmbean.TabularData TabularData} classes
      from the standard {@link javax.management.openmbean} package, it
      is possible to build data structures of arbitrary complexity
      using only standard classes.</p>

    <p>This becomes clearer if we compare what the clients of the two
      models might look like:</p>

    <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
      <tr>
        <th>Standard MBean</th><th>MXBean</th>
      </tr>
      <tr>
        <td><pre>
String name = (String)
    mbeanServer.{@link MBeanServer#getAttribute
    getAttribute}(objectName, "Name");
<b>MemoryUsage</b> usage = (<b>MemoryUsage</b>)
    mbeanServer.getAttribute(objectName, "Usage");
<b>long used = usage.getUsed();</b>
          </pre></td>
        <td><pre>
String name = (String)
    mbeanServer.{@link MBeanServer#getAttribute
    getAttribute}(objectName, "Name");
<b>{@link CompositeData}</b> usage = (<b>CompositeData</b>)
    mbeanServer.getAttribute(objectName, "Usage");
<b>long used = (Long) usage.{@link CompositeData#get get}("used");</b>
          </pre></td>
    </table>

    <p>For attributes with simple types like <code>String</code>, the
      code is the same.  But for attributes with complex types, the
      Standard MBean code requires the client to know the model-specific
      class <code>MemoryUsage</code>, while the MXBean code requires no
      non-standard classes.</p>

    <p>The client code shown here is slightly more complicated for the
      MXBean client.  But, if the client does in fact know the model,
      here the interface <code>MemoryPoolMXBean</code> and the
      class <code>MemoryUsage</code>, then it can construct a
      <em>proxy</em>.  This is the recommended way to interact with
      managed objects when you know the model beforehand, regardless
      of whether you are using Standard MBeans or MXBeans:</p>

    <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
      <tr>
        <th>Standard MBean</th><th>MXBean</th>
      </tr>
      <tr>
        <td><pre>
MemoryPool<b>MBean</b> proxy =
    JMX.<b>{@link JMX#newMBeanProxy(MBeanServerConnection, ObjectName,
              Class) newMBeanProxy}</b>(
        mbeanServer,
        objectName,
        MemoryPool<b>MBean</b>.class);
String name = proxy.getName();
MemoryUsage usage = proxy.getUsage();
long used = usage.getUsed();
          </pre></td>
        <td><pre>
MemoryPool<b>MXBean</b> proxy =
    JMX.<b>{@link JMX#newMXBeanProxy(MBeanServerConnection, ObjectName,
              Class) newMXBeanProxy}</b>(
        mbeanServer,
        objectName,
        MemoryPool<b>MXBean</b>.class);
String name = proxy.getName();
MemoryUsage usage = proxy.getUsage();
long used = usage.getUsed();
          </pre></td>
      </tr>
    </table>

    <p>Implementing the MemoryPool object works similarly for both
      Standard MBeans and MXBeans.</p>

    <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
      <tr>
        <th>Standard MBean</th><th>MXBean</th>
      </tr>
      <tr>
        <td><pre>
public class MemoryPool
        implements MemoryPool<b>MBean</b> {
    public String getName() {...}
    public MemoryUsage getUsage() {...}
    // ...
}
          </pre></td>
        <td><pre>
public class MemoryPool
        implements MemoryPool<b>MXBean</b> {
    public String getName() {...}
    public MemoryUsage getUsage() {...}
    // ...
}
          </pre></td>
      </tr>
    </table>

    <p>Registering the MBean in the MBean Server works in the same way
      in both cases:</p>

    <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
      <tr>
        <th>Standard MBean</th><th>MXBean</th>
      </tr>
      <tr>
        <td><pre>
{
    MemoryPool<b>MBean</b> pool = new MemoryPool();
    mbeanServer.{@link MBeanServer#registerMBean
    registerMBean}(pool, objectName);
}
          </pre></td>
        <td><pre>
{
    MemoryPool<b>MXBean</b> pool = new MemoryPool();
    mbeanServer.{@link MBeanServer#registerMBean
    registerMBean}(pool, objectName);
}
          </pre></td>
      </tr>
    </table>


    <h2 id="mxbean-def">Definition of an MXBean</h2>

    <p>An MXBean is a kind of MBean.  An MXBean object can be
      registered directly in the MBean Server, or it can be used as an
      argument to {@link StandardMBean} and the resultant MBean
      registered in the MBean Server.</p>

    <p>When an object is registered in the MBean Server using the
      {@code registerMBean} or {@code createMBean} methods of the
      {@link MBeanServer} interface, the object's class is examined
      to determine what type of MBean it is:</p>

    <ul>
      <li>If the class implements the interface {@link DynamicMBean}
        then the MBean is a Dynamic MBean.  Note that the class
        {@code StandardMBean} implements this interface, so this
        case applies to a Standard MBean or MXBean created using
        the class {@code StandardMBean}.</li>

      <li>Otherwise, if the class matches the Standard MBean naming
        conventions, then the MBean is a Standard MBean.</li>

      <li>Otherwise, it may be an MXBean.  The set of interfaces
        implemented by the object is examined for interfaces that:

        <ul>
          <li>have a class name <code><em>S</em>MXBean</code> where
            <code><em>S</em></code> is any non-empty string, and
            do not have an annotation {@code @MXBean(false)}; and/or</li>
          <li>have an annotation {@code @MXBean(true)}
            or just {@code @MXBean}.</li>
        </ul>

        If there is exactly one such interface, or if there is one
        such interface that is a subinterface of all the others, then
        the object is an MXBean.  The interface in question is the
        <em>MXBean interface</em>.  In the example above, the MXBean
        interface is {@code MemoryPoolMXBean}.

      <li>If none of these conditions is met, the MBean is invalid and
        the attempt to register it will generate {@link
        NotCompliantMBeanException}.
    </ul>

    <p>Every Java type that appears as the parameter or return type of a
      method in an MXBean interface must be <em>convertible</em> using
      the rules below.  Additionally, parameters must be
      <em>reconstructible</em> as defined below.</p>

    <p>An attempt to construct an MXBean that does not conform to the
      above rules will produce an exception.</p>


    <h2 id="naming-conv">Naming conventions</h2>

    <p>The same naming conventions are applied to the methods in an
      MXBean as in a Standard MBean:</p>

    <ol>
      <li>A method <code><em>T</em> get<em>N</em>()</code>, where
        <code><em>T</em></code> is a Java type (not <code>void</code>)
        and <code><em>N</em></code> is a non-empty string, specifies
        that there is a readable attribute called
        <code><em>N</em></code>.  The Java type and Open type of the
        attribute are determined by the mapping rules below.
        The method {@code final Class getClass()} inherited from {@code
        Object} is ignored when looking for getters.</li>

      <li>A method <code>boolean is<em>N</em>()</code> specifies that
        there is a readable attribute called <code><em>N</em></code>
        with Java type <code>boolean</code> and Open type
        <code>SimpleType.Boolean</code>.</li>

      <li>A method <code>void set<em>N</em>(<em>T</em> x)</code>
        specifies that there is a writeable attribute called
        <code><em>N</em></code>.  The Java type and Open type of the
        attribute are determined by the mapping rules below.  (Of
        course, the name <code>x</code> of the parameter is
        irrelevant.)</li>

      <li>Every other method specifies that there is an operation with
        the same name as the method.  The Java type and Open type of the
        return value and of each parameter are determined by the mapping
        rules below.</li>
    </ol>

    <p>The rules for <code>get<em>N</em></code> and
      <code>is<em>N</em></code> collectively define the notion of a
      <em>getter</em>.  The rule for <code>set<em>N</em></code> defines
      the notion of a <em>setter</em>.</p>

    <p>It is an error for there to be two getters with the same name, or
      two setters with the same name.  If there is a getter and a setter
      for the same name, then the type <code><em>T</em></code> in both
      must be the same.  In this case the attribute is read/write.  If
      there is only a getter or only a setter, the attribute is
      read-only or write-only respectively.</p>


    <h2 id="mapping-rules">Type mapping rules</h2>

    <p>An MXBean is a kind of Open MBean, as defined by the {@link
      javax.management.openmbean} package.  This means that the types of
      attributes, operation parameters, and operation return values must
      all be describable using <em>Open Types</em>, that is the four
      standard subclasses of {@link javax.management.openmbean.OpenType}.
      MXBeans achieve this by mapping Java types into Open Types.</p>

    <p>For every Java type <em>J</em>, the MXBean mapping is described
      by the following information:</p>

    <ul>
      <li>The corresponding Open Type, <em>opentype(J)</em>.  This is
        an instance of a subclass of {@link
        javax.management.openmbean.OpenType}.</li>
      <li>The <em>mapped</em> Java type, <em>opendata(J)</em>, which is
        always the same for any given <em>opentype(J)</em>.  This is a Java
        class.</li>
      <li>How a value is converted from type <em>J</em> to type
        <em>opendata(J)</em>.</li>
      <li>How a value is converted from type <em>opendata(J)</em> to
        type <em>J</em>, if it can be.</li>
    </ul>

    <p>For example, for the Java type {@code List<String>}:</p>

    <ul>
      <li>The Open Type, <em>opentype(</em>{@code
        List<String>}<em>)</em>, is {@link ArrayType}<code>(1, </code>{@link
          SimpleType#STRING}<code>)</code>, representing a 1-dimensional
          array of <code>String</code>s.</li>
      <li>The mapped Java type, <em>opendata(</em>{@code
        List<String>}<em>)</em>, is {@code String[]}.</li>
      <li>A {@code List<String>} can be converted to a {@code String[]}
          using {@link List#toArray(Object[]) List.toArray(new
          String[0])}.</li>
      <li>A {@code String[]} can be converted to a {@code List<String>}
          using {@link Arrays#asList Arrays.asList}.</li>
    </ul>

    <p>If no mapping rules exist to derive <em>opentype(J)</em> from
      <em>J</em>, then <em>J</em> cannot be the type of a method
      parameter or return value in an MXBean interface.</p>

    <p id="reconstructible-def">If there is a way to convert
      <em>opendata(J)</em> back to <em>J</em> then we say that <em>J</em> is
      <em>reconstructible</em>.  All method parameters in an MXBean
      interface must be reconstructible, because when the MXBean
      framework is invoking a method it will need to convert those
      parameters from <em>opendata(J)</em> to <em>J</em>.  In a proxy
      generated by {@link JMX#newMXBeanProxy(MBeanServerConnection,
      ObjectName, Class) JMX.newMXBeanProxy}, it is the return values
      of the methods in the MXBean interface that must be
      reconstructible.</p>

    <p>Null values are allowed for all Java types and Open Types,
      except primitive Java types where they are not possible.  When
      converting from type <em>J</em> to type <em>opendata(J)</em> or
      from type <em>opendata(J)</em> to type <em>J</em>, a null value is
      mapped to a null value.</p>

    <p>The following table summarizes the type mapping rules.</p>

    <table border="1" cellpadding="5" summary="Type Mapping Rules">
      <tr>
        <th>Java type <em>J</em></th>
        <th><em>opentype(J)</em></th>
        <th><em>opendata(J)</em></th>
      </tr>
      <tbody valign="top">
        <tr>
          <td>{@code int}, {@code boolean}, etc<br>
            (the 8 primitive Java types)</td>
          <td>{@code SimpleType.INTEGER},<br>
            {@code SimpleType.BOOLEAN}, etc</td>
          <td>{@code Integer}, {@code Boolean}, etc<br>
            (the corresponding boxed types)</td>
        </tr>
        <tr>
          <td>{@code Integer}, {@code ObjectName}, etc<br>
            (the types covered by {@link SimpleType})</td>
          <td>the corresponding {@code SimpleType}</td>
          <td><em>J</em>, the same type</td>
        </tr>
        <tr>
          <td>{@code int[]} etc<br>
            (a one-dimensional array with<br>
            primitive element type)</td>
          <td>{@code ArrayType.getPrimitiveArrayType(int[].class)} etc</td>
          <td><em>J</em>, the same type</td>
        <tr>
          <td><em>E</em>{@code []}<br>
            (an array with non-primitive element type <em>E</em>;
              this includes {@code int[][]}, where <em>E</em> is {@code int[]})</td>
          <td>{@code ArrayType.getArrayType(}<em>opentype(E)</em>{@code )}</td>
          <td><em>opendata(E)</em>{@code []}</td>
        </tr>
        <tr>
          <td>{@code List<}<em>E</em>{@code >}<br>
            {@code Set<}<em>E</em>{@code >}<br>
            {@code SortedSet<}<em>E</em>{@code >} (see below)</td>
          <td>same as for <em>E</em>{@code []}</td>
          <td>same as for <em>E</em>{@code []}</td>
        </tr>
        <tr>
          <td>An enumeration <em>E</em><br>
            (declared in Java as {@code enum }<em>E</em>
            {@code {...}})</td>
          <td>{@code SimpleType.STRING}</td>
          <td>{@code String}</td>
        </tr>
        <tr>
          <td>{@code Map<}<em>K</em>,<em>V</em>{@code >}<br>
            {@code SortedMap<}<em>K</em>,<em>V</em>{@code >}</td>
          <td>{@link TabularType}<br>
            (see below)</td>
          <td>{@link TabularData}<br>
            (see below)</td>
        </tr>
        <tr>
          <td>An MXBean interface</td>
          <td>{@code SimpleType.OBJECTNAME}<br>
            (see below)</td>
          <td>{@link ObjectName}<br>
            (see below)</td>
        </tr>
        <tr>
          <td>Any other type</td>
          <td>{@link CompositeType},
            if possible<br>
            (see below)</td>
          <td>{@link CompositeData}</td>
      </tbody>
    </table>

    <p>The following sections give further details of these rules.</p>


    <h3>Mappings for primitive types</h3>

    <p>The 8 primitive Java types
      ({@code boolean}, {@code byte}, {@code short}, {@code int}, {@code
      long}, {@code float}, {@code double}, {@code char}) are mapped to the
      corresponding boxed types from {@code java.lang}, namely {@code
      Boolean}, {@code Byte}, etc.  The Open Type is the corresponding
      {@code SimpleType}.  Thus, <em>opentype(</em>{@code
      long}<em>)</em> is {@code SimpleType.LONG}, and
      <em>opendata(</em>{@code long}<em>)</em> is {@code
      java.lang.Long}.</p>

    <p>An array of primitive type such as {@code long[]} can be represented
      directly as an Open Type.  Thus, <em>openType(</em>{@code
      long[]}<em>)</em> is {@code
      ArrayType.getPrimitiveArrayType(long[].class)}, and
      <em>opendata(</em>{@code long[]}<em>)</em> is {@code
      long[]}.</p>

    <p>In practice, the difference between a plain {@code int} and {@code
      Integer}, etc, does not show up because operations in the JMX API
      are always on Java objects, not primitives.  However, the
      difference <em>does</em> show up with arrays.</p>


    <h3>Mappings for collections ({@code List<}<em>E</em>{@code >} etc)</h3>

    <p>A {@code List<}<em>E</em>{@code >} or {@code
      Set<}<em>E</em>{@code >}, such as {@code List<String>} or {@code
        Set<ObjectName>}, is mapped in the same way as an array of the
          same element type, such as {@code String[]} or {@code
          ObjectName[]}.</p>

    <p>A {@code SortedSet<}<em>E</em>{@code >} is also mapped in the
      same way as an <em>E</em>{@code []}, but it is only convertible if
      <em>E</em> is a class or interface that implements {@link
      java.lang.Comparable}.  Thus, a {@code SortedSet<String>} or
        {@code SortedSet<Integer>} is convertible, but a {@code
          SortedSet<int[]>} or {@code SortedSet<List<String>>} is not.  The
                conversion of a {@code SortedSet} instance will fail with an
                {@code IllegalArgumentException} if it has a
                non-null {@link java.util.SortedSet#comparator()
                comparator()}.</p>

    <p>A {@code List<}<em>E</em>{@code >} is reconstructed as a
      {@code java.util.ArrayList<}<em>E</em>{@code >};
      a {@code Set<}<em>E</em>{@code >} as a
      {@code java.util.HashSet<}<em>E</em>{@code >};
      a {@code SortedSet<}<em>E</em>{@code >} as a
      {@code java.util.TreeSet<}<em>E</em>{@code >}.</p>


    <h3>Mappings for maps ({@code Map<}<em>K</em>,<em>V</em>{@code >} etc)</h3>

    <p>A {@code Map<}<em>K</em>,<em>V</em>{@code >} or {@code
      SortedMap<}<em>K</em>,<em>V</em>{@code >}, for example {@code
      Map<String,ObjectName>}, has Open Type {@link TabularType} and is mapped
        to a {@link TabularData}.
        The {@code TabularType} has two items called {@code key} and
        {@code value}.  The Open Type of {@code key} is
        <em>opentype(K)</em>, and the Open Type of {@code value} is
        <em>opentype(V)</em>.  The index of the {@code TabularType} is the
        single item {@code key}.</p>

    <p>For example, the {@code TabularType} for a {@code
      Map<String,ObjectName>} might be constructed with code like
        this:</p>

    <pre>
String typeName =
    "java.util.Map&lt;java.lang.String, javax.management.ObjectName&gt;";
String[] keyValue =
    new String[] {"key", "value"};
OpenType[] openTypes =
    new OpenType[] {SimpleType.STRING, SimpleType.OBJECTNAME};
CompositeType rowType =
    new CompositeType(typeName, typeName, keyValue, keyValue, openTypes);
TabularType tabularType =
    new TabularType(typeName, typeName, rowType, new String[] {"key"});
    </pre>

    <p>The {@code typeName} here is determined by the <a href="#type-names">
      type name rules</a> detailed below.

    <p>A {@code SortedMap<}<em>K</em>,<em>V</em>{@code >} is mapped in the
      same way, but it is only convertible if
      <em>K</em> is a class or interface that implements {@link
      java.lang.Comparable}.  Thus, a {@code SortedMap<String,int[]>}
        is convertible, but a
        {@code SortedMap<int[],String>} is not.  The conversion of a
          {@code SortedMap} instance will fail with an {@code
          IllegalArgumentException} if it has a non-null {@link
          java.util.SortedMap#comparator() comparator()}.</p>

    <p>A {@code Map<}<em>K</em>,<em>V</em>{@code >} is reconstructed as
      a {@code java.util.HashMap<}<em>K</em>,<em>V</em>{@code >};
      a {@code SortedMap<}<em>K</em>,<em>V</em>{@code >} as
      a {@code java.util.TreeMap<}<em>K</em>,<em>V</em>{@code >}.</p>

    <p>{@code TabularData} is an interface.  The concrete class that is
      used to represent a {@code Map<}<em>K</em>,<em>V</em>{@code >} as
      Open Data is {@link TabularDataSupport},
      or another class implementing {@code
      TabularData} that serializes as {@code TabularDataSupport}.</p>


    <h3 id="mxbean-map">Mappings for MXBean interfaces</h3>

    <p>An MXBean interface, or a type referenced within an MXBean
      interface, can reference another MXBean interface, <em>J</em>.
      Then <em>opentype(J)</em> is {@code SimpleType.OBJECTNAME} and
      <em>opendata(J)</em> is {@code ObjectName}.</p>

    <p>For example, suppose you have two MXBean interfaces like this:</p>

    <pre>
public interface ProductMXBean {
    public ModuleMXBean[] getModules();
}

public interface ModuleMXBean {
    public ProductMXBean getProduct();
}
    </pre>

    <p>The object implementing the {@code ModuleMXBean} interface
      returns from its {@code getProduct} method an object
      implementing the {@code ProductMXBean} interface.  The
      {@code ModuleMXBean} object and the returned {@code
      ProductMXBean} objects must both be registered as MXBeans in the
      same MBean Server.</p>

    <p>The method {@code ModuleMXBean.getProduct()} defines an
      attribute called {@code Product}.  The Open Type for this
      attribute is {@code SimpleType.OBJECTNAME}, and the corresponding
      {@code ObjectName} value will be the name under which the
      referenced {@code ProductMXBean} is registered in the MBean
      Server.</p>

    <p>If you make an MXBean proxy for a {@code ModuleMXBean} and
      call its {@code getProduct()} method, the proxy will map the
      {@code ObjectName} back into a {@code ProductMXBean} by making
      another MXBean proxy.  More formally, when a proxy made with
      {@link JMX#newMXBeanProxy(MBeanServerConnection, ObjectName,
       Class)
      JMX.newMXBeanProxy(mbeanServerConnection, objectNameX,
      interfaceX)} needs to map {@code objectNameY} back into {@code
      interfaceY}, another MXBean interface, it does so with {@code
      JMX.newMXBeanProxy(mbeanServerConnection, objectNameY,
      interfaceY)}.  The implementation may return a proxy that was
      previously created by a call to {@code JMX.newMXBeanProxy}
      with the same parameters, or it may create a new proxy.</p>

    <p>The reverse mapping is illustrated by the following change to the
      {@code ModuleMXBean} interface:</p>

    <pre>
public interface ModuleMXBean {
    public ProductMXBean getProduct();
    public void setProduct(ProductMXBean c);
}
    </pre>

    <p>The presence of the {@code setProduct} method now means that the
      {@code Product} attribute is read/write.  As before, the value
      of this attribute is an {@code ObjectName}.  When the attribute is
      set, the {@code ObjectName} must be converted into the
      {@code ProductMXBean} object that the {@code setProduct} method
      expects.  This object will be an MXBean proxy for the given
      {@code ObjectName} in the same MBean Server.</p>

    <p>If you make an MXBean proxy for a {@code ModuleMXBean} and
      call its {@code setProduct} method, the proxy will map its
      {@code ProductMXBean} argument back into an {@code ObjectName}.
      This will only work if the argument is in fact another proxy,
      for a {@code ProductMXBean} in the same {@code
      MBeanServerConnection}.  The proxy can have been returned from
      another proxy (like {@code ModuleMXBean.getProduct()} which
      returns a proxy for a {@code ProductMXBean}); or it can have
      been created by {@link
      JMX#newMXBeanProxy(MBeanServerConnection, ObjectName, Class)
      JMX.newMXBeanProxy}; or it can have been created using {@link
      java.lang.reflect.Proxy Proxy} with an invocation handler that
      is {@link MBeanServerInvocationHandler} or a subclass.</p>

    <p>If the same MXBean were registered under two different
      {@code ObjectName}s, a reference to that MXBean from another
      MXBean would be ambiguous.  Therefore, if an MXBean object is
      already registered in an MBean Server and an attempt is made to
      register it in the same MBean Server under another name, the
      result is an {@link InstanceAlreadyExistsException}.  Registering
      the same MBean object under more than one name is discouraged in
      general, notably because it does not work well for MBeans that are
      {@link NotificationBroadcaster}s.</p>

    <h3 id="composite-map">Mappings for other types</h3>

    <p>Given a Java class or interface <em>J</em> that does not match the other
      rules in the table above, the MXBean framework will attempt to map
      it to a {@link CompositeType} as follows.  The type name of this
      {@code CompositeType} is determined by the <a href="#type-names">
      type name rules</a> below.</p>

    <p>The class is examined for getters using the conventions
      <a href="#naming-conv">above</a>.  (Getters must be public
      instance methods.)  If there are no getters, or if
      any getter has a type that is not convertible, then <em>J</em> is
      not convertible.</p>

    <p>If there is at least one getter and every getter has a
      convertible type, then <em>opentype(J)</em> is a {@code
      CompositeType} with one item for every getter.  If the getter is

    <blockquote>
      <code><em>T</em> get<em>Name</em>()</code>
    </blockquote>

    then the item in the {@code CompositeType} is called {@code name}
    and has type <em>opentype(T)</em>.  For example, if the item is

    <blockquote>
      <code>String getOwner()</code>
    </blockquote>

    then the item is called {@code owner} and has Open Type {@code
    SimpleType.STRING}.  If the getter is

    <blockquote>
      <code>boolean is<em>Name</em>()</code>
    </blockquote>

    then the item in the {@code CompositeType} is called {@code name}
    and has type {@code SimpleType.BOOLEAN}.

    <p>Notice that the first character (or code point) is converted to
      lower case.  This follows the Java Beans convention, which for
      historical reasons is different from the Standard MBean
      convention.  In a Standard MBean or MXBean interface, a method
      {@code getOwner} defines an attribute called {@code Owner}, while
      in a Java Bean or mapped {@code CompositeType}, a method {@code
      getOwner} defines a property or item called {@code owner}.</p>

    <p>If two methods produce the same item name (for example, {@code
      getOwner} and {@code isOwner}, or {@code getOwner} and {@code
      getowner}) then the type is not convertible.</p>

    <p>When the Open Type is {@code CompositeType}, the corresponding
      mapped Java type (<em>opendata(J)</em>) is {@link
      CompositeData}.  The mapping from an instance of <em>J</em> to a
      {@code CompositeData} corresponding to the {@code CompositeType}
      just described is done as follows.  First, if <em>J</em>
      implements the interface {@link CompositeDataView}, then that
      interface's {@link CompositeDataView#toCompositeData
      toCompositeData} method is called to do the conversion.
      Otherwise, the {@code CompositeData} is constructed by calling
      the getter for each item and converting it to the corresponding
      Open Data type.  Thus, a getter such as</p>

    <blockquote>
      {@code List<String> getNames()}
    </blockquote>

    <p>will have been mapped to an item with name "{@code names}" and
      Open Type {@code ArrayType(1, SimpleType.STRING)}.  The conversion
      to {@code CompositeData} will call {@code getNames()} and convert
      the resultant {@code List<String>} into a {@code String[]} for the
        item "{@code names}".</p>

    <p>{@code CompositeData} is an interface.  The concrete class that is
      used to represent a type as Open Data is {@link
      CompositeDataSupport}, or another class implementing {@code
      CompositeData} that serializes as {@code
      CompositeDataSupport}.</p>


    <h4>Reconstructing an instance of Java type <em>J</em> from
      a {@code CompositeData}</h4>

    <p>If <em>opendata(J)</em> is {@code CompositeData} for a Java type
      <em>J</em>, then either an instance of <em>J</em> can be
      reconstructed from a {@code CompositeData}, or <em>J</em> is not
      reconstructible.  If any item in the {@code CompositeData} is not
      reconstructible, then <em>J</em> is not reconstructible either.</p>

    <p>For any given <em>J</em>, the following rules are consulted to
      determine how to reconstruct instances of <em>J</em> from
      {@code CompositeData}.  The first applicable rule in the list is
      the one that will be used.</p>

    <ol>

      <li><p>If <em>J</em> has a method<br>
        {@code public static }<em>J </em>{@code from(CompositeData cd)}<br>
        then that method is called to reconstruct an instance of
        <em>J</em>.</p></li>

      <li><p>Otherwise, if <em>J</em> has at least one public
        constructor with a {@link java.beans.ConstructorProperties
        ConstructorProperties} annotation, then one
        of those constructors (not necessarily always the same one)
        will be called to reconstruct an instance of <em>J</em>.
        Every such annotation must list as many strings as the
        constructor has parameters; each string must name a property
        corresponding to a getter of <em>J</em>; and the type of this
        getter must be the same as the corresponding constructor
        parameter.  It is not an error for there to be getters that
        are not mentioned in the {@code ConstructorProperties} annotation
        (these may correspond to information that is not needed to
        reconstruct the object).</p>

        <p>An instance of <em>J</em> is reconstructed by calling a
        constructor with the appropriate reconstructed items from the
        {@code CompositeData}.  The constructor to be called will be
        determined at runtime based on the items actually present in
        the {@code CompositeData}, given that this {@code
        CompositeData} might come from an earlier version of
        <em>J</em> where not all the items were present.  A
        constructor is <em>applicable</em> if all the properties named
        in its {@code ConstructorProperties} annotation are present as items
        in the {@code CompositeData}.  If no constructor is
        applicable, then the attempt to reconstruct <em>J</em> fails.</p>

        <p>For any possible combination of properties, it must be the
        case that either (a) there are no applicable constructors, or
        (b) there is exactly one applicable constructor, or (c) one of
        the applicable constructors names a proper superset of the
        properties named by each other applicable constructor.  (In
        other words, there should never be ambiguity over which
        constructor to choose.)  If this condition is not true, then
        <em>J</em> is not reconstructible.</p></li>

      <li><p>Otherwise, if <em>J</em> has a public no-arg constructor, and
        for every getter in <em>J</em> with type
        <em>T</em> and name <em>N</em> there is a corresponding setter
        with the same name and type, then an instance of <em>J</em> is
        constructed with the no-arg constructor and the setters are
        called with the reconstructed items from the {@code CompositeData}
        to restore the values.  For example, if there is a method<br>
        {@code public List<String> getNames()}<br>
          then there must also be a method<br>
          {@code public void setNames(List<String> names)}<br>
            for this rule to apply.</p>

        <p>If the {@code CompositeData} came from an earlier version of
        <em>J</em>, some items might not be present.  In this case,
        the corresponding setters will not be called.</p></li>

      <li><p>Otherwise, if <em>J</em> is an interface that has no methods
        other than getters, an instance of <em>J</em> is constructed
        using a {@link java.lang.reflect.Proxy} with a {@link
        CompositeDataInvocationHandler} backed by the {@code
        CompositeData} being converted.</p></li>

      <li><p>Otherwise, <em>J</em> is not reconstructible.</p></li>
    </ol>

    <p>Rule 2 is not applicable to subset Profiles of Java SE that do not
    include the {@code java.beans} package. When targeting a runtime that does
    not include the {@code java.beans} package, and where there is a mismatch
    between the compile-time and runtime environment whereby <em>J</em> is
    compiled with a public constructor and the {@code ConstructorProperties}
    annotation, then <em>J</em> is not reconstructible unless another rule
    applies.</p>

    <p>Here are examples showing different ways to code a type {@code
      NamedNumber} that consists of an {@code int} and a {@code
      String}.  In each case, the {@code CompositeType} looks like this:</p>

    <blockquote>
      <pre>
{@link CompositeType}(
    "NamedNumber",                      // typeName
    "NamedNumber",                      // description
    new String[] {"number", "name"},    // itemNames
    new String[] {"number", "name"},    // itemDescriptions
    new OpenType[] {SimpleType.INTEGER,
                    SimpleType.STRING}  // itemTypes
);
      </pre>
    </blockquote>

    <ol>
      <li>Static {@code from} method:

        <blockquote>
          <pre>
public class NamedNumber {
    public int getNumber() {return number;}
    public String getName() {return name;}
    private NamedNumber(int number, String name) {
        this.number = number;
        this.name = name;
    }
    <b>public static NamedNumber from(CompositeData cd)</b> {
        return new NamedNumber((Integer) cd.get("number"),
                               (String) cd.get("name"));
    }
    private final int number;
    private final String name;
}
          </pre>
        </blockquote>
      </li>

      <li>Public constructor with <code>&#64;ConstructorProperties</code> annotation:

        <blockquote>
          <pre>
public class NamedNumber {
    public int getNumber() {return number;}
    public String getName() {return name;}
    <b>&#64;ConstructorProperties({"number", "name"})
    public NamedNumber(int number, String name)</b> {
        this.number = number;
        this.name = name;
    }
    private final int number;
    private final String name;
}
          </pre>
        </blockquote>
      </li>

      <li>Setter for every getter:

        <blockquote>
          <pre>
public class NamedNumber {
    public int getNumber() {return number;}
    public void <b>setNumber</b>(int number) {this.number = number;}
    public String getName() {return name;}
    public void <b>setName</b>(String name) {this.name = name;}
    <b>public NamedNumber()</b> {}
    private int number;
    private String name;
}
          </pre>
        </blockquote>
      </li>

      <li>Interface with only getters:

        <blockquote>
          <pre>
public interface NamedNumber {
    public int getNumber();
    public String getName();
}
          </pre>
        </blockquote>
      </li>
    </ol>

    <p>It is usually better for classes that simply represent a
      collection of data to be <em>immutable</em>.  An instance of an
      immutable class cannot be changed after it has been constructed.
      Notice that {@code CompositeData} itself is immutable.
      Immutability has many advantages, notably with regard to
      thread-safety and security.  So the approach using setters should
      generally be avoided if possible.</p>


    <h3>Recursive types</h3>

    <p>Recursive (self-referential) types cannot be used in MXBean
      interfaces.  This is a consequence of the immutability of {@link
      CompositeType}.  For example, the following type could not be the
      type of an attribute, because it refers to itself:</p>

    <pre>
public interface <b>Node</b> {
    public String getName();
    public int getPriority();
    public <b>Node</b> getNext();
}
</pre>

    <p>It is always possible to rewrite recursive types like this so
      they are no longer recursive.  Doing so may require introducing
      new types.  For example:</p>

    <pre>
public interface <b>NodeList</b> {
    public List&lt;Node&gt; getNodes();
}

public interface Node {
    public String getName();
    public int getPriority();
}
</pre>

    <h3>MBeanInfo contents for an MXBean</h3>

    <p>An MXBean is a type of Open MBean.  However, for compatibility
      reasons, its {@link MBeanInfo} is not an {@link OpenMBeanInfo}.
      In particular, when the type of an attribute, parameter, or
      operation return value is a primitive type such as {@code int},
      or is {@code void} (for a return type), then the attribute,
      parameter, or operation will be represented respectively by an
      {@link MBeanAttributeInfo}, {@link MBeanParameterInfo}, or
      {@link MBeanOperationInfo} whose {@code getType()} or {@code
      getReturnType()} returns the primitive name ("{@code int}" etc).
      This is so even though the mapping rules above specify that the
      <em>opendata</em> mapping is the wrapped type ({@code Integer}
      etc).</p>

    <p>The array of public constructors returned by {@link
      MBeanInfo#getConstructors()} for an MXBean that is directly
      registered in the MBean Server will contain all of the public
      constructors of that MXBean.  If the class of the MXBean is not
      public then its constructors are not considered public either.
      The list returned for an MXBean that is constructed using the
      {@link StandardMBean} class is derived in the same way as for
      Standard MBeans.  Regardless of how the MXBean was constructed,
      its constructor parameters are not subject to MXBean mapping
      rules and do not have a corresponding {@code OpenType}.</p>

    <p>The array of notification types returned by {@link
      MBeanInfo#getNotifications()} for an MXBean that is directly
      registered in the MBean Server will be empty if the MXBean does
      not implement the {@link NotificationBroadcaster} interface.
      Otherwise, it will be the result of calling {@link
      NotificationBroadcaster#getNotificationInfo()} at the time the MXBean
      was registered.  Even if the result of this method changes
      subsequently, the result of {@code MBeanInfo.getNotifications()}
      will not.  The list returned for an MXBean that is constructed
      using the {@link StandardMBean} or {@link StandardEmitterMBean}
      class is derived in the same way as for Standard MBeans.</p>

    <p>The {@link Descriptor} for all of the
      {@code MBeanAttributeInfo}, {@code MBeanParameterInfo}, and
      {@code MBeanOperationInfo} objects contained in the {@code MBeanInfo}
      will have a field {@code openType} whose value is the {@link OpenType}
      specified by the mapping rules above.  So even when {@code getType()}
      is "{@code int}", {@code getDescriptor().getField("openType")} will
      be {@link SimpleType#INTEGER}.</p>

    <p>The {@code Descriptor} for each of these objects will also have a
      field {@code originalType} that is a string representing the Java type
      that appeared in the MXBean interface.  The format of this string
      is described in the section <a href="#type-names">Type Names</a>
      below.</p>

    <p>The {@code Descriptor} for the {@code MBeanInfo} will have a field
      {@code mxbean} whose value is the string "{@code true}".</p>


    <h3 id="type-names">Type Names</h3>

    <p>Sometimes the unmapped type <em>T</em> of a method parameter or
    return value in an MXBean must be represented as a string.  If
    <em>T</em> is a non-generic type, this string is the value
    returned by {@link Class#getName()}.  Otherwise it is the value of
    <em>genericstring(T)</em>, defined as follows:

    <ul>

      <li>If <em>T</em> is a non-generic non-array type,
      <em>genericstring(T)</em> is the value returned by {@link
      Class#getName()}, for example {@code "int"} or {@code
      "java.lang.String"}.

      <li>If <em>T</em> is an array <em>E[]</em>,
      <em>genericstring(T)</em> is <em>genericstring(E)</em> followed
      by {@code "[]"}.  For example, <em>genericstring({@code int[]})</em>
      is {@code "int[]"}, and <em>genericstring({@code
      List<String>[][]})</em> is {@code
      "java.util.List<java.lang.String>[][]"}.

    <li>Otherwise, <em>T</em> is a parameterized type such as {@code
    List<String>} and <em>genericstring(T)</em> consists of the
    following: the fully-qualified name of the parameterized type as
    returned by {@code Class.getName()}; a left angle bracket ({@code
    "<"}); <em>genericstring(A)</em> where <em>A</em> is the first
    type parameter; if there is a second type parameter <em>B</em>
    then {@code ", "} (a comma and a single space) followed by
    <em>genericstring(B)</em>; a right angle bracket ({@code ">"}).

    </ul>

    <p>Note that if a method returns {@code int[]}, this will be
      represented by the string {@code "[I"} returned by {@code
      Class.getName()}, but if a method returns {@code List<int[]>},
      this will be represented by the string {@code
      "java.util.List<int[]>"}.

    <h3>Exceptions</h3>

    <p>A problem with mapping <em>from</em> Java types <em>to</em>
      Open types is signaled with an {@link OpenDataException}.  This
      can happen when an MXBean interface is being analyzed, for
      example if it references a type like {@link java.util.Random
      java.util.Random} that has no getters.  Or it can happen when an
      instance is being converted (a return value from a method in an
      MXBean or a parameter to a method in an MXBean proxy), for
      example when converting from {@code SortedSet<String>} to {@code
      String[]} if the {@code SortedSet} has a non-null {@code
      Comparator}.</p>

    <p>A problem with mapping <em>to</em> Java types <em>from</em>
      Open types is signaled with an {@link InvalidObjectException}.
      This can happen when an MXBean interface is being analyzed, for
      example if it references a type that is not
      <em>reconstructible</em> according to the rules above, in a
      context where a reconstructible type is required.  Or it can
      happen when an instance is being converted (a parameter to a
      method in an MXBean or a return value from a method in an MXBean
      proxy), for example from a String to an Enum if there is no Enum
      constant with that name.</p>

    <p>Depending on the context, the {@code OpenDataException} or
      {@code InvalidObjectException} may be wrapped in another
      exception such as {@link RuntimeMBeanException} or {@link
      UndeclaredThrowableException}.  For every thrown exception,
      the condition <em>C</em> will be true: "<em>e</em> is {@code
      OpenDataException} or {@code InvalidObjectException} (as
      appropriate), or <em>C</em> is true of <em>e</em>.{@link
      Throwable#getCause() getCause()}".</p>

/* <p>
/*  <p>将接口标记为MXBean接口或不是MXBean接口的注释默认情况下,接口是一个MXBean接口,如果它是public的,其名称以{@code MXBean}结尾,如{@代码SomethingMXBean}
/* 以下接口是MXBean接口：</p>。
/* 
/* <pre>
/*  public interface WhatsitMXBean {}
/* 
/*  @MXBean public interface Whatsit1Interface {}
/* 
/*  @MXBean(true)public interface Whatsit2Interface {}
/* </pre>
/* 
/*  <p>以下接口不是MXBean接口：</p>
/* 
/* <pre>
/*  interface NonPublicInterfaceNotMXBean {}
/* 
/*  public interface Whatsit3Interface {}
/* 
/*  @MXBean(false)public interface MisleadingMXBean {}
/* </pre>
/* 
/* <h3 id ="MXBean-spec"> MXBean规范</h3>
/* 
/*  <p> MXBean概念提供了一种简单的方法来编写只引用一组预定义类型的MBean,由{@link javaxmanagementopenmbean}定义的类型。
/* 通过这种方式,您可以确保任何客户端都可以使用MBean,包括远程客户端,而不需要客户端可以访问代表您的MBean类型的<em>特定于模型的类</em> </p>。
/* 
/*  <p>与标准MBean概念相比,这些概念更容易理解。下面是一个被管理对象如何被表示为一个标准MBean和一个MXBean：</p>
/* 
/* <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
/* <tr>
/*  <th>标准MBean </th> <th> MXBean </th>
/* </tr>
/* <tr>
/* <td> <pre> public interface MemoryPool <b> MBean </b> {String getName(); MemoryUsage getUsage(); //} 
/* </pre> </td> <td> <pre> public interface MemoryPool <b> MXBean </b> {String getName MemoryUsage getUsage(); //}
/*  </pre> </td>。
/* </tr>
/* </table>
/* 
/*  <p>正如你所看到的,定义是非常相似的唯一的区别是,命名接口的约定是使用<code> <em> Something </em> MXBean </code>的MXBeans, > <em> </em>
/*  MBean </code>的标记MBean </p>。
/* 
/* <p>在这个托管对象中,有一个类型为{@link MemoryUsage}的属性<code> Usage </code>。这样的属性的一点是它给出了一组数据项的一致性快照。
/* 例如,它可能包括内存池中使用的内存的当前量和内存池的当前最大值如果这些是单独的项,通过单独的{@link MBeanServer#getAttribute getAttribute}调用获得,则我们可以
/* 获得在不同的不一致的时间我们可能会得到大于<code> max </code>值的<code> used </code>值</p>。
/* <p>在这个托管对象中,有一个类型为{@link MemoryUsage}的属性<code> Usage </code>。这样的属性的一点是它给出了一组数据项的一致性快照。
/* 
/*  <p>因此,我们可以这样定义<code> MemoryUsage </code>：</p>
/* 
/* <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
/* <tr>
/*  <th>标准MBean </th> <th> MXBean </th>
/* </tr>
/* <tr>
/* <td> <pre> public class MemoryUsage <b> implements Serializable </b> {// getter的标准JavaBean约定
/* 
/*  public MemoryUsage(long init,long used,long committed,long max){} long getUsed(){} long getCommitted
/* (){} long getMax(){}} </pre> <td> <pre> public class MemoryUsage {//使用getter <b> @ConstructorProperties({"init","used","committed","max"}
/* )的标准JavaBean约定public MemoryUsage ,long used,long committed,long max){} long getUsed(){} long getCommi
/* tted(){} long getMax(){}} </pre>。
/* </tr>
/* </table>
/* 
/* <p>在两种情况下的定义是一样的,除了使用MXBean,<code> MemoryUsage </code>不再需要标记<code> Serializable </code>(虽然可以)我们添加了一个{@code @ConstructorProperties}
/* 注释来将构造函数参数链接到相应的getter我们将在下面更多地了解这个</p>。
/* 
/* <p> <code> MemoryUsage </code>是特定于模型的类</em>对于标准MBeans,MBean Server的客户端无法访问<code> Usage </code>知道类<code>
/*  MemoryUsage </code>假设客户端是基于JMX技术的通用控制台然后控制台必须配置可能连接到的每个应用程序的特定于类的类。
/* 问题对于客户端更糟糕那些没有写在Java语言中然后可能没有任何方法告诉客户端<code> MemoryUsage </code>看起来像</p>。
/* 
/* <p>这是MXBeans与标准MBeans不同的地方尽管我们以几乎完全相同的方式定义管理界面,但MXBean框架<em>将</em>特定于模型的类转换为来自Java平台的标准类使用数组和{@link javaxmanagementopenmbean}
/* 包中的{@link javaxmanagementopenmbeanCompositeData CompositeData}和{@link javaxmanagementopenmbeanTabularData TabularData}
/* 类,可以使用标准类构建任意复杂度的数据结构。
/* </p>。
/* 
/*  <p>如果我们比较两个模型的客户端可能是什么样子,这变得更清楚：</p>
/* 
/* <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
/* <tr>
/*  <th>标准MBean </th> <th> MXBean </th>
/* </tr>
/* <tr>
/* <td> <pre> String name =(String)mbeanServer {@link MBeanServer#getAttribute getAttribute}(objectName,
/* "Name"); <b> MemoryUsage </b> usage =(<b> MemoryUsage </b>)mbeanServergetAttribute(objectName,"Usage"
/* ); <b> long used = usagegetUsed(); </b> </pre> </td> <td> <pre> String name =(String)mbeanServer {@link MBeanServer#getAttribute getAttribute}
/* (objectName,"Name") ; <b> {@ link CompositeData} </b> usage =(<b> CompositeData </b>)mbeanServergetAt
/* tribute(objectName,"Usage"); <b> long used =(Long)usage {@link CompositeData#get get}("used"); </b> </pre>
/*  </td>。
/* </table>
/* 
/* <p>对于像<code> String </code>这样简单类型的属性,代码是相同的。
/* 对于具有复杂类型的属性,标准MBean代码要求客户端知道特定于模型的类<code> MemoryUsage <代码>,而MXBean代码不需要非标准类</p>。
/* 
/*  <p>这里显示的客户端代码对于MXBean客户端稍微复杂一点。
/* 但是,如果客户端确实知道模型,那么这里接口<code> MemoryPoolMXBean </code>和类<code> MemoryUsage </code> ,那么它可以构造一个<em>代理</em>
/* 。
/*  <p>这里显示的客户端代码对于MXBean客户端稍微复杂一点。无论您是使用标准MBean还是MXBean,这是在事先知道模型时与被管对象交互的推荐方法：</p>。
/* 
/* <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
/* <tr>
/*  <th>标准MBean </th> <th> MXBean </th>
/* </tr>
/* <tr>
/* <td> <pre> MemoryPool <b> MBean </b> proxy = JMX <b> {@ link JMX#newMBeanProxy(MBeanServerConnection,ObjectName,Class)newMBeanProxy}
/*  </b>(mbeanServer,objectName,MemoryPool <b> MBean </b> class); String name = proxygetName(); MemoryUs
/* age usage = proxygetUsage(); long used = usagegetUsed(); </pre> </td> <td> <pre> MemoryPool <b> MXBea
/* n </b> proxy = JMX <b> {@ link JMX#newMXBeanProxy(MBeanServerConnection,ObjectName,Class)newMXBeanProxy}
/*  </b> ,objectName,MemoryPool <b> MXBean </b> class); String name = proxygetName(); MemoryUsage usage 
/* = proxygetUsage(); long used = usagegetUsed(); </pre> </td>。
/* </tr>
/* </table>
/* 
/*  <p>实现MemoryPool对象对于标准MBean和MXBeans工作方式类似。</p>
/* 
/* <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
/* <tr>
/*  <th>标准MBean </th> <th> MXBean </th>
/* </tr>
/* <tr>
/* <td> <pre> public class MemoryPool implements MemoryPool <b> MBean </b> {public String getName(){} pu
/* blic MemoryUsage getUsage(){} //} </pre> </td> <td> <pre > public class MemoryPool implements MemoryP
/* ool <b> MXBean </b> {public String getName(){} public MemoryUsage getUsage(){} //} </pre>。
/* </tr>
/* </table>
/* 
/*  <p>在这两种情况下,在MBean Server中注册MBean的工作方式相同：</p>
/* 
/* <table border="1" cellpadding="5" summary="Standard Bean vs. MXBean">
/* <tr>
/*  <th>标准MBean </th> <th> MXBean </th>
/* </tr>
/* <tr>
/*  <td> <pre> {MemoryPool <b> MBean </b> pool = new MemoryPool(); mbeanServer {@link MBeanServer#registerMBean registerMBean}
/* (pool,objectName); } </pre> </td> <td> <pre> {MemoryPool <b> MXBean </b> pool = new MemoryPool mbeanServer {@link MBeanServer#registerMBean registerMBean}
/* (pool,objectName); } </pre> </td>。
/* </tr>
/* </table>
/* 
/*  <h2 id ="mxbean-def"> MXBean的定义</h2>
/* 
/* <p> MXBean是一种MBean MXBean对象可以直接在MBean Server中注册,或者可以用作{@link StandardMBean}的参数以及在MBean Server中注册的结果MB
/* ean </p>。
/* 
/*  <p>当使用{@link MBeanServer}接口的{@code registerMBean}或{@code createMBean}方法在MBean服务器中注册对象时,将检查对象的类以确定它是什
/* 么类型的MBean： / p>。
/* 
/* <ul>
/*  <li>如果类实现了接口{@link DynamicMBean},那么MBean是一个动态MBean请注意,{@code StandardMBean}类实现了这个接口,因此这种情况适用于使用类{@interface)创建的标准MBean或MXBean, code StandardMBean}
/*  </li>。
/* 
/* <li>否则,如果类匹配标准MBean命名约定,则MBean是标准MBean </li>
/* 
/*  <li>否则,它可能是一个MXBean由对象实现的接口集将检查以下接口：
/* 
/* <ul>
/*  <li>有一个类名<code> <em> S </em> MXBean </code>,其中<code> <em> S </em> </code>是任何非空字符串,注释{@code @MXBean(false)}
/* ;和/或</li> <li>拥有注释{@code @MXBean(true)}或{@code @MXBean} </li>。
/* </ul>
/* 
/*  如果只有一个这样的接口,或者如果有一个这样的接口是所有其他接口的子接口,则对象是MXBean。
/* 所讨论的接口是<em> MXBean接口</em>在上面的例子中, MXBean接口是{@code MemoryPoolMXBean}。
/* 
/* <li>如果没有满足这些条件,则MBean无效,并且尝试注册它将生成{@link NotCompliantMBeanException}
/* </ul>
/* 
/*  <p>在MXBean介面中显示为方法的参数或返回类型的每种Java类型都必须使用以下规则<em> convertible </em>。此外,参数必须<em>可重新定义</em> </p>
/* 
/*  <p>尝试构建不符合上述规则的MXBean将产生异常</p>
/* 
/*  <h2 id ="naming-conv">命名约定</h2>
/* 
/*  <p>与标准MBean中的方法相同的命名约定适用于MXBean中的方法：</p>
/* 
/* <ol>
/* <li> <code> <em> </em> </em>(</code>)</code> Java类型(非<code> void </code>)和<code> <em> N </em> </code>
/* 是非空字符串,指定有一个可读属性<code> <em> N </code> </code>属性的Java类型和Open类型由下面的映射规则确定当查找getter时,忽略从{@code Object}继承
/* 的{@code final Class getClass() / li>。
/* 
/*  <li>方法<code> boolean is </em>()</code>指定有一个可读的属性<code> <em> N </em> </code> <code> boolean </code>和打
/* 开类型<code> SimpleTypeBoolean </code> </li>。
/* 
/* <li> <code> void set <em> N </em>(<em> T </em> x)</code>方法指定有一个可写属性<code> <em> N < em> </code>属性的Java
/* 类型和Open类型由下面的映射规则确定(当然,参数的<code> x </code>名称是不相关的)</li>。
/* 
/*  <li>每个其他方法都指定存在与方法名称相同的操作。返回值和每个参数的Java类型和Open类型由以下映射规则确定</li>
/* </ol>
/* 
/*  <p> <code> get <em> N </em> </code>和<code>的规则是<em> N </em> </code>共同定义<em> getter < / em> <code> set
/*  <em> N </em> </code>的规则定义了<em> setter </p>。
/* 
/* <p>有两个具有相同名称的getter或两个具有相同名称的setter是一个错误如果有相同名称的getter和setter,那么类型<code> <em> T < em> </code>必须是相同的在这
/* 种情况下属性是读/写如果只有一个getter或只有一个setter,属性是只读或只写分别</p>。
/* 
/*  <h2 id ="mapping-rules">类型映射规则</h2>
/* 
/* <p> MXBean是一种由{@link javaxmanagementopenmbean}包定义的Open MBean。
/* 这意味着,必须使用<em>打开类型</em>来描述属性类型,操作参数和操作返回值>,这是{@link javaxmanagementopenmbeanOpenType}的四个标准子类。
/* MXBeans通过将Java类型映射到Open Types中实现这一点。</p>。
/* 
/*  <p>对于每个Java类型<em> J </em>,MXBean映射由以下信息描述：</p>
/* 
/* <ul>
/* <li>相应的Open Type,<em> opentype(J)</em>这是{@link javaxmanagementopenmbeanOpenType}的子类的实例</li> <li> </em>
/* 这是一个Java类</li> </em> </em> opendata(J)</em> </em>键入<em> </em>以键入<em> </em> </em> em> J </em>,如果可以</li>
/* 。
/* </ul>
/* 
/*  <p>例如,对于Java类型{@code List <String>}：</p>
/* 
/* <ul>
/* <li>开放类型<em> </em> {@ code List <String>} <em>)是{@link ArrayType} <code>(1,</code>代表<code> String </code>
/* 的一维数组</li> <li>映射的Java类型,<em> opendata </code> em> {@ code List <String>} <em>)</em>是{@code String []}
/*  </li> <li>可以将{@code List <String>}转换为{@代码String []}使用{@link List#toArray(Object [])ListtoArray(new String [0])}
/*  </li> <li>可以将{@code String []}转换为{@code List <String>}使用{@link Arrays#asList ArraysasList} </li>。
/* </ul>
/* 
/*  <p>如果没有映射规则用于从<em> J </em>导出<em> opentype(J)</em>,则<em> J </em>不能是方法参数的类型或返回MXBean接口中的值</p>
/* 
/* <p id ="reconstructible-def">如果有一种方法可以将<em> opendata(J)</em>转换回<em> J </em>,那么我们说<em>是可重建的</em> MXBea
/* n接口中的所有方法参数必须是可重构的,因为当MXBean框架调用一个方法时,它需要将这些参数从<em> opendata(J)</em> J </em>在由{@link JMX#newMXBeanProxy(MBeanServerConnection,ObjectName,Class)JMXnewMXBeanProxy}
/* 生成的代理中,它是MXBean接口中必须可重构的方法的返回值</p>。
/* 
/* <p>对于所有Java类型和打开类型,允许使用空值,除非它们不可能的原始Java类型从类型<em> J </em>转换为类型<em> opendata(J)</em>从<em> opendata(J)</em>
/* 类型键入<em> J </em>,空值映射到空值</p>。
/* 
/*  <p>下表总结了类型映射规则</p>
/* 
/* <table border="1" cellpadding="5" summary="Type Mapping Rules">
/* <tr>
/*  <th> </em> </em> </em> </em> opendata(J)</em> / th>
/* </tr>
/* <tbody valign="top">
/* <tr>
/*  <td> {@ code int},{@code boolean}等<br>(8种基本Java类型)</td> <td> {@ code SimpleTypeINTEGER},<br> {@code SimpleTypeBOOLEAN}
/*  / td> <td> {@ code Integer},{@code Boolean}等<br>(相应的框类型)</td>。
/* </tr>
/* <tr>
/* <td> {@ code Integer},{@code ObjectName}等<br>({@link SimpleType}涵盖的类型)</td> <td>相应的{@code SimpleType}
/*  </td> <td > <em> J </em>,相同类型</td>。
/* </tr>
/* <tr>
/*  <td> {@ code int []}等<br>(具有<br>基本元素类型的一维数组)</td> <td> {@ code ArrayTypegetPrimitiveArrayType(int [] class)}
/*  etc </td > <td> <em> J </em>,相同类型</td>。
/* <tr>
/*  <td> <em> E </em> {@ code []} <br>(非基本元素类型为<em> E的数组</em>;这包括{@code int [] []}其中<em> </em>是{@code int []}
/* )</td> <td> {@ code ArrayTypegetArrayType(} <em> opentype(E) > <td> <em> opendata(E)</em> {@ code []}
/*  </td>。
/* </tr>
/* <tr>
/* <TD> << EM>和</em>的{@代码{@名单码}>} <br {@code设置<} <em>和</em>的{@码>} <BR {@code SortedSet的<} <EM>和</em>的{@码>}
/* (见下文)</TD> <TD>相同的<em>和</em>的{@代码[]} </TD> <TD >相同的<em>和</em>的{@代码[]} </TD>。
/* </tr>
/* <tr>
/*  <TD>枚举的<em>和</em>的大全(在Java枚举{} @code声明的<em>和</em>的{@code {}})</TD> <TD> {@ SimpleTypeSTRING代码} </TD>
/*  <TD> {}字符串代码@ </TD>。
/* </tr>
/* <tr>
/*  <TD> << EM> K </em>的<EM> V </em>的{@代码{@地图代码}>} <br {@code的SortedMap <} <em>氏"/ EM>,< EM> V </em>的{@码>}
/*  </TD> <TD> {@链接} TabularType结果(见下文)</TD> <TD> {@链接} TabularData大全(见下文) </TD>。
/* </tr>
/* <tr>
/*  <TD> MXBean接口</TD> <TD> {@代码SimpleTypeOBJECTNAME} <br (see below) </td> <TD> {@链接}的ObjectName结果(见下文)
/* </TD>。
/* </tr>
/* <tr>
/* <td>任何其他类型</td> <td> {@ link CompositeType}(如果可能)<br>(见下文)</td> <td> {@ link CompositeData} </td>
/* </tbody>
/* </table>
/* 
/*  <p>以下部分提供这些规则的进一步详情</p>
/* 
/*  <h3>原始类型的映射</h3>
/* 
/*  <p> 8种原始Java类型({@code boolean},{@code byte},{@code short},{@code int},{@code long},{@code float},{@code double}
/*  ,{@code char})被映射到来自{@code javalang},即{@code Boolean},{@code Byte}等的相应的盒装类型。
/* 打开类型是相应的{@code SimpleType}因此, > opentype(</em> {@ code long} <em>)</em>是{@code SimpleTypeLONG}和<em> o
/* pendata(</em> {@ code long}是{@code javalangLong} </p>。
/* 
/* <p> {@code long []}等原始类型数组可以直接表示为开放类型因此,openType(</em> {@ code long []} <em> >是{@code ArrayTypegetPrimitiveArrayType(long [] class)}
/* 和<em> opendata(</em> {@ code long []} <em>)</em>是{@code long []} >。
/* 
/*  <p>在实践中,一个简单的{@code int}和{@code Integer}等之间的区别不会出现,因为JMX API中的操作总是对Java对象,而不是基元。但是, </em>显示数组</p>
/* 
/*  <h3>集合的映射({@code List <} <em> E </em> {@ code>}等)</h3>
/* 
/* <p> {@code List <} <em> E </em> {@ code>}或{@code Set <} <em> E </em> {@ code>},例如{@code List <String>}
/* 或{@code Set <ObjectName>}以与相同元素类型的数组(例如{@code String []}或{@code ObjectName []} </p>。
/* 
/*  <p> A {@code SortedSet <} <em> E </em> {@ code>}也以与<em> E </em> {@ code []}相同的方式映射,只有可转换,如果<em> E </em>
/* 是实现{@link javalangComparable}的类或接口因此,{@code SortedSet <String>}或{@code SortedSet <Integer>}是可转换的,代码So
/* rtedSet <int []>}或{@code SortedSet <List <String >>}不是{@code SortedSet}实例的转换将失败,如果它有一个非空的{@code IllegalArgumentException}
/*  link javautilSortedSet#comparator()comparator()} </p>。
/* 
/* <p> {@code List <} <em> E </em> {@ code>}已重建为{@code javautilArrayList <} <em> E </em> {@ code>};作为{@code javautilHashSet <} <em>
/*  E </em> {@ code>}的{@code Set <} <em> E </em> {@ code>};作为{@code javautilTreeSet <} <em> E </em> {@ code>}
/*  </p>的{@code SortedSet <} <em> E </em> {@ code>}。
/* 
/*  <h3>映射映射({@code Map <} <em> K </em>,<em> </em> {@ code}}等)</h3>
/* 
/* <p>一个{@code地图<} <em> K </em>,<em> V </em> {@ code>}或{@code SortedMap <} <em> K </em> > V </em> {@ code>}
/* ,例如{@code Map <String,ObjectName>},具有打开类型{@link TabularType},并映射到{@link TabularData} {@code TabularType}
/* 称为{@code key}和{@code value}的两个项{@code key}的打开类型是<op> opentype(K)</em>,{@code value}的打开类型是<em> opentyp
/* e(V)</em> {@code TabularType}的索引是单个项目{@code key} </p>。
/* 
/*  <p>例如,{@code Map <String,ObjectName>}的{@code TabularType}可能用如下代码构建：</p>
/* 
/* <pre>
/* String typeName ="javautilMap&lt; javalangString,javaxmanagementObjectName&gt;"; String [] keyValue =
/*  new String [] {"key","value"}; OpenType [] openTypes = new OpenType [] {SimpleTypeSTRING,SimpleTypeOBJECTNAME}
/* ; CompositeType rowType = new CompositeType(typeName,typeName,keyValue,keyValue,openTypes); TabularTy
/* 
   @since 1.6
*/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MXBean {
    /**
       True if the annotated interface is an MXBean interface.
    /* <p>
    /* pe tabularType = new TabularType(typeName,typeName,rowType,new String [] {"key"});。
    /* </pre>
    /* 
    /*  <p>这里的{@code typeName}由下面详述的<a href=\"#type-names\">类型名称规则</a>决定
    /* 
    /* <p> A {@code SortedMap <} <em> K </em>,<em> V </em> {@ code>}的映射方式相同,但只有<em> / em>是一个实现{@link javalangComparable}
    /* 的类或接口因此,{@code SortedMap <String,int []>}是可转换的,但是{@code SortedMap <int [],String>如果{@code IllegalArgumentException}
    /* 有非空的{@link javautilSortedMap#comparator()比较器()},则{@code SortedMap}实例的转换将失败。
    /* </p>。
    /* 
    /*  <p> {@code Map <} <em> K </em>,<em> </em> {@ code>}已重建为{@code javautilHashMap <} <em> ,<em> V </em> 
    /* {@ code>};作为{@code javautilTreeMap <} <em> K </em>,<em> V </em>,{@code SortedMap <} <em> </em> {@ code>}
    /*  </p>。
    /* 
    /* <p> {@ code TabularData}是一个接口。
    /* 用于表示{@code Map <} <em> K </em>,<em> V </em> {@ code>}数据为{@link TabularDataSupport}或另一个实现{@code TabularData}
    /* 的类,其序列化为{@code TabularDataSupport} </p>。
    /* <p> {@ code TabularData}是一个接口。
    /* 
    /*  <h3 id ="mxbean-map"> MXBean接口的映射</h3>
    /* 
    /*  <p> MXBean接口或在MXBean接口中引用的类型可以引用另一个MXBean接口,<em> </em>然后<em> opentype(J)</em>是{@code SimpleTypeOBJECTNAME}
    /*  em> opendata(J)</em>是{@code ObjectName} </p>。
    /* 
    /*  <p>例如,假设您有两个MXBean接口,如下所示：</p>
    /* 
    /* <pre>
    /*  public interface ProductMXBean {public ModuleMXBean [] getModules(); }}
    /* 
    /*  public interface ModuleMXBean {public ProductMXBean getProduct(); }}
    /* </pre>
    /* 
    /* <p>实现{@code ModuleMXBean}接口的对象从其{@code getProduct}方法返回实现{@code ProductMXBean}接口的对象。
    /* {@code ModuleMXBean}对象和返回的{@code ProductMXBean}对象必须在同一MBean Server中注册为MXBeans </p>。
    /* 
    /*  <p>方法{@code ModuleMXBeangetProduct()}定义了一个名为{@code Product}的属性。
    /* 此属性的开放类型是{@code SimpleTypeOBJECTNAME},对应的{@code ObjectName}值将是引用的{@code ProductMXBean}已在MBean Server 
    /* </p>中注册。
    /*  <p>方法{@code ModuleMXBeangetProduct()}定义了一个名为{@code Product}的属性。
    /* 
    /* <p>如果您为{@code ModuleMXBean}制作MXBean代理并调用其{@code getProduct()}方法,代理将通过创建另一个MXBean将{@code ObjectName}映射
    /* 回{@code ProductMXBean}代理更正式地,当使用{@link JMX#newMXBeanProxy(MBeanServerConnection,ObjectName,Class)JMXnewMXBeanProxy(mbeanServerConnection,objectNameX,interfaceX)}
    /* 做出的代理需要将{@code objectNameY}映射回{@code interfaceY}时,另一个MXBean接口,它使用{@code JMXnewMXBeanProxy(mbeanServerConnection,objectNameY,interfaceY)}
    /* 这样做实现可以返回一个代理,该代理以前是通过调用具有相同参数的{@code JMXnewMXBeanProxy}创建的,或者它可以创建一个新代理< p>。
    /* 
    /* <p>反向映射通过对{@code ModuleMXBean}接口的以下更改来说明：</p>
    /* 
    /* <pre>
    /*  public interface ModuleMXBean {public ProductMXBean getProduct(); public void setProduct(ProductMXBean c); }
    /* }。
    /* </pre>
    /* 
    /*  <p> {@code setProduct}方法的存在意味着{@code Product}属性被读/写与之前一样,此属性的值是一个{@code ObjectName}当属性设置时,{@ @code ObjectName}
    /* 必须转换为{@code setProduct}方法期望的{@code ProductMXBean}对象此对象将是同一MBean Server中的给定{@code ObjectName}的MXBean代理
    /* </p>。
    /* 
    /* <p>如果您为{@code ModuleMXBean}制作MXBean代理并调用其{@code setProduct}方法,则代理会将其{@code ProductMXBean}参数映射回{@code ObjectName}
    /* 。
    /* 这只会在以下情况下有效：该参数实际上是另一个代理,对于{@code MBeanServerConnection}中的{@code ProductMXBean}代理可以从另一个代理(如{@code ModuleMXBeangetProduct()}
    /* 返回一个代理{@code ProductMXBean});或者它可以由{@link JMX#newMXBeanProxy(MBeanServerConnection,ObjectName,Class)JMXnewMXBeanProxy}
    /* 创建;或者可以使用{@link javalangreflectProxy Proxy}创建一个调用处理程序{@link MBeanServerInvocationHandler}或其子类</p>。
    /* 
    /* <p>如果相同的MXBean在两个不同的{@code ObjectName}下注册,则来自另一个MXBean的对该MXBean的引用将是不明确的因此,如果MXBean对象已经在MBean Server中
    /* 注册并尝试注册它在同一MBean服务器下的另一个名称,结果是一个{@link InstanceAlreadyExistsException}注册同一个MBean对象下的一个以上的名称一般不鼓励,特别是因
    /* 为它不能很好地为{@link NotificationBroadcaster} s </p>。
    /* 
    /*  <h3 id ="composite-map">其他类型的映射</h3>
    /* 
    /* <p>给定一个与上表中的其他规则不匹配的Java类或接口<em> J </em>,MXBean框架将尝试将其映射到{@link CompositeType},如下所示：此{@code CompositeType}
    /* 由下面的<a href=\"#type-names\">类型名称规则</a>确定</p>。
    /* 
    /*  <p>使用约定<a href=\"#naming-conv\">以上</a>(Getters必须是公共实例方法)检查​​类是否有getter如果没有getter,或者任何getter有一个类型不可兑换
    /* ,则<em> J </em>不可兑换</p>。
    /* 
    /*  <p>如果至少有一个getter且每个getter都有可转换类型,则<em> opentype(J)</em>是一个{@code CompositeType},每个getter有一个项目如果gette
    /* r是。
    /* 
    /* <blockquote>
    /*  <code> <em> </em>取得<em> </em>(</em>)</code>
    /* </blockquote>
    /* 
    /* 那么{@code CompositeType}中的项目将调用{@code name}并具有类型<em> opentype(T)</em>。例如,如果项目
    /* 
    /* <blockquote>
    /*  <code> String getOwner()</code>
    /* </blockquote>
    /* 
    /*  那么该项目被称为{@code owner}并具有打开类型{@code SimpleTypeSTRING}如果getter是
    /* 
    /* <blockquote>
    /*  <code> boolean是</em>(</em>)</code>
    /* </blockquote>
    /* 
    /*  那么{@code CompositeType}中的项目称为{@code name},并且类型为{@code SimpleTypeBOOLEAN}
    /* 
    /* <p>请注意,第一个字符(或代码点)转换为小写符合以下Java Bean规则,由于历史原因,它不同于标准MBean约定在标准MBean或MXBean接口中,方法{@code getOwner }定义一个
    /* 称为{@code Owner}的属性,而在Java Bean或映射的{@code CompositeType}中,{@code getOwner}方法定义一个名为{@code owner}的属性或项目</p>
    /* 。
    /* 
    /*  <p>如果两个方法产生相同的项名称(例如{@code getOwner}和{@code isOwner}或{@code getOwner}和{@code getowner}),则类型不可转换</p>。
    /* 
    /* <p>当打开类型是{@code CompositeType}时,对应的映射Java类型(<em> opendata(J)</em>)是{@link CompositeData} em>到与刚刚描述的{@code CompositeType}
    /* 相对应的{@code CompositeData},首先如果<em> J </em>实现接口{@link CompositeDataView},那么该接口的{@link CompositeDataView #toCompositeData toCompositeData}
    /* 方法进行转换另外,{@code CompositeData}是通过调用每个项目的getter并将其转换为相应的Open Data类型来构造的。
    /* 因此,一个getter例如</p>。
    /* 
    /* <blockquote>
    /*  {@code List <String> getNames()}
    /* </blockquote>
    /* 
    /* <p>将映射到名称为"{@code names}"和打开类型的项目{@code ArrayType(1,SimpleTypeSTRING)}转换到{@code CompositeData}将调用{@code getNames()}
    /* ,将结果{@code List <String>}转换为项目"{@code names}"的{@code String []} </p>。
    /* 
    /*  <p> {@ code CompositeData}是一个接口。
    /* 用于表示类型为Open Data的具体类是{@link CompositeDataSupport},或者是另一个实现{@code CompositeData}的类,其序列化为{@code CompositeDataSupport}
    /*  p>。
    /*  <p> {@ code CompositeData}是一个接口。
    /* 
    /*  <h4>从{@code CompositeData} </h4>重建Java类型的实例<em> J </em>
    /* 
    /* <p>如果对于Java类型<em> J </em>,<em> opendata(J)</em>是{@code CompositeData},则可以重建<em> J </em>来自{@code CompositeData}
    /* 或<em> J </em>无法重建如果{@code CompositeData}中的任何项目无法重建,则<em> </em> >。
    /* 
    /*  <p>对于任何给定的<em> J </em>,请参阅以下规则以确定如何从{@code CompositeData}重建<em> J </em>的实例。列表中的第一个适用规则是将使用的</p>
    /* 
    /* <ol>
    /* 
    /*  <li> <p>如果<em> J </em>有<br> {@code public static} <em> J </em> {@ code from(CompositeData cd)} <br>,
    /* 以重建<em> J </em> </p> </li>的实例。
    /* 
    /* <li> <p>否则,如果<em> J </em>至少有一个带有{@link javabeansConstructorProperties ConstructorProperties}注释的公共构造函数
    /* ,那么其中一个构造函数重建<em> </em>的实例</em>每个这样的注释必须列出与构造函数具有参数一样多的字符串;每个字符串必须命名与<em> J </em>的getter对应的属性;并且此gett
    /* er的类型必须与相应的构造函数参数相同。
    /* {@code ConstructorProperties}注释中未提及的getter不是一个错误(这些getter可能对应于不需要重建对象)</p>。
    /* 
    /* <p>通过调用具有来自{@code CompositeData}的适当重构项目的构造函数来重构<em> J </em>的实例。
    /* 将在运行时基于实际存在于{@code CompositeData},因为此{@code CompositeData}可能来自<em> J </em>的早期版本,其中并非所有项目都存在。
    /* 如果所有项目都是<em> </em>在{@code ConstructorProperties}注释中命名的属性作为{@code CompositeData}中的项目存在。
    /* 如果没有构造函数适用,那么尝试重建<em> J </em>失败</p>。
    /* 
    /* <p>对于任何可能的属性组合,必须是(a)没有适用的构造函数,或者(b)恰好有一个适用的构造函数,或者(c)其中一个适用的构造函数命名了正确的超集(换句话说,决不应该选择哪个构造函数)。
    /* 如果这个条件不是真的,那么<em> </em>是不可重构的</p> </li>。
    /* 
    /* <li> <p>否则,如果<em> J </em>有一个公共的无参数构造函数,并且<em> J </em> <em> N </em>有一个具有相同名称和类型的对应的setter,则使用no​​-arg构
    /* 造函数构造<em> J </em>的实例,并使用重构的项目{@code CompositeData}恢复值例如,如果有一个方法<br> {@code public List <String> getNames()}
    /*  <br>那么还必须有一个方法<br> {@code public void setNames(List <String> names)} <br>此规则的应用</p>。
    /* 
    /*  <p>如果{@code CompositeData}来自早期版本的<em> </em>,</em>有些项目可能不存在。在这种情况下,不会调用相应的setter </p>
    /* 
    /* <li> <p>否则,如果<em> J </em>是除getter之外没有方法的接口,则<em> J </em>的实例使用{@link javalangreflectProxy} {@link CompositeDataInvocationHandler}
    /*  {@code CompositeData}正在转换</p> </li>。
    /* 
    /*  <li> <p>否则,</em>无法重建</p> </li>
    /* </ol>
    /* 
    /* 
       @return true if the annotated interface is an MXBean interface.
    */
    boolean value() default true;
}
