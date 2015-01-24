/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option.util;

import edu.tsinghua.lumaqq.ecore.option.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage
 * @generated
 */
public class OptionAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static OptionPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OptionAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = OptionPackage.eINSTANCE;
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
	protected OptionSwitch modelSwitch =
		new OptionSwitch() {
			@Override
			public Object caseGUIOption(GUIOption object) {
				return createGUIOptionAdapter();
			}
			@Override
			public Object caseKeyOption(KeyOption object) {
				return createKeyOptionAdapter();
			}
			@Override
			public Object caseMessageOption(MessageOption object) {
				return createMessageOptionAdapter();
			}
			@Override
			public Object caseOptions(Options object) {
				return createOptionsAdapter();
			}
			@Override
			public Object caseOtherOption(OtherOption object) {
				return createOtherOptionAdapter();
			}
			@Override
			public Object caseSyncOption(SyncOption object) {
				return createSyncOptionAdapter();
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
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption <em>GUI Option</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption
	 * @generated
	 */
	public Adapter createGUIOptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.option.KeyOption <em>Key Option</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.option.KeyOption
	 * @generated
	 */
	public Adapter createKeyOptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption <em>Message Option</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.option.MessageOption
	 * @generated
	 */
	public Adapter createMessageOptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.option.Options <em>Options</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.option.Options
	 * @generated
	 */
	public Adapter createOptionsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption <em>Other Option</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.option.OtherOption
	 * @generated
	 */
	public Adapter createOtherOptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption <em>Sync Option</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.tsinghua.lumaqq.ecore.option.SyncOption
	 * @generated
	 */
	public Adapter createSyncOptionAdapter() {
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

} //OptionAdapterFactory
