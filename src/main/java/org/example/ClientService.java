package org.example;
/*
  @author   george
  @project   multi-treads23
  @class  ClientService
  @version  1.0.0 
  @since 11.11.23 - 20.28
*/

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientService {
    private List<Client> clients = new ArrayList<>();


    public void addClient(Client client) {
        getClients().add(client);
    }
    public void removeClient(Client client) {
        getClients().add(client);
    }
    public Client getClient(int i) {
        return clients.get(i);
    }
    public int pay(Client client, Ticket ticket) {
        if (ticket == null) return 0;
        int price = ticket.getPrice();
        client.setMoney(client.getMoney() - price);
        return price;
    }
    public void getTicket(Ticket ticket, Client client) {
        client.setTicket(ticket);
    }
}
