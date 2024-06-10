package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.CustomUser;
import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.service.TweetService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = { "/admin" })
public class TweetController {
	@Autowired
	TweetService tweetService;

	@Autowired
	UserService userService;

	/*
	 * マイクロブログホーム画面
	 */
	@GetMapping()
	public String index(Tweet tweet, Model model, @AuthenticationPrincipal CustomUser user) {
		User loginUser = user.getUser();
		tweet.setUser(loginUser);

		List<Tweet> tweetlist = tweetService.findFollowTweet(loginUser.getId());
		model.addAttribute("tweet", tweet);
		model.addAttribute("tweetList", tweetService.exchangeTweetInfoList(tweetlist, loginUser.getId()));
		return "admin/tweet/index";
	}

	/*
	 * つぶやき登録
	 */
	@PostMapping()
	public String register(@Validated(Create.class) Tweet tweet, BindingResult result, Model model,
			RedirectAttributes ra, @AuthenticationPrincipal CustomUser user) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				User loginUser = user.getUser();
				tweet.setUser(loginUser);

				List<Tweet> tweetlist = tweetService.findFollowTweet(loginUser.getId());
				model.addAttribute("tweet", tweet);
				model.addAttribute("tweetList", tweetService.exchangeTweetInfoList(tweetlist, loginUser.getId()));
				return "admin/tweet/index";
			}
			// 新規登録
			tweetService.insert(tweet);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin";
	}

	/*
	 * 個別つぶやき画面
	 */
	@GetMapping(value = "/detail/{tweetId}")
	public String tweet(@PathVariable Integer tweetId, Model model, @AuthenticationPrincipal CustomUser user) {
		try {
			Tweet tweet = tweetService.findById(tweetId);
			User loginUser = user.getUser();
			model.addAttribute("tweet", tweetService.exchangeTweetInfo(tweet, loginUser.getId()));
		} catch (DataNotFoundException e) {
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "admin/tweet/detail";
	}

	/*
	 * 個別ユーザつぶやき画面
	 */
	@GetMapping(value = "/userTweet/{userId}")
	public String userTweet(@PathVariable Integer userId, Model model, @AuthenticationPrincipal CustomUser user) {
		try {
			User loginUser = user.getUser();
			User tweetUser = userService.findById(userId);
			List<Tweet> tweetlist = tweetService.findByUserId(userId);
			model.addAttribute("user", tweetUser);
			var list = tweetService.exchangeTweetInfoList(tweetlist, loginUser.getId());
			model.addAttribute("userTweetList", list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "admin/tweet/userTweet";
	}
}
