/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.reply;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

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
 * @see edu.tsinghua.lumaqq.ecore.reply.ReplyFactory
 * @model kind="package"
 * @generated
 */
public interface ReplyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "reply";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://lumaqq.tsinghua.edu/ecore/edu/tsinghua/lumaqq/ecore/reply";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "reply";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ReplyPackage eINSTANCE = edu.tsinghua.lumaqq.ecore.reply.impl.ReplyPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.tsinghua.lumaqq.ecore.reply.impl.RepliesImpl <em>Replies</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.tsinghua.lumaqq.ecore.reply.impl.RepliesImpl
	 * @see edu.tsinghua.lumaqq.ecore.reply.impl.ReplyPackageImpl#getReplies()
	 * @generated
	 */
	int REPLIES = 0;

	/**
	 * The feature id for the '<em><b>Quick Reply</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLIES__QUICK_REPLY = 0;

	/**
	 * The feature id for the '<em><b>Auto Reply</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLIES__AUTO_REPLY = 1;

	/**
	 * The feature id for the '<em><b>Current Auto Reply</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLIES__CURRENT_AUTO_REPLY = 2;

	/**
	 * The feature id for the '<em><b>Current Quick Reply</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLIES__CURRENT_QUICK_REPLY = 3;

	/**
	 * The number of structural features of the '<em>Replies</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLIES_FEATURE_COUNT = 4;


	/**
	 * Returns the meta object for class '{@link edu.tsinghua.lumaqq.ecore.reply.Replies <em>Replies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Replies</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.reply.Replies
	 * @generated
	 */
	EClass getReplies();

	/**
	 * Returns the meta object for the attribute list '{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getQuickReply <em>Quick Reply</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Quick Reply</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.reply.Replies#getQuickReply()
	 * @see #getReplies()
	 * @generated
	 */
	EAttribute getReplies_QuickReply();

	/**
	 * Returns the meta object for the attribute list '{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getAutoReply <em>Auto Reply</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Auto Reply</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.reply.Replies#getAutoReply()
	 * @see #getReplies()
	 * @generated
	 */
	EAttribute getReplies_AutoReply();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentAutoReply <em>Current Auto Reply</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Current Auto Reply</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentAutoReply()
	 * @see #getReplies()
	 * @generated
	 */
	EAttribute getReplies_CurrentAutoReply();

	/**
	 * Returns the meta object for the attribute '{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentQuickReply <em>Current Quick Reply</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Current Quick Reply</em>'.
	 * @see edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentQuickReply()
	 * @see #getReplies()
	 * @generated
	 */
	EAttribute getReplies_CurrentQuickReply();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ReplyFactory getReplyFactory();

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
		 * The meta object literal for the '{@link edu.tsinghua.lumaqq.ecore.reply.impl.RepliesImpl <em>Replies</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.tsinghua.lumaqq.ecore.reply.impl.RepliesImpl
		 * @see edu.tsinghua.lumaqq.ecore.reply.impl.ReplyPackageImpl#getReplies()
		 * @generated
		 */
		EClass REPLIES = eINSTANCE.getReplies();

		/**
		 * The meta object literal for the '<em><b>Quick Reply</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPLIES__QUICK_REPLY = eINSTANCE.getReplies_QuickReply();

		/**
		 * The meta object literal for the '<em><b>Auto Reply</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPLIES__AUTO_REPLY = eINSTANCE.getReplies_AutoReply();

		/**
		 * The meta object literal for the '<em><b>Current Auto Reply</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPLIES__CURRENT_AUTO_REPLY = eINSTANCE.getReplies_CurrentAutoReply();

		/**
		 * The meta object literal for the '<em><b>Current Quick Reply</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPLIES__CURRENT_QUICK_REPLY = eINSTANCE.getReplies_CurrentQuickReply();

	}

} //ReplyPackage
