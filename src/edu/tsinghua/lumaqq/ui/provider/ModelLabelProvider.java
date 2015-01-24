/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package edu.tsinghua.lumaqq.ui.provider;

import static edu.tsinghua.lumaqq.resource.Resources.*;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Dummy;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.MessageSetting;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.Organization;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.widgets.qstyle.IQTreeLabelProvider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * 所有Model的文本提供器
 * 
 * @author luma
 */
public class ModelLabelProvider implements IQTreeLabelProvider<Model> {	
	public static final ModelLabelProvider INSTANCE = new ModelLabelProvider();
	private boolean showSignature;
	private boolean showCustomHead;
	
	private Resources res;
	
	public ModelLabelProvider() {
		res = Resources.getInstance();	
	}
	
	public String getText(Model element) {
		switch(element.type) {
			case GROUP:
				Group g = (Group)element;
				switch(g.groupType) {
					case CLUSTER_GROUP:
						return g.name;
					case LATEST_GROUP:
						return g.name + ' ' + '(' + g.getOnlineUserCount() + '/' + (g.users.size() + g.clusters.size()) + ')';
					default:
						return g.name + ' ' + '(' + g.getOnlineUserCount() + '/' + g.users.size() + ')';
				}
			case USER:
				User u = (User)element;
				if(showSignature && u.signature != null && !u.signature.equals(""))
					return u.displayName + '(' + u.signature + ')';
				else
					return u.displayName;
			case CLUSTER:
				Cluster c = (Cluster)element;
				if(c.messageSetting == MessageSetting.COUNTER && c.messageCount > 0)
					return c.name + ' ' + '(' + c.messageCount + ')';
				else
					return c.name;
			case ORGANIZATION:
				return ((Organization)element).name;
			case DUMMY:
				return ((Dummy)element).name;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return null;
		}
	}

	public Image getImage(Model element) {	
		switch(element.type) {
			case GROUP:
				Group g = (Group)element;
				return res.getImage(g.expanded ? icoExpanded : icoCollapsed);
			case USER:
				User u = (User)element;
				switch(u.status) {
					case OFFLINE:
						if(u.hasCustomHead && showCustomHead) {
							Image customHead = res.getCustomHead(u.customHeadId, true);
							if(customHead != null)
								return customHead;
						}
						if(u.isTM())
							return res.getGrayImage(u.female ? icoTMFemale : icoTMMale);
						else
							return res.getHead(u.headId + 1);
					case HIDDEN:
						if(u.hasCustomHead && showCustomHead) {
							Image customHead = res.getCustomHead(u.customHeadId, true);
							if(customHead != null)
								return customHead;
						}
						if(u.isTM())
							return res.getGrayImage(u.female ? icoTMFemale : icoTMMale);
						else
							return res.getHead(u.headId);
					case AWAY:
					case ONLINE:
						if(u.hasCustomHead && showCustomHead) {
							Image customHead = res.getCustomHead(u.customHeadId, false);
							if(customHead != null)
								return customHead;
						}
						if(u.isTM())
							return res.getImage(u.female ? icoTMFemale : icoTMMale);
						else
							return res.getHead(u.headId);	
					default:
						SWT.error(SWT.ERROR_INVALID_RANGE);
						return null;
				}
			case CLUSTER:
				Cluster c = (Cluster)element;
				if(c.group.isLatest()) {
					return res.getClusterHead(c.headId);
				} else {
					switch(c.clusterType) {
						case NORMAL:
							return res.getSmallClusterHead(c.headId);
						default:
							return res.getImage(icoDialog);					
					}					
				}
			case ORGANIZATION:
				return res.getImage(icoOrganization);
			case DUMMY:
				switch(((Dummy)element).dummyType) {
					case CLUSTER_ORGANIZATION:
						return res.getImage(icoOrganization);
					case SUBJECTS:
						return res.getImage(icoDialog);
					default:
						return null;
				}
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return null;				
		}
	}

	public Image getDecoration(Model element) {
		switch(element.type) {
			case USER:
				User u = (User)element;
				switch(u.status) {
					case AWAY:
						return res.getImage(icoAwayDecoration);
					case HIDDEN:
						return res.getImage(icoHiddenDecoration);
					case ONLINE:
					case OFFLINE:
						return null;
					default:
						SWT.error(SWT.ERROR_INVALID_RANGE);
						return null;	
				}
			case DUMMY:
			case ORGANIZATION:
			case CLUSTER:				
			case GROUP:
				return null;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return null;	
		}
	}

	public Image getAttachment(Model element, int index) {
		switch(element.type) {
			case USER:
				User u = (User)element;
				switch(index) {
					case 0:
						return u.pinned ? res.getImage(icoPin) : null;
					case 1:
						return u.hasCam() ? res.getImage(icoCam) : null;
					case 3:
						if(u.isMobile()) {
							return res.getImage(icoMobileQQ);
						} else if(u.isBind()) {
							return res.getImage(icoBindQQ);
						} else
							return null;
					default:
						return null;
				}
			default:
				return null;
		}
	}

	public Color getForeground(Model element) {
		switch(element.type) {
			case USER:
				User u = (User)element;
				switch(u.status) {
					case AWAY:
						return Colors.BLUE;
					case ONLINE:
						if(u.isMember())
							return Colors.RED;
						else
							return null;
					case HIDDEN:
					case OFFLINE:
						return null;
					default:
						SWT.error(SWT.ERROR_INVALID_RANGE);
						return null;	
				}
			case GROUP:
			case CLUSTER:
			case DUMMY:
			case ORGANIZATION:
				return null;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return null;	
		}
	}

	public boolean isExpaned(Model element) {
		return element.expanded;
	}

	public void setExpanded(Model element, boolean exp) {
		element.expanded = exp;
	}

	/**
	 * @return Returns the showSignature.
	 */
	public boolean isShowSignature() {
		return showSignature;
	}

	/**
	 * @param showSignature The showSignature to set.
	 */
	public void setShowSignature(boolean showSignature) {
		this.showSignature = showSignature;
	}

	/**
	 * @param element
	 * @return
	 */
	public Image getPrefix(Model element) {
		switch(element.type) {
			case USER:
			case GROUP:
			case CLUSTER:
				return null;
			case DUMMY:
			case ORGANIZATION:
				return res.getImage(element.expanded ? icoExpanded9 : icoCollapsed9);
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return null;	
		}
	}

	/**
	 * @param showCustomHead the showCustomHead to set
	 */
	public void setShowCustomHead(boolean showCustomHead) {
		this.showCustomHead = showCustomHead;
	}
}
