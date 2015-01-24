/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.impl;

import edu.tsinghua.lumaqq.ecore.EcorePackage;
import edu.tsinghua.lumaqq.ecore.LoginOption;
import edu.tsinghua.lumaqq.ecore.ProxyType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Login Option</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl#isAutoSelect <em>Auto Select</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl#getProxyPassword <em>Proxy Password</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl#getProxyPort <em>Proxy Port</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl#getProxyServer <em>Proxy Server</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl#getProxyType <em>Proxy Type</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl#getProxyUsername <em>Proxy Username</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl#getServer <em>Server</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl#getTcpPort <em>Tcp Port</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.impl.LoginOptionImpl#isUseTcp <em>Use Tcp</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoginOptionImpl extends EObjectImpl implements LoginOption {
	/**
	 * The default value of the '{@link #isAutoSelect() <em>Auto Select</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoSelect()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTO_SELECT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAutoSelect() <em>Auto Select</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoSelect()
	 * @generated
	 * @ordered
	 */
	protected boolean autoSelect = AUTO_SELECT_EDEFAULT;

	/**
	 * This is true if the Auto Select attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean autoSelectESet = false;

	/**
	 * The default value of the '{@link #getProxyPassword() <em>Proxy Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PROXY_PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProxyPassword() <em>Proxy Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyPassword()
	 * @generated
	 * @ordered
	 */
	protected String proxyPassword = PROXY_PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #getProxyPort() <em>Proxy Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyPort()
	 * @generated
	 * @ordered
	 */
	protected static final int PROXY_PORT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getProxyPort() <em>Proxy Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyPort()
	 * @generated
	 * @ordered
	 */
	protected int proxyPort = PROXY_PORT_EDEFAULT;

	/**
	 * This is true if the Proxy Port attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean proxyPortESet = false;

	/**
	 * The default value of the '{@link #getProxyServer() <em>Proxy Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyServer()
	 * @generated
	 * @ordered
	 */
	protected static final String PROXY_SERVER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProxyServer() <em>Proxy Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyServer()
	 * @generated
	 * @ordered
	 */
	protected String proxyServer = PROXY_SERVER_EDEFAULT;

	/**
	 * The default value of the '{@link #getProxyType() <em>Proxy Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyType()
	 * @generated
	 * @ordered
	 */
	protected static final ProxyType PROXY_TYPE_EDEFAULT = ProxyType.NONE_LITERAL;

	/**
	 * The cached value of the '{@link #getProxyType() <em>Proxy Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyType()
	 * @generated
	 * @ordered
	 */
	protected ProxyType proxyType = PROXY_TYPE_EDEFAULT;

	/**
	 * This is true if the Proxy Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean proxyTypeESet = false;

	/**
	 * The default value of the '{@link #getProxyUsername() <em>Proxy Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyUsername()
	 * @generated
	 * @ordered
	 */
	protected static final String PROXY_USERNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProxyUsername() <em>Proxy Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyUsername()
	 * @generated
	 * @ordered
	 */
	protected String proxyUsername = PROXY_USERNAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getServer() <em>Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServer()
	 * @generated
	 * @ordered
	 */
	protected static final String SERVER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getServer() <em>Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServer()
	 * @generated
	 * @ordered
	 */
	protected String server = SERVER_EDEFAULT;

	/**
	 * The default value of the '{@link #getTcpPort() <em>Tcp Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTcpPort()
	 * @generated
	 * @ordered
	 */
	protected static final int TCP_PORT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTcpPort() <em>Tcp Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTcpPort()
	 * @generated
	 * @ordered
	 */
	protected int tcpPort = TCP_PORT_EDEFAULT;

	/**
	 * This is true if the Tcp Port attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean tcpPortESet = false;

	/**
	 * The default value of the '{@link #isUseTcp() <em>Use Tcp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseTcp()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_TCP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseTcp() <em>Use Tcp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseTcp()
	 * @generated
	 * @ordered
	 */
	protected boolean useTcp = USE_TCP_EDEFAULT;

	/**
	 * This is true if the Use Tcp attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean useTcpESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoginOptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EcorePackage.Literals.LOGIN_OPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAutoSelect() {
		return autoSelect;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoSelect(boolean newAutoSelect) {
		boolean oldAutoSelect = autoSelect;
		autoSelect = newAutoSelect;
		boolean oldAutoSelectESet = autoSelectESet;
		autoSelectESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EcorePackage.LOGIN_OPTION__AUTO_SELECT, oldAutoSelect, autoSelect, !oldAutoSelectESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAutoSelect() {
		boolean oldAutoSelect = autoSelect;
		boolean oldAutoSelectESet = autoSelectESet;
		autoSelect = AUTO_SELECT_EDEFAULT;
		autoSelectESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, EcorePackage.LOGIN_OPTION__AUTO_SELECT, oldAutoSelect, AUTO_SELECT_EDEFAULT, oldAutoSelectESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAutoSelect() {
		return autoSelectESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProxyPassword() {
		return proxyPassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProxyPassword(String newProxyPassword) {
		String oldProxyPassword = proxyPassword;
		proxyPassword = newProxyPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EcorePackage.LOGIN_OPTION__PROXY_PASSWORD, oldProxyPassword, proxyPassword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getProxyPort() {
		return proxyPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProxyPort(int newProxyPort) {
		int oldProxyPort = proxyPort;
		proxyPort = newProxyPort;
		boolean oldProxyPortESet = proxyPortESet;
		proxyPortESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EcorePackage.LOGIN_OPTION__PROXY_PORT, oldProxyPort, proxyPort, !oldProxyPortESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetProxyPort() {
		int oldProxyPort = proxyPort;
		boolean oldProxyPortESet = proxyPortESet;
		proxyPort = PROXY_PORT_EDEFAULT;
		proxyPortESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, EcorePackage.LOGIN_OPTION__PROXY_PORT, oldProxyPort, PROXY_PORT_EDEFAULT, oldProxyPortESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetProxyPort() {
		return proxyPortESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProxyServer() {
		return proxyServer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProxyServer(String newProxyServer) {
		String oldProxyServer = proxyServer;
		proxyServer = newProxyServer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EcorePackage.LOGIN_OPTION__PROXY_SERVER, oldProxyServer, proxyServer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProxyType getProxyType() {
		return proxyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProxyType(ProxyType newProxyType) {
		ProxyType oldProxyType = proxyType;
		proxyType = newProxyType == null ? PROXY_TYPE_EDEFAULT : newProxyType;
		boolean oldProxyTypeESet = proxyTypeESet;
		proxyTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EcorePackage.LOGIN_OPTION__PROXY_TYPE, oldProxyType, proxyType, !oldProxyTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetProxyType() {
		ProxyType oldProxyType = proxyType;
		boolean oldProxyTypeESet = proxyTypeESet;
		proxyType = PROXY_TYPE_EDEFAULT;
		proxyTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, EcorePackage.LOGIN_OPTION__PROXY_TYPE, oldProxyType, PROXY_TYPE_EDEFAULT, oldProxyTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetProxyType() {
		return proxyTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProxyUsername() {
		return proxyUsername;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProxyUsername(String newProxyUsername) {
		String oldProxyUsername = proxyUsername;
		proxyUsername = newProxyUsername;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EcorePackage.LOGIN_OPTION__PROXY_USERNAME, oldProxyUsername, proxyUsername));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getServer() {
		return server;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServer(String newServer) {
		String oldServer = server;
		server = newServer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EcorePackage.LOGIN_OPTION__SERVER, oldServer, server));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTcpPort() {
		return tcpPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTcpPort(int newTcpPort) {
		int oldTcpPort = tcpPort;
		tcpPort = newTcpPort;
		boolean oldTcpPortESet = tcpPortESet;
		tcpPortESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EcorePackage.LOGIN_OPTION__TCP_PORT, oldTcpPort, tcpPort, !oldTcpPortESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTcpPort() {
		int oldTcpPort = tcpPort;
		boolean oldTcpPortESet = tcpPortESet;
		tcpPort = TCP_PORT_EDEFAULT;
		tcpPortESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, EcorePackage.LOGIN_OPTION__TCP_PORT, oldTcpPort, TCP_PORT_EDEFAULT, oldTcpPortESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTcpPort() {
		return tcpPortESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseTcp() {
		return useTcp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseTcp(boolean newUseTcp) {
		boolean oldUseTcp = useTcp;
		useTcp = newUseTcp;
		boolean oldUseTcpESet = useTcpESet;
		useTcpESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EcorePackage.LOGIN_OPTION__USE_TCP, oldUseTcp, useTcp, !oldUseTcpESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetUseTcp() {
		boolean oldUseTcp = useTcp;
		boolean oldUseTcpESet = useTcpESet;
		useTcp = USE_TCP_EDEFAULT;
		useTcpESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, EcorePackage.LOGIN_OPTION__USE_TCP, oldUseTcp, USE_TCP_EDEFAULT, oldUseTcpESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetUseTcp() {
		return useTcpESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EcorePackage.LOGIN_OPTION__AUTO_SELECT:
				return isAutoSelect() ? Boolean.TRUE : Boolean.FALSE;
			case EcorePackage.LOGIN_OPTION__PROXY_PASSWORD:
				return getProxyPassword();
			case EcorePackage.LOGIN_OPTION__PROXY_PORT:
				return new Integer(getProxyPort());
			case EcorePackage.LOGIN_OPTION__PROXY_SERVER:
				return getProxyServer();
			case EcorePackage.LOGIN_OPTION__PROXY_TYPE:
				return getProxyType();
			case EcorePackage.LOGIN_OPTION__PROXY_USERNAME:
				return getProxyUsername();
			case EcorePackage.LOGIN_OPTION__SERVER:
				return getServer();
			case EcorePackage.LOGIN_OPTION__TCP_PORT:
				return new Integer(getTcpPort());
			case EcorePackage.LOGIN_OPTION__USE_TCP:
				return isUseTcp() ? Boolean.TRUE : Boolean.FALSE;
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
			case EcorePackage.LOGIN_OPTION__AUTO_SELECT:
				setAutoSelect(((Boolean)newValue).booleanValue());
				return;
			case EcorePackage.LOGIN_OPTION__PROXY_PASSWORD:
				setProxyPassword((String)newValue);
				return;
			case EcorePackage.LOGIN_OPTION__PROXY_PORT:
				setProxyPort(((Integer)newValue).intValue());
				return;
			case EcorePackage.LOGIN_OPTION__PROXY_SERVER:
				setProxyServer((String)newValue);
				return;
			case EcorePackage.LOGIN_OPTION__PROXY_TYPE:
				setProxyType((ProxyType)newValue);
				return;
			case EcorePackage.LOGIN_OPTION__PROXY_USERNAME:
				setProxyUsername((String)newValue);
				return;
			case EcorePackage.LOGIN_OPTION__SERVER:
				setServer((String)newValue);
				return;
			case EcorePackage.LOGIN_OPTION__TCP_PORT:
				setTcpPort(((Integer)newValue).intValue());
				return;
			case EcorePackage.LOGIN_OPTION__USE_TCP:
				setUseTcp(((Boolean)newValue).booleanValue());
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
			case EcorePackage.LOGIN_OPTION__AUTO_SELECT:
				unsetAutoSelect();
				return;
			case EcorePackage.LOGIN_OPTION__PROXY_PASSWORD:
				setProxyPassword(PROXY_PASSWORD_EDEFAULT);
				return;
			case EcorePackage.LOGIN_OPTION__PROXY_PORT:
				unsetProxyPort();
				return;
			case EcorePackage.LOGIN_OPTION__PROXY_SERVER:
				setProxyServer(PROXY_SERVER_EDEFAULT);
				return;
			case EcorePackage.LOGIN_OPTION__PROXY_TYPE:
				unsetProxyType();
				return;
			case EcorePackage.LOGIN_OPTION__PROXY_USERNAME:
				setProxyUsername(PROXY_USERNAME_EDEFAULT);
				return;
			case EcorePackage.LOGIN_OPTION__SERVER:
				setServer(SERVER_EDEFAULT);
				return;
			case EcorePackage.LOGIN_OPTION__TCP_PORT:
				unsetTcpPort();
				return;
			case EcorePackage.LOGIN_OPTION__USE_TCP:
				unsetUseTcp();
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
			case EcorePackage.LOGIN_OPTION__AUTO_SELECT:
				return isSetAutoSelect();
			case EcorePackage.LOGIN_OPTION__PROXY_PASSWORD:
				return PROXY_PASSWORD_EDEFAULT == null ? proxyPassword != null : !PROXY_PASSWORD_EDEFAULT.equals(proxyPassword);
			case EcorePackage.LOGIN_OPTION__PROXY_PORT:
				return isSetProxyPort();
			case EcorePackage.LOGIN_OPTION__PROXY_SERVER:
				return PROXY_SERVER_EDEFAULT == null ? proxyServer != null : !PROXY_SERVER_EDEFAULT.equals(proxyServer);
			case EcorePackage.LOGIN_OPTION__PROXY_TYPE:
				return isSetProxyType();
			case EcorePackage.LOGIN_OPTION__PROXY_USERNAME:
				return PROXY_USERNAME_EDEFAULT == null ? proxyUsername != null : !PROXY_USERNAME_EDEFAULT.equals(proxyUsername);
			case EcorePackage.LOGIN_OPTION__SERVER:
				return SERVER_EDEFAULT == null ? server != null : !SERVER_EDEFAULT.equals(server);
			case EcorePackage.LOGIN_OPTION__TCP_PORT:
				return isSetTcpPort();
			case EcorePackage.LOGIN_OPTION__USE_TCP:
				return isSetUseTcp();
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
		result.append(" (autoSelect: ");
		if (autoSelectESet) result.append(autoSelect); else result.append("<unset>");
		result.append(", proxyPassword: ");
		result.append(proxyPassword);
		result.append(", proxyPort: ");
		if (proxyPortESet) result.append(proxyPort); else result.append("<unset>");
		result.append(", proxyServer: ");
		result.append(proxyServer);
		result.append(", proxyType: ");
		if (proxyTypeESet) result.append(proxyType); else result.append("<unset>");
		result.append(", proxyUsername: ");
		result.append(proxyUsername);
		result.append(", server: ");
		result.append(server);
		result.append(", tcpPort: ");
		if (tcpPortESet) result.append(tcpPort); else result.append("<unset>");
		result.append(", useTcp: ");
		if (useTcpESet) result.append(useTcp); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //LoginOptionImpl
