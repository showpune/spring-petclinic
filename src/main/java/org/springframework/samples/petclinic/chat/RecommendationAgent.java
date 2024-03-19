package org.springframework.samples.petclinic.chat;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.UserName;
import dev.langchain4j.service.V;

public interface RecommendationAgent {

	String SYSTEM_PROMPT = "You help pet clinic customer to register as a owner and to register pets under the owner. You should follow below rules: \n" +
							"1. Vet Recommendation\n"
							+ "1.1 recommend vets by the pet's illness and needs\n"
							+ "1.2 Customer can query all vets and they specialty.\n"
							+ "1.3 provide vets' phone number if customer need to make an appointment with the vet";

	@SystemMessage({ SYSTEM_PROMPT })
	String chat(@UserMessage String message);

}
