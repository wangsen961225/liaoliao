package com.liaoliao.content.service;

import com.liaoliao.content.entity.OriginalArticleInfo;

public interface OriginalArticleInfoService {

	void saveOAI(OriginalArticleInfo oai);

	OriginalArticleInfo findByArticleId(Integer id);

	void updateOAI(OriginalArticleInfo oai);

}
