/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.login.impl;

import edu.tsinghua.lumaqq.ecore.login.Login;
import edu.tsinghua.lumaqq.ecore.login.LoginPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Login</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginImpl#isAutoLogin <em>Auto Login</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginImpl#isLoginHidden <em>Login Hidden</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginImpl#getQq <em>Qq</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.login.impl.LoginImpl#isRememberPassword <em>Remember Password</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoginImpl extends EObjectImpl implements Login {
	/**
	 * The default value of the '{@link #isAutoLogin() <em>Auto Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoLogin()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTO_LOGIN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAutoLogin() <em>Auto Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoLogin()
	 * @generated
	 * @ordered
	 */
	protected boolean autoLogin = AUTO_LOGIN_EDEFAULT;

	/**
	 * This is true if the Auto Login attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean autoLoginESet = false;

	/**
	 * The default value of the '{@link #isLoginHidden() <em>Login Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoginHidden()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOGIN_HIDDEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLoginHidden() <em>Login Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoginHidden()
	 * @generated
	 * @ordered
	 */
	protected boolean loginHidden = LOGIN_HIDDEN_EDEFAULT;

	/**
	 * This is true if the Login Hidden attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean loginHiddenESet = false;

	/**
	 * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected String password = PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #getQq() <em>Qq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQq()
	 * @generated
	 * @ordered
	 */
	protected static final String QQ_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQq() <em>Qq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQq()
	 * @generated
	 * @ordered
	 */
	protected String qq = QQ_EDEFAULT;

	/**
	 * The default value of the '{@link #isRememberPassword() <em>Remember Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRememberPassword()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REMEMBER_PASSWORD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRememberPassword() <em>Remember Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRememberPassword()
	 * @generated
	 * @ordered
	 */
	protected boolean rememberPassword = REMEMBER_PASSWORD_EDEFAULT;

	/**
	 * This is true if the Remember Password attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean rememberPasswordESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoginImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LoginPackage.Literals.LOGIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAutoLogin() {
		return autoLogin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoLogin(boolean newAutoLogin) {
		boolean oldAutoLogin = autoLogin;
		autoLogin = newAutoLogin;
		boolean oldAutoLoginESet = autoLoginESet;
		autoLoginESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__AUTO_LOGIN, oldAutoLogin, autoLogin, !oldAutoLoginESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAutoLogin() {
		boolean oldAutoLogin = autoLogin;
		boolean oldAutoLoginESet = autoLoginESet;
		autoLogin = AUTO_LOGIN_EDEFAULT;
		autoLoginESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, LoginPackage.LOGIN__AUTO_LOGIN, oldAutoLogin, AUTO_LOGIN_EDEFAULT, oldAutoLoginESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAutoLogin() {
		return autoLoginESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLoginHidden() {
		return loginHidden;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoginHidden(boolean newLoginHidden) {
		boolean oldLoginHidden = loginHidden;
		loginHidden = newLoginHidden;
		boolean oldLoginHiddenESet = loginHiddenESet;
		loginHiddenESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__LOGIN_HIDDEN, oldLoginHidden, loginHidden, !oldLoginHiddenESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLoginHidden() {
		boolean oldLoginHidden = loginHidden;
		boolean oldLoginHiddenESet = loginHiddenESet;
		loginHidden = LOGIN_HIDDEN_EDEFAULT;
		loginHiddenESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, LoginPackage.LOGIN__LOGIN_HIDDEN, oldLoginHidden, LOGIN_HIDDEN_EDEFAULT, oldLoginHiddenESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLoginHidden() {
		return loginHiddenESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassword(String newPassword) {
		String oldPassword = password;
		password = newPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__PASSWORD, oldPassword, password));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQq(String newQq) {
		String oldQq = qq;
		qq = newQq;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__QQ, oldQq, qq));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRememberPassword() {
		return rememberPassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRememberPassword(boolean newRememberPassword) {
		boolean oldRememberPassword = rememberPassword;
		rememberPassword = newRememberPassword;
		boolean oldRememberPasswordESet = rememberPasswordESet;
		rememberPasswordESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__REMEMBER_PASSWORD, oldRememberPassword, rememberPassword, !oldRememberPasswordESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRememberPassword() {
		boolean oldRememberPassword = rememberPassword;
		boolean oldRememberPasswordESet = rememberPasswordESet;
		rememberPassword = REMEMBER_PASSWORD_EDEFAULT;
		rememberPasswordESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, LoginPackage.LOGIN__REMEMBER_PASSWORD, oldRememberPassword, REMEMBER_PASSWORD_EDEFAULT, oldRememberPasswordESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRememberPassword() {
		return rememberPasswordESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LoginPackage.LOGIN__AUTO_LOGIN:
				return isAutoLogin() ? Boolean.TRUE : Boolean.FALSE;
			case LoginPackage.LOGIN__LOGIN_HIDDEN:
				return isLoginHidden() ? Boolean.TRUE : Boolean.FALSE;
			case LoginPackage.LOGIN__PASSWORD:
				return getPassword();
			case LoginPackage.LOGIN__QQ:
				return getQq();
			case LoginPackage.LOGIN__REMEMBER_PASSWORD:
				return isRememberPassword() ? Boolean.TRUE : Boolean.FALSE;
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
			case LoginPackage.LOGIN__AUTO_LOGIN:
				setAutoLogin(((Boolean)newValue).booleanValue());
				return;
			case LoginPackage.LOGIN__LOGIN_HIDDEN:
				setLoginHidden(((Boolean)newValue).booleanValue());
				return;
			case LoginPackage.LOGIN__PASSWORD:
				setPassword((String)newValue);
				return;
			case LoginPackage.LOGIN__QQ:
				setQq((String)newValue);
				return;
			case LoginPackage.LOGIN__REMEMBER_PASSWORD:
				setRememberPassword(((Boolean)newValue).booleanValue());
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
			case LoginPackage.LOGIN__AUTO_LOGIN:
				unsetAutoLogin();
				return;
			case LoginPackage.LOGIN__LOGIN_HIDDEN:
				unsetLoginHidden();
				return;
			case LoginPackage.LOGIN__PASSWORD:
				setPassword(PASSWORD_EDEFAULT);
				return;
			case LoginPackage.LOGIN__QQ:
				setQq(QQ_EDEFAULT);
				return;
			case LoginPackage.LOGIN__REMEMBER_PASSWORD:
				unsetRememberPassword();
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
			case LoginPackage.LOGIN__AUTO_LOGIN:
				return isSetAutoLogin();
			case LoginPackage.LOGIN__LOGIN_HIDDEN:
				return isSetLoginHidden();
			case LoginPackage.LOGIN__PASSWORD:
				return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
			case LoginPackage.LOGIN__QQ:
				return QQ_EDEFAULT == null ? qq != null : !QQ_EDEFAULT.equals(qq);
			case LoginPackage.LOGIN__REMEMBER_PASSWORD:
				return isSetRememberPassword();
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
		result.append(" (autoLogin: ");
		if (autoLoginESet) result.append(autoLogin); else result.append("<unset>");
		result.append(", loginHidden: ");
		if (loginHiddenESet) result.append(loginHidden); else result.append("<unset>");
		result.append(", password: ");
		result.append(password);
		result.append(", qq: ");
		result.append(qq);
		result.append(", rememberPassword: ");
		if (rememberPasswordESet) result.append(rememberPassword); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //LoginImpl
