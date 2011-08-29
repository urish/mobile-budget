package org.urish.mobudget.client.view;

import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.TableView;
import org.urish.gwtit.titanium.ui.TableViewRow;
import org.urish.gwtit.titanium.ui.View;
import org.urish.gwtit.titanium.ui.Window;
import org.urish.gwtit.titanium.ui.events.ClickEvent;
import org.urish.gwtit.titanium.ui.events.ClickHandler;
import org.urish.mobudget.client.logger.Logger;
import org.urish.mobudget.client.logger.LoggerFactory;
import org.urish.mobudget.client.model.BudgetDatabase;
import org.urish.mobudget.client.model.BudgetLine;
import org.urish.mobudget.client.model.Model;

public class BudgetViewScreen {
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.get(BudgetViewScreen.class);

	private final Window view;
	private final TableView rankList;

	public BudgetViewScreen() {
		view = UI.createWindow();
		view.setTitle("תקציב 2012");
		rankList = UI.createTableView();

		final BudgetDatabase budgetDatabase = Model.loadJson("data/2012.json", BudgetDatabase.class);
		for (BudgetLine budgetInfo : budgetDatabase.getData()) {
			rankList.appendRow(createBudgetRow(budgetInfo));
		}

		rankList.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				event.getSource();
			}
		});

		view.add(rankList);
	}

	private TableViewRow createBudgetRow(BudgetLine rankInfo) {
		TableViewRow result = UI.createTableViewRow();

		result.setTitle(rankInfo.getTitle());
		return result;
	}

	public View getView() {
		return view;
	}
}
