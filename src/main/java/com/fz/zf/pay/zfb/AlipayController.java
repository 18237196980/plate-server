package com.fz.zf.pay.zfb;

import com.fz.zf.config.PropertiesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/ali/pay")
@ResponseBody
public class AlipayController {
    @Autowired
    PropertiesBean propertiesBean;
}
