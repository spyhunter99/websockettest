/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.spyhunter99.wsstest.websockettest;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@javax.enterprise.context.ApplicationScoped
@ServerEndpoint("/WebSocketEndpoint")
public class WebSocketEndpoint extends BaseSocketServer {

    public WebSocketEndpoint() throws Exception {

        System.out.println("WebSocketEndpoint started");

    }

    //inbound message from the web socket(client)
    @OnMessage
    public void handleMessage(String message, Session session) {
        try {
            JsonReader reader = Json.createReader(new StringReader(message));
            JsonObject jsonMessage = reader.readObject();

            final String action = jsonMessage.getString("action");
            System.out.println("WSS inbound from " + session.getId() + " action " + action);
            if ("broadcast".equals(action)) {
                try {
                    JsonProvider provider = JsonProvider.provider();

                    JsonObject addMessage = provider.createObjectBuilder()
                            .add("action", action)
                            .add("echo", "Broadcast!")
                            .build();

                    sendToAllConnectedSessions(addMessage);
                    

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                try {
                    JsonProvider provider = JsonProvider.provider();

                    JsonObject addMessage = provider.createObjectBuilder()
                            .add("echo", "Hello world from web socket endpoint!")
                            .add("action", action)
                            .build();
                    sendToAllConnectedSessions(addMessage);
                    //session.getBasicRemote().sendText(addMessage.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

   
}
