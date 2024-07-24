package com.github.travelbuddy.chat.entity;

import com.github.travelbuddy.chat.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document
public class ChatUser {
    @Id
    private String id;
    private String userName;
    private Status status;
}