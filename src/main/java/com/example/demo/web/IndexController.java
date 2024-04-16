package com.example.demo.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = { "/" })
public class IndexController {
	@Autowired
	UserService userService;

	/*
	 * ログイン画面表示
	 */
	@GetMapping(value = { "/", "/users", "/users/login", "/admin/users" })
	public String index(UserForm userForm, Model model, Principal principal) {
		// ログインしているか確認
		if (principal != null) {
			return "redirect:/admin";
		} else {
			return "users/login";
		}
	}
}