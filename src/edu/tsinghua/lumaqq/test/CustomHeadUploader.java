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
package edu.tsinghua.lumaqq.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;

/**
 * 自定义头像上传类
 *
 * @author luma
 */
public class CustomHeadUploader {
	public static void main(String[] args) {
		CustomHeadUploader uploader = new CustomHeadUploader();
		QQUser user = new QQUser(8422190, new byte[] { 0 });
		uploader.upload("D:\\Software\\eclipse3.2M4\\workspace\\LumaQQ_2006\\src\\edu\\tsinghua\\lumaqq\\resource\\image\\tipbackground.gif", user);
	}
	
	/**
	 * 上传
	 * 
	 * @param filename
	 */
	public void upload(String filename, QQUser user) {		
		HttpClient client = new HttpClient();
		HostConfiguration conf = new HostConfiguration();
		conf.setHost(QQ.QQ_SERVER_UPLOAD_CUSTOM_HEAD);
		client.setHostConfiguration(conf);
		PostMethod method = new PostMethod("/cgi-bin/cface/upload");
		method.addRequestHeader("Accept", "*/*");
		method.addRequestHeader("Pragma", "no-cache");
		
		StringPart uid = new StringPart("clientuin", String.valueOf(user.getQQ()));
		uid.setContentType(null);
		uid.setTransferEncoding(null);
		
		//StringPart clientkey = new StringPart("clientkey", "0D649E66B0C1DBBDB522CE9C846754EF6AFA10BBF1A48A532DF6369BBCEF6EE7");
		//StringPart clientkey = new StringPart("clientkey", "3285284145CC19EC0FFB3B25E4F6817FF3818B0E72F1C4E017D9238053BA2040");
		StringPart clientkey = new StringPart("clientkey", "2FEEBE858DAEDFE6352870E32E5297ABBFC8C87125F198A5232FA7ADA9EADE67");
		//StringPart clientkey = new StringPart("clientkey", "8FD643A7913C785AB612F126C6CD68A253F459B90EBCFD9375053C159418AF16");
//		StringPart clientkey = new StringPart("clientkey", Util.convertByteToHexStringWithoutSpace(user.getClientKey()));
		clientkey.setContentType(null);
		clientkey.setTransferEncoding(null);
		
		//StringPart sign = new StringPart("sign", "2D139466226299A73F8BCDA7EE9AB157E8D9DA0BB38B6F4942A1658A00BD4C1FEE415838810E5AEF40B90E2AA384A875");
		//StringPart sign = new StringPart("sign", "50F479417088F26EFC75B9BCCF945F35346188BB9ADD3BDF82098C9881DA086E3B28D56726D6CB2331909B62459E1E62");
		//StringPart sign = new StringPart("sign", "31A69198C19A7C4BFB60DCE352FE2CC92C9D27E7C7FEADE1CBAAFD988906981ECC0DD1782CBAE88A2B716F84F9E285AA");
		StringPart sign = new StringPart("sign", "68FFB636DE63D164D4072D7581213C77EC7B425DDFEB155428768E1E409935AA688AC88910A74C5D2D94D5EF2A3D1764");
		sign.setContentType(null);
		sign.setTransferEncoding(null);
		
		FilePart file;
		try {
			file = new FilePart("customfacefile", filename, new File(filename));
		} catch(FileNotFoundException e) {
			return;
		}
		
		Part[] parts = new Part[] {
				uid, clientkey, sign, file
		};
		MultipartRequestEntity entity = new MultipartRequestEntity(parts, method.getParams());
		
		method.setRequestEntity(entity);
		try {
			client.executeMethod(method);
			if(method.getStatusCode() == HttpStatus.SC_OK) {
				Header header = method.getResponseHeader("CFace-Msg");
				System.out.println(header.getValue());
				header = method.getResponseHeader("CFace-RetCode");
				System.out.println(header.getValue());
			}
		} catch(HttpException e) {
			return;
		} catch(IOException e) {
			return;
		} finally {
			method.releaseConnection();
		}
	}
}
