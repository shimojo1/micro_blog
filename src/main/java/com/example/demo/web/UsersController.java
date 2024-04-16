package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.common.CustomUser;
import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.common.ValidationGroups.Update;
import com.example.demo.domain.UserInfo;
import com.example.demo.entity.User;
import com.example.demo.form.UserForm;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = { "/users", "/admin/users" })
public class UsersController {
	@Autowired
	UserService userService;

	@Autowired
	MailService mailService;

	/*
	 * 新規作成画面表示
	 */
	@GetMapping(value = "/create")
	public String form(UserForm userForm, Model model) {
		model.addAttribute("userForm", userForm);
		return "users/create";
	}

	/*
	 * 新規登録
	 */
	@PostMapping(value = "/create")
	public String register(@Validated(Create.class) UserForm userForm, BindingResult result, Model model,
			RedirectAttributes ra) {
		FlashData flash;
		try {
			User user = new User();
			if (result.hasErrors()) {
				return "users/create";
			}
			if (!userService.isUnique(userForm.getMail())) {
				// emailが重複している
				flash = new FlashData().danger("メールアドレスが重複しています");
				model.addAttribute("flash", flash);
				return "users/create";
			}
			// 平文のパスワードを暗号文にする
			user.encodePassword(userForm.getPassword());
			user.setNickname(userForm.getNickname());
			user.setMail(userForm.getMail());

			// 新規登録
			userService.save(user);
			flash = new FlashData().success("新規作成しました");

			// メール送信
			mailService.sendMail(user.getMail());

		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/users/login";
	}

	/*
	 * ユーザ一覧画面表示
	 */
	@GetMapping(value = "/list")
	public String list(Model model, @AuthenticationPrincipal CustomUser user) {
		List<UserInfo> userList = new ArrayList<UserInfo>();
		List<User> users = userService.findByIdNot(user.getUser().getId());

		User loginUser = user.getUser();

		for (var userData : users) {
			Boolean isFollow = false;
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userData.getId());
			userInfo.setNickname(userData.getNickname());
			userInfo.setFollowCount(userData.getFollow().size());
			userInfo.setFollowerCount(userData.getFollower().size());
			for (var follower : userData.getFollower()) {
				if (follower.getUserId() == loginUser.getId() && follower.getFollowUserId() == userData.getId()) {
					isFollow = true;
					break;
				}
			}

			userInfo.setIsFollow(isFollow);
			userList.add(userInfo);
		}

		model.addAttribute("users", userList);
		return "admin/users/list";
	}

	/*
	 * ログインユーザの編集画面表示
	 */
	@GetMapping(value = "/edit")
	public String edit(Model model, RedirectAttributes ra) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User editUser;
		try {
			editUser = userService.findByEmail(email);
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin";
		}
		model.addAttribute(editUser);
		return "admin/users/edit";
	}

	/*
	 * 更新
	 */
	@PostMapping(value = "/edit")
	public String update(@Validated(Update.class) User user, BindingResult result, Model model, RedirectAttributes ra) {
		// SpringSecurity側からログインユーザの情報を取得する
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		FlashData flash;
		try {
			User authUser = userService.findByEmail(email);
			if (result.hasErrors()) {
				model.addAttribute(user);
				return "admin/users/edit";
			}
			// リクエスト値とマージ
			authUser.setNickname(user.getNickname());
			userService.save(authUser);
			flash = new FlashData().success("更新しました");
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません!");
		} catch (Exception e) {
			flash = new FlashData().danger("エラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin";
	}
}