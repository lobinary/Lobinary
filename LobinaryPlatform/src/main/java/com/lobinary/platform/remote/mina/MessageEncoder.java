package com.lobinary.platform.remote.mina;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.lobinary.platform.model.db.InteractionMessage;

public class MessageEncoder extends ProtocolEncoderAdapter {
	
	private final Charset charset;
	
	public MessageEncoder(Charset charset) {
		super();
		this.charset = charset;
	}

	@Override
	public void encode(IoSession ioSession, Object obj, ProtocolEncoderOutput out) throws Exception {
		InteractionMessage message = (InteractionMessage)obj;
        CharsetEncoder charst = charset.newEncoder();
        IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
        String statusLine = "M sip:wap.fetion.com.cn SIP-C/2.0";
        long sender = message.getSenderId();
        long recevier = message.getReceiverId();
        String messageInfo = message.getMessageInfo();
        buffer.putString(statusLine+'\n', charst);
        buffer.putString("S: "+sender+'\n', charst);
        buffer.putString("R: "+recevier+'\n', charst);
        buffer.putString("M: "+messageInfo.getBytes(charset).length+"\n", charst);
        buffer.putString(messageInfo, charst);
        buffer.flip();
        out.write(buffer);
	}

}
