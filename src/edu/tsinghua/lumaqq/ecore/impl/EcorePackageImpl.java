/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.impl;

import edu.tsinghua.lumaqq.ecore.EcoreFactory;
import edu.tsinghua.lumaqq.ecore.EcorePackage;
import edu.tsinghua.lumaqq.ecore.LoginOption;
import edu.tsinghua.lumaqq.ecore.ProxyType;

import edu.tsinghua.lumaqq.ecore.face.FacePackage;

import edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl;

import edu.tsinghua.lumaqq.ecore.global.GlobalPackage;

import edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl;

import edu.tsinghua.lumaqq.ecore.group.GroupPackage;

import edu.tsinghua.lumaqq.ecore.group.impl.GroupPackageImpl;

import edu.tsinghua.lumaqq.ecore.login.LoginPackage;

import edu.tsinghua.lumaqq.ecore.login.impl.LoginPackageImpl;

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
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EcorePackageImpl extends EPackageImpl implements EcorePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loginOptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum proxyTypeEEnum = null;

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
	 * @see edu.tsinghua.lumaqq.ecore.EcorePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EcorePackageImpl() {
		super(eNS_URI, EcoreFactory.eINSTANCE);
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
	public static EcorePackage init() {
		if (isInited) return (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Obtain or create and register package
		EcorePackageImpl theEcorePackage = (EcorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof EcorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new EcorePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		GlobalPackageImpl theGlobalPackage = (GlobalPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GlobalPackage.eNS_URI) instanceof GlobalPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GlobalPackage.eNS_URI) : GlobalPackage.eINSTANCE);
		FacePackageImpl theFacePackage = (FacePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FacePackage.eNS_URI) instanceof FacePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FacePackage.eNS_URI) : FacePackage.eINSTANCE);
		GroupPackageImpl theGroupPackage = (GroupPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GroupPackage.eNS_URI) instanceof GroupPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GroupPackage.eNS_URI) : GroupPackage.eINSTANCE);
		LoginPackageImpl theLoginPackage = (LoginPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(LoginPackage.eNS_URI) instanceof LoginPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(LoginPackage.eNS_URI) : LoginPackage.eINSTANCE);
		OptionPackageImpl theOptionPackage = (OptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OptionPackage.eNS_URI) instanceof OptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OptionPackage.eNS_URI) : OptionPackage.eINSTANCE);
		ProxyPackageImpl theProxyPackage = (ProxyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProxyPackage.eNS_URI) instanceof ProxyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProxyPackage.eNS_URI) : ProxyPackage.eINSTANCE);
		RemarkPackageImpl theRemarkPackage = (RemarkPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI) instanceof RemarkPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI) : RemarkPackage.eINSTANCE);
		ReplyPackageImpl theReplyPackage = (ReplyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReplyPackage.eNS_URI) instanceof ReplyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReplyPackage.eNS_URI) : ReplyPackage.eINSTANCE);

		// Create package meta-data objects
		theEcorePackage.createPackageContents();
		theGlobalPackage.createPackageContents();
		theFacePackage.createPackageContents();
		theGroupPackage.createPackageContents();
		theLoginPackage.createPackageContents();
		theOptionPackage.createPackageContents();
		theProxyPackage.createPackageContents();
		theRemarkPackage.createPackageContents();
		theReplyPackage.createPackageContents();

		// Initialize created meta-data
		theEcorePackage.initializePackageContents();
		theGlobalPackage.initializePackageContents();
		theFacePackage.initializePackageContents();
		theGroupPackage.initializePackageContents();
		theLoginPackage.initializePackageContents();
		theOptionPackage.initializePackageContents();
		theProxyPackage.initializePackageContents();
		theRemarkPackage.initializePackageContents();
		theReplyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEcorePackage.freeze();

		return theEcorePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLoginOption() {
		return loginOptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoginOption_AutoSelect() {
		return (EAttribute)loginOptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoginOption_ProxyPassword() {
		return (EAttribute)loginOptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoginOption_ProxyPort() {
		return (EAttribute)loginOptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoginOption_ProxyServer() {
		return (EAttribute)loginOptionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoginOption_ProxyType() {
		return (EAttribute)loginOptionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoginOption_ProxyUsername() {
		return (EAttribute)loginOptionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoginOption_Server() {
		return (EAttribute)loginOptionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoginOption_TcpPort() {
		return (EAttribute)loginOptionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoginOption_UseTcp() {
		return (EAttribute)loginOptionEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getProxyType() {
		return proxyTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EcoreFactory getEcoreFactory() {
		return (EcoreFactory)getEFactoryInstance();
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
		loginOptionEClass = createEClass(LOGIN_OPTION);
		createEAttribute(loginOptionEClass, LOGIN_OPTION__AUTO_SELECT);
		createEAttribute(loginOptionEClass, LOGIN_OPTION__PROXY_PASSWORD);
		createEAttribute(loginOptionEClass, LOGIN_OPTION__PROXY_PORT);
		createEAttribute(loginOptionEClass, LOGIN_OPTION__PROXY_SERVER);
		createEAttribute(loginOptionEClass, LOGIN_OPTION__PROXY_TYPE);
		createEAttribute(loginOptionEClass, LOGIN_OPTION__PROXY_USERNAME);
		createEAttribute(loginOptionEClass, LOGIN_OPTION__SERVER);
		createEAttribute(loginOptionEClass, LOGIN_OPTION__TCP_PORT);
		createEAttribute(loginOptionEClass, LOGIN_OPTION__USE_TCP);

		// Create enums
		proxyTypeEEnum = createEEnum(PROXY_TYPE);
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
	@SuppressWarnings("unchecked")
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		GlobalPackage theGlobalPackage = (GlobalPackage)EPackage.Registry.INSTANCE.getEPackage(GlobalPackage.eNS_URI);
		FacePackage theFacePackage = (FacePackage)EPackage.Registry.INSTANCE.getEPackage(FacePackage.eNS_URI);
		GroupPackage theGroupPackage = (GroupPackage)EPackage.Registry.INSTANCE.getEPackage(GroupPackage.eNS_URI);
		LoginPackage theLoginPackage = (LoginPackage)EPackage.Registry.INSTANCE.getEPackage(LoginPackage.eNS_URI);
		OptionPackage theOptionPackage = (OptionPackage)EPackage.Registry.INSTANCE.getEPackage(OptionPackage.eNS_URI);
		ProxyPackage theProxyPackage = (ProxyPackage)EPackage.Registry.INSTANCE.getEPackage(ProxyPackage.eNS_URI);
		RemarkPackage theRemarkPackage = (RemarkPackage)EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI);
		ReplyPackage theReplyPackage = (ReplyPackage)EPackage.Registry.INSTANCE.getEPackage(ReplyPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theGlobalPackage);
		getESubpackages().add(theFacePackage);
		getESubpackages().add(theGroupPackage);
		getESubpackages().add(theLoginPackage);
		getESubpackages().add(theOptionPackage);
		getESubpackages().add(theProxyPackage);
		getESubpackages().add(theRemarkPackage);
		getESubpackages().add(theReplyPackage);

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(loginOptionEClass, LoginOption.class, "LoginOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLoginOption_AutoSelect(), theXMLTypePackage.getBoolean(), "autoSelect", null, 1, 1, LoginOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLoginOption_ProxyPassword(), theXMLTypePackage.getString(), "proxyPassword", null, 1, 1, LoginOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLoginOption_ProxyPort(), theXMLTypePackage.getInt(), "proxyPort", null, 1, 1, LoginOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLoginOption_ProxyServer(), theXMLTypePackage.getString(), "proxyServer", null, 1, 1, LoginOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLoginOption_ProxyType(), this.getProxyType(), "proxyType", "None", 1, 1, LoginOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLoginOption_ProxyUsername(), theXMLTypePackage.getString(), "proxyUsername", null, 1, 1, LoginOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLoginOption_Server(), theXMLTypePackage.getString(), "server", null, 1, 1, LoginOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLoginOption_TcpPort(), theXMLTypePackage.getInt(), "tcpPort", null, 1, 1, LoginOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLoginOption_UseTcp(), theXMLTypePackage.getBoolean(), "useTcp", null, 1, 1, LoginOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(proxyTypeEEnum, ProxyType.class, "ProxyType");
		addEEnumLiteral(proxyTypeEEnum, ProxyType.NONE_LITERAL);
		addEEnumLiteral(proxyTypeEEnum, ProxyType.HTTP_LITERAL);
		addEEnumLiteral(proxyTypeEEnum, ProxyType.SOCKS5_LITERAL);

		// Create resource
		createResource(eNS_URI);
	}

} //EcorePackageImpl
