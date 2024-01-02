package com.ifs.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.ifs.entity.CandidateInfo;
import com.ifs.entity.InterviewFeedback;
import com.ifs.entity.InterviewerInfo;
import com.ifs.entity.SoftSkillsEvaluation;
import com.ifs.entity.TechnicalEvaluation;

public class ExcelParser {

	public static List<InterviewFeedback> parseExcel(MultipartFile file) {
		List<InterviewFeedback> feedbackList = new ArrayList<>();

		try (InputStream inputStream = file.getInputStream()) {
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0);

			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				InterviewFeedback feedback = createFeedbackFromRow(row);
				feedbackList.add(feedback);
			}

			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return feedbackList;
	}

	private static InterviewFeedback createFeedbackFromRow(Row row) {
		InterviewFeedback feedback = new InterviewFeedback();

		feedback.setDocumentNo(getStringCellValue(row.getCell(0)));
		feedback.setRevisionNo(getStringCellValue(row.getCell(1)));
	
		feedback.setEffectiveDate(getDateCellValue(row.getCell(2)));

		// Candidate Info
		CandidateInfo candidateInfo = new CandidateInfo();
		candidateInfo.setFullName(getStringCellValue(row.getCell(3)));
		candidateInfo.setPosition(getStringCellValue(row.getCell(4)));
		candidateInfo.setDivision(getStringCellValue(row.getCell(5)));
		candidateInfo.setTotalExperience(getStringCellValue(row.getCell(6)));
		candidateInfo.setRelevantExperience(getStringCellValue(row.getCell(7)));
		candidateInfo.setPrimarySkills(getStringCellValue(row.getCell(8)));
		candidateInfo.setSecondarySkills(getStringCellValue(row.getCell(9)));
		feedback.setCandidateInfo(candidateInfo);

		InterviewerInfo interviewerInfo = new InterviewerInfo();
		interviewerInfo.setName(getStringCellValue(row.getCell(10)));
		interviewerInfo.setDesignation(getStringCellValue(row.getCell(11)));
		feedback.setInterviewerInfo(interviewerInfo);

		// Technical Evaluations
		List<TechnicalEvaluation> technicalEvaluations = new ArrayList<>();
		String technicalSkillCell = getStringCellValue(row.getCell(12));
		String technicalRatingCell = getStringCellValue(row.getCell(13));
		String technicalCommentsCell = getStringCellValue(row.getCell(14));

		TechnicalEvaluation technicalEvaluation = new TechnicalEvaluation();
		technicalEvaluation.setSkill(technicalSkillCell);
		technicalEvaluation.setRating(technicalRatingCell);
		technicalEvaluation.setComments(technicalCommentsCell);

		technicalEvaluations.add(technicalEvaluation);
		feedback.setTechnicalEvaluation(technicalEvaluations);

		// Soft Skills Evaluations
		List<SoftSkillsEvaluation> softSkillsEvaluations = new ArrayList<>();
		String softSkillCell = getStringCellValue(row.getCell(15));
		String softSkillRatingCell = getStringCellValue(row.getCell(16));

		SoftSkillsEvaluation softSkillsEvaluation = new SoftSkillsEvaluation();
		softSkillsEvaluation.setSkill(softSkillCell);
		softSkillsEvaluation.setRating(softSkillRatingCell);

		softSkillsEvaluations.add(softSkillsEvaluation);
		feedback.setSoftSkillsEvaluation(softSkillsEvaluations);

		// Hiring Decision
		String hiringDecisionCell = getStringCellValue(row.getCell(17));
		feedback.setHiringDecision(hiringDecisionCell);

		// Date of Interview
		LocalDate dateOfInterviewCell = getDateCellValue(row.getCell(18));
		feedback.setDateOfInterview(dateOfInterviewCell);

		return feedback;
	}
	
	private static LocalDate getDateCellValue(Cell cell) {
	    if (cell == null) {
	        return null;
	    }
	    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
	        Date dateValue = cell.getDateCellValue();
	        return dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    } else {
	        return null;
	    }
	}

	private static String getStringCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf((Double) cell.getNumericCellValue());
		} else {
			return "";
		}
	}

}
