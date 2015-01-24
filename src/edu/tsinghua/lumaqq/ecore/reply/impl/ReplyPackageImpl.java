/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.reply.impl;

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

import edu.tsinghua.lumaqq.ecore.option.OptionPackage;

import edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl;

import edu.tsinghua.lumaqq.ecore.proxy.ProxyPackage;

import edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyPackageImpl;

import edu.tsinghua.lumaqq.ecore.remark.RemarkPackage;

import edu.tsinghua.lumaqq.ecore.remark.impl.RemarkPackageImpl;

import edu.tsinghua.lumaqq.ecore.reply.Replies;
import edu.tsinghua.lumaqq.ecore.reply.ReplyFactory;
import edu.tsinghua.lumaqq.ecore.reply.ReplyPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReplyPackageImpl extends EPackageImpl implements ReplyPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass repliesEClass = null;

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
	 * @see edu.tsinghua.lumaqq.ecore.reply.ReplyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ReplyPackageImpl() {
		super(eNS_URI, ReplyFactory.eINSTANCE);
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
	public static ReplyPackage init() {
		if (isInited) return (ReplyPackage)EPackage.Registry.INSTANCE.getEPackage(ReplyPackage.eNS_URI);

		// Obtain or create and register package
		ReplyPackageImpl theReplyPackage = (ReplyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof ReplyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new ReplyPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EcorePackageImpl theEcorePackage = (EcorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) instanceof EcorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) : EcorePackage.eINSTANCE);
		GlobalPackageImpl theGlobalPackage = (GlobalPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GlobalPackage.eNS_URI) instanceof GlobalPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GlobalPackage.eNS_URI) : GlobalPackage.eINSTANCE);
		FacePackageImpl theFacePackage = (FacePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FacePackage.eNS_URI) instanceof FacePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FacePackage.eNS_URI) : FacePackage.eINSTANCE);
		GroupPackageImpl theGroupPackage = (GroupPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GroupPackage.eNS_URI) instanceof GroupPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GroupPackage.eNS_URI) : GroupPackage.eINSTANCE);
		LoginPackageImpl theLoginPackage = (LoginPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(LoginPackage.eNS_URI) instanceof LoginPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(LoginPackage.eNS_URI) : LoginPackage.eINSTANCE);
		OptionPackageImpl theOptionPackage = (OptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OptionPackage.eNS_URI) instanceof OptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OptionPackage.eNS_URI) : OptionPackage.eINSTANCE);
		ProxyPackageImpl theProxyPackage = (ProxyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProxyPackage.eNS_URI) instanceof ProxyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProxyPackage.eNS_URI) : ProxyPackage.eINSTANCE);
		RemarkPackageImpl theRemarkPackage = (RemarkPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI) instanceof RemarkPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI) : RemarkPackage.eINSTANCE);

		// Create package meta-data objects
		theReplyPackage.createPackageContents();
		theEcorePackage.createPackageContents();
		theGlobalPackage.createPackageContents();
		theFacePackage.createPackageContents();
		theGroupPackage.createPackageContents();
		theLoginPackage.createPackageContents();
		theOptionPackage.createPackageContents();
		theProxyPackage.createPackageContents();
		theRemarkPackage.createPackageContents();

		// Initialize created meta-data
		theReplyPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();
		theGlobalPackage.initializePackageContents();
		theFacePackage.initializePackageContents();
		theGroupPackage.initializePackageContents();
		theLoginPackage.initializePackageContents();
		theOptionPackage.initializePackageContents();
		theProxyPackage.initializePackageContents();
		theRemarkPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theReplyPackage.freeze();

		return theReplyPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReplies() {
		return repliesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReplies_QuickReply() {
		return (EAttribute)repliesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReplies_AutoReply() {
		return (EAttribute)repliesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReplies_CurrentAutoReply() {
		return (EAttribute)repliesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReplies_CurrentQuickReply() {
		return (EAttribute)repliesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReplyFactory getReplyFactory() {
		return (ReplyFactory)getEFactoryInstance();
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
		repliesEClass = createEClass(REPLIES);
		createEAttribute(repliesEClass, REPLIES__QUICK_REPLY);
		createEAttribute(repliesEClass, REPLIES__AUTO_REPLY);
		createEAttribute(repliesEClass, REPLIES__CURRENT_AUTO_REPLY);
		createEAttribute(repliesEClass, REPLIES__CURRENT_QUICK_REPLY);
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

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(repliesEClass, Replies.class, "Replies", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReplies_QuickReply(), theXMLTypePackage.getString(), "quickReply", null, 0, -1, Replies.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReplies_AutoReply(), theXMLTypePackage.getString(), "autoReply", null, 0, -1, Replies.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReplies_CurrentAutoReply(), theXMLTypePackage.getInt(), "currentAutoReply", null, 1, 1, Replies.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReplies_CurrentQuickReply(), theXMLTypePackage.getInt(), "currentQuickReply", null, 1, 1, Replies.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //ReplyPackageImpl
