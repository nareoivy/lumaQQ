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
package edu.tsinghua.lumaqq.ui.helper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.customface.FaceEntry;
import edu.tsinghua.lumaqq.ecore.face.Face;
import edu.tsinghua.lumaqq.ecore.face.FaceConstant;
import edu.tsinghua.lumaqq.ecore.face.FaceFactory;
import edu.tsinghua.lumaqq.ecore.face.FaceGroup;
import edu.tsinghua.lumaqq.ecore.face.Faces;
import edu.tsinghua.lumaqq.eutil.FaceUtil;
import edu.tsinghua.lumaqq.resource.Messages;

/**
 * 管理自定义表情的工具类。其实准确来说，它是用来管理外部图像资源的，因为它不光包括了
 * 自定义表情，也包括了自定义头像。FaceRegistry这个名字不太准确，但是现在还没有改
 * 过来，就这么沿用下去吧。
 * 
 * @author luma
 */
public class FaceRegistry {
	private Faces faces;
	private String fileName;
	
	// id号到md5的映射
	private Map<Integer, String> id2md5;
	// md5到id的映射
	private Map<String, Integer> md52id;
	// face到face group的映射
	private Map<Face, FaceGroup> face2group;
	// md5到face的映射
	private Map<String, Face> md52face;
	// 组名到组的映射
	private Map<String, FaceGroup> name2group;
	
	private boolean dirty;
	
	// singleton模式
	private static FaceRegistry instance = new FaceRegistry();
	
	/**
	 * @return 单一实例
	 */
	public static FaceRegistry getInstance() {
		return instance;
	}
	
	/**
	 * 构造函数
	 * @param options
	 */
	private FaceRegistry() {
	    id2md5 = new HashMap<Integer, String>();
	    md52id = new HashMap<String, Integer>();
	    face2group = new HashMap<Face, FaceGroup>();
	    md52face = new HashMap<String, Face>();
	    name2group = new HashMap<String, FaceGroup>();
	    dirty = false;
	}
	
	/**
	 * 根据md5得到文件名
	 * 
	 * @param md5
	 * @return
	 * 		如果不存在返回null
	 */
	public String getFaceFileName(String md5) {
	    Face f = md52face.get(md5);
	    return (f == null) ? null : f.getFilename();
	}
	
	/**
	 * 把group组的第index个图片上移一个位置
	 * 
	 * @param group
	 * 		组索引
	 * @param index
	 * 		图片的组内索引
	 */
	@SuppressWarnings("unchecked")
	public void upFace(int group, int index) {
	    FaceGroup g = getFaceGroup(group);
	    if(g == null)
	        return;
	    
	    List<Face> faceList = g.getFace();
	    if(index < 1 || index >= faceList.size())
	        return;
	    
	    faceList.add(index - 1, faceList.remove(index));
	    dirty = true;
	}
	
	/**
	 * 把group组的第index个图片下移一个位置
	 * 
	 * @param group
	 * 		组索引
	 * @param index
	 */
	@SuppressWarnings("unchecked")
	public void downFace(int group, int index) {
	    FaceGroup g = getFaceGroup(group);
	    if(g == null)
	        return;
	    
	    List<Face> faceList = g.getFace();
	    if(index < 0 || index >= faceList.size() - 1)
	        return;

	    faceList.add(index + 1, faceList.remove(index));
	    dirty = true;
	}
	
	/**
	 * 设置根元素对象
	 * 
	 * @param faces
	 */
	@SuppressWarnings("unchecked")
	public void setFacesModel(Faces faces) {
		this.faces = faces;
		
		// 建立映射
		for(FaceGroup g : (List<FaceGroup>)faces.getGroup()) {
			for(Face face : (List<Face>)g.getFace()) {
				id2md5.put(face.getId(), face.getMd5());
				md52id.put(face.getMd5(), face.getId());
				md52face.put(face.getMd5(), face);
				face2group.put(face, g);
			}
			name2group.put(g.getName(), g);
		}
	}
	
	/**
	 * @return
	 * 		根元素对象
	 */
	public Faces getFacesModel() {
		return faces;
	}
	
	/**
	 * 添加一个组
	 * 
	 * @param name
	 * 		组名
	 */
	@SuppressWarnings("unchecked")
	public void addGroup(String name) {
		if(faces == null)
			return;
		
		FaceGroup g = FaceFactory.eINSTANCE.createFaceGroup();
		g.setName(name);
		g.setId(faces.getNextGroupId());
		
		faces.getGroup().add(g);
		File dir = new File(LumaQQ.CUSTOM_FACE_DIR + g.getId());
		if(!dir.exists())
			dir.mkdirs();
		faces.setNextGroupId(faces.getNextGroupId() + 1);
		
		name2group.put(g.getName(), g);
	    dirty = true;
	}
	
	/**
	 * 删除一个组
	 * 
	 * @param group
	 * 		组序号
	 */
	public void removeGroup(int group) {
	    if(faces == null)
	        return;	    
	    if(group < 0 || group >= getGroupCount())
	        return;
	    
	    removeGroup((FaceGroup)faces.getGroup().get(group));
	}

	/**
	 * 删除一个组
	 * 
	 * @param name
	 * 		组名
	 */
	public void removeGroup(String name) {
		if(faces == null)
			return;
		
		FaceGroup g = getFaceGroup(name);
		if(g == null)
			return;
		
		removeGroup(g);
	}
	
	/**
	 * 删除一个组
	 * 
	 * @param g
	 */
	@SuppressWarnings("unchecked")
	public void removeGroup(FaceGroup g) {
		faces.getGroup().remove(g);
		name2group.remove(g.getName());
		FileTool.deleteDirectory(LumaQQ.CUSTOM_FACE_DIR + g.getId());
		
		for(Face f : (List<Face>)g.getFace()) {
			md52face.remove(f.getMd5());
			md52id.remove(f.getMd5());
			id2md5.remove(f.getId());
			face2group.remove(f);
		}
	    dirty = true;
	}
	
	/**
	 * 添加一个表情
	 * 
	 * @param md5
	 * 		图片的md5
	 * @param g
	 * 		组元素对象
	 */
	@SuppressWarnings("unchecked")
	public void addFace(FaceEntry entry, FaceGroup g) {
		Face f = FaceFactory.eINSTANCE.createFace();
		f.setMd5(entry.md5);
		f.setFilename(entry.filename);
		f.setId(faces.getNextId());		
		g.getFace().add(f);
		id2md5.put(f.getId(), f.getMd5());
		md52id.put(f.getMd5(), f.getId());
		md52face.put(f.getMd5(), f);
		face2group.put(f, g);
		faces.setNextId(faces.getNextId() + 1);
	    dirty = true;
	}
	
	/**
	 * 添加一个收到的表情
	 * 
	 * @param md5
	 * @param filename
	 */
	@SuppressWarnings("unchecked")
	public void addReceivedFace(String md5, String filename) {
		FaceGroup g = getGroupById(FaceConstant.RECEIVED_GROUP_ID);
		if(g == null)
			g = createReceviedGroup();
		
		Face f = FaceFactory.eINSTANCE.createFace();
		f.setMd5(md5);
		f.setFilename(filename);
		f.setId(faces.getNextId());
		g.getFace().add(f);
		id2md5.put(f.getId(), f.getMd5());
		md52id.put(f.getMd5(), f.getId());
		md52face.put(f.getMd5(), f);
		face2group.put(f, g);
		faces.setNextId(faces.getNextId() + 1);
	    dirty = true;
	}
	
	/**
	 * 创建接收组
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private FaceGroup createReceviedGroup() {
		FaceGroup g = FaceFactory.eINSTANCE.createFaceGroup();
		g.setId(FaceConstant.RECEIVED_GROUP_ID);
		g.setName(Messages.face_group_recv);
		
		faces.getGroup().add(g);
		FileTool.mkdirs(LumaQQ.CUSTOM_FACE_DIR + g.getId());
		name2group.put(g.getName(), g);
		return g;
	}
	
	/**
	 * 添加一个自定义头像
	 * 
	 * @param md5
	 * @param filename
	 */
	@SuppressWarnings("unchecked")
	public void addCustomHead(String md5, String filename) {
		FaceGroup g = getGroupById(FaceConstant.CUSTOM_HEAD_GROUP_ID);
		if(g == null)
			g = createCustomHeadGroup();
		
		Face f = FaceFactory.eINSTANCE.createFace();
		f.setMd5(md5);
		f.setFilename(filename);
		f.setId(faces.getNextId());
		g.getFace().add(f);
		id2md5.put(f.getId(), f.getMd5());
		md52id.put(f.getMd5(), f.getId());
		md52face.put(f.getMd5(), f);
		face2group.put(f, g);
		faces.setNextId(faces.getNextId() + 1);
	    dirty = true;
	}
	
	/**
	 * 创建自定义头像组
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private FaceGroup createCustomHeadGroup() {
		FaceGroup g = FaceFactory.eINSTANCE.createFaceGroup();
		g.setId(FaceConstant.CUSTOM_HEAD_GROUP_ID);
		g.setName(Messages.face_group_custom_head);
		
		faces.getGroup().add(g);
		FileTool.mkdirs(LumaQQ.CUSTOM_FACE_DIR + g.getId());
		name2group.put(g.getName(), g);
		return g;
	}
	
	/**
	 * 根据id的得到组
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private FaceGroup getGroupById(int id) {
		for(FaceGroup g : (List<FaceGroup>)faces.getGroup()) {
			if(g.getId() == id)
				return g;
		}
		return null;
	}
	
	/**
	 * 得到自定义表情组的个数
	 * 
	 * @return
	 * 		组个数
	 */
	public int getGroupCount() {
	    if(faces == null)
	        return 0;
	    else
	        return faces.getGroup().size();
	}
	
	/**
	 * 得到某个组中自定义表情数目
	 * 
	 * @param group
	 * @return
	 */
	public int getFaceCount(int group) {
	    FaceGroup g = getFaceGroup(group);
	    if(g == null)
	        return 0;
	    
	    return g.getFace().size();
	}
	
	/**
	 * 删除一个自定义表情
	 * 
	 * @param md5
	 */
	public void removeFace(String md5) {
		if(faces == null)
			return;
		
		Face f = md52face.remove(md5);
		if(f == null)
			return;
		
		md52id.remove(md5);
		id2md5.remove(f.getId());
		
		FaceGroup g = face2group.remove(f);
		if(g == null)
			return;
		
		g.getFace().remove(f);		
	    dirty = true;
	}
	
	/**
	 * 设置组名称
	 * 
	 * @param group
	 * @param name
	 */
	public void setGroupName(int group, String name) {
	    FaceGroup g = getFaceGroup(group);
	    if(g == null)
	        return;
	    
	    g.setName(name);
	    dirty = true;
	}
	
	/**
	 * 移动一个表情到另外一个组
	 * 
	 * @param face
	 * 		表情文件元素对象
	 * @param destGroup
	 * 		目的组索引
	 */
	@SuppressWarnings("unchecked")
	public void moveFace(Face face, int destGroup) {
	    FaceGroup dest = getFaceGroup(destGroup);
	    if(dest == null)
	        return;	    
	    FaceGroup src = face2group.get(face);
	    
	    boolean success = false;
	    if(dest.getId() == FaceConstant.CUSTOM_HEAD_GROUP_ID) {
	    	success = saveCustomHead(face);
	    } else {
	    	success = FileTool.copyFile(getFacePath(face), getFacePath(face, dest)) &&
		    	FileTool.copyFile(getSmallFacePath(face), getSmallFacePath(face, dest));	    	
	    }
	    
	    if(success) {
	    	FileTool.deleteFile(getFacePath(face));
	    	FileTool.deleteFile(getSmallFacePath(face));
	    	src.getFace().remove(face);
	    	dest.getFace().add(face);
	    	face2group.put(face, dest);
	    }
	    dirty = true;
	}
	
	/**
	 * 保存文件为自定义头像
	 * 
     * @return
     * 		true表示保存成功
	 */
	private boolean saveCustomHead(Face face) {
		try {
			ImageLoader loader = new ImageLoader();
			loader.load(getFacePath(face));
			ImageData data = loader.data[0].scaledTo(40, 40);
			
			// save 40x40 bmp
			ImageLoader saveLoader = new ImageLoader();
			saveLoader.data = new ImageData[] { data };
			saveLoader.save(LumaQQ.CUSTOM_FACE_DIR + FaceConstant.CUSTOM_HEAD_GROUP_ID + '/' + face.getMd5() + ".bmp", SWT.IMAGE_BMP);
			
			// save 20x20 bmp
			data = loader.data[0].scaledTo(20, 20);
			saveLoader = new ImageLoader();
			saveLoader.data = new ImageData[] { data };
			saveLoader.save(LumaQQ.CUSTOM_FACE_DIR + FaceConstant.CUSTOM_HEAD_GROUP_ID + '/' + face.getMd5() + "fixed.bmp", SWT.IMAGE_BMP);
			
			return true;
		} catch(SWTException e) {
			return false;
		}
	}
	
	/**
	 * 得到表情路径
	 * 
	 * @param face
	 * @param g
	 * @return
	 */
	private String getFacePath(Face face, FaceGroup g) {
		return LumaQQ.CUSTOM_FACE_DIR + g.getId() + '/' + face.getFilename();
	}
	
	/**
	 * 得到表情路径
	 * 
	 * @param face
	 * @return
	 */
	public String getFacePath(Face face) {
		FaceGroup g = face2group.get(face);
		if(g == null)
			return null;
		
		return LumaQQ.CUSTOM_FACE_DIR + g.getId() + '/' + face.getFilename();
	}
	
	/**
	 * 得到表情路径
	 * 
	 * @param id
	 * @return
	 */
	public String getFacePath(int id) {
		return getFacePath(getMd5ById(id));
	}
	
	/**
	 * 得到表情路径
	 * 
	 * @param md5
	 * @return
	 */
	public String getFacePath(String md5) {
		Face f = md52face.get(md5);
		if(f == null)
			return null;
		return getFacePath(f);
	}
	
	/**
	 * 得到缩略图表情路径
	 * 
	 * @param face
	 * @param g
	 * @return
	 */
	private String getSmallFacePath(Face face, FaceGroup g) {
		return LumaQQ.CUSTOM_FACE_DIR + g.getId() + '/' + face.getMd5() + "fixed.bmp";
	}
	
	/**
	 * 得到缩略图表情路径
	 * 
	 * @param face
	 * @return
	 */
	public String getSmallFacePath(Face face) {
		FaceGroup g = face2group.get(face);
		if(g == null)
			return null;
		
		return LumaQQ.CUSTOM_FACE_DIR + g.getId() + '/' + face.getMd5() + "fixed.bmp";
	}
	
	/**
	 * 得到缩略图表情路径
	 * 
	 * @param md5
	 * @return
	 */
	public String getSmallFacePath(String md5) {
		Face f = md52face.get(md5);
		if(f == null)
			return null;
		return getSmallFacePath(f);
	}
    
    /**
     * 得到接收到的表情文件路径
     * 
     * @param filename
     * @return
     */
    public String getReceivedFacePath(String filename) {
        if(filename == null)
            return null;
        return LumaQQ.CUSTOM_FACE_DIR + FaceConstant.RECEIVED_GROUP_ID + '/' + filename;
    }
    
    /**
     * 返回接收到的表情缩略图路径
     * 
     * @param md5
     * @return
     */
    public String getReceivedFaceThumbPath(String md5) {
    	if(md5 == null)
    		return null;
    	return LumaQQ.CUSTOM_FACE_DIR + FaceConstant.RECEIVED_GROUP_ID + '/' + md5 + "fixed.bmp";
    }
    
    /**
     * 得到接收到的表情文件路径
     * 
     * @param face
     * @return
     */
    public String getReceivedFacePath(Face face) {
        return LumaQQ.CUSTOM_FACE_DIR + FaceConstant.RECEIVED_GROUP_ID + '/' + face.getFilename();
    }
    
    /**
     * 得到接收到的表情文件路径
     * 
     * @param id
     * @return
     */
    public String getReceivedFacePath(int id) {
    	Face f = md52face.get(id2md5.get(id));
    	if(f == null)
    		return null;
    	return getReceivedFacePath(f);
    }
    
    /**
     * 得到自定义头像路径
     * 
     * @param id
     * @return
     */
    public String getCustomHeadPath(int id) {
    	Face f = md52face.get(id2md5.get(id));
    	if(f == null)
    		return null;
    	return LumaQQ.CUSTOM_FACE_DIR + FaceConstant.CUSTOM_HEAD_GROUP_ID + '/' + f.getFilename();
    }
    
    /**
     * 得到小自定义头像路径
     * 
     * @param id
     * @return
     */
    public String getSmallCustomHeadPath(int id) {
    	Face f = md52face.get(id2md5.get(id));
    	if(f == null)
    		return null;
    	return LumaQQ.CUSTOM_FACE_DIR + FaceConstant.CUSTOM_HEAD_GROUP_ID + '/' + f.getMd5() + "fixed.bmp";
    }
    
	/**
	 * 得到一个组
	 * 
	 * @param name
	 * 		组名
	 * @return
	 * 		如果没有这个组，返回null
	 */
	public FaceGroup getFaceGroup(String name) {
		return name2group.get(name);
	}
	
	/**
	 * @param name
	 * 		组名
	 * @return
	 * 		true表示这个组已经存在
	 */
	public boolean hasGroup(String name) {
		return getFaceGroup(name) != null;
	}
	
	/**
	 * 通过序号得到表情组
	 * 
	 * @param index
	 * @return
	 */
	public FaceGroup getFaceGroup(int index) {
	    if(faces == null)
	        return null;	    
	    List list = faces.getGroup();
	    if(index < 0 || index >= list.size())
	        return null;
	    return (FaceGroup)faces.getGroup().get(index);
	}
	
	/**
	 * 通过序号得到表情
	 * 
	 * @param group
	 * 		组序号
	 * @param index
	 * 		表情序号
	 * @return
	 * 		Face元素对象
	 */
	public Face getFace(int group, int index) {
	    FaceGroup g = getFaceGroup(group);
	    if(g == null)
	        return null;
	    List list = g.getFace();
	    if(index < 0 || index >= list.size())
	        return null;
	    return (Face)g.getFace().get(index);
	}
	
	/**
	 * 得到图片id
	 * 
	 * @param md5
	 * @return
	 */
	public int getFaceId(String md5) {
	    Integer id = md52id.get(md5);
	    if(id == null)
	    	return 0;
	    else
	    	return id;
	}
	
	/**
	 * 根据表情id得到Face对象
	 * 
	 * @param id
	 * 		id
	 * @return
	 * 		Face
	 */
	public Face getFace(int id) {
	    String md5 = id2md5.get(id);
	    return md52face.get(md5);
	}
	
	/**
	 * 检查是否存在这个face
	 * 
	 * @param md5
	 * 		图片的md5值
	 * @return
	 * 		true表示这个图片已经存在
	 */
	public boolean hasFace(String md5) {
	    return md52face.containsKey(md5);
	}
	
	/**
	 * 保存设置
	 */
	public void save() {
		if(dirty) {
			File file = new File(fileName);
			FaceUtil.save(file, faces);			
		}
	}
	
	/**
	 * 得到表情文件的长度
	 * 
	 * @param md5
	 * 		表情文件的md5
	 * @return
	 * 		长度
	 */
	public int getFaceLength(String md5) {
	    String path = getFacePath(md5);
	    File file = new File(path);
	    if(file.exists())
	        return (int)file.length();
	    else
	        return 0;
	}

	/**
	 * @param string
	 */
	public void setFileName(String string) {
		this.fileName = string;
	}

	public String getMd5ById(int code) {
		return id2md5.get(code);
	}

	public boolean isDirty() {
		return dirty;
	}
}
