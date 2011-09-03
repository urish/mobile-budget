package org.urish.mobudget.client.view;

import java.util.ArrayList;
import java.util.List;

import org.urish.gwtit.titanium.UI;
import org.urish.gwtit.titanium.ui.SearchBar;
import org.urish.gwtit.titanium.ui.TableView;
import org.urish.gwtit.titanium.ui.TableViewRow;
import org.urish.gwtit.titanium.ui.Window;
import org.urish.gwtit.titanium.ui.events.SearchBarChangeEvent;
import org.urish.gwtit.titanium.ui.events.SearchBarChangeHandler;
import org.urish.mobudget.client.logger.Logger;
import org.urish.mobudget.client.logger.LoggerFactory;
import org.urish.mobudget.client.model.BudgetLine;

public class SearchScreen extends BaseBudgetView implements SearchBarChangeHandler {
	private final static Logger logger = LoggerFactory.get(SearchScreen.class);

	private final Window view;
	private final TableView searchWindow;
	private final SearchBar searchBar;

	public SearchScreen() {
		view = UI.createWindow();
		view.setTitle("חיפוש סעיפים");
		searchWindow = UI.createTableView();
		searchWindow.setFilterAttribute("searchText");
		searchBar = UI.createSearchBar();
		searchBar.addChangeHandler(this);
		searchWindow.setSearch(searchBar);
		view.add(searchWindow);
	}

	public Window getView() {
		return view;
	}

	@Override
	public void onSearchBarChange(SearchBarChangeEvent event) {
		String value = searchBar.getValue();
		logger.info("Search for: " + searchBar.getValue());
		if (!value.trim().equals("") && (searchWindow.getData().length == 0)) {
			final List<BudgetLine> filtered = new ArrayList<BudgetLine>();
			for (BudgetLine line : getBudgetDatabase().getData()) {
				if (line.getTitle().contains(value)) {
					filtered.add(line);
				}
			}
			sortBudget(filtered);

			logger.info("Starting to process, size=" + filtered.size());
			TableViewRow rows[] = new TableViewRow[filtered.size()];
			for (int i = 0; i < rows.length; i++) {
				rows[i] = createBudgetRow(filtered.get(i), 0);
			}
			logger.info("Processing done, setting...");
			searchWindow.setData(rows);
			logger.info("Handler finished.");
			searchBar.setValue(value);
		} else if (value.trim().equals("")) {
			searchWindow.setData(new TableViewRow[0]);
		}
	}
}
