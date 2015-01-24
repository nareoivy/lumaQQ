/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.global.impl;

import edu.tsinghua.lumaqq.ecore.global.GlobalPackage;
import edu.tsinghua.lumaqq.ecore.global.Servers;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Servers</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.impl.ServersImpl#getTCPServer <em>TCP Server</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.impl.ServersImpl#getUDPServer <em>UDP Server</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServersImpl extends EObjectImpl implements Servers {
	/**
	 * The cached value of the '{@link #getTCPServer() <em>TCP Server</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTCPServer()
	 * @generated
	 * @ordered
	 */
	protected EList tcpServer = null;

	/**
	 * The cached value of the '{@link #getUDPServer() <em>UDP Server</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUDPServer()
	 * @generated
	 * @ordered
	 */
	protected EList udpServer = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServersImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GlobalPackage.Literals.SERVERS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTCPServer() {
		if (tcpServer == null) {
			tcpServer = new EDataTypeUniqueEList(String.class, this, GlobalPackage.SERVERS__TCP_SERVER);
		}
		return tcpServer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getUDPServer() {
		if (udpServer == null) {
			udpServer = new EDataTypeUniqueEList(String.class, this, GlobalPackage.SERVERS__UDP_SERVER);
		}
		return udpServer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GlobalPackage.SERVERS__TCP_SERVER:
				return getTCPServer();
			case GlobalPackage.SERVERS__UDP_SERVER:
				return getUDPServer();
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
			case GlobalPackage.SERVERS__TCP_SERVER:
				getTCPServer().clear();
				getTCPServer().addAll((Collection)newValue);
				return;
			case GlobalPackage.SERVERS__UDP_SERVER:
				getUDPServer().clear();
				getUDPServer().addAll((Collection)newValue);
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
			case GlobalPackage.SERVERS__TCP_SERVER:
				getTCPServer().clear();
				return;
			case GlobalPackage.SERVERS__UDP_SERVER:
				getUDPServer().clear();
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
			case GlobalPackage.SERVERS__TCP_SERVER:
				return tcpServer != null && !tcpServer.isEmpty();
			case GlobalPackage.SERVERS__UDP_SERVER:
				return udpServer != null && !udpServer.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (TCPServer: ");
		result.append(tcpServer);
		result.append(", UDPServer: ");
		result.append(udpServer);
		result.append(')');
		return result.toString();
	}

} //ServersImpl
