package com.mg.util.date;


import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * A class for getting and outputing the time and date 
 * from and to many other date types especially from a html form.
 * It differs from TimeAndDate in that it can be set to a 'null' 
 * state (meaning we don't know the time and date)
 * This includes to and from a:<br>
 * string,<br>
 * long, <br>
 * java.util.date and time, <br>
 * java.sql.date, time, and timestamp. <br><br>
 * 
 * To use this for a html form:<br>
 * Always first create an instance of NullableTimeAndDate 
 * (it will by default be null).<br>
 * Decide and set the required temp 
 * fields(tempYear, tempMonth, etc.), and if the date is lenient or not.<br>
 * Have your setters on the form point to the proper temp fields 
 * (tempYear, tempMonth, etc.)<br>
 * When the form is submitted, call validateTempAndRecompute. If the 
 * tempData is good, 
 * then it will be stored for all other methods to get access 
 * to and the temp data will be cleared. 
 * Else if the temp data is bad, it be accessible only from the 
 * getters for the fields. <br>
 * If the date validated correctly, you will be able to access it 
 * for the db using one of the getters for sql (For example getSQLTimestamp).<br>
 * If the date did not validate, then your form will still access 
 * the data that the user entered using the temp field getters.<br><br>
 *
 * When getting a date for a form that will be prepopulated with 
 * the stored db date, you will:<br>
 * Again, always first create an instance of NullableTimeAndDate 
 * (it will by default be null).<br>
 * Again, decide and set the required temp fields(tempYear, tempMonth, etc.), 
 * and if the date is lenient or not.<br>
 * Again, Have your setters one the form point to the proper temp fields 
 * (tempYear, tempMonth, etc.)<br>
 * Have your NullableTimeAndDate set from the correct sql setter 
 * (For example setSQLTimestamp).<br>
 * Now when you call the field getters, all the data will come 
 * from what was in the db (until a setter is called).<br>
 * After a setter is called, the data will come from the set 
 * values as above.<br>
 * If the user hits the cancel button or wants to refresh to the 
 * db version, you can call setUsingTempValues(false)<br>
 *
 * @author  Grant Swanson
 */
public class NullableTimeAndDate
{
   private TimeAndDate time = null;     

   /** Whether to be strict or lenient in checking a string validity when parsing a string.    
    * @see java.text.SimpleDateFormat#isLenient
    */
   private boolean lenient = DEFAULT_IS_LENIENT;
   
   /** Creates new NullableTimeAndDate with the current time and date (and timezone)*/
   public NullableTimeAndDate()
   {   
      // leave time as null
   }
    
   // contructor to take a time and date 
   public NullableTimeAndDate(TimeAndDate tad)
   {
       this.setTimeAndDate(tad);
   }
 
   /** The default format of the time when output or input as a string */
   public static final SimpleDateFormat DEFAULT_STRING_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   
   /** The default value for parsing strings (to be strict or lenient in checking their validity) 
    * @see java.text.SimpleDateFormat#isLenient
    */
   public static final boolean DEFAULT_IS_LENIENT = false;
   
   /** Holds value of property NULL_DATE_AS_STRING. 
    * By default it is the empty string, but could also be null
    */
   public static final String NULL_DATE_AS_STRING = "";

   
   
   /** This is true if we do not know what the time is. 
    * @return True if the time and date are unknown. Else returns false.
    */
   public boolean getIsNull()
   {
      return time == null;
   }
   
   /** This sets the time and date to null to indicate we don't know them.
    */
   public void setIsNull()
   {
      time = null;
   }

   /** Helper function to create a new time and date and set isLenient on it */
   private TimeAndDate getNewTimeAndDate()
   {
      TimeAndDate t = new TimeAndDate();
      t.setLenient(lenient);
      return t;
   }
   
   /** Getter for property lenient.
    * @return Value of property lenient.
    */
   public boolean isLenient() {
       return lenient;       
   }
   
   /** Setter for property lenient.
    * @param lenient New value of property lenient.
    */
   public void setLenient(boolean pLenient) {
       lenient = pLenient;
       if(time != null)
       {
           time.setLenient(lenient);
       }
   }   
   

   /******************* TimeAndDate **********************/   
   /** Get the time as a TimeAndDate.
    * Null will be returned if the time is unknown (<code>getIsNull()</code> is true).
    * @return The time.
    */
   public TimeAndDate getTimeAndDate()
   {
      if(time == null)
      {
         return null;
      }
      else
      {         
         return (TimeAndDate)time.clone();
      }
   }
   
   /** Set the time from a TimeAndDate.
    * @param timeAndDate New value of property timeAndDate.
    */
   public void setTimeAndDate(TimeAndDate timeAndDate)
   {
      if(timeAndDate == null)
      {
         time = null;
      }
      else
      {
         if(time == null)
         {
            time = getNewTimeAndDate();
         }
         TimeAndDate.copy(time, timeAndDate);
         time.setLenient(lenient);
      }
   }

   /******************* milliseconds **********************/   
   /** Get the time in milliseconds
    * See the description of the class java.util.Date for a discussion of slight discrepancies that may arise between "computer time" and coordinated universal time (UTC).
    * @return The number of milliseconds difference between the time of this object and midnight, January 1, 1970 UTC.
    * @throws NullPointerException when the the time is unknown (<code>getIsNull()</code> is true)
    */
   public long getInMillis() throws NullPointerException
   {
      return time.getInMillis();
   }
   /** Set the time in milliseconds
    * See the description of the class java.util.Date for a discussion of slight discrepancies that may arise between "computer time" and coordinated universal time (UTC).
    * @param t The number of milliseconds to set as the difference between the time of this object and midnight, January 1, 1970 UTC.

    */
   public void setInMillis(long t) 
   {
      if(time == null)
      {
         time = getNewTimeAndDate();
      }
      time.setInMillis(t);
   }
   
   /******************* java.sql.Date **********************/
   /** Return the date as a java.sql.Date
    *  Null will be returned if the time is unknown (<code>getIsNull()</code> is true).
    *  @return The date as a java.sql.Date
    */
   public java.sql.Date getSQLDate()
   {
      if(time == null)
      {
         return null;
      }
      else
      {
         return new java.sql.Date(time.getInMillis());
      }   
   }
   /** Sets the date from a java.sql.Date
    *  @param t The date as a java.sql.Date
    */
   public void setSQLDate(java.sql.Date t)
   {
      if(t == null)
      {
         time = null;
      }
      else          
      {
         if(time == null)
         {
            time = getNewTimeAndDate();
         }
         time.setInMillis(t.getTime());
      }
   }
   
   
   /******************* java.sql.Time **********************/
   /** Return the date as a java.sql.Time
    *  Null will be returned if the time is unknown (<code>getIsNull()</code> is true).
    *  @return The date as a java.sql.Time
    */
   public java.sql.Time getSQLTime()
   {
      if(time == null)
      {
         return null;
      }
      else
      {
         return new java.sql.Time(time.getInMillis());
      }
   }
   /** Sets the date from a java.sql.Time
    *  @param t The date as a java.sql.Time
    */
   public void setSQLTime(java.sql.Time t)
   {
      if(t == null)
      {
         time = null;
      }
      else 
      {
         if(time == null)
         {
            time = getNewTimeAndDate();
         }
         time.setInMillis(t.getTime());
      }
   }
   
   
   /******************* java.sql.Timestamp **********************/
   /** Return the date as a java.sql.Timestamp
    *  Null will be returned if the time is unknown (<code>getIsNull()</code> is true).
    *  @return The date as a java.sql.Timestamp
    */
   public java.sql.Timestamp getSQLTimestamp()
   {
      if(time == null)
      {
         return null;
      }
      else
      {
         return new java.sql.Timestamp(time.getInMillis());
      }
   }
   /** Sets the date from a java.sql.Timestamp
    *  @param t The date as a java.sql.Timestamp
    */
   public void setSQLTimestamp(java.sql.Timestamp t)
   {
      if(t == null)
      {
         time = null;
      }
      else 
      {
         if(time == null)
         {
            time = getNewTimeAndDate();
         }      
         time.setInMillis(t.getTime());
      }
   }
   

   
   /******************* java.util.Date **********************/
   /** Return the date as a java.util.Date
    *  Null will be returned if the time is unknown (<code>getIsNull()</code> is true).
    *  @return The date as a java.util.Date
    */   
   public java.util.Date getUtilDate()
   {
      if(time == null)
      {
         return null;
      }
      else
      {      
         return time.getTime();
      }
   }
   /** Sets the date from a java.util.Date
    *  @param t The date as a java.util.Date
    */
   public void setUtilDate(java.util.Date t)
   {
      if(t == null)
      {
         time = null;
      }
      else 
      {
         if(time == null)
         {
            time = getNewTimeAndDate();
         }      
         time.setTime(t);
      }
   }

   
   /******************* to strings **********************/
   /** Get the time and date in the DEFAULT_STRING_FORMAT 
    * @return The time and date in the DEFAULT_STRING_FORMAT
    */
   public String toString()
   {
      if(time == null)
      {
         return NULL_DATE_AS_STRING; 
      }
      else
      {
         return(time.toString());
      }
   }
   /** Get the time and date in the given format.
    *  <code>SimpleDateFormat</code> is used to format the time and date into a string.
    * @param format The format to return the date in.
    * @return The time and date in the given format
    * @see java.text.SimpleDateFormat#format
    */
   public String toString(String format)
   {
      if(time == null)
      {
         return NULL_DATE_AS_STRING; 
      }
      else
      {
         return (new SimpleDateFormat(format)).format(time.getTime());
      }
   }
   /** Get the time and date in the given format.
    * @param sdf The format to return the date in.
    * @return The time and date in the given format
    * @see java.text.SimpleDateFormat#format
    */
   public String toString(SimpleDateFormat sdf)
   {
      if(time == null)
      {
         return NULL_DATE_AS_STRING; 
      }
      else
      {
         return sdf.format(time.getTime());
      }
   }
   
   
   /******************* from strings **********************/
   /** Parse the string into this time and date object
    * see isLenient() for weather to forbid or normalize invalid dates (For example Feb 30).
    * The format of <code>t</code> is assumend to be DEFAULT_STRING_FORMAT.
    * @param t The string to parse
    * @return True if parsed successfully into a time and date
    * @see java.text.SimpleDateFormat#parse
    */   
   public boolean parse(String t)
   {
      return parse(t,DEFAULT_STRING_FORMAT);
   }
   

   /** Parse the string into this time and date object. 
    * see isLenient() for weather to forbid or normalize invalid dates (For example Feb 30).
    * @param t The string to parse
    * @param format The format of <code>t</code>.
    * @return True if parsed successfully into a time and date
    * @see java.text.SimpleDateFormat#parse
    */
   public boolean parse(String timeString, String format)
   {
      return parse(timeString, new SimpleDateFormat(format));
   }
   
   /** Parse the string into this time and date object. 
    * see isLenient() for weather to forbid or normalize invalid dates (For example Feb 30).
    * @param t The string to parse
    * @param sdf The format that <code>t</code> is in.Cannot be NULL otherwise a NullPointerException will be thrown.
    * @param isLenient Weather to forbid or normalize invalid dates (For example Feb 30).
    * @return True if parsed successfully into a time and date. Right now, false is 
    *   returned if the passed in time string is null. 
    * @see java.text.SimpleDateFormat#parse
    */
   public boolean parse(String t, SimpleDateFormat sdf)
   {
      try
      {
         if((t == null) ||
            (t.equals("")))
         {
            time = null;
            return false; // should this return true or false???
         }
         sdf.setLenient(lenient);

         java.util.Date tmp = sdf.parse(t);
         if(time == null)
         {
            time = getNewTimeAndDate();
         }         
         time.setTime(tmp);
         return true;
      }
      catch (ParseException e)
      {
         //ExceptionHandler.handleException(e, "Parse exception: Parsing string='"+t+
         //"' Pattern='"+sdf.toPattern()+"' Error at offset "+e.getErrorOffset());
         //System.out.println("e="+e+"\nParse exception: Parsing string='"+t+
         //"' Pattern='"+sdf.toPattern()+"' Error at offset "+e.getErrorOffset());
         
         time = null;
         return false;
      }
   }

   
   
   /* get a particular field 
    * Note MONTHS are 1-12 (rather than 0-11 as gregorian calendar)
    */
   public String get(int field)
   {
      if(time== null)
      {
         return NULL_DATE_AS_STRING;
      }
      else
      {
         int t2 = time.get(field);
         
         // if it is the month, then add 1 because greg. cal. uses 0-11 and we use 1-12
         if(field == TimeAndDate.MONTH)
         {
            t2=t2+1;               
         }
         return String.valueOf(t2);
      }
   }


   /** Gets the age of the date in years (as if it were a birthday)
    *  If it is exactly 1 year later (your birtday), age will return 1.<br><br>
    *  If you were born on Feb 29 (on a leap year), your birthday will be considered to be on March 1st on non leap years (and Feb 29th on leap years).<br><br>
    *  If this TimeAndDate has not yet occured (it is in the future), then the age will be a negative number.
    *  For dates that have not yet passed, but are not a full year away we cannot return -0 to indicate this. Therefore
    *  we will return future date ages as (-1 * #yearsInFuture) - 1.<br>
    *  For example:<br>
    *  For dates that have not yet passed, but are not a full year away will be returned as -1.<br>
    *  Dates more than 1 year in the future, but not 2 years in the future will be returned as -2.<br>
    *  Dates more than 2 years in the future, but not 3 years in the future will be returned as -3.<br>
    *  etc.<br><br>
    *  Times and time zones are always ignored for calculating the age.
    *  @return Then age of this TimeAndDate in number of years (as if this TimeAndDate were your birthday) 
    *  @throws NullPointerException when the the time is unknown (<code>getIsNull()</code> is true)
    *  @see TimeAndDate.getAge()
    */    
   public int getAge()
   {
      return time.getAge();
   }
   
   
  /** for testing */
   public static void testNull()
   {
      System.out.println("Get current date and time and output it in all formats");
      NullableTimeAndDate t = new  NullableTimeAndDate();
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");
      
      
      System.out.println("toString(ddMMMyyyyHHmmss) conversion");
      t.parse(t.toString());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");
      
      /**
      System.out.println("millis conversion");
      t = new  NullableTimeAndDate();
      t.setInMillis(t.getInMillis());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");
      */
      
      System.out.println("sqlDate conversion");
      t.setSQLDate(t.getSQLDate());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");

      System.out.println("sqlTimestamp conversion");
      t.setSQLTimestamp(t.getSQLTimestamp());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");      
      
      System.out.println("utilDate conversion");
      t.setUtilDate(t.getUtilDate());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");
   }
   
   public static void test2()
   {
      System.out.println("Get current date and time and output it in all formats");
      NullableTimeAndDate t = new  NullableTimeAndDate();
      t.setInMillis(System.currentTimeMillis());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");
      
      
      System.out.println("toString(ddMMMyyyyHHmmss) conversion");
      t.parse(t.toString());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");
      
      System.out.println("millis conversion");
      t.setInMillis(t.getInMillis());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");
      
      System.out.println("sqlDate conversion");
      t.setSQLDate(t.getSQLDate());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");

      System.out.println("sqlTimestamp conversion");
      t.setSQLTimestamp(t.getSQLTimestamp());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");      
      
      System.out.println("utilDate conversion");
      t.setUtilDate(t.getUtilDate());
      System.out.println("toString="+t+" getIsNull()="+t.getIsNull());
      System.out.println("");
   }

   /** for testing */
   public static void main(String args[])
   {
      testNull();
      test2();
   }     
}
 
   
   
   
