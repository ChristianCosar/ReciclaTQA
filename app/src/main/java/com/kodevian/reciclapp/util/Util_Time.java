package com.kodevian.reciclapp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Gantz on 24/01/15.
 */
public class Util_Time {

    /*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = new Date().getTime();
        if (time > now || time <= 0) {
            return "hace instantes";
        }
        long diff;
        diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "hace instantes";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "hace unos minuto";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return "hace "+diff / MINUTE_MILLIS + " minutos";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "hace una hora";
        } else if (diff < 24 * HOUR_MILLIS) {
            return "hace "+diff / HOUR_MILLIS + " horas";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "ayer";
        } else {
            return "hace "+diff / DAY_MILLIS + " días";
        }
    }

    public static String getAgeWithMonth(Date birthday){
        Calendar todayDate = Calendar.getInstance();
        String edad="";

        Calendar birthdateDate = Calendar.getInstance();

        birthdateDate.setTime(birthday);
        birthdateDate.set(birthdateDate.get(Calendar.YEAR), birthdateDate.get(Calendar.MONTH),
                birthdateDate.get(Calendar.DAY_OF_MONTH));

        if(todayDate.get(Calendar.YEAR)==birthdateDate.get(Calendar.YEAR)){

            if(todayDate.get(Calendar.MONTH)==birthdateDate.get(Calendar.MONTH)){
                if(todayDate.get(Calendar.DAY_OF_MONTH)==birthdateDate.get(Calendar.DAY_OF_MONTH)){
                    edad="Recien nacido";
                }else{
                    if( todayDate.get(Calendar.DAY_OF_MONTH)-birthdateDate.get(Calendar.DAY_OF_MONTH)==1){
                        edad="1 día";
                    }else{
                        edad= todayDate.get(Calendar.DAY_OF_MONTH)-birthdateDate.get(Calendar.DAY_OF_MONTH)+" días";
                    }


                }

            }else{
                if(todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)==1){

                    edad= ((birthdateDate.getActualMaximum(Calendar.DAY_OF_MONTH)-birthdateDate.get(Calendar.DAY_OF_MONTH))+
                            todayDate.get(Calendar.DAY_OF_MONTH))+" días";


                }else{
                    if(todayDate.get(Calendar.DAY_OF_MONTH)>=birthdateDate.get(Calendar.DAY_OF_MONTH)){

                        edad=(todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH))+" meses";
                    }else{

                        edad=(todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)-1)+" meses";
                    }
                }



            }


        }else{
            if(todayDate.get(Calendar.YEAR)-birthdateDate.get(Calendar.YEAR)==1){
                if(todayDate.get(Calendar.MONTH)==birthdateDate.get(Calendar.MONTH)){
                    if(todayDate.get(Calendar.DAY_OF_MONTH)>=birthdateDate.get(Calendar.DAY_OF_MONTH)){
                        edad="1 año";
                    }else{
                        edad="11 meses";
                    }


                }else{
                    if(todayDate.get(Calendar.DAY_OF_MONTH)>=birthdateDate.get(Calendar.DAY_OF_MONTH)){

                        if(12+todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)==12){
                            edad="1 año";
                        }else{
                            if(12+todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)>12){
                                edad ="1 año "+(todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH))+" meses";
                            }else{
                                edad=(12+todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH))+" meses";
                            }
                        }

                    }else{
                        if(12+todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)==12){
                            edad="11 meses";
                        }else{
                            if(12+todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)>12){
                                if(todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)-1==0){
                                    edad ="1 año ";
                                }else{
                                    edad ="1 año "+(todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)-1)+" meses";
                                }

                            }else{
                                edad=(12+todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)-1)+" meses";
                            }
                        }

                    }

                }

            }else{
                if(todayDate.get(Calendar.MONTH)<birthdateDate.get(Calendar.MONTH)){
                    if(todayDate.get(Calendar.DAY_OF_MONTH)>birthdateDate.get(Calendar.DAY_OF_MONTH)){
                        edad=(todayDate.get(Calendar.YEAR)-birthdateDate.get(Calendar.YEAR)-1)+" años y "+
                                (12+todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH))+" meses";
                    }else{
                        if(todayDate.get(Calendar.DAY_OF_MONTH)<birthdateDate.get(Calendar.DAY_OF_MONTH))
                            edad=(todayDate.get(Calendar.YEAR)-birthdateDate.get(Calendar.YEAR)-1)+" años y "+
                                    (12+todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)-1)+" meses";
                    }


                }else{
                    if(todayDate.get(Calendar.MONTH)==birthdateDate.get(Calendar.MONTH)){
                        if(todayDate.get(Calendar.DAY_OF_MONTH)>birthdateDate.get(Calendar.DAY_OF_MONTH)){
                            edad=(todayDate.get(Calendar.YEAR)-birthdateDate.get(Calendar.YEAR))+" años";
                        }else{
                            edad=(todayDate.get(Calendar.YEAR)-birthdateDate.get(Calendar.YEAR)-1)+" años y 11 meses";
                        }

                    }else{


                        if(todayDate.get(Calendar.DAY_OF_MONTH)>birthdateDate.get(Calendar.DAY_OF_MONTH)){
                            edad=(todayDate.get(Calendar.YEAR)-birthdateDate.get(Calendar.YEAR))+" años y "+
                                    (todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH))+" meses";
                        }else{
                            if(todayDate.get(Calendar.DAY_OF_MONTH)<birthdateDate.get(Calendar.DAY_OF_MONTH))
                                edad=(todayDate.get(Calendar.YEAR)-birthdateDate.get(Calendar.YEAR))+" años y "+
                                        (todayDate.get(Calendar.MONTH)-birthdateDate.get(Calendar.MONTH)-1)+" meses";
                        }
                    }

                }
            }

        }

        return  edad;

    }

    public static long  numDaysBeetwenTwoDates(Date dateInitial, Date datePost){
        return  ( dateInitial.getTime() - datePost.getTime() )/ DAY_MILLIS;
    }

    public static String addMoth(int counMoth,Date birthday){
        Calendar birthdateDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM, h:mm a");

        birthdateDate.setTime(birthday);
        birthdateDate.set(birthdateDate.get(Calendar.YEAR) - 1900, birthdateDate.get(Calendar.MONTH) - 1,
                birthdateDate.get(Calendar.DAY_OF_MONTH));

        birthdateDate.add(Calendar.MONTH, counMoth);


        return "Fecha: "+dateFormat.format(birthdateDate.getTime());
    }


    public static Date addMothDate(int counMoth,Date birthday){

        Calendar birthdateDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM, h:mm a");

        birthdateDate.setTime(birthday);
        birthdateDate.set(birthdateDate.get(Calendar.YEAR) - 1900, birthdateDate.get(Calendar.MONTH) - 1,
                birthdateDate.get(Calendar.DAY_OF_MONTH));

        birthdateDate.add(Calendar.MONTH, counMoth);

        Date newDate = calendarToDate(birthdateDate);
        return newDate;
    }

    public static int comparateDateToday(Date date1){

        Calendar todayDate = Calendar.getInstance();

        Calendar date = Calendar.getInstance();
        date.setTime(date1);
        date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH) - 1,
                date.get(Calendar.DAY_OF_MONTH));

        return calendarToDate(date).compareTo(calendarToDate(todayDate));
    }

    public static int comparateDate(Date date1, Date date2){

        return date1.compareTo(date2);
    }

    private static Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    //Convert Calendar to Date
    private static Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }

}
