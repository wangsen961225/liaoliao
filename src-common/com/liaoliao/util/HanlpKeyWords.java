package com.liaoliao.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

import love.cq.util.StringUtil;

public class HanlpKeyWords {

	public static void main(String[] args) {
		System.out.println("首次编译运行时，HanLP会自动构建词典缓存，请稍候……\n");
		// 第一次运行会有文件找不到的错误但不影响运行，缓存完成后就不会再有了
		System.out.println("标准分词：");
		System.out.println(HanLP.segment("你好，欢迎使用HanLP！"));
		System.out.println("\n");
		java.util.List<Term> termList = NLPTokenizer.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程");
		System.out.println("NLP分词：");
		System.out.println(termList);
		System.out.println("\n");

	}

	/**
	 * 智能推荐部分
	 */
	public static List<String> getSegement(List<String> titleArray) {
		Suggester suggester = new Suggester();
		for (String title : titleArray) {
			suggester.addSentence(title);
		}
		List<String> aList=suggester.suggest("搞笑视频", 1);
		List<String> aList1=suggester.suggest("美女豪车", 1);
		List<String> aList2=suggester.suggest("爆笑段子", 1);
		List<String> aList3=suggester.suggest("黄段子", 1);
		List<String> aList4=suggester.suggest("风景", 1);
		aList.addAll(aList1);
		aList.addAll(aList2);
		aList.addAll(aList3);
		aList.addAll(aList4);
		System.out.println(suggester.suggest("搞笑视频", 1)); // 语义
		System.out.println(suggester.suggest("美女豪车", 1)); // 字符
		System.out.println(suggester.suggest("搞笑段子", 1)); // 拼音
		System.out.println(suggester.suggest("黄段子", 1));
		System.out.println(suggester.suggest("风景", 1));
		return aList;
	}

	/**
	 * 关键字提取
	 */
	public static List<String> getMainIdea(String content) {
		
		List<String> keywordList = HanLP.extractKeyword(content, 30);
		
		return keywordList;
	}

	/**
	 * 自动摘要
	 */
	public static void getZhaiYao() {
		String document = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、"
				+ "动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。\n" + "算法可以宽泛的分为三类，\n"
				+ "一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间" + "来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。\n"
				+ "二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。\n"
				+ "三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算" + "法的产生是由于未能确定的定义终止条件。";
		java.util.List<String> sentenceList = HanLP.extractSummary(document, 3);
		System.out.println(sentenceList);
	}

	/**
	 * 短语提取
	 */
	public static List<String> getDuanYu(String text) {
		
		List<String> phraseList = HanLP.extractPhrase(text, 10);
		
		return phraseList;

	}

}