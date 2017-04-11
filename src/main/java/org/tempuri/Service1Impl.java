/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tempuri;

import com.github.spyhunter99.wsstest.websockettest.SingletonInstance;
import com.github.spyhunter99.wsstest.websockettest.WebSocketEndpoint;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.tempuri.IService1;
import org.tempuri.OneWayMethodMethod;

/**
 *
 * @author AO
 */
@WebService(name = "IService1", targetNamespace = "http://tempuri.org/")
public class Service1Impl implements IService1 {

    
    /**
     * 
     * @param value
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "WorkingGetData", action = "http://tempuri.org/IService1/WorkingGetData")
    @WebResult(name = "WorkingGetDataResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "WorkingGetData", targetNamespace = "http://tempuri.org/", className = "org.tempuri.WorkingGetData")
    @ResponseWrapper(localName = "WorkingGetDataResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.WorkingGetDataResponse")
    @Override
    public String workingGetData(
        @WebParam(name = "value", targetNamespace = "http://tempuri.org/")
         Integer value) {
          
        WebSocketEndpoint instance = SingletonInstance.getInstance();
       
        
            JsonProvider provider = JsonProvider.provider();
            JsonObject addMessage = provider.createObjectBuilder()
                    .add("action", "workingGetData")
                    
                    .build();
            
            instance.sendToAllConnectedSessions(addMessage);
       
          return "workingGetData";
    }

    /**
     *
     * @param value
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "CallDependantService", action = "http://tempuri.org/IService1/CallDependantService")
    @WebResult(name = "CallDependantServiceResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "CallDependantService", targetNamespace = "http://tempuri.org/", className = "org.tempuri.CallDependantService")
    @ResponseWrapper(localName = "CallDependantServiceResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.CallDependantServiceResponse")
    public String callDependantService(
            @WebParam(name = "value", targetNamespace = "http://tempuri.org/") Integer value) {
        return "";
    }

    /**
     *
     * @param value
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "CallWCFDependantService", action = "http://tempuri.org/IService1/CallWCFDependantService")
    @WebResult(name = "CallWCFDependantServiceResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "CallWCFDependantService", targetNamespace = "http://tempuri.org/", className = "org.tempuri.CallWCFDependantService")
    @ResponseWrapper(localName = "CallWCFDependantServiceResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.CallWCFDependantServiceResponse")
    public String callWCFDependantService(
            @WebParam(name = "value", targetNamespace = "http://tempuri.org/") Integer value) {
        return "hi";
    }

    /**
     *
     * @param value
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "FailingGetData", action = "http://tempuri.org/IService1/FailingGetData")
    @WebResult(name = "FailingGetDataResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "FailingGetData", targetNamespace = "http://tempuri.org/", className = "org.tempuri.FailingGetData")
    @ResponseWrapper(localName = "FailingGetDataResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.FailingGetDataResponse")
    public String failingGetData(
            @WebParam(name = "value", targetNamespace = "http://tempuri.org/") Integer value)

    {
        throw new IllegalArgumentException();
    }

    /**
     *
     * @param value
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "LongRunningGetData", action = "http://tempuri.org/IService1/LongRunningGetData")
    @WebResult(name = "LongRunningGetDataResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "LongRunningGetData", targetNamespace = "http://tempuri.org/", className = "org.tempuri.LongRunningGetData")
    @ResponseWrapper(localName = "LongRunningGetDataResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.LongRunningGetDataResponse")
    public String longRunningGetData(
            @WebParam(name = "value", targetNamespace = "http://tempuri.org/") Integer value){
        return "h";
    }

    /**
     *
     * @param value
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "RandomWorkingMethod", action = "http://tempuri.org/IService1/RandomWorkingMethod")
    @WebResult(name = "RandomWorkingMethodResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "RandomWorkingMethod", targetNamespace = "http://tempuri.org/", className = "org.tempuri.RandomWorkingMethod")
    @ResponseWrapper(localName = "RandomWorkingMethodResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.RandomWorkingMethodResponse")
    public String randomWorkingMethod(
            @WebParam(name = "value", targetNamespace = "http://tempuri.org/") Integer value){
        if (0.5d > Math.random())
            return "success";
        return "failure";
    }

    /**
     *
     * @param parameters
     */
    @WebMethod(operationName = "OneWayMethod", action = "http://tempuri.org/IService1/OneWayMethod")
    @Oneway
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public void oneWayMethod(
            @WebParam(name = "OneWayMethodMethod", targetNamespace = "http://tempuri.org/", partName = "parameters") OneWayMethodMethod parameters){
        
    }

}
