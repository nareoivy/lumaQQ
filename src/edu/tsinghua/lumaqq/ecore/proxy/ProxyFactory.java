/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.proxy;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.proxy.ProxyPackage
 * @generated
 */
public interface ProxyFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProxyFactory eINSTANCE = edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Http Proxy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Http Proxy</em>'.
	 * @generated
	 */
	HttpProxy createHttpProxy();

	/**
	 * Returns a new object of class '<em>Proxies</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proxies</em>'.
	 * @generated
	 */
	Proxies createProxies();

	/**
	 * Returns a new object of class '<em>Socks5 Proxy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Socks5 Proxy</em>'.
	 * @generated
	 */
	Socks5Proxy createSocks5Proxy();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ProxyPackage getProxyPackage();

} //ProxyFactory
