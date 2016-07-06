/*
ID: lobianr1
LANG: JAVA
TASK: ride
*/
import java.io.*;
import java.util.*;

public class ride {
  public static void main (String [] args) throws IOException {
    @SuppressWarnings("resource")
	BufferedReader f = new BufferedReader(new FileReader("ride.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));
    StringTokenizer st = new StringTokenizer(f.readLine());
    String s1 = st.nextToken(); 
    System.out.println("s1"+s1);
    char[] ca = s1.toCharArray();
    int sum = 1;
    for (int i:ca) {
    	int j = i-64;
    	System.out.println(j);
		sum*=j;
	}
    int ys1 = sum%47;
    st = new StringTokenizer(f.readLine());
    String s2 = st.nextToken();
    System.out.println("s2"+s2);
    char[] ca2 = s2.toCharArray();
    int sum2 = 1;
    for (int i:ca2) {
    	int j = i-64;
    	System.out.println(j);
		sum2*=j;
	}
    System.out.println(sum+":"+sum2);
    int ys2 = sum2%47;
    System.out.println(ys1+":"+ys2);
    if(ys2==ys1){
    	out.println("GO");          
    }else{
    	out.println("STAY"); 
    }
    out.close();                                  // close the output file
  }
}