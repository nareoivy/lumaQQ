/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option.impl;

import edu.tsinghua.lumaqq.ecore.EcorePackage;

import edu.tsinghua.lumaqq.ecore.face.FacePackage;

import edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl;

import edu.tsinghua.lumaqq.ecore.global.GlobalPackage;

import edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl;

import edu.tsinghua.lumaqq.ecore.group.GroupPackage;

import edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl;

import edu.tsinghua.lumaqq.ecore.impl.EcorePackageImpl;

import edu.tsinghua.lumaqq.ecore.login.LoginPackage;

import edu.tsinghua.lumaqq.ecore.login.impl.LoginPackageImpl;

import edu.tsinghua.lumaqq.ecore.option.GUIOption;
import edu.tsinghua.lumaqq.ecore.option.KeyOption;
import edu.tsinghua.lumaqq.ecore.option.MessageOption;
import edu.tsinghua.lumaqq.ecore.option.OpType;
import edu.tsinghua.lumaqq.ecore.option.OptionFactory;
import edu.tsinghua.lumaqq.ecore.option.OptionPackage;
import edu.tsinghua.lumaqq.ecore.option.Options;
import edu.tsinghua.lumaqq.ecore.option.OtherOption;
import edu.tsinghua.lumaqq.ecore.option.SyncOption;

import edu.tsinghua.lumaqq.ecore.proxy.ProxyPackage;

import edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyPackageImpl;

import edu.tsinghua.lumaqq.ecore.remark.RemarkPackage;

import edu.tsinghua.lumaqq.ecore.remark.impl.RemarkPackageImpl;

import edu.tsinghua.lumaqq.ecore.reply.ReplyPackage;

import edu.tsinghua.lumaqq.ecore.reply.impl.ReplyPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OptionPackageImpl extends EPackageImpl implements OptionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass guiOptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass keyOptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass messageOptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass optionsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass otherOptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass syncOptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum opTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OptionPackageImpl() {
		super(eNS_URI, OptionFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static OptionPackage init() {
		if (isInited) return (OptionPackage)EPackage.Registry.INSTANCE.getEPackage(OptionPackage.eNS_URI);

		// Obtain or create and register package
		OptionPackageImpl theOptionPackage = (OptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof OptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new OptionPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EcorePackageImpl theEcorePackage = (EcorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) instanceof EcorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) : EcorePackage.eINSTANCE);
		GlobalPackageImpl theGlobalPackage = (GlobalPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GlobalPackage.eNS_URI) instanceof GlobalPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GlobalPackage.eNS_URI) : GlobalPackage.eINSTANCE);
		FacePackageImpl theFacePackage = (FacePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FacePackage.eNS_URI) instanceof FacePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FacePackage.eNS_URI) : FacePackage.eINSTANCE);
		GroupPackageImpl theGroupPackage = (GroupPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GroupPackage.eNS_URI) instanceof GroupPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GroupPackage.eNS_URI) : GroupPackage.eINSTANCE);
		LoginPackageImpl theLoginPackage = (LoginPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(LoginPackage.eNS_URI) instanceof LoginPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(LoginPackage.eNS_URI) : LoginPackage.eINSTANCE);
		ProxyPackageImpl theProxyPackage = (ProxyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProxyPackage.eNS_URI) instanceof ProxyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProxyPackage.eNS_URI) : ProxyPackage.eINSTANCE);
		RemarkPackageImpl theRemarkPackage = (RemarkPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI) instanceof RemarkPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI) : RemarkPackage.eINSTANCE);
		ReplyPackageImpl theReplyPackage = (ReplyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReplyPackage.eNS_URI) instanceof ReplyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReplyPackage.eNS_URI) : ReplyPackage.eINSTANCE);

		// Create package meta-data objects
		theOptionPackage.createPackageContents();
		theEcorePackage.createPackageContents();
		theGlobalPackage.createPackageContents();
		theFacePackage.createPackageContents();
		theGroupPackage.createPackageContents();
		theLoginPackage.createPackageContents();
		theProxyPackage.createPackageContents();
		theRemarkPackage.createPackageContents();
		theReplyPackage.createPackageContents();

		// Initialize created meta-data
		theOptionPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();
		theGlobalPackage.initializePackageContents();
		theFacePackage.initializePackageContents();
		theGroupPackage.initializePackageContents();
		theLoginPackage.initializePackageContents();
		theProxyPackage.initializePackageContents();
		theRemarkPackage.initializePackageContents();
		theReplyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOptionPackage.freeze();

		return theOptionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGUIOption() {
		return guiOptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_AutoDock() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_Bold() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_FontColor() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_FontName() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_FontSize() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_GroupBackground() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_Height() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_HideWhenMinimize() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_ImOnTop() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_Italic() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_LocationX() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_LocationY() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_MinimizeWhenClose() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_OnlineTipLocationX() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_OnlineTipLocationY() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_OnTop() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_ShowBlacklist() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_ShowFriendTip() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_ShowNick() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_ShowOnlineOnly() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_ShowOnlineTip() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_ShowLastLoginTip() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_ShowSignature() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_ShowCustomHead() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_ShowSmallHead() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_TreeMode() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_UseTabIMWindow() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_BarExpanded() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGUIOption_Width() {
		return (EAttribute)guiOptionEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getKeyOption() {
		return keyOptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getKeyOption_MessageKey() {
		return (EAttribute)keyOptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMessageOption() {
		return messageOptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMessageOption_AutoEject() {
		return (EAttribute)messageOptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMessageOption_EnableSound() {
		return (EAttribute)messageOptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMessageOption_RejectStranger() {
		return (EAttribute)messageOptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMessageOption_RejectTempSessionIM() {
		return (EAttribute)messageOptionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMessageOption_UseEnterInMessageMode() {
		return (EAttribute)messageOptionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMessageOption_UseEnterInTalkMode() {
		return (EAttribute)messageOptionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOptions() {
		return optionsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOptions_LoginOption() {
		return (EReference)optionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOptions_GuiOption() {
		return (EReference)optionsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOptions_MessageOption() {
		return (EReference)optionsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOptions_OtherOption() {
		return (EReference)optionsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOptions_SyncOption() {
		return (EReference)optionsEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOptions_KeyOption() {
		return (EReference)optionsEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOtherOption() {
		return otherOptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOtherOption_Browser() {
		return (EAttribute)otherOptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOtherOption_EnableLatest() {
		return (EAttribute)otherOptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOtherOption_KeepStrangerInLatest() {
		return (EAttribute)otherOptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOtherOption_LatestSize() {
		return (EAttribute)otherOptionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOtherOption_ShowFakeCam() {
		return (EAttribute)otherOptionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSyncOption() {
		return syncOptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSyncOption_AutoCheckUpdate() {
		return (EAttribute)syncOptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSyncOption_AutoDownloadFriendRemark() {
		return (EAttribute)syncOptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSyncOption_AutoDownloadGroup() {
		return (EAttribute)syncOptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSyncOption_AutoUploadGroup() {
		return (EAttribute)syncOptionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOpType() {
		return opTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OptionFactory getOptionFactory() {
		return (OptionFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		guiOptionEClass = createEClass(GUI_OPTION);
		createEAttribute(guiOptionEClass, GUI_OPTION__AUTO_DOCK);
		createEAttribute(guiOptionEClass, GUI_OPTION__BOLD);
		createEAttribute(guiOptionEClass, GUI_OPTION__FONT_COLOR);
		createEAttribute(guiOptionEClass, GUI_OPTION__FONT_NAME);
		createEAttribute(guiOptionEClass, GUI_OPTION__FONT_SIZE);
		createEAttribute(guiOptionEClass, GUI_OPTION__GROUP_BACKGROUND);
		createEAttribute(guiOptionEClass, GUI_OPTION__HEIGHT);
		createEAttribute(guiOptionEClass, GUI_OPTION__HIDE_WHEN_MINIMIZE);
		createEAttribute(guiOptionEClass, GUI_OPTION__IM_ON_TOP);
		createEAttribute(guiOptionEClass, GUI_OPTION__ITALIC);
		createEAttribute(guiOptionEClass, GUI_OPTION__LOCATION_X);
		createEAttribute(guiOptionEClass, GUI_OPTION__LOCATION_Y);
		createEAttribute(guiOptionEClass, GUI_OPTION__MINIMIZE_WHEN_CLOSE);
		createEAttribute(guiOptionEClass, GUI_OPTION__ONLINE_TIP_LOCATION_X);
		createEAttribute(guiOptionEClass, GUI_OPTION__ONLINE_TIP_LOCATION_Y);
		createEAttribute(guiOptionEClass, GUI_OPTION__ON_TOP);
		createEAttribute(guiOptionEClass, GUI_OPTION__SHOW_BLACKLIST);
		createEAttribute(guiOptionEClass, GUI_OPTION__SHOW_FRIEND_TIP);
		createEAttribute(guiOptionEClass, GUI_OPTION__SHOW_NICK);
		createEAttribute(guiOptionEClass, GUI_OPTION__SHOW_ONLINE_ONLY);
		createEAttribute(guiOptionEClass, GUI_OPTION__SHOW_ONLINE_TIP);
		createEAttribute(guiOptionEClass, GUI_OPTION__SHOW_LAST_LOGIN_TIP);
		createEAttribute(guiOptionEClass, GUI_OPTION__SHOW_SIGNATURE);
		createEAttribute(guiOptionEClass, GUI_OPTION__SHOW_CUSTOM_HEAD);
		createEAttribute(guiOptionEClass, GUI_OPTION__SHOW_SMALL_HEAD);
		createEAttribute(guiOptionEClass, GUI_OPTION__TREE_MODE);
		createEAttribute(guiOptionEClass, GUI_OPTION__USE_TAB_IM_WINDOW);
		createEAttribute(guiOptionEClass, GUI_OPTION__BAR_EXPANDED);
		createEAttribute(guiOptionEClass, GUI_OPTION__WIDTH);

		keyOptionEClass = createEClass(KEY_OPTION);
		createEAttribute(keyOptionEClass, KEY_OPTION__MESSAGE_KEY);

		messageOptionEClass = createEClass(MESSAGE_OPTION);
		createEAttribute(messageOptionEClass, MESSAGE_OPTION__AUTO_EJECT);
		createEAttribute(messageOptionEClass, MESSAGE_OPTION__ENABLE_SOUND);
		createEAttribute(messageOptionEClass, MESSAGE_OPTION__REJECT_STRANGER);
		createEAttribute(messageOptionEClass, MESSAGE_OPTION__REJECT_TEMP_SESSION_IM);
		createEAttribute(messageOptionEClass, MESSAGE_OPTION__USE_ENTER_IN_MESSAGE_MODE);
		createEAttribute(messageOptionEClass, MESSAGE_OPTION__USE_ENTER_IN_TALK_MODE);

		optionsEClass = createEClass(OPTIONS);
		createEReference(optionsEClass, OPTIONS__LOGIN_OPTION);
		createEReference(optionsEClass, OPTIONS__GUI_OPTION);
		createEReference(optionsEClass, OPTIONS__MESSAGE_OPTION);
		createEReference(optionsEClass, OPTIONS__OTHER_OPTION);
		createEReference(optionsEClass, OPTIONS__SYNC_OPTION);
		createEReference(optionsEClass, OPTIONS__KEY_OPTION);

		otherOptionEClass = createEClass(OTHER_OPTION);
		createEAttribute(otherOptionEClass, OTHER_OPTION__BROWSER);
		createEAttribute(otherOptionEClass, OTHER_OPTION__ENABLE_LATEST);
		createEAttribute(otherOptionEClass, OTHER_OPTION__KEEP_STRANGER_IN_LATEST);
		createEAttribute(otherOptionEClass, OTHER_OPTION__LATEST_SIZE);
		createEAttribute(otherOptionEClass, OTHER_OPTION__SHOW_FAKE_CAM);

		syncOptionEClass = createEClass(SYNC_OPTION);
		createEAttribute(syncOptionEClass, SYNC_OPTION__AUTO_CHECK_UPDATE);
		createEAttribute(syncOptionEClass, SYNC_OPTION__AUTO_DOWNLOAD_FRIEND_REMARK);
		createEAttribute(syncOptionEClass, SYNC_OPTION__AUTO_DOWNLOAD_GROUP);
		createEAttribute(syncOptionEClass, SYNC_OPTION__AUTO_UPLOAD_GROUP);

		// Create enums
		opTypeEEnum = createEEnum(OP_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(guiOptionEClass, GUIOption.class, "GUIOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGUIOption_AutoDock(), theXMLTypePackage.getBoolean(), "autoDock", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_Bold(), theXMLTypePackage.getBoolean(), "bold", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_FontColor(), theXMLTypePackage.getInt(), "fontColor", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_FontName(), theXMLTypePackage.getString(), "fontName", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_FontSize(), theXMLTypePackage.getInt(), "fontSize", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_GroupBackground(), theXMLTypePackage.getInt(), "groupBackground", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_Height(), theXMLTypePackage.getInt(), "height", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_HideWhenMinimize(), theXMLTypePackage.getBoolean(), "hideWhenMinimize", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_ImOnTop(), theXMLTypePackage.getBoolean(), "imOnTop", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_Italic(), theXMLTypePackage.getBoolean(), "italic", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_LocationX(), theXMLTypePackage.getInt(), "locationX", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_LocationY(), theXMLTypePackage.getInt(), "locationY", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_MinimizeWhenClose(), theXMLTypePackage.getBoolean(), "minimizeWhenClose", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_OnlineTipLocationX(), theXMLTypePackage.getInt(), "onlineTipLocationX", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_OnlineTipLocationY(), theXMLTypePackage.getInt(), "onlineTipLocationY", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_OnTop(), theXMLTypePackage.getBoolean(), "onTop", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_ShowBlacklist(), theXMLTypePackage.getBoolean(), "showBlacklist", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_ShowFriendTip(), theXMLTypePackage.getBoolean(), "showFriendTip", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_ShowNick(), theXMLTypePackage.getBoolean(), "showNick", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_ShowOnlineOnly(), theXMLTypePackage.getBoolean(), "showOnlineOnly", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_ShowOnlineTip(), theXMLTypePackage.getBoolean(), "showOnlineTip", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_ShowLastLoginTip(), theXMLTypePackage.getBoolean(), "showLastLoginTip", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_ShowSignature(), theXMLTypePackage.getBoolean(), "showSignature", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_ShowCustomHead(), theXMLTypePackage.getBoolean(), "showCustomHead", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_ShowSmallHead(), theXMLTypePackage.getBoolean(), "showSmallHead", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_TreeMode(), theXMLTypePackage.getBoolean(), "treeMode", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_UseTabIMWindow(), theXMLTypePackage.getBoolean(), "useTabIMWindow", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_BarExpanded(), theXMLTypePackage.getBoolean(), "barExpanded", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGUIOption_Width(), theXMLTypePackage.getInt(), "width", null, 1, 1, GUIOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(keyOptionEClass, KeyOption.class, "KeyOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getKeyOption_MessageKey(), theXMLTypePackage.getString(), "messageKey", null, 1, 1, KeyOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(messageOptionEClass, MessageOption.class, "MessageOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMessageOption_AutoEject(), theXMLTypePackage.getBoolean(), "autoEject", null, 1, 1, MessageOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMessageOption_EnableSound(), theXMLTypePackage.getBoolean(), "enableSound", null, 1, 1, MessageOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMessageOption_RejectStranger(), theXMLTypePackage.getBoolean(), "rejectStranger", null, 1, 1, MessageOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMessageOption_RejectTempSessionIM(), theXMLTypePackage.getBoolean(), "rejectTempSessionIM", null, 1, 1, MessageOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMessageOption_UseEnterInMessageMode(), theXMLTypePackage.getBoolean(), "useEnterInMessageMode", null, 1, 1, MessageOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMessageOption_UseEnterInTalkMode(), theXMLTypePackage.getBoolean(), "useEnterInTalkMode", null, 1, 1, MessageOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(optionsEClass, Options.class, "Options", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOptions_LoginOption(), theEcorePackage.getLoginOption(), null, "loginOption", null, 1, 1, Options.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOptions_GuiOption(), this.getGUIOption(), null, "guiOption", null, 1, 1, Options.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOptions_MessageOption(), this.getMessageOption(), null, "messageOption", null, 1, 1, Options.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOptions_OtherOption(), this.getOtherOption(), null, "otherOption", null, 1, 1, Options.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOptions_SyncOption(), this.getSyncOption(), null, "syncOption", null, 1, 1, Options.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOptions_KeyOption(), this.getKeyOption(), null, "keyOption", null, 1, 1, Options.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(otherOptionEClass, OtherOption.class, "OtherOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOtherOption_Browser(), theXMLTypePackage.getString(), "browser", null, 1, 1, OtherOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOtherOption_EnableLatest(), theXMLTypePackage.getBoolean(), "enableLatest", null, 1, 1, OtherOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOtherOption_KeepStrangerInLatest(), theXMLTypePackage.getBoolean(), "keepStrangerInLatest", null, 1, 1, OtherOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOtherOption_LatestSize(), theXMLTypePackage.getInt(), "latestSize", null, 1, 1, OtherOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOtherOption_ShowFakeCam(), theXMLTypePackage.getBoolean(), "showFakeCam", null, 1, 1, OtherOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(syncOptionEClass, SyncOption.class, "SyncOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSyncOption_AutoCheckUpdate(), theXMLTypePackage.getBoolean(), "autoCheckUpdate", null, 1, 1, SyncOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSyncOption_AutoDownloadFriendRemark(), theXMLTypePackage.getBoolean(), "autoDownloadFriendRemark", null, 1, 1, SyncOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSyncOption_AutoDownloadGroup(), theXMLTypePackage.getBoolean(), "autoDownloadGroup", null, 1, 1, SyncOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSyncOption_AutoUploadGroup(), this.getOpType(), "autoUploadGroup", "Always", 1, 1, SyncOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(opTypeEEnum, OpType.class, "OpType");
		addEEnumLiteral(opTypeEEnum, OpType.ALWAYS_LITERAL);
		addEEnumLiteral(opTypeEEnum, OpType.NEVER_LITERAL);
		addEEnumLiteral(opTypeEEnum, OpType.PROMPT_LITERAL);
	}

} //OptionPackageImpl
