package com.example.chatjee;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        SendMessage sendMessage = new SendMessage();
        try {
            sendMessage.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Hello, World!";
    }


}