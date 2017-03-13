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
/*
/* <p>
/* 
 * @author    IBM Corp.
 *
 * Copyright IBM Corp. 1999-2000.  All rights reserved.
 */

package javax.management.modelmbean;

import static com.sun.jmx.defaults.JmxProperties.MODELMBEAN_LOGGER;
import com.sun.jmx.mbeanserver.GetPropertyAction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.logging.Level;

import javax.management.Descriptor;
import javax.management.DescriptorAccess;
import javax.management.DescriptorKey;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.RuntimeOperationsException;

/**
 * <p>The ModelMBeanOperationInfo object describes a management operation of
 * the ModelMBean.  It is a subclass of MBeanOperationInfo with the addition
 * of an associated Descriptor and an implementation of the DescriptorAccess
 * interface.</p>
 *
 * <P id="descriptor">
 * The fields in the descriptor are defined, but not limited to, the following.
 * Note that when the Type in this table is Number, a String that is the decimal
 * representation of a Long can also be used.</P>
 *
 * <table border="1" cellpadding="5" summary="ModelMBeanOperationInfo Fields">
 * <tr><th>Name</th><th>Type</th><th>Meaning</th></tr>
 * <tr><td>name</td><td>String</td>
 *     <td>Operation name.</td></tr>
 * <tr><td>descriptorType</td><td>String</td>
 *     <td>Must be "operation".</td></tr>
 * <tr><td>class</td><td>String</td>
 *     <td>Class where method is defined (fully qualified).</td></tr>
 * <tr><td>role</td><td>String</td>
 *     <td>Must be "operation", "getter", or "setter".</td></tr>
 * <tr><td>targetObject</td><td>Object</td>
 *     <td>Object on which to execute this method.</td></tr>
 * <tr><td>targetType</td><td>String</td>
 *     <td>type of object reference for targetObject. Can be:
 *         ObjectReference | Handle | EJBHandle | IOR | RMIReference.</td></tr>
 * <tr><td>value</td><td>Object</td>
 *     <td>Cached value for operation.</td></tr>
 * <tr><td>displayName</td><td>String</td>
 *     <td>Human readable display name of the operation.</td>
 * <tr><td>currencyTimeLimit</td><td>Number</td>
 *     <td>How long cached value is valid.</td></tr>
 * <tr><td>lastUpdatedTimeStamp</td><td>Number</td>
 *     <td>When cached value was set.</td></tr>
 * <tr><td>visibility</td><td>Number</td>
 *     <td>1-4 where 1: always visible 4: rarely visible.</td></tr>
 * <tr><td>presentationString</td><td>String</td>
 *     <td>XML formatted string to describe how to present operation</td></tr>
 * </table>
 *
 * <p>The default descriptor will have name, descriptorType, displayName and
 * role fields set.  The default value of the name and displayName fields is
 * the operation name.</p>
 *
 * <p><b>Note:</b> because of inconsistencies in previous versions of
 * this specification, it is recommended not to use negative or zero
 * values for <code>currencyTimeLimit</code>.  To indicate that a
 * cached value is never valid, omit the
 * <code>currencyTimeLimit</code> field.  To indicate that it is
 * always valid, use a very large number for this field.</p>
 *
 * <p>The <b>serialVersionUID</b> of this class is <code>6532732096650090465L</code>.
 *
 * <p>
 *  <p> ModelMBeanOperationInfo对象描述了ModelMBean的管理操作。
 * 它是MBeanOperationInfo的子类,添加了相关的描述符和DescriptorAccess接口的实现</p>。
 * 
 * <P id="descriptor">
 *  描述符中的字段被定义,但不限于,以下注意,当该表中的类型是Number时,也可以使用作为Long的十进制表示的String </P>
 * 
 * <table border="1" cellpadding="5" summary="ModelMBeanOperationInfo Fields">
 * <tr> <th>名称</th> <th>类型</th> <th>含义</th> </tr> <tr> <td>名称</td> <td>字符串</td> <td>操作名称</td> </tr> <tr>
 *  <td> descriptorType </td> <td>字符串</td> <td>必须是"operation"</td> </tr> <tr > <td> class </td> <td> Str
 * ing </td> <td>定义方法的类别(完全限定)</td> </tr> <tr> <td> > String </td> <td>必须是"operation","getter"或"setter"</td>
 *  </tr> <tr> <td> targetObject </td> <td> <td>要执行此方法的对象</td> </tr> <tr> <td> targetType </td> <td> Str
 * ing </td> <td> targetObject的对象引用类型可以是：ObjectReference |句柄| EJBHandle | IOR | RMIReference </td> </tr>
 *  <tr> <td> value </td> <td>对象</td> <td>操作的缓存值</td> </tr> <tr> <td> displayName </td> <td>字符串</td> <td>
 * 操作的可读显示名称。
 * </table>
 * 
 * <p>默认描述符将具有name,descriptorType,displayName和role fields set name和displayName字段的默认值是操作名称</p>
 * 
 *  <p> <b>注意：</b>由于本规范之前版本的不一致,建议不要对<code> currencyTimeLimit </code>使用负值或零值。
 * 表示高速缓存的值从不有效,省略<code> currencyTimeLimit </code>字段要指示它始终有效,请对此字段使用非常大的数字</p>。
 * 
 *  <p>此类别的<b> serialVersionUID </b>是<code> 6532732096650090465L </code>
 * 
 * 
 * @since 1.5
 */

@SuppressWarnings("serial")  // serialVersionUID is not constant
public class ModelMBeanOperationInfo extends MBeanOperationInfo
         implements DescriptorAccess
{

    // Serialization compatibility stuff:
    // Two serial forms are supported in this class. The selected form depends
    // on system property "jmx.serial.form":
    //  - "1.0" for JMX 1.0
    //  - any other value for JMX 1.1 and higher
    //
    // Serial version for old serial form
    private static final long oldSerialVersionUID = 9087646304346171239L;
    //
    // Serial version for new serial form
    private static final long newSerialVersionUID = 6532732096650090465L;
    //
    // Serializable fields in old serial form
    private static final ObjectStreamField[] oldSerialPersistentFields =
    {
      new ObjectStreamField("operationDescriptor", Descriptor.class),
      new ObjectStreamField("currClass", String.class)
    };
    //
    // Serializable fields in new serial form
    private static final ObjectStreamField[] newSerialPersistentFields =
    {
      new ObjectStreamField("operationDescriptor", Descriptor.class)
    };
    //
    // Actual serial version and serial form
    private static final long serialVersionUID;
    /**
    /* <p>
    /* 
     * @serialField operationDescriptor Descriptor The descriptor
     * containing the appropriate metadata for this instance
     */
    private static final ObjectStreamField[] serialPersistentFields;
    private static boolean compat = false;
    static {
        try {
            GetPropertyAction act = new GetPropertyAction("jmx.serial.form");
            String form = AccessController.doPrivileged(act);
            compat = (form != null && form.equals("1.0"));
        } catch (Exception e) {
            // OK: No compat with 1.0
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
         * @serial The descriptor containing the appropriate metadata for this instance
         */
        private Descriptor operationDescriptor = validDescriptor(null);

        private static final String currClass = "ModelMBeanOperationInfo";

        /**
         * Constructs a ModelMBeanOperationInfo object with a default
         * descriptor. The {@link Descriptor} of the constructed
         * object will include fields contributed by any annotations
         * on the {@code Method} object that contain the {@link
         * DescriptorKey} meta-annotation.
         *
         * <p>
         * 使用默认描述符构造ModelMBeanOperationInfo对象构造对象的{@link Descriptor}将包含由{@code Method}对象上的任何注释贡献的字段,该对象包含{@link DescriptorKey}
         * 元注释。
         * 
         * 
         * @param operationMethod The java.lang.reflect.Method object
         * describing the MBean operation.
         * @param description A human readable description of the operation.
         */

        public ModelMBeanOperationInfo(String description,
                                       Method operationMethod)
        {
                super(description, operationMethod);
                // create default descriptor
                if (MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
                    MODELMBEAN_LOGGER.logp(Level.FINER,
                            ModelMBeanOperationInfo.class.getName(),
                            "ModelMBeanOperationInfo(String,Method)",
                            "Entry");
                }
                operationDescriptor = validDescriptor(null);
        }

        /**
         * Constructs a ModelMBeanOperationInfo object. The {@link
         * Descriptor} of the constructed object will include fields
         * contributed by any annotations on the {@code Method} object
         * that contain the {@link DescriptorKey} meta-annotation.
         *
         * <p>
         *  构造ModelMBeanOperationInfo对象构造的对象的{@link描述符}将包含由{@code Method}对象上的任何注释贡献的字段,该对象包含{@link DescriptorKey}
         * 元注释。
         * 
         * 
         * @param operationMethod The java.lang.reflect.Method object
         * describing the MBean operation.
         * @param description A human readable description of the
         * operation.
         * @param descriptor An instance of Descriptor containing the
         * appropriate metadata for this instance of the
         * ModelMBeanOperationInfo.  If it is null a default
         * descriptor will be created. If the descriptor does not
         * contain the fields
         * "displayName" or "role", the missing ones are added with
         * their default values.
         *
         * @exception RuntimeOperationsException Wraps an
         * IllegalArgumentException. The descriptor is invalid; or
         * descriptor field "name" is not equal to
         * operation name; or descriptor field "DescriptorType" is
         * not equal to "operation"; or descriptor
         * optional field "role" is present but not equal to "operation",
         * "getter", or "setter".
         *
         */

        public ModelMBeanOperationInfo(String description,
                                       Method operationMethod,
                                       Descriptor descriptor)
        {

                super(description, operationMethod);
                if (MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
                    MODELMBEAN_LOGGER.logp(Level.FINER,
                            ModelMBeanOperationInfo.class.getName(),
                            "ModelMBeanOperationInfo(String,Method,Descriptor)",
                            "Entry");
                }
                operationDescriptor = validDescriptor(descriptor);
        }

        /**
        * Constructs a ModelMBeanOperationInfo object with a default descriptor.
        *
        * <p>
        *  构造具有默认描述符的ModelMBeanOperationInfo对象
        * 
        * 
        * @param name The name of the method.
        * @param description A human readable description of the operation.
        * @param signature MBeanParameterInfo objects describing the
        * parameters(arguments) of the method.
        * @param type The type of the method's return value.
        * @param impact The impact of the method, one of INFO, ACTION,
        * ACTION_INFO, UNKNOWN.
        */

        public ModelMBeanOperationInfo(String name,
                                       String description,
                                       MBeanParameterInfo[] signature,
                                       String type,
                                       int impact)
        {

                super(name, description, signature, type, impact);
                // create default descriptor
                if (MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
                    MODELMBEAN_LOGGER.logp(Level.FINER,
                            ModelMBeanOperationInfo.class.getName(),
                            "ModelMBeanOperationInfo(" +
                            "String,String,MBeanParameterInfo[],String,int)",
                            "Entry");
                }
                operationDescriptor = validDescriptor(null);
        }

        /**
        * Constructs a ModelMBeanOperationInfo object.
        *
        * <p>
        *  构造ModelMBeanOperationInfo对象
        * 
        * 
        * @param name The name of the method.
        * @param description A human readable description of the operation.
        * @param signature MBeanParameterInfo objects describing the
        * parameters(arguments) of the method.
        * @param type The type of the method's return value.
        * @param impact The impact of the method, one of INFO, ACTION,
        * ACTION_INFO, UNKNOWN.
        * @param descriptor An instance of Descriptor containing the
        * appropriate metadata for this instance of the
        * MBeanOperationInfo. If it is null then a default descriptor
        * will be created.  If the descriptor does not contain
        * fields "displayName" or "role",
        * the missing ones are added with their default values.
        *
        * @exception RuntimeOperationsException Wraps an
        * IllegalArgumentException. The descriptor is invalid; or
        * descriptor field "name" is not equal to
        * operation name; or descriptor field "DescriptorType" is
        * not equal to "operation"; or descriptor optional
        * field "role" is present but not equal to "operation", "getter", or
        * "setter".
        */

        public ModelMBeanOperationInfo(String name,
                                       String description,
                                       MBeanParameterInfo[] signature,
                                       String type,
                                       int impact,
                                       Descriptor descriptor)
        {
                super(name, description, signature, type, impact);
                if (MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
                    MODELMBEAN_LOGGER.logp(Level.FINER,
                            ModelMBeanOperationInfo.class.getName(),
                            "ModelMBeanOperationInfo(String,String," +
                            "MBeanParameterInfo[],String,int,Descriptor)",
                            "Entry");
                }
                operationDescriptor = validDescriptor(descriptor);
        }

        /**
         * Constructs a new ModelMBeanOperationInfo object from this
         * ModelMBeanOperation Object.
         *
         * <p>
         *  从此ModelMBeanOperation对象构造一个新的ModelMBeanOperationInfo对象
         * 
         * 
         * @param inInfo the ModelMBeanOperationInfo to be duplicated
         *
         */

        public ModelMBeanOperationInfo(ModelMBeanOperationInfo inInfo)
        {
                super(inInfo.getName(),
                          inInfo.getDescription(),
                          inInfo.getSignature(),
                          inInfo.getReturnType(),
                          inInfo.getImpact());
                if (MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
                    MODELMBEAN_LOGGER.logp(Level.FINER,
                            ModelMBeanOperationInfo.class.getName(),
                            "ModelMBeanOperationInfo(ModelMBeanOperationInfo)",
                            "Entry");
                }
                Descriptor newDesc = inInfo.getDescriptor();
                operationDescriptor = validDescriptor(newDesc);
        }

        /**
        * Creates and returns a new ModelMBeanOperationInfo which is a
        * duplicate of this ModelMBeanOperationInfo.
        *
        * <p>
        *  创建并返回一个新的ModelMBeanOperationInfo,它是此ModelMBeanOperationInfo的副本
        * 
        */

        public Object clone ()
        {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
                MODELMBEAN_LOGGER.logp(Level.FINER,
                        ModelMBeanOperationInfo.class.getName(),
                        "clone()", "Entry");
            }
                return(new ModelMBeanOperationInfo(this)) ;
        }

        /**
         * Returns a copy of the associated Descriptor of the
         * ModelMBeanOperationInfo.
         *
         * <p>
         * 返回ModelMBeanOperationInfo的相关描述符的副本
         * 
         * 
         * @return Descriptor associated with the
         * ModelMBeanOperationInfo object.
         *
         * @see #setDescriptor
         */

        public Descriptor getDescriptor()
        {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
                MODELMBEAN_LOGGER.logp(Level.FINER,
                        ModelMBeanOperationInfo.class.getName(),
                        "getDescriptor()", "Entry");
            }
            if (operationDescriptor == null) {
                operationDescriptor = validDescriptor(null);
            }

            return((Descriptor) operationDescriptor.clone());
        }

        /**
         * Sets associated Descriptor (full replace) for the
         * ModelMBeanOperationInfo If the new Descriptor is null, then
         * the associated Descriptor reverts to a default descriptor.
         * The Descriptor is validated before it is assigned.  If the
         * new Descriptor is invalid, then a
         * RuntimeOperationsException wrapping an
         * IllegalArgumentException is thrown.
         *
         * <p>
         *  为ModelMBeanOperationInfo设置相关的描述符(完全替换)如果新的描述符为空,则相关的描述符将恢复为默认描述符。
         * 描述符在分配之前被验证如果新的描述符无效,则抛出包含IllegalArgumentException的RuntimeOperationsException。
         * 
         * 
         * @param inDescriptor replaces the Descriptor associated with the
         * ModelMBeanOperation.
         *
         * @exception RuntimeOperationsException Wraps an
         * IllegalArgumentException for invalid Descriptor.
         *
         * @see #getDescriptor
         */
        public void setDescriptor(Descriptor inDescriptor)
        {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
                MODELMBEAN_LOGGER.logp(Level.FINER,
                        ModelMBeanOperationInfo.class.getName(),
                        "setDescriptor(Descriptor)", "Entry");
            }
            operationDescriptor = validDescriptor(inDescriptor);
        }

        /**
        * Returns a string containing the entire contents of the
        * ModelMBeanOperationInfo in human readable form.
        * <p>
        *  返回一个包含人类可读形式的ModelMBeanOperationInfo的全部内容的字符串
        * 
        */
        public String toString()
        {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
                MODELMBEAN_LOGGER.logp(Level.FINER,
                        ModelMBeanOperationInfo.class.getName(),
                        "toString()", "Entry");
            }
                String retStr =
                    "ModelMBeanOperationInfo: " + this.getName() +
                    " ; Description: " + this.getDescription() +
                    " ; Descriptor: " + this.getDescriptor() +
                    " ; ReturnType: " + this.getReturnType() +
                    " ; Signature: ";
                MBeanParameterInfo[] pTypes = this.getSignature();
                for (int i=0; i < pTypes.length; i++)
                {
                        retStr = retStr.concat((pTypes[i]).getType() + ", ");
                }
                return retStr;
        }

        /**
         * Clones the passed in Descriptor, sets default values, and checks for validity.
         * If the Descriptor is invalid (for instance by having the wrong "name"),
         * this indicates programming error and a RuntimeOperationsException will be thrown.
         *
         * The following fields will be defaulted if they are not already set:
         * displayName=this.getName(),name=this.getName(),
         * descriptorType="operation", role="operation"
         *
         *
         * <p>
         *  克隆传递的描述符,设置默认值,并检查有效性如果描述符无效(例如有错误的"名称"),这表示编程错误和一个RuntimeOperationsException将抛出
         * 
         * 如果尚未设置以下字段,则将默认为：displayName = thisgetName(),name = thisgetName(),descriptorType ="operation",role ="
         * operation"。
         * 
         * 
         * @param in Descriptor to be checked, or null which is equivalent to
         * an empty Descriptor.
         * @exception RuntimeOperationsException if Descriptor is invalid
         */
        private Descriptor validDescriptor(final Descriptor in)
        throws RuntimeOperationsException {
            Descriptor clone;
            boolean defaulted = (in == null);
            if (defaulted) {
                clone = new DescriptorSupport();
                MODELMBEAN_LOGGER.finer("Null Descriptor, creating new.");
            } else {
                clone = (Descriptor) in.clone();
            }

            //Setting defaults.
            if (defaulted && clone.getFieldValue("name")==null) {
                clone.setField("name", this.getName());
                MODELMBEAN_LOGGER.finer("Defaulting Descriptor name to " + this.getName());
            }
            if (defaulted && clone.getFieldValue("descriptorType")==null) {
                clone.setField("descriptorType", "operation");
                MODELMBEAN_LOGGER.finer("Defaulting descriptorType to \"operation\"");
            }
            if (clone.getFieldValue("displayName") == null) {
                clone.setField("displayName",this.getName());
                MODELMBEAN_LOGGER.finer("Defaulting Descriptor displayName to " + this.getName());
            }
            if (clone.getFieldValue("role") == null) {
                clone.setField("role","operation");
                MODELMBEAN_LOGGER.finer("Defaulting Descriptor role field to \"operation\"");
            }

            //Checking validity
            if (!clone.isValid()) {
                 throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"),
                    "The isValid() method of the Descriptor object itself returned false,"+
                    "one or more required fields are invalid. Descriptor:" + clone.toString());
            }
            if (!getName().equalsIgnoreCase((String) clone.getFieldValue("name"))) {
                    throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"),
                    "The Descriptor \"name\" field does not match the object described. " +
                     " Expected: "+ this.getName() + " , was: " + clone.getFieldValue("name"));
            }
            if (!"operation".equalsIgnoreCase((String) clone.getFieldValue("descriptorType"))) {
                     throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"),
                    "The Descriptor \"descriptorType\" field does not match the object described. " +
                     " Expected: \"operation\" ," + " was: " + clone.getFieldValue("descriptorType"));
            }
            final String role = (String)clone.getFieldValue("role");
            if (!(role.equalsIgnoreCase("operation") ||
                  role.equalsIgnoreCase("setter") ||
                  role.equalsIgnoreCase("getter"))) {
                     throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"),
                    "The Descriptor \"role\" field does not match the object described. " +
                     " Expected: \"operation\", \"setter\", or \"getter\" ," + " was: " + clone.getFieldValue("role"));
            }
            final Object targetValue = clone.getFieldValue("targetType");
            if (targetValue != null) {
                if (!(targetValue instanceof java.lang.String)) {
                    throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"),
                    "The Descriptor field \"targetValue\" is invalid class. " +
                     " Expected: java.lang.String, " + " was: " + targetValue.getClass().getName());
                }
            }
            return clone;
        }

    /**
     * Deserializes a {@link ModelMBeanOperationInfo} from an {@link ObjectInputStream}.
     * <p>
     *  从{@link ObjectInputStream}反序列化{@link ModelMBeanOperationInfo}
     * 
     */
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
      // New serial form ignores extra field "currClass"
      in.defaultReadObject();
    }


    /**
     * Serializes a {@link ModelMBeanOperationInfo} to an {@link ObjectOutputStream}.
     * <p>
     *  将{@link ModelMBeanOperationInfo}序列化为{@link ObjectOutputStream}
     */
    private void writeObject(ObjectOutputStream out)
            throws IOException {
      if (compat)
      {
        // Serializes this instance in the old serial form
        //
        ObjectOutputStream.PutField fields = out.putFields();
        fields.put("operationDescriptor", operationDescriptor);
        fields.put("currClass", currClass);
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
