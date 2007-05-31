/*******************************************************************************
 * 
 *    Copyright (c) 2003-2007 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/
/*
 * Created on 16.03.2007 by Christian Klukas
 */
package scenario;

import java.util.Date;

import org.graffiti.plugin.algorithm.Algorithm;
import org.graffiti.plugin.parameter.Parameter;

public class ScenarioService {
	
	private static Scenario currentScenario;
	private static boolean recordingActive = false;
	
	private static ScenarioGui gui;

	public static Scenario getCurrentScenario() {
		return currentScenario;
	}
	
	public static void setCurrentScenario(Scenario scenario) {
		ScenarioService.currentScenario = scenario;
	}
	
	public static Scenario createScenario() {
		return new Scenario("");
	}
	
	public static boolean isRecording() {
		recordingActive = true;
		return recordingActive ;
	}
	
	@SuppressWarnings("deprecation")
	public static void recordStart() {
		currentScenario = new Scenario("Recorded at "+new Date().toLocaleString());
		recordingActive = true;
	}

	public static void setGUI(ScenarioGui gui) {
		ScenarioService.gui = gui;
	}

	public static void postWorkflowStep(Algorithm algorithm, Parameter[] params) {
		if (gui!=null)
			gui.postWorkflowStep(algorithm, params);
	}
	
	
}
