/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.spyhunter99.wsstest.websockettest;

/**
 *
 * @author alex
 */
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/actions")
public class SocketServer {

     
     private static SessionHandler sessionHandler= new SessionHandler();

     @OnOpen
     public void open(Session session) {
          //session.getContainer().
          sessionHandler.addSession(session);
          
     }
     //first hit, basicla serialization goes here
     @OnMessage
     public void handleMessage(String message, Session session) {
          try (JsonReader reader = Json.createReader(new StringReader(message))) {
               JsonObject jsonMessage = reader.readObject();

               if ("add".equals(jsonMessage.getString("action"))) {
                    Model device = new Model();
                    device.setName(jsonMessage.getString("name"));
                    device.setDescription(jsonMessage.getString("description"));
                    device.setType(jsonMessage.getString("type"));
                    device.setStatus("Off");
                    sessionHandler.addDevice(device);
               }

               if ("remove".equals(jsonMessage.getString("action"))) {
                    int id = (int) jsonMessage.getInt("id");
                    sessionHandler.removeDevice(id);
               }

               if ("toggle".equals(jsonMessage.getString("action"))) {
                    int id = (int) jsonMessage.getInt("id");
                    sessionHandler.toggleDevice(id);
               }
          }
     }

     @OnClose
     public void close(Session session) {
          sessionHandler.removeSession(session);
     }

     @OnError
     public void onError(Throwable error) {
          Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, error);
     }
}
