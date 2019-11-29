package com.kamer.linecodingsimulator.app;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EncoderService {

	public Graph unipolarNrz(String inputText) {

		final String binaryText = Utils.convertToBinary(inputText);

		final List<Integer> binaryNumbers = binaryText.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());

		final Graph graph = convertToSignal(binaryNumbers);

		graph.setAlgorithm("Unipolar NRZ");

		graph.setGridLineCount(binaryText.length());

		graph.setXAxisMax(binaryText.length());

		return graph;

	}

	public Graph nrzLevel(String inputText) {

		final String binaryText = Utils.convertToBinary(inputText);

		final List<Integer> nrzLeveled = binaryText.chars()
				.mapToObj(Character::getNumericValue)
				.map(numeric -> numeric == 1 ? -1 : 1)
				.collect(Collectors.toList());

		final Graph graph = convertToSignal(nrzLeveled);

		graph.setAlgorithm("NRZ Level");

		graph.setGridLineCount(binaryText.length());

		graph.setXAxisMax(binaryText.length());

		return graph;

	}

	public Graph nrzInvert(String inputText) {

		final String binaryText = Utils.convertToBinary(inputText);

		final List<Integer> nrzInverted = new ArrayList<>();

		nrzInverted.add(binaryText.charAt(0) == 0 ? 1 : -1);

		for (int i = 1; i < binaryText.length(); i++) {

			if (binaryText.charAt(i) == '1') {
				nrzInverted.add(nrzInverted.get(i - 1) == 1 ? -1 : 1);
			}
			else {
				nrzInverted.add(nrzInverted.get(i - 1));
			}
		}

		final Graph graph = convertToSignal(nrzInverted);

		graph.setAlgorithm("NRZ Invert");

		graph.setGridLineCount(binaryText.length());

		graph.setXAxisMax(binaryText.length());

		return graph;

	}

	Graph manchester(String inputText) {

		final String binaryText = Utils.convertToBinary(inputText);

		final List<Integer> manchestered = new ArrayList<>();

		binaryText.chars().mapToObj(Character::getNumericValue).forEach(numeric -> {
			if (numeric == 1) {
				manchestered.add(-1);
				manchestered.add(1);
			}
			else {
				manchestered.add(1);
				manchestered.add(-1);
			}
		});

		final Graph graph = convertToSignalManchester(manchestered);

		graph.setAlgorithm("Manchester");

		graph.setGridLineCount(binaryText.length());

		graph.setXAxisMax(binaryText.length());

		return graph;
	}

	Graph difManchester(String inputText) {

		final String binaryText = Utils.convertToBinary(inputText);

		final StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("01");

		for (int i = 1; i < binaryText.length(); i++) {

			if (binaryText.charAt(i) == '1') {
				stringBuilder.append(stringBuilder.substring((i * 2) - 2, (i * 2)).equals("01") ? "10" : "01");
			}
			else {
				stringBuilder.append(stringBuilder.substring((i * 2) - 2, (i * 2)));
			}
		}

		final List<Integer> difManchestered = stringBuilder.chars()
				.mapToObj(Character::getNumericValue)
				.map(numeric -> numeric == 0 ? -1 : 1)
				.collect(Collectors.toList());

		final Graph graph = convertToSignalManchester(difManchestered);

		graph.setGridLineCount(binaryText.length());

		graph.setXAxisMax(binaryText.length());

		graph.setAlgorithm("Differential Manchester");

		return graph;
	}

	Graph ami(String inputText) {

		final String binaryText = Utils.convertToBinary(inputText);

		final List<Integer> amid = new ArrayList<>();

		int tempPositive = 1;

		for (int i = 0; i < binaryText.length(); i++) {

			if (binaryText.charAt(i) == '0') {

				amid.add(0);
			}
			else {

				amid.add(tempPositive);

				tempPositive = tempPositive == 1 ? -1 : 1;
			}

		}

		final Graph graph = convertToSignal(amid);

		graph.setAlgorithm("AMI");

		graph.setGridLineCount(binaryText.length());

		graph.setXAxisMax(binaryText.length());

		return graph;

	}

	private Graph convertToSignal(List<Integer> binaryNumbers) {

		Object[][] data = new Object[binaryNumbers.size() * 2 + 1][2];

		data[0][0] = 'x';
		data[0][1] = 'y';

		int clock = 0;
		int dataIndex = 0;

		for (int i = 1; i < binaryNumbers.size() * 2 + 1; i++) {

			data[i][0] = clock;
			data[i][1] = binaryNumbers.get(dataIndex);

			if (i % 2 == 1) {
				clock++;
			}
			else {
				dataIndex++;
			}

		}

		final Graph graph = new Graph();
		graph.setWave(data);

		return graph;
	}

	private Graph convertToSignalManchester(List<Integer> binaryNumbers) {

		Object[][] data = new Object[binaryNumbers.size() * 2 + 1][2];

		data[0][0] = 'x';
		data[0][1] = 'y';

		float clock = 0.0f;
		int dataIndex = 0;

		for (int i = 1; i < binaryNumbers.size() * 2 + 1; i++) {

			data[i][0] = clock;
			data[i][1] = binaryNumbers.get(dataIndex);

			if (i % 2 == 1) {
				clock += 0.5;
			}
			else {
				dataIndex++;
			}

		}

		final Graph graph = new Graph();
		graph.setWave(data);

		return graph;
	}

}
