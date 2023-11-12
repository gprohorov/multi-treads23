package org.example;


/*
  @author   george
  @project   Default (Template) Project
  @class  ${NAME}
  @version  1.0.0 
  @since 09.11.23 - 10.16
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        TicketService service = new TicketService();
        service.loadTicketBoxAsThread(5);
        Client john = new Client("John", 500);
        service.serveClient(john);
        System.out.println("-------------------------");
        System.out.println(john);
        System.out.println(service.getTickets().size());
        System.out.println(service.getSoldTickets().size());
        System.out.println(service.getTakings());





    }
}
