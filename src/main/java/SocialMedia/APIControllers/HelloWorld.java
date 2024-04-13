package SocialMedia.APIControllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class HelloWorld {

	@GetMapping("/")
	public ResponseEntity<?> Home(HttpServletRequest request, Model model) {
		return new ResponseEntity<Object>("Hello", HttpStatus.OK);
	}
}
