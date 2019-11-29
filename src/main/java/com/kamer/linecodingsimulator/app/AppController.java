package com.kamer.linecodingsimulator.app;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on November, 2019
 *
 * @author kamer
 */
@Controller
@AllArgsConstructor
public class AppController {

	private final EncoderService encoderService;

	private final DecoderService decoderService;

	@GetMapping("")
	public String index(Model model) {
		return "index";
	}

	@PostMapping("/text-to-signal")
	public String textToSignal(RedirectAttributes redirectAttributes, @RequestParam("ttg-textInput") String textInput,
			@RequestParam("algorithm") Algorithm algorithm) {

		Graph graph;

		switch (algorithm) {
		case UNIPOLAR_NRZ:
			graph = encoderService.unipolarNrz(textInput);
			break;
		case NRZ_LEVEL:
			graph = encoderService.nrzLevel(textInput);
			break;
		case NRZ_INVERT:
			graph = encoderService.nrzInvert(textInput);
			break;
		case MANCHESTER:
			graph = encoderService.manchester(textInput);
			break;
		case DIFFERENTIAL_MANCHESTER:
			graph = encoderService.difManchester(textInput);
			break;
		case AMI:
			graph = encoderService.ami(textInput);
			break;
		default:
			graph = encoderService.unipolarNrz(textInput);
		}

		redirectAttributes.addFlashAttribute("operation", "ttg");

		redirectAttributes.addFlashAttribute("graph", graph);

		redirectAttributes.addFlashAttribute("algorithm", algorithm);

		return "redirect:/";
	}

	@PostMapping("/signal-to-text")
	public String signalToText(RedirectAttributes redirectAttributes, @RequestParam("encoded-input") String textInput,
			@RequestParam("algorithm") Algorithm algorithm) {

		String decodedBinaryString = "";
		StringBuilder decodedText = new StringBuilder();

		final List<Integer> splittedValues = Arrays.stream(textInput.split("\\.")).map(Integer::valueOf).collect(Collectors.toList());

		switch (algorithm) {
		case UNIPOLAR_NRZ:
			decodedBinaryString = decoderService.unipolarNrz(splittedValues);
			break;
		case NRZ_LEVEL:
			decodedBinaryString = decoderService.nrzLevel(splittedValues);
			break;
		case NRZ_INVERT:
			decodedBinaryString = decoderService.nrzInvert(splittedValues);
			break;
		case MANCHESTER:
			decodedBinaryString = decoderService.manchester(splittedValues);
			break;
		case DIFFERENTIAL_MANCHESTER:
			decodedBinaryString = decoderService.difManchester(splittedValues);
			break;
		case AMI:
			decodedBinaryString = decoderService.ami(splittedValues);
			break;
		}

		for (int j = 0; j < decodedBinaryString.length() / 7; j++) {

			int start = j * 7;

			int end = ((j + 1) * 7);

			int decimal = Integer.parseInt(decodedBinaryString.substring(start, end), 2);

			char character = (char) decimal;

			decodedText.append(character);

		}

		if (decodedBinaryString.length() < 7) {

			int decimal = Integer.parseInt(decodedBinaryString);

			char character = (char) decimal;

			decodedText.append(character);
		}

		redirectAttributes.addFlashAttribute("operation", "gtt");

		redirectAttributes.addFlashAttribute("decodedText", decodedText);

		redirectAttributes.addFlashAttribute("algorithm", algorithm);

		return "redirect:/";
	}

}
