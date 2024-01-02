package com.ifs.service;
import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.ifs.entity.DecisionCount;
import com.ifs.entity.InterviewFeedback;

public interface InterviewFeedbackService {

    InterviewFeedback saveFeedback(InterviewFeedback feedback);

    List<InterviewFeedback> getAllFeedback();

    Optional<InterviewFeedback> getFeedbackById(String id);

    void deleteFeedback(String id);

    InterviewFeedback updateFeedback(String id, InterviewFeedback updatedFeedback);
    
    List<InterviewFeedback> getFeedbackByCandidateName(String fullName);

	void readAndStoreFeedbackFromJsonFile(MultipartFile file);
	
	List<InterviewFeedback> saveFromExcel(MultipartFile file);
	
	List<DecisionCount> countHiringDecisions();
}
