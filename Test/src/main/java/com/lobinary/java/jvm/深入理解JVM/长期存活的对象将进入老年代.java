package com.lobinary.java.jvm.深入理解JVM;

import com.lobinary.java.多线程.TU;

/**
 * 
 * VM params: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
 * 
 * 
 * 典型设置: 
    java -Xmx3550m -Xms3550m -Xmn2g -Xss128k
    -Xmx3550m:设置JVM最大可用内存为3550M.
    -Xms3550m:设置JVM促使内存为3550m.此值可以设置与-Xmx相同,以避免每次垃圾回收完成后JVM重新分配内存.
    -Xmn2g:设置年轻代大小为2G.整个堆大小=年轻代大小 + 年老代大小 + 持久代大小.持久代一般固定大小为64m,所以增大年轻代后,将会减小年老代大小.此值对系统性能影响较大,Sun官方推荐配置为整个堆的3/8.
    -Xss128k:设置每个线程的堆栈大小.JDK5.0以后每个线程堆栈大小为1M,以前每个线程堆栈大小为256K.更具应用的线程所需内存大小进行 调整.在相同物理内存下,减小这个值能生成更多的线程.但是操作系统对一个进程内的线程数还是有限制的,不能无限生成,经验值在3000~5000左右.
 * @author bin.lv
 * @since create by bin.lv 2017年10月12日 上午10:24:25
 *
 */
public class 长期存活的对象将进入老年代 {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        System.out.println("hello");
        byte[] allocation1,allocation2,allocation3,allocation4;
        //Eden Space:8M   From Survivor:1M    To Survivor:1M  Olde Gen:10M
        //ES:0          FS:0        TS:0        OG:0
        allocation1 = new byte[_1MB / 4];
        //ES:0.25M      FS:0        TS:0        OG:0
        allocation2 = new byte[4 * _1MB];
        //ES:4.25M      FS:0        TS:0        OG:0
        allocation3 = new byte[4 * _1MB];
        //ES:4.25M      FS:0        TS:0        OG:4M
        allocation3 = null;
        //ES:4.25M      FS:0        TS:0        OG:4M
        allocation3 = new byte[4 * _1MB];//Full GC
        //ES:4.25M      FS:0        TS:0        OG:4M
    }

    /*
     
Heap
 PSYoungGen      total 9216K, used 5993K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 73% used [0x00000000ff600000,0x00000000ffbda5f0,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 8192K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 80% used [0x00000000fec00000,0x00000000ff400020,0x00000000ff600000)
 Metaspace       used 2795K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 308K, capacity 386K, committed 512K, reserved 1048576K

[GC (Allocation Failure) --[PSYoungGen: 5828K->5828K(9216K)] 14021K->14133K(19456K), 0.0014722 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 5828K->0K(9216K)] [ParOldGen: 8304K->9239K(10240K)] 14133K->9239K(19456K), [Metaspace: 2791K->2791K(1056768K)], 0.0069215 secs] [Times: user=0.09 sys=0.02, real=0.01 secs] 
Heap
 PSYoungGen      total 9216K, used 4178K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 51% used [0x00000000ff600000,0x00000000ffa14930,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 9239K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 90% used [0x00000000fec00000,0x00000000ff505de0,0x00000000ff600000)
 Metaspace       used 2798K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 308K, capacity 386K, committed 512K, reserved 1048576K
  
     */
}
