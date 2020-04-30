package com.dreamsjewelrystudio.util.comparators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.dreamsjewelrystudio.models.Product;

public class DateComparator implements Comparator<Product>{
	private boolean isReversed;
	public DateComparator(boolean isReversed) {
		this.isReversed = isReversed;
	}

	@Override
	public int compare(Product o1, Product o2) {
		String o1DateTime = o1.getDate() + " " + o1.getTime();
		String o2DateTime = o2.getDate() + " " + o2.getTime();
	    try {
			Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(o1DateTime);
			Date date2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(o2DateTime);
			
			if(!isReversed) {
				if(date1.compareTo(date2) > 0)
					return -1;
				else if(date1.compareTo(date2) < 0)
					return 1;
				else if(date1.compareTo(date2) == 0)
					return 0;
			}else {
				if(date1.compareTo(date2) > 0)
					return 1;
				else if(date1.compareTo(date2) < 0)
					return -1;
				else if(date1.compareTo(date2) == 0)
					return 0;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return 0;
	}

}
