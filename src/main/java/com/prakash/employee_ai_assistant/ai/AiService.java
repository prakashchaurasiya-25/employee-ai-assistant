package com.prakash.employee_ai_assistant.ai;

import com.prakash.employee_ai_assistant.entity.Employee;
import com.prakash.employee_ai_assistant.repository.EmployeeRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {

    private final ChatClient chatClient;
    private final EmployeeRepository employeeRepository;

    public AiService(
            ChatClient.Builder chatClientBuilder,
            EmployeeRepository employeeRepository) {

        this.chatClient = chatClientBuilder.build();
        this.employeeRepository = employeeRepository;
    }

    public String chat(String message) {

        List<Employee> employees = employeeRepository.findAll();

        String employeeData = employees.stream()
        .map(employee -> String.format(
                "ID: %s, Name: %s, Email: %s, Department: %s, Salary: %s",
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        ))
        .collect(java.util.stream.Collectors.joining("\n"));

        String prompt = """
                You are an Employee Management AI Assistant.

                Here is the current employee data from the database:

                %s

                User question:
                %s

                Answer the user's question using the employee data.
                If the data does not contain enough information, clearly say so.
                Do not invent employee information.
                """.formatted(employeeData, message);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}