package org;

import java.io.File;
import java.io.IOException;

/**
 * @author klukas
 */
public class SettingsHelperDefaultIsTrue {

	public static boolean isEnabled(String name) {
		return !new File(ReleaseInfo.getAppFolderWithFinalSep()+"feature_disabled_"+encode(name)).exists();
	}

	private static String encode(String name) {
		return name.replaceAll(" ", "_");
	}

	public static void setEnabled(String name, boolean b) {
		if (!b)
			try {
				new File(ReleaseInfo.getAppFolderWithFinalSep()+"feature_disabled_"+encode(name)).createNewFile();
			} catch (IOException e) {
				ErrorMsg.addErrorMessage(e);
			}
		else {
			new File(ReleaseInfo.getAppFolderWithFinalSep()+"feature_disabled_"+encode(name)).delete();
		}
	}

}
