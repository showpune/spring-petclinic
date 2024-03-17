package org.springframework.samples.petclinic.chat;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.model.azure.AzureOpenAiModelName.GPT_3_5_TURBO;

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

	@Bean
	ChatMemoryProvider chatMemoryProvider() {
		ChatMemoryStore store = new InMemoryChatMemoryStore();
		return memoryId -> MessageWindowChatMemory.builder()
			.id(memoryId)
			.maxMessages(20)
			.chatMemoryStore(store)
			.build();
	}

	@Bean
	ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {

		// You will need to adjust these parameters to find the optimal setting, which
		// will depend on two main factors:
		// - The nature of your data
		// - The embedding model you are using
		int maxResults = 1;
		double minScore = 0.6;

		return EmbeddingStoreContentRetriever.builder()
			.embeddingStore(embeddingStore)
			.embeddingModel(embeddingModel)
			.maxResults(maxResults)
			.minScore(minScore)
			.build();
	}

	@Bean
	EmbeddingModel embeddingModel() {
		return new AllMiniLmL6V2EmbeddingModel();
	}

	@Bean
	EmbeddingStore<TextSegment> embeddingStore(EmbeddingModel embeddingModel, ResourceLoader resourceLoader)
			throws IOException {

		EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

		Resource resource = resourceLoader.getResource("classpath:petclinic-terms-of-use.txt");
		Document document = loadDocument(resource.getFile().toPath(), new TextDocumentParser());

		DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0, new OpenAiTokenizer(GPT_3_5_TURBO));
		EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
			.documentSplitter(documentSplitter)
			.embeddingModel(embeddingModel)
			.embeddingStore(embeddingStore)
			.build();
		ingestor.ingest(document);

		return embeddingStore;
	}

}
