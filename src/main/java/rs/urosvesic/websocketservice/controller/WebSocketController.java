package rs.urosvesic.websocketservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.urosvesic.websocketservice.dto.MessageResponse;
import rs.urosvesic.websocketservice.dto.NotificationDto;
import rs.urosvesic.websocketservice.service.WebSocketService;

@RestController
@RequestMapping("/api/websocket")
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketService webSocketService;

    @PostMapping("/message")
    public void notifyForSentMessage(@RequestBody MessageResponse messageResponse){
        webSocketService.sendMessage(messageResponse.getFrom(),messageResponse);
        System.out.println("Sent message "+messageResponse);
    }

    @PostMapping("/notification")
    public void notifyForSentNotification(@RequestBody NotificationDto notificationDto){
        webSocketService.sendNotification(notificationDto.getReceiver(),notificationDto);
        System.out.println("Notification of type "
                +notificationDto.getNotificationType()
                +"for  "
                +notificationDto);
    }
}
