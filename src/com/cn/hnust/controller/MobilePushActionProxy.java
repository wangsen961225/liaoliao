package com.cn.hnust.controller;

public class MobilePushActionProxy implements com.cn.hnust.controller.MobilePushAction {
  private String _endpoint = null;
  private com.cn.hnust.controller.MobilePushAction mobilePushAction = null;
  
  public MobilePushActionProxy() {
    _initMobilePushActionProxy();
  }
  
  public MobilePushActionProxy(String endpoint) {
    _endpoint = endpoint;
    _initMobilePushActionProxy();
  }
  
  private void _initMobilePushActionProxy() {
    try {
      mobilePushAction = (new com.cn.hnust.controller.MobilePushActionServiceLocator()).getMobilePushActionPort();
      if (mobilePushAction != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mobilePushAction)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mobilePushAction)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mobilePushAction != null)
      ((javax.xml.rpc.Stub)mobilePushAction)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.cn.hnust.controller.MobilePushAction getMobilePushAction() {
    if (mobilePushAction == null)
      _initMobilePushActionProxy();
    return mobilePushAction;
  }
  
  public void send(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, int arg3) throws java.rmi.RemoteException, com.cn.hnust.controller.Exception{
    if (mobilePushAction == null)
      _initMobilePushActionProxy();
    mobilePushAction.send(arg0, arg1, arg2, arg3);
  }
  
  public void sendMessage(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, int arg3) throws java.rmi.RemoteException, com.cn.hnust.controller.Exception{
    if (mobilePushAction == null)
      _initMobilePushActionProxy();
    mobilePushAction.sendMessage(arg0, arg1, arg2, arg3);
  }
  
  
}