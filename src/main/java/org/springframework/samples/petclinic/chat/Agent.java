package org.springframework.samples.petclinic.chat;

import dev.langchain4j.service.*;

public interface Agent {

	@SystemMessage(fromResource = "/petclinic-terms-of-use.txt")
	String chat(@UserMessage String message, @MemoryId @UserName @V("username") String username);

}
