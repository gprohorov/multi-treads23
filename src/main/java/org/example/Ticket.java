package org.example;
/*
  @author   george
  @project   threads23
  @class  Ticket
  @version  1.0.0 
  @since 09.11.23 - 10.18
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Setter
public class Ticket {
     private int place;
     private String opera = "Phantom of the opera";;
     private LocalDateTime time = LocalDateTime.of(2023,11,15,19,00);
     private int price;
     private boolean sold;

    public Ticket(int place, int price, boolean sold) {
        this.place = place;
        this.price = price;
        this.sold = sold;
    }
}
