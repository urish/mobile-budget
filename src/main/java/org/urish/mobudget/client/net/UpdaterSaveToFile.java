package org.urish.mobudget.client.net;

import org.urish.gwtit.client.util.Javascript;
import org.urish.gwtit.titanium.Filesystem;
import org.urish.mobudget.client.logger.Logger;
import org.urish.mobudget.client.logger.LoggerFactory;

public class UpdaterSaveToFile implements UpdaterCallback {
	private final static Logger logger = LoggerFactory.get(UpdaterSaveToFile.class);

	private final String fileName;
	private final UpdaterCallback nextCallback;

	public UpdaterSaveToFile(String fileName) {
		this(fileName, null);
	}

	public UpdaterSaveToFile(String fileName, UpdaterCallback nextCallback) {
		super();
		if (fileName.indexOf("file://") == 0) {
			this.fileName = Javascript.unescape(fileName.replaceAll("^file://[^/]*", ""));
		} else {
			this.fileName = fileName;
		}
		this.nextCallback = nextCallback;
	}

	@Override
	public void onUpdateReceived(String data) {
		logger.debug("Writing received to file " + fileName);
		Filesystem.getFile(fileName).write(data);
		if (nextCallback != null) {
			nextCallback.onUpdateReceived(data);
		}
	}

}
