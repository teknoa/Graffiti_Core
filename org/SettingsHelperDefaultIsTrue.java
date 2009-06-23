package org;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.Timer;

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

	public static JComponent getBooleanSettingsEditor(String description, final String option, final Runnable enable, final Runnable disable) {
		final JCheckBox result = new JCheckBox(description, isEnabled(option));
		result.setOpaque(false);
//		final ObjectRef currentSetting = new ObjectRef("n/a", SettingsHelperDefaultIsTrue.isEnabled(option));
		result.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean enabled = SettingsHelperDefaultIsTrue.isEnabled(option);
				enabled = !enabled;
				SettingsHelperDefaultIsTrue.setEnabled(option, enabled);
				if (enabled) {
					if (enable!=null)
						enable.run();
				} else {
					if (disable!=null)
						disable.run();
				}
//				currentSetting.setObject(enabled);
			}});
		Timer t = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean active = SettingsHelperDefaultIsTrue.isEnabled(option);
//				boolean a = ((Boolean)currentSetting.getObject());
				boolean b = active;
				result.setSelected(b);
//				if ((a && !b) || (!a && b) ) {
//					result.setSelected(b);
//				}
			}});
		t.start();
		return result;
	}

}
