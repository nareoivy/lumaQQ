/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.proxy.impl;

import edu.tsinghua.lumaqq.ecore.proxy.HttpProxy;
import edu.tsinghua.lumaqq.ecore.proxy.Proxies;
import edu.tsinghua.lumaqq.ecore.proxy.ProxyPackage;
import edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proxies</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.proxy.impl.ProxiesImpl#getSocks5Proxy <em>Socks5 Proxy</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.proxy.impl.ProxiesImpl#getHttpProxy <em>Http Proxy</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProxiesImpl extends EObjectImpl implements Proxies {
	/**
	 * The cached value of the '{@link #getSocks5Proxy() <em>Socks5 Proxy</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSocks5Proxy()
	 * @generated
	 * @ordered
	 */
	protected EList socks5Proxy = null;

	/**
	 * The cached value of the '{@link #getHttpProxy() <em>Http Proxy</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHttpProxy()
	 * @generated
	 * @ordered
	 */
	protected EList httpProxy = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProxiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProxyPackage.Literals.PROXIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getSocks5Proxy() {
		if (socks5Proxy == null) {
			socks5Proxy = new EObjectContainmentEList(Socks5Proxy.class, this, ProxyPackage.PROXIES__SOCKS5_PROXY);
		}
		return socks5Proxy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getHttpProxy() {
		if (httpProxy == null) {
			httpProxy = new EObjectContainmentEList(HttpProxy.class, this, ProxyPackage.PROXIES__HTTP_PROXY);
		}
		return httpProxy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProxyPackage.PROXIES__SOCKS5_PROXY:
				return ((InternalEList)getSocks5Proxy()).basicRemove(otherEnd, msgs);
			case ProxyPackage.PROXIES__HTTP_PROXY:
				return ((InternalEList)getHttpProxy()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProxyPackage.PROXIES__SOCKS5_PROXY:
				return getSocks5Proxy();
			case ProxyPackage.PROXIES__HTTP_PROXY:
				return getHttpProxy();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProxyPackage.PROXIES__SOCKS5_PROXY:
				getSocks5Proxy().clear();
				getSocks5Proxy().addAll((Collection)newValue);
				return;
			case ProxyPackage.PROXIES__HTTP_PROXY:
				getHttpProxy().clear();
				getHttpProxy().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ProxyPackage.PROXIES__SOCKS5_PROXY:
				getSocks5Proxy().clear();
				return;
			case ProxyPackage.PROXIES__HTTP_PROXY:
				getHttpProxy().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ProxyPackage.PROXIES__SOCKS5_PROXY:
				return socks5Proxy != null && !socks5Proxy.isEmpty();
			case ProxyPackage.PROXIES__HTTP_PROXY:
				return httpProxy != null && !httpProxy.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProxiesImpl
