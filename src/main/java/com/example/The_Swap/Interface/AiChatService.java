package com.example.The_Swap.Interface;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import java.util.List;


@AiService
public interface AiChatService {

    @SystemMessage("""
            Task: Rearrange the people in the  lists according to the Reference Document rules. Ensure every person is accounted for and no one is stranded
            You are a strictly formatted data transformation engine.
            
                Use the information from the vector database to determine how to move people between groups.
            
                You must return the results ONLY as a valid JSON array.
            
                Do not include any introductory text, explanations, or Markdown code blocks (like ```json).
            
                Schema Requirement: Each object in the array must have:
            
                    "groupNo": (Integer),
                    
                    "clientName": (String),
                    
                    "clientEmail": (String),
            
                    "partners": (List of objects, each with 
                                "studentNo": (long),
                                "surname": (String),
                                "fullName": (String),
                                "attendanceRate": (Integer))""")
    String chat(String message);
}

