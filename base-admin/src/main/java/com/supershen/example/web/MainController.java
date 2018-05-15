package com.supershen.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.supershen.example.utils.UserHelper;

@Controller
@RequestMapping("/")
public class MainController {

	@RequestMapping(method = RequestMethod.GET)
	public String view(Model model){
		model.addAttribute("user", UserHelper.getCurrentUser());
		return "main";
	}
}
