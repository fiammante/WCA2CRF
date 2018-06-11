package com.ibm;

import java.io.FileInputStream;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.impl.XmiCasDeserializer;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

public class StartCRFAnnotation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JCas jCas;
		try {
			jCas = getJCas(args); // get the XMI 
			AnalysisEngine tokenizer = AnalysisEngineFactory.createEngineFromPath(args[1]); // Get the descriptor
			runPipeline(jCas, tokenizer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static JCas getJCas(String[] args)
	        throws Exception
	{
	   	
	   
	    	FileInputStream inputStream = new FileInputStream(args[0]);
	
	        JCas jCas = JCasFactory.createJCas();
	        XmiCasDeserializer.deserialize(inputStream, jCas.getCas());

	        return jCas;
	    	    
	}

}

