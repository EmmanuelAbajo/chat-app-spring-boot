package com.example.chatapp.controller;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.chatapp.model.ChatInMessage;
import com.example.chatapp.model.ChatOutMessage;

@Controller
public class ChatController {
	
	@MessageMapping("/guestchat") // map handler method to URI
	@SendTo("/topic/guestchats") // map handler to subscription queue/topic for message sending and receival
	public ChatOutMessage handleMessage(ChatInMessage message) throws Exception {
		// when a message is received, the server broadcasts the response to subscribers of the topic
		Thread.sleep(1000); // simulate server processing
		return new ChatOutMessage(message.getMessage());
	}
	
	@MessageMapping("/guestupdate")
	@SendTo("/topic/guestupdates") 
	public ChatOutMessage handleUserTyping(ChatInMessage message) throws Exception {
		return new ChatOutMessage("Someone is typing");
	}
	
	@MessageExceptionHandler
	@SendTo("/topic/errors") 
	public ChatOutMessage handleException(Throwable exception) {
		return new ChatOutMessage("An error occured!");
	}


}
