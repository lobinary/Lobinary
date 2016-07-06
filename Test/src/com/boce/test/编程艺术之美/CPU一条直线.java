package com.boce.test.编程艺术之美;

public class CPU一条直线 {
	
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println(getCpuRatioForWindows());
	}
	
	 private static double getCpuRatioForWindows() {   
	        try {   
	            String procCmd = System.getenv("windir")   
	                    + "//system32//wbem//wmic.exe process get Caption,CommandLine,"  
	                    + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";   
	            // 取进程信息   
	            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));   
	            Thread.sleep(CPUTIME);   
	            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));   
	            if (c0 != null && c1 != null) {   
	                long idletime = c1[0] - c0[0];   
	                long busytime = c1[1] - c0[1];   
	                return Double.valueOf(   
	                		100 * (busytime) / (busytime + idletime))   
	                        .doubleValue();   
	            } else {   
	                return 0.0;   
	            }   
	        } catch (Exception ex) {   
	            ex.printStackTrace();   
	            return 0.0;   
	        }   
	    }   
	  

}
