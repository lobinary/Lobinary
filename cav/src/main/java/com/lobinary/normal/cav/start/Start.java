package com.lobinary.normal.cav.start;

import java.net.SocketTimeoutException;

import com.lobinary.normal.cav.service.WebService;

public class Start {
	
	public static void main(String[] args) throws SocketTimeoutException {
		WebService ws = new WebService();
		ws.service();
	}

}
