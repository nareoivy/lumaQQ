/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Login Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.LoginOption#isAutoSelect <em>Auto Select</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyPassword <em>Proxy Password</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyPort <em>Proxy Port</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyServer <em>Proxy Server</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyType <em>Proxy Type</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyUsername <em>Proxy Username</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.LoginOption#getServer <em>Server</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.LoginOption#getTcpPort <em>Tcp Port</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.LoginOption#isUseTcp <em>Use Tcp</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getLoginOption()
 * @model
 * @generated
 */
public interface LoginOption extends EObject {
	/**
	 * Returns the value of the '<em><b>Auto Select</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Select</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Select</em>' attribute.
	 * @see #isSetAutoSelect()
	 * @see #unsetAutoSelect()
	 * @see #setAutoSelect(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getLoginOption_AutoSelect()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isAutoSelect();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#isAutoSelect <em>Auto Select</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Select</em>' attribute.
	 * @see #isSetAutoSelect()
	 * @see #unsetAutoSelect()
	 * @see #isAutoSelect()
	 * @generated
	 */
	void setAutoSelect(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#isAutoSelect <em>Auto Select</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAutoSelect()
	 * @see #isAutoSelect()
	 * @see #setAutoSelect(boolean)
	 * @generated
	 */
	void unsetAutoSelect();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#isAutoSelect <em>Auto Select</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Auto Select</em>' attribute is set.
	 * @see #unsetAutoSelect()
	 * @see #isAutoSelect()
	 * @see #setAutoSelect(boolean)
	 * @generated
	 */
	boolean isSetAutoSelect();

	/**
	 * Returns the value of the '<em><b>Proxy Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proxy Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proxy Password</em>' attribute.
	 * @see #setProxyPassword(String)
	 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getLoginOption_ProxyPassword()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getProxyPassword();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyPassword <em>Proxy Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proxy Password</em>' attribute.
	 * @see #getProxyPassword()
	 * @generated
	 */
	void setProxyPassword(String value);

	/**
	 * Returns the value of the '<em><b>Proxy Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proxy Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proxy Port</em>' attribute.
	 * @see #isSetProxyPort()
	 * @see #unsetProxyPort()
	 * @see #setProxyPort(int)
	 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getLoginOption_ProxyPort()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getProxyPort();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyPort <em>Proxy Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proxy Port</em>' attribute.
	 * @see #isSetProxyPort()
	 * @see #unsetProxyPort()
	 * @see #getProxyPort()
	 * @generated
	 */
	void setProxyPort(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyPort <em>Proxy Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetProxyPort()
	 * @see #getProxyPort()
	 * @see #setProxyPort(int)
	 * @generated
	 */
	void unsetProxyPort();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyPort <em>Proxy Port</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Proxy Port</em>' attribute is set.
	 * @see #unsetProxyPort()
	 * @see #getProxyPort()
	 * @see #setProxyPort(int)
	 * @generated
	 */
	boolean isSetProxyPort();

	/**
	 * Returns the value of the '<em><b>Proxy Server</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proxy Server</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proxy Server</em>' attribute.
	 * @see #setProxyServer(String)
	 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getLoginOption_ProxyServer()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getProxyServer();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyServer <em>Proxy Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proxy Server</em>' attribute.
	 * @see #getProxyServer()
	 * @generated
	 */
	void setProxyServer(String value);

	/**
	 * Returns the value of the '<em><b>Proxy Type</b></em>' attribute.
	 * The default value is <code>"None"</code>.
	 * The literals are from the enumeration {@link edu.tsinghua.lumaqq.ecore.ProxyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proxy Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proxy Type</em>' attribute.
	 * @see edu.tsinghua.lumaqq.ecore.ProxyType
	 * @see #isSetProxyType()
	 * @see #unsetProxyType()
	 * @see #setProxyType(ProxyType)
	 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getLoginOption_ProxyType()
	 * @model default="None" unique="false" unsettable="true" required="true"
	 * @generated
	 */
	ProxyType getProxyType();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyType <em>Proxy Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proxy Type</em>' attribute.
	 * @see edu.tsinghua.lumaqq.ecore.ProxyType
	 * @see #isSetProxyType()
	 * @see #unsetProxyType()
	 * @see #getProxyType()
	 * @generated
	 */
	void setProxyType(ProxyType value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyType <em>Proxy Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetProxyType()
	 * @see #getProxyType()
	 * @see #setProxyType(ProxyType)
	 * @generated
	 */
	void unsetProxyType();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyType <em>Proxy Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Proxy Type</em>' attribute is set.
	 * @see #unsetProxyType()
	 * @see #getProxyType()
	 * @see #setProxyType(ProxyType)
	 * @generated
	 */
	boolean isSetProxyType();

	/**
	 * Returns the value of the '<em><b>Proxy Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proxy Username</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proxy Username</em>' attribute.
	 * @see #setProxyUsername(String)
	 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getLoginOption_ProxyUsername()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getProxyUsername();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getProxyUsername <em>Proxy Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proxy Username</em>' attribute.
	 * @see #getProxyUsername()
	 * @generated
	 */
	void setProxyUsername(String value);

	/**
	 * Returns the value of the '<em><b>Server</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Server</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Server</em>' attribute.
	 * @see #setServer(String)
	 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getLoginOption_Server()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getServer();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getServer <em>Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Server</em>' attribute.
	 * @see #getServer()
	 * @generated
	 */
	void setServer(String value);

	/**
	 * Returns the value of the '<em><b>Tcp Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tcp Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tcp Port</em>' attribute.
	 * @see #isSetTcpPort()
	 * @see #unsetTcpPort()
	 * @see #setTcpPort(int)
	 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getLoginOption_TcpPort()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getTcpPort();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getTcpPort <em>Tcp Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tcp Port</em>' attribute.
	 * @see #isSetTcpPort()
	 * @see #unsetTcpPort()
	 * @see #getTcpPort()
	 * @generated
	 */
	void setTcpPort(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getTcpPort <em>Tcp Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTcpPort()
	 * @see #getTcpPort()
	 * @see #setTcpPort(int)
	 * @generated
	 */
	void unsetTcpPort();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#getTcpPort <em>Tcp Port</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Tcp Port</em>' attribute is set.
	 * @see #unsetTcpPort()
	 * @see #getTcpPort()
	 * @see #setTcpPort(int)
	 * @generated
	 */
	boolean isSetTcpPort();

	/**
	 * Returns the value of the '<em><b>Use Tcp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use Tcp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use Tcp</em>' attribute.
	 * @see #isSetUseTcp()
	 * @see #unsetUseTcp()
	 * @see #setUseTcp(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getLoginOption_UseTcp()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isUseTcp();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#isUseTcp <em>Use Tcp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Tcp</em>' attribute.
	 * @see #isSetUseTcp()
	 * @see #unsetUseTcp()
	 * @see #isUseTcp()
	 * @generated
	 */
	void setUseTcp(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#isUseTcp <em>Use Tcp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUseTcp()
	 * @see #isUseTcp()
	 * @see #setUseTcp(boolean)
	 * @generated
	 */
	void unsetUseTcp();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.LoginOption#isUseTcp <em>Use Tcp</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Use Tcp</em>' attribute is set.
	 * @see #unsetUseTcp()
	 * @see #isUseTcp()
	 * @see #setUseTcp(boolean)
	 * @generated
	 */
	boolean isSetUseTcp();

} // LoginOption
