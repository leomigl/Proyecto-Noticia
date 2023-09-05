/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Egg.news.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErroresControlador implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView enderizarPaginaError(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error");
        String errorMsq = "";

        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsq = "El recurso solicitado no existe.";
                break;
            }
            case 401: {
                errorMsq = "No se encuentra autorizado.";
                break;
            }
            case 403: {
                errorMsq = "No tiene permisos para acceder al recurso.";
                break;
            }
            case 404:{
                errorMsq="el recurso solicitado no fue encontrado";
                break;
            }
            case 500: {
                errorMsq = "ocurrio un error interno.";
                break;
                      
            }
            }
        
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsq);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
    public String getErrorPath(){
        return "/error.html";
    }
}
