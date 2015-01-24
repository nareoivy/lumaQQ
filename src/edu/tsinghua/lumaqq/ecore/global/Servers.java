/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.global;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Servers</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.Servers#getTCPServer <em>TCP Server</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.Servers#getUDPServer <em>UDP Server</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.global.GlobalPackage#getServers()
 * @model
 * @generated
 */
public interface Servers extends EObject {
	/**
	 * Returns the value of the '<em><b>TCP Server</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>TCP Server</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>TCP Server</em>' attribute list.
	 * @see edu.tsinghua.lumaqq.ecore.global.GlobalPackage#getServers_TCPServer()
	 * @model type="java.lang.String"
	 * @generated
	 */
	EList getTCPServer();

	/**
	 * Returns the value of the '<em><b>UDP Server</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>UDP Server</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>UDP Server</em>' attribute list.
	 * @see edu.tsinghua.lumaqq.ecore.global.GlobalPackage#getServers_UDPServer()
	 * @model type="java.lang.String"
	 * @generated
	 */
	EList getUDPServer();

} // Servers
