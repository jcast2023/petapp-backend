package com.petapp.backend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SignalController {

    private final SimpMessagingTemplate messagingTemplate;

    public SignalController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Cuando un usuario envía una oferta, respuesta o candidato ICE
    @MessageMapping("/signal/{roomId}")
    public void procesarSenal(@DestinationVariable String roomId, @Payload JsonNode payload) {
        // Reenvía el mensaje al otro usuario en la misma sala
        messagingTemplate.convertAndSend("/topic/room/" + roomId, payload);
    }
}