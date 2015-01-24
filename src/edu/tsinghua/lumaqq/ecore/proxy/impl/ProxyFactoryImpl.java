/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.proxy.impl;

import edu.tsinghua.lumaqq.ecore.proxy.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProxyFactoryImpl extends EFactoryImpl implements ProxyFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProxyFactory init() {
		try {
			ProxyFactory theProxyFactory = (ProxyFactory)EPackage.Registry.INSTANCE.getEFactory("http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore/proxy"); 
			if (theProxyFactory != null) {
				return theProxyFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ProxyFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProxyFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ProxyPackage.HTTP_PROXY: return createHttpProxy();
			case ProxyPackage.PROXIES: return createProxies();
			case ProxyPackage.SOCKS5_PROXY: return createSocks5Proxy();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HttpProxy createHttpProxy() {
		HttpProxyImpl httpProxy = new HttpProxyImpl();
		return httpProxy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Proxies createProxies() {
		ProxiesImpl proxies = new ProxiesImpl();
		return proxies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Socks5Proxy createSocks5Proxy() {
		Socks5ProxyImpl socks5Proxy = new Socks5ProxyImpl();
		return socks5Proxy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProxyPackage getProxyPackage() {
		return (ProxyPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ProxyPackage getPackage() {
		return ProxyPackage.eINSTANCE;
	}

} //ProxyFactoryImpl
