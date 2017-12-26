package com.fh.filter;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.fh.controller.base.BaseController;
import com.fh.plugin.websocketInstantMsg.ChatServer;
import com.fh.plugin.websocketOnline.OnlineChatServer;

public class startFilter extends BaseController implements Filter {
	public void init(FilterConfig fc) throws ServletException {
		startWebsocketInstantMsg();
		startWebsocketOnline();
	}

	public void startWebsocketInstantMsg() {
		org.java_websocket.WebSocketImpl.DEBUG = false;
		int port = 8887;
		try {
			ChatServer s = new ChatServer(port);
			s.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void startWebsocketOnline() {
		org.java_websocket.WebSocketImpl.DEBUG = false;
		int port = 8889;
		try {
			OnlineChatServer s = new OnlineChatServer(port);
			s.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void timer() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(11, 9);
		calendar.set(12, 0);
		calendar.set(13, 0);

		Date time = calendar.getTime();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
			}
		}, time, 86400000L);
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
	}
} 