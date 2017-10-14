package com.liaoliao.content.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.content.dao.VideoDao;
import com.liaoliao.content.entity.Video;
import com.liaoliao.util.StaticKey;

public class VideoDaoImpl extends BaseDaoImpl<Video, Integer>  implements VideoDao {

	@Override
	public List<Video> findAll(Integer pageNo,Map<String,Object> map) {
		String hql="from Video where 1 = 1 ";
		String countHql="select count(v) from Video v where 1 = 1";
		
		if(map !=null && map.get("searchType")!=null&& !("".equals(map.get("searchType")))){
			Integer searchType = (Integer) map.get("searchType");
			hql+= " and status="+searchType;
			hql += "order by id desc";
			countHql+=" and status="+searchType;
		}
		
		PageResults<Video> results=(PageResults<Video>) this.findPageByFetchedHql(hql, countHql, pageNo, StaticKey.VideoPageNum);
		return results.getResults();
	}
 
	/**
	 * 接口下拉刷新获取随机新数据
	 */
	@Override
	public Video findByRand() {
//			String sql = "SELECT * FROM `ll_video` AS t1 "
//					+ " JOIN (SELECT ROUND(RAND() * (SELECT MAX(id) FROM `ll_article`)) AS idd) AS t2 "
//					+ " WHERE t1.id >= t2.idd "
//					+ " ORDER BY t1.id ASC LIMIT 1;";
			String sql = "SELECT * FROM `ll_video` AS t1 "
					+ " JOIN (SELECT ROUND(RAND() * "
					+ " ((SELECT MAX(id) FROM `ll_video` where `STATUS`= 1)-(SELECT MIN(id) FROM `ll_video` where `STATUS`=1))+(SELECT MIN(id) FROM `ll_video` where `STATUS`=1)) AS idd) "
					+ " AS t2 "
					+ " WHERE t1.id >= t2.idd and t1.`STATUS`=1 "
					+ " ORDER BY t1.id LIMIT 1;";
			return this.getBySQL(sql);
	}
	
	@Override
	public void saveVideo(Video v) {
		this.save(v);
	}

	@Override
	public Video findByKeyAndType(String keyId, Integer type) {
		String hql="from Video where sourceId = ?0 and keyId = ?1";
		return this.getByHQL(hql, type, keyId);
	}

	@Override
	public Integer findCount(Map<String,Object> map) {
		String countHql="select count(v) from Video v where 1=1";
		
		if(map.get("searchType")!=null&&!("".equals(map.get("searchType")))){
			Integer searchType = (Integer) map.get("searchType");
			countHql+="and status ="+searchType;
		}
		
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public Video videoDao(Integer id) {
		return this.get(id);
	}

	@Override
	public void deleteVideo(Video video) {
		this.delete(video);
	}

	@Override
	public void updateVideo(Video video) {
		this.update(video);
	}

	@Override
	public Integer findUserSelfCount(Map<String,Object> map) {
		String countHql="select count(v) from Video v where  type = 1";
		
		if(map.get("searchType")!=null&&!("".equals(map.get("searchType")))){
			Integer searchType = (Integer) map.get("searchType");
			countHql+="and status ="+searchType;
		}
		
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public List<Video> findUserSelfList(Integer pageNo,Map<String,Object> map) {
		String hql="from Video where  type = 1";
		String countHql="select count(v) from Video v where  type = 1";
		
		if(map.get("searchType")!=null && !("".equals(map.get("searchType")))){
			Integer searchType = (Integer) map.get("searchType");
			hql+="and status="+searchType;
			hql+="order by id desc";
			countHql+="and status="+searchType;
		}
		
		PageResults<Video> results=(PageResults<Video>) this.findPageByFetchedHql(hql, countHql, pageNo, 10);
		return results.getResults();
	}

	@Override
	public List<Video> findBySourceId(Integer userId) {
		String hql="from Video where sourceId = ?0 order by id";
		return this.getListByHQL(hql, userId);
	}

	@Override
	public void delOriginalVideo(Integer id) {
		
		String originalVideoInfoHql = "delete from OriginalVideoInfo where video.id =?";
		String videoHql = "delete from Video where id =?";
		
		Query originalVideoInfoQuery = this.getSession().createQuery(originalVideoInfoHql);
		Query videoQuery = this.getSession().createQuery(videoHql);
		
		if(id != null && !("".equals(id))){
			originalVideoInfoQuery.setInteger(0,id);
			originalVideoInfoQuery.executeUpdate();
			
			videoQuery.setInteger(0,id);
			videoQuery.executeUpdate();
		}
	}

	@Override
	public List<Video> findPassedBySourceId(Integer userId) {
		String hql="from Video where sourceId = ?0 and status=1 and addTime between ?1 and ?2 order by id";
		Date now = new Date();
		Date old = new Date(now.getTime() - 5*60*1000);
		
        Timestamp nowT = new Timestamp(now.getTime());
        Timestamp oldT = new Timestamp(old.getTime());
		
		return this.getListByHQL(hql, userId,oldT,nowT);
	}
}
