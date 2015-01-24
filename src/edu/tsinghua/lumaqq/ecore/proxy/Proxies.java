/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.proxy;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proxies</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.proxy.Proxies#getSocks5Proxy <em>Socks5 Proxy</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.proxy.Proxies#getHttpProxy <em>Http Proxy</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.proxy.ProxyPackage#getProxies()
 * @model
 * @generated
 */
public interface Proxies extends EObject {
	/**
	 * Returns the value of the '<em><b>Socks5 Proxy</b></em>' containment reference list.
	 * The list contents are of type {@link edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Socks5 Proxy</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Socks5 Proxy</em>' containment reference list.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.ProxyPackage#getProxies_Socks5Proxy()
	 * @model type="edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy" containment="true" resolveProxies="false"
	 * @generated
	 */
	EList getSocks5Proxy();

	/**
	 * Returns the value of the '<em><b>Http Proxy</b></em>' containment reference list.
	 * The list contents are of type {@link edu.tsinghua.lumaqq.ecore.proxy.HttpProxy}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Http Proxy</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Http Proxy</em>' containment reference list.
	 * @see edu.tsinghua.lumaqq.ecore.proxy.ProxyPackage#getProxies_HttpProxy()
	 * @model type="edu.tsinghua.lumaqq.ecore.proxy.HttpProxy" containment="true" resolveProxies="false"
	 * @generated
	 */
	EList getHttpProxy();

} // Proxies
