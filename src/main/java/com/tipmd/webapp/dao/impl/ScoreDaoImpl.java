package com.tipmd.webapp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tipmd.webapp.dao.iface.IScoreDao;
import com.tipmd.webapp.entity.Score;

@Repository
public class ScoreDaoImpl extends BaseDao implements IScoreDao
{

	@Override
	public void addScore(Score score) {
		sqlSessionTemplate.insert("ScoreMapper.createScore", score);
		
	}

	@Override
	public List<Score> getStudentScores(int studentId) {
		return sqlSessionTemplate.selectList("ScoreMapper.getStudentScores", studentId);
	}

}
