package org.urish.mobudget.client.view;

import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.TableView;
import org.urish.gwtit.titanium.ui.Window;

public class SearchScreen {
	private final Window view;
	private final TableView searchWindow;
	
	public SearchScreen() {
		view = UI.createWindow();
		view.setTitle("חיפוש סעיפים");
		searchWindow = UI.createTableView();
		view.add(searchWindow);
	}

	public Window getView() {
		return view;
	}
}
