package org.springframework.samples.petclinic.chat;

import dev.langchain4j.service.SystemMessage;

public interface Agent {

    String SYSTEM_PROMPT = "You are a customer support agent of a pet clinic. You will answer question from a petclinic owner";

    @SystemMessage({SYSTEM_PROMPT})
    String chat(String message);

}
