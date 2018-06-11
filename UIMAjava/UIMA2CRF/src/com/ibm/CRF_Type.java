
/* First created by JCasGen Wed Jun 06 15:12:38 CEST 2018 */
package com.ibm;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Wed Jun 06 15:12:38 CEST 2018
 * @generated */
public class CRF_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = CRF.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ibm.CRF");
 
  /** @generated */
  final Feature casFeat_Taglist;
  /** @generated */
  final int     casFeatCode_Taglist;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTaglist(int addr) {
        if (featOkTst && casFeat_Taglist == null)
      jcas.throwFeatMissing("Taglist", "com.ibm.CRF");
    return ll_cas.ll_getRefValue(addr, casFeatCode_Taglist);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTaglist(int addr, int v) {
        if (featOkTst && casFeat_Taglist == null)
      jcas.throwFeatMissing("Taglist", "com.ibm.CRF");
    ll_cas.ll_setRefValue(addr, casFeatCode_Taglist, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getTaglist(int addr, int i) {
        if (featOkTst && casFeat_Taglist == null)
      jcas.throwFeatMissing("Taglist", "com.ibm.CRF");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_Taglist), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_Taglist), i);
	return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_Taglist), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setTaglist(int addr, int i, String v) {
        if (featOkTst && casFeat_Taglist == null)
      jcas.throwFeatMissing("Taglist", "com.ibm.CRF");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_Taglist), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_Taglist), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_Taglist), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_Tagname;
  /** @generated */
  final int     casFeatCode_Tagname;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTagname(int addr) {
        if (featOkTst && casFeat_Tagname == null)
      jcas.throwFeatMissing("Tagname", "com.ibm.CRF");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Tagname);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTagname(int addr, String v) {
        if (featOkTst && casFeat_Tagname == null)
      jcas.throwFeatMissing("Tagname", "com.ibm.CRF");
    ll_cas.ll_setStringValue(addr, casFeatCode_Tagname, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public CRF_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Taglist = jcas.getRequiredFeatureDE(casType, "Taglist", "uima.cas.StringArray", featOkTst);
    casFeatCode_Taglist  = (null == casFeat_Taglist) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Taglist).getCode();

 
    casFeat_Tagname = jcas.getRequiredFeatureDE(casType, "Tagname", "uima.cas.String", featOkTst);
    casFeatCode_Tagname  = (null == casFeat_Tagname) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Tagname).getCode();

  }
}



    