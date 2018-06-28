package com.lobinary.算法.排序;

/**
 * 归并排序
 * 
 * 
 * MERGE-SORT
 *
 */
public class 归并排序 {
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	public static int[] sort(int[] a){
		System.out.println("MergeSort Start");
		SortUtil.out(a);
		merge(a, 0, a.length-1);
		SortUtil.out(a);
		System.out.println("MergeSort End");
		return a;
	}
	
	
	
	
	public static int[] merge(int[] a,int start,int end){
	    System.out.println(a+":"+start+":"+end);
        if(end-start==1){
            if(SortUtil.compare(a[start], a[end])){
                int[] result = { a[end],a[start]};
                return result;
            }else{
                int[] result = { a[start],a[end]};
                return result;
            }
        }
        int midIndex = (end+start)/2;
        int[] preArray = merge(a,start,midIndex);
        int[] postArray = merge(a,midIndex,end);
        int preIndex = 0;
        int postIndex = 0;
        int preMax = preArray.length-1;
        int postMax = postArray.length-1;
        int resultArrayLength = preMax+postMax;
        int[] resultArray = new int[resultArrayLength];
        for (int i = 0; i < resultArray.length; i++) {
            if(SortUtil.compare(preArray[preIndex], postArray[postIndex])){
                resultArray[i] = SortUtil.fz(postArray[postIndex]);
                postIndex++;
            }else{
                resultArray[i] = SortUtil.fz(preArray[preIndex]);
                preIndex++;
            }
        }
        return resultArray;
	}


	public static void main(String[] args) {
		归并排序.sort(SortUtil.getArray());
		System.out.println("");
	}
}
