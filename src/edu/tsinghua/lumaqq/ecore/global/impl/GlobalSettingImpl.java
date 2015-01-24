/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.global.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import edu.tsinghua.lumaqq.ecore.global.GlobalPackage;
import edu.tsinghua.lumaqq.ecore.global.GlobalSetting;
import edu.tsinghua.lumaqq.ecore.global.LanguageType;
import edu.tsinghua.lumaqq.ecore.global.Robots;
import edu.tsinghua.lumaqq.ecore.global.Servers;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Setting</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.impl.GlobalSettingImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.impl.GlobalSettingImpl#getServers <em>Servers</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.global.impl.GlobalSettingImpl#getRobots <em>Robots</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GlobalSettingImpl extends EObjectImpl implements GlobalSetting {
	/**
	 * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final LanguageType LANGUAGE_EDEFAULT = LanguageType.ZH_LITERAL;

	/**
	 * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected LanguageType language = LANGUAGE_EDEFAULT;

	/**
	 * This is true if the Language attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean languageESet = false;

	/**
	 * The cached value of the '{@link #getServers() <em>Servers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServers()
	 * @generated
	 * @ordered
	 */
	protected Servers servers = null;

	/**
	 * The cached value of the '{@link #getRobots() <em>Robots</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRobots()
	 * @generated
	 * @ordered
	 */
	protected Robots robots = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GlobalSettingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GlobalPackage.Literals.GLOBAL_SETTING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LanguageType getLanguage() {
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLanguage(LanguageType newLanguage) {
		LanguageType oldLanguage = language;
		language = newLanguage == null ? LANGUAGE_EDEFAULT : newLanguage;
		boolean oldLanguageESet = languageESet;
		languageESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GlobalPackage.GLOBAL_SETTING__LANGUAGE, oldLanguage, language, !oldLanguageESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLanguage() {
		LanguageType oldLanguage = language;
		boolean oldLanguageESet = languageESet;
		language = LANGUAGE_EDEFAULT;
		languageESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GlobalPackage.GLOBAL_SETTING__LANGUAGE, oldLanguage, LANGUAGE_EDEFAULT, oldLanguageESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLanguage() {
		return languageESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Servers getServers() {
		return servers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetServers(Servers newServers, NotificationChain msgs) {
		Servers oldServers = servers;
		servers = newServers;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GlobalPackage.GLOBAL_SETTING__SERVERS, oldServers, newServers);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServers(Servers newServers) {
		if (newServers != servers) {
			NotificationChain msgs = null;
			if (servers != null)
				msgs = ((InternalEObject)servers).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GlobalPackage.GLOBAL_SETTING__SERVERS, null, msgs);
			if (newServers != null)
				msgs = ((InternalEObject)newServers).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GlobalPackage.GLOBAL_SETTING__SERVERS, null, msgs);
			msgs = basicSetServers(newServers, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GlobalPackage.GLOBAL_SETTING__SERVERS, newServers, newServers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Robots getRobots() {
		return robots;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRobots(Robots newRobots, NotificationChain msgs) {
		Robots oldRobots = robots;
		robots = newRobots;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GlobalPackage.GLOBAL_SETTING__ROBOTS, oldRobots, newRobots);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRobots(Robots newRobots) {
		if (newRobots != robots) {
			NotificationChain msgs = null;
			if (robots != null)
				msgs = ((InternalEObject)robots).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GlobalPackage.GLOBAL_SETTING__ROBOTS, null, msgs);
			if (newRobots != null)
				msgs = ((InternalEObject)newRobots).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GlobalPackage.GLOBAL_SETTING__ROBOTS, null, msgs);
			msgs = basicSetRobots(newRobots, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GlobalPackage.GLOBAL_SETTING__ROBOTS, newRobots, newRobots));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GlobalPackage.GLOBAL_SETTING__SERVERS:
				return basicSetServers(null, msgs);
			case GlobalPackage.GLOBAL_SETTING__ROBOTS:
				return basicSetRobots(null, msgs);
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
			case GlobalPackage.GLOBAL_SETTING__LANGUAGE:
				return getLanguage();
			case GlobalPackage.GLOBAL_SETTING__SERVERS:
				return getServers();
			case GlobalPackage.GLOBAL_SETTING__ROBOTS:
				return getRobots();
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
			case GlobalPackage.GLOBAL_SETTING__LANGUAGE:
				setLanguage((LanguageType)newValue);
				return;
			case GlobalPackage.GLOBAL_SETTING__SERVERS:
				setServers((Servers)newValue);
				return;
			case GlobalPackage.GLOBAL_SETTING__ROBOTS:
				setRobots((Robots)newValue);
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
			case GlobalPackage.GLOBAL_SETTING__LANGUAGE:
				unsetLanguage();
				return;
			case GlobalPackage.GLOBAL_SETTING__SERVERS:
				setServers((Servers)null);
				return;
			case GlobalPackage.GLOBAL_SETTING__ROBOTS:
				setRobots((Robots)null);
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
			case GlobalPackage.GLOBAL_SETTING__LANGUAGE:
				return isSetLanguage();
			case GlobalPackage.GLOBAL_SETTING__SERVERS:
				return servers != null;
			case GlobalPackage.GLOBAL_SETTING__ROBOTS:
				return robots != null;
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
		result.append(" (language: ");
		if (languageESet) result.append(language); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //GlobalSettingImpl
