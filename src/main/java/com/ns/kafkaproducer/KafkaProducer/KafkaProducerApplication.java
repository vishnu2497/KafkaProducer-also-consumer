package com.ns.kafkaproducer.KafkaProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@RestController
public class KafkaProducerApplication {

    private String topic = "usertopic";

    @Autowired
    private KafkaTemplate<String, User> template;

    @Autowired
    private KafkaTemplate<String, String> template1;


    @GetMapping(value = "/publish/{name}")
    public String publishMessage(@PathVariable String name) {
        template1.send(topic, "Hi" + name + "this is from springBoot Kafka prodicer");
        return "data published Succesfully";
    }

    @GetMapping(value = "/publishjson")
    public String publishJson() {
        User user1 = new User("vishnu", 10);

        template.send(topic, user1);

        return "Json data Published Successfully";

    }

    @KafkaListener(topics = "usertopic1", groupId = "asdf")
    public void listenForConsumre(String data) {
        System.out.println("Message From Consumer Side But uis is  in Producer==>" + data);

    }

//    @GetMapping(value = "/file")
//    public String sendFile() throws IOException {
//
//        Path path = Paths.get("C:\\Users\\vmuralidharan\\Downloads\\KafkaProducer\\KafkaProducer\\pom.xml");
//
//        String data = new String(Files.readAllBytes(path));
//
//        template.send(topic, data);
//        return "File posted succesfully";
//    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

}
