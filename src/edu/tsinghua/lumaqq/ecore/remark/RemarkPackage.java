/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.remark;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see edu.tsinghua.lumaqq.ecore.remark.RemarkFactory
 * @model kind="package"
 * @generated
 */
public interface RemarkPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "remark";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore/remark";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "remark";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RemarkPackage eINSTANCE = edu.tsinghua.lumaqq.ecore.remark.impl.RemarkPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl <em>Remark</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl
	 * @see edu.tsinghua.lumaqq.ecore.remark.impl.RemarkPackageImpl#getRemark()
	 * @generated
	 */
	int REMARK = 0;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARK__ADDRESS = 0;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARK__EMAIL = 1;

	/**
	 * The feature id for the '<em><b>Mobile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARK__MOBILE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARK__NAME = 3;

	/**
	 * The feature id for the '<em><b>Note</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARK__NOTE = 4;

	/**
	 * The feature id for the '<em><b>Qq</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARK__QQ = 5;

	/**
	 * The feature id for the '<em><b>Telephone</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARK__TELEPHONE = 6;

	/**
	 * The feature id for the '<em><b>Zipcode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARK__ZIPCODE = 7;

	/**
	 * The number of structural features of the '<em>Remark</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARK_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarksImpl <em>Remarks</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.remark.impl.RemarksImpl
	 * @see edu.tsinghua.lumaqq.ecore.remark.impl.RemarkPackageImpl#getRemarks()
	 * @generated
	 */
	int REMARKS = 1;

	/**
	 * The feature id for the '<em><b>Remark</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARKS__REMARK = 0;

	/**
	 * The number of structural features of the '<em>Remarks</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMARKS_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.remark.Remark <em>Remark</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remark</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remark
	 * @generated
	 */
	EClass getRemark();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.remark.Remark#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Address</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remark#getAddress()
	 * @see #getRemark()
	 * @generated
	 */
	EAttribute getRemark_Address();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.remark.Remark#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remark#getEmail()
	 * @see #getRemark()
	 * @generated
	 */
	EAttribute getRemark_Email();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.remark.Remark#getMobile <em>Mobile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mobile</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remark#getMobile()
	 * @see #getRemark()
	 * @generated
	 */
	EAttribute getRemark_Mobile();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.remark.Remark#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remark#getName()
	 * @see #getRemark()
	 * @generated
	 */
	EAttribute getRemark_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.remark.Remark#getNote <em>Note</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Note</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remark#getNote()
	 * @see #getRemark()
	 * @generated
	 */
	EAttribute getRemark_Note();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.remark.Remark#getQq <em>Qq</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qq</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remark#getQq()
	 * @see #getRemark()
	 * @generated
	 */
	EAttribute getRemark_Qq();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.remark.Remark#getTelephone <em>Telephone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Telephone</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remark#getTelephone()
	 * @see #getRemark()
	 * @generated
	 */
	EAttribute getRemark_Telephone();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.remark.Remark#getZipcode <em>Zipcode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zipcode</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remark#getZipcode()
	 * @see #getRemark()
	 * @generated
	 */
	EAttribute getRemark_Zipcode();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.remark.Remarks <em>Remarks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remarks</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remarks
	 * @generated
	 */
	EClass getRemarks();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.remark.Remarks#getRemark <em>Remark</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Remark</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.remark.Remarks#getRemark()
	 * @see #getRemarks()
	 * @generated
	 */
	EReference getRemarks_Remark();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RemarkFactory getRemarkFactory();

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
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl <em>Remark</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl
		 * @see edu.tsinghua.lumaqq.ecore.remark.impl.RemarkPackageImpl#getRemark()
		 * @generated
		 */
		EClass REMARK = eINSTANCE.getRemark();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMARK__ADDRESS = eINSTANCE.getRemark_Address();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMARK__EMAIL = eINSTANCE.getRemark_Email();

		/**
		 * The meta object literal for the '<em><b>Mobile</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMARK__MOBILE = eINSTANCE.getRemark_Mobile();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMARK__NAME = eINSTANCE.getRemark_Name();

		/**
		 * The meta object literal for the '<em><b>Note</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMARK__NOTE = eINSTANCE.getRemark_Note();

		/**
		 * The meta object literal for the '<em><b>Qq</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMARK__QQ = eINSTANCE.getRemark_Qq();

		/**
		 * The meta object literal for the '<em><b>Telephone</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMARK__TELEPHONE = eINSTANCE.getRemark_Telephone();

		/**
		 * The meta object literal for the '<em><b>Zipcode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMARK__ZIPCODE = eINSTANCE.getRemark_Zipcode();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarksImpl <em>Remarks</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.remark.impl.RemarksImpl
		 * @see edu.tsinghua.lumaqq.ecore.remark.impl.RemarkPackageImpl#getRemarks()
		 * @generated
		 */
		EClass REMARKS = eINSTANCE.getRemarks();

		/**
		 * The meta object literal for the '<em><b>Remark</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMARKS__REMARK = eINSTANCE.getRemarks_Remark();

	}

} //RemarkPackage
