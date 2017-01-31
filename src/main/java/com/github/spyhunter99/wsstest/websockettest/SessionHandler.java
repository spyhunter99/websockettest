/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.spyhunter99.wsstest.websockettest;

import java.io.IOException;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

@ApplicationScoped
public class SessionHandler {

     private int deviceId = 0;

     private final Set<Session> sessions = new HashSet<>();
     private final Set<Model> devices = new HashSet<>();

     public void addSession(Session session) {
          sessions.add(session);
          for (Model device : devices) {
               JsonObject addMessage = createAddMessage(device);
               sendToSession(session, addMessage);
          }
     }

     public void removeSession(Session session) {
          sessions.remove(session);
     }

     public List<Model> getDevices() {
          return new ArrayList<>(devices);
     }

     public void addDevice(Model device) {
          device.setId(deviceId);
          devices.add(device);
          deviceId++;
          JsonObject addMessage = createAddMessage(device);
          sendToAllConnectedSessions(addMessage);
     }

     public void removeDevice(int id) {
          Model device = getDeviceById(id);
          if (device != null) {
               devices.remove(device);
               JsonProvider provider = JsonProvider.provider();
               JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
               sendToAllConnectedSessions(removeMessage);
          }
     }

     public void toggleDevice(int id) {
          JsonProvider provider = JsonProvider.provider();
          Model device = getDeviceById(id);
          if (device != null) {
               if ("On".equals(device.getStatus())) {
                    device.setStatus("Off");
               } else {
                    device.setStatus("On");
               }
               JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", device.getId())
                    .add("status", device.getStatus())
                    .build();
               sendToAllConnectedSessions(updateDevMessage);
          }
     }

     private Model getDeviceById(int id) {
          for (Model device : devices) {
               if (device.getId() == id) {
                    return device;
               }
          }
          return null;
     }

     private JsonObject createAddMessage(Model device) {
          JsonProvider provider = JsonProvider.provider();
          JsonObject addMessage = provider.createObjectBuilder()
               .add("action", "add")
               .add("id", device.getId())
               .add("name", device.getName())
               .add("type", device.getType())
               .add("status", device.getStatus())
               .add("description", device.getDescription())
               .build();
          return addMessage;
     }

     //useful user online/offline messages, shriek?
     //new database records, new mappable items from database
     //updated database stats?
     
     private void sendToAllConnectedSessions(JsonObject message) {
          for (Session session : sessions) {
               sendToSession(session, message);
          }
     }

     private void sendToSession(Session session, JsonObject message) {
          try {
               session.getBasicRemote().sendText(message.toString());
          } catch (Exception ex) {
               sessions.remove(session);
               Logger.getLogger(BaseSocketServer.class.getName()).log(Level.SEVERE, null, ex);
          }
     }
}
