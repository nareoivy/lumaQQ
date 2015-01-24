/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.group.impl;

import edu.tsinghua.lumaqq.ecore.group.GroupPackage;
import edu.tsinghua.lumaqq.ecore.group.XCluster;
import edu.tsinghua.lumaqq.ecore.group.XOrganization;
import edu.tsinghua.lumaqq.ecore.group.XUser;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>XCluster</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getUser <em>User</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getOrganization <em>Organization</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getAdmins <em>Admins</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getAuthType <em>Auth Type</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getClusterId <em>Cluster Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getCreator <em>Creator</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getExternalId <em>External Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getHeadId <em>Head Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getLastMessageTime <em>Last Message Time</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getMessageSetting <em>Message Setting</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getNotice <em>Notice</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getOldCategory <em>Old Category</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getParentClusterId <em>Parent Cluster Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getStockholders <em>Stockholders</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XClusterImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class XClusterImpl extends EObjectImpl implements XCluster {
	/**
	 * The cached value of the '{@link #getUser() <em>User</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected EList user = null;

	/**
	 * The cached value of the '{@link #getOrganization() <em>Organization</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganization()
	 * @generated
	 * @ordered
	 */
	protected EList organization = null;

	/**
	 * The default value of the '{@link #getAdmins() <em>Admins</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdmins()
	 * @generated
	 * @ordered
	 */
	protected static final String ADMINS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAdmins() <em>Admins</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdmins()
	 * @generated
	 * @ordered
	 */
	protected String admins = ADMINS_EDEFAULT;

	/**
	 * The default value of the '{@link #getAuthType() <em>Auth Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthType()
	 * @generated
	 * @ordered
	 */
	protected static final int AUTH_TYPE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAuthType() <em>Auth Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthType()
	 * @generated
	 * @ordered
	 */
	protected int authType = AUTH_TYPE_EDEFAULT;

	/**
	 * This is true if the Auth Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean authTypeESet = false;

	/**
	 * The default value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected static final int CATEGORY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected int category = CATEGORY_EDEFAULT;

	/**
	 * This is true if the Category attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean categoryESet = false;

	/**
	 * The default value of the '{@link #getClusterId() <em>Cluster Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClusterId()
	 * @generated
	 * @ordered
	 */
	protected static final int CLUSTER_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getClusterId() <em>Cluster Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClusterId()
	 * @generated
	 * @ordered
	 */
	protected int clusterId = CLUSTER_ID_EDEFAULT;

	/**
	 * This is true if the Cluster Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean clusterIdESet = false;

	/**
	 * The default value of the '{@link #getCreator() <em>Creator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreator()
	 * @generated
	 * @ordered
	 */
	protected static final int CREATOR_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCreator() <em>Creator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreator()
	 * @generated
	 * @ordered
	 */
	protected int creator = CREATOR_EDEFAULT;

	/**
	 * This is true if the Creator attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean creatorESet = false;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * This is true if the Description attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean descriptionESet = false;

	/**
	 * The default value of the '{@link #getExternalId() <em>External Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExternalId()
	 * @generated
	 * @ordered
	 */
	protected static final int EXTERNAL_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getExternalId() <em>External Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExternalId()
	 * @generated
	 * @ordered
	 */
	protected int externalId = EXTERNAL_ID_EDEFAULT;

	/**
	 * This is true if the External Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean externalIdESet = false;

	/**
	 * The default value of the '{@link #getHeadId() <em>Head Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeadId()
	 * @generated
	 * @ordered
	 */
	protected static final int HEAD_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHeadId() <em>Head Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeadId()
	 * @generated
	 * @ordered
	 */
	protected int headId = HEAD_ID_EDEFAULT;

	/**
	 * This is true if the Head Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean headIdESet = false;

	/**
	 * The default value of the '{@link #getLastMessageTime() <em>Last Message Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastMessageTime()
	 * @generated
	 * @ordered
	 */
	protected static final long LAST_MESSAGE_TIME_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getLastMessageTime() <em>Last Message Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastMessageTime()
	 * @generated
	 * @ordered
	 */
	protected long lastMessageTime = LAST_MESSAGE_TIME_EDEFAULT;

	/**
	 * This is true if the Last Message Time attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean lastMessageTimeESet = false;

	/**
	 * The default value of the '{@link #getMessageSetting() <em>Message Setting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageSetting()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_SETTING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessageSetting() <em>Message Setting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageSetting()
	 * @generated
	 * @ordered
	 */
	protected String messageSetting = MESSAGE_SETTING_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getNotice() <em>Notice</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotice()
	 * @generated
	 * @ordered
	 */
	protected static final String NOTICE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getNotice() <em>Notice</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotice()
	 * @generated
	 * @ordered
	 */
	protected String notice = NOTICE_EDEFAULT;

	/**
	 * This is true if the Notice attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean noticeESet = false;

	/**
	 * The default value of the '{@link #getOldCategory() <em>Old Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldCategory()
	 * @generated
	 * @ordered
	 */
	protected static final int OLD_CATEGORY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOldCategory() <em>Old Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldCategory()
	 * @generated
	 * @ordered
	 */
	protected int oldCategory = OLD_CATEGORY_EDEFAULT;

	/**
	 * This is true if the Old Category attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean oldCategoryESet = false;

	/**
	 * The default value of the '{@link #getParentClusterId() <em>Parent Cluster Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentClusterId()
	 * @generated
	 * @ordered
	 */
	protected static final int PARENT_CLUSTER_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getParentClusterId() <em>Parent Cluster Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentClusterId()
	 * @generated
	 * @ordered
	 */
	protected int parentClusterId = PARENT_CLUSTER_ID_EDEFAULT;

	/**
	 * This is true if the Parent Cluster Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean parentClusterIdESet = false;

	/**
	 * The default value of the '{@link #getStockholders() <em>Stockholders</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStockholders()
	 * @generated
	 * @ordered
	 */
	protected static final String STOCKHOLDERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStockholders() <em>Stockholders</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStockholders()
	 * @generated
	 * @ordered
	 */
	protected String stockholders = STOCKHOLDERS_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XClusterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GroupPackage.Literals.XCLUSTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getUser() {
		if (user == null) {
			user = new EObjectContainmentEList(XUser.class, this, GroupPackage.XCLUSTER__USER);
		}
		return user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOrganization() {
		if (organization == null) {
			organization = new EObjectContainmentEList(XOrganization.class, this, GroupPackage.XCLUSTER__ORGANIZATION);
		}
		return organization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAdmins() {
		return admins;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdmins(String newAdmins) {
		String oldAdmins = admins;
		admins = newAdmins;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__ADMINS, oldAdmins, admins));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getAuthType() {
		return authType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthType(int newAuthType) {
		int oldAuthType = authType;
		authType = newAuthType;
		boolean oldAuthTypeESet = authTypeESet;
		authTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__AUTH_TYPE, oldAuthType, authType, !oldAuthTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAuthType() {
		int oldAuthType = authType;
		boolean oldAuthTypeESet = authTypeESet;
		authType = AUTH_TYPE_EDEFAULT;
		authTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__AUTH_TYPE, oldAuthType, AUTH_TYPE_EDEFAULT, oldAuthTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAuthType() {
		return authTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategory(int newCategory) {
		int oldCategory = category;
		category = newCategory;
		boolean oldCategoryESet = categoryESet;
		categoryESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__CATEGORY, oldCategory, category, !oldCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCategory() {
		int oldCategory = category;
		boolean oldCategoryESet = categoryESet;
		category = CATEGORY_EDEFAULT;
		categoryESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__CATEGORY, oldCategory, CATEGORY_EDEFAULT, oldCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCategory() {
		return categoryESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getClusterId() {
		return clusterId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClusterId(int newClusterId) {
		int oldClusterId = clusterId;
		clusterId = newClusterId;
		boolean oldClusterIdESet = clusterIdESet;
		clusterIdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__CLUSTER_ID, oldClusterId, clusterId, !oldClusterIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetClusterId() {
		int oldClusterId = clusterId;
		boolean oldClusterIdESet = clusterIdESet;
		clusterId = CLUSTER_ID_EDEFAULT;
		clusterIdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__CLUSTER_ID, oldClusterId, CLUSTER_ID_EDEFAULT, oldClusterIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetClusterId() {
		return clusterIdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCreator() {
		return creator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreator(int newCreator) {
		int oldCreator = creator;
		creator = newCreator;
		boolean oldCreatorESet = creatorESet;
		creatorESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__CREATOR, oldCreator, creator, !oldCreatorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCreator() {
		int oldCreator = creator;
		boolean oldCreatorESet = creatorESet;
		creator = CREATOR_EDEFAULT;
		creatorESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__CREATOR, oldCreator, CREATOR_EDEFAULT, oldCreatorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCreator() {
		return creatorESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		boolean oldDescriptionESet = descriptionESet;
		descriptionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__DESCRIPTION, oldDescription, description, !oldDescriptionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDescription() {
		String oldDescription = description;
		boolean oldDescriptionESet = descriptionESet;
		description = DESCRIPTION_EDEFAULT;
		descriptionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__DESCRIPTION, oldDescription, DESCRIPTION_EDEFAULT, oldDescriptionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDescription() {
		return descriptionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getExternalId() {
		return externalId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExternalId(int newExternalId) {
		int oldExternalId = externalId;
		externalId = newExternalId;
		boolean oldExternalIdESet = externalIdESet;
		externalIdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__EXTERNAL_ID, oldExternalId, externalId, !oldExternalIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetExternalId() {
		int oldExternalId = externalId;
		boolean oldExternalIdESet = externalIdESet;
		externalId = EXTERNAL_ID_EDEFAULT;
		externalIdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__EXTERNAL_ID, oldExternalId, EXTERNAL_ID_EDEFAULT, oldExternalIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetExternalId() {
		return externalIdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getHeadId() {
		return headId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeadId(int newHeadId) {
		int oldHeadId = headId;
		headId = newHeadId;
		boolean oldHeadIdESet = headIdESet;
		headIdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__HEAD_ID, oldHeadId, headId, !oldHeadIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetHeadId() {
		int oldHeadId = headId;
		boolean oldHeadIdESet = headIdESet;
		headId = HEAD_ID_EDEFAULT;
		headIdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__HEAD_ID, oldHeadId, HEAD_ID_EDEFAULT, oldHeadIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetHeadId() {
		return headIdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getLastMessageTime() {
		return lastMessageTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastMessageTime(long newLastMessageTime) {
		long oldLastMessageTime = lastMessageTime;
		lastMessageTime = newLastMessageTime;
		boolean oldLastMessageTimeESet = lastMessageTimeESet;
		lastMessageTimeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__LAST_MESSAGE_TIME, oldLastMessageTime, lastMessageTime, !oldLastMessageTimeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLastMessageTime() {
		long oldLastMessageTime = lastMessageTime;
		boolean oldLastMessageTimeESet = lastMessageTimeESet;
		lastMessageTime = LAST_MESSAGE_TIME_EDEFAULT;
		lastMessageTimeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__LAST_MESSAGE_TIME, oldLastMessageTime, LAST_MESSAGE_TIME_EDEFAULT, oldLastMessageTimeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLastMessageTime() {
		return lastMessageTimeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMessageSetting() {
		return messageSetting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessageSetting(String newMessageSetting) {
		String oldMessageSetting = messageSetting;
		messageSetting = newMessageSetting;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__MESSAGE_SETTING, oldMessageSetting, messageSetting));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNotice(String newNotice) {
		String oldNotice = notice;
		notice = newNotice;
		boolean oldNoticeESet = noticeESet;
		noticeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__NOTICE, oldNotice, notice, !oldNoticeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetNotice() {
		String oldNotice = notice;
		boolean oldNoticeESet = noticeESet;
		notice = NOTICE_EDEFAULT;
		noticeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__NOTICE, oldNotice, NOTICE_EDEFAULT, oldNoticeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetNotice() {
		return noticeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOldCategory() {
		return oldCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldCategory(int newOldCategory) {
		int oldOldCategory = oldCategory;
		oldCategory = newOldCategory;
		boolean oldOldCategoryESet = oldCategoryESet;
		oldCategoryESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__OLD_CATEGORY, oldOldCategory, oldCategory, !oldOldCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOldCategory() {
		int oldOldCategory = oldCategory;
		boolean oldOldCategoryESet = oldCategoryESet;
		oldCategory = OLD_CATEGORY_EDEFAULT;
		oldCategoryESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__OLD_CATEGORY, oldOldCategory, OLD_CATEGORY_EDEFAULT, oldOldCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOldCategory() {
		return oldCategoryESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getParentClusterId() {
		return parentClusterId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentClusterId(int newParentClusterId) {
		int oldParentClusterId = parentClusterId;
		parentClusterId = newParentClusterId;
		boolean oldParentClusterIdESet = parentClusterIdESet;
		parentClusterIdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__PARENT_CLUSTER_ID, oldParentClusterId, parentClusterId, !oldParentClusterIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetParentClusterId() {
		int oldParentClusterId = parentClusterId;
		boolean oldParentClusterIdESet = parentClusterIdESet;
		parentClusterId = PARENT_CLUSTER_ID_EDEFAULT;
		parentClusterIdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XCLUSTER__PARENT_CLUSTER_ID, oldParentClusterId, PARENT_CLUSTER_ID_EDEFAULT, oldParentClusterIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetParentClusterId() {
		return parentClusterIdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStockholders() {
		return stockholders;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStockholders(String newStockholders) {
		String oldStockholders = stockholders;
		stockholders = newStockholders;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__STOCKHOLDERS, oldStockholders, stockholders));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XCLUSTER__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GroupPackage.XCLUSTER__USER:
				return ((InternalEList)getUser()).basicRemove(otherEnd, msgs);
			case GroupPackage.XCLUSTER__ORGANIZATION:
				return ((InternalEList)getOrganization()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GroupPackage.XCLUSTER__USER:
				return getUser();
			case GroupPackage.XCLUSTER__ORGANIZATION:
				return getOrganization();
			case GroupPackage.XCLUSTER__ADMINS:
				return getAdmins();
			case GroupPackage.XCLUSTER__AUTH_TYPE:
				return new Integer(getAuthType());
			case GroupPackage.XCLUSTER__CATEGORY:
				return new Integer(getCategory());
			case GroupPackage.XCLUSTER__CLUSTER_ID:
				return new Integer(getClusterId());
			case GroupPackage.XCLUSTER__CREATOR:
				return new Integer(getCreator());
			case GroupPackage.XCLUSTER__DESCRIPTION:
				return getDescription();
			case GroupPackage.XCLUSTER__EXTERNAL_ID:
				return new Integer(getExternalId());
			case GroupPackage.XCLUSTER__HEAD_ID:
				return new Integer(getHeadId());
			case GroupPackage.XCLUSTER__LAST_MESSAGE_TIME:
				return new Long(getLastMessageTime());
			case GroupPackage.XCLUSTER__MESSAGE_SETTING:
				return getMessageSetting();
			case GroupPackage.XCLUSTER__NAME:
				return getName();
			case GroupPackage.XCLUSTER__NOTICE:
				return getNotice();
			case GroupPackage.XCLUSTER__OLD_CATEGORY:
				return new Integer(getOldCategory());
			case GroupPackage.XCLUSTER__PARENT_CLUSTER_ID:
				return new Integer(getParentClusterId());
			case GroupPackage.XCLUSTER__STOCKHOLDERS:
				return getStockholders();
			case GroupPackage.XCLUSTER__TYPE:
				return getType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GroupPackage.XCLUSTER__USER:
				getUser().clear();
				getUser().addAll((Collection)newValue);
				return;
			case GroupPackage.XCLUSTER__ORGANIZATION:
				getOrganization().clear();
				getOrganization().addAll((Collection)newValue);
				return;
			case GroupPackage.XCLUSTER__ADMINS:
				setAdmins((String)newValue);
				return;
			case GroupPackage.XCLUSTER__AUTH_TYPE:
				setAuthType(((Integer)newValue).intValue());
				return;
			case GroupPackage.XCLUSTER__CATEGORY:
				setCategory(((Integer)newValue).intValue());
				return;
			case GroupPackage.XCLUSTER__CLUSTER_ID:
				setClusterId(((Integer)newValue).intValue());
				return;
			case GroupPackage.XCLUSTER__CREATOR:
				setCreator(((Integer)newValue).intValue());
				return;
			case GroupPackage.XCLUSTER__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case GroupPackage.XCLUSTER__EXTERNAL_ID:
				setExternalId(((Integer)newValue).intValue());
				return;
			case GroupPackage.XCLUSTER__HEAD_ID:
				setHeadId(((Integer)newValue).intValue());
				return;
			case GroupPackage.XCLUSTER__LAST_MESSAGE_TIME:
				setLastMessageTime(((Long)newValue).longValue());
				return;
			case GroupPackage.XCLUSTER__MESSAGE_SETTING:
				setMessageSetting((String)newValue);
				return;
			case GroupPackage.XCLUSTER__NAME:
				setName((String)newValue);
				return;
			case GroupPackage.XCLUSTER__NOTICE:
				setNotice((String)newValue);
				return;
			case GroupPackage.XCLUSTER__OLD_CATEGORY:
				setOldCategory(((Integer)newValue).intValue());
				return;
			case GroupPackage.XCLUSTER__PARENT_CLUSTER_ID:
				setParentClusterId(((Integer)newValue).intValue());
				return;
			case GroupPackage.XCLUSTER__STOCKHOLDERS:
				setStockholders((String)newValue);
				return;
			case GroupPackage.XCLUSTER__TYPE:
				setType((String)newValue);
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
			case GroupPackage.XCLUSTER__USER:
				getUser().clear();
				return;
			case GroupPackage.XCLUSTER__ORGANIZATION:
				getOrganization().clear();
				return;
			case GroupPackage.XCLUSTER__ADMINS:
				setAdmins(ADMINS_EDEFAULT);
				return;
			case GroupPackage.XCLUSTER__AUTH_TYPE:
				unsetAuthType();
				return;
			case GroupPackage.XCLUSTER__CATEGORY:
				unsetCategory();
				return;
			case GroupPackage.XCLUSTER__CLUSTER_ID:
				unsetClusterId();
				return;
			case GroupPackage.XCLUSTER__CREATOR:
				unsetCreator();
				return;
			case GroupPackage.XCLUSTER__DESCRIPTION:
				unsetDescription();
				return;
			case GroupPackage.XCLUSTER__EXTERNAL_ID:
				unsetExternalId();
				return;
			case GroupPackage.XCLUSTER__HEAD_ID:
				unsetHeadId();
				return;
			case GroupPackage.XCLUSTER__LAST_MESSAGE_TIME:
				unsetLastMessageTime();
				return;
			case GroupPackage.XCLUSTER__MESSAGE_SETTING:
				setMessageSetting(MESSAGE_SETTING_EDEFAULT);
				return;
			case GroupPackage.XCLUSTER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case GroupPackage.XCLUSTER__NOTICE:
				unsetNotice();
				return;
			case GroupPackage.XCLUSTER__OLD_CATEGORY:
				unsetOldCategory();
				return;
			case GroupPackage.XCLUSTER__PARENT_CLUSTER_ID:
				unsetParentClusterId();
				return;
			case GroupPackage.XCLUSTER__STOCKHOLDERS:
				setStockholders(STOCKHOLDERS_EDEFAULT);
				return;
			case GroupPackage.XCLUSTER__TYPE:
				setType(TYPE_EDEFAULT);
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
			case GroupPackage.XCLUSTER__USER:
				return user != null && !user.isEmpty();
			case GroupPackage.XCLUSTER__ORGANIZATION:
				return organization != null && !organization.isEmpty();
			case GroupPackage.XCLUSTER__ADMINS:
				return ADMINS_EDEFAULT == null ? admins != null : !ADMINS_EDEFAULT.equals(admins);
			case GroupPackage.XCLUSTER__AUTH_TYPE:
				return isSetAuthType();
			case GroupPackage.XCLUSTER__CATEGORY:
				return isSetCategory();
			case GroupPackage.XCLUSTER__CLUSTER_ID:
				return isSetClusterId();
			case GroupPackage.XCLUSTER__CREATOR:
				return isSetCreator();
			case GroupPackage.XCLUSTER__DESCRIPTION:
				return isSetDescription();
			case GroupPackage.XCLUSTER__EXTERNAL_ID:
				return isSetExternalId();
			case GroupPackage.XCLUSTER__HEAD_ID:
				return isSetHeadId();
			case GroupPackage.XCLUSTER__LAST_MESSAGE_TIME:
				return isSetLastMessageTime();
			case GroupPackage.XCLUSTER__MESSAGE_SETTING:
				return MESSAGE_SETTING_EDEFAULT == null ? messageSetting != null : !MESSAGE_SETTING_EDEFAULT.equals(messageSetting);
			case GroupPackage.XCLUSTER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case GroupPackage.XCLUSTER__NOTICE:
				return isSetNotice();
			case GroupPackage.XCLUSTER__OLD_CATEGORY:
				return isSetOldCategory();
			case GroupPackage.XCLUSTER__PARENT_CLUSTER_ID:
				return isSetParentClusterId();
			case GroupPackage.XCLUSTER__STOCKHOLDERS:
				return STOCKHOLDERS_EDEFAULT == null ? stockholders != null : !STOCKHOLDERS_EDEFAULT.equals(stockholders);
			case GroupPackage.XCLUSTER__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
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
		result.append(" (admins: ");
		result.append(admins);
		result.append(", authType: ");
		if (authTypeESet) result.append(authType); else result.append("<unset>");
		result.append(", category: ");
		if (categoryESet) result.append(category); else result.append("<unset>");
		result.append(", clusterId: ");
		if (clusterIdESet) result.append(clusterId); else result.append("<unset>");
		result.append(", creator: ");
		if (creatorESet) result.append(creator); else result.append("<unset>");
		result.append(", description: ");
		if (descriptionESet) result.append(description); else result.append("<unset>");
		result.append(", externalId: ");
		if (externalIdESet) result.append(externalId); else result.append("<unset>");
		result.append(", headId: ");
		if (headIdESet) result.append(headId); else result.append("<unset>");
		result.append(", lastMessageTime: ");
		if (lastMessageTimeESet) result.append(lastMessageTime); else result.append("<unset>");
		result.append(", messageSetting: ");
		result.append(messageSetting);
		result.append(", name: ");
		result.append(name);
		result.append(", notice: ");
		if (noticeESet) result.append(notice); else result.append("<unset>");
		result.append(", oldCategory: ");
		if (oldCategoryESet) result.append(oldCategory); else result.append("<unset>");
		result.append(", parentClusterId: ");
		if (parentClusterIdESet) result.append(parentClusterId); else result.append("<unset>");
		result.append(", stockholders: ");
		result.append(stockholders);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //XClusterImpl
