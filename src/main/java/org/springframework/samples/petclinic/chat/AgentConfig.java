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
	ChatAgent configurePetclinicChatAgent(ChatLanguageModel chatLanguageModel, ContentRetriever contentRetriever,
										  ChatMemoryProvider chatMemoryProvider, VetTools VetTools, OwnerTools OwnerTools) {
		return AiServices.builder(ChatAgent.class)
			.chatLanguageModel(chatLanguageModel)
			.tools(VetTools, OwnerTools)
			.contentRetriever(contentRetriever)
			.chatMemory(MessageWindowChatMemory.withMaxMessages(20))
			.chatMemoryProvider(chatMemoryProvider)
			.build();
	}

	@Bean
	RegistrationAgent configureRegistrationAgent(ChatLanguageModel chatLanguageModel, ContentRetriever contentRetriever,
										 ChatMemoryProvider chatMemoryProvider) {
		return AiServices.builder(RegistrationAgent.class)
			.chatLanguageModel(chatLanguageModel)
			.contentRetriever(contentRetriever)
			.chatMemory(MessageWindowChatMemory.withMaxMessages(20))
			.chatMemoryProvider(chatMemoryProvider)
			.build();
	}

	@Bean
	RecommendationAgent configureRecommendationAgent(ChatLanguageModel chatLanguageModel,
												 ChatMemoryProvider chatMemoryProvider) {
		return AiServices.builder(RecommendationAgent.class)
			.chatLanguageModel(chatLanguageModel)
			.chatMemory(MessageWindowChatMemory.withMaxMessages(20))
			.chatMemoryProvider(chatMemoryProvider)
			.build();
	}



}
