package org.urish.mobudget.client.view;

import java.util.ArrayList;
import java.util.List;

import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.TableView;
import org.urish.gwtit.titanium.ui.TableViewRow;
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
	private final TableView tableView;
	private int total;
	private boolean populated;

	public BudgetViewScreen() {
		view = UI.createWindow();
		view.setTitle("תקציב 2012");
		tableView = UI.createTableView();
		view.add(tableView);
	}

	protected TableViewRow getPrincipalRow() {
		return null;
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
		TableViewRow principalRow = getPrincipalRow();
		if (principalRow != null) {
			tableView.appendRow(principalRow);
		}
		for (BudgetLine budgetLine : filtered) {
			tableView.appendRow(createBudgetRow(budgetLine, getTotal()));
		}
		final boolean hadPrincipal = principalRow != null;

		tableView.addRowClickHandler(new RowClickHandler() {

			@Override
			public void onRowClick(RowClickEvent event) {
				int index = (int) event.getIndex();
				if (hadPrincipal) {
					index--;
				}
				if (index >= 0) {
					BudgetLine selectedLine = filtered.get(index);
					MoBudget.tabGroup.getActiveTab().open(new BudgetSubScreen(selectedLine).getView());
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
