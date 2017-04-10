<%-- 
    Document   : wsclient
    Created on : Apr 10, 2017, 4:38:09 PM
    Author     : Dad
--%>

<%@page import="javax.xml.ws.BindingProvider"%>
<%@page import="org.tempuri.IService1"%>
<%@page import="org.tempuri.Service1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <%
            
            if (request.getMethod().equalsIgnoreCase("post")) {
                Service1 client = new Service1();
                IService1 port = client.getBasicHttpBindingIService1();
                ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, request.getParameter("url"));
                out.write("response: " + port.workingGetData(5));
            }
            
            %>
            <form method="POST" action="wsclient.jsp">
            Url:
            <input type="text" name="url" value="http://localhost:8080/websockettest/HelloWorldImplSunRi1">
            
            <button type="submit">Submit</button>
        </form>
    </body>
</html>
