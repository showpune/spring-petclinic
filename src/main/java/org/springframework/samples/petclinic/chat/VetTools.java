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

	public VetTools(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	@Tool(value = { "recommend vet by the illness of the pet" })
	public String recommendVet(String petIllness) {
		if (petIllness.isEmpty())
			return "Please provide the illness or the situation of your pet.";
		Collection<Vet> vetList = vetRepository.findAll();
		StringBuilder vetListString = new StringBuilder();
		for (Vet vet : vetList) {
			vetListString.append("Name: ").append(vet.getFirstName()).append(" ").append(vet.getLastName()).append(", Specialties: ").append(vet.getSpecialties()).append(", Telephone: ").append(vet.getTelephone()).append(";\n");
		}
		String vetListStr = vetListString.toString();
	
		String recommendationAsk = "Pet illness: " + petIllness+". Please recommend one or two vets who specialize at the illness. Vet list: "+vetListStr;
		return recommendationAsk;
	}

//		@Tool(value = { "query vet list" })
//		public Collection<Vet> getVetList() {
//			// Here we are returning an object of type 'Vets' rather than a collection of Vet
//			// objects so it is simpler for Object-Xml mapping
//			Vets vets = new Vets();
//			return vetRepository.findAll();
//		}

}
