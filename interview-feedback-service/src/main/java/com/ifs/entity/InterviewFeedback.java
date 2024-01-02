package com.ifs.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "interview_feedback")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewFeedback {

    @Id
    private String id;

    private String documentNo;
    private String revisionNo;
    private LocalDate effectiveDate;

    private CandidateInfo candidateInfo;
    private InterviewerInfo interviewerInfo;
    
    private List<TechnicalEvaluation> technicalEvaluation;
    private List<SoftSkillsEvaluation> softSkillsEvaluation;

    private String hiringDecision;
    private LocalDate dateOfInterview;

}
