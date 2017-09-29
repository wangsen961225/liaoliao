package com.liaoliao.content.service;

import com.liaoliao.content.entity.OriginalVideoInfo;

public interface OriginalVideoInfoService {

	void saveOVI(OriginalVideoInfo ovi);

	OriginalVideoInfo findByVideoId(Integer id);

	void updateOVI(OriginalVideoInfo ovi);

	void updateOrSaveOVI(OriginalVideoInfo oai);

}
