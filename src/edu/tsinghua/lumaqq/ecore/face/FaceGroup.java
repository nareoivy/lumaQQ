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
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup#getFace <em>Face</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup#getId <em>Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.face.FacePackage#getFaceGroup()
 * @model
 * @generated
 */
public interface FaceGroup extends EObject {
	/**
	 * Returns the value of the '<em><b>Face</b></em>' containment reference list.
	 * The list contents are of type {@link edu.tsinghua.lumaqq.ecore.face.Face}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Face</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Face</em>' containment reference list.
	 * @see edu.tsinghua.lumaqq.ecore.face.FacePackage#getFaceGroup_Face()
	 * @model type="edu.tsinghua.lumaqq.ecore.face.Face" containment="true" resolveProxies="false"
	 * @generated
	 */
	EList getFace();

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #isSetId()
	 * @see #unsetId()
	 * @see #setId(int)
	 * @see edu.tsinghua.lumaqq.ecore.face.FacePackage#getFaceGroup_Id()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getId();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #isSetId()
	 * @see #unsetId()
	 * @see #getId()
	 * @generated
	 */
	void setId(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetId()
	 * @see #getId()
	 * @see #setId(int)
	 * @generated
	 */
	void unsetId();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup#getId <em>Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Id</em>' attribute is set.
	 * @see #unsetId()
	 * @see #getId()
	 * @see #setId(int)
	 * @generated
	 */
	boolean isSetId();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see edu.tsinghua.lumaqq.ecore.face.FacePackage#getFaceGroup_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // FaceGroup
