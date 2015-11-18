package com.lobinary.platform.remote.mina;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.lobinary.platform.model.db.InteractionMessage;

public class MessageDecoder extends CumulativeProtocolDecoder {
	
	private final Charset charset;

	public MessageDecoder(Charset charset) {
		super();
		this.charset = charset;
	}

	@Override
	public boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput out) throws Exception {
        IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
        CharsetDecoder decoder = charset.newDecoder();
        int matchCount = 0;

        String statusLine = "";
        String sender = "";
        String receiver = "";
        String length = "";
        String sms="";
        int i = 1;
        
        while(ioBuffer.hasRemaining()){
            byte b = ioBuffer.get();
            buffer.put(b);
            
            if(b==10&&i< 5){
                matchCount ++;
                if(i == 1){
                    buffer.flip();
                    statusLine = buffer.getString(matchCount,decoder);
                    statusLine = statusLine.substring(0, statusLine.length()-1);
                    matchCount = 0;
                    buffer.clear();
                }
                if(i == 2){
                    buffer.flip();
                    sender = buffer.getString(matchCount,decoder);
                    sender = sender.substring(0, sender.length()-1);
                    matchCount = 0;
                    buffer.clear();
                }
                if(i == 3){
                    buffer.flip();
                    receiver = buffer.getString(matchCount,decoder);
                    receiver = receiver.substring(0, receiver.length()-1);
                    matchCount = 0;
                    buffer.clear();
                }
                if(i == 4){
                    buffer.flip();
                    length = buffer.getString(matchCount,decoder);
                    length = length.substring(0, length.length()-1);
                    matchCount = 0;
                    buffer.clear();
                }
                
                i++;
            }else if(i == 5){
                matchCount ++;
                if(matchCount == Long.parseLong(length.split(": ")[1])){
                    buffer.flip();
                    sms = buffer.getString(matchCount,decoder);
                    i++;
                    break;
                }
            }else{
                matchCount ++;
            }
        }
        InteractionMessage message = new InteractionMessage();
        message.setSenderId(Long.parseLong(sender.split(": ")[1]));
        message.setReceiverId(Long.parseLong(receiver.split(": ")[1]));
        message.setMessageInfo(sms);
        out.write(message);
        return false;
	}

}
