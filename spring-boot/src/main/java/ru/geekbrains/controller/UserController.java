package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.entity.User;
import ru.geekbrains.persist.repo.UserRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String allUsers(Model model, @RequestParam(value = "name", required = false) String name) {
        logger.info("Filtering by name: {}", name);

        List<User> allUsers;
        if(name == null || name.isEmpty()) {
            allUsers = userRepository.findAll();
        } else {
            allUsers = userRepository.findByLoginLike("%" + name + "%");
        }
        model.addAttribute("users", allUsers);
        return "users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user";
        }

        userRepository.save(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/user";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundExceptionHandler(NotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.getModel().put("entity_name", exception.getMessage());
        return modelAndView;
    }
}
