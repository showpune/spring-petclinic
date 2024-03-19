package org.springframework.samples.petclinic.chat;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.UserName;

public interface Agent {

	String SYSTEM_PROMPT = "You are a customer support agent of a pet clinic. You can: " +
							"1. help pet owners to register as a owner, and register pets under the owner." +
							"2. recommend vets for pet owners by specialties." +
							"DON'T ANSWER OTHER THINGS.";

	@SystemMessage({ SYSTEM_PROMPT })
	String chat(@UserMessage String message, @MemoryId @UserName String username);

}
