/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.utility;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.IOException;

/**
 *
 * @author sundar.inmaa
 */
public class WriteLog {
	public static void setMessage(String msg)
	{
            Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            //DBUtils d = new DBUtils();

           // String fileName = d.getSQL("XMLclob.LogFile")+dateFormat.format(cal.getTime())+".txt";
             String fileName = "c:\\pixelcrayons\\log\\"+dateFormat.format(cal.getTime())+".txt";
            try
            {
                    BufferedWriter fos1 = new BufferedWriter(new FileWriter(fileName, true));
                    fos1.write (msg);
                    fos1.newLine();
                    fos1.close();
                    System.out.println(msg);
            }
            catch (IOException e2)
            {
                            System.out.println(e2.getMessage()+e2);
            }
        }
}
