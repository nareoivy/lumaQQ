/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option.impl;

import edu.tsinghua.lumaqq.ecore.LoginOption;

import edu.tsinghua.lumaqq.ecore.option.GUIOption;
import edu.tsinghua.lumaqq.ecore.option.KeyOption;
import edu.tsinghua.lumaqq.ecore.option.MessageOption;
import edu.tsinghua.lumaqq.ecore.option.OptionPackage;
import edu.tsinghua.lumaqq.ecore.option.Options;
import edu.tsinghua.lumaqq.ecore.option.OtherOption;
import edu.tsinghua.lumaqq.ecore.option.SyncOption;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Options</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OptionsImpl#getLoginOption <em>Login Option</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OptionsImpl#getGuiOption <em>Gui Option</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OptionsImpl#getMessageOption <em>Message Option</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OptionsImpl#getOtherOption <em>Other Option</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OptionsImpl#getSyncOption <em>Sync Option</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OptionsImpl#getKeyOption <em>Key Option</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OptionsImpl extends EObjectImpl implements Options {
	/**
	 * The cached value of the '{@link #getLoginOption() <em>Login Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoginOption()
	 * @generated
	 * @ordered
	 */
	protected LoginOption loginOption = null;

	/**
	 * The cached value of the '{@link #getGuiOption() <em>Gui Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuiOption()
	 * @generated
	 * @ordered
	 */
	protected GUIOption guiOption = null;

	/**
	 * The cached value of the '{@link #getMessageOption() <em>Message Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageOption()
	 * @generated
	 * @ordered
	 */
	protected MessageOption messageOption = null;

	/**
	 * The cached value of the '{@link #getOtherOption() <em>Other Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherOption()
	 * @generated
	 * @ordered
	 */
	protected OtherOption otherOption = null;

	/**
	 * The cached value of the '{@link #getSyncOption() <em>Sync Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncOption()
	 * @generated
	 * @ordered
	 */
	protected SyncOption syncOption = null;

	/**
	 * The cached value of the '{@link #getKeyOption() <em>Key Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyOption()
	 * @generated
	 * @ordered
	 */
	protected KeyOption keyOption = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OptionsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OptionPackage.Literals.OPTIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoginOption getLoginOption() {
		return loginOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLoginOption(LoginOption newLoginOption, NotificationChain msgs) {
		LoginOption oldLoginOption = loginOption;
		loginOption = newLoginOption;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__LOGIN_OPTION, oldLoginOption, newLoginOption);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoginOption(LoginOption newLoginOption) {
		if (newLoginOption != loginOption) {
			NotificationChain msgs = null;
			if (loginOption != null)
				msgs = ((InternalEObject)loginOption).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__LOGIN_OPTION, null, msgs);
			if (newLoginOption != null)
				msgs = ((InternalEObject)newLoginOption).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__LOGIN_OPTION, null, msgs);
			msgs = basicSetLoginOption(newLoginOption, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__LOGIN_OPTION, newLoginOption, newLoginOption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GUIOption getGuiOption() {
		return guiOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGuiOption(GUIOption newGuiOption, NotificationChain msgs) {
		GUIOption oldGuiOption = guiOption;
		guiOption = newGuiOption;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__GUI_OPTION, oldGuiOption, newGuiOption);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuiOption(GUIOption newGuiOption) {
		if (newGuiOption != guiOption) {
			NotificationChain msgs = null;
			if (guiOption != null)
				msgs = ((InternalEObject)guiOption).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__GUI_OPTION, null, msgs);
			if (newGuiOption != null)
				msgs = ((InternalEObject)newGuiOption).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__GUI_OPTION, null, msgs);
			msgs = basicSetGuiOption(newGuiOption, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__GUI_OPTION, newGuiOption, newGuiOption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MessageOption getMessageOption() {
		return messageOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMessageOption(MessageOption newMessageOption, NotificationChain msgs) {
		MessageOption oldMessageOption = messageOption;
		messageOption = newMessageOption;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__MESSAGE_OPTION, oldMessageOption, newMessageOption);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessageOption(MessageOption newMessageOption) {
		if (newMessageOption != messageOption) {
			NotificationChain msgs = null;
			if (messageOption != null)
				msgs = ((InternalEObject)messageOption).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__MESSAGE_OPTION, null, msgs);
			if (newMessageOption != null)
				msgs = ((InternalEObject)newMessageOption).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__MESSAGE_OPTION, null, msgs);
			msgs = basicSetMessageOption(newMessageOption, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__MESSAGE_OPTION, newMessageOption, newMessageOption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OtherOption getOtherOption() {
		return otherOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOtherOption(OtherOption newOtherOption, NotificationChain msgs) {
		OtherOption oldOtherOption = otherOption;
		otherOption = newOtherOption;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__OTHER_OPTION, oldOtherOption, newOtherOption);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOtherOption(OtherOption newOtherOption) {
		if (newOtherOption != otherOption) {
			NotificationChain msgs = null;
			if (otherOption != null)
				msgs = ((InternalEObject)otherOption).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__OTHER_OPTION, null, msgs);
			if (newOtherOption != null)
				msgs = ((InternalEObject)newOtherOption).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__OTHER_OPTION, null, msgs);
			msgs = basicSetOtherOption(newOtherOption, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__OTHER_OPTION, newOtherOption, newOtherOption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SyncOption getSyncOption() {
		return syncOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSyncOption(SyncOption newSyncOption, NotificationChain msgs) {
		SyncOption oldSyncOption = syncOption;
		syncOption = newSyncOption;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__SYNC_OPTION, oldSyncOption, newSyncOption);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSyncOption(SyncOption newSyncOption) {
		if (newSyncOption != syncOption) {
			NotificationChain msgs = null;
			if (syncOption != null)
				msgs = ((InternalEObject)syncOption).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__SYNC_OPTION, null, msgs);
			if (newSyncOption != null)
				msgs = ((InternalEObject)newSyncOption).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__SYNC_OPTION, null, msgs);
			msgs = basicSetSyncOption(newSyncOption, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__SYNC_OPTION, newSyncOption, newSyncOption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KeyOption getKeyOption() {
		return keyOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKeyOption(KeyOption newKeyOption, NotificationChain msgs) {
		KeyOption oldKeyOption = keyOption;
		keyOption = newKeyOption;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__KEY_OPTION, oldKeyOption, newKeyOption);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeyOption(KeyOption newKeyOption) {
		if (newKeyOption != keyOption) {
			NotificationChain msgs = null;
			if (keyOption != null)
				msgs = ((InternalEObject)keyOption).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__KEY_OPTION, null, msgs);
			if (newKeyOption != null)
				msgs = ((InternalEObject)newKeyOption).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OptionPackage.OPTIONS__KEY_OPTION, null, msgs);
			msgs = basicSetKeyOption(newKeyOption, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OPTIONS__KEY_OPTION, newKeyOption, newKeyOption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OptionPackage.OPTIONS__LOGIN_OPTION:
				return basicSetLoginOption(null, msgs);
			case OptionPackage.OPTIONS__GUI_OPTION:
				return basicSetGuiOption(null, msgs);
			case OptionPackage.OPTIONS__MESSAGE_OPTION:
				return basicSetMessageOption(null, msgs);
			case OptionPackage.OPTIONS__OTHER_OPTION:
				return basicSetOtherOption(null, msgs);
			case OptionPackage.OPTIONS__SYNC_OPTION:
				return basicSetSyncOption(null, msgs);
			case OptionPackage.OPTIONS__KEY_OPTION:
				return basicSetKeyOption(null, msgs);
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
			case OptionPackage.OPTIONS__LOGIN_OPTION:
				return getLoginOption();
			case OptionPackage.OPTIONS__GUI_OPTION:
				return getGuiOption();
			case OptionPackage.OPTIONS__MESSAGE_OPTION:
				return getMessageOption();
			case OptionPackage.OPTIONS__OTHER_OPTION:
				return getOtherOption();
			case OptionPackage.OPTIONS__SYNC_OPTION:
				return getSyncOption();
			case OptionPackage.OPTIONS__KEY_OPTION:
				return getKeyOption();
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
			case OptionPackage.OPTIONS__LOGIN_OPTION:
				setLoginOption((LoginOption)newValue);
				return;
			case OptionPackage.OPTIONS__GUI_OPTION:
				setGuiOption((GUIOption)newValue);
				return;
			case OptionPackage.OPTIONS__MESSAGE_OPTION:
				setMessageOption((MessageOption)newValue);
				return;
			case OptionPackage.OPTIONS__OTHER_OPTION:
				setOtherOption((OtherOption)newValue);
				return;
			case OptionPackage.OPTIONS__SYNC_OPTION:
				setSyncOption((SyncOption)newValue);
				return;
			case OptionPackage.OPTIONS__KEY_OPTION:
				setKeyOption((KeyOption)newValue);
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
			case OptionPackage.OPTIONS__LOGIN_OPTION:
				setLoginOption((LoginOption)null);
				return;
			case OptionPackage.OPTIONS__GUI_OPTION:
				setGuiOption((GUIOption)null);
				return;
			case OptionPackage.OPTIONS__MESSAGE_OPTION:
				setMessageOption((MessageOption)null);
				return;
			case OptionPackage.OPTIONS__OTHER_OPTION:
				setOtherOption((OtherOption)null);
				return;
			case OptionPackage.OPTIONS__SYNC_OPTION:
				setSyncOption((SyncOption)null);
				return;
			case OptionPackage.OPTIONS__KEY_OPTION:
				setKeyOption((KeyOption)null);
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
			case OptionPackage.OPTIONS__LOGIN_OPTION:
				return loginOption != null;
			case OptionPackage.OPTIONS__GUI_OPTION:
				return guiOption != null;
			case OptionPackage.OPTIONS__MESSAGE_OPTION:
				return messageOption != null;
			case OptionPackage.OPTIONS__OTHER_OPTION:
				return otherOption != null;
			case OptionPackage.OPTIONS__SYNC_OPTION:
				return syncOption != null;
			case OptionPackage.OPTIONS__KEY_OPTION:
				return keyOption != null;
		}
		return super.eIsSet(featureID);
	}

} //OptionsImpl
