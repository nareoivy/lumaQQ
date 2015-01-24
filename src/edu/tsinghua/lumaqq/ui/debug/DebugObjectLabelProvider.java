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
package edu.tsinghua.lumaqq.ui.debug;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.debug.FragmentDO;
import edu.tsinghua.lumaqq.qq.debug.IDebugObject;
import edu.tsinghua.lumaqq.qq.debug.PacketDO;
import edu.tsinghua.lumaqq.resource.Resources;

/**
 * IDebugObject对象的label provider
 * 
 * @author luma
 */
public class DebugObjectLabelProvider extends LabelProvider implements ITableLabelProvider {
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage(Object element, int columnIndex) {
        if(element instanceof PacketDO) {
            IDebugObject obj = (IDebugObject)element;
            if(columnIndex == 0)
                return Resources.getInstance().getImage(obj.isIncoming() ? Resources.icoIn : Resources.icoOut);
            else
                return null;
        } else if(element instanceof FragmentDO) {
            if(columnIndex == 0)
                return Resources.getInstance().getImage(Resources.icoFragment);
            else
                return null;
        } else
            return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText(Object element, int columnIndex) {
        if(element instanceof IDebugObject) {
            IDebugObject obj = (IDebugObject)element;
            return obj.getName();
        } else
            return QQ.EMPTY_STRING;
    }
}
