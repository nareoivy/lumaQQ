/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.remark;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.remark.RemarkPackage
 * @generated
 */
public interface RemarkFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RemarkFactory eINSTANCE = edu.tsinghua.lumaqq.ecore.remark.impl.RemarkFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Remark</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remark</em>'.
	 * @generated
	 */
	Remark createRemark();

	/**
	 * Returns a new object of class '<em>Remarks</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remarks</em>'.
	 * @generated
	 */
	Remarks createRemarks();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RemarkPackage getRemarkPackage();

} //RemarkFactory
