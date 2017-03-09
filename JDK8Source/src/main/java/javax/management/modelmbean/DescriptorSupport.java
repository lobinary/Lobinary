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
import static com.sun.jmx.mbeanserver.Util.cast;
import com.sun.jmx.mbeanserver.GetPropertyAction;
import com.sun.jmx.mbeanserver.Util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;

import java.lang.reflect.Constructor;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;

import javax.management.Descriptor;
import javax.management.ImmutableDescriptor;
import javax.management.MBeanException;
import javax.management.RuntimeOperationsException;

import sun.reflect.misc.ReflectUtil;

/**
 * This class represents the metadata set for a ModelMBean element.  A
 * descriptor is part of the ModelMBeanInfo,
 * ModelMBeanNotificationInfo, ModelMBeanAttributeInfo,
 * ModelMBeanConstructorInfo, and ModelMBeanParameterInfo.
 * <P>
 * A descriptor consists of a collection of fields.  Each field is in
 * fieldname=fieldvalue format.  Field names are not case sensitive,
 * case will be preserved on field values.
 * <P>
 * All field names and values are not predefined. New fields can be
 * defined and added by any program.  Some fields have been predefined
 * for consistency of implementation and support by the
 * ModelMBeanInfo, ModelMBeanAttributeInfo, ModelMBeanConstructorInfo,
 * ModelMBeanNotificationInfo, ModelMBeanOperationInfo and ModelMBean
 * classes.
 *
 * <p>The <b>serialVersionUID</b> of this class is <code>-6292969195866300415L</code>.
 *
 * <p>
 *  此类表示ModelMBean元素的元数据集。
 * 描述符是ModelMBeanInfo,ModelMBeanNotificationInfo,ModelMBeanAttributeInfo,ModelMBeanConstructorInfo和Model
 * MBeanParameterInfo的一部分。
 *  此类表示ModelMBean元素的元数据集。
 * <P>
 *  描述符由字段的集合组成。每个字段都是fieldname = fieldvalue格式。字段名不区分大小写,大小写将保留在字段值上。
 * <P>
 *  所有字段名称和值都未预定义。新字段可以由任何程序定义和添加。
 * 一些字段已经被预定义为通过ModelMBeanInfo,ModelMBeanAttributeInfo,ModelMBeanConstructorInfo,ModelMBeanNotificationI
 * nfo,ModelMBeanOperationInfo和ModelMBean类的实现和支持的一致性。
 *  所有字段名称和值都未预定义。新字段可以由任何程序定义和添加。
 * 
 *  <p>此类别的<b> serialVersionUID </b>是<code> -6292969195866300415L </code>。
 * 
 * 
 * @since 1.5
 */
@SuppressWarnings("serial")  // serialVersionUID not constant
public class DescriptorSupport
         implements javax.management.Descriptor
{

    // Serialization compatibility stuff:
    // Two serial forms are supported in this class. The selected form depends
    // on system property "jmx.serial.form":
    //  - "1.0" for JMX 1.0
    //  - any other value for JMX 1.1 and higher
    //
    // Serial version for old serial form
    private static final long oldSerialVersionUID = 8071560848919417985L;
    //
    // Serial version for new serial form
    private static final long newSerialVersionUID = -6292969195866300415L;
    //
    // Serializable fields in old serial form
    private static final ObjectStreamField[] oldSerialPersistentFields =
    {
      new ObjectStreamField("descriptor", HashMap.class),
      new ObjectStreamField("currClass", String.class)
    };
    //
    // Serializable fields in new serial form
    private static final ObjectStreamField[] newSerialPersistentFields =
    {
      new ObjectStreamField("descriptor", HashMap.class)
    };
    //
    // Actual serial version and serial form
    private static final long serialVersionUID;
    /**
    /* <p>
    /* 
     * @serialField descriptor HashMap The collection of fields representing this descriptor
     */
    private static final ObjectStreamField[] serialPersistentFields;
    private static final String serialForm;
    static {
        String form = null;
        boolean compat = false;
        try {
            GetPropertyAction act = new GetPropertyAction("jmx.serial.form");
            form = AccessController.doPrivileged(act);
            compat = "1.0".equals(form);  // form may be null
        } catch (Exception e) {
            // OK: No compat with 1.0
        }
        serialForm = form;
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

    /* Spec says that field names are case-insensitive, but that case
       is preserved.  This means that we need to be able to map from a
       name that may differ in case to the actual name that is used in
       the HashMap.  Thus, descriptorMap is a TreeMap with a Comparator
       that ignores case.

       Previous versions of this class had a field called "descriptor"
       of type HashMap where the keys were directly Strings.  This is
       hard to reconcile with the required semantics, so we fabricate
       that field virtually during serialization and deserialization
       but keep the real information in descriptorMap.
    /* <p>
    /*  保留。这意味着我们需要能够从可能与HashMap中使用的实际名称不同的名称进行映射。因此,descriptorMap是一个具有忽略大小写的Comparator的TreeMap。
    /* 
    /*  此类的先前版本有一个称为"描述符"的类型为HashMap的字段,其中键是直接字符串。
    /* 这很难与所需的语义协调,所以我们在序列化和反序列化期间虚拟地构建该字段,但在descriptorMap中保留真实信息。
    /* 
    */
    private transient SortedMap<String, Object> descriptorMap;

    private static final String currClass = "DescriptorSupport";


    /**
     * Descriptor default constructor.
     * Default initial descriptor size is 20.  It will grow as needed.<br>
     * Note that the created empty descriptor is not a valid descriptor
     * (the method {@link #isValid isValid} returns <CODE>false</CODE>)
     * <p>
     * 描述符默认构造函数。 <br>请注意,创建的空描述符不是有效的描述符(方法{@link #isValid isValid}返回<CODE> false </CODE>)
     * 
     */
    public DescriptorSupport() {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "DescriptorSupport()" , "Constructor");
        }
        init(null);
    }

    /**
     * Descriptor constructor.  Takes as parameter the initial
     * capacity of the Map that stores the descriptor fields.
     * Capacity will grow as needed.<br> Note that the created empty
     * descriptor is not a valid descriptor (the method {@link
     * #isValid isValid} returns <CODE>false</CODE>).
     *
     * <p>
     *  描述符构造函数。将参数作为存储描述符字段的Map的初始容量。
     *  <br>请注意,创建的空描述符不是有效的描述符(方法{@link #isValid isValid}返回<CODE> false </CODE>)。
     * 
     * 
     * @param initNumFields The initial capacity of the Map that
     * stores the descriptor fields.
     *
     * @exception RuntimeOperationsException for illegal value for
     * initNumFields (&lt;= 0)
     * @exception MBeanException Wraps a distributed communication Exception.
     */
    public DescriptorSupport(int initNumFields)
            throws MBeanException, RuntimeOperationsException {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "Descriptor(initNumFields = " + initNumFields + ")",
                    "Constructor");
        }
        if (initNumFields <= 0) {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                MODELMBEAN_LOGGER.logp(Level.FINEST,
                        DescriptorSupport.class.getName(),
                        "Descriptor(initNumFields)",
                        "Illegal arguments: initNumFields <= 0");
            }
            final String msg =
                "Descriptor field limit invalid: " + initNumFields;
            final RuntimeException iae = new IllegalArgumentException(msg);
            throw new RuntimeOperationsException(iae, msg);
        }
        init(null);
    }

    /**
     * Descriptor constructor taking a Descriptor as parameter.
     * Creates a new descriptor initialized to the values of the
     * descriptor passed in parameter.
     *
     * <p>
     *  以描述符为参数的描述符构造函数。创建一个新的描述符,初始化为在参数中传递的描述符的值。
     * 
     * 
     * @param inDescr the descriptor to be used to initialize the
     * constructed descriptor. If it is null or contains no descriptor
     * fields, an empty Descriptor will be created.
     */
    public DescriptorSupport(DescriptorSupport inDescr) {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "Descriptor(Descriptor)", "Constructor");
        }
        if (inDescr == null)
            init(null);
        else
            init(inDescr.descriptorMap);
    }


    /**
     * <p>Descriptor constructor taking an XML String.</p>
     *
     * <p>The format of the XML string is not defined, but an
     * implementation must ensure that the string returned by
     * {@link #toXMLString() toXMLString()} on an existing
     * descriptor can be used to instantiate an equivalent
     * descriptor using this constructor.</p>
     *
     * <p>In this implementation, all field values will be created
     * as Strings.  If the field values are not Strings, the
     * programmer will have to reset or convert these fields
     * correctly.</p>
     *
     * <p>
     *  <p>描述符构造函数接受XML字符串。</p>
     * 
     *  <p> XML字符串的格式未定义,但实现必须确保现有描述符上的{@link #toXMLString()toXMLString()}返回的字符串可用于使用此构造函数实例化等效描述符。 </p>
     * 
     *  <p>在此实现中,所有字段值都将创建为字符串。如果字段值不是字符串,程序员将必须正确地重置或转换这些字段。</p>
     * 
     * 
     * @param inStr An XML-formatted string used to populate this
     * Descriptor.  The format is not defined, but any
     * implementation must ensure that the string returned by
     * method {@link #toXMLString toXMLString} on an existing
     * descriptor can be used to instantiate an equivalent
     * descriptor when instantiated using this constructor.
     *
     * @exception RuntimeOperationsException If the String inStr
     * passed in parameter is null
     * @exception XMLParseException XML parsing problem while parsing
     * the input String
     * @exception MBeanException Wraps a distributed communication Exception.
     */
    /* At some stage we should rewrite this code to be cleverer.  Using
       a StringTokenizer as we do means, first, that we accept a lot of
       bogus strings without noticing they are bogus, and second, that we
       split the string being parsed at characters like > even if they
    /* <p>
    /*  一个StringTokenizer,因为我们的意思,首先,我们接受了很多假的字符串,而没有注意到他们是假的,其次,我们分割字符串解析的字符像>,即使他们
    /* 
    /* 
       occur in the middle of a field value. */
    public DescriptorSupport(String inStr)
            throws MBeanException, RuntimeOperationsException,
                   XMLParseException {
        /* parse an XML-formatted string and populate internal
        /* <p>
        /* 
         * structure with it */
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "Descriptor(String = '" + inStr + "')", "Constructor");
        }
        if (inStr == null) {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                MODELMBEAN_LOGGER.logp(Level.FINEST,
                        DescriptorSupport.class.getName(),
                        "Descriptor(String = null)", "Illegal arguments");
            }
            final String msg = "String in parameter is null";
            final RuntimeException iae = new IllegalArgumentException(msg);
            throw new RuntimeOperationsException(iae, msg);
        }

        final String lowerInStr = inStr.toLowerCase();
        if (!lowerInStr.startsWith("<descriptor>")
            || !lowerInStr.endsWith("</descriptor>")) {
            throw new XMLParseException("No <descriptor>, </descriptor> pair");
        }

        // parse xmlstring into structures
        init(null);
        // create dummy descriptor: should have same size
        // as number of fields in xmlstring
        // loop through structures and put them in descriptor

        StringTokenizer st = new StringTokenizer(inStr, "<> \t\n\r\f");

        boolean inFld = false;
        boolean inDesc = false;
        String fieldName = null;
        String fieldValue = null;


        while (st.hasMoreTokens()) {  // loop through tokens
            String tok = st.nextToken();

            if (tok.equalsIgnoreCase("FIELD")) {
                inFld = true;
            } else if (tok.equalsIgnoreCase("/FIELD")) {
                if ((fieldName != null) && (fieldValue != null)) {
                    fieldName =
                        fieldName.substring(fieldName.indexOf('"') + 1,
                                            fieldName.lastIndexOf('"'));
                    final Object fieldValueObject =
                        parseQuotedFieldValue(fieldValue);
                    setField(fieldName, fieldValueObject);
                }
                fieldName = null;
                fieldValue = null;
                inFld = false;
            } else if (tok.equalsIgnoreCase("DESCRIPTOR")) {
                inDesc = true;
            } else if (tok.equalsIgnoreCase("/DESCRIPTOR")) {
                inDesc = false;
                fieldName = null;
                fieldValue = null;
                inFld = false;
            } else if (inFld && inDesc) {
                // want kw=value, eg, name="myname" value="myvalue"
                int eq_separator = tok.indexOf("=");
                if (eq_separator > 0) {
                    String kwPart = tok.substring(0,eq_separator);
                    String valPart = tok.substring(eq_separator+1);
                    if (kwPart.equalsIgnoreCase("NAME"))
                        fieldName = valPart;
                    else if (kwPart.equalsIgnoreCase("VALUE"))
                        fieldValue = valPart;
                    else {  // xml parse exception
                        final String msg =
                            "Expected `name' or `value', got `" + tok + "'";
                        throw new XMLParseException(msg);
                    }
                } else { // xml parse exception
                    final String msg =
                        "Expected `keyword=value', got `" + tok + "'";
                    throw new XMLParseException(msg);
                }
            }
        }  // while tokens
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "Descriptor(XMLString)", "Exit");
        }
    }

    /**
     * Constructor taking field names and field values.  Neither array
     * can be null.
     *
     * <p>
     *  构造函数获取字段名称和字段值。两个数组都不能为空。
     * 
     * 
     * @param fieldNames String array of field names.  No elements of
     * this array can be null.
     * @param fieldValues Object array of the corresponding field
     * values.  Elements of the array can be null. The
     * <code>fieldValue</code> must be valid for the
     * <code>fieldName</code> (as defined in method {@link #isValid
     * isValid})
     *
     * <p>Note: array sizes of parameters should match. If both arrays
     * are empty, then an empty descriptor is created.</p>
     *
     * @exception RuntimeOperationsException for illegal value for
     * field Names or field Values.  The array lengths must be equal.
     * If the descriptor construction fails for any reason, this
     * exception will be thrown.
     *
     */
    public DescriptorSupport(String[] fieldNames, Object[] fieldValues)
            throws RuntimeOperationsException {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "Descriptor(fieldNames,fieldObjects)", "Constructor");
        }

        if ((fieldNames == null) || (fieldValues == null) ||
            (fieldNames.length != fieldValues.length)) {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                MODELMBEAN_LOGGER.logp(Level.FINEST,
                        DescriptorSupport.class.getName(),
                        "Descriptor(fieldNames,fieldObjects)",
                        "Illegal arguments");
            }

            final String msg =
                "Null or invalid fieldNames or fieldValues";
            final RuntimeException iae = new IllegalArgumentException(msg);
            throw new RuntimeOperationsException(iae, msg);
        }

        /* populate internal structure with fields */
        init(null);
        for (int i=0; i < fieldNames.length; i++) {
            // setField will throw an exception if a fieldName is be null.
            // the fieldName and fieldValue will be validated in setField.
            setField(fieldNames[i], fieldValues[i]);
        }
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "Descriptor(fieldNames,fieldObjects)", "Exit");
        }
    }

    /**
     * Constructor taking fields in the <i>fieldName=fieldValue</i>
     * format.
     *
     * <p>
     *  构造函数以<i> fieldName = fieldValue </i>格式输入字段。
     * 
     * 
     * @param fields String array with each element containing a
     * field name and value.  If this array is null or empty, then the
     * default constructor will be executed. Null strings or empty
     * strings will be ignored.
     *
     * <p>All field values should be Strings.  If the field values are
     * not Strings, the programmer will have to reset or convert these
     * fields correctly.
     *
     * <p>Note: Each string should be of the form
     * <i>fieldName=fieldValue</i>.  The field name
     * ends at the first {@code =} character; for example if the String
     * is {@code a=b=c} then the field name is {@code a} and its value
     * is {@code b=c}.
     *
     * @exception RuntimeOperationsException for illegal value for
     * field Names or field Values.  The field must contain an
     * "=". "=fieldValue", "fieldName", and "fieldValue" are illegal.
     * FieldName cannot be null.  "fieldName=" will cause the value to
     * be null.  If the descriptor construction fails for any reason,
     * this exception will be thrown.
     *
     */
    public DescriptorSupport(String... fields)
    {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "Descriptor(String... fields)", "Constructor");
        }
        init(null);
        if (( fields == null ) || ( fields.length == 0))
            return;

        init(null);

        for (int i=0; i < fields.length; i++) {
            if ((fields[i] == null) || (fields[i].equals(""))) {
                continue;
            }
            int eq_separator = fields[i].indexOf("=");
            if (eq_separator < 0) {
                // illegal if no = or is first character
                if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                    MODELMBEAN_LOGGER.logp(Level.FINEST,
                            DescriptorSupport.class.getName(),
                            "Descriptor(String... fields)",
                            "Illegal arguments: field does not have " +
                            "'=' as a name and value separator");
                }
                final String msg = "Field in invalid format: no equals sign";
                final RuntimeException iae = new IllegalArgumentException(msg);
                throw new RuntimeOperationsException(iae, msg);
            }

            String fieldName = fields[i].substring(0,eq_separator);
            String fieldValue = null;
            if (eq_separator < fields[i].length()) {
                // = is not in last character
                fieldValue = fields[i].substring(eq_separator+1);
            }

            if (fieldName.equals("")) {
                if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                    MODELMBEAN_LOGGER.logp(Level.FINEST,
                            DescriptorSupport.class.getName(),
                            "Descriptor(String... fields)",
                            "Illegal arguments: fieldName is empty");
                }

                final String msg = "Field in invalid format: no fieldName";
                final RuntimeException iae = new IllegalArgumentException(msg);
                throw new RuntimeOperationsException(iae, msg);
            }

            setField(fieldName,fieldValue);
        }
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "Descriptor(String... fields)", "Exit");
        }
    }

    private void init(Map<String, ?> initMap) {
        descriptorMap =
                new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
        if (initMap != null)
            descriptorMap.putAll(initMap);
    }

    // Implementation of the Descriptor interface


    public synchronized Object getFieldValue(String fieldName)
            throws RuntimeOperationsException {

        if ((fieldName == null) || (fieldName.equals(""))) {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                MODELMBEAN_LOGGER.logp(Level.FINEST,
                        DescriptorSupport.class.getName(),
                        "getFieldValue(String fieldName)",
                        "Illegal arguments: null field name");
            }
            final String msg = "Fieldname requested is null";
            final RuntimeException iae = new IllegalArgumentException(msg);
            throw new RuntimeOperationsException(iae, msg);
        }
        Object retValue = descriptorMap.get(fieldName);
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "getFieldValue(String fieldName = " + fieldName + ")",
                    "Returns '" + retValue + "'");
        }
        return(retValue);
    }

    public synchronized void setField(String fieldName, Object fieldValue)
            throws RuntimeOperationsException {

        // field name cannot be null or empty
        if ((fieldName == null) || (fieldName.equals(""))) {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                MODELMBEAN_LOGGER.logp(Level.FINEST,
                        DescriptorSupport.class.getName(),
                        "setField(fieldName,fieldValue)",
                        "Illegal arguments: null or empty field name");
            }

            final String msg = "Field name to be set is null or empty";
            final RuntimeException iae = new IllegalArgumentException(msg);
            throw new RuntimeOperationsException(iae, msg);
        }

        if (!validateField(fieldName, fieldValue)) {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                MODELMBEAN_LOGGER.logp(Level.FINEST,
                        DescriptorSupport.class.getName(),
                        "setField(fieldName,fieldValue)",
                        "Illegal arguments");
            }

            final String msg =
                "Field value invalid: " + fieldName + "=" + fieldValue;
            final RuntimeException iae = new IllegalArgumentException(msg);
            throw new RuntimeOperationsException(iae, msg);
        }

        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "setField(fieldName,fieldValue)", "Entry: setting '"
                    + fieldName + "' to '" + fieldValue + "'");
        }

        // Since we do not remove any existing entry with this name,
        // the field will preserve whatever case it had, ignoring
        // any difference there might be in fieldName.
        descriptorMap.put(fieldName, fieldValue);
    }

    public synchronized String[] getFields() {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "getFields()", "Entry");
        }
        int numberOfEntries = descriptorMap.size();

        String[] responseFields = new String[numberOfEntries];
        Set<Map.Entry<String, Object>> returnedSet = descriptorMap.entrySet();

        int i = 0;

        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "getFields()", "Returning " + numberOfEntries + " fields");
        }
        for (Iterator<Map.Entry<String, Object>> iter = returnedSet.iterator();
             iter.hasNext(); i++) {
            Map.Entry<String, Object> currElement = iter.next();

            if (currElement == null) {
                if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                    MODELMBEAN_LOGGER.logp(Level.FINEST,
                            DescriptorSupport.class.getName(),
                            "getFields()", "Element is null");
                }
            } else {
                Object currValue = currElement.getValue();
                if (currValue == null) {
                    responseFields[i] = currElement.getKey() + "=";
                } else {
                    if (currValue instanceof java.lang.String) {
                        responseFields[i] =
                            currElement.getKey() + "=" + currValue.toString();
                    } else {
                        responseFields[i] =
                            currElement.getKey() + "=(" +
                            currValue.toString() + ")";
                    }
                }
            }
        }

        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "getFields()", "Exit");
        }

        return responseFields;
    }

    public synchronized String[] getFieldNames() {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "getFieldNames()", "Entry");
        }
        int numberOfEntries = descriptorMap.size();

        String[] responseFields = new String[numberOfEntries];
        Set<Map.Entry<String, Object>> returnedSet = descriptorMap.entrySet();

        int i = 0;

        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "getFieldNames()",
                    "Returning " + numberOfEntries + " fields");
        }

        for (Iterator<Map.Entry<String, Object>> iter = returnedSet.iterator();
             iter.hasNext(); i++) {
            Map.Entry<String, Object> currElement = iter.next();

            if (( currElement == null ) || (currElement.getKey() == null)) {
                if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                    MODELMBEAN_LOGGER.logp(Level.FINEST,
                            DescriptorSupport.class.getName(),
                            "getFieldNames()", "Field is null");
                }
            } else {
                responseFields[i] = currElement.getKey().toString();
            }
        }

        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "getFieldNames()", "Exit");
        }

        return responseFields;
    }


    public synchronized Object[] getFieldValues(String... fieldNames) {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "getFieldValues(String... fieldNames)", "Entry");
        }
        // if fieldNames == null return all values
        // if fieldNames is String[0] return no values

        final int numberOfEntries =
            (fieldNames == null) ? descriptorMap.size() : fieldNames.length;
        final Object[] responseFields = new Object[numberOfEntries];

        int i = 0;

        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "getFieldValues(String... fieldNames)",
                    "Returning " + numberOfEntries + " fields");
        }

        if (fieldNames == null) {
            for (Object value : descriptorMap.values())
                responseFields[i++] = value;
        } else {
            for (i=0; i < fieldNames.length; i++) {
                if ((fieldNames[i] == null) || (fieldNames[i].equals(""))) {
                    responseFields[i] = null;
                } else {
                    responseFields[i] = getFieldValue(fieldNames[i]);
                }
            }
        }

        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "getFieldValues(String... fieldNames)", "Exit");
        }

        return responseFields;
    }

    public synchronized void setFields(String[] fieldNames,
                                       Object[] fieldValues)
            throws RuntimeOperationsException {

        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "setFields(fieldNames,fieldValues)", "Entry");
        }

        if ((fieldNames == null) || (fieldValues == null) ||
            (fieldNames.length != fieldValues.length)) {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                MODELMBEAN_LOGGER.logp(Level.FINEST,
                        DescriptorSupport.class.getName(),
                        "setFields(fieldNames,fieldValues)",
                        "Illegal arguments");
            }

            final String msg = "fieldNames and fieldValues are null or invalid";
            final RuntimeException iae = new IllegalArgumentException(msg);
            throw new RuntimeOperationsException(iae, msg);
        }

        for (int i=0; i < fieldNames.length; i++) {
            if (( fieldNames[i] == null) || (fieldNames[i].equals(""))) {
                if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                    MODELMBEAN_LOGGER.logp(Level.FINEST,
                            DescriptorSupport.class.getName(),
                            "setFields(fieldNames,fieldValues)",
                            "Null field name encountered at element " + i);
                }
                final String msg = "fieldNames is null or invalid";
                final RuntimeException iae = new IllegalArgumentException(msg);
                throw new RuntimeOperationsException(iae, msg);
            }
            setField(fieldNames[i], fieldValues[i]);
        }
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "setFields(fieldNames,fieldValues)", "Exit");
        }
    }

    /**
     * Returns a new Descriptor which is a duplicate of the Descriptor.
     *
     * <p>
     * 返回与描述符重复的新描述符。
     * 
     * 
     * @exception RuntimeOperationsException for illegal value for
     * field Names or field Values.  If the descriptor construction
     * fails for any reason, this exception will be thrown.
     */

    @Override
    public synchronized Object clone() throws RuntimeOperationsException {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "clone()", "Entry");
        }
        return(new DescriptorSupport(this));
    }

    public synchronized void removeField(String fieldName) {
        if ((fieldName == null) || (fieldName.equals(""))) {
            return;
        }

        descriptorMap.remove(fieldName);
    }

    /**
     * Compares this descriptor to the given object.  The objects are equal if
     * the given object is also a Descriptor, and if the two Descriptors have
     * the same field names (possibly differing in case) and the same
     * associated values.  The respective values for a field in the two
     * Descriptors are equal if the following conditions hold:
     *
     * <ul>
     * <li>If one value is null then the other must be too.</li>
     * <li>If one value is a primitive array then the other must be a primitive
     * array of the same type with the same elements.</li>
     * <li>If one value is an object array then the other must be too and
     * {@link java.util.Arrays#deepEquals(Object[],Object[]) Arrays.deepEquals}
     * must return true.</li>
     * <li>Otherwise {@link Object#equals(Object)} must return true.</li>
     * </ul>
     *
     * <p>
     *  将此描述符与给定对象进行比较。如果给定对象也是描述符,并且如果两个描述符具有相同的字段名(在情况下可能不同)和相同的关联值,则对象是相等的。如果以下条件成立,则两个描述符中的字段的相应值相等：
     * 
     * <ul>
     *  <li>如果一个值为null,那么另一个值也必须为空。</li> <li>如果一个值是基本数组,则另一个值必须是具有相同元素的相同类型的基本数组。
     * </li> <li>如果一个值是对象数组,则另一个值也必须是对象数组,而{@ link java.util.Arrays#deepEquals(Object [],Object [])Arrays.deepEquals}
     * 必须返回true。
     *  <li>如果一个值为null,那么另一个值也必须为空。</li> <li>如果一个值是基本数组,则另一个值必须是具有相同元素的相同类型的基本数组。
     * </li> <li >否则{@link Object#equals(Object)}必须返回true。</li>。
     * </ul>
     * 
     * 
     * @param o the object to compare with.
     *
     * @return {@code true} if the objects are the same; {@code false}
     * otherwise.
     *
     */
    // Note: this Javadoc is copied from javax.management.Descriptor
    //       due to 6369229.
    @Override
    public synchronized boolean equals(Object o) {
        if (o == this)
            return true;
        if (! (o instanceof Descriptor))
            return false;
        if (o instanceof ImmutableDescriptor)
            return o.equals(this);
        return new ImmutableDescriptor(descriptorMap).equals(o);
    }

    /**
     * <p>Returns the hash code value for this descriptor.  The hash
     * code is computed as the sum of the hash codes for each field in
     * the descriptor.  The hash code of a field with name {@code n}
     * and value {@code v} is {@code n.toLowerCase().hashCode() ^ h}.
     * Here {@code h} is the hash code of {@code v}, computed as
     * follows:</p>
     *
     * <ul>
     * <li>If {@code v} is null then {@code h} is 0.</li>
     * <li>If {@code v} is a primitive array then {@code h} is computed using
     * the appropriate overloading of {@code java.util.Arrays.hashCode}.</li>
     * <li>If {@code v} is an object array then {@code h} is computed using
     * {@link java.util.Arrays#deepHashCode(Object[]) Arrays.deepHashCode}.</li>
     * <li>Otherwise {@code h} is {@code v.hashCode()}.</li>
     * </ul>
     *
     * <p>
     *  <p>返回此描述符的哈希码值。哈希码被计算为描述符中的每个字段的哈希码的和。名为{@code n}和值{@code v}的字段的哈希码是{@code n.toLowerCase()。
     * hashCode()^ h}。这里{@code h}是{@code v}的哈希码,计算如下：</p>。
     * 
     * <ul>
     * <li>如果{@code v}为null,则{@code h}为0。
     * </li> <li>如果{@code v}是原始数组,则{@code h} {@code java.util.Arrays.hashCode}。
     * </li> <li>如果{@code v}是一个对象数组,那么{@code h}是使用{@link java.util.Arrays#deepHashCode [])Arrays.deepHashCode}
     * 。
     * </li> <li>如果{@code v}是原始数组,则{@code h} {@code java.util.Arrays.hashCode}。
     * </li> <li>否则{@code h}是{@code v.hashCode()}。</li>。
     * </ul>
     * 
     * 
     * @return A hash code value for this object.
     *
     */
    // Note: this Javadoc is copied from javax.management.Descriptor
    //       due to 6369229.
    @Override
    public synchronized int hashCode() {
        final int size = descriptorMap.size();
        // descriptorMap is sorted with a comparator that ignores cases.
        //
        return Util.hashCode(
                descriptorMap.keySet().toArray(new String[size]),
                descriptorMap.values().toArray(new Object[size]));
    }

    /**
     * Returns true if all of the fields have legal values given their
     * names.
     * <P>
     * This implementation does not support  interoperating with a directory
     * or lookup service. Thus, conforming to the specification, no checking is
     * done on the <i>"export"</i> field.
     * <P>
     * Otherwise this implementation returns false if:
     * <UL>
     * <LI> name and descriptorType fieldNames are not defined, or
     * null, or empty, or not String
     * <LI> class, role, getMethod, setMethod fieldNames, if defined,
     * are null or not String
     * <LI> persistPeriod, currencyTimeLimit, lastUpdatedTimeStamp,
     * lastReturnedTimeStamp if defined, are null, or not a Numeric
     * String or not a Numeric Value {@literal >= -1}
     * <LI> log fieldName, if defined, is null, or not a Boolean or
     * not a String with value "t", "f", "true", "false". These String
     * values must not be case sensitive.
     * <LI> visibility fieldName, if defined, is null, or not a
     * Numeric String or a not Numeric Value {@literal >= 1 and <= 4}
     * <LI> severity fieldName, if defined, is null, or not a Numeric
     * String or not a Numeric Value {@literal >= 0 and <= 6}<br>
     * <LI> persistPolicy fieldName, if defined, is null, or not one of
     * the following strings:<br>
     *   "OnUpdate", "OnTimer", "NoMoreOftenThan", "OnUnregister", "Always",
     *   "Never". These String values must not be case sensitive.<br>
     * </UL>
     *
     * <p>
     *  如果所有字段都具有指定名称的合法值,则返回true。
     * <P>
     *  此实现不支持与目录或查找服务的互操作。因此,符合规范,不对</i>"export"</i>字段进行检查。
     * <P>
     *  否则,此实现返回false如果：
     * <UL>
     * <LI> name和descriptorType fieldNames未定义,或为null或空,或不为String。
     * String <li> class,role,getMethod,setMethod fieldNames,如果定义,为null或不为String StringPeriod,currencyTimeLi
     * mit,lastUpdatedTimeStamp,lastReturnedTimeStamp如果定义,则为null,或不是数字字符串或不是数字值{@literal> = -1} <li>如果定义了log
     * 字段名称,则为null,或不是布尔值或不是值为"t" "f","true","false"。
     * <LI> name和descriptorType fieldNames未定义,或为null或空,或不为String。这些String值不能区分大小写。
     *  <LI> visibility fieldName(如果已定义)为null,或不是数字字符串或非数值值{@literal> = 1和<= 4} <LI> severity fieldName(如果已定
     * 义)为null,或者不是数字值{@literal> = 0和<= 6} <br> <li>如果定义了persistPolicy fieldName,则为null或不是以下字符串之一：<br>"OnUpd
     * ate","OnTimer" "NoMoreOftenThan","OnUnregister","Always","Never"。
     * <LI> name和descriptorType fieldNames未定义,或为null或空,或不为String。这些String值不能区分大小写。这些String值不能区分大小写。<br>。
     * </UL>
     * 
     * 
     * @exception RuntimeOperationsException If the validity checking
     * fails for any reason, this exception will be thrown.
     */

    public synchronized boolean isValid() throws RuntimeOperationsException {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "isValid()", "Entry");
        }
        // verify that the descriptor is valid, by iterating over each field...

        Set<Map.Entry<String, Object>> returnedSet = descriptorMap.entrySet();

        if (returnedSet == null) {   // null descriptor, not valid
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                MODELMBEAN_LOGGER.logp(Level.FINEST,
                        DescriptorSupport.class.getName(),
                        "isValid()", "Returns false (null set)");
            }
            return false;
        }
        // must have a name and descriptor type field
        String thisName = (String)(this.getFieldValue("name"));
        String thisDescType = (String)(getFieldValue("descriptorType"));

        if ((thisName == null) || (thisDescType == null) ||
            (thisName.equals("")) || (thisDescType.equals(""))) {
            return false;
        }

        // According to the descriptor type we validate the fields contained

        for (Map.Entry<String, Object> currElement : returnedSet) {
            if (currElement != null) {
                if (currElement.getValue() != null) {
                    // validate the field valued...
                    if (validateField((currElement.getKey()).toString(),
                                      (currElement.getValue()).toString())) {
                        continue;
                    } else {
                        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                            MODELMBEAN_LOGGER.logp(Level.FINEST,
                                    DescriptorSupport.class.getName(),
                                    "isValid()",
                                    "Field " + currElement.getKey() + "=" +
                                    currElement.getValue() + " is not valid");
                        }
                        return false;
                    }
                }
            }
        }

        // fell through, all fields OK
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "isValid()", "Returns true");
        }
        return true;
    }


    // worker routine for isValid()
    // name is not null
    // descriptorType is not null
    // getMethod and setMethod are not null
    // persistPeriod is numeric
    // currencyTimeLimit is numeric
    // lastUpdatedTimeStamp is numeric
    // visibility is 1-4
    // severity is 0-6
    // log is T or F
    // role is not null
    // class is not null
    // lastReturnedTimeStamp is numeric


    private boolean validateField(String fldName, Object fldValue) {
        if ((fldName == null) || (fldName.equals("")))
            return false;
        String SfldValue = "";
        boolean isAString = false;
        if ((fldValue != null) && (fldValue instanceof java.lang.String)) {
            SfldValue = (String) fldValue;
            isAString = true;
        }

        boolean nameOrDescriptorType =
            (fldName.equalsIgnoreCase("Name") ||
             fldName.equalsIgnoreCase("DescriptorType"));
        if (nameOrDescriptorType ||
            fldName.equalsIgnoreCase("SetMethod") ||
            fldName.equalsIgnoreCase("GetMethod") ||
            fldName.equalsIgnoreCase("Role") ||
            fldName.equalsIgnoreCase("Class")) {
            if (fldValue == null || !isAString)
                return false;
            if (nameOrDescriptorType && SfldValue.equals(""))
                return false;
            return true;
        } else if (fldName.equalsIgnoreCase("visibility")) {
            long v;
            if ((fldValue != null) && (isAString)) {
                v = toNumeric(SfldValue);
            } else if (fldValue instanceof java.lang.Integer) {
                v = ((Integer)fldValue).intValue();
            } else return false;

            if (v >= 1 &&  v <= 4)
                return true;
            else
                return false;
        } else if (fldName.equalsIgnoreCase("severity")) {

            long v;
            if ((fldValue != null) && (isAString)) {
                v = toNumeric(SfldValue);
            } else if (fldValue instanceof java.lang.Integer) {
                v = ((Integer)fldValue).intValue();
            } else return false;

            return (v >= 0 && v <= 6);
        } else if (fldName.equalsIgnoreCase("PersistPolicy")) {
            return (((fldValue != null) && (isAString)) &&
                    ( SfldValue.equalsIgnoreCase("OnUpdate") ||
                      SfldValue.equalsIgnoreCase("OnTimer") ||
                      SfldValue.equalsIgnoreCase("NoMoreOftenThan") ||
                      SfldValue.equalsIgnoreCase("Always") ||
                      SfldValue.equalsIgnoreCase("Never") ||
                      SfldValue.equalsIgnoreCase("OnUnregister")));
        } else if (fldName.equalsIgnoreCase("PersistPeriod") ||
                   fldName.equalsIgnoreCase("CurrencyTimeLimit") ||
                   fldName.equalsIgnoreCase("LastUpdatedTimeStamp") ||
                   fldName.equalsIgnoreCase("LastReturnedTimeStamp")) {

            long v;
            if ((fldValue != null) && (isAString)) {
                v = toNumeric(SfldValue);
            } else if (fldValue instanceof java.lang.Number) {
                v = ((Number)fldValue).longValue();
            } else return false;

            return (v >= -1);
        } else if (fldName.equalsIgnoreCase("log")) {
            return ((fldValue instanceof java.lang.Boolean) ||
                    (isAString &&
                     (SfldValue.equalsIgnoreCase("T") ||
                      SfldValue.equalsIgnoreCase("true") ||
                      SfldValue.equalsIgnoreCase("F") ||
                      SfldValue.equalsIgnoreCase("false") )));
        }

        // default to true, it is a field we aren't validating (user etc.)
        return true;
    }



    /**
     * <p>Returns an XML String representing the descriptor.</p>
     *
     * <p>The format is not defined, but an implementation must
     * ensure that the string returned by this method can be
     * used to build an equivalent descriptor when instantiated
     * using the constructor {@link #DescriptorSupport(String)
     * DescriptorSupport(String inStr)}.</p>
     *
     * <p>Fields which are not String objects will have toString()
     * called on them to create the value. The value will be
     * enclosed in parentheses.  It is not guaranteed that you can
     * reconstruct these objects unless they have been
     * specifically set up to support toString() in a meaningful
     * format and have a matching constructor that accepts a
     * String in the same format.</p>
     *
     * <p>If the descriptor is empty the following String is
     * returned: &lt;Descriptor&gt;&lt;/Descriptor&gt;</p>
     *
     * <p>
     *  <p>返回表示描述符的XML字符串。</p>
     * 
     *  <p>格式未定义,但实现必须确保此方法返回的字符串可用于在使用构造函数{@link #DescriptorSupport(String)DescriptorSupport(String inStr)}
     * 实例化时构建等效描述符。
     *  / p>。
     * 
     * <p>不是String对象的字段将有toString()调用它们来创建值。该值将括在括号中。
     * 不能保证您可以重建这些对象,除非它们已被专门设置为以有意义的格式支持toString(),并且具有接受相同格式的字符串的匹配构造函数。</p>。
     * 
     *  <p>如果描述符为空,则返回以下String：&lt; Descriptor&gt;&lt; / Descriptor&gt; </p>
     * 
     * 
     * @return the XML string.
     *
     * @exception RuntimeOperationsException for illegal value for
     * field Names or field Values.  If the XML formatted string
     * construction fails for any reason, this exception will be
     * thrown.
     */
    public synchronized String toXMLString() {
        final StringBuilder buf = new StringBuilder("<Descriptor>");
        Set<Map.Entry<String, Object>> returnedSet = descriptorMap.entrySet();
        for (Map.Entry<String, Object> currElement : returnedSet) {
            final String name = currElement.getKey();
            Object value = currElement.getValue();
            String valueString = null;
            /* Set valueString to non-null if and only if this is a string that
               cannot be confused with the encoding of an object.  If it
               could be so confused (surrounded by parentheses) then we
               call makeFieldValue as for any non-String object and end
            /* <p>
            /*  不能与对象的编码混淆。如果它可能如此混淆(由括号括起来),那么我们调用makeFieldValue作为任何非String对象和结束
            /* 
            /* 
               up with an encoding like "(java.lang.String/(thing))".  */
            if (value instanceof String) {
                final String svalue = (String) value;
                if (!svalue.startsWith("(") || !svalue.endsWith(")"))
                    valueString = quote(svalue);
            }
            if (valueString == null)
                valueString = makeFieldValue(value);
            buf.append("<field name=\"").append(name).append("\" value=\"")
                .append(valueString).append("\"></field>");
        }
        buf.append("</Descriptor>");
        return buf.toString();
    }

    private static final String[] entities = {
        " &#32;",
        "\"&quot;",
        "<&lt;",
        ">&gt;",
        "&&amp;",
        "\r&#13;",
        "\t&#9;",
        "\n&#10;",
        "\f&#12;",
    };
    private static final Map<String,Character> entityToCharMap =
        new HashMap<String,Character>();
    private static final String[] charToEntityMap;

    static {
        char maxChar = 0;
        for (int i = 0; i < entities.length; i++) {
            final char c = entities[i].charAt(0);
            if (c > maxChar)
                maxChar = c;
        }
        charToEntityMap = new String[maxChar + 1];
        for (int i = 0; i < entities.length; i++) {
            final char c = entities[i].charAt(0);
            final String entity = entities[i].substring(1);
            charToEntityMap[c] = entity;
            entityToCharMap.put(entity, c);
        }
    }

    private static boolean isMagic(char c) {
        return (c < charToEntityMap.length && charToEntityMap[c] != null);
    }

    /*
     * Quote the string so that it will be acceptable to the (String)
     * constructor.  Since the parsing code in that constructor is fairly
     * stupid, we're obliged to quote apparently innocuous characters like
     * space, <, and >.  In a future version, we should rewrite the parser
     * and only quote " plus either \ or & (depending on the quote syntax).
     * <p>
     *  引用字符串,以便它可以接受(String)构造函数。由于在构造函数中的解析代码是相当愚蠢的,我们不得不引用明显无害的字符,如空格,<和>。
     * 在将来的版本中,我们应该重写解析器,并且只引用"加上\或&(取决于引用语法)。
     * 
     */
    private static String quote(String s) {
        boolean found = false;
        for (int i = 0; i < s.length(); i++) {
            if (isMagic(s.charAt(i))) {
                found = true;
                break;
            }
        }
        if (!found)
            return s;
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isMagic(c))
                buf.append(charToEntityMap[c]);
            else
                buf.append(c);
        }
        return buf.toString();
    }

    private static String unquote(String s) throws XMLParseException {
        if (!s.startsWith("\"") || !s.endsWith("\""))
            throw new XMLParseException("Value must be quoted: <" + s + ">");
        final StringBuilder buf = new StringBuilder();
        final int len = s.length() - 1;
        for (int i = 1; i < len; i++) {
            final char c = s.charAt(i);
            final int semi;
            final Character quoted;
            if (c == '&'
                && (semi = s.indexOf(';', i + 1)) >= 0
                && ((quoted = entityToCharMap.get(s.substring(i, semi+1)))
                    != null)) {
                buf.append(quoted);
                i = semi;
            } else
                buf.append(c);
        }
        return buf.toString();
    }

    /**
     * Make the string that will go inside "..." for a value that is not
     * a plain String.
     * <p>
     *  为不是纯String的值创建将在"..."内部的字符串。
     * 
     * 
     * @throws RuntimeOperationsException if the value cannot be encoded.
     */
    private static String makeFieldValue(Object value) {
        if (value == null)
            return "(null)";

        Class<?> valueClass = value.getClass();
        try {
            valueClass.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            final String msg =
                "Class " + valueClass + " does not have a public " +
                "constructor with a single string arg";
            final RuntimeException iae = new IllegalArgumentException(msg);
            throw new RuntimeOperationsException(iae,
                                                 "Cannot make XML descriptor");
        } catch (SecurityException e) {
            // OK: we'll pretend the constructor is there
            // too bad if it's not: we'll find out when we try to
            // reconstruct the DescriptorSupport
        }

        final String quotedValueString = quote(value.toString());

        return "(" + valueClass.getName() + "/" + quotedValueString + ")";
    }

    /*
     * Parse a field value from the XML produced by toXMLString().
     * Given a descriptor XML containing <field name="nnn" value="vvv">,
     * the argument to this method will be "vvv" (a string including the
     * containing quote characters).  If vvv begins and ends with parentheses,
     * then it may contain:
     * - the characters "null", in which case the result is null;
     * - a value of the form "some.class.name/xxx", in which case the
     * result is equivalent to `new some.class.name("xxx")';
     * - some other string, in which case the result is that string,
     * without the parentheses.
     * <p>
     * 从由toXMLString()生成的XML中解析字段值。给定包含<field name ="nnn"value ="vvv">的描述符XML,此方法的参数将为"vvv"(包含引号字符的字符串)。
     * 如果vvv以括号开头和结尾,则它可能包含： - 字符"null",在这种情况下,结果为null; - 形式"some.class.name/xxx"的值,在这种情况下,结果等同于new some.cla
     * ss.name("xxx")'; - 一些其他字符串,在这种情况下的结果是字符串,没有括号。
     * 从由toXMLString()生成的XML中解析字段值。给定包含<field name ="nnn"value ="vvv">的描述符XML,此方法的参数将为"vvv"(包含引号字符的字符串)。
     * 
     */
    private static Object parseQuotedFieldValue(String s)
            throws XMLParseException {
        s = unquote(s);
        if (s.equalsIgnoreCase("(null)"))
            return null;
        if (!s.startsWith("(") || !s.endsWith(")"))
            return s;
        final int slash = s.indexOf('/');
        if (slash < 0) {
            // compatibility: old code didn't include class name
            return s.substring(1, s.length() - 1);
        }
        final String className = s.substring(1, slash);

        final Constructor<?> constr;
        try {
            ReflectUtil.checkPackageAccess(className);
            final ClassLoader contextClassLoader =
                Thread.currentThread().getContextClassLoader();
            final Class<?> c =
                Class.forName(className, false, contextClassLoader);
            constr = c.getConstructor(new Class<?>[] {String.class});
        } catch (Exception e) {
            throw new XMLParseException(e,
                                        "Cannot parse value: <" + s + ">");
        }
        final String arg = s.substring(slash + 1, s.length() - 1);
        try {
            return constr.newInstance(new Object[] {arg});
        } catch (Exception e) {
            final String msg =
                "Cannot construct instance of " + className +
                " with arg: <" + s + ">";
            throw new XMLParseException(e, msg);
        }
    }

    /**
     * Returns a human readable string representing the
     * descriptor.  The string will be in the format of
     * "fieldName=fieldValue,fieldName2=fieldValue2,..."<br>
     *
     * If there are no fields in the descriptor, then an empty String
     * is returned.<br>
     *
     * If a fieldValue is an object then the toString() method is
     * called on it and its returned value is used as the value for
     * the field enclosed in parenthesis.
     *
     * <p>
     *  返回一个表示描述符的可读字符串。该字符串的格式为"fieldName = fieldValue,fieldName2 = fieldValue2,..."<br>
     * 
     *  如果描述符中没有字段,则返回一个空字符串。<br>
     * 
     *  如果fieldValue是一个对象,那么将调用toString()方法,其返回值将用作括号中的字段的值。
     * 
     * 
     * @exception RuntimeOperationsException for illegal value for
     * field Names or field Values.  If the descriptor string fails
     * for any reason, this exception will be thrown.
     */
    @Override
    public synchronized String toString() {
        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "toString()", "Entry");
        }

        String respStr = "";
        String[] fields = getFields();

        if ((fields == null) || (fields.length == 0)) {
            if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
                MODELMBEAN_LOGGER.logp(Level.FINEST,
                        DescriptorSupport.class.getName(),
                        "toString()", "Empty Descriptor");
            }
            return respStr;
        }

        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "toString()", "Printing " + fields.length + " fields");
        }

        for (int i=0; i < fields.length; i++) {
            if (i == (fields.length - 1)) {
                respStr = respStr.concat(fields[i]);
            } else {
                respStr = respStr.concat(fields[i] + ", ");
            }
        }

        if (MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
            MODELMBEAN_LOGGER.logp(Level.FINEST,
                    DescriptorSupport.class.getName(),
                    "toString()", "Exit returning " + respStr);
        }

        return respStr;
    }

    // utility to convert to int, returns -2 if bogus.

    private long toNumeric(String inStr) {
        try {
            return java.lang.Long.parseLong(inStr);
        } catch (Exception e) {
            return -2;
        }
    }


    /**
     * Deserializes a {@link DescriptorSupport} from an {@link
     * ObjectInputStream}.
     * <p>
     *  从{@link ObjectInputStream}反序列化{@link DescriptorSupport}。
     * 
     */
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField fields = in.readFields();
        Map<String, Object> descriptor = cast(fields.get("descriptor", null));
        init(null);
        if (descriptor != null) {
            descriptorMap.putAll(descriptor);
        }
    }


    /**
     * Serializes a {@link DescriptorSupport} to an {@link ObjectOutputStream}.
     * <p>
     *  将{@link DescriptorSupport}序列化为{@link ObjectOutputStream}。
     * 
     */
    /* If you set jmx.serial.form to "1.2.0" or "1.2.1", then we are
       bug-compatible with those versions.  Specifically, field names
       are forced to lower-case before being written.  This
       contradicts the spec, which, though it does not mention
       serialization explicitly, does say that the case of field names
       is preserved.  But in 1.2.0 and 1.2.1, this requirement was not
       met.  Instead, field names in the descriptor map were forced to
       lower case.  Those versions expect this to have happened to a
       descriptor they deserialize and e.g. getFieldValue will not
       find a field whose name is spelt with a different case.
    /* <p>
    /* bug与这些版本兼容。具体来说,字段名称在写入之前强制为小写。这与规范相矛盾,虽然它没有明确提及序列化,但确实保留了字段名的情况。但是在1.2.0和1.2.1中,这个要求没有得到满足。
    /* 相反,描述符映射中的字段名被强制为小写。这些版本期望这发生在他们反序列化的描述符上。 getFieldValue将不会找到一个名称拼写与不同大小写的字段。
    /* 
    */
    private void writeObject(ObjectOutputStream out) throws IOException {
        ObjectOutputStream.PutField fields = out.putFields();
        boolean compat = "1.0".equals(serialForm);
        if (compat)
            fields.put("currClass", currClass);

        /* Purge the field "targetObject" from the DescriptorSupport before
         * serializing since the referenced object is typically not
         * serializable.  We do this here rather than purging the "descriptor"
         * variable below because that HashMap doesn't do case-insensitivity.
         * See CR 6332962.
         * <p>
         */
        SortedMap<String, Object> startMap = descriptorMap;
        if (startMap.containsKey("targetObject")) {
            startMap = new TreeMap<String, Object>(descriptorMap);
            startMap.remove("targetObject");
        }

        final HashMap<String, Object> descriptor;
        if (compat || "1.2.0".equals(serialForm) ||
                "1.2.1".equals(serialForm)) {
            descriptor = new HashMap<String, Object>();
            for (Map.Entry<String, Object> entry : startMap.entrySet())
                descriptor.put(entry.getKey().toLowerCase(), entry.getValue());
        } else
            descriptor = new HashMap<String, Object>(startMap);

        fields.put("descriptor", descriptor);
        out.writeFields();
    }

}
