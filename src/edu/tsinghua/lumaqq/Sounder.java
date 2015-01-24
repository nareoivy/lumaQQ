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
package edu.tsinghua.lumaqq;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Queue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 播放声音的精灵线程
 * 
 * @author luma
 */
public class Sounder extends Thread implements MetaEventListener {	
    // Log对象
    private static Log log = LogFactory.getLog(Sounder.class);
    // 停止标志
    private volatile boolean stop;
    // 本次需要播放的声音文件对象
    private Object currentSound;
    // 是否使能声音提示
    private boolean enable;
    // 声音播放队列，里面存放要播放的声音文件名
    private Queue<String> playQueue;
    // 序列化器
    private Sequencer sequencer;
    // midi文件是否已经结束
    private boolean midiEOM;
    
	/**
	 * 构造函数
	 */
	public Sounder() {
		stop = false;
		enable = true;
		playQueue = new LinkedList<String>();
		setName("Sound");
		setDaemon(true);
	}
	
	/**
	 * 把要播放的声音文件名加入到播放队列
	 * @param filename 声音文件名
	 */
	public synchronized void play(String filename) {
		// 播放
	    playQueue.offer(filename);
	    this.notify();
	}
	
	/**
	 * @return 下一个要播放的声音文件名
	 */
	public synchronized String getNext() {
		return playQueue.poll();
	}
	
	/**
	 * 设置停止标志
	 * @param b
	 */
	public synchronized void setStop(boolean b) {
		stop = b;
		this.notify();
	}
	
	/**
	 * @return true表示当前需要停止
	 */
	public synchronized boolean isStop() {
	    return stop;
	}
	
	/**
	 * 清空播放队列
	 */
	public synchronized void clear() {
	    playQueue.clear();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		log.debug("声音播放精灵线程已经启动");
		while(!isStop()) {
		    // 等待直到有声音要播放或者需要停止时才醒来
			synchronized(this) {
				try {
				    if(stop) break;
				    if(playQueue.size() == 0)
				        this.wait();
				} catch (InterruptedException e) {
				    // 没有什么要做的
				}
			}
			
			// 播放队列中的声音文件
			while(enable && !isStop()) {
			    // 得到下一个文件名
			    String filename = getNext();
			    // 如果文件名为null，跳出
			    if(filename == null) break;
			    // 如果载入声音文件成功，播放
			    if(loadSound(filename))
			        playSound();
			}	
			
			clear();
		}
		log.debug("声音播放精灵线程正常退出");
	}
	
    /**
     * 播放声音
     */
    private void playSound() {
        // 设置midi文件没有结束
        midiEOM = false;
        if(currentSound instanceof Sequence || currentSound instanceof BufferedInputStream) {
	        /* 如果当前声音是Sequence对象，调用序列化器播放他 */
            if(sequencer == null)
                openSequencer();
            
            sequencer.start();
            while (!midiEOM) {
                try {
                    sleep(99);
                } catch (Exception e) {
                    break;
                }
            }
            sequencer.stop();
            sequencer.close();
        } else if(currentSound instanceof Clip) {
            /* 如果声音对象是Clip，直接播放 */
            Clip clip = (Clip) currentSound;
            clip.start();
            try {
                sleep(99);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            while (clip.isActive()) {
                try {
                    sleep(99);
                } catch (Exception e) {
                    break;
                }
            }
            clip.stop();
            clip.close();
        }
        currentSound = null;
    }
    
	/**
	 * 载入声音文件
	 * @param filename
	 * @return
	 */
	private boolean loadSound(String filename) {
	    // 从文件读取声音的原数据
	    File file = new File(filename);
        try {
          currentSound = AudioSystem.getAudioInputStream(file);
        } catch (Exception e) {
            try {
                FileInputStream is = new FileInputStream(file);
                currentSound = new BufferedInputStream(is, 1024);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                currentSound = null;
                return false;
            }
        }
        
        // 根据声音对象的不同类型调用不同的处理方法
        if(currentSound instanceof AudioInputStream) {
            try {
                AudioInputStream stream = (AudioInputStream) currentSound;
                AudioFormat format = stream.getFormat();

                // 由于目前无法处理 ALAW/ULAW 格式的播放， 所以把 ALAW/ULAW 转换成 PCM                
                if((format.getEncoding() == AudioFormat.Encoding.ULAW) || (format.getEncoding() == AudioFormat.Encoding.ALAW)) {
                    AudioFormat tmp = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), format.getSampleSizeInBits() * 2, format
                            .getChannels(), format.getFrameSize() * 2, format.getFrameRate(), true);
                    stream = AudioSystem.getAudioInputStream(tmp, stream);
                    format = tmp;
                }
                DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(), ((int) stream.getFrameLength() * format.getFrameSize()));

                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);
                currentSound = clip;
            } catch (Exception ex) {
                log.error(ex.getMessage());
                currentSound = null;
                return false;
            }
        } else if(currentSound instanceof Sequence || currentSound instanceof BufferedInputStream) {
            try {
                sequencer.open();
                if(currentSound instanceof Sequence) {
                    sequencer.setSequence((Sequence) currentSound);
                } else {
                    sequencer.setSequence((BufferedInputStream) currentSound);
                }
                log.trace("Sequence Created");
            } catch (InvalidMidiDataException imde) {
                log.error("不支持的声音文件格式");
                currentSound = null;
                return false;
            } catch (Exception ex) {
                log.error(ex.getMessage());
                currentSound = null;
                return false;
            }
        }
        
        return true;
	}
	
	/**
	 * @return Returns the enable.
	 */
	public boolean isEnable() {
		return enable;
	}
	
	/**
	 * @param enable The enable to set.
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
    /**
     * 打开序列化器
     */
    public void openSequencer() {
        try {
            sequencer = MidiSystem.getSequencer();
	        sequencer.addMetaEventListener(this);
        } catch (Exception ex) {
            log.error("Midi Sequencer初始化失败");
            sequencer = null;
            return;
        }
    }

    /* (non-Javadoc)
     * @see javax.sound.midi.MetaEventListener#meta(javax.sound.midi.MetaMessage)
     */
    public void meta(MetaMessage meta) {
        if(meta.getType() == 47) { // 47表示音轨的结束
            midiEOM = true;
        }
    }
}
