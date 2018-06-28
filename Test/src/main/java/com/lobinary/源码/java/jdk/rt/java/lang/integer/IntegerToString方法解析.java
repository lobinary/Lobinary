package com.lobinary.源码.java.jdk.rt.java.lang.integer;

import java.lang.Integer;

public class IntegerToString方法解析 {

    public static void main(String[] args) {
    	System.out.println("start");
    	
    	System.out.println(Integer.toString(1000000));
    	
    	
    	
    	
    	char[] 字符串数组 = new char[7];
		getChars(1000000,7,字符串数组);
		System.out.println(字符串数组);
		System.out.println(1234567/100);
		System.out.println( DigitOnes[65]);
		System.out.println(12345/10*10);
		System.out.println("end");
	}
    
    final static char [] DigitOnes = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            } ;

    final static char [] DigitTens = {
        '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
        '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
        '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
        '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
        '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
        '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
        '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
        '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
        '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
        '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
        } ;
    /**
     * All possible chars for representing a number as a String
     * <p>
     *  用于将数字表示为字符串的所有可能的字符
     * 
     */
    final static char[] digits = {
        '0' , '1' , '2' , '3' , '4' , '5' ,
        '6' , '7' , '8' , '9' , 'a' , 'b' ,
        'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
        'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
        'o' , 'p' , 'q' , 'r' , 's' , 't' ,
        'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    /**
     * Places characters representing the integer i into the
     * character array buf. The characters are placed into
     * the buffer backwards starting with the least significant
     * digit at the specified index (exclusive), and working
     * backwards from there.
     *
     * Will fail if i == Integer.MIN_VALUE
     * <p>
     *  将表示整数i的字符放入字符数组buf中。字符从指定索引(独占)处的最低有效数字开始向后放置到缓冲区中,并从那里向后工作。
     * 
     *  如果i == Integer.MIN_VALUE将失败
     * 
     */
    static void getChars(int int数据, int 开始指针, char[] 字符串数组) {
        int 被截取后的数字, 截取的数字;
        int 字符串数组指针 = 开始指针;
        char 正负数标识 = 0;

      //提取符号位，并将i转换成正数
        if (int数据 < 0) {
            正负数标识 = '-';
            int数据 = -int数据;
        }

        // Generate two digits per iteration
        while (int数据 >= 65536) {//如果是16位到32位的int值			1234567
            被截取后的数字 = int数据 / 100;								//q=  12345
        // really: r = i - (q * 100);
            截取的数字 = int数据 - ((被截取后的数字 << 6) + (被截取后的数字 << 5) + (被截取后的数字 << 2));	//r=1234567-(12345*100) = 1234567-1234500=67;
            int数据 = 被截取后的数字;										//int数据=12345
            			
            字符串数组 [--字符串数组指针] = DigitOnes[截取的数字];		//[][][][][][][7]
            字符串数组 [--字符串数组指针] = DigitTens[截取的数字];		//[][][][][][6][7]
        }

        // Fall thru to fast mode for smaller numbers
//         assert(int数据 <= 65536, int数据);
        //int数据=12345 
        for (;;) {
        	//12345-12340=5
        	//12345/10*10
        	//
            被截取后的数字 = (int数据 * 52429) >>> (16+3);	//12345/10
            //  0010 0110 1001 0100 0000 1001 1010 0101
            //	0000 0000 0000 0000 0000 0100 1101 0010 //100 0000 1001 1010 0101
            //	被截取后的数字 = 1234
            //  截取的数字 = 12345 - ((1234<<3)+(1234<<1))=12345-(9872+2468)=12345-12340=5
            截取的数字 = int数据 - ((被截取后的数字 << 3) + (被截取后的数字 << 1));  // r = i-(q*10) ...
            字符串数组 [--字符串数组指针] = digits [截取的数字];//[][][][][5][6][7]
            int数据 = 被截取后的数字;//int数据 = 1234
            if (int数据 == 0) break;
        }
        if (正负数标识 != 0) {
            字符串数组 [--字符串数组指针] = 正负数标识;
        }
    }
}
