/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.face;

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
 * @see edu.tsinghua.lumaqq.ecore.face.FaceFactory
 * @model kind="package"
 * @generated
 */
public interface FacePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "face";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore/face";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "face";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FacePackage eINSTANCE = edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.face.impl.FaceImpl <em>Face</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.face.impl.FaceImpl
	 * @see edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl#getFace()
	 * @generated
	 */
	int FACE = 0;

	/**
	 * The feature id for the '<em><b>Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACE__FILENAME = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACE__ID = 1;

	/**
	 * The feature id for the '<em><b>Md5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACE__MD5 = 2;

	/**
	 * The number of structural features of the '<em>Face</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.face.impl.FaceGroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.face.impl.FaceGroupImpl
	 * @see edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl#getFaceGroup()
	 * @generated
	 */
	int FACE_GROUP = 1;

	/**
	 * The feature id for the '<em><b>Face</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACE_GROUP__FACE = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACE_GROUP__ID = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACE_GROUP__NAME = 2;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACE_GROUP_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.face.impl.FacesImpl <em>Faces</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.face.impl.FacesImpl
	 * @see edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl#getFaces()
	 * @generated
	 */
	int FACES = 2;

	/**
	 * The feature id for the '<em><b>Group</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACES__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Next Group Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACES__NEXT_GROUP_ID = 1;

	/**
	 * The feature id for the '<em><b>Next Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACES__NEXT_ID = 2;

	/**
	 * The number of structural features of the '<em>Faces</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACES_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.face.FaceConstant <em>Constant</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.face.FaceConstant
	 * @see edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl#getFaceConstant()
	 * @generated
	 */
	int FACE_CONSTANT = 3;


	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.face.Face <em>Face</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Face</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.Face
	 * @generated
	 */
	EClass getFace();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.face.Face#getFilename <em>Filename</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Filename</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.Face#getFilename()
	 * @see #getFace()
	 * @generated
	 */
	EAttribute getFace_Filename();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.face.Face#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.Face#getId()
	 * @see #getFace()
	 * @generated
	 */
	EAttribute getFace_Id();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.face.Face#getMd5 <em>Md5</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Md5</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.Face#getMd5()
	 * @see #getFace()
	 * @generated
	 */
	EAttribute getFace_Md5();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.FaceGroup
	 * @generated
	 */
	EClass getFaceGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup#getFace <em>Face</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Face</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.FaceGroup#getFace()
	 * @see #getFaceGroup()
	 * @generated
	 */
	EReference getFaceGroup_Face();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.FaceGroup#getId()
	 * @see #getFaceGroup()
	 * @generated
	 */
	EAttribute getFaceGroup_Id();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.face.FaceGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.FaceGroup#getName()
	 * @see #getFaceGroup()
	 * @generated
	 */
	EAttribute getFaceGroup_Name();

	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.face.Faces <em>Faces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Faces</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.Faces
	 * @generated
	 */
	EClass getFaces();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.tsinghua.lumaqq.ecore.face.Faces#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Group</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.Faces#getGroup()
	 * @see #getFaces()
	 * @generated
	 */
	EReference getFaces_Group();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.face.Faces#getNextGroupId <em>Next Group Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Next Group Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.Faces#getNextGroupId()
	 * @see #getFaces()
	 * @generated
	 */
	EAttribute getFaces_NextGroupId();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.face.Faces#getNextId <em>Next Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Next Id</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.Faces#getNextId()
	 * @see #getFaces()
	 * @generated
	 */
	EAttribute getFaces_NextId();

	/**
	 * Returns the meta object for enum '{@link edu.tsinghua.lumaqq.ecore.face.FaceConstant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Constant</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.face.FaceConstant
	 * @generated
	 */
	EEnum getFaceConstant();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FaceFactory getFaceFactory();

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
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.face.impl.FaceImpl <em>Face</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.face.impl.FaceImpl
		 * @see edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl#getFace()
		 * @generated
		 */
		EClass FACE = eINSTANCE.getFace();

		/**
		 * The meta object literal for the '<em><b>Filename</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FACE__FILENAME = eINSTANCE.getFace_Filename();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FACE__ID = eINSTANCE.getFace_Id();

		/**
		 * The meta object literal for the '<em><b>Md5</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FACE__MD5 = eINSTANCE.getFace_Md5();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.face.impl.FaceGroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.face.impl.FaceGroupImpl
		 * @see edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl#getFaceGroup()
		 * @generated
		 */
		EClass FACE_GROUP = eINSTANCE.getFaceGroup();

		/**
		 * The meta object literal for the '<em><b>Face</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FACE_GROUP__FACE = eINSTANCE.getFaceGroup_Face();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FACE_GROUP__ID = eINSTANCE.getFaceGroup_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FACE_GROUP__NAME = eINSTANCE.getFaceGroup_Name();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.face.impl.FacesImpl <em>Faces</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.face.impl.FacesImpl
		 * @see edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl#getFaces()
		 * @generated
		 */
		EClass FACES = eINSTANCE.getFaces();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FACES__GROUP = eINSTANCE.getFaces_Group();

		/**
		 * The meta object literal for the '<em><b>Next Group Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FACES__NEXT_GROUP_ID = eINSTANCE.getFaces_NextGroupId();

		/**
		 * The meta object literal for the '<em><b>Next Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FACES__NEXT_ID = eINSTANCE.getFaces_NextId();

		/**
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.face.FaceConstant <em>Constant</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.face.FaceConstant
		 * @see edu.tsinghua.lumaqq.ecore.face.impl.FacePackageImpl#getFaceConstant()
		 * @generated
		 */
		EEnum FACE_CONSTANT = eINSTANCE.getFaceConstant();

	}

} //FacePackage
