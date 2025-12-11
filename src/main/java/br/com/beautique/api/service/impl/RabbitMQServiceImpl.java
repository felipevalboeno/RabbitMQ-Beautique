package br.com.beautique.api.service.impl;

import br.com.beautique.api.configurations.RabbitMQTopicConfig;
import br.com.beautique.api.service.BrokerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceImpl implements BrokerService {

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQTopicConfig rabbitMQTopicConfig;



    public RabbitMQServiceImpl(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, RabbitMQTopicConfig rabbitMQTopicConfig, TopicExchange topicExchange){
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQTopicConfig = rabbitMQTopicConfig;

    }

    @Override
    public void send(String type, Object data){
        String routineKey = type + ".#";
        try{
            String jsonData = objectMapper.writeValueAsString(data);
            rabbitTemplate.convertAndSend(rabbitMQTopicConfig.exchange().getName(), routineKey, jsonData, message ->{
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;

            } );

        }catch (JsonProcessingException e){
            throw new RuntimeException("Error serializing method" + e.getMessage());
        }

    }
}
