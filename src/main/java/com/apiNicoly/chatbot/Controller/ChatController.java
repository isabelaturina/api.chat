package com.apiNicoly.chatbot.Controller;

import com.apiNicoly.chatbot.Service.ChatService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService groqService;

    public ChatController(ChatService groqService) {
        this.groqService = groqService;
    }

    @PostMapping
    public Mono<String> enviarMensagem(@RequestBody MensagemRequest request) {
        return groqService.enviarMensagem(request.getMessage());
    }

    @Data
    @NoArgsConstructor // Adicionei essa anotação, que é uma boa prática
    @AllArgsConstructor // Adicionei essa anotação, que é uma boa prática
    public static class MensagemRequest {
        private String message; // O nome da variável foi corrigido
    }
}