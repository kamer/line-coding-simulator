package com.kamer.linecodingsimulator.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created on November, 2019
 *
 * @author kamer
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Graph {

	private Object[][] wave;

	private Integer xAxisMax;

	private Integer gridLineCount;

	private String algorithm = "";

}
