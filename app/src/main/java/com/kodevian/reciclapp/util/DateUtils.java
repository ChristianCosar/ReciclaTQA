package com.kodevian.reciclapp.util;

import java.util.Date;

/**
 * Created by Leo on 18/01/2016.
 */
public class DateUtils {

    private static final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
    private static final long MILLSECS_PER_MOTH =30*MILLSECS_PER_DAY;
    private static final long MILLSECS_PER_YEAR = 365*MILLSECS_PER_DAY;

    public static long  numDaysBeetwenTwoDates(Date dateInitial, Date datePost){
        return  ( dateInitial.getTime() - datePost.getTime() )/ MILLSECS_PER_DAY;
    }

    public static long  numMothBeetwenTwoDates(Date dateInitial,Date datePost){
        return  ( dateInitial.getTime() - datePost.getTime() )/ MILLSECS_PER_MOTH;
    }
    public static long  numYearBeetwenTwoDates(Date dateInitial,Date datePost){
        return  ( dateInitial.getTime() - datePost.getTime() )/ MILLSECS_PER_YEAR;
    }

    public static String returnTime(Date dateInitial){
        Date dateActual= new Date();

        if(dateActual.getYear()-dateInitial.getYear()>0){
            if(dateActual.getMonth()-dateInitial.getMonth()>0){

            }

        }
        if(numDaysBeetwenTwoDates(dateInitial,dateActual)<=30){
            return numDaysBeetwenTwoDates(dateInitial,dateActual)+" Días ";
        }else  if((numMothBeetwenTwoDates(dateInitial, dateActual)<=12)){
            return numMothBeetwenTwoDates(dateInitial,dateActual)+" Meses";
        }else{
            return numYearBeetwenTwoDates(dateInitial,dateActual)+" Años";
        }
    }

}
