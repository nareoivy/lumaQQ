/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage
 * @generated
 */
public interface OptionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OptionFactory eINSTANCE = edu.tsinghua.lumaqq.ecore.option.impl.OptionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>GUI Option</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GUI Option</em>'.
	 * @generated
	 */
	GUIOption createGUIOption();

	/**
	 * Returns a new object of class '<em>Key Option</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Key Option</em>'.
	 * @generated
	 */
	KeyOption createKeyOption();

	/**
	 * Returns a new object of class '<em>Message Option</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Message Option</em>'.
	 * @generated
	 */
	MessageOption createMessageOption();

	/**
	 * Returns a new object of class '<em>Options</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Options</em>'.
	 * @generated
	 */
	Options createOptions();

	/**
	 * Returns a new object of class '<em>Other Option</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Other Option</em>'.
	 * @generated
	 */
	OtherOption createOtherOption();

	/**
	 * Returns a new object of class '<em>Sync Option</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sync Option</em>'.
	 * @generated
	 */
	SyncOption createSyncOption();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	OptionPackage getOptionPackage();

} //OptionFactory
