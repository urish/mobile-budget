package org.urish.mobudget.client.view;

import org.urish.gwtit.client.util.Version;
import org.urish.gwtit.titanium.Filesystem;
import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.WebView;
import org.urish.gwtit.titanium.ui.Window;

public abstract class BaseHtmlWindow {
	private Window window;

	public BaseHtmlWindow(String url) {
		window = UI.createWindow();
		window.setOrientationModes(new int[]{UI.PORTRAIT, UI.LANDSCAPE_LEFT, UI.LANDSCAPE_RIGHT});
		
		WebView webView = UI.createWebView();
		webView.setBackgroundColor("black");
		webView.setUrl(htmlResourceUrl(url));
		window.add(webView);
	}
	
	private String htmlResourceUrl(String relativePath) {
		if (Version.android()) {
			return Filesystem.getFile(Filesystem.getResourcesDirectory() + relativePath).getNativePath(); 			
		} else {
			return Filesystem.getResourcesDirectory() + relativePath;
		}
	}
	
	public Window getWindow() {
		return window;
	}
}
