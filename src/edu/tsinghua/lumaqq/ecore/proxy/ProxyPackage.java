/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.proxy;

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
 * @see edu.tsinghua.lumaqq.ecore.proxy.ProxyFactory
 * @model kind="package"
 * @generated
 */
public interface ProxyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "proxy";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore/proxy";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "proxy";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProxyPackage eINSTANCE = edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.proxy.impl.HttpProxyImpl <em>Http Proxy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.HttpProxyImpl
	 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyPackageImpl#getHttpProxy()
	 * @generated
	 */
	int HTTP_PROXY = 0;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_PROXY__PASSWORD = 0;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_PROXY__PORT = 1;

	/**
	 * The feature id for the '<em><b>Server</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_PROXY__SERVER = 2;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_PROXY__USERNAME = 3;

	/**
	 * The number of structural features of the '<em>Http Proxy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_PROXY_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.proxy.impl.ProxiesImpl <em>Proxies</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.ProxiesImpl
	 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyPackageImpl#getProxies()
	 * @generated
	 */
	int PROXIES = 1;

	/**
	 * The feature id for the '<em><b>Socks5 Proxy</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXIES__SOCKS5_PROXY = 0;

	/**
	 * The feature id for the '<em><b>Http Proxy</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXIES__HTTP_PROXY = 1;

	/**
	 * The number of structural features of the '<em>Proxies</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXIES_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.proxy.impl.Socks5ProxyImpl <em>Socks5 Proxy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.Socks5ProxyImpl
	 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyPackageImpl#getSocks5Proxy()
	 * @generated
	 */
	int SOCKS5_PROXY = 2;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCKS5_PROXY__PASSWORD = 0;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCKS5_PROXY__PORT = 1;

	/**
	 * The feature id for the '<em><b>Server</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCKS5_PROXY__SERVER = 2;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCKS5_PROXY__USERNAME = 3;

	/**
	 * The number of structural features of the '<em>Socks5 Proxy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCKS5_PROXY_FEATURE_COUNT = 4;


	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.proxy.HttpProxy <em>Http Proxy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Http Proxy</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.HttpProxy
	 * @generated
	 */
	EClass getHttpProxy();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.proxy.HttpProxy#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.HttpProxy#getPassword()
	 * @see #getHttpProxy()
	 * @generated
	 */
	EAttribute getHttpProxy_Password();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.proxy.HttpProxy#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.HttpProxy#getPort()
	 * @see #getHttpProxy()
	 * @generated
	 */
	EAttribute getHttpProxy_Port();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.proxy.HttpProxy#getServer <em>Server</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Server</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.HttpProxy#getServer()
	 * @see #getHttpProxy()
	 * @generated
	 */
	EAttribute getHttpProxy_Server();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.proxy.HttpProxy#getUsername <em>Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.HttpProxy#getUsername()
	 * @see #getHttpProxy()
	 * @generated
	 */
	EAttribute getHttpProxy_Username();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.proxy.Proxies <em>Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proxies</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.Proxies
	 * @generated
	 */
	EClass getProxies();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.proxy.Proxies#getSocks5Proxy <em>Socks5 Proxy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Socks5 Proxy</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.Proxies#getSocks5Proxy()
	 * @see #getProxies()
	 * @generated
	 */
	EReference getProxies_Socks5Proxy();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.proxy.Proxies#getHttpProxy <em>Http Proxy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Http Proxy</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.Proxies#getHttpProxy()
	 * @see #getProxies()
	 * @generated
	 */
	EReference getProxies_HttpProxy();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy <em>Socks5 Proxy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Socks5 Proxy</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy
	 * @generated
	 */
	EClass getSocks5Proxy();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy#getPassword()
	 * @see #getSocks5Proxy()
	 * @generated
	 */
	EAttribute getSocks5Proxy_Password();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy#getPort()
	 * @see #getSocks5Proxy()
	 * @generated
	 */
	EAttribute getSocks5Proxy_Port();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy#getServer <em>Server</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Server</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy#getServer()
	 * @see #getSocks5Proxy()
	 * @generated
	 */
	EAttribute getSocks5Proxy_Server();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy#getUsername <em>Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy#getUsername()
	 * @see #getSocks5Proxy()
	 * @generated
	 */
	EAttribute getSocks5Proxy_Username();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProxyFactory getProxyFactory();

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
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.proxy.impl.HttpProxyImpl <em>Http Proxy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.HttpProxyImpl
		 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyPackageImpl#getHttpProxy()
		 * @generated
		 */
		EClass HTTP_PROXY = eINSTANCE.getHttpProxy();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HTTP_PROXY__PASSWORD = eINSTANCE.getHttpProxy_Password();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HTTP_PROXY__PORT = eINSTANCE.getHttpProxy_Port();

		/**
		 * The meta object literal for the '<em><b>Server</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HTTP_PROXY__SERVER = eINSTANCE.getHttpProxy_Server();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HTTP_PROXY__USERNAME = eINSTANCE.getHttpProxy_Username();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.proxy.impl.ProxiesImpl <em>Proxies</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.ProxiesImpl
		 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyPackageImpl#getProxies()
		 * @generated
		 */
		EClass PROXIES = eINSTANCE.getProxies();

		/**
		 * The meta object literal for the '<em><b>Socks5 Proxy</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROXIES__SOCKS5_PROXY = eINSTANCE.getProxies_Socks5Proxy();

		/**
		 * The meta object literal for the '<em><b>Http Proxy</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROXIES__HTTP_PROXY = eINSTANCE.getProxies_HttpProxy();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.proxy.impl.Socks5ProxyImpl <em>Socks5 Proxy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.Socks5ProxyImpl
		 * @see edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyPackageImpl#getSocks5Proxy()
		 * @generated
		 */
		EClass SOCKS5_PROXY = eINSTANCE.getSocks5Proxy();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOCKS5_PROXY__PASSWORD = eINSTANCE.getSocks5Proxy_Password();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOCKS5_PROXY__PORT = eINSTANCE.getSocks5Proxy_Port();

		/**
		 * The meta object literal for the '<em><b>Server</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOCKS5_PROXY__SERVER = eINSTANCE.getSocks5Proxy_Server();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOCKS5_PROXY__USERNAME = eINSTANCE.getSocks5Proxy_Username();

	}

} //ProxyPackage
