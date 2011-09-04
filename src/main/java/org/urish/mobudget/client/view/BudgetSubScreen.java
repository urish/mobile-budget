package org.urish.mobudget.client.view;

import java.util.ArrayList;
import java.util.List;

import org.urish.gwtit.client.font.Font;
import org.urish.gwtit.client.font.FontWeight;
import org.urish.gwtit.client.util.Javascript;
import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.Label;
import org.urish.gwtit.titanium.ui.TableViewRow;
import org.urish.gwtit.titanium.ui.events.RowClickEvent;
import org.urish.mobudget.client.model.BudgetLine;

public class BudgetSubScreen extends BudgetViewScreen {
	final String parentCode;
	final BudgetLine budgetLine;
	private TableViewRow commentsRow;

	public BudgetSubScreen(BudgetLine budgetLine) {
		super();
		this.budgetLine = budgetLine;
		this.parentCode = budgetLine.getCode();
		getWindow().setTitle(budgetLine.getTitle());
	}

	private TableViewRow createHeaderRow() {
		TableViewRow result = UI.createTableViewRow();

		result.setSelectedBackgroundColor("white");
		result.setHeight("auto");
		result.setLayout("vertical");

		String code = "";
		if (budgetLine.getCode().length() >= 4) {
			code = budgetLine.getCode().substring(0, 4).replaceAll("^0+", "");
		}
		if (budgetLine.getCode().length() >= 6) {
			code += "." + budgetLine.getCode().substring(4, 6).replaceAll("^0+", "");
		}
		if (budgetLine.getCode().length() >= 8) {
			code += "." + budgetLine.getCode().substring(6, 8).replaceAll("^0+", "");
		}

		Label topLabel = UI.createLabel();
		topLabel.setWidth(320);
		topLabel.setHeight("auto");
		topLabel.setText(code + ". " + budgetLine.getTitle());
		topLabel.setFont(Font.createFont(12, FontWeight.BOLD));
		topLabel.setTextAlign(UI.TEXT_ALIGNMENT_CENTER);
		topLabel.setTop(4);
		topLabel.setBottom(4);
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
	
	private TableViewRow createCommentsRow() {
		TableViewRow result = UI.createTableViewRow();

		result.setHasChild(true);
		result.setTitle("5 " + Strings.COMMENTS);

		return result;
	}

	
	@Override
	protected void populateView() {
		getTableView().appendRow(createHeaderRow());
		commentsRow = createCommentsRow();
		getTableView().appendRow(commentsRow);
		super.populateView();
	}
	
	@Override
	public void onRowClick(RowClickEvent event) {
		if (event.getRow().equals(commentsRow)) {
			Javascript.alert("Should show comments view...");
		}
		super.onRowClick(event);
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
