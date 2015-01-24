/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.global;

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
 * @see edu.tsinghua.lumaqq.ecore.global.GlobalFactory
 * @model kind="package"
 * @generated
 */
public interface GlobalPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "global";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore/global";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "global";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GlobalPackage eINSTANCE = edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.global.impl.GlobalSettingImpl <em>Setting</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalSettingImpl
	 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl#getGlobalSetting()
	 * @generated
	 */
	int GLOBAL_SETTING = 0;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_SETTING__LANGUAGE = 0;

	/**
	 * The feature id for the '<em><b>Servers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_SETTING__SERVERS = 1;

	/**
	 * The feature id for the '<em><b>Robots</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_SETTING__ROBOTS = 2;

	/**
	 * The number of structural features of the '<em>Setting</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_SETTING_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.global.impl.ServersImpl <em>Servers</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.global.impl.ServersImpl
	 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl#getServers()
	 * @generated
	 */
	int SERVERS = 1;

	/**
	 * The feature id for the '<em><b>TCP Server</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVERS__TCP_SERVER = 0;

	/**
	 * The feature id for the '<em><b>UDP Server</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVERS__UDP_SERVER = 1;

	/**
	 * The number of structural features of the '<em>Servers</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVERS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.global.impl.RobotImpl <em>Robot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.global.impl.RobotImpl
	 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl#getRobot()
	 * @generated
	 */
	int ROBOT = 2;

	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROBOT__CLASS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROBOT__NAME = 1;

	/**
	 * The number of structural features of the '<em>Robot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROBOT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.global.impl.RobotsImpl <em>Robots</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.global.impl.RobotsImpl
	 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl#getRobots()
	 * @generated
	 */
	int ROBOTS = 3;

	/**
	 * The feature id for the '<em><b>Robot</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROBOTS__ROBOT = 0;

	/**
	 * The number of structural features of the '<em>Robots</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROBOTS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.global.LanguageType <em>Language Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.global.LanguageType
	 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl#getLanguageType()
	 * @generated
	 */
	int LANGUAGE_TYPE = 4;


	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting <em>Setting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Setting</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.GlobalSetting
	 * @generated
	 */
	EClass getGlobalSetting();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getLanguage()
	 * @see #getGlobalSetting()
	 * @generated
	 */
	EAttribute getGlobalSetting_Language();

	/**
	 * Returns the meta object for the containment reference '{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getServers <em>Servers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Servers</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getServers()
	 * @see #getGlobalSetting()
	 * @generated
	 */
	EReference getGlobalSetting_Servers();

	/**
	 * Returns the meta object for the containment reference '{@link edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getRobots <em>Robots</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Robots</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.GlobalSetting#getRobots()
	 * @see #getGlobalSetting()
	 * @generated
	 */
	EReference getGlobalSetting_Robots();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.global.Servers <em>Servers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Servers</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.Servers
	 * @generated
	 */
	EClass getServers();

	/**
	 * Returns the meta object for the attribute list '{@link edu.tsinghua.lumaqq.ecore.global.Servers#getTCPServer <em>TCP Server</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>TCP Server</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.Servers#getTCPServer()
	 * @see #getServers()
	 * @generated
	 */
	EAttribute getServers_TCPServer();

	/**
	 * Returns the meta object for the attribute list '{@link edu.tsinghua.lumaqq.ecore.global.Servers#getUDPServer <em>UDP Server</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>UDP Server</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.Servers#getUDPServer()
	 * @see #getServers()
	 * @generated
	 */
	EAttribute getServers_UDPServer();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.global.Robot <em>Robot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Robot</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.Robot
	 * @generated
	 */
	EClass getRobot();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.global.Robot#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.Robot#getClass_()
	 * @see #getRobot()
	 * @generated
	 */
	EAttribute getRobot_Class();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.global.Robot#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.Robot#getName()
	 * @see #getRobot()
	 * @generated
	 */
	EAttribute getRobot_Name();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.global.Robots <em>Robots</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Robots</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.Robots
	 * @generated
	 */
	EClass getRobots();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.global.Robots#getRobot <em>Robot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Robot</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.Robots#getRobot()
	 * @see #getRobots()
	 * @generated
	 */
	EReference getRobots_Robot();

	/**
	 * Returns the meta object for enum '{@link edu.tsinghua.lumaqq.ecore.global.LanguageType <em>Language Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Language Type</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.global.LanguageType
	 * @generated
	 */
	EEnum getLanguageType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GlobalFactory getGlobalFactory();

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
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.global.impl.GlobalSettingImpl <em>Setting</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalSettingImpl
		 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl#getGlobalSetting()
		 * @generated
		 */
		EClass GLOBAL_SETTING = eINSTANCE.getGlobalSetting();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_SETTING__LANGUAGE = eINSTANCE.getGlobalSetting_Language();

		/**
		 * The meta object literal for the '<em><b>Servers</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_SETTING__SERVERS = eINSTANCE.getGlobalSetting_Servers();

		/**
		 * The meta object literal for the '<em><b>Robots</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_SETTING__ROBOTS = eINSTANCE.getGlobalSetting_Robots();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.global.impl.ServersImpl <em>Servers</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.global.impl.ServersImpl
		 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl#getServers()
		 * @generated
		 */
		EClass SERVERS = eINSTANCE.getServers();

		/**
		 * The meta object literal for the '<em><b>TCP Server</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVERS__TCP_SERVER = eINSTANCE.getServers_TCPServer();

		/**
		 * The meta object literal for the '<em><b>UDP Server</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVERS__UDP_SERVER = eINSTANCE.getServers_UDPServer();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.global.impl.RobotImpl <em>Robot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.global.impl.RobotImpl
		 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl#getRobot()
		 * @generated
		 */
		EClass ROBOT = eINSTANCE.getRobot();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROBOT__CLASS = eINSTANCE.getRobot_Class();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROBOT__NAME = eINSTANCE.getRobot_Name();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.global.impl.RobotsImpl <em>Robots</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.global.impl.RobotsImpl
		 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl#getRobots()
		 * @generated
		 */
		EClass ROBOTS = eINSTANCE.getRobots();

		/**
		 * The meta object literal for the '<em><b>Robot</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROBOTS__ROBOT = eINSTANCE.getRobots_Robot();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.global.LanguageType <em>Language Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.global.LanguageType
		 * @see edu.tsinghua.lumaqq.ecore.global.impl.GlobalPackageImpl#getLanguageType()
		 * @generated
		 */
		EEnum LANGUAGE_TYPE = eINSTANCE.getLanguageType();

	}

} //GlobalPackage
