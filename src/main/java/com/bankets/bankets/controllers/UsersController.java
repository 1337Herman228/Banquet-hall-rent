package com.bankets.bankets.controllers;

import com.bankets.bankets.controllers.extraClasses.printUsers;
import com.bankets.bankets.models.*;
import com.bankets.bankets.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Controller
public class UsersController {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @GetMapping("/users/add")
    public String usersAdd(Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            Iterable<Roles> roles = rolesRepository.findAll();
            model.addAttribute("roles",roles);
            model.addAttribute("title", "Добавить учётную запись");
            return "users-add";
        }
        else {return "login";}

    }
    @PostMapping("/users/add")
    public String usersPostAdd(@RequestParam String login, @RequestParam String password, @RequestParam Long role,  @RequestParam String phone,  @RequestParam String email,  @RequestParam String name ,Model model) {
        Person person = new Person(phone,email,name);
        personRepository.save(person);

        Users users = new Users(role,person.getPerson_id(),login,password);
        Roles roles = rolesRepository.findById(role).orElseThrow();

        usersRepository.save(users);
        return "redirect:/users";
    }
    @GetMapping("/users")
    public String users(Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            ArrayList<printUsers> printUsersArrayList = new ArrayList<>();

            Iterable<Users> users = usersRepository.findAll();
            Iterator<Users> iterator = users.iterator();
            while (iterator.hasNext()) {
                Users element = iterator.next();
                printUsers e = new printUsers();

                e.setCustomer_id(element.getCustomer_id());
                e.setPerson_id(element.getPerson_id());
                e.setRole_id(element.getRole_id());
                e.setLogin(element.getLogin());
                e.setPassword(element.getPassword());

                Person person = personRepository.findById( element.getPerson_id()).orElseThrow();
                e.setFio(person.getName());
                e.setEmail(person.getEmail());
                e.setPhone(person.getPhone());

                Roles roles = rolesRepository.findById(element.getRole_id()).orElseThrow();
                e.setRole(roles.getPosition());

                printUsersArrayList.add(e);
            }

            ArrayList<String> rolesName = new ArrayList<>();
            Iterable<Roles> roles = rolesRepository.findAll();
            Iterator<Roles> rolesIterator = roles.iterator();
            while (rolesIterator.hasNext()) {
                Roles element = rolesIterator.next();
                rolesName.add(element.getPosition());
            }

            model.addAttribute("users",printUsersArrayList);
            model.addAttribute("roles",rolesName);

            return "users";

        }
        else {return "login";}

    }


    @GetMapping("/role/add")
    public String roleAdd(Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            model.addAttribute("title", "Добавить роль");
            return "role-add";
        }
        else {return "login";}

    }
    @PostMapping("/role/add")
    public String rolePostAdd(@RequestParam String role_name,Model model) {
        Roles roles = new Roles(role_name);
        rolesRepository.save(roles);
        return "redirect:/role";
    }

    @GetMapping("/role")
    public String role(Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            Iterable<Roles> roles = rolesRepository.findAll();
            model.addAttribute("roles",roles);
            model.addAttribute("title", "Роли ");
            return "role";
        }
        else {return "login";}

    }
    @PostMapping("/role/{id}/remove")
    public String roleDelete( @PathVariable(value = "id") long id,Model model) {

        Iterable<Users> users = usersRepository.findAll();
        Iterator<Users> iterator = users.iterator();
        while (iterator.hasNext()) {
            Users element = iterator.next();
            if(element.getRole_id() == id) {

                Long purchaseId = 0L;
                Iterable<Purchases> purchases = purchasesRepository.findAll();
                Iterator<Purchases> iterator1 = purchases.iterator();
                while (iterator1.hasNext()) {
                    Purchases purchases1 = iterator1.next();
                    if (purchases1.getCustomer_id() == element.getCustomer_id()) {
                        purchaseId = purchases1.getPurchase_id();

                        Purchases del_purchases = purchasesRepository.findById(purchaseId).orElseThrow();
                        purchasesRepository.delete(del_purchases);
                        PurchaseItems purchaseItems2 = purchaseItemsRepository.findById(purchaseId).orElseThrow();
                        purchaseItemsRepository.delete(purchaseItems2);
                    }
                }

                Iterable<Favorite> favorites = favoriteRepository.findAll();
                Iterator<Favorite> iterator2 = favorites.iterator();
                while (iterator2.hasNext()) {
                    Favorite favoriteItem = iterator2.next();
                    if (favoriteItem.getId().getCustomer_id().equals(element.getCustomer_id())) {
                        favoriteRepository.delete(favoriteItem);
                    }
                }

                usersRepository.delete(element);
                Person person = personRepository.findById(element.getPerson_id()).orElseThrow();
                personRepository.delete(person);
            }
        }
        Roles roles = rolesRepository.findById(id).orElseThrow();
        rolesRepository.delete(roles);

        return "redirect:/role";
    }
    @GetMapping("/role/{id}/change")
    public String roleChange(@PathVariable(value = "id") Long id, Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            if(!rolesRepository.existsById(id)) {
                return "redirect:/role";
            }
            Optional<Roles> roles = rolesRepository.findById(id);
            ArrayList<Roles> res = new ArrayList<>();
            roles.ifPresent(res::add);
            model.addAttribute("roles", res);

            return "role-change";

        }
        else {return "login";}

    }
    @PostMapping("/role/{id}/change")
    public String roleChange (@PathVariable(value = "id") Long id, @RequestParam String role_name, Model model) {

        Roles roles = rolesRepository.findById(id).orElseThrow();
        roles.setPosition(role_name);
        rolesRepository.save(roles);

        return "redirect:/role";
    }


    @PostMapping("/users/{id}/remove")
    public String usersDelete( @PathVariable(value = "id") long id,Model model) {
        Users users = usersRepository.findById(id).orElseThrow();
        long person_id = users.getPerson_id();

        Long purchaseId = 0L;
        Iterable<Purchases> purchases = purchasesRepository.findAll();
        Iterator<Purchases> iterator1 = purchases.iterator();
        while (iterator1.hasNext()) {
            Purchases purchases1 = iterator1.next();
            if (purchases1.getCustomer_id() == users.getCustomer_id()) {
                purchaseId = purchases1.getPurchase_id();

                Purchases del_purchases = purchasesRepository.findById(purchaseId).orElseThrow();
                purchasesRepository.delete(del_purchases);
                PurchaseItems purchaseItems2 = purchaseItemsRepository.findById(purchaseId).orElseThrow();
                purchaseItemsRepository.delete(purchaseItems2);
            }
        }

        Iterable<Favorite> favorites = favoriteRepository.findAll();
        Iterator<Favorite> iterator2 = favorites.iterator();
        while (iterator2.hasNext()) {
            Favorite favoriteItem = iterator2.next();
            if (favoriteItem.getId().getCustomer_id().equals(users.getCustomer_id())) {
                favoriteRepository.delete(favoriteItem);
            }
        }

        usersRepository.delete(users);
        Person person = personRepository.findById(person_id).orElseThrow();
        personRepository.delete(person);

        return "redirect:/users";
    }

    @GetMapping("/users/{id}/change")
    public String usersChange(@PathVariable(value = "id") Long id, Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            if(!usersRepository.existsById(id)) {
                return "redirect:/users";
            }

            Iterable<Roles> roles = rolesRepository.findAll();
            model.addAttribute("roles",roles);

            Users users = usersRepository.findById(id).orElseThrow();
            Person person = personRepository.findById(users.getPerson_id()).orElseThrow();

            ArrayList<printUsers> printUsersArrayList = new ArrayList<>();
            printUsers e = new printUsers();

            e.setCustomer_id(users.getCustomer_id());
            e.setPerson_id(users.getPerson_id());
            e.setRole_id(users.getRole_id());
            e.setLogin(users.getLogin());
            e.setPassword(users.getPassword());

            e.setFio(person.getName());
            e.setEmail(person.getEmail());
            e.setPhone(person.getPhone());

            Roles roless = rolesRepository.findById(users.getRole_id()).orElseThrow();
            e.setRole(roless.getPosition());

            printUsersArrayList.add(e);

            model.addAttribute("users", printUsersArrayList);

            return "user-change";

        }
        else {return "login";}

    }

    @PostMapping("/users/{id}/change")
    public String usersChange (@PathVariable(value = "id") Long id,@RequestParam String login, @RequestParam String password, @RequestParam Long role,  @RequestParam String phone,  @RequestParam String email,  @RequestParam String name ,Model model) {

        Users users = usersRepository.findById(id).orElseThrow();
        users.setLogin(login);
        users.setPassword(password);
        users.setRole_id(role);

        Person person = personRepository.findById(users.getPerson_id()).orElseThrow();
        person.setName(name);
        person.setEmail(email);
        person.setPhone(phone);
        personRepository.save(person);

        usersRepository.save(users);

        return "redirect:/users";
    }


}
