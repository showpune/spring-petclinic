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

package org.springframework.samples.petclinic.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Controller
class WelcomeController {

	@GetMapping("/")
	public String welcome(Map<String, Object> model) {

		try {
			model.put("userName", "Welcome " + System.getenv("USER_NAME"));
			File file = new File("/data/mount.txt");
			if (file.exists()) {
				String content = Files.readString(Path.of(file.getAbsolutePath()));
				model.put("mountFileContent", "Mounted Content: " + content);
			} else {
				model.put("mountFileContent", "No mount file found");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "welcome";
	}
}

