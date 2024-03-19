package org.springframework.samples.petclinic.chat;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.UserName;
import dev.langchain4j.service.V;

public interface ChatAgent {

	String SYSTEM_PROMPT = "You are a customer support agent of a pet clinic. You should follow the steps to help users:" +
							"1. ask for user's needs, comfort user's emotions." +
							"2. [Registration for owners and pets] guide pet owners to register as a owner, and register pets under the owner. Ask user to provide owner name and telephone and pet name, pet type." +
							"3. [Vet Recommendation] Ask the pet's illness. Recommend the vet who specialize in the pet's illness." +
							"You cannot: make an appointment with the vets.";

	@SystemMessage({ SYSTEM_PROMPT })
	String chat(@UserMessage String message, @MemoryId @UserName String username, @V("registration name") String registrationName);

}
