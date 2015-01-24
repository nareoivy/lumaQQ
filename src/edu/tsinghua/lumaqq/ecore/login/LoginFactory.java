/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.login;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage
 * @generated
 */
public interface LoginFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LoginFactory eINSTANCE = edu.tsinghua.lumaqq.ecore.login.impl.LoginFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Login</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Login</em>'.
	 * @generated
	 */
	Login createLogin();

	/**
	 * Returns a new object of class '<em>Logins</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Logins</em>'.
	 * @generated
	 */
	Logins createLogins();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	LoginPackage getLoginPackage();

} //LoginFactory
