/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Other Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#getBrowser <em>Browser</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isEnableLatest <em>Enable Latest</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isKeepStrangerInLatest <em>Keep Stranger In Latest</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#getLatestSize <em>Latest Size</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isShowFakeCam <em>Show Fake Cam</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOtherOption()
 * @model
 * @generated
 */
public interface OtherOption extends EObject {
	/**
	 * Returns the value of the '<em><b>Browser</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Browser</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Browser</em>' attribute.
	 * @see #setBrowser(String)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOtherOption_Browser()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getBrowser();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#getBrowser <em>Browser</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Browser</em>' attribute.
	 * @see #getBrowser()
	 * @generated
	 */
	void setBrowser(String value);

	/**
	 * Returns the value of the '<em><b>Enable Latest</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable Latest</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Latest</em>' attribute.
	 * @see #isSetEnableLatest()
	 * @see #unsetEnableLatest()
	 * @see #setEnableLatest(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOtherOption_EnableLatest()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isEnableLatest();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isEnableLatest <em>Enable Latest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Latest</em>' attribute.
	 * @see #isSetEnableLatest()
	 * @see #unsetEnableLatest()
	 * @see #isEnableLatest()
	 * @generated
	 */
	void setEnableLatest(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isEnableLatest <em>Enable Latest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetEnableLatest()
	 * @see #isEnableLatest()
	 * @see #setEnableLatest(boolean)
	 * @generated
	 */
	void unsetEnableLatest();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isEnableLatest <em>Enable Latest</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Enable Latest</em>' attribute is set.
	 * @see #unsetEnableLatest()
	 * @see #isEnableLatest()
	 * @see #setEnableLatest(boolean)
	 * @generated
	 */
	boolean isSetEnableLatest();

	/**
	 * Returns the value of the '<em><b>Keep Stranger In Latest</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Keep Stranger In Latest</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Keep Stranger In Latest</em>' attribute.
	 * @see #isSetKeepStrangerInLatest()
	 * @see #unsetKeepStrangerInLatest()
	 * @see #setKeepStrangerInLatest(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOtherOption_KeepStrangerInLatest()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isKeepStrangerInLatest();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isKeepStrangerInLatest <em>Keep Stranger In Latest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Keep Stranger In Latest</em>' attribute.
	 * @see #isSetKeepStrangerInLatest()
	 * @see #unsetKeepStrangerInLatest()
	 * @see #isKeepStrangerInLatest()
	 * @generated
	 */
	void setKeepStrangerInLatest(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isKeepStrangerInLatest <em>Keep Stranger In Latest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetKeepStrangerInLatest()
	 * @see #isKeepStrangerInLatest()
	 * @see #setKeepStrangerInLatest(boolean)
	 * @generated
	 */
	void unsetKeepStrangerInLatest();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isKeepStrangerInLatest <em>Keep Stranger In Latest</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Keep Stranger In Latest</em>' attribute is set.
	 * @see #unsetKeepStrangerInLatest()
	 * @see #isKeepStrangerInLatest()
	 * @see #setKeepStrangerInLatest(boolean)
	 * @generated
	 */
	boolean isSetKeepStrangerInLatest();

	/**
	 * Returns the value of the '<em><b>Latest Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Latest Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Latest Size</em>' attribute.
	 * @see #isSetLatestSize()
	 * @see #unsetLatestSize()
	 * @see #setLatestSize(int)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOtherOption_LatestSize()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getLatestSize();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#getLatestSize <em>Latest Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Latest Size</em>' attribute.
	 * @see #isSetLatestSize()
	 * @see #unsetLatestSize()
	 * @see #getLatestSize()
	 * @generated
	 */
	void setLatestSize(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#getLatestSize <em>Latest Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLatestSize()
	 * @see #getLatestSize()
	 * @see #setLatestSize(int)
	 * @generated
	 */
	void unsetLatestSize();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#getLatestSize <em>Latest Size</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Latest Size</em>' attribute is set.
	 * @see #unsetLatestSize()
	 * @see #getLatestSize()
	 * @see #setLatestSize(int)
	 * @generated
	 */
	boolean isSetLatestSize();

	/**
	 * Returns the value of the '<em><b>Show Fake Cam</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Fake Cam</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Fake Cam</em>' attribute.
	 * @see #isSetShowFakeCam()
	 * @see #unsetShowFakeCam()
	 * @see #setShowFakeCam(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOtherOption_ShowFakeCam()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowFakeCam();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isShowFakeCam <em>Show Fake Cam</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Fake Cam</em>' attribute.
	 * @see #isSetShowFakeCam()
	 * @see #unsetShowFakeCam()
	 * @see #isShowFakeCam()
	 * @generated
	 */
	void setShowFakeCam(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isShowFakeCam <em>Show Fake Cam</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowFakeCam()
	 * @see #isShowFakeCam()
	 * @see #setShowFakeCam(boolean)
	 * @generated
	 */
	void unsetShowFakeCam();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isShowFakeCam <em>Show Fake Cam</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Fake Cam</em>' attribute is set.
	 * @see #unsetShowFakeCam()
	 * @see #isShowFakeCam()
	 * @see #setShowFakeCam(boolean)
	 * @generated
	 */
	boolean isSetShowFakeCam();

} // OtherOption
