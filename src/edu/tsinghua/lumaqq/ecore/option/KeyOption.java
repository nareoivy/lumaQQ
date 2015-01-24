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
 * A representation of the model object '<em><b>Key Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.KeyOption#getMessageKey <em>Message Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getKeyOption()
 * @model
 * @generated
 */
public interface KeyOption extends EObject {
	/**
	 * Returns the value of the '<em><b>Message Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message Key</em>' attribute.
	 * @see #setMessageKey(String)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getKeyOption_MessageKey()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getMessageKey();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.KeyOption#getMessageKey <em>Message Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message Key</em>' attribute.
	 * @see #getMessageKey()
	 * @generated
	 */
	void setMessageKey(String value);

} // KeyOption
