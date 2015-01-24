/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.login.impl;

import edu.tsinghua.lumaqq.ecore.LoginOption;

import edu.tsinghua.lumaqq.ecore.login.Login;
import edu.tsinghua.lumaqq.ecore.login.LoginPackage;
import edu.tsinghua.lumaqq.ecore.login.Logins;

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
 * An implementation of the model object '<em><b>Logins</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginsImpl#getLogin <em>Login</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginsImpl#getLastLogin <em>Last Login</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginsImpl#getNetwork <em>Network</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoginsImpl extends EObjectImpl implements Logins {
	/**
	 * The cached value of the '{@link #getLogin() <em>Login</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLogin()
	 * @generated
	 * @ordered
	 */
	protected EList login = null;

	/**
	 * The default value of the '{@link #getLastLogin() <em>Last Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastLogin()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_LOGIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastLogin() <em>Last Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastLogin()
	 * @generated
	 * @ordered
	 */
	protected String lastLogin = LAST_LOGIN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNetwork() <em>Network</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNetwork()
	 * @generated
	 * @ordered
	 */
	protected LoginOption network = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoginsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LoginPackage.Literals.LOGINS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getLogin() {
		if (login == null) {
			login = new EObjectContainmentEList(Login.class, this, LoginPackage.LOGINS__LOGIN);
		}
		return login;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastLogin() {
		return lastLogin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastLogin(String newLastLogin) {
		String oldLastLogin = lastLogin;
		lastLogin = newLastLogin;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGINS__LAST_LOGIN, oldLastLogin, lastLogin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoginOption getNetwork() {
		return network;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNetwork(LoginOption newNetwork, NotificationChain msgs) {
		LoginOption oldNetwork = network;
		network = newNetwork;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LoginPackage.LOGINS__NETWORK, oldNetwork, newNetwork);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNetwork(LoginOption newNetwork) {
		if (newNetwork != network) {
			NotificationChain msgs = null;
			if (network != null)
				msgs = ((InternalEObject)network).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LoginPackage.LOGINS__NETWORK, null, msgs);
			if (newNetwork != null)
				msgs = ((InternalEObject)newNetwork).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LoginPackage.LOGINS__NETWORK, null, msgs);
			msgs = basicSetNetwork(newNetwork, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGINS__NETWORK, newNetwork, newNetwork));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LoginPackage.LOGINS__LOGIN:
				return ((InternalEList)getLogin()).basicRemove(otherEnd, msgs);
			case LoginPackage.LOGINS__NETWORK:
				return basicSetNetwork(null, msgs);
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
			case LoginPackage.LOGINS__LOGIN:
				return getLogin();
			case LoginPackage.LOGINS__LAST_LOGIN:
				return getLastLogin();
			case LoginPackage.LOGINS__NETWORK:
				return getNetwork();
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
			case LoginPackage.LOGINS__LOGIN:
				getLogin().clear();
				getLogin().addAll((Collection)newValue);
				return;
			case LoginPackage.LOGINS__LAST_LOGIN:
				setLastLogin((String)newValue);
				return;
			case LoginPackage.LOGINS__NETWORK:
				setNetwork((LoginOption)newValue);
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
			case LoginPackage.LOGINS__LOGIN:
				getLogin().clear();
				return;
			case LoginPackage.LOGINS__LAST_LOGIN:
				setLastLogin(LAST_LOGIN_EDEFAULT);
				return;
			case LoginPackage.LOGINS__NETWORK:
				setNetwork((LoginOption)null);
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
			case LoginPackage.LOGINS__LOGIN:
				return login != null && !login.isEmpty();
			case LoginPackage.LOGINS__LAST_LOGIN:
				return LAST_LOGIN_EDEFAULT == null ? lastLogin != null : !LAST_LOGIN_EDEFAULT.equals(lastLogin);
			case LoginPackage.LOGINS__NETWORK:
				return network != null;
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
		result.append(" (lastLogin: ");
		result.append(lastLogin);
		result.append(')');
		return result.toString();
	}

} //LoginsImpl
