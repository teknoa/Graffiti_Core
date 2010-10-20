package org.graffiti.plugin.io.resources;

import java.io.InputStream;

public interface ResourceIOHandler {

	public String getPrefix();

	public InputStream getInputStream(IOurl url) throws Exception;

	/**
	 * @return new url, or null, if not copied
	 * @throws Exception if something went wrong during copying
	 */
	public IOurl copyDataAndReplaceURLPrefix(InputStream is, String targetFilename, ReourceIOConfigObject config) throws Exception ;

}
