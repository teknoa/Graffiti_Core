/*******************************************************************************
 * 
 *    Copyright (c) 2003-2007 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/
/*
 * Created on 08.06.2005 by Christian Klukas
 */
package org;

import java.io.File;
import java.util.Properties;

import javax.swing.JOptionPane;

public class ReleaseInfo {
	private static Release currentRelease = Release.DEBUG;

	public static Release getRunningReleaseStatus() {
		return currentRelease;
	}
	
	public static void setRunningReleaseStatus(Release currentReleaseStatus) {
		currentRelease = currentReleaseStatus;
	}
	
	public static boolean getIsAllowedFeature(FeatureSet fs) {
		
		try {
			String s = getAppFolder();
		} catch(Exception e) {
			if (fs==FeatureSet.GravistoJavaHelp)
				return false;
			if (fs==FeatureSet.KEGG_ACCESS)
				return false;
			if (fs==FeatureSet.KEGG_ACCESS_ENH)
				return false;
			return true;
		}
		
		switch (fs) {
			case KEGG_ACCESS :
		          if ((new File(getAppFolderWithFinalSep()+"license_kegg_accepted")).exists())
						return true;
		          else
		         	 return false;
			case KEGG_ACCESS_ENH :
				if (!(currentRelease==Release.RELEASE_PUBLIC || currentRelease==Release.KGML_EDITOR || currentRelease==Release.DEBUG))
					return false;
		          if ((new File(getAppFolderWithFinalSep()+"license_kegg_accepted")).exists())
						return true;
		          else
		         	 return false;
			case AUTO_NEWS_DOWNLOAD :
	          if ((new File(getAppFolderWithFinalSep()+"setting_news_download_enabled")).exists())
					return true;
	          else
	         	 return false;
			case TRANSPATH_ACCESS :
				if (currentRelease==Release.DEBUG || currentRelease==Release.RELEASE_IPK)
					return true;
				break;
			case URL_HELPTEXT :
				if (currentRelease==Release.DEBUG || currentRelease==Release.RELEASE_IPK)
					return true;
				break;
			case URL_RELEASEINFO : 
				if (currentRelease==Release.DEBUG || currentRelease==Release.RELEASE_IPK)
					return true;
				break;
			case DBE_ACCESS :
				if (currentRelease==Release.DEBUG || currentRelease==Release.RELEASE_IPK)
					return true;
				break;
			case MetaCrop_ACCESS :
				if (currentRelease!=Release.KGML_EDITOR && currentRelease==Release.RELEASE_IPK)
					return true;
				break;	
			case DATA_CARD_ACCESS :
					return false;
				// if (currentRelease==Release.DEBUG || currentRelease==Release.RELEASE_IPK)
				// 	return true;
				// break;					
			case METHOUSE_ACCESS :
				if (currentRelease==Release.DEBUG || currentRelease==Release.RELEASE_IPK)
					return true;
				break;
			case FLAREX_ACCESS :
				if (currentRelease==Release.DEBUG || currentRelease==Release.RELEASE_IPK)
					return true;
				break;
			case SCRIPT_ACCESS :
				return true; /*
				if (currentRelease==Release.DEBUG)
					return true;
				break;*/
			case GravistoJavaHelp :
				if (currentRelease!=Release.RELEASE_CLUSTERVIS && currentRelease!=Release.KGML_EDITOR) {
			          if ((new File(getAppFolderWithFinalSep()+"setting_help_enabled")).exists())
			        	  return true;
			          else
			        	  return false;
				}
				break;
			case TAB_LAYOUT :
				return true; /*
				if (currentRelease==Release.RELEASE_CLUSTERVIS || currentRelease==Release.KGML_EDITOR
						 || currentRelease==Release.DEBUG)
					return true;*/
				// break;
			case TAB_STATISTICS :
				if (currentRelease==Release.RELEASE_IPK || 
						currentRelease==Release.RELEASE_PUBLIC || currentRelease==Release.DEBUG )
					return true;
				break;
				
			case TAB_PATTERNSEARCH :
				if (currentRelease==Release.KGML_EDITOR)
					return false;
				else
					return true;
				// return false;
				// if (currentRelease==Release.DEBUG)
				//	return true;
				// break;
			case DATAMAPPING :
				if (currentRelease!=Release.RELEASE_CLUSTERVIS && currentRelease!=Release.KGML_EDITOR)
					return true;
				break;
			case AGLET_NETWORK :
				if (currentRelease!=Release.RELEASE_CLUSTERVIS && currentRelease!=Release.KGML_EDITOR)
					return true;
				break;
			case FUNCAT_ACCESS :
				return true;
			case URL_NODE_ANNOTATION :
				if (currentRelease==Release.KGML_EDITOR)
					return false;
				else
					return true;
			case TOOLTIPS :
				if (currentRelease==Release.KGML_EDITOR)
					return false;
				else
					return true;
			case PATHWAY_FILE_REFERENCE : 
			if (currentRelease==Release.KGML_EDITOR)
				return false;
			else
				return true;
			default :
				return true;
		}
		return false;
	}

	public static String getAppFolder() {
		String appFolder = getAppFolderName();
		try {
			if (!new File(appFolder).isDirectory()) {
				boolean success = (new File(appFolder)).mkdirs();
				if (!success) {
					appFolder = System.getenv("USERPROFILE");
					if (!new File(appFolder).isDirectory()) {
						success = (new File(appFolder)).mkdirs();
					}		
				}
			}
		} catch(Exception e) {
			// empty
		}
		return appFolder;
	}

	private static String getAppFolderName() {
		String newStyle = getAppFolderNameNewStyle();

		try {
		
		String oldStyle = getAppFolderNameOldStyle();
		if (!oldStyle.equals(newStyle)) {
			if (new File(oldStyle).isDirectory()) {

			    File src = new File(oldStyle);
			    File tgt = new File(newStyle);
			    boolean success = src.renameTo(tgt);
			    if (success) {
			        System.out.println("Moved user preferences from "+oldStyle+" to "+newStyle+"!");
				   	  JOptionPane.showMessageDialog(
								null,
								"<html>" +
								"<h3>New Preferences Folder</h3>"+
								"User preferences have been moved:<br>" +
								"<ul>" +
								"<li>Old: "+oldStyle+"" +
								"<li>New: "+newStyle+"</ul>",
								"Information",
								JOptionPane.INFORMATION_MESSAGE);

			    }
			}
		}
		
		} catch(Exception e) {
			ErrorMsg.addErrorMessage(e);
		}
		
		return newStyle;
	}
		
	private static String getAppFolderNameNewStyle() {
		String home = System.getProperty("user.home");
		boolean windows = false;
		if (ErrorMsg.isMac())
			home = home+getPathSeparator()+"Library"+getPathSeparator()+"Preferences";
		else {
			if (new File(home+getPathSeparator()+"AppData"+getPathSeparator()+"Roaming").isDirectory()) {
				home = home+getPathSeparator()+"AppData"+getPathSeparator()+"Roaming";
				windows = true;
			} else {
				String hhh = System.getenv("APPDATA");
				if (new File(hhh).isDirectory())
					home = hhh;
			}
		}
		
		if (ErrorMsg.isMac() || windows) {
			if (getRunningReleaseStatus()==Release.KGML_EDITOR)
				return home+getPathSeparator()+"KGML_EDITOR";
			else 
				return home+getPathSeparator()+"VANTED";
		} else {
			if (getRunningReleaseStatus()==Release.KGML_EDITOR)
				return home+getPathSeparator()+".kgml_editor";
			else 
				return home+getPathSeparator()+".vanted";
		}
	}

	private static String getAppFolderNameOldStyle() {
		String home = System.getProperty("user.home");
		if (getRunningReleaseStatus()==Release.KGML_EDITOR)
			return home+getPathSeparator()+".kgml_editor";
		else 
			return home+getPathSeparator()+".vanted";
	}

	public static String getPathSeparator() {
		return System.getProperty("file.separator");
	}

	public static String getAppFolderWithFinalSep() {
		return getAppFolder()+getPathSeparator();
	}

	public static String getAppWebURL() {
		if (getRunningReleaseStatus()==Release.KGML_EDITOR)
			return "http://kgml-ed.ipk-gatersleben.de";
		else
			return "http://vanted.ipk-gatersleben.de";
	}
	
	private static String helpIntro = "";
	
	public static void setHelpIntroductionText(String statusMessage) {
		helpIntro = statusMessage;
	}

	public static String getHelpIntroductionText() {
		return helpIntro;
	}

	public static boolean isRunningAsApplet() {
		try {
			@SuppressWarnings("unused")
			String s = System.getProperty("user.home");
			return false;
		} catch(Exception e) {
			return true;
		}
	}
}
