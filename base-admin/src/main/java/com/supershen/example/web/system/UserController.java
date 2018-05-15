package com.supershen.example.web.system;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.supershen.example.Constants;
import com.supershen.example.entity.User;
import com.supershen.example.service.UserService;
import com.supershen.example.utils.UserHelper;
import com.supershen.example.web.Servlets;

/**
 * 用户管理
 */
@Controller
@RequestMapping(value = "/sys/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE) int pageSize, String sortType,
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
		model.addAttribute("roles", userService.findRoleAll());
		return "system/userForm";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		User address = userService.findOne(id);
		if(address == null || "0".equals(address.getState())){
			redirectAttributes.addFlashAttribute(Constants.MSG, "用户已不存在");
			return "redirect:/sys/user";
		}
		model.addAttribute("action", "update");
		model.addAttribute("entity", userService.findOne(id));
		model.addAttribute("roles", userService.findRoleAll());
		return "system/userForm";
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws Exception {
		if(id == 1){
			redirectAttributes.addFlashAttribute(Constants.MSG, "删除失败:admin账户不能删除");
			return "redirect:/sys/user";
		}
		
		if(id == UserHelper.getCurrentUserId()){
			redirectAttributes.addFlashAttribute(Constants.MSG, "删除失败:用户不能删除自己");
			return "redirect:/sys/user";
		}
		
		try {
			userService.delete(id);
			redirectAttributes.addFlashAttribute(Constants.MSG, "删除成功!");
		} catch (ServiceException e) {
			redirectAttributes.addFlashAttribute(Constants.MSG, "删除失败:" + e.getMessage());
		}

		return "redirect:/sys/user";
	}
	
	@RequestMapping(value = "changePassword/{id}", method = RequestMethod.GET)
	public String changePassword(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
		User address = userService.findOne(id);
		if(address == null || "0".equals(address.getState())){
			redirectAttributes.addFlashAttribute(Constants.MSG, "用户已不存在");
			return "redirect:/sys/user";
		}
		try {
			userService.changePassword(id);
			redirectAttributes.addFlashAttribute(Constants.MSG, "重置成功!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(Constants.MSG, "重置失败:" + e.getMessage());
		}
		return "redirect:/sys/user";
	}
	
	/**
	 * Ajax请求校验userName是否唯一。
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "checkUserName", produces = "text/plan;charset=UTF-8")
	public @ResponseBody String checkUserName(@RequestParam("username") String username, ServletRequest request)
			throws UnsupportedEncodingException {
		String old = request.getParameter("old");
		if (username.equals(old)) {
			return "true";
		}
		User user = userService.findUserByName(username);
		if (null == user || "0".equals(user.getState())) {
			return "true";
		}

		return "false";
	}
	
	/**
	 * 请求该Conntroller里的任何方法时会先调用本方法,当更新页面只更新部分字段时,需要添加该方法.
	 */
	@ModelAttribute
	public void modelAttribute(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("entity", userService.findOne(id));
		}
	}
}
