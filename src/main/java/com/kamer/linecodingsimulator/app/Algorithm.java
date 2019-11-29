package com.kamer.linecodingsimulator.app;

/**
 * Created on November, 2019
 *
 * @author kamer
 */
public enum Algorithm {

	UNIPOLAR_NRZ ("Unipolar NRZ"),
	NRZ_LEVEL("NRZ Level"),
	NRZ_INVERT("NRZ Invert"),
	MANCHESTER("Manchester"),
	DIFFERENTIAL_MANCHESTER("Differential Manchester"),
	AMI("AMI");

	private String name;

	Algorithm(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
