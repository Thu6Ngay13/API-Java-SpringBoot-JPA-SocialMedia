package SocialMedia.APIControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Auth.Email.EmailService;
import SocialMedia.Entities.Account;
import SocialMedia.Enums.TypeBanAccountEnum;
import SocialMedia.Models.BanAccountModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IAccountService;
import SocialMedia.Services.IAdminService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAPIController {

	@Autowired
	IAdminService adminService;

	@Autowired
	IAccountService accountService;

	@Autowired
	EmailService emailService;

	@GetMapping
	@PreAuthorize("hasAuthority('admin:read')")
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("Hi Admin");
	}

	@PostMapping
	@PreAuthorize("hasAuthority('admin:create')")
	@Hidden
	public String post() {
		return "POST:: admin controller";
	}

	@PutMapping
	@PreAuthorize("hasAuthority('admin:update')")
	@Hidden
	public String put() {
		return "PUT:: admin controller";
	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('admin:delete')")
	@Hidden
	public String delete() {
		return "DELETE:: admin controller";
	}

	@GetMapping("/banaccount")
	public ResponseEntity<?> getBanAccounts(HttpServletRequest request) {

		List<Account> accounts = accountService.findAll();
		List<BanAccountModel> banAccountModels = new ArrayList<>();
		for (Account account : accounts) {
			if (account.getUsername().equals("admin"))
				continue;

			BanAccountModel banAccountModel = new BanAccountModel();
			banAccountModel.setAvatar(account.getAvatarURL());
			banAccountModel.setBanned(account.isBanned());
			banAccountModel.setUsername(account.getUsername());
			banAccountModel.setFullname(account.getFullname());
			if (account.isBanned()) {
				banAccountModel.setViewType(TypeBanAccountEnum.IS_BANNED);
			} else {
				banAccountModel.setViewType(TypeBanAccountEnum.IS_NOT_BANNED);
			}
			banAccountModels.add(banAccountModel);
		}

		return new ResponseEntity<Response>(new Response(true, "Thành công", banAccountModels), HttpStatus.OK);
	}

	@PostMapping("/banaccount/unban/{username}")
	public ResponseEntity<?> unbanAccount(@PathVariable(value = "username") String username,
			HttpServletRequest request) {

		Optional<Account> accountF = accountService.findByUsername(username);
		if (accountF.isPresent()) {
			Account account = accountF.get();
			if (account.isBanned()) {
				String body = "<!DOCTYPE html>\r\n"
						+ "<html lang=\"en\">\r\n"
						+ "<head>\r\n"
						+ "    <meta charset=\"UTF-8\">\r\n"
						+ "    <title>Account Banned</title>\r\n"
						+ "    <style>\r\n"
						+ "        body {\r\n"
						+ "            font-family: Arial, sans-serif;\r\n"
						+ "            background-color: #f9f9f9;\r\n"
						+ "            color: #333;\r\n"
						+ "            line-height: 1.6;\r\n"
						+ "            margin: 0;\r\n"
						+ "            padding: 0;\r\n"
						+ "            display: flex;\r\n"
						+ "            justify-content: center;\r\n"
						+ "            align-items: center;\r\n"
						+ "            height: 100vh;\r\n"
						+ "        }\r\n"
						+ "        .container {\r\n"
						+ "            background-color: #fff;\r\n"
						+ "            padding: 20px;\r\n"
						+ "            border-radius: 5px;\r\n"
						+ "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\r\n"
						+ "            text-align: center;\r\n"
						+ "            max-width: 500px;\r\n"
						+ "            width: 100%;\r\n"
						+ "        }\r\n"
						+ "        h1 {\r\n"
						+ "            color: #e74c3c;\r\n"
						+ "            font-size: 24px;\r\n"
						+ "        }\r\n"
						+ "        p {\r\n"
						+ "            margin: 10px 0;\r\n"
						+ "        }\r\n"
						+ "        a {\r\n"
						+ "            color: #3498db;\r\n"
						+ "            text-decoration: none;\r\n"
						+ "            display: inline-block;\r\n"
						+ "            margin-top: 10px;\r\n"
						+ "        }\r\n"
						+ "        a:hover {\r\n"
						+ "            text-decoration: underline;\r\n"
						+ "        }\r\n"
						+ "    </style>\r\n"
						+ "</head>\r\n"
						+ "<body>\r\n"
						+ "    <div class=\"container\">\r\n"
						+ "        <h1>Your account has been unbanned</h1>\r\n"
						+ "        <p>please read the policy carefully before returning.</p>\r\n"
						+ "        <a href=\"http://localhost:8181/api/policy\" target=\"_blank\">OUR POLICIES</a>\r\n"
						+ "    </div>\r\n"
						+ "</body>\r\n"
						+ "</html>\r\n"
						+ "";
				adminService.unbanAccount(account);
				emailService.sendEmail(account.getEmail(), "YOUR ACCOUNT", body);
			}

			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		}

		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}

	@PostMapping("/banaccount/ban/{username}")
	public ResponseEntity<?> banAccount(@PathVariable(value = "username") String username, HttpServletRequest request) {

		Optional<Account> accountF = accountService.findByUsername(username);
		if (accountF.isPresent()) {
			Account account = accountF.get();
			if (!account.isBanned()) {
				String body = "<!DOCTYPE html>\r\n"
						+ "<html lang=\"en\">\r\n"
						+ "<head>\r\n"
						+ "    <meta charset=\"UTF-8\">\r\n"
						+ "    <title>Account Banned</title>\r\n"
						+ "    <style>\r\n"
						+ "        body {\r\n"
						+ "            font-family: Arial, sans-serif;\r\n"
						+ "            background-color: #f9f9f9;\r\n"
						+ "            color: #333;\r\n"
						+ "            line-height: 1.6;\r\n"
						+ "            margin: 0;\r\n"
						+ "            padding: 0;\r\n"
						+ "            display: flex;\r\n"
						+ "            justify-content: center;\r\n"
						+ "            align-items: center;\r\n"
						+ "            height: 100vh;\r\n"
						+ "        }\r\n"
						+ "        .container {\r\n"
						+ "            background-color: #fff;\r\n"
						+ "            padding: 20px;\r\n"
						+ "            border-radius: 5px;\r\n"
						+ "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\r\n"
						+ "            text-align: center;\r\n"
						+ "            max-width: 500px;\r\n"
						+ "            width: 100%;\r\n"
						+ "        }\r\n"
						+ "        h1 {\r\n"
						+ "            color: #e74c3c;\r\n"
						+ "            font-size: 24px;\r\n"
						+ "        }\r\n"
						+ "        p {\r\n"
						+ "            margin: 10px 0;\r\n"
						+ "        }\r\n"
						+ "        a {\r\n"
						+ "            color: #3498db;\r\n"
						+ "            text-decoration: none;\r\n"
						+ "            display: inline-block;\r\n"
						+ "            margin-top: 10px;\r\n"
						+ "        }\r\n"
						+ "        a:hover {\r\n"
						+ "            text-decoration: underline;\r\n"
						+ "        }\r\n"
						+ "    </style>\r\n"
						+ "</head>\r\n"
						+ "<body>\r\n"
						+ "    <div class=\"container\">\r\n"
						+ "        <h1>Your account has been banned</h1>\r\n"
						+ "        <p>for violating our policies.</p>\r\n"
						+ "        <p>If there is any mistake, please give us feedback.</p>\r\n"
						+ "        <a href=\"http://localhost:8181/api/policy\" target=\"_blank\">OUR POLICIES</a>\r\n"
						+ "    </div>\r\n"
						+ "</body>\r\n"
						+ "</html>\r\n"
						+ "";
				adminService.banAccount(account);
				emailService.sendEmail(account.getEmail(), "YOUR ACCOUNT", body);
			}

			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		}

		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}

}
