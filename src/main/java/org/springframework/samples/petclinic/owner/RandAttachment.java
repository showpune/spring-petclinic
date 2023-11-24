package org.springframework.samples.petclinic.owner;

import java.sql.Timestamp;
import java.util.Date;

public class RandAttachment {

	public static String genereateString() {
		// generate a random string with length 200 to 300
		String sb = new String();

		sb = sb + new Date();
		String ranmdom = Math.random() + "";
		for (int i = 0; i < 600; i++) {
			sb = sb + ranmdom + i;
		}

		sb = sb + new Timestamp(System.currentTimeMillis());
		return "v1" + sb.substring(1, 200);
	}

	public static void main(String[] args) {
		System.out.println(genereateString());
	}

}
