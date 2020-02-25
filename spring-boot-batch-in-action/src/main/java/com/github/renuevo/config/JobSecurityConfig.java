package com.github.renuevo.config;

import com.github.renuevo.vo.XmlItemVo;
import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.xstream.XStreamMarshaller;

@Configuration
public class JobSecurityConfig {

    public JobSecurityConfig(XStreamMarshaller marshaller) {
        XStream xstream = marshaller.getXStream();
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{XmlItemVo.class});
    }

}
