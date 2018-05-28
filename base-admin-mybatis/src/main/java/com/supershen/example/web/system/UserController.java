package com.supershen.example.web.system;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.supershen.example.entity.User;
import com.supershen.example.service.UserService;

/**
 * 用户管理
 */
@Controller
@RequestMapping(value = "/sys/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) throws Exception {
		userService.delete(id);
		return "{'code':'1',message:'success!'}";
	}
	
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User get(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) throws Exception {
		User user = userService.findOne(id);
		return user;
	}
	
	@RequestMapping(value = "input", method = RequestMethod.GET)
	public String input( RedirectAttributes redirectAttributes,Model model) throws Exception {
		model.addAttribute("entity", new User());
		return "system/userForm";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("entity") User entity, ServletRequest request,
			RedirectAttributes redirectAttributes) {
		Date time = new Date();
		entity.setCreater("admin");
		entity.setUpdater("admin");
		entity.setCreateTime(time);
		entity.setUpdateTime(time);
			userService.save(entity);
		return "redirect:/sys/user/list";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public List<User> list(RedirectAttributes redirectAttributes) throws Exception {
		List<User> users = userService.findAll();
		return users;
	}
	

}
