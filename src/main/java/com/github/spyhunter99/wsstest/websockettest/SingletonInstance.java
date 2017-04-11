/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.spyhunter99.wsstest.websockettest;

/**
 *
 * @author AO
 */
public class SingletonInstance {

    private static WebSocketEndpoint instance=null;
    
    public static void setInstance(WebSocketEndpoint  item){
        instance = item;
    }
    public static WebSocketEndpoint getInstance(){
        return instance;
    }
}
