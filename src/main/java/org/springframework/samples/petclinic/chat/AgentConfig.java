package org.springframework.samples.petclinic.chat;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

	@Bean
	Agent configurePetclinicChatAgent(ChatLanguageModel chatLanguageModel, ContentRetriever contentRetriever,
			ChatMemoryProvider chatMemoryProvider, VetTools VetTools, OwnerTools OwnerTools) {
		return AiServices.builder(Agent.class)
			.chatLanguageModel(chatLanguageModel)
			.tools(VetTools, OwnerTools)
			.contentRetriever(contentRetriever)
			.chatMemory(MessageWindowChatMemory.withMaxMessages(20))
			.chatMemoryProvider(chatMemoryProvider)
			.build();
	}

}
