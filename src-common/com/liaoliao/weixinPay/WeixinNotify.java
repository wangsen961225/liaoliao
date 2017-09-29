package com.liaoliao.weixinPay;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liaoliao.weixinPay.Utils.HttpXmlUtils;
import com.liaoliao.weixinPay.Utils.JdomParseXmlUtils;
import com.liaoliao.weixinPay.Utils.WXSignUtils;
import com.liaoliao.weixinPay.entity.WXPayResult;

public class WeixinNotify {
	/** 
     * 微信支付回调 
     * @param request 
     * @param resposne 
     */  
    @RequestMapping(value="/notifyUrlWeixin")  
    public void notifyWeixinPayment(HttpServletRequest request,HttpServletResponse response){
        try{  
            BufferedReader reader = request.getReader();  
            String line = "";  
            StringBuffer inputString = new StringBuffer();  
            try{  
                PrintWriter writer = response.getWriter();  
                while ((line = reader.readLine()) != null) {  
                    inputString.append(line);  
                }  
                if(reader!=null){  
                    reader.close();  
                }  
                if(!StringUtils.isEmpty(inputString.toString())){  
                    WXPayResult wxPayResult = JdomParseXmlUtils.getWXPayResult(inputString.toString());  
                    if("SUCCESS".equalsIgnoreCase(wxPayResult.getReturn_code())){
                        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
                        parameters.put("appid", wxPayResult.getAppid());  
                        parameters.put("attach", wxPayResult.getAttach()); 
                        parameters.put("bank_type", wxPayResult.getBank_type());
                        parameters.put("cash_fee", wxPayResult.getCash_fee()); 
                        parameters.put("fee_type", wxPayResult.getFee_type());  
                        parameters.put("is_subscribe", wxPayResult.getIs_subscribe());
                        parameters.put("mch_id", wxPayResult.getMch_id());  
                        parameters.put("nonce_str", wxPayResult.getNonce_str());  
                        parameters.put("openid", wxPayResult.getOpenid());  
                        parameters.put("out_trade_no", wxPayResult.getOut_trade_no());  
                        parameters.put("result_code", wxPayResult.getResult_code());  
                        parameters.put("return_code", wxPayResult.getReturn_code());  
                        parameters.put("time_end", wxPayResult.getTime_end());  
                        parameters.put("total_fee", wxPayResult.getTotal_fee());  
                        parameters.put("trade_type", wxPayResult.getTrade_type());  
                        parameters.put("transaction_id", wxPayResult.getTransaction_id());  
                        //反校验签名  
                        String sign = WXSignUtils.createSign("UTF-8", parameters);
                        
                        if(sign.equals(wxPayResult.getSign())){
                        	if("SUCCESS".equalsIgnoreCase(wxPayResult.getResult_code())){
//                        		处理你们的业务逻辑
//                        		WeixinPayLog wpl=weixinPayLogService.findByOutTradNo(wxPayResult.getOut_trade_no());
//                        		if(wpl.getStatus()==StaticKey.WeixinPaySuccess){
                        			writer.write(HttpXmlUtils.backWeixin("SUCCESS","OK")); 
//                        		}else{
//		                        	wpl.setStatus(StaticKey.WeixinPaySuccess);
//		                        	wpl.setPayTime(new Date());
//		                        	Users user = wpl.getUser();
//		                        	user.setVipStatus(StaticKey.UserVipStatusTrue);
//		                        	userService.updateUser(user); //更新用户vip状态
//		                        	weixinPayLogService.updateWeixinPayLog(wpl);
		                            writer.write(HttpXmlUtils.backWeixin("SUCCESS","OK"));  
//                        		}
                        	}
                        }else{  
                            writer.write(HttpXmlUtils.backWeixin("FAIL","签名失败"));  
                        }  
                    }else{  
//                        writer.write(HttpXmlUtils.backWeixin("FAIL",wxPayResult.getReturn_msg()));  
                    }  
  
                    if(writer!=null){  
                        writer.close();  
                    }  
                }else{  
                    writer.write(HttpXmlUtils.backWeixin("FAIL","未获取到微信返回的结果"));  
                }  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
    }
}
