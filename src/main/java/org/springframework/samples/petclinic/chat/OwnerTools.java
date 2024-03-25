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

	public OwnerTools(OwnerRepository clinicService) {
		this.owners = clinicService;
	}

	@Tool(value = { "Query the owners by name, include their pets and visit record" })
	List<Owner> queryOwners(String name) {
		Pageable pageable = PageRequest.of(0, 5);
		return owners.findByLastName(name, pageable).toList();
	}

	@Tool(value = { "Query the pet by owner id" })
	List<Pet> listPetByOwnerId(int ownerId) {
		Owner owner = this.owners.findById(ownerId);
		if (owner == null) {
			throw new IllegalArgumentException("Owner ID not found: " + ownerId);
		}
		return owner.getPets();
	}

	@Tool(value = { "Create a new owner by inputting firstName, lastName, address, telephone and city" })
	public void addOwner(String address, String telephone, String city, String firstName, String lastName) {
		Owner owner = new Owner();
		owner.setAddress(address);
		owner.setTelephone(telephone);
		owner.setCity(city);
		owner.setLastName(lastName);
		owner.setFirstName(firstName);
		this.owners.save(owner);
	}

	@Tool(value = { "return all pairs of pet type id and pet type name" })
	public Collection<PetType> populatePetTypes() {
		return this.owners.findPetTypes();
	}

	@Tool(value = { "Create a new pet by  Owner id, Pet Type, Pet Type Id and Name" })
	public void addPet(int ownerid, String petType, int petTypeId, String name) {
		Owner owner = owners.findById(ownerid);
		Pet pet = new Pet();
		pet.setName(name);
		pet.setBirthDate(LocalDate.now());
		pet.setType(new PetType() {
			{
				setName(petType);
				setId(petTypeId);
			}
		});
		owner.addPet(pet);
		this.owners.save(owner);
	}

}
