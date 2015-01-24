/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option.impl;

import edu.tsinghua.lumaqq.ecore.option.MessageOption;
import edu.tsinghua.lumaqq.ecore.option.OptionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message Option</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.MessageOptionImpl#isAutoEject <em>Auto Eject</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.MessageOptionImpl#isEnableSound <em>Enable Sound</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.MessageOptionImpl#isRejectStranger <em>Reject Stranger</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.MessageOptionImpl#isRejectTempSessionIM <em>Reject Temp Session IM</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.MessageOptionImpl#isUseEnterInMessageMode <em>Use Enter In Message Mode</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.MessageOptionImpl#isUseEnterInTalkMode <em>Use Enter In Talk Mode</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MessageOptionImpl extends EObjectImpl implements MessageOption {
	/**
	 * The default value of the '{@link #isAutoEject() <em>Auto Eject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoEject()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTO_EJECT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAutoEject() <em>Auto Eject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoEject()
	 * @generated
	 * @ordered
	 */
	protected boolean autoEject = AUTO_EJECT_EDEFAULT;

	/**
	 * This is true if the Auto Eject attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean autoEjectESet = false;

	/**
	 * The default value of the '{@link #isEnableSound() <em>Enable Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnableSound()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLE_SOUND_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEnableSound() <em>Enable Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnableSound()
	 * @generated
	 * @ordered
	 */
	protected boolean enableSound = ENABLE_SOUND_EDEFAULT;

	/**
	 * This is true if the Enable Sound attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean enableSoundESet = false;

	/**
	 * The default value of the '{@link #isRejectStranger() <em>Reject Stranger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRejectStranger()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REJECT_STRANGER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRejectStranger() <em>Reject Stranger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRejectStranger()
	 * @generated
	 * @ordered
	 */
	protected boolean rejectStranger = REJECT_STRANGER_EDEFAULT;

	/**
	 * This is true if the Reject Stranger attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean rejectStrangerESet = false;

	/**
	 * The default value of the '{@link #isRejectTempSessionIM() <em>Reject Temp Session IM</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRejectTempSessionIM()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REJECT_TEMP_SESSION_IM_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRejectTempSessionIM() <em>Reject Temp Session IM</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRejectTempSessionIM()
	 * @generated
	 * @ordered
	 */
	protected boolean rejectTempSessionIM = REJECT_TEMP_SESSION_IM_EDEFAULT;

	/**
	 * This is true if the Reject Temp Session IM attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean rejectTempSessionIMESet = false;

	/**
	 * The default value of the '{@link #isUseEnterInMessageMode() <em>Use Enter In Message Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseEnterInMessageMode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_ENTER_IN_MESSAGE_MODE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseEnterInMessageMode() <em>Use Enter In Message Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseEnterInMessageMode()
	 * @generated
	 * @ordered
	 */
	protected boolean useEnterInMessageMode = USE_ENTER_IN_MESSAGE_MODE_EDEFAULT;

	/**
	 * This is true if the Use Enter In Message Mode attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean useEnterInMessageModeESet = false;

	/**
	 * The default value of the '{@link #isUseEnterInTalkMode() <em>Use Enter In Talk Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseEnterInTalkMode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_ENTER_IN_TALK_MODE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseEnterInTalkMode() <em>Use Enter In Talk Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseEnterInTalkMode()
	 * @generated
	 * @ordered
	 */
	protected boolean useEnterInTalkMode = USE_ENTER_IN_TALK_MODE_EDEFAULT;

	/**
	 * This is true if the Use Enter In Talk Mode attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean useEnterInTalkModeESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MessageOptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OptionPackage.Literals.MESSAGE_OPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAutoEject() {
		return autoEject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoEject(boolean newAutoEject) {
		boolean oldAutoEject = autoEject;
		autoEject = newAutoEject;
		boolean oldAutoEjectESet = autoEjectESet;
		autoEjectESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.MESSAGE_OPTION__AUTO_EJECT, oldAutoEject, autoEject, !oldAutoEjectESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAutoEject() {
		boolean oldAutoEject = autoEject;
		boolean oldAutoEjectESet = autoEjectESet;
		autoEject = AUTO_EJECT_EDEFAULT;
		autoEjectESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.MESSAGE_OPTION__AUTO_EJECT, oldAutoEject, AUTO_EJECT_EDEFAULT, oldAutoEjectESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAutoEject() {
		return autoEjectESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnableSound() {
		return enableSound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableSound(boolean newEnableSound) {
		boolean oldEnableSound = enableSound;
		enableSound = newEnableSound;
		boolean oldEnableSoundESet = enableSoundESet;
		enableSoundESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.MESSAGE_OPTION__ENABLE_SOUND, oldEnableSound, enableSound, !oldEnableSoundESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetEnableSound() {
		boolean oldEnableSound = enableSound;
		boolean oldEnableSoundESet = enableSoundESet;
		enableSound = ENABLE_SOUND_EDEFAULT;
		enableSoundESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.MESSAGE_OPTION__ENABLE_SOUND, oldEnableSound, ENABLE_SOUND_EDEFAULT, oldEnableSoundESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetEnableSound() {
		return enableSoundESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRejectStranger() {
		return rejectStranger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRejectStranger(boolean newRejectStranger) {
		boolean oldRejectStranger = rejectStranger;
		rejectStranger = newRejectStranger;
		boolean oldRejectStrangerESet = rejectStrangerESet;
		rejectStrangerESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.MESSAGE_OPTION__REJECT_STRANGER, oldRejectStranger, rejectStranger, !oldRejectStrangerESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRejectStranger() {
		boolean oldRejectStranger = rejectStranger;
		boolean oldRejectStrangerESet = rejectStrangerESet;
		rejectStranger = REJECT_STRANGER_EDEFAULT;
		rejectStrangerESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.MESSAGE_OPTION__REJECT_STRANGER, oldRejectStranger, REJECT_STRANGER_EDEFAULT, oldRejectStrangerESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRejectStranger() {
		return rejectStrangerESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRejectTempSessionIM() {
		return rejectTempSessionIM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRejectTempSessionIM(boolean newRejectTempSessionIM) {
		boolean oldRejectTempSessionIM = rejectTempSessionIM;
		rejectTempSessionIM = newRejectTempSessionIM;
		boolean oldRejectTempSessionIMESet = rejectTempSessionIMESet;
		rejectTempSessionIMESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.MESSAGE_OPTION__REJECT_TEMP_SESSION_IM, oldRejectTempSessionIM, rejectTempSessionIM, !oldRejectTempSessionIMESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRejectTempSessionIM() {
		boolean oldRejectTempSessionIM = rejectTempSessionIM;
		boolean oldRejectTempSessionIMESet = rejectTempSessionIMESet;
		rejectTempSessionIM = REJECT_TEMP_SESSION_IM_EDEFAULT;
		rejectTempSessionIMESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.MESSAGE_OPTION__REJECT_TEMP_SESSION_IM, oldRejectTempSessionIM, REJECT_TEMP_SESSION_IM_EDEFAULT, oldRejectTempSessionIMESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRejectTempSessionIM() {
		return rejectTempSessionIMESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseEnterInMessageMode() {
		return useEnterInMessageMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseEnterInMessageMode(boolean newUseEnterInMessageMode) {
		boolean oldUseEnterInMessageMode = useEnterInMessageMode;
		useEnterInMessageMode = newUseEnterInMessageMode;
		boolean oldUseEnterInMessageModeESet = useEnterInMessageModeESet;
		useEnterInMessageModeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_MESSAGE_MODE, oldUseEnterInMessageMode, useEnterInMessageMode, !oldUseEnterInMessageModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetUseEnterInMessageMode() {
		boolean oldUseEnterInMessageMode = useEnterInMessageMode;
		boolean oldUseEnterInMessageModeESet = useEnterInMessageModeESet;
		useEnterInMessageMode = USE_ENTER_IN_MESSAGE_MODE_EDEFAULT;
		useEnterInMessageModeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_MESSAGE_MODE, oldUseEnterInMessageMode, USE_ENTER_IN_MESSAGE_MODE_EDEFAULT, oldUseEnterInMessageModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetUseEnterInMessageMode() {
		return useEnterInMessageModeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseEnterInTalkMode() {
		return useEnterInTalkMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseEnterInTalkMode(boolean newUseEnterInTalkMode) {
		boolean oldUseEnterInTalkMode = useEnterInTalkMode;
		useEnterInTalkMode = newUseEnterInTalkMode;
		boolean oldUseEnterInTalkModeESet = useEnterInTalkModeESet;
		useEnterInTalkModeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_TALK_MODE, oldUseEnterInTalkMode, useEnterInTalkMode, !oldUseEnterInTalkModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetUseEnterInTalkMode() {
		boolean oldUseEnterInTalkMode = useEnterInTalkMode;
		boolean oldUseEnterInTalkModeESet = useEnterInTalkModeESet;
		useEnterInTalkMode = USE_ENTER_IN_TALK_MODE_EDEFAULT;
		useEnterInTalkModeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_TALK_MODE, oldUseEnterInTalkMode, USE_ENTER_IN_TALK_MODE_EDEFAULT, oldUseEnterInTalkModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetUseEnterInTalkMode() {
		return useEnterInTalkModeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OptionPackage.MESSAGE_OPTION__AUTO_EJECT:
				return isAutoEject() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.MESSAGE_OPTION__ENABLE_SOUND:
				return isEnableSound() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.MESSAGE_OPTION__REJECT_STRANGER:
				return isRejectStranger() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.MESSAGE_OPTION__REJECT_TEMP_SESSION_IM:
				return isRejectTempSessionIM() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_MESSAGE_MODE:
				return isUseEnterInMessageMode() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_TALK_MODE:
				return isUseEnterInTalkMode() ? Boolean.TRUE : Boolean.FALSE;
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
			case OptionPackage.MESSAGE_OPTION__AUTO_EJECT:
				setAutoEject(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.MESSAGE_OPTION__ENABLE_SOUND:
				setEnableSound(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.MESSAGE_OPTION__REJECT_STRANGER:
				setRejectStranger(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.MESSAGE_OPTION__REJECT_TEMP_SESSION_IM:
				setRejectTempSessionIM(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_MESSAGE_MODE:
				setUseEnterInMessageMode(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_TALK_MODE:
				setUseEnterInTalkMode(((Boolean)newValue).booleanValue());
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
			case OptionPackage.MESSAGE_OPTION__AUTO_EJECT:
				unsetAutoEject();
				return;
			case OptionPackage.MESSAGE_OPTION__ENABLE_SOUND:
				unsetEnableSound();
				return;
			case OptionPackage.MESSAGE_OPTION__REJECT_STRANGER:
				unsetRejectStranger();
				return;
			case OptionPackage.MESSAGE_OPTION__REJECT_TEMP_SESSION_IM:
				unsetRejectTempSessionIM();
				return;
			case OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_MESSAGE_MODE:
				unsetUseEnterInMessageMode();
				return;
			case OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_TALK_MODE:
				unsetUseEnterInTalkMode();
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
			case OptionPackage.MESSAGE_OPTION__AUTO_EJECT:
				return isSetAutoEject();
			case OptionPackage.MESSAGE_OPTION__ENABLE_SOUND:
				return isSetEnableSound();
			case OptionPackage.MESSAGE_OPTION__REJECT_STRANGER:
				return isSetRejectStranger();
			case OptionPackage.MESSAGE_OPTION__REJECT_TEMP_SESSION_IM:
				return isSetRejectTempSessionIM();
			case OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_MESSAGE_MODE:
				return isSetUseEnterInMessageMode();
			case OptionPackage.MESSAGE_OPTION__USE_ENTER_IN_TALK_MODE:
				return isSetUseEnterInTalkMode();
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
		result.append(" (autoEject: ");
		if (autoEjectESet) result.append(autoEject); else result.append("<unset>");
		result.append(", enableSound: ");
		if (enableSoundESet) result.append(enableSound); else result.append("<unset>");
		result.append(", rejectStranger: ");
		if (rejectStrangerESet) result.append(rejectStranger); else result.append("<unset>");
		result.append(", rejectTempSessionIM: ");
		if (rejectTempSessionIMESet) result.append(rejectTempSessionIM); else result.append("<unset>");
		result.append(", useEnterInMessageMode: ");
		if (useEnterInMessageModeESet) result.append(useEnterInMessageMode); else result.append("<unset>");
		result.append(", useEnterInTalkMode: ");
		if (useEnterInTalkModeESet) result.append(useEnterInTalkMode); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //MessageOptionImpl
