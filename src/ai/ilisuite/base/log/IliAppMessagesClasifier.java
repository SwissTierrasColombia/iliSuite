package ai.ilisuite.base.log;

public class IliAppMessagesClasifier implements IliAppStreamProcessor {
	private IliMessageType lastMessageType;
	private String lastMessage;
	private IliMessageReceiver receiver;
	
	public IliAppMessagesClasifier(IliMessageReceiver receiver) {
	    lastMessageType = null;
	    lastMessage = "";
	    
	    this.receiver = receiver;
	}

	@Override
	public void writeMessage(String message, boolean isErrorStream) {
		IliMessageType currentMessageType;

		String endOfLine = "";
		String newMessage;
		if (!message.endsWith("\n")) {
			endOfLine = "\n";
		}
		
		message += endOfLine;
		
		if(message.startsWith("Info: ")) {
			newMessage = message.replace("Info: ", "");
			currentMessageType = IliMessageType.Info;
			
			if(newMessage.charAt(0) == 160) {
				currentMessageType = IliMessageType.RegisterReport;
				newMessage = newMessage.substring(1);
			} else if(isMessageResult(newMessage))
				currentMessageType = IliMessageType.Result;
		} else if(message.startsWith("Warning: ")) {
			currentMessageType = IliMessageType.Warning;
			newMessage = message.replace("Warning: ", "");
		} else if(message.startsWith("Error: ")) {
			currentMessageType = IliMessageType.Error;
			newMessage = message.replace("Error: ", "");
		} else {
			currentMessageType = IliMessageType.Other;
			newMessage = message;
			if(isMessageResult(newMessage))
				currentMessageType = IliMessageType.Result;
		}
		
		boolean differentBlock = currentMessageType != lastMessageType ||
				!Character.isWhitespace(newMessage.charAt(0));

		if(differentBlock && lastMessageType!=null) {
			sendMessage(lastMessage, lastMessageType);
			lastMessage = "";
		}
		
		lastMessage += newMessage;
		lastMessageType = currentMessageType;
	}
	
	private boolean isMessageResult(String message) {
		return message.startsWith("...") &&
				(message.endsWith("done\n") || message.endsWith("failed\n"));
	}
	

	@Override
	public void finishWriting() {
		sendMessage(lastMessage, lastMessageType);
		lastMessageType = null;
		lastMessage = "";
	}

	private void sendMessage(String message, IliMessageType messageType) {
		receiver.message(message, messageType);
	}
}
