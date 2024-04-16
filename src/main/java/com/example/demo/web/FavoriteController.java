package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.common.UserImpl;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.TweetService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = { "/admin/favorites" })
public class FavoriteController {
	@Autowired
	FavoriteService favoriteService;

	@Autowired
	TweetService tweetService;

	@Autowired
	UserService userService;

	/*
	 * お気に入り一覧画面
	 */
	@GetMapping(value = "/")
	public String index(Model model, @AuthenticationPrincipal UserImpl user) {
		User loginUser = user.getUser();
		model.addAttribute("favoriteList", favoriteService.findByUserId(loginUser.getId()));
		return "admin/favorite/index";
	}

	/*
	 * お気に入り登録
	 */
	@GetMapping(value = "/create/{tweetId}")
	public String register(@PathVariable Integer tweetId, Model model, RedirectAttributes ra,
			@AuthenticationPrincipal UserImpl user) {
		FlashData flash;
		try {
			Tweet tweet = tweetService.findById(tweetId);
			User loginUser = user.getUser();
			Favorite favorite = new Favorite();
			favorite.setTweet(tweet);
			favorite.setUser(loginUser);
			// 新規登録
			favoriteService.save(favorite);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/favorites/";
	}

	/*
	 * お気に入り削除
	 */
	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			favoriteService.findById(id);
			favoriteService.deleteById(id);
			flash = new FlashData().success("お気に入りを解除しました");
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			flash = new FlashData().danger("エラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/favorites/";
	}
}