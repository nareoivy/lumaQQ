/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.group;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>XUser</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCardName <em>Card Name</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#isFemale <em>Female</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getHeadId <em>Head Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getLastMessageTime <em>Last Message Time</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getLevel <em>Level</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#isMember <em>Member</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getNick <em>Nick</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getOrganizationId <em>Organization Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#isPinned <em>Pinned</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getQq <em>Qq</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getSignature <em>Signature</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#isTalkMode <em>Talk Mode</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#isHasCustomHead <em>Has Custom Head</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadId <em>Custom Head Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getUserFlag <em>User Flag</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadTimestamp <em>Custom Head Timestamp</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getWindowX <em>Window X</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XUser#getWindowY <em>Window Y</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser()
 * @model
 * @generated
 */
public interface XUser extends EObject {
	/**
	 * Returns the value of the '<em><b>Card Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Card Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Card Name</em>' attribute.
	 * @see #isSetCardName()
	 * @see #unsetCardName()
	 * @see #setCardName(String)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_CardName()
	 * @model default="" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getCardName();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCardName <em>Card Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Card Name</em>' attribute.
	 * @see #isSetCardName()
	 * @see #unsetCardName()
	 * @see #getCardName()
	 * @generated
	 */
	void setCardName(String value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCardName <em>Card Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCardName()
	 * @see #getCardName()
	 * @see #setCardName(String)
	 * @generated
	 */
	void unsetCardName();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCardName <em>Card Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Card Name</em>' attribute is set.
	 * @see #unsetCardName()
	 * @see #getCardName()
	 * @see #setCardName(String)
	 * @generated
	 */
	boolean isSetCardName();

	/**
	 * Returns the value of the '<em><b>Female</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Female</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Female</em>' attribute.
	 * @see #isSetFemale()
	 * @see #unsetFemale()
	 * @see #setFemale(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_Female()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isFemale();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isFemale <em>Female</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Female</em>' attribute.
	 * @see #isSetFemale()
	 * @see #unsetFemale()
	 * @see #isFemale()
	 * @generated
	 */
	void setFemale(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isFemale <em>Female</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFemale()
	 * @see #isFemale()
	 * @see #setFemale(boolean)
	 * @generated
	 */
	void unsetFemale();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isFemale <em>Female</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Female</em>' attribute is set.
	 * @see #unsetFemale()
	 * @see #isFemale()
	 * @see #setFemale(boolean)
	 * @generated
	 */
	boolean isSetFemale();

	/**
	 * Returns the value of the '<em><b>Head Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Head Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Head Id</em>' attribute.
	 * @see #isSetHeadId()
	 * @see #unsetHeadId()
	 * @see #setHeadId(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_HeadId()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getHeadId();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getHeadId <em>Head Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Head Id</em>' attribute.
	 * @see #isSetHeadId()
	 * @see #unsetHeadId()
	 * @see #getHeadId()
	 * @generated
	 */
	void setHeadId(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getHeadId <em>Head Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHeadId()
	 * @see #getHeadId()
	 * @see #setHeadId(int)
	 * @generated
	 */
	void unsetHeadId();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getHeadId <em>Head Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Head Id</em>' attribute is set.
	 * @see #unsetHeadId()
	 * @see #getHeadId()
	 * @see #setHeadId(int)
	 * @generated
	 */
	boolean isSetHeadId();

	/**
	 * Returns the value of the '<em><b>Last Message Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Message Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Message Time</em>' attribute.
	 * @see #isSetLastMessageTime()
	 * @see #unsetLastMessageTime()
	 * @see #setLastMessageTime(long)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_LastMessageTime()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
	 * @generated
	 */
	long getLastMessageTime();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getLastMessageTime <em>Last Message Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Message Time</em>' attribute.
	 * @see #isSetLastMessageTime()
	 * @see #unsetLastMessageTime()
	 * @see #getLastMessageTime()
	 * @generated
	 */
	void setLastMessageTime(long value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getLastMessageTime <em>Last Message Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLastMessageTime()
	 * @see #getLastMessageTime()
	 * @see #setLastMessageTime(long)
	 * @generated
	 */
	void unsetLastMessageTime();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getLastMessageTime <em>Last Message Time</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Last Message Time</em>' attribute is set.
	 * @see #unsetLastMessageTime()
	 * @see #getLastMessageTime()
	 * @see #setLastMessageTime(long)
	 * @generated
	 */
	boolean isSetLastMessageTime();

	/**
	 * Returns the value of the '<em><b>Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Level</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Level</em>' attribute.
	 * @see #isSetLevel()
	 * @see #unsetLevel()
	 * @see #setLevel(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_Level()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getLevel();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getLevel <em>Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Level</em>' attribute.
	 * @see #isSetLevel()
	 * @see #unsetLevel()
	 * @see #getLevel()
	 * @generated
	 */
	void setLevel(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getLevel <em>Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLevel()
	 * @see #getLevel()
	 * @see #setLevel(int)
	 * @generated
	 */
	void unsetLevel();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getLevel <em>Level</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Level</em>' attribute is set.
	 * @see #unsetLevel()
	 * @see #getLevel()
	 * @see #setLevel(int)
	 * @generated
	 */
	boolean isSetLevel();

	/**
	 * Returns the value of the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Member</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Member</em>' attribute.
	 * @see #isSetMember()
	 * @see #unsetMember()
	 * @see #setMember(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_Member()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isMember();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isMember <em>Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Member</em>' attribute.
	 * @see #isSetMember()
	 * @see #unsetMember()
	 * @see #isMember()
	 * @generated
	 */
	void setMember(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isMember <em>Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMember()
	 * @see #isMember()
	 * @see #setMember(boolean)
	 * @generated
	 */
	void unsetMember();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isMember <em>Member</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Member</em>' attribute is set.
	 * @see #unsetMember()
	 * @see #isMember()
	 * @see #setMember(boolean)
	 * @generated
	 */
	boolean isSetMember();

	/**
	 * Returns the value of the '<em><b>Nick</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nick</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nick</em>' attribute.
	 * @see #isSetNick()
	 * @see #unsetNick()
	 * @see #setNick(String)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_Nick()
	 * @model default="" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getNick();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getNick <em>Nick</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nick</em>' attribute.
	 * @see #isSetNick()
	 * @see #unsetNick()
	 * @see #getNick()
	 * @generated
	 */
	void setNick(String value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getNick <em>Nick</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetNick()
	 * @see #getNick()
	 * @see #setNick(String)
	 * @generated
	 */
	void unsetNick();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getNick <em>Nick</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Nick</em>' attribute is set.
	 * @see #unsetNick()
	 * @see #getNick()
	 * @see #setNick(String)
	 * @generated
	 */
	boolean isSetNick();

	/**
	 * Returns the value of the '<em><b>Organization Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization Id</em>' attribute.
	 * @see #isSetOrganizationId()
	 * @see #unsetOrganizationId()
	 * @see #setOrganizationId(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_OrganizationId()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getOrganizationId();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getOrganizationId <em>Organization Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Organization Id</em>' attribute.
	 * @see #isSetOrganizationId()
	 * @see #unsetOrganizationId()
	 * @see #getOrganizationId()
	 * @generated
	 */
	void setOrganizationId(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getOrganizationId <em>Organization Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOrganizationId()
	 * @see #getOrganizationId()
	 * @see #setOrganizationId(int)
	 * @generated
	 */
	void unsetOrganizationId();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getOrganizationId <em>Organization Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Organization Id</em>' attribute is set.
	 * @see #unsetOrganizationId()
	 * @see #getOrganizationId()
	 * @see #setOrganizationId(int)
	 * @generated
	 */
	boolean isSetOrganizationId();

	/**
	 * Returns the value of the '<em><b>Pinned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pinned</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pinned</em>' attribute.
	 * @see #isSetPinned()
	 * @see #unsetPinned()
	 * @see #setPinned(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_Pinned()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isPinned();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isPinned <em>Pinned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pinned</em>' attribute.
	 * @see #isSetPinned()
	 * @see #unsetPinned()
	 * @see #isPinned()
	 * @generated
	 */
	void setPinned(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isPinned <em>Pinned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPinned()
	 * @see #isPinned()
	 * @see #setPinned(boolean)
	 * @generated
	 */
	void unsetPinned();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isPinned <em>Pinned</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Pinned</em>' attribute is set.
	 * @see #unsetPinned()
	 * @see #isPinned()
	 * @see #setPinned(boolean)
	 * @generated
	 */
	boolean isSetPinned();

	/**
	 * Returns the value of the '<em><b>Qq</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qq</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qq</em>' attribute.
	 * @see #isSetQq()
	 * @see #unsetQq()
	 * @see #setQq(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_Qq()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getQq();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getQq <em>Qq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qq</em>' attribute.
	 * @see #isSetQq()
	 * @see #unsetQq()
	 * @see #getQq()
	 * @generated
	 */
	void setQq(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getQq <em>Qq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetQq()
	 * @see #getQq()
	 * @see #setQq(int)
	 * @generated
	 */
	void unsetQq();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getQq <em>Qq</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Qq</em>' attribute is set.
	 * @see #unsetQq()
	 * @see #getQq()
	 * @see #setQq(int)
	 * @generated
	 */
	boolean isSetQq();

	/**
	 * Returns the value of the '<em><b>Signature</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature</em>' attribute.
	 * @see #isSetSignature()
	 * @see #unsetSignature()
	 * @see #setSignature(String)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_Signature()
	 * @model default="" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getSignature();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getSignature <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signature</em>' attribute.
	 * @see #isSetSignature()
	 * @see #unsetSignature()
	 * @see #getSignature()
	 * @generated
	 */
	void setSignature(String value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getSignature <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSignature()
	 * @see #getSignature()
	 * @see #setSignature(String)
	 * @generated
	 */
	void unsetSignature();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getSignature <em>Signature</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Signature</em>' attribute is set.
	 * @see #unsetSignature()
	 * @see #getSignature()
	 * @see #setSignature(String)
	 * @generated
	 */
	boolean isSetSignature();

	/**
	 * Returns the value of the '<em><b>Talk Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Talk Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Talk Mode</em>' attribute.
	 * @see #isSetTalkMode()
	 * @see #unsetTalkMode()
	 * @see #setTalkMode(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_TalkMode()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isTalkMode();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isTalkMode <em>Talk Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Talk Mode</em>' attribute.
	 * @see #isSetTalkMode()
	 * @see #unsetTalkMode()
	 * @see #isTalkMode()
	 * @generated
	 */
	void setTalkMode(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isTalkMode <em>Talk Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTalkMode()
	 * @see #isTalkMode()
	 * @see #setTalkMode(boolean)
	 * @generated
	 */
	void unsetTalkMode();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isTalkMode <em>Talk Mode</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Talk Mode</em>' attribute is set.
	 * @see #unsetTalkMode()
	 * @see #isTalkMode()
	 * @see #setTalkMode(boolean)
	 * @generated
	 */
	boolean isSetTalkMode();

	/**
	 * Returns the value of the '<em><b>Has Custom Head</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Custom Head</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Custom Head</em>' attribute.
	 * @see #isSetHasCustomHead()
	 * @see #unsetHasCustomHead()
	 * @see #setHasCustomHead(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_HasCustomHead()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isHasCustomHead();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isHasCustomHead <em>Has Custom Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Custom Head</em>' attribute.
	 * @see #isSetHasCustomHead()
	 * @see #unsetHasCustomHead()
	 * @see #isHasCustomHead()
	 * @generated
	 */
	void setHasCustomHead(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isHasCustomHead <em>Has Custom Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHasCustomHead()
	 * @see #isHasCustomHead()
	 * @see #setHasCustomHead(boolean)
	 * @generated
	 */
	void unsetHasCustomHead();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isHasCustomHead <em>Has Custom Head</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Has Custom Head</em>' attribute is set.
	 * @see #unsetHasCustomHead()
	 * @see #isHasCustomHead()
	 * @see #setHasCustomHead(boolean)
	 * @generated
	 */
	boolean isSetHasCustomHead();

	/**
	 * Returns the value of the '<em><b>Custom Head Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Custom Head Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Custom Head Id</em>' attribute.
	 * @see #isSetCustomHeadId()
	 * @see #unsetCustomHeadId()
	 * @see #setCustomHeadId(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_CustomHeadId()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getCustomHeadId();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadId <em>Custom Head Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Custom Head Id</em>' attribute.
	 * @see #isSetCustomHeadId()
	 * @see #unsetCustomHeadId()
	 * @see #getCustomHeadId()
	 * @generated
	 */
	void setCustomHeadId(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadId <em>Custom Head Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCustomHeadId()
	 * @see #getCustomHeadId()
	 * @see #setCustomHeadId(int)
	 * @generated
	 */
	void unsetCustomHeadId();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadId <em>Custom Head Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Custom Head Id</em>' attribute is set.
	 * @see #unsetCustomHeadId()
	 * @see #getCustomHeadId()
	 * @see #setCustomHeadId(int)
	 * @generated
	 */
	boolean isSetCustomHeadId();

	/**
	 * Returns the value of the '<em><b>User Flag</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Flag</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Flag</em>' attribute.
	 * @see #isSetUserFlag()
	 * @see #unsetUserFlag()
	 * @see #setUserFlag(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_UserFlag()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getUserFlag();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getUserFlag <em>User Flag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Flag</em>' attribute.
	 * @see #isSetUserFlag()
	 * @see #unsetUserFlag()
	 * @see #getUserFlag()
	 * @generated
	 */
	void setUserFlag(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getUserFlag <em>User Flag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUserFlag()
	 * @see #getUserFlag()
	 * @see #setUserFlag(int)
	 * @generated
	 */
	void unsetUserFlag();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getUserFlag <em>User Flag</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>User Flag</em>' attribute is set.
	 * @see #unsetUserFlag()
	 * @see #getUserFlag()
	 * @see #setUserFlag(int)
	 * @generated
	 */
	boolean isSetUserFlag();

	/**
	 * Returns the value of the '<em><b>Custom Head Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Custom Head Timestamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Custom Head Timestamp</em>' attribute.
	 * @see #isSetCustomHeadTimestamp()
	 * @see #unsetCustomHeadTimestamp()
	 * @see #setCustomHeadTimestamp(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_CustomHeadTimestamp()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getCustomHeadTimestamp();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadTimestamp <em>Custom Head Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Custom Head Timestamp</em>' attribute.
	 * @see #isSetCustomHeadTimestamp()
	 * @see #unsetCustomHeadTimestamp()
	 * @see #getCustomHeadTimestamp()
	 * @generated
	 */
	void setCustomHeadTimestamp(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadTimestamp <em>Custom Head Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCustomHeadTimestamp()
	 * @see #getCustomHeadTimestamp()
	 * @see #setCustomHeadTimestamp(int)
	 * @generated
	 */
	void unsetCustomHeadTimestamp();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadTimestamp <em>Custom Head Timestamp</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Custom Head Timestamp</em>' attribute is set.
	 * @see #unsetCustomHeadTimestamp()
	 * @see #getCustomHeadTimestamp()
	 * @see #setCustomHeadTimestamp(int)
	 * @generated
	 */
	boolean isSetCustomHeadTimestamp();

	/**
	 * Returns the value of the '<em><b>Window X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Window X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Window X</em>' attribute.
	 * @see #isSetWindowX()
	 * @see #unsetWindowX()
	 * @see #setWindowX(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_WindowX()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getWindowX();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getWindowX <em>Window X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Window X</em>' attribute.
	 * @see #isSetWindowX()
	 * @see #unsetWindowX()
	 * @see #getWindowX()
	 * @generated
	 */
	void setWindowX(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getWindowX <em>Window X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWindowX()
	 * @see #getWindowX()
	 * @see #setWindowX(int)
	 * @generated
	 */
	void unsetWindowX();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getWindowX <em>Window X</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Window X</em>' attribute is set.
	 * @see #unsetWindowX()
	 * @see #getWindowX()
	 * @see #setWindowX(int)
	 * @generated
	 */
	boolean isSetWindowX();

	/**
	 * Returns the value of the '<em><b>Window Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Window Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Window Y</em>' attribute.
	 * @see #isSetWindowY()
	 * @see #unsetWindowY()
	 * @see #setWindowY(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXUser_WindowY()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getWindowY();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getWindowY <em>Window Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Window Y</em>' attribute.
	 * @see #isSetWindowY()
	 * @see #unsetWindowY()
	 * @see #getWindowY()
	 * @generated
	 */
	void setWindowY(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getWindowY <em>Window Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWindowY()
	 * @see #getWindowY()
	 * @see #setWindowY(int)
	 * @generated
	 */
	void unsetWindowY();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getWindowY <em>Window Y</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Window Y</em>' attribute is set.
	 * @see #unsetWindowY()
	 * @see #getWindowY()
	 * @see #setWindowY(int)
	 * @generated
	 */
	boolean isSetWindowY();

} // XUser
