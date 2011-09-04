package org.urish.mobudget.client.view;

import java.util.ArrayList;
import java.util.List;

import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.Label;
import org.urish.gwtit.titanium.ui.TableViewRow;
import org.urish.mobudget.client.model.BudgetLine;

public class BudgetSubScreen extends BudgetViewScreen {
	final String parentCode;
	final BudgetLine budgetLine;

	public BudgetSubScreen(BudgetLine budgetLine) {
		super();
		this.budgetLine = budgetLine;
		this.parentCode = budgetLine.getCode();
		getWindow().setTitle(budgetLine.getTitle());
	}

	@Override
	protected TableViewRow getPrincipalRow() {
		TableViewRow result = UI.createTableViewRow();
		
		result.setHeight("auto");
		result.setLayout("vertical");
		
		Label topLabel = UI.createLabel();
		topLabel.setWidth(320);
		topLabel.setHeight("auto");
		topLabel.setText(budgetLine.getTitle());
		topLabel.setTextAlign(UI.TEXT_ALIGNMENT_CENTER);
		result.add(topLabel);

		Label amountLabel = UI.createLabel();
		amountLabel.setHeight("auto");
		amountLabel.setText(getAmountText(budgetLine.getNet_allocated()) + " " + Strings.NIS);
		amountLabel.setTextAlign(UI.TEXT_ALIGNMENT_RIGHT);
		result.add(amountLabel);
		
		int totalBudget = getBudgetDatabase().getTotalBudget();
		String percent = String.valueOf((10000 * budgetLine.getNet_allocated() / totalBudget) / 100.0);
		if (!percent.equals("0")) {			
			Label percentLabel = UI.createLabel();
			percentLabel.setHeight("auto");
			percentLabel.setText(percent + " " + Strings.PERCENT_OF_STATE_BUDGET);
			percentLabel.setTextAlign(UI.TEXT_ALIGNMENT_RIGHT);
			result.add(percentLabel);
		}
		
		return result;
	}

	private String getAmountText(int amount) {
		if (amount < 1000) {
			return String.valueOf(amount) + " " + Strings.THOUSAND;
		} else if (amount < 100000) {
			return String.valueOf((amount / 100) / 10.0) + " " + Strings.MILLION;
		} else if (amount < 1000000) {
			return String.valueOf(amount / 1000) + " " + Strings.MILLION;
		} else {
			return String.valueOf((amount / 100000) / 10.0) + " " + Strings.BILLION;
		}
	}

	@Override
	protected List<BudgetLine> filterBudget() {
		int total = 0;
		final List<BudgetLine> filtered = new ArrayList<BudgetLine>();
		for (BudgetLine budgetLine : getBudgetDatabase().getData()) {
			if (budgetLine.getCode().startsWith(parentCode) && (budgetLine.getCode().length() - parentCode.length() == 2)
					&& (budgetLine.getNet_allocated() > 0)) {
				total += budgetLine.getNet_allocated();
				filtered.add(budgetLine);
			}
		}
		setTotal(total);
		return filtered;
	}

}
