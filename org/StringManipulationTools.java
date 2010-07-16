/*******************************************************************************
 * 
 *    Copyright (c) 2003-2009 Plant Bioinformatics Group, IPK Gatersleben
 * 
 *******************************************************************************/
/*
 * Created on Jul 16, 2010 by Christian Klukas
 */

package org;

import java.util.ArrayList;
import java.util.List;

/**
 * @author klukas
 *
 */
public class StringManipulationTools implements HelperClass {


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
    
	public static String removeHTMLtags(String textWithHtmlTags) {
		if (textWithHtmlTags==null)
			return null;
		String res = StringManipulationTools.removeTags(textWithHtmlTags, "<", ">");
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
			if (tagB.length()>0 && tagBpos>0) {
				textWithHtmlTags = textWithHtmlTags.substring(0, tagApos) + textWithHtmlTags.substring(tagBpos);
				tagApos = textWithHtmlTags.indexOf(tagA);
			} else {
				textWithHtmlTags = textWithHtmlTags.substring(0, tagApos);
				tagApos = textWithHtmlTags.indexOf(tagA);
			}
		}
		if (tagB.length()>0 && textWithHtmlTags.indexOf(tagB)>=0)
			textWithHtmlTags = textWithHtmlTags.substring(textWithHtmlTags.indexOf(tagB)+tagB.length());
		return textWithHtmlTags; 
	}

	/**
	 * Removes the tags from a html-text and gives back the striped text.
	 * 
	 * @param textWithHtmlTags the text with html tags
	 * @param tagA The left tag (e.g. <a>)
	 * @param tagB The right tag (e.g. </a>)
	 * 
	 * @return The array list< string>, where get(0) is the striped text and all other are the striped texts
	 */
	public static ArrayList<String> removeTagsGetTextAndRemovedTexts(String textWithHtmlTags, String tagA, String tagB) {
		ArrayList<String> tu = new ArrayList<String>();
		if (textWithHtmlTags==null) 
			return null;
	
		tu.add(textWithHtmlTags);
		int tagApos = tu.get(0).indexOf(tagA);
		while (tagApos>=0) {
			int tagBpos = tu.get(0).indexOf(tagB, tagApos+tagB.length())+tagB.length();
			if (tagBpos>0) {
				tu.add(tu.get(0).substring(tagApos+tagA.length(),tagBpos-tagB.length()));
				tu.set(0,tu.get(0).substring(0, tagApos) + tu.get(0).substring(tagBpos));
				tagApos = tu.get(0).indexOf(tagA);
			} else {
				tu.add(tu.get(0).substring(tagApos+1));
				tu.set(0,tu.get(0).substring(0, tagApos));
				tagApos = tu.get(0).indexOf(tagA);
			}
		}
		if (tu.get(0).indexOf(tagB)>=0)
			tu.set(0,tu.get(0).substring(tu.get(0).indexOf(tagB)+tagB.length()));
		
		
		return tu; 
	}

	public static String getWordWrap(String desc, int width) {
		String[] words = desc.split(" ");
		String result = "";
		int column = 0;
		for (int i=0; i<words.length; i++) {
			if (i>0 && column+words[i].length()>width) {
				result = result+"<br>"+words[i];
				column = words[i].length();
			} else
				if (i>0) {
					result = result + " " + words[i];
					column += words[i].length();
				} else {
					result = words[0];
					column = words[0].length();
				}
		}
		return result;
	}

	public static String getWordWrap(String[] desc, int width) {
		StringBuilder sb = new StringBuilder();
		for (String s : desc) {
			sb.append(getWordWrap(s, width));
		}
		return sb.toString();
	}

	/**
	 * @param mapName
	 * @return
	 */
	final static String[] numbers = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public static String removeNumbersFromString(String s) {
		for (String r : numbers)
			 s= stringReplace(s, r, "");
		return s;
	}

	public static String getNumbersFromString(String s) {
		StringBuilder res = new StringBuilder();
		for (Character c : s.toCharArray()) {
			for (String n : numbers) {
				if (n.equals(c+"")) {
					res.append(n);
					break;
				}
			}
		}
		return res.toString();
	}

	public static List<Integer> getAllNumbersFromString(String str) {
	    
	    if (str == null || str.length() == 0) {
	        return null;
	    }
		 ArrayList<Integer> ints = new ArrayList<Integer>();
	
	    StringBuffer strBuff = new StringBuffer();
	    char c;
	    
	    for (int i = 0; i < str.length() ; i++) {
	        c = str.charAt(i);
	        
	        if (Character.isDigit(c))
	        	strBuff.append(c);
	        else
	        	if(strBuff.length()>0) {
		        	ints.add(new Integer(strBuff.toString()));
		        	strBuff = new StringBuffer();
	        }
	    }
	    return ints;
	}

	public static void addOnAddonLoadingFinishedAction(Runnable runnable) {
		if (ErrorMsg.getAppLoadingStatus()==ApplicationStatus.ADDONS_LOADED)
			runnable.run();
		else
			ErrorMsg.finishedAddonLoadingListeners.add(runnable);
	}
}
