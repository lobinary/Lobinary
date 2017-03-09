/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.corba.se.spi.monitoring;

import java.util.*;

/**
 * <p>
 *
 * <p>
 * <p>
 * 
 * 
 * @author Hemanth Puttaswamy
 * </p>
 * <p>
 * StatisticsMonitoredAttribute is provided as a convenience to collect the
 * Statistics of any entity. The getValue() call will be delegated to the
 * StatisticsAccumulator set by the user.
 * </p>
 */
public class StatisticMonitoredAttribute extends MonitoredAttributeBase {


    // Every StatisticMonitoredAttribute will have a StatisticAccumulator. User
    // will use Statisticsaccumulator to accumulate the samples associated with
    // this Monitored Attribute
    private StatisticsAccumulator statisticsAccumulator;

    // Mutex is passed from the user class which is providing the sample values.
    // getValue() and clearState() is synchronized on this user provided mutex
    private Object  mutex;


  ///////////////////////////////////////
  // operations


/**
 * <p>
 * Constructs the StaisticMonitoredAttribute, builds the required
 * MonitoredAttributeInfo with Long as the class type and is always
 * readonly attribute.
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  构造StaisticMonitoredAttribute,构建所需的MonitoredAttributeInfo,Long作为类类型并且始终为只读属性。
 * </p>
 * <p>
 * 
 * 
 * @param name Of this attribute
 * </p>
 * <p>
 * @return a StatisticMonitoredAttribute
 * </p>
 * <p>
 * @param desc should provide a good description on the kind of statistics
 * collected, a good example is "Connection Response Time Stats will Provide the
 * detailed stats based on the samples provided from every request completion
 * time"
 * </p>
 * <p>
 * @param s is the StatisticsAcumulator that user will use to accumulate the
 * samples and this Attribute Object will get the computed statistics values
 * from.
 * </p>
 * <p>
 * @param mutex using which clearState() and getValue() calls need to be locked.
 * </p>
 */
    public  StatisticMonitoredAttribute(String name, String desc,
        StatisticsAccumulator s, Object mutex)
    {
        super( name );
        MonitoredAttributeInfoFactory f =
            MonitoringFactories.getMonitoredAttributeInfoFactory();
        MonitoredAttributeInfo maInfo = f.createMonitoredAttributeInfo(
                desc, String.class, false, true );

        this.setMonitoredAttributeInfo( maInfo );
        this.statisticsAccumulator = s;
        this.mutex = mutex;
    } // end StatisticMonitoredAttribute



/**
 *  Gets the value from the StatisticsAccumulator, the value will be a formatted
 *  String with the computed statistics based on the samples accumulated in the
 *  Statistics Accumulator.
 * <p>
 *  从StatisticsAccumulator获取值,该值将是一个格式化的字符串,其计算的统计信息基于累加在统计累加器中的样本。
 * 
 */
    public Object getValue( ) {
        synchronized( mutex ) {
            return statisticsAccumulator.getValue( );
        }
    }

/**
 *  Clears the state on Statistics Accumulator, After this call all samples are
 *  treated fresh and the old sample computations are disregarded.
 * <p>
 *  清除统计累加器上的状态。在此调用之后,所有样本被处理为新鲜,而旧样本计算被忽略。
 * 
 */
    public void clearState( ) {
        synchronized( mutex ) {
            statisticsAccumulator.clearState( );
        }
    }

/**
 *  Gets the statistics accumulator associated with StatisticMonitoredAttribute.
 *  Usually, the user don't need to use this method as they can keep the handle
 *  to Accumulator to collect the samples.
 * <p>
 *  获取与StatisticMonitoredAttribute关联的统计累积器。通常,用户不需要使用此方法,因为他们可以保持句柄到累加器收集样本。
 */
    public StatisticsAccumulator getStatisticsAccumulator( ) {
        return statisticsAccumulator;
    }
} // end StatisticMonitoredAttribute
