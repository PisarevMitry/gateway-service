package com.mirea.homedepot.gatewayservice.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {


    Logger logger = LoggerFactory.getLogger(RabbitConfiguration.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue catalogQueueGetAllProductCategory() {
        return new Queue("catalogQueueGetAllProductCategory");
    }

    @Bean
    public Queue catalogQueueGetProductCategoryById() {
        return new Queue("catalogQueueGetProductCategoryById");
    }


    @Bean
    public DirectExchange catalogQueueDirectExchange() {
        return new DirectExchange("exchangeCatalogQueue");
    }

    @Bean
    public Binding getAllProductCategoryBinding() {
        return BindingBuilder.bind(catalogQueueGetAllProductCategory()).to(catalogQueueDirectExchange()).with("getAllProductCategory");
    }

    @Bean
    public Binding getProductCategoryByIdBinding() {
        return BindingBuilder.bind(catalogQueueGetProductCategoryById()).to(catalogQueueDirectExchange()).with("getProductCategoryById");
    }
   /* @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange("exchangeCatalogQueue");
        return rabbitTemplate;
    }

    @Bean
    public Queue myQueue1() {
        return new Queue("catalogQueue");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("exchangeCatalogQueue");
    }

    @Bean
    public Binding errorBinding1(){
        return BindingBuilder.bind(myQueue1()).to(directExchange()).with("key.1");
    }

    @Bean
    public Binding errorBinding2(){
        return BindingBuilder.bind(myQueue1()).to(directExchange()).with("key.2");
    }
*/
    /*Logger logger = LoggerFactory.getLogger(RabbitConfiguration.class);

    //Создаем соединение с именем localhost
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    //Модуль управления
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    //Шаблон
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    //Очередь с названием
    @Bean
    public Queue catalogQueue() {
        return new Queue("catalogQueue");
    }

    //Получатель сообщений, читающий очереди
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames("catalogQueue");
        //Здесь нужно прописать обработчик соощений
        container.setMessageListener(message -> logger.info("Recieved from catalogQueue2 : " + new String(message.getBody())));
        return container;
    }

    */

}
