package org.example;
/*
  @author   george
  @project   multi-treads23
  @class  TicketService
  @version  1.0.0 
  @since 11.11.23 - 20.34
*/

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class TicketService {
    private volatile List<Ticket> tickets = new ArrayList<>();
    private List<Ticket> soldTickets = new ArrayList<>();
    private List<Client> clients;
    private int takings = 0;
    private boolean loading = false;

    public synchronized void loadTickets() {
        tickets.add(new Ticket(1, 120, false));
        tickets.add(new Ticket(2, 130, false));
        tickets.add(new Ticket(3, 150, false));
        tickets.add(new Ticket(4, 110, false));
        System.out.println(" Tickets are ready for sale - custom");
    }
    public  void loadTicketBoxAsThread(int interval)  {
        Runnable task = () -> {
            synchronized (tickets) {
                MyUtils.delay(interval);
                tickets.add(new Ticket(1, 120, false));
                tickets.add(new Ticket(2, 130, false));
                tickets.add(new Ticket(3, 150, false));
                tickets.add(new Ticket(4, 110, false));
                System.out.println(" Tickets are ready for sale via thread");
;
            }

        };
        Thread thread = new Thread(task, " TicketBox ");
        thread.start();
        System.out.println(thread.getName() + " started");
    }

    public  Ticket selectOne()  {
        Ticket ticket = null;
        System.out.println("Somebody is trying to get a  ticket");
            synchronized (tickets) {
                try {
                    tickets.wait(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ticket = tickets.stream()
                        .findFirst().orElse(null);
            }
        return ticket;
    }

    public void pay(Client client, Ticket ticket) {
        int money = client.getMoney();
        client.setMoney(money - ticket.getPrice());
        takings += ticket.getPrice();
    }

    public void transferTicketToClient(Ticket ticket, Client client) {
        boolean success = tickets.remove(ticket);
            soldTickets.add(ticket);
            client.setTicket(ticket);
            System.out.println("Success : " + client.getName() + ": " + ticket.getPlace());
    }

    public  void serveClient(Client client)  {

        MyUtils.delay(1);

        Ticket ticket = this.selectOne();
        if (ticket == null) {
            System.out.println(" Tickets are not available for client " + client.getName());
            return;
        }
        pay(client,ticket);
        ticket.setSold(true);
        transferTicketToClient(ticket, client);

    }

}
