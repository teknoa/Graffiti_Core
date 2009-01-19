//==============================================================================
//
//   XMLHelper.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: XMLHelper.java,v 1.6 2009/01/19 09:31:04 belau Exp $

package org.graffiti.plugin;

//Java imports
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.ErrorMsg;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import com.sun.org.apache.xpath.internal.XPathAPI;
import com.sun.org.apache.xpath.internal.objects.XBoolean;

/**
 * Contains some (static) auxiliary methods for writing XML.
 */
public class XMLHelper {
	// ~ Static fields/initializers =============================================

	/** Indicates whether or not indent XML elements. */
	public static boolean useIndentation = false;

	// ~ Methods ================================================================

	/**
	 * Returns a string used to separate XML elements for better readability.
	 * 
	 * @return XML element delimiter string
	 */
	public static String getDelimiter() {
		// return GeneralUtils.getNewLineDelimiter();
		return "";
	}

	/**
	 * Returns a String containing <code>n</code> spaces (or the empty String
	 * if <code>useIndentation</code> is set to <code>false</code>).
	 * 
	 * @param n
	 *           number of spaces
	 * 
	 * @return DOCUMENT ME!
	 */
	public static String spc(int n) {
		if (useIndentation) {
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < n; i++) {
				sb.append(" ");
			}

			return sb.toString();
		} else {
			return "";
		}
	}

	public static String getOuterXml(Node node) throws TransformerException {
		DOMSource nodeSource = new DOMSource(node);
		//System.out.println("ClassTypeNodeDOMsource:"+nodeSource.getClass().getCanonicalName());
		StringWriter resultStringWriter = new StringWriter();
		StreamResult streamResult = new StreamResult(resultStringWriter);

		Transformer outerXmlTransformer = TransformerFactory.newInstance()
				.newTransformer();
		// System.out.println("OutTransformer:"+outerXmlTransformer.getClass().getCanonicalName());
		outerXmlTransformer.setOutputProperty("omit-xml-declaration", "yes");
		outerXmlTransformer.transform(nodeSource, streamResult);

		String result = resultStringWriter.toString();
		result = ErrorMsg.stringReplace(result, "'", "");
		return result;
	}

	@SuppressWarnings("deprecation")
	public static String getOuterXmlPretty(Node n) throws IOException, TransformerException, JDOMException {
		ByteArrayInputStream is = new ByteArrayInputStream(getOuterXml(n).getBytes("UTF-8"));
		Document doc = getDocument(is);
		StringWriter resultStringWriter = new StringWriter();
		XMLOutputter serializer = new XMLOutputter();
		serializer.setFormat(Format.getPrettyFormat());
		serializer.output(getJDOMfromDOM(doc), resultStringWriter);
		String result = resultStringWriter.toString();
		result = ErrorMsg.stringReplace(result, "'", "");
		return result;
	}
	
	public static org.jdom.Document getJDOMfromDOM(org.w3c.dom.Document doc) {
		DOMBuilder db = new DOMBuilder();
		return db.build(doc);
	}

	/**
	 * @param xmlString
	 * @return
	 */
	public static Node getXMLnodeFromString(String xmlString) {
		Document d = getDocumentFromXMLstring(xmlString);
		if (d != null)
			return d.getFirstChild();
		else
			return d;
	}

	// private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private static DocumentBuilderFactory dbf = 
		new DocumentBuilderFactoryImpl();

	/**
	 * @param res
	 * @return
	 */
	public static Document getDocumentFromXMLstring(String res) {
		// System.out.println("Try to parse:\n"+res);
		// Set namespaceAware to true to get a DOM Level 2 tree with nodes
		// containing namespace information. This is necessary because the
		// default value from JAXP 1.0 was defined to be false.
		dbf.setNamespaceAware(false);
		DocumentBuilder db;
		Document doc = null;
		try {
			db = dbf.newDocumentBuilder();
			// Step 3: parse the input file
			InputSource is = new InputSource(new StringReader(res));
			doc = db.parse(is);
			// System.err.println("Type of XML Document Builder: " + db.getClass().getCanonicalName());
			return doc;
		} catch (NullPointerException e) {
			ErrorMsg.addErrorMessage("Null Pointer Exception, data could not be retrieved.<br>"
							+ e.getLocalizedMessage());
		} catch (SAXException e) {
			System.out.println("Invalid XML: "+res);
		} catch (IOException e) {
			ErrorMsg.addErrorMessage("IO Exception while processing experimental data.<br>"
							+ e.getLocalizedMessage());
		} catch (ParserConfigurationException e) {
			ErrorMsg.addErrorMessage("Format Parser Configuration Exception while processing experimental data.<br>"
							+ e.getLocalizedMessage());
		} catch (Exception e) {
			ErrorMsg.addErrorMessage("Exception, data could not be processed.<br>"
							+ e.getLocalizedMessage());
		}
		return null;
	}
	
	public static Document getDocument(InputStream inpS) {
		// System.out.println("Try to parse:\n"+res);
		// Set namespaceAware to true to get a DOM Level 2 tree with nodes
		// containing namesapce information. This is necessary because the
		// default value from JAXP 1.0 was defined to be false.
		dbf.setNamespaceAware(false);
		DocumentBuilder db;
		Document doc = null;
		try {
			db = dbf.newDocumentBuilder();
			// Step 3: parse the input file
			InputSource is = new InputSource(inpS);
			doc = db.parse(is);
			// System.err.println("Type of XML Document Builder: " + db.getClass().getCanonicalName());
			return doc;
		} catch (NullPointerException e) {
			ErrorMsg
					.addErrorMessage("Null Pointer Exception, data could not be retrieved.<br>"
							+ e.getLocalizedMessage());
		} catch (SAXException e) {
			ErrorMsg
					.addErrorMessage("Format Parser (SAX) Exception while processing experimental data.<br>"
							+ e.getLocalizedMessage());
		} catch (IOException e) {
			ErrorMsg
					.addErrorMessage("IO Exception while processing experimental data.<br>"
							+ e.getLocalizedMessage());
		} catch (ParserConfigurationException e) {
			ErrorMsg
					.addErrorMessage("Format Parser Configuration Exception while processing experimental data.<br>"
							+ e.getLocalizedMessage());
		}
		return null;
	}

	public static void setTtestInfoSampleIsReference(org.w3c.dom.Node sampleNode) {
		Attr attr = sampleNode.getOwnerDocument().createAttribute("t-test");
		attr.setNodeValue("reference");
		Element e = (Element) sampleNode;
		e.setAttributeNode(attr);
	}

	public static Double[] getDataList(Node sampleNode) {
		if (sampleNode == null)
			return new Double[0];
		List<Double> measureValues = new ArrayList<Double>();
		Node n = sampleNode.getFirstChild();
		while(n!=null) {
			if (n.getNodeName().equalsIgnoreCase("data")) {
				String ms = n.getFirstChild().getNodeValue();
				double mesVal = Double.parseDouble(ms);
				measureValues.add(new Double(mesVal));
			}
			n = n.getNextSibling();
		}
		return measureValues.toArray(new Double[0]);
	}

	public static int getSampleTimeValueForComparison(Node sampleNode) {
		String time;
		if (sampleNode.getAttributes() == null
				|| sampleNode.getAttributes().getNamedItem("time") == null)
			time = "-1";
		else
			time = sampleNode.getAttributes().getNamedItem("time").getFirstChild()
					.getNodeValue();
		int result;
		try {
			result = Integer.parseInt(time);
		} catch(NumberFormatException nfe) {
			ErrorMsg.addErrorMessage("Sample time \""+time+"\" not in integer format!");
			result = -1;
		}
		return result;
	}

	/**
	 * @param sampleNode
	 * @return The time and time unit from a sample. e.g. "day 1" or "week 2".
	 *         Returns "" if no time information is avalilable.
	 */
	public static String getSampleTime(Node sampleNode) {
		String time;
		if (sampleNode.getAttributes() == null
				|| sampleNode.getAttributes().getNamedItem("time") == null)
			time = "-1";
		else
			time = sampleNode.getAttributes().getNamedItem("time").getFirstChild()
					.getNodeValue();
		String timeunit;
		if (sampleNode.getAttributes() == null
				|| sampleNode.getAttributes().getNamedItem("unit") == null)
			timeunit = "-1";
		else
			timeunit = sampleNode.getAttributes().getNamedItem("unit")
					.getFirstChild().getNodeValue();

		String timeUnitAndTime = timeunit + " " + time; // +" ("+unit+")";
		if (time.equals("-1") && timeunit.equals("-1"))
			timeUnitAndTime = ""; // "("+unit+")";

		return timeUnitAndTime;
	}

	/**
	 * @param sampleNode
	 * @return The time and time unit from a sample. e.g. "day 1" or "week 2".
	 *         Returns "" if no time information is avalilable.
	 */
	public static String getSampleTimeUnit(Node sampleNode) {
		String timeunit;
		if (sampleNode.getAttributes() == null
				|| sampleNode.getAttributes().getNamedItem("unit") == null)
			timeunit = null;
		else
			timeunit = sampleNode.getAttributes().getNamedItem("unit")
					.getFirstChild().getNodeValue();

		return timeunit;
	}

	/**
	 * @param sampleNode
	 */
	public static void tTestSetSampleAsReference(Node sampleNode) {
		Attr attr = sampleNode.getOwnerDocument().createAttribute("ttest");
		attr.setNodeValue("reference");
		Element e = (Element) sampleNode;
		e.setAttributeNode(attr);
	}

	public static void tTestSetSampleSignificane(Node sampleNode,
			boolean different) {
		Attr attr = sampleNode.getOwnerDocument().createAttribute("ttest");
		if (different)
			attr.setNodeValue("H1");
		else
			attr.setNodeValue("H0");
		// System.out.println(attr.getNodeValue());
		Element e = (Element) sampleNode;
		e.setAttributeNode(attr);
		e.removeAttribute("ttest-level");
	}
	
	public static void setTTestSetSampleSignificane(Node sampleNode,
			boolean different, double level) {
		Attr attr = sampleNode.getOwnerDocument().createAttribute("ttest");
		Attr attrLevel = sampleNode.getOwnerDocument().createAttribute("ttest-level");
		if (different)
			attr.setNodeValue("H1");
		else
			attr.setNodeValue("H0");
		// System.out.println(attr.getNodeValue());
		attrLevel.setNodeValue(level+"");
		Element e = (Element) sampleNode;
		e.setAttributeNode(attr);
		e.setAttributeNode(attrLevel);
	}

	/**
	 * @param samplenode
	 * @return
	 */
	public static boolean tTestIsReference(Node sampleNode) {
		if (sampleNode.getAttributes() == null
				|| sampleNode.getAttributes().getNamedItem("ttest") == null)
			return true;
		else
			return sampleNode.getAttributes().getNamedItem("ttest")
					.getFirstChild().getNodeValue().equals("reference");
	}

	/**
	 * @param linenode
	 * @return
	 */
	public static String getSeriesNameForLine(org.w3c.dom.Node linenode) {
		try {
			 String linename = linenode.getAttributes().getNamedItem("name").getFirstChild().getNodeValue();
			String linegenotype = linenode.getAttributes().getNamedItem("genotype").getFirstChild().getNodeValue();
			String linetreatment = linenode.getAttributes().getNamedItem("treatment").getFirstChild().getNodeValue();
			return getSeriesNameFromSpeciesGenotypeAndTreatment(linename, linegenotype, linetreatment);
		} catch(Exception e) {
			ErrorMsg.addErrorMessage(e);
			return "Error: Series Name Unknown";
		}
	}

	public static String getSeriesNameFromSpeciesGenotypeAndTreatment(String linename, String linegenotype, String linetreatment) {
		String serie;
		// DO NOT CHANGE THE NAMING SYSTEM
		// IT IS PROCESSED BY CLASS SeriesData and at least XML Helper !
		if (linetreatment != null && linetreatment.length()>0 && !linetreatment.equalsIgnoreCase("null") && !checkForSameGenoTypeAndTreatment(linegenotype, linetreatment))
			serie = linename + (linegenotype.length()>0 ? "/" + linegenotype : "") + " (" + linetreatment + ")";
		else
			serie = linename + (linegenotype.length()>0 ? "/" + linegenotype : "");
		return serie;
	}


	private static boolean checkForSameGenoTypeAndTreatment(String linegenotype, String linetreatment) {
		if (linegenotype==null || linetreatment==null)
			return false;
		if (linegenotype.equalsIgnoreCase(linetreatment))
			return true;
		else 
			return false;
	}

	/**
	 * @param samplenode
	 * @return
	 */
	public static boolean ttestIsH1(Node sampleNode) {
		if (sampleNode.getAttributes() == null
				|| sampleNode.getAttributes().getNamedItem("ttest") == null)
			return true;
		else
			return sampleNode.getAttributes().getNamedItem("ttest")
					.getFirstChild().getNodeValue().equals("H1");
	}

	public static String getExpAndSeriesName(String expName, String seriesName) {
		return expName + ": " + seriesName;
	}

	public static String getExpAndSeriesNameFromSample(
			org.w3c.dom.Node sampleNode) {
		org.w3c.dom.Node lineNode = sampleNode.getParentNode();
		if (lineNode.getNodeName().equals("line")) {
			return getExpAndSeriesNameFromLine(lineNode);
		} else
			return null;
	}

	public static String getExpAndSeriesNameFromLine(org.w3c.dom.Node lineNode) {
		String expName = getExperimentNameFromLineNode(lineNode);
		return getExpAndSeriesName(expName, getSeriesNameForLine(lineNode));
	}

	public static Node getFirstMatchingSampleFromTimeAndLine(List samplesInNode,
			String compareTime, String seriesNameFromSample) {
		Node result = null;
		for (Iterator it = samplesInNode.iterator(); it.hasNext();) {
			Node n = (Node) it.next();
			if (n.getNodeName().equalsIgnoreCase("sample")) {
				String time = getSampleTime(n);
				if (time.equals(compareTime)
						&& seriesNameFromSample
								.equals(getExpAndSeriesNameFromSample(n))) {
					result = n;
					break;
				}
			} else
				ErrorMsg.addErrorMessage("Expected Sample Node, but got different data set: "+ n.getNodeName());
		}
		return result;
	}

	public static String getExperimentNameFromLineNode(Node linenode) {
		try {
		Node t1 = linenode.getAttributes().getNamedItem("experimentname");
		if (t1 != null)
			return t1.getNodeValue();
		else
			return linenode.getOwnerDocument().getElementsByTagName(
					"experimentname").item(0).getFirstChild().getNodeValue();
		} catch(NullPointerException npe) {
			return "";
		}
	}
	
	public static String getCoordinatorFromLineNode(Node linenode) {
		Node t1 = linenode.getAttributes().getNamedItem("coordinator");
		if (t1 != null)
			return t1.getNodeValue();
		else
			return linenode.getOwnerDocument().getElementsByTagName(
					"coordinator").item(0).getFirstChild().getNodeValue();
	}	
	
	public static String getStartDateFromLineNode(Node linenode) {
		String date = "";
		Node t1 = linenode.getAttributes().getNamedItem("startdate");
		if (t1 != null)
			date = t1.getNodeValue();
		else
			date = linenode.getOwnerDocument().getElementsByTagName(
					"startdate").item(0).getFirstChild().getNodeValue();
		return date;
	}
	
	
	public static String getFuncatDescFromLineNode(Node linenode) {
		Node t1 = linenode.getAttributes().getNamedItem("funcat");
		if (t1 != null)
			return t1.getNodeValue();
		else
			return null;
	}

	public static String getSeriesIDforLine(Node linenode) {
		try {
			String lineid = linenode.getAttributes().getNamedItem("id").getFirstChild().getNodeValue();
			return lineid;
		} catch(Exception eee) {
			return null;
		}
	}

	/**
	 * (1) search substance names which end with <code>substanceStdDevEndID</code>
	 * (2) for each substance get all experiment/genotype/line/time combinations enclosed
	 *      extract average sample values
	 * (3) use average sample values as sample stddev for corresponding substance/sample data
	 *       whose substance name is the same as the one mentioned above, but without the trailing
	 *       <code>substanceStdDevEndID</code> id.
	 * @param mydoc
	 * @param substanceStdDevEndID
	 * @return Number of processed "std-dev substances"
	 */
	public static int processAvailableStdDevSubstanceData(Document mydoc, String substanceStdDevEndID) {
		substanceStdDevEndID = substanceStdDevEndID.toUpperCase();
		int transformed = 0;
		String xpath = "//substance";
		HashMap<String,Node> substName2substNode = new HashMap<String,Node>();
		ArrayList<String> toBeDeleted = new ArrayList<String>();
		try {
			NodeIterator substanceNodes = XPathAPI.selectNodeIterator(mydoc, xpath);
			Node substance;
			while((substance = substanceNodes.nextNode()) != null) {
					String substanceName = substance.getAttributes().getNamedItem("name").getNodeValue();
					if (substName2substNode.containsKey(substanceName.toUpperCase())) {
						ErrorMsg.addErrorMessage("Substance named "+substanceName+" seems to be more than one time defined in the dataset!");
					} else
						substName2substNode.put(substanceName.toUpperCase(), substance);
			}
			for (String substName : substName2substNode.keySet()) {
				if (substName.endsWith(substanceStdDevEndID)) {
					String correspondingSubstanceName = substName.substring(0, substName.length()-substanceStdDevEndID.length());
					if (substName2substNode.containsKey(correspondingSubstanceName)) {
						int transformedForThisSubstance = 0;

						HashMap<String,Node> samplePath2avgNode_stdDevData = getSamplePathAndAvgValues(substName2substNode.get(substName));
						HashMap<String,Node> samplePath2avgNode_actualDataWithoutStdDev = getSamplePathAndAvgValues(substName2substNode.get(correspondingSubstanceName));
						// samplePath = //id of plant/name of plant/genotype of plant/timepoint and timeunit
						for (String samplePath : samplePath2avgNode_stdDevData.keySet()) {
							if (samplePath2avgNode_actualDataWithoutStdDev.containsKey(samplePath)) {
								Node stdDevAvgData = samplePath2avgNode_stdDevData.get(samplePath);
								Node actualDataWithoutStdDev = samplePath2avgNode_actualDataWithoutStdDev.get(samplePath);
								// avg value --> std dev value
								String avgValue = stdDevAvgData.getFirstChild().getNodeValue();
								actualDataWithoutStdDev.getAttributes().getNamedItem("stddev").setNodeValue(avgValue);
								transformed++;
								transformedForThisSubstance++;
							}
						}							
						
						if (transformedForThisSubstance>0)
							toBeDeleted.add(substName);
					}
				}
			}
			for (String deleteSubstance : toBeDeleted) {
				Node n = substName2substNode.get(deleteSubstance);
				n.getParentNode().removeChild(n);
			}
		} catch (TransformerException e) {
			ErrorMsg.addErrorMessage(e);
		}
		return transformed;
	}

	private static HashMap<String, Node> getSamplePathAndAvgValues(Node substanceNode) throws TransformerException {
		HashMap<String,Node> result = new HashMap<String,Node>();
		String xpath = "line/sample";
		NodeIterator sampleNodes = XPathAPI.selectNodeIterator(substanceNode, xpath);
		Node sample;
		while((sample = sampleNodes.nextNode()) != null) {
			String sampleTime = sample.getAttributes().getNamedItem("time").getNodeValue();
			String sampleTimeUnit = sample.getAttributes().getNamedItem("unit").getNodeValue();
			String lineSpeciesID = sample.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
			String lineSpeciesName = sample.getParentNode().getAttributes().getNamedItem("name").getNodeValue();
			String lineSpeciesGenotype = sample.getParentNode().getAttributes().getNamedItem("genotype").getNodeValue();
			String sampleAverageValue = null;
			// search average child node 
			Node avgOrData = sample.getFirstChild();
			while (avgOrData!=null) {
				if (avgOrData.getNodeName().equalsIgnoreCase("average")) {
					String path = "//"+lineSpeciesID+"/"+lineSpeciesName+"/"+lineSpeciesGenotype+"/"+sampleTime+" "+sampleTimeUnit;
					path = path.toUpperCase();
					if (result.containsKey(path))
						ErrorMsg.addErrorMessage("Duplicate measurement data found for species/genotype/sample time combination: "+path);
					else
						result.put(path, avgOrData);
				}
				avgOrData = avgOrData.getNextSibling();
			}
		}
		return result;
	}

	public static boolean isReplicateDataMissing(Document mydoc) {
		// 
		String xpath = "count(//substance/line/sample/average[@replicates > 1])<=0";
		Object result;
		try {
			result = XPathAPI.eval(mydoc, xpath);
		} catch (TransformerException e) {
			ErrorMsg.addErrorMessage(e);
			return false;
		}
		return ((XBoolean)result).bool();
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
