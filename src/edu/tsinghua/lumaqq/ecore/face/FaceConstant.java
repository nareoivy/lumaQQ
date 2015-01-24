/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.face;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Constant</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.face.FacePackage#getFaceConstant()
 * @model
 * @generated
 */
public final class FaceConstant extends AbstractEnumerator {
	/**
	 * The '<em><b>DEFAULT GROUP ID</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DEFAULT GROUP ID</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEFAULT_GROUP_ID_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DEFAULT_GROUP_ID = 0;

	/**
	 * The '<em><b>RECEIVED GROUP ID</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RECEIVED GROUP ID</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RECEIVED_GROUP_ID_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RECEIVED_GROUP_ID = 1;

	/**
	 * The '<em><b>CUSTOM HEAD GROUP ID</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CUSTOM HEAD GROUP ID</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_HEAD_GROUP_ID_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CUSTOM_HEAD_GROUP_ID = 2;

	/**
	 * The '<em><b>DEFAULT GROUP ID</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEFAULT_GROUP_ID
	 * @generated
	 * @ordered
	 */
	public static final FaceConstant DEFAULT_GROUP_ID_LITERAL = new FaceConstant(DEFAULT_GROUP_ID, "DEFAULT_GROUP_ID", "DEFAULT_GROUP_ID");

	/**
	 * The '<em><b>RECEIVED GROUP ID</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RECEIVED_GROUP_ID
	 * @generated
	 * @ordered
	 */
	public static final FaceConstant RECEIVED_GROUP_ID_LITERAL = new FaceConstant(RECEIVED_GROUP_ID, "RECEIVED_GROUP_ID", "RECEIVED_GROUP_ID");

	/**
	 * The '<em><b>CUSTOM HEAD GROUP ID</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_HEAD_GROUP_ID
	 * @generated
	 * @ordered
	 */
	public static final FaceConstant CUSTOM_HEAD_GROUP_ID_LITERAL = new FaceConstant(CUSTOM_HEAD_GROUP_ID, "CUSTOM_HEAD_GROUP_ID", "CUSTOM_HEAD_GROUP_ID");

	/**
	 * An array of all the '<em><b>Constant</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FaceConstant[] VALUES_ARRAY =
		new FaceConstant[] {
			DEFAULT_GROUP_ID_LITERAL,
			RECEIVED_GROUP_ID_LITERAL,
			CUSTOM_HEAD_GROUP_ID_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Constant</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Constant</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FaceConstant get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FaceConstant result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Constant</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FaceConstant getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FaceConstant result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Constant</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FaceConstant get(int value) {
		switch (value) {
			case DEFAULT_GROUP_ID: return DEFAULT_GROUP_ID_LITERAL;
			case RECEIVED_GROUP_ID: return RECEIVED_GROUP_ID_LITERAL;
			case CUSTOM_HEAD_GROUP_ID: return CUSTOM_HEAD_GROUP_ID_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private FaceConstant(int value, String name, String literal) {
		super(value, name, literal);
	}

} //FaceConstant
