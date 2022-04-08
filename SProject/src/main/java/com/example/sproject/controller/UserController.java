package com.example.sproject.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import com.example.sproject.entity.Role;
import com.example.sproject.entity.User;
import com.example.sproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public String viewHomePage() {
        return "welcomePage";
    }
    


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }
    @PostMapping("/process_register")
    public String processRegister(User user) {
        service.registerDefaultUser(user);

        return "register_success";
    }
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);

        return "Table";
    }
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = service.get(id);
        List<Role> listRoles = service.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_form";
    }
    @PostMapping("/users/save")
    public String saveUser(User user) {
        service.save(user);
        return "redirect:/Table";
    }
    @GetMapping("/calendar")
    public String Showcalendar(Model model) {
        return "calendar";
    }

    @GetMapping("/payroll/export")
    public ResponseEntity<Resource> exportAgencyData(){
        try {
            ByteArrayInputStream inputStream = this.service.exportAgencyData();
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+"admin.xlsx").contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(inputStreamResource);
        } catch (IOException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //new controller methods

	@RequestMapping("/new")
	public String showNewProductForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		
		return "new_product";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("user") User user) {
		service.save(user);
		
		return "redirect:/users";
	}
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_user");
		
		User user = service.get(id);
		mav.addObject("user", user);
		
		return mav;
	}	
	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Long id) {
		service.delete(id);
		
		return "redirect:/users";
	}
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String submit(
      @ModelAttribute("user") User user,
      BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        service.save(user);

        return "redirect:/users";
    }

}