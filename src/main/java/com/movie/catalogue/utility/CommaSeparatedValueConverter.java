package com.movie.catalogue.utility;

import java.util.Arrays;

import org.apache.commons.text.WordUtils;

public class CommaSeparatedValueConverter {
	
	public static String[] stringToArray(String string) {
		String[] list = string
				.split(",");

		list = Arrays.stream(list)
				.map(item -> {
					return item.trim().replaceAll("\\s+", " ");
				})
				.map(item -> {
					return WordUtils.capitalizeFully(item);
				})
				.toArray(String[]::new);			
		return list;
	}

}
