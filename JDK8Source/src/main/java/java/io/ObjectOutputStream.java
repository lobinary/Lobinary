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

package java.io;

import java.io.ObjectStreamClass.WeakClassKey;
import java.lang.ref.ReferenceQueue;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import static java.io.ObjectStreamClass.processQueue;
import java.io.SerialCallbackContext;
import sun.reflect.misc.ReflectUtil;

/**
 * An ObjectOutputStream writes primitive data types and graphs of Java objects
 * to an OutputStream.  The objects can be read (reconstituted) using an
 * ObjectInputStream.  Persistent storage of objects can be accomplished by
 * using a file for the stream.  If the stream is a network socket stream, the
 * objects can be reconstituted on another host or in another process.
 *
 * <p>Only objects that support the java.io.Serializable interface can be
 * written to streams.  The class of each serializable object is encoded
 * including the class name and signature of the class, the values of the
 * object's fields and arrays, and the closure of any other objects referenced
 * from the initial objects.
 *
 * <p>The method writeObject is used to write an object to the stream.  Any
 * object, including Strings and arrays, is written with writeObject. Multiple
 * objects or primitives can be written to the stream.  The objects must be
 * read back from the corresponding ObjectInputstream with the same types and
 * in the same order as they were written.
 *
 * <p>Primitive data types can also be written to the stream using the
 * appropriate methods from DataOutput. Strings can also be written using the
 * writeUTF method.
 *
 * <p>The default serialization mechanism for an object writes the class of the
 * object, the class signature, and the values of all non-transient and
 * non-static fields.  References to other objects (except in transient or
 * static fields) cause those objects to be written also. Multiple references
 * to a single object are encoded using a reference sharing mechanism so that
 * graphs of objects can be restored to the same shape as when the original was
 * written.
 *
 * <p>For example to write an object that can be read by the example in
 * ObjectInputStream:
 * <br>
 * <pre>
 *      FileOutputStream fos = new FileOutputStream("t.tmp");
 *      ObjectOutputStream oos = new ObjectOutputStream(fos);
 *
 *      oos.writeInt(12345);
 *      oos.writeObject("Today");
 *      oos.writeObject(new Date());
 *
 *      oos.close();
 * </pre>
 *
 * <p>Classes that require special handling during the serialization and
 * deserialization process must implement special methods with these exact
 * signatures:
 * <br>
 * <pre>
 * private void readObject(java.io.ObjectInputStream stream)
 *     throws IOException, ClassNotFoundException;
 * private void writeObject(java.io.ObjectOutputStream stream)
 *     throws IOException
 * private void readObjectNoData()
 *     throws ObjectStreamException;
 * </pre>
 *
 * <p>The writeObject method is responsible for writing the state of the object
 * for its particular class so that the corresponding readObject method can
 * restore it.  The method does not need to concern itself with the state
 * belonging to the object's superclasses or subclasses.  State is saved by
 * writing the individual fields to the ObjectOutputStream using the
 * writeObject method or by using the methods for primitive data types
 * supported by DataOutput.
 *
 * <p>Serialization does not write out the fields of any object that does not
 * implement the java.io.Serializable interface.  Subclasses of Objects that
 * are not serializable can be serializable. In this case the non-serializable
 * class must have a no-arg constructor to allow its fields to be initialized.
 * In this case it is the responsibility of the subclass to save and restore
 * the state of the non-serializable class. It is frequently the case that the
 * fields of that class are accessible (public, package, or protected) or that
 * there are get and set methods that can be used to restore the state.
 *
 * <p>Serialization of an object can be prevented by implementing writeObject
 * and readObject methods that throw the NotSerializableException.  The
 * exception will be caught by the ObjectOutputStream and abort the
 * serialization process.
 *
 * <p>Implementing the Externalizable interface allows the object to assume
 * complete control over the contents and format of the object's serialized
 * form.  The methods of the Externalizable interface, writeExternal and
 * readExternal, are called to save and restore the objects state.  When
 * implemented by a class they can write and read their own state using all of
 * the methods of ObjectOutput and ObjectInput.  It is the responsibility of
 * the objects to handle any versioning that occurs.
 *
 * <p>Enum constants are serialized differently than ordinary serializable or
 * externalizable objects.  The serialized form of an enum constant consists
 * solely of its name; field values of the constant are not transmitted.  To
 * serialize an enum constant, ObjectOutputStream writes the string returned by
 * the constant's name method.  Like other serializable or externalizable
 * objects, enum constants can function as the targets of back references
 * appearing subsequently in the serialization stream.  The process by which
 * enum constants are serialized cannot be customized; any class-specific
 * writeObject and writeReplace methods defined by enum types are ignored
 * during serialization.  Similarly, any serialPersistentFields or
 * serialVersionUID field declarations are also ignored--all enum types have a
 * fixed serialVersionUID of 0L.
 *
 * <p>Primitive data, excluding serializable fields and externalizable data, is
 * written to the ObjectOutputStream in block-data records. A block data record
 * is composed of a header and data. The block data header consists of a marker
 * and the number of bytes to follow the header.  Consecutive primitive data
 * writes are merged into one block-data record.  The blocking factor used for
 * a block-data record will be 1024 bytes.  Each block-data record will be
 * filled up to 1024 bytes, or be written whenever there is a termination of
 * block-data mode.  Calls to the ObjectOutputStream methods writeObject,
 * defaultWriteObject and writeFields initially terminate any existing
 * block-data record.
 *
 * <p>
 *  ObjectOutputStream将原始数据类型和Java对象图形写入OutputStream。可以使用ObjectInputStream读取(重构)对象。
 * 对象的持久存储可以通过使用流的文件来实现。如果流是网络套接字流,则对象可以在另一主机上或在另一进程中重构。
 * 
 *  <p>只有支持java.io.Serializable接口的对象才能写入流。每个可序列化对象的类被编码,包括类的名称和签名,对象的字段和数组的值,以及从初始对象引用的任何其他对象的闭包。
 * 
 *  <p>方法writeObject用于将对象写入流。任何对象,包括字符串和数组,都是用writeObject写的。多个对象或基元可以写入流。
 * 对象必须从相应的ObjectInputstream中读回,它们的类型和顺序与写入时相同。
 * 
 *  <p>原始数据类型也可以使用DataOutput中的相应方法写入流。字符串也可以使用writeUTF方法写入。
 * 
 * <p>对象的默认序列化机制会写入对象的类,类签名以及所有非瞬态和非静态字段的值。引用其他对象(除了瞬态或静态字段)导致这些对象也被写入。
 * 对单个对象的多个引用使用引用共享机制来编码,使得对象的图形可以被恢复到与原始写入时相同的形状。
 * 
 *  <p>例如,要写一个可以由ObjectInputStream中的示例读取的对象：
 * <br>
 * <pre>
 *  FileOutputStream fos = new FileOutputStream("t.tmp"); ObjectOutputStream oos = new ObjectOutputStrea
 * m(fos);。
 * 
 *  oos.writeInt(12345); oos.writeObject("Today"); oos.writeObject(new Date());
 * 
 *  oos.close();
 * </pre>
 * 
 *  <p>在序列化和反序列化过程中需要特殊处理的类必须实现具有这些确切签名的特殊方法：
 * <br>
 * <pre>
 *  private void readObject(java.io.ObjectInputStream stream)throws IOException,ClassNotFoundException; 
 * private void writeObject(java.io.ObjectOutputStream stream)throws IOException private void readObject
 * NoData()throws ObjectStreamException;。
 * </pre>
 * 
 * <p> writeObject方法负责为其特定类编写对象的状态,以便相应的readObject方法可以恢复它。该方法不需要关心属于对象的超类或子类的状态。
 * 通过使用writeObject方法或通过使用DataOutput支持的原始数据类型的方法将各个字段写入ObjectOutputStream来保存状态。
 * 
 *  <p>序列化不会写出任何未实现java.io.Serializable接口的对象的字段。不可序列化的对象的子类可以是可序列化的。
 * 在这种情况下,非可序列化类必须有一个无参数构造函数,以允许其字段被初始化。在这种情况下,子类的责任是保存和恢复非可序列化类的状态。
 * 通常情况下,该类的字段是可访问的(public,package或protected),或者有可用于恢复状态的get和set方法。
 * 
 *  <p>可以通过实现抛出NotSerializableException的writeObject和readObject方法来防止对象的序列化。
 * 异常将被ObjectOutputStream捕获并中止序列化进程。
 * 
 * <p>实现Externalizable接口允许对象完全控制对象的序列化形式的内容和格式。
 * 调用Externalizable接口的方法writeExternal和readExternal来保存和恢复对象状态。
 * 当由类实现时,它们可以使用ObjectOutput和ObjectInput的所有方法写入和读取自己的状态。处理发生的任何版本控制是对象的责任。
 * 
 *  <p>枚举常量与普通可序列化或可外部化对象的序列化不同。枚举常量的序列化形式完全由其名称组成;不传送常数的字段值。
 * 为了序列化枚举常量,ObjectOutputStream写入由常量名方法返回的字符串。与其他可序列化或可外部化对象一样,枚举常量可以用作后续在序列化流中出现的后向引用的目标。
 * 枚举常量序列化的过程不能定制;序列化期间忽略枚举类型定义的任何特定于类的writeObject和writeReplace方法。
 * 类似地,任何serialPersistentFields或serialVersionUID字段声明也被忽略 - 所有枚举类型都有固定的serialVersionUID为0L。
 * 
 * <p>原始数据(不包括可序列化字段和可外部化数据)写入块数据记录中的ObjectOutputStream。块数据记录由标题和数据组成。块数据头由一个标记和跟随该标题的字节数组成。
 * 连续的原始数据写入被合并到一个块数据记录中。用于块数据记录的阻塞因子将是1024字节。每个块数据记录将被填充到1024字节,或者每当块数据模式终止时被写入。
 * 对ObjectOutputStream方法的调用writeObject,defaultWriteObject和writeFields最初终止任何现有的块数据记录。
 * 
 * 
 * @author      Mike Warres
 * @author      Roger Riggs
 * @see java.io.DataOutput
 * @see java.io.ObjectInputStream
 * @see java.io.Serializable
 * @see java.io.Externalizable
 * @see <a href="../../../platform/serialization/spec/output.html">Object Serialization Specification, Section 2, Object Output Classes</a>
 * @since       JDK1.1
 */
public class ObjectOutputStream
    extends OutputStream implements ObjectOutput, ObjectStreamConstants
{

    private static class Caches {
        /** cache of subclass security audit results */
        static final ConcurrentMap<WeakClassKey,Boolean> subclassAudits =
            new ConcurrentHashMap<>();

        /** queue for WeakReferences to audited subclasses */
        static final ReferenceQueue<Class<?>> subclassAuditsQueue =
            new ReferenceQueue<>();
    }

    /** filter stream for handling block data conversion */
    private final BlockDataOutputStream bout;
    /** obj -> wire handle map */
    private final HandleTable handles;
    /** obj -> replacement obj map */
    private final ReplaceTable subs;
    /** stream protocol version */
    private int protocol = PROTOCOL_VERSION_2;
    /** recursion depth */
    private int depth;

    /** buffer for writing primitive field values */
    private byte[] primVals;

    /** if true, invoke writeObjectOverride() instead of writeObject() */
    private final boolean enableOverride;
    /** if true, invoke replaceObject() */
    private boolean enableReplace;

    // values below valid only during upcalls to writeObject()/writeExternal()
    /**
     * Context during upcalls to class-defined writeObject methods; holds
     * object currently being serialized and descriptor for current class.
     * Null when not during writeObject upcall.
     * <p>
     *  上升到类定义的writeObject方法期间的上下文;保存当前正在序列化的对象和当前类的描述符。 Null当不在writeObject upcall。
     * 
     */
    private SerialCallbackContext curContext;
    /** current PutField object */
    private PutFieldImpl curPut;

    /** custom storage for debug trace info */
    private final DebugTraceInfoStack debugInfoStack;

    /**
     * value of "sun.io.serialization.extendedDebugInfo" property,
     * as true or false for extended information about exception's place
     * <p>
     *  "sun.io.serialization.extendedDebugInfo"属性的值,对于有关异常地址的扩展信息,值为true或false
     * 
     */
    private static final boolean extendedDebugInfo =
        java.security.AccessController.doPrivileged(
            new sun.security.action.GetBooleanAction(
                "sun.io.serialization.extendedDebugInfo")).booleanValue();

    /**
     * Creates an ObjectOutputStream that writes to the specified OutputStream.
     * This constructor writes the serialization stream header to the
     * underlying stream; callers may wish to flush the stream immediately to
     * ensure that constructors for receiving ObjectInputStreams will not block
     * when reading the header.
     *
     * <p>If a security manager is installed, this constructor will check for
     * the "enableSubclassImplementation" SerializablePermission when invoked
     * directly or indirectly by the constructor of a subclass which overrides
     * the ObjectOutputStream.putFields or ObjectOutputStream.writeUnshared
     * methods.
     *
     * <p>
     *  创建一个ObjectOutputStream,写入指定的OutputStream。
     * 这个构造函数将序列化流头部写入基础流;调用者可能希望立即刷新流,以确保用于接收ObjectInputStreams的构造函数在读取头时不会阻塞。
     * 
     * <p>如果安装了安全管理器,此构造函数将直接或间接地通过覆盖ObjectOutputStream.putFields或ObjectOutputStream.writeUnshared方法的子类的构造函数
     * 调用时检查"enableSubclassImplementation"SerializablePermission。
     * 
     * 
     * @param   out output stream to write to
     * @throws  IOException if an I/O error occurs while writing stream header
     * @throws  SecurityException if untrusted subclass illegally overrides
     *          security-sensitive methods
     * @throws  NullPointerException if <code>out</code> is <code>null</code>
     * @since   1.4
     * @see     ObjectOutputStream#ObjectOutputStream()
     * @see     ObjectOutputStream#putFields()
     * @see     ObjectInputStream#ObjectInputStream(InputStream)
     */
    public ObjectOutputStream(OutputStream out) throws IOException {
        verifySubclass();
        bout = new BlockDataOutputStream(out);
        handles = new HandleTable(10, (float) 3.00);
        subs = new ReplaceTable(10, (float) 3.00);
        enableOverride = false;
        writeStreamHeader();
        bout.setBlockDataMode(true);
        if (extendedDebugInfo) {
            debugInfoStack = new DebugTraceInfoStack();
        } else {
            debugInfoStack = null;
        }
    }

    /**
     * Provide a way for subclasses that are completely reimplementing
     * ObjectOutputStream to not have to allocate private data just used by
     * this implementation of ObjectOutputStream.
     *
     * <p>If there is a security manager installed, this method first calls the
     * security manager's <code>checkPermission</code> method with a
     * <code>SerializablePermission("enableSubclassImplementation")</code>
     * permission to ensure it's ok to enable subclassing.
     *
     * <p>
     *  为完全重新实现ObjectOutputStream的子类提供一种方法,而不必分配由此ObjectOutputStream实现使用的私有数据。
     * 
     *  <p>如果安装了安全管理器,此方法首先使用<code> SerializablePermission("enableSubclassImplementation")</code>权限调用安全管理器的<code>
     *  checkPermission </code>方法,以确保启用子类。
     * 
     * 
     * @throws  SecurityException if a security manager exists and its
     *          <code>checkPermission</code> method denies enabling
     *          subclassing.
     * @throws  IOException if an I/O error occurs while creating this stream
     * @see SecurityManager#checkPermission
     * @see java.io.SerializablePermission
     */
    protected ObjectOutputStream() throws IOException, SecurityException {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
        }
        bout = null;
        handles = null;
        subs = null;
        enableOverride = true;
        debugInfoStack = null;
    }

    /**
     * Specify stream protocol version to use when writing the stream.
     *
     * <p>This routine provides a hook to enable the current version of
     * Serialization to write in a format that is backwards compatible to a
     * previous version of the stream format.
     *
     * <p>Every effort will be made to avoid introducing additional
     * backwards incompatibilities; however, sometimes there is no
     * other alternative.
     *
     * <p>
     *  指定在写入流时要使用的流协议版本。
     * 
     *  <p>此例程提供了一个钩子,以使当前版本的序列化能够以与流格式的先前版本向后兼容的格式写入。
     * 
     *  <p>将尽一切努力避免引入额外的向后不兼容性;然而,有时没有其他选择。
     * 
     * 
     * @param   version use ProtocolVersion from java.io.ObjectStreamConstants.
     * @throws  IllegalStateException if called after any objects
     *          have been serialized.
     * @throws  IllegalArgumentException if invalid version is passed in.
     * @throws  IOException if I/O errors occur
     * @see java.io.ObjectStreamConstants#PROTOCOL_VERSION_1
     * @see java.io.ObjectStreamConstants#PROTOCOL_VERSION_2
     * @since   1.2
     */
    public void useProtocolVersion(int version) throws IOException {
        if (handles.size() != 0) {
            // REMIND: implement better check for pristine stream?
            throw new IllegalStateException("stream non-empty");
        }
        switch (version) {
            case PROTOCOL_VERSION_1:
            case PROTOCOL_VERSION_2:
                protocol = version;
                break;

            default:
                throw new IllegalArgumentException(
                    "unknown version: " + version);
        }
    }

    /**
     * Write the specified object to the ObjectOutputStream.  The class of the
     * object, the signature of the class, and the values of the non-transient
     * and non-static fields of the class and all of its supertypes are
     * written.  Default serialization for a class can be overridden using the
     * writeObject and the readObject methods.  Objects referenced by this
     * object are written transitively so that a complete equivalent graph of
     * objects can be reconstructed by an ObjectInputStream.
     *
     * <p>Exceptions are thrown for problems with the OutputStream and for
     * classes that should not be serialized.  All exceptions are fatal to the
     * OutputStream, which is left in an indeterminate state, and it is up to
     * the caller to ignore or recover the stream state.
     *
     * <p>
     * 将指定的对象写入ObjectOutputStream。将写入对象的类,类的签名,以及类的非瞬态和非静态字段的值及其所有超类型的值。
     * 类的默认序列化可以使用writeObject和readObject方法重写。此对象引用的对象是传递式写入的,以便可以通过ObjectInputStream重建对象的完整等效图。
     * 
     *  <p>对于OutputStream的问题以及不应该序列化的类,抛出异常。所有的异常对OutputStream是致命的,它留在一个不确定的状态,它是由调用者忽略或恢复流状态。
     * 
     * 
     * @throws  InvalidClassException Something is wrong with a class used by
     *          serialization.
     * @throws  NotSerializableException Some object to be serialized does not
     *          implement the java.io.Serializable interface.
     * @throws  IOException Any exception thrown by the underlying
     *          OutputStream.
     */
    public final void writeObject(Object obj) throws IOException {
        if (enableOverride) {
            writeObjectOverride(obj);
            return;
        }
        try {
            writeObject0(obj, false);
        } catch (IOException ex) {
            if (depth == 0) {
                writeFatalException(ex);
            }
            throw ex;
        }
    }

    /**
     * Method used by subclasses to override the default writeObject method.
     * This method is called by trusted subclasses of ObjectInputStream that
     * constructed ObjectInputStream using the protected no-arg constructor.
     * The subclass is expected to provide an override method with the modifier
     * "final".
     *
     * <p>
     *  子类用于覆盖默认writeObject方法的方法。
     * 此方法由ObjectInputStream的可信子类调用,ObjectInputStream使用受保护的无参构造函数构造ObjectInputStream。
     * 子类期望提供具有修饰符"final"的覆盖方法。
     * 
     * 
     * @param   obj object to be written to the underlying stream
     * @throws  IOException if there are I/O errors while writing to the
     *          underlying stream
     * @see #ObjectOutputStream()
     * @see #writeObject(Object)
     * @since 1.2
     */
    protected void writeObjectOverride(Object obj) throws IOException {
    }

    /**
     * Writes an "unshared" object to the ObjectOutputStream.  This method is
     * identical to writeObject, except that it always writes the given object
     * as a new, unique object in the stream (as opposed to a back-reference
     * pointing to a previously serialized instance).  Specifically:
     * <ul>
     *   <li>An object written via writeUnshared is always serialized in the
     *       same manner as a newly appearing object (an object that has not
     *       been written to the stream yet), regardless of whether or not the
     *       object has been written previously.
     *
     *   <li>If writeObject is used to write an object that has been previously
     *       written with writeUnshared, the previous writeUnshared operation
     *       is treated as if it were a write of a separate object.  In other
     *       words, ObjectOutputStream will never generate back-references to
     *       object data written by calls to writeUnshared.
     * </ul>
     * While writing an object via writeUnshared does not in itself guarantee a
     * unique reference to the object when it is deserialized, it allows a
     * single object to be defined multiple times in a stream, so that multiple
     * calls to readUnshared by the receiver will not conflict.  Note that the
     * rules described above only apply to the base-level object written with
     * writeUnshared, and not to any transitively referenced sub-objects in the
     * object graph to be serialized.
     *
     * <p>ObjectOutputStream subclasses which override this method can only be
     * constructed in security contexts possessing the
     * "enableSubclassImplementation" SerializablePermission; any attempt to
     * instantiate such a subclass without this permission will cause a
     * SecurityException to be thrown.
     *
     * <p>
     *  将"非共享"对象写入ObjectOutputStream。此方法与writeObject相同,除了它始终将给定对象作为流中的一个新的唯一对象(而不是指向以前序列化的实例的反向引用)。特别：
     * <ul>
     * <li>通过writeUnshared写入的对象总是以与新出现的对象(尚未写入流的对象)相同的方式进行序列化,而不管对象是否先前已写入。
     * 
     *  <li>如果writeObject用于写入先前使用writeUnshared写入的对象,则之前的writeUnshared操作将被视为写入单独的对象。
     * 换句话说,ObjectOutputStream永远不会生成对writeUnshared调用写入的对象数据的反向引用。
     * </ul>
     *  虽然通过writeUnshared编写对象本身并不保证对对象进行反序列化时的唯一引用,但它允许在流中多次定义单个对象,以便由接收器对readUnshared的多个调用不会发生冲突。
     * 请注意,上述规则仅适用于使用writeUnshared写入的基本级对象,而不适用于要序列化的对象图中的任何传递引用子对象。
     * 
     *  <p>覆盖此方法的ObjectOutputStream子类只能在拥有"enableSubclassImplementation"SerializablePermission的安全上下文中构建;任何尝试
     * 实例化这样的子类没有此权限将导致SecurityException抛出。
     * 
     * 
     * @param   obj object to write to stream
     * @throws  NotSerializableException if an object in the graph to be
     *          serialized does not implement the Serializable interface
     * @throws  InvalidClassException if a problem exists with the class of an
     *          object to be serialized
     * @throws  IOException if an I/O error occurs during serialization
     * @since 1.4
     */
    public void writeUnshared(Object obj) throws IOException {
        try {
            writeObject0(obj, true);
        } catch (IOException ex) {
            if (depth == 0) {
                writeFatalException(ex);
            }
            throw ex;
        }
    }

    /**
     * Write the non-static and non-transient fields of the current class to
     * this stream.  This may only be called from the writeObject method of the
     * class being serialized. It will throw the NotActiveException if it is
     * called otherwise.
     *
     * <p>
     * 将当前类的非静态和非瞬态字段写入此流。这只能从被序列化的类的writeObject方法中调用。如果它被调用,它将抛出NotActiveException异常。
     * 
     * 
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          <code>OutputStream</code>
     */
    public void defaultWriteObject() throws IOException {
        SerialCallbackContext ctx = curContext;
        if (ctx == null) {
            throw new NotActiveException("not in call to writeObject");
        }
        Object curObj = ctx.getObj();
        ObjectStreamClass curDesc = ctx.getDesc();
        bout.setBlockDataMode(false);
        defaultWriteFields(curObj, curDesc);
        bout.setBlockDataMode(true);
    }

    /**
     * Retrieve the object used to buffer persistent fields to be written to
     * the stream.  The fields will be written to the stream when writeFields
     * method is called.
     *
     * <p>
     *  检索用于缓冲要写入流的持久字段的对象。当writeFields方法被调用时,字段将被写入流。
     * 
     * 
     * @return  an instance of the class Putfield that holds the serializable
     *          fields
     * @throws  IOException if I/O errors occur
     * @since 1.2
     */
    public ObjectOutputStream.PutField putFields() throws IOException {
        if (curPut == null) {
            SerialCallbackContext ctx = curContext;
            if (ctx == null) {
                throw new NotActiveException("not in call to writeObject");
            }
            Object curObj = ctx.getObj();
            ObjectStreamClass curDesc = ctx.getDesc();
            curPut = new PutFieldImpl(curDesc);
        }
        return curPut;
    }

    /**
     * Write the buffered fields to the stream.
     *
     * <p>
     *  将缓冲字段写入流。
     * 
     * 
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     * @throws  NotActiveException Called when a classes writeObject method was
     *          not called to write the state of the object.
     * @since 1.2
     */
    public void writeFields() throws IOException {
        if (curPut == null) {
            throw new NotActiveException("no current PutField object");
        }
        bout.setBlockDataMode(false);
        curPut.writeFields();
        bout.setBlockDataMode(true);
    }

    /**
     * Reset will disregard the state of any objects already written to the
     * stream.  The state is reset to be the same as a new ObjectOutputStream.
     * The current point in the stream is marked as reset so the corresponding
     * ObjectInputStream will be reset at the same point.  Objects previously
     * written to the stream will not be referred to as already being in the
     * stream.  They will be written to the stream again.
     *
     * <p>
     *  重置将忽略已写入流的任何对象的状态。状态被重置为与新的ObjectOutputStream相同。流中的当前点被标记为重置,因此相应的ObjectInputStream将在同一点重置。
     * 先前写入流的对象将不会被称为已在流中。它们将再次写入流。
     * 
     * 
     * @throws  IOException if reset() is invoked while serializing an object.
     */
    public void reset() throws IOException {
        if (depth != 0) {
            throw new IOException("stream active");
        }
        bout.setBlockDataMode(false);
        bout.writeByte(TC_RESET);
        clear();
        bout.setBlockDataMode(true);
    }

    /**
     * Subclasses may implement this method to allow class data to be stored in
     * the stream. By default this method does nothing.  The corresponding
     * method in ObjectInputStream is resolveClass.  This method is called
     * exactly once for each unique class in the stream.  The class name and
     * signature will have already been written to the stream.  This method may
     * make free use of the ObjectOutputStream to save any representation of
     * the class it deems suitable (for example, the bytes of the class file).
     * The resolveClass method in the corresponding subclass of
     * ObjectInputStream must read and use any data or objects written by
     * annotateClass.
     *
     * <p>
     *  子类可以实现此方法以允许类数据存储在流中。默认情况下,此方法不执行任何操作。 ObjectInputStream中的相应方法是resolveClass。对于流中的每个唯一类,此方法只调用一次。
     * 类名和签名将已经写入流。该方法可以自由使用ObjectOutputStream来保存它认为合适的类的任何表示(例如,类文件的字节)。
     *  ObjectInputStream对应子类中的resolveClass方法必须读取和使用由annotateClass写的任何数据或对象。
     * 
     * 
     * @param   cl the class to annotate custom data for
     * @throws  IOException Any exception thrown by the underlying
     *          OutputStream.
     */
    protected void annotateClass(Class<?> cl) throws IOException {
    }

    /**
     * Subclasses may implement this method to store custom data in the stream
     * along with descriptors for dynamic proxy classes.
     *
     * <p>This method is called exactly once for each unique proxy class
     * descriptor in the stream.  The default implementation of this method in
     * <code>ObjectOutputStream</code> does nothing.
     *
     * <p>The corresponding method in <code>ObjectInputStream</code> is
     * <code>resolveProxyClass</code>.  For a given subclass of
     * <code>ObjectOutputStream</code> that overrides this method, the
     * <code>resolveProxyClass</code> method in the corresponding subclass of
     * <code>ObjectInputStream</code> must read any data or objects written by
     * <code>annotateProxyClass</code>.
     *
     * <p>
     * 子类可以实现此方法以将自定义数据与动态代理类的描述符一起存储在流中。
     * 
     *  <p>对于流中的每个唯一代理类描述符,此方法只调用一次。此方法在<code> ObjectOutputStream </code>中的默认实现不起作用。
     * 
     *  <p> <code> ObjectInputStream </code>中的对应方法是<code> resolveProxyClass </code>。
     * 对于覆盖此方法的<code> ObjectOutputStream </code>的给定子类,<code> ObjectInputStream </code>的相应子类中的<code> resolveP
     * roxyClass </code>方法必须读取由< code> annotateProxyClass </code>。
     *  <p> <code> ObjectInputStream </code>中的对应方法是<code> resolveProxyClass </code>。
     * 
     * 
     * @param   cl the proxy class to annotate custom data for
     * @throws  IOException any exception thrown by the underlying
     *          <code>OutputStream</code>
     * @see ObjectInputStream#resolveProxyClass(String[])
     * @since   1.3
     */
    protected void annotateProxyClass(Class<?> cl) throws IOException {
    }

    /**
     * This method will allow trusted subclasses of ObjectOutputStream to
     * substitute one object for another during serialization. Replacing
     * objects is disabled until enableReplaceObject is called. The
     * enableReplaceObject method checks that the stream requesting to do
     * replacement can be trusted.  The first occurrence of each object written
     * into the serialization stream is passed to replaceObject.  Subsequent
     * references to the object are replaced by the object returned by the
     * original call to replaceObject.  To ensure that the private state of
     * objects is not unintentionally exposed, only trusted streams may use
     * replaceObject.
     *
     * <p>The ObjectOutputStream.writeObject method takes a parameter of type
     * Object (as opposed to type Serializable) to allow for cases where
     * non-serializable objects are replaced by serializable ones.
     *
     * <p>When a subclass is replacing objects it must insure that either a
     * complementary substitution must be made during deserialization or that
     * the substituted object is compatible with every field where the
     * reference will be stored.  Objects whose type is not a subclass of the
     * type of the field or array element abort the serialization by raising an
     * exception and the object is not be stored.
     *
     * <p>This method is called only once when each object is first
     * encountered.  All subsequent references to the object will be redirected
     * to the new object. This method should return the object to be
     * substituted or the original object.
     *
     * <p>Null can be returned as the object to be substituted, but may cause
     * NullReferenceException in classes that contain references to the
     * original object since they may be expecting an object instead of
     * null.
     *
     * <p>
     *  此方法将允许ObjectOutputStream的可信子类在序列化期间将一个对象替换为另一个对象。在调用enableReplaceObject之前,将取消替换对象。
     *  enableReplaceObject方法检查请求做替换的流是否可信。写入序列化流的每个对象的第一次出现传递给replaceObject。
     * 对对象的后续引用将由对replaceObject的原始调用返回的对象替换。为了确保对象的私有状态不被无意暴露,只有受信任的流可以使用replaceObject。
     * 
     *  <p> ObjectOutputStream.writeObject方法接受Object类型的参数(而不是类型Serializable),以允许非序列化对象被可序列化对象替换的情况。
     * 
     * <p>当子类替换对象时,它必须确保在反序列化期间必须进行互补替换,或者替换对象与引用将被存储的每个字段兼容。类型不是字段或数组元素类型的子类的对象通过引发异常来中止序列化,并且不会存储对象。
     * 
     *  <p>当每个对象首次遇到时,此方法只调用一次。对对象的所有后续引用将重定向到新对象。此方法应返回要替换的对象或原始对象。
     * 
     *  <p> Null可以作为要替换的对象返回,但是在包含对原始对象的引用的类中可能会导致NullReferenceException,因为它们可能期望一个对象而不是null。
     * 
     * 
     * @param   obj the object to be replaced
     * @return  the alternate object that replaced the specified one
     * @throws  IOException Any exception thrown by the underlying
     *          OutputStream.
     */
    protected Object replaceObject(Object obj) throws IOException {
        return obj;
    }

    /**
     * Enable the stream to do replacement of objects in the stream.  When
     * enabled, the replaceObject method is called for every object being
     * serialized.
     *
     * <p>If <code>enable</code> is true, and there is a security manager
     * installed, this method first calls the security manager's
     * <code>checkPermission</code> method with a
     * <code>SerializablePermission("enableSubstitution")</code> permission to
     * ensure it's ok to enable the stream to do replacement of objects in the
     * stream.
     *
     * <p>
     *  启用流以替换流中的对象。启用时,会为正在序列化的每个对象调用replaceObject方法。
     * 
     *  <p>如果<code> enable </code>为true,并且安装了安全管理器,则此方法首先使用<code> SerializablePermission("enableSubstitution
     * ")调用安全管理器的<code> checkPermission </code> </code>权限,以确保可以启用流替换流中的对象。
     * 
     * 
     * @param   enable boolean parameter to enable replacement of objects
     * @return  the previous setting before this method was invoked
     * @throws  SecurityException if a security manager exists and its
     *          <code>checkPermission</code> method denies enabling the stream
     *          to do replacement of objects in the stream.
     * @see SecurityManager#checkPermission
     * @see java.io.SerializablePermission
     */
    protected boolean enableReplaceObject(boolean enable)
        throws SecurityException
    {
        if (enable == enableReplace) {
            return enable;
        }
        if (enable) {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                sm.checkPermission(SUBSTITUTION_PERMISSION);
            }
        }
        enableReplace = enable;
        return !enableReplace;
    }

    /**
     * The writeStreamHeader method is provided so subclasses can append or
     * prepend their own header to the stream.  It writes the magic number and
     * version to the stream.
     *
     * <p>
     *  提供了writeStreamHeader方法,因此子类可以在流中附加或预置自己的头。它将幻数和版本写入流。
     * 
     * 
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    protected void writeStreamHeader() throws IOException {
        bout.writeShort(STREAM_MAGIC);
        bout.writeShort(STREAM_VERSION);
    }

    /**
     * Write the specified class descriptor to the ObjectOutputStream.  Class
     * descriptors are used to identify the classes of objects written to the
     * stream.  Subclasses of ObjectOutputStream may override this method to
     * customize the way in which class descriptors are written to the
     * serialization stream.  The corresponding method in ObjectInputStream,
     * <code>readClassDescriptor</code>, should then be overridden to
     * reconstitute the class descriptor from its custom stream representation.
     * By default, this method writes class descriptors according to the format
     * defined in the Object Serialization specification.
     *
     * <p>Note that this method will only be called if the ObjectOutputStream
     * is not using the old serialization stream format (set by calling
     * ObjectOutputStream's <code>useProtocolVersion</code> method).  If this
     * serialization stream is using the old format
     * (<code>PROTOCOL_VERSION_1</code>), the class descriptor will be written
     * internally in a manner that cannot be overridden or customized.
     *
     * <p>
     * 将指定的类描述符写入ObjectOutputStream。类描述符用于标识写入流的对象的类。 ObjectOutputStream的子类可以覆盖此方法以自定义类描述符写入序列化流的方式。
     *  ObjectInputStream中的相应方法<code> readClassDescriptor </code>应该被重写以从其自定义流表示中重构类描述符。
     * 默认情况下,此方法根据对象序列化规范中定义的格式写入类描述符。
     * 
     *  <p>请注意,只有当ObjectOutputStream不使用旧的序列化流格式(通过调用ObjectOutputStream的<code> useProtocolVersion </code>方法设置
     * )时,才会调用此方法。
     * 如果此序列化流使用旧格式(<code> PROTOCOL_VERSION_1 </code>),则类描述符将以无法重写或自定义的方式在内部写入。
     * 
     * 
     * @param   desc class descriptor to write to the stream
     * @throws  IOException If an I/O error has occurred.
     * @see java.io.ObjectInputStream#readClassDescriptor()
     * @see #useProtocolVersion(int)
     * @see java.io.ObjectStreamConstants#PROTOCOL_VERSION_1
     * @since 1.3
     */
    protected void writeClassDescriptor(ObjectStreamClass desc)
        throws IOException
    {
        desc.writeNonProxy(this);
    }

    /**
     * Writes a byte. This method will block until the byte is actually
     * written.
     *
     * <p>
     *  写入一个字节。此方法将阻塞,直到字节实际写入。
     * 
     * 
     * @param   val the byte to be written to the stream
     * @throws  IOException If an I/O error has occurred.
     */
    public void write(int val) throws IOException {
        bout.write(val);
    }

    /**
     * Writes an array of bytes. This method will block until the bytes are
     * actually written.
     *
     * <p>
     *  写入字节数组。此方法将阻塞,直到字节实际写入。
     * 
     * 
     * @param   buf the data to be written
     * @throws  IOException If an I/O error has occurred.
     */
    public void write(byte[] buf) throws IOException {
        bout.write(buf, 0, buf.length, false);
    }

    /**
     * Writes a sub array of bytes.
     *
     * <p>
     *  写入字节的子数组。
     * 
     * 
     * @param   buf the data to be written
     * @param   off the start offset in the data
     * @param   len the number of bytes that are written
     * @throws  IOException If an I/O error has occurred.
     */
    public void write(byte[] buf, int off, int len) throws IOException {
        if (buf == null) {
            throw new NullPointerException();
        }
        int endoff = off + len;
        if (off < 0 || len < 0 || endoff > buf.length || endoff < 0) {
            throw new IndexOutOfBoundsException();
        }
        bout.write(buf, off, len, false);
    }

    /**
     * Flushes the stream. This will write any buffered output bytes and flush
     * through to the underlying stream.
     *
     * <p>
     *  刷新流。这将写入任何缓冲的输出字节并刷新到底层流。
     * 
     * 
     * @throws  IOException If an I/O error has occurred.
     */
    public void flush() throws IOException {
        bout.flush();
    }

    /**
     * Drain any buffered data in ObjectOutputStream.  Similar to flush but
     * does not propagate the flush to the underlying stream.
     *
     * <p>
     *  在ObjectOutputStream中排除任何缓冲的数据。类似于flush,但不将刷新传播到底层流。
     * 
     * 
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    protected void drain() throws IOException {
        bout.drain();
    }

    /**
     * Closes the stream. This method must be called to release any resources
     * associated with the stream.
     *
     * <p>
     * 关闭流。必须调用此方法才能释放与流关联的任何资源。
     * 
     * 
     * @throws  IOException If an I/O error has occurred.
     */
    public void close() throws IOException {
        flush();
        clear();
        bout.close();
    }

    /**
     * Writes a boolean.
     *
     * <p>
     *  写入布尔值。
     * 
     * 
     * @param   val the boolean to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeBoolean(boolean val) throws IOException {
        bout.writeBoolean(val);
    }

    /**
     * Writes an 8 bit byte.
     *
     * <p>
     *  写入8位字节。
     * 
     * 
     * @param   val the byte value to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeByte(int val) throws IOException  {
        bout.writeByte(val);
    }

    /**
     * Writes a 16 bit short.
     *
     * <p>
     *  写一个16位短。
     * 
     * 
     * @param   val the short value to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeShort(int val)  throws IOException {
        bout.writeShort(val);
    }

    /**
     * Writes a 16 bit char.
     *
     * <p>
     *  写一个16位的char。
     * 
     * 
     * @param   val the char value to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeChar(int val)  throws IOException {
        bout.writeChar(val);
    }

    /**
     * Writes a 32 bit int.
     *
     * <p>
     *  写一个32位int。
     * 
     * 
     * @param   val the integer value to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeInt(int val)  throws IOException {
        bout.writeInt(val);
    }

    /**
     * Writes a 64 bit long.
     *
     * <p>
     *  写64位长。
     * 
     * 
     * @param   val the long value to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeLong(long val)  throws IOException {
        bout.writeLong(val);
    }

    /**
     * Writes a 32 bit float.
     *
     * <p>
     *  写入32位浮点数。
     * 
     * 
     * @param   val the float value to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeFloat(float val) throws IOException {
        bout.writeFloat(val);
    }

    /**
     * Writes a 64 bit double.
     *
     * <p>
     *  写一个64位双。
     * 
     * 
     * @param   val the double value to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeDouble(double val) throws IOException {
        bout.writeDouble(val);
    }

    /**
     * Writes a String as a sequence of bytes.
     *
     * <p>
     *  将字符串写为字节序列。
     * 
     * 
     * @param   str the String of bytes to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeBytes(String str) throws IOException {
        bout.writeBytes(str);
    }

    /**
     * Writes a String as a sequence of chars.
     *
     * <p>
     *  将字符串写为字符序列。
     * 
     * 
     * @param   str the String of chars to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeChars(String str) throws IOException {
        bout.writeChars(str);
    }

    /**
     * Primitive data write of this String in
     * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
     * format.  Note that there is a
     * significant difference between writing a String into the stream as
     * primitive data or as an Object. A String instance written by writeObject
     * is written into the stream as a String initially. Future writeObject()
     * calls write references to the string into the stream.
     *
     * <p>
     *  此字符串的原始数据以<a href="DataInput.html#modified-utf-8">修改的UTF-8 </a>格式写入。
     * 注意,将String作为基本数据或作为Object写入流中有一个显着的区别。由writeObject写入的String实例最初作为String写入流。
     * 未来writeObject()调用将对字符串的引用写入流。
     * 
     * 
     * @param   str the String to be written
     * @throws  IOException if I/O errors occur while writing to the underlying
     *          stream
     */
    public void writeUTF(String str) throws IOException {
        bout.writeUTF(str);
    }

    /**
     * Provide programmatic access to the persistent fields to be written
     * to ObjectOutput.
     *
     * <p>
     *  提供对要写入ObjectOutput的持久字段的编程访问。
     * 
     * 
     * @since 1.2
     */
    public static abstract class PutField {

        /**
         * Put the value of the named boolean field into the persistent field.
         *
         * <p>
         *  将指定的布尔字段的值放入持久字段。
         * 
         * 
         * @param  name the name of the serializable field
         * @param  val the value to assign to the field
         * @throws IllegalArgumentException if <code>name</code> does not
         * match the name of a serializable field for the class whose fields
         * are being written, or if the type of the named field is not
         * <code>boolean</code>
         */
        public abstract void put(String name, boolean val);

        /**
         * Put the value of the named byte field into the persistent field.
         *
         * <p>
         *  将命名的字节字段的值放入持久字段。
         * 
         * 
         * @param  name the name of the serializable field
         * @param  val the value to assign to the field
         * @throws IllegalArgumentException if <code>name</code> does not
         * match the name of a serializable field for the class whose fields
         * are being written, or if the type of the named field is not
         * <code>byte</code>
         */
        public abstract void put(String name, byte val);

        /**
         * Put the value of the named char field into the persistent field.
         *
         * <p>
         *  将命名的char字段的值放入持久字段。
         * 
         * 
         * @param  name the name of the serializable field
         * @param  val the value to assign to the field
         * @throws IllegalArgumentException if <code>name</code> does not
         * match the name of a serializable field for the class whose fields
         * are being written, or if the type of the named field is not
         * <code>char</code>
         */
        public abstract void put(String name, char val);

        /**
         * Put the value of the named short field into the persistent field.
         *
         * <p>
         *  将命名的短字段的值放入持久字段。
         * 
         * 
         * @param  name the name of the serializable field
         * @param  val the value to assign to the field
         * @throws IllegalArgumentException if <code>name</code> does not
         * match the name of a serializable field for the class whose fields
         * are being written, or if the type of the named field is not
         * <code>short</code>
         */
        public abstract void put(String name, short val);

        /**
         * Put the value of the named int field into the persistent field.
         *
         * <p>
         *  将命名的int字段的值放入持久字段。
         * 
         * 
         * @param  name the name of the serializable field
         * @param  val the value to assign to the field
         * @throws IllegalArgumentException if <code>name</code> does not
         * match the name of a serializable field for the class whose fields
         * are being written, or if the type of the named field is not
         * <code>int</code>
         */
        public abstract void put(String name, int val);

        /**
         * Put the value of the named long field into the persistent field.
         *
         * <p>
         *  将命名的长字段的值放入持久字段。
         * 
         * 
         * @param  name the name of the serializable field
         * @param  val the value to assign to the field
         * @throws IllegalArgumentException if <code>name</code> does not
         * match the name of a serializable field for the class whose fields
         * are being written, or if the type of the named field is not
         * <code>long</code>
         */
        public abstract void put(String name, long val);

        /**
         * Put the value of the named float field into the persistent field.
         *
         * <p>
         *  将命名的float字段的值放入持久字段。
         * 
         * 
         * @param  name the name of the serializable field
         * @param  val the value to assign to the field
         * @throws IllegalArgumentException if <code>name</code> does not
         * match the name of a serializable field for the class whose fields
         * are being written, or if the type of the named field is not
         * <code>float</code>
         */
        public abstract void put(String name, float val);

        /**
         * Put the value of the named double field into the persistent field.
         *
         * <p>
         *  将命名的double字段的值放入持久字段。
         * 
         * 
         * @param  name the name of the serializable field
         * @param  val the value to assign to the field
         * @throws IllegalArgumentException if <code>name</code> does not
         * match the name of a serializable field for the class whose fields
         * are being written, or if the type of the named field is not
         * <code>double</code>
         */
        public abstract void put(String name, double val);

        /**
         * Put the value of the named Object field into the persistent field.
         *
         * <p>
         *  将命名对象字段的值放入持久字段。
         * 
         * 
         * @param  name the name of the serializable field
         * @param  val the value to assign to the field
         *         (which may be <code>null</code>)
         * @throws IllegalArgumentException if <code>name</code> does not
         * match the name of a serializable field for the class whose fields
         * are being written, or if the type of the named field is not a
         * reference type
         */
        public abstract void put(String name, Object val);

        /**
         * Write the data and fields to the specified ObjectOutput stream,
         * which must be the same stream that produced this
         * <code>PutField</code> object.
         *
         * <p>
         * 将数据和字段写入指定的ObjectOutput流,该流必须是生成此<code> PutField </code>对象的流。
         * 
         * 
         * @param  out the stream to write the data and fields to
         * @throws IOException if I/O errors occur while writing to the
         *         underlying stream
         * @throws IllegalArgumentException if the specified stream is not
         *         the same stream that produced this <code>PutField</code>
         *         object
         * @deprecated This method does not write the values contained by this
         *         <code>PutField</code> object in a proper format, and may
         *         result in corruption of the serialization stream.  The
         *         correct way to write <code>PutField</code> data is by
         *         calling the {@link java.io.ObjectOutputStream#writeFields()}
         *         method.
         */
        @Deprecated
        public abstract void write(ObjectOutput out) throws IOException;
    }


    /**
     * Returns protocol version in use.
     * <p>
     *  返回正在使用的协议版本。
     * 
     */
    int getProtocolVersion() {
        return protocol;
    }

    /**
     * Writes string without allowing it to be replaced in stream.  Used by
     * ObjectStreamClass to write class descriptor type strings.
     * <p>
     *  写入字符串,不允许在流中替换它。由ObjectStreamClass用于编写类描述符类型字符串。
     * 
     */
    void writeTypeString(String str) throws IOException {
        int handle;
        if (str == null) {
            writeNull();
        } else if ((handle = handles.lookup(str)) != -1) {
            writeHandle(handle);
        } else {
            writeString(str, false);
        }
    }

    /**
     * Verifies that this (possibly subclass) instance can be constructed
     * without violating security constraints: the subclass must not override
     * security-sensitive non-final methods, or else the
     * "enableSubclassImplementation" SerializablePermission is checked.
     * <p>
     *  验证这个(可能是子类)实例可以在不违反安全约束的情况下构造：子类不能覆盖安全敏感的非最终方法,否则将检查"enableSubclassImplementation"SerializablePermis
     * sion。
     * 
     */
    private void verifySubclass() {
        Class<?> cl = getClass();
        if (cl == ObjectOutputStream.class) {
            return;
        }
        SecurityManager sm = System.getSecurityManager();
        if (sm == null) {
            return;
        }
        processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
        WeakClassKey key = new WeakClassKey(cl, Caches.subclassAuditsQueue);
        Boolean result = Caches.subclassAudits.get(key);
        if (result == null) {
            result = Boolean.valueOf(auditSubclass(cl));
            Caches.subclassAudits.putIfAbsent(key, result);
        }
        if (result.booleanValue()) {
            return;
        }
        sm.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
    }

    /**
     * Performs reflective checks on given subclass to verify that it doesn't
     * override security-sensitive non-final methods.  Returns true if subclass
     * is "safe", false otherwise.
     * <p>
     *  对给定的子类执行反射检查以验证它不覆盖安全敏感的非最终方法。如果子类是"安全"则返回true,否则返回false。
     * 
     */
    private static boolean auditSubclass(final Class<?> subcl) {
        Boolean result = AccessController.doPrivileged(
            new PrivilegedAction<Boolean>() {
                public Boolean run() {
                    for (Class<?> cl = subcl;
                         cl != ObjectOutputStream.class;
                         cl = cl.getSuperclass())
                    {
                        try {
                            cl.getDeclaredMethod(
                                "writeUnshared", new Class<?>[] { Object.class });
                            return Boolean.FALSE;
                        } catch (NoSuchMethodException ex) {
                        }
                        try {
                            cl.getDeclaredMethod("putFields", (Class<?>[]) null);
                            return Boolean.FALSE;
                        } catch (NoSuchMethodException ex) {
                        }
                    }
                    return Boolean.TRUE;
                }
            }
        );
        return result.booleanValue();
    }

    /**
     * Clears internal data structures.
     * <p>
     *  清除内部数据结构。
     * 
     */
    private void clear() {
        subs.clear();
        handles.clear();
    }

    /**
     * Underlying writeObject/writeUnshared implementation.
     * <p>
     *  底层writeObject / writeUnshared实现。
     * 
     */
    private void writeObject0(Object obj, boolean unshared)
        throws IOException
    {
        boolean oldMode = bout.setBlockDataMode(false);
        depth++;
        try {
            // handle previously written and non-replaceable objects
            int h;
            if ((obj = subs.lookup(obj)) == null) {
                writeNull();
                return;
            } else if (!unshared && (h = handles.lookup(obj)) != -1) {
                writeHandle(h);
                return;
            } else if (obj instanceof Class) {
                writeClass((Class) obj, unshared);
                return;
            } else if (obj instanceof ObjectStreamClass) {
                writeClassDesc((ObjectStreamClass) obj, unshared);
                return;
            }

            // check for replacement object
            Object orig = obj;
            Class<?> cl = obj.getClass();
            ObjectStreamClass desc;
            for (;;) {
                // REMIND: skip this check for strings/arrays?
                Class<?> repCl;
                desc = ObjectStreamClass.lookup(cl, true);
                if (!desc.hasWriteReplaceMethod() ||
                    (obj = desc.invokeWriteReplace(obj)) == null ||
                    (repCl = obj.getClass()) == cl)
                {
                    break;
                }
                cl = repCl;
            }
            if (enableReplace) {
                Object rep = replaceObject(obj);
                if (rep != obj && rep != null) {
                    cl = rep.getClass();
                    desc = ObjectStreamClass.lookup(cl, true);
                }
                obj = rep;
            }

            // if object replaced, run through original checks a second time
            if (obj != orig) {
                subs.assign(orig, obj);
                if (obj == null) {
                    writeNull();
                    return;
                } else if (!unshared && (h = handles.lookup(obj)) != -1) {
                    writeHandle(h);
                    return;
                } else if (obj instanceof Class) {
                    writeClass((Class) obj, unshared);
                    return;
                } else if (obj instanceof ObjectStreamClass) {
                    writeClassDesc((ObjectStreamClass) obj, unshared);
                    return;
                }
            }

            // remaining cases
            if (obj instanceof String) {
                writeString((String) obj, unshared);
            } else if (cl.isArray()) {
                writeArray(obj, desc, unshared);
            } else if (obj instanceof Enum) {
                writeEnum((Enum<?>) obj, desc, unshared);
            } else if (obj instanceof Serializable) {
                writeOrdinaryObject(obj, desc, unshared);
            } else {
                if (extendedDebugInfo) {
                    throw new NotSerializableException(
                        cl.getName() + "\n" + debugInfoStack.toString());
                } else {
                    throw new NotSerializableException(cl.getName());
                }
            }
        } finally {
            depth--;
            bout.setBlockDataMode(oldMode);
        }
    }

    /**
     * Writes null code to stream.
     * <p>
     *  将空代码写入流。
     * 
     */
    private void writeNull() throws IOException {
        bout.writeByte(TC_NULL);
    }

    /**
     * Writes given object handle to stream.
     * <p>
     *  将给定对象句柄写入流。
     * 
     */
    private void writeHandle(int handle) throws IOException {
        bout.writeByte(TC_REFERENCE);
        bout.writeInt(baseWireHandle + handle);
    }

    /**
     * Writes representation of given class to stream.
     * <p>
     *  将给定类的表示写入流。
     * 
     */
    private void writeClass(Class<?> cl, boolean unshared) throws IOException {
        bout.writeByte(TC_CLASS);
        writeClassDesc(ObjectStreamClass.lookup(cl, true), false);
        handles.assign(unshared ? null : cl);
    }

    /**
     * Writes representation of given class descriptor to stream.
     * <p>
     *  将给定类描述符的表示写入流。
     * 
     */
    private void writeClassDesc(ObjectStreamClass desc, boolean unshared)
        throws IOException
    {
        int handle;
        if (desc == null) {
            writeNull();
        } else if (!unshared && (handle = handles.lookup(desc)) != -1) {
            writeHandle(handle);
        } else if (desc.isProxy()) {
            writeProxyDesc(desc, unshared);
        } else {
            writeNonProxyDesc(desc, unshared);
        }
    }

    private boolean isCustomSubclass() {
        // Return true if this class is a custom subclass of ObjectOutputStream
        return getClass().getClassLoader()
                   != ObjectOutputStream.class.getClassLoader();
    }

    /**
     * Writes class descriptor representing a dynamic proxy class to stream.
     * <p>
     *  将表示动态代理类的类描述符写入流。
     * 
     */
    private void writeProxyDesc(ObjectStreamClass desc, boolean unshared)
        throws IOException
    {
        bout.writeByte(TC_PROXYCLASSDESC);
        handles.assign(unshared ? null : desc);

        Class<?> cl = desc.forClass();
        Class<?>[] ifaces = cl.getInterfaces();
        bout.writeInt(ifaces.length);
        for (int i = 0; i < ifaces.length; i++) {
            bout.writeUTF(ifaces[i].getName());
        }

        bout.setBlockDataMode(true);
        if (cl != null && isCustomSubclass()) {
            ReflectUtil.checkPackageAccess(cl);
        }
        annotateProxyClass(cl);
        bout.setBlockDataMode(false);
        bout.writeByte(TC_ENDBLOCKDATA);

        writeClassDesc(desc.getSuperDesc(), false);
    }

    /**
     * Writes class descriptor representing a standard (i.e., not a dynamic
     * proxy) class to stream.
     * <p>
     *  将表示标准(即,不是动态代理)类的类描述符写入流。
     * 
     */
    private void writeNonProxyDesc(ObjectStreamClass desc, boolean unshared)
        throws IOException
    {
        bout.writeByte(TC_CLASSDESC);
        handles.assign(unshared ? null : desc);

        if (protocol == PROTOCOL_VERSION_1) {
            // do not invoke class descriptor write hook with old protocol
            desc.writeNonProxy(this);
        } else {
            writeClassDescriptor(desc);
        }

        Class<?> cl = desc.forClass();
        bout.setBlockDataMode(true);
        if (cl != null && isCustomSubclass()) {
            ReflectUtil.checkPackageAccess(cl);
        }
        annotateClass(cl);
        bout.setBlockDataMode(false);
        bout.writeByte(TC_ENDBLOCKDATA);

        writeClassDesc(desc.getSuperDesc(), false);
    }

    /**
     * Writes given string to stream, using standard or long UTF format
     * depending on string length.
     * <p>
     *  将给定字符串写入流,根据字符串长度使用标准或长UTF格式。
     * 
     */
    private void writeString(String str, boolean unshared) throws IOException {
        handles.assign(unshared ? null : str);
        long utflen = bout.getUTFLength(str);
        if (utflen <= 0xFFFF) {
            bout.writeByte(TC_STRING);
            bout.writeUTF(str, utflen);
        } else {
            bout.writeByte(TC_LONGSTRING);
            bout.writeLongUTF(str, utflen);
        }
    }

    /**
     * Writes given array object to stream.
     * <p>
     *  将给定的数组对象写入流。
     * 
     */
    private void writeArray(Object array,
                            ObjectStreamClass desc,
                            boolean unshared)
        throws IOException
    {
        bout.writeByte(TC_ARRAY);
        writeClassDesc(desc, false);
        handles.assign(unshared ? null : array);

        Class<?> ccl = desc.forClass().getComponentType();
        if (ccl.isPrimitive()) {
            if (ccl == Integer.TYPE) {
                int[] ia = (int[]) array;
                bout.writeInt(ia.length);
                bout.writeInts(ia, 0, ia.length);
            } else if (ccl == Byte.TYPE) {
                byte[] ba = (byte[]) array;
                bout.writeInt(ba.length);
                bout.write(ba, 0, ba.length, true);
            } else if (ccl == Long.TYPE) {
                long[] ja = (long[]) array;
                bout.writeInt(ja.length);
                bout.writeLongs(ja, 0, ja.length);
            } else if (ccl == Float.TYPE) {
                float[] fa = (float[]) array;
                bout.writeInt(fa.length);
                bout.writeFloats(fa, 0, fa.length);
            } else if (ccl == Double.TYPE) {
                double[] da = (double[]) array;
                bout.writeInt(da.length);
                bout.writeDoubles(da, 0, da.length);
            } else if (ccl == Short.TYPE) {
                short[] sa = (short[]) array;
                bout.writeInt(sa.length);
                bout.writeShorts(sa, 0, sa.length);
            } else if (ccl == Character.TYPE) {
                char[] ca = (char[]) array;
                bout.writeInt(ca.length);
                bout.writeChars(ca, 0, ca.length);
            } else if (ccl == Boolean.TYPE) {
                boolean[] za = (boolean[]) array;
                bout.writeInt(za.length);
                bout.writeBooleans(za, 0, za.length);
            } else {
                throw new InternalError();
            }
        } else {
            Object[] objs = (Object[]) array;
            int len = objs.length;
            bout.writeInt(len);
            if (extendedDebugInfo) {
                debugInfoStack.push(
                    "array (class \"" + array.getClass().getName() +
                    "\", size: " + len  + ")");
            }
            try {
                for (int i = 0; i < len; i++) {
                    if (extendedDebugInfo) {
                        debugInfoStack.push(
                            "element of array (index: " + i + ")");
                    }
                    try {
                        writeObject0(objs[i], false);
                    } finally {
                        if (extendedDebugInfo) {
                            debugInfoStack.pop();
                        }
                    }
                }
            } finally {
                if (extendedDebugInfo) {
                    debugInfoStack.pop();
                }
            }
        }
    }

    /**
     * Writes given enum constant to stream.
     * <p>
     *  将给定的枚举常量写入流。
     * 
     */
    private void writeEnum(Enum<?> en,
                           ObjectStreamClass desc,
                           boolean unshared)
        throws IOException
    {
        bout.writeByte(TC_ENUM);
        ObjectStreamClass sdesc = desc.getSuperDesc();
        writeClassDesc((sdesc.forClass() == Enum.class) ? desc : sdesc, false);
        handles.assign(unshared ? null : en);
        writeString(en.name(), false);
    }

    /**
     * Writes representation of a "ordinary" (i.e., not a String, Class,
     * ObjectStreamClass, array, or enum constant) serializable object to the
     * stream.
     * <p>
     *  将"普通"(即,不是String,Class,ObjectStreamClass,数组或枚举常量)可序列化对象的表示写入流。
     * 
     */
    private void writeOrdinaryObject(Object obj,
                                     ObjectStreamClass desc,
                                     boolean unshared)
        throws IOException
    {
        if (extendedDebugInfo) {
            debugInfoStack.push(
                (depth == 1 ? "root " : "") + "object (class \"" +
                obj.getClass().getName() + "\", " + obj.toString() + ")");
        }
        try {
            desc.checkSerialize();

            bout.writeByte(TC_OBJECT);
            writeClassDesc(desc, false);
            handles.assign(unshared ? null : obj);
            if (desc.isExternalizable() && !desc.isProxy()) {
                writeExternalData((Externalizable) obj);
            } else {
                writeSerialData(obj, desc);
            }
        } finally {
            if (extendedDebugInfo) {
                debugInfoStack.pop();
            }
        }
    }

    /**
     * Writes externalizable data of given object by invoking its
     * writeExternal() method.
     * <p>
     * 通过调用writeExternal()方法来写给定对象的可外化数据。
     * 
     */
    private void writeExternalData(Externalizable obj) throws IOException {
        PutFieldImpl oldPut = curPut;
        curPut = null;

        if (extendedDebugInfo) {
            debugInfoStack.push("writeExternal data");
        }
        SerialCallbackContext oldContext = curContext;
        try {
            curContext = null;
            if (protocol == PROTOCOL_VERSION_1) {
                obj.writeExternal(this);
            } else {
                bout.setBlockDataMode(true);
                obj.writeExternal(this);
                bout.setBlockDataMode(false);
                bout.writeByte(TC_ENDBLOCKDATA);
            }
        } finally {
            curContext = oldContext;
            if (extendedDebugInfo) {
                debugInfoStack.pop();
            }
        }

        curPut = oldPut;
    }

    /**
     * Writes instance data for each serializable class of given object, from
     * superclass to subclass.
     * <p>
     *  为给定对象的每个可序列化类编写实例数据,从超类到子类。
     * 
     */
    private void writeSerialData(Object obj, ObjectStreamClass desc)
        throws IOException
    {
        ObjectStreamClass.ClassDataSlot[] slots = desc.getClassDataLayout();
        for (int i = 0; i < slots.length; i++) {
            ObjectStreamClass slotDesc = slots[i].desc;
            if (slotDesc.hasWriteObjectMethod()) {
                PutFieldImpl oldPut = curPut;
                curPut = null;
                SerialCallbackContext oldContext = curContext;

                if (extendedDebugInfo) {
                    debugInfoStack.push(
                        "custom writeObject data (class \"" +
                        slotDesc.getName() + "\")");
                }
                try {
                    curContext = new SerialCallbackContext(obj, slotDesc);
                    bout.setBlockDataMode(true);
                    slotDesc.invokeWriteObject(obj, this);
                    bout.setBlockDataMode(false);
                    bout.writeByte(TC_ENDBLOCKDATA);
                } finally {
                    curContext.setUsed();
                    curContext = oldContext;
                    if (extendedDebugInfo) {
                        debugInfoStack.pop();
                    }
                }

                curPut = oldPut;
            } else {
                defaultWriteFields(obj, slotDesc);
            }
        }
    }

    /**
     * Fetches and writes values of serializable fields of given object to
     * stream.  The given class descriptor specifies which field values to
     * write, and in which order they should be written.
     * <p>
     *  获取和写入给定对象的可序列化字段的值到流。给定的类描述符指定要写入的字段值,以及它们应该以什么顺序写入。
     * 
     */
    private void defaultWriteFields(Object obj, ObjectStreamClass desc)
        throws IOException
    {
        Class<?> cl = desc.forClass();
        if (cl != null && obj != null && !cl.isInstance(obj)) {
            throw new ClassCastException();
        }

        desc.checkDefaultSerialize();

        int primDataSize = desc.getPrimDataSize();
        if (primVals == null || primVals.length < primDataSize) {
            primVals = new byte[primDataSize];
        }
        desc.getPrimFieldValues(obj, primVals);
        bout.write(primVals, 0, primDataSize, false);

        ObjectStreamField[] fields = desc.getFields(false);
        Object[] objVals = new Object[desc.getNumObjFields()];
        int numPrimFields = fields.length - objVals.length;
        desc.getObjFieldValues(obj, objVals);
        for (int i = 0; i < objVals.length; i++) {
            if (extendedDebugInfo) {
                debugInfoStack.push(
                    "field (class \"" + desc.getName() + "\", name: \"" +
                    fields[numPrimFields + i].getName() + "\", type: \"" +
                    fields[numPrimFields + i].getType() + "\")");
            }
            try {
                writeObject0(objVals[i],
                             fields[numPrimFields + i].isUnshared());
            } finally {
                if (extendedDebugInfo) {
                    debugInfoStack.pop();
                }
            }
        }
    }

    /**
     * Attempts to write to stream fatal IOException that has caused
     * serialization to abort.
     * <p>
     *  尝试写入导致序列化中止的流致命IOException。
     * 
     */
    private void writeFatalException(IOException ex) throws IOException {
        /*
         * Note: the serialization specification states that if a second
         * IOException occurs while attempting to serialize the original fatal
         * exception to the stream, then a StreamCorruptedException should be
         * thrown (section 2.1).  However, due to a bug in previous
         * implementations of serialization, StreamCorruptedExceptions were
         * rarely (if ever) actually thrown--the "root" exceptions from
         * underlying streams were thrown instead.  This historical behavior is
         * followed here for consistency.
         * <p>
         *  注意：序列化规范说明如果在尝试将原始致命异常序列化到流时发生第二个IOException,则应抛出StreamCorruptedException(第2.1节)。
         * 然而,由于以前的序列化实现中的错误,StreamCorruptedExceptions很少(如果有)实际抛出 - 来自底层流的"根"异常被抛出。这里的历史行为是为了一致性。
         * 
         */
        clear();
        boolean oldMode = bout.setBlockDataMode(false);
        try {
            bout.writeByte(TC_EXCEPTION);
            writeObject0(ex, false);
            clear();
        } finally {
            bout.setBlockDataMode(oldMode);
        }
    }

    /**
     * Converts specified span of float values into byte values.
     * <p>
     *  将指定的浮点值范围转换为字节值。
     * 
     */
    // REMIND: remove once hotspot inlines Float.floatToIntBits
    private static native void floatsToBytes(float[] src, int srcpos,
                                             byte[] dst, int dstpos,
                                             int nfloats);

    /**
     * Converts specified span of double values into byte values.
     * <p>
     *  将指定的双精度跨度值转换为字节值。
     * 
     */
    // REMIND: remove once hotspot inlines Double.doubleToLongBits
    private static native void doublesToBytes(double[] src, int srcpos,
                                              byte[] dst, int dstpos,
                                              int ndoubles);

    /**
     * Default PutField implementation.
     * <p>
     *  默认PutField实现。
     * 
     */
    private class PutFieldImpl extends PutField {

        /** class descriptor describing serializable fields */
        private final ObjectStreamClass desc;
        /** primitive field values */
        private final byte[] primVals;
        /** object field values */
        private final Object[] objVals;

        /**
         * Creates PutFieldImpl object for writing fields defined in given
         * class descriptor.
         * <p>
         *  创建用于写入在给定类描述符中定义的字段的PutFieldImpl对象。
         * 
         */
        PutFieldImpl(ObjectStreamClass desc) {
            this.desc = desc;
            primVals = new byte[desc.getPrimDataSize()];
            objVals = new Object[desc.getNumObjFields()];
        }

        public void put(String name, boolean val) {
            Bits.putBoolean(primVals, getFieldOffset(name, Boolean.TYPE), val);
        }

        public void put(String name, byte val) {
            primVals[getFieldOffset(name, Byte.TYPE)] = val;
        }

        public void put(String name, char val) {
            Bits.putChar(primVals, getFieldOffset(name, Character.TYPE), val);
        }

        public void put(String name, short val) {
            Bits.putShort(primVals, getFieldOffset(name, Short.TYPE), val);
        }

        public void put(String name, int val) {
            Bits.putInt(primVals, getFieldOffset(name, Integer.TYPE), val);
        }

        public void put(String name, float val) {
            Bits.putFloat(primVals, getFieldOffset(name, Float.TYPE), val);
        }

        public void put(String name, long val) {
            Bits.putLong(primVals, getFieldOffset(name, Long.TYPE), val);
        }

        public void put(String name, double val) {
            Bits.putDouble(primVals, getFieldOffset(name, Double.TYPE), val);
        }

        public void put(String name, Object val) {
            objVals[getFieldOffset(name, Object.class)] = val;
        }

        // deprecated in ObjectOutputStream.PutField
        public void write(ObjectOutput out) throws IOException {
            /*
             * Applications should *not* use this method to write PutField
             * data, as it will lead to stream corruption if the PutField
             * object writes any primitive data (since block data mode is not
             * unset/set properly, as is done in OOS.writeFields()).  This
             * broken implementation is being retained solely for behavioral
             * compatibility, in order to support applications which use
             * OOS.PutField.write() for writing only non-primitive data.
             *
             * Serialization of unshared objects is not implemented here since
             * it is not necessary for backwards compatibility; also, unshared
             * semantics may not be supported by the given ObjectOutput
             * instance.  Applications which write unshared objects using the
             * PutField API must use OOS.writeFields().
             * <p>
             * 应用程序应该*不使用此方法来写入PutField数据,因为如果PutField对象写入任何原始数据(因为块数据模式未正确设置/设置,如OOS.writeFields()中所做的那样),它将导致流损坏。
             *  。这个破坏的实现仅仅为了行为兼容性而被保留,以便支持使用OOS.PutField.write()仅写入非原始数据的应用程序。
             * 
             *  非共享对象的序列化在这里不实现,因为它不是向后兼容性所必需的;此外,给定的ObjectOutput实例可能不支持非共享语义。
             * 使用PutField API写非共享对象的应用程序必须使用OOS.writeFields()。
             * 
             */
            if (ObjectOutputStream.this != out) {
                throw new IllegalArgumentException("wrong stream");
            }
            out.write(primVals, 0, primVals.length);

            ObjectStreamField[] fields = desc.getFields(false);
            int numPrimFields = fields.length - objVals.length;
            // REMIND: warn if numPrimFields > 0?
            for (int i = 0; i < objVals.length; i++) {
                if (fields[numPrimFields + i].isUnshared()) {
                    throw new IOException("cannot write unshared object");
                }
                out.writeObject(objVals[i]);
            }
        }

        /**
         * Writes buffered primitive data and object fields to stream.
         * <p>
         *  将缓冲的基本数据和对象字段写入流。
         * 
         */
        void writeFields() throws IOException {
            bout.write(primVals, 0, primVals.length, false);

            ObjectStreamField[] fields = desc.getFields(false);
            int numPrimFields = fields.length - objVals.length;
            for (int i = 0; i < objVals.length; i++) {
                if (extendedDebugInfo) {
                    debugInfoStack.push(
                        "field (class \"" + desc.getName() + "\", name: \"" +
                        fields[numPrimFields + i].getName() + "\", type: \"" +
                        fields[numPrimFields + i].getType() + "\")");
                }
                try {
                    writeObject0(objVals[i],
                                 fields[numPrimFields + i].isUnshared());
                } finally {
                    if (extendedDebugInfo) {
                        debugInfoStack.pop();
                    }
                }
            }
        }

        /**
         * Returns offset of field with given name and type.  A specified type
         * of null matches all types, Object.class matches all non-primitive
         * types, and any other non-null type matches assignable types only.
         * Throws IllegalArgumentException if no matching field found.
         * <p>
         *  返回具有给定名称和类型的字段的偏移量。指定的null类型匹配所有类型,Object.class匹配所有非基本类型,任何其他非null类型仅匹配可分配类型。
         * 如果找不到匹配字段,则抛出IllegalArgumentException。
         * 
         */
        private int getFieldOffset(String name, Class<?> type) {
            ObjectStreamField field = desc.getField(name, type);
            if (field == null) {
                throw new IllegalArgumentException("no such field " + name +
                                                   " with type " + type);
            }
            return field.getOffset();
        }
    }

    /**
     * Buffered output stream with two modes: in default mode, outputs data in
     * same format as DataOutputStream; in "block data" mode, outputs data
     * bracketed by block data markers (see object serialization specification
     * for details).
     * <p>
     *  缓冲输出流有两种模式：在默认模式下,输出格式与DataOutputStream相同的数据;在"块数据"模式下,输出块数据标记括起的数据(有关详细信息,请参阅对象序列化规范)。
     * 
     */
    private static class BlockDataOutputStream
        extends OutputStream implements DataOutput
    {
        /** maximum data block length */
        private static final int MAX_BLOCK_SIZE = 1024;
        /** maximum data block header length */
        private static final int MAX_HEADER_SIZE = 5;
        /** (tunable) length of char buffer (for writing strings) */
        private static final int CHAR_BUF_SIZE = 256;

        /** buffer for writing general/block data */
        private final byte[] buf = new byte[MAX_BLOCK_SIZE];
        /** buffer for writing block data headers */
        private final byte[] hbuf = new byte[MAX_HEADER_SIZE];
        /** char buffer for fast string writes */
        private final char[] cbuf = new char[CHAR_BUF_SIZE];

        /** block data mode */
        private boolean blkmode = false;
        /** current offset into buf */
        private int pos = 0;

        /** underlying output stream */
        private final OutputStream out;
        /** loopback stream (for data writes that span data blocks) */
        private final DataOutputStream dout;

        /**
         * Creates new BlockDataOutputStream on top of given underlying stream.
         * Block data mode is turned off by default.
         * <p>
         *  在给定的底层流之上创建新的BlockDataOutputStream。块数据模式默认关闭。
         * 
         */
        BlockDataOutputStream(OutputStream out) {
            this.out = out;
            dout = new DataOutputStream(this);
        }

        /**
         * Sets block data mode to the given mode (true == on, false == off)
         * and returns the previous mode value.  If the new mode is the same as
         * the old mode, no action is taken.  If the new mode differs from the
         * old mode, any buffered data is flushed before switching to the new
         * mode.
         * <p>
         * 将块数据模式设置为给定模式(true == on,false == off),并返回上一个模式值。如果新模式与旧模式相同,则不采取任何操作。
         * 如果新模式与旧模式不同,则在切换到新模式之前刷新任何缓冲的数据。
         * 
         */
        boolean setBlockDataMode(boolean mode) throws IOException {
            if (blkmode == mode) {
                return blkmode;
            }
            drain();
            blkmode = mode;
            return !blkmode;
        }

        /**
         * Returns true if the stream is currently in block data mode, false
         * otherwise.
         * <p>
         *  如果流当前处于块数据模式,则返回true,否则返回false。
         * 
         */
        boolean getBlockDataMode() {
            return blkmode;
        }

        /* ----------------- generic output stream methods ----------------- */
        /*
         * The following methods are equivalent to their counterparts in
         * OutputStream, except that they partition written data into data
         * blocks when in block data mode.
         * <p>
         *  以下方法等效于它们在OutputStream中的对应方法,除了在块数据模式下将写入数据分区为数据块。
         * 
         */

        public void write(int b) throws IOException {
            if (pos >= MAX_BLOCK_SIZE) {
                drain();
            }
            buf[pos++] = (byte) b;
        }

        public void write(byte[] b) throws IOException {
            write(b, 0, b.length, false);
        }

        public void write(byte[] b, int off, int len) throws IOException {
            write(b, off, len, false);
        }

        public void flush() throws IOException {
            drain();
            out.flush();
        }

        public void close() throws IOException {
            flush();
            out.close();
        }

        /**
         * Writes specified span of byte values from given array.  If copy is
         * true, copies the values to an intermediate buffer before writing
         * them to underlying stream (to avoid exposing a reference to the
         * original byte array).
         * <p>
         *  从给定数组写入指定的字节值跨度。如果copy为true,则在将它们写入底层流之前将这些值复制到中间缓冲区(以避免将引用暴露给原始字节数组)。
         * 
         */
        void write(byte[] b, int off, int len, boolean copy)
            throws IOException
        {
            if (!(copy || blkmode)) {           // write directly
                drain();
                out.write(b, off, len);
                return;
            }

            while (len > 0) {
                if (pos >= MAX_BLOCK_SIZE) {
                    drain();
                }
                if (len >= MAX_BLOCK_SIZE && !copy && pos == 0) {
                    // avoid unnecessary copy
                    writeBlockHeader(MAX_BLOCK_SIZE);
                    out.write(b, off, MAX_BLOCK_SIZE);
                    off += MAX_BLOCK_SIZE;
                    len -= MAX_BLOCK_SIZE;
                } else {
                    int wlen = Math.min(len, MAX_BLOCK_SIZE - pos);
                    System.arraycopy(b, off, buf, pos, wlen);
                    pos += wlen;
                    off += wlen;
                    len -= wlen;
                }
            }
        }

        /**
         * Writes all buffered data from this stream to the underlying stream,
         * but does not flush underlying stream.
         * <p>
         *  将来自此流的所有缓冲数据写入基础流,但不刷新基础流。
         * 
         */
        void drain() throws IOException {
            if (pos == 0) {
                return;
            }
            if (blkmode) {
                writeBlockHeader(pos);
            }
            out.write(buf, 0, pos);
            pos = 0;
        }

        /**
         * Writes block data header.  Data blocks shorter than 256 bytes are
         * prefixed with a 2-byte header; all others start with a 5-byte
         * header.
         * <p>
         *  写入块数据头。短于256字节的数据块以2字节的报头作为前缀;所有其他的都从5字节的头开始。
         * 
         */
        private void writeBlockHeader(int len) throws IOException {
            if (len <= 0xFF) {
                hbuf[0] = TC_BLOCKDATA;
                hbuf[1] = (byte) len;
                out.write(hbuf, 0, 2);
            } else {
                hbuf[0] = TC_BLOCKDATALONG;
                Bits.putInt(hbuf, 1, len);
                out.write(hbuf, 0, 5);
            }
        }


        /* ----------------- primitive data output methods ----------------- */
        /*
         * The following methods are equivalent to their counterparts in
         * DataOutputStream, except that they partition written data into data
         * blocks when in block data mode.
         * <p>
         *  以下方法等效于DataOutputStream中的对应方法,除了在块数据模式下将写入数据分区为数据块。
         * 
         */

        public void writeBoolean(boolean v) throws IOException {
            if (pos >= MAX_BLOCK_SIZE) {
                drain();
            }
            Bits.putBoolean(buf, pos++, v);
        }

        public void writeByte(int v) throws IOException {
            if (pos >= MAX_BLOCK_SIZE) {
                drain();
            }
            buf[pos++] = (byte) v;
        }

        public void writeChar(int v) throws IOException {
            if (pos + 2 <= MAX_BLOCK_SIZE) {
                Bits.putChar(buf, pos, (char) v);
                pos += 2;
            } else {
                dout.writeChar(v);
            }
        }

        public void writeShort(int v) throws IOException {
            if (pos + 2 <= MAX_BLOCK_SIZE) {
                Bits.putShort(buf, pos, (short) v);
                pos += 2;
            } else {
                dout.writeShort(v);
            }
        }

        public void writeInt(int v) throws IOException {
            if (pos + 4 <= MAX_BLOCK_SIZE) {
                Bits.putInt(buf, pos, v);
                pos += 4;
            } else {
                dout.writeInt(v);
            }
        }

        public void writeFloat(float v) throws IOException {
            if (pos + 4 <= MAX_BLOCK_SIZE) {
                Bits.putFloat(buf, pos, v);
                pos += 4;
            } else {
                dout.writeFloat(v);
            }
        }

        public void writeLong(long v) throws IOException {
            if (pos + 8 <= MAX_BLOCK_SIZE) {
                Bits.putLong(buf, pos, v);
                pos += 8;
            } else {
                dout.writeLong(v);
            }
        }

        public void writeDouble(double v) throws IOException {
            if (pos + 8 <= MAX_BLOCK_SIZE) {
                Bits.putDouble(buf, pos, v);
                pos += 8;
            } else {
                dout.writeDouble(v);
            }
        }

        public void writeBytes(String s) throws IOException {
            int endoff = s.length();
            int cpos = 0;
            int csize = 0;
            for (int off = 0; off < endoff; ) {
                if (cpos >= csize) {
                    cpos = 0;
                    csize = Math.min(endoff - off, CHAR_BUF_SIZE);
                    s.getChars(off, off + csize, cbuf, 0);
                }
                if (pos >= MAX_BLOCK_SIZE) {
                    drain();
                }
                int n = Math.min(csize - cpos, MAX_BLOCK_SIZE - pos);
                int stop = pos + n;
                while (pos < stop) {
                    buf[pos++] = (byte) cbuf[cpos++];
                }
                off += n;
            }
        }

        public void writeChars(String s) throws IOException {
            int endoff = s.length();
            for (int off = 0; off < endoff; ) {
                int csize = Math.min(endoff - off, CHAR_BUF_SIZE);
                s.getChars(off, off + csize, cbuf, 0);
                writeChars(cbuf, 0, csize);
                off += csize;
            }
        }

        public void writeUTF(String s) throws IOException {
            writeUTF(s, getUTFLength(s));
        }


        /* -------------- primitive data array output methods -------------- */
        /*
         * The following methods write out spans of primitive data values.
         * Though equivalent to calling the corresponding primitive write
         * methods repeatedly, these methods are optimized for writing groups
         * of primitive data values more efficiently.
         * <p>
         *  以下方法写出原始数据值的范围。虽然等效于重复调用相应的基本写入方法,但是这些方法被优化用于更有效地写入基本数据值的组。
         * 
         */

        void writeBooleans(boolean[] v, int off, int len) throws IOException {
            int endoff = off + len;
            while (off < endoff) {
                if (pos >= MAX_BLOCK_SIZE) {
                    drain();
                }
                int stop = Math.min(endoff, off + (MAX_BLOCK_SIZE - pos));
                while (off < stop) {
                    Bits.putBoolean(buf, pos++, v[off++]);
                }
            }
        }

        void writeChars(char[] v, int off, int len) throws IOException {
            int limit = MAX_BLOCK_SIZE - 2;
            int endoff = off + len;
            while (off < endoff) {
                if (pos <= limit) {
                    int avail = (MAX_BLOCK_SIZE - pos) >> 1;
                    int stop = Math.min(endoff, off + avail);
                    while (off < stop) {
                        Bits.putChar(buf, pos, v[off++]);
                        pos += 2;
                    }
                } else {
                    dout.writeChar(v[off++]);
                }
            }
        }

        void writeShorts(short[] v, int off, int len) throws IOException {
            int limit = MAX_BLOCK_SIZE - 2;
            int endoff = off + len;
            while (off < endoff) {
                if (pos <= limit) {
                    int avail = (MAX_BLOCK_SIZE - pos) >> 1;
                    int stop = Math.min(endoff, off + avail);
                    while (off < stop) {
                        Bits.putShort(buf, pos, v[off++]);
                        pos += 2;
                    }
                } else {
                    dout.writeShort(v[off++]);
                }
            }
        }

        void writeInts(int[] v, int off, int len) throws IOException {
            int limit = MAX_BLOCK_SIZE - 4;
            int endoff = off + len;
            while (off < endoff) {
                if (pos <= limit) {
                    int avail = (MAX_BLOCK_SIZE - pos) >> 2;
                    int stop = Math.min(endoff, off + avail);
                    while (off < stop) {
                        Bits.putInt(buf, pos, v[off++]);
                        pos += 4;
                    }
                } else {
                    dout.writeInt(v[off++]);
                }
            }
        }

        void writeFloats(float[] v, int off, int len) throws IOException {
            int limit = MAX_BLOCK_SIZE - 4;
            int endoff = off + len;
            while (off < endoff) {
                if (pos <= limit) {
                    int avail = (MAX_BLOCK_SIZE - pos) >> 2;
                    int chunklen = Math.min(endoff - off, avail);
                    floatsToBytes(v, off, buf, pos, chunklen);
                    off += chunklen;
                    pos += chunklen << 2;
                } else {
                    dout.writeFloat(v[off++]);
                }
            }
        }

        void writeLongs(long[] v, int off, int len) throws IOException {
            int limit = MAX_BLOCK_SIZE - 8;
            int endoff = off + len;
            while (off < endoff) {
                if (pos <= limit) {
                    int avail = (MAX_BLOCK_SIZE - pos) >> 3;
                    int stop = Math.min(endoff, off + avail);
                    while (off < stop) {
                        Bits.putLong(buf, pos, v[off++]);
                        pos += 8;
                    }
                } else {
                    dout.writeLong(v[off++]);
                }
            }
        }

        void writeDoubles(double[] v, int off, int len) throws IOException {
            int limit = MAX_BLOCK_SIZE - 8;
            int endoff = off + len;
            while (off < endoff) {
                if (pos <= limit) {
                    int avail = (MAX_BLOCK_SIZE - pos) >> 3;
                    int chunklen = Math.min(endoff - off, avail);
                    doublesToBytes(v, off, buf, pos, chunklen);
                    off += chunklen;
                    pos += chunklen << 3;
                } else {
                    dout.writeDouble(v[off++]);
                }
            }
        }

        /**
         * Returns the length in bytes of the UTF encoding of the given string.
         * <p>
         *  返回给定字符串的UTF编码的长度(以字节为单位)。
         * 
         */
        long getUTFLength(String s) {
            int len = s.length();
            long utflen = 0;
            for (int off = 0; off < len; ) {
                int csize = Math.min(len - off, CHAR_BUF_SIZE);
                s.getChars(off, off + csize, cbuf, 0);
                for (int cpos = 0; cpos < csize; cpos++) {
                    char c = cbuf[cpos];
                    if (c >= 0x0001 && c <= 0x007F) {
                        utflen++;
                    } else if (c > 0x07FF) {
                        utflen += 3;
                    } else {
                        utflen += 2;
                    }
                }
                off += csize;
            }
            return utflen;
        }

        /**
         * Writes the given string in UTF format.  This method is used in
         * situations where the UTF encoding length of the string is already
         * known; specifying it explicitly avoids a prescan of the string to
         * determine its UTF length.
         * <p>
         * 以UTF格式写入给定字符串。此方法用于已知字符串的UTF编码长度的情况;指定它明确地避免字符串的预扫描确定其UTF长度。
         * 
         */
        void writeUTF(String s, long utflen) throws IOException {
            if (utflen > 0xFFFFL) {
                throw new UTFDataFormatException();
            }
            writeShort((int) utflen);
            if (utflen == (long) s.length()) {
                writeBytes(s);
            } else {
                writeUTFBody(s);
            }
        }

        /**
         * Writes given string in "long" UTF format.  "Long" UTF format is
         * identical to standard UTF, except that it uses an 8 byte header
         * (instead of the standard 2 bytes) to convey the UTF encoding length.
         * <p>
         *  以"long"UTF格式写入给定字符串。 "长"UTF格式与标准UTF相同,除了它使用8字节报头(而不是标准的2字节)来传达UTF编码长度。
         * 
         */
        void writeLongUTF(String s) throws IOException {
            writeLongUTF(s, getUTFLength(s));
        }

        /**
         * Writes given string in "long" UTF format, where the UTF encoding
         * length of the string is already known.
         * <p>
         *  以"long"UTF格式写入给定字符串,其中字符串的UTF编码长度是已知的。
         * 
         */
        void writeLongUTF(String s, long utflen) throws IOException {
            writeLong(utflen);
            if (utflen == (long) s.length()) {
                writeBytes(s);
            } else {
                writeUTFBody(s);
            }
        }

        /**
         * Writes the "body" (i.e., the UTF representation minus the 2-byte or
         * 8-byte length header) of the UTF encoding for the given string.
         * <p>
         *  对给定字符串写入UTF编码的"正文"(即,UTF表示减去2字节或8字节长度头)。
         * 
         */
        private void writeUTFBody(String s) throws IOException {
            int limit = MAX_BLOCK_SIZE - 3;
            int len = s.length();
            for (int off = 0; off < len; ) {
                int csize = Math.min(len - off, CHAR_BUF_SIZE);
                s.getChars(off, off + csize, cbuf, 0);
                for (int cpos = 0; cpos < csize; cpos++) {
                    char c = cbuf[cpos];
                    if (pos <= limit) {
                        if (c <= 0x007F && c != 0) {
                            buf[pos++] = (byte) c;
                        } else if (c > 0x07FF) {
                            buf[pos + 2] = (byte) (0x80 | ((c >> 0) & 0x3F));
                            buf[pos + 1] = (byte) (0x80 | ((c >> 6) & 0x3F));
                            buf[pos + 0] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                            pos += 3;
                        } else {
                            buf[pos + 1] = (byte) (0x80 | ((c >> 0) & 0x3F));
                            buf[pos + 0] = (byte) (0xC0 | ((c >> 6) & 0x1F));
                            pos += 2;
                        }
                    } else {    // write one byte at a time to normalize block
                        if (c <= 0x007F && c != 0) {
                            write(c);
                        } else if (c > 0x07FF) {
                            write(0xE0 | ((c >> 12) & 0x0F));
                            write(0x80 | ((c >> 6) & 0x3F));
                            write(0x80 | ((c >> 0) & 0x3F));
                        } else {
                            write(0xC0 | ((c >> 6) & 0x1F));
                            write(0x80 | ((c >> 0) & 0x3F));
                        }
                    }
                }
                off += csize;
            }
        }
    }

    /**
     * Lightweight identity hash table which maps objects to integer handles,
     * assigned in ascending order.
     * <p>
     *  轻量级身份哈希表,将对象映射到整数句柄,以升序分配。
     * 
     */
    private static class HandleTable {

        /* number of mappings in table/next available handle */
        private int size;
        /* size threshold determining when to expand hash spine */
        private int threshold;
        /* factor for computing size threshold */
        private final float loadFactor;
        /* maps hash value -> candidate handle value */
        private int[] spine;
        /* maps handle value -> next candidate handle value */
        private int[] next;
        /* maps handle value -> associated object */
        private Object[] objs;

        /**
         * Creates new HandleTable with given capacity and load factor.
         * <p>
         *  创建具有给定容量和负载系数的新HandleTable。
         * 
         */
        HandleTable(int initialCapacity, float loadFactor) {
            this.loadFactor = loadFactor;
            spine = new int[initialCapacity];
            next = new int[initialCapacity];
            objs = new Object[initialCapacity];
            threshold = (int) (initialCapacity * loadFactor);
            clear();
        }

        /**
         * Assigns next available handle to given object, and returns handle
         * value.  Handles are assigned in ascending order starting at 0.
         * <p>
         *  将下一个可用句柄分配给给定对象,并返回句柄值。把手从0开始按升序分配。
         * 
         */
        int assign(Object obj) {
            if (size >= next.length) {
                growEntries();
            }
            if (size >= threshold) {
                growSpine();
            }
            insert(obj, size);
            return size++;
        }

        /**
         * Looks up and returns handle associated with given object, or -1 if
         * no mapping found.
         * <p>
         *  查找并返回与给定对象关联的句柄,如果未找到映射,则返回-1。
         * 
         */
        int lookup(Object obj) {
            if (size == 0) {
                return -1;
            }
            int index = hash(obj) % spine.length;
            for (int i = spine[index]; i >= 0; i = next[i]) {
                if (objs[i] == obj) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Resets table to its initial (empty) state.
         * <p>
         *  将表重置为其初始(空)状态。
         * 
         */
        void clear() {
            Arrays.fill(spine, -1);
            Arrays.fill(objs, 0, size, null);
            size = 0;
        }

        /**
         * Returns the number of mappings currently in table.
         * <p>
         *  返回当前在表中的映射数。
         * 
         */
        int size() {
            return size;
        }

        /**
         * Inserts mapping object -> handle mapping into table.  Assumes table
         * is large enough to accommodate new mapping.
         * <p>
         *  插入映射对象 - >句柄映射到表中。假设表足够大以适应新的映射。
         * 
         */
        private void insert(Object obj, int handle) {
            int index = hash(obj) % spine.length;
            objs[handle] = obj;
            next[handle] = spine[index];
            spine[index] = handle;
        }

        /**
         * Expands the hash "spine" -- equivalent to increasing the number of
         * buckets in a conventional hash table.
         * <p>
         *  扩展散列"spine" - 相当于增加常规散列表中的桶数。
         * 
         */
        private void growSpine() {
            spine = new int[(spine.length << 1) + 1];
            threshold = (int) (spine.length * loadFactor);
            Arrays.fill(spine, -1);
            for (int i = 0; i < size; i++) {
                insert(objs[i], i);
            }
        }

        /**
         * Increases hash table capacity by lengthening entry arrays.
         * <p>
         *  通过延长条目数组来增加散列表容量。
         * 
         */
        private void growEntries() {
            int newLength = (next.length << 1) + 1;
            int[] newNext = new int[newLength];
            System.arraycopy(next, 0, newNext, 0, size);
            next = newNext;

            Object[] newObjs = new Object[newLength];
            System.arraycopy(objs, 0, newObjs, 0, size);
            objs = newObjs;
        }

        /**
         * Returns hash value for given object.
         * <p>
         *  返回给定对象的哈希值。
         * 
         */
        private int hash(Object obj) {
            return System.identityHashCode(obj) & 0x7FFFFFFF;
        }
    }

    /**
     * Lightweight identity hash table which maps objects to replacement
     * objects.
     * <p>
     * 轻量级身份哈希表,将对象映射到替换对象。
     * 
     */
    private static class ReplaceTable {

        /* maps object -> index */
        private final HandleTable htab;
        /* maps index -> replacement object */
        private Object[] reps;

        /**
         * Creates new ReplaceTable with given capacity and load factor.
         * <p>
         *  使用给定的容量和负载系数创建新的ReplaceTable。
         * 
         */
        ReplaceTable(int initialCapacity, float loadFactor) {
            htab = new HandleTable(initialCapacity, loadFactor);
            reps = new Object[initialCapacity];
        }

        /**
         * Enters mapping from object to replacement object.
         * <p>
         *  进入从对象到替换对象的映射。
         * 
         */
        void assign(Object obj, Object rep) {
            int index = htab.assign(obj);
            while (index >= reps.length) {
                grow();
            }
            reps[index] = rep;
        }

        /**
         * Looks up and returns replacement for given object.  If no
         * replacement is found, returns the lookup object itself.
         * <p>
         *  查找并返回给定对象的替换。如果未找到替换,则返回查找对象本身。
         * 
         */
        Object lookup(Object obj) {
            int index = htab.lookup(obj);
            return (index >= 0) ? reps[index] : obj;
        }

        /**
         * Resets table to its initial (empty) state.
         * <p>
         *  将表重置为其初始(空)状态。
         * 
         */
        void clear() {
            Arrays.fill(reps, 0, htab.size(), null);
            htab.clear();
        }

        /**
         * Returns the number of mappings currently in table.
         * <p>
         *  返回当前在表中的映射数。
         * 
         */
        int size() {
            return htab.size();
        }

        /**
         * Increases table capacity.
         * <p>
         *  增加表容量。
         * 
         */
        private void grow() {
            Object[] newReps = new Object[(reps.length << 1) + 1];
            System.arraycopy(reps, 0, newReps, 0, reps.length);
            reps = newReps;
        }
    }

    /**
     * Stack to keep debug information about the state of the
     * serialization process, for embedding in exception messages.
     * <p>
     *  堆栈以保持关于序列化过程的状态的调试信息,用于嵌入异常消息中。
     * 
     */
    private static class DebugTraceInfoStack {
        private final List<String> stack;

        DebugTraceInfoStack() {
            stack = new ArrayList<>();
        }

        /**
         * Removes all of the elements from enclosed list.
         * <p>
         *  从封闭列表中删除所有元素。
         * 
         */
        void clear() {
            stack.clear();
        }

        /**
         * Removes the object at the top of enclosed list.
         * <p>
         *  删除包含列表顶部的对象。
         * 
         */
        void pop() {
            stack.remove(stack.size()-1);
        }

        /**
         * Pushes a String onto the top of enclosed list.
         * <p>
         *  将字符串推送到封闭列表的顶部。
         * 
         */
        void push(String entry) {
            stack.add("\t- " + entry);
        }

        /**
         * Returns a string representation of this object
         * <p>
         *  返回此对象的字符串表示形式
         */
        public String toString() {
            StringBuilder buffer = new StringBuilder();
            if (!stack.isEmpty()) {
                for(int i = stack.size(); i > 0; i-- ) {
                    buffer.append(stack.get(i-1) + ((i != 1) ? "\n" : ""));
                }
            }
            return buffer.toString();
        }
    }

}
