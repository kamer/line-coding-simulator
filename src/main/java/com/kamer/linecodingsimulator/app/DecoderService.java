package com.kamer.linecodingsimulator.app;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on November, 2019
 *
 * @author kamer
 */

@Service
@NoArgsConstructor
public class DecoderService {

	public String unipolarNrz(List<Integer> diagramValues) {

		return diagramValues.stream().map(String::valueOf).collect(Collectors.joining(""));
	}

	public String nrzLevel(List<Integer> diagramValues) {

		return diagramValues.stream().map(numeric -> numeric == -1 ? String.valueOf(1) : String.valueOf(0)).collect(Collectors.joining(""));
	}

	public String nrzInvert(List<Integer> diagramValues) {

		final StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < diagramValues.size(); i++) {

			if (i == 0) {
				if (diagramValues.get(0) == 1) {
					stringBuilder.append('0');
				}
				else {
					stringBuilder.append('1');
				}
			}
			else {

				if (diagramValues.get(i) == 1) {
					stringBuilder.append(diagramValues.get(i - 1) == 1 ? '0' : '1');
				}
				else {
					stringBuilder.append(diagramValues.get(i - 1) == 1 ? '1' : '0');
				}
			}
		}

		return stringBuilder.toString();
	}

	public String manchester(List<Integer> diagramValues) {

		final String normalized = diagramValues.stream().map(numeric -> numeric == -1 ? "0" : "1").collect(Collectors.joining(""));

		final StringBuilder stringBuilder = new StringBuilder();

		for (int i = 2; i <= diagramValues.size(); i += 2) {

			if (normalized.substring(i - 2, i).equals("01")) {

				stringBuilder.append("1");
			}
			else {

				stringBuilder.append("0");
			}
		}

		return stringBuilder.toString();
	}

	public String difManchester(List<Integer> diagramValues) {

		final String normalized = diagramValues.stream().map(numeric -> numeric == -1 ? "0" : "1").collect(Collectors.joining());

		final List<String> duoString = new ArrayList<>();

		final StringBuilder stringBuilder = new StringBuilder();

		if (normalized.substring(0, 2).equals("01")) {
			stringBuilder.append("0");
		}
		else {
			stringBuilder.append("1");
		}

		for (int i = 0; i <= normalized.length()-2; i += 2) {

			duoString.add(normalized.substring(i, i+2));
		}

		for (int i = 1; i < duoString.size(); i++) {

			if (duoString.get(i - 1).equals(duoString.get(i))) {

				stringBuilder.append("0");
			}
			else {

				stringBuilder.append("1");
			}
		}

		return stringBuilder.toString();
	}

	public String ami(List<Integer> diagramValues){

		return diagramValues.stream().map(numeric -> numeric == 0 ? "0" : "1").collect(Collectors.joining());
	}

}
