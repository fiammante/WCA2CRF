/*******************************************************************************
 * Copyright (c) 2017 IBM Corp.
 * @author Marc Fiammante marc.fiammante@fr.ibm.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/ 
package com.ibm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

// import cc.mallet.fst.SimpleTagger;




public class Annotation2CRF extends JCasAnnotator_ImplBase {
	HashSet<String> ignoreFeaturelist = new HashSet<String>(Arrays.asList("sofa", "begin", "end", "lemma",
			"lemmaEntries", "dictionaryMatch", "posTag", "takmiPOS", "ftrs", "syntacticTag", "parent", "children",
			"language", "languageCandidates", "paragraphNumber", "sentenceNumber", "ruleId"));
	HashSet<String> ignoreAnnotationlist = new HashSet<String>(Arrays.asList("uima.tt.ParagraphAnnotation","uima.tt.DocumentAnnotation"));
	private String[] pAnnotationCombination= {};
	static HashSet<String> AnnotationCombinationlist = new HashSet<String>();
	private String pLabel="";
	private Boolean pSpan=false;
	private String spanType="uima.tt.SentenceAnnotation";
	private Boolean pTrain=false;
	private String pModelFile="model";
	private Logger logger;
	private Boolean pRemovePrefix=true;
	private String datapath="";
	private Vector<Integer> begins=new Vector<Integer>();
	private Vector<Integer> ends=new Vector<Integer>();

	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);

		try {

			logger = aContext.getLogger();
			logger.log(Level.INFO, "DocAnnot: initializing:");
			pLabel = (String) aContext.getConfigParameterValue( "CRF", "LabelAnnotation");
			if (pLabel==null) pLabel="";
			pSpan = (Boolean) aContext.getConfigParameterValue( "CRF", "ParagraphSpan");
			if (pSpan==null) pSpan=false;
			if (pSpan) 	{ // Change span to a paragraph instead of a sentence
				ignoreAnnotationlist = new HashSet<String>(Arrays.asList("uima.tt.SentenceAnnotation","uima.tt.DocumentAnnotation"));
				spanType="uima.tt.ParagraphAnnotation";
			}
			pTrain = (Boolean) aContext.getConfigParameterValue( "CRF", "train");
			if (pTrain==null) pTrain=false;
			pModelFile = (String) aContext.getConfigParameterValue( "CRF", "modelfile");
			if (pModelFile==null) pModelFile="train.mdl";
			pAnnotationCombination = (String[]) aContext.getConfigParameterValue( "CRF", "AnnotationCombination");
			if (pAnnotationCombination!=null)  AnnotationCombinationlist = new HashSet<String>(Arrays.asList(pAnnotationCombination));
			pRemovePrefix = (Boolean) aContext.getConfigParameterValue( "CRF", "RemovePrefix");
			if (pRemovePrefix==null) pRemovePrefix=false;
			datapath=aContext.getDataPath();
		} catch (Exception e) {
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter("c:\\logs\\processerror.txt"));
				
					writer.write("Error processing " + e.toString()+" ");
					StackTraceElement[] trace=e.getStackTrace();
					for (StackTraceElement elt:trace) {
						writer.write("Error processing " + elt.toString()+ " \n");
					}
					writer.close();
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			
		}
	}

	public void process(JCas aJCas) {
		try {
		logger.log(Level.INFO, "processing:");
		JCas lrw_view = aJCas;
		String views = "";
		try {
			Iterator<JCas> viewI = aJCas.getViewIterator();
			while (viewI.hasNext()) {
				lrw_view = viewI.next();
				views = views + "\n" + lrw_view.getViewName();
				if (lrw_view.getViewName().equals("lrw-view")) {
					break;
				} else {
					lrw_view = aJCas;
				}
			}
			System.out.println("View " + lrw_view.getViewName());
		} catch (CASException e) {

		}

		// The JCas object is the data object inside UIMA where all the
		// information is stored. It contains all annotations created by
		// previous annotators, and the document text to be analyzed.

		
		try {
			// Define file and paths variable names
			String CRFfile="";
			String Modelfile="";
			String Logfile="";
			String Outputfile="";
			String classpath="";	
			String path="";					
		    String[] dpaths=datapath.split(";");
			for (String dpath:dpaths) {
				if (dpath.endsWith("CRF")) {
					path=dpath;
				} else if (dpath.endsWith("lib")) {
					classpath=dpath;
				}
			}
			if (path.equals("")) {
				File fpath=new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());	
				
				path=fpath.getParentFile().getParent()+"\\Documents\\CRF";
				classpath=fpath.getParentFile().getParent()+"\\lib";
				File directory = new File(path);
			    if (! directory.exists()){
			        directory.mkdir();
			        // If you require it to make the entire directory path including parents,
			        // use directory.mkdirs(); here instead.
			    }
			}
			if (pTrain) CRFfile=path+"\\CRFtrain.txt";
			else CRFfile=path+"\\CRFtest.txt";
			Logfile=path+"\\log.txt";
			Outputfile=path+"\\output.txt";
			Modelfile=path+"\\"+pModelFile;
			// Get the span type for either sentence or paragraph
			TypeSystem typeSystem = lrw_view.getTypeSystem();			
			Type spant = typeSystem.getType(spanType);
			String spann = spant.getName();
			// get annotation iterator for this CAS on all annotations
			AnnotationIndex<Annotation> anIndex = lrw_view.getAnnotationIndex();
			FSIterator<Annotation> anIter = anIndex.iterator();

			// Define sets to locate the annotations for tokens and only keep annotations that have a Part-Of-Speech tagging "posTag"
			TreeSet<CRFAnnotationBegin> tokens = new TreeSet<CRFAnnotationBegin>();
			TreeSet<CRFAnnotationBegin> spanblock = new TreeSet<CRFAnnotationBegin>();
			TreeSet<CRFAnnotationBegin> otherbegin = new TreeSet<CRFAnnotationBegin>();
			TreeSet<CRFAnnotationEnd> otherend = new TreeSet<CRFAnnotationEnd>();
			while (anIter.isValid()) {
				Annotation annot = anIter.get();
				Type type = annot.getType();
				String typename = type.getName();
				Feature typefeature = type.getFeatureByBaseName("posTag");
				
				if (!ignoreAnnotationlist.contains(typename)) {
					if (typefeature!=null) {
						tokens.add(new CRFAnnotationBegin(annot));
					} else if (typename.equals(spann)) {
						spanblock.add(new CRFAnnotationBegin(annot));
					} else {
						otherbegin.add(new CRFAnnotationBegin(annot));
						otherend.add(new CRFAnnotationEnd(annot));
					}					
				}
				anIter.moveToNext();
			}
			Iterator<CRFAnnotationBegin> spaniter = spanblock.iterator();
			BufferedWriter writer = null;
			// Now iterate on paragraph or sentences and find tokens within annotations within that span
			try {								
			    writer = new BufferedWriter(new FileWriter(CRFfile));
				while (spaniter.hasNext()) {
					// Get the span block
					CRFAnnotationBegin block = spaniter.next();
					// Get tokens starting after the block beginning
					SortedSet<CRFAnnotationBegin> blocktokens = tokens.tailSet(block);
					// First create data for checking combination is present in block	
					// Create a checklist of annotations which combination is necessary for tagging.
					HashSet<String> templist=new HashSet<String>(AnnotationCombinationlist);					
					SortedSet<CRFAnnotationBegin> blockothers; // Other annotations in block
					Iterator<CRFAnnotationBegin> otheriter;
					int initsize=templist.size();		
					// Iterate tokens in sentence
					CRFAnnotationBegin token; // Current token
					Annotation tannot;        // Token annotation
					Iterator<CRFAnnotationBegin> tokiter = blocktokens.iterator();
					// Go through the checklist if  training phase and tagging is necessary
					if (pTrain) while (tokiter.hasNext()) {						
						token = tokiter.next();
						tannot = token.annot;
						// If beginning is after block end then we are done with tokens for this sentence or paragraph
						if (tannot.getBegin() > block.annot.getEnd()-1)
							break;
						blockothers = otherbegin.tailSet(block);
						otheriter = blockothers.iterator();
						// Get matching annotations for this token in sentence or paragraph					
						while (otheriter.hasNext()) {
							CRFAnnotationBegin other = otheriter.next();
							Annotation oannot = other.annot;
							if (oannot.getBegin() > tannot.getEnd()-1)
								break;
							if (oannot.getEnd() < tannot.getBegin())
								continue;
							// If one of the annotations required for tagging is found then remove it from the checklist
							if (templist.contains(oannot.getType().getName())) {
									templist.remove(oannot.getType().getName());
							}
						}
					}
				    // Now create the CRF line either for training or testing
					tokiter = blocktokens.iterator(); // Iterate on the tree that is sorted by start position
					while (tokiter.hasNext()) {
						token = tokiter.next();
						tannot = token.annot;
						// Stop when token are no more within block
						if (tannot.getBegin() > block.annot.getEnd()-1)
							break;
						// If token is within block
						Type type = tannot.getType();
						String text = tannot.getCoveredText();
						if (text != null && text.length() > 20) {
							text = text.substring(0, 20) + "...";
						}
						// Get token type
						Feature typefeature = type.getFeatureByBaseName("posTag");					
						String tpname=type.getName();
						
						if (pRemovePrefix) tpname=tpname.substring(tpname.lastIndexOf(".")+1);
						// Create training line buffer
						StringBuffer buffer = new StringBuffer();
						buffer.append(" Text_"+text+" "+tpname+" PosTag_"+tannot.getStringValue(typefeature));
						
						blockothers = otherbegin.tailSet(block);
						otheriter = blockothers.iterator();
						// Get tokens in sentence
						boolean tagline=false;
						while (otheriter.hasNext()) {
							CRFAnnotationBegin other = otheriter.next();
							Annotation oannot = other.annot;
							if (oannot.getBegin() > tannot.getEnd()-1)
								break;
							if (oannot.getEnd() < tannot.getBegin())
								continue;
							String tname=oannot.getType().getName();
							String sname=tname;
							if (pRemovePrefix) sname=tname.substring(tname.lastIndexOf(".")+1);
							else  sname=tname;
							buffer.append(" "+sname);
							if (AnnotationCombinationlist.contains(tname)) tagline=true;
						}
						if (pTrain) {
							if (tagline==true&&templist.size()==0&&initsize>0) buffer.append(" "+pLabel);
							else buffer.append(" .");
						}
						
						writer.write(buffer.toString());
						// Remember token location in a vector corresponding to train/test file index
						// Not putting in the file to limite feature data for CRF
						begins.add(tannot.getBegin()); 
						ends.add(tannot.getEnd());
						writer.write("\r\n");
					}
					writer.write("\r\n");
					// make sure Vector indexes are consistent with lines.
					begins.add(-1);
					ends.add(-1);
				}
			} catch (IOException ex) {
			} finally {
				try {
					if (writer != null)
						writer.close();
				} catch (IOException ex) {
				}
			}
			// Now launch Mallet CRF separately as it conflict with WCA studio Java heap.
			String javaHome = System.getProperty("java.home");
	        String javaBin = "\""+javaHome +
	                File.separator + "bin" +
	                File.separator + "java"+"\"";
			ProcessBuilder builder;
			if (pTrain)
				 builder = new ProcessBuilder(
	                javaBin, "-cp",classpath+File.separator+"mallet.jar;"+classpath+File.separator+"mallet-deps.jar;","cc.mallet.fst.SimpleTagger",
	                "--train","true","--model-file",Modelfile,CRFfile);
			else builder = new ProcessBuilder(
	                javaBin, "-cp",classpath+File.separator+"mallet.jar;"+classpath+File.separator+"mallet-deps.jar;","cc.mallet.fst.SimpleTagger",
	                "--model-file",Modelfile,"--include-input","true",CRFfile);
			File log = new File(Logfile);
			File output = new File(Outputfile);		
			builder.redirectOutput(output);
			builder.redirectError(log);
	        Process process = builder.start();
	        process.waitFor();
	        // If testing now read output file from CRF and create UIMA annotations
	        if (!pTrain) {
	        	BufferedReader outputreader;
	        	int lineidx=0;
	        	outputreader = new BufferedReader(new FileReader(Outputfile));
	        	String line = outputreader.readLine();
	        	while (line != null) {       		
	        		String[] tags=line.split("\\s+");
	        		// if a valid tag found by CRF
	        		if (!tags[0].equals("")&&!tags[0].equals(".")) {
	        			// Create a CRF annotation
	        			CRF annotation = new CRF(
								lrw_view);
	        			annotation.setBegin(begins.get(lineidx));
						annotation.setEnd(ends.get(lineidx));
						annotation.setTagname(tags[0]);
						if (tags.length>1) {
							StringArray sarr=new StringArray(aJCas,tags.length-1);							
							for (int i=1;i<tags.length;i++) {
								sarr.set(i-1,tags[i]);
							}
							annotation.setTaglist(sarr);
						}						
						annotation.addToIndexes();
			   		}
	        		line = outputreader.readLine();
	        		lineidx++;
	        	}
	         	outputreader.close();
	        }
		} catch (Exception e) {
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter("c:\\Logs\\processerror.txt"));
				writer.write("Error processing " + e.toString());
			} catch (IOException ex) {
			} finally {
				try {
					if (writer != null)
						writer.close();
				} catch (IOException ex) {
				}
			}
		}
		} catch (Exception ex) {
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter("c:\\logs\\processerror.txt"));
				
					writer.write("Error processing " + ex.toString()+" ");
					StackTraceElement[] trace=ex.getStackTrace();
					for (StackTraceElement elt:trace) {
						writer.write("Error processing " + elt.toString()+ " \n");
					}
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		}
	}
}
