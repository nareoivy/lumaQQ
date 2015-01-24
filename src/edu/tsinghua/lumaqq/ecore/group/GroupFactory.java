/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.group;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage
 * @generated
 */
public interface GroupFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GroupFactory eINSTANCE = edu.tsinghua.lumaqq.ecore.group.impl.GroupFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>XCluster</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>XCluster</em>'.
	 * @generated
	 */
	XCluster createXCluster();

	/**
	 * Returns a new object of class '<em>XGroup</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>XGroup</em>'.
	 * @generated
	 */
	XGroup createXGroup();

	/**
	 * Returns a new object of class '<em>XGroups</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>XGroups</em>'.
	 * @generated
	 */
	XGroups createXGroups();

	/**
	 * Returns a new object of class '<em>XOrganization</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>XOrganization</em>'.
	 * @generated
	 */
	XOrganization createXOrganization();

	/**
	 * Returns a new object of class '<em>XUser</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>XUser</em>'.
	 * @generated
	 */
	XUser createXUser();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GroupPackage getGroupPackage();

} //GroupFactory
