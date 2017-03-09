/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.org.apache.xalan.internal.utils;


import com.sun.org.apache.xalan.internal.XalanConstants;

/**
 * This class manages security related properties
 *
 * <p>
 *  这个类管理安全相关的属性
 * 
 */
public final class FeatureManager extends FeaturePropertyBase {

    /**
     * States of the settings of a property, in the order: default value, value
     * set by FEATURE_SECURE_PROCESSING, jaxp.properties file, jaxp system
     * properties, and jaxp api properties
     * <p>
     *  状态设置的属性,按顺序：默认值,由FEATURE_SECURE_PROCESSING设置的值,jaxp.properties文件,jaxp系统属性和jaxp api属性
     * 
     */
    public static enum State {
        //this order reflects the overriding order
        DEFAULT, FSP, JAXPDOTPROPERTIES, SYSTEMPROPERTY, APIPROPERTY
    }

    /**
     * Xalan Features
     * <p>
     *  Xalan特性
     * 
     */
    public static enum Feature {
        ORACLE_ENABLE_EXTENSION_FUNCTION(XalanConstants.ORACLE_ENABLE_EXTENSION_FUNCTION,
                "true");

        final String name;
        final String defaultValue;

        Feature(String name, String value) {
            this.name = name;
            this.defaultValue = value;
        }

        public boolean equalsName(String propertyName) {
            return (propertyName == null) ? false : name.equals(propertyName);
        }

        String defaultValue() {
            return defaultValue;
        }
    }

    /**
     * Default constructor. Establishes default values
     * <p>
     *  默认构造函数。建立默认值
     * 
     */
    public FeatureManager() {
        values = new String[Feature.values().length];
        for (Feature feature : Feature.values()) {
            values[feature.ordinal()] = feature.defaultValue();
        }
        //read system properties or jaxp.properties
        readSystemProperties();
    }


    /**
     * Check if the feature is enabled
     * <p>
     *  检查功能是否已启用
     * 
     * 
     * @param feature name of the feature
     * @return true if enabled, false otherwise
     */
    public boolean isFeatureEnabled(Feature feature) {
        return Boolean.parseBoolean(values[feature.ordinal()]);
    }

    /**
     * Check if the feature is enabled
     * <p>
     *  检查功能是否已启用
     * 
     * 
     * @param propertyName name of the feature
     * @return true if enabled, false otherwise
     */
    public boolean isFeatureEnabled(String propertyName) {
        return Boolean.parseBoolean(values[getIndex(propertyName)]);
    }

    /**
     * Get the index by property name
     * <p>
     *  按属性名称获取索引
     * 
     * 
     * @param propertyName property name
     * @return the index of the property if found; return -1 if not
     */
    public int getIndex(String propertyName){
        for (Feature feature : Feature.values()) {
            if (feature.equalsName(propertyName)) {
                return feature.ordinal();
            }
        }
        return -1;
    }

    /**
     * Read from system properties, or those in jaxp.properties
     * <p>
     *  从系统属性或jaxp.properties中读取
     */
    private void readSystemProperties() {
        getSystemProperty(Feature.ORACLE_ENABLE_EXTENSION_FUNCTION,
                XalanConstants.SP_ORACLE_ENABLE_EXTENSION_FUNCTION);
    }

}
