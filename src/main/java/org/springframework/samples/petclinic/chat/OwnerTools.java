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
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class OwnerTools {

    private final OwnerRepository owners;

    public OwnerTools(OwnerRepository clinicService) {
        this.owners = clinicService;
    }

    @Tool(value = {"Query the owners by name"})
    List<Owner> queryOwners(String name) {
        Pageable pageable = PageRequest.of(0, 5);
        return owners.findByLastName(name, pageable).toList();
    }
}
