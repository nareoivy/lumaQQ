/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.global;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Setting</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getLanguage <em>Language</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getServers <em>Servers</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getRobots <em>Robots</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.global.GlobalPackage#getGlobalSetting()
 * @model
 * @generated
 */
public interface GlobalSetting extends EObject {
	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute.
	 * The default value is <code>"zh"</code>.
	 * The literals are from the enumeration {@link edu.tsinghua.lumaqq.ecore.global.LanguageType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Language</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see edu.tsinghua.lumaqq.ecore.global.LanguageType
	 * @see #isSetLanguage()
	 * @see #unsetLanguage()
	 * @see #setLanguage(LanguageType)
	 * @see edu.tsinghua.lumaqq.ecore.global.GlobalPackage#getGlobalSetting_Language()
	 * @model default="zh" unique="false" unsettable="true" required="true"
	 * @generated
	 */
	LanguageType getLanguage();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see edu.tsinghua.lumaqq.ecore.global.LanguageType
	 * @see #isSetLanguage()
	 * @see #unsetLanguage()
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(LanguageType value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLanguage()
	 * @see #getLanguage()
	 * @see #setLanguage(LanguageType)
	 * @generated
	 */
	void unsetLanguage();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getLanguage <em>Language</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Language</em>' attribute is set.
	 * @see #unsetLanguage()
	 * @see #getLanguage()
	 * @see #setLanguage(LanguageType)
	 * @generated
	 */
	boolean isSetLanguage();

	/**
	 * Returns the value of the '<em><b>Servers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Servers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Servers</em>' containment reference.
	 * @see #setServers(Servers)
	 * @see edu.tsinghua.lumaqq.ecore.global.GlobalPackage#getGlobalSetting_Servers()
	 * @model containment="true" resolveProxies="false" required="true"
	 * @generated
	 */
	Servers getServers();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getServers <em>Servers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Servers</em>' containment reference.
	 * @see #getServers()
	 * @generated
	 */
	void setServers(Servers value);

	/**
	 * Returns the value of the '<em><b>Robots</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Robots</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Robots</em>' containment reference.
	 * @see #setRobots(Robots)
	 * @see edu.tsinghua.lumaqq.ecore.global.GlobalPackage#getGlobalSetting_Robots()
	 * @model containment="true" resolveProxies="false" required="true"
	 * @generated
	 */
	Robots getRobots();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getRobots <em>Robots</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Robots</em>' containment reference.
	 * @see #getRobots()
	 * @generated
	 */
	void setRobots(Robots value);

} // GlobalSetting
