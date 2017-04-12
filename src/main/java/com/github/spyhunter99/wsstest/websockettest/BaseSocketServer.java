/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.spyhunter99.wsstest.websockettest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;

public abstract class BaseSocketServer {

    // a collection containing all the sessions
    private static final Set<Session> sessions
            = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void open(Session session) {

        addSession(session);

    }

    public void addSession(Session session) {
        System.out.println("Session connected to " + session.getId());
        sessions.add(session);

    }

    public void removeSession(Session session) {
        System.out.println("Session disconnected to " + session.getId());
        sessions.remove(session);

    }

    public void sendToAllConnectedSessions(JsonObject message) {
        System.out.println("TX broadcast " + sessions.size());
        synchronized (sessions) {

            for (Session session : sessions) {
                sendToSession(session, message);
            }
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

    @OnClose
    public void close(Session session) {
        removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

}
