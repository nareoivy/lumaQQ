/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see edu.tsinghua.lumaqq.ecore.option.OptionFactory
 * @model kind="package"
 * @generated
 */
public interface OptionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "option";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore/option";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "option";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OptionPackage eINSTANCE = edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl <em>GUI Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getGUIOption()
	 * @generated
	 */
	int GUI_OPTION = 0;

	/**
	 * The feature id for the '<em><b>Auto Dock</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__AUTO_DOCK = 0;

	/**
	 * The feature id for the '<em><b>Bold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__BOLD = 1;

	/**
	 * The feature id for the '<em><b>Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__FONT_COLOR = 2;

	/**
	 * The feature id for the '<em><b>Font Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__FONT_NAME = 3;

	/**
	 * The feature id for the '<em><b>Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__FONT_SIZE = 4;

	/**
	 * The feature id for the '<em><b>Group Background</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__GROUP_BACKGROUND = 5;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__HEIGHT = 6;

	/**
	 * The feature id for the '<em><b>Hide When Minimize</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__HIDE_WHEN_MINIMIZE = 7;

	/**
	 * The feature id for the '<em><b>Im On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__IM_ON_TOP = 8;

	/**
	 * The feature id for the '<em><b>Italic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__ITALIC = 9;

	/**
	 * The feature id for the '<em><b>Location X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__LOCATION_X = 10;

	/**
	 * The feature id for the '<em><b>Location Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__LOCATION_Y = 11;

	/**
	 * The feature id for the '<em><b>Minimize When Close</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__MINIMIZE_WHEN_CLOSE = 12;

	/**
	 * The feature id for the '<em><b>Online Tip Location X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__ONLINE_TIP_LOCATION_X = 13;

	/**
	 * The feature id for the '<em><b>Online Tip Location Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__ONLINE_TIP_LOCATION_Y = 14;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__ON_TOP = 15;

	/**
	 * The feature id for the '<em><b>Show Blacklist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__SHOW_BLACKLIST = 16;

	/**
	 * The feature id for the '<em><b>Show Friend Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__SHOW_FRIEND_TIP = 17;

	/**
	 * The feature id for the '<em><b>Show Nick</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__SHOW_NICK = 18;

	/**
	 * The feature id for the '<em><b>Show Online Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__SHOW_ONLINE_ONLY = 19;

	/**
	 * The feature id for the '<em><b>Show Online Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__SHOW_ONLINE_TIP = 20;

	/**
	 * The feature id for the '<em><b>Show Last Login Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__SHOW_LAST_LOGIN_TIP = 21;

	/**
	 * The feature id for the '<em><b>Show Signature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__SHOW_SIGNATURE = 22;

	/**
	 * The feature id for the '<em><b>Show Custom Head</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__SHOW_CUSTOM_HEAD = 23;

	/**
	 * The feature id for the '<em><b>Show Small Head</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__SHOW_SMALL_HEAD = 24;

	/**
	 * The feature id for the '<em><b>Tree Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__TREE_MODE = 25;

	/**
	 * The feature id for the '<em><b>Use Tab IM Window</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__USE_TAB_IM_WINDOW = 26;

	/**
	 * The feature id for the '<em><b>Bar Expanded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__BAR_EXPANDED = 27;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION__WIDTH = 28;

	/**
	 * The number of structural features of the '<em>GUI Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUI_OPTION_FEATURE_COUNT = 29;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.KeyOptionImpl <em>Key Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.KeyOptionImpl
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getKeyOption()
	 * @generated
	 */
	int KEY_OPTION = 1;

	/**
	 * The feature id for the '<em><b>Message Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEY_OPTION__MESSAGE_KEY = 0;

	/**
	 * The number of structural features of the '<em>Key Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEY_OPTION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.MessageOptionImpl <em>Message Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.MessageOptionImpl
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getMessageOption()
	 * @generated
	 */
	int MESSAGE_OPTION = 2;

	/**
	 * The feature id for the '<em><b>Auto Eject</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_OPTION__AUTO_EJECT = 0;

	/**
	 * The feature id for the '<em><b>Enable Sound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_OPTION__ENABLE_SOUND = 1;

	/**
	 * The feature id for the '<em><b>Reject Stranger</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_OPTION__REJECT_STRANGER = 2;

	/**
	 * The feature id for the '<em><b>Reject Temp Session IM</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_OPTION__REJECT_TEMP_SESSION_IM = 3;

	/**
	 * The feature id for the '<em><b>Use Enter In Message Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_OPTION__USE_ENTER_IN_MESSAGE_MODE = 4;

	/**
	 * The feature id for the '<em><b>Use Enter In Talk Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_OPTION__USE_ENTER_IN_TALK_MODE = 5;

	/**
	 * The number of structural features of the '<em>Message Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_OPTION_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.OptionsImpl <em>Options</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionsImpl
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getOptions()
	 * @generated
	 */
	int OPTIONS = 3;

	/**
	 * The feature id for the '<em><b>Login Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPTIONS__LOGIN_OPTION = 0;

	/**
	 * The feature id for the '<em><b>Gui Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPTIONS__GUI_OPTION = 1;

	/**
	 * The feature id for the '<em><b>Message Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPTIONS__MESSAGE_OPTION = 2;

	/**
	 * The feature id for the '<em><b>Other Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPTIONS__OTHER_OPTION = 3;

	/**
	 * The feature id for the '<em><b>Sync Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPTIONS__SYNC_OPTION = 4;

	/**
	 * The feature id for the '<em><b>Key Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPTIONS__KEY_OPTION = 5;

	/**
	 * The number of structural features of the '<em>Options</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPTIONS_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.OtherOptionImpl <em>Other Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.OtherOptionImpl
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getOtherOption()
	 * @generated
	 */
	int OTHER_OPTION = 4;

	/**
	 * The feature id for the '<em><b>Browser</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_OPTION__BROWSER = 0;

	/**
	 * The feature id for the '<em><b>Enable Latest</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_OPTION__ENABLE_LATEST = 1;

	/**
	 * The feature id for the '<em><b>Keep Stranger In Latest</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_OPTION__KEEP_STRANGER_IN_LATEST = 2;

	/**
	 * The feature id for the '<em><b>Latest Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_OPTION__LATEST_SIZE = 3;

	/**
	 * The feature id for the '<em><b>Show Fake Cam</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_OPTION__SHOW_FAKE_CAM = 4;

	/**
	 * The number of structural features of the '<em>Other Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_OPTION_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.SyncOptionImpl <em>Sync Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.SyncOptionImpl
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getSyncOption()
	 * @generated
	 */
	int SYNC_OPTION = 5;

	/**
	 * The feature id for the '<em><b>Auto Check Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNC_OPTION__AUTO_CHECK_UPDATE = 0;

	/**
	 * The feature id for the '<em><b>Auto Download Friend Remark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNC_OPTION__AUTO_DOWNLOAD_FRIEND_REMARK = 1;

	/**
	 * The feature id for the '<em><b>Auto Download Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNC_OPTION__AUTO_DOWNLOAD_GROUP = 2;

	/**
	 * The feature id for the '<em><b>Auto Upload Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNC_OPTION__AUTO_UPLOAD_GROUP = 3;

	/**
	 * The number of structural features of the '<em>Sync Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNC_OPTION_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.option.OpType <em>Op Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.option.OpType
	 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getOpType()
	 * @generated
	 */
	int OP_TYPE = 6;


	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption <em>GUI Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GUI Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption
	 * @generated
	 */
	EClass getGUIOption();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isAutoDock <em>Auto Dock</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Dock</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isAutoDock()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_AutoDock();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isBold <em>Bold</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bold</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isBold()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_Bold();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontColor <em>Font Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Color</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontColor()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_FontColor();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontName <em>Font Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Name</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontName()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_FontName();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontSize <em>Font Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Size</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontSize()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_FontSize();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getGroupBackground <em>Group Background</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Group Background</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#getGroupBackground()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_GroupBackground();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#getHeight()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_Height();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isHideWhenMinimize <em>Hide When Minimize</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hide When Minimize</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isHideWhenMinimize()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_HideWhenMinimize();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isImOnTop <em>Im On Top</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Im On Top</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isImOnTop()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_ImOnTop();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isItalic <em>Italic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Italic</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isItalic()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_Italic();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationX <em>Location X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location X</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationX()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_LocationX();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationY <em>Location Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location Y</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationY()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_LocationY();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isMinimizeWhenClose <em>Minimize When Close</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Minimize When Close</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isMinimizeWhenClose()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_MinimizeWhenClose();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationX <em>Online Tip Location X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Online Tip Location X</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationX()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_OnlineTipLocationX();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationY <em>Online Tip Location Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Online Tip Location Y</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationY()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_OnlineTipLocationY();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isOnTop <em>On Top</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>On Top</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isOnTop()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_OnTop();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowBlacklist <em>Show Blacklist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Blacklist</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowBlacklist()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_ShowBlacklist();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowFriendTip <em>Show Friend Tip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Friend Tip</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowFriendTip()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_ShowFriendTip();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowNick <em>Show Nick</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Nick</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowNick()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_ShowNick();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineOnly <em>Show Online Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Online Only</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineOnly()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_ShowOnlineOnly();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineTip <em>Show Online Tip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Online Tip</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineTip()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_ShowOnlineTip();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowLastLoginTip <em>Show Last Login Tip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Last Login Tip</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowLastLoginTip()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_ShowLastLoginTip();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSignature <em>Show Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Signature</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSignature()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_ShowSignature();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowCustomHead <em>Show Custom Head</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Custom Head</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowCustomHead()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_ShowCustomHead();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSmallHead <em>Show Small Head</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Small Head</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSmallHead()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_ShowSmallHead();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isTreeMode <em>Tree Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tree Mode</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isTreeMode()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_TreeMode();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isUseTabIMWindow <em>Use Tab IM Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Tab IM Window</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isUseTabIMWindow()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_UseTabIMWindow();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isBarExpanded <em>Bar Expanded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bar Expanded</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#isBarExpanded()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_BarExpanded();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.GUIOption#getWidth()
	 * @see #getGUIOption()
	 * @generated
	 */
	EAttribute getGUIOption_Width();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.option.KeyOption <em>Key Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Key Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.KeyOption
	 * @generated
	 */
	EClass getKeyOption();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.KeyOption#getMessageKey <em>Message Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message Key</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.KeyOption#getMessageKey()
	 * @see #getKeyOption()
	 * @generated
	 */
	EAttribute getKeyOption_MessageKey();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption <em>Message Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Message Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.MessageOption
	 * @generated
	 */
	EClass getMessageOption();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isAutoEject <em>Auto Eject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Eject</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.MessageOption#isAutoEject()
	 * @see #getMessageOption()
	 * @generated
	 */
	EAttribute getMessageOption_AutoEject();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isEnableSound <em>Enable Sound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enable Sound</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.MessageOption#isEnableSound()
	 * @see #getMessageOption()
	 * @generated
	 */
	EAttribute getMessageOption_EnableSound();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectStranger <em>Reject Stranger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reject Stranger</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectStranger()
	 * @see #getMessageOption()
	 * @generated
	 */
	EAttribute getMessageOption_RejectStranger();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectTempSessionIM <em>Reject Temp Session IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reject Temp Session IM</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.MessageOption#isRejectTempSessionIM()
	 * @see #getMessageOption()
	 * @generated
	 */
	EAttribute getMessageOption_RejectTempSessionIM();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInMessageMode <em>Use Enter In Message Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Enter In Message Mode</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInMessageMode()
	 * @see #getMessageOption()
	 * @generated
	 */
	EAttribute getMessageOption_UseEnterInMessageMode();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInTalkMode <em>Use Enter In Talk Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Enter In Talk Mode</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.MessageOption#isUseEnterInTalkMode()
	 * @see #getMessageOption()
	 * @generated
	 */
	EAttribute getMessageOption_UseEnterInTalkMode();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.option.Options <em>Options</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Options</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.Options
	 * @generated
	 */
	EClass getOptions();

	/**
	 * Returns the meta object for the containment reference '{@link edu.tsinghua.lumaqq.ecore.option.Options#getLoginOption <em>Login Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Login Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.Options#getLoginOption()
	 * @see #getOptions()
	 * @generated
	 */
	EReference getOptions_LoginOption();

	/**
	 * Returns the meta object for the containment reference '{@link edu.tsinghua.lumaqq.ecore.option.Options#getGuiOption <em>Gui Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Gui Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.Options#getGuiOption()
	 * @see #getOptions()
	 * @generated
	 */
	EReference getOptions_GuiOption();

	/**
	 * Returns the meta object for the containment reference '{@link edu.tsinghua.lumaqq.ecore.option.Options#getMessageOption <em>Message Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Message Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.Options#getMessageOption()
	 * @see #getOptions()
	 * @generated
	 */
	EReference getOptions_MessageOption();

	/**
	 * Returns the meta object for the containment reference '{@link edu.tsinghua.lumaqq.ecore.option.Options#getOtherOption <em>Other Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Other Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.Options#getOtherOption()
	 * @see #getOptions()
	 * @generated
	 */
	EReference getOptions_OtherOption();

	/**
	 * Returns the meta object for the containment reference '{@link edu.tsinghua.lumaqq.ecore.option.Options#getSyncOption <em>Sync Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sync Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.Options#getSyncOption()
	 * @see #getOptions()
	 * @generated
	 */
	EReference getOptions_SyncOption();

	/**
	 * Returns the meta object for the containment reference '{@link edu.tsinghua.lumaqq.ecore.option.Options#getKeyOption <em>Key Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Key Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.Options#getKeyOption()
	 * @see #getOptions()
	 * @generated
	 */
	EReference getOptions_KeyOption();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption <em>Other Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Other Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.OtherOption
	 * @generated
	 */
	EClass getOtherOption();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#getBrowser <em>Browser</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Browser</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.OtherOption#getBrowser()
	 * @see #getOtherOption()
	 * @generated
	 */
	EAttribute getOtherOption_Browser();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isEnableLatest <em>Enable Latest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enable Latest</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.OtherOption#isEnableLatest()
	 * @see #getOtherOption()
	 * @generated
	 */
	EAttribute getOtherOption_EnableLatest();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isKeepStrangerInLatest <em>Keep Stranger In Latest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Keep Stranger In Latest</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.OtherOption#isKeepStrangerInLatest()
	 * @see #getOtherOption()
	 * @generated
	 */
	EAttribute getOtherOption_KeepStrangerInLatest();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#getLatestSize <em>Latest Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Latest Size</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.OtherOption#getLatestSize()
	 * @see #getOtherOption()
	 * @generated
	 */
	EAttribute getOtherOption_LatestSize();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.OtherOption#isShowFakeCam <em>Show Fake Cam</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Fake Cam</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.OtherOption#isShowFakeCam()
	 * @see #getOtherOption()
	 * @generated
	 */
	EAttribute getOtherOption_ShowFakeCam();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption <em>Sync Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sync Option</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.SyncOption
	 * @generated
	 */
	EClass getSyncOption();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoCheckUpdate <em>Auto Check Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Check Update</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoCheckUpdate()
	 * @see #getSyncOption()
	 * @generated
	 */
	EAttribute getSyncOption_AutoCheckUpdate();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadFriendRemark <em>Auto Download Friend Remark</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Download Friend Remark</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadFriendRemark()
	 * @see #getSyncOption()
	 * @generated
	 */
	EAttribute getSyncOption_AutoDownloadFriendRemark();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadGroup <em>Auto Download Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Download Group</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadGroup()
	 * @see #getSyncOption()
	 * @generated
	 */
	EAttribute getSyncOption_AutoDownloadGroup();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#getAutoUploadGroup <em>Auto Upload Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Upload Group</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.SyncOption#getAutoUploadGroup()
	 * @see #getSyncOption()
	 * @generated
	 */
	EAttribute getSyncOption_AutoUploadGroup();

	/**
	 * Returns the meta object for enum '{@link edu.tsinghua.lumaqq.ecore.option.OpType <em>Op Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Op Type</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.option.OpType
	 * @generated
	 */
	EEnum getOpType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	OptionFactory getOptionFactory();

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
	interface Literals  {
		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl <em>GUI Option</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.GUIOptionImpl
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getGUIOption()
		 * @generated
		 */
		EClass GUI_OPTION = eINSTANCE.getGUIOption();

		/**
		 * The meta object literal for the '<em><b>Auto Dock</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__AUTO_DOCK = eINSTANCE.getGUIOption_AutoDock();

		/**
		 * The meta object literal for the '<em><b>Bold</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__BOLD = eINSTANCE.getGUIOption_Bold();

		/**
		 * The meta object literal for the '<em><b>Font Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__FONT_COLOR = eINSTANCE.getGUIOption_FontColor();

		/**
		 * The meta object literal for the '<em><b>Font Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__FONT_NAME = eINSTANCE.getGUIOption_FontName();

		/**
		 * The meta object literal for the '<em><b>Font Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__FONT_SIZE = eINSTANCE.getGUIOption_FontSize();

		/**
		 * The meta object literal for the '<em><b>Group Background</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__GROUP_BACKGROUND = eINSTANCE.getGUIOption_GroupBackground();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__HEIGHT = eINSTANCE.getGUIOption_Height();

		/**
		 * The meta object literal for the '<em><b>Hide When Minimize</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__HIDE_WHEN_MINIMIZE = eINSTANCE.getGUIOption_HideWhenMinimize();

		/**
		 * The meta object literal for the '<em><b>Im On Top</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__IM_ON_TOP = eINSTANCE.getGUIOption_ImOnTop();

		/**
		 * The meta object literal for the '<em><b>Italic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__ITALIC = eINSTANCE.getGUIOption_Italic();

		/**
		 * The meta object literal for the '<em><b>Location X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__LOCATION_X = eINSTANCE.getGUIOption_LocationX();

		/**
		 * The meta object literal for the '<em><b>Location Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__LOCATION_Y = eINSTANCE.getGUIOption_LocationY();

		/**
		 * The meta object literal for the '<em><b>Minimize When Close</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__MINIMIZE_WHEN_CLOSE = eINSTANCE.getGUIOption_MinimizeWhenClose();

		/**
		 * The meta object literal for the '<em><b>Online Tip Location X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__ONLINE_TIP_LOCATION_X = eINSTANCE.getGUIOption_OnlineTipLocationX();

		/**
		 * The meta object literal for the '<em><b>Online Tip Location Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__ONLINE_TIP_LOCATION_Y = eINSTANCE.getGUIOption_OnlineTipLocationY();

		/**
		 * The meta object literal for the '<em><b>On Top</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__ON_TOP = eINSTANCE.getGUIOption_OnTop();

		/**
		 * The meta object literal for the '<em><b>Show Blacklist</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__SHOW_BLACKLIST = eINSTANCE.getGUIOption_ShowBlacklist();

		/**
		 * The meta object literal for the '<em><b>Show Friend Tip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__SHOW_FRIEND_TIP = eINSTANCE.getGUIOption_ShowFriendTip();

		/**
		 * The meta object literal for the '<em><b>Show Nick</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__SHOW_NICK = eINSTANCE.getGUIOption_ShowNick();

		/**
		 * The meta object literal for the '<em><b>Show Online Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__SHOW_ONLINE_ONLY = eINSTANCE.getGUIOption_ShowOnlineOnly();

		/**
		 * The meta object literal for the '<em><b>Show Online Tip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__SHOW_ONLINE_TIP = eINSTANCE.getGUIOption_ShowOnlineTip();

		/**
		 * The meta object literal for the '<em><b>Show Last Login Tip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__SHOW_LAST_LOGIN_TIP = eINSTANCE.getGUIOption_ShowLastLoginTip();

		/**
		 * The meta object literal for the '<em><b>Show Signature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__SHOW_SIGNATURE = eINSTANCE.getGUIOption_ShowSignature();

		/**
		 * The meta object literal for the '<em><b>Show Custom Head</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__SHOW_CUSTOM_HEAD = eINSTANCE.getGUIOption_ShowCustomHead();

		/**
		 * The meta object literal for the '<em><b>Show Small Head</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__SHOW_SMALL_HEAD = eINSTANCE.getGUIOption_ShowSmallHead();

		/**
		 * The meta object literal for the '<em><b>Tree Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__TREE_MODE = eINSTANCE.getGUIOption_TreeMode();

		/**
		 * The meta object literal for the '<em><b>Use Tab IM Window</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__USE_TAB_IM_WINDOW = eINSTANCE.getGUIOption_UseTabIMWindow();

		/**
		 * The meta object literal for the '<em><b>Bar Expanded</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__BAR_EXPANDED = eINSTANCE.getGUIOption_BarExpanded();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUI_OPTION__WIDTH = eINSTANCE.getGUIOption_Width();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.KeyOptionImpl <em>Key Option</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.KeyOptionImpl
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getKeyOption()
		 * @generated
		 */
		EClass KEY_OPTION = eINSTANCE.getKeyOption();

		/**
		 * The meta object literal for the '<em><b>Message Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute KEY_OPTION__MESSAGE_KEY = eINSTANCE.getKeyOption_MessageKey();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.MessageOptionImpl <em>Message Option</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.MessageOptionImpl
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getMessageOption()
		 * @generated
		 */
		EClass MESSAGE_OPTION = eINSTANCE.getMessageOption();

		/**
		 * The meta object literal for the '<em><b>Auto Eject</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MESSAGE_OPTION__AUTO_EJECT = eINSTANCE.getMessageOption_AutoEject();

		/**
		 * The meta object literal for the '<em><b>Enable Sound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MESSAGE_OPTION__ENABLE_SOUND = eINSTANCE.getMessageOption_EnableSound();

		/**
		 * The meta object literal for the '<em><b>Reject Stranger</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MESSAGE_OPTION__REJECT_STRANGER = eINSTANCE.getMessageOption_RejectStranger();

		/**
		 * The meta object literal for the '<em><b>Reject Temp Session IM</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MESSAGE_OPTION__REJECT_TEMP_SESSION_IM = eINSTANCE.getMessageOption_RejectTempSessionIM();

		/**
		 * The meta object literal for the '<em><b>Use Enter In Message Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MESSAGE_OPTION__USE_ENTER_IN_MESSAGE_MODE = eINSTANCE.getMessageOption_UseEnterInMessageMode();

		/**
		 * The meta object literal for the '<em><b>Use Enter In Talk Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MESSAGE_OPTION__USE_ENTER_IN_TALK_MODE = eINSTANCE.getMessageOption_UseEnterInTalkMode();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.OptionsImpl <em>Options</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionsImpl
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getOptions()
		 * @generated
		 */
		EClass OPTIONS = eINSTANCE.getOptions();

		/**
		 * The meta object literal for the '<em><b>Login Option</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPTIONS__LOGIN_OPTION = eINSTANCE.getOptions_LoginOption();

		/**
		 * The meta object literal for the '<em><b>Gui Option</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPTIONS__GUI_OPTION = eINSTANCE.getOptions_GuiOption();

		/**
		 * The meta object literal for the '<em><b>Message Option</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPTIONS__MESSAGE_OPTION = eINSTANCE.getOptions_MessageOption();

		/**
		 * The meta object literal for the '<em><b>Other Option</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPTIONS__OTHER_OPTION = eINSTANCE.getOptions_OtherOption();

		/**
		 * The meta object literal for the '<em><b>Sync Option</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPTIONS__SYNC_OPTION = eINSTANCE.getOptions_SyncOption();

		/**
		 * The meta object literal for the '<em><b>Key Option</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPTIONS__KEY_OPTION = eINSTANCE.getOptions_KeyOption();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.OtherOptionImpl <em>Other Option</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.OtherOptionImpl
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getOtherOption()
		 * @generated
		 */
		EClass OTHER_OPTION = eINSTANCE.getOtherOption();

		/**
		 * The meta object literal for the '<em><b>Browser</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OTHER_OPTION__BROWSER = eINSTANCE.getOtherOption_Browser();

		/**
		 * The meta object literal for the '<em><b>Enable Latest</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OTHER_OPTION__ENABLE_LATEST = eINSTANCE.getOtherOption_EnableLatest();

		/**
		 * The meta object literal for the '<em><b>Keep Stranger In Latest</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OTHER_OPTION__KEEP_STRANGER_IN_LATEST = eINSTANCE.getOtherOption_KeepStrangerInLatest();

		/**
		 * The meta object literal for the '<em><b>Latest Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OTHER_OPTION__LATEST_SIZE = eINSTANCE.getOtherOption_LatestSize();

		/**
		 * The meta object literal for the '<em><b>Show Fake Cam</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OTHER_OPTION__SHOW_FAKE_CAM = eINSTANCE.getOtherOption_ShowFakeCam();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.option.impl.SyncOptionImpl <em>Sync Option</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.SyncOptionImpl
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getSyncOption()
		 * @generated
		 */
		EClass SYNC_OPTION = eINSTANCE.getSyncOption();

		/**
		 * The meta object literal for the '<em><b>Auto Check Update</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNC_OPTION__AUTO_CHECK_UPDATE = eINSTANCE.getSyncOption_AutoCheckUpdate();

		/**
		 * The meta object literal for the '<em><b>Auto Download Friend Remark</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNC_OPTION__AUTO_DOWNLOAD_FRIEND_REMARK = eINSTANCE.getSyncOption_AutoDownloadFriendRemark();

		/**
		 * The meta object literal for the '<em><b>Auto Download Group</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNC_OPTION__AUTO_DOWNLOAD_GROUP = eINSTANCE.getSyncOption_AutoDownloadGroup();

		/**
		 * The meta object literal for the '<em><b>Auto Upload Group</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNC_OPTION__AUTO_UPLOAD_GROUP = eINSTANCE.getSyncOption_AutoUploadGroup();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.option.OpType <em>Op Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.option.OpType
		 * @see edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl#getOpType()
		 * @generated
		 */
		EEnum OP_TYPE = eINSTANCE.getOpType();

	}

} //OptionPackage
