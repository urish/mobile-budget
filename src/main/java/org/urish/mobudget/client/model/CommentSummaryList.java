package org.urish.mobudget.client.model;

import java.util.Map;

public interface CommentSummaryList {
	GenerationData getGeneration();

	Map<String, CommentSummary> getItems();
}
