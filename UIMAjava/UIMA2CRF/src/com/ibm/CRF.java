

/* First created by JCasGen Wed Jun 06 15:12:38 CEST 2018 */
package com.ibm;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Jun 06 15:12:38 CEST 2018
 * XML source: C:/Users/MarcFiammante/eclipse-workspace/UIMA2CRF/desc/aeDescriptor.xml
 * @generated */
public class CRF extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(CRF.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected CRF() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public CRF(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public CRF(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public CRF(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: Taglist

  /** getter for Taglist - gets 
   * @generated
   * @return value of the feature 
   */
  public StringArray getTaglist() {
    if (CRF_Type.featOkTst && ((CRF_Type)jcasType).casFeat_Taglist == null)
      jcasType.jcas.throwFeatMissing("Taglist", "com.ibm.CRF");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CRF_Type)jcasType).casFeatCode_Taglist)));}
    
  /** setter for Taglist - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTaglist(StringArray v) {
    if (CRF_Type.featOkTst && ((CRF_Type)jcasType).casFeat_Taglist == null)
      jcasType.jcas.throwFeatMissing("Taglist", "com.ibm.CRF");
    jcasType.ll_cas.ll_setRefValue(addr, ((CRF_Type)jcasType).casFeatCode_Taglist, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for Taglist - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getTaglist(int i) {
    if (CRF_Type.featOkTst && ((CRF_Type)jcasType).casFeat_Taglist == null)
      jcasType.jcas.throwFeatMissing("Taglist", "com.ibm.CRF");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((CRF_Type)jcasType).casFeatCode_Taglist), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((CRF_Type)jcasType).casFeatCode_Taglist), i);}

  /** indexed setter for Taglist - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setTaglist(int i, String v) { 
    if (CRF_Type.featOkTst && ((CRF_Type)jcasType).casFeat_Taglist == null)
      jcasType.jcas.throwFeatMissing("Taglist", "com.ibm.CRF");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((CRF_Type)jcasType).casFeatCode_Taglist), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((CRF_Type)jcasType).casFeatCode_Taglist), i, v);}
   
    
  //*--------------*
  //* Feature: Tagname

  /** getter for Tagname - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTagname() {
    if (CRF_Type.featOkTst && ((CRF_Type)jcasType).casFeat_Tagname == null)
      jcasType.jcas.throwFeatMissing("Tagname", "com.ibm.CRF");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CRF_Type)jcasType).casFeatCode_Tagname);}
    
  /** setter for Tagname - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTagname(String v) {
    if (CRF_Type.featOkTst && ((CRF_Type)jcasType).casFeat_Tagname == null)
      jcasType.jcas.throwFeatMissing("Tagname", "com.ibm.CRF");
    jcasType.ll_cas.ll_setStringValue(addr, ((CRF_Type)jcasType).casFeatCode_Tagname, v);}    
  }

    