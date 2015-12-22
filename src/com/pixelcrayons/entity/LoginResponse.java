/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.entity;
//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
//import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author sundar.inmaa
 */
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "login")
public class LoginResponse
{

    Integer code;
    String message ;
    Integer token;
    String name;
    String empcode;
    String empType;
    public LoginResponse()
    {

    }
    public LoginResponse(Integer code,
                         String message,
                         Integer token,
                         String name,
                         String empcode,
                         String empType)
    {
        super();
        this.code = code;
        this.message = message;
        this.token = token;
        this.name = name;
        this.empType = empType;
        this.empcode = empcode;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }
    
}
