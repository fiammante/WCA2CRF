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

