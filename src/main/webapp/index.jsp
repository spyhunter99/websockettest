
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WSS Socket Test</title>

        <script type="text/javascript" src="js/jquery-2.2.4.js"></script>

        <script type="text/javascript"  >
             var data_socket;
            $(document).ready(function () {

                data_socket = new WebSocket("ws://localhost:8080/websockettest/WebSocketEndpoint");
                data_socket.onmessage = onDataMessage;
                data_socket.onopen = onOpen;
                data_socket.onclose = onClose;



            });
            
            
            function onOpen() {
                
                window.console && window.console.log("web socket opened");
                $("#wsSocketTest").text("connected...");
                createFakeData();
            }
            function onClose() {
                window.console && window.console.log("web socket closed");
                $("#wsSocketTest").text("disconnected...");
            }
            /**
             * inbound message FROM the web data_socket(server)
             * @type type
             */
            function onDataMessage(event) {

                var message = JSON.parse(event.data);
                window.console && window.console.log("onMessage received, action is " + message.action);
                var m = JSON.stringify(message);
                $("#wsSocketTest").html($("#wsSocketTest").html() + "<br>" + "RX from web socket endpoint" + m);
                
            }

            function createFakeData() {
                $("#wsSocketTest").html($("#wsSocketTest").html() + "<br>" + "TX to web socket endpoint");
                var msg = {
                    action: "createFakeData",
                };
                data_socket.send(JSON.stringify(msg));
            }

        </script>
    </head>
    <body>
        <button onclick="javascript:createFakeData();" title="TX to server">Send Message</button>
        <div id="wsSocketTest">Connecting....</div>
    </body>
</html>
