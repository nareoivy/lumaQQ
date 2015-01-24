/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Proxy Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#getProxyType()
 * @model
 * @generated
 */
public final class ProxyType extends AbstractEnumerator {
	/**
	 * The '<em><b>None</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>None</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NONE_LITERAL
	 * @model name="None"
	 * @generated
	 * @ordered
	 */
	public static final int NONE = 0;

	/**
	 * The '<em><b>Http</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Http</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HTTP_LITERAL
	 * @model name="Http"
	 * @generated
	 * @ordered
	 */
	public static final int HTTP = 1;

	/**
	 * The '<em><b>Socks5</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Socks5</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SOCKS5_LITERAL
	 * @model name="Socks5"
	 * @generated
	 * @ordered
	 */
	public static final int SOCKS5 = 2;

	/**
	 * The '<em><b>None</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE
	 * @generated
	 * @ordered
	 */
	public static final ProxyType NONE_LITERAL = new ProxyType(NONE, "None", "None");

	/**
	 * The '<em><b>Http</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HTTP
	 * @generated
	 * @ordered
	 */
	public static final ProxyType HTTP_LITERAL = new ProxyType(HTTP, "Http", "Http");

	/**
	 * The '<em><b>Socks5</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SOCKS5
	 * @generated
	 * @ordered
	 */
	public static final ProxyType SOCKS5_LITERAL = new ProxyType(SOCKS5, "Socks5", "Socks5");

	/**
	 * An array of all the '<em><b>Proxy Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ProxyType[] VALUES_ARRAY =
		new ProxyType[] {
			NONE_LITERAL,
			HTTP_LITERAL,
			SOCKS5_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Proxy Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Proxy Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProxyType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProxyType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Proxy Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProxyType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProxyType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Proxy Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProxyType get(int value) {
		switch (value) {
			case NONE: return NONE_LITERAL;
			case HTTP: return HTTP_LITERAL;
			case SOCKS5: return SOCKS5_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ProxyType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //ProxyType
