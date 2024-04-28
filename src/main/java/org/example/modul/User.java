package org.example.modul;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.states.BotState;
import org.example.states.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long chatId;
    private String fullName;
    private Role role;
    private Object grade;
    private BotState botState;
}
