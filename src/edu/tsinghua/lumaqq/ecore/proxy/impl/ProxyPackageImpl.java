/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.proxy.impl;

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

import edu.tsinghua.lumaqq.ecore.proxy.HttpProxy;
import edu.tsinghua.lumaqq.ecore.proxy.Proxies;
import edu.tsinghua.lumaqq.ecore.proxy.ProxyFactory;
import edu.tsinghua.lumaqq.ecore.proxy.ProxyPackage;
import edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy;

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
public class ProxyPackageImpl extends EPackageImpl implements ProxyPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass httpProxyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass proxiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass socks5ProxyEClass = null;

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
	 * @see edu.tsinghua.lumaqq.ecore.proxy.ProxyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ProxyPackageImpl() {
		super(eNS_URI, ProxyFactory.eINSTANCE);
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
	public static ProxyPackage init() {
		if (isInited) return (ProxyPackage)EPackage.Registry.INSTANCE.getEPackage(ProxyPackage.eNS_URI);

		// Obtain or create and register package
		ProxyPackageImpl theProxyPackage = (ProxyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof ProxyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new ProxyPackageImpl());

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
		RemarkPackageImpl theRemarkPackage = (RemarkPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI) instanceof RemarkPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RemarkPackage.eNS_URI) : RemarkPackage.eINSTANCE);
		ReplyPackageImpl theReplyPackage = (ReplyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReplyPackage.eNS_URI) instanceof ReplyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReplyPackage.eNS_URI) : ReplyPackage.eINSTANCE);

		// Create package meta-data objects
		theProxyPackage.createPackageContents();
		theEcorePackage.createPackageContents();
		theGlobalPackage.createPackageContents();
		theFacePackage.createPackageContents();
		theGroupPackage.createPackageContents();
		theLoginPackage.createPackageContents();
		theOptionPackage.createPackageContents();
		theRemarkPackage.createPackageContents();
		theReplyPackage.createPackageContents();

		// Initialize created meta-data
		theProxyPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();
		theGlobalPackage.initializePackageContents();
		theFacePackage.initializePackageContents();
		theGroupPackage.initializePackageContents();
		theLoginPackage.initializePackageContents();
		theOptionPackage.initializePackageContents();
		theRemarkPackage.initializePackageContents();
		theReplyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theProxyPackage.freeze();

		return theProxyPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHttpProxy() {
		return httpProxyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHttpProxy_Password() {
		return (EAttribute)httpProxyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHttpProxy_Port() {
		return (EAttribute)httpProxyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHttpProxy_Server() {
		return (EAttribute)httpProxyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHttpProxy_Username() {
		return (EAttribute)httpProxyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProxies() {
		return proxiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProxies_Socks5Proxy() {
		return (EReference)proxiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProxies_HttpProxy() {
		return (EReference)proxiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSocks5Proxy() {
		return socks5ProxyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSocks5Proxy_Password() {
		return (EAttribute)socks5ProxyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSocks5Proxy_Port() {
		return (EAttribute)socks5ProxyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSocks5Proxy_Server() {
		return (EAttribute)socks5ProxyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSocks5Proxy_Username() {
		return (EAttribute)socks5ProxyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProxyFactory getProxyFactory() {
		return (ProxyFactory)getEFactoryInstance();
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
		httpProxyEClass = createEClass(HTTP_PROXY);
		createEAttribute(httpProxyEClass, HTTP_PROXY__PASSWORD);
		createEAttribute(httpProxyEClass, HTTP_PROXY__PORT);
		createEAttribute(httpProxyEClass, HTTP_PROXY__SERVER);
		createEAttribute(httpProxyEClass, HTTP_PROXY__USERNAME);

		proxiesEClass = createEClass(PROXIES);
		createEReference(proxiesEClass, PROXIES__SOCKS5_PROXY);
		createEReference(proxiesEClass, PROXIES__HTTP_PROXY);

		socks5ProxyEClass = createEClass(SOCKS5_PROXY);
		createEAttribute(socks5ProxyEClass, SOCKS5_PROXY__PASSWORD);
		createEAttribute(socks5ProxyEClass, SOCKS5_PROXY__PORT);
		createEAttribute(socks5ProxyEClass, SOCKS5_PROXY__SERVER);
		createEAttribute(socks5ProxyEClass, SOCKS5_PROXY__USERNAME);
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
		initEClass(httpProxyEClass, HttpProxy.class, "HttpProxy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHttpProxy_Password(), theXMLTypePackage.getString(), "password", null, 1, 1, HttpProxy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHttpProxy_Port(), theXMLTypePackage.getString(), "port", null, 1, 1, HttpProxy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHttpProxy_Server(), theXMLTypePackage.getString(), "server", null, 1, 1, HttpProxy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHttpProxy_Username(), theXMLTypePackage.getString(), "username", null, 1, 1, HttpProxy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(proxiesEClass, Proxies.class, "Proxies", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProxies_Socks5Proxy(), this.getSocks5Proxy(), null, "socks5Proxy", null, 0, -1, Proxies.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProxies_HttpProxy(), this.getHttpProxy(), null, "httpProxy", null, 0, -1, Proxies.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(socks5ProxyEClass, Socks5Proxy.class, "Socks5Proxy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSocks5Proxy_Password(), theXMLTypePackage.getString(), "password", null, 1, 1, Socks5Proxy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSocks5Proxy_Port(), theXMLTypePackage.getString(), "port", null, 1, 1, Socks5Proxy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSocks5Proxy_Server(), theXMLTypePackage.getString(), "server", null, 1, 1, Socks5Proxy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSocks5Proxy_Username(), theXMLTypePackage.getString(), "username", null, 1, 1, Socks5Proxy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //ProxyPackageImpl
