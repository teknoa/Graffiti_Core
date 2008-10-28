/*******************************************************************************
 * 
 *    Copyright (c) 2003-2007 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/

package org;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

public class ErrorMsg {
    public static final String Unicode = "UTF-8";
    
	private static LinkedList<String> errorMessages=new LinkedList<String>();
    private static LinkedList<String> errorMessagesShort=new LinkedList<String>();
    private static String statusMsg = null;

	 
	 public static DecimalFormat getDecimalFormat(String pattern) {
	    	pattern = ErrorMsg.stringReplace(pattern, ",", "");
	    	NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
	    	DecimalFormat df = (DecimalFormat)nf;
	    	df.applyPattern(pattern);		
	    	return df;
		}
    /**
     * Adds a errorMessage to a global list.
     * The error messages can be retrieved with <code>getErrorMessages</code>
     * and cleared with <code>clearErrorMessages</code>.
     * @param errorMsg
     */
    public synchronized static void addErrorMessage(String errorMsg) {
    	synchronized(errorMessages) {
    		StackTraceElement[] stack = Java_1_5_compatibility.getStackFrame();
    		String res;
    		String firstMethod = "";
    		if (stack==null) {
    		    res="<br><font color=\"gray\"><code>No Stack Information (Running on Java 1.4 or lower)</code></font><hr>";
    		} else {
	    		res="<br><font color=\"gray\">Stack:<br><small><small><code>";
	    		boolean thisMethodFound=false;
	    		for (int i=0; i<stack.length; i++) {
	    			if (stack[i].getLineNumber()<0) continue;
	    			if (thisMethodFound) {
	    				String methodName=stack[i].getMethodName();
	    				if (methodName==null || methodName.length()<=0)
	    					methodName=stack[i].getClass().getName(); // if methodname is empty, the constructor caused the problem
	    				res=res+"     Line: "+stack[i].getLineNumber()+" Method: "+stack[i].getClassName()+"/"+methodName+"<br>";
	    				if (firstMethod.length()<=0 && methodName!=null && !methodName.endsWith("addErrorMessage")) {
	    					firstMethod = ", Line "+stack[i].getLineNumber()+" Method "+stack[i].getClassName()+"/"+methodName;
	    				}
	    			}
	    			if (stack[i].getMethodName().equalsIgnoreCase("addErrorMessage")) thisMethodFound=true;
	    		}
	    		res=res+"</code></small></small></font><hr>";
    		}
    		String err="<code>Error: "+errorMsg+"</code>"+res;
    		if (!errorMessages.contains(err))
    		    errorMessages.add(err);
    		synchronized (errorMessagesShort) {
    			if (firstMethod.length()>0)
    				firstMethod = ", "+firstMethod;
        		if (!errorMessagesShort.contains(errorMsg+firstMethod));
        		    errorMessagesShort.add(errorMsg+firstMethod);
			}
    	}
    }
	 
	 public synchronized static void setStatusMessage(String statusMsg) {
	    	synchronized(errorMessages) {
				ErrorMsg.statusMsg = statusMsg;
	    	}
	 }
    
    /**
     * Removes the current error messages. E.g. after showing them to the user.
     */
    public synchronized static void clearErrorMessages() {
    	synchronized(errorMessages) {
    		errorMessages.clear();
			statusMsg = null;
    	}
    	synchronized (errorMessagesShort) {
    		errorMessagesShort.clear();
		}
    }

	/**
	 * Returns pending error messages that were not shown to the user 
	 * immediatly.
	 * @return Pending Error Messages
	 */
	public synchronized static String[] getErrorMessages() {
		synchronized(errorMessages) {
			int statusAvail = 0;
			if (statusMsg!=null && errorMessages.size()>0)
				statusAvail = 1;
			
			String[] result = new String[errorMessages.size()+statusAvail];
			if (statusMsg!=null && errorMessages.size()>0)
				result[0] = "Last Status: "+statusMsg;
			int i=0;
			for (Iterator<String> it=errorMessages.iterator(); it.hasNext(); ) {
				result[statusAvail+(i++)]= it.next();
			}
			return result;
		}
	}
	
	public synchronized static String[] getErrorMessagesShort() {
		synchronized(errorMessagesShort) {
			String[] result = new String[errorMessagesShort.size()];
			int i=0;
			for (Iterator<String> it=errorMessagesShort.iterator(); it.hasNext(); ) {
				String s = it.next();
				if (s!=null && s.length()>0) {
					s = ErrorMsg.stringReplace(s, "\"", "");
					s = ErrorMsg.stringReplace(s, ">", "");
					s = ErrorMsg.stringReplace(s, "<", "");
					s = ErrorMsg.stringReplace(s, "#", "");
					s = ErrorMsg.stringReplace(s, "?", "");
					s = ErrorMsg.stringReplace(s, "&", "");
				} else
					s = "";
				result[(i++)] = s;
			}
			return result;
		}
	}

    /**
     * @return
     */
    public synchronized static String getErrorMessagesAsXML() {
        String errorTag="error";
        String[] errmsg=getErrorMessages();
        String res="";
        if (errmsg!=null)
            for (int i=0; i<errmsg.length; i++)
                res+="<"+errorTag+">"+UnicodeToHtml(errmsg[i])+"</"+errorTag+">";
        return "<errormessages>"+res+"</errormessages>";
    }
    
    /**
	 * @param html
	 * @return
	 */
	public synchronized static String htmlToUnicode(String html) {
		String uni = html;
		int p = uni.indexOf("&#"); //$NON-NLS-1$
		while (p >= 0) {
			String s = uni.substring(p + 1);
			int p2 = s.indexOf(";"); //$NON-NLS-1$
			String code = s.substring(1, p2);
			int ic;
			try {
				ic = Integer.parseInt(code);
			} catch(Exception ee) {
				System.err.println("Invalid String: "+html);
				break;
			}
			char c = (char) ic;
			uni = stringReplace(uni, "&#" + code + ";", c + ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			p = uni.indexOf("&#"); //$NON-NLS-1$
		}
		return uni;
	}
	
	public synchronized static String UnicodeToHtml(String unicodeText) {
	    StringBuffer result = new StringBuffer();
	    char[] characters = unicodeText.toCharArray();
        for (int i=0; i<characters.length; i++) {
            char curChar = characters[i];
            if (curChar<128 && Character.isLetterOrDigit(curChar)) {
                result.append(curChar);
            } else {
                String html="&#"+new Integer(curChar).toString()+";";
                while (html.length()<8) html=stringReplace(html, "&#", "&#0");
                result.append(html);
            }
        }
        return result.toString();
	}
	
	public synchronized static String UnicodeToURLsyntax(String unicodeText) {
	    StringBuffer result = new StringBuffer();
	    char[] characters = unicodeText.toCharArray();
        for (int i=0; i<characters.length; i++) {
            char curChar = characters[i];
            if (curChar<128 && Character.isLetterOrDigit(curChar)) {
                result.append(curChar);
            } else {
                String html="%"+new Integer(curChar).toString();
                result.append(html);
            }
        }
        return result.toString();
	}
	
	/**
     * 
     * Replace occurrences of a substring.
     * http://ostermiller.org/utils/StringHelper.html
     *
     * StringHelper.replace("1-2-3", "-", "|");<br>
     * result: "1|2|3"<br>
     * StringHelper.replace("-1--2-", "-", "|");<br>
     * result: "|1||2|"<br>
     * StringHelper.replace("123", "", "|");<br>
     * result: "123"<br>
     * StringHelper.replace("1-2---3----4", "--", "|");<br>
     * result: "1-2|-3||4"<br>
     * StringHelper.replace("1-2---3----4", "--", "---");<br>
     * result: "1-2----3------4"<br>
     *
     * @param s String to be modified.
     * @param find String to find.
     * @param replace String to replace.
     * @return a string with all the occurrences of the string to find replaced.
     * @throws NullPointerException if s is null.
     *
     */
    public static String stringReplace(String s, String find, String replace){
        int findLength;
        // the next statement has the side effect of throwing a null pointer
        // exception if s is null.
        int stringLength = s.length();
        if (find == null || (findLength = find.length()) == 0){
            // If there is nothing to find, we won't try and find it.
            return s;
        }
        if (replace == null){
            // a null string and an empty string are the same
            // for replacement purposes.
            replace = ""; //$NON-NLS-1$
        }
        int replaceLength = replace.length();

        // We need to figure out how long our resulting string will be.
        // This is required because without it, the possible resizing
        // and copying of memory structures could lead to an unacceptable runtime.
        // In the worst case it would have to be resized n times with each
        // resize having a O(n) copy leading to an O(n^2) algorithm.
        int length;
        if (findLength == replaceLength){
            // special case in which we don't need to count the replacements
            // because the count falls out of the length formula.
            length = stringLength;
        } else {
            int count;
            int start;
            int end;

            // Scan s and count the number of times we find our target.
            count = 0;
            start = 0;
            while((end = s.indexOf(find, start)) != -1) {
                count++;
                start = end + findLength;
            }
            if (count == 0){
                // special case in which on first pass, we find there is nothing
                // to be replaced.  No need to do a second pass or create a string buffer.
                return s;
            }
            length = stringLength - (count * (findLength - replaceLength));
        }

        int start = 0;
        int end = s.indexOf(find, start);
        if (end == -1){
            // nothing was found in the string to replace.
            // we can get this if the find and replace strings
            // are the same length because we didn't check before.
            // in this case, we will return the original string
            return s;
        }
        // it looks like we actually have something to replace
        // *sigh* allocate memory for it.
        StringBuffer sb = new StringBuffer(length);

        // Scan s and do the replacements
        while (end != -1) {
            sb.append(s.substring(start, end).toString());
            sb.append(replace.toString());
            start = end + findLength;
            end = s.indexOf(find, start);
        }
        end = stringLength;
        sb.append(s.substring(start, end).toString());

        return (sb.toString());
    }

	public static void addErrorMessage(Exception e) {
		addErrorMessage(e.toString()+"<p><p>"+e.getLocalizedMessage());
		// if (ReleaseInfo.getRunningReleaseStatus()==Release.DEBUG)
			e.printStackTrace();
	}

	public static int getErrorMsgCount() {
		return errorMessages.size();
	}

	public static String getLastStatusMessage() {
		return statusMsg;
	}

	public static String getHexFromColor(Color c) {
		String r = Integer.toHexString(c.getRed());
      String g = Integer.toHexString(c.getGreen());
      String b = Integer.toHexString(c.getBlue());

      if(r.length() < 2)
          r = "0" + r;

      if(g.length() < 2)
          g = "0" + g;

      if(b.length() < 2)
          b = "0" + b;

      return "#" + (r + g + b);
	}

	public static Color getColorFromHex(String colorString) {
		try {
			String r = colorString.substring(1, 3);
			String g = colorString.substring(3, 5);
			String b = colorString.substring(5, 7);
			int ri = Integer.decode("0x"+r);
			int gi = Integer.decode("0x"+g);
			int bi = Integer.decode("0x"+b);
			return new Color(ri, gi, bi);
		} catch(Exception e) {
			return Color.BLACK;
		}
	}
	
	private static boolean apploadingCompleted = false;

	public static boolean isAppLoadingCompleted() {
		return apploadingCompleted;
	}
	public static void setAppLoadingCompleted(boolean status) {
		apploadingCompleted = status;
	}
	
	public static boolean isMac() {
		return AttributeHelper.macOSrunning();
	}

	public static boolean isLinux() {
		return AttributeHelper.linuxRunning();
	}

	public static int getAccelModifier() {
		return java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		/*
		String vers = System.getProperty("os.name").toLowerCase();
	    if (vers.indexOf("mac") >= 0)
	    	return ActionEvent.META_MASK;
	    else
	    	return ActionEvent.CTRL_MASK;*/
	}

	@SuppressWarnings("unchecked")
	public static Object findParentComponent(Component c, Class searchClass) {
		if (c==null)
			return null;
		// System.out.println(c.getClass().getCanonicalName());
		if (c.getClass()==searchClass)
			return c;
		try {
			Object o = c.getClass().asSubclass(searchClass);
			if (o!=null)
				return c;
		} catch(Exception err) {
			// Component c is not of desired type
		}
		return findParentComponent(c.getParent(), searchClass);
	}
	
	public static String removeHTMLtags(String textWithHtmlTags) {
		if (textWithHtmlTags==null)
			return null;
		String res = removeTags(textWithHtmlTags, "<", ">");
		res = stringReplace(res, "&nbsp;", " ");
		res = stringReplace(res, "%20", " ");
		return res;
	}
	
	public static String removeTags(String textWithHtmlTags, String tagA, String tagB) {
		if (textWithHtmlTags==null) 
			return null;
		int tagApos = textWithHtmlTags.indexOf(tagA);
		while (tagApos>=0) {
			int tagBpos = textWithHtmlTags.indexOf(tagB, tagApos+tagB.length())+tagB.length();
			if (tagBpos>0) {
				textWithHtmlTags = textWithHtmlTags.substring(0, tagApos) + textWithHtmlTags.substring(tagBpos);
				tagApos = textWithHtmlTags.indexOf(tagA);
			} else {
				textWithHtmlTags = textWithHtmlTags.substring(0, tagApos);
				tagApos = textWithHtmlTags.indexOf(tagA);
			}
		}
		if (textWithHtmlTags.indexOf(tagB)>=0)
			textWithHtmlTags = textWithHtmlTags.substring(textWithHtmlTags.indexOf(tagB)+tagB.length());
		return textWithHtmlTags; 
	}
}