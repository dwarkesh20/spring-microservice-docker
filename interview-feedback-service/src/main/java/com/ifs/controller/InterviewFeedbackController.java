package com.ifs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ifs.entity.DecisionCount;
import com.ifs.entity.InterviewFeedback;
import com.ifs.service.InterviewFeedbackService;

@RestController
@RequestMapping("/api/feedback")
public class InterviewFeedbackController {

	@Autowired
	private InterviewFeedbackService feedbackService;

	@PostMapping
	public InterviewFeedback createFeedback(@RequestBody InterviewFeedback feedback) {
		return feedbackService.saveFeedback(feedback);
	}

	@PostMapping("/upload")
	public String createAllFeedback(@RequestParam MultipartFile file) {
		feedbackService.readAndStoreFeedbackFromJsonFile(file);
		return "added";

	}

	@PostMapping("/excel")
	public List<InterviewFeedback> createAllFeedbackExcel(@RequestParam MultipartFile file) {
		return feedbackService.saveFromExcel(file);
	}

	@GetMapping("/getall")
	public List<InterviewFeedback> getAllFeedback() {
		return feedbackService.getAllFeedback();
	}

	@GetMapping("/id/{id}")
	public Optional<InterviewFeedback> getFeedbackById(@PathVariable String id) {
		return feedbackService.getFeedbackById(id);
	}

	@GetMapping("/hiringcounts")
	public List<DecisionCount> getHiringDecisions() {
		var countResponse = feedbackService.countHiringDecisions();
		countResponse.sort((o1, o2) -> o1.get_id().compareTo(o2.get_id()));
		return countResponse;
	}

	@GetMapping("/candidate/{name}")
	public List<InterviewFeedback> getFeedbackByCandidateName(@PathVariable String name) {
		return feedbackService.getFeedbackByCandidateName(name);
	}

	@PutMapping("/update/{id}")
	public InterviewFeedback updateFeedback(@PathVariable String id, @RequestBody InterviewFeedback updatedFeedback) {
		return feedbackService.updateFeedback(id, updatedFeedback);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteFeedback(@PathVariable String id) {
		feedbackService.deleteFeedback(id);
	}
}
