package org.urish.mobudget.client.model;

import org.urish.gwtit.titanium.Filesystem;
import org.urish.gwtit.titanium.filesystem.File;
import org.urish.mobudget.client.logger.Logger;
import org.urish.mobudget.client.logger.LoggerFactory;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

public class Model {
	private final static Logger logger = LoggerFactory.get(Model.class);

	private static final Factory factory = GWT.create(Factory.class);

	public static File getJsonWriteFile(String filename) {
		return Filesystem.getFile(getJsonWritePath(filename));
	}

	public static <T> T loadStaticJson(String filename, Class<T> clazz) {
		String jsonData = Filesystem.getFile(Filesystem.getResourcesDirectory() + filename).read().toString_();
		return AutoBeanCodex.decode(factory, clazz, jsonData).as();
	}
	
	public static <T> T loadJson(String filename, Class<T> clazz) {
		File jsonFile = getJsonWriteFile(filename);
		if (!jsonFile.exists()) {
			jsonFile = Filesystem.getFile(Filesystem.getResourcesDirectory() + filename);
		}
		logger.info("loading JSON " + jsonFile.getNativePath());
		String jsonData = jsonFile.read().toString_();
		return AutoBeanCodex.decode(factory, clazz, jsonData).as();
	}
	
	public static <T> T loadJsonString(String jsonData, Class<T> clazz) {
		return AutoBeanCodex.decode(factory, clazz, jsonData).as();		
	}

	public static String getJsonWritePath(String filename) {
		return Filesystem.getApplicationDataDirectory() + filename;
	}

}
