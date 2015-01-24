/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.group;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.tsinghua.lumaqq.ecore.group.GroupFactory
 * @model kind="package"
 * @generated
 */
public interface GroupPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "group";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore/group";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "group";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GroupPackage eINSTANCE = edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl <em>XCluster</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl
	 * @see edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl#getXCluster()
	 * @generated
	 */
	int XCLUSTER = 0;

	/**
	 * The feature id for the '<em><b>User</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__USER = 0;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__ORGANIZATION = 1;

	/**
	 * The feature id for the '<em><b>Admins</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__ADMINS = 2;

	/**
	 * The feature id for the '<em><b>Auth Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__AUTH_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__CATEGORY = 4;

	/**
	 * The feature id for the '<em><b>Cluster Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__CLUSTER_ID = 5;

	/**
	 * The feature id for the '<em><b>Creator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__CREATOR = 6;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__DESCRIPTION = 7;

	/**
	 * The feature id for the '<em><b>External Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__EXTERNAL_ID = 8;

	/**
	 * The feature id for the '<em><b>Head Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__HEAD_ID = 9;

	/**
	 * The feature id for the '<em><b>Last Message Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__LAST_MESSAGE_TIME = 10;

	/**
	 * The feature id for the '<em><b>Message Setting</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__MESSAGE_SETTING = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__NAME = 12;

	/**
	 * The feature id for the '<em><b>Notice</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__NOTICE = 13;

	/**
	 * The feature id for the '<em><b>Old Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__OLD_CATEGORY = 14;

	/**
	 * The feature id for the '<em><b>Parent Cluster Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__PARENT_CLUSTER_ID = 15;

	/**
	 * The feature id for the '<em><b>Stockholders</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__STOCKHOLDERS = 16;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER__TYPE = 17;

	/**
	 * The number of structural features of the '<em>XCluster</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XCLUSTER_FEATURE_COUNT = 18;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.group.impl.XGroupImpl <em>XGroup</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.group.impl.XGroupImpl
	 * @see edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl#getXGroup()
	 * @generated
	 */
	int XGROUP = 1;

	/**
	 * The feature id for the '<em><b>User</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XGROUP__USER = 0;

	/**
	 * The feature id for the '<em><b>Cluster</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XGROUP__CLUSTER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XGROUP__NAME = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XGROUP__TYPE = 3;

	/**
	 * The number of structural features of the '<em>XGroup</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XGROUP_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.group.impl.XGroupsImpl <em>XGroups</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.group.impl.XGroupsImpl
	 * @see edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl#getXGroups()
	 * @generated
	 */
	int XGROUPS = 2;

	/**
	 * The feature id for the '<em><b>Group</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XGROUPS__GROUP = 0;

	/**
	 * The number of structural features of the '<em>XGroups</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XGROUPS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.group.impl.XOrganizationImpl <em>XOrganization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.group.impl.XOrganizationImpl
	 * @see edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl#getXOrganization()
	 * @generated
	 */
	int XORGANIZATION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XORGANIZATION__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XORGANIZATION__NAME = 1;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XORGANIZATION__PATH = 2;

	/**
	 * The number of structural features of the '<em>XOrganization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XORGANIZATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl <em>XUser</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl
	 * @see edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl#getXUser()
	 * @generated
	 */
	int XUSER = 4;

	/**
	 * The feature id for the '<em><b>Card Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__CARD_NAME = 0;

	/**
	 * The feature id for the '<em><b>Female</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__FEMALE = 1;

	/**
	 * The feature id for the '<em><b>Head Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__HEAD_ID = 2;

	/**
	 * The feature id for the '<em><b>Last Message Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__LAST_MESSAGE_TIME = 3;

	/**
	 * The feature id for the '<em><b>Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__LEVEL = 4;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__MEMBER = 5;

	/**
	 * The feature id for the '<em><b>Nick</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__NICK = 6;

	/**
	 * The feature id for the '<em><b>Organization Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__ORGANIZATION_ID = 7;

	/**
	 * The feature id for the '<em><b>Pinned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__PINNED = 8;

	/**
	 * The feature id for the '<em><b>Qq</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__QQ = 9;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__SIGNATURE = 10;

	/**
	 * The feature id for the '<em><b>Talk Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__TALK_MODE = 11;

	/**
	 * The feature id for the '<em><b>Has Custom Head</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__HAS_CUSTOM_HEAD = 12;

	/**
	 * The feature id for the '<em><b>Custom Head Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__CUSTOM_HEAD_ID = 13;

	/**
	 * The feature id for the '<em><b>User Flag</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__USER_FLAG = 14;

	/**
	 * The feature id for the '<em><b>Custom Head Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__CUSTOM_HEAD_TIMESTAMP = 15;

	/**
	 * The feature id for the '<em><b>Window X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__WINDOW_X = 16;

	/**
	 * The feature id for the '<em><b>Window Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER__WINDOW_Y = 17;

	/**
	 * The number of structural features of the '<em>XUser</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XUSER_FEATURE_COUNT = 18;


	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.group.XCluster <em>XCluster</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>XCluster</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster
	 * @generated
	 */
	EClass getXCluster();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>User</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getUser()
	 * @see #getXCluster()
	 * @generated
	 */
	EReference getXCluster_User();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getOrganization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Organization</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getOrganization()
	 * @see #getXCluster()
	 * @generated
	 */
	EReference getXCluster_Organization();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getAdmins <em>Admins</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Admins</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getAdmins()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_Admins();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getAuthType <em>Auth Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auth Type</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getAuthType()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_AuthType();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getCategory()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_Category();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getClusterId <em>Cluster Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cluster Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getClusterId()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_ClusterId();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getCreator <em>Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creator</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getCreator()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_Creator();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getDescription()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_Description();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getExternalId <em>External Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>External Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getExternalId()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_ExternalId();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getHeadId <em>Head Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Head Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getHeadId()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_HeadId();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getLastMessageTime <em>Last Message Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Message Time</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getLastMessageTime()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_LastMessageTime();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getMessageSetting <em>Message Setting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message Setting</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getMessageSetting()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_MessageSetting();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getName()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getNotice <em>Notice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Notice</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getNotice()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_Notice();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getOldCategory <em>Old Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Category</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getOldCategory()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_OldCategory();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getParentClusterId <em>Parent Cluster Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Cluster Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getParentClusterId()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_ParentClusterId();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getStockholders <em>Stockholders</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stockholders</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getStockholders()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_Stockholders();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XCluster#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XCluster#getType()
	 * @see #getXCluster()
	 * @generated
	 */
	EAttribute getXCluster_Type();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.group.XGroup <em>XGroup</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>XGroup</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XGroup
	 * @generated
	 */
	EClass getXGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.group.XGroup#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>User</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XGroup#getUser()
	 * @see #getXGroup()
	 * @generated
	 */
	EReference getXGroup_User();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.group.XGroup#getCluster <em>Cluster</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cluster</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XGroup#getCluster()
	 * @see #getXGroup()
	 * @generated
	 */
	EReference getXGroup_Cluster();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XGroup#getName()
	 * @see #getXGroup()
	 * @generated
	 */
	EAttribute getXGroup_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XGroup#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XGroup#getType()
	 * @see #getXGroup()
	 * @generated
	 */
	EAttribute getXGroup_Type();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.group.XGroups <em>XGroups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>XGroups</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XGroups
	 * @generated
	 */
	EClass getXGroups();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.group.XGroups#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Group</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XGroups#getGroup()
	 * @see #getXGroups()
	 * @generated
	 */
	EReference getXGroups_Group();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.group.XOrganization <em>XOrganization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>XOrganization</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XOrganization
	 * @generated
	 */
	EClass getXOrganization();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XOrganization#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XOrganization#getId()
	 * @see #getXOrganization()
	 * @generated
	 */
	EAttribute getXOrganization_Id();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XOrganization#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XOrganization#getName()
	 * @see #getXOrganization()
	 * @generated
	 */
	EAttribute getXOrganization_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XOrganization#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XOrganization#getPath()
	 * @see #getXOrganization()
	 * @generated
	 */
	EAttribute getXOrganization_Path();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.group.XUser <em>XUser</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>XUser</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser
	 * @generated
	 */
	EClass getXUser();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCardName <em>Card Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Card Name</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getCardName()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_CardName();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isFemale <em>Female</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Female</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#isFemale()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_Female();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getHeadId <em>Head Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Head Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getHeadId()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_HeadId();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getLastMessageTime <em>Last Message Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Message Time</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getLastMessageTime()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_LastMessageTime();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getLevel <em>Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Level</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getLevel()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_Level();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#isMember()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_Member();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getNick <em>Nick</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nick</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getNick()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_Nick();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getOrganizationId <em>Organization Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Organization Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getOrganizationId()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_OrganizationId();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isPinned <em>Pinned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pinned</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#isPinned()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_Pinned();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getQq <em>Qq</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qq</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getQq()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_Qq();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Signature</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getSignature()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_Signature();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isTalkMode <em>Talk Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Talk Mode</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#isTalkMode()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_TalkMode();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#isHasCustomHead <em>Has Custom Head</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has Custom Head</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#isHasCustomHead()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_HasCustomHead();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadId <em>Custom Head Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Custom Head Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadId()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_CustomHeadId();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getUserFlag <em>User Flag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Flag</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getUserFlag()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_UserFlag();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadTimestamp <em>Custom Head Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Custom Head Timestamp</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getCustomHeadTimestamp()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_CustomHeadTimestamp();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getWindowX <em>Window X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Window X</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getWindowX()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_WindowX();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.group.XUser#getWindowY <em>Window Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Window Y</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.group.XUser#getWindowY()
	 * @see #getXUser()
	 * @generated
	 */
	EAttribute getXUser_WindowY();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GroupFactory getGroupFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl <em>XCluster</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl
		 * @see edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl#getXCluster()
		 * @generated
		 */
		EClass XCLUSTER = eINSTANCE.getXCluster();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XCLUSTER__USER = eINSTANCE.getXCluster_User();

		/**
		 * The meta object literal for the '<em><b>Organization</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XCLUSTER__ORGANIZATION = eINSTANCE.getXCluster_Organization();

		/**
		 * The meta object literal for the '<em><b>Admins</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__ADMINS = eINSTANCE.getXCluster_Admins();

		/**
		 * The meta object literal for the '<em><b>Auth Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__AUTH_TYPE = eINSTANCE.getXCluster_AuthType();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__CATEGORY = eINSTANCE.getXCluster_Category();

		/**
		 * The meta object literal for the '<em><b>Cluster Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__CLUSTER_ID = eINSTANCE.getXCluster_ClusterId();

		/**
		 * The meta object literal for the '<em><b>Creator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__CREATOR = eINSTANCE.getXCluster_Creator();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__DESCRIPTION = eINSTANCE.getXCluster_Description();

		/**
		 * The meta object literal for the '<em><b>External Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__EXTERNAL_ID = eINSTANCE.getXCluster_ExternalId();

		/**
		 * The meta object literal for the '<em><b>Head Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__HEAD_ID = eINSTANCE.getXCluster_HeadId();

		/**
		 * The meta object literal for the '<em><b>Last Message Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__LAST_MESSAGE_TIME = eINSTANCE.getXCluster_LastMessageTime();

		/**
		 * The meta object literal for the '<em><b>Message Setting</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__MESSAGE_SETTING = eINSTANCE.getXCluster_MessageSetting();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__NAME = eINSTANCE.getXCluster_Name();

		/**
		 * The meta object literal for the '<em><b>Notice</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__NOTICE = eINSTANCE.getXCluster_Notice();

		/**
		 * The meta object literal for the '<em><b>Old Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__OLD_CATEGORY = eINSTANCE.getXCluster_OldCategory();

		/**
		 * The meta object literal for the '<em><b>Parent Cluster Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__PARENT_CLUSTER_ID = eINSTANCE.getXCluster_ParentClusterId();

		/**
		 * The meta object literal for the '<em><b>Stockholders</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__STOCKHOLDERS = eINSTANCE.getXCluster_Stockholders();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XCLUSTER__TYPE = eINSTANCE.getXCluster_Type();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.group.impl.XGroupImpl <em>XGroup</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.group.impl.XGroupImpl
		 * @see edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl#getXGroup()
		 * @generated
		 */
		EClass XGROUP = eINSTANCE.getXGroup();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XGROUP__USER = eINSTANCE.getXGroup_User();

		/**
		 * The meta object literal for the '<em><b>Cluster</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XGROUP__CLUSTER = eINSTANCE.getXGroup_Cluster();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XGROUP__NAME = eINSTANCE.getXGroup_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XGROUP__TYPE = eINSTANCE.getXGroup_Type();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.group.impl.XGroupsImpl <em>XGroups</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.group.impl.XGroupsImpl
		 * @see edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl#getXGroups()
		 * @generated
		 */
		EClass XGROUPS = eINSTANCE.getXGroups();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XGROUPS__GROUP = eINSTANCE.getXGroups_Group();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.group.impl.XOrganizationImpl <em>XOrganization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.group.impl.XOrganizationImpl
		 * @see edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl#getXOrganization()
		 * @generated
		 */
		EClass XORGANIZATION = eINSTANCE.getXOrganization();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XORGANIZATION__ID = eINSTANCE.getXOrganization_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XORGANIZATION__NAME = eINSTANCE.getXOrganization_Name();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XORGANIZATION__PATH = eINSTANCE.getXOrganization_Path();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl <em>XUser</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl
		 * @see edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl#getXUser()
		 * @generated
		 */
		EClass XUSER = eINSTANCE.getXUser();

		/**
		 * The meta object literal for the '<em><b>Card Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__CARD_NAME = eINSTANCE.getXUser_CardName();

		/**
		 * The meta object literal for the '<em><b>Female</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__FEMALE = eINSTANCE.getXUser_Female();

		/**
		 * The meta object literal for the '<em><b>Head Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__HEAD_ID = eINSTANCE.getXUser_HeadId();

		/**
		 * The meta object literal for the '<em><b>Last Message Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__LAST_MESSAGE_TIME = eINSTANCE.getXUser_LastMessageTime();

		/**
		 * The meta object literal for the '<em><b>Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__LEVEL = eINSTANCE.getXUser_Level();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__MEMBER = eINSTANCE.getXUser_Member();

		/**
		 * The meta object literal for the '<em><b>Nick</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__NICK = eINSTANCE.getXUser_Nick();

		/**
		 * The meta object literal for the '<em><b>Organization Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__ORGANIZATION_ID = eINSTANCE.getXUser_OrganizationId();

		/**
		 * The meta object literal for the '<em><b>Pinned</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__PINNED = eINSTANCE.getXUser_Pinned();

		/**
		 * The meta object literal for the '<em><b>Qq</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__QQ = eINSTANCE.getXUser_Qq();

		/**
		 * The meta object literal for the '<em><b>Signature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__SIGNATURE = eINSTANCE.getXUser_Signature();

		/**
		 * The meta object literal for the '<em><b>Talk Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__TALK_MODE = eINSTANCE.getXUser_TalkMode();

		/**
		 * The meta object literal for the '<em><b>Has Custom Head</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__HAS_CUSTOM_HEAD = eINSTANCE.getXUser_HasCustomHead();

		/**
		 * The meta object literal for the '<em><b>Custom Head Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__CUSTOM_HEAD_ID = eINSTANCE.getXUser_CustomHeadId();

		/**
		 * The meta object literal for the '<em><b>User Flag</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__USER_FLAG = eINSTANCE.getXUser_UserFlag();

		/**
		 * The meta object literal for the '<em><b>Custom Head Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__CUSTOM_HEAD_TIMESTAMP = eINSTANCE.getXUser_CustomHeadTimestamp();

		/**
		 * The meta object literal for the '<em><b>Window X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__WINDOW_X = eINSTANCE.getXUser_WindowX();

		/**
		 * The meta object literal for the '<em><b>Window Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XUSER__WINDOW_Y = eINSTANCE.getXUser_WindowY();

	}

} //GroupPackage
