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

import static java.lang.Math.*;
import java.util.HashMap;
import java.util.Map;

import edu.tsinghua.lumaqq.widgets.rich.IRichContent;

/**
 * 缺省表情工具类
 * 
 * @author luma
 */
public class DefaultFace {
	public static final int[][] SEQ_CODE = new int[][] {
		{0, 65},
		{1, 66},
		{2, 67},
		{3, 68},
		{4, 69},
		{5, 70},
		{6, 71},
		{7, 72},
		{8, 73},
		{9, 74},
		{10, 75},
		{11, 76},
		{12, 77},
		{13, 78},
		{14, 79},
		{15, 115},
		{16, 116},
		{17, 117},
		{18, 118},
		{19, 119},
		{20, 138},
		{21, 139},
		{22, 140},
		{23, 141},
		{24, 142},
		{25, 143},
		{26, 120},
		{27, 121},
		{28, 122},
		{29, 123},
		{30, 144},
		{31, 145},
		{32, 146},
		{33, 147},
		{34, 148},
		{35, 149},
		{36, 150},
		{37, 151},
		{38, 152},
		{39, 153},
		{40, 89},
		{41, 90},
		{42, 92},
		{43, 88},
		{44, 87},
		{45, 85},
		{46, 124},
		{47, 125},
		{48, 126},
		{49, 127},
		{50, 154},
		{51, 155},
		{52, 96},
		{53, 103},
		{54, 156},
		{55, 157},
		{56, 158},
		{57, 94},
		{58, 159},
		{59, 137},
		{60, 128},
		{61, 129},
		{62, 130},
		{63, 98},
		{64, 99},
		{65, 100},
		{66, 101},
		{67, 102},
		{68, 131},
		{69, 104},
		{70, 132},
		{71, 133},
		{72, 134},
		{73, 135},
		{74, 107},
		{75, 110},
		{76, 111},
		{77, 112},
		{78, 136},
		{79, 160},
		{80, 80},
		{81, 81},
		{82, 82},
		{83, 83},
		{84, 84},
		{85, 86},
		{86, 91},
		{87, 93},
		{88, 95},
		{89, 97},
		{90, 105},
		{91, 106},
		{92, 108},
		{93, 109},
		{94, 113},
		{95, 114}
	};
	
	public static final String[][] ESCAPE = new String[][] {
		{"惊讶", ":o", "jy"},
		{"撇嘴", ":~", "pz"},
		{"色", ":*", "se"},
		{"发呆", ":|", "fd"},
		{"得意", "8-)", "dy"},
		{"流泪", ":<", "ll"},
		{"害羞", ":$", "hx"},
		{"闭嘴", ":x", "bz"},
		{"睡", ":z", "shui"},
		{"大哭", ":'(", "dk"},
		{"尴尬", ":-|", "gg"},
		{"发怒", ":@", "fn"},
		{"调皮", ":p", "tp"},
		{"呲牙", ":d", "cy"},
		{"微笑", ":)", "wx"},
		{"难过", ":(", "ng"},
		{"酷", ":+", "kuk"},
		{"非典", ":#", "feid"},
		{"抓狂", ":q", "zk"},
		{"吐", ":t", "tu"},
		{"偷笑", ";p", "tx"},
		{"可爱", ";-d", "ka"},
		{"白眼", ";d", "baiy"},
		{"傲慢", ";o", "am"},
		{"饥饿", ":g", "jie"},
		{"困", "|-)", "kun"},
		{"惊恐", ":!", "jk"},
		{"流汗", ":l", "lh"},
		{"憨笑", ":>", "hanx"},
		{"大兵", ":;", "db"},
		{"奋斗", ";f", "fendou"},
		{"咒骂", ":-s", "zhm"},
		{"疑问", "?", "yiw"},
		{"嘘...", ";x", "xu"},
		{"晕", ";@", "yun"},
		{"折磨", ":8", "zhem"},
		{"衰", ";!", "shuai"},
		{"骷髅", "!!!", "kl"},
		{"敲打", "xx", "qiao"},
		{"再见", "bye", "zj"},
		{"闪人", "go", "shan"},
		{"发抖", "shake", "fad"},
		{"爱情", "love", "aiq"},
		{"跳", "jump", "tiao"},
		{"找", "find", "zhao"},
		{"美眉", "&", "mm"},
		{"猪头", "pig", "zt"},
		{"猫咪", "cat", "maom"},
		{"小狗", "dog", "xg"},
		{"拥抱", "hug", "yb"},
		{"钱", "$", "qianc"},
		{"灯泡", "(!)", "dp"},
		{"酒杯", "cup", "bei"},
		{"蛋糕", "cake", "dg"},
		{"闪电", "li", "shd"},
		{"炸弹", "bome", "zhd"},
		{"刀", "kn", "dao"},
		{"足球", "footb", "zq"},
		{"音乐", "music", "yy"},
		{"便便", "shit", "bb"},
		{"咖啡", "coffee", "kf"},
		{"饭", "eat", "fan"},
		{"药丸", "pill", "yw"},
		{"玫瑰", "rose", "mg"},
		{"凋谢", "fade", "dx"},
		{"吻", "kiss", "wen"},
		{"爱心", "heart", "xin"},
		{"心碎", "break", "xs"},
		{"会议", "meeting", "hy"},
		{"礼物", "gift", "lw"},
		{"电话", "phone", "dh"},
		{"时间", "time", "sj"},
		{"邮件", "email", "yj"},
		{"电视", "tv", "ds"},
		{"太阳", "sun", "ty"},
		{"月亮", "moon", "yl"},
		{"强", "strong", "qiang"},
		{"弱", "weak", "ruo"},
		{"握手", "share", "ws"},
		{"胜利", "v", "shl"},
		{"多多", "<d>", "dd"},
		{"美女", "<j>", "mn"},
		{"汉良", "<h>", "hl"},
		{"毛毛", "<m>", "mamao"},
		{"q仔", "<qq>", "qz"},
		{"飞吻", "<l>", "fw"},
		{"怄火", "<o>", "oh"},
		{"白酒", "<b>", "bj"},
		{"汽水", "<u>", "qsh"},
		{"西瓜", "<w>", "xig"},
		{"下雨", "<!!>", "xy"},
		{"多云", "<~>", "duoy"},
		{"雪人", "<z>", "xr"},
		{"星星", "<*>", "xixing"},
		{"女", "<00>", "nv"},
		{"男", "<11>", "nan"}
	};
	
	private static final Map<String, Integer> escape2code = new HashMap<String, Integer>();
	private static final Map<Integer, Integer> code2seq = new HashMap<Integer, Integer>();
	private static final Map<Integer, Integer> seq2code = new HashMap<Integer, Integer>();
	private static int MAX_LENGTH;
	
	static {
		for(int[] i : SEQ_CODE) {
			seq2code.put(i[0], i[1]);
			code2seq.put(i[1], i[0]);
		}
		
		MAX_LENGTH = 0;
		int i = 0;
		for(String[] escapes : ESCAPE) {
			for(String escape: escapes) {
				escape2code.put(escape, SEQ_CODE[i][1]);
				MAX_LENGTH = max(MAX_LENGTH, escape.length());
			}
			i++;
		}
	}
	
	/**
	 * 替换文本中的表情转义字符串为richbox可以理解的形式
	 * 
	 * @param origin
	 * 		原始字符串
	 * @return
	 * 		RichBox可以理解的字符串形式
	 */
	public static String escapeFaces(String origin) {
		int i = origin.indexOf('/');
		if(i == -1)
			return origin;
		
		StringBuilder sb = new StringBuilder(origin);
		int code = -1;
		String maxStr;
		int escapeLength = 0;
		while(i != -1) {
			// 搜索转义字符串的匹配
			if(i + MAX_LENGTH + 1 > sb.length())
				maxStr = sb.substring(i + 1);
			else
				maxStr = sb.substring(i + 1, i + MAX_LENGTH + 1).toLowerCase();
			escapeLength = maxStr.length();
			do {		
				Integer iCode = escape2code.get(maxStr.substring(0, escapeLength--));
				if(iCode == null)
					code = -1;
				else
					code = iCode;
			} while(code == -1 && escapeLength > 1);
			
			// 如果找到了一个匹配
			if(code != -1) {
				escapeLength++;
				sb.delete(i, i + escapeLength + 1);
				sb.insert(i, (char)code);
				sb.insert(i, IRichContent.DEFAULT_FACE_TAG);
				i += 2;
				code = -1;
			} else 
				i++;
			
			// 找寻下一个缺省表情
			i = sb.indexOf("/", i);
		}
		return sb.toString();
	}

	/**
	 * 根据表情序号得到表情代码
	 * 
	 * @param seq
	 * 		序号
	 * @return
	 * 		代码
	 */
	public static int getFaceCode(int seq) {
		Integer code = seq2code.get(seq);
		if(code == null)
			return -1;
		else
			return code;
	}
	
	/**
	 * 根据表情代码得到表情序号
	 * 
	 * @param code
	 * 		代码
	 * @return
	 * 		序号
	 */
	public static int getFaceSequence(int code) {
		Integer seq = code2seq.get(code);
		if(seq == null)
			return -1;
		else
			return seq;
	}
}
