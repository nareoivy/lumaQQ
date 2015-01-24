/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option.impl;

import edu.tsinghua.lumaqq.ecore.option.OptionPackage;
import edu.tsinghua.lumaqq.ecore.option.OtherOption;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Other Option</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OtherOptionImpl#getBrowser <em>Browser</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OtherOptionImpl#isEnableLatest <em>Enable Latest</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OtherOptionImpl#isKeepStrangerInLatest <em>Keep Stranger In Latest</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OtherOptionImpl#getLatestSize <em>Latest Size</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.impl.OtherOptionImpl#isShowFakeCam <em>Show Fake Cam</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OtherOptionImpl extends EObjectImpl implements OtherOption {
	/**
	 * The default value of the '{@link #getBrowser() <em>Browser</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrowser()
	 * @generated
	 * @ordered
	 */
	protected static final String BROWSER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBrowser() <em>Browser</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrowser()
	 * @generated
	 * @ordered
	 */
	protected String browser = BROWSER_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnableLatest() <em>Enable Latest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnableLatest()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLE_LATEST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEnableLatest() <em>Enable Latest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnableLatest()
	 * @generated
	 * @ordered
	 */
	protected boolean enableLatest = ENABLE_LATEST_EDEFAULT;

	/**
	 * This is true if the Enable Latest attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean enableLatestESet = false;

	/**
	 * The default value of the '{@link #isKeepStrangerInLatest() <em>Keep Stranger In Latest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isKeepStrangerInLatest()
	 * @generated
	 * @ordered
	 */
	protected static final boolean KEEP_STRANGER_IN_LATEST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isKeepStrangerInLatest() <em>Keep Stranger In Latest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isKeepStrangerInLatest()
	 * @generated
	 * @ordered
	 */
	protected boolean keepStrangerInLatest = KEEP_STRANGER_IN_LATEST_EDEFAULT;

	/**
	 * This is true if the Keep Stranger In Latest attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean keepStrangerInLatestESet = false;

	/**
	 * The default value of the '{@link #getLatestSize() <em>Latest Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLatestSize()
	 * @generated
	 * @ordered
	 */
	protected static final int LATEST_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLatestSize() <em>Latest Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLatestSize()
	 * @generated
	 * @ordered
	 */
	protected int latestSize = LATEST_SIZE_EDEFAULT;

	/**
	 * This is true if the Latest Size attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean latestSizeESet = false;

	/**
	 * The default value of the '{@link #isShowFakeCam() <em>Show Fake Cam</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowFakeCam()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_FAKE_CAM_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowFakeCam() <em>Show Fake Cam</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowFakeCam()
	 * @generated
	 * @ordered
	 */
	protected boolean showFakeCam = SHOW_FAKE_CAM_EDEFAULT;

	/**
	 * This is true if the Show Fake Cam attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showFakeCamESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OtherOptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OptionPackage.Literals.OTHER_OPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBrowser(String newBrowser) {
		String oldBrowser = browser;
		browser = newBrowser;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OTHER_OPTION__BROWSER, oldBrowser, browser));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnableLatest() {
		return enableLatest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableLatest(boolean newEnableLatest) {
		boolean oldEnableLatest = enableLatest;
		enableLatest = newEnableLatest;
		boolean oldEnableLatestESet = enableLatestESet;
		enableLatestESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OTHER_OPTION__ENABLE_LATEST, oldEnableLatest, enableLatest, !oldEnableLatestESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetEnableLatest() {
		boolean oldEnableLatest = enableLatest;
		boolean oldEnableLatestESet = enableLatestESet;
		enableLatest = ENABLE_LATEST_EDEFAULT;
		enableLatestESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.OTHER_OPTION__ENABLE_LATEST, oldEnableLatest, ENABLE_LATEST_EDEFAULT, oldEnableLatestESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetEnableLatest() {
		return enableLatestESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isKeepStrangerInLatest() {
		return keepStrangerInLatest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeepStrangerInLatest(boolean newKeepStrangerInLatest) {
		boolean oldKeepStrangerInLatest = keepStrangerInLatest;
		keepStrangerInLatest = newKeepStrangerInLatest;
		boolean oldKeepStrangerInLatestESet = keepStrangerInLatestESet;
		keepStrangerInLatestESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OTHER_OPTION__KEEP_STRANGER_IN_LATEST, oldKeepStrangerInLatest, keepStrangerInLatest, !oldKeepStrangerInLatestESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetKeepStrangerInLatest() {
		boolean oldKeepStrangerInLatest = keepStrangerInLatest;
		boolean oldKeepStrangerInLatestESet = keepStrangerInLatestESet;
		keepStrangerInLatest = KEEP_STRANGER_IN_LATEST_EDEFAULT;
		keepStrangerInLatestESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.OTHER_OPTION__KEEP_STRANGER_IN_LATEST, oldKeepStrangerInLatest, KEEP_STRANGER_IN_LATEST_EDEFAULT, oldKeepStrangerInLatestESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetKeepStrangerInLatest() {
		return keepStrangerInLatestESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLatestSize() {
		return latestSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLatestSize(int newLatestSize) {
		int oldLatestSize = latestSize;
		latestSize = newLatestSize;
		boolean oldLatestSizeESet = latestSizeESet;
		latestSizeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OTHER_OPTION__LATEST_SIZE, oldLatestSize, latestSize, !oldLatestSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLatestSize() {
		int oldLatestSize = latestSize;
		boolean oldLatestSizeESet = latestSizeESet;
		latestSize = LATEST_SIZE_EDEFAULT;
		latestSizeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.OTHER_OPTION__LATEST_SIZE, oldLatestSize, LATEST_SIZE_EDEFAULT, oldLatestSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLatestSize() {
		return latestSizeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowFakeCam() {
		return showFakeCam;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowFakeCam(boolean newShowFakeCam) {
		boolean oldShowFakeCam = showFakeCam;
		showFakeCam = newShowFakeCam;
		boolean oldShowFakeCamESet = showFakeCamESet;
		showFakeCamESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OptionPackage.OTHER_OPTION__SHOW_FAKE_CAM, oldShowFakeCam, showFakeCam, !oldShowFakeCamESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowFakeCam() {
		boolean oldShowFakeCam = showFakeCam;
		boolean oldShowFakeCamESet = showFakeCamESet;
		showFakeCam = SHOW_FAKE_CAM_EDEFAULT;
		showFakeCamESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OptionPackage.OTHER_OPTION__SHOW_FAKE_CAM, oldShowFakeCam, SHOW_FAKE_CAM_EDEFAULT, oldShowFakeCamESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowFakeCam() {
		return showFakeCamESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OptionPackage.OTHER_OPTION__BROWSER:
				return getBrowser();
			case OptionPackage.OTHER_OPTION__ENABLE_LATEST:
				return isEnableLatest() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.OTHER_OPTION__KEEP_STRANGER_IN_LATEST:
				return isKeepStrangerInLatest() ? Boolean.TRUE : Boolean.FALSE;
			case OptionPackage.OTHER_OPTION__LATEST_SIZE:
				return new Integer(getLatestSize());
			case OptionPackage.OTHER_OPTION__SHOW_FAKE_CAM:
				return isShowFakeCam() ? Boolean.TRUE : Boolean.FALSE;
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
			case OptionPackage.OTHER_OPTION__BROWSER:
				setBrowser((String)newValue);
				return;
			case OptionPackage.OTHER_OPTION__ENABLE_LATEST:
				setEnableLatest(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.OTHER_OPTION__KEEP_STRANGER_IN_LATEST:
				setKeepStrangerInLatest(((Boolean)newValue).booleanValue());
				return;
			case OptionPackage.OTHER_OPTION__LATEST_SIZE:
				setLatestSize(((Integer)newValue).intValue());
				return;
			case OptionPackage.OTHER_OPTION__SHOW_FAKE_CAM:
				setShowFakeCam(((Boolean)newValue).booleanValue());
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
			case OptionPackage.OTHER_OPTION__BROWSER:
				setBrowser(BROWSER_EDEFAULT);
				return;
			case OptionPackage.OTHER_OPTION__ENABLE_LATEST:
				unsetEnableLatest();
				return;
			case OptionPackage.OTHER_OPTION__KEEP_STRANGER_IN_LATEST:
				unsetKeepStrangerInLatest();
				return;
			case OptionPackage.OTHER_OPTION__LATEST_SIZE:
				unsetLatestSize();
				return;
			case OptionPackage.OTHER_OPTION__SHOW_FAKE_CAM:
				unsetShowFakeCam();
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
			case OptionPackage.OTHER_OPTION__BROWSER:
				return BROWSER_EDEFAULT == null ? browser != null : !BROWSER_EDEFAULT.equals(browser);
			case OptionPackage.OTHER_OPTION__ENABLE_LATEST:
				return isSetEnableLatest();
			case OptionPackage.OTHER_OPTION__KEEP_STRANGER_IN_LATEST:
				return isSetKeepStrangerInLatest();
			case OptionPackage.OTHER_OPTION__LATEST_SIZE:
				return isSetLatestSize();
			case OptionPackage.OTHER_OPTION__SHOW_FAKE_CAM:
				return isSetShowFakeCam();
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
		result.append(" (browser: ");
		result.append(browser);
		result.append(", enableLatest: ");
		if (enableLatestESet) result.append(enableLatest); else result.append("<unset>");
		result.append(", keepStrangerInLatest: ");
		if (keepStrangerInLatestESet) result.append(keepStrangerInLatest); else result.append("<unset>");
		result.append(", latestSize: ");
		if (latestSizeESet) result.append(latestSize); else result.append("<unset>");
		result.append(", showFakeCam: ");
		if (showFakeCamESet) result.append(showFakeCam); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //OtherOptionImpl
