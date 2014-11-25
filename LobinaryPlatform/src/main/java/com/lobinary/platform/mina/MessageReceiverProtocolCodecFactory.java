package com.lobinary.platform.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MessageReceiverProtocolCodecFactory implements ProtocolCodecFactory {

	private final MessageEncoder encoder;
	private final MessageDecoder decoder;

	public MessageReceiverProtocolCodecFactory() {
		this(Charset.defaultCharset());
	}

	/**
	 * @param defaultCharset
	 */
	public MessageReceiverProtocolCodecFactory(Charset charSet) {
		this.encoder = new MessageEncoder(charSet);
		this.decoder = new MessageDecoder(charSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.filter.codec.ProtocolCodecFactory#getDecoder(org.apache
	 * .mina.core.session.IoSession)
	 */
	@Override
	public ProtocolDecoder getDecoder(IoSession iosession) throws Exception {
		// TODO Auto-generated method stub
		return decoder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.filter.codec.ProtocolCodecFactory#getEncoder(org.apache
	 * .mina.core.session.IoSession)
	 */
	@Override
	public ProtocolEncoder getEncoder(IoSession iosession) throws Exception {
		// TODO Auto-generated method stub
		return encoder;
	}

}
