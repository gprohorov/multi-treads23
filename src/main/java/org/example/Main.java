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
    public static List<Ticket> sellOut = new ArrayList<>();

    public static List<Ticket> tickets = new ArrayList<>(Arrays.asList(
            new Ticket(1, 120, false),
            new Ticket(2, 130,false),
            new Ticket(3, 150,false),
            new Ticket(4, 110,false),
            new Ticket(5,50, true)
    ));
    public static void randomDelay(int from, int to) {
        Random random = new Random();
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(from,to));
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
    public static int takings = 0;
    public static Ticket selectTicket(List<Ticket> tickets) {
        randomDelay(5,9);
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
        tickets.remove(ticket);
        ticket.setSold(true);
        sellOut.add(ticket);
        client.setTicket(ticket);
    }
    public static void payAndGetTicket(Client client) {
        Ticket ticket = selectTicket(tickets);
        if (ticket == null) return;
        pay(client, ticket);
        getTicket(client, ticket);
        System.out.println(client.getName() + " "
                + client.getTicket().getPlace() + " place");
    }
    public static void useThread(Client client) {
        Runnable task = () -> {
            payAndGetTicket(client);
        };
        Thread thread = new Thread(task, client.getName());
        thread.start();
        System.out.println(thread.getName() + " strated");
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



        for (int i = 0; i < clients.size(); i++) {
            useThread(clients.get(i));
        }
        System.out.println("-------- tickets distribution -----------------");


    }
}
