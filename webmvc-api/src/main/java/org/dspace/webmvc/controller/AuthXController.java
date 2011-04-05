package org.dspace.webmvc.controller;

import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.dspace.webmvc.bind.annotation.RequestAttribute;
import org.dspace.webmvc.utils.RequestInfo;
import org.dspace.webmvc.utils.RequestInfoService;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthXController {
    private static Logger log = Logger.getLogger(AuthXController.class);

    private RequestInfoService ris = new RequestInfoService();

    @RequestMapping("/unauthorized")
    public String handleAuthorizeException(@RequestAttribute Context context, HttpServletRequest request, HttpServletResponse response) {
        if (context.getCurrentUser() == null) {
            if (ris.getRequestInfoSession(request) == null) {
                // Take stored info from request scope and place in session to restore later
                RequestInfo info = ris.getRequestInfoAttribute(request);
                ris.setRequestInfoSession(request, info);
            }

            // Start authentication
            return "redirect:/login";
        }

        return "/pages/unauthorized";
    }
}
