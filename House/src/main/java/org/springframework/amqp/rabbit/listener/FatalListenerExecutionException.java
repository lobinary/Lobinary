/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.springframework.amqp.rabbit.listener;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * Exception to be thrown when the execution of a listener method failed unrecoverably.
 *
 * @author Dave Syer
 * @see MessageListenerAdapter
 */
@SuppressWarnings("serial")
public class FatalListenerExecutionException extends AmqpException {

	/**
	 * Constructor for ListenerExecutionFailedException.
	 * @param msg the detail message
	 * @param cause the exception thrown by the listener method
	 */
	public FatalListenerExecutionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Constructor for ListenerExecutionFailedException.
	 * @param msg the detail message
	 */
	public FatalListenerExecutionException(String msg) {
		super(msg);
	}

}
