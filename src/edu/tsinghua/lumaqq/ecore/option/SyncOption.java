/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.tsinghua.lumaqq.ecore.option;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sync Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoCheckUpdate <em>Auto Check Update</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadFriendRemark <em>Auto Download Friend Remark</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadGroup <em>Auto Download Group</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#getAutoUploadGroup <em>Auto Upload Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getSyncOption()
 * @model
 * @generated
 */
public interface SyncOption extends EObject {
	/**
	 * Returns the value of the '<em><b>Auto Check Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Check Update</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Check Update</em>' attribute.
	 * @see #isSetAutoCheckUpdate()
	 * @see #unsetAutoCheckUpdate()
	 * @see #setAutoCheckUpdate(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getSyncOption_AutoCheckUpdate()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isAutoCheckUpdate();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoCheckUpdate <em>Auto Check Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Check Update</em>' attribute.
	 * @see #isSetAutoCheckUpdate()
	 * @see #unsetAutoCheckUpdate()
	 * @see #isAutoCheckUpdate()
	 * @generated
	 */
	void setAutoCheckUpdate(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoCheckUpdate <em>Auto Check Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAutoCheckUpdate()
	 * @see #isAutoCheckUpdate()
	 * @see #setAutoCheckUpdate(boolean)
	 * @generated
	 */
	void unsetAutoCheckUpdate();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoCheckUpdate <em>Auto Check Update</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Auto Check Update</em>' attribute is set.
	 * @see #unsetAutoCheckUpdate()
	 * @see #isAutoCheckUpdate()
	 * @see #setAutoCheckUpdate(boolean)
	 * @generated
	 */
	boolean isSetAutoCheckUpdate();

	/**
	 * Returns the value of the '<em><b>Auto Download Friend Remark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Download Friend Remark</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Download Friend Remark</em>' attribute.
	 * @see #isSetAutoDownloadFriendRemark()
	 * @see #unsetAutoDownloadFriendRemark()
	 * @see #setAutoDownloadFriendRemark(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getSyncOption_AutoDownloadFriendRemark()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isAutoDownloadFriendRemark();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadFriendRemark <em>Auto Download Friend Remark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Download Friend Remark</em>' attribute.
	 * @see #isSetAutoDownloadFriendRemark()
	 * @see #unsetAutoDownloadFriendRemark()
	 * @see #isAutoDownloadFriendRemark()
	 * @generated
	 */
	void setAutoDownloadFriendRemark(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadFriendRemark <em>Auto Download Friend Remark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAutoDownloadFriendRemark()
	 * @see #isAutoDownloadFriendRemark()
	 * @see #setAutoDownloadFriendRemark(boolean)
	 * @generated
	 */
	void unsetAutoDownloadFriendRemark();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadFriendRemark <em>Auto Download Friend Remark</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Auto Download Friend Remark</em>' attribute is set.
	 * @see #unsetAutoDownloadFriendRemark()
	 * @see #isAutoDownloadFriendRemark()
	 * @see #setAutoDownloadFriendRemark(boolean)
	 * @generated
	 */
	boolean isSetAutoDownloadFriendRemark();

	/**
	 * Returns the value of the '<em><b>Auto Download Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Download Group</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Download Group</em>' attribute.
	 * @see #isSetAutoDownloadGroup()
	 * @see #unsetAutoDownloadGroup()
	 * @see #setAutoDownloadGroup(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getSyncOption_AutoDownloadGroup()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isAutoDownloadGroup();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadGroup <em>Auto Download Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Download Group</em>' attribute.
	 * @see #isSetAutoDownloadGroup()
	 * @see #unsetAutoDownloadGroup()
	 * @see #isAutoDownloadGroup()
	 * @generated
	 */
	void setAutoDownloadGroup(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadGroup <em>Auto Download Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAutoDownloadGroup()
	 * @see #isAutoDownloadGroup()
	 * @see #setAutoDownloadGroup(boolean)
	 * @generated
	 */
	void unsetAutoDownloadGroup();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#isAutoDownloadGroup <em>Auto Download Group</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Auto Download Group</em>' attribute is set.
	 * @see #unsetAutoDownloadGroup()
	 * @see #isAutoDownloadGroup()
	 * @see #setAutoDownloadGroup(boolean)
	 * @generated
	 */
	boolean isSetAutoDownloadGroup();

	/**
	 * Returns the value of the '<em><b>Auto Upload Group</b></em>' attribute.
	 * The default value is <code>"Always"</code>.
	 * The literals are from the enumeration {@link edu.tsinghua.lumaqq.ecore.option.OpType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Upload Group</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Upload Group</em>' attribute.
	 * @see edu.tsinghua.lumaqq.ecore.option.OpType
	 * @see #isSetAutoUploadGroup()
	 * @see #unsetAutoUploadGroup()
	 * @see #setAutoUploadGroup(OpType)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getSyncOption_AutoUploadGroup()
	 * @model default="Always" unique="false" unsettable="true" required="true"
	 * @generated
	 */
	OpType getAutoUploadGroup();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#getAutoUploadGroup <em>Auto Upload Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Upload Group</em>' attribute.
	 * @see edu.tsinghua.lumaqq.ecore.option.OpType
	 * @see #isSetAutoUploadGroup()
	 * @see #unsetAutoUploadGroup()
	 * @see #getAutoUploadGroup()
	 * @generated
	 */
	void setAutoUploadGroup(OpType value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#getAutoUploadGroup <em>Auto Upload Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAutoUploadGroup()
	 * @see #getAutoUploadGroup()
	 * @see #setAutoUploadGroup(OpType)
	 * @generated
	 */
	void unsetAutoUploadGroup();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.SyncOption#getAutoUploadGroup <em>Auto Upload Group</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Auto Upload Group</em>' attribute is set.
	 * @see #unsetAutoUploadGroup()
	 * @see #getAutoUploadGroup()
	 * @see #setAutoUploadGroup(OpType)
	 * @generated
	 */
	boolean isSetAutoUploadGroup();

} // SyncOption
