/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.group.util;

import edu.tsinghua.lumaqq.ecore.group.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage
 * @generated
 */
public class GroupAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static GroupPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = GroupPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GroupSwitch modelSwitch =
		new GroupSwitch() {
			@Override
			public Object caseXCluster(XCluster object) {
				return createXClusterAdapter();
			}
			@Override
			public Object caseXGroup(XGroup object) {
				return createXGroupAdapter();
			}
			@Override
			public Object caseXGroups(XGroups object) {
				return createXGroupsAdapter();
			}
			@Override
			public Object caseXOrganization(XOrganization object) {
				return createXOrganizationAdapter();
			}
			@Override
			public Object caseXUser(XUser object) {
				return createXUserAdapter();
			}
			@Override
			public Object defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.group.XCluster <em>XCluster</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster
	 * @generated
	 */
	public Adapter createXClusterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.group.XGroup <em>XGroup</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.group.XGroup
	 * @generated
	 */
	public Adapter createXGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.group.XGroups <em>XGroups</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.group.XGroups
	 * @generated
	 */
	public Adapter createXGroupsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.group.XOrganization <em>XOrganization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.group.XOrganization
	 * @generated
	 */
	public Adapter createXOrganizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.group.XUser <em>XUser</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser
	 * @generated
	 */
	public Adapter createXUserAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //GroupAdapterFactory
