package com.ifs.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ifs.entity.DecisionCount;
import com.ifs.entity.InterviewFeedback;

public interface InterviewFeedbackRepository extends MongoRepository<InterviewFeedback, String> {
	List<InterviewFeedback> findByCandidateInfoFullName(String fullName);
	
	@Aggregation("{$group: {_id: '$hiringDecision', count: {$sum: 1}}}")
    List<DecisionCount> countByHiringDecision();
}
