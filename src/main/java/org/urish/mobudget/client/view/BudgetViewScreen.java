package org.urish.mobudget.client.view;

import java.util.ArrayList;
import java.util.List;

import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.AlertDialog;
import org.urish.gwtit.titanium.ui.TableView;
import org.urish.gwtit.titanium.ui.View;
import org.urish.gwtit.titanium.ui.Window;
import org.urish.gwtit.titanium.ui.events.RowClickEvent;
import org.urish.gwtit.titanium.ui.events.RowClickHandler;
import org.urish.mobudget.client.MoBudget;
import org.urish.mobudget.client.logger.Logger;
import org.urish.mobudget.client.logger.LoggerFactory;
import org.urish.mobudget.client.model.BudgetLine;

public class BudgetViewScreen extends BaseBudgetView {
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.get(BudgetViewScreen.class);

	private final Window view;
	private final TableView rankList;
	private int total;
	private boolean populated;

	public BudgetViewScreen() {
		view = UI.createWindow();
		view.setTitle("תקציב 2012");
		rankList = UI.createTableView();
		view.add(rankList);
	}

	protected List<BudgetLine> filterBudget() {
		int total = 0;
		final List<BudgetLine> filtered = new ArrayList<BudgetLine>();
		for (BudgetLine budgetLine : getBudgetDatabase().getData()) {
			if ((budgetLine.getCode().length() == 4) && (budgetLine.getNet_allocated() > 0)) {
				total += budgetLine.getNet_allocated();
				filtered.add(budgetLine);
			}
		}
		setTotal(total);
		return filtered;
	}


	private void populateView() {
		final List<BudgetLine> filtered = filterBudget();
		sortBudget(filtered);
		for (BudgetLine budgetLine : filtered) {
			rankList.appendRow(createBudgetRow(budgetLine, getTotal()));
		}

		rankList.addRowClickHandler(new RowClickHandler() {

			@Override
			public void onRowClick(RowClickEvent event) {
				BudgetLine selectedLine = filtered.get((int)event.getIndex());
				if (selectedLine.getCode().length() < 8) {
					MoBudget.tabGroup.getActiveTab().open(new BudgetSubScreen(selectedLine).getView());
				} else {
					AlertDialog alertDialog = UI.createAlertDialog();
					alertDialog.setTitle(selectedLine.getTitle());
					alertDialog.setMessage(String.valueOf(selectedLine.getNet_allocated()) + " אלף ש\"ח");
					alertDialog.show();
				}
			}
		});
	}

	public View getView() {
		if (!populated) {
			populateView();
			populated = true;
		}
		return view;
	}

	protected Window getWindow() {
		return view;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
