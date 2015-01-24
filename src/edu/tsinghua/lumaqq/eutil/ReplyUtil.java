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
package edu.tsinghua.lumaqq.eutil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

import edu.tsinghua.lumaqq.ecore.reply.Replies;
import edu.tsinghua.lumaqq.ecore.reply.ReplyFactory;
import edu.tsinghua.lumaqq.ecore.reply.ReplyPackage;

/**
 * 回复信息文件工具类
 *
 * @author luma
 */
public class ReplyUtil {
	/**
	 * 保存回复信息文件
	 * 
	 * @param file
	 * 		文件路径
	 * @param server
	 * 		文件根元素对象
	 * @throws IOException
	 * 		如果保存出错
	 */
	@SuppressWarnings("unchecked")
	public static final void save(File file, Replies replies) {
        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();
        
        // Register the default resource factory -- only needed for stand-alone!
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl() {
            @Override
			public Resource createResource(URI uri) {
                XMLResource xmlResource = (XMLResource) super.createResource(uri);
                return xmlResource;
            }
        });
        
        resourceSet.getPackageRegistry().put(ReplyPackage.eINSTANCE.getNsURI(), ReplyPackage.eINSTANCE);
                
        // Get the URI of the model file.
        URI fileURI = URI.createFileURI(file.getAbsolutePath());

        // Create a resource for this file.
        Resource resource = resourceSet.createResource(fileURI);

        // add the globalsetting to the resoource
        resource.getContents().add(replies);

        // Save the contents of the resource to the file system.
        Map options = new HashMap();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
			resource.save(options);
		} catch (IOException e) {
		}
	}
	
	/**
	 * 载入回复信息文件
	 * 
	 * @param file
	 * 		文件路径
	 * @return
	 * 		根元素对象
	 */
	@SuppressWarnings("unchecked")
	public static final Replies load(File file) {
        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register the default resource factory -- only needed for stand-alone!
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl() {
            @Override
			public Resource createResource(URI uri) {
                XMLResource xmlResource = (XMLResource) super.createResource(uri);
                return xmlResource;
            }
        });
                
        resourceSet.getPackageRegistry().put(ReplyPackage.eINSTANCE.getNsURI(), ReplyPackage.eINSTANCE);
                
        // Get the URI of the model file.
        URI fileURI = URI.createFileURI(file.getAbsolutePath());

        // Create a resource for this file.
        Resource resource = resourceSet.createResource(fileURI);

        // Save the contents of the resource to the file system.
        Map options = new HashMap();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
			resource.load(options);
	        if(resource.getContents().isEmpty())
	        	return null;
	        else
	        	return (Replies) resource.getContents().get(0);
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * @return
	 * 		缺省的回复信息文件根元素对象
	 */
	public static final Replies createDefault() {
		Replies replies = ReplyFactory.eINSTANCE.createReplies();
		return replies;
	}
}
