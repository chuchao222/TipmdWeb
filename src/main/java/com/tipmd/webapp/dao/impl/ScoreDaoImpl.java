package com.tipmd.webapp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tipmd.webapp.dao.iface.IScoreDao;
import com.tipmd.webapp.entity.Score;

@Repository("iScoreDao")
@Transactional
public class ScoreDaoImpl extends GenericDaoImpl<Score, Integer> implements IScoreDao
{

	@Override
	public List<Score> getStudentScores(int studentId) {
		return sqlSessionTemplate.selectList("ScoreMapper.getStudentScores", studentId);
	}

	//如果子类的mapperid 和 namespace 遵守命名规范，此方法可以直接返回null。
	//有关命名规范请参考 GenericDaoImpl 开头的注释
	@Override
	protected GenericDaoConfiguration buildConfiguration() {
		return null;
	}

}
