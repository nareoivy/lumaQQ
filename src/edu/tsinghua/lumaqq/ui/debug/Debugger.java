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

import static org.apache.commons.codec.digest.DigestUtils.md5;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import edu.tsinghua.lumaqq.qq.Crypter;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.debug.BasicDebugPacket;
import edu.tsinghua.lumaqq.qq.debug.DebugSwitch;
import edu.tsinghua.lumaqq.qq.debug.FragmentDO;
import edu.tsinghua.lumaqq.qq.debug.IDebugListener;
import edu.tsinghua.lumaqq.qq.debug.IDebugObject;
import edu.tsinghua.lumaqq.qq.debug.InitialArgument;
import edu.tsinghua.lumaqq.qq.debug._03DebugPacket;
import edu.tsinghua.lumaqq.qq.debug._05DebugPacket;
import edu.tsinghua.lumaqq.qq.net.IConnection;
import edu.tsinghua.lumaqq.qq.packets.PacketHelper;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
import edu.tsinghua.lumaqq.qq.packets.in.LoginReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.GetKeyReplyPacket;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.provider.ListContentProvider;

/**
 * <pre>
 * 图形化调试客户端，可以独立运行，也可以做为程序的子窗口运行。在独立运行时，其不具备自动接收包
 * 的能力，需要从input框输入包数据方能解析；在做为子窗口运行时，如果打开调试功能，则其会自动接收
 * 到DebugSwitch发来的包并显示。
 * </pre>
 * 
 * @author luma
 */
public class Debugger extends ApplicationWindow implements IDebugListener {
    /**
     * 包名修改器
     * 
     * @author luma
     */
    private class PacketNameCellModifier implements ICellModifier {
        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
         */
        public boolean canModify(Object element, String property) {
            return Debugger.NAME.equals(property);
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
         */
        public Object getValue(Object element, String property) {
            IDebugObject obj = (IDebugObject)element;
            
            if(Debugger.NAME.equals(property))
                return obj.getName();
            else
                return null;
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
         */
        public void modify(Object element, String property, Object value) {
            IDebugObject obj = (IDebugObject)((TableItem)element).getData();
            
            if(Debugger.NAME.equals(property)) {
                obj.setName((String)value);
                packetViewer.refresh(obj);
            }
        }
    }
    
    /**
     * <pre>
     * 执行添加包的任务，因为这些方法可能是从非UI线程中调用的，我们需要确保其在UI线程中执行
     * </pre>
     * 
     * @author luma
     */
    private class AddPacketRunnable implements Runnable {
        public IDebugObject obj;
        
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        public void run() {
            packets.add(obj);
            if(!packetViewer.getControl().isDisposed())
                packetViewer.refresh();
        }
    }
    
    private Display display;
    
    // 当前是否正在调试
    private boolean debug;
    
    private Font font;
    private Resources res;
    private AddPacketRunnable addPacketRunnable;
    private MainShell main;
    private QQUser user;
    private PacketHelper packetHelper;
    
    private StyledText textBytes, textInput;
    private Text textSend;
    private TableViewer packetViewer;
    private List<IDebugObject> packets;
    private CTabFolder viewFolder;

    private static Crypter crypter = new Crypter();
    protected static final String NAME = "name";
    
    private Menu packetMenu, textMenu, decryptMenu, encryptMenu, parseMenu, sendMenu;
    private ToolItem tiParse, tiDecrypt;
    
    private Button chkBodyOnly;
    private Label lblSelection;

	private ToolItem tiInputArg;

	private ToolItem tiEncrypt;
    
    /**
     * @param main
     * 		MainShell, 为null表示独立模式
     */
    public Debugger(MainShell main) {
        super(null);
        this.main = main;
        if(main != null)
            user = main.getClient().getUser();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.ApplicationWindow#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        initializeVariables(shell);
        shell.setText("Debugger 2006");
        shell.setImage(res.getImage(Resources.icoDebug));
        shell.addDisposeListener(new DisposeListener() {
        	public void widgetDisposed(DisposeEvent e) {
                font.dispose();
        	}
        });
        setBlockOnOpen(main == null);
        if(main != null) {
        	final ShellListener sl = new ShellAdapter() {
        		@Override
        		public void shellClosed(ShellEvent e) {
        			close();
        		}
        	};
        	main.getShell().addShellListener(sl);
        	shell.addShellListener(new ShellAdapter() {
        		@Override
        		public void shellClosed(ShellEvent e) {
        			main.getShell().removeShellListener(sl);
        		}
        	});
        }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#handleShellCloseEvent()
     */
    protected void handleShellCloseEvent() {
        disableDebug();
        super.handleShellCloseEvent();
    }
    
    /**
     * 关闭调试模式
     */
    private void disableDebug() {
        DebugSwitch ds = DebugSwitch.getInstance();
        ds.removeDebugListener(this);
        setDebug(false);
        tiInputArg.setText("Start Debug");
    }
    
    /**
     * 打开调试模式
     */
    private void enableDebug() {
        DebugSwitch ds = DebugSwitch.getInstance();
        ds.addDebugListener(this);
        setDebug(true);
        tiInputArg.setText("Stop Debug");
    }

    /**
     * 切换调试状态，这个方法只会被用在子窗口模式式
     * 
     * @param e
     * 			按钮选择事件
     */
    private void switchDebugMode() {
        /* 开始或停止调试 */
        if(debug)
            disableDebug();           
        else 
            enableDebug();
    }
    
    /**
     * 初始化菜单
     */
    private void initMenu() {
        initPacketMenu();        
        initTextMenu();        
        initDecryptMenu();        
        initParseMenu();        
        initSendMenu();
        initEncryptMenu();
    }
    
    /**
     * 使用指定密钥加密输入串
     * 
     * @param key
     */
    private void encryptInput(byte[] key) {
        byte[] b = Util.convertHexStringToByte(textInput.getText());
        if(b == null)
        	b = Util.getBytes(textInput.getText());
        if(b == null)
        	return;
        
        b = crypter.encrypt(b, key);
        textInput.append(System.getProperty("line.separator"));
        textInput.append(Util.convertByteToHexString(b));
    }

	private void initEncryptMenu() {
		MenuItem mi;
		// 加密菜单
        encryptMenu = new Menu(getShell());
        // 用会话密钥加密
        final MenuItem miSessionKey = new MenuItem(encryptMenu, SWT.PUSH);
        miSessionKey.setText("Encrypt with Session Key");
        miSessionKey.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                encryptInput(user.getSessionKey());
            }
        });
        // 用密码密钥加密
        final MenuItem miPasswordKey = new MenuItem(encryptMenu, SWT.PUSH);
        miPasswordKey.setText("Encrypt with Password Key");
        miPasswordKey.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	encryptInput(user.getPasswordKey());
            }
        });
        // 用文件传输会话密钥加密
        final MenuItem miFileSessionKey = new MenuItem(encryptMenu, SWT.PUSH);
        miFileSessionKey.setText("Encrypt with File Session Key");
        miFileSessionKey.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	encryptInput(user.getFileSessionKey());
            }
        });
        // 用QQ初始密钥加密
        final MenuItem miInitKey = new MenuItem(encryptMenu, SWT.PUSH);
        miInitKey.setText("Encrypt with QQ Initial Key");
        miInitKey.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	encryptInput(user.getInitKey());
            }
        });
        // 用文件中转密钥加密
        final MenuItem miAgentKey = new MenuItem(encryptMenu, SWT.PUSH);
        miAgentKey.setText("Encrypt with File Agent Key");
        miAgentKey.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		encryptInput(user.getFileAgentKey());
        	}
        });
        // 用未知密钥03加密
        final MenuItem mi03Key = new MenuItem(encryptMenu, SWT.PUSH);
        mi03Key.setText("Encrypt with Unknown Key 03");
        mi03Key.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		encryptInput(user.getUnknown03Key());
        	}
        });
        // 用未知密钥06加密
        final MenuItem mi06Key = new MenuItem(encryptMenu, SWT.PUSH);
        mi06Key.setText("Encrypt with Unknown Key 06");
        mi06Key.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		encryptInput(user.getUnknown06Key());
        	}
        });
        // 用未知密钥07加密
        final MenuItem mi07Key = new MenuItem(encryptMenu, SWT.PUSH);
        mi07Key.setText("Encrypt with Unknown Key 07");
        mi07Key.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		encryptInput(user.getUnknown07Key());
        	}
        });
        // 用未知密钥08加密
        final MenuItem mi08Key = new MenuItem(encryptMenu, SWT.PUSH);
        mi08Key.setText("Encrypt with Unknown Key 08");
        mi08Key.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		encryptInput(user.getUnknown08Key());
        	}
        });
        // 用用户输入的内容做为密钥加密
        mi = new MenuItem(encryptMenu, SWT.PUSH);
        mi.setText("Encrypt with User Input Key...");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                String s = getInput("Input a KEY and the content of the input textbox will be decrypted with it");
                if(s == null)
                    showMessage("Your input is invalid!");
                else {
                    byte[] key = Util.convertHexStringToByte(s);
                    if(key == null || key.length != QQ.QQ_LENGTH_KEY)
                        showMessage("The key is invalid!");
                    else
                    	encryptInput(key);
                }                    
            }
        });
        // 用用户输入的密码的密码密钥加密
        mi = new MenuItem(encryptMenu, SWT.PUSH);
        mi.setText("Encrypt with Key of User Input Password...");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                String s = getInput("Input a password and the content of the input textbox will be decrypted with its password key");
                if(s == null)
                    showMessage("Your input is empty");
                else {
                    byte[] key = md5(md5(s.getBytes()));
                    encryptInput(key);
                }                    
            }
        });
        // 菜单事件
        encryptMenu.addMenuListener(new MenuAdapter() {
        	@Override
        	public void menuShown(MenuEvent e) {
        		boolean userNull = user == null;
        		miAgentKey.setEnabled(!userNull && user.getFileAgentKey() != null);
        		mi03Key.setEnabled(!userNull && user.getUnknown03Key() != null);
        		mi06Key.setEnabled(!userNull && user.getUnknown06Key() != null);
        		mi07Key.setEnabled(!userNull && user.getUnknown07Key() != null);
        		mi08Key.setEnabled(!userNull && user.getUnknown08Key() != null);
        		miInitKey.setEnabled(main != null);
        		miFileSessionKey.setEnabled(!userNull && user.getFileSessionKey() != null);
        		miSessionKey.setEnabled(!userNull && user.getSessionKey() != null);
        		miPasswordKey.setEnabled(!userNull && user.getPasswordKey() != null);
        	}
        });
	}

	private void initPacketMenu() {
		/* 包列表右键菜单 */
        final Table packetTable = packetViewer.getTable();
        packetMenu = new Menu(packetTable);
        // 删除一个包
        MenuItem mi = new MenuItem(packetMenu, SWT.PUSH);
        mi.setText("Remove");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                int[] index = packetTable.getSelectionIndices();
                for(int i = index.length - 1; i >= 0; i--)
                    removeDebugObject(index[i]);
            }
        });
        // 删除所有包
        mi = new MenuItem(packetMenu, SWT.PUSH);
        mi.setText("Remove All");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                removeAllPackets();
            }
        });
        
        // 添加菜单事件
        packetMenu.addMenuListener(new MenuAdapter() {
            public void menuShown(MenuEvent e) {
                packetMenu.getItem(0).setEnabled(packetTable.getSelectionIndex() != -1);
                packetMenu.getItem(1).setEnabled(packetTable.getItemCount() > 0);
            }
        });
	}

	private void initTextMenu() {
		MenuItem mi;
		// body窗口右键菜单
        textMenu = new Menu(getShell());
        // copy
        mi = new MenuItem(textMenu, SWT.PUSH);
        mi.setText("Copy");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                byte[] b = Util.convertHexStringToByte(getSelectionText());
                if(b != null) {
	                Clipboard clipboard = new Clipboard(display);
	                clipboard.setContents(new Object[] { textBytes.getSelectionText() }, new Transfer[] { TextTransfer.getInstance() });
	                clipboard.dispose();  
                }
            }
        });
        // copy as string
        mi = new MenuItem(textMenu, SWT.PUSH);
        mi.setText("Copy As String");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                byte[] b = Util.convertHexStringToByte(getSelectionText());
                if(b != null) {
	                Clipboard clipboard = new Clipboard(display);
	                clipboard.setContents(new Object[] { Util.getString(b) }, new Transfer[] { TextTransfer.getInstance() });
	                clipboard.dispose();  
                }
            }
        });
        // separator
        mi = new MenuItem(textMenu, SWT.SEPARATOR);
        // As Text
        mi = new MenuItem(textMenu, SWT.PUSH);
        mi.setText("Show as Text");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                byte[] b = Util.convertHexStringToByte(getSelectionText());
                if(b != null) {
                    String msg = Util.getString(b);
	                showMessage(msg);
                }
            }
        });
        // get ip
        mi = new MenuItem(textMenu, SWT.PUSH);
        mi.setText("Show as IP");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                StringTokenizer st = new StringTokenizer(getSelectionText(), " ");
                int len = st.countTokens();
                if(len != 4)
                    showWarning("To show an IP, you should selected 4 bytes.");
                else {
                    byte[] ip = Util.convertHexStringToByte(getSelectionText());
                    showMessage(edu.tsinghua.lumaqq.qq.Util.getIpStringFromBytes(ip));
                }
            }
        });
        // get port
        mi = new MenuItem(textMenu, SWT.PUSH);
        mi.setText("Show as Port");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                StringTokenizer st = new StringTokenizer(getSelectionText(), " ");
                int len = st.countTokens();
                if(len != 2)
                    showWarning("To show a port, you should selected 2 bytes.");
                else {
                    byte[] ip = Util.convertHexStringToByte(getSelectionText());
                    int port = (ip[0] << 8) & 0xFF00 | ip[1] & 0xFF;
                    showMessage(String.valueOf(port));
                }
            }
        });
        // get integer
        mi = new MenuItem(textMenu, SWT.PUSH);
        mi.setText("Show as Integer");
        mi.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
                StringTokenizer st = new StringTokenizer(getSelectionText(), " ");
                int len = st.countTokens();
                if(len > 4)
                    showWarning("To show a integer, you should selected no more than 4 bytes.");
                else {
                	String s = getSelectionText().replaceAll(" ", "");
                	int i = Util.getInt(s, 16, 0);
                    showMessage(String.valueOf(i));
                }
			}
        });
        // get long
        mi = new MenuItem(textMenu, SWT.PUSH);
        mi.setText("Show as Long");
        mi.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
                StringTokenizer st = new StringTokenizer(getSelectionText(), " ");
                int len = st.countTokens();
                if(len > 8)
                    showWarning("To show a integer, you should selected no more than 8 bytes.");
                else {
                	String s = getSelectionText().replaceAll(" ", "");
                	long l = Util.getLong(s, 16, 0);
                    showMessage(String.valueOf(l));
                }
			}
        });
        // get time
        mi = new MenuItem(textMenu, SWT.PUSH);
        mi.setText("Show as Time");
        mi.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
                StringTokenizer st = new StringTokenizer(getSelectionText(), " ");
                int len = st.countTokens();
                if(len > 4)
                    showWarning("To show a integer, you should selected no more than 4 bytes.");
                else {
                	String s = getSelectionText().replaceAll(" ", "");
                	long t = Util.getLong(s, 16, 0) * 1000L;
                    showMessage(new Date(t).toString());
                }
			}
        });
        // separator
        mi = new MenuItem(textMenu, SWT.SEPARATOR);   
        // Go to
        mi = new MenuItem(textMenu, SWT.PUSH);
        mi.setText("Go To...");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                String s = getInput("Which byte you want to locate");
                if(s == null)
                    showWarning("You Should Input a Integer");
                else {
                    int index = Util.getInt(s, -1);
                    if(index == -1)
                        return;
                    textBytes.setSelection(index * 3, index * 3 + 2);
                }
            }
        });      
        // menu listener
        textMenu.addMenuListener(new MenuAdapter() {
            public void menuShown(MenuEvent e) {
                textMenu.getItem(0).setEnabled(getSelectionText().length() > 0);
            }
        });
	}

	private void initSendMenu() {
		MenuItem mi;
		// 发送菜单
        if(main != null) {
        	sendMenu = new Menu(getShell());
        	// 发送为基本协议族包
        	mi = new MenuItem(sendMenu, SWT.PUSH);
        	mi.setText("As Basic Family");
        	mi.addSelectionListener(new SelectionAdapter() {
        		@Override
        		public void widgetSelected(SelectionEvent e) {
        			char command = inputCommand();
        			if(command == QQ.QQ_CMD_UNKNOWN)
        				return;
                    byte[] b = Util.convertHexStringToByte(textSend.getText().trim());
                    if(b == null)
                        showWarning("The content you input can't parsed as a byte array");
                    else {
                        BasicDebugPacket packet = new BasicDebugPacket(command, user);
                        packet.setBody(b);
                        main.getClient().sendPacket(packet);                            
                    }
        		}
        	});
        	// Advanced...
        	mi = new MenuItem(sendMenu, SWT.PUSH);
        	mi.setText("Advanced...");
        	mi.addSelectionListener(new SelectionAdapter() {
        		@Override
        		public void widgetSelected(SelectionEvent e) {
        			PortDialog dialog = new PortDialog(getShell(), main.getClient());
        			if(dialog.open() == IDialogConstants.OK_ID) {
        				String id = dialog.getConnectionId();
        				IConnection con = main.getClient().getConnectionPool().getConnection(id);
        				if(con == null)
        					return;
        				int family = dialog.getSelectedFamily();
        				if(family == 0)
        					return;
        				
            			char command = inputCommand();
            			if(command == QQ.QQ_CMD_UNKNOWN)
            				return;
                        byte[] b = Util.convertHexStringToByte(textSend.getText().trim());
                        if(b == null)
                            showWarning("The content you input can't parsed as a byte array");
                        else {
                        	switch(family) {
                        		case QQ.QQ_PROTOCOL_FAMILY_BASIC:
                                    BasicDebugPacket packet = new BasicDebugPacket(command, user);
                                    packet.setBody(b);
                                    main.getClient().sendPacket(packet, id);   
                                    break;
                        		case QQ.QQ_PROTOCOL_FAMILY_03:
                        			_03DebugPacket _03packet = new _03DebugPacket(command, user);
                        			_03packet.setBody(b);
                        			main.getClient().sendPacket(_03packet, id);
                        			break;
                        		case QQ.QQ_PROTOCOL_FAMILY_05:
                        			_05DebugPacket _05packet = new _05DebugPacket(command, user);
                        			_05packet.setBody(b);
                        			main.getClient().sendPacket(_05packet, id);
                        			break;
                        	}
                        }
        			}
        		}
        	});
        }
	}

	private void initParseMenu() {
		MenuItem mi;
		// 解析菜单
        parseMenu = new Menu(getShell());
        // 解析为输入包
        mi = new MenuItem(parseMenu, SWT.PUSH);
        mi.setText("Incoming Packet");
        mi.addSelectionListener(new SelectionAdapter() {
        	@Override
            public void widgetSelected(SelectionEvent e) {
                byte[] b = Util.convertHexStringToByte(textInput.getText());
                if(b == null) {
                    showError("Parse packet error!");
                } else {
                    ByteBuffer buf = ByteBuffer.wrap(b);
                    try {
                        if(packetHelper.parseIn(QQ.QQ_PROTOCOL_ALL, buf, user, true) == null)
                            showError("Parse packet error!");                            
                    } catch (PacketParseException e1) {
                        showError("Parse packet error!");
                    }
                }
            }
        });
        // 解析为输出包
        mi = new MenuItem(parseMenu, SWT.PUSH);
        mi.setText("Outcoming Packet");
        mi.addSelectionListener(new SelectionAdapter() {
        	@Override
            public void widgetSelected(SelectionEvent e) {
                byte[] b = Util.convertHexStringToByte(textInput.getText());
                if(b == null) {
                    showError("Parse packet error!");
                } else {
                    ByteBuffer buf = ByteBuffer.wrap(b);
                    try {
                        if(packetHelper.parseOut(QQ.QQ_PROTOCOL_ALL, buf, user) == null)
                            showError("Parse packet error!");                            
                    } catch (PacketParseException e1) {
                        showError("Parse packet error!");
                    }
                }
            }
        });
	}

	private void initDecryptMenu() {
		MenuItem mi;
		// 解密菜单
        decryptMenu = new Menu(getShell());
        // 用会话密钥解密
        final MenuItem miSessionKey = new MenuItem(decryptMenu, SWT.PUSH);
        miSessionKey.setText("Decrypt with Session Key");
        miSessionKey.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                decryptInput(user.getSessionKey());
            }
        });
        // 用密码密钥解密
        final MenuItem miPasswordKey = new MenuItem(decryptMenu, SWT.PUSH);
        miPasswordKey.setText("Decrpty with Password Key");
        miPasswordKey.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                decryptInput(user.getPasswordKey());
            }
        });
        // 用文件传输会话密钥解密
        final MenuItem miFileSessionKey = new MenuItem(decryptMenu, SWT.PUSH);
        miFileSessionKey.setText("Decrypt with File Session Key");
        miFileSessionKey.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                decryptInput(user.getFileSessionKey());
            }
        });
        // 用QQ初始密钥解密
        final MenuItem miInitKey = new MenuItem(decryptMenu, SWT.PUSH);
        miInitKey.setText("Decrypt with QQ Initial Key");
        miInitKey.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                decryptInput(user.getInitKey());
            }
        });
        // 用文件中转密钥解密
        final MenuItem miAgentKey = new MenuItem(decryptMenu, SWT.PUSH);
        miAgentKey.setText("Decrypt with File Agent Key");
        miAgentKey.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		decryptInput(user.getFileAgentKey());
        	}
        });
        // 用未知密钥03解密
        final MenuItem mi03Key = new MenuItem(decryptMenu, SWT.PUSH);
        mi03Key.setText("Decrypt with Unknown Key 03");
        mi03Key.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		decryptInput(user.getUnknown03Key());
        	}
        });
        // 用未知密钥06解密
        final MenuItem mi06Key = new MenuItem(decryptMenu, SWT.PUSH);
        mi06Key.setText("Decrypt with Unknown Key 06");
        mi06Key.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		decryptInput(user.getUnknown06Key());
        	}
        });
        // 用未知密钥07解密
        final MenuItem mi07Key = new MenuItem(decryptMenu, SWT.PUSH);
        mi07Key.setText("Decrypt with Unknown Key 07");
        mi07Key.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		decryptInput(user.getUnknown07Key());
        	}
        });
        // 用未知密钥08解密
        final MenuItem mi08Key = new MenuItem(decryptMenu, SWT.PUSH);
        mi08Key.setText("Decrypt with Unknown Key 08");
        mi08Key.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		decryptInput(user.getUnknown08Key());
        	}
        });
        // 用用户输入的内容做为密钥解密
        mi = new MenuItem(decryptMenu, SWT.PUSH);
        mi.setText("Decrypt with User Input Key...");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                String s = getInput("Input a KEY and the content of the input textbox will be decrypted with it");
                if(s == null)
                    showMessage("Your input is invalid!");
                else {
                    byte[] key = Util.convertHexStringToByte(s);
                    if(key == null || key.length != QQ.QQ_LENGTH_KEY)
                        showMessage("The key is invalid!");
                    else
                        decryptInput(key);
                }                    
            }
        });
        // 用用户输入的密码的密码密钥解密
        mi = new MenuItem(decryptMenu, SWT.PUSH);
        mi.setText("Decrypt with Key of User Input Password...");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                String s = getInput("Input a password and the content of the input textbox will be decrypted with its password key");
                if(s == null)
                    showMessage("Your input is empty");
                else {
                    byte[] key = md5(md5(s.getBytes()));
                    decryptInput(key);
                }                    
            }
        });
        // 菜单事件
        decryptMenu.addMenuListener(new MenuAdapter() {
        	@Override
        	public void menuShown(MenuEvent e) {
        		boolean userNull = user == null;
        		miAgentKey.setEnabled(!userNull && user.getFileAgentKey() != null);
        		mi03Key.setEnabled(!userNull && user.getUnknown03Key() != null);
        		mi06Key.setEnabled(!userNull && user.getUnknown06Key() != null);
        		mi07Key.setEnabled(!userNull && user.getUnknown07Key() != null);
        		mi08Key.setEnabled(!userNull && user.getUnknown08Key() != null);
        		miInitKey.setEnabled(main != null);
        		miFileSessionKey.setEnabled(!userNull && user.getFileSessionKey() != null);
        		miSessionKey.setEnabled(!userNull && user.getSessionKey() != null);
        		miPasswordKey.setEnabled(!userNull && user.getPasswordKey() != null);
        	}
        });
	}
    
    /**
     * @return
     * 		输入的command
     */
    protected char inputCommand() {
        String s = getInput("Input the packet command in Hex");
        if(s == null)
        	return QQ.QQ_CMD_UNKNOWN;
        try {
            char command = (char)Integer.parseInt(s, 16);
            return command;
        } catch (NumberFormatException e1) {
            showWarning("The command you inputed isn't correct");
            return QQ.QQ_CMD_UNKNOWN;
        }
	}

	/**
     * 初始化变量
     * 
     * @param shell
     */
    protected void initializeVariables(Shell shell) {
        display = shell.getDisplay();        
        res = Resources.getInstance();
        font = new Font(display, "Courier New", 9, SWT.NORMAL);
        packets = new ArrayList<IDebugObject>();
        addPacketRunnable = new AddPacketRunnable();
    }
    
    /**
     * 如果要做为一个独立程序运行，则使用main方法
     * 
     * @param args
     * 			命令行参数
     */
    public static void main(String[] args) {        
        new Debugger(null).open();
    }
    
    /**
     * 打开一个输入对话框，返回用户输入
     * 
     * @param msg
     * 		提示
     * @return
     * 		输入的字符串
     */
    private String getInput(String msg) {
        InputDialog dialog = new InputDialog(getShell(), 
                "Input",
                msg,
                null,
                null);
        if(dialog.open() == IDialogConstants.OK_ID)
            return dialog.getValue();
        else
            return null;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents(Composite parent) {
        parent.setLayout(new GridLayout());
        Composite control = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 0;
        control.setLayout(layout);
        control.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        boolean individual = main == null;
        
        UITool.setDefaultBackground(parent.getBackground());
        
        layout = new GridLayout(2, false);
        layout.marginHeight = layout.marginWidth = 0;
        Composite comp = UITool.createContainer(control, new GridData(GridData.FILL_HORIZONTAL), layout);
        
        // 输入0x001D携带的key
        ToolBar mainTb = new ToolBar(comp, SWT.FLAT);
        ToolItem tiInputKey = new ToolItem(mainTb, SWT.PUSH);
        tiInputKey.setText("Input 0x001D Keys");
        tiInputKey.setEnabled(individual);
        tiInputKey.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		onInputKey();
        	}
        });

        // 输入初始参数
        tiInputArg = new ToolItem(mainTb, SWT.PUSH);
		tiInputArg.setText(individual ? "Input Initial Argument" : "Start Debug");
        tiInputArg.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		onStart();
        	}
        });     
        
        SashForm hSash = new SashForm(control, SWT.HORIZONTAL);
        hSash.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        CTabFolder tableFolder = new CTabFolder(hSash, SWT.TOP | SWT.BORDER);
        CTabItem packetTab = new CTabItem(tableFolder, SWT.NONE);
        packetTab.setText("Packets");       
        packetTab.setImage(res.getImage(Resources.icoDebugPacket));
        tableFolder.setSelection(0);
        packetViewer = new TableViewer(tableFolder, SWT.MULTI | SWT.FULL_SELECTION | SWT.V_SCROLL);
        packetViewer.setContentProvider(new ListContentProvider<IDebugObject>(packets));
        packetViewer.setLabelProvider(new DebugObjectLabelProvider());
        packetViewer.setUseHashlookup(true);
        packetViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                onPacketViewerSelectionChanged(event);
            }
        });
        
        Table packetTable = packetViewer.getTable();
        new TableColumn(packetTable, SWT.LEFT);
        packetTable.addControlListener(new ControlAdapter() {
            public void controlResized(ControlEvent e) {
                Table t = (Table)e.getSource();
                t.getColumn(0).setWidth(t.getClientArea().width);
            }
        });
        packetTable.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
                if(e.button == 3)
                    packetMenu.setVisible(true);
            }
        });
        packetTab.setControl(packetTable);
        packetViewer.setColumnProperties(new String[] { NAME });
        packetViewer.setCellEditors(new CellEditor[] { new TextCellEditor(packetTable) });
        packetViewer.setCellModifier(new PacketNameCellModifier());
        packetViewer.setInput(this);
        
        viewFolder = new CTabFolder(hSash, SWT.TOP | SWT.BORDER);        
        // 数据字节窗口
        CTabItem byteTab = new CTabItem(viewFolder, SWT.NONE);
        byteTab.setText("Bytes");
        byteTab.setImage(res.getImage(Resources.icoDebugBytes));
        ViewForm byteForm = new ViewForm(viewFolder, SWT.FLAT);
        Composite c = new Composite(byteForm, SWT.NONE);
        c.setLayout(new GridLayout(2, false));
        chkBodyOnly = new Button(c, SWT.CHECK | SWT.FLAT);
        chkBodyOnly.setText("Body Only");
        chkBodyOnly.setLayoutData(new GridData());
        chkBodyOnly.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection)packetViewer.getSelection();
                IDebugObject obj = (IDebugObject)selection.getFirstElement();
                if(obj != null)
                    textBytes.setText(chkBodyOnly.getSelection() ? obj.getBodyHexString() : obj.getHexString());        
            }
        });
        lblSelection = new Label(c, SWT.LEFT);
        lblSelection.setText("Selection: null");
        GridData gd = new GridData();
        gd.widthHint = 200;
        lblSelection.setLayoutData(gd);
        byteForm.setTopLeft(c);                
        textBytes = new StyledText(byteForm, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY | SWT.V_SCROLL);
        textBytes.setFont(font);
        textBytes.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
                if(e.button == 3) {
                    textMenu.setLocation(textBytes.toDisplay(e.x, e.y));
                    textMenu.setVisible(true);
                }
            }
        });
        textBytes.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Point selection = textBytes.getSelectionRange();
                int start = (selection.x + 1) / 3;
                int end = (selection.x + selection.y + 1) / 3;
                lblSelection.setText("Start: " + start + ", Length: " + (end - start) + ", Hex: " + Integer.toHexString(end - start).toUpperCase());
            }
        });
        byteForm.setContent(textBytes);
        byteTab.setControl(byteForm);
        
        // 输入窗口
        CTabItem inputTab = new CTabItem(viewFolder, SWT.NONE);
        inputTab.setText("Input");
        inputTab.setImage(res.getImage(Resources.icoDebugInput));
        ViewForm inputForm = new ViewForm(viewFolder, SWT.FLAT);
        // viewform的工具条
        final ToolBar tb = new ToolBar(inputForm, SWT.FLAT);
        // 解析
        tiParse = new ToolItem(tb, SWT.DROP_DOWN);
        tiParse.setText("Parse as");
        tiParse.setEnabled(main != null);
        tiParse.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
                Rectangle rect = tiParse.getBounds();
                Point p = tb.toDisplay(rect.x, rect.y);
                p.y += rect.height;
                parseMenu.setLocation(p);
                parseMenu.setVisible(true);
        	}
        });
        // 做md5
        ToolItem tiMd5 = new ToolItem(tb, SWT.PUSH);
        tiMd5.setText("MD5");
        tiMd5.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                byte[] b = Util.convertHexStringToByte(textInput.getText());
                if(b == null)
                	b = Util.getBytes(textInput.getText());
                if(b == null)
                	return;
                b = md5(b);
                textInput.append(System.getProperty("line.separator"));
                textInput.append(Util.convertByteToHexString(b));
            }
        });
        // 解密
        tiDecrypt = new ToolItem(tb, SWT.DROP_DOWN);
        tiDecrypt.setText("Decrypt");
        tiDecrypt.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Rectangle rect = tiDecrypt.getBounds();
                Point p = tb.toDisplay(rect.x, rect.y);
                p.y += rect.height;
                decryptMenu.setLocation(p);
                decryptMenu.setVisible(true);
            }
        });
        tiEncrypt = new ToolItem(tb, SWT.DROP_DOWN);
		tiEncrypt.setText("Encrypt");
        tiEncrypt.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
                Rectangle rect = tiEncrypt.getBounds();
                Point p = tb.toDisplay(rect.x, rect.y);
                p.y += rect.height;
                encryptMenu.setLocation(p);
                encryptMenu.setVisible(true);
        	}
        });
        inputForm.setTopLeft(tb);
        // 选择
        final Label lblInputSelection = new Label(inputForm, SWT.RIGHT);
        lblInputSelection.setText("Selection: null, Length: 0, Hex: 0");
        inputForm.setTopRight(lblInputSelection);
        textInput = new StyledText(inputForm, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);    
        textInput.setFont(font);
        textInput.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                int start = 0;
                int end = textInput.getText().length() / 3;
                lblInputSelection.setText("Length: " + (end - start) + " Hex: " + Integer.toHexString(end - start).toUpperCase());
            }
        });
        textInput.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
                if(e.button == 3) {
                    textMenu.setLocation(textInput.toDisplay(e.x, e.y));
                    textMenu.setVisible(true);
                }
            }
        });
        inputForm.setContent(textInput);
        inputTab.setControl(inputForm);
        
        // 发送窗口
        if(!individual) {
	        CTabItem sendTab = new CTabItem(viewFolder, SWT.NONE);
	        sendTab.setText("Send");
	        sendTab.setImage(res.getImage(Resources.icoDebugSend));
	        ViewForm sendForm = new ViewForm(viewFolder, SWT.FLAT);
	        sendTab.setControl(sendForm);
	        
	        final ToolBar sendBar = new ToolBar(sendForm, SWT.FLAT);
	        final ToolItem tiSend = new ToolItem(sendBar, SWT.DROP_DOWN);
	        tiSend.setText("Send");
	        tiSend.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    Rectangle rect = tiSend.getBounds();
                    Point p = sendBar.toDisplay(rect.x, rect.y);
                    p.y += rect.height;
                    sendMenu.setLocation(p);
                    sendMenu.setVisible(true);
                }
	        });	        
	        sendForm.setTopLeft(sendBar);
	        
	        textSend = new Text(sendForm, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
	        textSend.setFont(font);
	        sendForm.setContent(textSend);
        }
        
        viewFolder.setSelection(0);
        
        hSash.setWeights(new int[] { 30, 70} );
        
        initMenu();
        return control;
    }
    
    protected void onInputKey() {
    	if(user == null || packetHelper == null)
    		return;
    	
		String s = getInput("Input 0x001D reply packet");
		byte[] b = Util.convertHexStringToByte(s);
		if(b == null)
			return;
		ByteBuffer buf = ByteBuffer.wrap(b);
		try {
			GetKeyReplyPacket packet = (GetKeyReplyPacket)packetHelper.parseIn(QQ.QQ_PROTOCOL_FAMILY_BASIC, buf, user, true);
			switch(packet.subCommand) {
				case QQ.QQ_SUB_CMD_REQUEST_FILE_AGENT_KEY:
					user.setFileAgentKey(packet.key);
					break;
				case QQ.QQ_SUB_CMD_REQUEST_UNKNOWN03_KEY:
					user.setUnknown03Key(packet.key);
					break;
				case QQ.QQ_SUB_CMD_REQUEST_UNKNOWN06_KEY:
					user.setUnknown06Key(packet.key);
					break;
				case QQ.QQ_SUB_CMD_REQUEST_UNKNOWN07_KEY:
					user.setUnknown07Key(packet.key);
					break;
				case QQ.QQ_SUB_CMD_REQUEST_UNKNOWN08_KEY:
					user.setUnknown08Key(packet.key);
					break;
			}
		} catch(PacketParseException e1) {
			return;
		}
	}

	/**
     * 输入Agent参数
     */
    protected void onAgent() {
        if(user == null)
            return;
        
        InputDialog dialog = new InputDialog(getShell(), 
                "Agent Key", 
                "Input File Agent Key",
                null, 
                null);
        if(dialog.open() == IDialogConstants.OK_ID) {
            byte[] key = Util.convertHexStringToByte(dialog.getValue());
            user.setFileAgentKey(key);
        } else
            return;
        
        dialog = new InputDialog(getShell(),
                "Agent Token",
                "Input File Agent Token",
                null,
                null);
        if(dialog.open() == IDialogConstants.OK_ID) {
            byte[] token = Util.convertHexStringToByte(dialog.getValue());
            user.setFileAgentToken(token);
        }
    }

    /**
     * start标签按下时
     */
    protected void onStart() {
    	packetHelper = new PacketHelper(QQ.QQ_PROTOCOL_ALL);
        if(main == null) {
        	if(!debug) {
                InitialArgument arg = openDialog();
                if(arg != null) {
                    if(initIndividualMode(arg)) {
                        tiInputArg.setText("User " + user.getQQ());
                        tiInputArg.getParent().layout();
                        tiParse.setEnabled(true);
                    }
                }                        
            }
        } else
            switchDebugMode();
    }

    /**
     * 打开调试参数对话框
     * 
     * @return
     * 			DebugArgument对象
     */
    protected InitialArgument openDialog() {
        InitialArgumentDialog dialog = new InitialArgumentDialog(getShell());
        if(dialog.open() == IDialogConstants.OK_ID)
        	return dialog.getArg();
        else
        	return null;
    }
    
    /**
     * @param event
     */
    protected void onPacketViewerSelectionChanged(SelectionChangedEvent event) {
        IStructuredSelection selection = (IStructuredSelection)event.getSelection();
        IDebugObject obj = (IDebugObject)selection.getFirstElement();
        if(obj != null)
            textBytes.setText(chkBodyOnly.getSelection() ? obj.getBodyHexString() : obj.getHexString());        
    }
    
    /**
     * @return
     * 		当前选择的文本，从当前激活的text中得到
     */
    private String getSelectionText() {
        if(viewFolder.getSelectionIndex() == 0)
            return textBytes.getSelectionText();
        else if(viewFolder.getSelectionIndex() == 1)
            return textInput.getSelectionText();
        else
            return "";
    }
    
    /**
     * 初始化独立模式的参数
     * 
     * @param arg
     * 			调试参数
     * @return true表示初始化成功
     */
    protected boolean initIndividualMode(InitialArgument arg) {
        try {
            // 打开调试模式
            DebugSwitch.getInstance().addDebugListener(this);
            setDebug(true);
            
            // 初始化各重要参数，在解密以后的包必须要用到
            user = new QQUser(arg.getQQ(), arg.getPassword());
            ByteBuffer myBuf = ByteBuffer.wrap(arg.getLoginReply());
            LoginReplyPacket lrp = (LoginReplyPacket)packetHelper.parseIn(QQ.QQ_PROTOCOL_FAMILY_BASIC, myBuf, user, true);
            if(lrp == null)
                throw new PacketParseException("Error Format");
            user.setSessionKey(lrp.sessionKey);  
		    byte[] b = new byte[4 + QQ.QQ_LENGTH_KEY];
		    b[0] = (byte)((user.getQQ() >>> 24) & 0xFF);
		    b[1] = (byte)((user.getQQ() >>> 16) & 0xFF);
		    b[2] = (byte)((user.getQQ() >>> 8) & 0xFF);
		    b[3] = (byte)(user.getQQ() & 0xFF);
		    System.arraycopy(user.getSessionKey(), 0, b, 4, QQ.QQ_LENGTH_KEY);
		    user.setFileSessionKey(md5(b));
            return true;
        } catch (PacketParseException e) {
            DebugSwitch.getInstance().removeDebugListener(this);
            setDebug(false);
            return false;
        }
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.debug.IDebugListener#deliverDebugObject(edu.tsinghua.lumaqq.qq.debug.IDebugObject)
     */
    public void deliverDebugObject(IDebugObject obj) {
        addDebugObject(obj);
    }

    /**
     * @return Returns the debug.
     */
    public boolean isDebug() {
        return debug;
    }
    
    /**
     * @param debug The debug to set.
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    private void showMessage(String msg) {
        MessageDialog.openInformation(getShell(), "Debug", msg);
    }
    
    private void showWarning(String msg) {
        MessageDialog.openWarning(getShell(), "Warning", msg);
    }
    
    private void showError(String msg) {
        MessageDialog.openError(getShell(), "Error", msg);
    }
    
    /**
     * 添加一个DebugObject到包列表
     * 
     * @param obj
     * 		IDebugObject
     */
    public void addDebugObject(IDebugObject obj) {
        addPacketRunnable.obj = obj;
        display.syncExec(addPacketRunnable);
    }
    
    /**
     * 删除一个包
     * 
     * @param index
     */
    public void removeDebugObject(int index) {
        packets.remove(index);
        packetViewer.refresh();
    }
    
    /**
     * 清空packets
     */
    public void removeAllPackets() {
        packets.clear();
        packetViewer.refresh();
    }
    
    /**
     * 解密
     * 
     * @param key
     */
    protected void decryptInput(byte[] key) {
        byte[] b = Util.convertHexStringToByte(textInput.getText());
        if(b == null) 
            showWarning("Can't get byte array from your input!");
        else {
            try {
	            b = crypter.decrypt(b, key);
	            if(b == null)
	                showWarning("Decrypt Error!");
	            else 
	                addDebugObject(new FragmentDO(b));
            } catch(Exception e) {
                showError(e.getMessage());
            }
        }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getInitialSize()
     */
    protected Point getInitialSize() {
        return new Point(800, 600);
    }
}
