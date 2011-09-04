package org.urish.mobudget.client.model;

import java.util.Date;

public interface CommentSummary {
	public String getCode();

	public int totalComments();

	public Date lastUpdated();
}
