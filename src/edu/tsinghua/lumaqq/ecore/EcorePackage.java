/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

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
 * @see edu.tsinghua.lumaqq.ecore.EcoreFactory
 * @model kind="package"
 * @generated
 */
public interface EcorePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "ecore";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ecore";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EcorePackage eINSTANCE = edu.tsinghua.lumaqq.ecore.impl.EcorePackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl <em>Login Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl
	 * @see edu.tsinghua.lumaqq.ecore.impl.EcorePackageImpl#getLoginOption()
	 * @generated
	 */
	int LOGIN_OPTION = 0;

	/**
	 * The feature id for the '<em><b>Auto Select</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_OPTION__AUTO_SELECT = 0;

	/**
	 * The feature id for the '<em><b>Proxy Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_OPTION__PROXY_PASSWORD = 1;

	/**
	 * The feature id for the '<em><b>Proxy Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_OPTION__PROXY_PORT = 2;

	/**
	 * The feature id for the '<em><b>Proxy Server</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_OPTION__PROXY_SERVER = 3;

	/**
	 * The feature id for the '<em><b>Proxy Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_OPTION__PROXY_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Proxy Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_OPTION__PROXY_USERNAME = 5;

	/**
	 * The feature id for the '<em><b>Server</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_OPTION__SERVER = 6;

	/**
	 * The feature id for the '<em><b>Tcp Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_OPTION__TCP_PORT = 7;

	/**
	 * The feature id for the '<em><b>Use Tcp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_OPTION__USE_TCP = 8;

	/**
	 * The number of structural features of the '<em>Login Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_OPTION_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.ProxyType <em>Proxy Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.ProxyType
	 * @see edu.tsinghua.lumaqq.ecore.impl.EcorePackageImpl#getProxyType()
	 * @generated
	 */
	int PROXY_TYPE = 1;


	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.LoginOption <em>Login Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Login Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.LoginOption
	 * @generated
	 */
	EClass getLoginOption();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.LoginOption#isAutoSelect <em>Auto Select</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Select</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.LoginOption#isAutoSelect()
	 * @see #getLoginOption()
	 * @generated
	 */
	EAttribute getLoginOption_AutoSelect();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyPassword <em>Proxy Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Proxy Password</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.LoginOption#getProxyPassword()
	 * @see #getLoginOption()
	 * @generated
	 */
	EAttribute getLoginOption_ProxyPassword();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyPort <em>Proxy Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Proxy Port</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.LoginOption#getProxyPort()
	 * @see #getLoginOption()
	 * @generated
	 */
	EAttribute getLoginOption_ProxyPort();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyServer <em>Proxy Server</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Proxy Server</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.LoginOption#getProxyServer()
	 * @see #getLoginOption()
	 * @generated
	 */
	EAttribute getLoginOption_ProxyServer();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyType <em>Proxy Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Proxy Type</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.LoginOption#getProxyType()
	 * @see #getLoginOption()
	 * @generated
	 */
	EAttribute getLoginOption_ProxyType();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyUsername <em>Proxy Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Proxy Username</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.LoginOption#getProxyUsername()
	 * @see #getLoginOption()
	 * @generated
	 */
	EAttribute getLoginOption_ProxyUsername();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getServer <em>Server</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Server</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.LoginOption#getServer()
	 * @see #getLoginOption()
	 * @generated
	 */
	EAttribute getLoginOption_Server();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getTcpPort <em>Tcp Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tcp Port</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.LoginOption#getTcpPort()
	 * @see #getLoginOption()
	 * @generated
	 */
	EAttribute getLoginOption_TcpPort();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.LoginOption#isUseTcp <em>Use Tcp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Tcp</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.LoginOption#isUseTcp()
	 * @see #getLoginOption()
	 * @generated
	 */
	EAttribute getLoginOption_UseTcp();

	/**
	 * Returns the meta object for enum '{@link edu.tsinghua.lumaqq.ecore.ProxyType <em>Proxy Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Proxy Type</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.ProxyType
	 * @generated
	 */
	EEnum getProxyType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EcoreFactory getEcoreFactory();

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
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl <em>Login Option</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl
		 * @see edu.tsinghua.lumaqq.ecore.impl.EcorePackageImpl#getLoginOption()
		 * @generated
		 */
		EClass LOGIN_OPTION = eINSTANCE.getLoginOption();

		/**
		 * The meta object literal for the '<em><b>Auto Select</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN_OPTION__AUTO_SELECT = eINSTANCE.getLoginOption_AutoSelect();

		/**
		 * The meta object literal for the '<em><b>Proxy Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN_OPTION__PROXY_PASSWORD = eINSTANCE.getLoginOption_ProxyPassword();

		/**
		 * The meta object literal for the '<em><b>Proxy Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN_OPTION__PROXY_PORT = eINSTANCE.getLoginOption_ProxyPort();

		/**
		 * The meta object literal for the '<em><b>Proxy Server</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN_OPTION__PROXY_SERVER = eINSTANCE.getLoginOption_ProxyServer();

		/**
		 * The meta object literal for the '<em><b>Proxy Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN_OPTION__PROXY_TYPE = eINSTANCE.getLoginOption_ProxyType();

		/**
		 * The meta object literal for the '<em><b>Proxy Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN_OPTION__PROXY_USERNAME = eINSTANCE.getLoginOption_ProxyUsername();

		/**
		 * The meta object literal for the '<em><b>Server</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN_OPTION__SERVER = eINSTANCE.getLoginOption_Server();

		/**
		 * The meta object literal for the '<em><b>Tcp Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN_OPTION__TCP_PORT = eINSTANCE.getLoginOption_TcpPort();

		/**
		 * The meta object literal for the '<em><b>Use Tcp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGIN_OPTION__USE_TCP = eINSTANCE.getLoginOption_UseTcp();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.ProxyType <em>Proxy Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.ProxyType
		 * @see edu.tsinghua.lumaqq.ecore.impl.EcorePackageImpl#getProxyType()
		 * @generated
		 */
		EEnum PROXY_TYPE = eINSTANCE.getProxyType();

	}

} //EcorePackage
