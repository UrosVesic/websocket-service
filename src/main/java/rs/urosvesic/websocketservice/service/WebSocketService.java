package rs.urosvesic.websocketservice.service;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import rs.urosvesic.websocketservice.dto.Dto;

@AllArgsConstructor
@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;


    public void sendMessage(String username, Dto dto) {
        messagingTemplate.convertAndSend("/topic/"+username, dto.convertToJsonString());
    }

    public void sendMessageToUser(String to, Dto dto) {
        messagingTemplate.convertAndSendToUser(to,"/topic",dto.convertToJsonString());
    }

    public void sendNotification(String username,Dto dto){
        messagingTemplate.convertAndSend("/topic/notification/"+username,dto);
    }

    public void sendNotificationToUser(String to,Dto dto){
        messagingTemplate.convertAndSendToUser(to, "/topic/notification",dto);
    }


}
