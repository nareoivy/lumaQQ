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
package edu.tsinghua.lumaqq.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;

/**
 * 管理图像资源，程序协议切换到2004，使用2004头像的选项不再使用，注释掉
 */
public class Resources {
    // Log
    private static Log log = LogFactory.getLog(Resources.class);
    
    // 缺省字体
    public static String LUMAQQ_DEFAULT_FONT = "SongTi9";
    
    // 一些路径常量
    private static final String HEAD_PATH_PREFIX = "/edu/tsinghua/lumaqq/resource/head/";
    private static final String SMALL_HEAD_PATH_PREFIX = "/edu/tsinghua/lumaqq/resource/smallhead/";
    private static final String CLUSTER_HEAD_PATH_PREFIX = "/edu/tsinghua/lumaqq/resource/clusterhead/";
    private static final String FACE_PATH_PREFIX = "/edu/tsinghua/lumaqq/resource/face/";
    private static final String ICON_PATH_PREFIX = "/edu/tsinghua/lumaqq/resource/icon";
    private static final String IMAGE_PATH_PREFIX = "/edu/tsinghua/lumaqq/resource/image";
    
    // resource id
    public static final int 
		icoOnline = 0,
    	icoAway = 1,
    	icoHidden = 2,
    	icoOffline = 3,
    	icoLumaQQ = 4,
    	icoAbout = 5,
    	icoExit = 6,
    	icoSysMsg = 7,
    	icoSysOpt = 8,
    	icoChangeUser = 9,
    	icoDiskDownload = 10,
		icoPersonInfo = 11,
		bmpLogin = 12,
		icoReply = 13,
		icoNoReply = 14,
		icoCustomReply = 15,
		icoMessage = 16,
		icoSearch = 17,
		icoLargeHead = 18,
		icoSmallHead = 19,
		icoDiskUpload = 20,
		icoDelGroup = 21,
		icoAddFriend = 22,
		icoDiskPassword = 23,
		icoShowBlackList = 24,
		icoRename = 25,
		icoSortFriend = 26,
		icoSharedDisk = 27,
		icoHideBlackList = 28,
		icoChecked = 29,
		icoFriendInfoManage = 30,
		icoDelFriend = 31,
		icoSendReceiveMessage = 32,
		icoFont = 33,
		icoSmiley = 34,
		icoQuickReply = 35,
		icoBlank = 36,
		icoDelete = 37,
		bmpSearch = 38,
		icoSendFile = 39,
		icoGroup = 40,
		icoUploadGroup = 41,
		icoDownloadGroup = 42,
		bmpTipBackground = 44,
		bmpDefaultQQShow = 45,
		bmpDownloading = 46,
		icoMessageManage = 47,
		icoTxtFile = 48,
		icoCopy = 49,
		icoCluster = 50,
		icoSearchCluster = 51,
		icoAddCluster = 52,
		icoFolder = 53, 
		icoRightArrow = 54,
		icoLeftArrow = 55,
		bmpOnlineTipBackground = 56,
		icoNotFound = 57,
		icoEnableFriendTip = 58,
		icoDisableFriendTip = 59,
		icoFriend = 60,
		icoApply = 61,
		icoDownloadRemark = 62,
		icoBack = 63,
		icoForward = 64,
		icoRefresh = 65,
		icoStop = 66,
		icoUpdate = 67, 
		icoFirefox = 68,
		bmpCheckUpdate = 69,
		icoSearchIp = 70,
		icoAwayDecoration = 71,
		icoColor = 72,
		icoIn = 73,
		icoOut = 74,
		icoFragment = 75,
		icoDebug = 76,
		icoMobileQQ = 77,
		icoMobile = 78,
		icoPalm = 79,
		icoCam = 80,
		icoMachine = 81,
		icoChatroom = 82,
		icoFirst = 83,
		icoAddAlbum = 84,
		icoMobileFriend = 85,
		icoFriendView = 86,
		icoSysOpt24 = 87,
		icoModifyPersonInfo24 = 88,
		icoChangeQQShow = 89,
		icoQQHome = 90,
		icoQQShowMall = 91,
		icoExport = 92,
		icoViewPersonInfo24 = 93,
		icoAboutTitleImage = 94,
		icoSearchWizard = 95,
		icoNext = 96,
		icoPrevious = 97,
		icoWarning = 98,
		icoDebugBytes = 99,
		icoDebugInput = 100,
		icoDebugPacket = 101,
		icoDebugFormat = 102,
		icoDebugSend = 103,
		icoHiddenDecoration = 104,
		icoClusterWizard = 105,
		bmpSmth = 106,
		icoCam24 = 107,
		icoSendFile32 = 108,
		icoMobile24 = 109,
		icoExport24 = 110,
		icoImport24 = 111,
		icoAddFaceGroup24 = 112,
		icoModifyFaceGroup24 = 113,
		icoDeleteFaceGroup24 = 114,
		bmpLogin2 = 115,
		icoBindQQ = 116,
		icoExpanded = 117,
		icoCollapsed = 118,
		icoTreeMode = 119,
		icoTraditionalMode = 120,
		icoOrganization = 121,
		icoDialog = 122,
		icoNewSubject = 123,
		bmpMenuDown = 124,
		bmpMenuHover = 125,
		bmpMenuNormal = 126,
		bmpToolbarBackground = 127,
		icoPin = 128,
		icoFlatExpand = 129,
		icoFlatCollapse = 130,
		icoClusterInfo = 131,
		icoClusterCard = 132,
		icoDiskView = 133,
		icoSysMenu = 134,
		icoClusterCreator = 135,
		icoClusterAdmin = 136,
		icoSun = 137,
		icoMoon = 138,
		icoStar = 139,
		icoCollapsed9 = 140,
		icoExpanded9 = 141,
		icoTool = 142,
		icoTempSessionIM = 143,
		icoClusterStockholder = 144,
		bmpProgress = 145,
		bmpCloseDown = 146,
		bmpCloseHover = 147,
		bmpCloseNormal = 148,
		bmpMinDown = 149,
		bmpMinHover = 150,
		bmpMinNormal = 151,
		bmpMaxDown = 152,
		bmpMaxHover = 153,
		bmpMaxNormal = 154,
		bmpRestoreDown = 155,
		bmpRestoreHover = 156,
		bmpRestoreNormal = 157,
		icoDazzleRing = 158,
		icoMobileDazzleRing = 159,
		icoBindDazzleRing = 160,
		icoLast = 161,
		icoTMMale = 162,
		icoTMFemale = 163,
		icoTMMale16 = 164,
		icoTMFemale16 = 165,
		bmpLumaQQ = 166,
		bmpCancelLoginNormal = 167,
		bmpCancelLoginHover = 168,
		bmpCancelLoginDown = 169,
		bmpPlusNormal = 170,
		bmpPlusHover = 171,
		bmpPlusDown = 172,
		icoSharedResource = 173,
		icoMyDisk = 174,
		icoMyAssistant = 175,
		icoMyDoc = 176,
		icoMyPicture = 177,
		icoMyMultimedia = 178,
		icoFile = 179,
		icoMyCustomHead = 180,
		icoMyAlbum = 181,
		icoMyNotebook = 182,
		icoAbort = 183,
		icoAbortAll = 184,
		bmpQQMM = 185,
		bmpQQGG = 186;

    // resource location
    public static final String[] resourceLocations = {
        ICON_PATH_PREFIX + "/online.gif",
        ICON_PATH_PREFIX + "/away.gif",
        ICON_PATH_PREFIX + "/hidden.gif",
        ICON_PATH_PREFIX + "/offline.gif",
        ICON_PATH_PREFIX + "/lumaqq.gif",
        ICON_PATH_PREFIX + "/about.gif",
        ICON_PATH_PREFIX + "/exit.gif",
        ICON_PATH_PREFIX + "/sysmsg.gif",
        ICON_PATH_PREFIX + "/sysoption.gif",
        ICON_PATH_PREFIX + "/changeuser.gif",
        ICON_PATH_PREFIX + "/diskdownload.gif",
		ICON_PATH_PREFIX + "/personinfo.gif",
        IMAGE_PATH_PREFIX + "/login.gif",
		ICON_PATH_PREFIX + "/reply.gif",
		ICON_PATH_PREFIX + "/noreply.gif",
		ICON_PATH_PREFIX + "/customreply.gif",
		ICON_PATH_PREFIX + "/message.gif",
		ICON_PATH_PREFIX + "/search.gif",
		ICON_PATH_PREFIX + "/largeface.gif",
		ICON_PATH_PREFIX + "/smallface.gif",
		ICON_PATH_PREFIX + "/diskupload.gif",
		ICON_PATH_PREFIX + "/delgroup.gif",
		ICON_PATH_PREFIX + "/addfriend.gif",
		ICON_PATH_PREFIX + "/diskpassword.gif",
		ICON_PATH_PREFIX + "/showblacklist.gif",
		ICON_PATH_PREFIX + "/rename.gif",
		ICON_PATH_PREFIX + "/sortfriend.gif",
		ICON_PATH_PREFIX + "/shareddisk.gif",
		ICON_PATH_PREFIX + "/hideblacklist.gif",
		ICON_PATH_PREFIX + "/checked.gif",
		ICON_PATH_PREFIX + "/friendinfomanage.gif",
		ICON_PATH_PREFIX + "/delfriend.gif",
		ICON_PATH_PREFIX + "/sendreceivemessage.gif",
		ICON_PATH_PREFIX + "/font.gif",
		ICON_PATH_PREFIX + "/smiley.gif",
		ICON_PATH_PREFIX + "/quickreply.gif",
		ICON_PATH_PREFIX + "/blank.gif",
		ICON_PATH_PREFIX + "/delete.gif",
		IMAGE_PATH_PREFIX + "/search.gif",
		ICON_PATH_PREFIX + "/sendfile.gif",
		ICON_PATH_PREFIX + "/group.gif",
		ICON_PATH_PREFIX + "/uploadgroup.gif",
		ICON_PATH_PREFIX + "/downloadgroup.gif",
		ICON_PATH_PREFIX + "/me.gif",
		IMAGE_PATH_PREFIX + "/tipbackground.gif",
		IMAGE_PATH_PREFIX + "/defaultqqshow.gif",
		IMAGE_PATH_PREFIX + "/downloading.gif",
		ICON_PATH_PREFIX + "/messagemanage.gif",
		ICON_PATH_PREFIX + "/txtfile.gif",
		ICON_PATH_PREFIX + "/copy.gif",
		ICON_PATH_PREFIX + "/cluster.gif",
		ICON_PATH_PREFIX + "/searchcluster.gif",
		ICON_PATH_PREFIX + "/addcluster.gif",
		ICON_PATH_PREFIX + "/folder.gif",
		ICON_PATH_PREFIX + "/rightarrow.gif",
		ICON_PATH_PREFIX + "/leftarrow.gif",
		IMAGE_PATH_PREFIX + "/onlinetipbackground.gif",
		ICON_PATH_PREFIX + "/notfound.gif",
		ICON_PATH_PREFIX + "/enablefriendtip.gif",
		ICON_PATH_PREFIX + "/disablefriendtip.gif",
		ICON_PATH_PREFIX + "/friend.gif",
		ICON_PATH_PREFIX + "/apply.gif",
		ICON_PATH_PREFIX + "/downloadremark.gif",
		ICON_PATH_PREFIX + "/back.gif",
		ICON_PATH_PREFIX + "/forward.gif",
		ICON_PATH_PREFIX + "/refresh.gif",
		ICON_PATH_PREFIX + "/stop.gif",
		ICON_PATH_PREFIX + "/update.gif",
		ICON_PATH_PREFIX + "/firefox.gif",
		IMAGE_PATH_PREFIX + "/checkupdate.gif",
		ICON_PATH_PREFIX + "/searchip.gif",
		ICON_PATH_PREFIX + "/awaydecoration.gif",
		ICON_PATH_PREFIX + "/colorsetting.gif",
		ICON_PATH_PREFIX + "/in.gif",
		ICON_PATH_PREFIX + "/out.gif",
		ICON_PATH_PREFIX + "/frag.gif",
		ICON_PATH_PREFIX + "/debug.gif",
		ICON_PATH_PREFIX + "/mobileqq.gif",
		ICON_PATH_PREFIX + "/mobile.gif",		
		ICON_PATH_PREFIX + "/palm.gif",
		ICON_PATH_PREFIX + "/cam.gif",
		ICON_PATH_PREFIX + "/machine.gif",
		ICON_PATH_PREFIX + "/chatroom.gif",
		ICON_PATH_PREFIX + "/first.gif",
		ICON_PATH_PREFIX + "/addalbum.gif",
		ICON_PATH_PREFIX + "/mobilefriend.gif",
		ICON_PATH_PREFIX + "/friendview.gif",
		ICON_PATH_PREFIX + "/sysoption_24.gif",
		ICON_PATH_PREFIX + "/modifypersoninfo_24.gif",
		ICON_PATH_PREFIX + "/changeqqshow.gif",
		ICON_PATH_PREFIX + "/qqhome.gif",
		ICON_PATH_PREFIX + "/qqshowmall.gif",
		ICON_PATH_PREFIX + "/export.gif", 
		ICON_PATH_PREFIX + "/viewpersoninfo_24.gif",
		ICON_PATH_PREFIX + "/abouttitleimage.gif",
		ICON_PATH_PREFIX + "/searchwizard.gif",
		ICON_PATH_PREFIX + "/next.gif",
		ICON_PATH_PREFIX + "/previous.gif",
		ICON_PATH_PREFIX + "/warning.gif",
		ICON_PATH_PREFIX + "/debug_bytes.gif",
		ICON_PATH_PREFIX + "/debug_input.gif",
		ICON_PATH_PREFIX + "/debug_packet.gif",
		ICON_PATH_PREFIX + "/debug_format.gif",
		ICON_PATH_PREFIX + "/debug_send.gif",
		ICON_PATH_PREFIX + "/hiddendecoration.gif",
		ICON_PATH_PREFIX + "/clusterwizard.gif",
		IMAGE_PATH_PREFIX + "/smth.gif",
		ICON_PATH_PREFIX + "/cam_24.gif",
		ICON_PATH_PREFIX + "/sendfile_32.gif",
		ICON_PATH_PREFIX + "/mobile_24.gif",
		ICON_PATH_PREFIX + "/export_24.gif",
		ICON_PATH_PREFIX + "/import_24.gif",
		ICON_PATH_PREFIX + "/addfacegroup_24.gif",
		ICON_PATH_PREFIX + "/modifyfacegroup_24.gif",
		ICON_PATH_PREFIX + "/deletefacegroup_24.gif",
		IMAGE_PATH_PREFIX + "/login2.gif",
		ICON_PATH_PREFIX + "/bindqq.gif",
		ICON_PATH_PREFIX + "/expanded.gif",
		ICON_PATH_PREFIX + "/collapsed.gif",
		ICON_PATH_PREFIX + "/treemode.gif",
		ICON_PATH_PREFIX + "/traditionalmode.gif",
		ICON_PATH_PREFIX + "/organization.gif",
		ICON_PATH_PREFIX + "/dialog.gif",
		ICON_PATH_PREFIX + "/newsubject.gif",
		IMAGE_PATH_PREFIX + "/menudown.gif",
		IMAGE_PATH_PREFIX + "/menuhover.gif",
		IMAGE_PATH_PREFIX + "/menunormal.gif",
		IMAGE_PATH_PREFIX + "/toolbarbackground.gif",
		ICON_PATH_PREFIX + "/pin.gif",
		ICON_PATH_PREFIX + "/flatexpand.gif",
		ICON_PATH_PREFIX + "/flatcollapse.gif",
		ICON_PATH_PREFIX + "/clusterinfo.gif",
		ICON_PATH_PREFIX + "/clustercard.gif",
		ICON_PATH_PREFIX + "/diskview.gif",
		ICON_PATH_PREFIX + "/sysmenu.gif",
		ICON_PATH_PREFIX + "/cluster_creator.gif",
		ICON_PATH_PREFIX + "/cluster_admin.gif",
		ICON_PATH_PREFIX + "/sun.gif",
		ICON_PATH_PREFIX + "/moon.gif",
		ICON_PATH_PREFIX + "/star.gif",
		ICON_PATH_PREFIX + "/collapsed_9.gif",
		ICON_PATH_PREFIX + "/expanded_9.gif",
		ICON_PATH_PREFIX + "/tool.gif",
		ICON_PATH_PREFIX + "/tempsessionim.gif",
		ICON_PATH_PREFIX + "/cluster_stockholder.gif",
		IMAGE_PATH_PREFIX + "/progress.gif",
		IMAGE_PATH_PREFIX + "/closedown.gif",
		IMAGE_PATH_PREFIX + "/closehover.gif",
		IMAGE_PATH_PREFIX + "/closenormal.gif",
		IMAGE_PATH_PREFIX + "/mindown.gif",
		IMAGE_PATH_PREFIX + "/minhover.gif",
		IMAGE_PATH_PREFIX + "/minnormal.gif",
		IMAGE_PATH_PREFIX + "/maxdown.gif",
		IMAGE_PATH_PREFIX + "/maxhover.gif",
		IMAGE_PATH_PREFIX + "/maxnormal.gif",
		IMAGE_PATH_PREFIX + "/restoredown.gif",
		IMAGE_PATH_PREFIX + "/restorehover.gif",
		IMAGE_PATH_PREFIX + "/restorenormal.gif",
		ICON_PATH_PREFIX + "/dazzlering.gif",
		ICON_PATH_PREFIX + "/mobile_dazzlering.gif",
		ICON_PATH_PREFIX + "/bind_dazzlering.gif",
		ICON_PATH_PREFIX + "/last.gif",
		ICON_PATH_PREFIX + "/tm_male.gif",
		ICON_PATH_PREFIX + "/tm_female.gif",
		ICON_PATH_PREFIX + "/tm_male_16.gif",
		ICON_PATH_PREFIX + "/tm_female_16.gif",
		IMAGE_PATH_PREFIX + "/lumaqq.gif",
		IMAGE_PATH_PREFIX + "/cancelloginnormal.gif",
		IMAGE_PATH_PREFIX + "/cancelloginhover.gif",
		IMAGE_PATH_PREFIX + "/cancellogindown.gif",
		IMAGE_PATH_PREFIX + "/plusnormal.gif",
		IMAGE_PATH_PREFIX + "/plushover.gif",
		IMAGE_PATH_PREFIX + "/plusdown.gif",
		ICON_PATH_PREFIX + "/sharedresource.gif",
		ICON_PATH_PREFIX + "/mydisk.gif",
		ICON_PATH_PREFIX + "/myassistant.gif",
		ICON_PATH_PREFIX + "/mydoc.gif",
		ICON_PATH_PREFIX + "/mypicture.gif",
		ICON_PATH_PREFIX + "/mymultimedia.gif",
		ICON_PATH_PREFIX + "/file.gif",
		ICON_PATH_PREFIX + "/mycustomhead.gif",
		ICON_PATH_PREFIX + "/myalbum.gif",
		ICON_PATH_PREFIX + "/mynotebook.gif",
		ICON_PATH_PREFIX + "/abort.gif",
		ICON_PATH_PREFIX + "/abortall.gif",
		IMAGE_PATH_PREFIX + "/QQMM.png",
		IMAGE_PATH_PREFIX + "/QQGG.png"
    };
    
    // 单一实例
    private static Resources holder = new Resources();
    
    // 2004头像最大索引
    public static final int QQ_2005_FACE_MAX_INDEX = 303;
    
    // 注册表
    private ImageRegistry registry;
    
    /**
     * 私有构造，singleton模式
     */
    private Resources() {
        // 创建图像注册表
        registry = new ImageRegistry(Display.getCurrent());
        // 注册缺省字体
    	FontData fd = new FontData("宋体", 9, SWT.NORMAL);
    	JFaceResources.getFontRegistry().put(LUMAQQ_DEFAULT_FONT, new FontData[] { fd });
    }

    /**
     * @return 实例
     */
    public static Resources getInstance() {
    	return holder;
    }
    
    /**
     * 返回Imageloader
     * 
     * @param resId
     * @return
     */
    public ImageLoader getImageLoader(int resId) {
    	ImageLoader loader = new ImageLoader();
    	InputStream is = Resources.class.getResourceAsStream(resourceLocations[resId]);
    	loader.load(is);
    	try {
			is.close();
		} catch(IOException e) {
		}
		return loader;
    }
    
    /**
     * @param imageId 图标id
     * @return 图标
     */
    public Image getImage(int resId) {
    	if(resId < 0 || resId >= resourceLocations.length) return null;
    	//System.out.println(resourceLocations[resId]+"resId:"+resId);
    	return getImage(resourceLocations[resId]);
    }    
    
    /**
     * 得到某个图标的灰度图像
     * 
     * @param resId
     * @return
     */
    public Image getGrayImage(int resId) {
    	// 尝试在registry中查找
    	String path = resourceLocations[resId] + ".offline";
    	Image img = registry.get(path);
    	// 尝试得到非灰度图像
    	if(img == null) {
    		img = getImage(resourceLocations[resId]);
    		if(img == null)
    			return null;
    		
    		// 生成灰度图像
    		img = new Image(Display.getCurrent(), img, SWT.IMAGE_GRAY);
    		registry.put(path, img);
    	}
    	return img;
    }
    
    /**
     * 得到程序图标
     * @param s 资源路径
     * @return Image
     */
    private Image getImage(String s) {
    	Image ret = registry.get(s);
    	if(ret == null) {
    		ret = createImageFromJar(s);
    		if(ret != null)
    			registry.put(s, ret);
    		return ret;
    	} else
    		return ret;
    }
    
    /**
     * 得到图像描述符
     * 
     * @param resId
     * 		资源ID
     * @return
     * 		ImageDescriptor
     */
    public ImageDescriptor getImageDescriptor(int resId) {
        ImageDescriptor ret = registry.getDescriptor(resourceLocations[resId]);
        if(ret == null) {
            ret = ImageDescriptor.createFromFile(LumaQQ.class, resourceLocations[resId]);
            if(ret != null)
                registry.put(resourceLocations[resId], ret);
            return ret;
        } else
            return ret;
    }
    
    /**
     * <pre>
     * 得到头像
     * </pre>
     * 
     * @param headId
     * 			头像ID
     * @return 
     * 			头像Image对象
     */
    public Image getHead(int headId) {
        // 检查头像范围，如果超出了，调整之
        if(headId < 0) headId = 0;
        if(headId >= QQ_2005_FACE_MAX_INDEX) 
            headId = QQ_2005_FACE_MAX_INDEX - 3;
        
        // 计算头像的主号码和次号码，对于2004头像来说，次号码无用
    	int major = headId / 3 + 1;
    	int minor = headId % 3 + 1;
    	// 获得头像
        String face = HEAD_PATH_PREFIX + major + ".gif";
        if(minor == 1)
            return getImage(face);
        else {
            // 检查cache中是否有不在线头像
            String faceOffline = face + ".offline";
            Image image = registry.get(faceOffline);
            if(image == null) {
                // 得到在线图像的灰度图像来用作不在线头像
                image = getImage(face);
                image = new Image(Display.getCurrent(), image, SWT.IMAGE_GRAY);
                registry.put(faceOffline, image);
            }
            return image;
        } 
    }
    
    /**
     * 得到头像小图标，没办法，table控件中的图像在linux下，居然不会自动缩小，只好再搞一套
     * 小图标
     * 
     * @param headId 
     * 			头像Id
     * @return 头像
     */
    public Image getSmallHead(int headId) {
        // 检查头像范围，如果超出了，调整之
        if(headId < 0) headId = 0;
        if(headId >= QQ_2005_FACE_MAX_INDEX) headId = QQ_2005_FACE_MAX_INDEX - 3;
        
    	int major = headId / 3 + 1;
    	int minor = headId % 3 + 1;
    	String face = SMALL_HEAD_PATH_PREFIX + major + "-" + minor + ".gif";
    	return getImage(face);
    }
    
    /**
     * 得到群组头像图标，如果索引不对则返回null
     * @param headId 群组头像，目前提供了18张
     * @return 群组头像图标，如果索引不对返回null
     */
    public Image getClusterHead(int headId) {
    	if(headId < 1 || headId > 6) return null;
    	String face = CLUSTER_HEAD_PATH_PREFIX + headId + ".gif";
    	return getImage(face);
    }
    
    /**
     * 得到群组头像小图标，如果索引不对则返回null，没办法，linux下面不会自动缩小
     * @param faceId
     * @return
     */
    public Image getSmallClusterHead(int faceId) {
    	if(faceId < 1 || faceId > 6) return null;
    	String face = CLUSTER_HEAD_PATH_PREFIX + faceId + "-1.gif";
    	return getImage(face);
    }
    
    /**
     * 通过表情的顺序号得到图像，顺序号是从0开始的整数，和code不同，code不是顺序的
     * 
     * @param seq
     * 		顺序号
     * @return
     * 		Image
     */
    public Image getFaceBySequence(int seq) {
    	String smiley = FACE_PATH_PREFIX + seq + ".gif";
    	return getImage(smiley);
    }
    
    /**
     * 根据表情代码得到表情的路径
     * 
     * @param code
     * 		表情代码
     * @return
     * 		路径
     */
    public String getFacePathByCode(int code) {
    	int seq = getFaceSequence(code);
    	if(seq == -1)
    		return null;
    	else
    		return FACE_PATH_PREFIX + seq + ".gif";
    }
    
    /**
     * 根据表情代码得到表情的图片
     * 
     * @param code
     * 		表情代码
     * @return
     * 		Image
     */
    public Image getFaceByCode(int code) {
        return getFaceBySequence(getFaceSequence(code));
    }
    
    /**
     * 根据表情代码得到ImageLoader
     * 
     * @param code
     * @return
     */
    public ImageLoader getFaceLoaderByCode(int code) {
    	return getFaceLoaderBySequence(getFaceSequence(code));
    }
    
    /**
     * 根据表情序号得到ImageLoader
     * 
     * @param seq
     * @return
     */
    public ImageLoader getFaceLoaderBySequence(int seq) {
    	String smiley = FACE_PATH_PREFIX + seq + ".gif";
    	try {
			ImageLoader loader = new ImageLoader();
			loader.load(Resources.class.getResourceAsStream(smiley));
			return loader;
		} catch(RuntimeException e) {
			return null;
		}
    }

    /**
     * 从文件创建Image对象
     * 
     * @param path
     *            the relative path to the icon 
     */
    private Image createImageFromJar(String path) {
        return createImageFromStream(Resources.class.getResourceAsStream(path));
    }
    
    /**
     * 从一个外部文件中创建一个图片
     * 
     * @param path
     * @return
     */
    private Image createImageFromFile(String path) {
        try {
            return createImageFromStream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    
    /**
     * 从一个输入流中创建一个图片
     * 
     * @param is
     * @return
     */
    private Image createImageFromStream(InputStream is) {
        try {
            if (is != null) {
                ImageData imageData = new ImageData(is);
                if (imageData != null) {
                    ImageData mask = imageData.getTransparencyMask();
                    return new Image(Display.getCurrent(), imageData, mask);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if(is != null)
                    is.close();
            } catch (IOException e1) {
            }
        }
        return null;
    }
    
    /**
     * 根据顺序号，得到表情代码
     * 
     * @param seq
     * 		顺序号
     * @return
     * 		表情代码，失败则返回-1
     */
    public int getFaceCode(int seq) {
    	return DefaultFace.getFaceCode(seq);
    }
    
    /**
     * 根据表情代码，得到顺序号
     * 
     * @param code
     * 		表情代码
     * @return
     * 		表情顺序号，失败返回1
     */
    public int getFaceSequence(int code) {
    	return DefaultFace.getFaceSequence(code);
    }
    
    /**
     * @return
     * 		表情个数
     */
    public int getFaceCount() {
    	return DefaultFace.SEQ_CODE.length;
    }
    
    /**
     * 得到自定义表情的缩略图
     * 
     * @param md5
     * 		自定义表情的md5
     * @return
     * 		Image对象
     */
    public Image getSmallCustomFace(String md5) {
        if(md5 == null)
            return null;
        String path = FaceRegistry.getInstance().getSmallFacePath(md5);
    	Image ret = registry.get(path);
    	if(ret == null) {
    		ret = createImageFromFile(path);
    		if(ret != null)
    			registry.put(path, ret);
    		return ret;
    	} else
    		return ret;
    }
    
    /**
     * 根据md5得到接收到的表情Image对象
     * 
     * @param filename
     * 		图片文件名
     * @return
     * 		Image对象，如果失败返回null
     */
    public Image getReceivedCustomFace(String filename) {
        if(filename == null)
            return null;
        String path = FaceRegistry.getInstance().getReceivedFacePath(filename);
    	Image ret = registry.get(path);
    	if(ret == null) {
    		ret = createImageFromFile(path);
    		if(ret != null)
    			registry.put(path, ret);
    		return ret;
    	} else
    		return ret;
    }
    
    /**
     * 根据id得到自定义头像Image对象
     * 
     * @param id
     * 		头像id
     * @param gray
     * 		true表示得到灰度图像
     * @return
     * 		Image对象，如果失败返回null
     */
    public Image getCustomHead(int id, boolean gray) {
        String path = FaceRegistry.getInstance().getCustomHeadPath(id);
        if(path == null)
        	return null;
        
        if(!gray) {
        	Image ret = registry.get(path);
        	if(ret == null) {
        		ret = createImageFromFile(path);
        		if(ret != null)
        			registry.put(path, ret);
        	}
        	return ret;
        } else{
            String grayKey = path + ".offline";
            Image ret = registry.get(grayKey);
            if(ret == null) {
            	ret = registry.get(path); 
            	if(ret == null) {
            		ret = createImageFromFile(path);
            		if(ret != null)
            			registry.put(path, ret);
            	}
            	if(ret != null) {
            		ret = new Image(Display.getCurrent(), ret, SWT.IMAGE_GRAY);
            		registry.put(grayKey, ret);            		
            	}
            }
            return ret;
        }
    }
    
    /**
     * 根据id得到小自定义头像Image对象
     * 
     * @param id
     * 		头像id
     * @param gray
     * 		true表示得到灰度图像
     * @return
     * 		Image对象，如果失败返回null
     */
    public Image getSmallCustomHead(int id, boolean gray) {
        String path = FaceRegistry.getInstance().getSmallCustomHeadPath(id);
        if(path == null)
        	return null;
        
        if(!gray) {
        	Image ret = registry.get(path);
        	if(ret == null) {
        		ret = createImageFromFile(path);
        		if(ret != null)
        			registry.put(path, ret);
        	}
        	return ret;
        } else{
            String grayKey = path + ".offline";
            Image ret = registry.get(grayKey);
            if(ret == null) {
            	ret = registry.get(path); 
            	if(ret == null) {
            		ret = createImageFromFile(path);
            		if(ret != null)
            			registry.put(path, ret);
            	}
            	if(ret != null) {
            		ret = new Image(Display.getCurrent(), ret, SWT.IMAGE_GRAY);
            		registry.put(grayKey, ret);            		
            	}
            }
            return ret;
        }
    }

	/**
	 * @return
	 * 		缺省字体
	 */
	public Font getDefaultFont() {
		return JFaceResources.getFont(LUMAQQ_DEFAULT_FONT);
	}
	
	/**
	 * @return
	 * 		缺省斜体
	 */
	public Font getItalicDefaultFont() {
		return JFaceResources.getFontRegistry().getItalic(LUMAQQ_DEFAULT_FONT);
	}
	
	/**
	 * 根据文件扩展名得到相应的图标
	 * 
	 * @param ext
	 * @return
	 */
	public Image getExtensionImage(String ext) {
		Image img = registry.get(ext);
		if(img != null)
			return img;
		else {
			try {
				Program p = Program.findProgram(ext);
				if(p.getImageData() == null)
					return getImage(icoFile);
				else {
					img = new Image(Display.getCurrent(), p.getImageData());
					registry.put(ext, img);
					return img;					
				}
			} catch(RuntimeException e) {
				return null;
			}					
		}
	}
	
	/**
	 * 根据文件系统路径得到imageloader
	 * 
	 * @param path
	 * @return
	 */
	public ImageLoader getImageLoader(String path) {
		try {
			ImageLoader loader = new ImageLoader();
			loader.load(path);
			return loader;
		} catch(RuntimeException e) {
			return null;
		}
	}
}
