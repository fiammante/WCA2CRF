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

import org.apache.uima.jcas.tcas.Annotation;

public class CRFAnnotationEnd implements Comparable<CRFAnnotationEnd> {
	public Annotation annot = null;

	public CRFAnnotationEnd(Annotation _annot) {
		annot = _annot;
	}

	@Override
	public int compareTo(CRFAnnotationEnd o) {
		if (annot != null) {
			return annot.getEnd() - o.annot.getEnd();
		} else
			return 0;
	}

}
