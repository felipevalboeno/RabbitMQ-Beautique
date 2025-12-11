package br.com.beautique.api.service;

public interface BrokerService {
    public void send(String type, Object message);
}
