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
package edu.tsinghua.lumaqq.qq.packets.in;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.Card;
import edu.tsinghua.lumaqq.qq.beans.CardStub;
import edu.tsinghua.lumaqq.qq.beans.ClusterInfo;
import edu.tsinghua.lumaqq.qq.beans.Member;
import edu.tsinghua.lumaqq.qq.beans.QQFriend;
import edu.tsinghua.lumaqq.qq.beans.QQOrganization;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.beans.SimpleClusterInfo;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 群命令的回复包，根据不同的子命令类型格式有所不同：
 * 创建群的回复包，格式为：
 * 1. 头部
 * 2. 命令类型，1字节，创建群是0x1
 * 3. 回复码，1字节，成功是0x0，如果成功则后面为
 *    1. 群内部ID，4字节，如果为0，表示创建失败
 *    2. 群外部ID，4字节，如果为0，表示创建失败
 * 4. 如果回复码不为0，则后面为出错信息
 * 5. 尾部
 * 
 * 创建临时群的回复包
 * 1. 头部
 * 2. 命令类型，1字节，0x30
 * 3. 回复码，1字节
 * 4. 临时群类型，1字节，0x01是多人对话，0x02是讨论组
 * 5. 父群内部ID，4字节
 * 6. 创建的临时群的内部ID，4字节
 * 7. 尾部
 * 
 * 得到群信息的回复包，格式为：
 * 头部
 * -------- 加密开始（会话密钥） ---------
 * 命令类型，1字节，得到群信息是0x4
 * 回复码，1字节，成功是0x0
 * --------- ClusterInfo Start ----------
 * 群内部ID，4字节
 * 群外部ID，4字节
 * 群类型，1字节
 * 未知的4字节
 * 创建者QQ号，4字节
 * 认证类型，1字节
 * 群分类，4字节，这是2004的分类法。2004只有4个分类
 * 未知的2字节
 * 群分类ID，4字节，这是2005的分类法，2005的分类最多有三层。每个分类有一个唯一的id
 * 未知的2字节
 * 未知的1字节
 * 未知4字节
 * 群version id, 4字节
 * 群名称长度，1字节
 * 群名称
 * 未知的两字节，全0
 * 群声明长度，1字节
 * 群声明
 * 群描述长度，1字节
 * 群描述
 * ------------ ClusterInfo End ----------
 * ------- Member Start (Repeat) --------
 * 群中成员的QQ号，4字节
 * 成员所属组织的序号，1字节，组织序号从1开始，如果为0，表示其不在某个组织中，一个成员只能在一个组织里面，组织和讨论组不同，讨论组可以看成是群中群，组织只是群成员的分类而已，它并不是一个群
 * 群成员的类型，是不是管理员之类的，1字节
 * ------- Member End --------
 * ---------- 加密结束 ----------
 * 尾部
 * 
 * 激活群的回复包，格式为：
 * 头部
 * -------- 加密开始（会话密钥） ---------
 * 命令类型，激活是0x5
 * 回复码，1字节，成功是0x0
 * 群的内部ID
 * ---------- 加密结束 ----------
 * 尾部
 * 
 * 搜索群的回复包，格式为：
 * 头部
 * --------- 加密开始(会话密钥) ----------
 * 命令类型，这里是0x06
 * 回复码，1字节，成功是0x0
 * 搜索方式，1字节
 * 群内部ID，4字节
 * 群外部ID，4字节
 * 群类型，1字节
 * 未知的4字节
 * 群创建者，4字节
 * 群分类，4字节，这是2004的分类法。2004只有4个分类
 * 群分类，4字节，这是2005分类法
 * 未知的2字节
 * 群名称长度，1字节
 * 群名称
 * 未知的两字节
 * 认证类型，1字节
 * 群简介长度，1字节
 * 群简介
 * 未知1字节
 * 一个未知id的长度，1字节
 * 未知id，可能和群共享有关系
 * --------- 加密结束 --------
 * 尾部
 * 
 * 请求成员信息的回复包，格式为
 * 头部
 * -------- 加密开始（会话密钥）----------
 * 命令类型，1字节，请求成员信息是0x0C
 * 回复码，1字节，成功是0x0
 * 群内部ID，4字节
 * ------- QQFriend Start (Repeat) ----------
 * 成员QQ号，4字节
 * 头像号，2字节
 * 年龄，1字节
 * 性别，1字节
 * 昵称长度，1字节
 * 昵称
 * 未知的2字节
 * 扩展标志，1字节
 * 通用标志，1字节
 * -------- QQFriend End ------------
 * ----------- 加密结束 -----------
 * 尾部
 * 
 * 得到临时群信息的回复包，格式为
 * 1. 头部
 * 2. 命令类型，1字节，0x33
 * 3. 回复码，1字节，成功是0x00
 * 4. 群类型，1字节
 * 5. 父群内部ID，4字节
 * 6. 临时群内部ID，4字节
 * 7. 创建者QQ号，4字节
 * 8. 未知的4字节，全0
 * 9. 群名称长度，1字节
 * 10. 群名称
 * 11. 群中成员的QQ号，4字节
 * 12. 成员所属组织的序号，1字节。对于临时群来说，没有群内组织的概念，所以这个字段无用
 * 13. 如果有更多成员，重复11-12部分
 * 14. 尾部
 * 
 * 退出群的回复，格式为：
 * 1. 头部
 * 2. 命令类型，1字节，退出是0x9
 * 3. 回复码，1字节，成功是0x0
 * 4. 群内部ID，4字节，应该是个非0值
 * 5. 尾部
 * 
 * 解散群的回复
 * 1. 头部
 * 2. 子命令，1字节，0x1D
 * 3. 回复码，1字节
 * 4. 群内部id，4字节
 * 5. 尾部
 * 
 * 得到在线成员的回复包，格式为：
 * 1. 头部
 * 2. 命令类型，1字节，请求成员信息是0x0B
 * 3. 回复码，1字节，成功是0x0
 * 4. 群内部ID，4字节
 * 5. 未知字节，0x3C
 * 6. 在线成员的qq号
 * 7. 如果有更多在线成员，重复6
 * 8. 尾部
 * 
 * 激活临时群的回复包
 * 1. 头部
 * 2. 命令类型，1字节，0x37
 * 3. 回复码，1字节
 * 4. 临时群类型，1字节
 * 5. 父群内部ID，4字节
 * 6. 临时群内部ID，4字节
 * 7. 成员QQ号，4字节
 * 8. 如果有更多成员，重复7部分
 * 9. 尾部
 * 
 * 请求加入群的回复包，格式为：
 * 1. 头部
 * 2. 命令类型，1字节，请求成员信息是0x07
 * 3. 回复码，1字节，成功是0x0
 * 4. 群内部ID，4字节
 * 5. 回复码，这个回复码是比较细的特定于join请求的回复，1字节
 * 6. 尾部
 * 
 * 请求加入群的认证信息回复包，没什么太大用处，就是表示服务器收到了，格式为：
 * 1. 头部
 * 2. 命令类型，这里是0x8
 * 3. 回复码，1字节，成功是0x0
 * 4. 群内部ID，4字节，如果为0表示出错
 * 5. 尾部
 * 
 * 修改群信息的回复包，格式为：
 * 1. 头部
 * 2. 命令类型，这里是0x03
 * 3. 回复码，1字节，成功是0x0
 * 4. 群内部ID，4字节
 * 5. 尾部
 * 
 * 修改群成员的回复包，格式为：
 * 1. 头部
 * 2. 命令类型，这里是0x03
 * 3. 回复码，1字节，成功是0x0
 * 4. 群内部ID，4字节
 * 5. 尾部
 * 
 * 发送群信息和发送扩展群信息的回复包
 * 1. 头部
 * 2. 命令类型，1字节，0x0A
 * 3. 回复码，1字节
 * 4. 群内部id，4字节
 * 5. 尾部
 * 
 * 发送临时群信息的回复包
 * 1. 头部
 * 2. 命令类型，1字节，0x35
 * 3. 回复码，1字节
 * 4. 临时群类型，1字节
 * 5. 父群内部ID，4字节
 * 6. 临时群内部ID，4字节
 * 7. 尾部
 * 
 * 退出临时群的回复包
 * 1. 头部
 * 2. 命令类型，1字节，0x32
 * 3. 回复码，1字节
 * 4. 临时群类型，1字节
 * 5. 父群内部ID，4字节
 * 6. 临时群内部ID，4字节
 * 7. 尾部
 * 
 * 修改临时群信息的回复包
 * 1. 头部
 * 2. 命令类型，这里是0x34
 * 3. 回复码，1字节，成功是0x00
 * 4. 临时群类型，1字节
 * 5. 父群内部ID，4字节
 * 6. 临时群内部ID，4字节
 * 7. 尾部
 * 
 * 修改临时群成员的回复包
 * 1. 头部
 * 2. 命令类型，这里是0x31
 * 3. 回复码，1字节，成功是0x00
 * 4. 临时群类型，1字节
 * 5. 父群内部ID，4字节
 * 6. 临时群内部ID，4字节
 * 7. 操作方式，1字节，0x01是添加，0x02是删除
 * 8. 操作的成员QQ号，4字节
 * 9. 如果有更多成员，重复8部分
 * 10. 尾部
 * 
 * 讨论组操作的回复包，子命令类型为0x02时（得到讨论组列表）
 * 1. 头部
 * 2. 命令类型，0x36
 * 3. 回复码，1字节
 * 4. 子命令类型，1字节，0x02
 * 5. 群内部id，4字节
 * 6. 群外部id，4字节
 * 7. 讨论组id，4字节
 * 8. 讨论组名称字节长度，1字节
 * 9. 讨论组名称
 * 10. 如果有更多讨论组，重复7-9部分
 * 11. 尾部 
 * 
 * 讨论组操作的回复包，子命令类型为0x01时（得到多人对话列表）
 * 1. 头部
 * 2. 命令类型，0x36
 * 3. 回复码，1字节
 * 4. 子命令类型，1字节，0x01
 * 5. 群内部id，4字节，为0
 * 6. 群外部id，4字节，为0
 * 7. 讨论组id，4字节
 * 8. 讨论组名称字节长度，1字节
 * 9. 讨论组名称
 * 10. 如果有更多讨论组，重复7-9部分
 * 11. 尾部 
 * 
 * 从服务器更新组织架构的回复包
 * 1. 头部
 * 2. 命令，1字节，0x12
 * 3. 回复码，1字节，0x00为成功 
 * 4. 群内部ID，4字节
 * 5. 未知1字节，0x00
 * 6. 组织Version ID，4字节，意义和群的Version ID相同。
 * 	  如果这个字段为0，表示没有组织，并且7-12部分不存在
 * 7. 组织个数，1字节
 * 8. 组织序号，1字节，从1开始
 * 9. 组织的层次关系，4字节。QQ的组织最多支持4层，4个字节一共32bit，第一层用了8位，
 *    后面的层用了6位，所以还有6位是保留未用的。举个例子说明一下这个字段的具体格式，
 *    假如这个字段的二进制表示为
 *    0000 0001 0000 1100 0101 0010 0100 0000
 *    那么得知，前8位0000 0001，值为1
 *    然后是0000 11，值为3
 *    然后是00 0101，值为5
 *    然后后0010 01，值为9，
 *    最后6位保留未用，
 *    所以这个组织位于第四层，它是父节点的第9个子组织，它的父节点是祖父节点的第5个子组织，
 *    它的祖父节点是曾祖父节点的第3个组织，它的曾祖父节点是群的第一个组织。
 *    我们要分清楚的是，组织的序号和层次号并不是一样的，也不是有对应关系的。所以目前来看，
 *    这个关系需要我们自己维护，以便查找组织
 * 10. 组织名称字节长度，1字节
 * 11. 组织名称
 * 12. 如果有更多组织，重复8-11部分
 * 13. 尾部 
 * 
 * 提交组织架构的回复包
 * 1. 头部
 * 2. 命令，1字节，0x11
 * 3. 回复码，1字节，0x00为成功 
 * 4. 群内部ID，4字节
 * 5. 组织Version ID，4字节
 * 6. 组织个数，2字节
 * 7. 组织序号，1字节
 * 8. 如果有更多组织，重复7部分
 * 9. 尾部
 * 
 * 提交成员分组情况的回复包
 * 1. 头部
 * 2. 命令，1字节，0x13
 * 3. 回复码，1字节，0x00为成功 
 * 4. 群内部ID，4字节
 * 5. 成员分组情况version id，4字节
 * 6. 尾部
 * 
 * 修改群名片回复包
 * 1. 头部
 * 2. 命令, 1字节，0x0E
 * 3. 回复码，1字节，0x00为成功 
 * 4. 群内部ID，4字节
 * 5. 自己的QQ号，4字节
 * 
 * 批量得到群名片真实姓名的回复包
 * 1. 头部
 * 2. 命令, 1字节，0x0F
 * 3. 回复码，1字节，0x00为成功 
 * 4. 群内部ID，4字节
 * 5. 群名片Version id， 4字节
 * 6. 下一个请求包的起始位置，4字节。这个字段如果为0，表示所有名片都已经得到
 *    如果不为0，表示起始记录数，比如一共有10条名片信息，这次得到了6条，还剩
 *    4条，那么这个字段就是0x00000006，因为下一条的序号是6(从0开始)
 * 7. 成员QQ号，4字节
 * 8. 真实姓名长度，1字节
 * 9. 真实姓名
 * 10. 如果有更多成员，重复7-9部分
 * 11. 尾部
 * 
 * 得到单个成员全部群名片信息的回复包
 * 1. 头部
 * 2. 命令, 1字节，0x0F
 * 3. 回复码，1字节，0x00为成功 
 * 4. 群内部ID，4字节
 * 5. 成员QQ号，4字节
 * 6. 真实姓名长度，1字节
 * 7. 真实姓名
 * 8. 性别索引，1字节，性别的顺序是'男', '女', '-'，所以男是0x00，等等
 * 9. 电话字符串长度，1字节
 * 10. 电话的字符串表示
 * 11. 电子邮件长度，1字节
 * 12. 电子邮件
 * 13. 备注长度，1字节
 * 14. 备注内容
 * 15. 尾部
 * 
 * 设置角色的回复包
 * 1. 头部
 * 2. 命令，1字节，0x1B
 * 3. 回复码，1字节
 * 4. 群内部ID，4字节
 * 5. 群version id, 4字节
 * 6. 被设置的QQ号，4字节
 * 7. 操作之后成员的角色，1字节
 * 8. 尾部
 * 
 * 转让角色的回复包
 * 1. 头部
 * 2. 命令，1字节，0x1B
 * 3. 回复码，1字节
 * 4. 群内部ID，4字节
 * 5. 转让到的QQ号，4字节
 * 6. 根据回复码不同，有:
 *    i. 3部分为0x00时，为群version id，4字节
 *    ii. 3部分为其他值时，为错误信息
 * 7. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterCommandReplyPacket extends BasicInPacket {
	// 公共字段
	/** 子命令 */
	public byte subCommand;
	/** 回复码 */
	public byte replyCode;
	/** 群内部id */
	public int clusterId;
	/** 群外部id */
	public int externalId;
	/** 群类型 */
	public byte type;
	/** 父群内部ID */
	public int parentClusterId;
	/** 群版本号 */
	public int versionId;	
	/** 如果某个包是对单个群成员进行操作，则使用这个字段保存QQ号 */
	public int memberQQ;
	
	/** 如果replyCode不是ok，那么这个字段有效，表示出错信息 */
	public String errorMessage;
	
	// 仅用于得到群信息的回复包，list的元素类型为ClusterInfo
	/** 群信息 */
	public ClusterInfo info;
	
	// 仅用于得到群信息和得到临时群成员列表的回复包
	/** 群成员列表，元素类型为Integer，包含了成员的QQ号 */
	public List<Member> members;
	
	// 仅用于得到群成员信息的回复包，list的元素类型是QQFriend
	/** 包含了群成员信息的列表，元素类型是QQFriend */
	public List<QQFriend> memberInfos;
	
	// 仅用于得到在线成员的回复包，list的元素类型是Integer
	/** 包含了在线成员列表，元素类型是Integer，表示成员的QQ号 */
	public List<Integer> onlineMembers;
	
	/** 子群列表，可能是讨论组也可能是多人对话 */
	public List<SimpleClusterInfo> subClusters;	
	/** 子群操作子类型 */
	public byte subClusterOpByte;
	
	// 仅用于加入群的回复包
	/** 加入群的回复 */
	public byte joinReply;
	
	// 仅用于搜索群的回复包，元素类型是ClusterInfo
	/** 搜索类型 */
	public byte searchType;
	/** 搜索到的群，类型是ClusterInfo */
	public List<ClusterInfo> clusters;
	
	// 用于更新组织架构的回复包和提交组织架构的回复包
	public int organizationVersionId;
	public int organizationCount;
	public List<QQOrganization> organizations;
	
	// 用于提交成员分组的回复包
	public int memberOrganziationVersionId;
	
	// 用于批量得到群名片真实姓名的回复包
	public List<CardStub> cardStubs;
	public int cardVersionId;
	public int nextStart;
	
	// 用于得到单个成员群名片的回复包
	public Card card;
	
	// 用于设置角色回复包
	public byte role;
	
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public ClusterCommandReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }   
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Cluster Command Reply";
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
        // 得到群操作命令和回复码
		subCommand = buf.get();
		replyCode = buf.get();
		// 判断命令类型
		switch(subCommand) {
			case QQ.QQ_CLUSTER_CMD_SEND_IM_EX:
				parseSendIMReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_SEND_TEMP_IM:
			    parseSendTempClusterIMReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_CREATE_CLUSTER:
				parseCreateReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_CREATE_TEMP:
			    parseCreateTempCluster(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_ACTIVATE_CLUSTER:
				parseActivateReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_MODIFY_MEMBER:
				parseModifyMemberReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_CLUSTER_INFO:
				parseGetInfoReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_EXIT_CLUSTER:
				parseExitReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_MEMBER_INFO:
				parseGetMemberInfoReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_ONLINE_MEMBER:
				parseGetOnlineMemberReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_JOIN_CLUSTER:
				parseJoinReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_JOIN_CLUSTER_AUTH:
				parseJoinAuthReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_MODIFY_CLUSTER_INFO:
				parseModifyInfoReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_SEARCH_CLUSTER:
				parseSearchReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_TEMP_INFO:
			    parseGetTempClusterInfoReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_EXIT_TEMP:
			    parseExitTempClusterReply(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_ACTIVATE_TEMP:
			    parseActivateTempCluster(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_SUB_CLUSTER_OP:
				parseSubClusterOp(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_UPDATE_ORGANIZATION:
				parseUpdateOrganization(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_COMMIT_ORGANIZATION:
				parseCommitOrganization(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_COMMIT_MEMBER_ORGANIZATION:
				parseCommitMemberOrganization(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_MODIFY_TEMP_INFO:
				parseModifyTempClusterInfo(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_MODIFY_CARD:
				parseModifyCard(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_CARD_BATCH:
				parseGetCardBatch(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_CARD:
				parseGetCard(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_SET_ROLE:
				parseSetRole(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_TRANSFER_ROLE:
				parseTransferRole(buf);
				break;
			case QQ.QQ_CLUSTER_CMD_DISMISS_CLUSTER:
				parseDismissCluster(buf);
				break;
		}			
		
		// 如果操作失败
		if(replyCode != QQ.QQ_REPLY_OK) {
			switch(subCommand) {
				case QQ.QQ_CLUSTER_CMD_TRANSFER_ROLE:
					clusterId = buf.getInt();
					memberQQ = buf.getInt();
					errorMessage = Util.getString(buf);
					break;
				case QQ.QQ_CLUSTER_CMD_SET_ROLE:
					clusterId = buf.getInt();
					errorMessage = Util.getString(buf);
					break;
				default:
					/* 操作失败 */
					errorMessage = Util.getString(buf);
					break;
			}
		}
    }

    /**
     * 处理解散群的回复包
     * 
     * @param buf
     */
    private void parseDismissCluster(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			clusterId = buf.getInt();
		}
	}

	/**
     * 处理转让角色的回复包
     * 
     * @param buf
     */
    private void parseTransferRole(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			clusterId = buf.getInt();
			memberQQ = buf.getInt();
			versionId = buf.getInt();
		}
	}

	/**
     * 处理设置群成员角色的回复包
     * 
     * @param buf
     */
    private void parseSetRole(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			clusterId = buf.getInt();
			versionId = buf.getInt();
			memberQQ = buf.getInt();
			role = buf.get();
		}
	}

	/**
     * 处理得到单个成员群名片回复包
     * 
     * @param buf
     */
    private void parseGetCard(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {	
			clusterId = buf.getInt();
			memberQQ = buf.getInt();
			card = new Card();
			card.readBean(buf);
		}
	}

	/**
     * 处理批量得到群名片真实姓名回复包
     * 
     * @param buf
     */
    private void parseGetCardBatch(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {	
			clusterId = buf.getInt();
			cardVersionId = buf.getInt();
			cardStubs = new ArrayList<CardStub>();
			nextStart = buf.getInt();
			while(buf.hasRemaining()) {
				CardStub stub = new CardStub();
				stub.readBean(buf);
				cardStubs.add(stub);
			}
		}
	}

	/**
     * 解析修改群名片回复包
     * 
     * @param buf
     */
    private void parseModifyCard(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {	
			clusterId = buf.getInt();
		}
	}

	/**
     * 处理修改临时群信息回复包
     * 
     * @param buf
     */
    private void parseModifyTempClusterInfo(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {	
			type = buf.get();
			parentClusterId = buf.getInt();
			clusterId = buf.getInt();
		}
	}

	/**
     * 处理提交成员分组情况的回复包
     * 
     * @param buf
     */
    private void parseCommitMemberOrganization(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {		
			clusterId = buf.getInt();
			memberOrganziationVersionId = buf.getInt();
		}
	}

	/**
     * 处理提交组织架构的回复包
     * 
     * @param buf
     */
    private void parseCommitOrganization(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {		
			clusterId = buf.getInt();
			organizationVersionId = buf.getInt();
			organizationCount = buf.getChar();			
		}
	}

	/**
     * 解析更新组织架构回复包
     * 
     * @param buf
     * 		ByteBuffer
     */
    private void parseUpdateOrganization(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {		
			organizations = new ArrayList<QQOrganization>(); 
			clusterId = buf.getInt();
			buf.get();			
			organizationVersionId = buf.getInt();
			if(organizationVersionId != 0) {
				organizationCount = buf.get() & 0xFF;
				while(buf.hasRemaining()) {
					QQOrganization org = new QQOrganization();
					org.readBean(buf);
					organizations.add(org);
				}
			}
		}
	}

	/**
     * 解析子群操作回复包
     * 
     * @param buf
     */
    private void parseSubClusterOp(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {		
			subClusterOpByte = buf.get();
			clusterId = buf.getInt();
			externalId = buf.getInt();
			subClusters = new ArrayList<SimpleClusterInfo>();
			while(buf.hasRemaining()) {
				SimpleClusterInfo s = new SimpleClusterInfo();
				s.readBean(buf);
				subClusters.add(s);
			}
		}
	}

	/**
     * @param buf
     */
    private void parseCreateTempCluster(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {		    
		    type = buf.get();		    
			parentClusterId = buf.getInt();
			clusterId = buf.getInt();
		}
    }

    /**
     * @param buf
     */
    private void parseExitTempClusterReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {		    
		    type = buf.get();		    
			parentClusterId = buf.getInt();
			clusterId = buf.getInt();
		}
    }

    /**
     * 处理发送临时群信息的回复包
     * 
     * @param buf
     */
    private void parseSendTempClusterIMReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {		    
		    type = buf.get();		    
			parentClusterId = buf.getInt();
			clusterId = buf.getInt();
		}
    }

    /**
	 * 处理修改群成员的回复包
	 * 
	 * @param buf
	 */
	private void parseModifyMemberReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK)
			clusterId = buf.getInt();
	}

	/**
	 * 处理发送消息的回复包
	 * 
	 * @param buf
	 */
	private void parseSendIMReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			clusterId = buf.getInt();
		}
	}

	/**
	 * 处理搜索群的回复包
	 * 
	 * @param buf
	 */
	private void parseSearchReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			searchType = buf.get();
			clusters = new ArrayList<ClusterInfo>();
			while(buf.hasRemaining()) {
				ClusterInfo ci = new ClusterInfo();
				ci.readClusterInfoFromSearchReply(buf);
				clusters.add(ci);
			}
		}
	}

	/**
	 * 处理修改群信息的回复包
	 * 
	 * @param buf
	 */
	private void parseModifyInfoReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			clusterId = buf.getInt();
		}
	}

	/**
	 * 处理认证信息的回复包，这个回复包只是个简单的回复，没什么用
	 * 
	 * @param buf
	 */
	private void parseJoinAuthReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			clusterId = buf.getInt();
		}
	}

	/**
	 * 处理加入群的回复包
	 * 
	 * @param buf
	 */
	private void parseJoinReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			clusterId = buf.getInt();
			joinReply = buf.get();
		}
	}

	/**
	 * 处理得到在线成员的回复包
	 * 
	 * @param buf
	 */
	private void parseGetOnlineMemberReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			// 内部ID
			clusterId = buf.getInt();
			// 未知字节，0x3C
			buf.get();
			// 成员信息
			onlineMembers = new ArrayList<Integer>();
			while(buf.hasRemaining())
				onlineMembers.add(buf.getInt());			
		}
	}

    /**
     * 处理激活临时群回复包
     * 
     * @param buf
     */
    private void parseActivateTempCluster(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
		    // 临时群类型
		    type = buf.get();
			// 父群内部ID
			parentClusterId = buf.getInt();
			// 临时群内部ID
			clusterId = buf.getInt();
			// 成员信息
			members = new ArrayList<Member>();
			while(buf.hasRemaining()) {
				Member member = new Member();
				member.qq = buf.getInt();
			    members.add(member);
			}
		}
    }

	/**
	 * 处理得到群成员信息的回复包
	 * 
	 * @param buf
	 */
	private void parseGetMemberInfoReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			clusterId = buf.getInt();
			// 成员信息
			memberInfos = new ArrayList<QQFriend>();
			while(buf.hasRemaining()) {
				QQFriend friend = new QQFriend();
				friend.readBean(buf);
				memberInfos.add(friend);
			}			
		}
	}

	/**
	 * 处理退出群的回复包
	 * 
	 * @param buf
	 */
	private void parseExitReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK)
			clusterId = buf.getInt();
	}

	/**
	 * 处理得到群信息的回复包
	 * 
	 * @param buf
	 */
	private void parseGetInfoReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			// 群信息
			info = new ClusterInfo();
			info.readClusterInfo(buf);
			clusterId = info.clusterId;
			externalId = info.externalId;
			// 读取成员列表
			members = new ArrayList<Member>();
			while(buf.hasRemaining()) {
				Member member = new Member();
				member.readBean(buf);
			    members.add(member);
			}			
		}
	}	

	/**
	 * 处理得到临时群信息的回复包
	 * 
     * @param buf
     */
    private void parseGetTempClusterInfoReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK) {
			// 群信息
			info = new ClusterInfo();
			info.readTempClusterInfo(buf);
			type = info.type;
			clusterId = info.clusterId;
			parentClusterId = info.externalId;
			// 读取成员列表
			members = new ArrayList<Member>();
			while(buf.hasRemaining()) {
				Member member = new Member();
				member.readTempBean(buf);
			    members.add(member);
			}			
		}
    }

	/**
	 * 处理激活群的回复包
	 * 
	 * @param buf
	 */
	private void parseActivateReply(ByteBuffer buf) {
		if(replyCode == QQ.QQ_REPLY_OK)
			clusterId = buf.getInt();
	}

	/**
	 * 解析创建群的回复包
	 * 
	 * @param buf
	 */
	private void parseCreateReply(ByteBuffer buf) {		
		if(replyCode == QQ.QQ_REPLY_OK) {
			clusterId = buf.getInt();
			externalId = buf.getInt();					
		} 
	}
}
