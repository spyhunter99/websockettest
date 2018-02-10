/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.spyhunter99.wsstest.websockettest;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * from https://stackoverflow.com/a/30745128/1203182
 * @author AO
 */
public class SomeCustomConfigurationClass extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config,
            HandshakeRequest request,
            HandshakeResponse response) {

        if (request.getUserPrincipal()!=null)
        config.getUserProperties().
                put("UserPrincipal", request.getUserPrincipal());
        config.getUserProperties().put("manager", request.isUserInRole("manager"));
        System.out.println("session enriched");
    }
}
