package org;

import static org.junit.Assert.assertEquals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.Timer;

import org.junit.Test;

/**
 * @author klukas
 */
public class SettingsHelperDefaultIsTrue implements HelperClass {

	public boolean isEnabled(String name) {
		return !new File(ReleaseInfo.getAppFolderWithFinalSep() + "feature_disabled_" + encode(name)).exists();
	}

	protected static String encode(String name) {
		return StringManipulationTools.removeHTMLtags(name).replaceAll(" ", "_").replaceAll("/", "_");
	}

	@Test
	public void testEncode() {
		assertEquals("Setting Encode", "test_test_2", encode("test/test 2"));
	}

	public void setEnabled(String name, boolean b) {
		if (!b)
			try {
				new File(ReleaseInfo.getAppFolderWithFinalSep() + "feature_disabled_" + encode(name)).createNewFile();
			} catch (IOException e) {
				ErrorMsg.addErrorMessage(e);
			}
		else {
			new File(ReleaseInfo.getAppFolderWithFinalSep() + "feature_disabled_" + encode(name)).delete();
		}
	}

	public JComponent getBooleanSettingsEditor(String description, final String option, final Runnable enable, final Runnable disable) {
		final JCheckBox result = new JCheckBox(description, isEnabled(option));
		result.setOpaque(false);
		result.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean enabled = isEnabled(option);
				enabled = !enabled;
				setEnabled(option, enabled);
				if (enabled) {
					if (enable != null)
						enable.run();
				} else {
					if (disable != null)
						disable.run();
				}
			}
		});
		Timer t = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean active = isEnabled(option);
				boolean b = active;
				result.setSelected(b);
			}
		});
		t.start();
		return result;
	}

}
