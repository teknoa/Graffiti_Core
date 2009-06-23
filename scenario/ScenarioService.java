/*******************************************************************************
 * 
 *    Copyright (c) 2003-2007 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/
/*
 * Created on 16.03.2007 by Christian Klukas
 */
package scenario;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.Action;

import org.ReleaseInfo;
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
		return new Scenario("", "");
	}
	
	public static boolean isRecording() {
		return recordingActive ;
	}
	
	public static void recordStop() {
		recordingActive = false;
	}
	
	@SuppressWarnings("deprecation")
	public static void recordStart() {
		currentScenario = new Scenario("Workflow", "Recorded at "+new Date().toLocaleString());
		recordingActive = true;
	}

	public static void setGUI(ScenarioGui gui) {
		ScenarioService.gui = gui;
	}

	public static void postWorkflowStep(Algorithm algorithm, Parameter[] params) {
		if (isRecording()) {
			if (gui!=null)
				gui.postWorkflowStep(algorithm, params);
			currentScenario.addImports(getImportsForAlgorithm(algorithm, params));
			currentScenario.addCommands(getStartCommandsForAlgorithm(algorithm, params));
		}
	}
	
	public static void postWorkflowStep(Action action) {
		if (isRecording()) { 
			if (gui!=null)
				gui.postWorkflowStep(action);
			currentScenario.addImports(getImportsForAction(action));
			currentScenario.addCommands(getCommandsForAction(action));
		}
	}

	private static Collection<String> getCommandsForAction(Action action) {
		ArrayList<String> res = new ArrayList<String>();

		String name = (String)action.getValue("name");
		res.add("GraffitiAction.performAction(\""+name+"\");");
		
		return res;
	}

	private static Collection<String> getImportsForAction(Action action) {
		ArrayList<String> res = new ArrayList<String>();
		
		res.add("import org.graffiti.plugin.actions.GraffitiAction;");
		
		return res;
	}

	private static Collection<String> getImportsForAlgorithm(
			Algorithm algorithm, Parameter[] params) {
		ArrayList<String> res = new ArrayList<String>();
		
		res.add("import org.graffiti.editor.GravistoService;");
		res.add("import org.graffiti.editor.MainFrame;");
		
//		Package p = algorithm.getClass().getPackage();
//		res.add("import "+p.getName());
//		if (params!=null)
//			res.add("import org.graffiti.plugin.parameter.*");
		return res;
	}

	private static Collection<String> getStartCommandsForAlgorithm(
			Algorithm algorithm, Parameter[] params) {
		ArrayList<String> res = new ArrayList<String>();
		
		res.add("// Starting Algorithm "+algorithm.getName());
		res.add("{");
		res.add("   GravistoService.getInstance().runPlugin(\""+algorithm.getName()+"\", MainFrame.getInstance().getActiveEditorSession().getGraph());");
//		res.add("   "+algorithm.getClass().getName()+" algo = new "+algorithm.getClass().getName()+"();");
//		res.add("   algo.attach( MainFrame.getInstance().getActiveEditorSession().getGraph(), MainFrame.getInstance().getActiveEditorSession().getSelectionModel().getActiveSelection());");
//		res.add("   algo.check();");
//		res.add("   algo.execute();");
//		res.add("   algo.reset();");
		res.add("}");
		
//		algo.setParameters(params);
		return res;
	}

	public static Collection<Scenario> getAvailableScnenarios() {
		Collection<Scenario> result = new ArrayList<Scenario>();
		String folder = ReleaseInfo.getAppFolderWithFinalSep();
		File dir = new File(folder);
		for (File f : dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.canRead() && pathname.getAbsolutePath().toLowerCase().endsWith(".bsh");
			}})) {
			
			Scenario s = new Scenario(f);
			if (s.isValid())
				result.add(s);
		}
		return result;
	}

	
}
