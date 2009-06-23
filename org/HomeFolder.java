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
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			String outfile = getTemporaryFolderWithFinalSep()+f.getName();
			
			if(subfolder!=null&&!subfolder.equalsIgnoreCase("")) {
				new File(getTemporaryFolderWithFinalSep()+ReleaseInfo.getFileSeparator()+subfolder+ReleaseInfo.getFileSeparator()).mkdirs();
				outfile = getTemporaryFolderWithFinalSep()+ReleaseInfo.getFileSeparator()+subfolder+ReleaseInfo.getFileSeparator()+f.getName();
			}
			out = new BufferedOutputStream(new FileOutputStream(outfile, false));
			in.close();
			out.close();
			if(f.getPath().endsWith(".hdr".toLowerCase())) {
				in = new BufferedInputStream(new FileInputStream(new File(f.getPath().replaceFirst(".hdr",".img"))));
				out = new BufferedOutputStream(new FileOutputStream(outfile = getTemporaryFolderWithFinalSep()+ReleaseInfo.getFileSeparator()+subfolder+ReleaseInfo.getFileSeparator()+f.getName().replaceFirst(".hdr",".img"), false));
				copyFile(in, out);  
				in.close();
				out.close();
			}
			return true;
		} catch(Exception e) {
			ErrorMsg.addErrorMessage(e);
			return false;
		} finally {
			try {
				if(in!=null)
					in.close();
				if(out!=null)
					out.close();
			} catch (IOException e) {
				ErrorMsg.addErrorMessage(e);
			}
		}
	}

	public static void copyFile(BufferedInputStream in,BufferedOutputStream out) throws IOException {
		byte[] buffer = new byte[ 0xFFFF ]; 
		for ( int len; (len = in.read(buffer)) != -1; ) 
		    out.write( buffer, 0, len );
	}
	
}
