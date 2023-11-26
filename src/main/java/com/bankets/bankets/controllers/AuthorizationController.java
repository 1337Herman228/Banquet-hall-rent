package com.bankets.bankets.controllers;

import com.bankets.bankets.models.*;
import com.bankets.bankets.repo.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthorizationController {

    public static AuthorizedUser authorizedUser = new AuthorizedUser();

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PersonRepository personRepository;

    private String errorMessage = "";
    private String errorMessage_reg = "";
    private String regMessage = "";

    private String login = "";
    private String fullName = "";
    private String phone = "";
    private String email = "";


    @GetMapping("/reg")
    public String reg(Model model) {

        model.addAttribute("login", login);
        model.addAttribute("fullName", fullName);
        model.addAttribute("phone", phone);
        model.addAttribute("email", email);

        errorMessage = "";

        model.addAttribute("errorMessage", errorMessage_reg);
        return "reg";
    }

    @PostMapping("/reg")
    public String reg(@RequestParam String login, @RequestParam String password, @RequestParam String fullName,  @RequestParam String phone, @RequestParam String email, Model model) {

        try
        {
            Users users = usersRepository.findByLogin(login).orElseThrow();
            errorMessage_reg = "Login is already used";

            this.login = login;
            this.fullName = fullName;
            this.phone = phone;
            this.email = email;
        }
        catch (Exception e)
        {
            Person person = new Person(phone,email,fullName);
            personRepository.save(person);

            Long role_id = rolesRepository.findByPosition("Пользователь").orElseThrow().getRole_id();

            Users users = new Users(role_id,person.getPerson_id(),login,password);
            usersRepository.save(users);

            regMessage = "You have been registred";
            errorMessage_reg = "";
            errorMessage = "";
            return "redirect:/login";
        }


        return "redirect:/reg";

    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, Model model) {

            try
            {
                Users users = usersRepository.findByLogin(login).orElseThrow();
                if(users.getPassword().equals(password))
                {
                    errorMessage = "";
                    regMessage = "";

                    Roles role = rolesRepository.findById(users.getRole_id()).orElseThrow();
                    authorizedUser.setAuthorized(true);
                    authorizedUser.setUserId(users.getCustomer_id());

                    if(role.getPosition().equals("Администратор"))
                    {
                        authorizedUser.setRole("Администратор");
                        return "redirect:/banquet-halls-admin";
                    }

                    return "redirect:/banquet-halls";
                }
                else {
                    errorMessage = "Incorrect password";
                }
            }
            catch (Exception e)
            {
                errorMessage = "Incorrect login";
            }


        return "redirect:/login";

    }

    @GetMapping("/login")
    public String login_(Model model) {

        authorizedUser.setAuthorized(false);
        authorizedUser.setRole("Пользователь");

        errorMessage_reg = "";
        this.login = "";
        this.fullName = "";
        this.phone = "";
        this.email = "";

        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("regMessage", regMessage);

        return "login";
    }

    @GetMapping("/change-my-data/{id}")
    public String changeMyData(@PathVariable(value = "id") long id, Model model) {
        if (AuthorizationController.authorizedUser.isAuthorized()) {

            Users user = usersRepository.findById(id).orElseThrow();
            Person person = personRepository.findById(user.getPerson_id()).orElseThrow();

            model.addAttribute("login", user.getLogin());
            model.addAttribute("fullName", person.getName());
            model.addAttribute("phone", person.getPhone());
            model.addAttribute("email", person.getEmail());

            errorMessage = "";

            model.addAttribute("errorMessage", errorMessage_reg);
            return "change-my-data";

        } else {
            return "login";
        }
    }

    @PostMapping("/change-my-data")
    public String changeMyData(@RequestParam String login, @RequestParam String fullName,  @RequestParam String phone, @RequestParam String email, Model model) {

        try
        {
            Users authUser = usersRepository.findById(authorizedUser.getUserId()).orElseThrow();
            if(login.equals(authUser.getLogin()))
            {
                Person person = personRepository.findById(authUser.getPerson_id()).orElseThrow();
                person.setName(fullName);
                person.setPhone(phone);
                person.setEmail(email);
                personRepository.save(person);

                errorMessage_reg = "";
                errorMessage = "";
                return "redirect:/my-orders";
            }
            else {
                Users users = usersRepository.findByLogin(login).orElseThrow();
                errorMessage_reg = "Login is already used";

                this.login = login;
                this.fullName = fullName;
                this.phone = phone;
                this.email = email;
            }

        }
        catch (Exception e)
        {
            Users authUser = usersRepository.findById(authorizedUser.getUserId()).orElseThrow();
            authUser.setLogin(login);
            usersRepository.save(authUser);

            Person person = personRepository.findById(authUser.getPerson_id()).orElseThrow();
            person.setName(fullName);
            person.setPhone(phone);
            person.setEmail(email);
            personRepository.save(person);
            
            errorMessage_reg = "";
            errorMessage = "";
            return "redirect:/my-orders";
        }

        return "redirect:/change-my-data/" + authorizedUser.getUserId();

    }

    @GetMapping("/change-my-password/{id}")
    public String changeMyPassword(@PathVariable(value = "id") long id, Model model) {
        if (AuthorizationController.authorizedUser.isAuthorized()) {

            Users user = usersRepository.findById(id).orElseThrow();
            model.addAttribute("oldPassword", user.getPassword());

//            errorMessage = "";
//            errorMessage_reg = "";

            model.addAttribute("errorMessage_reg", errorMessage_reg);
            model.addAttribute("errorMessage", errorMessage);

            return "change-my-password";

        } else {
            return "login";
        }
    }

    @PostMapping("/change-my-password")
    public String changeMyPassword(@RequestParam String oldPassword, @RequestParam String newPassword,  @RequestParam String RepeatNewPassword, Model model) {

            Users authUser = usersRepository.findById(authorizedUser.getUserId()).orElseThrow();
            if(authUser.getPassword().equals(oldPassword)) {
                if(newPassword.equals(RepeatNewPassword)) {
                    authUser.setPassword(newPassword);
                    usersRepository.save(authUser);
                    errorMessage = "";
                    errorMessage_reg = "";
                }
                else {
                    errorMessage = "Passwords do not match";
                    errorMessage_reg = "";
                    return "redirect:/change-my-password/" + authorizedUser.getUserId();
                }
            }
            else {
                errorMessage_reg = "Incorrect old password";
                errorMessage = "";
                return "redirect:/change-my-password/" + authorizedUser.getUserId();
            }

        return "redirect:/my-orders";
    }

}
