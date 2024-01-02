package com.ifs.service.impl;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifs.entity.DecisionCount;
import com.ifs.entity.InterviewFeedback;
import com.ifs.repository.InterviewFeedbackRepository;
import com.ifs.service.InterviewFeedbackService;

@Service
public class InterviewFeedbackServiceImpl implements InterviewFeedbackService{

	@Autowired
    private InterviewFeedbackRepository feedbackRepository;

	@Override
    public InterviewFeedback saveFeedback(InterviewFeedback feedback) {
        return feedbackRepository.save(feedback);
    }
	
	@Override
    public List<InterviewFeedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
	
	@Override
    public Optional<InterviewFeedback> getFeedbackById(String id) {
        return feedbackRepository.findById(id);
    }
	
	@Override
    public void deleteFeedback(String id) {
		feedbackRepository.findById(id).orElseThrow(()->new RuntimeException("Feedback with ID " + id + " not found."));
        feedbackRepository.deleteById(id);
    }
	
	@Override
    public InterviewFeedback updateFeedback(String id, InterviewFeedback updatedFeedback) {
        if (feedbackRepository.existsById(id)) {
            updatedFeedback.setId(id);
            return feedbackRepository.save(updatedFeedback);
        } else {
            throw new RuntimeException("Feedback with ID " + id + " not found.");
        }
    }
	@Override
	public void readAndStoreFeedbackFromJsonFile(MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<InterviewFeedback> feedbackList = Arrays.asList(
                objectMapper.readValue(file.getBytes(), InterviewFeedback[].class)
            );
            
            feedbackRepository.saveAll(feedbackList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public List<InterviewFeedback> getFeedbackByCandidateName(String fullName) {
		return feedbackRepository.findByCandidateInfoFullName(fullName);
	}
	
	@Override
	public List<InterviewFeedback> saveFromExcel(MultipartFile file) {
        List<InterviewFeedback> list = ExcelParser.parseExcel(file);
        return feedbackRepository.saveAll(list);
    }
	
	@Override
	public List<DecisionCount> countHiringDecisions() {
        return feedbackRepository.countByHiringDecision();
    }
	
	
}
