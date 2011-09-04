package org.urish.mobudget.client.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.urish.gwtit.client.font.Font;
import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.Label;
import org.urish.gwtit.titanium.ui.TableViewRow;
import org.urish.mobudget.client.logger.Logger;
import org.urish.mobudget.client.logger.LoggerFactory;
import org.urish.mobudget.client.model.BudgetDatabase;
import org.urish.mobudget.client.model.BudgetLine;
import org.urish.mobudget.client.model.Model;

public class BaseBudgetView {
	private final static Logger logger = LoggerFactory.get(BaseBudgetView.class);

	private static final BudgetDatabase budgetDatabase = Model.loadStaticJson("data/2012.json", BudgetDatabase.class);

	protected void sortBudget(List<BudgetLine> budget) {
		Collections.sort(budget, new Comparator<BudgetLine>() {
			@Override
			public int compare(BudgetLine o1, BudgetLine o2) {
				return o2.getNet_allocated() - o1.getNet_allocated();
			}
		});
	}

	protected TableViewRow createBudgetRow(BudgetLine budgetLine, int total) {
		TableViewRow result = UI.createTableViewRow();

		Label label = UI.createLabel();
		label.setText(budgetLine.getTitle());
		label.setTextAlign(UI.TEXT_ALIGNMENT_RIGHT);
		label.setRight(10);
		label.setFont(Font.createFont(22));
		label.setLeft(80);
		result.add(label);

		Label labelAmount = UI.createLabel();
		Label amountTitle = UI.createLabel();
		int amount = budgetLine.getNet_allocated();
		if (amount < 1000) {
			labelAmount.setText(String.valueOf(amount));
			amountTitle.setText("אלף");
			labelAmount.setColor("gray");
		} else if (amount < 1000000) {
			labelAmount.setText(String.valueOf(amount / 1000));
			amountTitle.setText("מליון");
			labelAmount.setColor("black");
		} else {
			labelAmount.setText(String.valueOf(amount / 1000000));
			amountTitle.setText("מיליארד");
			labelAmount.setColor("red");
		}
		labelAmount.setTextAlign(UI.TEXT_ALIGNMENT_CENTER);
		labelAmount.setTop(-4);
		labelAmount.setLeft(10);
		labelAmount.setFont(Font.createFont(24));
		labelAmount.setWidth(60);
		result.add(labelAmount);

		amountTitle.setTextAlign(UI.TEXT_ALIGNMENT_CENTER);
		amountTitle.setLeft(10);
		amountTitle.setFont(Font.createFont(14));
		amountTitle.setWidth(60);
		amountTitle.setColor("gray");
		amountTitle.setTop(28);
		result.add(amountTitle);

		addSearchText(result, budgetLine.getTitle());

		return result;
	}

	private native void addSearchText(TableViewRow row, String searchText)
	/*-{
		row.searchText = searchText;
	}-*/;

	protected static BudgetDatabase getBudgetDatabase() {
		return budgetDatabase;
	}
}
