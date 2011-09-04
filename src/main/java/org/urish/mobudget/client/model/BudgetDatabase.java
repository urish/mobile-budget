package org.urish.mobudget.client.model;

import java.util.List;

public interface BudgetDatabase {
	List<BudgetLine> getData();

	int getTotalBudget();
}
