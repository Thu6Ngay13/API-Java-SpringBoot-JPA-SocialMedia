package SocialMedia.APIControllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HelloWorld {

	@GetMapping("/")
	public ResponseEntity<?> Home(HttpServletRequest request, Model model) {
		return new ResponseEntity<Object>("Hello", HttpStatus.OK);
	}
}
