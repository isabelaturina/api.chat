package com.apiNicoly.chatbot.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class RequestDTO {
    private String model;
    private List<Map<String, String>> messages;
}