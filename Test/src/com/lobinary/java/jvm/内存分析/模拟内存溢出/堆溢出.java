package com.lobinary.java.jvm.内存分析.模拟内存溢出;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆溢出一般解决思路：
 * 
 * 查看泄露内存是不是有一些对象被其他对象持有，不能由GC释放
 * 如果没有泄露，那么看看是不是虚拟机内存设置的小，可以通过 -Xmx 和 -Xms 来设置最大和最小内存
 * 如果还不行，看看是不是对象生命周期过长
 * @author bin.lv
 * @since create by bin.lv 2017年9月20日 下午5:40:12
 *
 */
public class 堆溢出 {
    
    static class T{
        
    }
    
    
    public static void main(String[] args) {
        System.out.println("开始堆溢出");
        List<T> l = new ArrayList<T>();
        while(true){
            l.add(new T());
        }
    }

}
/*
 * 

开始堆溢出
[GC (Allocation Failure) [PSYoungGen: 8021K->1001K(9216K)] 8021K->5532K(19456K), 0.0384526 secs] [Times: user=0.12 sys=0.00, real=0.04 secs] 
[GC (Allocation Failure) --[PSYoungGen: 9193K->9193K(9216K)] 13724K->19426K(19456K), 0.0147086 secs] [Times: user=0.11 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 9193K->1153K(9216K)] [ParOldGen: 10232K->10176K(10240K)] 19426K->11329K(19456K), [Metaspace: 2799K->2799K(1056768K)], 0.2611932 secs] [Times: user=0.64 sys=0.00, real=0.26 secs] 
[Full GC (Ergonomics) [PSYoungGen: 8192K->7167K(9216K)] [ParOldGen: 10176K->9087K(10240K)] 18368K->16255K(19456K), [Metaspace: 2800K->2800K(1056768K)], 0.2339694 secs] [Times: user=0.55 sys=0.00, real=0.23 secs] 
[Full GC (Ergonomics) [PSYoungGen: 7628K->7530K(9216K)] [ParOldGen: 9087K->9087K(10240K)] 16716K->16618K(19456K), [Metaspace: 2800K->2800K(1056768K)], 0.1142239 secs] [Times: user=0.45 sys=0.00, real=0.12 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 7530K->7530K(9216K)] [ParOldGen: 9087K->9076K(10240K)] 16618K->16606K(19456K), [Metaspace: 2800K->2800K(1056768K)], 0.1523724 secs] [Times: user=0.58 sys=0.01, real=0.15 secs] 
java.lang.OutOfMemoryError: Java heap space
Dumping heap to C:/test\java_pid10432.hprof ...
Heap dump file created [28290809 bytes in 0.131 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    at java.util.Arrays.copyOf(Unknown Source)
    at java.util.Arrays.copyOf(Unknown Source)
    at java.util.ArrayList.grow(Unknown Source)
    at java.util.ArrayList.ensureExplicitCapacity(Unknown Source)
    at java.util.ArrayList.ensureCapacityInternal(Unknown Source)
    at java.util.ArrayList.add(Unknown Source)
    at com.lobinary.java.jvm.内存分析.模拟内存溢出.堆溢出.main(堆溢出.java:26)
Heap
 PSYoungGen      total 9216K, used 7796K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 95% used [0x00000000ff600000,0x00000000ffd9d220,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 10240K, used 9076K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 88% used [0x00000000fec00000,0x00000000ff4dd2c8,0x00000000ff600000)
 Metaspace       used 2830K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 311K, capacity 386K, committed 512K, reserved 1048576K




 * 
 * 
 * */
