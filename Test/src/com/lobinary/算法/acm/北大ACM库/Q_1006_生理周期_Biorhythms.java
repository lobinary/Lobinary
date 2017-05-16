package com.lobinary.算法.acm.北大ACM库;

import java.util.Scanner;

import com.lobinary.算法.acm.AU;

/**
 * 
   传说西汉大将韩信，由于比较年轻，开始他的部下对他不很佩服。有一次阅兵时，韩信要求士兵分三路纵队，结果末尾多2人，改成五路纵队，结果末尾多3人，再改成七路纵队，结果又余下2人，
   后来下级军官向他报告共有士兵2395人，韩信立即笑笑说不对（因2395除以3余数是1，不是2），
   由于已经知道士兵总人数在2300?/FONT>2400之间，所以韩信根据23，128，233，------，每相邻两数的间隔是105，
   便立即说出实际人数应是2333人（因2333=128+20χ105+105，它除以3余2，除以5余3，除以7余2）。
   这样使下级军官十分敬佩，这就是韩信点兵的故事。 

   简化：已知 n%3=2,n%5=3,n%7=2,求n。 
   再看我们这道题，读入p,e,i,d 4个整数，已知(n+d)%23=p; (n+d)%28=e; (n+d)%33=i ,求n 。 
   是不是一样呢？ 

   呵呵，确实一样。想到这里觉得很兴奋。但是韩信是怎么计算出结果的呢？ 
   随便google了一下，原来这个东西叫“中国剩余定理”，《孙子算经》中就有计算方法。 
   韩信应该是这样算的： 
      因为n%3=2,n%5=3,n%7=2且3，5，7互质 
      使5×7被3除余1，用35×2=70； 
       使3×7被5除余1，用21×1=21； 
       使3×5被7除余1，用15×1=15。 
      （70×2+21×3+15×2）%（3×5×7）=23 

   同样，这道题也应该是： 
       使33×28被23除余1，用33×28×8=5544； 
       使23×33被28除余1，用23×33×19=14421； 
       使23×28被33除余1，用23×28×2=1288。 
      （5544×p+14421×e+1288×i）%（23×28×33）=n+d 
       n=（5544×p+14421×e+1288×i-d）%（23×28×33）
       由于我们面对的是计算机，所以以上那些很大的数字，可以单独写程序让电脑在近乎0的时候内求出:) 为什么要单独写呢？嘿嘿，为了主程序的效率着想
 * 该问题网络解决方案连接：http://www.cppblog.com/AClayton/archive/2007/09/14/32186.html
 * 
 * @see 
 * @author Lobinary
 *
 */
public class Q_1006_生理周期_Biorhythms {
	
	public static void main(String[] args) {
		AU.check();
//		Scanner cin = new Scanner(System.in);
//		run(cin);
	}
	
	public static void run(Scanner cin){
		int caseNum = 0;
		while(cin.hasNext()){
			caseNum++;
			//它们的周期长度为23天、28天和33天
			int p = cin.nextInt()%23;//23
			int e = cin.nextInt()%28;//28
			int i = cin.nextInt()%33;//33
			int d = cin.nextInt();
			if(p==-1&&e==-1&&i==-1&&d==-1){
				break;
			}
			int m = -1;
			while(true){
				m++;
//				int ti = (i+33*m)%365;
				int p高潮日期 = i+33*m;
				boolean tp = (p高潮日期-p)%23==0;
				boolean te = (p高潮日期-e)%28==0;
				int 当前日期 = p高潮日期-d;
				if(tp&&te&&当前日期>d){
//					System.out.println("Case "+caseNum+": the next triple peak occurs in "+当前日期+" days.");
					System.out.println("Case " + caseNum     
		                    + ": the next triple peak occurs in " + 当前日期     
		                    + " days.");   
					break;
				}
			}
		}
		
	}

}
