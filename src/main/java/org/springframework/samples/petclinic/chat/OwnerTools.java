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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
public class OwnerTools {

	private final OwnerRepository owners;

//	@Autowired
//	private PetTypeAgent petTypeAgent;

	public OwnerTools(OwnerRepository clinicService) {
		this.owners = clinicService;
	}

//	@Tool(value = {"Query the owners by name, include their pets and visit record"})
//	List<Owner> queryOwners(String name) {
//		Pageable pageable = PageRequest.of(0, 5);
//		return owners.findByLastName(name, pageable).toList();
//	}

	@Tool(value = { "Query the pet by owner id" })
	List<Pet> listPetByOwnerId(int ownerId) {
		Owner owner = this.owners.findById(ownerId);
		if (owner == null) {
			throw new IllegalArgumentException("Owner ID not found: " + ownerId);
		}
		return owner.getPets();
	}

	@Tool(value = { "Create a new owner by user's name and telephone." })
	public String addOwner(String telephone, String firstName, String lastName) {
		if (telephone.isEmpty())
			return "Please provide phone number.";
		if (firstName.isEmpty())
			return "Please provide first name.";
		if (lastName.isEmpty())
			return "Please provide last name.";
		Owner owner = new Owner();
		owner.setTelephone(telephone);
		owner.setLastName(lastName);
		owner.setFirstName(firstName);
		this.owners.save(owner);
		return "Owner created successfully. Owner id: " + owner.getId();
	}

	@Tool(value = { "return all pairs of pet type id and pet type name" })
	public Collection<PetType> populatePetTypes() {
		return this.owners.findPetTypes();
	}

	@Tool(value = { "Create a new pet by Owner id, pet type and pet name" })
	public String addPet(String ownerid, String petType, int petTypeId, String name) {
		if (ownerid.isEmpty())
			return "Please provide owner id, if the owner has not registered, please register the owner first.";
		if (name.isEmpty())
			return "Please provide pet name.";
		if (petType.isEmpty())
			return "Please tell me pet type";
		Owner owner = owners.findById(Integer.parseInt(ownerid));
		Pet pet = new Pet();
		pet.setName(name);
		PetType type = new PetType();
		type.setName(petType);
		type.setId(petTypeId);
		pet.setType(type);
		owner.addPet(pet);
		this.owners.save(owner);
		return "pet added successfully.";
	}
//
//	@Tool(value = { "Find out pet type by what kind of animal is the pet" })
//	public String findPetType(String animal) {
//		return "the pet type is: "+ petTypeAgent.chat(animal);
//	}

}
