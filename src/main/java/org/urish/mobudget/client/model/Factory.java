package org.urish.mobudget.client.model;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface Factory extends AutoBeanFactory {
	public AutoBean<BudgetLine> makeRankInfo();

	public AutoBean<BudgetDatabase> makeRankList();

	public AutoBean<GenerationData> makeGenerationData();

	public AutoBean<CommentInfo> makeCommentInfo();

	public AutoBean<CommentList> makeCommentList();

	public AutoBean<CommentSummary> makeCommentSummary();

	public AutoBean<CommentSummaryList> makeCommentSummaryList();
}
