package com.liaoliao.content.dao;

import com.liaoliao.content.entity.OriginalArticleInfo;

public interface OriginalArticleInfoDao {

	void saveOAI(OriginalArticleInfo oai);

	OriginalArticleInfo findByArticleId(Integer id);

	void updateOAI(OriginalArticleInfo oai);

}
