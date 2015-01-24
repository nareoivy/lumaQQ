/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Message Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isAutoEject <em>Auto Eject</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isEnableSound <em>Enable Sound</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectStranger <em>Reject Stranger</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectTempSessionIM <em>Reject Temp Session IM</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInMessageMode <em>Use Enter In Message Mode</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInTalkMode <em>Use Enter In Talk Mode</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getMessageOption()
 * @model
 * @generated
 */
public interface MessageOption extends EObject {
	/**
	 * Returns the value of the '<em><b>Auto Eject</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Eject</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Eject</em>' attribute.
	 * @see #isSetAutoEject()
	 * @see #unsetAutoEject()
	 * @see #setAutoEject(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getMessageOption_AutoEject()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isAutoEject();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isAutoEject <em>Auto Eject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Eject</em>' attribute.
	 * @see #isSetAutoEject()
	 * @see #unsetAutoEject()
	 * @see #isAutoEject()
	 * @generated
	 */
	void setAutoEject(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isAutoEject <em>Auto Eject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAutoEject()
	 * @see #isAutoEject()
	 * @see #setAutoEject(boolean)
	 * @generated
	 */
	void unsetAutoEject();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isAutoEject <em>Auto Eject</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Auto Eject</em>' attribute is set.
	 * @see #unsetAutoEject()
	 * @see #isAutoEject()
	 * @see #setAutoEject(boolean)
	 * @generated
	 */
	boolean isSetAutoEject();

	/**
	 * Returns the value of the '<em><b>Enable Sound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable Sound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Sound</em>' attribute.
	 * @see #isSetEnableSound()
	 * @see #unsetEnableSound()
	 * @see #setEnableSound(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getMessageOption_EnableSound()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isEnableSound();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isEnableSound <em>Enable Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Sound</em>' attribute.
	 * @see #isSetEnableSound()
	 * @see #unsetEnableSound()
	 * @see #isEnableSound()
	 * @generated
	 */
	void setEnableSound(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isEnableSound <em>Enable Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetEnableSound()
	 * @see #isEnableSound()
	 * @see #setEnableSound(boolean)
	 * @generated
	 */
	void unsetEnableSound();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isEnableSound <em>Enable Sound</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Enable Sound</em>' attribute is set.
	 * @see #unsetEnableSound()
	 * @see #isEnableSound()
	 * @see #setEnableSound(boolean)
	 * @generated
	 */
	boolean isSetEnableSound();

	/**
	 * Returns the value of the '<em><b>Reject Stranger</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reject Stranger</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reject Stranger</em>' attribute.
	 * @see #isSetRejectStranger()
	 * @see #unsetRejectStranger()
	 * @see #setRejectStranger(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getMessageOption_RejectStranger()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isRejectStranger();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectStranger <em>Reject Stranger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reject Stranger</em>' attribute.
	 * @see #isSetRejectStranger()
	 * @see #unsetRejectStranger()
	 * @see #isRejectStranger()
	 * @generated
	 */
	void setRejectStranger(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectStranger <em>Reject Stranger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRejectStranger()
	 * @see #isRejectStranger()
	 * @see #setRejectStranger(boolean)
	 * @generated
	 */
	void unsetRejectStranger();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectStranger <em>Reject Stranger</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Reject Stranger</em>' attribute is set.
	 * @see #unsetRejectStranger()
	 * @see #isRejectStranger()
	 * @see #setRejectStranger(boolean)
	 * @generated
	 */
	boolean isSetRejectStranger();

	/**
	 * Returns the value of the '<em><b>Reject Temp Session IM</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reject Temp Session IM</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reject Temp Session IM</em>' attribute.
	 * @see #isSetRejectTempSessionIM()
	 * @see #unsetRejectTempSessionIM()
	 * @see #setRejectTempSessionIM(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getMessageOption_RejectTempSessionIM()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isRejectTempSessionIM();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectTempSessionIM <em>Reject Temp Session IM</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reject Temp Session IM</em>' attribute.
	 * @see #isSetRejectTempSessionIM()
	 * @see #unsetRejectTempSessionIM()
	 * @see #isRejectTempSessionIM()
	 * @generated
	 */
	void setRejectTempSessionIM(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectTempSessionIM <em>Reject Temp Session IM</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRejectTempSessionIM()
	 * @see #isRejectTempSessionIM()
	 * @see #setRejectTempSessionIM(boolean)
	 * @generated
	 */
	void unsetRejectTempSessionIM();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectTempSessionIM <em>Reject Temp Session IM</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Reject Temp Session IM</em>' attribute is set.
	 * @see #unsetRejectTempSessionIM()
	 * @see #isRejectTempSessionIM()
	 * @see #setRejectTempSessionIM(boolean)
	 * @generated
	 */
	boolean isSetRejectTempSessionIM();

	/**
	 * Returns the value of the '<em><b>Use Enter In Message Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use Enter In Message Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use Enter In Message Mode</em>' attribute.
	 * @see #isSetUseEnterInMessageMode()
	 * @see #unsetUseEnterInMessageMode()
	 * @see #setUseEnterInMessageMode(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getMessageOption_UseEnterInMessageMode()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isUseEnterInMessageMode();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInMessageMode <em>Use Enter In Message Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Enter In Message Mode</em>' attribute.
	 * @see #isSetUseEnterInMessageMode()
	 * @see #unsetUseEnterInMessageMode()
	 * @see #isUseEnterInMessageMode()
	 * @generated
	 */
	void setUseEnterInMessageMode(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInMessageMode <em>Use Enter In Message Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUseEnterInMessageMode()
	 * @see #isUseEnterInMessageMode()
	 * @see #setUseEnterInMessageMode(boolean)
	 * @generated
	 */
	void unsetUseEnterInMessageMode();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInMessageMode <em>Use Enter In Message Mode</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Use Enter In Message Mode</em>' attribute is set.
	 * @see #unsetUseEnterInMessageMode()
	 * @see #isUseEnterInMessageMode()
	 * @see #setUseEnterInMessageMode(boolean)
	 * @generated
	 */
	boolean isSetUseEnterInMessageMode();

	/**
	 * Returns the value of the '<em><b>Use Enter In Talk Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use Enter In Talk Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use Enter In Talk Mode</em>' attribute.
	 * @see #isSetUseEnterInTalkMode()
	 * @see #unsetUseEnterInTalkMode()
	 * @see #setUseEnterInTalkMode(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getMessageOption_UseEnterInTalkMode()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isUseEnterInTalkMode();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInTalkMode <em>Use Enter In Talk Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Enter In Talk Mode</em>' attribute.
	 * @see #isSetUseEnterInTalkMode()
	 * @see #unsetUseEnterInTalkMode()
	 * @see #isUseEnterInTalkMode()
	 * @generated
	 */
	void setUseEnterInTalkMode(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInTalkMode <em>Use Enter In Talk Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUseEnterInTalkMode()
	 * @see #isUseEnterInTalkMode()
	 * @see #setUseEnterInTalkMode(boolean)
	 * @generated
	 */
	void unsetUseEnterInTalkMode();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInTalkMode <em>Use Enter In Talk Mode</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Use Enter In Talk Mode</em>' attribute is set.
	 * @see #unsetUseEnterInTalkMode()
	 * @see #isUseEnterInTalkMode()
	 * @see #setUseEnterInTalkMode(boolean)
	 * @generated
	 */
	boolean isSetUseEnterInTalkMode();

} // MessageOption
