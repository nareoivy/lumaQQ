/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.login;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.login.LoginFactory
 * @model kind="package"
 * @generated
 */
public interface LoginPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "login";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore/login";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "login";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LoginPackage eINSTANCE = edu.tsinghua.lumaqq.ecore.login.impl.LoginPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginImpl <em>Login</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.login.impl.LoginImpl
	 * @see edu.tsinghua.lumaqq.ecore.login.impl.LoginPackageImpl#getLogin()
	 * @generated
	 */
	int LOGIN = 0;

	/**
	 * The feature id for the '<em><b>Auto Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__AUTO_LOGIN = 0;

	/**
	 * The feature id for the '<em><b>Login Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__LOGIN_HIDDEN = 1;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__PASSWORD = 2;

	/**
	 * The feature id for the '<em><b>Qq</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__QQ = 3;

	/**
	 * The feature id for the '<em><b>Remember Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__REMEMBER_PASSWORD = 4;

	/**
	 * The number of structural features of the '<em>Login</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginsImpl <em>Logins</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.login.impl.LoginsImpl
	 * @see edu.tsinghua.lumaqq.ecore.login.impl.LoginPackageImpl#getLogins()
	 * @generated
	 */
	int LOGINS = 1;

	/**
	 * The feature id for the '<em><b>Login</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGINS__LOGIN = 0;

	/**
	 * The feature id for the '<em><b>Last Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGINS__LAST_LOGIN = 1;

	/**
	 * The feature id for the '<em><b>Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGINS__NETWORK = 2;

	/**
	 * The number of structural features of the '<em>Logins</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGINS_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.login.Login <em>Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Login</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.login.Login
	 * @generated
	 */
	EClass getLogin();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.login.Login#isAutoLogin <em>Auto Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Login</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.login.Login#isAutoLogin()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_AutoLogin();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.login.Login#isLoginHidden <em>Login Hidden</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Login Hidden</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.login.Login#isLoginHidden()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_LoginHidden();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.login.Login#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.login.Login#getPassword()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_Password();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.login.Login#getQq <em>Qq</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qq</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.login.Login#getQq()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_Qq();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.login.Login#isRememberPassword <em>Remember Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remember Password</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.login.Login#isRememberPassword()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_RememberPassword();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.login.Logins <em>Logins</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Logins</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.login.Logins
	 * @generated
	 */
	EClass getLogins();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.login.Logins#getLogin <em>Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Login</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.login.Logins#getLogin()
	 * @see #getLogins()
	 * @generated
	 */
	EReference getLogins_Login();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.login.Logins#getLastLogin <em>Last Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Login</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.login.Logins#getLastLogin()
	 * @see #getLogins()
	 * @generated
	 */
	EAttribute getLogins_LastLogin();

	/**
	 * Returns the meta object for the containment reference '{@link edu.tsinghua.lumaqq.ecore.login.Logins#getNetwork <em>Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Network</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.login.Logins#getNetwork()
	 * @see #getLogins()
	 * @generated
	 */
	EReference getLogins_Network();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LoginFactory getLoginFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals  {
		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginImpl <em>Login</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.login.impl.LoginImpl
		 * @see edu.tsinghua.lumaqq.ecore.login.impl.LoginPackageImpl#getLogin()
		 * @generated
		 */
		EClass LOGIN = eINSTANCE.getLogin();

		/**
		 * The meta object literal for the '<em><b>Auto Login</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN__AUTO_LOGIN = eINSTANCE.getLogin_AutoLogin();

		/**
		 * The meta object literal for the '<em><b>Login Hidden</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN__LOGIN_HIDDEN = eINSTANCE.getLogin_LoginHidden();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN__PASSWORD = eINSTANCE.getLogin_Password();

		/**
		 * The meta object literal for the '<em><b>Qq</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN__QQ = eINSTANCE.getLogin_Qq();

		/**
		 * The meta object literal for the '<em><b>Remember Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN__REMEMBER_PASSWORD = eINSTANCE.getLogin_RememberPassword();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginsImpl <em>Logins</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.login.impl.LoginsImpl
		 * @see edu.tsinghua.lumaqq.ecore.login.impl.LoginPackageImpl#getLogins()
		 * @generated
		 */
		EClass LOGINS = eINSTANCE.getLogins();

		/**
		 * The meta object literal for the '<em><b>Login</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOGINS__LOGIN = eINSTANCE.getLogins_Login();

		/**
		 * The meta object literal for the '<em><b>Last Login</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGINS__LAST_LOGIN = eINSTANCE.getLogins_LastLogin();

		/**
		 * The meta object literal for the '<em><b>Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOGINS__NETWORK = eINSTANCE.getLogins_Network();

	}

} //LoginPackage
