package MyTest;


public class ccc {
	
	public static int money=1004;//发送的金额
	public static int number=23;//发送的份数
//	public static	int minMoney = 1;//最小金额
	public static	int maxMoney = (int)Math.ceil(money/(number-1));//最大金额
	public int[] spiltRedPackets(){
		int[] array = new int[number];//存放金额的数组
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
				array[i]=mon;
				leftMoney-=mon;
				countMoney+=mon;
			}else{
				array[i]=money-countMoney;
			}
		}
		return array;
	}
	
	public static void main(String[] args) {
		ccc cc=new ccc();
		int[] aa=cc.spiltRedPackets( );
		int c=0;
		for(int i=0;i<aa.length;i++){
			System.out.println(aa[i]);
			c+=aa[i];
		}
		
		System.out.println("**************");
		
		System.out.println(c);
 		
	}
	
	/*public static void main(String[] args) {
		Integer money =20000;//总金额
		Integer number =50;//总份额
		//int a=3;
		int[] aa = new int[number];
		*//**
		 * 1~(money-(个数-1))
		 * int a1=(int)Math.ceil(Math.random()*(money-(个数-1)))
		 * money-=a1;
		 *//*
		int total=0;
		int leftMoney=money;
		int countMoney=0;
		for(int i=0;i<number;i++){
			int mon=0;
			if(i<number-1){
				total=number-(i+1);
				System.out.println(leftMoney+","+total);
				mon=(int)Math.ceil(Math.random()*(leftMoney-total));
				aa[i]=mon;
				leftMoney-=mon;
				countMoney+=mon;
			}else{
				aa[i]=money-countMoney;
			}
		}
		System.out.println("**********************");
		int dddd=0;
		for(int i=0;i<number;i++){
			System.out.println(aa[i]);
			dddd+=aa[i];
		}
		
		System.out.println("**********");
		System.out.println(dddd);
	}
*/
}
