package com.supershen.example.web;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.supershen.example.Constants;
import com.supershen.example.common.ShiroDbRealm.ShiroUser;
import com.supershen.example.entity.User;
import com.supershen.example.service.AccountService;
import com.supershen.example.utils.UserHelper;

/**
 * 个人资料.
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String updateForm(Model model) {
		Long id = UserHelper.getCurrentUserId();

		User user = accountService.findUserById(id);

		model.addAttribute("entity", user);

		return "profile";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String update(@ModelAttribute("entity") User entity, RedirectAttributes redirectAttributes) {
		accountService.updateUser(entity);

		updateCurrentUserName(entity.getUsername());
		
		redirectAttributes.addFlashAttribute(Constants.MSG, "保存成功!");

		return "redirect:/profile";
	}

	/**
	 * 更新Shiro中当前用户的用户名.
	 */
	private void updateCurrentUserName(String userName) {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		shiroUser.username = userName;
	}

	@RequestMapping(value = "passwd", method = RequestMethod.GET)
	public String passwd() {
		return "passwd";
	}

	@RequestMapping(value = "passwd", method = RequestMethod.POST)
	public String passwd(ServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		Long userId = UserHelper.getCurrentUserId();

		String old_password = request.getParameter("old_password");
		String new_password = request.getParameter("new_password");
		String plain_password = request.getParameter("plain_password");

		if (StringUtils.isBlank(old_password)) {
			model.addAttribute(Constants.MSG, "旧密码不能为空!");
			return "passwd";
		}

		if (StringUtils.isBlank(new_password)) {
			model.addAttribute(Constants.MSG, "新密码不能为空!");
			return "passwd";
		}

		if (StringUtils.isBlank(plain_password)) {
			model.addAttribute(Constants.MSG, "确认密码不能为空!");
			return "passwd";
		}

		if (!plain_password.equals(new_password)) {
			model.addAttribute(Constants.MSG, "新密码和确认密码必须相同!");
			return "passwd";
		}

		int ret = accountService.changePassword(userId, old_password, new_password);

		if (0 == ret) {
			model.addAttribute(Constants.MSG, "旧密码不正确!");
			return "passwd";
		} 
		
		if (1 == ret) {
			redirectAttributes.addFlashAttribute(Constants.MSG, "密码修改成功!");
			return "redirect:/profile";
		}

		return "redirect:/profile";
	}
}
