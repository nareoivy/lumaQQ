/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.reply.impl;

import edu.tsinghua.lumaqq.ecore.reply.Replies;
import edu.tsinghua.lumaqq.ecore.reply.ReplyPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replies</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.reply.impl.RepliesImpl#getQuickReply <em>Quick Reply</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.reply.impl.RepliesImpl#getAutoReply <em>Auto Reply</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.reply.impl.RepliesImpl#getCurrentAutoReply <em>Current Auto Reply</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.reply.impl.RepliesImpl#getCurrentQuickReply <em>Current Quick Reply</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RepliesImpl extends EObjectImpl implements Replies {
	/**
	 * The cached value of the '{@link #getQuickReply() <em>Quick Reply</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuickReply()
	 * @generated
	 * @ordered
	 */
	protected EList quickReply = null;

	/**
	 * The cached value of the '{@link #getAutoReply() <em>Auto Reply</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutoReply()
	 * @generated
	 * @ordered
	 */
	protected EList autoReply = null;

	/**
	 * The default value of the '{@link #getCurrentAutoReply() <em>Current Auto Reply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentAutoReply()
	 * @generated
	 * @ordered
	 */
	protected static final int CURRENT_AUTO_REPLY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCurrentAutoReply() <em>Current Auto Reply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentAutoReply()
	 * @generated
	 * @ordered
	 */
	protected int currentAutoReply = CURRENT_AUTO_REPLY_EDEFAULT;

	/**
	 * This is true if the Current Auto Reply attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean currentAutoReplyESet = false;

	/**
	 * The default value of the '{@link #getCurrentQuickReply() <em>Current Quick Reply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentQuickReply()
	 * @generated
	 * @ordered
	 */
	protected static final int CURRENT_QUICK_REPLY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCurrentQuickReply() <em>Current Quick Reply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentQuickReply()
	 * @generated
	 * @ordered
	 */
	protected int currentQuickReply = CURRENT_QUICK_REPLY_EDEFAULT;

	/**
	 * This is true if the Current Quick Reply attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean currentQuickReplyESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepliesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReplyPackage.Literals.REPLIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getQuickReply() {
		if (quickReply == null) {
			quickReply = new EDataTypeEList(String.class, this, ReplyPackage.REPLIES__QUICK_REPLY);
		}
		return quickReply;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAutoReply() {
		if (autoReply == null) {
			autoReply = new EDataTypeEList(String.class, this, ReplyPackage.REPLIES__AUTO_REPLY);
		}
		return autoReply;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCurrentAutoReply() {
		return currentAutoReply;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentAutoReply(int newCurrentAutoReply) {
		int oldCurrentAutoReply = currentAutoReply;
		currentAutoReply = newCurrentAutoReply;
		boolean oldCurrentAutoReplyESet = currentAutoReplyESet;
		currentAutoReplyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReplyPackage.REPLIES__CURRENT_AUTO_REPLY, oldCurrentAutoReply, currentAutoReply, !oldCurrentAutoReplyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCurrentAutoReply() {
		int oldCurrentAutoReply = currentAutoReply;
		boolean oldCurrentAutoReplyESet = currentAutoReplyESet;
		currentAutoReply = CURRENT_AUTO_REPLY_EDEFAULT;
		currentAutoReplyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ReplyPackage.REPLIES__CURRENT_AUTO_REPLY, oldCurrentAutoReply, CURRENT_AUTO_REPLY_EDEFAULT, oldCurrentAutoReplyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCurrentAutoReply() {
		return currentAutoReplyESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCurrentQuickReply() {
		return currentQuickReply;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentQuickReply(int newCurrentQuickReply) {
		int oldCurrentQuickReply = currentQuickReply;
		currentQuickReply = newCurrentQuickReply;
		boolean oldCurrentQuickReplyESet = currentQuickReplyESet;
		currentQuickReplyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReplyPackage.REPLIES__CURRENT_QUICK_REPLY, oldCurrentQuickReply, currentQuickReply, !oldCurrentQuickReplyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCurrentQuickReply() {
		int oldCurrentQuickReply = currentQuickReply;
		boolean oldCurrentQuickReplyESet = currentQuickReplyESet;
		currentQuickReply = CURRENT_QUICK_REPLY_EDEFAULT;
		currentQuickReplyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ReplyPackage.REPLIES__CURRENT_QUICK_REPLY, oldCurrentQuickReply, CURRENT_QUICK_REPLY_EDEFAULT, oldCurrentQuickReplyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCurrentQuickReply() {
		return currentQuickReplyESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReplyPackage.REPLIES__QUICK_REPLY:
				return getQuickReply();
			case ReplyPackage.REPLIES__AUTO_REPLY:
				return getAutoReply();
			case ReplyPackage.REPLIES__CURRENT_AUTO_REPLY:
				return new Integer(getCurrentAutoReply());
			case ReplyPackage.REPLIES__CURRENT_QUICK_REPLY:
				return new Integer(getCurrentQuickReply());
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ReplyPackage.REPLIES__QUICK_REPLY:
				getQuickReply().clear();
				getQuickReply().addAll((Collection)newValue);
				return;
			case ReplyPackage.REPLIES__AUTO_REPLY:
				getAutoReply().clear();
				getAutoReply().addAll((Collection)newValue);
				return;
			case ReplyPackage.REPLIES__CURRENT_AUTO_REPLY:
				setCurrentAutoReply(((Integer)newValue).intValue());
				return;
			case ReplyPackage.REPLIES__CURRENT_QUICK_REPLY:
				setCurrentQuickReply(((Integer)newValue).intValue());
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ReplyPackage.REPLIES__QUICK_REPLY:
				getQuickReply().clear();
				return;
			case ReplyPackage.REPLIES__AUTO_REPLY:
				getAutoReply().clear();
				return;
			case ReplyPackage.REPLIES__CURRENT_AUTO_REPLY:
				unsetCurrentAutoReply();
				return;
			case ReplyPackage.REPLIES__CURRENT_QUICK_REPLY:
				unsetCurrentQuickReply();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ReplyPackage.REPLIES__QUICK_REPLY:
				return quickReply != null && !quickReply.isEmpty();
			case ReplyPackage.REPLIES__AUTO_REPLY:
				return autoReply != null && !autoReply.isEmpty();
			case ReplyPackage.REPLIES__CURRENT_AUTO_REPLY:
				return isSetCurrentAutoReply();
			case ReplyPackage.REPLIES__CURRENT_QUICK_REPLY:
				return isSetCurrentQuickReply();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (quickReply: ");
		result.append(quickReply);
		result.append(", autoReply: ");
		result.append(autoReply);
		result.append(", currentAutoReply: ");
		if (currentAutoReplyESet) result.append(currentAutoReply); else result.append("<unset>");
		result.append(", currentQuickReply: ");
		if (currentQuickReplyESet) result.append(currentQuickReply); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //RepliesImpl
