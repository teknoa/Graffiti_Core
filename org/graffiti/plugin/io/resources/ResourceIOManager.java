package org.graffiti.plugin.io.resources;

import java.io.InputStream;
import java.util.ArrayList;

public class ResourceIOManager {

	private static ResourceIOManager instance;

	private static ResourceIOManager getInstance() {
		if (instance == null) {
			instance = new ResourceIOManager();
			registerIOHandler(new FileSystemHandler());
			registerIOHandler(new HTTPFileHandler());
		}
		return instance;
	}

	ArrayList<ResourceIOHandler> handlers;
	public static final String SEPERATOR = "||";

	private ResourceIOManager() {
		super();
		handlers = new ArrayList<ResourceIOHandler>();
	}

	public static void registerIOHandler(ResourceIOHandler handler) {
		getInstance().handlers.add(handler);
	}

	public static void removeIOHandler(ResourceIOHandler handler) {
		getInstance().handlers.remove(handler);
	}

	//	public static ArrayList<ResourceIOHandler> getHandlers() {
	//		return getInstance().handlers;
	//	}

	/**
	 * @return nice filename for displaying or null, if no handler found
	 */
	public static InputStream getInputStream(IOurl url) throws Exception {
		for (ResourceIOHandler mh : getInstance().handlers)
			if (url.isEqualPrefix(mh.getPrefix()))
				return mh.getInputStream(url);
		return null;
	}

	/**
	 * @return new url or null, if not copied
	 */
	public static IOurl copyDataAndReplaceURLPrefix(String targetHandlerPrefix, IOurl sourceURL,
			ReourceIOConfigObject config) throws Exception {
		if (sourceURL == null)
			return null;

		InputStream is = getInputStream(sourceURL);
		String filename = sourceURL.getFileName();

		return copyDataAndReplaceURLPrefix(targetHandlerPrefix, filename, is, config);
	}

	public static IOurl copyDataAndReplaceURLPrefix(String targetHandlerPrefix, String srcFileName, InputStream is,
			ReourceIOConfigObject config) throws Exception {
		if (is == null || srcFileName == null)
			return null;

		for (ResourceIOHandler mh : getInstance().handlers)
			if (mh.getPrefix().equals(targetHandlerPrefix))
				return mh.copyDataAndReplaceURLPrefix(is, srcFileName, config);
		return null;
	}

}
