/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option.impl;

import edu.tsinghua.lumaqq.ecore.option.GUIOption;
import edu.tsinghua.lumaqq.ecore.option.OptionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GUI Option</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isAutoDock <em>Auto Dock</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isBold <em>Bold</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#getFontColor <em>Font Color</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#getFontName <em>Font Name</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#getGroupBackground <em>Group Background</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isHideWhenMinimize <em>Hide When Minimize</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isImOnTop <em>Im On Top</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isItalic <em>Italic</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#getLocationX <em>Location X</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#getLocationY <em>Location Y</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isMinimizeWhenClose <em>Minimize When Close</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#getOnlineTipLocationX <em>Online Tip Location X</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#getOnlineTipLocationY <em>Online Tip Location Y</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isOnTop <em>On Top</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isShowBlacklist <em>Show Blacklist</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isShowFriendTip <em>Show Friend Tip</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isShowNick <em>Show Nick</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isShowOnlineOnly <em>Show Online Only</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isShowOnlineTip <em>Show Online Tip</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isShowLastLoginTip <em>Show Last Login Tip</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isShowSignature <em>Show Signature</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isShowCustomHead <em>Show Custom Head</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isShowSmallHead <em>Show Small Head</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isTreeMode <em>Tree Mode</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isUseTabIMWindow <em>Use Tab IM Window</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#isBarExpanded <em>Bar Expanded</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl#getWidth <em>Width</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GUIOptionImpl extends EObjectImpl implements GUIOption {
	/**
	 * The default value of the '{@link #isAutoDock() <em>Auto Dock</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoDock()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTO_DOCK_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAutoDock() <em>Auto Dock</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoDock()
	 * @generated
	 * @ordered
	 */
	protected boolean autoDock = AUTO_DOCK_EDEFAULT;

	/**
	 * This is true if the Auto Dock attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean autoDockESet = false;

	/**
	 * The default value of the '{@link #isBold() <em>Bold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBold()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOLD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBold() <em>Bold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBold()
	 * @generated
	 * @ordered
	 */
	protected boolean bold = BOLD_EDEFAULT;

	/**
	 * This is true if the Bold attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean boldESet = false;

	/**
	 * The default value of the '{@link #getFontColor() <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontColor()
	 * @generated
	 * @ordered
	 */
	protected static final int FONT_COLOR_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getFontColor() <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontColor()
	 * @generated
	 * @ordered
	 */
	protected int fontColor = FONT_COLOR_EDEFAULT;

	/**
	 * This is true if the Font Color attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean fontColorESet = false;

	/**
	 * The default value of the '{@link #getFontName() <em>Font Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontName()
	 * @generated
	 * @ordered
	 */
	protected static final String FONT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFontName() <em>Font Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontName()
	 * @generated
	 * @ordered
	 */
	protected String fontName = FONT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFontSize() <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontSize()
	 * @generated
	 * @ordered
	 */
	protected static final int FONT_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getFontSize() <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontSize()
	 * @generated
	 * @ordered
	 */
	protected int fontSize = FONT_SIZE_EDEFAULT;

	/**
	 * This is true if the Font Size attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean fontSizeESet = false;

	/**
	 * The default value of the '{@link #getGroupBackground() <em>Group Background</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupBackground()
	 * @generated
	 * @ordered
	 */
	protected static final int GROUP_BACKGROUND_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getGroupBackground() <em>Group Background</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupBackground()
	 * @generated
	 * @ordered
	 */
	protected int groupBackground = GROUP_BACKGROUND_EDEFAULT;

	/**
	 * This is true if the Group Background attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean groupBackgroundESet = false;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int HEIGHT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected int height = HEIGHT_EDEFAULT;

	/**
	 * This is true if the Height attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean heightESet = false;

	/**
	 * The default value of the '{@link #isHideWhenMinimize() <em>Hide When Minimize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHideWhenMinimize()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HIDE_WHEN_MINIMIZE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHideWhenMinimize() <em>Hide When Minimize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHideWhenMinimize()
	 * @generated
	 * @ordered
	 */
	protected boolean hideWhenMinimize = HIDE_WHEN_MINIMIZE_EDEFAULT;

	/**
	 * This is true if the Hide When Minimize attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean hideWhenMinimizeESet = false;

	/**
	 * The default value of the '{@link #isImOnTop() <em>Im On Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isImOnTop()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IM_ON_TOP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isImOnTop() <em>Im On Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isImOnTop()
	 * @generated
	 * @ordered
	 */
	protected boolean imOnTop = IM_ON_TOP_EDEFAULT;

	/**
	 * This is true if the Im On Top attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean imOnTopESet = false;

	/**
	 * The default value of the '{@link #isItalic() <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isItalic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ITALIC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isItalic() <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isItalic()
	 * @generated
	 * @ordered
	 */
	protected boolean italic = ITALIC_EDEFAULT;

	/**
	 * This is true if the Italic attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean italicESet = false;

	/**
	 * The default value of the '{@link #getLocationX() <em>Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocationX()
	 * @generated
	 * @ordered
	 */
	protected static final int LOCATION_X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLocationX() <em>Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocationX()
	 * @generated
	 * @ordered
	 */
	protected int locationX = LOCATION_X_EDEFAULT;

	/**
	 * This is true if the Location X attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean locationXESet = false;

	/**
	 * The default value of the '{@link #getLocationY() <em>Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocationY()
	 * @generated
	 * @ordered
	 */
	protected static final int LOCATION_Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLocationY() <em>Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocationY()
	 * @generated
	 * @ordered
	 */
	protected int locationY = LOCATION_Y_EDEFAULT;

	/**
	 * This is true if the Location Y attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean locationYESet = false;

	/**
	 * The default value of the '{@link #isMinimizeWhenClose() <em>Minimize When Close</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMinimizeWhenClose()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MINIMIZE_WHEN_CLOSE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMinimizeWhenClose() <em>Minimize When Close</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMinimizeWhenClose()
	 * @generated
	 * @ordered
	 */
	protected boolean minimizeWhenClose = MINIMIZE_WHEN_CLOSE_EDEFAULT;

	/**
	 * This is true if the Minimize When Close attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean minimizeWhenCloseESet = false;

	/**
	 * The default value of the '{@link #getOnlineTipLocationX() <em>Online Tip Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnlineTipLocationX()
	 * @generated
	 * @ordered
	 */
	protected static final int ONLINE_TIP_LOCATION_X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOnlineTipLocationX() <em>Online Tip Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnlineTipLocationX()
	 * @generated
	 * @ordered
	 */
	protected int onlineTipLocationX = ONLINE_TIP_LOCATION_X_EDEFAULT;

	/**
	 * This is true if the Online Tip Location X attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean onlineTipLocationXESet = false;

	/**
	 * The default value of the '{@link #getOnlineTipLocationY() <em>Online Tip Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnlineTipLocationY()
	 * @generated
	 * @ordered
	 */
	protected static final int ONLINE_TIP_LOCATION_Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOnlineTipLocationY() <em>Online Tip Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnlineTipLocationY()
	 * @generated
	 * @ordered
	 */
	protected int onlineTipLocationY = ONLINE_TIP_LOCATION_Y_EDEFAULT;

	/**
	 * This is true if the Online Tip Location Y attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean onlineTipLocationYESet = false;

	/**
	 * The default value of the '{@link #isOnTop() <em>On Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOnTop()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ON_TOP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOnTop() <em>On Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOnTop()
	 * @generated
	 * @ordered
	 */
	protected boolean onTop = ON_TOP_EDEFAULT;

	/**
	 * This is true if the On Top attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean onTopESet = false;

	/**
	 * The default value of the '{@link #isShowBlacklist() <em>Show Blacklist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowBlacklist()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_BLACKLIST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowBlacklist() <em>Show Blacklist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowBlacklist()
	 * @generated
	 * @ordered
	 */
	protected boolean showBlacklist = SHOW_BLACKLIST_EDEFAULT;

	/**
	 * This is true if the Show Blacklist attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showBlacklistESet = false;

	/**
	 * The default value of the '{@link #isShowFriendTip() <em>Show Friend Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowFriendTip()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_FRIEND_TIP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowFriendTip() <em>Show Friend Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowFriendTip()
	 * @generated
	 * @ordered
	 */
	protected boolean showFriendTip = SHOW_FRIEND_TIP_EDEFAULT;

	/**
	 * This is true if the Show Friend Tip attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showFriendTipESet = false;

	/**
	 * The default value of the '{@link #isShowNick() <em>Show Nick</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowNick()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_NICK_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowNick() <em>Show Nick</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowNick()
	 * @generated
	 * @ordered
	 */
	protected boolean showNick = SHOW_NICK_EDEFAULT;

	/**
	 * This is true if the Show Nick attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showNickESet = false;

	/**
	 * The default value of the '{@link #isShowOnlineOnly() <em>Show Online Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowOnlineOnly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_ONLINE_ONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowOnlineOnly() <em>Show Online Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowOnlineOnly()
	 * @generated
	 * @ordered
	 */
	protected boolean showOnlineOnly = SHOW_ONLINE_ONLY_EDEFAULT;

	/**
	 * This is true if the Show Online Only attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showOnlineOnlyESet = false;

	/**
	 * The default value of the '{@link #isShowOnlineTip() <em>Show Online Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowOnlineTip()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_ONLINE_TIP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowOnlineTip() <em>Show Online Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowOnlineTip()
	 * @generated
	 * @ordered
	 */
	protected boolean showOnlineTip = SHOW_ONLINE_TIP_EDEFAULT;

	/**
	 * This is true if the Show Online Tip attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showOnlineTipESet = false;

	/**
	 * The default value of the '{@link #isShowLastLoginTip() <em>Show Last Login Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowLastLoginTip()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_LAST_LOGIN_TIP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowLastLoginTip() <em>Show Last Login Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowLastLoginTip()
	 * @generated
	 * @ordered
	 */
	protected boolean showLastLoginTip = SHOW_LAST_LOGIN_TIP_EDEFAULT;

	/**
	 * This is true if the Show Last Login Tip attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showLastLoginTipESet = false;

	/**
	 * The default value of the '{@link #isShowSignature() <em>Show Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowSignature()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_SIGNATURE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowSignature() <em>Show Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowSignature()
	 * @generated
	 * @ordered
	 */
	protected boolean showSignature = SHOW_SIGNATURE_EDEFAULT;

	/**
	 * This is true if the Show Signature attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showSignatureESet = false;

	/**
	 * The default value of the '{@link #isShowCustomHead() <em>Show Custom Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowCustomHead()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_CUSTOM_HEAD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowCustomHead() <em>Show Custom Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowCustomHead()
	 * @generated
	 * @ordered
	 */
	protected boolean showCustomHead = SHOW_CUSTOM_HEAD_EDEFAULT;

	/**
	 * This is true if the Show Custom Head attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showCustomHeadESet = false;

	/**
	 * The default value of the '{@link #isShowSmallHead() <em>Show Small Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowSmallHead()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_SMALL_HEAD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowSmallHead() <em>Show Small Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowSmallHead()
	 * @generated
	 * @ordered
	 */
	protected boolean showSmallHead = SHOW_SMALL_HEAD_EDEFAULT;

	/**
	 * This is true if the Show Small Head attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showSmallHeadESet = false;

	/**
	 * The default value of the '{@link #isTreeMode() <em>Tree Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTreeMode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TREE_MODE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTreeMode() <em>Tree Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTreeMode()
	 * @generated
	 * @ordered
	 */
	protected boolean treeMode = TREE_MODE_EDEFAULT;

	/**
	 * This is true if the Tree Mode attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean treeModeESet = false;

	/**
	 * The default value of the '{@link #isUseTabIMWindow() <em>Use Tab IM Window</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseTabIMWindow()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_TAB_IM_WINDOW_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseTabIMWindow() <em>Use Tab IM Window</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseTabIMWindow()
	 * @generated
	 * @ordered
	 */
	protected boolean useTabIMWindow = USE_TAB_IM_WINDOW_EDEFAULT;

	/**
	 * This is true if the Use Tab IM Window attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean useTabIMWindowESet = false;

	/**
	 * The default value of the '{@link #isBarExpanded() <em>Bar Expanded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBarExpanded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BAR_EXPANDED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBarExpanded() <em>Bar Expanded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBarExpanded()
	 * @generated
	 * @ordered
	 */
	protected boolean barExpanded = BAR_EXPANDED_EDEFAULT;

	/**
	 * This is true if the Bar Expanded attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean barExpandedESet = false;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected int width = WIDTH_EDEFAULT;

	/**
	 * This is true if the Width attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean widthESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GUIOptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OptionPackage.Literals.GUI_OPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAutoDock() {
		return autoDock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoDock(boolean newAutoDock) {
		boolean oldAutoDock = autoDock;
		autoDock = newAutoDock;
		boolean oldAutoDockESet = autoDockESet;
		autoDockESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__AUTO_DOCK, oldAutoDock, autoDock, !oldAutoDockESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAutoDock() {
		boolean oldAutoDock = autoDock;
		boolean oldAutoDockESet = autoDockESet;
		autoDock = AUTO_DOCK_EDEFAULT;
		autoDockESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__AUTO_DOCK, oldAutoDock, AUTO_DOCK_EDEFAULT, oldAutoDockESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAutoDock() {
		return autoDockESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBold(boolean newBold) {
		boolean oldBold = bold;
		bold = newBold;
		boolean oldBoldESet = boldESet;
		boldESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__BOLD, oldBold, bold, !oldBoldESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetBold() {
		boolean oldBold = bold;
		boolean oldBoldESet = boldESet;
		bold = BOLD_EDEFAULT;
		boldESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__BOLD, oldBold, BOLD_EDEFAULT, oldBoldESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetBold() {
		return boldESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFontColor() {
		return fontColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontColor(int newFontColor) {
		int oldFontColor = fontColor;
		fontColor = newFontColor;
		boolean oldFontColorESet = fontColorESet;
		fontColorESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__FONT_COLOR, oldFontColor, fontColor, !oldFontColorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFontColor() {
		int oldFontColor = fontColor;
		boolean oldFontColorESet = fontColorESet;
		fontColor = FONT_COLOR_EDEFAULT;
		fontColorESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__FONT_COLOR, oldFontColor, FONT_COLOR_EDEFAULT, oldFontColorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFontColor() {
		return fontColorESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontName(String newFontName) {
		String oldFontName = fontName;
		fontName = newFontName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__FONT_NAME, oldFontName, fontName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontSize(int newFontSize) {
		int oldFontSize = fontSize;
		fontSize = newFontSize;
		boolean oldFontSizeESet = fontSizeESet;
		fontSizeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__FONT_SIZE, oldFontSize, fontSize, !oldFontSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFontSize() {
		int oldFontSize = fontSize;
		boolean oldFontSizeESet = fontSizeESet;
		fontSize = FONT_SIZE_EDEFAULT;
		fontSizeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__FONT_SIZE, oldFontSize, FONT_SIZE_EDEFAULT, oldFontSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFontSize() {
		return fontSizeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getGroupBackground() {
		return groupBackground;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroupBackground(int newGroupBackground) {
		int oldGroupBackground = groupBackground;
		groupBackground = newGroupBackground;
		boolean oldGroupBackgroundESet = groupBackgroundESet;
		groupBackgroundESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__GROUP_BACKGROUND, oldGroupBackground, groupBackground, !oldGroupBackgroundESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetGroupBackground() {
		int oldGroupBackground = groupBackground;
		boolean oldGroupBackgroundESet = groupBackgroundESet;
		groupBackground = GROUP_BACKGROUND_EDEFAULT;
		groupBackgroundESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__GROUP_BACKGROUND, oldGroupBackground, GROUP_BACKGROUND_EDEFAULT, oldGroupBackgroundESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetGroupBackground() {
		return groupBackgroundESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeight(int newHeight) {
		int oldHeight = height;
		height = newHeight;
		boolean oldHeightESet = heightESet;
		heightESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__HEIGHT, oldHeight, height, !oldHeightESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetHeight() {
		int oldHeight = height;
		boolean oldHeightESet = heightESet;
		height = HEIGHT_EDEFAULT;
		heightESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__HEIGHT, oldHeight, HEIGHT_EDEFAULT, oldHeightESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetHeight() {
		return heightESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHideWhenMinimize() {
		return hideWhenMinimize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHideWhenMinimize(boolean newHideWhenMinimize) {
		boolean oldHideWhenMinimize = hideWhenMinimize;
		hideWhenMinimize = newHideWhenMinimize;
		boolean oldHideWhenMinimizeESet = hideWhenMinimizeESet;
		hideWhenMinimizeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__HIDE_WHEN_MINIMIZE, oldHideWhenMinimize, hideWhenMinimize, !oldHideWhenMinimizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetHideWhenMinimize() {
		boolean oldHideWhenMinimize = hideWhenMinimize;
		boolean oldHideWhenMinimizeESet = hideWhenMinimizeESet;
		hideWhenMinimize = HIDE_WHEN_MINIMIZE_EDEFAULT;
		hideWhenMinimizeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__HIDE_WHEN_MINIMIZE, oldHideWhenMinimize, HIDE_WHEN_MINIMIZE_EDEFAULT, oldHideWhenMinimizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetHideWhenMinimize() {
		return hideWhenMinimizeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isImOnTop() {
		return imOnTop;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImOnTop(boolean newImOnTop) {
		boolean oldImOnTop = imOnTop;
		imOnTop = newImOnTop;
		boolean oldImOnTopESet = imOnTopESet;
		imOnTopESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__IM_ON_TOP, oldImOnTop, imOnTop, !oldImOnTopESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetImOnTop() {
		boolean oldImOnTop = imOnTop;
		boolean oldImOnTopESet = imOnTopESet;
		imOnTop = IM_ON_TOP_EDEFAULT;
		imOnTopESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__IM_ON_TOP, oldImOnTop, IM_ON_TOP_EDEFAULT, oldImOnTopESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetImOnTop() {
		return imOnTopESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isItalic() {
		return italic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setItalic(boolean newItalic) {
		boolean oldItalic = italic;
		italic = newItalic;
		boolean oldItalicESet = italicESet;
		italicESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__ITALIC, oldItalic, italic, !oldItalicESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetItalic() {
		boolean oldItalic = italic;
		boolean oldItalicESet = italicESet;
		italic = ITALIC_EDEFAULT;
		italicESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__ITALIC, oldItalic, ITALIC_EDEFAULT, oldItalicESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetItalic() {
		return italicESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLocationX() {
		return locationX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocationX(int newLocationX) {
		int oldLocationX = locationX;
		locationX = newLocationX;
		boolean oldLocationXESet = locationXESet;
		locationXESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__LOCATION_X, oldLocationX, locationX, !oldLocationXESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLocationX() {
		int oldLocationX = locationX;
		boolean oldLocationXESet = locationXESet;
		locationX = LOCATION_X_EDEFAULT;
		locationXESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__LOCATION_X, oldLocationX, LOCATION_X_EDEFAULT, oldLocationXESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLocationX() {
		return locationXESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLocationY() {
		return locationY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocationY(int newLocationY) {
		int oldLocationY = locationY;
		locationY = newLocationY;
		boolean oldLocationYESet = locationYESet;
		locationYESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__LOCATION_Y, oldLocationY, locationY, !oldLocationYESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLocationY() {
		int oldLocationY = locationY;
		boolean oldLocationYESet = locationYESet;
		locationY = LOCATION_Y_EDEFAULT;
		locationYESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__LOCATION_Y, oldLocationY, LOCATION_Y_EDEFAULT, oldLocationYESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLocationY() {
		return locationYESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMinimizeWhenClose() {
		return minimizeWhenClose;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinimizeWhenClose(boolean newMinimizeWhenClose) {
		boolean oldMinimizeWhenClose = minimizeWhenClose;
		minimizeWhenClose = newMinimizeWhenClose;
		boolean oldMinimizeWhenCloseESet = minimizeWhenCloseESet;
		minimizeWhenCloseESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__MINIMIZE_WHEN_CLOSE, oldMinimizeWhenClose, minimizeWhenClose, !oldMinimizeWhenCloseESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMinimizeWhenClose() {
		boolean oldMinimizeWhenClose = minimizeWhenClose;
		boolean oldMinimizeWhenCloseESet = minimizeWhenCloseESet;
		minimizeWhenClose = MINIMIZE_WHEN_CLOSE_EDEFAULT;
		minimizeWhenCloseESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__MINIMIZE_WHEN_CLOSE, oldMinimizeWhenClose, MINIMIZE_WHEN_CLOSE_EDEFAULT, oldMinimizeWhenCloseESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMinimizeWhenClose() {
		return minimizeWhenCloseESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOnlineTipLocationX() {
		return onlineTipLocationX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnlineTipLocationX(int newOnlineTipLocationX) {
		int oldOnlineTipLocationX = onlineTipLocationX;
		onlineTipLocationX = newOnlineTipLocationX;
		boolean oldOnlineTipLocationXESet = onlineTipLocationXESet;
		onlineTipLocationXESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_X, oldOnlineTipLocationX, onlineTipLocationX, !oldOnlineTipLocationXESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOnlineTipLocationX() {
		int oldOnlineTipLocationX = onlineTipLocationX;
		boolean oldOnlineTipLocationXESet = onlineTipLocationXESet;
		onlineTipLocationX = ONLINE_TIP_LOCATION_X_EDEFAULT;
		onlineTipLocationXESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_X, oldOnlineTipLocationX, ONLINE_TIP_LOCATION_X_EDEFAULT, oldOnlineTipLocationXESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOnlineTipLocationX() {
		return onlineTipLocationXESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOnlineTipLocationY() {
		return onlineTipLocationY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnlineTipLocationY(int newOnlineTipLocationY) {
		int oldOnlineTipLocationY = onlineTipLocationY;
		onlineTipLocationY = newOnlineTipLocationY;
		boolean oldOnlineTipLocationYESet = onlineTipLocationYESet;
		onlineTipLocationYESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_Y, oldOnlineTipLocationY, onlineTipLocationY, !oldOnlineTipLocationYESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOnlineTipLocationY() {
		int oldOnlineTipLocationY = onlineTipLocationY;
		boolean oldOnlineTipLocationYESet = onlineTipLocationYESet;
		onlineTipLocationY = ONLINE_TIP_LOCATION_Y_EDEFAULT;
		onlineTipLocationYESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_Y, oldOnlineTipLocationY, ONLINE_TIP_LOCATION_Y_EDEFAULT, oldOnlineTipLocationYESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOnlineTipLocationY() {
		return onlineTipLocationYESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOnTop() {
		return onTop;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnTop(boolean newOnTop) {
		boolean oldOnTop = onTop;
		onTop = newOnTop;
		boolean oldOnTopESet = onTopESet;
		onTopESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__ON_TOP, oldOnTop, onTop, !oldOnTopESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOnTop() {
		boolean oldOnTop = onTop;
		boolean oldOnTopESet = onTopESet;
		onTop = ON_TOP_EDEFAULT;
		onTopESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__ON_TOP, oldOnTop, ON_TOP_EDEFAULT, oldOnTopESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOnTop() {
		return onTopESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowBlacklist() {
		return showBlacklist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowBlacklist(boolean newShowBlacklist) {
		boolean oldShowBlacklist = showBlacklist;
		showBlacklist = newShowBlacklist;
		boolean oldShowBlacklistESet = showBlacklistESet;
		showBlacklistESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__SHOW_BLACKLIST, oldShowBlacklist, showBlacklist, !oldShowBlacklistESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowBlacklist() {
		boolean oldShowBlacklist = showBlacklist;
		boolean oldShowBlacklistESet = showBlacklistESet;
		showBlacklist = SHOW_BLACKLIST_EDEFAULT;
		showBlacklistESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__SHOW_BLACKLIST, oldShowBlacklist, SHOW_BLACKLIST_EDEFAULT, oldShowBlacklistESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowBlacklist() {
		return showBlacklistESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowFriendTip() {
		return showFriendTip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowFriendTip(boolean newShowFriendTip) {
		boolean oldShowFriendTip = showFriendTip;
		showFriendTip = newShowFriendTip;
		boolean oldShowFriendTipESet = showFriendTipESet;
		showFriendTipESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__SHOW_FRIEND_TIP, oldShowFriendTip, showFriendTip, !oldShowFriendTipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowFriendTip() {
		boolean oldShowFriendTip = showFriendTip;
		boolean oldShowFriendTipESet = showFriendTipESet;
		showFriendTip = SHOW_FRIEND_TIP_EDEFAULT;
		showFriendTipESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__SHOW_FRIEND_TIP, oldShowFriendTip, SHOW_FRIEND_TIP_EDEFAULT, oldShowFriendTipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowFriendTip() {
		return showFriendTipESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowNick() {
		return showNick;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowNick(boolean newShowNick) {
		boolean oldShowNick = showNick;
		showNick = newShowNick;
		boolean oldShowNickESet = showNickESet;
		showNickESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__SHOW_NICK, oldShowNick, showNick, !oldShowNickESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowNick() {
		boolean oldShowNick = showNick;
		boolean oldShowNickESet = showNickESet;
		showNick = SHOW_NICK_EDEFAULT;
		showNickESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__SHOW_NICK, oldShowNick, SHOW_NICK_EDEFAULT, oldShowNickESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowNick() {
		return showNickESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowOnlineOnly() {
		return showOnlineOnly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowOnlineOnly(boolean newShowOnlineOnly) {
		boolean oldShowOnlineOnly = showOnlineOnly;
		showOnlineOnly = newShowOnlineOnly;
		boolean oldShowOnlineOnlyESet = showOnlineOnlyESet;
		showOnlineOnlyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__SHOW_ONLINE_ONLY, oldShowOnlineOnly, showOnlineOnly, !oldShowOnlineOnlyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowOnlineOnly() {
		boolean oldShowOnlineOnly = showOnlineOnly;
		boolean oldShowOnlineOnlyESet = showOnlineOnlyESet;
		showOnlineOnly = SHOW_ONLINE_ONLY_EDEFAULT;
		showOnlineOnlyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__SHOW_ONLINE_ONLY, oldShowOnlineOnly, SHOW_ONLINE_ONLY_EDEFAULT, oldShowOnlineOnlyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowOnlineOnly() {
		return showOnlineOnlyESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowOnlineTip() {
		return showOnlineTip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowOnlineTip(boolean newShowOnlineTip) {
		boolean oldShowOnlineTip = showOnlineTip;
		showOnlineTip = newShowOnlineTip;
		boolean oldShowOnlineTipESet = showOnlineTipESet;
		showOnlineTipESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__SHOW_ONLINE_TIP, oldShowOnlineTip, showOnlineTip, !oldShowOnlineTipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowOnlineTip() {
		boolean oldShowOnlineTip = showOnlineTip;
		boolean oldShowOnlineTipESet = showOnlineTipESet;
		showOnlineTip = SHOW_ONLINE_TIP_EDEFAULT;
		showOnlineTipESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__SHOW_ONLINE_TIP, oldShowOnlineTip, SHOW_ONLINE_TIP_EDEFAULT, oldShowOnlineTipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowOnlineTip() {
		return showOnlineTipESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowLastLoginTip() {
		return showLastLoginTip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowLastLoginTip(boolean newShowLastLoginTip) {
		boolean oldShowLastLoginTip = showLastLoginTip;
		showLastLoginTip = newShowLastLoginTip;
		boolean oldShowLastLoginTipESet = showLastLoginTipESet;
		showLastLoginTipESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__SHOW_LAST_LOGIN_TIP, oldShowLastLoginTip, showLastLoginTip, !oldShowLastLoginTipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowLastLoginTip() {
		boolean oldShowLastLoginTip = showLastLoginTip;
		boolean oldShowLastLoginTipESet = showLastLoginTipESet;
		showLastLoginTip = SHOW_LAST_LOGIN_TIP_EDEFAULT;
		showLastLoginTipESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__SHOW_LAST_LOGIN_TIP, oldShowLastLoginTip, SHOW_LAST_LOGIN_TIP_EDEFAULT, oldShowLastLoginTipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowLastLoginTip() {
		return showLastLoginTipESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowSignature() {
		return showSignature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowSignature(boolean newShowSignature) {
		boolean oldShowSignature = showSignature;
		showSignature = newShowSignature;
		boolean oldShowSignatureESet = showSignatureESet;
		showSignatureESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__SHOW_SIGNATURE, oldShowSignature, showSignature, !oldShowSignatureESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowSignature() {
		boolean oldShowSignature = showSignature;
		boolean oldShowSignatureESet = showSignatureESet;
		showSignature = SHOW_SIGNATURE_EDEFAULT;
		showSignatureESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__SHOW_SIGNATURE, oldShowSignature, SHOW_SIGNATURE_EDEFAULT, oldShowSignatureESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowSignature() {
		return showSignatureESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowCustomHead() {
		return showCustomHead;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowCustomHead(boolean newShowCustomHead) {
		boolean oldShowCustomHead = showCustomHead;
		showCustomHead = newShowCustomHead;
		boolean oldShowCustomHeadESet = showCustomHeadESet;
		showCustomHeadESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__SHOW_CUSTOM_HEAD, oldShowCustomHead, showCustomHead, !oldShowCustomHeadESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowCustomHead() {
		boolean oldShowCustomHead = showCustomHead;
		boolean oldShowCustomHeadESet = showCustomHeadESet;
		showCustomHead = SHOW_CUSTOM_HEAD_EDEFAULT;
		showCustomHeadESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__SHOW_CUSTOM_HEAD, oldShowCustomHead, SHOW_CUSTOM_HEAD_EDEFAULT, oldShowCustomHeadESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowCustomHead() {
		return showCustomHeadESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowSmallHead() {
		return showSmallHead;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowSmallHead(boolean newShowSmallHead) {
		boolean oldShowSmallHead = showSmallHead;
		showSmallHead = newShowSmallHead;
		boolean oldShowSmallHeadESet = showSmallHeadESet;
		showSmallHeadESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__SHOW_SMALL_HEAD, oldShowSmallHead, showSmallHead, !oldShowSmallHeadESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowSmallHead() {
		boolean oldShowSmallHead = showSmallHead;
		boolean oldShowSmallHeadESet = showSmallHeadESet;
		showSmallHead = SHOW_SMALL_HEAD_EDEFAULT;
		showSmallHeadESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__SHOW_SMALL_HEAD, oldShowSmallHead, SHOW_SMALL_HEAD_EDEFAULT, oldShowSmallHeadESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowSmallHead() {
		return showSmallHeadESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTreeMode() {
		return treeMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTreeMode(boolean newTreeMode) {
		boolean oldTreeMode = treeMode;
		treeMode = newTreeMode;
		boolean oldTreeModeESet = treeModeESet;
		treeModeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__TREE_MODE, oldTreeMode, treeMode, !oldTreeModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTreeMode() {
		boolean oldTreeMode = treeMode;
		boolean oldTreeModeESet = treeModeESet;
		treeMode = TREE_MODE_EDEFAULT;
		treeModeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__TREE_MODE, oldTreeMode, TREE_MODE_EDEFAULT, oldTreeModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTreeMode() {
		return treeModeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseTabIMWindow() {
		return useTabIMWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseTabIMWindow(boolean newUseTabIMWindow) {
		boolean oldUseTabIMWindow = useTabIMWindow;
		useTabIMWindow = newUseTabIMWindow;
		boolean oldUseTabIMWindowESet = useTabIMWindowESet;
		useTabIMWindowESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__USE_TAB_IM_WINDOW, oldUseTabIMWindow, useTabIMWindow, !oldUseTabIMWindowESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetUseTabIMWindow() {
		boolean oldUseTabIMWindow = useTabIMWindow;
		boolean oldUseTabIMWindowESet = useTabIMWindowESet;
		useTabIMWindow = USE_TAB_IM_WINDOW_EDEFAULT;
		useTabIMWindowESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__USE_TAB_IM_WINDOW, oldUseTabIMWindow, USE_TAB_IM_WINDOW_EDEFAULT, oldUseTabIMWindowESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetUseTabIMWindow() {
		return useTabIMWindowESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBarExpanded() {
		return barExpanded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBarExpanded(boolean newBarExpanded) {
		boolean oldBarExpanded = barExpanded;
		barExpanded = newBarExpanded;
		boolean oldBarExpandedESet = barExpandedESet;
		barExpandedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__BAR_EXPANDED, oldBarExpanded, barExpanded, !oldBarExpandedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetBarExpanded() {
		boolean oldBarExpanded = barExpanded;
		boolean oldBarExpandedESet = barExpandedESet;
		barExpanded = BAR_EXPANDED_EDEFAULT;
		barExpandedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__BAR_EXPANDED, oldBarExpanded, BAR_EXPANDED_EDEFAULT, oldBarExpandedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetBarExpanded() {
		return barExpandedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		boolean oldWidthESet = widthESet;
		widthESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.GUI_OPTION__WIDTH, oldWidth, width, !oldWidthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetWidth() {
		int oldWidth = width;
		boolean oldWidthESet = widthESet;
		width = WIDTH_EDEFAULT;
		widthESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.GUI_OPTION__WIDTH, oldWidth, WIDTH_EDEFAULT, oldWidthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetWidth() {
		return widthESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OptionPackage.GUI_OPTION__AUTO_DOCK:
				return isAutoDock() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__BOLD:
				return isBold() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__FONT_COLOR:
				return new Integer(getFontColor());
			case OptionPackage.GUI_OPTION__FONT_NAME:
				return getFontName();
			case OptionPackage.GUI_OPTION__FONT_SIZE:
				return new Integer(getFontSize());
			case OptionPackage.GUI_OPTION__GROUP_BACKGROUND:
				return new Integer(getGroupBackground());
			case OptionPackage.GUI_OPTION__HEIGHT:
				return new Integer(getHeight());
			case OptionPackage.GUI_OPTION__HIDE_WHEN_MINIMIZE:
				return isHideWhenMinimize() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__IM_ON_TOP:
				return isImOnTop() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__ITALIC:
				return isItalic() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__LOCATION_X:
				return new Integer(getLocationX());
			case OptionPackage.GUI_OPTION__LOCATION_Y:
				return new Integer(getLocationY());
			case OptionPackage.GUI_OPTION__MINIMIZE_WHEN_CLOSE:
				return isMinimizeWhenClose() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_X:
				return new Integer(getOnlineTipLocationX());
			case OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_Y:
				return new Integer(getOnlineTipLocationY());
			case OptionPackage.GUI_OPTION__ON_TOP:
				return isOnTop() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__SHOW_BLACKLIST:
				return isShowBlacklist() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__SHOW_FRIEND_TIP:
				return isShowFriendTip() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__SHOW_NICK:
				return isShowNick() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__SHOW_ONLINE_ONLY:
				return isShowOnlineOnly() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__SHOW_ONLINE_TIP:
				return isShowOnlineTip() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__SHOW_LAST_LOGIN_TIP:
				return isShowLastLoginTip() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__SHOW_SIGNATURE:
				return isShowSignature() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__SHOW_CUSTOM_HEAD:
				return isShowCustomHead() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__SHOW_SMALL_HEAD:
				return isShowSmallHead() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__TREE_MODE:
				return isTreeMode() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__USE_TAB_IM_WINDOW:
				return isUseTabIMWindow() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__BAR_EXPANDED:
				return isBarExpanded() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.GUI_OPTION__WIDTH:
				return new Integer(getWidth());
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
			case OptionPackage.GUI_OPTION__AUTO_DOCK:
				setAutoDock(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__BOLD:
				setBold(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__FONT_COLOR:
				setFontColor(((Integer)newValue).intValue());
				return;
			case OptionPackage.GUI_OPTION__FONT_NAME:
				setFontName((String)newValue);
				return;
			case OptionPackage.GUI_OPTION__FONT_SIZE:
				setFontSize(((Integer)newValue).intValue());
				return;
			case OptionPackage.GUI_OPTION__GROUP_BACKGROUND:
				setGroupBackground(((Integer)newValue).intValue());
				return;
			case OptionPackage.GUI_OPTION__HEIGHT:
				setHeight(((Integer)newValue).intValue());
				return;
			case OptionPackage.GUI_OPTION__HIDE_WHEN_MINIMIZE:
				setHideWhenMinimize(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__IM_ON_TOP:
				setImOnTop(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__ITALIC:
				setItalic(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__LOCATION_X:
				setLocationX(((Integer)newValue).intValue());
				return;
			case OptionPackage.GUI_OPTION__LOCATION_Y:
				setLocationY(((Integer)newValue).intValue());
				return;
			case OptionPackage.GUI_OPTION__MINIMIZE_WHEN_CLOSE:
				setMinimizeWhenClose(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_X:
				setOnlineTipLocationX(((Integer)newValue).intValue());
				return;
			case OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_Y:
				setOnlineTipLocationY(((Integer)newValue).intValue());
				return;
			case OptionPackage.GUI_OPTION__ON_TOP:
				setOnTop(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__SHOW_BLACKLIST:
				setShowBlacklist(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__SHOW_FRIEND_TIP:
				setShowFriendTip(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__SHOW_NICK:
				setShowNick(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__SHOW_ONLINE_ONLY:
				setShowOnlineOnly(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__SHOW_ONLINE_TIP:
				setShowOnlineTip(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__SHOW_LAST_LOGIN_TIP:
				setShowLastLoginTip(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__SHOW_SIGNATURE:
				setShowSignature(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__SHOW_CUSTOM_HEAD:
				setShowCustomHead(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__SHOW_SMALL_HEAD:
				setShowSmallHead(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__TREE_MODE:
				setTreeMode(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__USE_TAB_IM_WINDOW:
				setUseTabIMWindow(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__BAR_EXPANDED:
				setBarExpanded(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.GUI_OPTION__WIDTH:
				setWidth(((Integer)newValue).intValue());
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
			case OptionPackage.GUI_OPTION__AUTO_DOCK:
				unsetAutoDock();
				return;
			case OptionPackage.GUI_OPTION__BOLD:
				unsetBold();
				return;
			case OptionPackage.GUI_OPTION__FONT_COLOR:
				unsetFontColor();
				return;
			case OptionPackage.GUI_OPTION__FONT_NAME:
				setFontName(FONT_NAME_EDEFAULT);
				return;
			case OptionPackage.GUI_OPTION__FONT_SIZE:
				unsetFontSize();
				return;
			case OptionPackage.GUI_OPTION__GROUP_BACKGROUND:
				unsetGroupBackground();
				return;
			case OptionPackage.GUI_OPTION__HEIGHT:
				unsetHeight();
				return;
			case OptionPackage.GUI_OPTION__HIDE_WHEN_MINIMIZE:
				unsetHideWhenMinimize();
				return;
			case OptionPackage.GUI_OPTION__IM_ON_TOP:
				unsetImOnTop();
				return;
			case OptionPackage.GUI_OPTION__ITALIC:
				unsetItalic();
				return;
			case OptionPackage.GUI_OPTION__LOCATION_X:
				unsetLocationX();
				return;
			case OptionPackage.GUI_OPTION__LOCATION_Y:
				unsetLocationY();
				return;
			case OptionPackage.GUI_OPTION__MINIMIZE_WHEN_CLOSE:
				unsetMinimizeWhenClose();
				return;
			case OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_X:
				unsetOnlineTipLocationX();
				return;
			case OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_Y:
				unsetOnlineTipLocationY();
				return;
			case OptionPackage.GUI_OPTION__ON_TOP:
				unsetOnTop();
				return;
			case OptionPackage.GUI_OPTION__SHOW_BLACKLIST:
				unsetShowBlacklist();
				return;
			case OptionPackage.GUI_OPTION__SHOW_FRIEND_TIP:
				unsetShowFriendTip();
				return;
			case OptionPackage.GUI_OPTION__SHOW_NICK:
				unsetShowNick();
				return;
			case OptionPackage.GUI_OPTION__SHOW_ONLINE_ONLY:
				unsetShowOnlineOnly();
				return;
			case OptionPackage.GUI_OPTION__SHOW_ONLINE_TIP:
				unsetShowOnlineTip();
				return;
			case OptionPackage.GUI_OPTION__SHOW_LAST_LOGIN_TIP:
				unsetShowLastLoginTip();
				return;
			case OptionPackage.GUI_OPTION__SHOW_SIGNATURE:
				unsetShowSignature();
				return;
			case OptionPackage.GUI_OPTION__SHOW_CUSTOM_HEAD:
				unsetShowCustomHead();
				return;
			case OptionPackage.GUI_OPTION__SHOW_SMALL_HEAD:
				unsetShowSmallHead();
				return;
			case OptionPackage.GUI_OPTION__TREE_MODE:
				unsetTreeMode();
				return;
			case OptionPackage.GUI_OPTION__USE_TAB_IM_WINDOW:
				unsetUseTabIMWindow();
				return;
			case OptionPackage.GUI_OPTION__BAR_EXPANDED:
				unsetBarExpanded();
				return;
			case OptionPackage.GUI_OPTION__WIDTH:
				unsetWidth();
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
			case OptionPackage.GUI_OPTION__AUTO_DOCK:
				return isSetAutoDock();
			case OptionPackage.GUI_OPTION__BOLD:
				return isSetBold();
			case OptionPackage.GUI_OPTION__FONT_COLOR:
				return isSetFontColor();
			case OptionPackage.GUI_OPTION__FONT_NAME:
				return FONT_NAME_EDEFAULT == null ? fontName != null : !FONT_NAME_EDEFAULT.equals(fontName);
			case OptionPackage.GUI_OPTION__FONT_SIZE:
				return isSetFontSize();
			case OptionPackage.GUI_OPTION__GROUP_BACKGROUND:
				return isSetGroupBackground();
			case OptionPackage.GUI_OPTION__HEIGHT:
				return isSetHeight();
			case OptionPackage.GUI_OPTION__HIDE_WHEN_MINIMIZE:
				return isSetHideWhenMinimize();
			case OptionPackage.GUI_OPTION__IM_ON_TOP:
				return isSetImOnTop();
			case OptionPackage.GUI_OPTION__ITALIC:
				return isSetItalic();
			case OptionPackage.GUI_OPTION__LOCATION_X:
				return isSetLocationX();
			case OptionPackage.GUI_OPTION__LOCATION_Y:
				return isSetLocationY();
			case OptionPackage.GUI_OPTION__MINIMIZE_WHEN_CLOSE:
				return isSetMinimizeWhenClose();
			case OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_X:
				return isSetOnlineTipLocationX();
			case OptionPackage.GUI_OPTION__ONLINE_TIP_LOCATION_Y:
				return isSetOnlineTipLocationY();
			case OptionPackage.GUI_OPTION__ON_TOP:
				return isSetOnTop();
			case OptionPackage.GUI_OPTION__SHOW_BLACKLIST:
				return isSetShowBlacklist();
			case OptionPackage.GUI_OPTION__SHOW_FRIEND_TIP:
				return isSetShowFriendTip();
			case OptionPackage.GUI_OPTION__SHOW_NICK:
				return isSetShowNick();
			case OptionPackage.GUI_OPTION__SHOW_ONLINE_ONLY:
				return isSetShowOnlineOnly();
			case OptionPackage.GUI_OPTION__SHOW_ONLINE_TIP:
				return isSetShowOnlineTip();
			case OptionPackage.GUI_OPTION__SHOW_LAST_LOGIN_TIP:
				return isSetShowLastLoginTip();
			case OptionPackage.GUI_OPTION__SHOW_SIGNATURE:
				return isSetShowSignature();
			case OptionPackage.GUI_OPTION__SHOW_CUSTOM_HEAD:
				return isSetShowCustomHead();
			case OptionPackage.GUI_OPTION__SHOW_SMALL_HEAD:
				return isSetShowSmallHead();
			case OptionPackage.GUI_OPTION__TREE_MODE:
				return isSetTreeMode();
			case OptionPackage.GUI_OPTION__USE_TAB_IM_WINDOW:
				return isSetUseTabIMWindow();
			case OptionPackage.GUI_OPTION__BAR_EXPANDED:
				return isSetBarExpanded();
			case OptionPackage.GUI_OPTION__WIDTH:
				return isSetWidth();
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
		result.append(" (autoDock: ");
		if (autoDockESet) result.append(autoDock); else result.append("<unset>");
		result.append(", bold: ");
		if (boldESet) result.append(bold); else result.append("<unset>");
		result.append(", fontColor: ");
		if (fontColorESet) result.append(fontColor); else result.append("<unset>");
		result.append(", fontName: ");
		result.append(fontName);
		result.append(", fontSize: ");
		if (fontSizeESet) result.append(fontSize); else result.append("<unset>");
		result.append(", groupBackground: ");
		if (groupBackgroundESet) result.append(groupBackground); else result.append("<unset>");
		result.append(", height: ");
		if (heightESet) result.append(height); else result.append("<unset>");
		result.append(", hideWhenMinimize: ");
		if (hideWhenMinimizeESet) result.append(hideWhenMinimize); else result.append("<unset>");
		result.append(", imOnTop: ");
		if (imOnTopESet) result.append(imOnTop); else result.append("<unset>");
		result.append(", italic: ");
		if (italicESet) result.append(italic); else result.append("<unset>");
		result.append(", locationX: ");
		if (locationXESet) result.append(locationX); else result.append("<unset>");
		result.append(", locationY: ");
		if (locationYESet) result.append(locationY); else result.append("<unset>");
		result.append(", minimizeWhenClose: ");
		if (minimizeWhenCloseESet) result.append(minimizeWhenClose); else result.append("<unset>");
		result.append(", onlineTipLocationX: ");
		if (onlineTipLocationXESet) result.append(onlineTipLocationX); else result.append("<unset>");
		result.append(", onlineTipLocationY: ");
		if (onlineTipLocationYESet) result.append(onlineTipLocationY); else result.append("<unset>");
		result.append(", onTop: ");
		if (onTopESet) result.append(onTop); else result.append("<unset>");
		result.append(", showBlacklist: ");
		if (showBlacklistESet) result.append(showBlacklist); else result.append("<unset>");
		result.append(", showFriendTip: ");
		if (showFriendTipESet) result.append(showFriendTip); else result.append("<unset>");
		result.append(", showNick: ");
		if (showNickESet) result.append(showNick); else result.append("<unset>");
		result.append(", showOnlineOnly: ");
		if (showOnlineOnlyESet) result.append(showOnlineOnly); else result.append("<unset>");
		result.append(", showOnlineTip: ");
		if (showOnlineTipESet) result.append(showOnlineTip); else result.append("<unset>");
		result.append(", showLastLoginTip: ");
		if (showLastLoginTipESet) result.append(showLastLoginTip); else result.append("<unset>");
		result.append(", showSignature: ");
		if (showSignatureESet) result.append(showSignature); else result.append("<unset>");
		result.append(", showCustomHead: ");
		if (showCustomHeadESet) result.append(showCustomHead); else result.append("<unset>");
		result.append(", showSmallHead: ");
		if (showSmallHeadESet) result.append(showSmallHead); else result.append("<unset>");
		result.append(", treeMode: ");
		if (treeModeESet) result.append(treeMode); else result.append("<unset>");
		result.append(", useTabIMWindow: ");
		if (useTabIMWindowESet) result.append(useTabIMWindow); else result.append("<unset>");
		result.append(", barExpanded: ");
		if (barExpandedESet) result.append(barExpanded); else result.append("<unset>");
		result.append(", width: ");
		if (widthESet) result.append(width); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //GUIOptionImpl
