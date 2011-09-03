package org.urish.mobudget.client.view;

import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.Window;

public class AboutScreen {
	private final Window view;
	
	public AboutScreen() {
		view = UI.createWindow();
		view.setTitle("אודות");
	}

	public Window getView() {
		return view;
	}
}
