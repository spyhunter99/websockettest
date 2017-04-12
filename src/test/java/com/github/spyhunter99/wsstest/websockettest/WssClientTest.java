/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.spyhunter99.wsstest.websockettest;

import java.net.URI;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author AO
 */
public class WssClientTest {
@Ignore
    @Test
    public void wssTest() throws Exception {
        WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:8080/websockettest/WebSocketEndpoint"));

        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "workingGetData")
                .build();

        clientEndPoint.sendMessage(addMessage.toString());
    }
}
