package soa.web;


import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {

    @Autowired
    private ProducerTemplate producerTemplate;


    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q, @RequestParam(name = "max", required = false) Integer max) {

        Map<String, Object> headers = new HashMap<>();
        headers.put("CamelTwitterKeywords", q);

        if(max != null && max <= Integer.MAX_VALUE) {
            headers.put("CamelTwitterCount", max.toString());
        }

        return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);
    }
}