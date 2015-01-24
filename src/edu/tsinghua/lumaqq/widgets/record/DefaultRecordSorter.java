package edu.tsinghua.lumaqq.widgets.record;

import java.util.Comparator;

import edu.tsinghua.lumaqq.record.RecordEntry;

/**
 * 缺省记录排序器
 *
 * @author luma
 */
public class DefaultRecordSorter implements Comparator<Object> {
	public static final DefaultRecordSorter INSTANCE = new DefaultRecordSorter();
	
	public int compare(Object o1, Object o2) {
		RecordEntry e1 = (RecordEntry)o1;
		RecordEntry e2 = (RecordEntry)o2;
		if(e1.time - e2.time != 0)
			return (e1.time - e2.time > 0) ? -1 : 1;
		else
			return 0;
	}
}
