package com.liaoliao.weixinPay.Utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.liaoliao.weixinPay.entity.UnifiedorderResult;
import com.liaoliao.weixinPay.entity.WXPayResult;


/**
 * 微信解析xml：带有CDATA格式的
 * @author vio
 * @date 2017年7月15日
 */
public class JdomParseXmlUtils {
	
	/**
	 * 1、统一下单获取微信返回
	 * 解析的时候自动去掉CDMA
	 * @param xml
	 */
	public static UnifiedorderResult getUnifiedorderResult(String xml){
		UnifiedorderResult unifieorderResult = new UnifiedorderResult();
		try { 
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc;
			doc = (Document) sb.build(source);

			Element root = doc.getRootElement();// 指向根节点
			List<Element> list = root.getChildren();

			if(list!=null&&list.size()>0){
				for (Element element : list) {
//					System.out.println("key是："+element.getName()+"，值是："+element.getText());
					if("return_code".equals(element.getName())){
						unifieorderResult.setReturn_code(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						unifieorderResult.setReturn_msg(element.getText());
					}
					
					if("appid".equals(element.getName())){
						unifieorderResult.setAppid(element.getText());
					}
					
					
					if("mch_id".equals(element.getName())){
						unifieorderResult.setMch_id(element.getText());
					}
					
					if("nonce_str".equals(element.getName())){
						unifieorderResult.setNonce_str(element.getText());
					}
					
					if("sign".equals(element.getName())){
						unifieorderResult.setSign(element.getText());
					}
					
					if("result_code".equals(element.getName())){
						unifieorderResult.setResult_code(element.getText());
					}
					
					if("prepay_id".equals(element.getName())){
						unifieorderResult.setPrepay_id(element.getText());
					}
					
					if("trade_type".equals(element.getName())){
						unifieorderResult.setTrade_type(element.getText());
					}
				}
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return unifieorderResult;
	}
	
	
	/**
	 * 2、微信回调后参数解析
	 * 解析的时候自动去掉CDMA
	 * @param xml
	 */
	public static WXPayResult getWXPayResult(String xml){
		WXPayResult wXPayResult = new WXPayResult();
		try { 
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc;
			doc = (Document) sb.build(source);

			Element root = doc.getRootElement();// 指向根节点
			List<Element> list = root.getChildren();

			if(list!=null&&list.size()>0){
				for (Element element : list) {
//					System.out.println("key是："+element.getName()+"，值是："+element.getText());
					if("appid".equals(element.getName())){
						wXPayResult.setAppid(element.getText());
					}
					
					if("attach".equals(element.getName())){
						wXPayResult.setAttach(element.getText());
					}
					
					if("bank_type".equals(element.getName())){
						wXPayResult.setBank_type(element.getText());
					}
					
					if("cash_fee".equals(element.getName())){
						wXPayResult.setCash_fee(Integer.valueOf(element.getText()));
					}
					
					if("fee_type".equals(element.getName())){
						wXPayResult.setFee_type(element.getText());
					}
					
					if("is_subscribe".equals(element.getName())){
						wXPayResult.setIs_subscribe(element.getText());
					}
					
					if("mch_id".equals(element.getName())){
						wXPayResult.setMch_id(element.getText());
					}
					
					if("nonce_str".equals(element.getName())){
						wXPayResult.setNonce_str(element.getText());
					}

					if("openid".equals(element.getName())){
						wXPayResult.setOpenid(element.getText());
					}
					
					if("out_trade_no".equals(element.getName())){
						wXPayResult.setOut_trade_no(element.getText());
					}
					
					if("result_code".equals(element.getName())){
						wXPayResult.setResult_code(element.getText());
					}
					
					if("return_code".equals(element.getName())){
						wXPayResult.setReturn_code(element.getText());
					}

					if("time_end".equals(element.getName())){
						wXPayResult.setTime_end(element.getText());
					}
					
					if("total_fee".equals(element.getName())){
						wXPayResult.setTotal_fee(Integer.valueOf(element.getText()));
					}
					
					if("trade_type".equals(element.getName())){
						wXPayResult.setTrade_type(element.getText());
					}
					if("transaction_id".equals(element.getName())){
						wXPayResult.setTransaction_id(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						wXPayResult.setReturn_msg(element.getText());
					}
					
					if("sign".equals(element.getName())){
						wXPayResult.setSign(element.getText());
					}
					
					
					
				}
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return wXPayResult;
	}

}
