package com.zwj.server;

import com.zwj.utils.ConfigUtils;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import javax.ws.rs.ext.Provider;
import java.util.Collection;
import com.zwj.controller.IndexController;

@Component
public class NettyServer {

    @Autowired
    ApplicationContext ac;

    ConfigurableNettyJaxrsServer netty;

    public void start() {

        ResteasyDeployment dp = new ResteasyDeployment();

        Collection<Object> providers = ac.getBeansWithAnnotation(Provider.class).values();
        Collection<Object> controllers = ac.getBeansWithAnnotation(Controller.class).values();

        Assert.notEmpty(controllers);

        // extract providers
        if (providers != null) {
            dp.getProviders().addAll(providers);
        }
        // extract only controller annotated beans
        dp.getResources().addAll(controllers);

        netty = new ConfigurableNettyJaxrsServer();
        netty.initBootstrap().setOption("reuseAddress", true);
        netty.setDeployment(dp);
        netty.setPort(Integer.parseInt(ConfigUtils.getProperty("server.port", "8087")));
        netty.setRootResourcePath(ConfigUtils.getProperty("server.context", "/"));
        netty.setSecurityDomain(null);
        netty.start();
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("root_context.xml");
        Assert.notNull(ac);
        Assert.notNull(ac.getBean(IndexController.class));

        NettyServer netty = ac.getBean(NettyServer.class);

        netty.start();

    }

    @PreDestroy
    public void cleanUp() {
        netty.stop();
    }

}
