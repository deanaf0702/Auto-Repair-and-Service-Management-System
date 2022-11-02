package AutoCenter.services;


import java.text.SimpleDateFormat;
import java.util.Date;

;

public class Helper {

	public static String dateConvertToString(Date startDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		//Date stringDate = formatter.parse(input);
		
		String stringDate = formatter.format(startDate);
		return stringDate;
	}
	
	
}
