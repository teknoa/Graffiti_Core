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
		new File(s).mkdirs();
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
				new File(getTemporaryFolderWithFinalSep()+ReleaseInfo.getFileSeparator()+subfolder+ReleaseInfo.getFileSeparator()).mkdirs();
				outfile = getTemporaryFolderWithFinalSep()+ReleaseInfo.getFileSeparator()+subfolder+ReleaseInfo.getFileSeparator()+f.getName();
			}
				
			copyFile(f, new File(outfile), true);
	
			if(f.getPath().endsWith(".hdr".toLowerCase()))
				copyFile(new File(f.getPath().replaceFirst(".hdr",".img")),new File(outfile.replaceFirst(".hdr",".img")), true);
			
			return true;
		} catch(Exception e) {
			ErrorMsg.addErrorMessage(e);
			return false;
		}

	}

	private static BufferedInputStream in;
	private static BufferedOutputStream out;
	
	public static void copyFile(File oldfile,File newfile, boolean deleteNewFileIfExists) throws IOException {
		if(deleteNewFileIfExists&&newfile.exists())
			newfile.delete();
		
		in = new BufferedInputStream(new FileInputStream(oldfile));
		out = new BufferedOutputStream(new FileOutputStream(newfile, true));
		
		byte[] buffer = new byte[ 0xFFFF ]; 
		for ( int len; (len = in.read(buffer)) != -1; ) 
		    out.write( buffer, 0, len );
	}
	
}
