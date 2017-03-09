/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.management.loading;

import static com.sun.jmx.defaults.JmxProperties.MLET_LOGGER;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * This class is used for parsing URLs.
 *
 * <p>
 *  此类用于解析URL。
 * 
 * 
 * @since 1.5
 */
class MLetParser {

/*
  * ------------------------------------------
  *   PRIVATE VARIABLES
  * ------------------------------------------
  * <p>
  *  ------------------------------------------私人变数------ ------------------------------------
  * 
  */

    /**
     * The current character
     * <p>
     *  当前字符
     * 
     */
    private int c;

    /**
     * Tag to parse.
     * <p>
     *  标记解析。
     * 
     */
    private static String tag = "mlet";


  /*
  * ------------------------------------------
  *   CONSTRUCTORS
  * ------------------------------------------
  * <p>
  *  ------------------------------------------建筑师------- -----------------------------------
  * 
  */

    /**
     * Create an MLet parser object
     * <p>
     *  创建MLet解析器对象
     * 
     */
    public MLetParser() {
    }

    /*
     * ------------------------------------------
     *   PUBLIC METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------公共方法------------------------------------
     * 
     */

    /**
     * Scan spaces.
     * <p>
     *  扫描空间。
     * 
     */
    public void skipSpace(Reader in) throws IOException {
        while ((c >= 0) && ((c == ' ') || (c == '\t') || (c == '\n') || (c == '\r'))) {
            c = in.read();
        }
    }

    /**
     * Scan identifier
     * <p>
     *  扫描标识符
     * 
     */
    public String scanIdentifier(Reader in) throws IOException {
        StringBuilder buf = new StringBuilder();
        while (true) {
            if (((c >= 'a') && (c <= 'z')) ||
                ((c >= 'A') && (c <= 'Z')) ||
                ((c >= '0') && (c <= '9')) || (c == '_')) {
                buf.append((char)c);
                c = in.read();
            } else {
                return buf.toString();
            }
        }
    }

    /**
     * Scan tag
     * <p>
     *  扫描标签
     * 
     */
    public Map<String,String> scanTag(Reader in) throws IOException {
        Map<String,String> atts = new HashMap<String,String>();
        skipSpace(in);
        while (c >= 0 && c != '>') {
            if (c == '<')
                throw new IOException("Missing '>' in tag");
            String att = scanIdentifier(in);
            String val = "";
            skipSpace(in);
            if (c == '=') {
                int quote = -1;
                c = in.read();
                skipSpace(in);
                if ((c == '\'') || (c == '\"')) {
                    quote = c;
                    c = in.read();
                }
                StringBuilder buf = new StringBuilder();
                while ((c > 0) &&
                       (((quote < 0) && (c != ' ') && (c != '\t') &&
                         (c != '\n') && (c != '\r') && (c != '>'))
                        || ((quote >= 0) && (c != quote)))) {
                    buf.append((char)c);
                    c = in.read();
                }
                if (c == quote) {
                    c = in.read();
                }
                skipSpace(in);
                val = buf.toString();
            }
            atts.put(att.toLowerCase(), val);
            skipSpace(in);
        }
        return atts;
    }

    /**
     * Scan an html file for {@literal <mlet>} tags.
     * <p>
     *  扫描html文件中的{@literal <mlet>}标签。
     * 
     */
    public List<MLetContent> parse(URL url) throws IOException {
        String mth = "parse";
        // Warning Messages
        String requiresTypeWarning = "<arg type=... value=...> tag requires type parameter.";
        String requiresValueWarning = "<arg type=... value=...> tag requires value parameter.";
        String paramOutsideWarning = "<arg> tag outside <mlet> ... </mlet>.";
        String requiresCodeWarning = "<mlet> tag requires either code or object parameter.";
        String requiresJarsWarning = "<mlet> tag requires archive parameter.";

        URLConnection conn;

        conn = url.openConnection();
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                                             "UTF-8"));

        // The original URL may have been redirected - this
        // sets it to whatever URL/codebase we ended up getting
        //
        url = conn.getURL();

        List<MLetContent> mlets = new ArrayList<MLetContent>();
        Map<String,String> atts = null;

        List<String> types = new ArrayList<String>();
        List<String> values = new ArrayList<String>();

        // debug("parse","*** Parsing " + url );
        while(true) {
            c = in.read();
            if (c == -1)
                break;
            if (c == '<') {
                c = in.read();
                if (c == '/') {
                    c = in.read();
                    String nm = scanIdentifier(in);
                    if (c != '>')
                        throw new IOException("Missing '>' in tag");
                    if (nm.equalsIgnoreCase(tag)) {
                        if (atts != null) {
                            mlets.add(new MLetContent(url, atts, types, values));
                        }
                        atts = null;
                        types = new ArrayList<String>();
                        values = new ArrayList<String>();
                    }
                } else {
                    String nm = scanIdentifier(in);
                    if (nm.equalsIgnoreCase("arg")) {
                        Map<String,String> t = scanTag(in);
                        String att = t.get("type");
                        if (att == null) {
                            MLET_LOGGER.logp(Level.FINER,
                                    MLetParser.class.getName(),
                                    mth, requiresTypeWarning);
                            throw new IOException(requiresTypeWarning);
                        } else {
                            if (atts != null) {
                                types.add(att);
                            } else {
                                MLET_LOGGER.logp(Level.FINER,
                                        MLetParser.class.getName(),
                                        mth, paramOutsideWarning);
                                throw new IOException(paramOutsideWarning);
                            }
                        }
                        String val = t.get("value");
                        if (val == null) {
                            MLET_LOGGER.logp(Level.FINER,
                                    MLetParser.class.getName(),
                                    mth, requiresValueWarning);
                            throw new IOException(requiresValueWarning);
                        } else {
                            if (atts != null) {
                                values.add(val);
                            } else {
                                MLET_LOGGER.logp(Level.FINER,
                                        MLetParser.class.getName(),
                                        mth, paramOutsideWarning);
                                throw new IOException(paramOutsideWarning);
                            }
                        }
                    } else {
                        if (nm.equalsIgnoreCase(tag)) {
                            atts = scanTag(in);
                            if (atts.get("code") == null && atts.get("object") == null) {
                                MLET_LOGGER.logp(Level.FINER,
                                        MLetParser.class.getName(),
                                        mth, requiresCodeWarning);
                                throw new IOException(requiresCodeWarning);
                            }
                            if (atts.get("archive") == null) {
                                MLET_LOGGER.logp(Level.FINER,
                                        MLetParser.class.getName(),
                                        mth, requiresJarsWarning);
                                throw new IOException(requiresJarsWarning);
                            }
                        }
                    }
                }
            }
        }
        in.close();
        return mlets;
    }

    /**
     * Parse the document pointed by the URL urlname
     * <p>
     *  解析URL urlname指向的文档
     */
    public List<MLetContent> parseURL(String urlname) throws IOException {
        // Parse the document
        //
        URL url;
        if (urlname.indexOf(':') <= 1) {
            String userDir = System.getProperty("user.dir");
            String prot;
            if (userDir.charAt(0) == '/' ||
                userDir.charAt(0) == File.separatorChar) {
                prot = "file:";
            } else {
                prot = "file:/";
            }
            url =
                new URL(prot + userDir.replace(File.separatorChar, '/') + "/");
            url = new URL(url, urlname);
        } else {
            url = new URL(urlname);
        }
        // Return list of parsed MLets
        //
        return parse(url);
    }

}
