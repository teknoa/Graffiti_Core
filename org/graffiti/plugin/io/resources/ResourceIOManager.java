package org.graffiti.plugin.io.resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashSet;

public class ResourceIOManager {
	
	private static ResourceIOManager instance;
	
	private static ResourceIOManager getInstance() {
		if (instance == null) {
			instance = new ResourceIOManager();
			registerIOHandler(new FileSystemHandler());
			registerIOHandler(new HTTPhandler());
			registerIOHandler(new FTPhandler());
		}
		return instance;
	}
	
	LinkedHashSet<ResourceIOHandler> handlers;
	public static final String SEPERATOR = "||";
	
	private ResourceIOManager() {
		super();
		handlers = new LinkedHashSet<ResourceIOHandler>();
	}
	
	public static void registerIOHandler(ResourceIOHandler handler) {
		getInstance().handlers.add(handler);
	}
	
	public static void removeIOHandler(ResourceIOHandler handler) {
		getInstance().handlers.remove(handler);
	}
	
	// public static ArrayList<ResourceIOHandler> getHandlers() {
	// return getInstance().handlers;
	// }
	
	/**
	 * Use {@link IOurl}.getInputStream instead.
	 * 
	 * @return inputstream of file or null, if no handler found.
	 */
	static InputStream getInputStream(IOurl url) throws Exception {
		if (url == null) {
			System.err.println("Could not create inputstream from NULL url!");
			return null;
		}
		for (ResourceIOHandler mh : getInstance().handlers)
			if (url.isEqualPrefix(mh.getPrefix())) {
				InputStream is = mh.getInputStream(url);
				if (is != null)
					return is;
			}
		System.err.println("Could not create inputstream from URL " + url.toString() + "!");
		return null;
	}
	
	/**
	 * @return new url or null, if not copied
	 */
	public static IOurl copyDataAndReplaceURLPrefix(String targetHandlerPrefix, IOurl sourceURL,
						ResourceIOConfigObject config) throws Exception {
		if (sourceURL == null)
			return null;
		
		InputStream is = getInputStream(sourceURL);
		String filename = sourceURL.getFileName();
		
		return copyDataAndReplaceURLPrefix(targetHandlerPrefix, filename, is, config);
	}
	
	public static IOurl copyDataAndReplaceURLPrefix(String targetHandlerPrefix, String srcFileName, InputStream is,
						ResourceIOConfigObject config) throws Exception {
		if (is == null || srcFileName == null)
			return null;
		
		for (ResourceIOHandler mh : getInstance().handlers)
			if (mh.getPrefix().equals(targetHandlerPrefix))
				return mh.copyDataAndReplaceURLPrefix(is, srcFileName, config);
		return null;
	}
	
	public static MyByteArrayInputStream getInputStreamMemoryCached(IOurl url) throws IOException, Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		InputStream is = url != null ? url.getInputStream() : null;
		if (is == null)
			return null;
		ResourceIOManager.copyContent(is, bos);
		return new MyByteArrayInputStream(bos.toByteArray());
	}
	
	public static void copyContent(InputStream intemp, OutputStream out) throws IOException {
		copyContent(intemp, out, -1);
	}
	
	public static void copyContent(InputStream in, OutputStream out, long maxIO) throws IOException {
		
		if (maxIO <= 0) {
			byte[] buffer = new byte[0xFFFF];
			for (int len; (len = in.read(buffer)) != -1;) {
				out.write(buffer, 0, len);
			}
		} else {
			long read = 0;
			byte[] buffer = new byte[0xFFFF];
			for (int len; (len = in.read(buffer)) != -1;) {
				read += len;
				if (read < maxIO)
					out.write(buffer, 0, len);
				else {
					read -= len;
					out.write(buffer, 0, (int) (maxIO - read));
					break;
				}
			}
		}
		
		in.close();
		out.close();
	}
}
