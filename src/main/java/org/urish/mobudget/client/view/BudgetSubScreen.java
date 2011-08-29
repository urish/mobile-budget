package org.urish.mobudget.client.view;

import java.util.ArrayList;
import java.util.List;

import org.urish.mobudget.client.model.BudgetLine;

public class BudgetSubScreen extends BudgetViewScreen {
	final String parentCode;

	public BudgetSubScreen(String parentCode) {
		super();
		this.parentCode = parentCode;
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
