package com.lobinary.java.jdk.jdk16.instrumentation;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Premain { 
    public static void premain(String agentArgs, Instrumentation inst) 
            throws ClassNotFoundException, UnmodifiableClassException { 
        inst.addTransformer(new Transformer()); 
    } 
 }