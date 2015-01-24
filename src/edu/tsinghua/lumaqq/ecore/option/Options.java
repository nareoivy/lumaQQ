/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option;

import edu.tsinghua.lumaqq.ecore.LoginOption;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Options</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.Options#getLoginOption <em>Login Option</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.Options#getGuiOption <em>Gui Option</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.Options#getMessageOption <em>Message Option</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.Options#getOtherOption <em>Other Option</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.Options#getSyncOption <em>Sync Option</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.Options#getKeyOption <em>Key Option</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOptions()
 * @model
 * @generated
 */
public interface Options extends EObject {
	/**
	 * Returns the value of the '<em><b>Login Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Login Option</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Login Option</em>' containment reference.
	 * @see #setLoginOption(LoginOption)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOptions_LoginOption()
	 * @model containment="true" resolveProxies="false" required="true"
	 * @generated
	 */
	LoginOption getLoginOption();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.Options#getLoginOption <em>Login Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Login Option</em>' containment reference.
	 * @see #getLoginOption()
	 * @generated
	 */
	void setLoginOption(LoginOption value);

	/**
	 * Returns the value of the '<em><b>Gui Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gui Option</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gui Option</em>' containment reference.
	 * @see #setGuiOption(GUIOption)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOptions_GuiOption()
	 * @model containment="true" resolveProxies="false" required="true"
	 * @generated
	 */
	GUIOption getGuiOption();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.Options#getGuiOption <em>Gui Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gui Option</em>' containment reference.
	 * @see #getGuiOption()
	 * @generated
	 */
	void setGuiOption(GUIOption value);

	/**
	 * Returns the value of the '<em><b>Message Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message Option</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message Option</em>' containment reference.
	 * @see #setMessageOption(MessageOption)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOptions_MessageOption()
	 * @model containment="true" resolveProxies="false" required="true"
	 * @generated
	 */
	MessageOption getMessageOption();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.Options#getMessageOption <em>Message Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message Option</em>' containment reference.
	 * @see #getMessageOption()
	 * @generated
	 */
	void setMessageOption(MessageOption value);

	/**
	 * Returns the value of the '<em><b>Other Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Other Option</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Other Option</em>' containment reference.
	 * @see #setOtherOption(OtherOption)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOptions_OtherOption()
	 * @model containment="true" resolveProxies="false" required="true"
	 * @generated
	 */
	OtherOption getOtherOption();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.Options#getOtherOption <em>Other Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Other Option</em>' containment reference.
	 * @see #getOtherOption()
	 * @generated
	 */
	void setOtherOption(OtherOption value);

	/**
	 * Returns the value of the '<em><b>Sync Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sync Option</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sync Option</em>' containment reference.
	 * @see #setSyncOption(SyncOption)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOptions_SyncOption()
	 * @model containment="true" resolveProxies="false" required="true"
	 * @generated
	 */
	SyncOption getSyncOption();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.Options#getSyncOption <em>Sync Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sync Option</em>' containment reference.
	 * @see #getSyncOption()
	 * @generated
	 */
	void setSyncOption(SyncOption value);

	/**
	 * Returns the value of the '<em><b>Key Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key Option</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key Option</em>' containment reference.
	 * @see #setKeyOption(KeyOption)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOptions_KeyOption()
	 * @model containment="true" resolveProxies="false" required="true"
	 * @generated
	 */
	KeyOption getKeyOption();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.Options#getKeyOption <em>Key Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key Option</em>' containment reference.
	 * @see #getKeyOption()
	 * @generated
	 */
	void setKeyOption(KeyOption value);

} // Options
