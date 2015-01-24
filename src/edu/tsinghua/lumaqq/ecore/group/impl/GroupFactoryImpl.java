/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.group.impl;

import edu.tsinghua.lumaqq.ecore.group.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GroupFactoryImpl extends EFactoryImpl implements GroupFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GroupFactory init() {
		try {
			GroupFactory theGroupFactory = (GroupFactory)EPackage.Registry.INSTANCE.getEFactory("http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore/group"); 
			if (theGroupFactory != null) {
				return theGroupFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GroupFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case GroupPackage.XCLUSTER: return createXCluster();
			case GroupPackage.XGROUP: return createXGroup();
			case GroupPackage.XGROUPS: return createXGroups();
			case GroupPackage.XORGANIZATION: return createXOrganization();
			case GroupPackage.XUSER: return createXUser();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XCluster createXCluster() {
		XClusterImpl xCluster = new XClusterImpl();
		return xCluster;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XGroup createXGroup() {
		XGroupImpl xGroup = new XGroupImpl();
		return xGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XGroups createXGroups() {
		XGroupsImpl xGroups = new XGroupsImpl();
		return xGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XOrganization createXOrganization() {
		XOrganizationImpl xOrganization = new XOrganizationImpl();
		return xOrganization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XUser createXUser() {
		XUserImpl xUser = new XUserImpl();
		return xUser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupPackage getGroupPackage() {
		return (GroupPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GroupPackage getPackage() {
		return GroupPackage.eINSTANCE;
	}

} //GroupFactoryImpl
