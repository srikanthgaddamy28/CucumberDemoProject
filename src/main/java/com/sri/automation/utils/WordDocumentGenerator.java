package com.sri.automation.utils;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WordDocumentGenerator {
    private static final Logger logger = LogManager.getLogger(WordDocumentGenerator.class);
    private static final String REPORTS_DIR = "test-output/word-reports/";
    private XWPFDocument document;
    private List<TestStepResult> testSteps;
    private String testName;
    
    static {
        createReportsDirectory();
    }
    
    private static void createReportsDirectory() {
        java.io.File directory = new java.io.File(REPORTS_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    public WordDocumentGenerator(String testName) {
        this.testName = testName;
        this.document = new XWPFDocument();
        this.testSteps = new ArrayList<>();
        initializeDocument();
    }
    
    private void initializeDocument() {
        // Create title
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Flipkart Automation Test Report");
        titleRun.setBold(true);
        titleRun.setFontSize(18);
        titleRun.setColor("2E74B5");
        
        // Create test info
        XWPFParagraph testInfo = document.createParagraph();
        XWPFRun testInfoRun = testInfo.createRun();
        testInfoRun.setText("Test Name: " + testName);
        testInfoRun.setBold(true);
        testInfoRun.setFontSize(12);
        
        XWPFParagraph dateInfo = document.createParagraph();
        XWPFRun dateInfoRun = dateInfo.createRun();
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        dateInfoRun.setText("Execution Date: " + currentDate);
        dateInfoRun.setFontSize(10);
        
        // Add separator
        document.createParagraph().createRun().addBreak();
    }
    
    public void addTestStep(String stepDescription, String status, String screenshotPath, String additionalInfo) {
        TestStepResult step = new TestStepResult(stepDescription, status, screenshotPath, additionalInfo);
        testSteps.add(step);
        
        // Add step to document
        XWPFParagraph stepPara = document.createParagraph();
        XWPFRun stepRun = stepPara.createRun();
        stepRun.setText("Step " + testSteps.size() + ": " + stepDescription);
        stepRun.setBold(true);
        stepRun.setFontSize(11);
        
        // Add status
        XWPFParagraph statusPara = document.createParagraph();
        XWPFRun statusRun = statusPara.createRun();
        statusRun.setText("Status: " + status);
        statusRun.setBold(true);
        
        if ("PASS".equalsIgnoreCase(status)) {
            statusRun.setColor("00B050");
        } else if ("FAIL".equalsIgnoreCase(status)) {
            statusRun.setColor("FF0000");
        } else {
            statusRun.setColor("FFC000");
        }
        
        // Add additional info if provided
        if (additionalInfo != null && !additionalInfo.trim().isEmpty()) {
            XWPFParagraph infoPara = document.createParagraph();
            XWPFRun infoRun = infoPara.createRun();
            infoRun.setText("Details: " + additionalInfo);
            infoRun.setFontSize(10);
        }
        
        // Add screenshot if available
        if (screenshotPath != null && !screenshotPath.trim().isEmpty()) {
            addScreenshotToDocument(screenshotPath);
        }
        
        // Add separator
        document.createParagraph().createRun().addBreak();
    }
    
    private void addScreenshotToDocument(String screenshotPath) {
        try {
            java.io.File imageFile = new java.io.File(screenshotPath);
            if (imageFile.exists()) {
                XWPFParagraph imagePara = document.createParagraph();
                imagePara.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun imageRun = imagePara.createRun();
                
                FileInputStream fis = new FileInputStream(imageFile);
                imageRun.addPicture(fis, XWPFDocument.PICTURE_TYPE_PNG, imageFile.getName(), 
                                  Units.toEMU(400), Units.toEMU(300));
                fis.close();
                
                logger.info("Screenshot added to Word document: {}", screenshotPath);
            }
        } catch (Exception e) {
            logger.error("Failed to add screenshot to Word document: {}", screenshotPath, e);
        }
    }
    
    public void generateSummary() {
        long passCount = testSteps.stream().filter(step -> "PASS".equalsIgnoreCase(step.getStatus())).count();
        long failCount = testSteps.stream().filter(step -> "FAIL".equalsIgnoreCase(step.getStatus())).count();
        long totalSteps = testSteps.size();
        
        // Add summary section
        XWPFParagraph summaryTitle = document.createParagraph();
        XWPFRun summaryTitleRun = summaryTitle.createRun();
        summaryTitleRun.setText("Test Execution Summary");
        summaryTitleRun.setBold(true);
        summaryTitleRun.setFontSize(14);
        summaryTitleRun.setColor("2E74B5");
        
        // Create summary table
        XWPFTable summaryTable = document.createTable(4, 2);
        summaryTable.getRow(0).getCell(0).setText("Total Steps");
        summaryTable.getRow(0).getCell(1).setText(String.valueOf(totalSteps));
        
        summaryTable.getRow(1).getCell(0).setText("Passed Steps");
        summaryTable.getRow(1).getCell(1).setText(String.valueOf(passCount));
        
        summaryTable.getRow(2).getCell(0).setText("Failed Steps");
        summaryTable.getRow(2).getCell(1).setText(String.valueOf(failCount));
        
        String overallStatus = failCount > 0 ? "FAIL" : "PASS";
        summaryTable.getRow(3).getCell(0).setText("Overall Status");
        summaryTable.getRow(3).getCell(1).setText(overallStatus);
    }
    
    public String saveDocument() {
        try {
            generateSummary();
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".docx";
            String filePath = REPORTS_DIR + fileName;
            
            FileOutputStream out = new FileOutputStream(filePath);
            document.write(out);
            out.close();
            document.close();
            
            logger.info("Word document generated successfully: {}", filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("Failed to save Word document for test: {}", testName, e);
            return null;
        }
    }
    
    private static class TestStepResult {
        private String stepDescription;
        private String status;
        private String screenshotPath;
        private String additionalInfo;
        
        public TestStepResult(String stepDescription, String status, String screenshotPath, String additionalInfo) {
            this.stepDescription = stepDescription;
            this.status = status;
            this.screenshotPath = screenshotPath;
            this.additionalInfo = additionalInfo;
        }
        
        public String getStatus() {
            return status;
        }
    }
}
