package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

/*
  @author   george
  @project   threads23
  @class  Client
  @version  1.0.0 
  @since 09.11.23 - 11.36
*/
@Data
@AllArgsConstructor
@Setter
public class Client {
    private String name;
    private Ticket ticket;
    private int money;

    public Client(String name, int money) {
        this.name = name;
        this.money = money;
    }
}
