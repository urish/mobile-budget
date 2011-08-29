package org.urish.mobudget.client;

import org.urish.gwtit.client.GwtTitaniumBootstrap;
import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.Tab;
import org.urish.gwtit.titanium.ui.TabGroup;
import org.urish.mobudget.client.logger.Logger;
import org.urish.mobudget.client.logger.LoggerFactory;
import org.urish.mobudget.client.view.BudgetViewScreen;

public class MoBudget extends GwtTitaniumBootstrap {
	private final static Logger logger = LoggerFactory.get(MoBudget.class);

	@Override
	public void main() {
		logger.info("Creating UI...");
		
		TabGroup tabGroup = UI.createTabGroup();

		tabGroup.setBarColor("#006800");

		Tab tab1 = UI.createTab();
		tab1.setTitle("רס\"ר");
		tab1.setWindow(new BudgetViewScreen().getView());
		tab1.setIcon("icons/beret.png");
		tabGroup.addTab(tab1);

		Tab tab2 = UI.createTab();
		tab2.setTitle("דרגות");
		tab2.setWindow(new BudgetViewScreen().getView());
		tab2.setIcon("icons/ranks.png");
		tabGroup.addTab(tab2);

		Tab tab3 = UI.createTab();
		tab3.setTitle("עוד...");
		tab3.setWindow(new BudgetViewScreen().getView());
		tab3.setIcon("icons/more.png");
		tabGroup.addTab(tab3);

		tabGroup.open();
	}

}
