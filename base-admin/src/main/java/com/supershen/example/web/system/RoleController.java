package com.supershen.example.web.system;

import java.io.UnsupportedEncodingException;
import java.util.List;
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
import com.supershen.example.entity.Perm;
import com.supershen.example.entity.Role;
import com.supershen.example.service.RoleService;
import com.supershen.example.web.Servlets;

/**
 * 角色管理
 * @author gshen
 *
 */
@Controller
@RequestMapping(value = "/sys/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE) int pageSize, String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Role> page = roleService.findPage(searchParams, pageNumber, pageSize);

		model.addAttribute("page", page);
		model.addAttribute("sortType", sortType);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "system/roleList";
	}

	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("role", roleService.findOne(id));
		return "system/roleForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("entity", new Role());
		return "system/roleForm";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("role") Role role, RedirectAttributes redirectAttributes) {
		try {
			roleService.save(role);
			redirectAttributes.addFlashAttribute(Constants.MSG, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(Constants.MSG, "保存失败!");
		}

		return "redirect:/sys/role";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {

		model.addAttribute("action", "update");
		model.addAttribute("entity", roleService.findOne(id));

		return "system/roleForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity") Role entity, RedirectAttributes redirectAttributes) {
		try {
			roleService.update(entity);
			redirectAttributes.addFlashAttribute(Constants.MSG, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(Constants.MSG, "保存失败!");
		}

		return "redirect:/sys/role";
	}

	@RequestMapping(value = "perm/{id}", method = RequestMethod.GET)
	public String perm(@PathVariable("id") Long id, Model model) {

		Role entity = roleService.findOne(id);
		List<Perm> perms = roleService.getPerms();

		model.addAttribute("entity", entity);
		model.addAttribute("perms", perms);

		return "system/permForm";
	}

	/**
	 * 保存权限
	 * @param entity 角色实体
	 * @param perm 权限集合
	 */
	@RequestMapping(value = "savePerm", method = RequestMethod.POST)
	public String savePerm(@Valid Role entity, @RequestParam(value = "perm") Long[] perm,
			RedirectAttributes redirectAttributes) {
		try {
			roleService.savePerm(entity, perm);
			redirectAttributes.addFlashAttribute(Constants.MSG, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(Constants.MSG, "保存失败!");
		}

		return "redirect:/sys/role";
	}

	/**
	 * Ajax请求校验roleName是否唯一。
	 * 
	 */
	@RequestMapping(value = "checkRoleName", produces = "text/plan;charset=UTF-8")
	@ResponseBody
	public String checkRoleName(@RequestParam("name") String roleName, ServletRequest request)
			throws UnsupportedEncodingException {
		String old = request.getParameter("old");

		if (roleName.equals(old)) {
			return "true";
		}

		if (null == roleService.findRoleByName(roleName)) {
			return "true";
		}

		return "false";
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws Exception {
		try {
			roleService.delete(id);
			redirectAttributes.addFlashAttribute(Constants.MSG, "删除成功!");
		} catch (ServiceException e) {
			redirectAttributes.addFlashAttribute(Constants.MSG, "删除失败:" + e.getMessage());
		}

		return "redirect:/sys/role";
	}
}
