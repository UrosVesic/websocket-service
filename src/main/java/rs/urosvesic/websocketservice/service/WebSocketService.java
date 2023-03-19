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
        System.out.println("Sending notification to topic /topic/notification/"+username);
        System.out.println("Notification: "+dto.convertToJsonString());
        messagingTemplate.convertAndSend("/topic/notification/"+username,dto.convertToJsonString());
    }

    public void sendNotificationToUser(String to,Dto dto){
        messagingTemplate.convertAndSendToUser(to, "/topic/notification",dto.convertToJsonString());
    }


    public void sendNotificationForMessage(String username) {
        messagingTemplate.convertAndSend("/topic/notification/"+username,"message");
    }
}
