/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.group.impl;

import edu.tsinghua.lumaqq.ecore.group.GroupPackage;
import edu.tsinghua.lumaqq.ecore.group.XUser;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>XUser</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getCardName <em>Card Name</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#isFemale <em>Female</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getHeadId <em>Head Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getLastMessageTime <em>Last Message Time</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getLevel <em>Level</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#isMember <em>Member</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getNick <em>Nick</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getOrganizationId <em>Organization Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#isPinned <em>Pinned</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getQq <em>Qq</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getSignature <em>Signature</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#isTalkMode <em>Talk Mode</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#isHasCustomHead <em>Has Custom Head</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getCustomHeadId <em>Custom Head Id</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getUserFlag <em>User Flag</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getCustomHeadTimestamp <em>Custom Head Timestamp</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getWindowX <em>Window X</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.group.impl.XUserImpl#getWindowY <em>Window Y</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class XUserImpl extends EObjectImpl implements XUser {
	/**
	 * The default value of the '{@link #getCardName() <em>Card Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCardName()
	 * @generated
	 * @ordered
	 */
	protected static final String CARD_NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getCardName() <em>Card Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCardName()
	 * @generated
	 * @ordered
	 */
	protected String cardName = CARD_NAME_EDEFAULT;

	/**
	 * This is true if the Card Name attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean cardNameESet = false;

	/**
	 * The default value of the '{@link #isFemale() <em>Female</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFemale()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FEMALE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFemale() <em>Female</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFemale()
	 * @generated
	 * @ordered
	 */
	protected boolean female = FEMALE_EDEFAULT;

	/**
	 * This is true if the Female attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean femaleESet = false;

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
	 * The default value of the '{@link #getLevel() <em>Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLevel()
	 * @generated
	 * @ordered
	 */
	protected static final int LEVEL_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLevel() <em>Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLevel()
	 * @generated
	 * @ordered
	 */
	protected int level = LEVEL_EDEFAULT;

	/**
	 * This is true if the Level attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean levelESet = false;

	/**
	 * The default value of the '{@link #isMember() <em>Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMember()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MEMBER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMember() <em>Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMember()
	 * @generated
	 * @ordered
	 */
	protected boolean member = MEMBER_EDEFAULT;

	/**
	 * This is true if the Member attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean memberESet = false;

	/**
	 * The default value of the '{@link #getNick() <em>Nick</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNick()
	 * @generated
	 * @ordered
	 */
	protected static final String NICK_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getNick() <em>Nick</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNick()
	 * @generated
	 * @ordered
	 */
	protected String nick = NICK_EDEFAULT;

	/**
	 * This is true if the Nick attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean nickESet = false;

	/**
	 * The default value of the '{@link #getOrganizationId() <em>Organization Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganizationId()
	 * @generated
	 * @ordered
	 */
	protected static final int ORGANIZATION_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOrganizationId() <em>Organization Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganizationId()
	 * @generated
	 * @ordered
	 */
	protected int organizationId = ORGANIZATION_ID_EDEFAULT;

	/**
	 * This is true if the Organization Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean organizationIdESet = false;

	/**
	 * The default value of the '{@link #isPinned() <em>Pinned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPinned()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PINNED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPinned() <em>Pinned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPinned()
	 * @generated
	 * @ordered
	 */
	protected boolean pinned = PINNED_EDEFAULT;

	/**
	 * This is true if the Pinned attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean pinnedESet = false;

	/**
	 * The default value of the '{@link #getQq() <em>Qq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQq()
	 * @generated
	 * @ordered
	 */
	protected static final int QQ_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getQq() <em>Qq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQq()
	 * @generated
	 * @ordered
	 */
	protected int qq = QQ_EDEFAULT;

	/**
	 * This is true if the Qq attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean qqESet = false;

	/**
	 * The default value of the '{@link #getSignature() <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected static final String SIGNATURE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getSignature() <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected String signature = SIGNATURE_EDEFAULT;

	/**
	 * This is true if the Signature attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean signatureESet = false;

	/**
	 * The default value of the '{@link #isTalkMode() <em>Talk Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTalkMode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TALK_MODE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTalkMode() <em>Talk Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTalkMode()
	 * @generated
	 * @ordered
	 */
	protected boolean talkMode = TALK_MODE_EDEFAULT;

	/**
	 * This is true if the Talk Mode attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean talkModeESet = false;

	/**
	 * The default value of the '{@link #isHasCustomHead() <em>Has Custom Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasCustomHead()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_CUSTOM_HEAD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasCustomHead() <em>Has Custom Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasCustomHead()
	 * @generated
	 * @ordered
	 */
	protected boolean hasCustomHead = HAS_CUSTOM_HEAD_EDEFAULT;

	/**
	 * This is true if the Has Custom Head attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean hasCustomHeadESet = false;

	/**
	 * The default value of the '{@link #getCustomHeadId() <em>Custom Head Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomHeadId()
	 * @generated
	 * @ordered
	 */
	protected static final int CUSTOM_HEAD_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCustomHeadId() <em>Custom Head Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomHeadId()
	 * @generated
	 * @ordered
	 */
	protected int customHeadId = CUSTOM_HEAD_ID_EDEFAULT;

	/**
	 * This is true if the Custom Head Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean customHeadIdESet = false;

	/**
	 * The default value of the '{@link #getUserFlag() <em>User Flag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserFlag()
	 * @generated
	 * @ordered
	 */
	protected static final int USER_FLAG_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getUserFlag() <em>User Flag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserFlag()
	 * @generated
	 * @ordered
	 */
	protected int userFlag = USER_FLAG_EDEFAULT;

	/**
	 * This is true if the User Flag attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean userFlagESet = false;

	/**
	 * The default value of the '{@link #getCustomHeadTimestamp() <em>Custom Head Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomHeadTimestamp()
	 * @generated
	 * @ordered
	 */
	protected static final int CUSTOM_HEAD_TIMESTAMP_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCustomHeadTimestamp() <em>Custom Head Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomHeadTimestamp()
	 * @generated
	 * @ordered
	 */
	protected int customHeadTimestamp = CUSTOM_HEAD_TIMESTAMP_EDEFAULT;

	/**
	 * This is true if the Custom Head Timestamp attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean customHeadTimestampESet = false;

	/**
	 * The default value of the '{@link #getWindowX() <em>Window X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindowX()
	 * @generated
	 * @ordered
	 */
	protected static final int WINDOW_X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWindowX() <em>Window X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindowX()
	 * @generated
	 * @ordered
	 */
	protected int windowX = WINDOW_X_EDEFAULT;

	/**
	 * This is true if the Window X attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean windowXESet = false;

	/**
	 * The default value of the '{@link #getWindowY() <em>Window Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindowY()
	 * @generated
	 * @ordered
	 */
	protected static final int WINDOW_Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWindowY() <em>Window Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindowY()
	 * @generated
	 * @ordered
	 */
	protected int windowY = WINDOW_Y_EDEFAULT;

	/**
	 * This is true if the Window Y attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean windowYESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XUserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GroupPackage.Literals.XUSER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCardName(String newCardName) {
		String oldCardName = cardName;
		cardName = newCardName;
		boolean oldCardNameESet = cardNameESet;
		cardNameESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__CARD_NAME, oldCardName, cardName, !oldCardNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCardName() {
		String oldCardName = cardName;
		boolean oldCardNameESet = cardNameESet;
		cardName = CARD_NAME_EDEFAULT;
		cardNameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__CARD_NAME, oldCardName, CARD_NAME_EDEFAULT, oldCardNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCardName() {
		return cardNameESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFemale() {
		return female;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFemale(boolean newFemale) {
		boolean oldFemale = female;
		female = newFemale;
		boolean oldFemaleESet = femaleESet;
		femaleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__FEMALE, oldFemale, female, !oldFemaleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFemale() {
		boolean oldFemale = female;
		boolean oldFemaleESet = femaleESet;
		female = FEMALE_EDEFAULT;
		femaleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__FEMALE, oldFemale, FEMALE_EDEFAULT, oldFemaleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFemale() {
		return femaleESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__HEAD_ID, oldHeadId, headId, !oldHeadIdESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__HEAD_ID, oldHeadId, HEAD_ID_EDEFAULT, oldHeadIdESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__LAST_MESSAGE_TIME, oldLastMessageTime, lastMessageTime, !oldLastMessageTimeESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__LAST_MESSAGE_TIME, oldLastMessageTime, LAST_MESSAGE_TIME_EDEFAULT, oldLastMessageTimeESet));
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
	public int getLevel() {
		return level;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLevel(int newLevel) {
		int oldLevel = level;
		level = newLevel;
		boolean oldLevelESet = levelESet;
		levelESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__LEVEL, oldLevel, level, !oldLevelESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLevel() {
		int oldLevel = level;
		boolean oldLevelESet = levelESet;
		level = LEVEL_EDEFAULT;
		levelESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__LEVEL, oldLevel, LEVEL_EDEFAULT, oldLevelESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLevel() {
		return levelESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMember() {
		return member;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMember(boolean newMember) {
		boolean oldMember = member;
		member = newMember;
		boolean oldMemberESet = memberESet;
		memberESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__MEMBER, oldMember, member, !oldMemberESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMember() {
		boolean oldMember = member;
		boolean oldMemberESet = memberESet;
		member = MEMBER_EDEFAULT;
		memberESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__MEMBER, oldMember, MEMBER_EDEFAULT, oldMemberESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMember() {
		return memberESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNick(String newNick) {
		String oldNick = nick;
		nick = newNick;
		boolean oldNickESet = nickESet;
		nickESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__NICK, oldNick, nick, !oldNickESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetNick() {
		String oldNick = nick;
		boolean oldNickESet = nickESet;
		nick = NICK_EDEFAULT;
		nickESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__NICK, oldNick, NICK_EDEFAULT, oldNickESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetNick() {
		return nickESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOrganizationId() {
		return organizationId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrganizationId(int newOrganizationId) {
		int oldOrganizationId = organizationId;
		organizationId = newOrganizationId;
		boolean oldOrganizationIdESet = organizationIdESet;
		organizationIdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__ORGANIZATION_ID, oldOrganizationId, organizationId, !oldOrganizationIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOrganizationId() {
		int oldOrganizationId = organizationId;
		boolean oldOrganizationIdESet = organizationIdESet;
		organizationId = ORGANIZATION_ID_EDEFAULT;
		organizationIdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__ORGANIZATION_ID, oldOrganizationId, ORGANIZATION_ID_EDEFAULT, oldOrganizationIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOrganizationId() {
		return organizationIdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPinned() {
		return pinned;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPinned(boolean newPinned) {
		boolean oldPinned = pinned;
		pinned = newPinned;
		boolean oldPinnedESet = pinnedESet;
		pinnedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__PINNED, oldPinned, pinned, !oldPinnedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPinned() {
		boolean oldPinned = pinned;
		boolean oldPinnedESet = pinnedESet;
		pinned = PINNED_EDEFAULT;
		pinnedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__PINNED, oldPinned, PINNED_EDEFAULT, oldPinnedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPinned() {
		return pinnedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getQq() {
		return qq;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQq(int newQq) {
		int oldQq = qq;
		qq = newQq;
		boolean oldQqESet = qqESet;
		qqESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__QQ, oldQq, qq, !oldQqESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQq() {
		int oldQq = qq;
		boolean oldQqESet = qqESet;
		qq = QQ_EDEFAULT;
		qqESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__QQ, oldQq, QQ_EDEFAULT, oldQqESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQq() {
		return qqESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignature(String newSignature) {
		String oldSignature = signature;
		signature = newSignature;
		boolean oldSignatureESet = signatureESet;
		signatureESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__SIGNATURE, oldSignature, signature, !oldSignatureESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSignature() {
		String oldSignature = signature;
		boolean oldSignatureESet = signatureESet;
		signature = SIGNATURE_EDEFAULT;
		signatureESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__SIGNATURE, oldSignature, SIGNATURE_EDEFAULT, oldSignatureESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSignature() {
		return signatureESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTalkMode() {
		return talkMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTalkMode(boolean newTalkMode) {
		boolean oldTalkMode = talkMode;
		talkMode = newTalkMode;
		boolean oldTalkModeESet = talkModeESet;
		talkModeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__TALK_MODE, oldTalkMode, talkMode, !oldTalkModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTalkMode() {
		boolean oldTalkMode = talkMode;
		boolean oldTalkModeESet = talkModeESet;
		talkMode = TALK_MODE_EDEFAULT;
		talkModeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__TALK_MODE, oldTalkMode, TALK_MODE_EDEFAULT, oldTalkModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTalkMode() {
		return talkModeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasCustomHead() {
		return hasCustomHead;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasCustomHead(boolean newHasCustomHead) {
		boolean oldHasCustomHead = hasCustomHead;
		hasCustomHead = newHasCustomHead;
		boolean oldHasCustomHeadESet = hasCustomHeadESet;
		hasCustomHeadESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__HAS_CUSTOM_HEAD, oldHasCustomHead, hasCustomHead, !oldHasCustomHeadESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetHasCustomHead() {
		boolean oldHasCustomHead = hasCustomHead;
		boolean oldHasCustomHeadESet = hasCustomHeadESet;
		hasCustomHead = HAS_CUSTOM_HEAD_EDEFAULT;
		hasCustomHeadESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__HAS_CUSTOM_HEAD, oldHasCustomHead, HAS_CUSTOM_HEAD_EDEFAULT, oldHasCustomHeadESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetHasCustomHead() {
		return hasCustomHeadESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCustomHeadId() {
		return customHeadId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCustomHeadId(int newCustomHeadId) {
		int oldCustomHeadId = customHeadId;
		customHeadId = newCustomHeadId;
		boolean oldCustomHeadIdESet = customHeadIdESet;
		customHeadIdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__CUSTOM_HEAD_ID, oldCustomHeadId, customHeadId, !oldCustomHeadIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCustomHeadId() {
		int oldCustomHeadId = customHeadId;
		boolean oldCustomHeadIdESet = customHeadIdESet;
		customHeadId = CUSTOM_HEAD_ID_EDEFAULT;
		customHeadIdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__CUSTOM_HEAD_ID, oldCustomHeadId, CUSTOM_HEAD_ID_EDEFAULT, oldCustomHeadIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCustomHeadId() {
		return customHeadIdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getUserFlag() {
		return userFlag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserFlag(int newUserFlag) {
		int oldUserFlag = userFlag;
		userFlag = newUserFlag;
		boolean oldUserFlagESet = userFlagESet;
		userFlagESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__USER_FLAG, oldUserFlag, userFlag, !oldUserFlagESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetUserFlag() {
		int oldUserFlag = userFlag;
		boolean oldUserFlagESet = userFlagESet;
		userFlag = USER_FLAG_EDEFAULT;
		userFlagESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__USER_FLAG, oldUserFlag, USER_FLAG_EDEFAULT, oldUserFlagESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetUserFlag() {
		return userFlagESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCustomHeadTimestamp() {
		return customHeadTimestamp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCustomHeadTimestamp(int newCustomHeadTimestamp) {
		int oldCustomHeadTimestamp = customHeadTimestamp;
		customHeadTimestamp = newCustomHeadTimestamp;
		boolean oldCustomHeadTimestampESet = customHeadTimestampESet;
		customHeadTimestampESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__CUSTOM_HEAD_TIMESTAMP, oldCustomHeadTimestamp, customHeadTimestamp, !oldCustomHeadTimestampESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCustomHeadTimestamp() {
		int oldCustomHeadTimestamp = customHeadTimestamp;
		boolean oldCustomHeadTimestampESet = customHeadTimestampESet;
		customHeadTimestamp = CUSTOM_HEAD_TIMESTAMP_EDEFAULT;
		customHeadTimestampESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__CUSTOM_HEAD_TIMESTAMP, oldCustomHeadTimestamp, CUSTOM_HEAD_TIMESTAMP_EDEFAULT, oldCustomHeadTimestampESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCustomHeadTimestamp() {
		return customHeadTimestampESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getWindowX() {
		return windowX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWindowX(int newWindowX) {
		int oldWindowX = windowX;
		windowX = newWindowX;
		boolean oldWindowXESet = windowXESet;
		windowXESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__WINDOW_X, oldWindowX, windowX, !oldWindowXESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetWindowX() {
		int oldWindowX = windowX;
		boolean oldWindowXESet = windowXESet;
		windowX = WINDOW_X_EDEFAULT;
		windowXESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__WINDOW_X, oldWindowX, WINDOW_X_EDEFAULT, oldWindowXESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetWindowX() {
		return windowXESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getWindowY() {
		return windowY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWindowY(int newWindowY) {
		int oldWindowY = windowY;
		windowY = newWindowY;
		boolean oldWindowYESet = windowYESet;
		windowYESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GroupPackage.XUSER__WINDOW_Y, oldWindowY, windowY, !oldWindowYESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetWindowY() {
		int oldWindowY = windowY;
		boolean oldWindowYESet = windowYESet;
		windowY = WINDOW_Y_EDEFAULT;
		windowYESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GroupPackage.XUSER__WINDOW_Y, oldWindowY, WINDOW_Y_EDEFAULT, oldWindowYESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetWindowY() {
		return windowYESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GroupPackage.XUSER__CARD_NAME:
				return getCardName();
			case GroupPackage.XUSER__FEMALE:
				return isFemale() ? Boolean.TRUE : Boolean.FALSE;
			case GroupPackage.XUSER__HEAD_ID:
				return new Integer(getHeadId());
			case GroupPackage.XUSER__LAST_MESSAGE_TIME:
				return new Long(getLastMessageTime());
			case GroupPackage.XUSER__LEVEL:
				return new Integer(getLevel());
			case GroupPackage.XUSER__MEMBER:
				return isMember() ? Boolean.TRUE : Boolean.FALSE;
			case GroupPackage.XUSER__NICK:
				return getNick();
			case GroupPackage.XUSER__ORGANIZATION_ID:
				return new Integer(getOrganizationId());
			case GroupPackage.XUSER__PINNED:
				return isPinned() ? Boolean.TRUE : Boolean.FALSE;
			case GroupPackage.XUSER__QQ:
				return new Integer(getQq());
			case GroupPackage.XUSER__SIGNATURE:
				return getSignature();
			case GroupPackage.XUSER__TALK_MODE:
				return isTalkMode() ? Boolean.TRUE : Boolean.FALSE;
			case GroupPackage.XUSER__HAS_CUSTOM_HEAD:
				return isHasCustomHead() ? Boolean.TRUE : Boolean.FALSE;
			case GroupPackage.XUSER__CUSTOM_HEAD_ID:
				return new Integer(getCustomHeadId());
			case GroupPackage.XUSER__USER_FLAG:
				return new Integer(getUserFlag());
			case GroupPackage.XUSER__CUSTOM_HEAD_TIMESTAMP:
				return new Integer(getCustomHeadTimestamp());
			case GroupPackage.XUSER__WINDOW_X:
				return new Integer(getWindowX());
			case GroupPackage.XUSER__WINDOW_Y:
				return new Integer(getWindowY());
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GroupPackage.XUSER__CARD_NAME:
				setCardName((String)newValue);
				return;
			case GroupPackage.XUSER__FEMALE:
				setFemale(((Boolean)newValue).booleanValue());
				return;
			case GroupPackage.XUSER__HEAD_ID:
				setHeadId(((Integer)newValue).intValue());
				return;
			case GroupPackage.XUSER__LAST_MESSAGE_TIME:
				setLastMessageTime(((Long)newValue).longValue());
				return;
			case GroupPackage.XUSER__LEVEL:
				setLevel(((Integer)newValue).intValue());
				return;
			case GroupPackage.XUSER__MEMBER:
				setMember(((Boolean)newValue).booleanValue());
				return;
			case GroupPackage.XUSER__NICK:
				setNick((String)newValue);
				return;
			case GroupPackage.XUSER__ORGANIZATION_ID:
				setOrganizationId(((Integer)newValue).intValue());
				return;
			case GroupPackage.XUSER__PINNED:
				setPinned(((Boolean)newValue).booleanValue());
				return;
			case GroupPackage.XUSER__QQ:
				setQq(((Integer)newValue).intValue());
				return;
			case GroupPackage.XUSER__SIGNATURE:
				setSignature((String)newValue);
				return;
			case GroupPackage.XUSER__TALK_MODE:
				setTalkMode(((Boolean)newValue).booleanValue());
				return;
			case GroupPackage.XUSER__HAS_CUSTOM_HEAD:
				setHasCustomHead(((Boolean)newValue).booleanValue());
				return;
			case GroupPackage.XUSER__CUSTOM_HEAD_ID:
				setCustomHeadId(((Integer)newValue).intValue());
				return;
			case GroupPackage.XUSER__USER_FLAG:
				setUserFlag(((Integer)newValue).intValue());
				return;
			case GroupPackage.XUSER__CUSTOM_HEAD_TIMESTAMP:
				setCustomHeadTimestamp(((Integer)newValue).intValue());
				return;
			case GroupPackage.XUSER__WINDOW_X:
				setWindowX(((Integer)newValue).intValue());
				return;
			case GroupPackage.XUSER__WINDOW_Y:
				setWindowY(((Integer)newValue).intValue());
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
			case GroupPackage.XUSER__CARD_NAME:
				unsetCardName();
				return;
			case GroupPackage.XUSER__FEMALE:
				unsetFemale();
				return;
			case GroupPackage.XUSER__HEAD_ID:
				unsetHeadId();
				return;
			case GroupPackage.XUSER__LAST_MESSAGE_TIME:
				unsetLastMessageTime();
				return;
			case GroupPackage.XUSER__LEVEL:
				unsetLevel();
				return;
			case GroupPackage.XUSER__MEMBER:
				unsetMember();
				return;
			case GroupPackage.XUSER__NICK:
				unsetNick();
				return;
			case GroupPackage.XUSER__ORGANIZATION_ID:
				unsetOrganizationId();
				return;
			case GroupPackage.XUSER__PINNED:
				unsetPinned();
				return;
			case GroupPackage.XUSER__QQ:
				unsetQq();
				return;
			case GroupPackage.XUSER__SIGNATURE:
				unsetSignature();
				return;
			case GroupPackage.XUSER__TALK_MODE:
				unsetTalkMode();
				return;
			case GroupPackage.XUSER__HAS_CUSTOM_HEAD:
				unsetHasCustomHead();
				return;
			case GroupPackage.XUSER__CUSTOM_HEAD_ID:
				unsetCustomHeadId();
				return;
			case GroupPackage.XUSER__USER_FLAG:
				unsetUserFlag();
				return;
			case GroupPackage.XUSER__CUSTOM_HEAD_TIMESTAMP:
				unsetCustomHeadTimestamp();
				return;
			case GroupPackage.XUSER__WINDOW_X:
				unsetWindowX();
				return;
			case GroupPackage.XUSER__WINDOW_Y:
				unsetWindowY();
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
			case GroupPackage.XUSER__CARD_NAME:
				return isSetCardName();
			case GroupPackage.XUSER__FEMALE:
				return isSetFemale();
			case GroupPackage.XUSER__HEAD_ID:
				return isSetHeadId();
			case GroupPackage.XUSER__LAST_MESSAGE_TIME:
				return isSetLastMessageTime();
			case GroupPackage.XUSER__LEVEL:
				return isSetLevel();
			case GroupPackage.XUSER__MEMBER:
				return isSetMember();
			case GroupPackage.XUSER__NICK:
				return isSetNick();
			case GroupPackage.XUSER__ORGANIZATION_ID:
				return isSetOrganizationId();
			case GroupPackage.XUSER__PINNED:
				return isSetPinned();
			case GroupPackage.XUSER__QQ:
				return isSetQq();
			case GroupPackage.XUSER__SIGNATURE:
				return isSetSignature();
			case GroupPackage.XUSER__TALK_MODE:
				return isSetTalkMode();
			case GroupPackage.XUSER__HAS_CUSTOM_HEAD:
				return isSetHasCustomHead();
			case GroupPackage.XUSER__CUSTOM_HEAD_ID:
				return isSetCustomHeadId();
			case GroupPackage.XUSER__USER_FLAG:
				return isSetUserFlag();
			case GroupPackage.XUSER__CUSTOM_HEAD_TIMESTAMP:
				return isSetCustomHeadTimestamp();
			case GroupPackage.XUSER__WINDOW_X:
				return isSetWindowX();
			case GroupPackage.XUSER__WINDOW_Y:
				return isSetWindowY();
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
		result.append(" (cardName: ");
		if (cardNameESet) result.append(cardName); else result.append("<unset>");
		result.append(", female: ");
		if (femaleESet) result.append(female); else result.append("<unset>");
		result.append(", headId: ");
		if (headIdESet) result.append(headId); else result.append("<unset>");
		result.append(", lastMessageTime: ");
		if (lastMessageTimeESet) result.append(lastMessageTime); else result.append("<unset>");
		result.append(", level: ");
		if (levelESet) result.append(level); else result.append("<unset>");
		result.append(", member: ");
		if (memberESet) result.append(member); else result.append("<unset>");
		result.append(", nick: ");
		if (nickESet) result.append(nick); else result.append("<unset>");
		result.append(", organizationId: ");
		if (organizationIdESet) result.append(organizationId); else result.append("<unset>");
		result.append(", pinned: ");
		if (pinnedESet) result.append(pinned); else result.append("<unset>");
		result.append(", qq: ");
		if (qqESet) result.append(qq); else result.append("<unset>");
		result.append(", signature: ");
		if (signatureESet) result.append(signature); else result.append("<unset>");
		result.append(", talkMode: ");
		if (talkModeESet) result.append(talkMode); else result.append("<unset>");
		result.append(", hasCustomHead: ");
		if (hasCustomHeadESet) result.append(hasCustomHead); else result.append("<unset>");
		result.append(", customHeadId: ");
		if (customHeadIdESet) result.append(customHeadId); else result.append("<unset>");
		result.append(", userFlag: ");
		if (userFlagESet) result.append(userFlag); else result.append("<unset>");
		result.append(", customHeadTimestamp: ");
		if (customHeadTimestampESet) result.append(customHeadTimestamp); else result.append("<unset>");
		result.append(", windowX: ");
		if (windowXESet) result.append(windowX); else result.append("<unset>");
		result.append(", windowY: ");
		if (windowYESet) result.append(windowY); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //XUserImpl
