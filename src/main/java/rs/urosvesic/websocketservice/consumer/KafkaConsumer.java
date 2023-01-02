package rs.urosvesic.websocketservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.urosvesic.websocketservice.dto.MessageResponse;
import rs.urosvesic.websocketservice.dto.NotificationDto;
import rs.urosvesic.websocketservice.service.WebSocketService;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final WebSocketService webSocketService;

    //@KafkaListener(topics = "${message.consumer.topic.name}",groupId = "message-consumer")
    public void listenOnMessageTopic(String receivedMessage){
        try {
            System.out.println("Received message");
            ObjectMapper objectMapper = new ObjectMapper();
            MessageResponse messageResponse = objectMapper.readValue(receivedMessage, MessageResponse.class);
            webSocketService.sendMessage(messageResponse.getSender(),messageResponse);
            System.out.println(messageResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't convert from JSON to MessageResponse "+e.getMessage());
        }

    }

    //@KafkaListener(topics = "${notification.consumer.topic.name}",groupId = "message-consumer")
    public void listenOnNotificationTopic(String receivedNotification){
        try {
            System.out.println("Received message");
            ObjectMapper objectMapper = new ObjectMapper();
            NotificationDto notificationDto = objectMapper.readValue(receivedNotification, NotificationDto.class);
            webSocketService.sendNotification(notificationDto.getReceiver(),notificationDto);
            System.out.println(notificationDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't convert from JSON to NotificationDto "+e.getMessage());
        }

    }
}
