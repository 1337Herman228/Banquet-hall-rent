package com.bankets.bankets.controllers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.bankets.bankets.controllers.extraClasses.printHalls;
import com.bankets.bankets.controllers.extraClasses.printPurchases;
import com.bankets.bankets.controllers.extraClasses.printUsers;
import com.bankets.bankets.models.*;
import com.bankets.bankets.repo.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

@Controller
public class PurchaseController {

    @Autowired
    private BanquetHallsRepository banquetHallsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;


    @GetMapping("/purchases")
    public String purchases(Model model) throws IOException {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            ArrayList<printPurchases> printPurchases = new ArrayList<>();

            Iterable<BanquetHalls> banquetHalls = banquetHallsRepository.findAll();
            Iterator<BanquetHalls> iterator = banquetHalls.iterator();
            while (iterator.hasNext()) {
                BanquetHalls element = iterator.next();
                printHalls e = new printHalls();

                e.setBanquet_halls_id(element.getBanquet_halls_id());
                e.setBanquet_halls_name(element.getBanquet_halls_name());
                e.setPrice(element.getPrice());

                Long purchaseItems_id = 0L;
                Date purchaseItems_date = null;
                Iterable<PurchaseItems> purchaseItems = purchaseItemsRepository.findAll();
                Iterator<PurchaseItems> iterator1 = purchaseItems.iterator();

                while (iterator1.hasNext()) {
                    PurchaseItems purchaseItems1 = iterator1.next();
                    if(purchaseItems1.getBanquet_halls_id().equals(element.getBanquet_halls_id())) {
                        purchaseItems_id = purchaseItems1.getPurchase_id();
                        purchaseItems_date = purchaseItems1.getPurchase_date();

                        Purchases purchases = purchasesRepository.findById(purchaseItems_id).orElseThrow();
                        Users users = usersRepository.findById(purchases.getCustomer_id()).orElseThrow();
                        Person person = personRepository.findById(users.getPerson_id()).orElseThrow();

                        printUsers p = new printUsers();
                        p.setFio(person.getName());
                        p.setPhone(person.getPhone());

                        printPurchases pP = new printPurchases(e, p, purchaseItems_date.toString().replaceFirst("00:00:00.0",""), purchaseItems_id);
                        printPurchases.add(pP);
                    }
                }

            }
            model.addAttribute("purchases",printPurchases);

            return "purchases";
        }
        else {return "login";}

    }

    @GetMapping("/purchases/add")
    public String purchasesAdd(Model model) throws IOException {

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

                printUsersArrayList.add(e);
            }
            model.addAttribute("users",printUsersArrayList);

            Iterable<BanquetHalls> banquetHalls = banquetHallsRepository.findAll();
            model.addAttribute("halls",banquetHalls);

            return "purchases-add";
        }
        else {return "login";}

    }

    @PostMapping("/purchases/add")
    public String purchasesAdd(@RequestParam Long user,  @RequestParam Long hall, @RequestParam String date,  Model model) throws IOException {

        PurchaseItems purchaseItems = new PurchaseItems();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate;
        try {
            parseDate = dateFormat.parse(date); // Преобразуем строку в объект Date
            purchaseItems.setPurchase_date(parseDate);
        } catch (ParseException e) {
            // Обработка ошибки парсинга даты
            e.printStackTrace();
        }

        purchaseItems.setBanquet_halls_id(hall);
        purchaseItemsRepository.save(purchaseItems);
        Purchases purchases = new Purchases(purchaseItems.getPurchase_id(),user);
        purchasesRepository.save(purchases);

        return "redirect:/purchases";
    }

    @PostMapping("/purchases/{id}/remove")
    public String purchasesDelete(@PathVariable(value = "id") long id, Model model) {

        Purchases purchases = purchasesRepository.findById(id).orElseThrow();
        purchasesRepository.delete(purchases);

        PurchaseItems purchaseItems = purchaseItemsRepository.findById(id).orElseThrow();
        purchaseItemsRepository.delete(purchaseItems);

        return "redirect:/purchases";
    }

    @GetMapping("/purchases/{id}/change")
    public String purchasesChange(@PathVariable(value = "id") Long id, Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            if(!purchasesRepository.existsById(id)) {
                return "redirect:/purchases";
            }

            PurchaseItems purchaseItems = purchaseItemsRepository.findById(id).orElseThrow();
            LocalDate localDate = purchaseItems.getPurchase_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            LocalDate date = localDate;
            Purchases purchases = purchasesRepository.findById(id).orElseThrow();
            Long user = purchases.getCustomer_id();
            Long hall = purchaseItems.getBanquet_halls_id();

            model.addAttribute("date",date);
            model.addAttribute("user",user);
            model.addAttribute("hall",hall);

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

                printUsersArrayList.add(e);
            }

            model.addAttribute("users",printUsersArrayList);

            Iterable<BanquetHalls> banquetHalls = banquetHallsRepository.findAll();
            model.addAttribute("halls",banquetHalls);

            return "purchases-change";
        }
        else {return "login";}


    }

    @PostMapping("/purchases/{id}/change")
    public String purchasesChange(@PathVariable(value = "id") long id, @RequestParam Long user,  @RequestParam Long hall, @RequestParam String date,  Model model) throws IOException {

        Purchases purchases = purchasesRepository.findById(id).orElseThrow();
        purchases.setCustomer_id(user);
        purchasesRepository.save(purchases);

        PurchaseItems purchaseItems = purchaseItemsRepository.findById(id).orElseThrow();
        purchaseItems.setBanquet_halls_id(hall);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate;
        try {
            parseDate = dateFormat.parse(date); // Преобразуем строку в объект Date
            purchaseItems.setPurchase_date(parseDate);
        } catch (ParseException e) {
            // Обработка ошибки парсинга даты
            e.printStackTrace();
        }

        purchaseItemsRepository.save(purchaseItems);

        return "redirect:/purchases";
    }

}
