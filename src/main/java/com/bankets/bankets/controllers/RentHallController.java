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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static com.bankets.bankets.controllers.AuthorizationController.authorizedUser;

@Controller
public class RentHallController {

    @Autowired
    private BanquetHallsRepository banquetHallsRepository;

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/banquet-hall-rent/{id}/{date}")
    public String banquetHallRent(@PathVariable(value = "date") String date, @PathVariable(value = "id") Long id, Model model) {

        if(authorizedUser.isAuthorized()) {

            BanquetHalls banquetHalls = banquetHallsRepository.findById(id).orElseThrow();
            Users users = usersRepository.findById(authorizedUser.getUserId()).orElseThrow();

            Iterable<Person> personIterable = personRepository.findAll();
            Iterator<Person> iterator = personIterable.iterator();
            while (iterator.hasNext()) {
                Person element = iterator.next();
                if (element.getPerson_id().equals(users.getPerson_id())) {

                    model.addAttribute("banquetHall", banquetHalls);
                    model.addAttribute("date", date);
                    model.addAttribute("person", element);
                    break;
                }
            }

            return "banquet-hall-rent";
        }
        else {return "login";}

    }

    @PostMapping("/banquet-hall-rent")
    public String addPurchase(@RequestParam String name, @RequestParam String phone, @RequestParam Long bHallId, @RequestParam String date, Model model) throws IOException {

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

        purchaseItems.setBanquet_halls_id(bHallId);
        purchaseItemsRepository.save(purchaseItems);

        Users user = usersRepository.findById(authorizedUser.getUserId()).orElseThrow();
        Person person = personRepository.findById(user.getPerson_id()).orElseThrow();
        person.setName(name);
        person.setPhone(phone);
        personRepository.save(person);
        Purchases purchases = new Purchases(purchaseItems.getPurchase_id(),authorizedUser.getUserId());
        purchasesRepository.save(purchases);

        return "redirect:/banquet-hall-view/" + bHallId;
    }

}
