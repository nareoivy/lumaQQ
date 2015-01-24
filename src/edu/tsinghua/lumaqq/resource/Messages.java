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

import edu.tsinghua.lumaqq.LumaQQ;

import org.eclipse.osgi.util.NLS;

/**
 * 资源类
 * 
 * @author luma
 */
public class Messages extends NLS {
	private static String BUNDLE_NAME;
	
	// 这个版本的更新时间，格式为 yyyy-mm-dd hh-mm，这个域用在检查更新功能中，它标识了当前的版本
	public static String update_time;

	// 缺省字体
	public static String default_font;
	
	// 不限
	public static String unlimited;
	
	// url
	public static String url_qqshow_mall;
	public static String url_qqhome;
	public static String url_alumni;
	public static String url_cluster_community;
	public static String url_apply;
	public static String url_chatroom;
	public static String url_game;
	public static String url_lumaqq;
	public static String url_password_protect;
	public static String url_update;
	public static String url_search_cluster;

	// 界面上的一些按钮文字
	public static String status_hidden;
	public static String status_away;
	public static String status_online;
	public static String status_offline;
	
	// 缺省的表情组
	public static String face_group_default;
	public static String face_group_recv;
	public static String face_group_custom_head;

	// 用于文件传输的一些提示信息
	public static String text_send_file;
	public static String text_receive_file;
	public static String text_accept_receive_file;
	public static String text_reject_receive_file;
	public static String text_accept_send_file;
	public static String text_reject_send_file;
	public static String text_udp_connected;
	public static String text_receive_file_finished;
	public static String text_receive_file_finished_hint;
	public static String text_send_file_finished;
	public static String text_cancel_transfer_file;
	public static String text_send_file_error;
	public static String text_error_open_local_file;
	public static String text_file_md5_correct;
	public static String text_file_md5_incorrect;
	public static String text_too_more_faces;
	
	// 通用提示
	public static String hint_login;
	public static String hint_get_friend;
	public static String hint_download_group;

	// 界面上一些按钮的tooltip
	public static String tooltip_button_sysmsg;
	public static String tooltip_button_sms;
	public static String tooltip_button_sysmenu;
	public static String tooltip_button_font;
	public static String tooltip_button_face;
	public static String tooltip_button_sendfile;
	public static String tooltip_button_cancel_sendfile;
	public static String tooltip_button_reject_sendfile;
	public static String tooltip_button_accept_sendfile;
	public static String tooltip_button_3721;
	public static String tooltip_button_search;
	public static String tooltip_button_hide;
	public static String tooltip_button_show;
	public static String tooltip_button_chatroom;
	public static String tooltip_button_qqgame;
	public static String tooltip_button_color;
	public static String tooltip_button_animation;
	public static String tooltip_button_disable_animation;

	// 按钮
	public static String button_modify;
	public static String button_ok;
	public static String button_cancel;
	public static String button_close;
	public static String button_stop;
	public static String button_update;
	public static String button_add;
	public static String button_delete;
	public static String button_add_dot;
	public static String button_up;
	public static String button_down;
	public static String button_move_dot;
	public static String button_export;
	public static String button_import;
	public static String button_login;
	public static String button_first;
	public static String button_last;
	public static String button_previous;
	public static String button_next;
	public static String button_send;
	public static String button_next_tab;
	public static String button_close_accel;
	public static String button_send_accel;
	public static String button_next_accel;
	public static String button_record_accel;
	public static String button_search;
	public static String button_setting;
	public static String button_resume;
	public static String button_overwrite;
	
	// 验证码输入窗口
	public static String verify_title;
	public static String verify_code_image;
	public static String verify_input;
	public static String verify_refresh;
	
	// 崩溃报告窗口
	public static String crash_title;
	public static String crash_copy;
	public static String crash_hint;
	public static String crash_restart;
	public static String crash_close;
	
	// 聊天记录导出格式选择对话框
	public static String record_export_title;
	public static String record_export_filetype;
	
	// 上次登录信息窗口
	public static String last_login_title;
	public static String last_login_setting;
	public static String last_login_tip;
	
	// 天气预报窗口
	public static String weather_title;
	public static String weather_ip;
	public static String weather_query;
	public static String weather_stop;
	public static String weather_fail;
	public static String weather_temperature;
	public static String weather_wind;
	
	// 信息管理器窗口
	public static String info_title;
	public static String info_search;
	public static String info_record;
	public static String info_friend;
	public static String info_scope;
	public static String info_all;
	public static String info_key;
	public static String info_sys_msg;
	public static String info_date;
	public static String info_time;
	public static String info_sender;
	public static String info_receiver;
	public static String info_content;
	public static String info_page;
	public static String info_groups;
	public static String info_cluster;

	// 自定义表情管理窗口的文字
	public static String face_title;
	public static String face_add_group;
	public static String face_modify_group;
	public static String face_delete_group;
	public static String face_id;
	public static String face_image;
	public static String face_new_group_title;
	public static String face_new_group_message;
	public static String face_cfc;
	public static String face_eip;

	// About对话框的文字
	public static String about_title;
	public static String about_version;
	public static String about_message;
	public static String about_group_contributor;
	public static String about_group_thanks;

	// 登录对话框的文字
	public static String login_title;
	public static String login_qq_number;
	public static String login_qq_password;
	public static String login_remember_password;
	public static String login_hidden;
	public static String login_auto;
	public static String login_network;
	public static String login_proxy_type;
	public static String login_no_proxy;
	public static String login_socks5;
	public static String login_http;
	public static String login_proxy_server;
	public static String login_proxy_port;
	public static String login_proxy_username;
	public static String login_proxy_password;

	// 图像选择窗口的文字
	public static String image_group_man;
	public static String image_group_woman;
	public static String image_group_pet;
	public static String image_group_qqtang;
	public static String image_group_cluster;
	public static String image_group_default;
	public static String image_link_add;
	
	// 临时群和组织信息修改窗口
	public static String member_edit_title;
	public static String member_edit_title_add;
	public static String member_edit_name;
	public static String member_edit_list;
	public static String member_edit_qq;
	public static String member_edit_nick;
	public static String member_edit_gender;
	public static String member_edit_age;

	// 群资料窗口的文字
	public static String cluster_info_title_modify;
	public static String cluster_info_title_view;
	public static String cluster_info_page_basic;
	public static String cluster_info_page_members;
	public static String cluster_info_page_message;
	public static String cluster_info_basic_id;
	public static String cluster_info_basic_creator;
	public static String cluster_info_basic_name;
	public static String cluster_info_basic_category;
	public static String cluster_info_basic_notice;
	public static String cluster_info_basic_description;
	public static String cluster_info_basic_auth;
	public static String cluster_info_basic_no_auth;
	public static String cluster_info_basic_need_auth;
	public static String cluster_info_basic_no_add;
	public static String cluster_info_members_qq;
	public static String cluster_info_members_nick;
	public static String cluster_info_members_gender;
	public static String cluster_info_members_age;
	public static String cluster_info_members_modify;
	public static String cluster_info_members_add;
	public static String cluster_info_members_set_admin;
	public static String cluster_info_members_unset_admin;
	public static String cluster_info_members_transfer_role;
	public static String cluster_info_message_group;
	public static String cluster_info_message_accept;
	public static String cluster_info_message_eject;
	public static String cluster_info_message_show_number;
	public static String cluster_info_message_record;
	public static String cluster_info_message_block;

	// 用户资料窗口的文字
	public static String user_info_title_modify;
	public static String user_info_title_view;
	public static String user_info_page_basic;
	public static String user_info_page_qqshow;
	public static String user_info_page_contact;
	public static String user_info_page_security;
	public static String user_info_page_remark;
	public static String user_info_page_card;
	public static String user_info_basic_qq;
	public static String user_info_basic_nick;
	public static String user_info_basic_gender;
	public static String user_info_basic_signature;
	public static String user_info_basic_level;
	public static String user_info_basic_name;
	public static String user_info_basic_college;
	public static String user_info_basic_occupation;
	public static String user_info_basic_homepage;
	public static String user_info_basic_age;
	public static String user_info_basic_zodiac;
	public static String user_info_basic_horoscope;
	public static String user_info_basic_blood;
	public static String user_info_basic_intro;
	public static String user_info_group_qqshow;
	public static String user_info_group_contact;
	public static String user_info_group_auth;
	public static String user_info_group_up_down;
	public static String user_info_label_go_qqshow_mall;
	public static String user_info_label_change_qqshow;
	public static String user_info_label_qqshow_home;
	public static String user_info_label_refresh_qqshow;
	public static String user_info_contact_country;
	public static String user_info_contact_province;
	public static String user_info_contact_city;
	public static String user_info_contact_zipcode;
	public static String user_info_contact_email;
	public static String user_info_contact_address;
	public static String user_info_contact_mobile;
	public static String user_info_contact_telephone;
	public static String user_info_contact_opencontact_label;
	public static String user_info_contact_opencontact_open;
	public static String user_info_contact_opencontact_onlyfriend;
	public static String user_info_contact_opencontact_close;
	public static String user_info_security_changepassword;
	public static String user_info_security_oldpassword;
	public static String user_info_security_newpassword;
	public static String user_info_security_confirmpassword;
	public static String user_info_security_changepassword_hint;
	public static String user_info_security_openhp;
	public static String user_info_security_openhp_anyone;
	public static String user_info_security_openhp_auth;
	public static String user_info_security_openhp_never;
	public static String user_info_remark_name;
	public static String user_info_remark_zipcode;
	public static String user_info_remark_telephone;
	public static String user_info_remark_mobile;
	public static String user_info_remark_email;
	public static String user_info_remark_address;
	public static String user_info_remark_note;
	public static String user_info_remark_button_download;
	public static String user_info_remark_button_upload;
	public static String user_info_remark_auto_upload;
	public static String user_info_card_name;
	public static String user_info_card_gender;
	public static String user_info_card_phone;
	public static String user_info_card_email;
	public static String user_info_card_remark;

	// 一些预定义的用户资料
	public static String user_info_basic_gender_male;
	public static String user_info_basic_gender_female;
	public static String user_info_basic_gender_ladyman;
	public static String user_info_basic_gender_null;
	public static String user_info_basic_gender_both;
	public static String user_info_basic_zodiac_0;
	public static String user_info_basic_zodiac_1;
	public static String user_info_basic_zodiac_2;
	public static String user_info_basic_zodiac_3;
	public static String user_info_basic_zodiac_4;
	public static String user_info_basic_zodiac_5;
	public static String user_info_basic_zodiac_6;
	public static String user_info_basic_zodiac_7;
	public static String user_info_basic_zodiac_8;
	public static String user_info_basic_zodiac_9;
	public static String user_info_basic_zodiac_10;
	public static String user_info_basic_zodiac_11;
	public static String user_info_basic_zodiac_12;
	public static String user_info_basic_blood_0;
	public static String user_info_basic_blood_1;
	public static String user_info_basic_blood_2;
	public static String user_info_basic_blood_3;
	public static String user_info_basic_blood_4;
	public static String user_info_basic_blood_5;
	public static String user_info_basic_occupation_number;
	public static String user_info_basic_occupation_1;
	public static String user_info_basic_occupation_2;
	public static String user_info_basic_occupation_3;
	public static String user_info_basic_occupation_4;
	public static String user_info_basic_occupation_5;
	public static String user_info_basic_occupation_6;
	public static String user_info_basic_occupation_7;
	public static String user_info_basic_occupation_8;
	public static String user_info_basic_occupation_9;
	public static String user_info_basic_occupation_10;
	public static String user_info_basic_occupation_11;
	public static String user_info_basic_occupation_12;
	public static String user_info_basic_occupation_13;
	public static String user_info_basic_occupation_14;
	public static String user_info_basic_occupation_15;
	public static String user_info_basic_occupation_16;
	public static String user_info_basic_occupation_17;
	public static String user_info_basic_occupation_18;
	public static String user_info_basic_occupation_19;
	public static String user_info_basic_occupation_20;
	public static String user_info_basic_horoscope_0;
	public static String user_info_basic_horoscope_1;
	public static String user_info_basic_horoscope_2;
	public static String user_info_basic_horoscope_3;
	public static String user_info_basic_horoscope_4;
	public static String user_info_basic_horoscope_5;
	public static String user_info_basic_horoscope_6;
	public static String user_info_basic_horoscope_7;
	public static String user_info_basic_horoscope_8;
	public static String user_info_basic_horoscope_9;
	public static String user_info_basic_horoscope_10;
	public static String user_info_basic_horoscope_11;
	public static String user_info_basic_horoscope_12;
	public static String user_info_basic_age_number;
	public static String user_info_basic_age_1;
	public static String user_info_basic_age_2;
	public static String user_info_basic_age_3;
	public static String user_info_basic_age_4;
	public static String user_info_basic_age_5;
	public static String user_info_contact_province_number;
	public static String user_info_contact_province_1;
	public static String user_info_contact_province_2;
	public static String user_info_contact_province_3;
	public static String user_info_contact_province_4;
	public static String user_info_contact_province_5;
	public static String user_info_contact_province_6;
	public static String user_info_contact_province_7;
	public static String user_info_contact_province_8;
	public static String user_info_contact_province_9;
	public static String user_info_contact_province_10;
	public static String user_info_contact_province_11;
	public static String user_info_contact_province_12;
	public static String user_info_contact_province_13;
	public static String user_info_contact_province_14;
	public static String user_info_contact_province_15;
	public static String user_info_contact_province_16;
	public static String user_info_contact_province_17;
	public static String user_info_contact_province_18;
	public static String user_info_contact_province_19;
	public static String user_info_contact_province_20;
	public static String user_info_contact_province_21;
	public static String user_info_contact_province_22;
	public static String user_info_contact_province_23;
	public static String user_info_contact_province_24;
	public static String user_info_contact_province_25;
	public static String user_info_contact_province_26;
	public static String user_info_contact_province_27;
	public static String user_info_contact_province_28;
	public static String user_info_contact_province_29;
	public static String user_info_contact_province_30;
	public static String user_info_contact_province_31;
	public static String user_info_contact_province_32;
	public static String user_info_contact_province_33;
	public static String user_info_contact_province_34;
	public static String user_info_contact_province_35;
	public static String user_info_contact_city_1_number;
	public static String user_info_contact_city_1_1;
	public static String user_info_contact_city_1_2;
	public static String user_info_contact_city_1_3;
	public static String user_info_contact_city_1_4;
	public static String user_info_contact_city_1_5;
	public static String user_info_contact_city_1_6;
	public static String user_info_contact_city_1_7;
	public static String user_info_contact_city_1_8;
	public static String user_info_contact_city_1_9;
	public static String user_info_contact_city_1_10;
	public static String user_info_contact_city_1_11;
	public static String user_info_contact_city_1_12;
	public static String user_info_contact_city_1_13;
	public static String user_info_contact_city_1_14;
	public static String user_info_contact_city_1_15;
	public static String user_info_contact_city_1_16;
	public static String user_info_contact_city_1_17;
	public static String user_info_contact_city_1_18;
	public static String user_info_contact_city_1_19;
	public static String user_info_contact_city_1_20;
	public static String user_info_contact_city_1_21;
	public static String user_info_contact_city_1_22;
	public static String user_info_contact_city_1_23;
	public static String user_info_contact_city_1_24;
	public static String user_info_contact_city_2_number;
	public static String user_info_contact_city_2_1;
	public static String user_info_contact_city_3_number;
	public static String user_info_contact_city_3_1;
	public static String user_info_contact_city_4_number;
	public static String user_info_contact_city_4_1;
	public static String user_info_contact_city_5_number;
	public static String user_info_contact_city_5_1;
	public static String user_info_contact_city_6_number;
	public static String user_info_contact_city_6_1;
	public static String user_info_contact_city_6_2;
	public static String user_info_contact_city_6_3;
	public static String user_info_contact_city_6_4;
	public static String user_info_contact_city_6_5;
	public static String user_info_contact_city_6_6;
	public static String user_info_contact_city_6_7;
	public static String user_info_contact_city_6_8;
	public static String user_info_contact_city_6_9;
	public static String user_info_contact_city_6_10;
	public static String user_info_contact_city_6_11;
	public static String user_info_contact_city_7_number;
	public static String user_info_contact_city_7_1;
	public static String user_info_contact_city_7_2;
	public static String user_info_contact_city_7_3;
	public static String user_info_contact_city_7_4;
	public static String user_info_contact_city_7_5;
	public static String user_info_contact_city_7_6;
	public static String user_info_contact_city_7_7;
	public static String user_info_contact_city_7_8;
	public static String user_info_contact_city_7_9;
	public static String user_info_contact_city_7_10;
	public static String user_info_contact_city_7_11;
	public static String user_info_contact_city_8_number;
	public static String user_info_contact_city_8_1;
	public static String user_info_contact_city_8_2;
	public static String user_info_contact_city_8_3;
	public static String user_info_contact_city_8_4;
	public static String user_info_contact_city_8_5;
	public static String user_info_contact_city_8_6;
	public static String user_info_contact_city_8_7;
	public static String user_info_contact_city_8_8;
	public static String user_info_contact_city_8_9;
	public static String user_info_contact_city_8_10;
	public static String user_info_contact_city_8_11;
	public static String user_info_contact_city_8_12;
	public static String user_info_contact_city_9_number;
	public static String user_info_contact_city_9_1;
	public static String user_info_contact_city_9_2;
	public static String user_info_contact_city_9_3;
	public static String user_info_contact_city_9_4;
	public static String user_info_contact_city_9_5;
	public static String user_info_contact_city_9_6;
	public static String user_info_contact_city_9_7;
	public static String user_info_contact_city_9_8;
	public static String user_info_contact_city_9_9;
	public static String user_info_contact_city_9_10;
	public static String user_info_contact_city_9_11;
	public static String user_info_contact_city_9_12;
	public static String user_info_contact_city_9_13;
	public static String user_info_contact_city_9_14;
	public static String user_info_contact_city_10_number;
	public static String user_info_contact_city_10_1;
	public static String user_info_contact_city_10_2;
	public static String user_info_contact_city_10_3;
	public static String user_info_contact_city_10_4;
	public static String user_info_contact_city_10_5;
	public static String user_info_contact_city_10_6;
	public static String user_info_contact_city_10_7;
	public static String user_info_contact_city_10_8;
	public static String user_info_contact_city_10_9;
	public static String user_info_contact_city_11_number;
	public static String user_info_contact_city_11_1;
	public static String user_info_contact_city_11_2;
	public static String user_info_contact_city_11_3;
	public static String user_info_contact_city_11_4;
	public static String user_info_contact_city_11_5;
	public static String user_info_contact_city_11_6;
	public static String user_info_contact_city_11_7;
	public static String user_info_contact_city_11_8;
	public static String user_info_contact_city_11_9;
	public static String user_info_contact_city_11_10;
	public static String user_info_contact_city_11_11;
	public static String user_info_contact_city_11_12;
	public static String user_info_contact_city_11_13;
	public static String user_info_contact_city_12_number;
	public static String user_info_contact_city_12_1;
	public static String user_info_contact_city_12_2;
	public static String user_info_contact_city_12_3;
	public static String user_info_contact_city_12_4;
	public static String user_info_contact_city_12_5;
	public static String user_info_contact_city_12_6;
	public static String user_info_contact_city_12_7;
	public static String user_info_contact_city_12_8;
	public static String user_info_contact_city_12_9;
	public static String user_info_contact_city_12_10;
	public static String user_info_contact_city_12_11;
	public static String user_info_contact_city_12_12;
	public static String user_info_contact_city_12_13;
	public static String user_info_contact_city_13_number;
	public static String user_info_contact_city_13_1;
	public static String user_info_contact_city_13_2;
	public static String user_info_contact_city_13_3;
	public static String user_info_contact_city_13_4;
	public static String user_info_contact_city_13_5;
	public static String user_info_contact_city_13_6;
	public static String user_info_contact_city_13_7;
	public static String user_info_contact_city_13_8;
	public static String user_info_contact_city_13_9;
	public static String user_info_contact_city_13_10;
	public static String user_info_contact_city_13_11;
	public static String user_info_contact_city_14_number;
	public static String user_info_contact_city_14_1;
	public static String user_info_contact_city_14_2;
	public static String user_info_contact_city_14_3;
	public static String user_info_contact_city_14_4;
	public static String user_info_contact_city_14_5;
	public static String user_info_contact_city_14_6;
	public static String user_info_contact_city_14_7;
	public static String user_info_contact_city_14_8;
	public static String user_info_contact_city_14_9;
	public static String user_info_contact_city_14_10;
	public static String user_info_contact_city_14_11;
	public static String user_info_contact_city_14_12;
	public static String user_info_contact_city_14_13;
	public static String user_info_contact_city_14_14;
	public static String user_info_contact_city_14_15;
	public static String user_info_contact_city_14_16;
	public static String user_info_contact_city_14_17;
	public static String user_info_contact_city_15_number;
	public static String user_info_contact_city_15_1;
	public static String user_info_contact_city_15_2;
	public static String user_info_contact_city_15_3;
	public static String user_info_contact_city_15_4;
	public static String user_info_contact_city_15_5;
	public static String user_info_contact_city_15_6;
	public static String user_info_contact_city_15_7;
	public static String user_info_contact_city_15_8;
	public static String user_info_contact_city_15_9;
	public static String user_info_contact_city_16_number;
	public static String user_info_contact_city_16_1;
	public static String user_info_contact_city_16_2;
	public static String user_info_contact_city_16_3;
	public static String user_info_contact_city_16_4;
	public static String user_info_contact_city_16_5;
	public static String user_info_contact_city_16_6;
	public static String user_info_contact_city_16_7;
	public static String user_info_contact_city_16_8;
	public static String user_info_contact_city_16_9;
	public static String user_info_contact_city_16_10;
	public static String user_info_contact_city_16_11;
	public static String user_info_contact_city_17_number;
	public static String user_info_contact_city_17_1;
	public static String user_info_contact_city_17_2;
	public static String user_info_contact_city_17_3;
	public static String user_info_contact_city_17_4;
	public static String user_info_contact_city_17_5;
	public static String user_info_contact_city_17_6;
	public static String user_info_contact_city_17_7;
	public static String user_info_contact_city_17_8;
	public static String user_info_contact_city_17_9;
	public static String user_info_contact_city_17_10;
	public static String user_info_contact_city_17_11;
	public static String user_info_contact_city_17_12;
	public static String user_info_contact_city_17_13;
	public static String user_info_contact_city_17_14;
	public static String user_info_contact_city_17_15;
	public static String user_info_contact_city_17_16;
	public static String user_info_contact_city_17_17;
	public static String user_info_contact_city_18_number;
	public static String user_info_contact_city_18_1;
	public static String user_info_contact_city_18_2;
	public static String user_info_contact_city_18_3;
	public static String user_info_contact_city_18_4;
	public static String user_info_contact_city_18_5;
	public static String user_info_contact_city_18_6;
	public static String user_info_contact_city_18_7;
	public static String user_info_contact_city_18_8;
	public static String user_info_contact_city_18_9;
	public static String user_info_contact_city_18_10;
	public static String user_info_contact_city_18_11;
	public static String user_info_contact_city_18_12;
	public static String user_info_contact_city_18_13;
	public static String user_info_contact_city_18_14;
	public static String user_info_contact_city_18_15;
	public static String user_info_contact_city_18_16;
	public static String user_info_contact_city_18_17;
	public static String user_info_contact_city_18_18;
	public static String user_info_contact_city_19_number;
	public static String user_info_contact_city_19_1;
	public static String user_info_contact_city_19_2;
	public static String user_info_contact_city_19_3;
	public static String user_info_contact_city_19_4;
	public static String user_info_contact_city_19_5;
	public static String user_info_contact_city_19_6;
	public static String user_info_contact_city_19_7;
	public static String user_info_contact_city_19_8;
	public static String user_info_contact_city_19_9;
	public static String user_info_contact_city_19_10;
	public static String user_info_contact_city_19_11;
	public static String user_info_contact_city_19_12;
	public static String user_info_contact_city_19_13;
	public static String user_info_contact_city_19_14;
	public static String user_info_contact_city_19_15;
	public static String user_info_contact_city_19_16;
	public static String user_info_contact_city_19_17;
	public static String user_info_contact_city_20_number;
	public static String user_info_contact_city_20_1;
	public static String user_info_contact_city_20_2;
	public static String user_info_contact_city_20_3;
	public static String user_info_contact_city_20_4;
	public static String user_info_contact_city_20_5;
	public static String user_info_contact_city_20_6;
	public static String user_info_contact_city_20_7;
	public static String user_info_contact_city_20_8;
	public static String user_info_contact_city_20_9;
	public static String user_info_contact_city_20_10;
	public static String user_info_contact_city_20_11;
	public static String user_info_contact_city_20_12;
	public static String user_info_contact_city_20_13;
	public static String user_info_contact_city_21_number;
	public static String user_info_contact_city_21_1;
	public static String user_info_contact_city_21_2;
	public static String user_info_contact_city_21_3;
	public static String user_info_contact_city_21_4;
	public static String user_info_contact_city_21_5;
	public static String user_info_contact_city_21_6;
	public static String user_info_contact_city_21_7;
	public static String user_info_contact_city_21_8;
	public static String user_info_contact_city_21_9;
	public static String user_info_contact_city_21_10;
	public static String user_info_contact_city_21_11;
	public static String user_info_contact_city_21_12;
	public static String user_info_contact_city_21_13;
	public static String user_info_contact_city_21_14;
	public static String user_info_contact_city_21_15;
	public static String user_info_contact_city_21_16;
	public static String user_info_contact_city_21_17;
	public static String user_info_contact_city_21_18;
	public static String user_info_contact_city_21_19;
	public static String user_info_contact_city_21_20;
	public static String user_info_contact_city_21_21;
	public static String user_info_contact_city_22_number;
	public static String user_info_contact_city_22_1;
	public static String user_info_contact_city_22_2;
	public static String user_info_contact_city_22_3;
	public static String user_info_contact_city_22_4;
	public static String user_info_contact_city_22_5;
	public static String user_info_contact_city_22_6;
	public static String user_info_contact_city_22_7;
	public static String user_info_contact_city_22_8;
	public static String user_info_contact_city_22_9;
	public static String user_info_contact_city_22_10;
	public static String user_info_contact_city_22_11;
	public static String user_info_contact_city_22_12;
	public static String user_info_contact_city_22_13;
	public static String user_info_contact_city_22_14;
	public static String user_info_contact_city_23_number;
	public static String user_info_contact_city_23_1;
	public static String user_info_contact_city_23_2;
	public static String user_info_contact_city_23_3;
	public static String user_info_contact_city_23_4;
	public static String user_info_contact_city_23_5;
	public static String user_info_contact_city_23_6;
	public static String user_info_contact_city_23_7;
	public static String user_info_contact_city_23_8;
	public static String user_info_contact_city_23_9;
	public static String user_info_contact_city_23_10;
	public static String user_info_contact_city_23_11;
	public static String user_info_contact_city_23_12;
	public static String user_info_contact_city_23_13;
	public static String user_info_contact_city_23_14;
	public static String user_info_contact_city_23_15;
	public static String user_info_contact_city_23_16;
	public static String user_info_contact_city_23_17;
	public static String user_info_contact_city_23_18;
	public static String user_info_contact_city_24_number;
	public static String user_info_contact_city_24_1;
	public static String user_info_contact_city_24_2;
	public static String user_info_contact_city_24_3;
	public static String user_info_contact_city_24_4;
	public static String user_info_contact_city_24_5;
	public static String user_info_contact_city_24_6;
	public static String user_info_contact_city_24_7;
	public static String user_info_contact_city_24_8;
	public static String user_info_contact_city_24_9;
	public static String user_info_contact_city_24_10;
	public static String user_info_contact_city_24_11;
	public static String user_info_contact_city_24_12;
	public static String user_info_contact_city_24_13;
	public static String user_info_contact_city_24_14;
	public static String user_info_contact_city_24_15;
	public static String user_info_contact_city_24_16;
	public static String user_info_contact_city_24_17;
	public static String user_info_contact_city_24_18;
	public static String user_info_contact_city_24_19;
	public static String user_info_contact_city_24_20;
	public static String user_info_contact_city_24_21;
	public static String user_info_contact_city_25_number;
	public static String user_info_contact_city_25_1;
	public static String user_info_contact_city_25_2;
	public static String user_info_contact_city_25_3;
	public static String user_info_contact_city_25_4;
	public static String user_info_contact_city_25_5;
	public static String user_info_contact_city_25_6;
	public static String user_info_contact_city_25_7;
	public static String user_info_contact_city_25_8;
	public static String user_info_contact_city_25_9;
	public static String user_info_contact_city_26_number;
	public static String user_info_contact_city_26_1;
	public static String user_info_contact_city_26_2;
	public static String user_info_contact_city_26_3;
	public static String user_info_contact_city_26_4;
	public static String user_info_contact_city_26_5;
	public static String user_info_contact_city_26_6;
	public static String user_info_contact_city_26_7;
	public static String user_info_contact_city_26_8;
	public static String user_info_contact_city_26_9;
	public static String user_info_contact_city_26_10;
	public static String user_info_contact_city_26_11;
	public static String user_info_contact_city_26_12;
	public static String user_info_contact_city_26_13;
	public static String user_info_contact_city_26_14;
	public static String user_info_contact_city_26_15;
	public static String user_info_contact_city_26_16;
	public static String user_info_contact_city_27_number;
	public static String user_info_contact_city_27_1;
	public static String user_info_contact_city_27_2;
	public static String user_info_contact_city_27_3;
	public static String user_info_contact_city_27_4;
	public static String user_info_contact_city_27_5;
	public static String user_info_contact_city_27_6;
	public static String user_info_contact_city_27_7;
	public static String user_info_contact_city_28_number;
	public static String user_info_contact_city_28_1;
	public static String user_info_contact_city_28_2;
	public static String user_info_contact_city_28_3;
	public static String user_info_contact_city_28_4;
	public static String user_info_contact_city_28_5;
	public static String user_info_contact_city_28_6;
	public static String user_info_contact_city_28_7;
	public static String user_info_contact_city_28_8;
	public static String user_info_contact_city_28_9;
	public static String user_info_contact_city_28_10;
	public static String user_info_contact_city_29_number;
	public static String user_info_contact_city_29_1;
	public static String user_info_contact_city_29_2;
	public static String user_info_contact_city_29_3;
	public static String user_info_contact_city_29_4;
	public static String user_info_contact_city_29_5;
	public static String user_info_contact_city_29_6;
	public static String user_info_contact_city_29_7;
	public static String user_info_contact_city_29_8;
	public static String user_info_contact_city_29_9;
	public static String user_info_contact_city_29_10;
	public static String user_info_contact_city_29_11;
	public static String user_info_contact_city_29_12;
	public static String user_info_contact_city_29_13;
	public static String user_info_contact_city_29_14;
	public static String user_info_contact_city_30_number;
	public static String user_info_contact_city_30_1;
	public static String user_info_contact_city_30_2;
	public static String user_info_contact_city_30_3;
	public static String user_info_contact_city_30_4;
	public static String user_info_contact_city_30_5;
	public static String user_info_contact_city_30_6;
	public static String user_info_contact_city_30_7;
	public static String user_info_contact_city_30_8;
	public static String user_info_contact_city_31_number;
	public static String user_info_contact_city_31_1;
	public static String user_info_contact_city_31_2;
	public static String user_info_contact_city_31_3;
	public static String user_info_contact_city_31_4;
	public static String user_info_contact_city_32_number;
	public static String user_info_contact_city_32_1;
	public static String user_info_contact_city_32_2;
	public static String user_info_contact_city_32_3;
	public static String user_info_contact_city_32_4;
	public static String user_info_contact_city_32_5;
	public static String user_info_contact_city_32_6;
	public static String user_info_contact_city_32_7;
	public static String user_info_contact_city_32_8;
	public static String user_info_contact_city_32_9;
	public static String user_info_contact_city_32_10;
	public static String user_info_contact_city_32_11;
	public static String user_info_contact_city_32_12;
	public static String user_info_contact_city_32_13;
	public static String user_info_contact_city_32_14;
	public static String user_info_contact_city_32_15;
	public static String user_info_contact_city_32_16;
	public static String user_info_contact_city_33_number;
	public static String user_info_contact_city_33_1;
	public static String user_info_contact_city_34_number;
	public static String user_info_contact_city_34_1;
	public static String user_info_contact_city_35_number;
	public static String user_info_contact_city_35_1;
	public static String user_info_contact_city_35_2;
	public static String user_info_contact_city_35_3;
	public static String user_info_contact_city_35_4;
	public static String user_info_contact_city_35_5;
	public static String user_info_contact_city_35_6;
	public static String user_info_contact_city_35_7;
	public static String user_info_contact_city_35_8;
	public static String user_info_contact_city_35_9;
	public static String user_info_contact_city_35_10;
	public static String user_info_contact_city_35_11;
	public static String user_info_contact_city_35_12;
	public static String user_info_contact_city_35_13;
	public static String user_info_contact_city_35_14;
	public static String user_info_contact_city_35_15;
	public static String user_info_contact_city_35_16;
	public static String user_info_contact_city_35_17;
	public static String user_info_contact_city_35_18;
	public static String user_info_contact_city_35_19;
	public static String user_info_contact_city_35_20;
	public static String user_info_contact_city_35_21;
	public static String user_info_contact_city_35_22;
	public static String user_info_contact_city_35_23;

	// 接收消息窗口的文字
	public static String receive_im_title;
	public static String receive_im_button_reply;
	public static String receive_im_button_quickreply;

	// 发送消息窗口的文字
	public static String im_title;
	public static String im_label_sending;
	public static String im_label_receiving;
	public static String im_label_finished;
	public static String im_button_mode_talk;
	public static String im_button_mode_message;
	public static String im_table_header_nick;
	public static String im_table_header_time;
	public static String im_table_header_message;
	public static String im_menu_use_enter;
	public static String im_menu_use_ctrl_enter;

	// 发送短消息窗口的文字
	public static String sms_title_bind_user;
	public static String sms_title_mobile_qq;
	public static String sms_title_no_bind;
	public static String sms_to;
	public static String sms_will_send;
	public static String sms_sender;
	public static String sms_sending;
	public static String sms_timeout;
	public static String sms_success;
	public static String sms_fail;
	public static String sms_unknown;

	// 发送群消息窗口的文字
	public static String cluster_im_title;
	public static String cluster_im_list_label;
	public static String cluster_im_list_info;
	public static String cluster_im_button_info;
	public static String cluster_im_button_card;
	public static String cluster_im_button_update;
	public static String cluster_im_label_notice;
	public static String cluster_im_menu_member_send;
	public static String cluster_im_menu_member_sendfile;
	public static String cluster_im_menu_member_add;
	public static String cluster_im_menu_member_viewinfo;
	public static String cluster_im_menu_member_message;
	public static String cluster_im_menu_member_export;
	public static String cluster_im_menu_use_enter;
	public static String cluster_im_menu_use_ctrl_enter;
	public static String cluster_im_table_header_nick;
	public static String cluster_im_table_header_time;
	public static String cluster_im_table_header_message;
	public static String cluster_im_hint_timemout;

	// 查看系统消息窗口的文字
	public static String receive_system_message_title;
	public static String receive_system_message_from;
	public static String receive_system_message_from_qq;
	public static String receive_system_message_from_nick;
	public static String receive_system_message_from_seedetail;
	public static String receive_system_message_content_label;
	public static String receive_system_message_success_label;
	public static String receive_system_message_fail_label;
	public static String receive_system_message_reject_label;
	public static String receive_system_message_auth_label;
	public static String receive_system_message_button_approve;
	public static String receive_system_message_button_reject;
	public static String receive_system_message_button_add;
	public static String receive_system_message_button_addstranger;
	public static String receive_system_message_addme;
	public static String receive_system_message_request;
	public static String receive_system_message_approved_and_add;
	public static String receive_system_message_approved;
	public static String receive_system_message_rejected;
	public static String receive_system_message_submited;
	public static String receive_system_message_send_fail;
	public static String receive_system_message_forbidden;
	public static String receive_system_message_add_success;
	public static String receive_system_message_adding;
	public static String receive_system_message_deleting;
	public static String receive_system_message_timeout;
	public static String cluster_message_temporary_cluster_created;
	public static String cluster_message_permanent_cluster_created;
	public static String cluster_message_approved;
	public static String cluster_message_rejected;
	public static String cluster_message_request;
	public static String cluster_message_removed;
	public static String cluster_message_exit;
	public static String cluster_message_admin_entitled;
	public static String cluster_message_admin_withdrawed;

	// 查找窗口的文字
	public static String search_title;
	public static String search_label_online;
	public static String search_what_title;
	public static String search_what_message;
	public static String search_what_user;
	public static String search_what_cluster;
	public static String how_to_search_user_title;
	public static String how_to_search_user_message;
	public static String how_to_search_online;
	public static String how_to_search_accurate;
	public static String how_to_search_advanced;
	public static String how_to_search_online_hint;
	public static String how_to_search_accurate_hint;
	public static String how_to_search_advanced_hint;
	public static String how_to_search_cluster_title;
	public static String how_to_search_cluster_message;
	public static String how_to_search_demo_cluster;
	public static String how_to_search_cluster_by_id;
	public static String how_to_search_cluster_by_category;
	public static String search_user_accurate_title;
	public static String search_user_accurate_message;
	public static String search_user_accurate_qq;
	public static String search_user_accurate_nick;
	public static String search_user_accurate_email;
	public static String search_user_advanced_title;
	public static String search_user_advanced_message;
	public static String search_user_advanced_online;
	public static String search_user_advanced_cam;
	public static String search_user_advanced_basic;
	public static String search_user_advanced_province;
	public static String search_user_advanced_city;
	public static String search_user_advanced_age;
	public static String search_user_advanced_gender;
	public static String search_user_result_title;
	public static String search_user_result_message;
	public static String search_user_result_qq;
	public static String search_user_result_nick;
	public static String search_user_result_gender;
	public static String search_user_result_age;
	public static String search_user_result_from;
	public static String search_user_result_status;
	public static String search_user_result_page;
	public static String search_user_result_showall;
	public static String search_user_result_all;
	public static String search_user_result_info;
	public static String search_cluster_result_title;
	public static String search_cluster_result_message;
	public static String search_cluster_result_id;
	public static String search_cluster_result_name;
	public static String search_cluster_result_creator;
	public static String search_cluster_result_info;
	public static String add_title;
	public static String add_message;
	public static String error_invalid_cluster_id;
	public static String error_at_least_one_field;
	public static String error_timeout;
	public static String error_select_category;
	public static String hint_processing;
	public static String hint_need_auth;
	public static String hint_added;
	public static String hint_add_deny;
	public static String hint_auth_sent;
	public static String hint_auth_timeout;
	public static String hint_auth_fail;
	public static String hint_join_deny;
	public static String hint_joined;
	public static String search_current_online_unknown;

	// 创建群组向导的文字
	public static String cluster_title;
	public static String create_what_title;
	public static String create_what_message;
	public static String create_what_permanent;
	public static String create_what_dialog;
	public static String create_what_subject;
	public static String permanent_title;
	public static String permanent_message;
	public static String permanent_name;
	public static String permanent_category;
	public static String permanent_notice;
	public static String permanent_description;
	public static String permanent_auth;
	public static String permanent_no_auth;
	public static String permanent_need_auth;
	public static String permanent_no_add;
	public static String temp_title;
	public static String temp_message;
	public static String temp_name;
	public static String temp_parent_cluster;
	public static String member_select_title;
	public static String member_select_message;
	public static String member_select_qq;
	public static String member_select_nick;
	public static String member_select_from;
	public static String create_title;
	public static String create_message;
	public static String hint_creating;
	public static String hint_created;
	public static String hint_failed;
	public static String hint_timeout;
	public static String error_empty_name;
	public static String error_parent_empty;

	// 系统参数窗口的文字
	public static String sys_opt_title;
	public static String sys_opt_button_gui;
	public static String sys_opt_button_key;
	public static String sys_opt_button_message;
	public static String sys_opt_button_reply;
	public static String sys_opt_button_login;
	public static String sys_opt_button_sync;
	public static String sys_opt_button_other;
	public static String sys_opt_button_verify;
	public static String sys_opt_group_window;
	public static String sys_opt_group_tip;
	public static String sys_opt_group_feel;
	public static String sys_opt_group_receive;
	public static String sys_opt_group_sound;
	public static String sys_opt_group_auto_reply;
	public static String sys_opt_group_quick_reply;
	public static String sys_opt_group_edit_reply;
	public static String sys_opt_group_login;
	public static String sys_opt_group_server;
	public static String sys_opt_group_proxy;
	public static String sys_opt_group_proxy_basic;
	public static String sys_opt_group_proxy_list;
	public static String sys_opt_group_browser;
	public static String sys_opt_group_latest;
	public static String sys_opt_group_disguise;
	public static String sys_opt_group_download_group;
	public static String sys_opt_group_upload_group;
	public static String sys_opt_group_check_update;
	public static String sys_opt_group_message_key;
	public static String sys_opt_button_add_auto_reply;
	public static String sys_opt_button_add_quick_reply;
	public static String sys_opt_login_method_udp;
	public static String sys_opt_login_method_tcp;
	public static String sys_opt_login_tcp_port;
	public static String sys_opt_login_server;
	public static String sys_opt_login_server_random;
	public static String sys_opt_login_use_proxy;
	public static String sys_opt_login_proxy_type;
	public static String sys_opt_login_proxy_address;
	public static String sys_opt_login_proxy_port;
	public static String sys_opt_login_proxy_username;
	public static String sys_opt_login_proxy_password;
	public static String sys_opt_gui_on_top;
	public static String sys_opt_gui_hide_when_minimize;
	public static String sys_opt_gui_auto_hide;
	public static String sys_opt_gui_use_tab_im;
	public static String sys_opt_gui_minimize_when_close;
	public static String sys_opt_gui_im_on_top;
	public static String sys_opt_gui_show_tip;
	public static String sys_opt_gui_show_online_tip;
	public static String sys_opt_gui_show_last_login_tip;
	public static String sys_opt_gui_show_signature;
	public static String sys_opt_gui_show_custom_head;
	public static String sys_opt_key_use_key;
	public static String sys_opt_key_for_platform;
	public static String sys_opt_message_auto_eject;
	public static String sys_opt_message_reject_stranger;
	public static String sys_opt_message_enable_sound;
	public static String sys_opt_message_reject_temp_session_im;
	public static String sys_opt_sync_auto_download_group;
	public static String sys_opt_sync_auto_download_friend_remark;
	public static String sys_opt_sync_auto_download_signature;
	public static String sys_opt_sync_prompt_upload_group;
	public static String sys_opt_sync_always_upload_group;
	public static String sys_opt_sync_never_upload_group;
	public static String sys_opt_sync_auto_check_update;
	public static String sys_opt_other_label_browser;
	public static String sys_opt_other_button_select;
	public static String sys_opt_other_enable_latest;
	public static String sys_opt_other_keep_stranger;
	public static String sys_opt_other_latest_size;
	public static String sys_opt_other_show_fake_cam;

	// 添加手机好友窗口
	public static String add_mobile_title;
	public static String add_mobile_label_name;
	public static String add_mobile_label_number;

	// 如来神掌窗口
	public static String rlsz_title;
	public static String rlsz_label_detecting;
	public static String rlsz_label_timeout;
	public static String rlsz_label_hitted;
	public static String rlsz_label_internet_ip;
	public static String rlsz_label_local_ip;

	// 系统消息历史记录窗口的文字
	public static String sys_msg_list_title;
	public static String sys_msg_list_table_header_from;
	public static String sys_msg_list_table_header_time;
	public static String sys_msg_list_table_header_message;

	// 好友浮动提示窗口的文字
	public static String friendtip_qqshow_mall;

	// 好友上线提示窗口的文字
	public static String onlinetip_user;
	public static String onlinetip_setting;

	// 浏览器窗口的标题
	public static String browser_button_back;
	public static String browser_button_forward;
	public static String browser_button_refresh;
	public static String browser_button_stop;
	public static String browser_label_url;

	// 检查是否有更新的窗口
	public static String check_update_title;
	public static String check_update_label_checking;
	public static String check_update_label_latest;
	public static String check_update_lable_old;
	public static String check_update_label_error;
	public static String check_update_button_i_know;

	// 选择组对话框
	public static String select_group_title;
	public static String select_group_label_hint;

	// viewbar菜单
	public static String menu_view_bar_hide;
	public static String menu_view_bar_show;
	
	// 改变状态菜单的文字
	public static String menu_status_away_null;
	public static String menu_status_away_absent;
	public static String menu_status_away_working;
	public static String menu_status_away_eating;
	public static String menu_status_away_custom;
	
	// 手机好友菜单的文字
	public static String menu_mobile_add;
	public static String menu_mobile_delete;
	public static String menu_mobile_send;

	// 组织菜单
	public static String menu_organization_add;
	public static String menu_organization_refresh;
	public static String menu_organization_remove;
	public static String menu_organization_modify;
	public static String menu_organization_discuss;
	
	// 讨论组菜单
	public static String menu_subject_new;
	public static String menu_subject_refresh_all;
	
	// 多人对话容器菜单
	public static String menu_dialog_new;
	public static String menu_dialog_refresh;
	
	// 系统菜单的文字
	public static String menu_sys_tool;
	public static String menu_sys_temp_session;
	public static String menu_sys_weather;
	public static String menu_sys_status;
	public static String menu_sys_exit;
	public static String menu_sys_debug;
	public static String menu_sys_about;
	public static String menu_sys_checkupdate;
	public static String menu_sys_lumaqq_homepage;
	public static String menu_sys_changeuser;
	public static String menu_sys_sysopt;
	public static String menu_sys_personinfo;
	public static String menu_sys_searchip;
	public static String menu_sys_group;
	public static String menu_sys_group_upload;
	public static String menu_sys_group_download;
	public static String menu_sys_apply;
	public static String menu_sys_friend;
	public static String menu_sys_friend_download_remark;
	public static String menu_sys_info_window;
	public static String menu_sys_friend_export_record;
	public static String menu_sys_robot;
	public static String menu_sys_robot_on;
	public static String menu_sys_robot_off;

	// 快捷回复的文字
	public static String menu_quick_reply_o;
	public static String menu_quick_reply_ok;
	public static String menu_quick_reply_enough;
	public static String menu_quick_reply_understand;
	public static String menu_quick_reply_bye;
	public static String menu_quick_reply_kao;
	public static String menu_quick_reply_addnew;

	// 主窗口plus按钮菜单文字
	public static String menu_plus_friend_view;
	public static String menu_plus_disk;
	
	// 组内右键菜单的文字
	public static String menu_group_showsmall;
	public static String menu_group_showlarge;
	public static String menu_group_cluster;
	public static String menu_group_cluster_search;
	public static String menu_group_cluster_create;
	public static String menu_group_alumni_search;
	public static String menu_group_alumni_create;
	public static String menu_group_community;
	public static String menu_group_color;
	public static String menu_group_group_color;
	public static String menu_group_displayname;
	public static String menu_group_displayname_nick;
	public static String menu_group_displayname_remark;
	public static String menu_group_enable_user_tip;
	public static String menu_group_disable_user_tip;
	public static String menu_group_tree_mode;
	public static String menu_group_traditional_mode;
	public static String menu_group_addgroup;
	public static String menu_group_addfriend;
	public static String menu_group_showall;
	public static String menu_group_showonlineonly;
	public static String menu_group_rename;
	public static String menu_group_delete;
	public static String menu_group_addbadguy;
	public static String menu_group_showblacklist;
	public static String menu_group_hideblacklist;

	// 好友上右键菜单的文字
	public static String menu_friend_sendreceive;
	public static String menu_friend_temp_session;
	public static String menu_friend_sendfile;
	public static String menu_friend_sendsms;
	public static String menu_friend_viewinfo;
	public static String menu_friend_rlsz;
	public static String menu_friend_message_manage;
	public static String menu_friend_message_export;
	public static String menu_friend_showsmall;
	public static String menu_friend_showlarge;
	public static String menu_friend_addgroup;
	public static String menu_friend_showonlineonly;
	public static String menu_friend_showall;
	public static String menu_friend_delfriend;
	public static String menu_friend_rename;
	public static String menu_friend_pin;
	public static String menu_friend_unpin;

	// 群上右键菜单的文字
	public static String menu_cluster_send;
	public static String menu_cluster_viewinfo;
	public static String menu_cluster_modifyinfo;
	public static String menu_cluster_exit;
	public static String menu_cluster_message_manage;
	public static String menu_cluster_message_export;

	// 文件传输任务列表菜单的文字
	public static String menu_file_none;
	public static String menu_file_send;
	public static String menu_file_receive;

	// 聊天记录查看控件的文字
	public static String record_viewer_label_display;
	public static String record_viewer_label_year;
	public static String record_viewer_label_month;
	public static String record_viewer_label_day;
	public static String record_viewer_button_display_all;
	public static String record_viewer_menu_copy;
	public static String record_viewer_menu_export;

	// 聊天输入控件右键菜单的文字
	public static String richbox_menu_copy;
	public static String richbox_menu_cut;
	public static String richbox_menu_paste;
	public static String richbox_menu_select_all;
	public static String richbox_menu_clear;

	// 缺省组的名字
	public static String group_default_friend;
	public static String group_default_cluster;
	public static String group_default_stranger;
	public static String group_default_blacklist;
	public static String group_default_latest;
	public static String group_default_mobile;
	
	// 群下面的子节点
	public static String cluster_organization;
	public static String cluster_subject;
	public static String cluster_dialogs;

	// 性别
	public static String gender_gg;
	public static String gender_mm;

	public static String unknown_ip;
	public static String unknown_version;
	public static String unknown_country;
	public static String unknown_area;
	public static String bad_ip_file;

	// IP查询窗口的文字
	public static String ip_seeker_title;
	public static String ip_seeker_button_ip2addr;
	public static String ip_seeker_button_addr2ip;
	public static String ip_seeker_label_input;
	public static String ip_seeker_text_address;
	public static String ip_seeker_text_begin_ip;
	public static String ip_seeker_text_end_ip;
	public static String ip_seeker_tooltip_ip2addr;
	public static String ip_seeker_tooltip_addr2ip;
	public static String ip_seeker_please_input;

	// 打开聊天记录导出目录的对话框
	public static String dir_dialog_common_title;
	public static String dir_dialog_export_message;

	// 导出的聊天记录名字
	public static String cluster_record_file_name;

	// 任务名
	public static String job_download_group_1;
	public static String job_download_group_2;
	public static String job_download_group_error;
	public static String job_upload_group_1;
	public static String job_upload_group_error;
	public static String job_download_remark_1;
	public static String job_download_remark_2;
	public static String job_get_friend_list;
	public static String job_get_friend_list_error;
	public static String job_import_cfc;
	public static String job_import_eip;
	public static String job_export_all_record;
	
	// 群分类对话框
	public static String cluster_category_title;
	
	// 程序所有的对话框消息串
	public static String error_change_status_fail;
	public static String error_old_password_wrong;
	public static String error_two_password_differ;
	public static String error_login_fail;
	public static String error_delete_friend_fail;
	public static String success_modify_info;
	public static String success_modify_member;
	public static String hint_delete_friend;
	public static String hint_delete_friend_and_remove_self;
	public static String hint_please_clear_friend;
	public static String message_box_success_modify_info_title;
	public static String message_box_change_password_title;
	public static String message_box_login_fail_title;
	public static String message_box_common_info_title;
	public static String message_box_common_fail_title;
	public static String message_box_common_success_title;
	public static String message_box_common_warning_title;
	public static String message_box_common_question_title;
	public static String message_box_proxy_error_title;
	public static String message_box_network_error_title;
	public static String message_box_send_message_timeout;
	public static String message_box_please_select_user;
	public static String message_box_please_select_cluster;
	public static String message_box_login_timeout;
	public static String message_box_login_unknown_error;
	public static String message_box_login_redirect_null;
	public static String message_box_please_login_first;
	public static String message_box_connection_lost;
	public static String message_box_op_upload_success;
	public static String message_box_op_download_success;
	public static String message_box_op_upload_fail;
	public static String message_box_op_download_fail;
	public static String message_box_upload_remark_success;
	public static String message_box_common_timeout;
	public static String message_box_please_input_cluster_name;
	public static String message_box_please_select_cluster_member;
	public static String message_box_login_twice;
	public static String message_box_cluster_message_option_modified;
	public static String message_box_exit_cluster;
	public static String message_box_exit_my_cluster;
	public static String message_box_search_cluster_fail;
	public static String message_box_cannot_send_empty_message;
	public static String message_box_cannot_switch_to_message_mode;
	public static String message_box_file_transfering;
	public static String message_box_file_length_zero;
	public static String message_box_file_open_error;
	public static String message_box_cannot_send_file_to_self;
	public static String message_box_please_fill_proxy_address;
	public static String message_box_please_fill_proxy_port;
	public static String message_box_proxy_verify_ok;
	public static String message_box_proxy_verify_fail;
	public static String message_box_please_select_proxy;
	public static String message_box_browser_not_set;
	public static String message_box_please_set_browser;
	public static String message_box_manual_browse;
	public static String message_box_please_select_group;
	public static String message_box_please_enter_sms;
	public static String message_box_cannot_insert_blink;
	public static String message_box_send_sms_timeout;
	public static String message_box_please_specify_receiver;
	public static String message_box_job_running;
	public static String message_box_upload_group;
	public static String message_box_need_face_group;
	public static String message_box_abort_sending;
	public static String message_box_face_to_user;
	public static String message_box_temp_cluster_created;
	public static String message_box_max_organization_level;
	public static String message_box_empty_organization_first;
	public static String message_box_empty_organization;
	public static String message_box_max_org;
	public static String message_box_card_modified;
	public static String message_box_admin_set;
	public static String message_box_admin_unset;
	public static String message_box_role_transferred;
	public static String message_box_wrong_qq;
	public static String message_box_too_long_sms;
	public static String message_box_sms_no_receiver;
	public static String message_box_sms_empty;
	public static String message_box_confirm_delete_message;
	public static String message_box_cannot_delete_default_group;
	public static String message_box_cannot_change_default_group;
	public static String message_box_old_password_incorrect;
	public static String message_box_new_password_confirm;
	public static String message_box_password_length;
	public static String message_box_set_disk_password_success;
	public static String message_box_set_disk_password_fail;
	public static String message_box_cancel_disk_password_success;
	public static String message_box_cancel_disk_password_fail;
	public static String message_box_resume_file;
	
	static {
		BUNDLE_NAME = "locale.LumaQQ_" + LumaQQ.language;
		System.out.println(BUNDLE_NAME);
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
