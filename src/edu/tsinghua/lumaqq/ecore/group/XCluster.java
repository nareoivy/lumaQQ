/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.group;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>XCluster</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getUser <em>User</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getOrganization <em>Organization</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getAdmins <em>Admins</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getAuthType <em>Auth Type</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getCategory <em>Category</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getClusterId <em>Cluster Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getCreator <em>Creator</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getDescription <em>Description</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getExternalId <em>External Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getHeadId <em>Head Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getLastMessageTime <em>Last Message Time</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getMessageSetting <em>Message Setting</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getName <em>Name</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getNotice <em>Notice</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getOldCategory <em>Old Category</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getParentClusterId <em>Parent Cluster Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getStockholders <em>Stockholders</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster()
 * @model
 * @generated
 */
public interface XCluster extends EObject {
	/**
	 * Returns the value of the '<em><b>User</b></em>' containment reference list.
	 * The list contents are of type {@link edu.tsinghua.lumaqq.ecore.group.XUser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User</em>' containment reference list.
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_User()
	 * @model type="edu.tsinghua.lumaqq.ecore.group.XUser" containment="true" resolveProxies="false"
	 * @generated
	 */
	EList getUser();

	/**
	 * Returns the value of the '<em><b>Organization</b></em>' containment reference list.
	 * The list contents are of type {@link edu.tsinghua.lumaqq.ecore.group.XOrganization}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization</em>' containment reference list.
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_Organization()
	 * @model type="edu.tsinghua.lumaqq.ecore.group.XOrganization" containment="true" resolveProxies="false"
	 * @generated
	 */
	EList getOrganization();

	/**
	 * Returns the value of the '<em><b>Admins</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Admins</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Admins</em>' attribute.
	 * @see #setAdmins(String)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_Admins()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getAdmins();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getAdmins <em>Admins</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Admins</em>' attribute.
	 * @see #getAdmins()
	 * @generated
	 */
	void setAdmins(String value);

	/**
	 * Returns the value of the '<em><b>Auth Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auth Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auth Type</em>' attribute.
	 * @see #isSetAuthType()
	 * @see #unsetAuthType()
	 * @see #setAuthType(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_AuthType()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getAuthType();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getAuthType <em>Auth Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auth Type</em>' attribute.
	 * @see #isSetAuthType()
	 * @see #unsetAuthType()
	 * @see #getAuthType()
	 * @generated
	 */
	void setAuthType(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getAuthType <em>Auth Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAuthType()
	 * @see #getAuthType()
	 * @see #setAuthType(int)
	 * @generated
	 */
	void unsetAuthType();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getAuthType <em>Auth Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Auth Type</em>' attribute is set.
	 * @see #unsetAuthType()
	 * @see #getAuthType()
	 * @see #setAuthType(int)
	 * @generated
	 */
	boolean isSetAuthType();

	/**
	 * Returns the value of the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category</em>' attribute.
	 * @see #isSetCategory()
	 * @see #unsetCategory()
	 * @see #setCategory(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_Category()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getCategory();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' attribute.
	 * @see #isSetCategory()
	 * @see #unsetCategory()
	 * @see #getCategory()
	 * @generated
	 */
	void setCategory(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCategory()
	 * @see #getCategory()
	 * @see #setCategory(int)
	 * @generated
	 */
	void unsetCategory();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getCategory <em>Category</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Category</em>' attribute is set.
	 * @see #unsetCategory()
	 * @see #getCategory()
	 * @see #setCategory(int)
	 * @generated
	 */
	boolean isSetCategory();

	/**
	 * Returns the value of the '<em><b>Cluster Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cluster Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cluster Id</em>' attribute.
	 * @see #isSetClusterId()
	 * @see #unsetClusterId()
	 * @see #setClusterId(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_ClusterId()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getClusterId();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getClusterId <em>Cluster Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cluster Id</em>' attribute.
	 * @see #isSetClusterId()
	 * @see #unsetClusterId()
	 * @see #getClusterId()
	 * @generated
	 */
	void setClusterId(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getClusterId <em>Cluster Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetClusterId()
	 * @see #getClusterId()
	 * @see #setClusterId(int)
	 * @generated
	 */
	void unsetClusterId();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getClusterId <em>Cluster Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Cluster Id</em>' attribute is set.
	 * @see #unsetClusterId()
	 * @see #getClusterId()
	 * @see #setClusterId(int)
	 * @generated
	 */
	boolean isSetClusterId();

	/**
	 * Returns the value of the '<em><b>Creator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creator</em>' attribute.
	 * @see #isSetCreator()
	 * @see #unsetCreator()
	 * @see #setCreator(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_Creator()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getCreator();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getCreator <em>Creator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creator</em>' attribute.
	 * @see #isSetCreator()
	 * @see #unsetCreator()
	 * @see #getCreator()
	 * @generated
	 */
	void setCreator(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getCreator <em>Creator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCreator()
	 * @see #getCreator()
	 * @see #setCreator(int)
	 * @generated
	 */
	void unsetCreator();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getCreator <em>Creator</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Creator</em>' attribute is set.
	 * @see #unsetCreator()
	 * @see #getCreator()
	 * @see #setCreator(int)
	 * @generated
	 */
	boolean isSetCreator();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #isSetDescription()
	 * @see #unsetDescription()
	 * @see #setDescription(String)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_Description()
	 * @model default="" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #isSetDescription()
	 * @see #unsetDescription()
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDescription()
	 * @see #getDescription()
	 * @see #setDescription(String)
	 * @generated
	 */
	void unsetDescription();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getDescription <em>Description</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Description</em>' attribute is set.
	 * @see #unsetDescription()
	 * @see #getDescription()
	 * @see #setDescription(String)
	 * @generated
	 */
	boolean isSetDescription();

	/**
	 * Returns the value of the '<em><b>External Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Id</em>' attribute.
	 * @see #isSetExternalId()
	 * @see #unsetExternalId()
	 * @see #setExternalId(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_ExternalId()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getExternalId();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getExternalId <em>External Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External Id</em>' attribute.
	 * @see #isSetExternalId()
	 * @see #unsetExternalId()
	 * @see #getExternalId()
	 * @generated
	 */
	void setExternalId(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getExternalId <em>External Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetExternalId()
	 * @see #getExternalId()
	 * @see #setExternalId(int)
	 * @generated
	 */
	void unsetExternalId();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getExternalId <em>External Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>External Id</em>' attribute is set.
	 * @see #unsetExternalId()
	 * @see #getExternalId()
	 * @see #setExternalId(int)
	 * @generated
	 */
	boolean isSetExternalId();

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
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_HeadId()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getHeadId();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getHeadId <em>Head Id</em>}' attribute.
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
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getHeadId <em>Head Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHeadId()
	 * @see #getHeadId()
	 * @see #setHeadId(int)
	 * @generated
	 */
	void unsetHeadId();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getHeadId <em>Head Id</em>}' attribute is set.
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
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_LastMessageTime()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
	 * @generated
	 */
	long getLastMessageTime();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getLastMessageTime <em>Last Message Time</em>}' attribute.
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
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getLastMessageTime <em>Last Message Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLastMessageTime()
	 * @see #getLastMessageTime()
	 * @see #setLastMessageTime(long)
	 * @generated
	 */
	void unsetLastMessageTime();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getLastMessageTime <em>Last Message Time</em>}' attribute is set.
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
	 * Returns the value of the '<em><b>Message Setting</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message Setting</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message Setting</em>' attribute.
	 * @see #setMessageSetting(String)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_MessageSetting()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getMessageSetting();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getMessageSetting <em>Message Setting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message Setting</em>' attribute.
	 * @see #getMessageSetting()
	 * @generated
	 */
	void setMessageSetting(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Notice</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Notice</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notice</em>' attribute.
	 * @see #isSetNotice()
	 * @see #unsetNotice()
	 * @see #setNotice(String)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_Notice()
	 * @model default="" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getNotice();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getNotice <em>Notice</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Notice</em>' attribute.
	 * @see #isSetNotice()
	 * @see #unsetNotice()
	 * @see #getNotice()
	 * @generated
	 */
	void setNotice(String value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getNotice <em>Notice</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetNotice()
	 * @see #getNotice()
	 * @see #setNotice(String)
	 * @generated
	 */
	void unsetNotice();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getNotice <em>Notice</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Notice</em>' attribute is set.
	 * @see #unsetNotice()
	 * @see #getNotice()
	 * @see #setNotice(String)
	 * @generated
	 */
	boolean isSetNotice();

	/**
	 * Returns the value of the '<em><b>Old Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Category</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Category</em>' attribute.
	 * @see #isSetOldCategory()
	 * @see #unsetOldCategory()
	 * @see #setOldCategory(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_OldCategory()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getOldCategory();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getOldCategory <em>Old Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Category</em>' attribute.
	 * @see #isSetOldCategory()
	 * @see #unsetOldCategory()
	 * @see #getOldCategory()
	 * @generated
	 */
	void setOldCategory(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getOldCategory <em>Old Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOldCategory()
	 * @see #getOldCategory()
	 * @see #setOldCategory(int)
	 * @generated
	 */
	void unsetOldCategory();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getOldCategory <em>Old Category</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Old Category</em>' attribute is set.
	 * @see #unsetOldCategory()
	 * @see #getOldCategory()
	 * @see #setOldCategory(int)
	 * @generated
	 */
	boolean isSetOldCategory();

	/**
	 * Returns the value of the '<em><b>Parent Cluster Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Cluster Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Cluster Id</em>' attribute.
	 * @see #isSetParentClusterId()
	 * @see #unsetParentClusterId()
	 * @see #setParentClusterId(int)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_ParentClusterId()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getParentClusterId();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getParentClusterId <em>Parent Cluster Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Cluster Id</em>' attribute.
	 * @see #isSetParentClusterId()
	 * @see #unsetParentClusterId()
	 * @see #getParentClusterId()
	 * @generated
	 */
	void setParentClusterId(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getParentClusterId <em>Parent Cluster Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetParentClusterId()
	 * @see #getParentClusterId()
	 * @see #setParentClusterId(int)
	 * @generated
	 */
	void unsetParentClusterId();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getParentClusterId <em>Parent Cluster Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Parent Cluster Id</em>' attribute is set.
	 * @see #unsetParentClusterId()
	 * @see #getParentClusterId()
	 * @see #setParentClusterId(int)
	 * @generated
	 */
	boolean isSetParentClusterId();

	/**
	 * Returns the value of the '<em><b>Stockholders</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stockholders</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stockholders</em>' attribute.
	 * @see #setStockholders(String)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_Stockholders()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getStockholders();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getStockholders <em>Stockholders</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stockholders</em>' attribute.
	 * @see #getStockholders()
	 * @generated
	 */
	void setStockholders(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see edu.tsinghua.lumaqq.ecore.group.GroupPackage#getXCluster_Type()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // XCluster
