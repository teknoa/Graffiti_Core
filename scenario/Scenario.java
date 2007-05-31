/*******************************************************************************
 * 
 *    Copyright (c) 2003-2007 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/
/*
 * Created on 16.03.2007 by Christian Klukas
 */
package scenario;

import java.util.ArrayList;
import java.util.Collection;

public class Scenario {
	
	ArrayList<String> imports = new ArrayList<String>();
	ArrayList<String> commands = new ArrayList<String>();
	String scenarioName;
	
	/**
	 * Create a new scenario
	 */
	public Scenario(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	
	public void addImports(Collection<String> bshScriptCommands) {
		for (String i : bshScriptCommands) {
			boolean found = false;
			for (String s : imports) {
				if (s.equals(i)) {
					found = true;
					break;
				}
			}
			if (!found)
				imports.add(i);
		}
	}

	public void addCommands(Collection<String> bshScriptCommands) {
		commands.addAll(bshScriptCommands);
	}
	
	public void addPluginCommand(PluginWithScenarioSupport plugin) {
		addImports(plugin.getScenarioImports());
		addCommands(plugin.getScenarioCommands());
	}
	
	public Collection<String> getScenarioCommands() {
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(getHeader());
		result.addAll(imports);
		result.addAll(commands);
		return result;
	}
	
	private Collection<String> getHeader() {
		ArrayList<String> header = new ArrayList<String>();
		header.add("//@Workflow:"+scenarioName);
		header.add("//");
		return header;
	}

	/**
	 * Writes this scenario as a BSH script file to the file system.
	 */
	public void writeScenarioToFile() {
		
	}
}
