package com.zwj.controller;

import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by zhangwj on 16-8-15.
 */
@Controller
@Path("/index")
public class IndexController {
    @GET
    @Path("/")
    @Produces("application/json")
    public Response findItemsByUserId(@QueryParam("id") int userId) throws Exception {
        return Response.ok("get id="+userId).build();
    }
}
