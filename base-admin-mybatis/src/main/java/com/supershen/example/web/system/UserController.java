package com.supershen.example.web.system;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baomidou.mybatisplus.plugins.Page;
import com.supershen.example.Constants;
import com.supershen.example.core.Servlets;
import com.supershen.example.entity.User;
import com.supershen.example.service.RoleService;
import com.supershen.example.service.UserService;

/**
 * 用户管理
 */
@Controller
@RequestMapping(value = "/sys/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE) int pageSize, String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<User> page = userService.findPage(searchParams, pageNumber, pageSize);

		model.addAttribute("page", page);
		model.addAttribute("sortType", sortType);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "system/userList";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("entity", new User());
		model.addAttribute("roles", roleService.findRoleAll());
		return "system/userForm";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		User user = userService.findOne(id);
		if(user == null || "0".equals(user.getState())){
			redirectAttributes.addFlashAttribute(Constants.MSG, "用户已不存在");
			return "redirect:/sys/user";
		}
		user.setRoles(roleService.selectRoleByUserId(id));
		model.addAttribute("action", "update");
		model.addAttribute("entity", user);
		model.addAttribute("roles", roleService.findRoleAll());
		return "system/userForm";
	}
	
	@PostMapping(value="save")
	public String save(@Valid @ModelAttribute("entity") User entity, ServletRequest request,
			RedirectAttributes redirectAttributes) {
		String ids = request.getParameter("ids");
		if(ids == null || "".equals(ids)){
			redirectAttributes.addFlashAttribute(Constants.MSG, "保存失败:角色不能为空!");
			return "redirect:/sys/user";
		}
		
		try {
			userService.save(entity, ids);
			redirectAttributes.addFlashAttribute(Constants.MSG, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(Constants.MSG, "保存失败:" + e.getMessage());
		}
		return "redirect:/sys/user";
	}
	
	@GetMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Integer id,ServletRequest request,RedirectAttributes redirectAttributes){
		userService.delete(id);
		redirectAttributes.addFlashAttribute(Constants.MSG, "删除成功!");
		return "redirect:/sys/user";
	}
	
	/**
	 * 重置密码
	 * @param id 用户id
	 */
	@RequestMapping(value = "changePassword/{id}", method = RequestMethod.GET)
	public String changePassword(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
		User address = userService.findOne(id);
		if(address == null || "0".equals(address.getState())){
			redirectAttributes.addFlashAttribute(Constants.MSG, "用户已不存在");
			return "redirect:/sys/user";
		}
		try {
			userService.changePassword(id);
			redirectAttributes.addFlashAttribute(Constants.MSG, "重置成功!初始密码为：" + Constants.PASSWORD);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(Constants.MSG, "重置失败:" + e.getMessage());
		}
		return "redirect:/sys/user";
	}
	
	/**
	 * 请求该Conntroller里的任何方法时会先调用本方法,当更新页面只更新部分字段时,需要添加该方法.
	 */
	@ModelAttribute
	public void modelAttribute(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("entity", userService.findOne(id));
		}
	}

}
