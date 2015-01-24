/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.login;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Login</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.Login#isAutoLogin <em>Auto Login</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.Login#isLoginHidden <em>Login Hidden</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.Login#getPassword <em>Password</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.Login#getQq <em>Qq</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.Login#isRememberPassword <em>Remember Password</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#getLogin()
 * @model
 * @generated
 */
public interface Login extends EObject {
	/**
	 * Returns the value of the '<em><b>Auto Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Login</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Login</em>' attribute.
	 * @see #isSetAutoLogin()
	 * @see #unsetAutoLogin()
	 * @see #setAutoLogin(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#getLogin_AutoLogin()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isAutoLogin();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#isAutoLogin <em>Auto Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Login</em>' attribute.
	 * @see #isSetAutoLogin()
	 * @see #unsetAutoLogin()
	 * @see #isAutoLogin()
	 * @generated
	 */
	void setAutoLogin(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#isAutoLogin <em>Auto Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAutoLogin()
	 * @see #isAutoLogin()
	 * @see #setAutoLogin(boolean)
	 * @generated
	 */
	void unsetAutoLogin();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#isAutoLogin <em>Auto Login</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Auto Login</em>' attribute is set.
	 * @see #unsetAutoLogin()
	 * @see #isAutoLogin()
	 * @see #setAutoLogin(boolean)
	 * @generated
	 */
	boolean isSetAutoLogin();

	/**
	 * Returns the value of the '<em><b>Login Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Login Hidden</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Login Hidden</em>' attribute.
	 * @see #isSetLoginHidden()
	 * @see #unsetLoginHidden()
	 * @see #setLoginHidden(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#getLogin_LoginHidden()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isLoginHidden();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#isLoginHidden <em>Login Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Login Hidden</em>' attribute.
	 * @see #isSetLoginHidden()
	 * @see #unsetLoginHidden()
	 * @see #isLoginHidden()
	 * @generated
	 */
	void setLoginHidden(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#isLoginHidden <em>Login Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLoginHidden()
	 * @see #isLoginHidden()
	 * @see #setLoginHidden(boolean)
	 * @generated
	 */
	void unsetLoginHidden();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#isLoginHidden <em>Login Hidden</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Login Hidden</em>' attribute is set.
	 * @see #unsetLoginHidden()
	 * @see #isLoginHidden()
	 * @see #setLoginHidden(boolean)
	 * @generated
	 */
	boolean isSetLoginHidden();

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#getLogin_Password()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>Qq</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qq</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qq</em>' attribute.
	 * @see #setQq(String)
	 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#getLogin_Qq()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getQq();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#getQq <em>Qq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qq</em>' attribute.
	 * @see #getQq()
	 * @generated
	 */
	void setQq(String value);

	/**
	 * Returns the value of the '<em><b>Remember Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remember Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remember Password</em>' attribute.
	 * @see #isSetRememberPassword()
	 * @see #unsetRememberPassword()
	 * @see #setRememberPassword(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#getLogin_RememberPassword()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isRememberPassword();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#isRememberPassword <em>Remember Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remember Password</em>' attribute.
	 * @see #isSetRememberPassword()
	 * @see #unsetRememberPassword()
	 * @see #isRememberPassword()
	 * @generated
	 */
	void setRememberPassword(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#isRememberPassword <em>Remember Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRememberPassword()
	 * @see #isRememberPassword()
	 * @see #setRememberPassword(boolean)
	 * @generated
	 */
	void unsetRememberPassword();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.login.Login#isRememberPassword <em>Remember Password</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Remember Password</em>' attribute is set.
	 * @see #unsetRememberPassword()
	 * @see #isRememberPassword()
	 * @see #setRememberPassword(boolean)
	 * @generated
	 */
	boolean isSetRememberPassword();

} // Login
