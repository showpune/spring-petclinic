package org.springframework.samples.petclinic.chat;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.UserName;
import dev.langchain4j.service.V;

public interface RegistrationAgent {

	String SYSTEM_PROMPT = "You help pet clinic customer to register as a owner and to register pets under the owner. You should follow below rules: \n" +
							"Registration for owners and pets\n"
							+ "1 To register a pet, the user must register the owner of the pet first\n"
							+ "2 To register the owner, The user must provide the owner's first name, last name, address, city, telephone. The id of new registered owner will be used for pet registering.\n"
							+ "3 If the owner is already registered, please query the owner id to register the pet.\n"
							+ "4 In addition to owner id, the user must provide the pet's name and type. Pet types including cat,dog,lizard,snake,bird and hamster. If the type of the pet is already told, don't ask it again.\n"
							+ "e.g., If the user already mentioned that he has a cat, don't ask the type.";

	@SystemMessage({ SYSTEM_PROMPT })
	String chat(@UserMessage String message, @MemoryId @UserName String username, @V("registration name") String registrationName);

}
