/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Op Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getOpType()
 * @model
 * @generated
 */
public final class OpType extends AbstractEnumerator {
	/**
	 * The '<em><b>Always</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Always</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ALWAYS_LITERAL
	 * @model name="Always"
	 * @generated
	 * @ordered
	 */
	public static final int ALWAYS = 0;

	/**
	 * The '<em><b>Never</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Never</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NEVER_LITERAL
	 * @model name="Never"
	 * @generated
	 * @ordered
	 */
	public static final int NEVER = 1;

	/**
	 * The '<em><b>Prompt</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Prompt</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROMPT_LITERAL
	 * @model name="Prompt"
	 * @generated
	 * @ordered
	 */
	public static final int PROMPT = 2;

	/**
	 * The '<em><b>Always</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALWAYS
	 * @generated
	 * @ordered
	 */
	public static final OpType ALWAYS_LITERAL = new OpType(ALWAYS, "Always", "Always");

	/**
	 * The '<em><b>Never</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NEVER
	 * @generated
	 * @ordered
	 */
	public static final OpType NEVER_LITERAL = new OpType(NEVER, "Never", "Never");

	/**
	 * The '<em><b>Prompt</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROMPT
	 * @generated
	 * @ordered
	 */
	public static final OpType PROMPT_LITERAL = new OpType(PROMPT, "Prompt", "Prompt");

	/**
	 * An array of all the '<em><b>Op Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final OpType[] VALUES_ARRAY =
		new OpType[] {
			ALWAYS_LITERAL,
			NEVER_LITERAL,
			PROMPT_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Op Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Op Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OpType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OpType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Op Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OpType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OpType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Op Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OpType get(int value) {
		switch (value) {
			case ALWAYS: return ALWAYS_LITERAL;
			case NEVER: return NEVER_LITERAL;
			case PROMPT: return PROMPT_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private OpType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //OpType
