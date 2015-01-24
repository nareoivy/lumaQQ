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

import edu.tsinghua.lumaqq.ecore.global.GlobalFactory;
import edu.tsinghua.lumaqq.ecore.global.GlobalPackage;
import edu.tsinghua.lumaqq.ecore.global.GlobalSetting;
import edu.tsinghua.lumaqq.ecore.global.LanguageType;
import edu.tsinghua.lumaqq.ecore.global.Robot;
import edu.tsinghua.lumaqq.ecore.global.Robots;
import edu.tsinghua.lumaqq.ecore.global.Servers;

/**
 * Global配置文件工具类
 *
 * @author luma
 */
public class GlobalUtil {
	/**
	 * 保存全局配置文件
	 * 
	 * @param file
	 * 		文件路径
	 * @param gs
	 * 		文件根元素对象
	 * @throws IOException
	 * 		如果保存出错
	 */
	@SuppressWarnings("unchecked")
	public static final void save(File file, GlobalSetting gs) {
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
        
        resourceSet.getPackageRegistry().put(GlobalPackage.eINSTANCE.getNsURI(), GlobalPackage.eINSTANCE);
                
        // Get the URI of the model file.
        URI fileURI = URI.createFileURI(file.getAbsolutePath());

        // Create a resource for this file.
        Resource resource = resourceSet.createResource(fileURI);

        // add the globalsetting to the resoource
        resource.getContents().add(gs);

        // Save the contents of the resource to the file system.
        Map options = new HashMap();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
			resource.save(options);
		} catch (IOException e) {
		}
	}
	
	/**
	 * 载入全局配置文件
	 * 
	 * @param file
	 * 		文件路径
	 * @return
	 * 		根元素对象
	 */
	@SuppressWarnings("unchecked")
	public static final GlobalSetting load(File file) {
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
                
        resourceSet.getPackageRegistry().put(GlobalPackage.eINSTANCE.getNsURI(), GlobalPackage.eINSTANCE);
                
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
	        	return (GlobalSetting) resource.getContents().get(0);
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * @return
	 * 		缺省的全局配置根元素对象
	 */
	@SuppressWarnings("unchecked")
	public static final GlobalSetting createDefault() {
		GlobalSetting gs = GlobalFactory.eINSTANCE.createGlobalSetting();
		gs.setLanguage(LanguageType.ZH_LITERAL);
		
		Servers servers = GlobalFactory.eINSTANCE.createServers();
		String[] tcpServers = new String[] {
				"tcpconn.tencent.com",
				"tcpconn2.tencent.com",
				"tcpconn3.tencent.com",
				"tcpconn4.tencent.com",
				"219.133.38.5",
				"218.17.209.23"
		};
		String[] udpServers = new String[] {
				"sz.tencent.com",
				"sz2.tencent.com",
				"sz3.tencent.com",
				"sz4.tencent.com",
				"sz5.tencent.com",
				"sz6.tencent.com",
				"sz7.tencent.com",
				"61.144.238.155",
				"61.144.238.156",
				"202.96.170.163",
				"202.96.170.164",
				"219.133.38.129",
				"219.133.38.130",
				"219.133.38.43",
				"219.133.38.44",
				"219.133.40.215",
				"219.133.40.216",
				"219.133.48.100"
		};
		for(String s : tcpServers)
			servers.getTCPServer().add(s);
		for(String s : udpServers)
			servers.getUDPServer().add(s);
		gs.setServers(servers);
		
		Robots robots = GlobalFactory.eINSTANCE.createRobots();
		Robot robot = GlobalFactory.eINSTANCE.createRobot();
		robot.setName("Dummy Robot");
		robot.setClass("edu.tsinghua.lumaqq.qq.robot.DummyRobot");
		robots.getRobot().add(robot);
		gs.setRobots(robots);
		
		return gs;
	}
}
