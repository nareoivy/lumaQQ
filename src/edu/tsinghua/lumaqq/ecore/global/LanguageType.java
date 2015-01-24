/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.global;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Language Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.global.GlobalPackage#getLanguageType()
 * @model
 * @generated
 */
public final class LanguageType extends AbstractEnumerator {
	/**
	 * The '<em><b>Zh</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Zh</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ZH_LITERAL
	 * @model name="zh"
	 * @generated
	 * @ordered
	 */
	public static final int ZH = 0;

	/**
	 * The '<em><b>En</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>En</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EN_LITERAL
	 * @model name="en"
	 * @generated
	 * @ordered
	 */
	public static final int EN = 1;

	/**
	 * The '<em><b>Zh</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ZH
	 * @generated
	 * @ordered
	 */
	public static final LanguageType ZH_LITERAL = new LanguageType(ZH, "zh", "zh");

	/**
	 * The '<em><b>En</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EN
	 * @generated
	 * @ordered
	 */
	public static final LanguageType EN_LITERAL = new LanguageType(EN, "en", "en");

	/**
	 * An array of all the '<em><b>Language Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final LanguageType[] VALUES_ARRAY =
		new LanguageType[] {
			ZH_LITERAL,
			EN_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Language Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Language Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LanguageType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LanguageType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Language Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LanguageType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LanguageType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Language Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LanguageType get(int value) {
		switch (value) {
			case ZH: return ZH_LITERAL;
			case EN: return EN_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private LanguageType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //LanguageType
