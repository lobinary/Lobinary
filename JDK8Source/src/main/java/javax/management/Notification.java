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

package javax.management;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.util.EventObject;

import java.security.AccessController;

import com.sun.jmx.mbeanserver.GetPropertyAction;

/**
 * <p>The Notification class represents a notification emitted by an
 * MBean.  It contains a reference to the source MBean: if the
 * notification has been forwarded through the MBean server, and the
 * original source of the notification was a reference to the emitting
 * MBean object, then the MBean server replaces it by the MBean's
 * ObjectName.  If the listener has registered directly with the
 * MBean, this is either the object name or a direct reference to the
 * MBean.</p>
 *
 * <p>It is strongly recommended that notification senders use the
 * object name rather than a reference to the MBean object as the
 * source.</p>
 *
 * <p>The <b>serialVersionUID</b> of this class is <code>-7516092053498031989L</code>.
 *
 * <p>
 *  <p>通知类表示由MBean发出的通知。
 * 它包含对源MBean的引用：如果通知已通过MBean服务器转发,并且通知的原始源是对发出MBean对象的引用,则MBean服务器将其替换为MBean的ObjectName。
 * 如果侦听器已直接向MBean注册,则这是对象名称或对MBean的直接引用。</p>。
 * 
 *  <p>强烈建议通知发件人使用对象名称而不是对MBean对象的引用作为源。</p>
 * 
 *  <p>此类的<b> serialVersionUID </b>是<code> -7516092053498031989L </code>。
 * 
 * 
 * @since 1.5
 */
@SuppressWarnings("serial")  // serialVersionUID is not constant
public class Notification extends EventObject {

    // Serialization compatibility stuff:
    // Two serial forms are supported in this class. The selected form depends
    // on system property "jmx.serial.form":
    //  - "1.0" for JMX 1.0
    //  - any other value for JMX 1.1 and higher
    //
    // Serial version for old serial form
    private static final long oldSerialVersionUID = 1716977971058914352L;
    //
    // Serial version for new serial form
    private static final long newSerialVersionUID = -7516092053498031989L;
    //
    // Serializable fields in old serial form
    private static final ObjectStreamField[] oldSerialPersistentFields =
    {
        new ObjectStreamField("message", String.class),
        new ObjectStreamField("sequenceNumber", Long.TYPE),
        new ObjectStreamField("source", Object.class),
        new ObjectStreamField("sourceObjectName", ObjectName.class),
        new ObjectStreamField("timeStamp", Long.TYPE),
        new ObjectStreamField("type", String.class),
        new ObjectStreamField("userData", Object.class)
    };
    //
    // Serializable fields in new serial form
    private static final ObjectStreamField[] newSerialPersistentFields =
    {
        new ObjectStreamField("message", String.class),
        new ObjectStreamField("sequenceNumber", Long.TYPE),
        new ObjectStreamField("source", Object.class),
        new ObjectStreamField("timeStamp", Long.TYPE),
        new ObjectStreamField("type", String.class),
        new ObjectStreamField("userData", Object.class)
    };
    //
    // Actual serial version and serial form
    private static final long serialVersionUID;
    /**
    /* <p>
    /* 
     * @serialField type String The notification type.
     *              A string expressed in a dot notation similar to Java properties.
     *              An example of a notification type is network.alarm.router
     * @serialField sequenceNumber long The notification sequence number.
     *              A serial number which identify particular instance
     *              of notification in the context of the notification source.
     * @serialField timeStamp long The notification timestamp.
     *              Indicating when the notification was generated
     * @serialField userData Object The notification user data.
     *              Used for whatever other data the notification
     *              source wishes to communicate to its consumers
     * @serialField message String The notification message.
     * @serialField source Object The object on which the notification initially occurred.
     */
    private static final ObjectStreamField[] serialPersistentFields;
    private static boolean compat = false;
    static {
        try {
            GetPropertyAction act = new GetPropertyAction("jmx.serial.form");
            String form = AccessController.doPrivileged(act);
            compat = (form != null && form.equals("1.0"));
        } catch (Exception e) {
            // OK: exception means no compat with 1.0, too bad
        }
        if (compat) {
            serialPersistentFields = oldSerialPersistentFields;
            serialVersionUID = oldSerialVersionUID;
        } else {
            serialPersistentFields = newSerialPersistentFields;
            serialVersionUID = newSerialVersionUID;
        }
    }
    //
    // END Serialization compatibility stuff

    /**
    /* <p>
    /* 
     * @serial The notification type.
     *         A string expressed in a dot notation similar to Java properties.
     *         An example of a notification type is network.alarm.router
     */
    private String type;

    /**
    /* <p>
    /* 
     * @serial The notification sequence number.
     *         A serial number which identify particular instance
     *         of notification in the context of the notification source.
     */
    private long sequenceNumber;

    /**
    /* <p>
    /* 
     * @serial The notification timestamp.
     *         Indicating when the notification was generated
     */
    private long timeStamp;

    /**
    /* <p>
    /* 
     * @serial The notification user data.
     *         Used for whatever other data the notification
     *         source wishes to communicate to its consumers
     */
    private Object userData = null;

    /**
    /* <p>
    /* 
     * @serial The notification message.
     */
    private String message  = "";

    /**
     * <p>This field hides the {@link EventObject#source} field in the
     * parent class to make it non-transient and therefore part of the
     * serialized form.</p>
     *
     * <p>
     *  <p>此字段隐藏父类中的{@link EventObject#source}字段,使其非瞬态,因此是序列化形式的一部分。</p>
     * 
     * 
     * @serial The object on which the notification initially occurred.
     */
    protected Object source = null;


    /**
     * Creates a Notification object.
     * The notification timeStamp is set to the current date.
     *
     * <p>
     *  创建Notification对象。通知timeStamp设置为当前日期。
     * 
     * 
     * @param type The notification type.
     * @param source The notification source.
     * @param sequenceNumber The notification sequence number within the source object.
     *
     */
    public Notification(String type, Object source, long sequenceNumber) {
        super (source) ;
        this.source = source;
        this.type = type;
        this.sequenceNumber = sequenceNumber ;
        this.timeStamp = (new java.util.Date()).getTime() ;
    }

    /**
     * Creates a Notification object.
     * The notification timeStamp is set to the current date.
     *
     * <p>
     *  创建Notification对象。通知timeStamp设置为当前日期。
     * 
     * 
     * @param type The notification type.
     * @param source The notification source.
     * @param sequenceNumber The notification sequence number within the source object.
     * @param message The detailed message.
     *
     */
    public Notification(String type, Object source, long sequenceNumber, String message) {
        super (source) ;
        this.source = source;
        this.type = type;
        this.sequenceNumber = sequenceNumber ;
        this.timeStamp = (new java.util.Date()).getTime() ;
        this.message = message ;
    }

    /**
     * Creates a Notification object.
     *
     * <p>
     *  创建Notification对象。
     * 
     * 
     * @param type The notification type.
     * @param source The notification source.
     * @param sequenceNumber The notification sequence number within the source object.
     * @param timeStamp The notification emission date.
     *
     */
    public Notification(String type, Object source, long sequenceNumber, long timeStamp) {
        super (source) ;
        this.source = source;
        this.type = type ;
        this.sequenceNumber = sequenceNumber ;
        this.timeStamp = timeStamp ;
    }

    /**
     * Creates a Notification object.
     *
     * <p>
     *  创建Notification对象。
     * 
     * 
     * @param type The notification type.
     * @param source The notification source.
     * @param sequenceNumber The notification sequence number within the source object.
     * @param timeStamp The notification emission date.
     * @param message The detailed message.
     *
     */
    public Notification(String type, Object source, long sequenceNumber, long timeStamp, String message) {
        super (source) ;
        this.source = source;
        this.type = type ;
        this.sequenceNumber = sequenceNumber ;
        this.timeStamp = timeStamp ;
        this.message = message ;
    }

    /**
     * Sets the source.
     *
     * <p>
     *  设置源。
     * 
     * 
     * @param source the new source for this object.
     *
     * @see EventObject#getSource
     */
    public void setSource(Object source) {
        super.source = source;
        this.source = source;
    }

    /**
     * Get the notification sequence number.
     *
     * <p>
     *  获取通知序列号。
     * 
     * 
     * @return The notification sequence number within the source object. It's a serial number
     * identifying a particular instance of notification in the context of the notification source.
     * The notification model does not assume that notifications will be received in the same order
     * that they are sent. The sequence number helps listeners to sort received notifications.
     *
     * @see #setSequenceNumber
     */
    public long getSequenceNumber() {
        return sequenceNumber ;
    }

    /**
     * Set the notification sequence number.
     *
     * <p>
     *  设置通知序列号。
     * 
     * 
     * @param sequenceNumber The notification sequence number within the source object. It is
     * a serial number identifying a particular instance of notification in the
     * context of the notification source.
     *
     * @see #getSequenceNumber
     */
    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Get the notification type.
     *
     * <p>
     *  获取通知类型。
     * 
     * 
     * @return The notification type. It's a string expressed in a dot notation
     * similar to Java properties. It is recommended that the notification type
     * should follow the reverse-domain-name convention used by Java package
     * names.  An example of a notification type is com.example.alarm.router.
     */
    public String getType() {
        return type ;
    }

    /**
     * Get the notification timestamp.
     *
     * <p>
     *  获取通知时间戳。
     * 
     * 
     * @return The notification timestamp.
     *
     * @see #setTimeStamp
     */
    public long getTimeStamp() {
        return timeStamp ;
    }

    /**
     * Set the notification timestamp.
     *
     * <p>
     *  设置通知时间戳。
     * 
     * 
     * @param timeStamp The notification timestamp. It indicates when the notification was generated.
     *
     * @see #getTimeStamp
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Get the notification message.
     *
     * <p>
     *  获取通知消息。
     * 
     * 
     * @return The message string of this notification object.
     *
     */
    public String getMessage() {
        return message ;
    }

    /**
     * Get the user data.
     *
     * <p>
     *  获取用户数据。
     * 
     * 
     * @return The user data object. It is used for whatever data
     * the notification source wishes to communicate to its consumers.
     *
     * @see #setUserData
     */
    public Object getUserData() {
        return userData ;
    }

    /**
     * Set the user data.
     *
     * <p>
     *  设置用户数据。
     * 
     * 
     * @param userData The user data object. It is used for whatever data
     * the notification source wishes to communicate to its consumers.
     *
     * @see #getUserData
     */
    public void setUserData(Object userData) {

        this.userData = userData ;
    }

    /**
     * Returns a String representation of this notification.
     *
     * <p>
     * 返回此通知的字符串表示形式。
     * 
     * 
     * @return A String representation of this notification.
     */
    @Override
    public String toString() {
        return super.toString()+"[type="+type+"][message="+message+"]";
    }

    /**
     * Deserializes a {@link Notification} from an {@link ObjectInputStream}.
     * <p>
     *  从{@link ObjectInputStream}反序列化{@link Notification}。
     * 
     */
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
      // New serial form ignores extra field "sourceObjectName"
      in.defaultReadObject();
      super.source = source;
    }


    /**
     * Serializes a {@link Notification} to an {@link ObjectOutputStream}.
     * <p>
     *  将{@link Notification}序列化为{@link ObjectOutputStream}。
     */
    private void writeObject(ObjectOutputStream out)
            throws IOException {
        if (compat) {
            // Serializes this instance in the old serial form
            //
            ObjectOutputStream.PutField fields = out.putFields();
            fields.put("type", type);
            fields.put("sequenceNumber", sequenceNumber);
            fields.put("timeStamp", timeStamp);
            fields.put("userData", userData);
            fields.put("message", message);
            fields.put("source", source);
            out.writeFields();
        } else {
            // Serializes this instance in the new serial form
            //
            out.defaultWriteObject();
        }
    }
}
