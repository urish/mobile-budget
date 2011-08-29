package org.urish.mobudget.client.model;

import org.urish.gwtit.titanium.Filesystem;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

public class Model {
	public static final Factory factory = GWT.create(Factory.class);

	public static <T> T loadJson(String filename, Class<T> clazz) {
		String jsonData = Filesystem.getFile(Filesystem.getResourcesDirectory() + filename).read().toString_();
		return AutoBeanCodex.decode(factory, clazz, jsonData).as();
	}
}
