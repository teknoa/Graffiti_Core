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
				if (currentRelease==Release.DEBUG || currentRelease==Release.RELEASE_IPK)
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
			case FUNCAT_ACCESS :
				return true;
			case URL_NODE_ANNOTATION :
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
		if (getRunningReleaseStatus()==Release.KGML_EDITOR)
			return System.getProperty("user.home")+getPathSeparator()+".kgml_editor";
		else
			return System.getProperty("user.home")+getPathSeparator()+".vanted";
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
