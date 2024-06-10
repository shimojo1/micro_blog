package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.CustomUser;
import com.example.demo.common.FlashData;
import com.example.demo.entity.Follow;
import com.example.demo.entity.User;
import com.example.demo.service.FollowService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = { "/admin/follow" })
public class FollowController {
	@Autowired
	FollowService followService;

	@Autowired
	UserService userService;

	/*
	 * フォロー登録
	 */
	@GetMapping(value = "/create/{userId}")
	public String register(@PathVariable Integer userId, Model model, RedirectAttributes ra,
			@AuthenticationPrincipal CustomUser user) {
		FlashData flash;
		try {
			User loginUser = user.getUser();
			Follow follow = new Follow();
			follow.setFollowUserId(userId);
			follow.setUserId(loginUser.getId());
			// 新規登録
			followService.insert(follow);
			flash = new FlashData().success("フォローしました");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/users/list";
	}
	
	/*
	 * フォロー解除
	 */
	@GetMapping(value = "/delete/{userId}")
	public String delete(@PathVariable Integer userId, Model model, RedirectAttributes ra,
			@AuthenticationPrincipal CustomUser user) {
		FlashData flash;
		try {
			User loginUser = user.getUser();
			followService.deleteByUserIdAndFollowUserId(loginUser.getId(), userId);
			flash = new FlashData().success("フォロー解除しました");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/users/list";
	}
}
