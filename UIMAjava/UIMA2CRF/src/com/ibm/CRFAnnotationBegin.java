package com.ibm;

import org.apache.uima.jcas.tcas.Annotation;

public class CRFAnnotationBegin implements Comparable<CRFAnnotationBegin> {
	public Annotation annot = null;

	public CRFAnnotationBegin(Annotation _annot) {
		annot = _annot;
	}

	@Override
	public int compareTo(CRFAnnotationBegin o) {
		if (annot != null) {
			return annot.getBegin() - o.annot.getBegin();
		} else
			return 0;
	}

}