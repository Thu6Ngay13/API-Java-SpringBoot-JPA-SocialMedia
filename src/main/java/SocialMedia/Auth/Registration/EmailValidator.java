package SocialMedia.Auth.Registration;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.function.Predicate;
@Service
public class EmailValidator implements Predicate<String>{
//	private static Pattern pattern;
//	private Matcher matcher;
//	private static final String EMAIL_REGEX =   "^[\\\\w\\\\-\\\\.]+@([\\\\w-]+\\\\.)+[\\\\w-]{2,}$";
	@Override
    public boolean test(String s) {
		String regex = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})";
        if (s.matches(regex)){
            return true;
        }
        return false;
    }
}
