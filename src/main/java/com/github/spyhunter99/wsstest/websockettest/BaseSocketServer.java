/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.spyhunter99.wsstest.websockettest;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.websocket.OnOpen;
import javax.websocket.Session;

public abstract class BaseSocketServer {

     private final Set<Session> sessions = new HashSet<>();

     @OnOpen
     public void open(Session session) {
         
          addSession(session);
          
     }
     public void addSession(Session session) {
          sessions.add(session);
         

     }

     public void removeSession(Session session) {
          sessions.remove(session);
         
     }

     public void sendToAllConnectedSessions(JsonObject message) {
          for (Session session : sessions) {
               sendToSession(session, message);
          }
     }

     public void sendToSession(Session session, JsonObject message) {
          try {
               session.getBasicRemote().sendText(message.toString());
          } catch (Exception ex) {
               sessions.remove(session);
               Logger.getLogger(BaseSocketServer.class.getName()).log(Level.SEVERE, null, ex);
          }
     }
}
