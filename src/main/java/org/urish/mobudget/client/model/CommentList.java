package org.urish.mobudget.client.model;

import java.util.List;

public interface CommentList {
	GenerationData getGeneration();

	List<CommentInfo> getItems();
}
