package com.liaoliao.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomKit {
	
	
	/**
	 * 两个整数之间的随机整数
	 * @param begin
	 * @param end
	 * @return
	 */
	public int getRandomBetween(int begin,int end){
		   double random = Math.random();
		   return (int)(random*(end - begin)+begin);
		}

	/**
	 * 6位手机验证码
	 * @return
	 */
	public static String messageCode(){
		String code=""+(int)Math.floor(Math.random()*10)+(int)Math.floor(Math.random()*10)+(int)Math.floor(Math.random()*10)
				+(int)Math.floor(Math.random()*10)+(int)Math.floor(Math.random()*10)+(int)Math.floor(Math.random()*10);
		return code;
	}
	
	
	/**
	 * 32位视频文章keyId
	 * @return
	 */
	public static String keyId(){
		 String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
		    Random random = new Random();     
		    StringBuffer sb = new StringBuffer();     
		    for (int i = 0; i < 32; i++) {     
		        int number = random.nextInt(base.length());     
		        sb.append(base.charAt(number));     
		    }     
		    return sb.toString();
	}
	
	
	/**
	 * 随机用户名
	 * @return
	 */
	public static String randomName(){
		String name="料料_";
		 String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
		    Random random = new Random();     
		    StringBuffer sb = new StringBuffer();     
		    for (int i = 0; i < 4; i++) {     
		        int number = random.nextInt(base.length());     
		        sb.append(base.charAt(number));     
		    }     
		    return name+sb.toString();
	}
	
	public static Integer invitingRandom(){
		Random random=new Random();
		Integer result = random.nextInt(20)+30;
		return result;
	}
	
	/**
	 * 随机红包
	 * @param args
	 */
	
	public static List<Integer> randomRedPackage(int money,int number){
		int maxMoney = (int)Math.ceil(money/(number/2));//最大金额
		List<Integer> list = new ArrayList<>();
		int total=0;
		int leftMoney=money;
		int countMoney=0;
		for(int i=0;i<number;i++){
			int mon=0;
			if(i<number-1){
				total=number-(i+1);//总人数除去剩下的人数，还有自己；
				int randMoney=leftMoney-total;
				if(randMoney>maxMoney){
					randMoney=maxMoney;
				}
				mon=(int)Math.ceil(Math.random()*randMoney);
				list.add(mon);
				leftMoney-=mon;
				countMoney+=mon;
			}else{
				list.add(money-countMoney);
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		
		List<Integer> aa=randomRedPackage(1001,50);
		int c=0;
		for(int i=0;i<aa.size();i++){
			System.out.println(aa.get(i));
			c+=(int)aa.get(i);
		}
		System.out.println("**************");
		System.out.println(c);
		System.out.println("**************");
		int maxMoney=0;
		for(int i=0;i<aa.size();i++){
			maxMoney=maxMoney>aa.get(i)?maxMoney:aa.get(i);
		}
		System.out.println(maxMoney);
	}
	
	
}
