package com.mirea.homedepot.gatewayservice.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Validated
@RestController
@RequestMapping("/category")
public class ProductCategoryController {
    Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);
    String hostname = "Unknown";

    final RabbitTemplate template;

    public ProductCategoryController(RabbitTemplate template) {
        this.template = template;
    }

    @GetMapping("/get")
    String getAllProductCategory() {
        logger.info("Sending to catalogQueue");
        String result = String.valueOf(template.convertSendAndReceive("exchangeCatalogQueue", "getAllProductCategory", "message"));

        try {
            InetAddress address;
            address = InetAddress.getLocalHost();
            hostname = address.getHostName();

        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }

        logger.info("Get from catalogQueue " + result);
        return result;
    }

    @GetMapping("/get/item/{id}")
    String getProductCategoryById(@PathVariable Long id) {
        logger.info("Sending to catalogQueue");
        String result = String.valueOf(template.convertSendAndReceive("exchangeCatalogQueue", "getProductCategoryById", "String.valueOf(id)"));

        try {
            InetAddress address;
            address = InetAddress.getLocalHost();
            hostname = address.getHostName();

        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }

        logger.info("Get from catalogQueue" + result);
        return result;
    }

    /*@GetMapping("/get/list")
    ResponseEntity getProductCategoryByListId(@RequestParam List<Long> listId) {
        template.convertAndSend("catalogQueue", "getProductCategoryByListId");

        try {
            InetAddress address;
            address = InetAddress.getLocalHost();
            hostname = address.getHostName();

        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }

        return ResponseEntity.ok(hostname + ": " + "Success sent to queue");
    }

    @GetMapping("/get/relative/list")
    ResponseEntity getProductCategoryByParentId(@RequestParam Long id) {
        template.convertAndSend("catalogQueue", "getProductCategoryByParentId");

        try {
            InetAddress address;
            address = InetAddress.getLocalHost();
            hostname = address.getHostName();

        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }

        return ResponseEntity.ok(hostname + ": " + "Success sent to queue");
    }*/
}
