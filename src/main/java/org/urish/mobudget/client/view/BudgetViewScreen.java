package org.urish.mobudget.client.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.urish.gwtit.client.font.Font;
import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.AlertDialog;
import org.urish.gwtit.titanium.ui.Label;
import org.urish.gwtit.titanium.ui.TableView;
import org.urish.gwtit.titanium.ui.TableViewRow;
import org.urish.gwtit.titanium.ui.View;
import org.urish.gwtit.titanium.ui.Window;
import org.urish.gwtit.titanium.ui.events.ClickEvent;
import org.urish.gwtit.titanium.ui.events.ClickHandler;
import org.urish.mobudget.client.MoBudget;
import org.urish.mobudget.client.logger.Logger;
import org.urish.mobudget.client.logger.LoggerFactory;
import org.urish.mobudget.client.model.BudgetDatabase;
import org.urish.mobudget.client.model.BudgetLine;
import org.urish.mobudget.client.model.Model;

public class BudgetViewScreen {
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.get(BudgetViewScreen.class);
	private static final BudgetDatabase budgetDatabase = Model.loadJson("data/2012.json", BudgetDatabase.class);

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
	
	protected void sortBudget(List<BudgetLine> budget) {
		Collections.sort(budget, new Comparator<BudgetLine>() {
			@Override
			public int compare(BudgetLine o1, BudgetLine o2) {
				return o2.getNet_allocated() - o1.getNet_allocated();
			}
		});
	}

	private TableViewRow createBudgetRow(BudgetLine budgetLine, int total) {
		TableViewRow result = UI.createTableViewRow();

		Label label = UI.createLabel();
		label.setText(budgetLine.getTitle());
		label.setTextAlign(UI.TEXT_ALIGNMENT_RIGHT);
		label.setRight(80);
		label.setFont(Font.createFont(22));
		label.setLeft(6);
		result.add(label);

		Label labelPercent = UI.createLabel();
		labelPercent.setText(String.valueOf((1000 * budgetLine.getNet_allocated() / total) / 10f));
		labelPercent.setTextAlign(UI.TEXT_ALIGNMENT_CENTER);
		labelPercent.setRight(10);
		labelPercent.setFont(Font.createFont(24));
		labelPercent.setWidth(60);
		result.add(labelPercent);

		return result;
	}
	
	private void populateView() {
		final List<BudgetLine> filtered = filterBudget();
		sortBudget(filtered);
		for (BudgetLine budgetLine : filtered) {
			rankList.appendRow(createBudgetRow(budgetLine, getTotal()));
		}

		rankList.addClickHandler(new ClickHandler() {

			private native int getIndex(ClickEvent event)
			/*-{
			 	return event.index;
			}-*/;

			@Override
			public void onClick(ClickEvent event) {
				BudgetLine selectedLine = filtered.get(getIndex(event));
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

	protected static BudgetDatabase getBudgetDatabase() {
		return budgetDatabase;
	}
}
