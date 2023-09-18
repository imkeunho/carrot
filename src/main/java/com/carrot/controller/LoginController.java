package com.carrot.controller;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carrot.domain.AuthVO;
import com.carrot.domain.UserVO;
import com.carrot.handler.CustomUser;
import com.carrot.repository.UserRepository;
import com.carrot.service.NaverLogin;
import com.carrot.service.UserService;

@Controller
@PropertySource("/WEB-INF/application.properties")
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private NaverLogin naverLogin;

	@Value("${oauth2.kakao.client-id}")
	private String KAKAO_ID;
	@Value("${oauth2.kakao.redirect_uri}")
	private String KAKAO_URI;
	@Value("${oauth2.google.client-id}")
	private String GOOGLE_ID;
	@Value("${oauth2.google.redirect_uri}")
	private String GOOGLE_URI;

	@GetMapping("/page/login")
	public String login(HttpSession session) {
		String naverAuthUrl = naverLogin.getAuthorizationUrl(session);
		session.setAttribute("naverAuthUrl", naverAuthUrl);
		session.setAttribute("kakaoID", KAKAO_ID);
		session.setAttribute("kakaoURI", KAKAO_URI);
		session.setAttribute("googleID", GOOGLE_ID);
		session.setAttribute("googleURI", GOOGLE_URI);
		System.out.println("googleID /" + GOOGLE_ID);
		System.out.println("googleURI /" + GOOGLE_URI);

		return "login";
	}

	@GetMapping("/page/signup")
	public String signUp() {
		System.out.println("회원가입페이지 이동");
		return "signUp";
	}

	@PostMapping("/api/signup")
	public String signUp(String id, String nickname, String password, String email, String loc1, String loc2,
			String loc3, RedirectAttributes redirectAttr) {

		String encodepw = encoder.encode(password);

		UserVO vo = new UserVO(id, nickname, encodepw, email, loc1, loc2, loc3);
		AuthVO authVo = new AuthVO(id);
		UserRepository mapper = sqlSession.getMapper(UserRepository.class);

		int result = mapper.signUp(vo);
		result += mapper.signUp_auth(authVo);

		if (result == 2) {
			redirectAttr.addFlashAttribute("msg", "success");
			return "redirect:/page/signup_res";
		}
		redirectAttr.addFlashAttribute("msg", "fail");
		return "redirect:/page/signup_res";
	}

	@GetMapping("/page/signup_res")
	public String loginResult() {
		System.out.println("회원가입 결과창 들렸음");
		return "signUpResult";
	}

	@GetMapping("/api/success")
	public String success(Model model) {
		System.out.println("에이피아이석섹");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserRepository mapper = sqlSession.getMapper(UserRepository.class);
		UserVO vo = mapper.selectById(authentication.getName());
		System.out.println("auth: " + authentication);
		System.out.println("authentication.getAuthorities().getClass():" + authentication.getAuthorities().getClass());
		System.out.println("authentication.getAuthorities():" + authentication.getAuthorities());
		model.addAttribute("user", vo);
		model.addAttribute("msg", authentication.getName() + "님 어서오세요");

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUser customuser = (CustomUser) principal;

		System.out.println(customuser.getUser().getLoc1());
		System.out.println(customuser.getUser().getLoc2());
		System.out.println(customuser.getUser().getLoc3());
		if (customuser.getUser().getLoc1() == null || customuser.getUser().getLoc2() == null
				|| customuser.getUser().getLoc3() == null) {
			model.addAttribute("req_locRegist", "locNull");
		}

		return "imsiLoginSuccess";
		// return "snsLoginSuccess";
	}

	@GetMapping("/access_denied")
	public String deny() {

		return "accessDenied";
	}

	@ResponseBody
	@PostMapping("/api/signup/idcheck")
	public int idCheck(@RequestParam String id) {
		int cnt = 0;
		cnt = userService.idCheck(id);

		return cnt;
	}

	@ResponseBody
	@PostMapping("/api/signup/niccheck")
	public int nicCheck(@RequestParam String nickname) {
		int cnt = 0;
		cnt = userService.nicCheck(nickname);

		return cnt;
	}

	@ResponseBody
	@PostMapping("/api/signup/emailcheck")
	public int emailCheck(@RequestParam String email) {
		int cnt = 0;
		cnt = userService.emailCheck(email);

		return cnt;
	}

	@GetMapping("/page/findid")
	public String findid(String id) {
		return "findId";
	}
}
