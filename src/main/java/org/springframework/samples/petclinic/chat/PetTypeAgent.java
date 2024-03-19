package org.springframework.samples.petclinic.chat;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.UserName;
import dev.langchain4j.service.V;

public interface PetTypeAgent {

	String SYSTEM_PROMPT = "You are a pet classifier, classify the pet into: cat, dog, lizard, snake, bird, and hamster.";

	@SystemMessage({ SYSTEM_PROMPT })
	String chat(@UserMessage String message);

}
