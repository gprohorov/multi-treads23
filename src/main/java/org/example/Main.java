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

    public static  volatile List<Ticket> tickets = new ArrayList<>();
    public static int takings = 0;
    public static boolean uploaded = false;

    public static void randomDelay(int from, int to) {
        Random random = new Random();
        int delay = 0;
        if (from == to) {
            delay = from;
        } else {
            delay = random.nextInt(from, to);
        }
        try {
            Thread.sleep(1000);
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
    public static void delay(int sec) {
        randomDelay(sec, sec);
    }
    public static void delayMs(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
//----------------------------------------------------------------------------------------

    public static void uploadTickets(){
        delay(5);
        tickets = new ArrayList<>(Arrays.asList(
                new Ticket(1, 120, false),
                new Ticket(2, 130,false),
                new Ticket(3, 150,false),
                new Ticket(4, 110,false),
                new Ticket(5,50, true)
        ));
        System.out.println(" Tickets are uploaded!");
    }

    public static void uploadThread(int ms) {
        Runnable task = () -> {
            System.out.println("Uploading ...");
            delayMs(ms);
            synchronized (tickets) {
                tickets.add(new Ticket(1, 120,false));
                tickets.add(new Ticket(2, 130,false));
                tickets.add(new Ticket(3, 150,false));
                tickets.add(new Ticket(4, 110, false));
                tickets.add(new Ticket(5, 50, true));
                uploaded = true;
                tickets.notifyAll();
                System.out.println(" --------UPLOADED ---------");
            }
        };
        Thread thread = new Thread(task, " Upload process");
        thread.start();
    }

    public static Ticket selectTicket(List<Ticket> tickets, int sec) {
        Ticket result = null;
        synchronized (tickets) {
            while (!uploaded) {
                try {
                    tickets.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            result = tickets.stream()
                    .filter(ticket -> ticket.isSold() == false)
                    .findFirst()
                    .orElse(null);
        }
        return result;
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
    public static synchronized void  payAndGetTicket(Client client, int sec) {
        Ticket ticket = selectTicket(tickets, sec);
        if (ticket == null) {
            System.out.println("---- " + client.getName() + " FAILED");
            return;}

        pay(client, ticket);
        getTicket(client, ticket);

        System.out.println(client.getName() + " "
                + ticket.getPlace() + " place");
    }
    public static void useThread(Client client, int sec) {
        Runnable task = () -> {
            payAndGetTicket(client, sec);
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
        uploadThread(5000);
        //
        for (int i = 0; i < clients.size(); i++) {
            useThread(clients.get(i), 1);
        }
        System.out.println("-------------------------");


    }
}
