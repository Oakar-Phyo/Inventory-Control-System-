package com.inventorycontrolsystem.IVCS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventorycontrolsystem.IVCS.formmodel.UserForm;
import com.inventorycontrolsystem.IVCS.model.Permission;
import com.inventorycontrolsystem.IVCS.model.StockIn;
import com.inventorycontrolsystem.IVCS.model.User;
import com.inventorycontrolsystem.IVCS.model.UserRole;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.service.PermissionJpa;
import com.inventorycontrolsystem.IVCS.service.UserJpa;
import com.inventorycontrolsystem.IVCS.service.UserRoleJpa;

@Controller
public class UserController {
	
	@Autowired
	UserRoleJpa roleJpa;
	@Autowired
	UserJpa userJpa;
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	PermissionJpa permissionJpa;

	//Profile
	@GetMapping("/IVCS_PRF_01")
	public String profile(  ) {
		return "User/IVCS_PRF_01";
	}
	
	@PostMapping("/profile")
	public String updateProfile(@RequestParam("password") String pass, @RequestParam("email") String email, Model model) {
		List<User> usrlist = userJpa.findAll();
		for (User usr:usrlist) {
			if (usr.getEmail().equals(email)) {
				User user = userJpa.selectForUser(email);
				if (!user.getCofpass().equals(pass)) {
					model.addAttribute("passError", "Invalid Password!!");
					return "User/IVCS_PRF_01";
				}
				model.addAttribute("user", user);
				return "User/IVCS_PRF_03";
			}
		}
		model.addAttribute("mailError", "Invalid Email!!");
		return "User/IVCS_PRF_01";
	}
	
	@PostMapping("/updateProfile/{id}")
	public String updateProfile(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
			@PathVariable("id") Integer id, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "User/IVCS_PRF_03";
		}

		if (!user.getPass().equals(user.getCofpass())) {
			model.addAttribute("error", "Confirm Password must be same with Password!!");
			return "User/IVCS_PRF_03";
		}
		

		User us = userJpa.selectOne(id);

		List<Permission> list = permissionJpa.select(us.getEmail());
		for (Permission p : list) {
			p.setEmail(user.getEmail());
			permissionJpa.update(p);
		}

		us.setName(user.getName());
		us.setEmail(user.getEmail());
		us.setPhone(user.getPhone());
		us.setPass(encoder.encode(user.getPass()));
		us.setCofpass(user.getCofpass());
		
		userJpa.update(us);
		model.addAttribute("message", "User Update Successful");
		return "redirect:/IVCS_DSH_01";
	}

	
	
//User Start
	
	@GetMapping("/IVCS_USR_01")
	public String addUser(@ModelAttribute("user") UserForm user, Model model) {
       model.addAttribute("list", userJpa.findAll());
		List<UserRole> list = roleJpa.findAll();
		model.addAttribute("rolelist", list);
		return "User/IVCS_USR_01";
	}
	
	@PostMapping("/addUser")
	public String addUser(@ModelAttribute("user") @Valid UserForm user, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAtt) {
		
		if (bindingResult.hasErrors()) {
			List<User>usr=userJpa.findAll();
			model.addAttribute("list", usr);
			List<UserRole> list = roleJpa.findAll();
			model.addAttribute("rolelist", list);
			
			return "User/IVCS_USR_01";
		}
		if (!user.getPass().equals(user.getCofpass())) {
			List<UserRole> list = roleJpa.findAll();
			model.addAttribute("rolelist", list);
			model.addAttribute("list", user);
			redirectAtt.addFlashAttribute("error", "Confirm Password must be same with Password!!");
			return "redirect:/IVCS_USR_01";
		}
		List <User> usrlist= new ArrayList<User>();
		usrlist = userJpa.getByEmail(user);
		if(usrlist.size()!=0) {
			if(usrlist.get(0).getEmail().equals(user.getEmail())){
				redirectAtt.addFlashAttribute("mailmessage", "Your Mail is already have try with another Mail!!");
				return "redirect:/IVCS_USR_01";
			}
		}else {

		UserRole usrRole=roleJpa.findById(user.getRole());
		String permit[] =usrRole.getPrmit().split(",");
		for(String per:permit) {
			Permission permission=new Permission(user.getEmail(),"ROLE_"+per);
			permissionJpa.save(permission);
		}
		
		User a = new User(user.getName(), user.getEmail(),user.getPhone(), encoder.encode(user.getPass()), user.getCofpass(),
				true, usrRole);		
		userJpa.inser(a);
		redirectAtt.addFlashAttribute("message", "User Insert Successful");
	

		}
		return "redirect:/IVCS_USR_01";
	}


	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redircetAttri) {
		User user = userJpa.selectOne(id);
		permissionJpa.delete(user.getEmail());
		userJpa.delete(id);
		redircetAttri.addFlashAttribute("message", "User Delete Successful");
		return "redirect:/IVCS_USR_01";
	}

	
	@GetMapping("/IVCS_USR_03/{id}")
	
	public String userUpdate(@PathVariable("id")Integer id,Model model) {
		
		List<UserRole> list = roleJpa.findAll();
		model.addAttribute("rolelist", list);
		User a = userJpa.selectOne(id);
		UserForm form = new UserForm(a.getId(),a.getName(), a.getEmail(),a.getPhone(), a.getPass(), a.getCofpass(),
				 a.getRole().getId());
		model.addAttribute("userupdate", form);

		return "User/IVCS_USR_03";
	}
	
	@PostMapping("/updateUser/{id}")
	public String updateUser(@ModelAttribute("userupdate") UserForm user, @PathVariable("id") Integer id, Model model
			,BindingResult bindingResult,RedirectAttributes redirectAttri) {
		
		if (bindingResult.hasErrors()) {
			return "User/IVCS_USR_03";
		}
		
		User usr = userJpa.selectOne(id);
		permissionJpa.delete(usr.getEmail());

		UserRole usrRole=roleJpa.findById(user.getRole());
		String permit[] =usrRole.getPrmit().split(",");
		for(String per:permit) {
			Permission permission=new Permission(user.getEmail(),"ROLE_"+per);
			permissionJpa.save(permission);
		}
		
		usr.setName(user.getName());
		usr.setEmail(user.getEmail());
		usr.setPhone(user.getPhone());
		usr.setRole(usrRole);
		usr.setEnable(true);
		userJpa.update(usr);
		redirectAttri.addFlashAttribute("message", "User Update Successful");

		return "redirect:/IVCS_USR_01";
	}
	

//	User end
	
	
//	UserRole Start
	
	@GetMapping("/IVCS_ROL_01")
	public String role(Model model) {
		model.addAttribute("listRole", roleJpa.findAll());
		UserRole newAddRole = new UserRole();
		model.addAttribute("newAddRole", newAddRole);
		return "/User/IVCS_ROL_01";
	}
	

	@PostMapping("/addRole")
	public String addRole(@ModelAttribute("newAddRole") @Valid UserRole role, BindingResult bindingResult,
			RedirectAttributes redirectAttri,Model model) {
		
		if (bindingResult.hasErrors()) {
			List<UserRole>list=roleJpa.findAll();
			model.addAttribute("listRole", list);
			return "/User/IVCS_ROL_01";
		}
		
		List<UserRole> roleList = new ArrayList<UserRole>();
		roleList=roleJpa.getByName(role);
		
		if (roleList.size() != 0) {

		 if (roleList.get(0).getName().equals(role.getName())) {
				redirectAttri.addFlashAttribute("error", "User Role has been already existed!!");
				return "redirect:/IVCS_ROL_01";
		 }
				
			} else {
				
				UserRole rol=new UserRole();
				rol.setPrmit(role.getPrmit());
				
				roleJpa.insert(role);
			redirectAttri.addFlashAttribute("message", "User Role Insert Success");

		}	
		
		return "redirect:/IVCS_ROL_01";

	}
	
	@GetMapping("/deleteRole/{id}")
	public String deleteRole(@PathVariable("id") Integer id, RedirectAttributes redirectAtt) {
       
       List<UserRole> userRole =roleJpa.findAll();
		if(userRole.size()!=0) {

			if(isRoleIdAlreadyUsed(id)) {

			redirectAtt.addFlashAttribute("error", "Role is Already Used!!");
			return "redirect:/IVCS_ROL_01";

			} else {
			roleJpa.deleteById(id);
			redirectAtt.addFlashAttribute("message", "Role Delete Successful");
			return "redirect:/IVCS_ROL_01";
			}
		}
		
       return "redirect:/IVCS_ROL_01";
	}
	
	private boolean isRoleIdAlreadyUsed(Integer userId) {
		List<User> users = userJpa.findAll();
		for (User user : users) {
		if (user.getRole() == null ) {
		continue;
		}
		if (userId == user.getRole().getId()) {
		return true;
		}
		}
		return false;
		}
	

	
	@GetMapping("/IVCS_ROL_03/{id}")
	public String userRoleUpdate(@PathVariable("id") Integer id, Model model) {
		UserRole role = roleJpa.findById(id);
		model.addAttribute("role", role);
		return "User/IVCS_ROL_03";
	}
	
	@PostMapping("/updateRole/{id}")
	public ModelAndView updateRole(@PathVariable("id") Integer id, @ModelAttribute("role") @Valid UserRole role,
			BindingResult bindingResult,RedirectAttributes redirectAttri) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("User/IVCS_ROL_03");
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("role", role);
			return modelAndView;
		}
		UserRole updateRole = roleJpa.findById(id);
		updateRole.setName(role.getName());
		updateRole.setPrmit(role.getPrmit());
		roleJpa.update(updateRole);
		modelAndView.setViewName("redirect:/IVCS_ROL_01");
		redirectAttri.addFlashAttribute("message", "User Role Update Successful");

		return modelAndView;
	}
	
//	UserRole end
}
