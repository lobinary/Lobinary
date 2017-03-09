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

package javax.management.relation;

import javax.management.Notification;
import javax.management.ObjectName;

import java.io.InvalidObjectException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.jmx.mbeanserver.GetPropertyAction;
import static com.sun.jmx.mbeanserver.Util.cast;

/**
 * A notification of a change in the Relation Service.
 * A RelationNotification notification is sent when a relation is created via
 * the Relation Service, or an MBean is added as a relation in the Relation
 * Service, or a role is updated in a relation, or a relation is removed from
 * the Relation Service.
 *
 * <p>The <b>serialVersionUID</b> of this class is <code>-6871117877523310399L</code>.
 *
 * <p>
 *  关系服务中的更改的通知。当通过关系服务创建关系时,或者在关系服务中添加了MBean作为关系,或者在关系中更新了角色,或者从关系服务中删除了关系时,将发送RelationNotification通知。
 * 
 *  <p>此类别的<b> serialVersionUID </b>是<code> -6871117877523310399L </code>。
 * 
 * 
 * @since 1.5
 */
@SuppressWarnings("serial")  // serialVersionUID not constant
public class RelationNotification extends Notification {

    // Serialization compatibility stuff:
    // Two serial forms are supported in this class. The selected form depends
    // on system property "jmx.serial.form":
    //  - "1.0" for JMX 1.0
    //  - any other value for JMX 1.1 and higher
    //
    // Serial version for old serial form
    private static final long oldSerialVersionUID = -2126464566505527147L;
    //
    // Serial version for new serial form
    private static final long newSerialVersionUID = -6871117877523310399L;
    //
    // Serializable fields in old serial form
    private static final ObjectStreamField[] oldSerialPersistentFields =
    {
        new ObjectStreamField("myNewRoleValue", ArrayList.class),
        new ObjectStreamField("myOldRoleValue", ArrayList.class),
        new ObjectStreamField("myRelId", String.class),
        new ObjectStreamField("myRelObjName", ObjectName.class),
        new ObjectStreamField("myRelTypeName", String.class),
        new ObjectStreamField("myRoleName", String.class),
        new ObjectStreamField("myUnregMBeanList", ArrayList.class)
    };
    //
    // Serializable fields in new serial form
    private static final ObjectStreamField[] newSerialPersistentFields =
    {
        new ObjectStreamField("newRoleValue", List.class),
        new ObjectStreamField("oldRoleValue", List.class),
        new ObjectStreamField("relationId", String.class),
        new ObjectStreamField("relationObjName", ObjectName.class),
        new ObjectStreamField("relationTypeName", String.class),
        new ObjectStreamField("roleName", String.class),
        new ObjectStreamField("unregisterMBeanList", List.class)
    };
    //
    // Actual serial version and serial form
    private static final long serialVersionUID;
    /**
    /* <p>
    /* 
     * @serialField relationId String Relation identifier of
     * created/removed/updated relation
     * @serialField relationTypeName String Relation type name of
     * created/removed/updated relation
     * @serialField relationObjName ObjectName {@link ObjectName} of
     * the relation MBean of created/removed/updated relation (only if
     * the relation is represented by an MBean)
     * @serialField unregisterMBeanList List List of {@link
     * ObjectName}s of referenced MBeans to be unregistered due to
     * relation removal
     * @serialField roleName String Name of updated role (only for role update)
     * @serialField oldRoleValue List Old role value ({@link
     * ArrayList} of {@link ObjectName}s) (only for role update)
     * @serialField newRoleValue List New role value ({@link
     * ArrayList} of {@link ObjectName}s) (only for role update)
     */
    private static final ObjectStreamField[] serialPersistentFields;
    private static boolean compat = false;
    static {
        try {
            GetPropertyAction act = new GetPropertyAction("jmx.serial.form");
            String form = AccessController.doPrivileged(act);
            compat = (form != null && form.equals("1.0"));
        } catch (Exception e) {
            // OK : Too bad, no compat with 1.0
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

    //
    // Notification types
    //

    /**
     * Type for the creation of an internal relation.
     * <p>
     *  用于创建内部关系的类型。
     * 
     */
    public static final String RELATION_BASIC_CREATION = "jmx.relation.creation.basic";
    /**
     * Type for the relation MBean added into the Relation Service.
     * <p>
     *  在关系服务中添加的关系MBean的类型。
     * 
     */
    public static final String RELATION_MBEAN_CREATION = "jmx.relation.creation.mbean";
    /**
     * Type for an update of an internal relation.
     * <p>
     *  键入内部关系的更新。
     * 
     */
    public static final String RELATION_BASIC_UPDATE = "jmx.relation.update.basic";
    /**
     * Type for the update of a relation MBean.
     * <p>
     *  键入更新关系MBean。
     * 
     */
    public static final String RELATION_MBEAN_UPDATE = "jmx.relation.update.mbean";
    /**
     * Type for the removal from the Relation Service of an internal relation.
     * <p>
     *  键入用于从关系服务中删除内部关系。
     * 
     */
    public static final String RELATION_BASIC_REMOVAL = "jmx.relation.removal.basic";
    /**
     * Type for the removal from the Relation Service of a relation MBean.
     * <p>
     *  键入从关系服务中删除关系MBean。
     * 
     */
    public static final String RELATION_MBEAN_REMOVAL = "jmx.relation.removal.mbean";

    //
    // Private members
    //

    /**
    /* <p>
    /* 
     * @serial Relation identifier of created/removed/updated relation
     */
    private String relationId = null;

    /**
    /* <p>
    /* 
     * @serial Relation type name of created/removed/updated relation
     */
    private String relationTypeName = null;

    /**
    /* <p>
    /* 
     * @serial {@link ObjectName} of the relation MBean of created/removed/updated relation
     *         (only if the relation is represented by an MBean)
     */
    private ObjectName relationObjName = null;

    /**
    /* <p>
    /* 
     * @serial List of {@link ObjectName}s of referenced MBeans to be unregistered due to
     *         relation removal
     */
    private List<ObjectName> unregisterMBeanList = null;

    /**
    /* <p>
    /* 
     * @serial Name of updated role (only for role update)
     */
    private String roleName = null;

    /**
    /* <p>
    /* 
     * @serial Old role value ({@link ArrayList} of {@link ObjectName}s) (only for role update)
     */
    private List<ObjectName> oldRoleValue = null;

    /**
    /* <p>
    /* 
     * @serial New role value ({@link ArrayList} of {@link ObjectName}s) (only for role update)
     */
    private List<ObjectName> newRoleValue = null;

    //
    // Constructors
    //

    /**
     * Creates a notification for either a relation creation (RelationSupport
     * object created internally in the Relation Service, or an MBean added as a
     * relation) or for a relation removal from the Relation Service.
     *
     * <p>
     *  为关系创建(在关系服务中内部创建的RelationSupport对象或作为关系添加的MBean)或从关系服务中删除关系创建通知。
     * 
     * 
     * @param notifType  type of the notification; either:
     * <P>- RELATION_BASIC_CREATION
     * <P>- RELATION_MBEAN_CREATION
     * <P>- RELATION_BASIC_REMOVAL
     * <P>- RELATION_MBEAN_REMOVAL
     * @param sourceObj  source object, sending the notification.  This is either
     * an ObjectName or a RelationService object.  In the latter case it must be
     * the MBean emitting the notification; the MBean Server will rewrite the
     * source to be the ObjectName under which that MBean is registered.
     * @param sequence  sequence number to identify the notification
     * @param timeStamp  time stamp
     * @param message  human-readable message describing the notification
     * @param id  relation id identifying the relation in the Relation
     * Service
     * @param typeName  name of the relation type
     * @param objectName  ObjectName of the relation object if it is an MBean
     * (null for relations internally handled by the Relation Service)
     * @param unregMBeanList  list of ObjectNames of referenced MBeans
     * expected to be unregistered due to relation removal (only for removal,
     * due to CIM qualifiers, can be null)
     *
     * @exception IllegalArgumentException  if:
     * <P>- no value for the notification type
     * <P>- the notification type is not RELATION_BASIC_CREATION,
     * RELATION_MBEAN_CREATION, RELATION_BASIC_REMOVAL or
     * RELATION_MBEAN_REMOVAL
     * <P>- no source object
     * <P>- the source object is not a Relation Service
     * <P>- no relation id
     * <P>- no relation type name
     */
    public RelationNotification(String notifType,
                                Object sourceObj,
                                long sequence,
                                long timeStamp,
                                String message,
                                String id,
                                String typeName,
                                ObjectName objectName,
                                List<ObjectName> unregMBeanList)
        throws IllegalArgumentException {

        super(notifType, sourceObj, sequence, timeStamp, message);

        if (!isValidBasicStrict(notifType,sourceObj,id,typeName) || !isValidCreate(notifType)) {
            throw new IllegalArgumentException("Invalid parameter.");
        }

        relationId = id;
        relationTypeName = typeName;
        relationObjName = safeGetObjectName(objectName);
        unregisterMBeanList = safeGetObjectNameList(unregMBeanList);
    }

    /**
     * Creates a notification for a role update in a relation.
     *
     * <p>
     *  为关系中的角色更新创建通知。
     * 
     * 
     * @param notifType  type of the notification; either:
     * <P>- RELATION_BASIC_UPDATE
     * <P>- RELATION_MBEAN_UPDATE
     * @param sourceObj  source object, sending the notification. This is either
     * an ObjectName or a RelationService object.  In the latter case it must be
     * the MBean emitting the notification; the MBean Server will rewrite the
     * source to be the ObjectName under which that MBean is registered.
     * @param sequence  sequence number to identify the notification
     * @param timeStamp  time stamp
     * @param message  human-readable message describing the notification
     * @param id  relation id identifying the relation in the Relation
     * Service
     * @param typeName  name of the relation type
     * @param objectName  ObjectName of the relation object if it is an MBean
     * (null for relations internally handled by the Relation Service)
     * @param name  name of the updated role
     * @param newValue  new role value (List of ObjectName objects)
     * @param oldValue  old role value (List of ObjectName objects)
     *
     * @exception IllegalArgumentException  if null parameter
     */
    public RelationNotification(String notifType,
                                Object sourceObj,
                                long sequence,
                                long timeStamp,
                                String message,
                                String id,
                                String typeName,
                                ObjectName objectName,
                                String name,
                                List<ObjectName> newValue,
                                List<ObjectName> oldValue
                                )
            throws IllegalArgumentException {

        super(notifType, sourceObj, sequence, timeStamp, message);

        if (!isValidBasicStrict(notifType,sourceObj,id,typeName) || !isValidUpdate(notifType,name,newValue,oldValue)) {
            throw new IllegalArgumentException("Invalid parameter.");
        }

        relationId = id;
        relationTypeName = typeName;
        relationObjName = safeGetObjectName(objectName);

        roleName = name;
        oldRoleValue = safeGetObjectNameList(oldValue);
        newRoleValue = safeGetObjectNameList(newValue);
    }

    //
    // Accessors
    //

    /**
     * Returns the relation identifier of created/removed/updated relation.
     *
     * <p>
     *  返回创建/删除/更新的关系的关系标识符。
     * 
     * 
     * @return the relation id.
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * Returns the relation type name of created/removed/updated relation.
     *
     * <p>
     *  返回创建/移除/更新的关系的关系类型名称。
     * 
     * 
     * @return the relation type name.
     */
    public String getRelationTypeName() {
        return relationTypeName;
    }

    /**
     * Returns the ObjectName of the
     * created/removed/updated relation.
     *
     * <p>
     *  返回创建/删除/更新的关系的ObjectName。
     * 
     * 
     * @return the ObjectName if the relation is an MBean, otherwise null.
     */
    public ObjectName getObjectName() {
        return relationObjName;
    }

    /**
     * Returns the list of ObjectNames of MBeans expected to be unregistered
     * due to a relation removal (only for relation removal).
     *
     * <p>
     *  返回由于关系删除而预期未注册的MBeans的ObjectName的列表(仅用于关系删除)。
     * 
     * 
     * @return a {@link List} of {@link ObjectName}.
     */
    public List<ObjectName> getMBeansToUnregister() {
        List<ObjectName> result;
        if (unregisterMBeanList != null) {
            result = new ArrayList<ObjectName>(unregisterMBeanList);
        } else {
            result = Collections.emptyList();
        }
        return result;
    }

    /**
     * Returns name of updated role of updated relation (only for role update).
     *
     * <p>
     * 返回更新关系的更新角色的名称(仅适用于角色更新)。
     * 
     * 
     * @return the name of the updated role.
     */
    public String getRoleName() {
        String result = null;
        if (roleName != null) {
            result = roleName;
        }
        return result;
    }

    /**
     * Returns old value of updated role (only for role update).
     *
     * <p>
     *  返回已更新角色的旧值(仅适用于角色更新)。
     * 
     * 
     * @return the old value of the updated role.
     */
    public List<ObjectName> getOldRoleValue() {
        List<ObjectName> result;
        if (oldRoleValue != null) {
            result = new ArrayList<ObjectName>(oldRoleValue);
        } else {
            result = Collections.emptyList();
        }
        return result;
    }

    /**
     * Returns new value of updated role (only for role update).
     *
     * <p>
     *  返回已更新角色的新值(仅适用于角色更新)。
     * 
     * 
     * @return the new value of the updated role.
     */
    public List<ObjectName> getNewRoleValue() {
        List<ObjectName> result;
        if (newRoleValue != null) {
            result = new ArrayList<ObjectName>(newRoleValue);
        } else {
            result = Collections.emptyList();
        }
        return result;
    }

    //
    // Misc
    //

    // Initializes members
    //
    // -param notifKind  1 for creation/removal, 2 for update
    // -param notifType  type of the notification; either:
    //  - RELATION_BASIC_UPDATE
    //  - RELATION_MBEAN_UPDATE
    //  for an update, or:
    //  - RELATION_BASIC_CREATION
    //  - RELATION_MBEAN_CREATION
    //  - RELATION_BASIC_REMOVAL
    //  - RELATION_MBEAN_REMOVAL
    //  for a creation or removal
    // -param sourceObj  source object, sending the notification. Will always
    //  be a RelationService object.
    // -param sequence  sequence number to identify the notification
    // -param timeStamp  time stamp
    // -param message  human-readable message describing the notification
    // -param id  relation id identifying the relation in the Relation
    //  Service
    // -param typeName  name of the relation type
    // -param objectName  ObjectName of the relation object if it is an MBean
    //  (null for relations internally handled by the Relation Service)
    // -param unregMBeanList  list of ObjectNames of MBeans expected to be
    //  removed due to relation removal
    // -param name  name of the updated role
    // -param newValue  new value (List of ObjectName objects)
    // -param oldValue  old value (List of ObjectName objects)
    //
    // -exception IllegalArgumentException  if:
    //  - no value for the notification type
    //  - incorrect notification type
    //  - no source object
    //  - the source object is not a Relation Service
    //  - no relation id
    //  - no relation type name
    //  - no role name (for role update)
    //  - no role old value (for role update)
    //  - no role new value (for role update)

    // Despite the fact, that validation in constructor of RelationNotification prohibit
    // creation of the class instance with null sourceObj its possible to set it to null later
    // by public setSource() method.
    // So we should relax validation rules to preserve serialization behavior compatibility.

    private boolean isValidBasicStrict(String notifType, Object sourceObj, String id, String typeName){
        if (sourceObj == null) {
            return false;
        }
        return isValidBasic(notifType,sourceObj,id,typeName);
    }

    private boolean isValidBasic(String notifType, Object sourceObj, String id, String typeName){
        if (notifType == null || id == null || typeName == null) {
            return false;
        }

        if (sourceObj != null && (
            !(sourceObj instanceof RelationService) &&
            !(sourceObj instanceof ObjectName))) {
            return false;
        }

        return true;
    }

    private boolean isValidCreate(String notifType) {
        String[] validTypes= {RelationNotification.RELATION_BASIC_CREATION,
                              RelationNotification.RELATION_MBEAN_CREATION,
                              RelationNotification.RELATION_BASIC_REMOVAL,
                              RelationNotification.RELATION_MBEAN_REMOVAL};

        Set<String> ctSet = new HashSet<String>(Arrays.asList(validTypes));
        return ctSet.contains(notifType);
    }

    private boolean isValidUpdate(String notifType, String name,
                                  List<ObjectName> newValue, List<ObjectName> oldValue) {

        if (!(notifType.equals(RelationNotification.RELATION_BASIC_UPDATE)) &&
            !(notifType.equals(RelationNotification.RELATION_MBEAN_UPDATE))) {
            return false;
        }

        if (name == null || oldValue == null || newValue == null) {
            return false;
        }

        return true;
    }

    private ArrayList<ObjectName> safeGetObjectNameList(List<ObjectName> src){
        ArrayList<ObjectName> dest = null;
        if (src != null) {
            dest = new ArrayList<ObjectName>();
            for (ObjectName item : src) {
                // NPE thrown if we attempt to add null object
                dest.add(ObjectName.getInstance(item));
            }
        }
        return dest;
    }

    private ObjectName safeGetObjectName(ObjectName src){
        ObjectName dest = null;
        if (src != null) {
            dest = ObjectName.getInstance(src);
        }
        return dest;
    }

    /**
     * Deserializes a {@link RelationNotification} from an {@link ObjectInputStream}.
     * <p>
     *  从{@link ObjectInputStream}反序列化{@link RelationNotification}。
     * 
     */
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        String tmpRelationId, tmpRelationTypeName, tmpRoleName;

        ObjectName tmpRelationObjName;
        List<ObjectName> tmpNewRoleValue, tmpOldRoleValue, tmpUnregMBeanList;

        ObjectInputStream.GetField fields = in.readFields();

        if (compat) {
            tmpRelationId = (String)fields.get("myRelId", null);
            tmpRelationTypeName = (String)fields.get("myRelTypeName", null);
            tmpRoleName = (String)fields.get("myRoleName", null);

            tmpRelationObjName = (ObjectName)fields.get("myRelObjName", null);
            tmpNewRoleValue = cast(fields.get("myNewRoleValue", null));
            tmpOldRoleValue = cast(fields.get("myOldRoleValue", null));
            tmpUnregMBeanList = cast(fields.get("myUnregMBeanList", null));
        }
        else {
            tmpRelationId = (String)fields.get("relationId", null);
            tmpRelationTypeName = (String)fields.get("relationTypeName", null);
            tmpRoleName = (String)fields.get("roleName", null);

            tmpRelationObjName = (ObjectName)fields.get("relationObjName", null);
            tmpNewRoleValue = cast(fields.get("newRoleValue", null));
            tmpOldRoleValue = cast(fields.get("oldRoleValue", null));
            tmpUnregMBeanList = cast(fields.get("unregisterMBeanList", null));
        }

        // Validate fields we just read, throw InvalidObjectException
        // if something goes wrong

        String notifType = super.getType();
        if (!isValidBasic(notifType,super.getSource(),tmpRelationId,tmpRelationTypeName)  ||
            (!isValidCreate(notifType) &&
             !isValidUpdate(notifType,tmpRoleName,tmpNewRoleValue,tmpOldRoleValue))) {

            super.setSource(null);
            throw new InvalidObjectException("Invalid object read");
        }

        // assign deserialized vaules to object fields
        relationObjName = safeGetObjectName(tmpRelationObjName);
        newRoleValue = safeGetObjectNameList(tmpNewRoleValue);
        oldRoleValue = safeGetObjectNameList(tmpOldRoleValue);
        unregisterMBeanList = safeGetObjectNameList(tmpUnregMBeanList);

        relationId = tmpRelationId;
        relationTypeName = tmpRelationTypeName;
        roleName = tmpRoleName;
    }


    /**
     * Serializes a {@link RelationNotification} to an {@link ObjectOutputStream}.
     * <p>
     *  将{@link RelationNotification}序列化为{@link ObjectOutputStream}。
     */
    private void writeObject(ObjectOutputStream out)
            throws IOException {
      if (compat)
      {
        // Serializes this instance in the old serial form
        //
        ObjectOutputStream.PutField fields = out.putFields();
        fields.put("myNewRoleValue", newRoleValue);
        fields.put("myOldRoleValue", oldRoleValue);
        fields.put("myRelId", relationId);
        fields.put("myRelObjName", relationObjName);
        fields.put("myRelTypeName", relationTypeName);
        fields.put("myRoleName",roleName);
        fields.put("myUnregMBeanList", unregisterMBeanList);
        out.writeFields();
      }
      else
      {
        // Serializes this instance in the new serial form
        //
        out.defaultWriteObject();
      }
    }
}
