/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.reply;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replies</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getQuickReply <em>Quick Reply</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getAutoReply <em>Auto Reply</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentAutoReply <em>Current Auto Reply</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentQuickReply <em>Current Quick Reply</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.reply.ReplyPackage#getReplies()
 * @model
 * @generated
 */
public interface Replies extends EObject {
	/**
	 * Returns the value of the '<em><b>Quick Reply</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Quick Reply</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Quick Reply</em>' attribute list.
	 * @see edu.tsinghua.lumaqq.ecore.reply.ReplyPackage#getReplies_QuickReply()
	 * @model type="java.lang.String" unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	EList getQuickReply();

	/**
	 * Returns the value of the '<em><b>Auto Reply</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Reply</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Reply</em>' attribute list.
	 * @see edu.tsinghua.lumaqq.ecore.reply.ReplyPackage#getReplies_AutoReply()
	 * @model type="java.lang.String" unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	EList getAutoReply();

	/**
	 * Returns the value of the '<em><b>Current Auto Reply</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Auto Reply</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Auto Reply</em>' attribute.
	 * @see #isSetCurrentAutoReply()
	 * @see #unsetCurrentAutoReply()
	 * @see #setCurrentAutoReply(int)
	 * @see edu.tsinghua.lumaqq.ecore.reply.ReplyPackage#getReplies_CurrentAutoReply()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getCurrentAutoReply();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentAutoReply <em>Current Auto Reply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Auto Reply</em>' attribute.
	 * @see #isSetCurrentAutoReply()
	 * @see #unsetCurrentAutoReply()
	 * @see #getCurrentAutoReply()
	 * @generated
	 */
	void setCurrentAutoReply(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentAutoReply <em>Current Auto Reply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCurrentAutoReply()
	 * @see #getCurrentAutoReply()
	 * @see #setCurrentAutoReply(int)
	 * @generated
	 */
	void unsetCurrentAutoReply();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentAutoReply <em>Current Auto Reply</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Current Auto Reply</em>' attribute is set.
	 * @see #unsetCurrentAutoReply()
	 * @see #getCurrentAutoReply()
	 * @see #setCurrentAutoReply(int)
	 * @generated
	 */
	boolean isSetCurrentAutoReply();

	/**
	 * Returns the value of the '<em><b>Current Quick Reply</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Quick Reply</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Quick Reply</em>' attribute.
	 * @see #isSetCurrentQuickReply()
	 * @see #unsetCurrentQuickReply()
	 * @see #setCurrentQuickReply(int)
	 * @see edu.tsinghua.lumaqq.ecore.reply.ReplyPackage#getReplies_CurrentQuickReply()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getCurrentQuickReply();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentQuickReply <em>Current Quick Reply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Quick Reply</em>' attribute.
	 * @see #isSetCurrentQuickReply()
	 * @see #unsetCurrentQuickReply()
	 * @see #getCurrentQuickReply()
	 * @generated
	 */
	void setCurrentQuickReply(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentQuickReply <em>Current Quick Reply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCurrentQuickReply()
	 * @see #getCurrentQuickReply()
	 * @see #setCurrentQuickReply(int)
	 * @generated
	 */
	void unsetCurrentQuickReply();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.reply.Replies#getCurrentQuickReply <em>Current Quick Reply</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Current Quick Reply</em>' attribute is set.
	 * @see #unsetCurrentQuickReply()
	 * @see #getCurrentQuickReply()
	 * @see #setCurrentQuickReply(int)
	 * @generated
	 */
	boolean isSetCurrentQuickReply();

} // Replies
