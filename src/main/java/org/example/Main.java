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
    public static  List<Ticket> sellOut = new ArrayList<>();

    public static   List<Ticket> tickets = new ArrayList<>();
    public static int takings = 0;

    public static void randomDelay(int from, int to) {
        Random random = new Random();
        int delay = 0;
        if (from == to) {
            delay = from;
        } else {
            delay = random.nextInt(from, to);
        }
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
    public static void delay(int interval) {
        randomDelay(interval, interval);
    }
    public static void delayMs(int interval) {
        try {
            TimeUnit.MILLISECONDS.sleep(interval);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
//----------------------------------------------------------------------------------------
    public static void fillTicketBoxAsThread()  {
        Runnable task = () -> {
         //   delay(3);
            tickets.add(new Ticket(1, 120, false));
            tickets.add(new Ticket(2, 130, false));
            tickets.add(new Ticket(3, 150, false));
            tickets.add(new Ticket(4, 110, false));
        };
        Thread thread = new Thread(task, " TicketBox ");
        thread.start();
        System.out.println(thread.getName() + " started");

  ;

    }
    public static  Ticket selectTicket(List<Ticket> tickets) {
    randomDelay(0,5);
        return tickets.stream()
                .filter(ticket -> ticket.isSold() == false)
                .findFirst()
                .orElse(null);
    }
    public static void pay(Client client, Ticket ticket) {
        int price = ticket.getPrice();
        int clientMoney = client.getMoney();

        client.setMoney(clientMoney - price);
        takings += price;
    }

    public static void getTicket(Client client, Ticket ticket) {
        delay(5);
        tickets.remove(ticket);
        ticket.setSold(true);
        sellOut.add(ticket);
        client.setTicket(ticket);
    }
    public static synchronized void  payAndGetTicket(Client client) {

            Ticket ticket = selectTicket(tickets);
            if (ticket == null) return;
            pay(client, ticket);

                getTicket(client, ticket);


            System.out.println(client.getName() + " "
                    + ticket.getPlace() + " place");

    }
    public static void useThread(Client client) {
        Runnable task = () -> {

            payAndGetTicket(client);
        };
        Thread thread = new Thread(task, client.getName());
        thread.start();
        System.out.println(thread.getName() + " started");
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");



        List<Client> clients = new ArrayList<>(Arrays.asList(
                new Client("John", 500),
                new Client("Paul", 200),
                new Client("George", 150),
                new Client("Ringo", 250),
                new Client("Freddie", 1000)
        ));
 fillTicketBoxAsThread();
 useThread(clients.get(0));
 useThread(clients.get(1));
 useThread(clients.get(2));
 useThread(clients.get(3));

        System.out.println("-------------------------");


    }
}
