package org.springframework.samples.petclinic.chat;

import com.azure.core.http.ProxyOptions;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.transformer.ExpandingQueryTransformer;
import dev.langchain4j.service.AiServices;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.langchain4j.internal.ValidationUtils.ensureNotNull;

@Configuration
@EnableConfigurationProperties(OpenAIProperties.class)
public class AgentConfig {

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

	@Bean
	Agent configurePetclinicChatAgent(ChatLanguageModel chatLanguageModel,
									  ChatMemoryProvider chatMemoryProvider,
									  RetrievalAugmentor retrievalAugmentor,
									  VetTools VetTools,
									  OwnerTools OwnerTools) {
		return AiServices.builder(Agent.class)
				.chatLanguageModel(chatLanguageModel)
				.tools(VetTools, OwnerTools)
				.chatMemoryProvider(chatMemoryProvider)
				.retrievalAugmentor(retrievalAugmentor)
				.build();
	}

	@Bean
	RetrievalAugmentor retrievalAugmentor(ChatLanguageModel chatLanguageModel,
										  ContentRetriever contentRetriever) {
		String expandString = ExpandingQueryTransformer.DEFAULT_PROMPT_TEMPLATE.template()
				+ "\n All must returned by English";
		ExpandingQueryTransformer expandingQueryTransformer =
				ExpandingQueryTransformer.builder()
						.chatLanguageModel(chatLanguageModel)
						.promptTemplate(PromptTemplate.from(expandString))
						.build();
		return DefaultRetrievalAugmentor.builder()
				.contentRetriever(ensureNotNull(contentRetriever, "contentRetriever"))
				.queryTransformer(expandingQueryTransformer)
				.build();
	}

}
