package org.urish.mobudget.client.net;

import org.urish.gwtit.client.EventCallback;
import org.urish.gwtit.client.util.Timer;
import org.urish.gwtit.client.util.TimerCallback;
import org.urish.gwtit.client.util.Timers;
import org.urish.gwtit.titanium.Network;
import org.urish.gwtit.titanium.network.HTTPClient;
import org.urish.mobudget.client.logger.Logger;
import org.urish.mobudget.client.logger.LoggerFactory;
import org.urish.mobudget.client.model.GenerationData;

import com.google.gwt.core.client.JavaScriptObject;

public class Updater {
	private final static Logger logger = LoggerFactory.get(Updater.class);

	private String updateUrl;
	private UpdaterCallback callback;
	private GenerationData generationData;
	private Integer updateInterval;
	private Timer timer;

	public Updater(String updateUrl, GenerationData generationData, UpdaterCallback callback) {
		super();
		this.updateUrl = updateUrl;
		this.callback = callback;
		this.generationData = generationData;
	}

	public void onUpdateComplete(String jsonData) {
		if (updateInterval != null) {
			timer = Timers.setTimeout(updateInterval * 1000, new TimerCallback() {

				@Override
				public void onTimerShot(Timer timer) {
					start();
				}
			});
		}

		if (callback != null) {
			callback.onUpdateReceived(jsonData);
		}
	}

	public Integer getUpdateInterval() {
		return updateInterval;
	}

	/**
	 * Sets the update interval.
	 * 
	 * @param updateInterval
	 *            The update interval, in seconds
	 */
	public void setUpdateInterval(Integer updateInterval) {
		this.updateInterval = updateInterval;
		if (updateInterval != null && timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public void start() {
		String urlSuffix = "";
		if (generationData != null) {
			if (updateUrl.indexOf("?") >= 0) {
				urlSuffix += "&";
			} else {
				urlSuffix += "?";
			}
			urlSuffix += "gen=" + generationData.getNumber();
		}
		final String url = updateUrl + urlSuffix;
		logger.info("Loading update from " + url);

		if (timer != null) {
			timer.cancel();
			timer = null;
		}

		final HTTPClient httpClient = Network.createHTTPClient();

		httpClient.setOnload(new EventCallback<JavaScriptObject>() {
			@Override
			public void onEvent(JavaScriptObject event) {
				if (httpClient.getStatus() >= 200 && httpClient.getStatus() < 300) {
					onUpdateComplete(httpClient.getResponseText());
				} else {
					if (httpClient.getStatus() == 304) {
						logger.info("Not modified: " + url);
					} else {
						logger.warn("Failed! [" + url + "] status=" + httpClient.getStatus());
					}
					onUpdateComplete(null);
				}
			}
		});
		httpClient.open("GET", url);
		httpClient.send();
	}
}
