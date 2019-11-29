package com.kamer.linecodingsimulator.app;

import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Utils {

	private static final int BITS_PER_CHAR = 7;

	static String convertToBinary(String inputText) {

		return inputText.chars().mapToObj(Integer::toBinaryString).collect(Collectors.joining(""));
	}

	static String convertToString(String binary) {

		final Iterable<String> iterableBinaryWords = Splitter.fixedLength(BITS_PER_CHAR).split(binary);

		final ArrayList<String> listBinaryWords = new ArrayList<>();

		iterableBinaryWords.forEach(listBinaryWords::add);

		return listBinaryWords.stream()
				.map(binaryCharacter -> (char) Integer.parseInt(binaryCharacter, 2))
				.map(String::valueOf)
				.collect(Collectors.joining(""));
	}

}