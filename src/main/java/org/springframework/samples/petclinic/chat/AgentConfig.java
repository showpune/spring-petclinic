package org.springframework.samples.petclinic.chat;

import com.azure.core.http.ProxyOptions;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OpenAIProperties.class)
public class AgentConfig {

	@Bean
	Agent configurePetclinicChatAgent(ChatLanguageModel chatLanguageModel, ContentRetriever contentRetriever,
			ChatMemoryProvider chatMemoryProvider, VetTools VetTools, OwnerTools OwnerTools) {
		return AiServices.builder(Agent.class)
			.chatLanguageModel(chatLanguageModel)
			.tools(VetTools, OwnerTools)
			.contentRetriever(contentRetriever)
			.chatMemoryProvider(chatMemoryProvider)
			.build();
	}

	@Bean
	@ConditionalOnProperty(OpenAIProperties.PREFIX + ".chat-model.api-key")
	AzureOpenAiChatModel openAiChatModel(OpenAIProperties properties) {
		ChatModelProperties chatModelProperties = properties.getChatModel();
		return AzureOpenAiChatModel.builder()
			.endpoint(chatModelProperties.getEndpoint())
			.apiKey(chatModelProperties.getApiKey())
			.deploymentName(chatModelProperties.getDeploymentName())
			.temperature(chatModelProperties.getTemperature())
			.topP(chatModelProperties.getTopP())
			.stop(chatModelProperties.getStop())
			.maxTokens(chatModelProperties.getMaxTokens())
			.presencePenalty(chatModelProperties.getPresencePenalty())
			.frequencyPenalty(chatModelProperties.getFrequencyPenalty())
			.timeout(chatModelProperties.getTimeout())
			.maxRetries(chatModelProperties.getMaxRetries())
			.proxyOptions(ProxyOptions.fromConfiguration(com.azure.core.util.Configuration.getGlobalConfiguration()))
			.logRequestsAndResponses(chatModelProperties.getLogRequestsAndResponses() != null
					&& chatModelProperties.getLogRequestsAndResponses())
			.build();
	}

}
