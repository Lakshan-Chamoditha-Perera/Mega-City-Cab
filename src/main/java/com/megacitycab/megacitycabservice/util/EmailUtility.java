package com.megacitycab.megacitycabservice.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class EmailUtility {
    private static final Logger logger = Logger.getLogger(EmailUtility.class.getName());
    private static EmailUtility instance;
    private final ExecutorService executorService;

    private EmailUtility() {
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public static EmailUtility getInstance() {
        if (instance == null) {
            synchronized (EmailUtility.class) {
                if (instance == null) {
                    instance = new EmailUtility();
                }
            }
        }
        return instance;
    }

    private String loadTemplateFromResource(String templateFileName) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("email_templates/" + templateFileName);
            if (inputStream == null) {
                throw new IllegalArgumentException("Template file not found: " + templateFileName);
            }

            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            return content.toString();
        } catch (Exception e) {
            logger.severe("Error loading template: " + e.getMessage());
            return "";
        }
    }

    public void sendEmailAsync(String to, String subject, String templateFileName, String... placeholders) {
        executorService.submit(new EmailSenderTask(to, subject, templateFileName, placeholders));
        logger.info("Email sending task submitted for: " + to);
    }

    public void sendEmail(String to, String subject, String templateFileName, String... placeholders) {
        String htmlBody = loadTemplateFromResource(templateFileName);

        if (placeholders.length > 0) {
            for (int i = 0; i < placeholders.length; i += 2) {
                String key = placeholders[i];
                String value = placeholders[i + 1];
                htmlBody = htmlBody.replace("{{" + key + "}}", value);
            }
        }

        // Simulate actual email sending
        System.out.println("Sending email...");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + htmlBody);
        System.out.println("Email sent successfully!");
    }

    public void shutdown() {
        executorService.shutdown();
        logger.info("EmailUtility executor service shutdown initiated");
    }

    private class EmailSenderTask implements Runnable {
        private final String to;
        private final String subject;
        private final String templateFileName;
        private final String[] placeholders;

        public EmailSenderTask(String to, String subject, String templateFileName, String... placeholders) {
            this.to = to;
            this.subject = subject;
            this.templateFileName = templateFileName;
            this.placeholders = placeholders;
        }

        @Override
        public void run() {
            try {
                String htmlBody = loadTemplateFromResource(templateFileName);

                if (placeholders.length > 0) {
                    for (int i = 0; i < placeholders.length; i += 2) {
                        String key = placeholders[i];
                        String value = placeholders[i + 1];
                        htmlBody = htmlBody.replace("{{" + key + "}}", value);
                    }
                }

                // Simulate actual email sending
                System.out.println("Sending email asynchronously...");
                System.out.println("To: " + to);
                System.out.println("Subject: " + subject);
                System.out.println("Body:\n" + htmlBody);
                System.out.println("Email sent successfully!");
                logger.info("Email sent asynchronously to: " + to);
            } catch (Exception e) {
                logger.severe("Failed to send email asynchronously to " + to + ": " + e.getMessage());
            }
        }
    }
}