package com.example.libraryprojectspringmvc.controller;

import com.example.libraryprojectspringmvc.model.User;
import com.example.libraryprojectspringmvc.model.UserRole;
import com.example.libraryprojectspringmvc.service.impl.UserServiceImpl;
import com.example.libraryprojectspringmvc.validator.LibraryProjectValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private final LibraryProjectValidator libraryProjectValidator;
    private final UserServiceImpl userService;

    public MainController(LibraryProjectValidator libraryProjectValidator, UserServiceImpl userService) {
        this.libraryProjectValidator = libraryProjectValidator;
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "homePage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/login/access")
    public String login(String email,
                        String password, ModelMap modelMap) {
        libraryProjectValidator.validateField(email);
        libraryProjectValidator.validateField(password);

        User currentUser = userService.getByEmailAndPassword(email, password);
        if (currentUser != null) {
            modelMap.addAttribute("user", currentUser.getName());
            if (currentUser.getUserRole() == UserRole.ADMIN) {
                return "redirect:/admin/dashboard";
            } else if (currentUser.getUserRole() == UserRole.USER) {
                return "userDashboard";
            }
        }
        return "errorPage";
    }

    @PostMapping("/register/success")
    public String register(HttpServletRequest request, ModelMap modelMap) {
        String name = request.getParameter("name");
        libraryProjectValidator.validateField(name);

        String lastName = request.getParameter("lastName");
        libraryProjectValidator.validateField(name);

        String email = request.getParameter("email");
        libraryProjectValidator.validateField(name);

        String password = request.getParameter("password");
        libraryProjectValidator.validateField(name);

        String confirmPassword = request.getParameter("confirmPassword");
        libraryProjectValidator.validateField(name);

        if (!password.equals(confirmPassword)) {
            modelMap.addAttribute("passwordMatchError", "Password did not match with confirm password");
            return "register";
        }

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setConfirmPassword(confirmPassword);
        user.setUserRole(UserRole.ADMIN);

        userService.save(user);
        return "redirect:/login";
    }
}
