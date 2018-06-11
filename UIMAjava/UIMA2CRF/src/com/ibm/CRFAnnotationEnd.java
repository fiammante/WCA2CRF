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