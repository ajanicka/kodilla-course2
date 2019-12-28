package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.ConfigProperties;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    ConfigProperties configProp;

    @Autowired
    private TaskRepository taskRepository;

    public String build(String message, Mail.Type mailType) {
        switch (mailType) {
            case TRELLO_CARD_EMAIL:
                return buildTrelloCardEmail(message);
            case DAILY_INFORMATION_EMAIL:
                return buildDailyInformationEmail(message);
            default:
                return message;
        }
    }

    private String buildDailyInformationEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message","Thank you for using Tasks app!");
        context.setVariable("company_name", configProp.getConfigValue("info.company.name"));
        context.setVariable("company_phone", configProp.getConfigValue("info.company.phone"));
        context.setVariable("company_mail", configProp.getConfigValue("info.company.email"));
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("tasks", taskRepository.findAll());
        return templateEngine.process("mail/scheduled-email", context);
    }

    public String buildTrelloCardEmail(String message) {
        ArrayList<String> functionality = new ArrayList<>();
        functionality.add("You can manage your task");
        functionality.add("Provides connection with Trello account");
        functionality.add("Application allows sending task to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview_message", "Trello card created");
        context.setVariable("goodbye_info", "Thank you for choosing us!");
        context.setVariable("company_name", configProp.getConfigValue("info.company.name"));
        context.setVariable("company_phone", configProp.getConfigValue("info.company.phone"));
        context.setVariable("company_mail", configProp.getConfigValue("info.company.email"));
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return  templateEngine.process("mail/created-trello-card-mail", context);
    }

}