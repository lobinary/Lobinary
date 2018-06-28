package com.lobinary.java.jdk.jdk16.jmx.一个没明白的程序;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.*; 
import javax.management.modelmbean.*; 
public class Main { 

    public static void main(String[] args) throws Exception{ 
        MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer(); 
        RequiredModelMBean serverMBean = 
            (RequiredModelMBean) mBeanServer.instantiate( 
            "javax.management.modelmbean.RequiredModelMBean"); 

        ObjectName serverMBeanName = 
            new ObjectName("server: id=Server"); 
        serverMBean.setModelMBeanInfo(getModelMBeanInfoForServer(serverMBeanName)); 
        Server server = new Server(); 
        serverMBean.setManagedResource(server, "ObjectReference"); 

        ObjectInstance registeredServerMBean = 
            mBeanServer.registerMBean((Object) serverMBean, serverMBeanName); 

        serverMBean.invoke("start",null, null); 

        Thread.sleep(1000); 

        System.out.println(serverMBean.getAttribute("upTime")); 
        Thread.sleep(5000); 
        System.out.println(serverMBean.getAttribute("upTime")); 
    } 

    private static ModelMBeanInfo getModelMBeanInfoForServer(ObjectName objectName) 
        throws Exception{ 
        ModelMBeanAttributeInfo[] serverAttributes = 
            new ModelMBeanAttributeInfo[1]; 
        Descriptor upTime = 
            new DescriptorSupport( 
            new String[] { 
                "name=upTime", 
                "descriptorType=attribute", 
                "displayName=Server upTime", 
                "getMethod=getUpTime",           
            }); 
            serverAttributes[0] = 
                new ModelMBeanAttributeInfo( 
                "upTime", 
                "long", 
                "Server upTime", 
                true, 
                false, 
                false, 
            upTime); 

        ModelMBeanOperationInfo[] serverOperations = 
            new ModelMBeanOperationInfo[2]; 

        Descriptor getUpTimeDesc = 
            new DescriptorSupport( 
            new String[] { 
                "name=getUpTime", 
                "descriptorType=operation", 
                "class=com.lobinary.java.jdk.jdk16.jmx.Server", 
                "role=operation"          
            }); 

        MBeanParameterInfo[] getUpTimeParms = new MBeanParameterInfo[0]; 
        serverOperations[0] = new ModelMBeanOperationInfo("getUpTime", 
            "get the up time of the server", 
            getUpTimeParms, 
            "java.lang.Long", 
            MBeanOperationInfo.ACTION, 
        getUpTimeDesc); 
    
        Descriptor startDesc = 
            new DescriptorSupport( 
            new String[] { 
                "name=start", 
                "descriptorType=operation", 
                "class=com.lobinary.java.jdk.jdk16.jmx.Server", 
                "role=operation"
            }); 
        MBeanParameterInfo[] startParms = new MBeanParameterInfo[0]; 
        serverOperations[1] = new ModelMBeanOperationInfo("start", 
            "start(): start server", 
            startParms, 
            "java.lang.Integer", 
            MBeanOperationInfo.ACTION, 
        startDesc); 

        ModelMBeanInfo serverMMBeanInfo = 
            new ModelMBeanInfoSupport( 
            "modelmbean.Server", 
            "ModelMBean for managing an Server", 
            serverAttributes, 
            null, 
            serverOperations, 
        null); 

        //Default strategy for the MBean. 
        Descriptor serverDescription = 
            new DescriptorSupport( 
            new String[] { 
                ("name=" + objectName), 
                "descriptorType=mbean", 
                ("displayName=Server"), 
                "type=modelmbean.Server", 
                "log=T", 
                "logFile=serverMX.log", 
                "currencyTimeLimit=10" }); 
        serverMMBeanInfo.setMBeanDescriptor(serverDescription); 
        return serverMMBeanInfo; 
    }
}