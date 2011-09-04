package org.urish.mobudget.client.model;

import java.util.Date;

public interface CommentInfo {
	public int getId();
	public String getAuthor();
	public String getContent();
	public Date getDate();
	public int getVotes();
}
