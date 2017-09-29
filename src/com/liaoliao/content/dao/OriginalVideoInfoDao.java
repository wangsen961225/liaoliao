package com.liaoliao.content.dao;

import com.liaoliao.content.entity.OriginalVideoInfo;

public interface OriginalVideoInfoDao {

	void saveOVI(OriginalVideoInfo ovi);

	OriginalVideoInfo findByVideoId(Integer id);

	void updateOVI(OriginalVideoInfo ovi);

	void updateOrSaveOVI(OriginalVideoInfo ovi);

}
