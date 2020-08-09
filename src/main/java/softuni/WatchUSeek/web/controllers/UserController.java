package softuni.WatchUSeek.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.WatchUSeek.data.models.binding.UserEditBindingModel;
import softuni.WatchUSeek.data.models.binding.UserRegisterBindingModel;
import softuni.WatchUSeek.data.models.service.RoleServiceModel;
import softuni.WatchUSeek.data.models.service.UserServiceModel;
import softuni.WatchUSeek.data.models.view.UserAllViewModel;
import softuni.WatchUSeek.data.models.view.UserViewModel;
import softuni.WatchUSeek.service.UserService;

import javax.management.relation.RoleNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Register User ")
    public ModelAndView register(@ModelAttribute(name = "model") UserRegisterBindingModel model){
        return view("user/register");
    }


    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm( @ModelAttribute(name = "model") UserRegisterBindingModel model,
                                        BindingResult bindingResult){
        if(this.passwordsDoesNotMatch(model.getPassword(), model.getConfirmPassword())){
            bindingResult.addError(new FieldError("model", "password", "Passwords doesn't match."));
        }
        if (bindingResult.hasErrors()) {
            return view("user/register");
        }
        UserServiceModel serviceModel = mapper.map(model, UserServiceModel.class);
        userService.registerUser(serviceModel);

        return redirect("/users/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login User")
    public ModelAndView login(){
        return view("user/login");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Profile")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView){
        UserServiceModel user = userService.findUserByUsername(principal.getName());
        UserViewModel model = mapper.map(user, UserViewModel.class);
        modelAndView.addObject("model", model);

        return view("user/profile", modelAndView);
    }


    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit User")
    public ModelAndView editProfile(Principal principal,
                                    ModelAndView modelAndView){
        UserServiceModel user = userService.findUserByUsername(principal.getName());
        UserEditBindingModel model = mapper.map(user, UserEditBindingModel.class);
        modelAndView.addObject("model", model);

        return view("user/edit-profile", modelAndView);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm( @ModelAttribute(name = "model") UserEditBindingModel model,
                                           BindingResult bindingResult){
        if(this.passwordsDoesNotMatch(model.getPassword(), model.getConfirmPassword())){
            bindingResult.addError(new FieldError("model", "password", "Passwords don't match."));
        }
        if (bindingResult.hasErrors()) {
            return view("user/edit-profile");
        }

        UserServiceModel serviceModel = mapper.map(model, UserServiceModel.class);
        userService.editUserProfile(serviceModel, model.getOldPassword());

        return redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All Users")
    public ModelAndView allUsers(ModelAndView modelAndView){
        List<UserAllViewModel> users = userService.findAll()
                .stream()
                .map(u -> {
                    UserAllViewModel user = mapper.map(u, UserAllViewModel.class);
                    Set<String> authorities = getAuthoritiesToString(u);
                    user.setAuthorities(authorities);
                    return user;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("users", users);
        return view("user/all-users", modelAndView);
    }


    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView setAdmin(@PathVariable String id){
        userService.makeAdmin(id);

        return redirect("/users/all");
    }



    private Set<String> getAuthoritiesToString(UserServiceModel userServiceModel) {
        return userServiceModel.getAuthorities()
                .stream()
                .map(RoleServiceModel::getAuthority)
                .collect(Collectors.toSet());
    }


    private boolean passwordsDoesNotMatch(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }


}