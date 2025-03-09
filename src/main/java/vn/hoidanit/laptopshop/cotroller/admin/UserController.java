package vn.hoidanit.laptopshop.cotroller.admin;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.RoleService;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping(path = "/admin/user")
    public String getUserPage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    @GetMapping(path = "/admin/user/{id}")
    public String getUserDetailPage(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping(path = "/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(path = "/admin/user/create")
    public String handleCreateUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult,
                                   @RequestParam("avatarFile") MultipartFile file) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors ) {
            System.out.println (error.getField() + " - " + error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return "admin/user/create";
        }
        user.setAvatar(uploadService.handleSaveUploadFile(file, "avatar"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName(user.getRole().getName()));
        userService.save(user);
        return "redirect:/admin/user";
    }

    @GetMapping(path = "/admin/user/update/{id}")
    public String getUpdateUserPage(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user/update";
    }

    @PostMapping(path = "/admin/user/update")
    public String handleUpdateUserPage(@ModelAttribute("user") User user) {
        User currentUser = userService.findById(user.getId());
        if (Objects.nonNull(currentUser)) {
            currentUser.setAddress(user.getAddress());
            currentUser.setPhone(user.getPhone());
            currentUser.setFullName(user.getFullName());
            userService.save(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping(path = "/admin/user/delete/{id}")
    public String getDeleteUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("user", new User());
        return "admin/user/delete";
    }

    @PostMapping(path = "/admin/user/delete")
    public String handleDeleteUserPage(@ModelAttribute("user") User user, Model model) {
        userService.deleteById(user.getId());
        return "redirect:/admin/user";
    }
}
