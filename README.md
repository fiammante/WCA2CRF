# UIMA2CRF 

UIMACRF Is a UIMA plugin for Watson Content Analytics that use Mallet Conditional Random Field to train on existing documents and then create annotations for new documents using Mallet machine learning http://mallet.cs.umass.edu/ 
The source code for the UIMA Custom plugin is in the UIMA2CRF Folder which contains the Eclipse project export.
The component for WCA studio are in the WCA folder. Add these components to the initial WCA Studio IBM project that contains police reports. 
Click on the configuration and edit the custom component in the pipeline.  Select train to true on the document. The custom components look for combination of annotations in a sentence or a paragraph (Carplate and Carstate in this case). Select the appropriate span in the customization.
![picture](Screenshots/customconf.png)
Use https://github.ibm.com/marc-fiammante/UIMA2CRF/blob/master/WCA/train.txt as the first document and run analyze document.
A CRFtrain.txt file is created see https://github.ibm.com/marc-fiammante/UIMA2CRF/blob/master/WCA/CRF/CRFtrain.txt. The machine learning model file is created in the same folder with the name given in the same folder, do not edit it.
The log result of the the Mallet CRF training is captured in the file log.txt. 
Then switch to the test.txt document and in the custom component configuration switch from train true to false. 
Analyze the test.txt document and you will see on the right that a com.ibm.CRF annotation is created with the combinations found by mallet CRF. 
The result of mallet Simple tagger process is captured in this file https://github.ibm.com/marc-fiammante/UIMA2CRF/blob/master/WCA/CRF/output.txt which is processed by the plugin to create the annotation in WCA.
![picture](Screenshots/testresult.png)
