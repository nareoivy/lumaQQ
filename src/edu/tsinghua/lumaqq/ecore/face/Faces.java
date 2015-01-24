/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.face;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Faces</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.face.Faces#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.face.Faces#getNextGroupId <em>Next Group Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.face.Faces#getNextId <em>Next Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.face.FacePackage#getFaces()
 * @model
 * @generated
 */
public interface Faces extends EObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' containment reference list.
	 * The list contents are of type {@link edu.tsinghua.lumaqq.ecore.face.FaceGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' containment reference list.
	 * @see edu.tsinghua.lumaqq.ecore.face.FacePackage#getFaces_Group()
	 * @model type="edu.tsinghua.lumaqq.ecore.face.FaceGroup" containment="true" resolveProxies="false"
	 * @generated
	 */
	EList getGroup();

	/**
	 * Returns the value of the '<em><b>Next Group Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next Group Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Next Group Id</em>' attribute.
	 * @see #isSetNextGroupId()
	 * @see #unsetNextGroupId()
	 * @see #setNextGroupId(int)
	 * @see edu.tsinghua.lumaqq.ecore.face.FacePackage#getFaces_NextGroupId()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getNextGroupId();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.face.Faces#getNextGroupId <em>Next Group Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Next Group Id</em>' attribute.
	 * @see #isSetNextGroupId()
	 * @see #unsetNextGroupId()
	 * @see #getNextGroupId()
	 * @generated
	 */
	void setNextGroupId(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.face.Faces#getNextGroupId <em>Next Group Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetNextGroupId()
	 * @see #getNextGroupId()
	 * @see #setNextGroupId(int)
	 * @generated
	 */
	void unsetNextGroupId();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.face.Faces#getNextGroupId <em>Next Group Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Next Group Id</em>' attribute is set.
	 * @see #unsetNextGroupId()
	 * @see #getNextGroupId()
	 * @see #setNextGroupId(int)
	 * @generated
	 */
	boolean isSetNextGroupId();

	/**
	 * Returns the value of the '<em><b>Next Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Next Id</em>' attribute.
	 * @see #isSetNextId()
	 * @see #unsetNextId()
	 * @see #setNextId(int)
	 * @see edu.tsinghua.lumaqq.ecore.face.FacePackage#getFaces_NextId()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getNextId();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.face.Faces#getNextId <em>Next Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Next Id</em>' attribute.
	 * @see #isSetNextId()
	 * @see #unsetNextId()
	 * @see #getNextId()
	 * @generated
	 */
	void setNextId(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.face.Faces#getNextId <em>Next Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetNextId()
	 * @see #getNextId()
	 * @see #setNextId(int)
	 * @generated
	 */
	void unsetNextId();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.face.Faces#getNextId <em>Next Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Next Id</em>' attribute is set.
	 * @see #unsetNextId()
	 * @see #getNextId()
	 * @see #setNextId(int)
	 * @generated
	 */
	boolean isSetNextId();

} // Faces
