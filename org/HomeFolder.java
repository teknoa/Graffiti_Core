package org;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HomeFolder {

	public static String getTemporaryFolder() {
		String s = ReleaseInfo.getAppFolder()+ReleaseInfo.getFileSeparator()+"tmp";
		File f = new File(s);
		f.mkdirs();
		f.deleteOnExit();
		return s;
	}
	
	public static String getTemporaryFolderWithFinalSep() {
		return getTemporaryFolder()+ReleaseInfo.getFileSeparator();
	}
	
	public static boolean copyFileToTemporaryFolder(File f)  {
		return copyFileToTemporaryFolder(null, f);
	}
	
	public static boolean copyFileToTemporaryFolder(String subfolder, File f)  {
		try {
			String outfile = getTemporaryFolderWithFinalSep()+f.getName();
			
			if(subfolder!=null&&!subfolder.equalsIgnoreCase("")) {
				File newDir = new File(getTemporaryFolderWithFinalSep()+ReleaseInfo.getFileSeparator()+subfolder);
				newDir.mkdirs();
				newDir.deleteOnExit();
				outfile = newDir+ReleaseInfo.getFileSeparator()+f.getName();
			}
				
			copyFile(f, new File(outfile));
	
			if(f.getPath().endsWith(".hdr".toLowerCase())) {
				File vol_data = new File(outfile.replaceFirst(".hdr",".img"));
				vol_data.deleteOnExit();
				copyFile(new File(f.getPath().replaceFirst(".hdr",".img")),vol_data);
			}
			
			new File(outfile).deleteOnExit();
			return true;
		} catch(Exception e) {
			ErrorMsg.addErrorMessage(e);
			return false;
		}

	}

	private static BufferedInputStream in;
	private static BufferedOutputStream out;
	
	public static void copyFile(File oldfile,File newfile) throws IOException {
		if(oldfile.compareTo(newfile)!=0) {
			
			in = new BufferedInputStream(new FileInputStream(oldfile));
			out = new BufferedOutputStream(new FileOutputStream(newfile, false));
			
			byte[] buffer = new byte[ 0xFFFF ]; 
			for ( int len; (len = in.read(buffer)) != -1; ) 
			    out.write( buffer, 0, len );
			
			in.close();
			out.close();
			
		}
	}
	
}
