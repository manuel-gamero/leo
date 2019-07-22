package com.mg.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

public class TimeAndDate extends GregorianCalendar implements Cloneable
{	
	private static final long serialVersionUID = -9063146162361143727L;

	private static Logger log = LogManager.getLogger(TimeAndDate.class);
	
    public static String CURRENT_YEAR = "2005";

   /** These are standard formats to input and output more can and should be added as needed */
   /** date formatter */
   static final SimpleDateFormat _ddMMMyyyy = new SimpleDateFormat("dd-MMM-yyyy");
   /** Exp date formatter */
   static final SimpleDateFormat _MMyy = new SimpleDateFormat("MM/yy");   // Y2K
   /** time formatter */
   static final SimpleDateFormat _HHmmss = new SimpleDateFormat("HH:mm:ss");
   /** date formatter */
   static final SimpleDateFormat _ddMMMyyyyHHmmss = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
   
   /** new date format created by CTJ to support the mySQL DB */
   static final SimpleDateFormat _yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // CTJ -- made this public so I could call it from db.Customer
    /** More new date formats for the mySQL DB, this one is JUST the date */
    static final public SimpleDateFormat _yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

   /** Default no argument constructor */
   public TimeAndDate()
   {
      super();
   }

   /**
   * Construct a new TimeAndDate in milliseconds
   * you will have to multiply this by 1000 to get
   * seconds since epoch (like in unix)
   * @param t The time in Milliseconds
   */

   public TimeAndDate(long t)
   {
       setTimeInMillis(t);
   }

   /** Another constructor */
   public TimeAndDate(int year, int month, int date, int hour, int minute)
   {
      super(year, month, date, hour, minute);
   }


   /** the format string to pass to the database to describe the format of 
	date strings that will be passed to it*/
   
   // UPDATE: CTJ- This  looks to be a strictly oracle thing for doing an
   //              alter session for a different DB format.
   // This is now being used in other files, namely CreditCard.java 
   public static final String standardSQLDateFormat = "DD-MON-YYYY HH24:MI:SS";

   /** makes a new instance of TimeAndDate */
   public Object clone()
   {
      TimeAndDate t = new TimeAndDate();
      t.setTimeInMillis(getTimeInMillis());
      return t;
   }

    /** Copy the contents of one TimeAndDate to another TimeAndDate
     * @param dest Where to copy to
     * @param src Where to copy from
     */
    public static void copy(TimeAndDate dest, TimeAndDate src)
    {
	dest.setTimeInMillis(src.getTimeInMillis());
    }


   /** get the time in millis
    * This just provides public access to a protected method of Calander
    */
   public long getInMillis()
   {
      return getTimeInMillis();
   }
   /** set the time from millis
    * This just provides public access to a protected method of Calander
    */
   public void setInMillis(long t)
   {
      setTimeInMillis(t);
   }

   /** Use this method to write a string version of the date for use by the database
     * WARNING: Use this method to write to the database.
     * Do not use getSQLDate().toString() write to the database as this will not set the
     * time correctly!
     */
   public static String getSQLDateStringFromTimeAndDate(TimeAndDate t)
   {
      if(t == null)
      {
         return "NULL";
      }
      else
      {
         // try to get the string format of the time and date
         // This sometimes results in a incorrect string.
         // I don't know why the string is sometimes wrong, but there is a fairly simple work around.
         // The incorrect string can be identified by its length being over 20 characters
         // We will just retry if the string is > 20 chars
	 // Changed by CTJ for the mySQL DB
         String retString = _yyyyMMddHHmmss.format(t.getTime());

         // create a place to store errors
         StringBuffer errors = new StringBuffer("");

         // if there is an error then retry.
         // Try to get a correct number 15 times maximum
         // if correct before then save result to be returned
         int count=0;   // prevent an infinite loop
         while((retString.length() >20) && (count < 15))
         {
            errors.append("\n retString = "+retString);
	    // Changed by CTJ for the mySQL DB
            retString = _yyyyMMddHHmmss.format(t.getTime());
            count++;
         }

         // if there were errors, send an alert
         if(!errors.toString().equals(""))
         {
            // send an error message to me
	     //Alert.send("grant@qspace.com", "TimeStamp.getSQLDateStringFromTimeAndDate() is to long!","TimeStamp.getSQLDateStringFromTimeAndDate() is to long!"+errors.toString());
         }

         // return the good string
         return retString;
      }
   }

   public static TimeAndDate getTimeAndDateFromSQLDateTypeString(String t)
   {
      if(t == null)
      {
         return null;
      }
      else
      {
         TimeAndDate ret= new TimeAndDate();
         if(ret.parseString(t, _yyyyMMdd))
         {
            return ret;
         }
         else
         {
            return null;
         }
      }
   }

   /** Sets the date from a db date that was read AS A STRING.
     * Reading the a database date field as a string is
     * is kind of strange because it returns the time in tenth of seconds.
     * However the database does not keep track of tenths of a second, so the tenths are always 0.
     * This method reads this funny string -tenths of a second and all- and sets the date correctly.
     * This is the preferred way of getting the date from the db.
     * WARNING: Use this method to read from the database.
     * Do NOT use ResultSet.getDate() and setSQLDate for reading the date from the database as it
     *  will NOT get the time correctly!
     */
   public static TimeAndDate getTimeAndDateFromSQLDateString(String t)
   {
      if(t == null)
      {
         return null;
      }
      else
      {
         TimeAndDate ret= new TimeAndDate();
         // SimpleDateFormat is not thread safe, this was throwing weird parsing errors when more than one thread tried
         // to use the date format
         synchronized (_yyyyMMddHHmmss) {
        	 //System.out.println("synchronizing on date parser");
        	 if(ret.parseString(t, _yyyyMMddHHmmss))
             {
                return ret;
             }
             else
             {
                return null;
             }
		}
         
      }
   }


   /** Return the date as a java.sql.Date
     * WARNING: Do not use for writing to the database as this will not set the time correctly!
     *  Use getSQLDateStringFromTimeAndDate() instead.
     */
   public java.sql.Date getSQLDate()
   {
      return new java.sql.Date(getTimeInMillis());
   }
   /** Sets the date from a java.sql.Date
     * WARNING: Do NOT use this for reading the date from the database as it
     *  will NOT get the time correctly!
     * Use ResultSet.getString() and getTimeAndDateFromSQLDateString() instead.
     */
   public void setSQLDate(java.sql.Date t)
   {
      setTimeInMillis(t.getTime());
   }


   /** Return the date as a java.sql.Time
     * WARNING: Do not use for writing to the database as this will not set the time correctly!
     *  Use  getSQLDateStringFromTimeAndDate() instead.
     */
   public java.sql.Time getSQLTime()
   {
      return new java.sql.Time(getTimeInMillis());
   }
   /** Sets the date from a java.sql.Time
     * WARNING: Do NOT use this for reading the date from the database as it
     *  will NOT get the time correctly!
     *  Use ResultSet.getString() and getTimeAndDateFromSQLDateString() instead.
     */
   public void setSQLTime(java.sql.Time t)
   {
      setTimeInMillis(t.getTime());
   }


   /** Return the date as a java.util.Date */
   public java.util.Date getUtilDate()
   {
      return getTime();
   }
   /** Set the date from a java.util.Date */
   public void setUtilDate(java.util.Date t)
   {
      setTime(t);
   }


   /* format a date 'fromformat' to 'toFormat' wished */
   public static String getTransformDate(String date,String fromFormat,String toFormat)
   {
      TimeAndDate td = new TimeAndDate();

      //parse the date being passed based on the format being passed.
      td.parseString(date,fromFormat);

      //return the format desired by the 'toFormat' string being passed.
      return td.toString(toFormat);
   }

    
    /** Gets the age of the date in years (as if it were a 
     *  birthday). If it is exactly 1 year later (your birtday), 
     *  age will return 1.<br><br>
     *  If you were born on Feb 29 (on a leap year), your 
     *  birthday will be considered to be on March 1st on non 
     *  leap years (and Feb 29th on leap years).<br><br>
     *  If this TimeAndDate has not yet occured (it is in the 
     *  future), then the age will be a negative number.
     *  For dates that have not yet passed, but are not a full 
     *  year away we cannot return -0 to indicate this. Therefore
     *  we will return future date ages as 
     *  (-1 * #yearsInFuture) - 1.<br>
     *  For example:<br>
     *  For dates that have not yet passed, but are not a full 
     *  year away will be returned as -1.<br>
     *  Dates more than 1 year in the future, but not 2 years 
     * in the future will be returned as -2.<br>
     *  Dates more than 2 years in the future, but not 3 years 
     *  in the future will be returned as -3.<br>
     *  etc.<br><br>
     *  Times and time zones are always ignored for calculating 
     *  the age.
     *  @return Then age of this TimeAndDate in number of years 
     *  (as if this TimeAndDate were your birthday)
    */
    public int getAge()
    {
	// get now
	TimeAndDate now = new TimeAndDate();

	// Get the number of years difference
	int age = getAge(now, this);

	// if your birthday has not passed yet, 
	// then get the number of years it is in the future.
	if(age <0)
	{
	    age = getAge(this, now);
	    // assert
	    if(age <0)
	    {
		// problem!
		System.out.println("TimeAndDate.getAge() is broken!!! now="+now+" dob="+this+" reverse age="+age);
	    }
	    // adjust so 0 and -0 can be differentiated
	    age = -age-1;
	}
	return age;
    }

    /** get the age, but return -1 if the date has not 
     *  yet passed */
    static private int getAge(TimeAndDate now, TimeAndDate dob)
    {
	// Get the number of years difference
	int age = now.get(TimeAndDate.YEAR)-dob.get(TimeAndDate.YEAR);

	// if your birthday has not passed yet, then subtract 
	// one from your age
	// if your birth month has not passed OR
	// it is your birth month and your birtday has not passed,
	// then subtract one year from your age.
	if(
        (now.get(TimeAndDate.MONTH) < dob.get(TimeAndDate.MONTH)) 
        || ((now.get(TimeAndDate.MONTH) == dob.get(TimeAndDate.MONTH)) && (now.get(TimeAndDate.DAY_OF_MONTH) < dob.get(TimeAndDate.DAY_OF_MONTH))))
	{
	    age--;
	}
	if(age >=0)
	{
	    return age;
	}
	else
        {
	    return -1;
	}
    }

    /**
     * This method will return the number of months between the two
     * TimeAndDate objects passed in. (Blatelently stolen from a
     * JavaWorld article called "Working in Java Time")
     **/
    public static int getElapsedMonths(TimeAndDate g1, TimeAndDate g2) {
	int elapsed = 0;
	TimeAndDate gc1, gc2;

	if (g2.after(g1)) {
	    gc2 = (TimeAndDate) g2.clone();
	    gc1 = (TimeAndDate) g1.clone();
	}
	else {
	    gc2 = (TimeAndDate) g1.clone();
	    gc1 = (TimeAndDate) g2.clone();
	}

	gc1.setTimePortionToZero();

	gc2.setTimePortionToZero();

	while ( gc1.before(gc2) ) {
	    gc1.add(TimeAndDate.MONTH, 1);
	    elapsed++;
	}
	return elapsed;
    }

    /**
     * This method will returned the number of days from the beginning of the
     * period THROUGH the end of the period.
     * So for begin and end periods of: 2004-02-01 and 2004-02-28,
     * The number of days returned would be 28. The method getElapsedDays()
     * would return 27 since it is not inclusive of the ending period. It
     * will instead determine the number of days leading up to the beginning
     * of the end period.
     *
     * @param g1 The beginning period.
     * @param g2 The ending period.
     * @return The number of days from the beginning through the end period.
     */
    public static int getElapsedPeriod(TimeAndDate g1, TimeAndDate g2){
         return getElapsedDays(g1, g2) + 1;
    }

    /**
     * This method will return the number of days between the two
     * TimeAndDate objects passed in.
     **/
    public static int getElapsedDays(TimeAndDate g1, TimeAndDate g2) {
	int elapsed = 0;
	TimeAndDate gc1, gc2;

	if (g2.after(g1)) {
	    gc2 = (TimeAndDate) g2.clone();
	    gc1 = (TimeAndDate) g1.clone();
	}
	else {
	    gc2 = (TimeAndDate) g1.clone();
	    gc1 = (TimeAndDate) g2.clone();
	}

	gc1.setTimePortionToZero();

	gc2.setTimePortionToZero();

	while ( gc1.before(gc2) ) {
	    gc1.add(TimeAndDate.DATE, 1);
	    elapsed++;
	}
	return elapsed;
    }


    public void setTimePortionToZero() {
	this.clear(TimeAndDate.MILLISECOND);
        this.clear(TimeAndDate.SECOND);
        this.clear(TimeAndDate.MINUTE);
	this.clear(TimeAndDate.HOUR);
        this.clear(TimeAndDate.HOUR_OF_DAY);
    }

    /** Returns the number of days in the month of the date
     ** that is passed to this function.
     **/
    public static int getNumberOfDaysInMonth(TimeAndDate date) {
	return(date.getActualMaximum(TimeAndDate.DAY_OF_MONTH));
    }

   /******************* to strings **********************/
   /** Return the time and date in the standard format */
   public String toString()
   {
      return _ddMMMyyyyHHmmss.format(getTime());
   }
   /** Return the time and date in the format passed in (the format is in the form used by SimpleDateFormat)*/
   public String toString(String format)
   {
      return (new SimpleDateFormat(format)).format(getTime());
   }
   /** Return the time and date in the format passed in (the format is in the form used by SimpleDateFormat)*/
   public String toString(SimpleDateFormat sdf)
   {
      return sdf.format(getTime());
   }

   /** a convenience function to return a date in a certain format */
   public String toddMMMyyyyHHmmssString()
   {
      return _ddMMMyyyyHHmmss.format(getTime());
   }
   /** a convenience function to return a date in a certain format */
   public String toddMMMyyyyString()
   {
      return _ddMMMyyyy.format(getTime());
   }
   /** a convenience function to return a date in a certain format */
   public String toMMyyString()
   {
      return _MMyy.format(getTime());
   }
   /** a convenience function to return a date in a certain format */
   public String toHHmmssString()
   {
      return _HHmmss.format(getTime());
   }


   /******************* from strings **********************/
   /** Parse the given string 't' asuming it is in standard format */
   public boolean parse(String t)
   {
      return parseString(t,_ddMMMyyyyHHmmss);
   }
   /** Parse the given string 'timeString' asuming it is of the format given by 'format'
    * (the format is in the form used by SimpleDateFormat)
    */
   public boolean parseString(String timeString, String format)
   {
      return(parseString(timeString, new SimpleDateFormat(format), false));
   }
    
    public boolean parseString(String timeString, String format, boolean lenient)
    {
	return(parseString(timeString, new SimpleDateFormat(format), lenient));
    }

    public boolean parseString(String timeString, SimpleDateFormat sdf, boolean lenient) {
	sdf.setLenient(lenient);
	return(parseString(timeString, sdf));
    }

   /** Parse the given string 't' asuming it is of the format given by 'sdf' */
   public boolean parseString(String t, SimpleDateFormat sdf)
   {
      try
      {
         if((t == null) ||
            (t.equals("")))
         {
            return false;
         }

         setTime(sdf.parse(t));
         return true;
      }
      catch (ParseException e)
      {
         // This will send email to a programmer to look at the problem
	 log.warn("Parse exception: Parsing string='"+t+"' Pattern='"+sdf.toPattern()+"' Error at offset "+e.getErrorOffset());
         return false;
      }
   }
   /** a convenience function to parse the string passed in */
   public boolean parseddMMMyyyy(String t)
   {
      return parseString(t,_ddMMMyyyy);
   }
   /** a convenience function to parse the string passed in */
   public boolean parseMMyy(String t)
   {
      return parseString(t,_MMyy);
   }
   /** a convenience function to parse the string passed in */
   public boolean parseHHmmss(String t)
   {
      return parseString(t,_HHmmss);
   }
   /** a convenience function to parse the string passed in */
   public boolean parseddMMMyyyyHHmmss(String t)
   {
      return parseString(t,_ddMMMyyyyHHmmss);
   }

   /** Method to convert time in PST to GMT **/
   public void convertGMTToPST(){
      add(HOUR_OF_DAY, -7);
   }

   /** Method to convert time in PST to GMT **/
   public void convertPSTToGMT(){
      add(HOUR_OF_DAY, 7);
   }
  
}
