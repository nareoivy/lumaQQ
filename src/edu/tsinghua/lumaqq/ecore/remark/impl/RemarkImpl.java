/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.remark.impl;

import edu.tsinghua.lumaqq.ecore.remark.Remark;
import edu.tsinghua.lumaqq.ecore.remark.RemarkPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remark</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl#getMobile <em>Mobile</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl#getNote <em>Note</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl#getQq <em>Qq</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl#getTelephone <em>Telephone</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.remark.impl.RemarkImpl#getZipcode <em>Zipcode</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RemarkImpl extends EObjectImpl implements Remark {
	/**
	 * The default value of the '{@link #getAddress() <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected static final String ADDRESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAddress() <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected String address = ADDRESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected String email = EMAIL_EDEFAULT;

	/**
	 * The default value of the '{@link #getMobile() <em>Mobile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMobile()
	 * @generated
	 * @ordered
	 */
	protected static final String MOBILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMobile() <em>Mobile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMobile()
	 * @generated
	 * @ordered
	 */
	protected String mobile = MOBILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getNote() <em>Note</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNote()
	 * @generated
	 * @ordered
	 */
	protected static final String NOTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNote() <em>Note</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNote()
	 * @generated
	 * @ordered
	 */
	protected String note = NOTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getQq() <em>Qq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQq()
	 * @generated
	 * @ordered
	 */
	protected static final int QQ_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getQq() <em>Qq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQq()
	 * @generated
	 * @ordered
	 */
	protected int qq = QQ_EDEFAULT;

	/**
	 * This is true if the Qq attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean qqESet = false;

	/**
	 * The default value of the '{@link #getTelephone() <em>Telephone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTelephone()
	 * @generated
	 * @ordered
	 */
	protected static final String TELEPHONE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTelephone() <em>Telephone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTelephone()
	 * @generated
	 * @ordered
	 */
	protected String telephone = TELEPHONE_EDEFAULT;

	/**
	 * The default value of the '{@link #getZipcode() <em>Zipcode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZipcode()
	 * @generated
	 * @ordered
	 */
	protected static final String ZIPCODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getZipcode() <em>Zipcode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZipcode()
	 * @generated
	 * @ordered
	 */
	protected String zipcode = ZIPCODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemarkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RemarkPackage.Literals.REMARK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddress(String newAddress) {
		String oldAddress = address;
		address = newAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RemarkPackage.REMARK__ADDRESS, oldAddress, address));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmail(String newEmail) {
		String oldEmail = email;
		email = newEmail;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RemarkPackage.REMARK__EMAIL, oldEmail, email));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMobile(String newMobile) {
		String oldMobile = mobile;
		mobile = newMobile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RemarkPackage.REMARK__MOBILE, oldMobile, mobile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RemarkPackage.REMARK__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNote() {
		return note;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNote(String newNote) {
		String oldNote = note;
		note = newNote;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RemarkPackage.REMARK__NOTE, oldNote, note));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getQq() {
		return qq;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQq(int newQq) {
		int oldQq = qq;
		qq = newQq;
		boolean oldQqESet = qqESet;
		qqESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RemarkPackage.REMARK__QQ, oldQq, qq, !oldQqESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQq() {
		int oldQq = qq;
		boolean oldQqESet = qqESet;
		qq = QQ_EDEFAULT;
		qqESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, RemarkPackage.REMARK__QQ, oldQq, QQ_EDEFAULT, oldQqESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQq() {
		return qqESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTelephone(String newTelephone) {
		String oldTelephone = telephone;
		telephone = newTelephone;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RemarkPackage.REMARK__TELEPHONE, oldTelephone, telephone));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setZipcode(String newZipcode) {
		String oldZipcode = zipcode;
		zipcode = newZipcode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RemarkPackage.REMARK__ZIPCODE, oldZipcode, zipcode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RemarkPackage.REMARK__ADDRESS:
				return getAddress();
			case RemarkPackage.REMARK__EMAIL:
				return getEmail();
			case RemarkPackage.REMARK__MOBILE:
				return getMobile();
			case RemarkPackage.REMARK__NAME:
				return getName();
			case RemarkPackage.REMARK__NOTE:
				return getNote();
			case RemarkPackage.REMARK__QQ:
				return new Integer(getQq());
			case RemarkPackage.REMARK__TELEPHONE:
				return getTelephone();
			case RemarkPackage.REMARK__ZIPCODE:
				return getZipcode();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RemarkPackage.REMARK__ADDRESS:
				setAddress((String)newValue);
				return;
			case RemarkPackage.REMARK__EMAIL:
				setEmail((String)newValue);
				return;
			case RemarkPackage.REMARK__MOBILE:
				setMobile((String)newValue);
				return;
			case RemarkPackage.REMARK__NAME:
				setName((String)newValue);
				return;
			case RemarkPackage.REMARK__NOTE:
				setNote((String)newValue);
				return;
			case RemarkPackage.REMARK__QQ:
				setQq(((Integer)newValue).intValue());
				return;
			case RemarkPackage.REMARK__TELEPHONE:
				setTelephone((String)newValue);
				return;
			case RemarkPackage.REMARK__ZIPCODE:
				setZipcode((String)newValue);
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
			case RemarkPackage.REMARK__ADDRESS:
				setAddress(ADDRESS_EDEFAULT);
				return;
			case RemarkPackage.REMARK__EMAIL:
				setEmail(EMAIL_EDEFAULT);
				return;
			case RemarkPackage.REMARK__MOBILE:
				setMobile(MOBILE_EDEFAULT);
				return;
			case RemarkPackage.REMARK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RemarkPackage.REMARK__NOTE:
				setNote(NOTE_EDEFAULT);
				return;
			case RemarkPackage.REMARK__QQ:
				unsetQq();
				return;
			case RemarkPackage.REMARK__TELEPHONE:
				setTelephone(TELEPHONE_EDEFAULT);
				return;
			case RemarkPackage.REMARK__ZIPCODE:
				setZipcode(ZIPCODE_EDEFAULT);
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
			case RemarkPackage.REMARK__ADDRESS:
				return ADDRESS_EDEFAULT == null ? address != null : !ADDRESS_EDEFAULT.equals(address);
			case RemarkPackage.REMARK__EMAIL:
				return EMAIL_EDEFAULT == null ? email != null : !EMAIL_EDEFAULT.equals(email);
			case RemarkPackage.REMARK__MOBILE:
				return MOBILE_EDEFAULT == null ? mobile != null : !MOBILE_EDEFAULT.equals(mobile);
			case RemarkPackage.REMARK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RemarkPackage.REMARK__NOTE:
				return NOTE_EDEFAULT == null ? note != null : !NOTE_EDEFAULT.equals(note);
			case RemarkPackage.REMARK__QQ:
				return isSetQq();
			case RemarkPackage.REMARK__TELEPHONE:
				return TELEPHONE_EDEFAULT == null ? telephone != null : !TELEPHONE_EDEFAULT.equals(telephone);
			case RemarkPackage.REMARK__ZIPCODE:
				return ZIPCODE_EDEFAULT == null ? zipcode != null : !ZIPCODE_EDEFAULT.equals(zipcode);
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
		result.append(" (address: ");
		result.append(address);
		result.append(", email: ");
		result.append(email);
		result.append(", mobile: ");
		result.append(mobile);
		result.append(", name: ");
		result.append(name);
		result.append(", note: ");
		result.append(note);
		result.append(", qq: ");
		if (qqESet) result.append(qq); else result.append("<unset>");
		result.append(", telephone: ");
		result.append(telephone);
		result.append(", zipcode: ");
		result.append(zipcode);
		result.append(')');
		return result.toString();
	}

} //RemarkImpl
