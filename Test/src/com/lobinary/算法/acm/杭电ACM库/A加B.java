package com.lobinary.算法.acm.杭电ACM库;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class A加B
{
    public static void main(String[] args) throws IOException
    {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        int a, b;
        while(in.nextToken() != StreamTokenizer.TT_EOF)
        {
            a = (int)in.nval;
            in.nextToken();
            b = (int)in.nval;
            out.println(a + b);
        }
        out.flush();
    }
}
