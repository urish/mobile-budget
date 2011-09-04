package org.urish.mobudget.client.logger;

import org.urish.gwtit.titanium.API;

class LoggerImpl implements Logger {
	private final String name;

	public LoggerImpl(String name) {
		this.name = name;
	}

	protected String decorate(String message) {
		return "[" + name + "] " + message;
	}

	@Override
	public void info(String message) {
		API.info(decorate(message));
	}

	@Override
	public void warn(String message) {
		API.warn(decorate(message));
	}

	@Override
	public void error(String message) {
		API.error(decorate(message));
	}

	@Override
	public void debug(String message) {
		API.debug(decorate(message));
	}
}
