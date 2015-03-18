package com.tipmd.webapp.dao.iface;

import java.util.List;

import com.tipmd.webapp.entity.Score;

public interface IScoreDao {
	//void addScore(Score score);
	List<Score> getStudentScores(int studentId);
}
