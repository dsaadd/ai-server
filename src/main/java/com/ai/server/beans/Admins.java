package com.ai.server.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admins {

    private int id;
    private String username;
    private String password;
    private String del;
    private String role;

}
