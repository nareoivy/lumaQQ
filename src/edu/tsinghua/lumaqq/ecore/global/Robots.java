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
 * A representation of the model object '<em><b>Robots</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.Robots#getRobot <em>Robot</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.global.GlobalPackage#getRobots()
 * @model
 * @generated
 */
public interface Robots extends EObject {
	/**
	 * Returns the value of the '<em><b>Robot</b></em>' containment reference list.
	 * The list contents are of type {@link edu.tsinghua.lumaqq.ecore.global.Robot}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Robot</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Robot</em>' containment reference list.
	 * @see edu.tsinghua.lumaqq.ecore.global.GlobalPackage#getRobots_Robot()
	 * @model type="edu.tsinghua.lumaqq.ecore.global.Robot" containment="true"
	 * @generated
	 */
	EList getRobot();

} // Robots
