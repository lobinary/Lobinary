package com.lobinary.java.jdk.jdk16.script;

import java.io.File;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 * <pre>
 * 	http://www.ibm.com/developerworks/cn/java/j-lo-jse66/index.html
 * </pre>
 *
 * @ClassName: 执行JS
 * @author 919515134@qq.com
 * @date 2016年10月10日 上午11:41:12
 * @version V1.0.0
 */
public class 执行JS {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval("print ('Hello World')");
        engine.eval("var a = 1; a += a;print (a)");
        
    }
}
