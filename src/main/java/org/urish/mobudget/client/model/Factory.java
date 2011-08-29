package org.urish.mobudget.client.model;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface Factory extends AutoBeanFactory {
	public AutoBean<BudgetLine> makeRankInfo();
	public AutoBean<BudgetDatabase> makeRankList();
}
