/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.login.impl;

import edu.tsinghua.lumaqq.ecore.EcorePackage;

import edu.tsinghua.lumaqq.ecore.face.FacePackage;

import edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl;

import edu.tsinghua.lumaqq.ecore.global.GlobalPackage;

import edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl;

import edu.tsinghua.lumaqq.ecore.group.GroupPackage;

import edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl;

import edu.tsinghua.lumaqq.ecore.impl.EcorePackageImpl;

import edu.tsinghua.lumaqq.ecore.login.Login;
import edu.tsinghua.lumaqq.ecore.login.LoginFactory;
import edu.tsinghua.lumaqq.ecore.login.LoginPackage;
import edu.tsinghua.lumaqq.ecore.login.Logins;

import edu.tsinghua.lumaqq.ecore.option.OptionPackage;

import edu.tsinghua.lumaqq.ecore.option.impl.OptionPackageImpl;

import edu.tsinghua.lumaqq.ecore.proxy.ProxyPackage;

import edu.tsinghua.lumaqq.ecore.proxy.impl.ProxyPackageImpl;

import edu.tsinghua.lumaqq.ecore.remark.RemarkPackage;

import edu.tsinghua.lumaqq.ecore.remark.impl.RemarkPackageImpl;

import edu.tsinghua.lumaqq.ecore.reply.ReplyPackage;

import edu.tsinghua.lumaqq.ecore.reply.impl.ReplyPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
public class LoginPackageImpl extends EPackageImpl implements LoginPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loginEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loginsEClass = null;

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
	 * @see edu.tsinghua.lumaqq.ecore.login.LoginPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LoginPackageImpl() {
		super(eNS_URI, LoginFactory.eINSTANCE);
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
	public static LoginPackage init() {
		if (isInited) return (LoginPackage)EPackage.Registry.INSTANCE.getEPackage(LoginPackage.eNS_URI);

		// Obtain or create and register package
		LoginPackageImpl theLoginPackage = (LoginPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof LoginPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new LoginPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EcorePackageImpl theEcorePackage = (EcorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) instanceof EcorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) : EcorePackage.eINSTANCE);
		GlobalPackageImpl theGlobalPackage = (GlobalPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GlobalPackage.eNS_URI) instanceof GlobalPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GlobalPackage.eNS_URI) : GlobalPackage.eINSTANCE);
		FacePackageImpl theFacePackage = (FacePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FacePackage.eNS_URI) instanceof FacePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FacePackage.eNS_URI) : FacePackage.eINSTANCE);
		GroupPackageImpl theGroupPackage = (GroupPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GroupPackage.eNS_URI) instanceof GroupPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GroupPackage.eNS_URI) : GroupPackage.eINSTANCE);
		OptionPackageImpl theOptionPackage = (OptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OptionPackage.eNS_URI) instanceof OptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OptionPackage.eNS_URI) : OptionPackage.eINSTANCE);
		ProxyPackageImpl theProxyPackage = (ProxyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProxyPackage.eNS_URI) instanceof ProxyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProxyPackage.eNS_URI) : ProxyPackage.eINSTANCE);
		RemarkPackageImpl theRemarkPackage = (RemarkPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI) instanceof RemarkPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI) : RemarkPackage.eINSTANCE);
		ReplyPackageImpl theReplyPackage = (ReplyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReplyPackage.eNS_URI) instanceof ReplyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReplyPackage.eNS_URI) : ReplyPackage.eINSTANCE);

		// Create package meta-data objects
		theLoginPackage.createPackageContents();
		theEcorePackage.createPackageContents();
		theGlobalPackage.createPackageContents();
		theFacePackage.createPackageContents();
		theGroupPackage.createPackageContents();
		theOptionPackage.createPackageContents();
		theProxyPackage.createPackageContents();
		theRemarkPackage.createPackageContents();
		theReplyPackage.createPackageContents();

		// Initialize created meta-data
		theLoginPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();
		theGlobalPackage.initializePackageContents();
		theFacePackage.initializePackageContents();
		theGroupPackage.initializePackageContents();
		theOptionPackage.initializePackageContents();
		theProxyPackage.initializePackageContents();
		theRemarkPackage.initializePackageContents();
		theReplyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLoginPackage.freeze();

		return theLoginPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLogin() {
		return loginEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_AutoLogin() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_LoginHidden() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_Password() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_Qq() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_RememberPassword() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLogins() {
		return loginsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogins_Login() {
		return (EReference)loginsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogins_LastLogin() {
		return (EAttribute)loginsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogins_Network() {
		return (EReference)loginsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoginFactory getLoginFactory() {
		return (LoginFactory)getEFactoryInstance();
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
		loginEClass = createEClass(LOGIN);
		createEAttribute(loginEClass, LOGIN__AUTO_LOGIN);
		createEAttribute(loginEClass, LOGIN__LOGIN_HIDDEN);
		createEAttribute(loginEClass, LOGIN__PASSWORD);
		createEAttribute(loginEClass, LOGIN__QQ);
		createEAttribute(loginEClass, LOGIN__REMEMBER_PASSWORD);

		loginsEClass = createEClass(LOGINS);
		createEReference(loginsEClass, LOGINS__LOGIN);
		createEAttribute(loginsEClass, LOGINS__LAST_LOGIN);
		createEReference(loginsEClass, LOGINS__NETWORK);
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
		initEClass(loginEClass, Login.class, "Login", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLogin_AutoLogin(), theXMLTypePackage.getBoolean(), "autoLogin", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogin_LoginHidden(), theXMLTypePackage.getBoolean(), "loginHidden", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogin_Password(), theXMLTypePackage.getString(), "password", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogin_Qq(), theXMLTypePackage.getString(), "qq", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogin_RememberPassword(), theXMLTypePackage.getBoolean(), "rememberPassword", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(loginsEClass, Logins.class, "Logins", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLogins_Login(), this.getLogin(), null, "login", null, 0, -1, Logins.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogins_LastLogin(), theXMLTypePackage.getString(), "lastLogin", null, 1, 1, Logins.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLogins_Network(), theEcorePackage.getLoginOption(), null, "network", null, 1, 1, Logins.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //LoginPackageImpl
