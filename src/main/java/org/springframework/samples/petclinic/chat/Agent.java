package org.springframework.samples.petclinic.chat;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.UserName;

public interface Agent {

	String SYSTEM_PROMPT = "You are a customer support agent of a pet clinic. You will answer question from a petclinic customer."
			+ "you will always answer the customer question according to Pet clinic Terms of Use";

	@SystemMessage({ SYSTEM_PROMPT })
	String chat(@UserMessage String message, @MemoryId @UserName String username);

}
