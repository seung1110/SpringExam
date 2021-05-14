package org.psy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@ServerEndpoint(value="/socket")
public class SocketController {
	
	@GetMapping("/test")
	public String test(){
		return "test";
	}
	
	private static final List<Session> sessionList = new ArrayList<Session>();
	
	public SocketController(){
		System.out.println("web socket create");
	}
	
	@OnOpen
	public void onOpen(Session session){
		log.info("open session : " + session.getId());
		try{
			final Basic basic = session.getBasicRemote();
			basic.sendText("연결 완료");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		sessionList.add(session);
	}
	
	private void sendAllSessionToMessage(Session self,String msg){
		try {
			for(Session s : SocketController.sessionList){
				if(!self.getId().equals(s.getId())){
					s.getBasicRemote().sendText("change" + s.getId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	@OnMessage
	public void onMessage (String message, Session session){
		log.info("message");
		try {
			//메세지 보낸 사람에게 표시될 내용
			final Basic basic = session.getBasicRemote();
			basic.sendText("전송 완료.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// 다른 사람에게 메세지 보내기
		sendAllSessionToMessage(session,message);
	}
	
	@OnError
	public void onError(Throwable e, Session session){
		log.info(e.getMessage() + "by session : " + session.getId());
	}
	@OnClose
	public void onClose(Session session){
		log.info("Session : "+ session.getId() + " closed");
		sessionList.remove(session);
	}
}
