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
 * A representation of the model object '<em><b>GUI Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isAutoDock <em>Auto Dock</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isBold <em>Bold</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontColor <em>Font Color</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontName <em>Font Name</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getGroupBackground <em>Group Background</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getHeight <em>Height</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isHideWhenMinimize <em>Hide When Minimize</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isImOnTop <em>Im On Top</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isItalic <em>Italic</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationX <em>Location X</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationY <em>Location Y</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isMinimizeWhenClose <em>Minimize When Close</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationX <em>Online Tip Location X</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationY <em>Online Tip Location Y</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isOnTop <em>On Top</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowBlacklist <em>Show Blacklist</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowFriendTip <em>Show Friend Tip</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowNick <em>Show Nick</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineOnly <em>Show Online Only</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineTip <em>Show Online Tip</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowLastLoginTip <em>Show Last Login Tip</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSignature <em>Show Signature</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowCustomHead <em>Show Custom Head</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSmallHead <em>Show Small Head</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isTreeMode <em>Tree Mode</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isUseTabIMWindow <em>Use Tab IM Window</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isBarExpanded <em>Bar Expanded</em>}</li>
 *   <li>{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getWidth <em>Width</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption()
 * @model
 * @generated
 */
public interface GUIOption extends EObject {
	/**
	 * Returns the value of the '<em><b>Auto Dock</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Dock</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Dock</em>' attribute.
	 * @see #isSetAutoDock()
	 * @see #unsetAutoDock()
	 * @see #setAutoDock(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_AutoDock()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isAutoDock();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isAutoDock <em>Auto Dock</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Dock</em>' attribute.
	 * @see #isSetAutoDock()
	 * @see #unsetAutoDock()
	 * @see #isAutoDock()
	 * @generated
	 */
	void setAutoDock(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isAutoDock <em>Auto Dock</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAutoDock()
	 * @see #isAutoDock()
	 * @see #setAutoDock(boolean)
	 * @generated
	 */
	void unsetAutoDock();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isAutoDock <em>Auto Dock</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Auto Dock</em>' attribute is set.
	 * @see #unsetAutoDock()
	 * @see #isAutoDock()
	 * @see #setAutoDock(boolean)
	 * @generated
	 */
	boolean isSetAutoDock();

	/**
	 * Returns the value of the '<em><b>Bold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bold</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bold</em>' attribute.
	 * @see #isSetBold()
	 * @see #unsetBold()
	 * @see #setBold(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_Bold()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isBold();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isBold <em>Bold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bold</em>' attribute.
	 * @see #isSetBold()
	 * @see #unsetBold()
	 * @see #isBold()
	 * @generated
	 */
	void setBold(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isBold <em>Bold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetBold()
	 * @see #isBold()
	 * @see #setBold(boolean)
	 * @generated
	 */
	void unsetBold();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isBold <em>Bold</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Bold</em>' attribute is set.
	 * @see #unsetBold()
	 * @see #isBold()
	 * @see #setBold(boolean)
	 * @generated
	 */
	boolean isSetBold();

	/**
	 * Returns the value of the '<em><b>Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Color</em>' attribute.
	 * @see #isSetFontColor()
	 * @see #unsetFontColor()
	 * @see #setFontColor(int)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_FontColor()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getFontColor();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontColor <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Color</em>' attribute.
	 * @see #isSetFontColor()
	 * @see #unsetFontColor()
	 * @see #getFontColor()
	 * @generated
	 */
	void setFontColor(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontColor <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFontColor()
	 * @see #getFontColor()
	 * @see #setFontColor(int)
	 * @generated
	 */
	void unsetFontColor();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontColor <em>Font Color</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Font Color</em>' attribute is set.
	 * @see #unsetFontColor()
	 * @see #getFontColor()
	 * @see #setFontColor(int)
	 * @generated
	 */
	boolean isSetFontColor();

	/**
	 * Returns the value of the '<em><b>Font Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Name</em>' attribute.
	 * @see #setFontName(String)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_FontName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getFontName();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontName <em>Font Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Name</em>' attribute.
	 * @see #getFontName()
	 * @generated
	 */
	void setFontName(String value);

	/**
	 * Returns the value of the '<em><b>Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Size</em>' attribute.
	 * @see #isSetFontSize()
	 * @see #unsetFontSize()
	 * @see #setFontSize(int)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_FontSize()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getFontSize();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontSize <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Size</em>' attribute.
	 * @see #isSetFontSize()
	 * @see #unsetFontSize()
	 * @see #getFontSize()
	 * @generated
	 */
	void setFontSize(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontSize <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFontSize()
	 * @see #getFontSize()
	 * @see #setFontSize(int)
	 * @generated
	 */
	void unsetFontSize();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getFontSize <em>Font Size</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Font Size</em>' attribute is set.
	 * @see #unsetFontSize()
	 * @see #getFontSize()
	 * @see #setFontSize(int)
	 * @generated
	 */
	boolean isSetFontSize();

	/**
	 * Returns the value of the '<em><b>Group Background</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group Background</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group Background</em>' attribute.
	 * @see #isSetGroupBackground()
	 * @see #unsetGroupBackground()
	 * @see #setGroupBackground(int)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_GroupBackground()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getGroupBackground();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getGroupBackground <em>Group Background</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group Background</em>' attribute.
	 * @see #isSetGroupBackground()
	 * @see #unsetGroupBackground()
	 * @see #getGroupBackground()
	 * @generated
	 */
	void setGroupBackground(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getGroupBackground <em>Group Background</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetGroupBackground()
	 * @see #getGroupBackground()
	 * @see #setGroupBackground(int)
	 * @generated
	 */
	void unsetGroupBackground();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getGroupBackground <em>Group Background</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Group Background</em>' attribute is set.
	 * @see #unsetGroupBackground()
	 * @see #getGroupBackground()
	 * @see #setGroupBackground(int)
	 * @generated
	 */
	boolean isSetGroupBackground();

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #isSetHeight()
	 * @see #unsetHeight()
	 * @see #setHeight(int)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_Height()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getHeight();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #isSetHeight()
	 * @see #unsetHeight()
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHeight()
	 * @see #getHeight()
	 * @see #setHeight(int)
	 * @generated
	 */
	void unsetHeight();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getHeight <em>Height</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Height</em>' attribute is set.
	 * @see #unsetHeight()
	 * @see #getHeight()
	 * @see #setHeight(int)
	 * @generated
	 */
	boolean isSetHeight();

	/**
	 * Returns the value of the '<em><b>Hide When Minimize</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hide When Minimize</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hide When Minimize</em>' attribute.
	 * @see #isSetHideWhenMinimize()
	 * @see #unsetHideWhenMinimize()
	 * @see #setHideWhenMinimize(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_HideWhenMinimize()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isHideWhenMinimize();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isHideWhenMinimize <em>Hide When Minimize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hide When Minimize</em>' attribute.
	 * @see #isSetHideWhenMinimize()
	 * @see #unsetHideWhenMinimize()
	 * @see #isHideWhenMinimize()
	 * @generated
	 */
	void setHideWhenMinimize(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isHideWhenMinimize <em>Hide When Minimize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHideWhenMinimize()
	 * @see #isHideWhenMinimize()
	 * @see #setHideWhenMinimize(boolean)
	 * @generated
	 */
	void unsetHideWhenMinimize();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isHideWhenMinimize <em>Hide When Minimize</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Hide When Minimize</em>' attribute is set.
	 * @see #unsetHideWhenMinimize()
	 * @see #isHideWhenMinimize()
	 * @see #setHideWhenMinimize(boolean)
	 * @generated
	 */
	boolean isSetHideWhenMinimize();

	/**
	 * Returns the value of the '<em><b>Im On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Im On Top</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Im On Top</em>' attribute.
	 * @see #isSetImOnTop()
	 * @see #unsetImOnTop()
	 * @see #setImOnTop(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_ImOnTop()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isImOnTop();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isImOnTop <em>Im On Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Im On Top</em>' attribute.
	 * @see #isSetImOnTop()
	 * @see #unsetImOnTop()
	 * @see #isImOnTop()
	 * @generated
	 */
	void setImOnTop(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isImOnTop <em>Im On Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetImOnTop()
	 * @see #isImOnTop()
	 * @see #setImOnTop(boolean)
	 * @generated
	 */
	void unsetImOnTop();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isImOnTop <em>Im On Top</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Im On Top</em>' attribute is set.
	 * @see #unsetImOnTop()
	 * @see #isImOnTop()
	 * @see #setImOnTop(boolean)
	 * @generated
	 */
	boolean isSetImOnTop();

	/**
	 * Returns the value of the '<em><b>Italic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Italic</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Italic</em>' attribute.
	 * @see #isSetItalic()
	 * @see #unsetItalic()
	 * @see #setItalic(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_Italic()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isItalic();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isItalic <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Italic</em>' attribute.
	 * @see #isSetItalic()
	 * @see #unsetItalic()
	 * @see #isItalic()
	 * @generated
	 */
	void setItalic(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isItalic <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetItalic()
	 * @see #isItalic()
	 * @see #setItalic(boolean)
	 * @generated
	 */
	void unsetItalic();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isItalic <em>Italic</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Italic</em>' attribute is set.
	 * @see #unsetItalic()
	 * @see #isItalic()
	 * @see #setItalic(boolean)
	 * @generated
	 */
	boolean isSetItalic();

	/**
	 * Returns the value of the '<em><b>Location X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location X</em>' attribute.
	 * @see #isSetLocationX()
	 * @see #unsetLocationX()
	 * @see #setLocationX(int)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_LocationX()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getLocationX();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationX <em>Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location X</em>' attribute.
	 * @see #isSetLocationX()
	 * @see #unsetLocationX()
	 * @see #getLocationX()
	 * @generated
	 */
	void setLocationX(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationX <em>Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLocationX()
	 * @see #getLocationX()
	 * @see #setLocationX(int)
	 * @generated
	 */
	void unsetLocationX();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationX <em>Location X</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Location X</em>' attribute is set.
	 * @see #unsetLocationX()
	 * @see #getLocationX()
	 * @see #setLocationX(int)
	 * @generated
	 */
	boolean isSetLocationX();

	/**
	 * Returns the value of the '<em><b>Location Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location Y</em>' attribute.
	 * @see #isSetLocationY()
	 * @see #unsetLocationY()
	 * @see #setLocationY(int)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_LocationY()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getLocationY();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationY <em>Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location Y</em>' attribute.
	 * @see #isSetLocationY()
	 * @see #unsetLocationY()
	 * @see #getLocationY()
	 * @generated
	 */
	void setLocationY(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationY <em>Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLocationY()
	 * @see #getLocationY()
	 * @see #setLocationY(int)
	 * @generated
	 */
	void unsetLocationY();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getLocationY <em>Location Y</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Location Y</em>' attribute is set.
	 * @see #unsetLocationY()
	 * @see #getLocationY()
	 * @see #setLocationY(int)
	 * @generated
	 */
	boolean isSetLocationY();

	/**
	 * Returns the value of the '<em><b>Minimize When Close</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minimize When Close</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Minimize When Close</em>' attribute.
	 * @see #isSetMinimizeWhenClose()
	 * @see #unsetMinimizeWhenClose()
	 * @see #setMinimizeWhenClose(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_MinimizeWhenClose()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isMinimizeWhenClose();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isMinimizeWhenClose <em>Minimize When Close</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minimize When Close</em>' attribute.
	 * @see #isSetMinimizeWhenClose()
	 * @see #unsetMinimizeWhenClose()
	 * @see #isMinimizeWhenClose()
	 * @generated
	 */
	void setMinimizeWhenClose(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isMinimizeWhenClose <em>Minimize When Close</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinimizeWhenClose()
	 * @see #isMinimizeWhenClose()
	 * @see #setMinimizeWhenClose(boolean)
	 * @generated
	 */
	void unsetMinimizeWhenClose();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isMinimizeWhenClose <em>Minimize When Close</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Minimize When Close</em>' attribute is set.
	 * @see #unsetMinimizeWhenClose()
	 * @see #isMinimizeWhenClose()
	 * @see #setMinimizeWhenClose(boolean)
	 * @generated
	 */
	boolean isSetMinimizeWhenClose();

	/**
	 * Returns the value of the '<em><b>Online Tip Location X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Online Tip Location X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Online Tip Location X</em>' attribute.
	 * @see #isSetOnlineTipLocationX()
	 * @see #unsetOnlineTipLocationX()
	 * @see #setOnlineTipLocationX(int)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_OnlineTipLocationX()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getOnlineTipLocationX();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationX <em>Online Tip Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Online Tip Location X</em>' attribute.
	 * @see #isSetOnlineTipLocationX()
	 * @see #unsetOnlineTipLocationX()
	 * @see #getOnlineTipLocationX()
	 * @generated
	 */
	void setOnlineTipLocationX(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationX <em>Online Tip Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOnlineTipLocationX()
	 * @see #getOnlineTipLocationX()
	 * @see #setOnlineTipLocationX(int)
	 * @generated
	 */
	void unsetOnlineTipLocationX();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationX <em>Online Tip Location X</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Online Tip Location X</em>' attribute is set.
	 * @see #unsetOnlineTipLocationX()
	 * @see #getOnlineTipLocationX()
	 * @see #setOnlineTipLocationX(int)
	 * @generated
	 */
	boolean isSetOnlineTipLocationX();

	/**
	 * Returns the value of the '<em><b>Online Tip Location Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Online Tip Location Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Online Tip Location Y</em>' attribute.
	 * @see #isSetOnlineTipLocationY()
	 * @see #unsetOnlineTipLocationY()
	 * @see #setOnlineTipLocationY(int)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_OnlineTipLocationY()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getOnlineTipLocationY();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationY <em>Online Tip Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Online Tip Location Y</em>' attribute.
	 * @see #isSetOnlineTipLocationY()
	 * @see #unsetOnlineTipLocationY()
	 * @see #getOnlineTipLocationY()
	 * @generated
	 */
	void setOnlineTipLocationY(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationY <em>Online Tip Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOnlineTipLocationY()
	 * @see #getOnlineTipLocationY()
	 * @see #setOnlineTipLocationY(int)
	 * @generated
	 */
	void unsetOnlineTipLocationY();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getOnlineTipLocationY <em>Online Tip Location Y</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Online Tip Location Y</em>' attribute is set.
	 * @see #unsetOnlineTipLocationY()
	 * @see #getOnlineTipLocationY()
	 * @see #setOnlineTipLocationY(int)
	 * @generated
	 */
	boolean isSetOnlineTipLocationY();

	/**
	 * Returns the value of the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Top</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Top</em>' attribute.
	 * @see #isSetOnTop()
	 * @see #unsetOnTop()
	 * @see #setOnTop(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_OnTop()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isOnTop();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isOnTop <em>On Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>On Top</em>' attribute.
	 * @see #isSetOnTop()
	 * @see #unsetOnTop()
	 * @see #isOnTop()
	 * @generated
	 */
	void setOnTop(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isOnTop <em>On Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOnTop()
	 * @see #isOnTop()
	 * @see #setOnTop(boolean)
	 * @generated
	 */
	void unsetOnTop();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isOnTop <em>On Top</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>On Top</em>' attribute is set.
	 * @see #unsetOnTop()
	 * @see #isOnTop()
	 * @see #setOnTop(boolean)
	 * @generated
	 */
	boolean isSetOnTop();

	/**
	 * Returns the value of the '<em><b>Show Blacklist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Blacklist</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Blacklist</em>' attribute.
	 * @see #isSetShowBlacklist()
	 * @see #unsetShowBlacklist()
	 * @see #setShowBlacklist(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_ShowBlacklist()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowBlacklist();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowBlacklist <em>Show Blacklist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Blacklist</em>' attribute.
	 * @see #isSetShowBlacklist()
	 * @see #unsetShowBlacklist()
	 * @see #isShowBlacklist()
	 * @generated
	 */
	void setShowBlacklist(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowBlacklist <em>Show Blacklist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowBlacklist()
	 * @see #isShowBlacklist()
	 * @see #setShowBlacklist(boolean)
	 * @generated
	 */
	void unsetShowBlacklist();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowBlacklist <em>Show Blacklist</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Blacklist</em>' attribute is set.
	 * @see #unsetShowBlacklist()
	 * @see #isShowBlacklist()
	 * @see #setShowBlacklist(boolean)
	 * @generated
	 */
	boolean isSetShowBlacklist();

	/**
	 * Returns the value of the '<em><b>Show Friend Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Friend Tip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Friend Tip</em>' attribute.
	 * @see #isSetShowFriendTip()
	 * @see #unsetShowFriendTip()
	 * @see #setShowFriendTip(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_ShowFriendTip()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowFriendTip();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowFriendTip <em>Show Friend Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Friend Tip</em>' attribute.
	 * @see #isSetShowFriendTip()
	 * @see #unsetShowFriendTip()
	 * @see #isShowFriendTip()
	 * @generated
	 */
	void setShowFriendTip(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowFriendTip <em>Show Friend Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowFriendTip()
	 * @see #isShowFriendTip()
	 * @see #setShowFriendTip(boolean)
	 * @generated
	 */
	void unsetShowFriendTip();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowFriendTip <em>Show Friend Tip</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Friend Tip</em>' attribute is set.
	 * @see #unsetShowFriendTip()
	 * @see #isShowFriendTip()
	 * @see #setShowFriendTip(boolean)
	 * @generated
	 */
	boolean isSetShowFriendTip();

	/**
	 * Returns the value of the '<em><b>Show Nick</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Nick</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Nick</em>' attribute.
	 * @see #isSetShowNick()
	 * @see #unsetShowNick()
	 * @see #setShowNick(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_ShowNick()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowNick();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowNick <em>Show Nick</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Nick</em>' attribute.
	 * @see #isSetShowNick()
	 * @see #unsetShowNick()
	 * @see #isShowNick()
	 * @generated
	 */
	void setShowNick(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowNick <em>Show Nick</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowNick()
	 * @see #isShowNick()
	 * @see #setShowNick(boolean)
	 * @generated
	 */
	void unsetShowNick();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowNick <em>Show Nick</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Nick</em>' attribute is set.
	 * @see #unsetShowNick()
	 * @see #isShowNick()
	 * @see #setShowNick(boolean)
	 * @generated
	 */
	boolean isSetShowNick();

	/**
	 * Returns the value of the '<em><b>Show Online Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Online Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Online Only</em>' attribute.
	 * @see #isSetShowOnlineOnly()
	 * @see #unsetShowOnlineOnly()
	 * @see #setShowOnlineOnly(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_ShowOnlineOnly()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowOnlineOnly();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineOnly <em>Show Online Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Online Only</em>' attribute.
	 * @see #isSetShowOnlineOnly()
	 * @see #unsetShowOnlineOnly()
	 * @see #isShowOnlineOnly()
	 * @generated
	 */
	void setShowOnlineOnly(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineOnly <em>Show Online Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowOnlineOnly()
	 * @see #isShowOnlineOnly()
	 * @see #setShowOnlineOnly(boolean)
	 * @generated
	 */
	void unsetShowOnlineOnly();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineOnly <em>Show Online Only</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Online Only</em>' attribute is set.
	 * @see #unsetShowOnlineOnly()
	 * @see #isShowOnlineOnly()
	 * @see #setShowOnlineOnly(boolean)
	 * @generated
	 */
	boolean isSetShowOnlineOnly();

	/**
	 * Returns the value of the '<em><b>Show Online Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Online Tip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Online Tip</em>' attribute.
	 * @see #isSetShowOnlineTip()
	 * @see #unsetShowOnlineTip()
	 * @see #setShowOnlineTip(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_ShowOnlineTip()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowOnlineTip();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineTip <em>Show Online Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Online Tip</em>' attribute.
	 * @see #isSetShowOnlineTip()
	 * @see #unsetShowOnlineTip()
	 * @see #isShowOnlineTip()
	 * @generated
	 */
	void setShowOnlineTip(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineTip <em>Show Online Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowOnlineTip()
	 * @see #isShowOnlineTip()
	 * @see #setShowOnlineTip(boolean)
	 * @generated
	 */
	void unsetShowOnlineTip();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowOnlineTip <em>Show Online Tip</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Online Tip</em>' attribute is set.
	 * @see #unsetShowOnlineTip()
	 * @see #isShowOnlineTip()
	 * @see #setShowOnlineTip(boolean)
	 * @generated
	 */
	boolean isSetShowOnlineTip();

	/**
	 * Returns the value of the '<em><b>Show Last Login Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Last Login Tip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Last Login Tip</em>' attribute.
	 * @see #isSetShowLastLoginTip()
	 * @see #unsetShowLastLoginTip()
	 * @see #setShowLastLoginTip(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_ShowLastLoginTip()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowLastLoginTip();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowLastLoginTip <em>Show Last Login Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Last Login Tip</em>' attribute.
	 * @see #isSetShowLastLoginTip()
	 * @see #unsetShowLastLoginTip()
	 * @see #isShowLastLoginTip()
	 * @generated
	 */
	void setShowLastLoginTip(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowLastLoginTip <em>Show Last Login Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowLastLoginTip()
	 * @see #isShowLastLoginTip()
	 * @see #setShowLastLoginTip(boolean)
	 * @generated
	 */
	void unsetShowLastLoginTip();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowLastLoginTip <em>Show Last Login Tip</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Last Login Tip</em>' attribute is set.
	 * @see #unsetShowLastLoginTip()
	 * @see #isShowLastLoginTip()
	 * @see #setShowLastLoginTip(boolean)
	 * @generated
	 */
	boolean isSetShowLastLoginTip();

	/**
	 * Returns the value of the '<em><b>Show Signature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Signature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Signature</em>' attribute.
	 * @see #isSetShowSignature()
	 * @see #unsetShowSignature()
	 * @see #setShowSignature(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_ShowSignature()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowSignature();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSignature <em>Show Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Signature</em>' attribute.
	 * @see #isSetShowSignature()
	 * @see #unsetShowSignature()
	 * @see #isShowSignature()
	 * @generated
	 */
	void setShowSignature(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSignature <em>Show Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowSignature()
	 * @see #isShowSignature()
	 * @see #setShowSignature(boolean)
	 * @generated
	 */
	void unsetShowSignature();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSignature <em>Show Signature</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Signature</em>' attribute is set.
	 * @see #unsetShowSignature()
	 * @see #isShowSignature()
	 * @see #setShowSignature(boolean)
	 * @generated
	 */
	boolean isSetShowSignature();

	/**
	 * Returns the value of the '<em><b>Show Custom Head</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Custom Head</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Custom Head</em>' attribute.
	 * @see #isSetShowCustomHead()
	 * @see #unsetShowCustomHead()
	 * @see #setShowCustomHead(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_ShowCustomHead()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowCustomHead();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowCustomHead <em>Show Custom Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Custom Head</em>' attribute.
	 * @see #isSetShowCustomHead()
	 * @see #unsetShowCustomHead()
	 * @see #isShowCustomHead()
	 * @generated
	 */
	void setShowCustomHead(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowCustomHead <em>Show Custom Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowCustomHead()
	 * @see #isShowCustomHead()
	 * @see #setShowCustomHead(boolean)
	 * @generated
	 */
	void unsetShowCustomHead();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowCustomHead <em>Show Custom Head</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Custom Head</em>' attribute is set.
	 * @see #unsetShowCustomHead()
	 * @see #isShowCustomHead()
	 * @see #setShowCustomHead(boolean)
	 * @generated
	 */
	boolean isSetShowCustomHead();

	/**
	 * Returns the value of the '<em><b>Show Small Head</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Small Head</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Small Head</em>' attribute.
	 * @see #isSetShowSmallHead()
	 * @see #unsetShowSmallHead()
	 * @see #setShowSmallHead(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_ShowSmallHead()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowSmallHead();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSmallHead <em>Show Small Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Small Head</em>' attribute.
	 * @see #isSetShowSmallHead()
	 * @see #unsetShowSmallHead()
	 * @see #isShowSmallHead()
	 * @generated
	 */
	void setShowSmallHead(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSmallHead <em>Show Small Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowSmallHead()
	 * @see #isShowSmallHead()
	 * @see #setShowSmallHead(boolean)
	 * @generated
	 */
	void unsetShowSmallHead();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isShowSmallHead <em>Show Small Head</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Small Head</em>' attribute is set.
	 * @see #unsetShowSmallHead()
	 * @see #isShowSmallHead()
	 * @see #setShowSmallHead(boolean)
	 * @generated
	 */
	boolean isSetShowSmallHead();

	/**
	 * Returns the value of the '<em><b>Tree Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tree Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tree Mode</em>' attribute.
	 * @see #isSetTreeMode()
	 * @see #unsetTreeMode()
	 * @see #setTreeMode(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_TreeMode()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isTreeMode();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isTreeMode <em>Tree Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tree Mode</em>' attribute.
	 * @see #isSetTreeMode()
	 * @see #unsetTreeMode()
	 * @see #isTreeMode()
	 * @generated
	 */
	void setTreeMode(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isTreeMode <em>Tree Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTreeMode()
	 * @see #isTreeMode()
	 * @see #setTreeMode(boolean)
	 * @generated
	 */
	void unsetTreeMode();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isTreeMode <em>Tree Mode</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Tree Mode</em>' attribute is set.
	 * @see #unsetTreeMode()
	 * @see #isTreeMode()
	 * @see #setTreeMode(boolean)
	 * @generated
	 */
	boolean isSetTreeMode();

	/**
	 * Returns the value of the '<em><b>Use Tab IM Window</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use Tab IM Window</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use Tab IM Window</em>' attribute.
	 * @see #isSetUseTabIMWindow()
	 * @see #unsetUseTabIMWindow()
	 * @see #setUseTabIMWindow(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_UseTabIMWindow()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isUseTabIMWindow();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isUseTabIMWindow <em>Use Tab IM Window</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Tab IM Window</em>' attribute.
	 * @see #isSetUseTabIMWindow()
	 * @see #unsetUseTabIMWindow()
	 * @see #isUseTabIMWindow()
	 * @generated
	 */
	void setUseTabIMWindow(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isUseTabIMWindow <em>Use Tab IM Window</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUseTabIMWindow()
	 * @see #isUseTabIMWindow()
	 * @see #setUseTabIMWindow(boolean)
	 * @generated
	 */
	void unsetUseTabIMWindow();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isUseTabIMWindow <em>Use Tab IM Window</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Use Tab IM Window</em>' attribute is set.
	 * @see #unsetUseTabIMWindow()
	 * @see #isUseTabIMWindow()
	 * @see #setUseTabIMWindow(boolean)
	 * @generated
	 */
	boolean isSetUseTabIMWindow();

	/**
	 * Returns the value of the '<em><b>Bar Expanded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bar Expanded</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bar Expanded</em>' attribute.
	 * @see #isSetBarExpanded()
	 * @see #unsetBarExpanded()
	 * @see #setBarExpanded(boolean)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_BarExpanded()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isBarExpanded();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isBarExpanded <em>Bar Expanded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bar Expanded</em>' attribute.
	 * @see #isSetBarExpanded()
	 * @see #unsetBarExpanded()
	 * @see #isBarExpanded()
	 * @generated
	 */
	void setBarExpanded(boolean value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isBarExpanded <em>Bar Expanded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetBarExpanded()
	 * @see #isBarExpanded()
	 * @see #setBarExpanded(boolean)
	 * @generated
	 */
	void unsetBarExpanded();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#isBarExpanded <em>Bar Expanded</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Bar Expanded</em>' attribute is set.
	 * @see #unsetBarExpanded()
	 * @see #isBarExpanded()
	 * @see #setBarExpanded(boolean)
	 * @generated
	 */
	boolean isSetBarExpanded();

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #isSetWidth()
	 * @see #unsetWidth()
	 * @see #setWidth(int)
	 * @see edu.tsinghua.lumaqq.ecore.option.OptionPackage#getGUIOption_Width()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 * @generated
	 */
	int getWidth();

	/**
	 * Sets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #isSetWidth()
	 * @see #unsetWidth()
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(int value);

	/**
	 * Unsets the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWidth()
	 * @see #getWidth()
	 * @see #setWidth(int)
	 * @generated
	 */
	void unsetWidth();

	/**
	 * Returns whether the value of the '{@link edu.tsinghua.lumaqq.ecore.option.GUIOption#getWidth <em>Width</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Width</em>' attribute is set.
	 * @see #unsetWidth()
	 * @see #getWidth()
	 * @see #setWidth(int)
	 * @generated
	 */
	boolean isSetWidth();

} // GUIOption
