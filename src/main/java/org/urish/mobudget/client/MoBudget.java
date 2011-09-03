package org.urish.mobudget.client;

import org.urish.gwtit.client.GwtTitaniumBootstrap;
import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.Tab;
import org.urish.gwtit.titanium.ui.TabGroup;
import org.urish.mobudget.client.logger.Logger;
import org.urish.mobudget.client.logger.LoggerFactory;
import org.urish.mobudget.client.view.AboutScreen;
import org.urish.mobudget.client.view.BudgetViewScreen;
import org.urish.mobudget.client.view.SearchScreen;

public class MoBudget extends GwtTitaniumBootstrap {
	private final static Logger logger = LoggerFactory.get(MoBudget.class);
	public final static TabGroup tabGroup = UI.createTabGroup();

	@Override
	public void main() {
		logger.info("Creating UI...");

		tabGroup.setBarColor("#006800");

		Tab tab1 = UI.createTab();
		tab1.setTitle("תקציב 2012");
		tab1.setWindow(new BudgetViewScreen().getView());
		tabGroup.addTab(tab1);

		Tab tab2 = UI.createTab();
		tab2.setTitle("חיפוש סעיפים");
		tab2.setWindow(new SearchScreen().getView());
		tabGroup.addTab(tab2);

		Tab tab3 = UI.createTab();
		tab3.setTitle("אודות");
		tab3.setWindow(new AboutScreen().getView());
		tabGroup.addTab(tab3);

		tabGroup.open();
	}

}
