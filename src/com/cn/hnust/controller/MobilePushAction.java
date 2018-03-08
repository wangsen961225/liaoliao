/**
 * MobilePushAction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cn.hnust.controller;

public interface MobilePushAction extends java.rmi.Remote {
    public void send(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, int arg3) throws java.rmi.RemoteException, com.cn.hnust.controller.Exception;
    public void sendMessage(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, int arg3) throws java.rmi.RemoteException, com.cn.hnust.controller.Exception;
}
