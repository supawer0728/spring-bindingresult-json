package com.parfait.springbindingresultjson.adder;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import com.parfait.springbindingresultjson.adder.model.AdderRequest;
import com.parfait.springbindingresultjson.adder.model.AdderResult;
import com.parfait.springbindingresultjson.validation.ValidationResult;

@RestController
@RequestMapping("/add")
public class AdderController {

	private final Validator adderRequestValidator;
	private final MessageSource messageSource;

	@Autowired
	public AdderController(@Qualifier("adderRequestValidator") Validator adderRequestValidator,
			MessageSource messageSource) {
		this.adderRequestValidator = adderRequestValidator;
		this.messageSource = messageSource;
	}

	@PostMapping
	public AdderResult add(AdderRequest request, BindingResult bindingResult) throws BindException {

		adderRequestValidator.validate(request, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}

		return new AdderResult(request.getA() + request.getB());
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationResult handleBindException(BindException bindException, Locale locale) {
		return ValidationResult.create(bindException, messageSource, locale);
	}
}