/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.chat;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.vet.Vets;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Component
public class VetTools {


	private final VetRepository vetRepository;

	@Autowired
	private RecommendationAgent recommendationAgent;

	public VetTools(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	@Tool(value = { "recommend one or two vets by the illness of the pet" })
	public String recommendVet(String petIllness) {
		if (petIllness.isEmpty())
			return "Please provide the illness or the situation of your pet.";
		Collection<Vet> vetList = vetRepository.findAll();
		StringBuilder vetListString = new StringBuilder();
		for (Vet vet : vetList) {
			vetListString.append(vet.getFirstName()).append(" ").append(vet.getLastName()).append(", ").append(vet.getSpecialties()).append(", ").append(vet.getTelephone()).append(";");
		}
		String vetListStr = vetListString.toString();
	
		String recommendation = recommendationAgent.chat("Pet illness: " + petIllness+"Please recommend one or two vets in the vet list. Vet list: "+vetListStr);
		return recommendation;
	}

}
