/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.face.impl;

import edu.tsinghua.lumaqq.ecore.face.FaceGroup;
import edu.tsinghua.lumaqq.ecore.face.FacePackage;
import edu.tsinghua.lumaqq.ecore.face.Faces;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Faces</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.face.impl.FacesImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.face.impl.FacesImpl#getNextGroupId <em>Next Group Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.face.impl.FacesImpl#getNextId <em>Next Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FacesImpl extends EObjectImpl implements Faces {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected EList group = null;

	/**
	 * The default value of the '{@link #getNextGroupId() <em>Next Group Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNextGroupId()
	 * @generated
	 * @ordered
	 */
	protected static final int NEXT_GROUP_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNextGroupId() <em>Next Group Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNextGroupId()
	 * @generated
	 * @ordered
	 */
	protected int nextGroupId = NEXT_GROUP_ID_EDEFAULT;

	/**
	 * This is true if the Next Group Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean nextGroupIdESet = false;

	/**
	 * The default value of the '{@link #getNextId() <em>Next Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNextId()
	 * @generated
	 * @ordered
	 */
	protected static final int NEXT_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNextId() <em>Next Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNextId()
	 * @generated
	 * @ordered
	 */
	protected int nextId = NEXT_ID_EDEFAULT;

	/**
	 * This is true if the Next Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean nextIdESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FacesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FacePackage.Literals.FACES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getGroup() {
		if (group == null) {
			group = new EObjectContainmentEList(FaceGroup.class, this, FacePackage.FACES__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNextGroupId() {
		return nextGroupId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNextGroupId(int newNextGroupId) {
		int oldNextGroupId = nextGroupId;
		nextGroupId = newNextGroupId;
		boolean oldNextGroupIdESet = nextGroupIdESet;
		nextGroupIdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FacePackage.FACES__NEXT_GROUP_ID, oldNextGroupId, nextGroupId, !oldNextGroupIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetNextGroupId() {
		int oldNextGroupId = nextGroupId;
		boolean oldNextGroupIdESet = nextGroupIdESet;
		nextGroupId = NEXT_GROUP_ID_EDEFAULT;
		nextGroupIdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, FacePackage.FACES__NEXT_GROUP_ID, oldNextGroupId, NEXT_GROUP_ID_EDEFAULT, oldNextGroupIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetNextGroupId() {
		return nextGroupIdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNextId() {
		return nextId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNextId(int newNextId) {
		int oldNextId = nextId;
		nextId = newNextId;
		boolean oldNextIdESet = nextIdESet;
		nextIdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FacePackage.FACES__NEXT_ID, oldNextId, nextId, !oldNextIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetNextId() {
		int oldNextId = nextId;
		boolean oldNextIdESet = nextIdESet;
		nextId = NEXT_ID_EDEFAULT;
		nextIdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, FacePackage.FACES__NEXT_ID, oldNextId, NEXT_ID_EDEFAULT, oldNextIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetNextId() {
		return nextIdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FacePackage.FACES__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FacePackage.FACES__GROUP:
				return getGroup();
			case FacePackage.FACES__NEXT_GROUP_ID:
				return new Integer(getNextGroupId());
			case FacePackage.FACES__NEXT_ID:
				return new Integer(getNextId());
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FacePackage.FACES__GROUP:
				getGroup().clear();
				getGroup().addAll((Collection)newValue);
				return;
			case FacePackage.FACES__NEXT_GROUP_ID:
				setNextGroupId(((Integer)newValue).intValue());
				return;
			case FacePackage.FACES__NEXT_ID:
				setNextId(((Integer)newValue).intValue());
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case FacePackage.FACES__GROUP:
				getGroup().clear();
				return;
			case FacePackage.FACES__NEXT_GROUP_ID:
				unsetNextGroupId();
				return;
			case FacePackage.FACES__NEXT_ID:
				unsetNextId();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FacePackage.FACES__GROUP:
				return group != null && !group.isEmpty();
			case FacePackage.FACES__NEXT_GROUP_ID:
				return isSetNextGroupId();
			case FacePackage.FACES__NEXT_ID:
				return isSetNextId();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (nextGroupId: ");
		if (nextGroupIdESet) result.append(nextGroupId); else result.append("<unset>");
		result.append(", nextId: ");
		if (nextIdESet) result.append(nextId); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //FacesImpl
