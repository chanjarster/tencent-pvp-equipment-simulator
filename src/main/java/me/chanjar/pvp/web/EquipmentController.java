package me.chanjar.pvp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class EquipmentController {

  @RequestMapping(method = GET, path = "/")
  public String index() {
    return "index";
  }


}
