/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.login;

import edu.tsinghua.lumaqq.ecore.LoginOption;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Logins</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.Logins#getLogin <em>Login</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.Logins#getLastLogin <em>Last Login</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.Logins#getNetwork <em>Network</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#getLogins()
 * @model
 * @generated
 */
public interface Logins extends EObject {
	/**
	 * Returns the value of the '<em><b>Login</b></em>' containment reference list.
	 * The list contents are of type {@link edu.tsinghua.lumaqq.ecore.login.Login}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Login</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Login</em>' containment reference list.
	 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#getLogins_Login()
	 * @model type="edu.tsinghua.lumaqq.ecore.login.Login" containment="true" resolveProxies="false"
	 * @generated
	 */
	EList getLogin();

	/**
	 * Returns the value of the '<em><b>Last Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Login</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Login</em>' attribute.
	 * @see #setLastLogin(String)
	 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#getLogins_LastLogin()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getLastLogin();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Logins#getLastLogin <em>Last Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Login</em>' attribute.
	 * @see #getLastLogin()
	 * @generated
	 */
	void setLastLogin(String value);

	/**
	 * Returns the value of the '<em><b>Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Network</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Network</em>' containment reference.
	 * @see #setNetwork(LoginOption)
	 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#getLogins_Network()
	 * @model containment="true" required="true"
	 * @generated
	 */
	LoginOption getNetwork();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Logins#getNetwork <em>Network</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Network</em>' containment reference.
	 * @see #getNetwork()
	 * @generated
	 */
	void setNetwork(LoginOption value);

} // Logins
