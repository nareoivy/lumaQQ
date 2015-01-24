/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option.impl;

import edu.tsinghua.lumaqq.ecore.option.OpType;
import edu.tsinghua.lumaqq.ecore.option.OptionPackage;
import edu.tsinghua.lumaqq.ecore.option.SyncOption;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sync Option</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.SyncOptionImpl#isAutoCheckUpdate <em>Auto Check Update</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.SyncOptionImpl#isAutoDownloadFriendRemark <em>Auto Download Friend Remark</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.SyncOptionImpl#isAutoDownloadGroup <em>Auto Download Group</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.SyncOptionImpl#getAutoUploadGroup <em>Auto Upload Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SyncOptionImpl extends EObjectImpl implements SyncOption {
	/**
	 * The default value of the '{@link #isAutoCheckUpdate() <em>Auto Check Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoCheckUpdate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTO_CHECK_UPDATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAutoCheckUpdate() <em>Auto Check Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoCheckUpdate()
	 * @generated
	 * @ordered
	 */
	protected boolean autoCheckUpdate = AUTO_CHECK_UPDATE_EDEFAULT;

	/**
	 * This is true if the Auto Check Update attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean autoCheckUpdateESet = false;

	/**
	 * The default value of the '{@link #isAutoDownloadFriendRemark() <em>Auto Download Friend Remark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoDownloadFriendRemark()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTO_DOWNLOAD_FRIEND_REMARK_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAutoDownloadFriendRemark() <em>Auto Download Friend Remark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoDownloadFriendRemark()
	 * @generated
	 * @ordered
	 */
	protected boolean autoDownloadFriendRemark = AUTO_DOWNLOAD_FRIEND_REMARK_EDEFAULT;

	/**
	 * This is true if the Auto Download Friend Remark attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean autoDownloadFriendRemarkESet = false;

	/**
	 * The default value of the '{@link #isAutoDownloadGroup() <em>Auto Download Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoDownloadGroup()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTO_DOWNLOAD_GROUP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAutoDownloadGroup() <em>Auto Download Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoDownloadGroup()
	 * @generated
	 * @ordered
	 */
	protected boolean autoDownloadGroup = AUTO_DOWNLOAD_GROUP_EDEFAULT;

	/**
	 * This is true if the Auto Download Group attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean autoDownloadGroupESet = false;

	/**
	 * The default value of the '{@link #getAutoUploadGroup() <em>Auto Upload Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutoUploadGroup()
	 * @generated
	 * @ordered
	 */
	protected static final OpType AUTO_UPLOAD_GROUP_EDEFAULT = OpType.ALWAYS_LITERAL;

	/**
	 * The cached value of the '{@link #getAutoUploadGroup() <em>Auto Upload Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutoUploadGroup()
	 * @generated
	 * @ordered
	 */
	protected OpType autoUploadGroup = AUTO_UPLOAD_GROUP_EDEFAULT;

	/**
	 * This is true if the Auto Upload Group attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean autoUploadGroupESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SyncOptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OptionPackage.Literals.SYNC_OPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAutoCheckUpdate() {
		return autoCheckUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoCheckUpdate(boolean newAutoCheckUpdate) {
		boolean oldAutoCheckUpdate = autoCheckUpdate;
		autoCheckUpdate = newAutoCheckUpdate;
		boolean oldAutoCheckUpdateESet = autoCheckUpdateESet;
		autoCheckUpdateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.SYNC_OPTION__AUTO_CHECK_UPDATE, oldAutoCheckUpdate, autoCheckUpdate, !oldAutoCheckUpdateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAutoCheckUpdate() {
		boolean oldAutoCheckUpdate = autoCheckUpdate;
		boolean oldAutoCheckUpdateESet = autoCheckUpdateESet;
		autoCheckUpdate = AUTO_CHECK_UPDATE_EDEFAULT;
		autoCheckUpdateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.SYNC_OPTION__AUTO_CHECK_UPDATE, oldAutoCheckUpdate, AUTO_CHECK_UPDATE_EDEFAULT, oldAutoCheckUpdateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAutoCheckUpdate() {
		return autoCheckUpdateESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAutoDownloadFriendRemark() {
		return autoDownloadFriendRemark;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoDownloadFriendRemark(boolean newAutoDownloadFriendRemark) {
		boolean oldAutoDownloadFriendRemark = autoDownloadFriendRemark;
		autoDownloadFriendRemark = newAutoDownloadFriendRemark;
		boolean oldAutoDownloadFriendRemarkESet = autoDownloadFriendRemarkESet;
		autoDownloadFriendRemarkESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_FRIEND_REMARK, oldAutoDownloadFriendRemark, autoDownloadFriendRemark, !oldAutoDownloadFriendRemarkESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAutoDownloadFriendRemark() {
		boolean oldAutoDownloadFriendRemark = autoDownloadFriendRemark;
		boolean oldAutoDownloadFriendRemarkESet = autoDownloadFriendRemarkESet;
		autoDownloadFriendRemark = AUTO_DOWNLOAD_FRIEND_REMARK_EDEFAULT;
		autoDownloadFriendRemarkESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_FRIEND_REMARK, oldAutoDownloadFriendRemark, AUTO_DOWNLOAD_FRIEND_REMARK_EDEFAULT, oldAutoDownloadFriendRemarkESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAutoDownloadFriendRemark() {
		return autoDownloadFriendRemarkESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAutoDownloadGroup() {
		return autoDownloadGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoDownloadGroup(boolean newAutoDownloadGroup) {
		boolean oldAutoDownloadGroup = autoDownloadGroup;
		autoDownloadGroup = newAutoDownloadGroup;
		boolean oldAutoDownloadGroupESet = autoDownloadGroupESet;
		autoDownloadGroupESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_GROUP, oldAutoDownloadGroup, autoDownloadGroup, !oldAutoDownloadGroupESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAutoDownloadGroup() {
		boolean oldAutoDownloadGroup = autoDownloadGroup;
		boolean oldAutoDownloadGroupESet = autoDownloadGroupESet;
		autoDownloadGroup = AUTO_DOWNLOAD_GROUP_EDEFAULT;
		autoDownloadGroupESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_GROUP, oldAutoDownloadGroup, AUTO_DOWNLOAD_GROUP_EDEFAULT, oldAutoDownloadGroupESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAutoDownloadGroup() {
		return autoDownloadGroupESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpType getAutoUploadGroup() {
		return autoUploadGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoUploadGroup(OpType newAutoUploadGroup) {
		OpType oldAutoUploadGroup = autoUploadGroup;
		autoUploadGroup = newAutoUploadGroup == null ? AUTO_UPLOAD_GROUP_EDEFAULT : newAutoUploadGroup;
		boolean oldAutoUploadGroupESet = autoUploadGroupESet;
		autoUploadGroupESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.SYNC_OPTION__AUTO_UPLOAD_GROUP, oldAutoUploadGroup, autoUploadGroup, !oldAutoUploadGroupESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAutoUploadGroup() {
		OpType oldAutoUploadGroup = autoUploadGroup;
		boolean oldAutoUploadGroupESet = autoUploadGroupESet;
		autoUploadGroup = AUTO_UPLOAD_GROUP_EDEFAULT;
		autoUploadGroupESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.SYNC_OPTION__AUTO_UPLOAD_GROUP, oldAutoUploadGroup, AUTO_UPLOAD_GROUP_EDEFAULT, oldAutoUploadGroupESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAutoUploadGroup() {
		return autoUploadGroupESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OptionPackage.SYNC_OPTION__AUTO_CHECK_UPDATE:
				return isAutoCheckUpdate() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_FRIEND_REMARK:
				return isAutoDownloadFriendRemark() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_GROUP:
				return isAutoDownloadGroup() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.SYNC_OPTION__AUTO_UPLOAD_GROUP:
				return getAutoUploadGroup();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OptionPackage.SYNC_OPTION__AUTO_CHECK_UPDATE:
				setAutoCheckUpdate(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_FRIEND_REMARK:
				setAutoDownloadFriendRemark(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_GROUP:
				setAutoDownloadGroup(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.SYNC_OPTION__AUTO_UPLOAD_GROUP:
				setAutoUploadGroup((OpType)newValue);
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
			case OptionPackage.SYNC_OPTION__AUTO_CHECK_UPDATE:
				unsetAutoCheckUpdate();
				return;
			case OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_FRIEND_REMARK:
				unsetAutoDownloadFriendRemark();
				return;
			case OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_GROUP:
				unsetAutoDownloadGroup();
				return;
			case OptionPackage.SYNC_OPTION__AUTO_UPLOAD_GROUP:
				unsetAutoUploadGroup();
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
			case OptionPackage.SYNC_OPTION__AUTO_CHECK_UPDATE:
				return isSetAutoCheckUpdate();
			case OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_FRIEND_REMARK:
				return isSetAutoDownloadFriendRemark();
			case OptionPackage.SYNC_OPTION__AUTO_DOWNLOAD_GROUP:
				return isSetAutoDownloadGroup();
			case OptionPackage.SYNC_OPTION__AUTO_UPLOAD_GROUP:
				return isSetAutoUploadGroup();
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
		result.append(" (autoCheckUpdate: ");
		if (autoCheckUpdateESet) result.append(autoCheckUpdate); else result.append("<unset>");
		result.append(", autoDownloadFriendRemark: ");
		if (autoDownloadFriendRemarkESet) result.append(autoDownloadFriendRemark); else result.append("<unset>");
		result.append(", autoDownloadGroup: ");
		if (autoDownloadGroupESet) result.append(autoDownloadGroup); else result.append("<unset>");
		result.append(", autoUploadGroup: ");
		if (autoUploadGroupESet) result.append(autoUploadGroup); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SyncOptionImpl
